/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.product;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import salepro.dao.ColorDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.SizeDAO;
import salepro.models.Colors;
import salepro.models.ProductMasters;
import salepro.models.ProductVariants;
import salepro.models.Sizes;

/**
 *
 * @author tungd
 */
@WebServlet(name = "ProductVariantController", urlPatterns = {"/productvariantcontroller"})
public class ProductVariantController extends HttpServlet {

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
            out.println("<title>Servlet ProductVariantController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductVariantController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        String code = request.getParameter("productCode");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        String unit = request.getParameter("unit");
        String averageQuantity = request.getParameter("averageQuantity");
        ProductMasterDAO pdao = new ProductMasterDAO();
        ProductVariantDAO pvdao = new ProductVariantDAO();
        String err = "";
        if (request.getParameter("add") != null) {
            if (unit.isBlank() || unit.isEmpty() || unit.trim().isEmpty()) {
                err += "Ko đc để Unit trống";
            } else {
                ProductVariants pv = new ProductVariants(0, code, Integer.parseInt(size), Integer.parseInt(color), null, unit, Integer.parseInt(averageQuantity));
                pvdao.add(pv);
            }
        }
        List<ProductVariants> pvdata = pvdao.getProductVariantByID(code);
        ProductMasters p = pdao.getProductById(code);
        ColorDAO cdao = new ColorDAO();
        List<Colors> cldata = cdao.getColors();
        SizeDAO sdao = new SizeDAO();
        List<Sizes> sdata = sdao.getSize();
        request.setAttribute("cldata", cldata);
        request.setAttribute("sdata", sdata);
        request.setAttribute("p", p);
        request.setAttribute("pvdata", pvdata);
        request.setAttribute("err", err);
        request.getRequestDispatcher("view/jsp/admin/ProductManagement/product_detail.jsp").forward(request, response);
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
