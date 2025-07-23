package salepro.controller.report;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;

/**
 * Customer Report Servlet - Optimized with 4 Actions Only
 */
@WebServlet(name = "CustomerReportServlet", urlPatterns = {"/CustomerReportServlet"})
public class CustomerReportServlet extends HttpServlet {

    private CustomerDAO customerDAO;
    private Gson gson;
    private static final String CUSTOMER_REPORT = "view/jsp/admin/Reports/CustomerReport.jsp";

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            showReportPage(request, response);
        } else {
            if ("exportTopCustomers".equals(action)) {
                exportTopCustomers(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showReportPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get dashboard statistics
            int totalCustomers = customerDAO.getTotalCustomers();
            int newCustomersThisMonth = customerDAO.getNewCustomersThisMonth();
            double totalRevenue = customerDAO.getTotalRevenue();
            double averageOrderValue = customerDAO.getAverageOrderValue();

            // Get top customers and additional stats
            List<Customers> topCustomers = customerDAO.getTopCustomers(10);
            int vipCustomers = customerDAO.getCustomerCountByRank("VIP");
            int goldCustomers = customerDAO.getCustomerCountByRank("Gold");
            int newCustomersWeek = customerDAO.getNewCustomersThisWeek();

            // Get chart data
            Map<String, Integer> rankData = customerDAO.getCustomersByRank();
            Map<String, Integer> genderData = customerDAO.getCustomersByGender();
            Map<String, Integer> monthlyData = customerDAO.getMonthlyRegistrations();
            Map<String, Integer> ageData = customerDAO.getCustomerAgeDistribution();

            // Set JSP attributes
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("newCustomersThisMonth", newCustomersThisMonth);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("averageOrderValue", averageOrderValue);
            request.setAttribute("topCustomers", topCustomers);
            request.setAttribute("vipCustomers", vipCustomers);
            request.setAttribute("goldCustomers", goldCustomers);
            request.setAttribute("newCustomersWeek", newCustomersWeek);

            // Convert chart data to arrays
            int[] monthlyArray = convertToMonthlyArray(monthlyData);
            int[] rankArray = convertToRankArray(rankData);
            int[] genderArray = convertToGenderArray(genderData);
            int[] ageArray = convertToAgeArray(ageData);

            // Set chart data as JSON
            request.setAttribute("monthlyDataArray", gson.toJson(monthlyArray));
            request.setAttribute("rankDataArray", gson.toJson(rankArray));
            request.setAttribute("genderDataArray", gson.toJson(genderArray));
            request.setAttribute("ageDataArray", gson.toJson(ageArray));

            request.getRequestDispatcher(CUSTOMER_REPORT).forward(request, response);

        } catch (Exception e) {
            handleError(request, response, e, "Có lỗi xảy ra khi tải báo cáo khách hàng");
        }
    }

    // ==================== DATA CONVERSION METHODS ====================
    private int[] convertToMonthlyArray(Map<String, Integer> monthlyData) {
        int[] array = new int[12];
        for (int i = 1; i <= 12; i++) {
            array[i - 1] = monthlyData.getOrDefault(String.valueOf(i), 0);
        }
        return array;
    }

    private int[] convertToRankArray(Map<String, Integer> rankData) {
        // Only return 4 actual ranks: Bronze, Silver, Gold, VIP
        return new int[]{
            rankData.getOrDefault("Bronze", 0), // Index 0: Bronze
            rankData.getOrDefault("Silver", 0), // Index 1: Silver  
            rankData.getOrDefault("Gold", 0), // Index 2: Gold
            rankData.getOrDefault("VIP", 0) // Index 3: VIP
        };
    }

    private int[] convertToGenderArray(Map<String, Integer> genderData) {
        return new int[]{
            genderData.getOrDefault("Male", 0),
            genderData.getOrDefault("Female", 0)
        };
    }

    private int[] convertToAgeArray(Map<String, Integer> ageData) {
        return new int[]{
            ageData.getOrDefault("Dưới 25", 0),
            ageData.getOrDefault("25-35", 0),
            ageData.getOrDefault("36-45", 0),
            ageData.getOrDefault("46-55", 0) + ageData.getOrDefault("Trên 55", 0)
        };
    }

    private void exportTopCustomers(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            List<Customers> topCustomers = customerDAO.getTopCustomers(10);

            // Set proper Excel headers
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"top-10-customers.xls\"");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");

            PrintWriter out = response.getWriter();

            // Excel XML format for better compatibility
            out.println("<?xml version=\"1.0\"?>");
            out.println("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
            out.println(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
            out.println(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\">");
            out.println("<Worksheet ss:Name=\"Top 10 Customers\">");
            out.println("<Table>");

            // Header row
            out.println("<Row>");
            out.println("<Cell><Data ss:Type=\"String\">STT</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Mã KH</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Họ tên</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Điện thoại</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Email</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Hạng</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Tổng chi tiêu</Data></Cell>");
            out.println("<Cell><Data ss:Type=\"String\">Điểm</Data></Cell>");
            out.println("</Row>");

            // Data rows
            for (int i = 0; i < topCustomers.size(); i++) {
                Customers customer = topCustomers.get(i);
                out.println("<Row>");
                out.println("<Cell><Data ss:Type=\"Number\">" + (i + 1) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(customer.getCustomerCode()) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(customer.getFullName()) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(customer.getPhone()) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(customer.getEmail()) + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"String\">" + safeValue(customer.getRank(), "Thường") + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"Number\">" + customer.getTotalSpent() + "</Data></Cell>");
                out.println("<Cell><Data ss:Type=\"Number\">" + customer.getPoints() + "</Data></Cell>");
                out.println("</Row>");
            }

            out.println("</Table>");
            out.println("</Worksheet>");
            out.println("</Workbook>");
            out.flush();

        } catch (Exception e) {
            // If error, redirect to avoid opening new page
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Lỗi xuất Excel: " + e.getMessage() + "'); window.close();</script>");
        }
    }

    // ==================== UTILITY METHODS ====================
    private String safeValue(String value) {
        return value != null ? value : "";
    }

    private String safeValue(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e, String message)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", message + ": " + e.getMessage());
        request.getRequestDispatcher("/view/jsp/error/error.jsp").forward(request, response);
    }
}
