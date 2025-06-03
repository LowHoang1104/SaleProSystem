/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import com.google.gson.Gson;
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
    
    private static final String CASHIER = "view/jsp/employees/Cashier.jsp";
    private static final String CASHIER1 = "view/jsp/employees/newjsp.jsp";
    private static final String CART_AJAX = "view/jsp/employees/cart_ajax.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession();
//        String action = request.getParameter("action");
        
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

        // Set attributes
        UserDAO userDAO = new UserDAO();
        List<Users> usersList = userDAO.getData();
        session.setAttribute("listUsers", usersList);
        
        request.setAttribute("products", pageProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("phoneNumber", "0996996996");
        
        request.getRequestDispatcher(CASHIER1).forward(request, response);
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
