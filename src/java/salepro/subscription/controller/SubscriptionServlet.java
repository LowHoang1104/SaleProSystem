package salepro.subscription.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.SuperAdmin.ServicePackageDAO;
import salepro.models.SuperAdmin.PaymentTransaction;
import salepro.models.SuperAdmin.ServicePackage;
import salepro.models.SuperAdmin.ShopOwner;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "SubscriptionServlet", urlPatterns = {"/subscription/*"})
public class SubscriptionServlet extends HttpServlet {

    public static final String SUBSCRIPTION = "/view/jsp/SubscriptionExpired/subscription-renewal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            ShopOwner shopOwner = (ShopOwner) session.getAttribute("ShopOwner");
            if (shopOwner == null) {
                response.sendRedirect(request.getContextPath() + "/view/jsp/Login.jsp");
                return;
            }

            // ✅ REFRESH SHOPOWNER FROM DATABASE IF PAYMENT SUCCESS
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null && successMessage.contains("Thanh toán thành công")) {
                System.out.println("=== Refreshing ShopOwner from database after payment ===");

                // Lấy thông tin mới từ database
                try {
                    ShopOwnerDAO shopOwnerDAO = new ShopOwnerDAO();
                    ShopOwner refreshedShopOwner = shopOwnerDAO.findById(shopOwner.getShopOwnerId());

                    if (refreshedShopOwner != null) {
                        System.out.println("Old status: " + shopOwner.getSubscriptionStatus());
                        System.out.println("New status: " + refreshedShopOwner.getSubscriptionStatus());
                        System.out.println("Old end date: " + shopOwner.getSubscriptionEndDate());
                        System.out.println("New end date: " + refreshedShopOwner.getSubscriptionEndDate());

                        // Cập nhật session với data mới
                        session.setAttribute("ShopOwner", refreshedShopOwner);
                        shopOwner = refreshedShopOwner;

                        System.out.println("✅ ShopOwner refreshed in session");
                    } else {
                        System.out.println("❌ Failed to refresh ShopOwner from database");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error refreshing ShopOwner: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // ✅ TIẾP TỤC VỚI LOGIC CŨ...
            if (shopOwner != null) {
                logSubscriptionInfo(shopOwner);
            }

            if (shopOwner != null) {
                logSubscriptionInfo(shopOwner);
            }

            // Kiểm tra nếu là API endpoint trước
            String pathInfo = request.getPathInfo();
            if (pathInfo != null && pathInfo.startsWith("/api/")) {
                handleAPIEndpoints(request, response);
                return;
            }

            if ("/payment-history".equals(pathInfo)) {
                handlePaymentHistory(request, response);
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDate = shopOwner.getSubscriptionEndDate();

            int remainingDays = 0;
            long remainingSeconds = 0;

            if (endDate != null) {
                remainingSeconds = ChronoUnit.SECONDS.between(now, endDate);

                if (remainingSeconds > 0) {
                    remainingDays = (int) Math.ceil(remainingSeconds / 86400.0);
                } else {
                    remainingDays = (int) Math.floor(remainingSeconds / 86400.0);
                }
            }

            ServicePackageDAO packageDAO = new ServicePackageDAO();

            List<ServicePackage> packages = null;
            try {
                packages = packageDAO.getAllActivePackages();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            enhancePackagesWithBadges(packages);

            request.setAttribute("shopOwner", shopOwner);
            request.setAttribute("shopName", shopOwner.getShopName());
            request.setAttribute("shopId", shopOwner.getShopOwnerId());
            request.setAttribute("remainingDays", remainingDays);
            request.setAttribute("remainingSeconds", remainingSeconds); // Thêm seconds để debug
            request.setAttribute("currentStatus", shopOwner.getSubscriptionStatus());
            request.setAttribute("subscriptionEndDate", endDate);
            request.setAttribute("packages", packages);

            boolean needsRenewal = remainingSeconds <= 0 || "Trial".equals(shopOwner.getSubscriptionStatus()) || "Expired".equals(shopOwner.getSubscriptionStatus());
            request.setAttribute("isRenewal", true);
            request.setAttribute("needsRenewal", needsRenewal);

            // Sử dụng remainingSeconds để kiểm tra chính xác hơn
            if (remainingSeconds <= 0) {
                if ("Trial".equals(shopOwner.getSubscriptionStatus())) {
                    request.setAttribute("warningMessage", "Gói dùng thử đã hết hạn. Vui lòng chọn gói dịch vụ để tiếp tục sử dụng.");
                } else {
                    request.setAttribute("warningMessage", "Dịch vụ đã hết hạn. Vui lòng gia hạn để tiếp tục sử dụng.");
                }
            } else if (remainingSeconds <= 3 * 86400) {
                if (remainingDays <= 0) {
                    request.setAttribute("warningMessage", "Dịch vụ hết hạn trong ngày hôm nay. Gia hạn ngay để tránh gián đoạn!");
                } else {
                    request.setAttribute("warningMessage", "Dịch vụ sắp hết hạn trong " + remainingDays + " ngày. Gia hạn ngay để được ưu đãi!");
                }
            }

            // Check payment status
            String paymentStatus = request.getParameter("status");

            if ("success".equals(paymentStatus)) {
                request.setAttribute("successMessage", "Thanh toán thành công! Dịch vụ đã được gia hạn.");
            } else if ("failed".equals(paymentStatus)) {
                request.setAttribute("errorMessage", "Thanh toán thất bại. Vui lòng thử lại.");
            } else if ("cancelled".equals(paymentStatus)) {
                request.setAttribute("infoMessage", "Bạn đã hủy thanh toán. Có thể thử lại bất cứ lúc nào.");
            }

            // Add context path for JSP
            request.setAttribute("contextPath", request.getContextPath());

            try {
                request.setAttribute("formattedRemainingTime", formatRemainingTime(remainingSeconds));
                request.getRequestDispatcher(SUBSCRIPTION).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());

            // Tạo error page đơn giản
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Lỗi</title></head><body>");
                out.println("<h2>Đã xảy ra lỗi</h2>");
                out.println("<p>Chi tiết: " + e.getMessage() + "</p>");
                out.println("<p><a href='" + request.getContextPath() + "/view/jsp/Login.jsp'>Quay lại đăng nhập</a></p>");
                out.println("</body></html>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Xử lý API endpoint cho subscription status Thêm vào doGet() method sau
     * phần check pathInfo
     */
    private void handleAPIEndpoints(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if ("/api/subscription-status".equals(pathInfo)) {
            handleSubscriptionStatusAPI(request, response);
            return;
        }
    }

    /**
     * API trả về trạng thái subscription (cho AJAX calls)
     */
    private void handleSubscriptionStatusAPI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            ShopOwner shopOwner = (ShopOwner) session.getAttribute("ShopOwner");

            if (shopOwner == null) {
                response.getWriter().write("{\"success\":false,\"message\":\"Không tìm thấy thông tin phiên làm việc\"}");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDate = shopOwner.getSubscriptionEndDate();
            long remainingSeconds = 0;
            int remainingDays = 0;

            if (endDate != null) {
                remainingSeconds = ChronoUnit.SECONDS.between(now, endDate);
                if (remainingSeconds > 0) {
                    remainingDays = (int) Math.ceil(remainingSeconds / 86400.0);
                } else {
                    remainingDays = (int) Math.floor(remainingSeconds / 86400.0);
                }
            }

            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"success\":true,");
            json.append("\"shopName\":\"").append(escapeJson(shopOwner.getShopName())).append("\",");
            json.append("\"subscriptionStatus\":\"").append(shopOwner.getSubscriptionStatus()).append("\",");
            json.append("\"remainingDays\":").append(remainingDays).append(",");
            json.append("\"remainingSeconds\":").append(remainingSeconds).append(",");
            json.append("\"subscriptionEndDate\":\"").append(endDate != null ? endDate.toString() : "null").append("\",");
            json.append("\"needsRenewal\":").append(remainingSeconds <= 0).append("");
            json.append("}");

            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false,\"message\":\"Có lỗi xảy ra: " + escapeJson(e.getMessage()) + "\"}");
        }
    }

    /**
     * Xử lý trang lịch sử thanh toán
     */
    private void handlePaymentHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            ShopOwner shopOwner = (ShopOwner) session.getAttribute("ShopOwner");

            if (shopOwner == null) {
                response.sendRedirect(request.getContextPath() + "/view/jsp/Login.jsp");
                return;
            }

            try {
                salepro.dao.SuperAdmin.PaymentTransactionDAO transactionDAO
                        = new salepro.dao.SuperAdmin.PaymentTransactionDAO();

                // ✅ THÊM method findByShopOwnerId vào PaymentTransactionDAO nếu chưa có
                // List<PaymentTransaction> transactions = transactionDAO.findByShopOwnerId(shopOwner.getShopOwnerId());
                // request.setAttribute("transactions", transactions);
                request.setAttribute("hasTransactionHistory", true);
            } catch (Exception e) {
                System.out.println("PaymentTransactionDAO not available yet: " + e.getMessage());
                request.setAttribute("hasTransactionHistory", false);
            }

            try {
                request.getRequestDispatcher("/view/jsp/SubscriptionExpired/payment-history.jsp").forward(request, response);
            } catch (Exception e) {
                // Fallback to subscription page with history info
                request.setAttribute("showPaymentHistory", true);
                request.setAttribute("infoMessage", "Tính năng lịch sử thanh toán sẽ có sớm!");
                request.getRequestDispatcher(SUBSCRIPTION).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải lịch sử thanh toán");
            request.getRequestDispatcher(SUBSCRIPTION).forward(request, response);
        }
    }

    /**
     * Tách logic hiện tại thành method riêng
     */
    private void handleRenewalPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Đây là toàn bộ logic hiện tại trong doGet()
        // Copy code hiện tại vào đây, không thay đổi gì
        HttpSession session = request.getSession();
        try {
            ShopOwner shopOwner = (ShopOwner) session.getAttribute("ShopOwner");

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDate = shopOwner.getSubscriptionEndDate();

            int remainingDays = 0;
            long remainingSeconds = 0;

            if (endDate != null) {
                remainingSeconds = ChronoUnit.SECONDS.between(now, endDate);

                if (remainingSeconds > 0) {
                    remainingDays = (int) Math.ceil(remainingSeconds / 86400.0);
                } else {
                    remainingDays = (int) Math.floor(remainingSeconds / 86400.0);
                }
            }

            ServicePackageDAO packageDAO = new ServicePackageDAO();
            List<ServicePackage> packages = packageDAO.getAllActivePackages();
            enhancePackagesWithBadges(packages);

            request.setAttribute("shopOwner", shopOwner);
            request.setAttribute("shopName", shopOwner.getShopName());
            request.setAttribute("shopId", shopOwner.getShopOwnerId());
            request.setAttribute("remainingDays", remainingDays);
            request.setAttribute("remainingSeconds", remainingSeconds);
            request.setAttribute("currentStatus", shopOwner.getSubscriptionStatus());
            request.setAttribute("subscriptionEndDate", endDate);
            request.setAttribute("packages", packages);

            boolean needsRenewal = remainingSeconds <= 0 || "Trial".equals(shopOwner.getSubscriptionStatus()) || "Expired".equals(shopOwner.getSubscriptionStatus());
            request.setAttribute("isRenewal", true);
            request.setAttribute("needsRenewal", needsRenewal);

            // Warning messages
            if (remainingSeconds <= 0) {
                if ("Trial".equals(shopOwner.getSubscriptionStatus())) {
                    request.setAttribute("warningMessage", "Gói dùng thử đã hết hạn. Vui lòng chọn gói dịch vụ để tiếp tục sử dụng.");
                } else {
                    request.setAttribute("warningMessage", "Dịch vụ đã hết hạn. Vui lòng gia hạn để tiếp tục sử dụng.");
                }
            } else if (remainingSeconds <= 3 * 86400) {
                if (remainingDays <= 0) {
                    request.setAttribute("warningMessage", "Dịch vụ hết hạn trong ngày hôm nay. Gia hạn ngay để tránh gián đoạn!");
                } else {
                    request.setAttribute("warningMessage", "Dịch vụ sắp hết hạn trong " + remainingDays + " ngày. Gia hạn ngay để được ưu đãi!");
                }
            }

            // Check payment status from URL parameters
            String paymentStatus = request.getParameter("status");
            if ("success".equals(paymentStatus)) {
                request.setAttribute("successMessage", "Thanh toán thành công! Dịch vụ đã được gia hạn.");
            } else if ("failed".equals(paymentStatus)) {
                request.setAttribute("errorMessage", "Thanh toán thất bại. Vui lòng thử lại.");
            } else if ("cancelled".equals(paymentStatus)) {
                request.setAttribute("infoMessage", "Bạn đã hủy thanh toán. Có thể thử lại bất cứ lúc nào.");
            }

            request.setAttribute("contextPath", request.getContextPath());
            request.getRequestDispatcher(SUBSCRIPTION).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Lỗi</title></head><body>");
                out.println("<h2>Đã xảy ra lỗi</h2>");
                out.println("<p>Chi tiết: " + e.getMessage() + "</p>");
                out.println("<p><a href='" + request.getContextPath() + "/view/jsp/Login.jsp'>Quay lại đăng nhập</a></p>");
                out.println("</body></html>");
            }
        }
    }

    /**
     * Escape JSON strings để tránh lỗi
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    /**
     * Enhance packages với logic thông minh hơn
     */
    private void enhancePackagesWithBadges(List<ServicePackage> packages) {
        if (packages == null || packages.isEmpty()) {
            return;
        }

        for (ServicePackage pkg : packages) {
            // Reset flags
            pkg.setRecommended(false);
            pkg.setPopular(false);

            // ✅ NULL CHECK cho safety
            if (pkg.getDurationMonths() == null) {
                continue;
            }

            // Logic thông minh hơn cho recommended
            if (pkg.getDurationMonths() == 6) {
                pkg.setPopular(true);      // 6 tháng = phổ biến
            } else if (pkg.getDurationMonths() == 12) {
                pkg.setRecommended(true);  // 1 năm = đề xuất
            }

            // Có thể thêm logic dựa trên discount
            if (pkg.getDiscountPercent() != null && pkg.getDiscountPercent() >= 30) {
                pkg.setRecommended(true);
            }
        }
    }

    private String formatRemainingTime(long remainingSeconds) {
        if (remainingSeconds <= 0) {
            return "Đã hết hạn";
        }

        long days = remainingSeconds / 86400;
        long hours = (remainingSeconds % 86400) / 3600;

        if (days > 0) {
            return days + " ngày " + hours + " giờ";
        } else if (hours > 0) {
            return hours + " giờ";
        } else {
            return "Dưới 1 giờ";
        }
    }

    private void logSubscriptionInfo(ShopOwner shopOwner) {
        System.out.println("=== Subscription Info ===");
        System.out.println("Shop: " + shopOwner.getShopName());
        System.out.println("Status: " + shopOwner.getSubscriptionStatus());
        System.out.println("End Date: " + shopOwner.getSubscriptionEndDate());
        System.out.println("Remaining Days: " + shopOwner.getRemainingDays());
        System.out.println("========================");
    }
}
