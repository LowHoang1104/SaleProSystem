///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//package salepro.controller.management.cashier.report;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import javax.swing.text.Document;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.wp.usermodel.Paragraph;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import salepro.dao.InvoiceDAO;
//import salepro.models.Invoices;
//
///**
// *
// * @author MY PC
// */
//@WebServlet(name="ExportReportServlet", urlPatterns={"/ExportReportServlet"})
//public class ExportReportServlet extends HttpServlet {
//   
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        String exportType = request.getParameter("exportType");
//        String date = request.getParameter("date");
//        String employeeIdStr = request.getParameter("employeeId");
//        String creatorIdStr = request.getParameter("creatorId");
//        String warehouseSelect = request.getParameter("warehouseSelect");
//        
//        // Lấy dữ liệu từ database (tương tự ReportEndDayServlet)
//        List<Invoices> invoiceData = getReportData(date, employeeIdStr, creatorIdStr, warehouseSelect);
//        
//        try {
//            switch (exportType) {
//                case "excel":
//                    exportToExcel(response, invoiceData, date, warehouseSelect);
//                    break;
//                case "pdf":
//                    exportToPDF(response, invoiceData, date, warehouseSelect);
//                    break;
//                case "word":
//                    exportToWord(response, invoiceData, date, warehouseSelect);
//                    break;
//                default:
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid export type");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Export failed: " + e.getMessage());
//        }
//    }
//    
//    private List<Invoices> getReportData(String date, String employeeIdStr, String creatorIdStr, String warehouseSelect) {
//        try {
//            InvoiceDAO invoiceDAO = new InvoiceDAO();
//            List<Invoices> allInvoices = invoiceDAO.getInvoicesByDate(date);
//            List<Invoices> filteredInvoices = new ArrayList<>();
//            
//            Integer selectedEmployeeId = null;
//            Integer selectedCreatorId = null;
//            
//            if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
//                selectedEmployeeId = Integer.parseInt(employeeIdStr);
//            }
//            
//            if (creatorIdStr != null && !creatorIdStr.isEmpty()) {
//                selectedCreatorId = Integer.parseInt(creatorIdStr);
//            }
//            
//            for (Invoices invoice : allInvoices) {
//                boolean shouldInclude = true;
//                
//                if (selectedEmployeeId != null && invoice.getUserId() != selectedEmployeeId) {
//                    shouldInclude = false;
//                }
//                
//                if (selectedCreatorId != null && invoice.getCreatedBy() != selectedCreatorId) {
//                    shouldInclude = false;
//                }
//                
//                if (shouldInclude) {
//                    filteredInvoices.add(invoice);
//                }
//            }
//            
//            return filteredInvoices;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//    
//    private void exportToExcel(HttpServletResponse response, List<Invoices> invoices, String date, String warehouse) throws IOException {
//        // Tạo workbook
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Báo cáo cuối ngày");
//        
//        // Tạo header style
//        CellStyle headerStyle = workbook.createCellStyle();
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 12);
//        headerStyle.setFont(headerFont);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
//        
//        // Tạo title
//        Row titleRow = sheet.createRow(0);
//        Cell titleCell = titleRow.createCell(0);
//        titleCell.setCellValue("K80".equals(warehouse) ? "Báo cáo cuối ngày tổng hợp" : "Báo cáo cuối ngày về bán hàng");
//        titleCell.setCellStyle(headerStyle);
//        
//        // Merge cells cho title
//        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 8));
//        
//        // Thông tin báo cáo
//        Row infoRow = sheet.createRow(2);
//        infoRow.createCell(0).setCellValue("Ngày báo cáo: " + date);
//        infoRow.createCell(4).setCellValue("Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
//        
//        if ("K80".equals(warehouse)) {
//            // Export dạng tổng hợp cho K80
//            exportSummaryToExcel(sheet, invoices, 4);
//        } else {
//            // Export dạng chi tiết cho A4
//            exportDetailToExcel(sheet, invoices, 4);
//        }
//        
//        // Set response headers
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=bao_cao_cuoi_ngay_" + date + ".xlsx");
//        
//        // Write workbook to response
//        try (OutputStream outputStream = response.getOutputStream()) {
//            workbook.write(outputStream);
//        }
//        
//        workbook.close();
//    }
//    
//    private void exportSummaryToExcel(Sheet sheet, List<Invoices> invoices, int startRow) {
//        // Tính toán tổng hợp
//        double totalIncome = invoices.stream().mapToDouble(Invoices::getTotalAmount).sum();
//        double totalCash = invoices.stream().filter(i -> i.getPaymentMethodID() == 1).mapToDouble(Invoices::getTotalAmount).sum();
//        double totalBank = invoices.stream().filter(i -> i.getPaymentMethodID() == 2).mapToDouble(Invoices::getTotalAmount).sum();
//        
//        int currentRow = startRow;
//        
//        // Tổng kết thu chi
//        sheet.createRow(currentRow++).createCell(0).setCellValue("TỔNG KẾT THU CHI");
//        sheet.createRow(currentRow).createCell(0).setCellValue("Tổng thu");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(totalIncome);
//        sheet.createRow(currentRow).createCell(0).setCellValue("Tổng chi");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(-totalIncome);
//        sheet.createRow(currentRow).createCell(0).setCellValue("Thu - chi");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(0);
//        
//        currentRow++;
//        
//        // Phương thức thanh toán
//        sheet.createRow(currentRow++).createCell(0).setCellValue("PHƯƠNG THỨC THANH TOÁN");
//        sheet.createRow(currentRow).createCell(0).setCellValue("Tiền mặt");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(totalCash);
//        sheet.createRow(currentRow).createCell(0).setCellValue("Chuyển khoản");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(totalBank);
//        
//        currentRow++;
//        
//        // Tổng kết bán hàng
//        sheet.createRow(currentRow++).createCell(0).setCellValue("TỔNG KẾT BÁN HÀNG");
//        sheet.createRow(currentRow).createCell(0).setCellValue("Số hóa đơn");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(invoices.size());
//        sheet.createRow(currentRow).createCell(0).setCellValue("Tổng giá trị");
//        sheet.getRow(currentRow++).createCell(2).setCellValue(totalIncome);
//    }
//    
//    private void exportDetailToExcel(Sheet sheet, List<Invoices> invoices, int startRow) {
//        // Tạo header row
//        Row headerRow = sheet.createRow(startRow);
//        String[] headers = {"Mã giao dịch", "Thời gian", "SL", "Doanh thu", "VAT", "Làm tròn", "Phí trả hàng", "Thực thu"};
//        
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//        
//        // Tạo data rows
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//        for (int i = 0; i < invoices.size(); i++) {
//            Invoices invoice = invoices.get(i);
//            Row row = sheet.createRow(startRow + 1 + i);
//            
//            row.createCell(0).setCellValue("Hóa đơn: " + invoice.getInvoiceId());
//            row.createCell(1).setCellValue(timeFormat.format(invoice.getInvoiceDate()));
//            row.createCell(2).setCellValue(invoice.getQuantityById()); // Cần implement method này
//            row.createCell(3).setCellValue(invoice.getSubTotal());
//            row.createCell(4).setCellValue(invoice.getVATAmount());
//            row.createCell(5).setCellValue(0);
//            row.createCell(6).setCellValue(0);
//            row.createCell(7).setCellValue(invoice.getTotalAmount());
//        }
//        
//        // Auto-size columns
//        for (int i = 0; i < headers.length; i++) {
//            sheet.autoSizeColumn(i);
//        }
//    }
//    
//    private void exportToPDF(HttpServletResponse response, List<Invoices> invoices, String date, String warehouse) throws Exception {
//        // Set response headers
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=bao_cao_cuoi_ngay_" + date + ".pdf");
//        
//        // Tạo PDF document
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, response.getOutputStream());
//        
//        document.open();
//        
//        // Add title
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
//        Paragraph title = new Paragraph("K80".equals(warehouse) ? "Báo cáo cuối ngày tổng hợp" : "Báo cáo cuối ngày về bán hàng", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        
//        // Add date info
//        document.add(new Paragraph("Ngày báo cáo: " + date));
//        document.add(new Paragraph("Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())));
//        document.add(new Paragraph(" ")); // Empty line
//        
//        if ("K80".equals(warehouse)) {
//            addSummaryToPDF(document, invoices);
//        } else {
//            addDetailToPDF(document, invoices);
//        }
//        
//        document.close();
//    }
//    
//    private void addSummaryToPDF(Document document, List<Invoices> invoices) throws Exception {
//        double totalIncome = invoices.stream().mapToDouble(Invoices::getTotalAmount).sum();
//        double totalCash = invoices.stream().filter(i -> i.getPaymentMethodID() == 1).mapToDouble(Invoices::getTotalAmount).sum();
//        double totalBank = invoices.stream().filter(i -> i.getPaymentMethodID() == 2).mapToDouble(Invoices::getTotalAmount).sum();
//        
//        // Tổng kết thu chi
//        document.add(new Paragraph("TỔNG KẾT THU CHI", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
//        document.add(new Paragraph("Tổng thu: " + String.format("%,.0f", totalIncome)));
//        document.add(new Paragraph("Tổng chi: " + String.format("%,.0f", -totalIncome)));
//        document.add(new Paragraph("Thu - chi: 0"));
//        document.add(new Paragraph(" "));
//        
//        // Phương thức thanh toán
//        document.add(new Paragraph("PHƯƠNG THỨC THANH TOÁN", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
//        document.add(new Paragraph("Tiền mặt: " + String.format("%,.0f", totalCash)));
//        document.add(new Paragraph("Chuyển khoản: " + String.format("%,.0f", totalBank)));
//        document.add(new Paragraph(" "));
//        
//        // Tổng kết bán hàng
//        document.add(new Paragraph("TỔNG KẾT BÁN HÀNG", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
//        document.add(new Paragraph("Số hóa đơn: " + invoices.size()));
//        document.add(new Paragraph("Tổng giá trị: " + String.format("%,.0f", totalIncome)));
//    }
//    
//    private void addDetailToPDF(Document document, List<Invoices> invoices) throws Exception {
//        // Tạo table
//        PdfPTable table = new PdfPTable(8);
//        table.setWidthPercentage(100);
//        
//        // Add headers
//        String[] headers = {"Mã giao dịch", "Thời gian", "SL", "Doanh thu", "VAT", "Làm tròn", "Phí trả hàng", "Thực thu"};
//        for (String header : headers) {
//            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.addCell(cell);
//        }
//        
//        // Add data
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//        for (Invoices invoice : invoices) {
//            table.addCell(new PdfPCell(new Phrase("HD: " + invoice.getInvoiceId(), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase(timeFormat.format(invoice.getInvoiceDate()), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase(String.valueOf(invoice.getQuantityById()), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase(String.format("%,.0f", invoice.getSubTotal()), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase(String.format("%,.0f", invoice.getVATAmount()), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase("0", FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase("0", FontFactory.getFont(FontFactory.HELVETICA, 7))));
//            table.addCell(new PdfPCell(new Phrase(String.format("%,.0f", invoice.getTotalAmount()), FontFactory.getFont(FontFactory.HELVETICA, 7))));
//        }
//        
//        document.add(table);
//    }
//    
//    private void exportToWord(HttpServletResponse response, List<Invoices> invoices, String date, String warehouse) throws IOException {
//        // Tạm thời sử dụng plain text format cho Word
//        response.setContentType("application/msword");
//        response.setHeader("Content-Disposition", "attachment; filename=bao_cao_cuoi_ngay_" + date + ".doc");
//        
//        StringBuilder content = new StringBuilder();
//        content.append("Báo cáo cuối ngày về bán hàng\n");
//        content.append("Ngày báo cáo: ").append(date).append("\n");
//        content.append("Ngày xuất: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n\n");
//        
//        if ("K80".equals(warehouse)) {
//            // Summary format
//            double totalIncome = invoices.stream().mapToDouble(Invoices::getTotalAmount).sum();
//            content.append("TỔNG KẾT THU CHI\n");
//            content.append("Tổng thu: ").append(String.format("%,.0f", totalIncome)).append("\n");
//            content.append("Tổng chi: ").append(String.format("%,.0f", -totalIncome)).append("\n");
//            content.append("Thu - chi: 0\n\n");
//            
//            content.append("TỔNG KẾT BÁN HÀNG\n");
//            content.append("Số hóa đơn: ").append(invoices.size()).append("\n");
//            content.append("Tổng giá trị: ").append(String.format("%,.0f", totalIncome)).append("\n");
//        } else {
//            // Detail format
//            content.append("Mã giao dịch\tThời gian\tSL\tDoanh thu\tVAT\tLàm tròn\tPhí trả hàng\tThực thu\n");
//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//            for (Invoices invoice : invoices) {
//                content.append("HD: ").append(invoice.getInvoiceId()).append("\t");
//                content.append(timeFormat.format(invoice.getInvoiceDate())).append("\t");
//                content.append(invoice.getQuantityById()).append("\t");
//                content.append(String.format("%,.0f", invoice.getSubTotal())).append("\t");
//                content.append(String.format("%,.0f", invoice.getVATAmount())).append("\t");
//                content.append("0\t0\t");
//                content.append(String.format("%,.0f", invoice.getTotalAmount())).append("\n");
//            }
//        }
//        
//        response.getWriter().write(content.toString());
//    }
//}
