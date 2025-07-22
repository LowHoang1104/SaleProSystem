/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.Login;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import salepro.dal.DBContext2;
import salepro.dao.ShopOwnerDAO;
import salepro.models.SuperAdmin.ShopOwner;

/**
 *
 * @author ADMIN
 */
public class LoginOnwerShopController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginOnwerShopController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginOnwerShopController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/Mg2/view/jsp/Login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try (PrintWriter out = response.getWriter()) {
            String shopName = request.getParameter("nameshop");

            // Validate input
            if (shopName == null || shopName.trim().isEmpty()) {
                out.print("Vui lòng nhập tên shop");
                return;
            }

            shopName = shopName.trim();
            ShopOwnerDAO shopDAO = new ShopOwnerDAO();
            ShopOwner shopOwner = shopDAO.getShopOwnerByName(shopName);
            if (shopOwner == null) {
                out.print("Không thể lấy thông tin shop");
                return;
            }
            // Check if shop exists
            if (!shopDAO.checkExistShopOwner(shopName)) {
                out.print("Tên shop không tồn tại");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endDate = shopOwner.getSubscriptionEndDate();
            long remainingSeconds = (endDate != null) ? ChronoUnit.SECONDS.between(now, endDate) : -1;

            // Cập nhật subscriptionStatus dựa trên remainingSeconds
            if (endDate == null || remainingSeconds <= 0) {
                shopOwner.setSubscriptionStatus("Expired");
            } else if (remainingSeconds < 3 * 86400) {
                shopOwner.setSubscriptionStatus("Warning");
            } else {
                shopOwner.setSubscriptionStatus("Active");
            }

            if (shopDAO.updateSubscriptionStatus(shopOwner.getShopOwnerId(), shopOwner.getSubscriptionStatus())) {
                System.out.println("Cập nhật trạng thái subscription thành công.");
            } else {
                System.out.println("Cập nhật trạng thái subscription thất bại.");
            }

            // Set database context
            DBContext2.setCurrentDatabase(shopName);

            // Store shop owner in session
            session.setAttribute("ShopOwner", shopOwner);
            session.setAttribute("testValue", "Hello from login"); // Test value
            System.out.println("Session created with ID: " + session.getId());
            session.setAttribute("shopName", shopName);
            session.setAttribute("shopId", shopOwner.getShopOwnerId());

            switch (shopOwner.getSubscriptionStatus()) {
                case "Active":
                case "Trial":
                    out.print("OK");
                    break;

                case "Expired":
                case "Suspended":
                    out.print("Subscription");
                    break;
                case "Warning":
                    out.print("OK");
                    break;
                default:
                    out.print("Trạng thái subscription không hợp lệ");
                    break;
            }
        }
    }

}
