
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.StoreDAO;
import salepro.dao.SupplierDAO;
import salepro.dao.UserDAO;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "HomepageController", urlPatterns = {"/HomepageController"})
public class HomepageController extends HttpServlet {

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
            out.println("</head>");
            out.println("<body>");
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
        CustomerDAO customerDA = new CustomerDAO();
        SupplierDAO supplierDA = new SupplierDAO();
        PurchaseDAO purchaseDA = new PurchaseDAO();
        InvoiceDAO invoiceDA = new InvoiceDAO();
        StoreDAO storeDA = new StoreDAO();
        HttpSession session = request.getSession();
        ArrayList<Stores> stores = new ArrayList<>();
        stores = (ArrayList<Stores>) session.getAttribute("storecurrent");
        Users user = (Users) session.getAttribute("user");
        ProductMasterDAO productmasterDA = new ProductMasterDAO();
        String op = request.getParameter("op");
        if (op != null) {
            if (op.equals("0")) {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProducts());

            } else if (op.equals("1")) {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProductsLast7Days());

            } else if (op.equals("2")) {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProductsLast1Month());

            } else if (op.equals("3")) {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProductsLast3Months());

            } else if (op.equals("4")) {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProductsLast6Months());
            } else {
                request.setAttribute("products", productmasterDA.GetTop10BestSellingProductsLast12Months());
            }
        } else {
            op = "0";
            request.setAttribute("products", productmasterDA.GetTop10BestSellingProducts());
        }
        request.setAttribute("op", op);
        request.setAttribute("customerNum", customerDA.getData().size());
        request.setAttribute("supplierNum", supplierDA.getData().size());
        int purchaseNum;
        int invoiceNum;
        if (user.getRoleId() == 1) {
            invoiceNum = invoiceDA.getData().size();
            purchaseNum = purchaseDA.getData().size();
        } else {
            invoiceNum = invoiceDA.getInvoicesByStoreId(stores.get(0).getStoreID()).size();
            purchaseNum = purchaseDA.getPurchaseByStoreID(stores.get(0).getStoreID()).size();
        }
        request.setAttribute("purchaseNum", purchaseNum);
        request.setAttribute("invoiceNum", invoiceNum);
        request.getRequestDispatcher("view/jsp/admin/Home_admin.jsp").forward(request, response);

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
