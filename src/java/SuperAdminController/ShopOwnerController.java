/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SuperAdminController;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.models.Purchases;
import salepro.models.up.ShopOwner;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ShopOwnerController", urlPatterns = {"/ShopOwnerController"})
public class ShopOwnerController extends HttpServlet {

    ArrayList<ShopOwner> data = new ArrayList<ShopOwner>();
    ShopOwnerDAO shopOwnerDAO = new ShopOwnerDAO();

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
            out.println("<title>Servlet ShopOwnerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShopOwnerController at " + request.getContextPath() + "</h1>");
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
        data = shopOwnerDAO.getData();
        request.setAttribute("data", data);

        request.getRequestDispatcher("view/jsp/superadmin/shop-owners.jsp").forward(request, response);
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
        String shop = "";
        String Owner = "";
        String status = "";
        String date = "";
        shop = request.getParameter("shop");
        Owner = request.getParameter("shopOwner");
        status = request.getParameter("status");
        date = request.getParameter("date");
        request.setAttribute("shop", shop);
        request.setAttribute("shopOwner", Owner);
        request.setAttribute("status", status);
        request.setAttribute("date", date);
        System.out.println(date);
        data = shopOwnerDAO.getDataBysearch(shop, Owner, status, date);
        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/superadmin/shop-owners.jsp").forward(request, response);
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
