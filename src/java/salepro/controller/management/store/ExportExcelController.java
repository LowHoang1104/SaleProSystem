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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.ReportProductDAO;
import salepro.dao.ReportSupplierDAO;
import salepro.models.ProductReportModel;
import salepro.models.SupplierReportModel;

/**
 *
 * @author tungd
 */
@WebServlet(name = "ExportExcelController", urlPatterns = {"/excelcontroller"})
public class ExportExcelController extends HttpServlet {

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
            out.println("<title>Servlet ExportExcelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportExcelController at " + request.getContextPath() + "</h1>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        if ("supplier".equals(type)) {
            ArrayList<SupplierReportModel> data = getSupplierDataFromSession(request);
            createSupplierExcelSheet(sheet, data);
            response.setHeader("Content-Disposition", "attachment; filename=supplier_report.xlsx");
        } else if ("product".equals(type)) {
            ArrayList<ProductReportModel> data = getProductDataFromSession(request);
            createProductExcelSheet(sheet, data);
            response.setHeader("Content-Disposition", "attachment; filename=product_report.xlsx");
        } else {
            System.out.println("ko thay excel");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // =================== Supplier Export ============================
    private ArrayList<SupplierReportModel> getSupplierDataFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String filter = (String) session.getAttribute("lastFilter");
        String keyword = (String) session.getAttribute("lastKeyword");

        ReportSupplierDAO dao = new ReportSupplierDAO();
        if (filter == null || filter.equals("all")) {
            return dao.getReport();
        }

        switch (filter) {
            case "today":
                return dao.getReportToday();
            case "yesterday":
                return dao.getReportYesterday();
            case "thisWeek":
                return dao.getReportThisWeek();
            case "lastWeek":
                return dao.getReportLastWeek();
            case "last7days":
                return dao.getReportLast7Days();
            case "thisMonth":
                return dao.getReportThisMonth();
            case "lastMonth":
                return dao.getReportLastMonth();
            case "last30days":
                return dao.getReportLast30Days();
            case "thisQuarter":
                return dao.getReportThisQuarter();
            case "lastQuarter":
                return dao.getReportLastQuarter();
            case "thisYear":
                return dao.getReportThisYear();
            case "lastYear":
                return dao.getReportLastYear();
            case "search":
                if (keyword != null && !keyword.trim().isEmpty()) {
                    return dao.getReportByKeyword(keyword);
                } else {
                    return dao.getReport();
                }
            default:
                return dao.getReport();
        }
    }

    private void createSupplierExcelSheet(Sheet sheet, ArrayList<SupplierReportModel> data) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Supplier ID");
        header.createCell(1).setCellValue("Supplier Name");
        header.createCell(2).setCellValue("Total Quantity");
        header.createCell(3).setCellValue("Total Amount");

        int rowIndex = 1;
        for (SupplierReportModel model : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(model.getId());
            row.createCell(1).setCellValue(model.getName());
            row.createCell(2).setCellValue(model.getProduct());
            row.createCell(3).setCellValue(model.getMoney());
        }
    }

    // =================== Product Export ============================
    private ArrayList<ProductReportModel> getProductDataFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String filter = (String) session.getAttribute("lastProductFilter");
        String startDate = (String) session.getAttribute("lastStartDate");
        String endDate = (String) session.getAttribute("lastEndDate");
        Double minPercent = (Double) session.getAttribute("lastMinPercentBelowMin");
        Double minStock = (Double) session.getAttribute("lastMinStockValue");

        ReportProductDAO dao = new ReportProductDAO();

        if (filter != null && !filter.isEmpty()) {
            return dao.getDataByDateRange(filter);
        } else if (startDate != null && endDate != null && minPercent != null && minStock != null) {
            return dao.getDataByCustomFilter(startDate, endDate, minPercent, minStock);
        } else {
            return dao.getData(); // fallback all
        }
    }

    private void createProductExcelSheet(Sheet sheet, ArrayList<ProductReportModel> data) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Category");
        header.createCell(1).setCellValue("Total Quantity");
        header.createCell(2).setCellValue("Stock Value");
        header.createCell(3).setCellValue("Below Min (%)");
        header.createCell(4).setCellValue("Above Min Count");
        header.createCell(5).setCellValue("Below Min Count");

        int rowIndex = 1;
        for (ProductReportModel model : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(model.getName());
            row.createCell(1).setCellValue(model.getQuantity());
            row.createCell(2).setCellValue(model.getTotalmoney());
            row.createCell(3).setCellValue(model.getPercentbelowmin());
            row.createCell(4).setCellValue(model.getNumbergreatermin());
            row.createCell(5).setCellValue(model.getNumberbelowmin());
        }
    }

}
