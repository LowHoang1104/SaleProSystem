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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.ReportSupplierDAO;
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
        // Gọi DAO lấy dữ liệu
        ReportSupplierDAO dao = new ReportSupplierDAO();
        ArrayList<SupplierReportModel> list = dao.getReport(); // Có thể tùy biến theo ngày/tháng

        // Tạo Excel Workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Supplier Report");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Supplier ID");
        header.createCell(1).setCellValue("Supplier Name");
        header.createCell(2).setCellValue("Total Quantity");
        header.createCell(3).setCellValue("Total Amount");

        // Dữ liệu từng dòng
        int rowIndex = 1;
        for (SupplierReportModel model : list) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(model.getId());
            row.createCell(1).setCellValue(model.getName());
            row.createCell(2).setCellValue(model.getProduct());
            row.createCell(3).setCellValue(model.getMoney());
        }

        // Thiết lập response để download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=supplier_report.xlsx");

        // Ghi dữ liệu ra response
        workbook.write(response.getOutputStream());
        workbook.close();
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
