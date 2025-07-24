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
import java.time.LocalDate;
import java.util.ArrayList;
import salepro.dao.ReportProductDAO;
import salepro.models.ProductReportModel;

/**
 *
 * @author tungd
 */
@WebServlet(name = "ProductReportController", urlPatterns = {"/productreport"})
public class ProductReportController extends HttpServlet {

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
            out.println("<title>Servlet ProductReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductReportController at " + request.getContextPath() + "</h1>");
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
        ReportProductDAO rpdao = new ReportProductDAO();
        ArrayList<ProductReportModel> data;
        data = rpdao.getData();
        HttpSession session = request.getSession();
        session.setAttribute("productViewMode", "table");
        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/InventoryReport/ProductReport.jsp").forward(request, response);
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

        ReportProductDAO rpdao = new ReportProductDAO();
        ArrayList<ProductReportModel> data = rpdao.getData();
        String err = "";
        HttpSession session = request.getSession();

        // Reset filters
        session.setAttribute("lastProductFilter", null);
        session.setAttribute("lastStartDate", null);
        session.setAttribute("lastEndDate", null);
        session.setAttribute("lastMinPercentBelowMin", null);
        session.setAttribute("lastMinStockValue", null);

        String view = request.getParameter("view");
        if (view != null && (view.equals("chart") || view.equals("table"))) {
            session.setAttribute("productViewMode", view);
        }

        // Xử lý các filter theo thời gian
        String[] timeKeys = {
            "today", "yesterday", "thisWeek", "lastWeek", "last7Days",
            "thisMonth", "lastMonth", "last30Days", "thisQuarter", "lastQuarter",
            "thisYear", "lastYear"
        };
        for (String key : timeKeys) {
            if (request.getParameter(key) != null) {
                data = rpdao.getDataByDateRange(key);
                session.setAttribute("lastProductFilter", key);
                break;
            }
        }

        // Xử lý filter tùy chỉnh
        if (request.getParameter("filter") != null) {
            String startStr = request.getParameter("startDate");
            String endStr = request.getParameter("endDate");
            String minPercentStr = request.getParameter("minPercentLow");
            String minStockStr = request.getParameter("minStockValue");

            double minPercent = 0;
            double minStock = 0;

            try {
                // Mặc định ngày nếu không nhập
                LocalDate startDate = (startStr == null || startStr.isEmpty())
                        ? LocalDate.of(2024, 1, 1)
                        : LocalDate.parse(startStr);

                LocalDate endDate = (endStr == null || endStr.isEmpty())
                        ? LocalDate.now()
                        : LocalDate.parse(endStr);

                if (startDate.isAfter(endDate)) {
                    err += "Ngày bắt đầu không được lớn hơn ngày kết thúc.<br>";
                }

                // Kiểm tra và parse phần trăm
                try {
                    if (minPercentStr != null && !minPercentStr.isEmpty()) {
                        minPercent = Double.parseDouble(minPercentStr);
                        if (minPercent < 0) {
                            err += "Phần trăm dưới mức tối thiểu không được âm.<br>";
                        }
                    }
                } catch (NumberFormatException e) {
                    err += "Phần trăm dưới mức tối thiểu phải là một số.<br>";
                }

                // Kiểm tra và parse giá trị tồn
                try {
                    if (minStockStr != null && !minStockStr.isEmpty()) {
                        minStock = Double.parseDouble(minStockStr);
                        if (minStock < 0) {
                            err += "Giá trị tồn không được âm.<br>";
                        }
                    }
                } catch (NumberFormatException e) {
                    err += "Giá trị tồn phải là một số.<br>";
                }

                // Nếu không có lỗi thì mới gọi DAO
                if (err.isEmpty()) {
                    data = rpdao.getDataByCustomFilter(startDate.toString(), endDate.toString(), minPercent, minStock);

                    // Lưu filter vào session để xuất Excel
                    session.setAttribute("lastStartDate", startDate.toString());
                    session.setAttribute("lastEndDate", endDate.toString());
                    session.setAttribute("lastMinPercentBelowMin", minPercent);
                    session.setAttribute("lastMinStockValue", minStock);
                }

            } catch (Exception e) {
                err += "Định dạng ngày không hợp lệ.<br>";
            }
        }

        request.setAttribute("data", data);
        request.setAttribute("err", err);
        request.getRequestDispatcher("view/jsp/admin/InventoryReport/ProductReport.jsp").forward(request, response);
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
