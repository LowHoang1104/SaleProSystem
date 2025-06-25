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
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;
import salepro.models.EmployeeTypes;
import salepro.models.Employees;
import salepro.models.Stores;
import salepro.models.Users;
import salepro.service.ResetPassword;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SaveUserServlet", urlPatterns = {"/SaveUserServlet"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 1024 * 1024 * 10,    // 10MB
    maxRequestSize = 1024 * 1024 * 50  // 50MB
)
public class SaveUserServlet extends HttpServlet {

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
            out.println("<title>Servlet SaveUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveUserServlet at " + request.getContextPath() + "</h1>");
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
        UserDAO uDao = new UserDAO();

        System.out.println(uDao.getUserById(1).getRoleId());
        EmployeeDAO employeeDAO = new EmployeeDAO();

        EmployeeTypeDAO et = new EmployeeTypeDAO();
        List<EmployeeTypes> listEt = et.getData();

        StoreDAO storeDao = new StoreDAO();
        List<Stores> listStore = storeDao.getData();

        request.setAttribute("employeeTypes", listEt);
        request.setAttribute("stores", listStore);

        String userIdStr = request.getParameter("UserId");
        if (userIdStr != null && !userIdStr.isBlank()) {
            int userId = Integer.parseInt(userIdStr);
            //Lấy employee từ userId

            Employees employee = employeeDAO.getEmployeeByUserId(userId);
            request.setAttribute("empFullName", employee.getFullName());
            request.setAttribute("empPhone", employee.getPhone());
            request.setAttribute("empStoreId", employee.getStoreID());
            request.setAttribute("empTypeId", employee.getEmployeeTypeID());
            request.setAttribute("empPhone", employee.getPhone());

            //Lấy User từ userId
            Users user = uDao.getUserById(userId);
            request.setAttribute("empId", user.getUserId());
            request.setAttribute("empUserName", user.getUsername());
            request.setAttribute("empEmail", user.getEmail());
            request.setAttribute("empAvatar", user.getAvatar());
        }
        // Forward đến form add user (ví dụ: Add_user.jsp)
        request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
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
        //Các DAO 
        EmployeeDAO empDAO = new EmployeeDAO();
        UserDAO uDao = new UserDAO();

        EmployeeTypeDAO et = new EmployeeTypeDAO();
        List<EmployeeTypes> listEt = et.getData();

        StoreDAO storeDao = new StoreDAO();
        List<Stores> listStore = storeDao.getData();

        request.setAttribute("employeeTypes", listEt);
        request.setAttribute("stores", listStore);

        String userIdStr = request.getParameter("UserId");
        String username = request.getParameter("username");
        boolean checkUser = uDao.checkUserName(username);
        String fullName = request.getParameter("fullName");
        String employeeTypeId = request.getParameter("employeeTypeId");
        String storeId = request.getParameter("storeId");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        boolean checkEmail = uDao.checkEmail(email);
        String avatar = null;
        String error = "";

        //Kiểm tra file upload 
        Part filePart = request.getPart("avatar");
        if (filePart != null && filePart.getSize() > 0) {
            //Lấy tên file
            String fileName = filePart.getSubmittedFileName();
            //Kiểm tra định dạng file
            if (fileName != null && !fileName.isBlank()) {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                if (!fileExtension.equals(".jpg") && !fileExtension.equals(".png") && !fileExtension.equals(".jpeg")) {
                    error = "Định dạng không hợp lệ. Chỉ chấp nhận .png, .jpg, .jpeg. Vui lòng chọn lại ảnh!";
                } else {
                    //Kiểm tra kích thước file phải <= 5MB
                    long maxSize = 5 * 1024 * 1024;
                    if (filePart.getSize() > maxSize) {
                        error = "Kích thước ảnh vượt quá 5 MB. Vui lòng chọn ảnh nhỏ hơn.";
                    } else {
                        // Lưu file vào thư mục (nếu hợp lệ)
                        String uploadPath = getServletContext().getRealPath("").split("build")[0] + "web\\view\\assets\\img\\user";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        String unique = (userIdStr != null) ? userIdStr : username;
                        if(unique == null || unique.isBlank()){
                            unique = UUID.randomUUID().toString();
                        }
                        fileName = "avt" + unique + fileExtension;
                        String filePath = uploadPath + File.separator + fileName;
                        filePart.write(filePath);
                        avatar = fileName;
                    }
                }
            }
        } else {
            avatar = request.getParameter("oldAvatar");
        }
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

