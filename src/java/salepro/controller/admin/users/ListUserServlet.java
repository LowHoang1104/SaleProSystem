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
//        HttpSession session = request.getSession();
//        session.setAttribute("user", userDAO.getUserById(1));
//        if (session == null || session.getAttribute("user") == null) {
//            response.sendRedirect("view/jsp/Login.jsp");
//            return;
//        }
//
//        Users loggedUser = (Users) session.getAttribute("user");
//        if (loggedUser.getRoleId() != 1) {
//            response.sendRedirect("accessDenied.jsp");
//            return;
//        }
//        Users u = (Users) session.getAttribute("user");


        //Lấy danh sách user
        List<Users> users;
        //Lọc theo keyword
        String keyword = request.getParameter("keyword");
        request.setAttribute("keyword", keyword);
        if (keyword != null && !keyword.isEmpty()) {
            String key = keyword.replaceAll("\\s+", " ").trim();
            users = userDAO.searchUserByKeyword(key);
        } else {
            //Lọc theo name, email, active
            String userName = request.getParameter("userName");
            String email = request.getParameter("email");
            String isActive = request.getParameter("isActive");
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.setAttribute("isActive", isActive);
            if ((userName != null && !userName.isBlank()) || (email != null && !email.isBlank()) || (isActive != null && !isActive.isBlank())) {
                users = userDAO.filterUsers(userName, email, isActive);
            } else {
                users = userDAO.getData();

            }
        }
        //Phân trang
        int totalRecords = users.size();

        int recordsPerPage = 10;
        int currentPage = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isBlank()) {
            currentPage = Integer.parseInt(pageStr);
        }

        int startIndex = (currentPage - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);

        // Lấy sublist từ list gốc
        users = users.subList(startIndex, endIndex);
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        request.setAttribute("currentPage", pageStr);
        request.setAttribute("totalPages", totalPages);

        String addUser = request.getParameter("addUser");
        if (addUser != null) {
            request.setAttribute("addUser", addUser.equalsIgnoreCase("true"));
        }

        // Gửi listUser sang JSP
        request.setAttribute("listUser", users);

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
