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
import salepro.dao.ProductMasterDAO;
import salepro.dao.UserDAO;
import salepro.models.ProductMasters;
import salepro.models.Users;
import salepro.models.up.CartItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CashierServelet", urlPatterns = {"/CashierServelet"})
public class CashierServelet extends HttpServlet {

    private static final String LIST = "view/jsp/employees/Cashier.jsp";
    private static final String LIST1 = "view/jsp/employees/cs1.jsp";

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

        // Set attributes
        request.setAttribute("products", pageProducts);
        request.setAttribute("cart", cart);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("phoneNumber", "0996996996");

        request.getRequestDispatcher(LIST1).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String productCode = request.getParameter("productCode");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");
        String productName = request.getParameter("productName");
        HttpSession session = request.getSession();

        ProductMasterDAO pDao = new ProductMasterDAO();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        if ("addToCart".equals(action)) {
            String code = productCode;
            String name = productName;
            double price2 = Double.parseDouble(price);

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProductCode().equalsIgnoreCase(code)) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(code, name, price2, 1));
            }

        } else if ("removeFromCart".equals(action)) {

            for (CartItem item : cart) {
                if (item.getProductCode().equalsIgnoreCase(productCode)) {
                    cart.remove(item);
                    break;
                }
            }

        } else if ("updateQuantity".equals(action)) {
            int quantity2 = Integer.parseInt(quantity);
            for (CartItem item : cart) {
                if (item.getProductCode().equalsIgnoreCase(productCode)) {
                    if (quantity2 > 0) {
                        item.setQuantity(quantity2);
                    } else {
                        cart.remove(item);
                    }
                    break;
                }
            }
        } else if ("checkout".equals(action)) {
//            double totalAmount = 0;
//            for (CartItem item : cart) {
//                totalAmount += item.getPrice() * item.getQuantity();
//
//            }
//            session.setAttribute("totalAmount", totalAmount);
//            session.setAttribute("cart", cart);
//            response.sendRedirect("view/jsp/payment.jsp");
//            return;
        } else if ("detailItem".equals(action)) {
//            Products p 
        }
        
        UserDAO userDAO = new UserDAO();
        List<Users> usersList = userDAO.getData();
        request.setAttribute("listUsers", usersList);
        session.setAttribute("cart", cart);
        response.sendRedirect("CashierServelet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
