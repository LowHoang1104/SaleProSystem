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
import java.util.Date;
import java.util.List;
import salepro.dao.ProductVariantDAO;
import salepro.dao.StockTakeDAO;
import salepro.dao.WarehouseDAO;
import salepro.models.ProductVariants;
import salepro.models.StockTake;
import salepro.models.StockTakeDetail;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
@WebServlet(name = "StockTakeController", urlPatterns = {"/stocktakecontroller"})
public class StockTakeController extends HttpServlet {

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
            out.println("<title>Servlet StockTakeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StockTakeController at " + request.getContextPath() + "</h1>");
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
        String stkid = request.getParameter("id");
        String mode = request.getParameter("mode");
        StockTakeDAO stkdao = new StockTakeDAO();
        ProductVariantDAO pvdao = new ProductVariantDAO();
        String err = "";
        if (mode != null) {
            if (mode.equals("1")) {
                List<StockTakeDetail> sddata = stkdao.getDetailById(Integer.parseInt(stkid));
                List<ProductVariants> pvdata = pvdao.getProductVariantStockTake(Integer.parseInt(stkid));
                request.setAttribute("pvdata", pvdata);
                request.setAttribute("sddata", sddata);
                request.setAttribute("stkid", stkid);
                request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktakedetail.jsp").forward(request, response);
                return;
            }
            if (mode.equals("searchEqual")) {
                List<StockTakeDetail> sddata = stkdao.searchEqual(Integer.parseInt(stkid));
                List<ProductVariants> pvdata = pvdao.getProductVariantStockTake(Integer.parseInt(stkid));
                request.setAttribute("pvdata", pvdata);
                request.setAttribute("sddata", sddata);
                request.setAttribute("stkid", stkid);
                request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktakedetail.jsp").forward(request, response);
                return;
            }
            if (mode.equals("searchNotEqual")) {
                List<StockTakeDetail> sddata = stkdao.searchNotEqual(Integer.parseInt(stkid));
                List<ProductVariants> pvdata = pvdao.getProductVariantStockTake(Integer.parseInt(stkid));
                request.setAttribute("pvdata", pvdata);
                request.setAttribute("sddata", sddata);
                request.setAttribute("stkid", stkid);
                request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktakedetail.jsp").forward(request, response);
                return;
            }
            if (mode.equals("3")) {
                try {
                    int id = Integer.parseInt(stkid);
                    boolean success = stkdao.delete(id);
                    if (success) {
                        response.sendRedirect("productsidebarcontroller?mode=4&msg=deleted");
                    } else {
                        response.sendRedirect("productsidebarcontroller?mode=4&msg=faildelete");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("productsidebarcontroller?mode=4&msg=invalidid");
                }
                return;
            }
        }
        List<StockTakeDetail> sddata = stkdao.getDetailById(Integer.parseInt(stkid));
        List<ProductVariants> pvdata = pvdao.getProductVariantStockTake(Integer.parseInt(stkid));
        request.setAttribute("pvdata", pvdata);
        request.setAttribute("sddata", sddata);
        request.setAttribute("stkid", stkid);
        request.setAttribute("err", err);
        request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktakedetail.jsp").forward(request, response);
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
        String stkid = request.getParameter("id");
        StockTakeDAO stkdao = new StockTakeDAO();
        ProductVariantDAO pvdao = new ProductVariantDAO();
        String err = "";
        List<Warehouse> wdata = new WarehouseDAO().getData();
        request.setAttribute("wdata", wdata);
        if (request.getParameter("add") != null) {
            String[] selectedIds = request.getParameterValues("variantIds");
            if (selectedIds != null) {
                for (String idStr : selectedIds) {
                    int variantId = Integer.parseInt(idStr);
                    String qtyStr = request.getParameter("actualQuantity_" + variantId);
                    int actualQty = 0;

                    if (qtyStr == null || qtyStr.trim().isEmpty()) {
                        err += "Actual quantity for PV ID " + variantId + " is required<br/>";
                        continue;
                    }

                    boolean isNumber = qtyStr.trim().matches("\\d+");
                    if (!isNumber) {
                        err += "Actual quantity for PV ID " + variantId + " must be a number<br/>";
                        continue;
                    }

                    actualQty = Integer.parseInt(qtyStr.trim());
                    if (actualQty < 0) {
                        err += "Actual quantity for PV ID " + variantId + " cannot be negative<br/>";
                        continue;
                    }

                    // Xử lý khi hợp lệ
                    StockTakeDetail sdnew = new StockTakeDetail(0, Integer.parseInt(stkid), variantId, actualQty);
                    stkdao.add(sdnew);
                }

                // Nếu không có lỗi thì redirect
                if (err.isEmpty()) {
                    response.sendRedirect("stocktakecontroller?id=" + stkid + "&mode=1&msg=added");
                    return;
                }
            }
        }
        if (request.getParameter("addStockTake") != null) {
            String warehouseIdRaw = request.getParameter("warehouseID");
            String note = request.getParameter("note");

            String stkerr = "";
            int warehouseID = 0;
            try {
                warehouseID = Integer.parseInt(warehouseIdRaw);
            } catch (NumberFormatException e) {
                stkerr += "Invalid warehouse ID. ";
            }

            if (err.isEmpty()) {
                StockTake st = new StockTake();
                st.setWarehouseID(warehouseID);
                st.setNote(note);
                st.setCheckDate(new Date());       // lấy ngày hiện tại
                st.setCheckedBy(2);                // tạm fix userID = 2

                boolean success = new StockTakeDAO().addStockTake(st);
                if (!success) {
                    stkerr += "Failed to add Stock Take.";
                }
            }

            // Có thể set lỗi vào session nếu muốn hiển thị ở trang đích
            if (!stkerr.isEmpty()) {
                request.getSession().setAttribute("stockTakeError", stkerr);
                return;
            }

            // Redirect về productsidebarcontroller?mode=4
            response.sendRedirect(request.getContextPath() + "/productsidebarcontroller?mode=4");
            return;
        }
        List<StockTakeDetail> sddata = stkdao.getDetailById(Integer.parseInt(stkid));
        List<ProductVariants> pvdata = pvdao.getProductVariantStockTake(Integer.parseInt(stkid));
        request.setAttribute("pvdata", pvdata);
        request.setAttribute("sddata", sddata);
        request.setAttribute("stkid", stkid);
        request.setAttribute("err", err);
        request.getRequestDispatcher("view/jsp/admin/ProductManagement/stocktakedetail.jsp").forward(request, response);
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
