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
import salepro.dao.CustomerDAO;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "AddCustomerServlet", urlPatterns = {"/AddCustomerServlet"})
public class AddCustomerServlet extends HttpServlet {

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
            out.println("<title>Servlet AddCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCustomerServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("view/jsp/admin/Add_customer.jsp").forward(request, response);
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

        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String birthDate = request.getParameter("birthDate");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        String error = "";

        // Kiểm tra họ tên
        if (fullName == null || fullName.trim().isEmpty()) {
            error = "Vui lòng nhập họ và tên.";
        } // Kiểm tra ngày sinh
        else if (birthDate == null || birthDate.trim().isEmpty()) {
            error = "Vui lòng chọn ngày sinh.";
        } // Kiểm tra số điện thoại
        else if (phone == null || !phone.matches("^0\\d{9}$")) {
            error = "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số bắt đầu bằng 0.";
        } // Kiểm tra email
        else if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            error = "Email không hợp lệ. Vui lòng nhập đúng định dạng email.";
        }

        CustomerDAO customerDao = new CustomerDAO();
        boolean success = customerDao.insertCustomer(fullName, phone, email, gender, birthDate);

        // Chuyển hướng hoặc hiển thị kết quả
        if (!success || !error.isBlank()) {
            // Giữ lại dữ liệu người dùng đã nhập
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("gender", gender);
            request.setAttribute("birthDate", birthDate);
            request.setAttribute("address", address);
            request.setAttribute("description", description);
            request.setAttribute("error", error);
            request.getRequestDispatcher("view/jsp/admin/Add_customer.jsp").forward(request, response);
        } else {

            response.sendRedirect("ListCustomerServlet"); // danh sách khách hàng

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
