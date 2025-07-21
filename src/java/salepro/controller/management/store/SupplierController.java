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
import salepro.dao.SupplierDAO;
import salepro.models.Suppliers;

/**
 *
 * @author tungd
 */
@WebServlet(name = "SupplierController", urlPatterns = {"/suppliercontroller"})
public class SupplierController extends HttpServlet {

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
            out.println("<title>Servlet SupplierController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SupplierController at " + request.getContextPath() + "</h1>");
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

        String name = request.getParameter("supplierName");
        String contact = request.getParameter("contactPerson");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        String id_raw = request.getParameter("id");

        SupplierDAO sdao = new SupplierDAO();
        List<Suppliers> spdata = new ArrayList<>();

        String errAdd = "", errEdit = "", errDelete = "";
        int supplierID = -1;

        HttpSession session = request.getSession();

        // ===== ADD =====
        if (request.getParameter("add") != null) {
            if (name == null || name.trim().isEmpty()) {
                errAdd += "Name required.<br>";
            }
            if (phone == null || phone.trim().isEmpty()) {
                errAdd += "Phone required.<br>";
            }

            if (errAdd.isEmpty()) {
                if (sdao.checkExists(name, phone)) {
                    errAdd += "Supplier already exists.<br>";
                } else {
                    Suppliers s = new Suppliers(0, name, contact, phone, email, address, description, null);
                    sdao.add(s);
                }
            }

            if (!errAdd.isEmpty()) {
                session.setAttribute("errAdd", errAdd);
                session.setAttribute("oldName", name);
                session.setAttribute("oldContact", contact);
                session.setAttribute("oldPhone", phone);
                session.setAttribute("oldEmail", email);
                session.setAttribute("oldAddress", address);
                session.setAttribute("oldDescription", description);
            }
        }

        // ===== EDIT =====
        if (request.getParameter("editSupplier") != null) {
            try {
                supplierID = Integer.parseInt(id_raw);
            } catch (NumberFormatException e) {
                errEdit += "Invalid ID.<br>";
            }

            if (name == null || name.trim().isEmpty()) {
                errEdit += "Name required.<br>";
            }
            if (phone == null || phone.trim().isEmpty()) {
                errEdit += "Phone required.<br>";
            }

            if (errEdit.isEmpty()) {
                Suppliers s = new Suppliers(supplierID, name, contact, phone, email, address, description, null);
                sdao.update(s);
            }

            if (!errEdit.isEmpty()) {
                session.setAttribute("errEdit", errEdit);
                session.setAttribute("oldEditName", name);
                session.setAttribute("oldEditAddress", address);
                session.setAttribute("oldEditPhone", phone); // nếu có phone
                session.setAttribute("oldEditID", supplierID); // hoặc warehouseID
            }
        }

        // ===== DELETE =====
        if (request.getParameter("deleteSupplier") != null) {
            try {
                supplierID = Integer.parseInt(id_raw);
                System.out.println(errDelete);
                if (sdao.isInPurchase(supplierID)) {
                    errDelete += "Supplier used in purchase. Cannot delete.<br>";
                    System.out.println(errDelete);
                    session.setAttribute("errDelete", errDelete);
                } else {
                    sdao.delete(supplierID);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (request.getParameter("search") != null) {
            String kw = request.getParameter("kw");
            List<Suppliers> result = sdao.searchByName(kw);
            request.setAttribute("spdata", result);
            request.getRequestDispatcher("view/jsp/admin/InventoryManagement/supplierlist.jsp").forward(request, response);
            return; // chặn các xử lý khác sau khi search
        }

        spdata = sdao.getData();
        request.setAttribute("spdata", spdata);
        request.getRequestDispatcher("view/jsp/admin/InventoryManagement/supplierlist.jsp").forward(request, response);
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
