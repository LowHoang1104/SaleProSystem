package salepro.controller.report;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import salepro.dao.InvoiceDAO;
import salepro.dao.StoreDAO;
import salepro.models.Invoices;
import salepro.models.Stores;

@WebServlet(name = "SalesReportServlet", urlPatterns = {"/SalesReportServlet"})
public class SalesReportServlet extends HttpServlet {

    private InvoiceDAO invoiceDAO;
    private StoreDAO storeDAO;
    private Gson gson;
    private static final String SALES_REPORT = "view/jsp/admin/Reports/SalesReport.jsp";

    @Override
    public void init() throws ServletException {
        invoiceDAO = new InvoiceDAO();
        storeDAO = new StoreDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String storeId = request.getParameter("storeId");

        if (action == null) {
            showReportPage(request, response, storeId);
        } else {
            switch (action) {
                case "exportExcel":
                    exportToExcel(request, response, storeId);
                    break;
                case "changeStore":
                    showReportPage(request, response, storeId);
                    break;
                default:
                    showReportPage(request, response, null);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showReportPage(HttpServletRequest request, HttpServletResponse response, String storeId)
            throws ServletException, IOException {
        try {
            // Lấy danh sách stores
            List<Stores> stores = storeDAO.getData();
            
            // Mặc định lấy store đầu tiên nếu không có storeId
            int selectedStoreId = 1; // Default store
            if (storeId != null && !storeId.isEmpty()) {
                try {
                    selectedStoreId = Integer.parseInt(storeId);
                } catch (NumberFormatException e) {
                    selectedStoreId = 1;
                }
            }

            // Lấy invoices theo store
            List<Invoices> allInvoices = invoiceDAO.getInvoicesByStoreId(selectedStoreId);
            
            // Tính toán các thống kê cơ bản từ tất cả invoices của store
            SalesStats stats = calculateSalesStats(allInvoices);
            
            // Lấy dữ liệu cho từng biểu đồ riêng biệt
            Map<String, Object> chartData = getChartData(allInvoices);

            // Set attributes cho JSP
            request.setAttribute("stores", stores);
            request.setAttribute("selectedStoreId", selectedStoreId);
            request.setAttribute("totalRevenue", stats.totalRevenue);
            request.setAttribute("totalOrders", stats.totalOrders);
            request.setAttribute("averageOrderValue", stats.averageOrderValue);
            request.setAttribute("totalCustomers", stats.uniqueCustomers);
            
            // Lấy 20 hóa đơn gần nhất
            List<Invoices> recentInvoices = getRecentInvoices(allInvoices, 20);
            request.setAttribute("invoices", recentInvoices);

            // Set chart data
            request.setAttribute("dailyRevenueData", gson.toJson(chartData.get("dailyRevenue")));
            request.setAttribute("paymentMethodData", gson.toJson(chartData.get("paymentMethods")));
            request.setAttribute("hourlyData", gson.toJson(chartData.get("hourlyData")));

            request.getRequestDispatcher(SALES_REPORT).forward(request, response);

        } catch (Exception e) {
            handleError(request, response, e, "Có lỗi xảy ra khi tải báo cáo bán hàng");
        }
    }

    private List<Invoices> getRecentInvoices(List<Invoices> allInvoices, int limit) {
        return allInvoices.stream()
            .filter(invoice -> "Completed".equals(invoice.getStatus()))
            .sorted((a, b) -> b.getInvoiceDate().compareTo(a.getInvoiceDate()))
            .limit(limit)
            .collect(Collectors.toList());
    }

    private SalesStats calculateSalesStats(List<Invoices> invoices) {
        SalesStats stats = new SalesStats();
        Set<Integer> uniqueCustomers = new HashSet<>();

        if (invoices == null) {
            return stats;
        }
        
        for (Invoices invoice : invoices) {
            if (invoice != null && "Completed".equals(invoice.getStatus())) {
                stats.totalRevenue += invoice.getTotalAmount();
                stats.totalOrders++;
                
                if (invoice.getCustomerId() > 0) {
                    uniqueCustomers.add(invoice.getCustomerId());
                }
            }
        }

        stats.uniqueCustomers = uniqueCustomers.size();
        stats.averageOrderValue = stats.totalOrders > 0 ? stats.totalRevenue / stats.totalOrders : 0;

        return stats;
    }

    private Map<String, Object> getChartData(List<Invoices> invoices) {
        Map<String, Object> chartData = new HashMap<>();
        
        // 1. Doanh thu 7 ngày gần nhất
        Map<String, Double> dailyRevenue = getDailyRevenueData(invoices);
        
        // 2. Phương thức thanh toán cho tất cả dữ liệu của store
        Map<String, Double> paymentMethods = getPaymentMethodData(invoices);
        
        // 3. Doanh thu theo giờ trong ngày hôm nay
        Map<String, Double> hourlyData = getTodayHourlyData(invoices);

        chartData.put("dailyRevenue", convertToArrays(dailyRevenue));
        chartData.put("paymentMethods", convertToArrays(paymentMethods));
        chartData.put("hourlyData", convertToArrays(hourlyData));
        
        return chartData;
    }

    private Map<String, Double> getDailyRevenueData(List<Invoices> invoices) {
        Map<String, Double> dailyRevenue = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        
        // Tạo 7 ngày gần nhất
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            dailyRevenue.put(date.format(DateTimeFormatter.ofPattern("MM/dd")), 0.0);
        }

        // Tính doanh thu cho 7 ngày gần nhất
        for (Invoices invoice : invoices) {
            if ("Completed".equals(invoice.getStatus())) {
                LocalDate invoiceDate = invoice.getInvoiceDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                String dateKey = invoiceDate.format(DateTimeFormatter.ofPattern("MM/dd"));
                
                if (dailyRevenue.containsKey(dateKey)) {
                    dailyRevenue.put(dateKey, dailyRevenue.get(dateKey) + invoice.getTotalAmount());
                }
            }
        }

        return dailyRevenue;
    }

    private Map<String, Double> getPaymentMethodData(List<Invoices> invoices) {
        Map<String, Double> paymentMethods = new HashMap<>();
        paymentMethods.put("Tiền mặt", 0.0);
        paymentMethods.put("Chuyển khoản", 0.0);
        paymentMethods.put("Thẻ tín dụng", 0.0);

        // Tính từ tất cả hóa đơn của store
        for (Invoices invoice : invoices) {
            if ("Completed".equals(invoice.getStatus())) {
                String paymentMethod = getPaymentMethodName(invoice.getPaymentMethodId());
                if (paymentMethods.containsKey(paymentMethod)) {
                    paymentMethods.put(paymentMethod, 
                        paymentMethods.get(paymentMethod) + invoice.getTotalAmount());
                }
            }
        }

        return paymentMethods;
    }

    private Map<String, Double> getTodayHourlyData(List<Invoices> invoices) {
        Map<String, Double> hourlyData = new LinkedHashMap<>();
        
        // Tạo dữ liệu cho các giờ trong ngày (8h-22h)
        for (int hour = 8; hour <= 22; hour++) {
            hourlyData.put(hour + "h", 0.0);
        }

        // Lọc chỉ hóa đơn hôm nay
        LocalDate today = LocalDate.now();
        for (Invoices invoice : invoices) {
            if ("Completed".equals(invoice.getStatus())) {
                LocalDate invoiceDate = invoice.getInvoiceDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                
                // Chỉ tính hóa đơn hôm nay
                if (invoiceDate.equals(today)) {
                    int hour = invoice.getInvoiceDate().toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).getHour();
                    String hourKey = hour + "h";
                    
                    if (hourlyData.containsKey(hourKey)) {
                        hourlyData.put(hourKey, hourlyData.get(hourKey) + invoice.getTotalAmount());
                    }
                }
            }
        }

        return hourlyData;
    }

    private String getPaymentMethodName(int paymentMethodId) {
        switch (paymentMethodId) {
            case 1: return "Tiền mặt";
            case 2: return "Chuyển khoản";
            case 3: return "Thẻ tín dụng";
            default: return "Tiền mặt";
        }
    }

    private Map<String, Object> convertToArrays(Map<String, Double> data) {
        Map<String, Object> result = new HashMap<>();
        List<String> labels = new ArrayList<>(data.keySet());
        List<Double> values = new ArrayList<>(data.values());
        
        result.put("labels", labels);
        result.put("values", values);
        
        return result;
    }

    private void exportToExcel(HttpServletRequest request, HttpServletResponse response, String storeId)
            throws IOException {
        try {
            int selectedStoreId = storeId != null ? Integer.parseInt(storeId) : 1;
            List<Invoices> invoices = invoiceDAO.getInvoicesByStoreId(selectedStoreId);
            SalesStats stats = calculateSalesStats(invoices);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"sales-report-store-" + selectedStoreId + "-" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xls\"");

            PrintWriter out = response.getWriter();

            // Excel XML format
            out.println("<?xml version=\"1.0\"?>");
            out.println("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\">");
            out.println("<Worksheet ss:Name=\"Sales Report\">");
            out.println("<Table>");

            // Thống kê tổng quan
            out.println("<Row><Cell><Data ss:Type=\"String\">BÁO CÁO BÁN HÀNG CHI NHÁNH " + selectedStoreId + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\"></Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Tổng doanh thu:</Data></Cell><Cell><Data ss:Type=\"Number\">" + stats.totalRevenue + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Tổng đơn hàng:</Data></Cell><Cell><Data ss:Type=\"Number\">" + stats.totalOrders + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Giá trị TB/đơn:</Data></Cell><Cell><Data ss:Type=\"Number\">" + stats.averageOrderValue + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\">Số khách hàng:</Data></Cell><Cell><Data ss:Type=\"Number\">" + stats.uniqueCustomers + "</Data></Cell></Row>");
            out.println("<Row><Cell><Data ss:Type=\"String\"></Data></Cell></Row>");

            // Header cho danh sách hóa đơn
            out.println("<Row>");
            out.println("<Cell><Data ss:Type=\"String\">Mã HĐ</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Ngày</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Tổng tiền</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Trạng thái</Data></Cell>");
            out.println("</Row>");

            // Dữ liệu hóa đơn
            List<Invoices> recentInvoices = getRecentInvoices(invoices, 100);
            for (Invoices invoice : recentInvoices) {
                out.println("<Row>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(invoice.getInvoiceCode()) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + invoice.getInvoiceDate() + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"Number\">" + invoice.getTotalAmount() + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(invoice.getStatus()) + "</Data></Cell>");
                out.println("</Row>");
            }

            out.println("</Table>");
            out.println("</Worksheet>");
            out.println("</Workbook>");
            out.flush();

        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Lỗi xuất Excel: " + e.getMessage() + "'); window.close();</script>");
        }
    }

    // Inner class cho thống kê
    private static class SalesStats {
        double totalRevenue = 0;
        int totalOrders = 0;
        double averageOrderValue = 0;
        int uniqueCustomers = 0;
    }

    // Utility methods
    private String safeValue(String value) {
        return value != null ? value : "";
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", message + ": " + e.getMessage());
        request.getRequestDispatcher("/view/jsp/error/error.jsp").forward(request, response);
    }
}