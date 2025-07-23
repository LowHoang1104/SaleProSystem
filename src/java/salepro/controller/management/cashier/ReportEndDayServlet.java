/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.InvoiceDAO;
import salepro.dao.UserDAO;
import salepro.models.Invoices;
import salepro.models.Users;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "ReportEndDayServlet", urlPatterns = {"/ReportEndDayServlet"})
public class ReportEndDayServlet extends HttpServlet {

    private static final String END_OF_DAY_REPORT = "view/jsp/employees/end_of_day_report.jsp";
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("exportExcel".equals(action)) {
            String date = request.getParameter("date");
            String employeeIdStr = request.getParameter("employeeId");
            String creatorIdStr = request.getParameter("creatorId");
            String warehouseSelect = request.getParameter("warehouseSelect");

            Integer selectedEmployeeId = null;
            Integer selectedCreatorId = null;

            if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
                try {
                    selectedEmployeeId = Integer.parseInt(employeeIdStr);
                } catch (NumberFormatException e) {
                    selectedEmployeeId = null;
                }
            }

            if (creatorIdStr != null && !creatorIdStr.isEmpty()) {
                try {
                    selectedCreatorId = Integer.parseInt(creatorIdStr);
                } catch (NumberFormatException e) {
                    selectedCreatorId = null;
                }
            }

