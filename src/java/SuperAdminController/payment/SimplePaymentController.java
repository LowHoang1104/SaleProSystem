package SuperAdminController.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller đơn giản để test VNPay
 */
@WebServlet(name = "SimplePaymentController", urlPatterns = {"/payment/*"})
public class SimplePaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if ("/return".equals(pathInfo)) {
            handleVNPayReturn(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if ("/create".equals(pathInfo)) {
            handleCreatePayment(request, response);
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
            String packageId = request.getParameter("packageId");
            String shopId = request.getParameter("shopId");

            if (packageId == null || shopId == null) {
                response.sendRedirect(request.getContextPath()
                        + "/SubscriptionController?status=failed&message=Thông tin không hợp lệ");
                return;
            }

            // Tạo VNPay URL
            String paymentUrl = createVNPayUrl(request, packageId, shopId);

            response.sendRedirect(paymentUrl);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath()
                    + "/SubscriptionController?status=failed&message=Lỗi tạo thanh toán");
        }
    }

    /**
     * Tạo VNPay payment URL
     */
    private String createVNPayUrl(HttpServletRequest request, String packageId, String shopId) throws Exception {
        long amount = getPackagePrice(packageId) * 100; // VNPay yêu cầu nhân 100

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", VNPayConfig.VNP_VERSION);
        vnpParams.put("vnp_Command", VNPayConfig.VNP_COMMAND);
        vnpParams.put("vnp_TmnCode", VNPayConfig.VNP_TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", VNPayConfig.VNP_CURR_CODE);
        vnpParams.put("vnp_TxnRef", VNPayUtils.generateTxnRef());
        vnpParams.put("vnp_OrderInfo", "Thanh toan goi dich vu " + packageId + " cho shop " + shopId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", VNPayConfig.VNP_LOCALE);
        vnpParams.put("vnp_ReturnUrl", VNPayConfig.VNP_RETURN_URL);
        vnpParams.put("vnp_IpAddr", getClientIP(request));
        vnpParams.put("vnp_CreateDate", VNPayUtils.formatDate(new java.util.Date()));

        // Bước 1: Tạo chuỗi hash
        String hashData = VNPayUtils.buildHashData(vnpParams);

        // Bước 2: Sinh Secure Hash
        String secureHash = VNPayUtils.hmacSHA512(VNPayConfig.VNP_HASH_SECRET, hashData);
        vnpParams.put("vnp_SecureHash", secureHash);

        // Debug
        System.out.println("=== FINAL DEBUG ===");
        System.out.println("Hash Data: " + hashData);
        System.out.println("Generated Hash: " + secureHash);
        System.out.println("===================");

        // Bước 3: Build URL
        return VNPayConfig.VNP_URL + "?" + VNPayUtils.buildQuery(vnpParams);
    }

    /**
     * Xử lý return từ VNPay
     */
    private void handleVNPayReturn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Map<String, String> vnpParams = new HashMap<>();
            for (String paramName : request.getParameterMap().keySet()) {
                String paramValue = request.getParameter(paramName);
                if (paramValue != null && !paramValue.isEmpty()) {
                    vnpParams.put(paramName, paramValue);
                }
            }

            // Lấy vnp_SecureHash và xóa khỏi map
            String vnpSecureHash = vnpParams.remove("vnp_SecureHash");
            vnpParams.remove("vnp_SecureHashType");

            // Tạo hash từ dữ liệu nhận về
            String hashData = VNPayUtils.buildHashData(vnpParams);
            String calculatedHash = VNPayUtils.hmacSHA512(VNPayConfig.VNP_HASH_SECRET, hashData);

            String status;
            String message;

            if (vnpSecureHash.equalsIgnoreCase(calculatedHash)) {
                String vnpResponseCode = vnpParams.get("vnp_ResponseCode");
                if (VNPayConfig.SUCCESS_CODE.equals(vnpResponseCode)) {
                    status = "success";
                    message = "Thanh toán thành công";
                    System.out.println("Payment success: " + vnpParams.get("vnp_TxnRef"));
                } else {
                    status = "failed";
                    message = "Thanh toán thất bại - Mã lỗi: " + vnpResponseCode;
                }
            } else {
                status = "failed";
                message = "Chữ ký không hợp lệ";
            }

            response.sendRedirect(request.getContextPath()
                    + "/SubscriptionController?status=" + status
                    + "&message=" + java.net.URLEncoder.encode(message, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath()
                    + "/SubscriptionController?status=failed&message=Lỗi xử lý kết quả");
        }
    }

    /**
     * Giả lập lấy giá gói (thực tế từ database)
     */
    private long getPackagePrice(String packageId) {
        switch (packageId) {
            case "1": return 100000;
            case "2": return 250000;
            case "3": return 450000;
            case "4": return 800000;
            default:  return 100000;
        }
    }

    /**
     * Get client IP address - Force IPv4
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
