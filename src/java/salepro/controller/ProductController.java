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
import java.util.List;
import salepro.dao.CategoryDAO;
import salepro.dao.ColorDAO;
import salepro.dao.ProductDAO;
import salepro.dao.SizeDAO;
import salepro.dao.StoreDAO;
import salepro.dao.TypeDAO;
import salepro.models.Categories;
import salepro.models.Colors;
import salepro.models.Products;
import salepro.models.Sizes;
import salepro.models.Stores;
import salepro.models.ProductTypes;

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
        String id =request.getParameter("id");
        ProductDAO pdao = new ProductDAO();
        List<Products> pdata;
        String mode = request.getParameter("mode");
        if(mode.equals("1")){ 
            Products p = pdao.getProductById(Integer.parseInt(id));
            request.setAttribute("p", p);
            request.getRequestDispatcher("view/jsp/admin/product_detail.jsp").forward(request, response);
        }
        if(request.getParameter("mode").equals("3")){
            //pdao.delProductById(id);
            pdata=pdao.getData();
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
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
        String category = request.getParameter("category");
        String color = request.getParameter("color");
        String type = request.getParameter("type");
        String size = request.getParameter("size");
        String store = request.getParameter("store");
        ProductDAO pdao = new ProductDAO();
        List<Products> pdata;
        if (request.getParameter("filter") != null) {
            pdata = pdao.filterProduct(category, color, type, size, store);
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            ColorDAO cldao = new ColorDAO();
            List<Colors> cldata = cldao.getColors();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            SizeDAO sdao = new SizeDAO();
            List<Sizes> sdata = sdao.getSize();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("cldata", cldata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("sdata", sdata);
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
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
