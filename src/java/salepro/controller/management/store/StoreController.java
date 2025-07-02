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
import salepro.dao.StoreDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.Stores;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
@WebServlet(name = "StoreController", urlPatterns = {"/storecontroller"})
public class StoreController extends HttpServlet {

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
            out.println("<title>Servlet StoreController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreController at " + request.getContextPath() + "</h1>");
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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("storeName");
        String address = request.getParameter("storeAddress");
        String phone = request.getParameter("description");
        String storeID_raw = request.getParameter("id");

        StoreDAO sdao = new StoreDAO();
        WarehouseDAO wdao = new WarehouseDAO();

        List<Stores> stdata = new ArrayList<>();
        String errAdd = "", errDelete = "";

        // ====== THÊM ======
        if (request.getParameter("add") != null) {
            if (name == null || name.trim().isEmpty()) {
                errAdd += "Store name is required.<br>";
            }

            if (address == null || address.trim().isEmpty()) {
                errAdd += "Store address is required.<br>";
            }

            if (phone == null || phone.trim().isEmpty()) {
                errAdd += "Store phone is required.<br>";
            }

            if (errAdd.isEmpty()) {
                // Check tồn tại
                if (sdao.checkExists(name, address)) {
                    errAdd += "Store with this name and address already exists.<br>";
                } else {
                    Stores s = new Stores(0, name, address, phone);
                    sdao.add(s);

                    int newStoreID = sdao.getLastInsertID();
                    if (newStoreID > 0) {
                        Warehouse w = new Warehouse(0, name, address, newStoreID);
                        wdao.add(w);
                    }
                }
            }

            if (!errAdd.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errAdd", errAdd);
                session.setAttribute("oldStoreName", name);
                session.setAttribute("oldStoreAddress", address);
                session.setAttribute("oldStorePhone", phone);
            }
        }

        // ====== XOÁ ======
        if (request.getParameter("deleteStore") != null) {
            try {
                int storeID = Integer.parseInt(storeID_raw);

                // Kiểm tra xem có warehouse liên quan không
                boolean isLinked = wdao.checkStoreInWarehouse(storeID);

                if (isLinked) {
                    errDelete += "Cannot delete store because it is linked to a warehouse.<br>";
                    request.getSession().setAttribute("errDelete", errDelete);
                } else {
                    sdao.delete(storeID);
                }

            } catch (NumberFormatException e) {
                errDelete += "Invalid Store ID.<br>";
                request.getSession().setAttribute("errDelete", errDelete);
            }
        }
// ====== SỬA ======
        if (request.getParameter("editStore") != null) {
            int storeID = -1;
            if (storeID_raw == null || storeID_raw.trim().isEmpty()) {
                errAdd += "Store ID is missing.<br>";
            } else {
                try {
                    storeID = Integer.parseInt(storeID_raw);
                } catch (NumberFormatException e) {
                    errAdd += "Store ID must be a number.<br>";
                }
            }

            if (name == null || name.trim().isEmpty()) {
                errAdd += "Store name is required.<br>";
            }
            if (address == null || address.trim().isEmpty()) {
                errAdd += "Store address is required.<br>";
            }
            if (phone == null || phone.trim().isEmpty()) {
                errAdd += "Store phone is required.<br>";
            }

            if (errAdd.isEmpty()) {
                Stores s = new Stores(storeID, name, address, phone);
                sdao.update(s);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("errEdit", errAdd);
                session.setAttribute("oldStoreName", name);
                session.setAttribute("oldStoreAddress", address);
                session.setAttribute("oldStorePhone", phone);
                session.setAttribute("editStoreID", storeID_raw);
            }
        }

        // ====== LOAD DỮ LIỆU ======
        stdata = sdao.getData();
        request.setAttribute("stdata", stdata != null ? stdata : new ArrayList<>());
        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/storelist.jsp").forward(request, response);
    }

}
