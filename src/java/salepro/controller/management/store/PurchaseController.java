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
import salepro.dao.ProductVariantDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.SupplierDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.ProductVariants;
import salepro.models.PurchaseDetails;
import salepro.models.Purchases;
import salepro.models.Suppliers;
import salepro.models.Warehouse;

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
        /////
        String pvid = request.getParameter("pvid");
        String qty = request.getParameter("qty");
        String price = request.getParameter("price");

        if (pvid != null && qty != null && price != null) {
            request.setAttribute("pvid", pvid);
            request.setAttribute("qty", qty);
            request.setAttribute("price", price);
        }
        //////
        if (mode != null) {
            if (mode.equals("1")) {
                List<PurchaseDetails> pddata = pcdao.getDetailById(Integer.parseInt(pcid));
                List<ProductVariants> pvdata = pvdao.getProductVariantPurchase(Integer.parseInt(pcid));
                request.setAttribute("pvdata", pvdata);
                request.setAttribute("pddata", pddata);
                request.setAttribute("pcid", pcid);
                request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchasedetail.jsp").forward(request, response);
                return;
            } else if (mode.equals("default")) {
                WarehouseDAO wdao = new WarehouseDAO();
                List<Warehouse> wdata = wdao.getData();
                List<Purchases> pcdata = pcdao.getData();
                SupplierDAO spdao = new SupplierDAO();
                List<Suppliers> spdata = spdao.getData();
                request.setAttribute("spdata", spdata != null ? spdata : new ArrayList<>());
                request.setAttribute("pcdata", pcdata != null ? pcdata : new ArrayList<>());
                request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
                request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchaselist.jsp").forward(request, response);
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
        String errEdit = "";
        if (request.getParameter("addDetail") != null) {
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
                    pcdao.updateTotalAmountById(purchaseId);
                }
            } else {
                err += "No product variant selected<br>";
            }
            System.out.println(err);
            // --- TRẢ VỀ VIEW ---
            request.getSession().setAttribute("err", err);
            response.sendRedirect("purchasecontroller?id=" + pcid + "&mode=1&msg=added");
            return;
        } else if (request.getParameter("editDetail") != null) {
            try {
                int purchaseId = Integer.parseInt(pcid);
                int variantId = Integer.parseInt(request.getParameter("productVariantID"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                // Xử lý costPrice đã format: "50.000" → "50000"
                String rawCost = request.getParameter("costPrice").replace(".", "").replace(",", "");
                double costPrice = Double.parseDouble(rawCost);

                // Validate
                if (quantity <= 0) {
                    errEdit += "Quantity must be greater than 0<br>";
                }
                if (costPrice < 0) {
                    errEdit += "Cost price cannot be negative<br>";
                }

                if (errEdit.isEmpty()) {
                    // Update chi tiết
                    PurchaseDetails updated = new PurchaseDetails(purchaseId, variantId, quantity, costPrice);
                    pcdao.updateDetail(updated);
                    pcdao.updateTotalAmountById(purchaseId);

                    response.sendRedirect("purchasecontroller?id=" + pcid + "&mode=1&msg=updated");
                } else {
                    // Có lỗi → lưu lỗi vào session và quay lại detail view
                    request.getSession().setAttribute("errEdit", errEdit);
                    response.sendRedirect("purchasecontroller?id=" + pcid + "&pvid=" + variantId
                            + "&qty=" + quantity + "&price=" + rawCost + "&mode=1&msg=updated");
                }
                return;
            } catch (Exception e) {
                request.getSession().setAttribute("errEdit", "Lỗi xử lý dữ liệu: " + e.getMessage());
                response.sendRedirect("purchasecontroller?id=" + pcid + "&mode=1");
                return;
            }
        } else if (request.getParameter("deleteDetail") != null) {
            try {
                int purchaseId = Integer.parseInt(request.getParameter("id"));
                int variantId = Integer.parseInt(request.getParameter("productVariantID"));

                // Gọi DAO để xóa chi tiết
                pcdao.deleteDetail(purchaseId, variantId);

                // Cập nhật tổng giá trị đơn hàng sau khi xóa
                pcdao.updateTotalAmountById(purchaseId);

                // Redirect lại trang chi tiết đơn hàng
                response.sendRedirect("purchasecontroller?id=" + purchaseId + "&mode=1&msg=delete");
                return;
            } catch (Exception e) {
                request.getSession().setAttribute("err", "Lỗi khi xoá chi tiết đơn hàng: " + e.getMessage());
                response.sendRedirect("purchasecontroller?id=" + request.getParameter("id") + "&mode=1&msg=delete");
                return;
            }
        }

        if (request.getParameter("addPurchase") != null) {
            String warehouseID = request.getParameter("warehouseID");
            String supplierID = request.getParameter("supplierID");
            Purchases p = new Purchases(0, null, Integer.parseInt(supplierID), Integer.parseInt(warehouseID), 0);
            System.out.println(p.getSupplierID() + ", " + p.getWarehouseID());
            pcdao.addPurchase(p);
            request.getSession().setAttribute("err", err);
            response.sendRedirect("purchasecontroller?mode=default");
            return;
        }
        if (request.getParameter("deletePurchase") != null) {
            try {
                int purchaseID = Integer.parseInt(request.getParameter("id"));
                pcdao.delete(purchaseID);  // Gọi DAO
                response.sendRedirect("purchasecontroller?mode=default");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.getSession().setAttribute("errDelete", "Invalid purchase ID.");
                response.sendRedirect("purchasecontroller?mode=default");
            }
        }
        if (request.getParameter("search") != null) {
            List<Purchases> pdata= new ArrayList<>();
            WarehouseDAO wdao = new WarehouseDAO();
            try {
                int warehouseID = Integer.parseInt(request.getParameter("warehouseID"));
                System.out.println(warehouseID);
                pdata = pcdao.searchByWarehouseID(warehouseID);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            request.setAttribute("wdata", wdao.getData());
            request.setAttribute("pcdata", pdata );
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/purchaselist.jsp")
                    .forward(request, response);
            return;
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
