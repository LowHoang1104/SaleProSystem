package salepro.controller.invoice;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import salepro.dao.InvoiceDAO;
import salepro.dao.UserDAO;
import salepro.models.Invoices;
import salepro.models.Users;

@WebServlet(name = "InvoiceManagementServlet", urlPatterns = {"/InvoiceManagementServlet"})
public class InvoiceManagementServlet extends HttpServlet {

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private static final String ORDER_INVOICES = "/view/jsp/employees/order/order_invoices.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // Get parameters
            String action = request.getParameter("action");
            String timeFilter = request.getParameter("timeFilter");
            String paymentMethod = request.getParameter("paymentMethod");
            String createdBy = request.getParameter("createdBy");
            String soldBy = request.getParameter("soldBy");
            int currentPage = getIntParameter(request, "page", 1);
            int pageSize = getIntParameter(request, "pageSize", 5);

            List<Invoices> listInvoice;
            int totalItems;
            UserDAO userDAO = new UserDAO();
            List<Users> listUsers = userDAO.getData();

            // Handle time filter
            if ("filter".equals(action) && timeFilter != null && !timeFilter.isEmpty()) {

                // Get filtered invoices based on time filter
                List<Invoices> allFilteredInvoices = getInvoicesByTimeFilter(timeFilter);

                // Apply additional filters if provided
                if (paymentMethod != null && !paymentMethod.isEmpty()) {
                    allFilteredInvoices = filterByPaymentMethod(allFilteredInvoices, paymentMethod);
                    System.out.println("Applied payment method filter: " + paymentMethod);
                }

                if (createdBy != null && !createdBy.isEmpty()) {
                    allFilteredInvoices = filterByCreatedBy(allFilteredInvoices, createdBy);
                    System.out.println("Applied created by filter: " + createdBy);
                }

                if (soldBy != null && !soldBy.isEmpty()) {
                    allFilteredInvoices = filterBySoldBy(allFilteredInvoices, soldBy);
                    System.out.println("Applied sold by filter: " + soldBy);
                }

                totalItems = allFilteredInvoices.size();

                // Apply pagination to filtered results
                listInvoice = applyPagination(allFilteredInvoices, currentPage, pageSize);

                System.out.println("Final filtered results: " + totalItems + " total items");
                System.out.println("Page results: " + listInvoice.size() + " items");

            } else {
                // Default: get all invoices with pagination
                System.out.println("Loading default invoices (today)");
                List<Invoices> allInvoices = invoiceDAO.getInvoicesToday(); // Default to today
                totalItems = allInvoices.size();
                listInvoice = applyPagination(allInvoices, currentPage, pageSize);
            }

            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Validate current page
            if (currentPage < 1) {
                currentPage = 1;
            }
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            // Set attributes for JSP
            request.setAttribute("listUsers", listUsers);
            request.setAttribute("listInvoice", listInvoice);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalItems", totalItems);
            request.setAttribute("timeFilter", timeFilter != null ? timeFilter : "today");

            // Set session attributes if not present
            if (request.getSession().getAttribute("canEditInvoice") == null) {
                request.getSession().setAttribute("canEditInvoice", true);
            }
            if (request.getSession().getAttribute("canDeleteInvoice") == null) {
                request.getSession().setAttribute("canDeleteInvoice", true);
            }

            request.getRequestDispatcher(ORDER_INVOICES).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            if ("true".equals(request.getParameter("ajax"))) {
                response.getWriter().write("Có lỗi xảy ra khi tải dữ liệu. Vui lòng thử lại.");
            } else {
                throw new ServletException(e);
            }
        }
    }

    /**
     * Get invoices by time filter
     */
    private List<Invoices> getInvoicesByTimeFilter(String timeFilter) {
        try {
            switch (timeFilter.toLowerCase()) {
                case "today":
                    return invoiceDAO.getInvoicesToday();
                case "yesterday":
                    return invoiceDAO.getInvoicesYesterday();
                case "this_week":
                    return invoiceDAO.getInvoicesThisWeek();
                case "last_week":
                    return invoiceDAO.getInvoicesLastWeek();
                case "7_days":
                    return invoiceDAO.getInvoicesLast7Days();
                case "this_month":
                    return invoiceDAO.getInvoicesThisMonth();
                case "last_month":
                    return invoiceDAO.getInvoicesLastMonth();
                case "30_days":
                    return invoiceDAO.getInvoicesLast30Days();
                case "this_quarter":
                    return invoiceDAO.getInvoicesThisQuarter();
                case "last_quarter":
                    return invoiceDAO.getInvoicesLastQuarter();
                case "this_year":
                    return invoiceDAO.getInvoicesThisYear();
                case "last_year":
                    return invoiceDAO.getInvoicesLastYear();
                default:
                    System.out.println("Unknown time filter: " + timeFilter + ", defaulting to today");
                    return invoiceDAO.getInvoicesToday();
            }
        } catch (Exception e) {
            System.err.println("Error getting invoices for time filter " + timeFilter + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Apply pagination to a list of invoices
     */
    private List<Invoices> applyPagination(List<Invoices> allInvoices, int page, int pageSize) {
        if (allInvoices == null || allInvoices.isEmpty()) {
            return new ArrayList<>();
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allInvoices.size());

        if (startIndex < allInvoices.size()) {
            return allInvoices.subList(startIndex, endIndex);
        } else {
            return new ArrayList<>();
        }
    }

    private int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private int getTotalCount() {
        try {
            List<Invoices> allInvoices = invoiceDAO.getData();
            return allInvoices != null ? allInvoices.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting total count: " + e.getMessage());
            return 0;
        }
    }

    private int getTotalCountByTimeFilter(String timeFilter) {
        try {
            List<Invoices> invoices = getInvoicesByTimeFilter(timeFilter);
            return invoices != null ? invoices.size() : 0;
        } catch (Exception e) {
            System.err.println("Error getting total count for time filter " + timeFilter + ": " + e.getMessage());
            return 0;
        }
    }

    /**
     * Filter invoices by payment method
     */
    private List<Invoices> filterByPaymentMethod(List<Invoices> invoices, String paymentMethodId) {
        List<Invoices> filtered = new ArrayList<>();
        try {
            int methodId = Integer.parseInt(paymentMethodId);
            for (Invoices invoice : invoices) {
                if (invoice.getPaymentMethodId() == methodId) {
                    filtered.add(invoice);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid payment method ID: " + paymentMethodId);
            return invoices; // Return original list if invalid
        }
        return filtered;
    }

    /**
     * Filter invoices by created by user
     */
    private List<Invoices> filterByCreatedBy(List<Invoices> invoices, String createdById) {
        List<Invoices> filtered = new ArrayList<>();
        try {
            int userId = Integer.parseInt(createdById);
            for (Invoices invoice : invoices) {
                if (invoice.getCreatedBy() == userId) {
                    filtered.add(invoice);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid created by user ID: " + createdById);
            return invoices; // Return original list if invalid
        }
        return filtered;
    }

    /**
     * Filter invoices by sold by user (sale person)
     */
    private List<Invoices> filterBySoldBy(List<Invoices> invoices, String soldById) {
        List<Invoices> filtered = new ArrayList<>();
        try {
            int userId = Integer.parseInt(soldById);
            for (Invoices invoice : invoices) {
                if (invoice.getUserId() == userId) {
                    filtered.add(invoice);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid sold by user ID: " + soldById);
            return invoices; // Return original list if invalid
        }
        return filtered;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
