/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;
import salepro.models.up.InvoiceItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CustomerSearchServlet", urlPatterns = {"/CustomerSearchServlet"})
public class CustomerSearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CustomerSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerSearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String phone = request.getParameter("phone");
        phone = phone.replaceAll("\\s", "");
        if (phone == null || phone.trim().isEmpty()) {
            //400
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Phone number is required\"}");
            return;
        }

        if (!isValidPhone(phone.trim())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid phone number format\"}");
            return;
        }

        try {
            CustomerDAO cDao = new CustomerDAO();
            Customers customer = cDao.findByPhone(phone.trim());

            if (customer == null) {
                // 404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Phone number not exist\"}");
                return;
            }

            Gson gson = new Gson();
            String json = gson.toJson(customer);
            response.getWriter().write(json);

        } catch (Exception e) {
            //500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Internal server error\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        try {
            if ("clearSelectedCustomer".equals(action)) {
                // Xóa khách hàng khỏi hóa đơn trong session
                InvoiceItem currentInvoice = (InvoiceItem) session.getAttribute("currentInvoice");
                if (currentInvoice != null) {
                    currentInvoice.setCustomer(null);
                    session.setAttribute("currentInvoice", currentInvoice);
                }

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"success\": true, \"message\": \"Khách hàng đã được xóa khỏi hóa đơn.\"}");

            } else if ("saveSelectedCustomer".equals(action)) {

                String customerIdStr = request.getParameter("customerId");

                if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"success\": false, \"message\": \"Customer ID is required.\"}");
                    return;
                }

                try {
                    int customerId = Integer.parseInt(customerIdStr.trim());

                    CustomerDAO cDao = new CustomerDAO();
                    Customers customer = cDao.findById(customerId);

                    if (customer != null) {

                        InvoiceItem currentInvoice = (InvoiceItem) session.getAttribute("currentInvoice");

                        currentInvoice.setCustomer(customer);
                        session.setAttribute("currentInvoice", currentInvoice);

                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("{\"success\": true, \"message\": \"Khách hàng đã được lưu vào session.\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"success\": false, \"message\": \"Customer not found.\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"success\": false, \"message\": \"Invalid customer ID format.\"}");
                }
            } else if ("displayCustomer".equals(action)) {
                InvoiceItem currentInvoice = (InvoiceItem) session.getAttribute("currentInvoice");

                Customers customer = null;
                if (currentInvoice != null && currentInvoice.getCustomer() != null) {
                    customer = currentInvoice.getCustomer();
                }

                Gson gson = new Gson();
                String customerJson = gson.toJson(customer);
                response.getWriter().write(customerJson);
            } else {
                // Action lỏ
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Invalid action.\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Internal server error.\"}");
            e.printStackTrace();
        }
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^(03|05|07|08|09|01[2|6|8|9])[0-9]{8}$"; 
        return phone.matches(phonePattern);
    }

}
