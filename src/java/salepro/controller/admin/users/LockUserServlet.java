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
import salepro.dao.UserDAO;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "LockUserServlet", urlPatterns = {"/LockUserServlet"})
public class LockUserServlet extends HttpServlet {

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
            out.println("<title>Servlet BlockUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlockUserServlet at " + request.getContextPath() + "</h1>");
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
        UserDAO daoUser = new UserDAO();
        String userId = request.getParameter("userId");
        String action = request.getParameter("action");

        int id = Integer.parseInt(userId);
        Users user = daoUser.getUserById(id);  // Lấy user cần block

        if (action.equalsIgnoreCase("lock")) {
            if (user != null && user.getRoleName().equalsIgnoreCase("Admin")) {
                // Không cho phép block Admin
                request.setAttribute("lockAdminNotAllowed", true);
                request.getRequestDispatcher("ListUserServlet").forward(request, response);
                return;
            }

            boolean success = daoUser.blockUser(id, true); // Cập nhật IsActive = 0 để block user

            // Gửi redirect kèm thông báo
            if (success) {
                request.setAttribute("lockSuccess", true);
            } else {
                request.setAttribute("lockFail", true);
            }
        } else {
            if (user != null && user.getRoleName().equalsIgnoreCase("Admin")) {
                // Không cho phép block Admin
                request.setAttribute("unlockAdminNotAllowed", true);
                request.getRequestDispatcher("ListUserServlet").forward(request, response);
                return;
            }

            boolean success = daoUser.blockUser(id, false); // Cập nhật IsActive = 0 để block user

            // Gửi redirect kèm thông báo
            if (success) {
                request.setAttribute("unlockSuccess", true);
            } else {
                request.setAttribute("unlockFail", true);
            }
        }
        request.getRequestDispatcher("ListUserServlet").forward(request, response);
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
        processRequest(request, response);
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
