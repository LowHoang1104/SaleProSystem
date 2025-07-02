/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package salepro.controller.management.store;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.PurchaseDAO;
import salepro.dao.StoreDAO;
import salepro.dao.SupplierDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.Purchases;
import salepro.models.Stores;
import salepro.models.Suppliers;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
@WebServlet(name="LogisticsController", urlPatterns={"/logisticscontroller"})
public class LogisticsController extends HttpServlet {
   
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
            out.println("<title>Servlet LogisticsController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LogisticsController at " + request.getContextPath () + "</h1>");
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
        String mode = request.getParameter("mode");
        StoreDAO stdao = new StoreDAO();
        List<Stores> stdata = stdao.getData();
        WarehouseDAO wdao = new WarehouseDAO();
        List<Warehouse> wdata = wdao.getData();
        PurchaseDAO pcdao = new PurchaseDAO();
        List<Purchases> pcdata = pcdao.getData();
        SupplierDAO spdao = new SupplierDAO();
        List<Suppliers> spdata= spdao.getData();
        if(mode.equals("1")){
            request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/storelist.jsp").forward(request, response);
        }
        if(mode.equals("2")){
            request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
            request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehouselist.jsp").forward(request, response);
        }
        if(mode.equals("3")){//chưa xử lý
            request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
            request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehouselist.jsp").forward(request, response);
        }
        if(mode.equals("4")){//done
            request.setAttribute("spdata", spdata != null ? spdata : new ArrayList<>());
            request.setAttribute("pcdata", pcdata != null ? pcdata : new ArrayList<>());
            request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchaselist.jsp").forward(request, response);
        }
        if(mode.equals("5")){
            request.setAttribute("spdata", spdata != null ? spdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/supplierlist.jsp").forward(request, response);
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
        processRequest(request, response);
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
