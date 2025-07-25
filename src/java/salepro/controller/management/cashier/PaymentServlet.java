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
import salepro.dao.CustomerDAO;
import salepro.dao.FundTransactionDAO;
import salepro.dao.InventoryDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.InvoiceDetailDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.UserDAO;
import salepro.models.Customers;
import salepro.models.Inventories;
import salepro.models.Invoices;
import salepro.models.ProductTypes;
import salepro.models.Stores;
import salepro.models.Users;
import salepro.models.up.CartItem;
import salepro.models.up.InvoiceItem;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String PAYMENT_AJAX = "view/jsp/employees/payment_ajax.jsp";

    private static final double POINTS_TO_VND_RATIO = 1000.0; // 1 điểm = 1,000 VND
    private static final double VND_TO_POINTS_RATIO = 50000.0; // 50,000 VND = 1 điểm
    private static final double VAT_RATE = 0.10; // 10% VAT

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
            double subTotal = currentInvoice.getSubTotal();
            double discountAmount = subTotal * currentInvoice.getDiscount() / 100;
            double getAfterdiscountAmount = subTotal - discountAmount;
            currentInvoice.setAfterdiscountAmount(getAfterdiscountAmount);

            double VATAmount = getAfterdiscountAmount * VAT_RATE;
            currentInvoice.setVATAmount(VATAmount);

            double totalAmount = getAfterdiscountAmount + VATAmount;
            currentInvoice.setTotalAmount(totalAmount);
            currentInvoice.setPaidAmount(totalAmount);
            currentInvoice.resetShortChangeAmount();
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

                String paymentMethod = request.getParameter("paymentMethod");
                int paymentMethodId = Integer.parseInt(paymentMethod);

                String fundIdStr = request.getParameter("fundId");
                int storeFundId = Integer.parseInt(fundIdStr);

                int storeID = 1;
                Integer storeIDSession = (Integer) session.getAttribute("currentStoreID");
                if (storeIDSession != null) {
                    storeID = storeIDSession;
                }
                
                double totalAmount = currentInvoice.getTotalAmount();
                double subTotal = currentInvoice.getSubTotal();
                double discount = currentInvoice.getDiscount();
                double discountAmount = currentInvoice.getDiscountAmount();
                double VATAmount = currentInvoice.getVATAmount();
                double paidAmount = currentInvoice.getPaidAmount();

                InvoiceDAO idao = new InvoiceDAO();
                boolean success = idao.insertInvoice(storeID, userId, 1, customerId, totalAmount, subTotal, discount, discountAmount, VATAmount, paidAmount, paymentMethodId);

                if (success) {
                    createInvoiceDetail(currentInvoice);
                    createFundTransaction(currentInvoice.getPaidAmount(), storeFundId);

                    if (currentInvoice.getChangeAmount() > 0) {
                        createFundTransaction2(currentInvoice.getChangeAmount(), storeFundId);
                    }

                    updateQuantityProduct(cart);

                    if (customerId > 1) { // Chỉ cập nhật cho khách hàng có tài khoản
                        CustomerDAO customerDAO = new CustomerDAO();

                        // 1. Cập nhật tổng chi tiêu
                        customerDAO.updateCustomerTotalSpent(customerId, totalAmount);

                        // 2. Xử lý điểm đã sử dụng (nếu có) - TRỪ TRƯỚC
                        if (currentInvoice.getPointsUsed() > 0) {
                            customerDAO.deductPointsFromCustomer(customerId, currentInvoice.getPointsUsed());
                        }

                        // 3. Tính điểm hoàn trả từ tổng hóa đơn (1:50000) - SỬA LẠI
                        double cashbackPoints = totalAmount / 50000;

                        // 4. Nếu có điểm từ tiền thừa, cộng thêm
                        if (currentInvoice.getPointsToAdd() > 0) {
                            cashbackPoints += currentInvoice.getPointsToAdd();
                        }

                        if (cashbackPoints > 0) {
                            customerDAO.addPointsToCustomer(customerId, cashbackPoints);

                            System.out.println("=== POINTS CALCULATION (FIXED) ===");
                            System.out.println("Invoice amount: " + totalAmount);
                            System.out.println("Cashback points (1:50000): " + totalAmount / 50000);
                            System.out.println("Change points: " + currentInvoice.getPointsToAdd());
                            System.out.println("Total points added: " + cashbackPoints);
                            System.out.println("Points used: " + currentInvoice.getPointsUsed());
                            System.out.println("=================================");
                        }

                        // 5. Cập nhật rank dựa trên tổng chi tiêu mới
                        customerDAO.updateCustomerRank(customerId, customerDAO);
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
                double discountAmount = (currentInvoice.getSubTotal() * currentInvoice.getDiscount() / 100);
                currentInvoice.setDiscountAmount(discountAmount);

                double afterDiscountAmount = currentInvoice.getSubTotal() - discountAmount;
                currentInvoice.setAfterdiscountAmount(afterDiscountAmount);

                double VATAmount = afterDiscountAmount * VAT_RATE;
                currentInvoice.setVATAmount(VATAmount);

                double totalAmount = afterDiscountAmount + VATAmount;
                currentInvoice.setTotalAmount(totalAmount);
                currentInvoice.setPaidAmount(totalAmount);

                currentInvoice.resetShortChangeAmount();

                session.setAttribute("currentInvoice", currentInvoice);
                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            } else if ("updatePaidAmount".equals(action)) {
                String paidAmountStr = request.getParameter("paidAmount");
                Double paidAmount = Double.parseDouble(paidAmountStr);
                Double totalAmount = currentInvoice.getTotalAmount();

                // RESET trạng thái điểm
                currentInvoice.resetShortChangeAmount();

                // Lấy customerId và xử lý trường hợp null hoặc 0
                Integer customerId = currentInvoice.getCustomer() != null
                        ? currentInvoice.getCustomer().getCustomerId() : 0;

                System.out.println("customer ID: " + customerId);

                if (customerId == null || customerId == 0 || customerId == 1) {
                    if (paidAmount < 0) {
                        paidAmount = totalAmount;
                    }

                    // Không cho phép trả ít hơn tổng tiền với khách lẻ
                    if (paidAmount < totalAmount) {
                        paidAmount = totalAmount;
                    }

                    // Tính tiền thừa nếu có
                    if (paidAmount > totalAmount) {
                        double changeAmount = paidAmount - totalAmount;
                        currentInvoice.setChangeAmount(changeAmount);
                    }
                    currentInvoice.setPaidAmount(paidAmount);
                } // Khách hàng có tài khoản (customerId > 1) - Cho phép thiếu tiền
                else {
                    // Lấy điểm hiện tại từ database
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customers customer = customerDAO.findById(customerId);
                    System.out.println("points: " + customer.getPoints());
                    if (customer != null) {
                        currentInvoice.setCustomer(customer);
                        System.out.println("points: " + currentInvoice.getCustomer().getPoints());
                    }

                    if (paidAmount < 0) {
                        paidAmount = totalAmount;
                    }
                    if (paidAmount > totalAmount) {
                        double changeAmount = paidAmount - totalAmount;
                        currentInvoice.setChangeAmount(changeAmount);
                    } else if (paidAmount < totalAmount) {
                        double shortAmount = totalAmount - paidAmount;
                        currentInvoice.setShortAmount(shortAmount);
                    }
                    currentInvoice.setPaidAmount(paidAmount);
                }
                session.setAttribute("currentInvoice", currentInvoice);
                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            } else if ("usePoints".equals(action)) {
                Integer customerId = currentInvoice.getCustomer().getCustomerId();

                if (customerId != null && customerId > 1) {
                    // Lấy điểm thực tế từ database
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customers customer = customerDAO.findById(customerId);

                    if (customer != null) {
                        double currentPoints = customer.getPoints();
                        double shortAmount = currentInvoice.getShortAmount();

                        if (currentPoints > 0 && shortAmount > 0) {
                            // Tính toán điểm sử dụng (1 điểm = 1,000 VND)
                            double pointsToUse = Math.min(currentPoints, shortAmount / POINTS_TO_VND_RATIO);
                            double amountFromPoints = pointsToUse * POINTS_TO_VND_RATIO;

                            if (pointsToUse > 0) {
                                // Cập nhật số tiền thanh toán
                                double newPaidAmount = currentInvoice.getPaidAmount() + amountFromPoints;
                                double newShortAmount = Math.max(0, currentInvoice.getShortAmount() - amountFromPoints);

                                currentInvoice.setPaidAmount(newPaidAmount);
                                currentInvoice.setShortAmount(newShortAmount);

                                // Nếu hết tiền thiếu, kiểm tra tiền thừa
                                if (newShortAmount == 0 && newPaidAmount > currentInvoice.getTotalAmount()) {
                                    double changeAmount = newPaidAmount - currentInvoice.getTotalAmount();
                                    currentInvoice.setChangeAmount(changeAmount);
                                }

                                // Đánh dấu điểm đã sử dụng
                                currentInvoice.setPointsUsed(pointsToUse);

                                // Cập nhật điểm hiển thị trong customer object (để hiển thị đúng)
                                currentInvoice.getCustomer().setPoints(currentPoints - pointsToUse);

                                System.out.println("Used " + pointsToUse + " points = " + amountFromPoints + " VND");

                                session.setAttribute("currentInvoice", currentInvoice);
                                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                                return;
                            }
                        }
                    }
                }

                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            } else if ("addPoints".equals(action)) {
                Integer customerId = currentInvoice.getCustomer().getCustomerId();

                if (customerId != null && customerId > 1) {
                    double changeAmount = currentInvoice.getChangeAmount();

                    if (changeAmount > 0) {
                        // Tính điểm tích lũy từ tiền thừa (1,000 VND = 1 điểm)
                        double pointsToAdd = changeAmount / VND_TO_POINTS_RATIO;

                        if (pointsToAdd > 0) {
                            // Tính số tiền thừa còn lại sau khi đổi điểm
                            double amountUsedForPoints = pointsToAdd * VND_TO_POINTS_RATIO;
                            double remainingChange = changeAmount - amountUsedForPoints;

                            currentInvoice.setChangeAmount(remainingChange);

                            // Đánh dấu sẽ tích điểm từ tiền thừa
                            currentInvoice.setPointsToAdd(pointsToAdd);

                            // Cập nhật preview điểm (chỉ để hiển thị)
                            double currentPoints = currentInvoice.getCustomer().getPoints();
                            currentInvoice.getCustomer().setPoints(currentPoints + pointsToAdd);

                            System.out.println("Will add " + pointsToAdd + " points from " + amountUsedForPoints + " VND change");
                            System.out.println("Remaining change: " + remainingChange + " VND");

                            session.setAttribute("currentInvoice", currentInvoice);
                            request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                            return;
                        }
                    }
                }

                request.getRequestDispatcher(PAYMENT_AJAX).forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi xử lý thanh toán: " + e.getMessage());
            request.getSession().removeAttribute("message");
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

    private void updateQuantityProduct(List<CartItem> cart) {
        InventoryDAO iDao = new InventoryDAO();
        for (CartItem item : cart) {
            int newQuantity = item.getStock() - item.getQuantity();
            iDao.updateInventory(newQuantity, item.getProductVariantId(), 1);
        }
    }
}
