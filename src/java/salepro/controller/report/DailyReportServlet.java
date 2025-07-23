package salepro.controller.report;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import salepro.dao.*;
import salepro.models.*;

@WebServlet(name = "DailyReportServlet", urlPatterns = {"/DailyReportServlet"})
public class DailyReportServlet extends HttpServlet {

    private InvoiceDAO invoiceDAO;
    private StoreDAO storeDAO;
    private EmployeeDAO employeeDAO;
    private InventoryDAO inventoryDAO;
    private FundTransactionDAO fundDAO;
    private Gson gson;
    private static final String DAILY_REPORT = "view/jsp/admin/Reports/DailyReport.jsp";

    @Override
    public void init() throws ServletException {
        invoiceDAO = new InvoiceDAO();
        storeDAO = new StoreDAO();
        employeeDAO = new EmployeeDAO();
        inventoryDAO = new InventoryDAO();
        fundDAO = new FundTransactionDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String reportDate = request.getParameter("reportDate");
        String storeId = request.getParameter("storeId");

        if (action == null) {
            showDailyReport(request, response, reportDate, storeId);
        } else {
            switch (action) {
                case "generateReport":
                    generateReport(request, response, reportDate, storeId);
                    break;
                case "exportPDF":
                    exportToPDF(request, response, reportDate, storeId);
                    break;
                case "exportExcel":
                    exportToExcel(request, response, reportDate, storeId);
                    break;
                default:
                    showDailyReport(request, response, reportDate, storeId);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showDailyReport(HttpServletRequest request, HttpServletResponse response,
            String reportDate, String storeId) throws ServletException, IOException {
        try {
            LocalDate selectedDate = reportDate != null ? LocalDate.parse(reportDate) : LocalDate.now();
            int selectedStoreId = storeId != null ? Integer.parseInt(storeId) : 1;

            List<Stores> stores = storeDAO.getData();
            DailyReportData reportData = calculateDailyReport(selectedDate, selectedStoreId);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = selectedDate.format(formatter);

            request.setAttribute("formattedDate", formattedDate);
            request.setAttribute("stores", stores);
            request.setAttribute("selectedStoreId", selectedStoreId);
            request.setAttribute("selectedDate", selectedDate);
            request.setAttribute("reportData", reportData);
            
            String hourlyRevenueJson = gson.toJson(reportData.hourlyRevenue);
            String paymentMethodJson = gson.toJson(reportData.paymentMethodBreakdown);
            request.setAttribute("hourlyRevenueData", hourlyRevenueJson);
            request.setAttribute("paymentMethodData", paymentMethodJson);

            request.getRequestDispatcher(DAILY_REPORT).forward(request, response);

        } catch (Exception e) {
            handleError(request, response, e, "Có lỗi xảy ra khi tải báo cáo cuối ngày");
        }
    }

    private DailyReportData calculateDailyReport(LocalDate reportDate, int storeId) {
        DailyReportData data = new DailyReportData();
        data.reportDate = reportDate;
        data.storeId = storeId;

        try {
            calculateSalesOverview(data);
            calculatePaymentBreakdown(data);
            calculateEmployeePerformance(data);
            calculateHourlyRevenue(data);
            calculateInventoryStatus(data);
            calculateCashFlow(data);
            calculateComparison(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private void calculateSalesOverview(DailyReportData data) {
        List<Invoices> dayInvoices = invoiceDAO.getInvoicesByDateAndStore(data.reportDate, data.storeId);

        data.totalRevenue = 0;
        data.totalOrders = 0;
        data.totalCustomers = new HashSet<>();

        for (Invoices invoice : dayInvoices) {
            if ("Completed".equals(invoice.getStatus())) {
                data.totalRevenue += invoice.getTotalAmount();
                data.totalOrders++;
                if (invoice.getCustomerId() != 0) {
                    data.totalCustomers.add(invoice.getCustomerId());
                }
            }
        }

        data.averageOrderValue = data.totalOrders > 0 ? data.totalRevenue / data.totalOrders : 0;
        data.customerCount = data.totalCustomers.size();
    }

    private void calculatePaymentBreakdown(DailyReportData data) {
        List<Invoices> dayInvoices = invoiceDAO.getInvoicesByDateAndStore(data.reportDate, data.storeId);
        List<PaymentMethods> paymentMethods = invoiceDAO.getPaymentMethods();

        data.paymentMethodBreakdown = new HashMap<>();
        for (PaymentMethods method : paymentMethods) {
            data.paymentMethodBreakdown.put(method.getMethodName(), 0.0);
        }

        for (Invoices invoice : dayInvoices) {
            if ("Completed".equals(invoice.getStatus()) && invoice.getPaymentMethodId() != 0) {
                String paymentMethod = getPaymentMethodName(invoice.getPaymentMethodId());
                data.paymentMethodBreakdown.merge(paymentMethod, invoice.getTotalAmount(), Double::sum);
            }
        }
    }

    private void calculateEmployeePerformance(DailyReportData data) {
        List<Invoices> dayInvoices = invoiceDAO.getInvoicesByDateAndStore(data.reportDate, data.storeId);
        data.employeePerformance = new HashMap<>();

        for (Invoices invoice : dayInvoices) {
            if ("Completed".equals(invoice.getStatus())) {
                int saleId = invoice.getUserId();
                Employees employee = employeeDAO.getEmployeeByUserId(saleId);
                String employeeName = employee != null ? employee.getFullName() : "Nhân viên " + saleId;

                EmployeeStats stats = data.employeePerformance.computeIfAbsent(employeeName, k -> new EmployeeStats());
                stats.revenue += invoice.getTotalAmount();
                stats.orderCount++;
                stats.averageOrderValue = stats.orderCount > 0 ? stats.revenue / stats.orderCount : 0;
            }
        }
    }

    private void calculateHourlyRevenue(DailyReportData data) {
        List<Invoices> dayInvoices = invoiceDAO.getInvoicesByDateAndStore(data.reportDate, data.storeId);

        data.hourlyRevenue = new LinkedHashMap<>();
        for (int hour = 8; hour <= 22; hour++) {
            data.hourlyRevenue.put(hour + ":00", 0.0);
        }

        for (Invoices invoice : dayInvoices) {
            if ("Completed".equals(invoice.getStatus()) && invoice.getInvoiceDate() != null) {
                try {
                    int hour = invoice.getInvoiceDate().toInstant()
                            .atZone(java.time.ZoneId.systemDefault()).getHour();
                    String hourKey = hour + ":00";
                    if (data.hourlyRevenue.containsKey(hourKey)) {
                        data.hourlyRevenue.merge(hourKey, invoice.getTotalAmount(), Double::sum);
                    }
                } catch (Exception e) {
                    // Ignore hour extraction errors
                }
            }
        }
    }

    private void calculateInventoryStatus(DailyReportData data) {
        data.productsSold = invoiceDAO.getProductsSoldByDate(data.reportDate, data.storeId);
        data.lowStockProducts = inventoryDAO.getLowStockProducts(data.storeId);

        data.totalProductsSold = data.productsSold.size();
        data.totalQuantitySold = data.productsSold.stream()
                .mapToInt(InvoiceDetails::getQuantity)
                .sum();
    }

    private void calculateCashFlow(DailyReportData data) {
        List<FundTransactions> dayTransactions = fundDAO.getTransactionsByDateAndStore(data.reportDate, data.storeId);

        data.cashInflow = 0;
        data.cashOutflow = 0;

        for (FundTransactions transaction : dayTransactions) {
            if ("Approved".equals(transaction.getStatus())) {
                switch (transaction.getTransactionType()) {
                    case "Income":
                        data.cashInflow += transaction.getAmount();
                        break;
                    case "Expense":
                        data.cashOutflow += transaction.getAmount();
                        break;
                    case "Transfer":
                        data.cashOutflow += transaction.getAmount();
                        break;
                }
            }
        }

        data.netCashFlow = data.cashInflow - data.cashOutflow;
    }

    private void calculateComparison(DailyReportData data) {
        LocalDate previousDate = data.reportDate.minusDays(1);
        List<Invoices> prevDayInvoices = invoiceDAO.getInvoicesByDateAndStore(previousDate, data.storeId);

        double prevRevenue = 0;
        int prevOrders = 0;

        for (Invoices invoice : prevDayInvoices) {
            if ("Completed".equals(invoice.getStatus())) {
                prevRevenue += invoice.getTotalAmount();
                prevOrders++;
            }
        }

        // Fixed growth calculation
        if (prevRevenue > 0) {
            data.revenueGrowth = ((data.totalRevenue - prevRevenue) / prevRevenue) * 100;
        } else if (data.totalRevenue > 0) {
            data.revenueGrowth = 100; // New revenue
        } else {
            data.revenueGrowth = 0;
        }

        if (prevOrders > 0) {
            data.orderGrowth = ((data.totalOrders - prevOrders) / (double) prevOrders) * 100;
        } else if (data.totalOrders > 0) {
            data.orderGrowth = 100; // New orders
        } else {
            data.orderGrowth = 0;
        }
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response,
            String reportDate, String storeId) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            LocalDate selectedDate = reportDate != null
                    ? LocalDate.parse(reportDate) : LocalDate.now();
            int selectedStoreId = storeId != null ? Integer.parseInt(storeId) : 1;

            DailyReportData reportData = calculateDailyReport(selectedDate, selectedStoreId);

            String jsonResponse = gson.toJson(Map.of(
                    "success", true,
                    "message", "Báo cáo đã được tạo thành công",
                    "data", reportData
            ));

            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            String jsonResponse = gson.toJson(Map.of(
                    "success", false,
                    "message", "Lỗi tạo báo cáo: " + e.getMessage()
            ));
            response.getWriter().write(jsonResponse);
        }
    }

    private void exportToExcel(HttpServletRequest request, HttpServletResponse response,
            String reportDate, String storeId) throws IOException {
        try {
            LocalDate selectedDate = reportDate != null
                    ? LocalDate.parse(reportDate) : LocalDate.now();
            int selectedStoreId = storeId != null ? Integer.parseInt(storeId) : 1;

            DailyReportData reportData = calculateDailyReport(selectedDate, selectedStoreId);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"daily-report-" + selectedDate + "-store-" + selectedStoreId + ".xls\"");

            PrintWriter out = response.getWriter();

            out.println("<?xml version=\"1.0\"?>");
            out.println("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\">");
            out.println("<Worksheet ss:Name=\"Daily Report\">");
            out.println("<Table>");

            out.println("<Row><Cell><Data ss:Type=\"String\">BÁO CÁO CUỐI NGÀY - " + selectedDate + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Chi nhánh: " + getStoreName(selectedStoreId) + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\"></Data></Cell></Row>");

            out.println("<Row><Cell><Data ss:Type=\"String\">TỔNG QUAN DOANH THU</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Tổng doanh thu:</Data></Cell><Cell><Data ss:Type=\"Number\">" + reportData.totalRevenue + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Số đơn hàng:</Data></Cell><Cell><Data ss:Type=\"Number\">" + reportData.totalOrders + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Giá trị TB/đơn:</Data></Cell><Cell><Data ss:Type=\"Number\">" + reportData.averageOrderValue + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Số khách hàng:</Data></Cell><Cell><Data ss:Type=\"Number\">" + reportData.customerCount + "</Data></Cell></Row>");

            out.println("</Table>");
            out.println("</Worksheet>");
            out.println("</Workbook>");
            out.flush();

        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Lỗi xuất Excel: " + e.getMessage() + "'); window.close();</script>");
        }
    }

    private void exportToPDF(HttpServletRequest request, HttpServletResponse response,
            String reportDate, String storeId) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script>alert('Tính năng PDF đang được phát triển'); window.close();</script>");
    }

    private String getPaymentMethodName(int paymentMethodId) {
        if (paymentMethodId == 0) return "Không xác định";
        
        try {
            PaymentMethods method = invoiceDAO.getPaymentMethodById(paymentMethodId);
            return method != null ? method.getMethodName() : "Không xác định";
        } catch (Exception e) {
            return "Không xác định";
        }
    }

    private String getStoreName(int storeId) {
        try {
            Stores store = storeDAO.getStoreByID(storeId);
            return store != null ? store.getStoreName() : "Chi nhánh " + storeId;
        } catch (Exception e) {
            return "Chi nhánh " + storeId;
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", message + ": " + e.getMessage());
        request.getRequestDispatcher("/view/jsp/error/error.jsp").forward(request, response);
    }

    // Data Classes
    public static class DailyReportData {
        public LocalDate reportDate;
        public int storeId;
        public double totalRevenue = 0;
        public int totalOrders = 0;
        public double averageOrderValue = 0;
        public int customerCount = 0;
        public Set<Integer> totalCustomers = new HashSet<>();
        public Map<String, Double> paymentMethodBreakdown = new HashMap<>();
        public Map<String, EmployeeStats> employeePerformance = new HashMap<>();
        public Map<String, Double> hourlyRevenue = new LinkedHashMap<>();
        public List<InvoiceDetails> productsSold = new ArrayList<>();
        public List<ProductVariants> lowStockProducts = new ArrayList<>();
        public int totalProductsSold = 0;
        public int totalQuantitySold = 0;
        public double cashInflow = 0;
        public double cashOutflow = 0;
        public double netCashFlow = 0;
        public double revenueGrowth = 0;
        public double orderGrowth = 0;

        // Getters and Setters
        public LocalDate getReportDate() { return reportDate; }
        public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
        public int getStoreId() { return storeId; }
        public void setStoreId(int storeId) { this.storeId = storeId; }
        public double getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
        public int getTotalOrders() { return totalOrders; }
        public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
        public double getAverageOrderValue() { return averageOrderValue; }
        public void setAverageOrderValue(double averageOrderValue) { this.averageOrderValue = averageOrderValue; }
        public int getCustomerCount() { return customerCount; }
        public void setCustomerCount(int customerCount) { this.customerCount = customerCount; }
        public Set<Integer> getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(Set<Integer> totalCustomers) { this.totalCustomers = totalCustomers; }
        public Map<String, Double> getPaymentMethodBreakdown() { return paymentMethodBreakdown; }
        public void setPaymentMethodBreakdown(Map<String, Double> paymentMethodBreakdown) { this.paymentMethodBreakdown = paymentMethodBreakdown; }
        public Map<String, EmployeeStats> getEmployeePerformance() { return employeePerformance; }
        public void setEmployeePerformance(Map<String, EmployeeStats> employeePerformance) { this.employeePerformance = employeePerformance; }
        public Map<String, Double> getHourlyRevenue() { return hourlyRevenue; }
        public void setHourlyRevenue(Map<String, Double> hourlyRevenue) { this.hourlyRevenue = hourlyRevenue; }
        public List<InvoiceDetails> getProductsSold() { return productsSold; }
        public void setProductsSold(List<InvoiceDetails> productsSold) { this.productsSold = productsSold; }
        public List<ProductVariants> getLowStockProducts() { return lowStockProducts; }
        public void setLowStockProducts(List<ProductVariants> lowStockProducts) { this.lowStockProducts = lowStockProducts; }
        public int getTotalProductsSold() { return totalProductsSold; }
        public void setTotalProductsSold(int totalProductsSold) { this.totalProductsSold = totalProductsSold; }
        public int getTotalQuantitySold() { return totalQuantitySold; }
        public void setTotalQuantitySold(int totalQuantitySold) { this.totalQuantitySold = totalQuantitySold; }
        public double getCashInflow() { return cashInflow; }
        public void setCashInflow(double cashInflow) { this.cashInflow = cashInflow; }
        public double getCashOutflow() { return cashOutflow; }
        public void setCashOutflow(double cashOutflow) { this.cashOutflow = cashOutflow; }
        public double getNetCashFlow() { return netCashFlow; }
        public void setNetCashFlow(double netCashFlow) { this.netCashFlow = netCashFlow; }
        public double getRevenueGrowth() { return revenueGrowth; }
        public void setRevenueGrowth(double revenueGrowth) { this.revenueGrowth = revenueGrowth; }
        public double getOrderGrowth() { return orderGrowth; }
        public void setOrderGrowth(double orderGrowth) { this.orderGrowth = orderGrowth; }
    }

    public static class EmployeeStats {
        public double revenue = 0;
        public int orderCount = 0;
        public double averageOrderValue = 0;

        public double getRevenue() { return revenue; }
        public void setRevenue(double revenue) { this.revenue = revenue; }
        public int getOrderCount() { return orderCount; }
        public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
        public double getAverageOrderValue() { return averageOrderValue; }
        public void setAverageOrderValue(double averageOrderValue) { this.averageOrderValue = averageOrderValue; }
    }
}