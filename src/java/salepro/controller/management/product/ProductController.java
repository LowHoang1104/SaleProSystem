/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.product;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.CategoryDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.StockTakeDAO;
import salepro.dao.StoreDAO;
import salepro.dao.TypeDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.Categories;
import salepro.models.ProductMasters;
import salepro.models.ProductTypes;
import salepro.models.StockTake;
import salepro.models.Stores;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
public class ProductController extends HttpServlet {

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
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
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
        ProductMasterDAO pdao = new ProductMasterDAO();
        List<ProductMasters> pdata = pdao.getData();
        String mode = request.getParameter("mode");
        request.setAttribute("pdata", pdata);
        if (mode.equals("1")) {
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("cdata", cdata != null ? cdata : new ArrayList<>());
            request.setAttribute("tdata", tdata != null ? tdata : new ArrayList<>());
            request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/productlist.jsp").forward(request, response);
        } else if (mode.equals("2")) {
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            request.setAttribute("cdata", cdata != null ? cdata : new ArrayList<>());
            request.setAttribute("tdata", tdata != null ? tdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/addproduct.jsp").forward(request, response);
        } else if (mode.equals("3")) {
            HttpSession attribute = request.getSession();
            attribute.setAttribute("atb", "1");
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            request.setAttribute("cdata", cdata);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/attributelist.jsp").forward(request, response);
        } else if (mode.equals("4")) {
            StockTakeDAO stkdao = new StockTakeDAO();
            List<StockTake> stkdata = stkdao.getStockTake();
            List<Warehouse> wdata = new WarehouseDAO().getData();
            request.setAttribute("wdata", wdata);
//            System.out.println(stkdata.size());
            request.setAttribute("stkdata", stkdata);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktake.jsp").forward(request, response);
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
