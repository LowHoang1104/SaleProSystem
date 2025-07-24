/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SuperAdminController;

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
import salepro.dao.SuperAdmin.ServicePackageDAO;
import salepro.models.SuperAdmin.ServicePackage;
import salepro.models.SuperAdmin.ShopOwner;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "SubscriptionController", urlPatterns = {"/SubscriptionController"})
public class SubscriptionController extends HttpServlet {

    public static final String SUBSCRIPTION = "/view/jsp/SubscriptionExpired/subscription-renewal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    private void enhancePackagesWithBadges(List<ServicePackage> packages) {
        if (packages == null) {
            return;
        }

        for (ServicePackage pkg : packages) {
            if (pkg.getDurationMonths() == 3) {
                pkg.setRecommended(true);  
            } else if (pkg.getDurationMonths() == 12) {
                pkg.setPopular(true);      
            }

        }
    }
}
