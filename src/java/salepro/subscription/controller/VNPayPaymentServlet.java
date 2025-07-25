package salepro.subscription.controller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.SuperAdmin.PaymentTransactionDAO;
import salepro.dao.SuperAdmin.ServicePackageDAO;
import salepro.models.SuperAdmin.PaymentTransaction;
import salepro.models.SuperAdmin.ServicePackage;
import salepro.models.SuperAdmin.ShopOwner;
import vnpay.common.Config;

@WebServlet(name = "VNPayPaymentServlet", urlPatterns = {"/payment", "/payment/*"})
public class VNPayPaymentServlet extends HttpServlet {

    private PaymentTransactionDAO transactionDAO = new PaymentTransactionDAO();
    private ServicePackageDAO packageDAO = new ServicePackageDAO();
    private ShopOwnerDAO shopOwnerDAO = new ShopOwnerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if ("/create".equals(pathInfo)) {
            handleCreatePayment(request, response);
        } else if ("/ipn".equals(pathInfo)) {
            handlePaymentIPN(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if ("/return".equals(pathInfo)) {
            handlePaymentReturn(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Tạo thanh toán VNPay
     */
    private void handleCreatePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // XÓA THÔNG BÁO CŨ TRƯỚC KHI TẠO THANH TOÁN MỚI
            clearMessages(request);

            // Lấy parameters
            String packageIdStr = request.getParameter("packageId");
            String shopIdStr = request.getParameter("shopId");

            if (packageIdStr == null || shopIdStr == null) {
                request.getSession().setAttribute("errorMessage", "Thông tin không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            Integer packageId = Integer.parseInt(packageIdStr);
            Integer shopId = Integer.parseInt(shopIdStr);

            // Lấy thông tin từ DAO
            ServicePackage servicePackage = packageDAO.findById(packageId);
            ShopOwner shopOwner = shopOwnerDAO.findById(shopId);

            if (servicePackage == null || shopOwner == null) {
                request.getSession().setAttribute("errorMessage", "Không tìm thấy thông tin");
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            // Tạo transaction code
            String transactionCode = "SUB" + System.currentTimeMillis();

            // Tạo parameters cho VNPay
            Map<String, String> vnpParams = new HashMap<>();
            vnpParams.put("vnp_Version", "2.1.0");
            vnpParams.put("vnp_Command", "pay");
            vnpParams.put("vnp_TmnCode", Config.vnp_TmnCode);
            vnpParams.put("vnp_Amount", String.valueOf((long) (servicePackage.getPrice() * 100)));
            vnpParams.put("vnp_CurrCode", "VND");
            vnpParams.put("vnp_TxnRef", transactionCode);
            vnpParams.put("vnp_OrderInfo", "Subscription renewal");
            vnpParams.put("vnp_OrderType", "other");
            vnpParams.put("vnp_Locale", "vn");
            vnpParams.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnpParams.put("vnp_IpAddr", Config.getIpAddress(request));

            // Thời gian tạo và hết hạn
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnpCreateDate = formatter.format(cld.getTime());
            vnpParams.put("vnp_CreateDate", vnpCreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnpExpireDate = formatter.format(cld.getTime());
            vnpParams.put("vnp_ExpireDate", vnpExpireDate);

            // Tạo hash và query string (với encoding)
            List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnpParams.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Build hash data (với encoding)
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnpSecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

            // Lưu transaction vào database
            PaymentTransaction transaction = new PaymentTransaction();
            transaction.setTransactionCode(transactionCode);
            transaction.setShopOwner(shopOwner);
            transaction.setServicePackage(servicePackage);
            transaction.setAmount(servicePackage.getPrice());
            transaction.setStatus("Pending");
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setExpiredAt(LocalDateTime.now().plusMinutes(15));
            transaction.setVnPayOrderInfo("Subscription renewal");

            boolean created = transactionDAO.createTransaction(transaction);
            if (!created) {
                request.getSession().setAttribute("errorMessage", "Không thể tạo giao dịch");
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            // Redirect đến VNPay
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
            response.sendRedirect(paymentUrl);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/subscription/");
        }
    }

    /**
     * Xử lý kết quả trả về từ VNPay
     */
    private void handlePaymentReturn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // XÓA THÔNG BÁO CŨ TRƯỚC KHI XỬ LÝ KẾT QUẢ
            clearMessages(request);

            // Lấy tất cả parameters từ VNPay
            Map<String, String> vnpParams = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                vnpParams.put(paramName, paramValue);
            }

            String vnpSecureHash = request.getParameter("vnp_SecureHash");
            String vnpTxnRef = request.getParameter("vnp_TxnRef");
            String vnpResponseCode = request.getParameter("vnp_ResponseCode");
            String vnpTransactionStatus = request.getParameter("vnp_TransactionStatus");

            // Validate signature
            if (!validateReturnSignature(vnpParams, vnpSecureHash)) {
                request.getSession().setAttribute("errorMessage", "Chữ ký thanh toán không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            // Tìm transaction
            PaymentTransaction transaction = transactionDAO.findByTransactionCode(vnpTxnRef);
            if (transaction == null) {
                request.getSession().setAttribute("errorMessage", "Không tìm thấy giao dịch");
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            // Kiểm tra transaction đã được xử lý chưa
            if (!"Pending".equals(transaction.getStatus())) {
                if ("Completed".equals(transaction.getStatus())) {
                    request.getSession().setAttribute("successMessage", "Giao dịch đã được xử lý thành công trước đó");
                } else {
                    request.getSession().setAttribute("errorMessage", "Giao dịch đã được xử lý với trạng thái: " + transaction.getStatus());
                }
                response.sendRedirect(request.getContextPath() + "/subscription/");
                return;
            }

            // Cập nhật thông tin VNPay
            transactionDAO.updateVnPayInfo(
                    transaction.getTransactionId(),
                    vnpParams.get("vnp_TxnRef"),
                    vnpParams.get("vnp_TransactionNo"),
                    vnpParams.get("vnp_OrderInfo"),
                    vnpParams.get("vnp_ResponseCode"),
                    vnpParams.get("vnp_TransactionStatus")
            );

            // Kiểm tra kết quả thanh toán
            if ("00".equals(vnpResponseCode) && "00".equals(vnpTransactionStatus)) {
                // Thanh toán thành công
                boolean success = processSuccessfulPayment(request, transaction);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Thanh toán thành công! Dịch vụ đã được gia hạn.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Thanh toán thành công nhưng có lỗi khi cập nhật dịch vụ");
                }
            } else {
                // Thanh toán thất bại
                transactionDAO.updateTransactionStatus(transaction.getTransactionId(), "Failed");
                String errorMessage = getDetailedErrorMessage(vnpResponseCode);
                request.getSession().setAttribute("errorMessage", errorMessage);
            }

            response.sendRedirect(request.getContextPath() + "/subscription/");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra khi xử lý kết quả thanh toán");
            response.sendRedirect(request.getContextPath() + "/subscription/");
        }
    }

    /**
     * Validate signature cho return (với encoding - giống như lúc gửi)
     */
    private boolean validateReturnSignature(Map<String, String> params, String receivedSignature) {
        try {
            if (receivedSignature == null) {
                return false;
            }

            // Loại bỏ vnp_SecureHash và vnp_SecureHashType
            Map<String, String> cleanParams = new HashMap<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!entry.getKey().equals("vnp_SecureHash") && !entry.getKey().equals("vnp_SecureHashType")) {
                    cleanParams.put(entry.getKey(), entry.getValue());
                }
            }

            // Sắp xếp và tạo hash data (với encoding - giống lúc gửi)
            List<String> fieldNames = new ArrayList<>(cleanParams.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = cleanParams.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    if (itr.hasNext()) {
                        hashData.append('&');
                    }
                }
            }

            String calculatedSignature = Config.hmacSHA512(Config.secretKey, hashData.toString());
            return calculatedSignature.equals(receivedSignature);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xử lý IPN từ VNPay
     */
    private void handlePaymentIPN(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy tất cả parameters
            Map<String, String> vnpParams = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                vnpParams.put(paramName, paramValue);
            }

            String vnpSecureHash = request.getParameter("vnp_SecureHash");
            String vnpTxnRef = request.getParameter("vnp_TxnRef");
            String vnpResponseCode = request.getParameter("vnp_ResponseCode");

            // Validate signature
            if (!validateReturnSignature(vnpParams, vnpSecureHash)) {
                response.getWriter().write(createIPNResponse("97", "Invalid signature"));
                return;
            }

            PaymentTransaction transaction = transactionDAO.findByTransactionCode(vnpTxnRef);
            if (transaction == null) {
                response.getWriter().write(createIPNResponse("01", "Order not found"));
                return;
            }

            // Kiểm tra trạng thái transaction
            if (!"Pending".equals(transaction.getStatus())) {
                response.getWriter().write(createIPNResponse("02", "Order already confirmed"));
                return;
            }

            // Cập nhật thông tin VNPay
            transactionDAO.updateVnPayInfo(
                    transaction.getTransactionId(),
                    vnpParams.get("vnp_TxnRef"),
                    vnpParams.get("vnp_TransactionNo"),
                    vnpParams.get("vnp_OrderInfo"),
                    vnpParams.get("vnp_ResponseCode"),
                    vnpParams.get("vnp_TransactionStatus")
            );

            if ("00".equals(vnpResponseCode)) {
                processSuccessfulPayment(null, transaction);
                response.getWriter().write(createIPNResponse("00", "Confirm Success"));
            } else {
                transactionDAO.updateTransactionStatus(transaction.getTransactionId(), "Failed");
                response.getWriter().write(createIPNResponse("00", "Confirm Success"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(createIPNResponse("99", "Unknown error"));
        }
    }

    /**
     * Xử lý thanh toán thành công (GIỮ NGUYÊN LOGIC GỐC)
     */
    private boolean processSuccessfulPayment(HttpServletRequest request, PaymentTransaction transaction) {
        try {
            // Cập nhật trạng thái transaction
            boolean statusUpdated = transactionDAO.updateTransactionStatus(transaction.getTransactionId(), "Completed");
            if (!statusUpdated) {
                return false;
            }

            // Cập nhật subscription của shop owner
            ShopOwner shopOwner = transaction.getShopOwner();
            ServicePackage servicePackage = transaction.getServicePackage();

            if (shopOwner == null || servicePackage == null) {
                return false;
            }

            // Tính toán ngày gia hạn (LOGIC GỐC CỦA BẠN)
            LocalDateTime currentEndDate = shopOwner.getSubscriptionEndDate();
            LocalDateTime newStartDate = LocalDateTime.now();
            LocalDateTime newEndDate;

            if (currentEndDate != null && currentEndDate.isAfter(LocalDateTime.now())) {
                // Nếu còn thời gian, gia hạn từ ngày hết hạn hiện tại
                newEndDate = currentEndDate.plusMonths(servicePackage.getDurationMonths());
            } else {
                // Nếu đã hết hạn, gia hạn từ ngày hiện tại
                newEndDate = LocalDateTime.now().plusMonths(servicePackage.getDurationMonths());
                shopOwner.setSubscriptionStartDate(newStartDate);
            }

            // Cập nhật thông tin subscription (LOGIC GỐC CỦA BẠN)
            shopOwner.setSubscriptionEndDate(newEndDate);
            shopOwner.setSubscriptionStatus("Active");
            shopOwner.setLastPaymentDate(LocalDateTime.now());
            shopOwner.setIsActive(true);

            // Cập nhật vào database (GIỮ NGUYÊN HÀM CỦA BẠN)
            boolean updated = shopOwnerDAO.updateSubscription(shopOwner);

            if (!updated) {
                return false;
            }

            // Refresh session nếu có request
            if (request != null) {
                refreshShopOwnerSession(request, shopOwner.getShopOwnerId());
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== HELPER METHODS ==========
    /**
     * Xóa tất cả thông báo cũ
     */
    private void clearMessages(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("errorMessage");
        session.removeAttribute("successMessage");
    }

    /**
     * Set error message
     */
    private void setErrorMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("errorMessage", message);
    }

    /**
     * Set success message
     */
    private void setSuccessMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("successMessage", message);
    }

    /**
     * Refresh ShopOwner trong session
     */
    private void refreshShopOwnerSession(HttpServletRequest request, Integer shopOwnerId) {
        try {
            HttpSession session = request.getSession();
            ShopOwner sessionShopOwner = (ShopOwner) session.getAttribute("shopOwner");

            if (sessionShopOwner != null && sessionShopOwner.getShopOwnerId().equals(shopOwnerId)) {
                // Refresh thông tin shopOwner từ database
                ShopOwner freshShopOwner = shopOwnerDAO.findById(shopOwnerId);
                if (freshShopOwner != null) {
                    session.setAttribute("shopOwner", freshShopOwner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy thông báo lỗi chi tiết theo mã VNPay
     */
    private String getDetailedErrorMessage(String responseCode) {
        switch (responseCode) {
            case "24":
                return "Giao dịch bị hủy: Bạn đã hủy thanh toán trên trang VNPay";
            case "05":
                return "Thanh toán thất bại: Tài khoản không đủ số dư";
            case "06":
                return "Thanh toán thất bại: Thông tin thẻ không chính xác";
            case "07":
                return "Thanh toán thất bại: Thẻ đã hết hạn";
            case "09":
                return "Thanh toán thất bại: Thẻ chưa đăng ký dịch vụ Internet Banking";
            case "10":
                return "Thanh toán thất bại: Thẻ đã bị khóa";
            case "11":
                return "Thanh toán thất bại: Mã OTP không chính xác";
            case "12":
                return "Thanh toán thất bại: OTP đã hết hạn";
            case "13":
                return "Thanh toán thất bại: Vượt quá số lần nhập OTP cho phép";
            case "51":
                return "Thanh toán thất bại: Tài khoản không đủ số dư để thực hiện giao dịch";
            case "65":
                return "Thanh toán thất bại: Vượt quá hạn mức giao dịch trong ngày";
            case "75":
                return "Thanh toán thất bại: Ngân hàng đang bảo trì, vui lòng thử lại sau";
            case "79":
                return "Thanh toán thất bại: Giao dịch vượt quá hạn mức cho phép";
            case "99":
                return "Thanh toán thất bại: Có lỗi xảy ra trong quá trình xử lý";
            default:
                return "Thanh toán thất bại: Mã lỗi " + responseCode + " - Vui lòng liên hệ hỗ trợ";
        }
    }

    /**
     * Tạo response cho IPN
     */
    private String createIPNResponse(String responseCode, String message) {
        JsonObject response = new JsonObject();
        response.addProperty("RspCode", responseCode);
        response.addProperty("Message", message);
        return response.toString();
    }
}