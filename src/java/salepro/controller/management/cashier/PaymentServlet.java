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
import java.util.List;
import salepro.dao.FundTransactionDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.InvoiceDetailDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.UserDAO;
import salepro.models.Customers;
import salepro.models.Invoices;
import salepro.models.ProductTypes;
import salepro.models.Users;
import salepro.models.up.CartItem;
import salepro.models.up.InvoiceItem;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String PAYMENT_AJAX = "view/jsp/employees/payment_ajax.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("getPaymentInfo".equals(action)) {

            HttpSession session = request.getSession();

            Integer currentInvoiceId = (Integer) session.getAttribute("currentInvoiceId");

            List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");

            InvoiceItem currentInvoice = null;
            for (InvoiceItem invoice : invoices) {
                if (invoice.getId() == currentInvoiceId) {
                    currentInvoice = invoice;
                    break;
                }
            }

            currentInvoice.updateOriginalAmountAndItems();
            double originalAmount = currentInvoice.getOriginalAmount();
            currentInvoice.setOriginalAmount(originalAmount);

            double subTotal = originalAmount - (originalAmount * currentInvoice.getDiscount() / 100);
            currentInvoice.setSubTotal(subTotal);

            double VATAmount = (subTotal * 10) / 100;
            currentInvoice.setVATAmount(VATAmount);

            double totalAmount = subTotal + VATAmount;
            currentInvoice.setTotalAmount(totalAmount);
            currentInvoice.setPaidAmount(totalAmount);

            session.setAttribute("currentInvoice", currentInvoice);
            request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String action = request.getParameter("action");

            InvoiceItem currentInvoice = (InvoiceItem) session.getAttribute("currentInvoice");
            Integer currentInvoiceIdObj = (Integer) session.getAttribute("currentInvoiceId");
            int currentInvoiceId = currentInvoiceIdObj.intValue();
            List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");

            if ("checkout".equals(action)) {

                List<CartItem> cart = currentInvoice.getCartItems();
                if (cart == null || cart.isEmpty()) {
                    session.setAttribute("error", "Giỏ hàng đang trống. Vui lòng thêm sản phẩm.");
                    response.sendRedirect("CashierServlet");
                    return;
                }

                for (CartItem item : cart) {
                    if (item.getColor() == null || item.getSize() == null) {
                        session.setAttribute("error", "Sản phẩm " + item.getProductName() + " thiếu thông tin size hoặc màu.");
                        response.sendRedirect("CashierServlet");
                        return;
                    }
                }

                Users user = currentInvoice.getUser();
                if (user.getUserId() == 0) {
                    user.setUserId(1);
                }
                int userId = user.getUserId();

                Customers customer = currentInvoice.getCustomer();
                if (customer.getCustomerId() == 0) {
                    customer.setCustomerId(1);
                }
                int customerId = customer.getCustomerId();
                int storeID = 1;

                String paymentMethod = request.getParameter("paymentMethod");
                int paymentMethodId = Integer.parseInt(paymentMethod);

                double totalAmount = currentInvoice.getTotalAmount();
                double subTotal = currentInvoice.getSubTotal();
                double VATAmount = currentInvoice.getVATAmount();
                InvoiceDAO idao = new InvoiceDAO();
                boolean success = idao.insertInvoice(storeID, userId, customerId, totalAmount, subTotal, VATAmount, paymentMethodId);
                if (success) {
                    createInvoiceDetail(currentInvoice);
                    int storeFundId = 1;
                    createFundTransaction(currentInvoice.getPaidAmount(), storeFundId);
                    
                    if (currentInvoice.getChangeAmount() > 0) {
                        createFundTransaction2(currentInvoice.getChangeAmount(), storeFundId);
                    }
                    if (invoices.size() == 1) {
                        currentInvoice.setCartItems(null);
                        currentInvoice.resetCart();
                    } else {
                        invoices.removeIf(invoice -> invoice.getId() == currentInvoiceId);
                        session.setAttribute("invoices", invoices);
                        if (!invoices.isEmpty()) {
                            InvoiceItem nextInvoice = invoices.get(invoices.size() - 1);
                            session.setAttribute("currentInvoice", nextInvoice);
                            session.setAttribute("currentInvoiceId", nextInvoice.getId());
                        }
                    }

                    session.setAttribute("currentInvoice", currentInvoice);
                    session.setAttribute("message", "Thanh toán thành công!");
                    session.setAttribute("error", null);
                    session.removeAttribute("invoiceSaleId");
                    response.sendRedirect("CashierServlet");
                    return;
                } else {
                    response.sendRedirect("CashierServlet");
                    session.setAttribute("error", "Không thể tạo hóa đơn. Vui lòng thử lại.");
                    return;
                }
            } else if ("updateInvoiceSaleId".equals(action)) {
                String staffId = request.getParameter("staffId");
                if (staffId != null && !staffId.trim().isEmpty()) {
                    int employeeIdAjax = Integer.parseInt(staffId);
                    currentInvoice.getUser().setUserId(employeeIdAjax);
                    session.setAttribute("currentInvoice", currentInvoice);
                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } else if ("updateDiscount".equals(action)) {
                String discountStr = request.getParameter("discount");
                double discount = 0;
                if (discountStr != null && !discountStr.trim().isEmpty()) {
                    try {
                        discount = Double.parseDouble(discountStr);
                        if (discount < 0) {
                            discount = 0;
                        }
                        if (discount > 100) {
                            discount = 100;
                        }
                    } catch (NumberFormatException e) {
                        discount = 0;
                    }
                } else {
                    discount = 0;
                }
                currentInvoice.setDiscount(discount);

                double subTotal = currentInvoice.getOriginalAmount() - (currentInvoice.getOriginalAmount() * currentInvoice.getDiscount() / 100);
                currentInvoice.setSubTotal(subTotal);

                double VATAmount = (subTotal * 10) / 100;
                currentInvoice.setVATAmount(VATAmount);

                double totalAmount = subTotal + VATAmount;
                currentInvoice.setTotalAmount(totalAmount);
                currentInvoice.setPaidAmount(totalAmount);
                currentInvoice.setChangeAmount(0);
                session.setAttribute("currentInvoice", currentInvoice);
                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            } else if ("updatePaidAmount".equals(action)) {
                String paidAmountStr = request.getParameter("paidAmount");
                Double paidAmount = Double.parseDouble(paidAmountStr);

                Double totalAmount = currentInvoice.getTotalAmount();

                if (paidAmount < 0 || paidAmount < totalAmount) {
                    paidAmount = totalAmount;
                    currentInvoice.setPaidAmount(paidAmount);
                    session.setAttribute("currentInvoice", currentInvoice);
                } else if (paidAmount > totalAmount) {
                    double changeAmount = paidAmount - totalAmount;
                    currentInvoice.setPaidAmount(paidAmount);
                    currentInvoice.setChangeAmount(changeAmount);
                    session.setAttribute("currentInvoice", currentInvoice);
                }

                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi xử lý thanh toán: " + e.getMessage());
            response.sendRedirect("CashierServlet");

        }
    }

    private void createInvoiceDetail(InvoiceItem invoiceItem) {
        InvoiceDAO iDao = new InvoiceDAO();
        InvoiceDetailDAO iDDao = new InvoiceDetailDAO();
        ProductVariantDAO pVDao = new ProductVariantDAO();

        int invoiceId = iDao.getInvoiceIdMax();
        int productVariantId = 0;
        List<CartItem> cart = invoiceItem.getCartItems();

        for (CartItem item : cart) {
            productVariantId = pVDao.getProductVariantId(item.getProductCode(), item.getSize(), item.getColor());
            iDDao.insert(invoiceId, productVariantId, item.getQuantity(), item.getPrice(), invoiceItem.getDiscount());
        }
    }

    private void createFundTransaction(double amount, int storeFundId) {
        InvoiceDAO iDao = new InvoiceDAO();
        FundTransactionDAO fDao = new FundTransactionDAO();
        int invoiceId = iDao.getInvoiceIdMax();
        Invoices invoice = iDao.getInvoiceById(invoiceId);

        boolean succ = fDao.insertFundTransactionWithInvoice(storeFundId, amount, invoice);
    }

    private void createFundTransaction2(double amount, int storeFundId) {
        InvoiceDAO iDao = new InvoiceDAO();
        FundTransactionDAO fDao = new FundTransactionDAO();
        int invoiceId = iDao.getInvoiceIdMax();
        Invoices invoice = iDao.getInvoiceById(invoiceId);

        boolean succ = fDao.insertFundTransactionWithInvoice2(storeFundId, amount, invoice);
    }

    private int mapPaymentMethodToId(String method) {
        if (method == null) {
            return 0;
        }
        switch (method.toLowerCase()) {
            case "cash":
                return 1;
            case "transfer":
                return 2;
            case "card":
                return 3;
            case "wallet":
                return 4;
            default:
                return 0;
        }
    }

}
