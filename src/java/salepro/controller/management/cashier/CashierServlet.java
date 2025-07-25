package salepro.controller.management.cashier;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import salepro.dao.CategoryDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.StoreFundDAO;
import salepro.dao.UserDAO;
import salepro.models.Categories;
import salepro.models.ProductMasters;
import salepro.models.ProductTypes;
import salepro.models.StoreFund;
import salepro.models.Users;
import salepro.models.up.InvoiceItem;

@WebServlet(name = "CashierServlet", urlPatterns = {"/CashierServlet"})
public class CashierServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String FILTER_PANEL = "view/jsp/employees/filter_panel.jsp";
    private static final String HEADER_AJAX = "view/jsp/employees/header_ajax.jsp";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");
        if (invoices == null) {
            invoices = new ArrayList<>();
            invoices.add(new InvoiceItem(1, "Hóa đơn 1"));
            session.setAttribute("invoices", invoices);
        }

        Integer currentInvoiceId = (Integer) session.getAttribute("currentInvoiceId");
        if (currentInvoiceId == null) {
            currentInvoiceId = invoices.get(0).getId();
            session.setAttribute("currentInvoiceId", currentInvoiceId);
        }

        InvoiceItem currentInvoice = (InvoiceItem) session.getAttribute("currentInvoice");
        if (currentInvoice == null) {
            currentInvoice = new InvoiceItem(currentInvoiceId, "Hóa đơn " + currentInvoiceId);
            session.setAttribute("currentInvoice", currentInvoice);
        }

        String[] selectedCategoryIds = request.getParameterValues("categoryIds");
        String[] selectedTypeIds = request.getParameterValues("typeIds");

        List<ProductMasters> pList = getFilteredProducts(session, selectedCategoryIds, selectedTypeIds);

        // Pagination
        int page = 1;
        int pageSize = 12;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (Exception e) {
            page = 1;
        }

        int totalProducts = pList.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min((startIndex + pageSize), totalProducts);

        List<ProductMasters> pageProducts = pList.subList(startIndex, endIndex);

        // Set attributes
        UserDAO userDAO = new UserDAO();
        List<Users> usersList = userDAO.getData();
        
        StoreFundDAO sfDao = new StoreFundDAO();
        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        List<StoreFund> cashs = new ArrayList<>();
        for (StoreFund storeFund : listStoreFundCash) {
            if(storeFund.getFundName().contains("thu ngân"))
            {
                cashs.add(storeFund);
            }
        }
        List<StoreFund> listStoreFundBank = sfDao.getFundsByStoreAndType(1, "Bank");
        
        System.out.println("cash" + cashs.size());
        System.out.println("bank" + listStoreFundBank.size());
        session.setAttribute("cashs", cashs);
        session.setAttribute("banks", listStoreFundBank);
        session.setAttribute("invoices", invoices);
        session.setAttribute("listUsers", usersList);
        session.setAttribute("listProducts", pList); // Store filtered products in session

        request.setAttribute("products", pageProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalProducts", totalProducts); // Add total count for display

        if (selectedCategoryIds != null && selectedCategoryIds.length > 0) {
            request.setAttribute("selectedCategoryIds", Arrays.asList(selectedCategoryIds));
        }
        if (selectedTypeIds != null && selectedTypeIds.length > 0) {
            request.setAttribute("selectedTypeIds", Arrays.asList(selectedTypeIds));
        }

        session.setAttribute("phoneNumber", "0996996996");
        request.getRequestDispatcher(CASHIER).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("filter".equals(action)) {
            handleFilterRequest(request, response);
        } else if ("applyFilter".equals(action)) {
            handleApplyFilter(request, response);
        } else if ("clearFilter".equals(action)) {
            handleClearFilter(request, response);
        }
    }

    private void handleFilterRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO();

            List<ProductTypes> listType = categoryDAO.getAllProductTypes();
            List<Categories> listCategory = categoryDAO.getAllCategories();

            String[] currentCategoryIds = request.getParameterValues("currentCategoryIds");
            String[] currentTypeIds = request.getParameterValues("currentTypeIds");

            request.setAttribute("listType", listType);
            request.setAttribute("listCategory", listCategory);

            if (currentCategoryIds != null) {
                request.setAttribute("currentCategoryIds", Arrays.asList(currentCategoryIds));
            }
            if (currentTypeIds != null) {
                request.setAttribute("currentTypeIds", Arrays.asList(currentTypeIds));
            }

            request.getRequestDispatcher(FILTER_PANEL).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu filter");
        }
    }

    private void handleApplyFilter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String[] selectedCategoryIds = request.getParameterValues("categoryIds");
            String[] selectedTypeIds = request.getParameterValues("typeIds");

            StringBuilder redirectUrl = new StringBuilder("CashierServlet");
            List<String> params = new ArrayList<>();

            if (selectedCategoryIds != null && selectedCategoryIds.length > 0) {
                for (String categoryId : selectedCategoryIds) {
                    params.add("categoryIds=" + categoryId);
                }
            }

            if (selectedTypeIds != null && selectedTypeIds.length > 0) {
                for (String typeId : selectedTypeIds) {
                    params.add("typeIds=" + typeId);
                }
            }

            if (!params.isEmpty()) {
                redirectUrl.append("?").append(String.join("&", params));
            }

            response.sendRedirect(redirectUrl.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi áp dụng filter");
        }
    }

    private void handleClearFilter(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.sendRedirect("CashierServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xóa filter");
        }
    }

    private List<ProductMasters> getFilteredProducts(HttpSession session, String[] selectedCategoryIds, String[] selectedTypeIds) {
        List<ProductMasters> allProducts = (List<ProductMasters>) session.getAttribute("allProductsCache");

        if (allProducts == null || allProducts.isEmpty()) {
            ProductMasterDAO pDao = new ProductMasterDAO();
            allProducts = pDao.getData();
            session.setAttribute("allProductsCache", allProducts);
        }
        if ((selectedCategoryIds == null || selectedCategoryIds.length == 0)
                && (selectedTypeIds == null || selectedTypeIds.length == 0)) {
            return allProducts;
        }

        List<ProductMasters> filteredProducts = new ArrayList<>();

        List<String> categoryIdList = selectedCategoryIds != null
                ? Arrays.asList(selectedCategoryIds) : new ArrayList<>();
        List<String> typeIdList = selectedTypeIds != null
                ? Arrays.asList(selectedTypeIds) : new ArrayList<>();

        for (ProductMasters product : allProducts) {
            boolean matchesFilter = false;

            if (!categoryIdList.isEmpty() && product.getCategoryId() != 0) {
                if (categoryIdList.contains(String.valueOf(product.getCategoryId()))) {
                    matchesFilter = true;
                }
            }

            if (!typeIdList.isEmpty() && product.getTypeId() != 0) {
                if (typeIdList.contains(String.valueOf(product.getTypeId()))) {
                    matchesFilter = true;
                }
            }

            if (matchesFilter) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

}
