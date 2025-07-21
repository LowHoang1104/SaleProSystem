/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ExcelExport;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PaymentMethodDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "ImportExportServlet", urlPatterns = {"/ImportExportServlet"})
@MultipartConfig(maxFileSize = 10485760) // 10MB limit
public class ImportExportServlet extends HttpServlet {

    private static final String SESSION_LIST_INVOICE = "listInvoice";
    
    // Constants cho POI 3.x
    private static final int CELL_TYPE_NUMERIC = 0;
    private static final int CELL_TYPE_STRING = 1;
    private static final int CELL_TYPE_FORMULA = 2;
    private static final int CELL_TYPE_BLANK = 3;
    private static final int CELL_TYPE_BOOLEAN = 4;
    private static final int CELL_TYPE_ERROR = 5;

    // EXPORT FUNCTIONALITY
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("export".equals(action)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"invoices_export.xlsx\"");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Invoices");

                // Create header row
                Row headerRow = sheet.createRow(0);
                String[] headers = {
                    "InvoiceCode", "InvoiceDate", "UpdateDate", "Store", "Sales",
                    "CreatedBy", "Customer", "TotalAmount", "SubTotal", "DiscountPercent",
                    "DiscountAmount", "VATPercent", "VATAmount", "PaidAmount", "PaymentMethod", "Status"
                };

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                List<Invoices> invoices = getListFromSession(session);
                if (invoices == null) {
                    invoices = new ArrayList<>();
                }

                int rowNum = 1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (Invoices invoice : invoices) {
                    try {
                        Row row = sheet.createRow(rowNum++);

                        setCellValue(row, 0, invoice.getInvoiceCode());
                        setCellValue(row, 1, dateFormat.format(invoice.getInvoiceDate()));
                        setCellValue(row, 2, dateFormat.format(invoice.getUpdateDate()));
                        setCellValue(row, 3, invoice.getStores().getStoreName());
                        setCellValue(row, 4, invoice.getSaleUsers().getUserCode());
                        setCellValue(row, 5, invoice.getCreateUsers().getUserCode());
                        setCellValue(row, 6, invoice.getCustomer().getCustomerCode());

                        setCellNumericValue(row, 7, invoice.getTotalAmount());
                        setCellNumericValue(row, 8, invoice.getSubTotal());
                        setCellNumericValue(row, 9, invoice.getDiscount());
                        setCellNumericValue(row, 10, invoice.getDiscountAmount());
                        setCellNumericValue(row, 11, invoice.getVATPercent());
                        setCellNumericValue(row, 12, invoice.getVATAmount());
                        setCellNumericValue(row, 13, invoice.getPaidAmount());

                        setCellValue(row, 14, invoice.getPaymentMethodNameByID());
                        setCellValue(row, 15, invoice.getStatus());

                    } catch (Exception e) {
                        // Log error but continue with next invoice
                        System.err.println("Error processing invoice " + invoice.getInvoiceId() + ": " + e.getMessage());
                    }
                }

                // Auto-size columns
                for (int i = 0; i < headers.length; i++) {
                    try {
                        sheet.autoSizeColumn(i);
                    } catch (Exception e) {
                        // Skip if auto-size fails
                    }
                }

