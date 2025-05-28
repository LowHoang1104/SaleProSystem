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
import org.apache.catalina.User;
import salepro.dao.CustomerDAO;
import salepro.dao.InventoryDAO;
import salepro.dao.ProductMasterDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.UserDAO;
import salepro.models.Customers;
import salepro.models.ProductMasters;
import salepro.models.Users;
import salepro.models.up.CartItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CashierServlet", urlPatterns = {"/CashierServlet"})
public class CashierServlet extends HttpServlet {

    private static final String LIST = "view/jsp/employees/Cashier.jsp";
    private static final String LIST1 = "view/jsp/employees/newjsp.jsp";
    private static final String CART_AJAX = "view/jsp/employees/cart_ajax.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
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

        double totalAmount = 0;
        double totalItems = 0;
        for (CartItem item : cart) {
            totalAmount += item.getPrice() * item.getQuantity();
            totalItems += item.getQuantity();
        }

        String message = (String) session.getAttribute("message");
        String error = (String) session.getAttribute("error");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        // Set attributes
        UserDAO userDAO = new UserDAO();
        List<Users> usersList = userDAO.getData();

        request.setAttribute("listUsers", usersList);

        request.setAttribute("products", pageProducts);
        request.setAttribute("cart", cart);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("phoneNumber", "0996996996");

        request.getRequestDispatcher(LIST).forward(request, response);
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

        switch (action) {
            case "addToCart":
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
            break;

            case "removeFromCart":
                String removeCode = request.getParameter("productCode");
                cart.removeIf(item -> item.getProductCode().equals(removeCode));
                break;

            case "updateQuantity":
            try {
                String updateCode = request.getParameter("productCode");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                for (CartItem item : cart) {
                    if (item.getProductCode().equals(updateCode)) {
                        item.setQuantity(quantity);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity");
                return;
            }
            break;

            // Xử lý thêm các action khác...
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
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

//        if ("addToCart".equals(action)) {
//            String code = productCode;
//            String name = productName;
//            double price2 = Double.parseDouble(price);
//
//            boolean found = false;
//            for (CartItem item : cart) {
//                if (item.getProductCode().equals(code)) {
//                    item.setQuantity(item.getQuantity() + 1);
//                    found = true;
//                    break;
//                }
//            }
//
//            if (!found) {
//                cart.add(new CartItem(code, name, price2, 1));
//            }
//
//        } else if ("removeFromCart".equals(action)) {
//
//            for (CartItem item : cart) {
//                if (item.getProductCode().equalsIgnoreCase(productCode)) {
//                    cart.remove(item);
//                    break;
//                }
//            }
//
//        } else if ("updateQuantity".equals(action)) {
//            int quantity2 = Integer.parseInt(quantity);
//            for (CartItem item : cart) {
//                if (item.getProductCode().equalsIgnoreCase(productCode)) {
//                    if (quantity2 > 0) {
//                        item.setQuantity(quantity2);
//                    } else {
//                        cart.remove(item);
//                    }
//                    break;
//                }
//            }
//        } else if ("updateVariant".equals(action)) {
//            String itemCode = request.getParameter("itemCode");
//            String variantType = request.getParameter("variantType");
//            String selectedValue = request.getParameter("selectedValue");
//            InventoryDAO inventoryDAO = new InventoryDAO();
//            ProductVariantDAO pvDA = new ProductVariantDAO();
//            int variantId;
//            for (CartItem item : cart) {
//                if (item.getProductCode().equals(itemCode)) {
//                    if ("size".equals(variantType)) {
//                        item.setSize(selectedValue);
//                    } else if ("color".equals(variantType)) {
//                        item.setColor(selectedValue);
//                    }
//                    if (item.getSize() != null && !item.getSize().isEmpty()
//                            && item.getColor() != null && !item.getColor().isEmpty()) {
//                        variantId = pvDA.getProductVariantId(itemCode, item.getSize(), item.getColor());
//
//                        if (variantId != 0) {
//                            int stock = inventoryDAO.getQuantityByWarehouseAndVariant(1, variantId);
//                            item.setStock(stock);
//
//                            if (stock == 0) {
//                                item.setQuantity(0);
//                                item.setStatus("Hết hàng");
//                            } else {
//
//                                if (item.getQuantity() > stock) {
//                                    item.setQuantity(stock);
//                                }
//                                item.setStatus(null); 
//                            }
//                        }
//                    }
//
//                    break;
//                }
//            }
//
//        } else if ("detailItem".equals(action)) {
////            Products p 
//        }
//
//        session.setAttribute("cart", cart);
//        request.getRequestDispatcher(CART_AJAX).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
