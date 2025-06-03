package salepro.controller.management.product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Date;
import salepro.dal.DBContext1;
import salepro.dal.DBContext2;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.UserDAO;
import salepro.models.up.ShopOwners;

/**
 *
 * @author ADMIN
 */
public class LoginController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nameshop = request.getParameter("nameshop");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
//         byte[] decodedBytes = Base64.getDecoder().decode(password);
//        String decoded = new String(decodedBytes);
        
        
        ShopOwnerDAO da = new ShopOwnerDAO();
        HttpSession session = request.getSession();
        if (da.checkShopOwner(nameshop, account, password)) {
            DBContext2.setCurrentDatabase(nameshop);
            session.setAttribute("currentShop", DBContext2.getCurrentDatabase());
            response.sendRedirect("view/jsp/Login.jsp");
        } else {
            if (da.checkExistShopOwner(nameshop)) {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
                request.setAttribute("shop", nameshop);
            }else{
                request.setAttribute("error", "Tên Shop ko tồn tại!");
                request.setAttribute("account", account);
            }
            request.getRequestDispatcher("view/jsp/LoginShopOwner.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String shop = request.getParameter("shop");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        ShopOwnerDAO da = new ShopOwnerDAO();
        String error = "";
        if (da.checkExistShopOwner(shop)) {
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            error += "Tên cửa hàng đã tồn tại";
        } else if (da.checkExistEmail(email)) {
            error += "Email đã tồn tại";
            request.setAttribute("storeName", shop);
            request.setAttribute("phone", phone);
        } else if (da.checkExistPhone(phone)) {
            error += "Số điện thoại đã tồn tại";
            request.setAttribute("storeName", shop);
            request.setAttribute("email", email);
        }
        if (!error.isEmpty()) {
            request.setAttribute("error", error);
            request.setAttribute("name", name);
            request.getRequestDispatcher("view/jsp/sign_up.jsp").forward(request, response);
        } else {
            String encoded = Base64.getEncoder().encodeToString(password.getBytes());
            ShopOwners newshop = new ShopOwners(shop, name, email, phone, encoded, 1, new Date());
            da.createShopOwner(newshop);
            response.sendRedirect("view/jsp/LoginShopOwner.jsp");
        }

    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
