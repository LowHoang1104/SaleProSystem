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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.InventoryDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.StoreDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.Inventories;
import salepro.models.ProductVariants;
import salepro.models.Stores;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
@WebServlet(name = "WarehouseController", urlPatterns = {"/warehousecontroller"})
public class WarehouseController extends HttpServlet {

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
            out.println("<title>Servlet WarehouseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WarehouseController at " + request.getContextPath() + "</h1>");
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

        String id_raw = request.getParameter("wid");

        // ====== Nếu có ID → hiển thị chi tiết kho ======
        if (id_raw != null && !id_raw.trim().isEmpty()) {
            try {
                int wid = Integer.parseInt(id_raw);

                InventoryDAO idao = new InventoryDAO();
                List<Inventories> idata = idao.getInventoryByWarehouseId(wid);

                ProductVariantDAO pvdao = new ProductVariantDAO();
                List<ProductVariants> pvdata = pvdao.getProductVariantNotInWarehouse(wid);

                request.setAttribute("wid", wid);
                request.setAttribute("idata", idata != null ? idata : new ArrayList<>());
                request.setAttribute("pvdata", pvdata != null ? pvdata : new ArrayList<>());

                request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehousedetail.jsp")
                        .forward(request, response);
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // fallback xuống danh sách nếu id sai
            }
        }

        // ====== Ngược lại → hiển thị danh sách kho ======
        WarehouseDAO wdao = new WarehouseDAO();
        StoreDAO stdao = new StoreDAO();

        List<Warehouse> wdata = wdao.getData();
        List<Stores> stdata = stdao.getData();

