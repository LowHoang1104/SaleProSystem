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
import java.util.List;
import salepro.dao.InventoryDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.ProductVariantDAO;
import salepro.models.up.CartItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    
    private static final String LIST = "view/jsp/employees/Cashier.jsp";
    private static final String LIST1 = "view/jsp/employees/newjsp.jsp";
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
        String action = request.getParameter("action");
        
        HttpSession session = request.getSession();
        
        ProductMasterDAO pDao = new ProductMasterDAO();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        if ("addToCart".equals(action)) {
            try {
                String code = request.getParameter("productCode");
                String name = request.getParameter("productName");
                int price = Integer.parseInt(request.getParameter("price"));
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
                        
                        if (stock == 0) {
                            // Hết hàng, set số lượng = 0 hoặc thông báo
                            item.setQuantity(0);
                            item.setStatus("Hết hàng");
                        } else if (quantity > stock) {
                            // Giới hạn số lượng max bằng tồn kho
                            item.setQuantity(stock);
                            item.setStatus(null);
                        } else {
                            // Cập nhật số lượng bình thường
                            item.setQuantity(quantity);
                            item.setStatus(null);
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
        double totalAmount = 0;
        double totalItems = 0;
        for (CartItem item : cart) {
            totalAmount += item.getPrice() * item.getQuantity();
            totalItems += item.getQuantity();
        }
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("totalItems", totalItems);
        session.setAttribute("cart", cart);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher(CART_AJAX).forward(request, response);
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
