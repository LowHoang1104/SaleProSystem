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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dao.CategoryDAO;
import salepro.dao.ProductDAO;
import salepro.dao.StoreDAO;
import salepro.dao.TypeDAO;
import salepro.models.Categories;
import salepro.models.ProductMasters;
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
        String id = request.getParameter("id");
        ProductDAO pdao = new ProductDAO();
        List<ProductMasters> pdata;
        String mode = request.getParameter("mode");
        if (mode.equals("1")) {
            ProductMasters p = pdao.getProductById(id);
            request.setAttribute("p", p);
            request.getRequestDispatcher("view/jsp/admin/product_detail.jsp").forward(request, response);
        }
        if (mode.equals("2")) {
            ProductMasters p = pdao.getProductById(id);
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("p", p);
            request.getRequestDispatcher("view/jsp/admin/product_update.jsp").forward(request, response);
        }
        if (request.getParameter("mode").equals("3")) {
            pdao.delProductById(id);
            pdata = pdao.getData();
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
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
        String kw = request.getParameter("kw");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        String des = request.getParameter("des");
        String price = request.getParameter("price");
        String cost = request.getParameter("cost");
        String image = request.getParameter("image");
        String store = request.getParameter("store");
        Date date = new Date();
        ProductDAO pdao = new ProductDAO();
        List<ProductMasters> pdata;
        if (request.getParameter("filter") != null) {
            pdata = pdao.filterProduct(category, type, store);
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
        }
        if (request.getParameter("add") != null) {
            int cate = Integer.parseInt(category);
            int tp = Integer.parseInt(type);
            double price1 = Double.parseDouble(price);
            double cost1 = Double.parseDouble(cost);
            ProductMasters pm = new ProductMasters(id, name, cate, tp, des, price1, cost1, image, true, date);
            pdao.addProduct(pm);
            pdata = pdao.getData();
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
        }
        if (request.getParameter("update") != null) {
            int cate = Integer.parseInt(category);
            int tp = Integer.parseInt(type);
            double price1 = Double.parseDouble(price);
            double cost1 = Double.parseDouble(cost);
            ProductMasters pm = new ProductMasters(id, name, cate, tp, des, price1, cost1, image, true, date);
            pdao.updateProduct(pm);
            pdata = pdao.getData();
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
        }
        if (request.getParameter("search") != null) {
            pdata = pdao.serchByKeyword(kw);
            CategoryDAO cdao = new CategoryDAO();
            List<Categories> cdata = cdao.getCategory();
            TypeDAO tdao = new TypeDAO();
            List<ProductTypes> tdata = tdao.getTypes();
            StoreDAO stdao = new StoreDAO();
            List<Stores> stdata = stdao.getStores();
            request.setAttribute("stdata", stdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("pdata", pdata);
            request.getRequestDispatcher("view/jsp/admin/productlist.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
