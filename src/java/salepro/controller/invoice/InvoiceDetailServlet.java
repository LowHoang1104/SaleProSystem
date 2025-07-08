/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.invoice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.InvoiceDetailDAO;
import salepro.dao.UserDAO;
import salepro.models.Customers;
import salepro.models.InvoiceDetails;
import salepro.models.Invoices;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "InvoiceDetailServlet", urlPatterns = {"/InvoiceDetailServlet"})
public class InvoiceDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String invoiceIdStr = request.getParameter("invoiceId");

        if (action == null || invoiceIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);

            switch (action) {
                case "getDetail":
                    getInvoiceDetail(request, response, invoiceId);
                    break;
                case "exportSingle":
                    exportSingleInvoice(request, response, invoiceId);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid invoice ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String invoiceIdStr = request.getParameter("invoiceId");

        if (action == null || invoiceIdStr == null) {
            sendJsonResponse(response, false, "Missing parameters");
            return;
        }

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);

            switch (action) {
                case "updateSoldBy":
                    updateSoldBy(request, response, invoiceId);
                    break;
                default:
                    sendJsonResponse(response, false, "Invalid action");
                    break;
            }

        } catch (NumberFormatException e) {
            sendJsonResponse(response, false, "Invalid invoice ID");
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Server error occurred");
        }
    }

    /**
     * Update sold by user for an invoice
     */
    private void updateSoldBy(HttpServletRequest request, HttpServletResponse response, int invoiceId)
            throws IOException {

        String soldByIdStr = request.getParameter("soldById");

        if (soldByIdStr == null || soldByIdStr.trim().isEmpty()) {
            sendJsonResponse(response, false, "Sold by ID is required");
            return;
        }

        try {
            int soldById = Integer.parseInt(soldByIdStr);

            // Validate that the user exists
            Users user = userDAO.getUserById(soldById);
            if (user == null) {
                sendJsonResponse(response, false, "User not found");
                return;
            }

            // Validate that the invoice exists
            Invoices invoice = invoiceDAO.getInvoiceById(invoiceId);
            if (invoice == null) {
                sendJsonResponse(response, false, "Invoice not found");
                return;
            }

            // Update the invoice
            boolean success = invoiceDAO.updateSoldBy(invoiceId, soldById);

            if (success) {
                sendJsonResponse(response, true, "Cập nhật người bán thành công");
            } else {
                sendJsonResponse(response, false, "Không thể cập nhật người bán");
            }

        } catch (NumberFormatException e) {
            sendJsonResponse(response, false, "Invalid sold by ID");
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Error updating sold by: " + e.getMessage());
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.success = success;
        updateResponse.message = message;

        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(updateResponse));
        out.flush();
    }

