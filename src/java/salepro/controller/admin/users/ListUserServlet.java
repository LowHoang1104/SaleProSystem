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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import salepro.dao.UserDAO;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListUserServlet", urlPatterns = {"/ListUserServlet"})
public class ListUserServlet extends HttpServlet {

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
            out.println("<title>Servlet ListUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListUserServlet at " + request.getContextPath() + "</h1>");
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
        UserDAO userDAO = new UserDAO();        
        HttpSession session = request.getSession();
//        session.setAttribute("user", userDAO.getUserById(1));
Users u = (Users) session.getAttribute("user");
        System.out.println(userDAO.getUserById(1).getRoleId());
        String message = request.getParameter("message");
        if (message != null) {
            if (message.equals("delete_success")) {
                request.setAttribute("alert", "Xóa người dùng thành công!");
                request.setAttribute("alertType", "success");
            } else if (message.equals("delete_failed")) {
                request.setAttribute("alert", "Xóa người dùng thất bại!");
                request.setAttribute("alertType", "danger");
            }
        }


        List<Users> listUser = userDAO.getData();

        String addUser = request.getParameter("addUser");
        if (addUser != null) {
            request.setAttribute("addUser", addUser.equalsIgnoreCase("true"));
        }

        // Gửi listUser sang JSP
        request.setAttribute("listUser", listUser);

        // Forward đến form add user (ví dụ: List_user.jsp)
        request.getRequestDispatcher("view/jsp/admin/UserManagement/List_user.jsp").forward(request, response);
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
        UserDAO dao = new UserDAO();
        String keyword = request.getParameter("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            String key = keyword.replaceAll("\\s+", " ").trim();
            List<Users> searchList = dao.searchUserByKeyword(key);
            request.setAttribute("keyword", keyword);
            request.setAttribute("listUser", searchList);
            request.getRequestDispatcher("view/jsp/admin/UserManagement/List_user.jsp").forward(request, response);
            return;
        }
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String isActive = request.getParameter("isActive");

        List<Users> filteredList = dao.filterUsers(userName, email, isActive);
        request.setAttribute("listUser", filteredList);
        request.getRequestDispatcher("view/jsp/admin/UserManagement/List_user.jsp").forward(request, response);
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