            exportToExcelPOI(request, response, date, selectedEmployeeId, selectedCreatorId, warehouseSelect);
        } else {
            doPost(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String date = request.getParameter("date");
        String pageStr = request.getParameter("page");
        String employeeIdStr = request.getParameter("employeeId");
        String creatorIdStr = request.getParameter("creatorId");
        String warehouseSelect = request.getParameter("warehouseSelect");

        int currentPage = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        Integer selectedEmployeeId = null;
        Integer selectedCreatorId = null;

        if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
            try {
                selectedEmployeeId = Integer.parseInt(employeeIdStr);
            } catch (NumberFormatException e) {
                selectedEmployeeId = null;
            }
        }

        if (creatorIdStr != null && !creatorIdStr.isEmpty()) {
            try {
                selectedCreatorId = Integer.parseInt(creatorIdStr);
            } catch (NumberFormatException e) {
                selectedCreatorId = null;
            }
        }

        if ("getReportData".equals(action)) {
            try {
                // Lấy listUsers để hiển thị trong dropdown
                UserDAO userDAO = new UserDAO();
                List<Users> listUsers = userDAO.getData();

                InvoiceDAO invoiceDAO = new InvoiceDAO();
                List<Invoices> allInvoices = invoiceDAO.getInvoicesByDate(date);
                System.out.println("Total invoices: " + allInvoices.size());
                List<Invoices> filteredInvoices = new ArrayList<>();

                for (Invoices invoice : allInvoices) {
                    boolean shouldInclude = true;

                    // Kiểm tra employee filter
                    if (selectedEmployeeId != null) {
                        if (invoice.getUserId() != selectedEmployeeId) {
                            shouldInclude = false;
                        }
                    }

                    // Kiểm tra creator filter
                    if (selectedCreatorId != null) {
                        if (invoice.getCreatedBy() != selectedCreatorId) {
                            shouldInclude = false;
                        }
                    }

                    if (shouldInclude) {
                        filteredInvoices.add(invoice);
                    }
                }

                // Áp dụng paging với điều kiện warehouse
                int totalInvoices = filteredInvoices.size();
                List<Invoices> pageInvoices = new ArrayList<>();
                int totalPages;

                if ("K80".equals(warehouseSelect)) {
                    // K80: Hiển thị tất cả trên 1 trang
                    pageInvoices = filteredInvoices;
                    totalPages = 1;
                    currentPage = 1;
                } else {
                    // A4: Sử dụng paging bình thường
                    totalPages = (int) Math.ceil((double) totalInvoices / PAGE_SIZE);

                    // Validate currentPage
                    if (currentPage > totalPages && totalPages > 0) {
                        currentPage = totalPages;
                    }

                    // Lấy invoices cho trang hiện tại
                    int startIndex = (currentPage - 1) * PAGE_SIZE;
                    int endIndex = Math.min(startIndex + PAGE_SIZE, filteredInvoices.size());

                    if (startIndex < filteredInvoices.size()) {
                        pageInvoices = filteredInvoices.subList(startIndex, endIndex);
                    }
                }
                System.out.println("Page invoices: " + pageInvoices.size());

                // Set vào request
                request.setAttribute("listUsers", listUsers);
                request.setAttribute("listInvoice", pageInvoices);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("totalInvoices", totalInvoices);
                request.setAttribute("pageSize", PAGE_SIZE);
                request.setAttribute("selectedDate", date);
                request.setAttribute("selectedEmployeeId", selectedEmployeeId);
                request.setAttribute("selectedCreatorId", selectedCreatorId);
                request.setAttribute("selectedWarehouse", warehouseSelect);
                // value select
                request.setAttribute("selectedEmployee", employeeIdStr);
                request.setAttribute("selectedCreator", creatorIdStr);

                request.getRequestDispatcher(END_OF_DAY_REPORT).forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();

                // Trường hợp lỗi
                try {
                    UserDAO userDAO = new UserDAO();
                    List<Users> listUsers = userDAO.getData();
                    request.setAttribute("listUsers", listUsers);
                } catch (Exception userEx) {
                    System.out.println("Error loading users: " + userEx.getMessage());
                }

                request.setAttribute("error", "Có lỗi xảy ra khi tải dữ liệu báo cáo: " + e.getMessage());
                request.setAttribute("listInvoice", new ArrayList<>());
                request.setAttribute("selectedDate", date);
                request.setAttribute("selectedEmployee", "");
                request.setAttribute("selectedCreator", "");
                request.getRequestDispatcher(END_OF_DAY_REPORT).forward(request, response);
            }
        }
    }

    /**
     * Export Excel using Apache POI
     */
    private void exportToExcelPOI(HttpServletRequest request, HttpServletResponse response,
            String date, Integer selectedEmployeeId, Integer selectedCreatorId, String warehouseSelect)
            throws IOException {

        Workbook workbook = null;
        try {
            // Get data
            List<Invoices> invoices = getFilteredInvoices(date, selectedEmployeeId, selectedCreatorId);

            // Set response headers
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"BaoCaoCuoiNgay_" + date.replace("-", "") + ".xlsx\"");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // Create workbook
            workbook = new XSSFWorkbook();

            if ("K80".equals(warehouseSelect)) {
                createSummarySheet(workbook, invoices, date);
            } else {
                createDetailSheet(workbook, invoices, date);
            }

            // Write to response
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.reset();
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("Lỗi xuất Excel: " + e.getMessage());
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    System.err.println("Error closing workbook: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Create Summary Sheet for K80
     */
    private void createSummarySheet(Workbook workbook, List<Invoices> invoices, String date) {
        Sheet sheet = workbook.createSheet("Báo cáo tổng hợp");

        // Create styles
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);

        int rowNum = 0;

        // Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO CUỐI NGÀY TỔNG HỢP");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 3));

        // Date
        Row dateRow = sheet.createRow(rowNum++);
        dateRow.createCell(0).setCellValue("Ngày: " + formatDateVN(date));

        // Empty row
        rowNum++;

        // Calculate totals
        double totalIncome = invoices.stream().mapToDouble(Invoices::getTotalAmount).sum();
        double totalCash = invoices.stream()
                .filter(inv -> inv.getPaymentMethodId() == 1)
                .mapToDouble(Invoices::getTotalAmount).sum();
        double totalBank = invoices.stream()
                .filter(inv -> inv.getPaymentMethodId() == 2)
                .mapToDouble(Invoices::getTotalAmount).sum();
        int totalQuantity = invoices.stream().mapToInt(inv -> inv.getQuantityById()).sum();

        // Tổng kết thu chi
        Row sectionRow1 = sheet.createRow(rowNum++);
        Cell sectionCell1 = sectionRow1.createCell(0);
        sectionCell1.setCellValue("TỔNG KẾT THU CHI");
        sectionCell1.setCellStyle(headerStyle);

        addSummaryRow(sheet, rowNum++, "Tổng thu", totalIncome, currencyStyle);
        addSummaryRow(sheet, rowNum++, "Tổng chi", 0, currencyStyle);
        addSummaryRow(sheet, rowNum++, "Thu - chi", totalIncome, currencyStyle);

        rowNum++; // Empty row

        // Phương thức thanh toán
        Row sectionRow2 = sheet.createRow(rowNum++);
        Cell sectionCell2 = sectionRow2.createCell(0);
        sectionCell2.setCellValue("PHƯƠNG THỨC THANH TOÁN");
        sectionCell2.setCellStyle(headerStyle);

        addSummaryRow(sheet, rowNum++, "Tiền mặt", totalCash, currencyStyle);
        addSummaryRow(sheet, rowNum++, "Chuyển khoản", totalBank, currencyStyle);
        addSummaryRow(sheet, rowNum++, "Voucher", 0, currencyStyle);

        rowNum++; // Empty row

        // Tổng kết bán hàng
        Row sectionRow3 = sheet.createRow(rowNum++);
        Cell sectionCell3 = sectionRow3.createCell(0);
        sectionCell3.setCellValue("TỔNG KẾT BÁN HÀNG");
        sectionCell3.setCellStyle(headerStyle);

        addSummaryRow(sheet, rowNum++, "Hóa đơn", invoices.size(), null);
        addSummaryRow(sheet, rowNum++, "Giá trị", totalIncome, currencyStyle);
        addSummaryRow(sheet, rowNum++, "SL sản phẩm", totalQuantity, null);

        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Create Detail Sheet for A4
     */
    private void createDetailSheet(Workbook workbook, List<Invoices> invoices, String date) {
        Sheet sheet = workbook.createSheet("Báo cáo chi tiết");

        // Create styles
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);

        int rowNum = 0;

        // Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO CUỐI NGÀY VỀ BÁN HÀNG");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 7));

        // Date
        Row dateRow = sheet.createRow(rowNum++);
        dateRow.createCell(0).setCellValue("Ngày: " + formatDateVN(date));

        // Empty row
        rowNum++;

        // Headers
        Row headerRow = sheet.createRow(rowNum++);
        String[] headers = {"Mã giao dịch", "Thời gian", "SL", "Doanh thu", "VAT", "Làm tròn", "Phí trả hàng", "Thực thu"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data rows
        for (Invoices invoice : invoices) {
            Row dataRow = sheet.createRow(rowNum++);

            dataRow.createCell(0).setCellValue("Hóa đơn: " + invoice.getInvoiceId());
            dataRow.createCell(1).setCellValue(formatTime(invoice.getInvoiceDate()));
            dataRow.createCell(2).setCellValue(invoice.getQuantityById());

            Cell revenueCell = dataRow.createCell(3);
            revenueCell.setCellValue(invoice.getSubTotal());
            revenueCell.setCellStyle(currencyStyle);

            Cell vatCell = dataRow.createCell(4);
            vatCell.setCellValue(invoice.getVATAmount());
            vatCell.setCellStyle(currencyStyle);

            dataRow.createCell(5).setCellValue(0); // Làm tròn
            dataRow.createCell(6).setCellValue(0); // Phí trả hàng

            Cell totalCell = dataRow.createCell(7);
            totalCell.setCellValue(invoice.getTotalAmount());
            totalCell.setCellStyle(currencyStyle);
        }

        // Summary row
        Row summaryRow = sheet.createRow(rowNum++);
        summaryRow.createCell(0).setCellValue("TỔNG CỘNG");

        int totalQuantity = invoices.stream().mapToInt(inv -> inv.getQuantityById()).sum();
        double totalAmount = invoices.stream().mapToDouble(Invoices::getTotalAmount).sum();

        summaryRow.createCell(2).setCellValue(totalQuantity);

        Cell totalAmountCell = summaryRow.createCell(7);
        totalAmountCell.setCellValue(totalAmount);
        totalAmountCell.setCellStyle(currencyStyle);

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Helper methods for styling
     */
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0"));
        return style;
    }

    private void addSummaryRow(Sheet sheet, int rowNum, String label, double value, CellStyle currencyStyle) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(label);

        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        if (currencyStyle != null) {
            valueCell.setCellStyle(currencyStyle);
        }
    }

    private void addSummaryRow(Sheet sheet, int rowNum, String label, int value, CellStyle currencyStyle) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(label);
        row.createCell(1).setCellValue(value);
    }

    /**
     * Lấy danh sách invoice đã lọc
     */
    private List<Invoices> getFilteredInvoices(String date, Integer selectedEmployeeId, Integer selectedCreatorId) {
        try {
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            List<Invoices> allInvoices = invoiceDAO.getInvoicesByDate(date);
            List<Invoices> filteredInvoices = new ArrayList<>();

            for (Invoices invoice : allInvoices) {
                boolean shouldInclude = true;

                if (selectedEmployeeId != null && invoice.getUserId() != selectedEmployeeId) {
                    shouldInclude = false;
                }

                if (selectedCreatorId != null && invoice.getCreatedBy() != selectedCreatorId) {
                    shouldInclude = false;
                }

                if (shouldInclude) {
                    filteredInvoices.add(invoice);
                }
            }

            return filteredInvoices;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Helper methods cho formatting
     */
    private String formatDateVN(String date) {
        try {
            java.time.LocalDate localDate = java.time.LocalDate.parse(date);
            return localDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return date;
        }
    }

    private String formatDateTime(java.util.Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(date);
    }

    private String formatTime(java.util.Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    private String formatNumber(double number) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
        return df.format(number);
    }
}