// Response class for updates
    private static class UpdateResponse {

        public boolean success;
        public String message;
    }

    /**
     * Get invoice detail and return as JSON
     */
    private void getInvoiceDetail(HttpServletRequest request, HttpServletResponse response, int invoiceId)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Get invoice basic info
            Invoices invoice = invoiceDAO.getInvoiceById(invoiceId);
            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
                return;
            }

            // Create response object with fields needed by modal
            InvoiceDetailResponse detailResponse = new InvoiceDetailResponse();

            // Basic invoice info
            detailResponse.invoiceId = invoice.getInvoiceId();
            detailResponse.invoiceCode = invoice.getInvoiceCode();
            detailResponse.invoiceDate = invoice.getInvoiceDate();
            detailResponse.updateDate = invoice.getUpdateDate();
            detailResponse.status = invoice.getStatus();
            detailResponse.totalAmount = invoice.getTotalAmount();
            detailResponse.VATAmount = invoice.getVATAmount();
            detailResponse.subTotal = invoice.getSubTotal();

            detailResponse.listUser = userDAO.getData();

            // Customer info
            detailResponse.customerName = invoice.getCustomer().getFullName();

            // User names as strings
            detailResponse.createdBy = invoice.getCreateUsers().getFullName();
            detailResponse.soldBy = invoice.getSaleUsers().getFullName();

            // For dropdown - keep user IDs
            detailResponse.createdById = invoice.getCreatedBy();
            detailResponse.soldById = invoice.getUserId();

            detailResponse.invoiceDetails = new java.util.ArrayList<>();
            try {
                List<InvoiceDetails> invoiceDetails = invoiceDetailDAO.getInvoiceDetailByID(invoiceId);
                if (invoiceDetails != null && !invoiceDetails.isEmpty()) {
                    detailResponse.invoiceDetails = invoiceDetails;
                    detailResponse.totalItems = invoiceDetails.size();
                } else {
                    detailResponse.totalItems = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                detailResponse.totalItems = 0;
            }

            // Static values for now (can be made dynamic later)
            detailResponse.discount = invoice.getDiscount();
            detailResponse.discountAmount = invoice.getDiscountAmount();
            detailResponse.paidAmount = invoice.getPaidAmount();
            detailResponse.branch = invoice.getStoreNameByID();
            detailResponse.salesChannel = "Bán trực tiếp";
            detailResponse.priceList = "Bảng giá chung";
            detailResponse.paymentMethod = invoice.getPaymentMethodNameByID();

            // Convert to JSON and send response
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd/MM/yyyy HH:mm")
                    .create();

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(detailResponse));
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving invoice details");
        }
    }
    private void exportSingleInvoice(HttpServletRequest request, HttpServletResponse response, int invoiceId)
            throws IOException {

        try {
            // Get invoice detail
            Invoices invoice = invoiceDAO.getInvoiceById(invoiceId);
            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
                return;
            }

            // Get invoice details
            List<InvoiceDetails> invoiceDetails = invoiceDetailDAO.getInvoiceDetailByID(invoiceId);

            // Create Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Invoice_" + invoice.getInvoiceCode());

            // Create header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            // Create data style
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);

            // Create number style
            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.cloneStyleFrom(dataStyle);
            numberStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

            int rowNum = 0;

            // Invoice header information
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("CHI TIẾT HÓA ĐƠN");
            titleCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6));

            rowNum++; // Empty row

            // Invoice info
            Row infoRow1 = sheet.createRow(rowNum++);
            infoRow1.createCell(0).setCellValue("Mã hóa đơn:");
            infoRow1.createCell(1).setCellValue(invoice.getInvoiceCode());
            infoRow1.createCell(3).setCellValue("Ngày tạo:");
            infoRow1.createCell(4).setCellValue(invoice.getInvoiceDate().toString());

            Row infoRow2 = sheet.createRow(rowNum++);
            infoRow2.createCell(0).setCellValue("Khách hàng:");
            infoRow2.createCell(1).setCellValue(invoice.getCustomer() != null ? invoice.getCustomer().getFullName() : "Khách lẻ");
            infoRow2.createCell(3).setCellValue("Người bán:");
            infoRow2.createCell(4).setCellValue(invoice.getSaleUsers() != null ? invoice.getSaleUsers().getFullName() : "N/A");

            rowNum++; // Empty row

            // Product table header
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng",
                "Đơn giá", "Giảm giá", "Giá bán", "Thành tiền"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Product data
            int stt = 1;
            double totalAmount = 0;

            if (invoiceDetails != null && !invoiceDetails.isEmpty()) {
                for (InvoiceDetails detail : invoiceDetails) {
                    Row dataRow = sheet.createRow(rowNum++);

                    dataRow.createCell(0).setCellValue(stt++);
                    dataRow.createCell(1).setCellValue(detail.getProductCode() != null ? detail.getProductCode() : "");
                    dataRow.createCell(2).setCellValue(detail.getProductName()!= null ? detail.getProductName() : "");
                    dataRow.createCell(3).setCellValue(detail.getQuantity());
                    dataRow.createCell(4).setCellValue(detail.getUnitPrice());
                    dataRow.createCell(5).setCellValue(0); // Discount
                    dataRow.createCell(6).setCellValue(detail.getUnitPrice());

                    double lineTotal = detail.getQuantity() * detail.getUnitPrice();
                    dataRow.createCell(7).setCellValue(lineTotal);
                    totalAmount += lineTotal;

                    // Apply styles
                    for (int i = 0; i < 8; i++) {
                        Cell cell = dataRow.getCell(i);
                        if (cell != null) {
                            if (i >= 3 && i <= 7) { // Number columns
                                cell.setCellStyle(numberStyle);
                            } else {
                                cell.setCellStyle(dataStyle);
                            }
                        }
                    }
                }
            }

            rowNum++; // Empty row

            // Summary
            Row summaryRow1 = sheet.createRow(rowNum++);
            summaryRow1.createCell(5).setCellValue("Tổng tiền hàng:");
            Cell totalCell = summaryRow1.createCell(6);
            totalCell.setCellValue(invoice.getSubTotal());
            totalCell.setCellStyle(numberStyle);

            Row summaryRow2 = sheet.createRow(rowNum++);
            summaryRow2.createCell(5).setCellValue("Giảm giá:");
            Cell discountCell = summaryRow2.createCell(6);
            discountCell.setCellValue(invoice.getDiscountAmount());
            discountCell.setCellStyle(numberStyle);

            Row summaryRow3 = sheet.createRow(rowNum++);
            summaryRow3.createCell(5).setCellValue("VAT:");
            Cell vatCell = summaryRow3.createCell(6);
            vatCell.setCellValue(invoice.getVATAmount());
            vatCell.setCellStyle(numberStyle);

            Row summaryRow4 = sheet.createRow(rowNum++);
            summaryRow4.createCell(5).setCellValue("Tổng thanh toán:");
            Cell finalCell = summaryRow4.createCell(6);
            finalCell.setCellValue(invoice.getTotalAmount());
            finalCell.setCellStyle(numberStyle);

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Set response headers
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition",
                    "attachment; filename=Invoice_" + invoice.getInvoiceCode() + "_"
                    + new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".xlsx");

            // Write to response
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error exporting invoice: " + e.getMessage());
        }
    }

    // Response class matching modal requirements
    private static class InvoiceDetailResponse {

        public int invoiceId;
        public String invoiceCode;
        public java.util.Date invoiceDate;
        public java.util.Date updateDate;
        public String status;
        public Double totalAmount;
        public Double VATAmount;
        public Double subTotal;
        public Double discount;
        public Double discountAmount;
        public Double paidAmount;
        public String customerName;
        public String createdBy;
        public String soldBy;
        public Integer createdById;
        public Integer soldById;
        public String branch;
        public String salesChannel;
        public String priceList;
        public String paymentMethod;
        public Integer totalItems;
        public List<InvoiceDetails> invoiceDetails;
        public List<Users> listUser;
    }
}
