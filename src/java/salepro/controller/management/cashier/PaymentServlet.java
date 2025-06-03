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
import salepro.models.Customers;
import salepro.models.Users;
import salepro.models.up.CartItem;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String CASHIER1 = "view/jsp/employees/newjsp.jsp";
    private static final String PAYMENT_AJAX = "view/jsp/employees/payment_ajax.jsp";

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

        String action = request.getParameter("action");

        if ("getPaymentInfo".equals(action)) {

            HttpSession session = request.getSession();

            double totalAmount = 0;
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem item : cart) {
                    totalAmount += item.getPrice() * item.getQuantity();
                }
            }
            Double discount = (Double) session.getAttribute("discount");
            if (discount == null) {
                discount = 0.0;
            }

            double payableAmount = totalAmount - (totalAmount * discount / 100);
            Customers customer = (Customers) session.getAttribute("customer");
            session.setAttribute("totalAmount", totalAmount);
            session.setAttribute("payableAmount", payableAmount);
            session.setAttribute("paidAmount", payableAmount);
            session.removeAttribute("changeAmount");
            session.setAttribute("customer", customer);

            request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");

            if ("checkout".equals(action)) {
                List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
                if (cart == null || cart.isEmpty()) {
                    session.setAttribute("error", "Giỏ hàng đang trống. Vui lòng thêm sản phẩm.");
                    response.sendRedirect("CashierServlet");
                    return;
                }
                for (CartItem item : cart) {
                    if (item.getColor() == null || item.getSize() == null) {
                        session.setAttribute("error", "Sản phẩm " + item.getProductName() + " thiếu thông tin size hoặc màu.");
                        response.sendRedirect("CashierServlet");
                        return;
                    }
                }
                int employeeId = (Integer) session.getAttribute("invoiceSaleId");
                Customers customer = (Customers) session.getAttribute("customer");
                int customerId = customer.getCustomerId();
                //            Integer storeID = (Integer) session.getAttribute("storeId");
                int storeID = 1;

                String paymentMethod = request.getParameter("paymentMethod");
                int paymentMethodId = mapPaymentMethodToId(paymentMethod);
                double payableAmount = (Double) session.getAttribute("payableAmount");
                InvoiceDAO idao = new InvoiceDAO();
                boolean success = idao.insertInvoice(storeID, employeeId, customerId, payableAmount, paymentMethodId);
                if (success) {
                    session.removeAttribute("cart");
                    session.removeAttribute("totalAmount");
                    session.removeAttribute("totalItems");
                    session.removeAttribute("changeAmount");
                    session.removeAttribute("discount");
                    session.removeAttribute("invoiceSaleId");
                    session.removeAttribute("customer");
                    response.sendRedirect("CashierServlet");
                    session.setAttribute("message", "Thanh toán thành công!");
                    return;
                } else {
                    session.setAttribute("error", "Không thể tạo hóa đơn. Vui lòng thử lại.");
                    return;
                }
            } else if ("updateInvoiceSaleId".equals(action)) {
                String staffId = request.getParameter("staffId");
                if (staffId != null && !staffId.trim().isEmpty()) {
                    int employeeIdAjax = Integer.parseInt(staffId);
                    session.setAttribute("invoiceSaleId", employeeIdAjax);
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } else if ("updateDiscount".equals(action)) {
                String discountStr = request.getParameter("discount");
                double discount = 0;
                if (discountStr != null && !discountStr.trim().isEmpty()) {
                    try {
                        discount = Double.parseDouble(discountStr);
                        if (discount < 0) {
                            discount = 0;
                        }
                        if (discount > 100) {
                            discount = 100;
                        }
                    } catch (NumberFormatException e) {
                        discount = 0;
                    }
                } else {
                    discount = 0;
                }

                double totalAmount = (Double) session.getAttribute("totalAmount");
                double payableAmount = totalAmount - (totalAmount * discount / 100);
                session.setAttribute("discount", discount);
                session.setAttribute("payableAmount", payableAmount);
                session.setAttribute("paidAmount", payableAmount);
                session.removeAttribute("changeAmount");
                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            } else if ("updatePaidAmount".equals(action)) {
                String paidAmountStr = request.getParameter("paidAmount");
                Double paidAmount = Double.parseDouble(paidAmountStr);
                Double payableAmount = (Double) session.getAttribute("payableAmount");
                if (paidAmount < 0 || paidAmount < payableAmount) {
                    paidAmount = payableAmount;
                } else if (paidAmount > payableAmount) {
                    double changeAmount = paidAmount - payableAmount;
                    session.setAttribute("paidAmount", paidAmount);
                    session.setAttribute("changeAmount", changeAmount);
                }
                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý thanh toán: " + e.getMessage());
            request.getRequestDispatcher(CASHIER1).forward(request, response);

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
