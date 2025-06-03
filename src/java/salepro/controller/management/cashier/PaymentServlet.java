/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import salepro.dao.EmployeeDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.UserDAO;
import salepro.models.Users;



@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
//            Integer storeID = (Integer) session.getAttribute("storeId");
            Integer storeID = 1;

            String staffId = request.getParameter("staffId");
            String customerIdStr = request.getParameter("customerId");
            String totalAmountStr = request.getParameter("totalAmount");
            String discountStr = request.getParameter("discount");
            String paidAmountStr = request.getParameter("paidAmount");
            String paymentMethod = request.getParameter("paymentMethod");
            String action = request.getParameter("action");

            int paymentMethodId = mapPaymentMethodToId(paymentMethod);

            double totalAmount = 0, discount = 0, paidAmount = 0;
            int customerId = 1, employeeId = 1;
            if (staffId != null && !staffId.trim().isEmpty()) {
                employeeId = Integer.parseInt(staffId);
            }

            if (totalAmountStr != null && !totalAmountStr.trim().isEmpty()) {
                totalAmount = Double.parseDouble(totalAmountStr);
            }

            if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
                customerId = Integer.parseInt(customerIdStr);
            }

            if (discountStr != null && !discountStr.trim().isEmpty()) {
                discount = Double.parseDouble(discountStr);
            }
            if (paidAmountStr != null && !paidAmountStr.trim().isEmpty()) {
                paidAmount = Double.parseDouble(paidAmountStr);
            }

            if (storeID == null) {
                storeID = 1;
            }

            if ("checkout".equals(action)) {
                InvoiceDAO idao = new InvoiceDAO();
                boolean success = idao.insertInvoice(storeID, employeeId, customerId, totalAmount, paymentMethodId);

                if (success) {
                    session.removeAttribute("cart");
                    session.setAttribute("message", "Thanh toán thành công!");
                } else {
                    session.setAttribute("error", "Không thể tạo hóa đơn. Vui lòng thử lại.");
                }
            }
            
            
            response.sendRedirect("CashierServlet");

        } catch (Exception e) {
            e.printStackTrace(); // 
            request.setAttribute("error", "Lỗi xử lý thanh toán: " + e.getMessage());
            request.getRequestDispatcher("view/jsp/employees/Cashier.jsp").forward(request, response);

        }

    }

    private int mapPaymentMethodToId(String method) {
        if (method == null) {
            return 0;
        }
        switch (method.toLowerCase()) {
            case "cash":
                return 1;
            case "transfer":
                return 2;
            case "card":
                return 3;
            case "wallet":
                return 4;
            default:
                return 0;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