                // Write to response
                ServletOutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();

            } catch (Exception e) {
                response.reset();
                response.setContentType("text/plain");
                response.getWriter().write("Error exporting file: " + e.getMessage());
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (Exception e) {
                        // Log error
                    }
                }
            }
        }
    }

    // IMPORT FUNCTIONALITY
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("import".equals(action)) {
            Part filePart = request.getPart("excelFile");

            if (filePart == null || filePart.getSize() == 0) {
                response.getWriter().write("Vui lòng chọn file để import!");
                return;
            }

            String fileName = filePart.getSubmittedFileName();

            if (fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
                try (InputStream fileContent = filePart.getInputStream()) {

                    byte[] fileBytes = fileContent.readAllBytes();

                    try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes); 
                         Workbook workbook = new XSSFWorkbook(bais)) {

                        Sheet sheet = workbook.getSheetAt(0);
                        int successCount = 0;
                        int errorCount = 0;
                        List<String> errors = new ArrayList<>();

                        InvoiceDAO invoicesDAO = new InvoiceDAO();

                        // Validate headers
                        Row headerRow = sheet.getRow(0);
                        if (!validateExportHeaders(headerRow)) {
                            response.getWriter().write("File Excel không đúng định dạng export! Vui lòng sử dụng file được export từ hệ thống.");
                            return;
                        }

                        int processedRows = 0;

                        // Process data rows
                        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                            Row row = sheet.getRow(rowIndex);

                            if (row == null || isRowEmpty(row)) {
                                continue;
                            }

                            processedRows++;

                            try {
                                Invoices invoice = parseExportRowToInvoice(row);

                                if (validateInvoiceData(invoice)) {
                                    boolean insertResult = invoicesDAO.insertInvoice(invoice);
                                    if (insertResult) {
                                        successCount++;
                                    } else {
                                        errorCount++;
                                        errors.add("Dòng " + (rowIndex + 1) + ": Lỗi khi insert vào database");
                                    }
                                } else {
                                    errorCount++;
                                    errors.add("Dòng " + (rowIndex + 1) + ": Dữ liệu không hợp lệ");
                                }

                            } catch (Exception e) {
                                errorCount++;
                                errors.add("Dòng " + (rowIndex + 1) + ": " + e.getMessage());
                            }
                        }

                        // Create response message
                        StringBuilder responseMsg = new StringBuilder();
                        responseMsg.append("Import hoàn tất!\n");
                        responseMsg.append("Đã xử lý: ").append(processedRows).append(" dòng\n");
                        responseMsg.append("Thành công: ").append(successCount).append(" bản ghi\n");
                        if (errorCount > 0) {
                            responseMsg.append("Lỗi: ").append(errorCount).append(" bản ghi\n");
                            if (errors.size() <= 10) {
                                responseMsg.append("Chi tiết lỗi:\n");
                                for (String error : errors) {
                                    responseMsg.append("- ").append(error).append("\n");
                                }
                            } else {
                                responseMsg.append("Chi tiết lỗi (10 đầu tiên):\n");
                                for (int i = 0; i < 10; i++) {
                                    responseMsg.append("- ").append(errors.get(i)).append("\n");
                                }
                                responseMsg.append("... và ").append(errors.size() - 10).append(" lỗi khác");
                            }
                        }

                        response.getWriter().write(responseMsg.toString());

                    } catch (Exception e) {
                        response.getWriter().write("Lỗi khi đọc file Excel: " + e.getMessage());
                    }
                } catch (Exception e) {
                    response.getWriter().write("Lỗi khi đọc file: " + e.getMessage());
                }
            } else {
                response.getWriter().write("Vui lòng chọn file Excel hợp lệ (.xls hoặc .xlsx)!");
            }
        }
    }

    // HELPER METHODS

    // Export helper methods
    private void setCellValue(Row row, int cellIndex, String value) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value != null ? value : "");
    }

    private void setCellNumericValue(Row row, int cellIndex, Number value) {
        Cell cell = row.createCell(cellIndex);
        if (value != null) {
            cell.setCellValue(value.doubleValue());
        } else {
            cell.setCellValue(0.0);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Invoices> getListFromSession(HttpSession session) {
        List<Invoices> sessionList = (List<Invoices>) session.getAttribute(SESSION_LIST_INVOICE);
        return sessionList != null ? sessionList : new ArrayList<>();
    }

    // Import helper methods
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CELL_TYPE_BLANK) {
                String cellValue = getCellValueAsString(cell);
                if (cellValue != null && !cellValue.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        int cellType = cell.getCellType();

        switch (cellType) {
            case CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();

            case CELL_TYPE_NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    } else {
                        return String.valueOf(numValue);
                    }
                }

            case CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case CELL_TYPE_FORMULA:
                try {
                    int cachedType = cell.getCachedFormulaResultType();
                    switch (cachedType) {
                        case CELL_TYPE_STRING:
                            return cell.getStringCellValue().trim();
                        case CELL_TYPE_NUMERIC:
                            double numValue = cell.getNumericCellValue();
                            if (numValue == (long) numValue) {
                                return String.valueOf((long) numValue);
                            } else {
                                return String.valueOf(numValue);
                            }
                        case CELL_TYPE_BOOLEAN:
                            return String.valueOf(cell.getBooleanCellValue());
                        default:
                            return "";
                    }
                } catch (Exception e) {
                    return "";
                }

            case CELL_TYPE_BLANK:
            default:
                return "";
        }
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0;
        }

        int cellType = cell.getCellType();

        switch (cellType) {
            case CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();

            case CELL_TYPE_STRING:
                try {
                    String stringValue = cell.getStringCellValue().trim();
                    if (stringValue.isEmpty()) {
                        return 0.0;
                    }
                    return Double.parseDouble(stringValue);
                } catch (NumberFormatException e) {
                    return 0.0;
                }

            case CELL_TYPE_FORMULA:
                try {
                    return cell.getNumericCellValue();
                } catch (Exception e) {
                    return 0.0;
                }

            case CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? 1.0 : 0.0;

            default:
                return 0.0;
        }
    }

    private Invoices parseExportRowToInvoice(Row row) throws Exception {
        Invoices invoice = new Invoices();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // Column 0: InvoiceCode - Skip setting as it's computed column
            String invoiceCode = getCellValueAsString(row.getCell(0));

            // Column 1: InvoiceDate
            String invoiceDateStr = getCellValueAsString(row.getCell(1));
            if (!invoiceDateStr.isEmpty()) {
                try {
                    invoice.setInvoiceDate(dateFormat.parse(invoiceDateStr));
                } catch (Exception e) {
                    invoice.setInvoiceDate(new java.util.Date());
                }
            } else {
                invoice.setInvoiceDate(new java.util.Date());
            }

            // Column 2: UpdateDate  
            String updateDateStr = getCellValueAsString(row.getCell(2));
            if (!updateDateStr.isEmpty()) {
                try {
                    invoice.setUpdateDate(dateFormat.parse(updateDateStr));
                } catch (Exception e) {
                    invoice.setUpdateDate(new java.util.Date());
                }
            } else {
                invoice.setUpdateDate(new java.util.Date());
            }

            // Column 3: Store
            String storeName = getCellValueAsString(row.getCell(3));
            if (!storeName.isEmpty()) {
                int storeId = convertStoreNameToId(storeName);
                if (storeId > 0) {
                    invoice.setStoreId(storeId);
                } else {
                    throw new Exception("Không tìm thấy Store: " + storeName);
                }
            } else {
                throw new Exception("Store không được để trống");
            }

            // Column 4: Sales User (SaleID trong DB)
            String salesUserCode = getCellValueAsString(row.getCell(4));
            if (!salesUserCode.isEmpty()) {
                int salesUserId = convertUserCodeToId(salesUserCode);
                if (salesUserId > 0) {
                    invoice.setUserId(salesUserId);
                } else {
                    throw new Exception("Không tìm thấy Sales User: " + salesUserCode);
                }
            } else {
                throw new Exception("Sales User không được để trống");
            }

            // Column 5: CreatedBy
            String createdByUserCode = getCellValueAsString(row.getCell(5));
            if (!createdByUserCode.isEmpty()) {
                int createdByUserId = convertUserCodeToId(createdByUserCode);
                if (createdByUserId > 0) {
                    invoice.setCreatedBy(createdByUserId);
                } else {
                    throw new Exception("Không tìm thấy CreatedBy User: " + createdByUserCode);
                }
            } else {
                throw new Exception("CreatedBy không được để trống");
            }

            // Column 6: Customer (có thể NULL)
            String customerCode = getCellValueAsString(row.getCell(6));
            if (!customerCode.isEmpty()) {
                int customerId = convertCustomerCodeToId(customerCode);
                if (customerId > 0) {
                    invoice.setCustomerId(customerId);
                } else {
                    invoice.setCustomerId(0); // NULL customer
                }
            } else {
                invoice.setCustomerId(0); // NULL customer
            }

            // Numeric columns
            invoice.setTotalAmount(getCellValueAsDouble(row.getCell(7)));
            invoice.setSubTotal(getCellValueAsDouble(row.getCell(8)));
            invoice.setDiscount(getCellValueAsDouble(row.getCell(9)));
            invoice.setDiscountAmount(getCellValueAsDouble(row.getCell(10)));
            invoice.setVATPercent(getCellValueAsDouble(row.getCell(11)));
            invoice.setVATAmount(getCellValueAsDouble(row.getCell(12)));
            invoice.setPaidAmount(getCellValueAsDouble(row.getCell(13)));

            // Column 14: PaymentMethod (có thể NULL)
            String paymentMethodName = getCellValueAsString(row.getCell(14));
            if (!paymentMethodName.isEmpty()) {
                int paymentMethodId = convertPaymentMethodNameToId(paymentMethodName);
                if (paymentMethodId > 0) {
                    invoice.setPaymentMethodId(paymentMethodId);
                } else {
                    invoice.setPaymentMethodId(0); // NULL
                }
            } else {
                invoice.setPaymentMethodId(0); // NULL
            }

            // Column 15: Status
            String status = getCellValueAsString(row.getCell(15));
            if (!status.isEmpty()) {
                invoice.setStatus(status);
            } else {
                invoice.setStatus("Completed"); // Default status
            }

        } catch (Exception e) {
            throw new Exception("Lỗi đọc dữ liệu tại dòng " + (row.getRowNum() + 1) + ": " + e.getMessage());
        }

        return invoice;
    }

    private boolean validateExportHeaders(Row headerRow) {
        if (headerRow == null) {
            return false;
        }

        String[] expectedHeaders = {
            "InvoiceCode", "InvoiceDate", "UpdateDate", "Store", "Sales",
            "CreatedBy", "Customer", "TotalAmount", "SubTotal", "DiscountPercent",
            "DiscountAmount", "VATPercent", "VATAmount", "PaidAmount", "PaymentMethod", "Status"
        };

        if (headerRow.getLastCellNum() < expectedHeaders.length) {
            return false;
        }

        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null) {
                return false;
            }

            String cellValue = getCellValueAsString(cell);
            if (!expectedHeaders[i].equals(cellValue)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateInvoiceData(Invoices invoice) {
        // Basic validation
        if (invoice.getStoreId() <= 0) return false;
        if (invoice.getUserId() <= 0) return false;
        if (invoice.getCreatedBy() <= 0) return false;
        if (invoice.getTotalAmount() < 0) return false;
        if (invoice.getSubTotal() < 0) return false;
        if (invoice.getPaidAmount() < 0) return false;
        if (invoice.getDiscountAmount() < 0) return false;
        if (invoice.getVATAmount() < 0) return false;
        if (invoice.getDiscount() < 0 || invoice.getDiscount() > 100) return false;
        if (invoice.getVATPercent() < 0 || invoice.getVATPercent() > 100) return false;
        if (invoice.getPaidAmount() > invoice.getTotalAmount()) return false;

        return true;
    }

    // Convert helper methods
    private int convertStoreNameToId(String storeName) {
        try {
            return new StoreDAO().getStoreIdByName(storeName);
        } catch (Exception e) {
            return 0;
        }
    }

    private int convertUserCodeToId(String userCode) {
        try {
            return new UserDAO().getUserIdByCode(userCode);
        } catch (Exception e) {
            return 0;
        }
    }

    private int convertCustomerCodeToId(String customerCode) {
        try {
            return new CustomerDAO().getCustomerIdByCode(customerCode);
        } catch (Exception e) {
            return 0;
        }
    }

    private int convertPaymentMethodNameToId(String paymentMethodName) {
        try {
            return new PaymentMethodDAO().getPaymentMethodIdByName(paymentMethodName);
        } catch (Exception e) {
            return 0;
        }
    }
}