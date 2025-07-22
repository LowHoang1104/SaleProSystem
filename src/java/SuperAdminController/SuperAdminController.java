/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SuperAdminController;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import salepro.dao.ShopOwnerDAO;
import salepro.models.SuperAdmin.ShopOwner;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SuperAdminController", urlPatterns = {"/SuperAdminController"})
public class SuperAdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SuperAdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SuperAdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        ShopOwnerDAO da = new ShopOwnerDAO();
        HttpSession session = request.getSession();
        session.setAttribute("listshopowner", da.getData());
        response.sendRedirect("view/jsp/superadmin/Homepage.jsp");
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
        String shopName = request.getParameter("shopName");
        String ownerName = request.getParameter("ownerName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String isActive = request.getParameter("isActive");
        String op = request.getParameter("op");
        String error = "";
        ShopOwnerDAO da = new ShopOwnerDAO();

        if (op.equals("addShopOwner")) {
            if (da.checkExistShopOwner(shopName)) {
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                error += "Tên cửa hàng đã tồn tại";
            } else if (da.checkExistEmail(email)) {
                error += "Email đã tồn tại";
                request.setAttribute("storeName", shopName);
                request.setAttribute("phone", phone);
            } else if (da.checkExistPhone(phone)) {
                error += "Số điện thoại đã tồn tại";
                request.setAttribute("storeName", shopName);
                request.setAttribute("email", email);
            } else if (!(password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*"))) {
                error = "Sai format password"
                        + "<br>có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số";
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("storeName", shopName);
            }
            if (!error.isEmpty()) {
                response.getWriter().write(error + " " + isActive);
            } else {
                String encoded = Base64.getEncoder().encodeToString(password.getBytes());
                ShopOwner newshop = new ShopOwner(shopName, ownerName, email, phone, encoded, Integer.parseInt(isActive), new Date());
                da.createShopOwner(newshop);
                response.getWriter().write("OK");

            }
        } else if (op.equals("updateShopOwner")) {
            String id = request.getParameter("id");
            ShopOwner temp = da.getShopOwnerById(Integer.parseInt(id));
            if (!temp.getShopName().equals(shopName)) {
                if (da.checkExistShopOwner(shopName)) {
                    request.setAttribute("phone", phone);
                    request.setAttribute("email", email);
                    error += "Tên cửa hàng đã tồn tại";
                }
            } else if (!temp.getEmail().equals(email)) { 
                if (da.checkExistEmail(email)) {
                error += "Email đã tồn tại";
                request.setAttribute("storeName", shopName);
                request.setAttribute("phone", phone);
                }
            } else if (!temp.getPhone().equals(phone)) {  
                if (da.checkExistPhone(phone)) {
                error += "Số điện thoại đã tồn tại";
                request.setAttribute("storeName", shopName);
                request.setAttribute("email", email);
                }
            } else if (!(password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*"))) {
                error = "Sai format password"
                        + "<br>có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số";
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("storeName", shopName);
            }
            if (!error.isEmpty()) {
                response.getWriter().write(error + " " + isActive);
            } else {
                String encoded = Base64.getEncoder().encodeToString(password.getBytes());
                ShopOwner newshop = new ShopOwner(shopName, ownerName, email, phone, encoded, Integer.parseInt(isActive), new Date());
                ShopOwner a= new ShopOwner(shopName, ownerName, email, phone, password, Integer.parseInt(isActive), new Date());
                da.updateShopOwner(a,temp.getShopName());
                response.getWriter().write("OK");

            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
