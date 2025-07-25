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
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import salepro.models.up.InvoiceItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "HeaderServlet", urlPatterns = {"/HeaderServlet"})
public class HeaderServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String CART_AJAX = "view/jsp/employees/cart_ajax.jsp";
    private static final String HEADER_AJAX = "view/jsp/employees/header_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HeaderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HeaderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if ("addInvoice".equals(action)) {
            List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");

            int newId = findNextAvailableId(invoices);
            invoices.add(new InvoiceItem(newId, "Hóa đơn " + newId));
//            Collections.sort(invoices, Comparator.comparingInt(InvoiceItem::getId));
            session.setAttribute("invoices", invoices);
            session.setAttribute("currentInvoiceId", newId);
            request.getRequestDispatcher(HEADER_AJAX).forward(request, response);
        } else if ("removeInvoice".equals(action)) {
            int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
            List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");

            if (invoices.size() <= 1) {
                session.setAttribute("invoices", invoices);
                request.getRequestDispatcher(HEADER_AJAX).forward(request, response);
                return;
            }
            Integer currentInvoiceId = (Integer) session.getAttribute("currentInvoiceId");

            if (currentInvoiceId != null && currentInvoiceId == invoiceId) {
                InvoiceItem nextInvoice = getNextInvoice(invoices, invoiceId);
                if (nextInvoice != null) {
                    session.setAttribute("currentInvoiceId", nextInvoice.getId());
                } else {
                    InvoiceItem prevInvoice = getPrevInvoice(invoices, invoiceId);
                    if (prevInvoice != null) {
                        session.setAttribute("currentInvoiceId", prevInvoice.getId());
                    }
                }
            }
            invoices.removeIf(inv -> inv.getId() == invoiceId);
            session.setAttribute("invoices", invoices);
            request.getRequestDispatcher(HEADER_AJAX).forward(request, response);
        } else if ("selectInvoice".equals(action)) {
            int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
            session.setAttribute("currentInvoiceId", invoiceId);
            request.getRequestDispatcher(HEADER_AJAX).forward(request, response);
        }else if("changeStore".equals(action)){
            String storeIdStr = request.getParameter("storeId");
            int storeId = Integer.parseInt(storeIdStr);
            session.setAttribute("currentStoreID", storeId);
        }
    }

    private int findNextAvailableId(List<InvoiceItem> invoices) {
        Set<Integer> existingIds = new HashSet<>();
        for (InvoiceItem invoice : invoices) {
            existingIds.add(invoice.getId());
        }

        int newId = 1;
        while (existingIds.contains(newId)) {
            newId++;
        }
        return newId;
    }

    private InvoiceItem getNextInvoice(List<InvoiceItem> invoices, int currentInvoiceId) {
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getId() == currentInvoiceId && i + 1 < invoices.size()) {
                return invoices.get(i + 1);
            }
        }
        return null;
    }

    private InvoiceItem getPrevInvoice(List<InvoiceItem> invoices, int currentInvoiceId) {
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getId() == currentInvoiceId && i - 1 >= 0) {
                return invoices.get(i - 1);
            }
        }
        return null;
    }

}
