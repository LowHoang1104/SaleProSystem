/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
package salepro.controller.admin.rolepermissions;
========

package salepro.controller;
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import salepro.dao.CategoryPermissionDAO;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.PermissionDAO;
import salepro.models.CategoryPermissions;
import salepro.models.EmployeeTypes;
import salepro.models.Permissions;
========
import jakarta.servlet.http.HttpSession;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java

/**
 *
 * @author Thinhnt
 */
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
@WebServlet(name = "ListUserPermissionServlet", urlPatterns = {"/ListUserPermissionServlet"})
public class ListUserPermissionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
========
public class LoginOnwerShopController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java
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
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
            out.println("<title>Servlet ListRolePermissionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListRolePermissionServlet at " + request.getContextPath() + "</h1>");
========
            out.println("<title>Servlet LoginOnwerShopController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginOnwerShopController at " + request.getContextPath () + "</h1>");
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
            throws ServletException, IOException {
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();
        List<EmployeeTypes> listE = eDao.getAllEmployeeTypes();

        PermissionDAO pDao = new PermissionDAO();
        List<Permissions> listP = pDao.getAllPermissions();

        Map<Integer, List<Permissions>> userPemissions = new HashMap<>();
        for (EmployeeTypes employeeType : listE) {
            int empTypeId = employeeType.getEmployeeTypeID();
            userPemissions.put(empTypeId, pDao.getPermissionsByEmployeeType(empTypeId));
        }

        CategoryPermissionDAO cDao = new CategoryPermissionDAO();
        List<CategoryPermissions> categoryPer = cDao.getAllCategoryPermission();

        Map<Integer, List<Permissions>> perByCategory = new HashMap<>();
        for (CategoryPermissions categoryPermissions : categoryPer) {
            int categoryId = categoryPermissions.getCategoryId();
            perByCategory.put(categoryId, pDao.getAllPermissionByCategoryId(categoryId));
        }

        request.setAttribute("categories", categoryPer);
        request.setAttribute("perByCategory", perByCategory);
        request.setAttribute("userPemissions", userPemissions);
        request.setAttribute("employeeTypes", listE);
        request.setAttribute("permissions", listP);

        request.getRequestDispatcher("view/jsp/admin/RoleManagement/List_role.jsp").forward(request, response);

    }
========
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        CustomerDAO customerDA=new CustomerDAO();
        StoreDAO storeDA=new StoreDAO();
        PurchaseDAO purchaseDA=new PurchaseDAO();
        InvoiceDAO invoiceDA=new InvoiceDAO();
        
//mã hóa 
//        byte[] decodedBytes = Base64.getDecoder().decode(password);
//        password = new String(decodedBytes);
        if (login.equals("1")) {
            UserDAO userda = new UserDAO();
            if (userda.checkManager(account, password)) {               
                session.setAttribute("user",userda.getUserbyAccountAndPass(account,password));
                request.getRequestDispatcher("HomepageController").forward(request, response);
            }else{
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        } else if (login.equals("2")) {
            UserDAO userda= new UserDAO();           
            if(userda.checkCashier(account, password)){             
                response.sendRedirect("view/jsp/employees/Cashier.jsp");
            }else{
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        }else{
            response.sendRedirect("view/jsp/LoginShopOwner.jsp");
        }
    } 
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/ListUserPermissionServlet.java
            throws ServletException, IOException {
        processRequest(request, response);
========
    throws ServletException, IOException {
         
>>>>>>>> main:src/java/salepro/controller/LoginOnwerShopController.java
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
