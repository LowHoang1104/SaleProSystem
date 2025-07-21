/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.Signup;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.UserDAO;
import salepro.service.GoogleAccount;
import salepro.service.GoogleService;
import salepro.models.up.ShopOwner;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "GoogleAuthController", urlPatterns = {"/GoogleAuthController"})
public class GoogleAuthController extends HttpServlet {

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
            out.println("<title>Servlet GoogleAuthController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GoogleAuthController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        UserDAO userDA = new UserDAO();
        String code = request.getParameter("code");
        String accessToken = GoogleService.getToken(code);
        GoogleAccount acc = GoogleService.getUserInfo(accessToken);
        session.setAttribute("googleAccount", acc);
        if (userDA.getUserByEmail(acc.getEmail()) == null) {
            request.getRequestDispatcher("view/jsp/FormShopNameGoogle.jsp").forward(request, response);
        } else {
            //delete session before enter Homepage admin
            session.removeAttribute("googleAccount");
            //email can la unique trong database
            session.setAttribute("user", userDA.getUserByEmail(acc.getEmail()));
            request.getRequestDispatcher("view/jsp/admin/Home_admin.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        GoogleAccount acc = (GoogleAccount) session.getAttribute("googleAccount");
        String shop = request.getParameter("shop");
        String password1 = request.getParameter("password");
        String password2 = request.getParameter("password2");
        ShopOwnerDAO ownerDA = new ShopOwnerDAO();
        String error = "";
        if (ownerDA.checkExistShopOwner(shop.trim())) {
            error = shop + " đã tồn tại";
        } else {
            if (!password1.equals(password2)) {
                error = "Mật khẩu không trùng khớp";
            } else if (!(password1.matches(".*[^a-zA-Z0-9].*") && password1.matches(".*[0-9].*") && password1.matches(".*[A-Z].*"))) {
                error = "Mật khẩu sai định dạng"
                        + "<br>mật khẩu ít nhất 1 kí tự đặc biệt, chữ hoa, số";
            }
        }
        if (error.isEmpty()) {
            ShopOwner newShop = new ShopOwner(shop, acc.getName(), acc.getEmail(), password1);
            
            ownerDA.createShopOwnerByEmail(newShop);
            response.sendRedirect("view/jsp/Homepage.jsp");
        } else {
            request.setAttribute("error", error);
            response.sendRedirect("view/jsp/FormShopNameGoogle.jsp");
        }

//        ownerDA.createShopOwner(newshop);
//        
//        try (PrintWriter out = response.getWriter()) {
//            out.print("<br>" + acc.getEmail());
//            out.print("<br>" + acc.getFamily_name());
//            out.print("<br>" + acc.getFirst_name());
//            out.print("<br>" + acc.getGiven_name());
//            out.print("<br>" + acc.getName());
//            out.print("<br>" + acc.getPicture());
//            out.print("<br>" + shop);
//        }
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
