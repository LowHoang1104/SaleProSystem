/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.customers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "UpdateCustomerServlet", urlPatterns = {"/UpdateCustomerServlet"})
public class UpdateCustomerServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCustomerServlet at " + request.getContextPath() + "</h1>");
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

        String customerId = request.getParameter("customerId");
        CustomerDAO customerDAO = new CustomerDAO();
        if (customerId != null && !customerId.isBlank()) {
            Customers customer = customerDAO.getCustomerById(Integer.parseInt(customerId));
            request.setAttribute("customer", customer);
        }
        request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Update_customer.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số từ form
        String customerId = request.getParameter("customerId");
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String birthDatestr = request.getParameter("birthDate");
        String address = request.getParameter("address");
        String description = request.getParameter("description");

        String error = "";

// Kiểm tra dữ liệu đầu vào
        if (fullName == null || fullName.trim().isEmpty()) {
            error = "Vui lòng nhập họ và tên.";
        } else if (birthDatestr == null || birthDatestr.trim().isEmpty()) {
            error = "Vui lòng chọn ngày sinh.";
        } else if (phone == null || !phone.matches("^0\\d{9}$")) {
            error = "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số bắt đầu bằng 0.";
        } else if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            error = "Email không hợp lệ. Vui lòng nhập đúng định dạng email.";
        }
        Customers customerInput = new Customers();
// Nếu có lỗi, đẩy dữ liệu nhập lại và thông báo lỗi sang JSP
        if (!error.isEmpty()) {
            request.setAttribute("error", error);

            String birthDateStr = request.getParameter("birthDate"); // ví dụ: "1999-12-31"
            request.setAttribute("test", birthDateStr);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date birthDate = formatter.parse(birthDateStr);
                customerInput.setBirthDate(birthDate);
                // sử dụng birthDate tại đây
            } catch (Exception e) {
                e.printStackTrace(); // hoặc xử lý lỗi theo yêu cầu
            }

            // Đưa lại các dữ liệu nhập vào để hiển thị lại trong form
            customerInput.setCustomerId(Integer.parseInt(customerId));
            customerInput.setFullName(fullName);
            customerInput.setGender(gender);
            customerInput.setPhone(phone);
            customerInput.setEmail(email);
            customerInput.setAddress(address);
            customerInput.setDescription(description);

            request.setAttribute("customer", customerInput);
            request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Update_customer.jsp").forward(request, response);
            return;
        }

// Nếu dữ liệu hợp lệ, tiến hành cập nhật
        CustomerDAO customerDao = new CustomerDAO();
        boolean updated = customerDao.updateCustomer(Integer.parseInt(customerId), fullName, phone, email, gender, birthDatestr, 0, address, description);

        if (updated) {
            request.setAttribute("updateSuccess", true);
            request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Update_customer.jsp").forward(request, response);
        } else {
            Customers customer = customerDao.getCustomerById(Integer.parseInt(customerId));
            request.setAttribute("customer", customer);
            request.setAttribute("error", "Vui lòng thay đổi thông tin khác."); // Lỗi cập nhật
            request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Update_customer.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
