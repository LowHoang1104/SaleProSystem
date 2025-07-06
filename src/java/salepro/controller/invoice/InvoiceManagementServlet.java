package salepro.controller.invoice;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    // Session keys
    private static final String SESSION_LIST_INVOICE = "listInvoice";
    private static final String SESSION_CURRENT_FILTERS = "currentFilters";
    private static final String SESSION_TOTAL_ITEMS = "totalItems";
    
    private static final String SESSION_SEARCH_RESULTS = "searchResults";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            int currentPage = getIntParameter(request, "page", 1);
            int pageSize = getIntParameter(request, "pageSize", 5);

            List<Invoices> fullListInvoice;
            List<Invoices> pageListInvoice;
            int totalItems;

            UserDAO userDAO = new UserDAO();
            List<Users> listUsers = userDAO.getData();

            if ("quickSearch".equals(action)) {
                // Quick search từ input chính
                String quickSearchQuery = request.getParameter("quickSearch");

                if (quickSearchQuery != null && !quickSearchQuery.trim().isEmpty()) {
                    fullListInvoice = performQuickSearch(quickSearchQuery.trim(), session);
                    
                    session.setAttribute(SESSION_SEARCH_RESULTS, fullListInvoice);
                    session.setAttribute(SESSION_TOTAL_ITEMS, fullListInvoice.size());

                    totalItems = fullListInvoice.size();
                    pageListInvoice = applyPagination(fullListInvoice, currentPage, pageSize);

                } else {
                    session.removeAttribute(SESSION_SEARCH_RESULTS);
                    fullListInvoice = getListFromSession(session);
                    totalItems = fullListInvoice.size();
                    pageListInvoice = applyPagination(fullListInvoice, currentPage, pageSize);
                }
                
            } else if ("filterWithSearch".equals(action)) {
               
                String quickSearchQuery = request.getParameter("quickSearch");
                
                fullListInvoice = applyAllFilters(request);

                if (quickSearchQuery != null && !quickSearchQuery.trim().isEmpty()) {
                    fullListInvoice = performQuickSearchOnList(quickSearchQuery.trim(), fullListInvoice);
                    
                    // Lưu search state
                    session.setAttribute(SESSION_SEARCH_RESULTS, fullListInvoice);
                } else {
                    session.removeAttribute(SESSION_SEARCH_RESULTS);
                }
                
                // Lưu filter state và list
                session.setAttribute(SESSION_LIST_INVOICE, fullListInvoice);
                session.setAttribute(SESSION_TOTAL_ITEMS, fullListInvoice.size());
                storeCurrentFiltersInSession(request, session);
                
                totalItems = fullListInvoice.size();
                pageListInvoice = applyPagination(fullListInvoice, currentPage, pageSize);
                
            } else if ("filter".equals(action)) {
                // Clear search khi chỉ filter
                session.removeAttribute(SESSION_SEARCH_RESULTS);
                
                if (haveFiltersChanged(request, session)) {
                    fullListInvoice = applyAllFilters(request);
                    session.setAttribute(SESSION_LIST_INVOICE, fullListInvoice);
                    session.setAttribute(SESSION_TOTAL_ITEMS, fullListInvoice.size());
                    storeCurrentFiltersInSession(request, session);
                } else {
                    fullListInvoice = getListFromSession(session);
                }

                totalItems = fullListInvoice.size();
                pageListInvoice = applyPagination(fullListInvoice, currentPage, pageSize);

            } else {
                session.removeAttribute(SESSION_SEARCH_RESULTS);
                fullListInvoice = invoiceDAO.getInvoicesToday();

                session.setAttribute(SESSION_LIST_INVOICE, fullListInvoice);
                session.setAttribute(SESSION_TOTAL_ITEMS, fullListInvoice.size());
                storeDefaultFiltersInSession(session);

                totalItems = fullListInvoice.size();
                pageListInvoice = applyPagination(fullListInvoice, currentPage, pageSize);
            }

            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            if (currentPage < 1) {
                currentPage = 1;
            }
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            // Set attributes for JSP
            request.setAttribute("listUsers", listUsers);
            request.setAttribute("listInvoice", pageListInvoice);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalItems", totalItems);

            if (session.getAttribute(SESSION_SEARCH_RESULTS) == null) {
                String timeFilter = request.getParameter("timeFilter");
                request.setAttribute("timeFilter", timeFilter != null ? timeFilter : "today");
            }
            
            // Set session attributes if not present
            if (session.getAttribute("canEditInvoice") == null) {
                session.setAttribute("canEditInvoice", true);
            }
            if (session.getAttribute("canDeleteInvoice") == null) {
                session.setAttribute("canDeleteInvoice", true);
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

    @SuppressWarnings("unchecked")
    private List<Invoices> getListFromSession(HttpSession session) {
        List<Invoices> sessionList = (List<Invoices>) session.getAttribute(SESSION_LIST_INVOICE);
        return sessionList != null ? sessionList : new ArrayList<>();
    }

    private boolean haveFiltersChanged(HttpServletRequest request, HttpSession session) {
        @SuppressWarnings("unchecked")
        java.util.Map<String, String> sessionFilters
                = (java.util.Map<String, String>) session.getAttribute(SESSION_CURRENT_FILTERS);

        if (sessionFilters == null) {
            return true;
        }

        String[] filterParams = {"timeFilter", "paymentMethod", "createdBy", "soldBy"};

        for (String param : filterParams) {
            String currentValue = request.getParameter(param);
            String sessionValue = sessionFilters.get(param);

            if (!java.util.Objects.equals(currentValue, sessionValue)) {
                return true;
            }
        }

        return false;
    }

    private void storeCurrentFiltersInSession(HttpServletRequest request, HttpSession session) {
        java.util.Map<String, String> filters = new java.util.HashMap<>();
        filters.put("timeFilter", request.getParameter("timeFilter"));
        filters.put("paymentMethod", request.getParameter("paymentMethod"));
        filters.put("createdBy", request.getParameter("createdBy"));
        filters.put("soldBy", request.getParameter("soldBy"));

        session.setAttribute(SESSION_CURRENT_FILTERS, filters);
    }

    private void storeDefaultFiltersInSession(HttpSession session) {
        java.util.Map<String, String> defaultFilters = new java.util.HashMap<>();
        defaultFilters.put("timeFilter", "today");
        defaultFilters.put("paymentMethod", "");
        defaultFilters.put("createdBy", "");
        defaultFilters.put("soldBy", "");

        session.setAttribute(SESSION_CURRENT_FILTERS, defaultFilters);
    }

    private List<Invoices> applyAllFilters(HttpServletRequest request) {
        String timeFilter = request.getParameter("timeFilter");
        String paymentMethod = request.getParameter("paymentMethod");
        String createdBy = request.getParameter("createdBy");
        String soldBy = request.getParameter("soldBy");

        // Bước 1: Lấy invoices theo time filter
        List<Invoices> filteredInvoices = getInvoicesByTimeFilter(timeFilter != null ? timeFilter : "today");

        // Bước 2: Apply các filters khác
        if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
            filteredInvoices = filterByPaymentMethod(filteredInvoices, paymentMethod);
        }

        if (createdBy != null && !createdBy.trim().isEmpty()) {
            filteredInvoices = filterByCreatedBy(filteredInvoices, createdBy);
        }

        if (soldBy != null && !soldBy.trim().isEmpty()) {
            filteredInvoices = filterBySoldBy(filteredInvoices, soldBy);
        }

        return filteredInvoices;
    }

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
                    return invoiceDAO.getInvoicesToday();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

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
            return invoices;
        }
        return filtered;
    }

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
            return invoices;
        }
        return filtered;
    }

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
            return invoices;
        }
        return filtered;
    }

    private List<Invoices> performQuickSearch(String query, HttpSession session) {
        try {
            List<Invoices> currentList = getListFromSession(session);
            return performQuickSearchOnList(query, currentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Invoices> performQuickSearchOnList(String query, List<Invoices> invoiceList) {
        try {
            List<Invoices> searchResults = new ArrayList<>();
            String queryLower = query.toLowerCase();

            for (Invoices invoice : invoiceList) {
                boolean matched = false;  
                
                // Tìm theo mã hóa đơn
                if (invoice.getInvoiceCode().toLowerCase().contains(queryLower)) {
                    matched = true;
                }
                // Tìm theo tên khách hàng (nếu có)
                if (!matched && invoice.getCustomerNameByID() != null
                        && invoice.getCustomerNameByID().toLowerCase().contains(queryLower)) {
                    matched = true;
                }

                if (matched) {
                    searchResults.add(invoice);
                }
            }
            return searchResults;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}