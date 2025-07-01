/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.InvoiceDAO;
import salepro.dao.UserDAO;
import salepro.models.Invoices;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "ReportEndDayServlet", urlPatterns = {"/ReportEndDayServlet"})
public class ReportEndDayServlet extends HttpServlet {

    private static final String END_OF_DAY_REPORT = "view/jsp/employees/end_of_day_report.jsp";
    private static final int PAGE_SIZE = 10;

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
                System.out.println(allInvoices.size());
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
                System.out.println(pageInvoices.size());

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

}
