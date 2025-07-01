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
import java.util.List;
import salepro.dao.ProductVariantDAO;
import salepro.dao.PurchaseDAO;
import salepro.models.ProductVariants;
import salepro.models.PurchaseDetails;
import salepro.models.StockTakeDetail;

/**
 *
 * @author tungd
 */
@WebServlet(name = "PurchaseController", urlPatterns = {"/purchasecontroller"})
public class PurchaseController extends HttpServlet {

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
            out.println("<title>Servlet PurchaseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseController at " + request.getContextPath() + "</h1>");
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
        String pcid = request.getParameter("id");
        ProductVariantDAO pvdao = new ProductVariantDAO();
        PurchaseDAO pcdao = new PurchaseDAO();
        String mode = request.getParameter("mode");
        if (mode != null) {
            if (mode.equals("1")) {
                List<PurchaseDetails> pddao = pcdao.getDetailById(Integer.parseInt(pcid));
                List<ProductVariants> pvdata = pvdao.getProductVariantPurchase(Integer.parseInt(pcid));
                request.setAttribute("pvdata", pvdata);
                request.setAttribute("pddao", pddao);
                request.setAttribute("pcid", pcid);
                request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchasedetail.jsp").forward(request, response);
                return;
            }

        }
        List<PurchaseDetails> sddata = pcdao.getDetailById(Integer.parseInt(pcid));
        List<ProductVariants> pvdata = pvdao.getProductVariantPurchase(Integer.parseInt(pcid));
        request.setAttribute("pvdata", pvdata);
        request.setAttribute("sddata", sddata);
        request.setAttribute("pcid", pcid);
        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchasedetail.jsp").forward(request, response);

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
        String pcid = request.getParameter("id");
        String err = "";
        ProductVariantDAO pvdao = new ProductVariantDAO();
        PurchaseDAO pcdao = new PurchaseDAO();
        if (request.getParameter("add") != null) {
            int purchaseId = Integer.parseInt(pcid);
            String[] selectedIds = request.getParameterValues("variantIds");

            if (selectedIds != null) {
                for (String idStr : selectedIds) {
                    int variantId = Integer.parseInt(idStr);

                    // --- XỬ LÝ QUANTITY ---
                    String qtyStr = request.getParameter("quantity_" + variantId);
                    int quantity = 0;
                    if (qtyStr == null || qtyStr.trim().isEmpty() || qtyStr.isBlank()) {
                        err += "Quantity for PV ID " + variantId + " is required<br>";
                        continue;
                    }
                    if (!qtyStr.trim().matches("\\d+")) {
                        err += "Quantity for PV ID " + variantId + " must be a number<br>";
                        continue;
                    }
                    quantity = Integer.parseInt(qtyStr.trim());
                    if (quantity <= 0) {
                        err += "Quantity for PV ID " + variantId + " must be greater than 0<br>";
                        continue;
                    }

                    // --- XỬ LÝ PRICE ---
                    String priceStr = request.getParameter("costPrice_" + variantId);
                    double price = 0;
                    if (priceStr == null || priceStr.trim().isEmpty()) {
                        err += "Price for PV ID " + variantId + " is required<br>";
                        continue;
                    }
                    price = Double.parseDouble(priceStr.trim());
                    if (price < 0) {
                        err += "Price for PV ID " + variantId + " cannot be negative<br>";
                        continue;
                    }

                    // --- INSERT ---
                    PurchaseDetails pd = new PurchaseDetails(purchaseId, variantId, quantity, price);
                    pcdao.addDetail(pd);
                }
            } else {
                err += "No product variant selected<br>";
            }
            System.out.println(err);
            // --- TRẢ VỀ VIEW ---
            request.getSession().setAttribute("err", err);
            response.sendRedirect("purchasecontroller?id=" + pcid + "&mode=1&msg=added");
            return;
        }

        List<PurchaseDetails> sddata = pcdao.getDetailById(Integer.parseInt(pcid));
        List<ProductVariants> pvdata = pvdao.getProductVariantPurchase(Integer.parseInt(pcid));
        request.setAttribute("pvdata", pvdata);
        request.setAttribute("sddata", sddata);
        request.setAttribute("pcid", pcid);
        request.setAttribute("err", err);
        System.out.println(err);
        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchasedetail.jsp").forward(request, response);
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
