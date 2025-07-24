/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ExcelExport;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.PayrollCalculationDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.models.PayrollCalculation;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import salepro.dao.ReportEmployeeDAO;
import salepro.models.EmployeeReportModel;
import salepro.models.PayrollPeriods;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ExportSalary", urlPatterns = {"/ExportSalary"})
public class ExportSalary extends HttpServlet {

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
            out.println("<title>Servlet ExportSalary</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportSalary at " + request.getContextPath() + "</h1>");
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
        //Các DAO
        PayrollCalculationDAO payrollCalculationDAO = new PayrollCalculationDAO();
        PayrollPeriodDAO payrollPeriodDao = new PayrollPeriodDAO();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        //Excel cho lương
        String periodIdStr = request.getParameter("periodId");
        int periodId = Integer.parseInt(periodIdStr);
        String action = request.getParameter("action");
        if ("exportSalary".equals(action)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"salary_export.xlsx\"");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            try {

                // Tạo header
                int currentRow = 0;

                // Tạo kiểu canh giữa + in đậm + font lớn cho tiêu đề
                CellStyle titleStyle = workbook.createCellStyle();
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                PayrollPeriods period = payrollPeriodDao.getById(periodId);
                // Dòng 1: Tiêu đề chính
                Row titleRow = sheet.createRow(currentRow++);
                titleRow.setHeightInPoints(25); // tăng chiều cao dòng
                Cell titleCell = titleRow.createCell(0);
                titleCell.setCellValue(period.getName());
                titleCell.setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8)); // merge toàn bộ từ cột A đến I

                // Dòng 2: Mã bảng lương
                Row codeRow = sheet.createRow(currentRow++);
                Cell codeCell = codeRow.createCell(0);
                codeCell.setCellValue(period.getCode());
                codeCell.setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

                // Dòng 3: Kỳ làm việc
                Row periodRow = sheet.createRow(currentRow++);
                Cell periodCell = periodRow.createCell(0);
                periodCell.setCellValue(period.getPeriod());
                periodCell.setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));

                // Tạo dòng header (dòng thứ 4)
                Row header = sheet.createRow(currentRow++);
                String[] columns = {
                    "STT", "Tên nhân viên", "Mã nhân viên", "Lương chính", "Làm thêm",
                    "Hoa hồng", "Phụ cấp", "Giảm trừ", "Tổng lương"
                };

                for (int i = 0; i < columns.length; i++) {
                    header.createCell(i).setCellValue(columns[i]);
                }

                // Dữ liệu giả lập – bạn thay bằng truy vấn từ DB hoặc list session
                List<PayrollCalculation> payrollCalculations = payrollCalculationDAO.getPayrollCalculationByPayrollPeriodID(periodId);

                int rowIdx = currentRow;
                int stt = 1;
                for (PayrollCalculation payroll : payrollCalculations) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(stt++);
                    row.createCell(1).setCellValue(payroll.getEmployee().getFullName());
                    row.createCell(2).setCellValue(payroll.getEmployee().getCode());
                    row.createCell(3).setCellValue(payroll.getSalaryAmount());
                    row.createCell(4).setCellValue(payroll.getOvertimeAmount());
                    row.createCell(5).setCellValue(payroll.getCommissionAmount());
                    row.createCell(6).setCellValue(payroll.getAllowanceAmount());
                    row.createCell(7).setCellValue(payroll.getDeductionAmount());
                    row.createCell(8).setCellValue(payroll.getNetSalary());
                }

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();
            } finally {
                workbook.close();
            }
        }
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
        //Excel cho nhân viên bán hàng nhiều nhất
        String type = request.getParameter("type");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        if ("employee".equals(type)) {
            ArrayList<EmployeeReportModel> data = getEmployeeDataFromSession(request);
            createEmployeeExcelSheet(sheet, data);
            response.setHeader("Content-Disposition", "attachment; filename=employee_report.xlsx");
        } else {
            System.out.println("ko thay excel");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // =================== Employee Export ============================
    private ArrayList<EmployeeReportModel> getEmployeeDataFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String filter = (String) session.getAttribute("lastFilter");
        String keyword = (String) session.getAttribute("lastKeyword");

        ReportEmployeeDAO dao = new ReportEmployeeDAO();
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

    private void createEmployeeExcelSheet(Sheet sheet, ArrayList<EmployeeReportModel> data) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Employee ID");
        header.createCell(1).setCellValue("Employee Name");
        header.createCell(2).setCellValue("Number of Invoice");
        header.createCell(3).setCellValue("Total Amount");

        int rowIndex = 1;
        for (EmployeeReportModel model : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(model.getId());
            row.createCell(1).setCellValue(model.getName());
            row.createCell(2).setCellValue(model.getInvoice());
            row.createCell(3).setCellValue(model.getMoney());
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
