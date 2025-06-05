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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import salepro.dao.InventoryDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.ProductVariantDAO;
import salepro.models.up.CartItem;
import salepro.models.up.InvoiceItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {

    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String CART_AJAX = "view/jsp/employees/cart_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
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
        try {
            HttpSession session = request.getSession();

            String action = request.getParameter("action");
            Integer invoiceId = (Integer) session.getAttribute("currentInvoiceId");

            List<InvoiceItem> invoices = (List<InvoiceItem>) session.getAttribute("invoices");

            if (invoices == null) {
                invoices = new ArrayList<>();
                session.setAttribute("invoices", invoices);
            }

            InvoiceItem currentInvoice = null;
            for (InvoiceItem invoice : invoices) {
                if (invoice.getId() == invoiceId) {
                    currentInvoice = invoice;
                    break;
                }
            }

            if (currentInvoice == null) {
                currentInvoice = new InvoiceItem(invoiceId, "Hóa đơn " + invoiceId);
                invoices.add(currentInvoice);
            }

            List<CartItem> cart = currentInvoice.getCartItems();
            if (cart == null) {
                cart = new ArrayList<>();
                currentInvoice.setCartItems(cart);
            }

            if ("addToCart".equals(action)) {

                try {
                    String code = request.getParameter("productCode");
                    String name = request.getParameter("productName");
                    Integer price = Integer.parseInt(request.getParameter("price"));
                    boolean found = false;
                    for (CartItem item : cart) {
                        if (item.getProductCode().equals(code)) {
                            item.setQuantity(item.getQuantity() + 1);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        cart.add(new CartItem(code, name, price, 1));
                    }
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price");
                    return;
                }
            } else if ("removeFromCart".equals(action)) {
                String removeCode = request.getParameter("productCode");
                cart.removeIf(item -> item.getProductCode().equals(removeCode));
            } else if ("updateQuantity".equals(action)) {
                try {
                    String updateCode = request.getParameter("productCode");
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    for (CartItem item : cart) {
                        if (item.getProductCode().equals(updateCode)) {
                            int stock = item.getStock();
                            if (quantity < 0) {
                                item.setQuantity(1);
                                item.setStatus(null);
                            }

                            if (stock != 0) {
                                if (quantity > stock) {

                                    item.setQuantity(stock);
                                    item.setStatus("Full_quantity");
                                } else {

                                    item.setQuantity(quantity);
                                    item.setStatus(null);
                                }
                            } else {
                                item.setStatus("Need_Size_And_Color");
                            }

                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity");
                    return;
                }
            } else if ("updateVariant".equals(action)) {
                String itemCode = request.getParameter("productCode");
                String variantType = request.getParameter("variantType");
                String selectValue = request.getParameter("selectValue");
                InventoryDAO idao = new InventoryDAO();
                ProductVariantDAO productVariant = new ProductVariantDAO();
                int variantId = 0;
                for (CartItem item : cart) {
                    if (item.getProductCode().equals(itemCode)) {
                        if ("size".equals(variantType)) {
                            item.setSize(selectValue);
                        } else if ("color".equals(variantType)) {
                            item.setColor(selectValue);
                        }

                        if (item.getSize() != null && item.getColor() != null) {
                            variantId = productVariant.getProductVariantId(itemCode, item.getSize(), item.getColor());
                        }

                        if (variantId != 0) {
                            int stock = idao.getQuantityByWarehouseAndVariant(1, variantId);
                            item.setStock(stock);

                            if (stock == 0) {
                                item.setQuantity(0);
                                item.setStatus("Hết hàng");
                            } else {
                                if (item.getQuantity() > stock) {
                                    item.setQuantity(stock);
                                }
                                item.setStatus(null);
                            }
                        }
                    }
                }
            }

            currentInvoice.setCartItems(cart);
            currentInvoice.updateTotalAmountAndItems();

            session.setAttribute("invoices", invoices);

            request.setAttribute("cart", cart);
            request.setAttribute("totalAmount", currentInvoice.getTotalAmount());
            request.setAttribute("totalItem", currentInvoice.getTotalItem());

            request.getRequestDispatcher(CART_AJAX).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
