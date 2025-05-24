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
import salepro.dao.ProductDAO;
import salepro.models.Products;
import salepro.models.up.CartItem;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "CashierServelet", urlPatterns = {"/CashierServelet"})
public class CashierServelet extends HttpServlet {

    private static final String LIST = "view/jsp/Cashier.jsp";
    private static final String LIST1 = "view/jsp/newjsp1.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LIST;
        try {

        } catch (Exception e) {

        } finally {

        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        ProductDAO pDao = new ProductDAO();
        List<Products> pList = pDao.getData();

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

        List<Products> pageProducts = pList.subList(startIndex, endIndex);

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

        request.getRequestDispatcher(LIST).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");
        String productName = request.getParameter("productName");
        HttpSession session = request.getSession();

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        
        if("addToCart".equals(action)){
            int id = Integer.parseInt(productId);
            String name = productName;
            double price2 = Double.parseDouble(price);
            
            boolean found =false;
            for(CartItem item : cart){
                if(item.getProductId() == id){
                    item.setQuantity(item.getQuantity()+1);
                    found = true;
                    break;
                }
            }
            
            if(!found){
                cart.add(new CartItem(id, productName, price2, 1));        
            }
            
        }else if("removeFromCart".equals(action)){
            int id = Integer.parseInt(productId);
            for(CartItem item : cart){
                if(item.getProductId() == id){
                    cart.remove(item);
                    break;
                }
            }
            
        }else if("updateQuantity".equals(action)){
            int id =Integer.parseInt(productId);
            int quantity2 = Integer.parseInt(quantity);
            
            for(CartItem item : cart){
                if(item.getProductId() == id){
                    if(quantity2 > 0){
                        item.setQuantity(quantity2);
                    }else{
                        cart.remove(item);
                    }
                    break;
                }
            }
        }else if("checkout".equals(action)){
            double totalAmount = 0;
            for(CartItem item : cart){
                totalAmount += item.getPrice()*item.getQuantity();
                
            }
            session.setAttribute("totalAmount", totalAmount);
            session.setAttribute("cart", cart);
            response.sendRedirect("view/jsp/payment.jsp");
            return;            
        }
        
       session.setAttribute("cart", cart);
       response.sendRedirect("CashierServelet");
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
