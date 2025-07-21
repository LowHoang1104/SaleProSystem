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
@WebServlet(name = "SaveCustomerServlet", urlPatterns = {"/SaveCustomerServlet"})
public class SaveCustomerServlet extends HttpServlet {

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
        String customerId = request.getParameter("customerId");
        CustomerDAO customerDAO = new CustomerDAO();
        if (customerId != null && !customerId.isBlank()) {
            Customers customer = customerDAO.getCustomerById(Integer.parseInt(customerId));
            request.setAttribute("cusId", customer.getCustomerId());
            request.setAttribute("cusFullName", customer.getFullName());
            request.setAttribute("cusPhone", customer.getPhone());
            request.setAttribute("cusEmail", customer.getEmail());
            request.setAttribute("cusGender", customer.getGender());
            request.setAttribute("cusBirthDate", customer.getBirthDate());
            request.setAttribute("cusAddress", customer.getAddress());
            request.setAttribute("cusDescription", customer.getDescription());
        }
        request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Add_customer.jsp").forward(request, response);
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
        CustomerDAO customerDao = new CustomerDAO();

        String cusIdStr = request.getParameter("customerId").trim();
        String fullName = request.getParameter("fullName").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String gender = request.getParameter("gender").trim();
        String birthDate = request.getParameter("birthDate").trim();
        String address = request.getParameter("address").trim();
        String description = request.getParameter("description").trim();
        String error = "";
        boolean checkEmailExists = customerDao.checkEmailExists(email);
        boolean checkPhoneExists = customerDao.checkPhoneExists(phone);

        // Kiểm tra họ tên
        if (fullName == null || fullName.trim().isEmpty()) {
            error = "Vui lòng nhập họ và tên.";
        } // Kiểm tra ngày sinh
        else if (birthDate == null || birthDate.trim().isEmpty()) {
            error = "Vui lòng chọn ngày sinh.";
        } else if (gender == null || gender.isBlank()) {
            error = "Vui lòng chọn giới tính";
        }// Kiểm tra số điện thoại
        else if (phone == null || !phone.matches("^0\\d{9}$")) {
            error = "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số bắt đầu bằng 0.";
        } // Kiểm tra email
        else if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            error = "Email không hợp lệ. Vui lòng nhập đúng định dạng email.";
        }
        if (cusIdStr != null && !cusIdStr.isBlank()) {
            int cusId = Integer.parseInt(cusIdStr);
            Customers cus = customerDao.getCustomerById(cusId);
            if (checkEmailExists && !cus.getEmail().equals(email)) {
                error = "Email đã tồn tại. Vui lòng nhập lại email";
            }
            if (checkPhoneExists && !cus.getPhone().equals(phone)) {
                error = "Số điện thoại đã tồn tại. Vui lòng nhập lại số điện thoại";

            }
            if (!error.isBlank()) {
                request.setAttribute("error", error);

                // Đưa lại các dữ liệu nhập vào để hiển thị lại trong form
                request.setAttribute("cusId", cusIdStr);
                request.setAttribute("cusFullName", fullName);
                request.setAttribute("cusPhone", phone);
                request.setAttribute("cusEmail", email);
                request.setAttribute("cusGender", gender);
                request.setAttribute("cusBirthDate", birthDate);
                request.setAttribute("cusAddress", address);
                request.setAttribute("cusDescription", description);
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Add_customer.jsp").forward(request, response);
                return;
            }
            // Nếu dữ liệu hợp lệ, tiến hành cập nhật
            boolean updated = customerDao.updateCustomer(cusId, fullName, phone, email, gender, birthDate, 0, address, description);

            if (updated) {
                request.setAttribute("updateSuccess", true);
                request.getRequestDispatcher("ListCustomerServlet").forward(request, response);
                return;
            } else {
                request.setAttribute("error", "Vui lòng thay đổi thông tin khác."); // Lỗi cập nhật
                request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Add_customer.jsp").forward(request, response);
            }
        } else {
            if (checkEmailExists) {
                error = "Email đã tồn tại. Vui lòng nhập lại email";
            }
            if (checkPhoneExists) {
                error = "Số điện thoại đã tồn tại. Vui lòng nhập lại số điện thoại";
            }
            if (!error.isBlank()) {
                request.setAttribute("cusFullName", fullName);
                request.setAttribute("cusPhone", phone);
                request.setAttribute("cusEmail", email);
                request.setAttribute("cusGender", gender);
                request.setAttribute("cusBirthDate", birthDate);
                request.setAttribute("cusAddress", address);
                request.setAttribute("cusDescription", description);
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/jsp/admin/CustomerManagement/Add_customer.jsp").forward(request, response);
                return;
            }
            //Add customer
            boolean addSuccess = customerDao.insertCustomer(fullName, phone, email, gender, birthDate);
            if (addSuccess) {
                request.setAttribute("addSuccess", addSuccess);
                request.getRequestDispatcher("ListCustomerServlet").forward(request, response);
            }
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