        request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
        request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());

        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehouselist.jsp")
                .forward(request, response);
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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String storeID_raw = request.getParameter("storeID");
        String name = request.getParameter("warehouseName");
        String address = request.getParameter("warehouseAddress");
        String warehouseID_raw = request.getParameter("warehouseID");

        WarehouseDAO wdao = new WarehouseDAO();
        StoreDAO stdao = new StoreDAO();

        List<Stores> stdata = stdao.getData();
        List<Warehouse> wdata = new ArrayList<>();

        String errAdd = "", errEdit = "";
        int storeID = -1, warehouseID = -1;

        // ====== ADD ======
        if (request.getParameter("add") != null) {

            if (name == null || name.trim().isEmpty()) {
                errAdd += "Warehouse name is required.<br>";
            }
            if (address == null || address.trim().isEmpty()) {
                errAdd += "Warehouse address is required.<br>";
            }
            if (storeID_raw == null || storeID_raw.trim().isEmpty()) {
                errAdd += "Store is required.<br>";
            }

            if (errAdd.isEmpty()) {
                try {
                    storeID = Integer.parseInt(storeID_raw);
                    if (wdao.checkExists(name, address, storeID)) {
                        errAdd += "Warehouse already exists.<br>";
                    } else {
                        Warehouse w = new Warehouse(0, name, address, storeID);
                        wdao.add(w);
                    }
                } catch (NumberFormatException e) {
                    errAdd += "Invalid Store ID.<br>";
                }
            }

            if (!errAdd.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errAdd", errAdd);
                session.setAttribute("oldWarehouseName", name);
                session.setAttribute("oldWarehouseAddress", address);
                session.setAttribute("oldStoreID", storeID_raw);
                session.setAttribute("oldWarehouseID", warehouseID_raw);
            }
        }

        // ====== EDIT ======
        if (request.getParameter("editWarehouse") != null) {

            if (name == null || name.trim().isEmpty()) {
                errEdit += "Warehouse name is required.<br>";
            }
            if (address == null || address.trim().isEmpty()) {
                errEdit += "Warehouse address is required.<br>";
            }
            if (storeID_raw == null || storeID_raw.trim().isEmpty()) {
                errEdit += "Store is required.<br>";
            }
            if (warehouseID_raw == null || warehouseID_raw.trim().isEmpty()) {
                errEdit += "Missing Warehouse ID.<br>";
            }

            if (errEdit.isEmpty()) {
                try {
                    storeID = Integer.parseInt(storeID_raw);
                    warehouseID = Integer.parseInt(warehouseID_raw);
                    Warehouse w = new Warehouse(warehouseID, name, address, storeID);
                    wdao.update(w);
                } catch (NumberFormatException e) {
                    errEdit += "Invalid Store or Warehouse ID.<br>";
                }
            }

            if (!errEdit.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errEdit", errEdit);
                session.setAttribute("oldWarehouseName", name);
                session.setAttribute("oldWarehouseAddress", address);
                session.setAttribute("oldStoreID", storeID_raw);
                session.setAttribute("oldWarehouseID", warehouseID_raw);
            }

        }

        // ====== DELETE ======
        if (request.getParameter("deleteWarehouse") != null) {
            try {
                warehouseID = Integer.parseInt(request.getParameter("id"));
                wdao.delete(warehouseID);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (request.getParameter("search") != null) {
            try {
                int storeIDs = Integer.parseInt(request.getParameter("storeID"));
                wdata = wdao.searchByStoreID(storeIDs);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // hoặc thêm errSearch nếu cần
            }

            request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
            request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehouselist.jsp")
                    .forward(request, response);
            return; // Dừng xử lý các phần bên dưới
        }
        // ====== DETAIL ======
        if (request.getParameter("warehousedetail") != null) {
            String wid = request.getParameter("wid");
            InventoryDAO idao = new InventoryDAO();
            List<Inventories> idata = idao.getInventoryByWarehouseId(Integer.parseInt(wid));
            ProductVariantDAO pvdao = new ProductVariantDAO();
            List<ProductVariants> pvdata = pvdao.getProductVariantNotInWarehouse(Integer.parseInt(wid));
            System.out.println(wid + " " + idata.size() + " " + pvdata.size());

            request.setAttribute("wid", wid);
            request.setAttribute("pvdata", pvdata);
            request.setAttribute("idata", idata);
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehousedetail.jsp")
                    .forward(request, response);
            return;
        }
        if (request.getParameter("addDetail") != null) {
            String[] variantIds = request.getParameterValues("variantIds");
            String err = "";
            String wid_raw = request.getParameter("id");
            if (variantIds != null && wid_raw != null) {
                int wid = Integer.parseInt(wid_raw);
                for (String vid_raw : variantIds) {
                    try {
                        int vid = Integer.parseInt(vid_raw);
                        int qty = Integer.parseInt(request.getParameter("quantity_" + vid));
                        wdao.addWarehouseDetail(wid, vid, qty);
                    } catch (Exception e) {
                        err += "Add failed for variant ID " + vid_raw + "<br>";
                    }
                }

                if (!err.isEmpty()) {
                    request.getSession().setAttribute("err", err);
                }
                response.sendRedirect("warehousecontroller?warehousedetail=&wid=" + wid_raw);
                return;
            }
        }
        if (request.getParameter("editDetail") != null) {
            String wid_raw = request.getParameter("warehouseID");
            String vid_raw = request.getParameter("productVariantID");
            String qty_raw = request.getParameter("quantity");
            String err = "";
            InventoryDAO idao = new InventoryDAO();
            try {
                int wid = Integer.parseInt(wid_raw);
                int vid = Integer.parseInt(vid_raw);
                int qty = Integer.parseInt(qty_raw);
                wdao.updateWarehouseDetail(wid, vid, qty);
            } catch (Exception e) {
                err += "Update failed: " + e.getMessage();
                request.getSession().setAttribute("errEdit", err);
                request.getSession().setAttribute("wid", wid_raw);
                request.getSession().setAttribute("pvid", vid_raw);
                request.getSession().setAttribute("qty", qty_raw);
            }

            response.sendRedirect("warehousecontroller?warehousedetail=&wid=" + wid_raw);
            return;
        }

        wdata = wdao.getData();
        request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
        request.setAttribute("wdata", wdata != null ? wdata : new ArrayList<>());

        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/warehouselist.jsp")
                .forward(request, response);
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
