/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.users;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.RoleDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;
import salepro.models.EmployeeTypes;
import salepro.models.Employees;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/UpdateUserServlet"})
public class UpdateUserServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserServlet at " + request.getContextPath() + "</h1>");
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
        EmployeeTypeDAO et = new EmployeeTypeDAO();
        List<EmployeeTypes> listEt = et.getData();

        StoreDAO storeDao = new StoreDAO();
        List<Stores> listStore = storeDao.getData();

        request.setAttribute("employeeTypes", listEt);
        request.setAttribute("stores", listStore);

        String userIdStr = request.getParameter("UserId");
        int userId = Integer.parseInt(userIdStr);
        //Lấy employee từ userId
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employees employee = employeeDAO.getEmployeeByUserId(userId);
        request.setAttribute("employee", employee);

        //Lấy User từ userId
        UserDAO uDao = new UserDAO();
        Users user = uDao.getUserById(userId);
        request.setAttribute("user", user);

        // Forward đến form add user (ví dụ: Update_user.jsp)
        request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
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

        EmployeeTypeDAO et = new EmployeeTypeDAO();
        List<EmployeeTypes> listEt = et.getData();

        StoreDAO storeDao = new StoreDAO();
        List<Stores> listStore = storeDao.getData();

        request.setAttribute("employeeTypes", listEt);
        request.setAttribute("stores", listStore);

        String userId = request.getParameter("UserId");
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String employeeTypeId = request.getParameter("employeeTypeId");
        String storeId = request.getParameter("storeId");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String avatar = request.getParameter("avatar");
        if (avatar != null && !avatar.isBlank()) {
            avatar = "view/assets/img/user/" + avatar;
        } else {

            avatar = request.getParameter("oldAvatar");
            System.out.println(avatar);
        }
        String error = "";
        // Xử lý dữ liệu
        int eTypeId = Integer.parseInt(employeeTypeId);
        int sId = Integer.parseInt(storeId);

        // Kiểm tra họ tên
        if (username == null || username.trim().isEmpty()) {
            error = "Vui lòng nhập tên đăng nhập";
        } else if (fullName == null || fullName.trim().isEmpty()) {
            error = "Vui lòng nhập họ và tên.";
        } // Kiểm tra số điện thoại
        else if (phone == null || !phone.matches("^0\\d{9}$")) {
            error = "Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số bắt đầu bằng 0.";
        }
        String passwordHash = "";

        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employees employee = employeeDAO.getEmployeeByUserId(Integer.parseInt(userId));

        //Đẩy user ra jsp
        UserDAO uDao = new UserDAO();
        Users user = uDao.getUserById(Integer.parseInt(userId));
        user.setUsername(username);
        user.setEmail(email);
        if (avatar != null && !avatar.isBlank()) {
            user.setAvatar(avatar);
        }
        request.setAttribute("user", user);

        //Đẩy empoyee ra jsp
        employee.setUserID(user.getUserId());
        employee.setFullName(fullName);
        employee.setEmployeeTypeID(eTypeId);
        employee.setStoreID(sId);
        employee.setPhone(phone);
        request.setAttribute("employee", employee);

        boolean checkPass = password != null && !password.isBlank();
        if (checkPass) {
            // Kiểm tra mật khẩu xác nhận
            if (!(password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*") && password.length() > 8)) {
                request.setAttribute("error", "Password phải có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số và có ít nhât 8 kí tự. Vui lòng nhập lại password");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
                return;
            }
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu không khớp. Vui lòng nhập lại mật khẩu.");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
                return;
            }
            // Mã hóa password 
            passwordHash = Base64.getEncoder()
                    .encodeToString(password.getBytes(StandardCharsets.UTF_8));
            user.setPasswordHash(passwordHash);
        }

        boolean checkEmail = uDao.checkEmail(email) && !email.equalsIgnoreCase(employee.getUser().getEmail());
        boolean checkUser = uDao.checkUserName(username) && !username.equalsIgnoreCase(employee.getUser().getUsername());
        if (checkEmail) {
            request.setAttribute("error", "email đã được đăng ký. Vui lòng nhập lại email.");
            request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
            return;
        }

        if (checkUser) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng nhập lại tên đăng nhập.");
            request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
            return;
        }

        if (!error.isBlank()) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
        } else {
            // Gọi DAO để lưu vào DB
            boolean success = (uDao.checkUpdateUser(checkUser, checkEmail, checkPass, user) && employeeDAO.updateEmployee(employee));
            if (success) {
                request.setAttribute("updateSuccess", true);
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response); // trang danh sách user
            } else {
                request.setAttribute("error", "Chỉnh sửa người dùng thất bại!");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Update_user.jsp").forward(request, response);
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
