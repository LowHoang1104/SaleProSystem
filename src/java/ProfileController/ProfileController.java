/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ProfileController;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import salepro.dao.EmployeeDAO;
import salepro.dao.UserDAO;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/ProfileController"})
public class ProfileController extends HttpServlet {

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
            out.println("<title>Servlet ProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileController at " + request.getContextPath() + "</h1>");
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
         String mode = request.getParameter("mode");
        if (mode != null && mode.equals("profile")) {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");
            request.getRequestDispatcher("view/jsp/admin/Profile.jsp").forward(request, response);
        } else if (mode != null && mode.equals("logout")) {

        }
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
        HttpSession session = request.getSession();
        String op = request.getParameter("op");
        String error = "";
        EmployeeDAO employeeDA = new EmployeeDAO();
        UserDAO userDA = new UserDAO();
        Users user = (Users) session.getAttribute("user");

        if (op!=null &&op.equals("save")) {
            if (user.getRoleId() != 1) {
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String name = request.getParameter("name");

                if (!email.trim().equals(user.getEmail())) {
                    if (userDA.checkEmail(email)) {
                        error += "Email đã tồn tại<br>";
                    } else {
                        userDA.updateEmail(email, user.getUserId());
                    }
                } else if (!phone.trim().equals(user.getPhoneEmployee())) {
                    if (employeeDA.checkPhonenumber(phone)) {
                        error += "Phone đã tồn tại<br>";
                    } else {
                        employeeDA.updatePhone(phone, user.getUserId());
                    }
                } else if (!name.trim().equals(user.getFullName())) {
                    if (name.isBlank()) {
                        error += "Tên không được để trống<br>";
                    } else {
                        employeeDA.updateName(name, user.getUserId());
                    }
                }
                if (!error.isEmpty()) {
                    request.setAttribute("error", error);
                }
                //luu lai thong tin vao session
                session.setAttribute("user", userDA.getUserById(user.getUserId()));
            }
            request.getRequestDispatcher("view/jsp/admin/Profile.jsp").forward(request, response);
        } else if (op!=null &&op.equals("resetpassword")) {
            
            String currentPassword = request.getParameter("currentPassword");
            //        String encoded = Base64.getEncoder().encodeToString(currentPassword.getBytes());
            //        currentPassword=encoded;
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (!newPassword.equals(confirmPassword)) {
                error += "Mật khẩu mới không trùng khớp";
            } else if (!userDA.checkUser(user.getUsername(), currentPassword)) {
                error += "Mật khẩu hiện tại không đúng";
            } else if (!(newPassword.matches(".*[A-Z].*") && newPassword.matches(".*[0-9].*") && newPassword.matches(".*[^a-zA-Z0-9].*"))) {
                error = "Sai format password"
                        + "<br>có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số";
            }

            if (!error.isEmpty()) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("view/jsp/admin/Changepassword.jsp").forward(request, response);
            } else {
                userDA.updatePasswordbyId(newPassword,user.getUserId());
               
            }
        } else if (op!=null &&op.equals("logout")) {
            session.removeAttribute("user");
                    
            
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
