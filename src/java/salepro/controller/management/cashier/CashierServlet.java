/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import salepro.dao.ProductMasterDAO;

import salepro.dao.UserDAO;

import salepro.models.ProductMasters;
import salepro.models.Users;
import salepro.models.up.InvoiceItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CashierServlet", urlPatterns = {"/CashierServlet"})
public class CashierServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String CART_AJAX = "view/jsp/employees/cart_ajax.jsp";
    private static final String HEADER_AJAX = "view/jsp/employees/header_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

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

        ProductMasterDAO pDao = new ProductMasterDAO();
        List<ProductMasters> pList = pDao.getData();

        // Phan trang
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

        session.setAttribute("invoices", invoices);

        session.setAttribute("listUsers", usersList);
        request.setAttribute("products", pageProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        session.setAttribute("phoneNumber", "0996996996");
        request.getRequestDispatcher(CASHIER).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
