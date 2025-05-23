package salepro.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import salepro.dal.DBContext1;
import salepro.dal.DBContext2;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author ADMIN
 */
public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
        String nameshop = request.getParameter("nameshop");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        ShopOwnerDAO da = new ShopOwnerDAO();
        HttpSession session = request.getSession();
        if (da.checkShopOwner(nameshop, account, password)) {
            DBContext2.setCurrentDatabase(nameshop);
            session.setAttribute("currentShop", DBContext2.getCurrentDatabase());
            response.sendRedirect("view/jsp/Login.jsp");

        } else {
            request.getRequestDispatcher("view/jsp/LoginShopOwner.jsp").forward(request, response);
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
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String login = request.getParameter("login");

        if (login.equals("1")) {
            ShopOwnerDAO shopOwnerda = new ShopOwnerDAO();
            if (shopOwnerda.checkShopOwner(session.getAttribute("currentShop").toString(), account, password)) {
                response.sendRedirect("view/jsp/Home_admin.jsp");
            }
        } else if (login.equals("2")) {
            UserDAO userda= new UserDAO();           
            if(userda.checkUser(account, password)){             
                response.sendRedirect("view/jsp/admin/newjsp.jsp");
            }else{
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        }else{           
            response.sendRedirect("view/jsp/LoginShopOwner.jsp");
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
