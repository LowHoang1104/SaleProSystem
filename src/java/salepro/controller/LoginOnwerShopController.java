/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package salepro.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.ProductDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author ADMIN
 */
public class LoginOnwerShopController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet LoginOnwerShopController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginOnwerShopController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        CustomerDAO customerDA=new CustomerDAO();
        StoreDAO storeDA=new StoreDAO();
        PurchaseDAO purchaseDA=new PurchaseDAO();
        InvoiceDAO invoiceDA=new InvoiceDAO();
        ProductDAO productDA= new ProductDAO();
         //mã hóa 
//        byte[] decodedBytes = Base64.getDecoder().decode(password);
//        password = new String(decodedBytes);
        if (login.equals("1")) {
            UserDAO userda = new UserDAO();
            if (userda.checkManager(account, password)) {               
                request.setAttribute("customerNum", customerDA.getData().size());
                request.setAttribute("storeNum", storeDA.getData().size());
                request.setAttribute("purchaseNum", purchaseDA.getData().size());
                request.setAttribute("invoiceNum", invoiceDA.getData().size());
                request.setAttribute("invoiceNum", invoiceDA.getData().size());
                request.setAttribute("products", productDA.getTopTenBestProduct());
                session.setAttribute("user",userda.getUserbyAccountAndPass(account,password));
                request.getRequestDispatcher("view/jsp/admin/Home_admin.jsp").forward(request, response);
            }else{
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        } else if (login.equals("2")) {
            UserDAO userda= new UserDAO();           
            if(userda.checkCashier(account, password)){             
                response.sendRedirect("view/jsp/Cashier.jsp");
            }else{
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect("view/jsp/LoginShopOwner.jsp");
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