        //Update
        if (userIdStr != null && !userIdStr.isBlank()) {
            int userId = Integer.parseInt(userIdStr);
            //Lấy user
            Users user = uDao.getUserById(userId);
            user.setUsername(username);
            user.setEmail(email);
            if (avatar != null && !avatar.isBlank()) {
                user.setAvatar(avatar);
            }
            //Đẩy dữ liệu ra jsp trong trường hợp update lỗi 
            request.setAttribute("empId", user.getUserId());
            request.setAttribute("empUserName", user.getUsername());
            request.setAttribute("empAvatar", user.getAvatar());
            request.setAttribute("empEmail", user.getEmail());

            //Lấy employee
            Employees employee = empDAO.getEmployeeByUserId(userId);
            employee.setUserID(user.getUserId());
            employee.setFullName(fullName);
            employee.setEmployeeTypeID(eTypeId);
            employee.setStoreID(sId);
            employee.setPhone(phone);
            request.setAttribute("empFullName", employee.getFullName());
            request.setAttribute("empPhone", employee.getPhone());
            request.setAttribute("empStoreId", employee.getStoreID());
            request.setAttribute("empTypeId", employee.getEmployeeTypeID());
            request.setAttribute("empPhone", employee.getPhone());

            checkEmail = checkEmail && !email.equalsIgnoreCase(employee.getUser().getEmail());
            checkUser = checkUser && !username.equalsIgnoreCase(employee.getUser().getUsername());
            if (checkEmail) {
                request.setAttribute("error", "email đã được đăng ký. Vui lòng nhập lại email.");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                return;
            }
            if (checkUser) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng nhập lại tên đăng nhập.");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                return;
            }
            if (!error.isBlank()) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
            } else {
                // Gọi DAO để lưu vào DB
                boolean success = (uDao.checkUpdateUser(checkUser, checkEmail, true, user) && empDAO.updateEmployee(employee));
                if (success) {
                    request.setAttribute("updateSuccess", true);
                    request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response); // trang danh sách user
                } else {
                    request.setAttribute("error", "Chỉnh sửa người dùng thất bại!");
                    request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                }
            }

            //Add
        } else {
            String password = generateRandomPassword(8);

            String passwordHash = Base64.getEncoder()
                    .encodeToString(password.getBytes(StandardCharsets.UTF_8));
            // Tạo đối tượng User
            Users user = new Users();
            user.setUsername(username);
            user.setPasswordHash(passwordHash);
            user.setRoleId(2);
            user.setEmail(email);
            user.setAvatar((avatar != null && !avatar.isBlank()) ? avatar : "profile.jpg");
            request.setAttribute("empUserName", user.getUsername());
            request.setAttribute("empAvatar", user.getAvatar());
            request.setAttribute("empEmail", user.getEmail());
            System.out.println(user.getUsername());
            // Tạo đối tượng Employee
            EmployeeDAO eDao = new EmployeeDAO();
            Employees employee = new Employees();
            employee.setFullName(fullName);
            employee.setEmployeeTypeID(eTypeId);
            employee.setStoreID(sId);
            employee.setPhone(phone);
            request.setAttribute("empFullName", employee.getFullName());
            request.setAttribute("empPhone", employee.getPhone());
            request.setAttribute("empStoreId", employee.getStoreID());
            request.setAttribute("empTypeId", employee.getEmployeeTypeID());
            request.setAttribute("empPhone", employee.getPhone());

            // Kiểm tra tên đăng nhập đã tồn tại chưa
            if (checkUser) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng nhập lại tên đăng nhập.");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                return;
            }

            // Kiểm tra email đã tồn tại chưa
            if (checkEmail) {
                request.setAttribute("error", "Email đã tồn tại. Vui lòng nhập lại email.");
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                return;
            }

            if (!error.isBlank()) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
            } else {
                // Thêm tài khoản cho nhân viên dựa vào userId
                int insertUserId = uDao.insertUser(user);
                employee.setUserID(insertUserId);
                boolean success = (insertUserId > 0 && eDao.insertEmployee(employee));
                if (success) {
                    ResetPassword a = new ResetPassword();
//                    a.sendPassword(user.getEmail(), user.getUsername(), password, employee.getFullName());
                    response.sendRedirect("ListUserServlet?addUser=" + success);
                } else {
                    request.setAttribute("error", "Tạo user mới thất bại!");
                    request.getRequestDispatcher("view/jsp/admin/UserManagement/Add_user.jsp").forward(request, response);
                }
            }
        }

    }

    public String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
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
