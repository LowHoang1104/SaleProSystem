/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package salepro.controller.admin.rolepermissions;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/AddEmployeeTypeServlet.java
import java.util.Arrays;
import java.util.List;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.PermissionDAO;
========
import java.util.ArrayList;
import salepro.dao.PurchaseDAO;
import salepro.models.Purchases;
>>>>>>>> main:src/java/salepro/controller/PurchaseController.java

/**
 *
 * @author Thinhnt
 */
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/AddEmployeeTypeServlet.java
@WebServlet(name="AddEmployeeTypeServlet", urlPatterns={"/AddEmployeeTypeServlet"})
public class AddEmployeeTypeServlet extends HttpServlet {
========
public class PurchaseController extends HttpServlet {
>>>>>>>> main:src/java/salepro/controller/PurchaseController.java
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/AddEmployeeTypeServlet.java
            out.println("<title>Servlet AddEmployeeTypeServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddEmployeeTypeServlet at " + request.getContextPath () + "</h1>");
========
            out.println("<title>Servlet PurchaseController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseController at " + request.getContextPath () + "</h1>");
>>>>>>>> main:src/java/salepro/controller/PurchaseController.java
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
    throws ServletException, IOException {
<<<<<<<< HEAD:src/java/salepro/controller/admin/rolepermissions/AddEmployeeTypeServlet.java
        processRequest(request, response);
    } 
========
        PurchaseDAO purchaseDA= new PurchaseDAO();
        ArrayList<Purchases> data= new ArrayList<>();
        data= purchaseDA.getData();
        request.setAttribute("data",data);
>>>>>>>> main:src/java/salepro/controller/PurchaseController.java

        request.getRequestDispatcher("view/jsp/admin/PurchaseManager/listpurchase.jsp").forward(request, response);
    }
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String typeName = request.getParameter("typeName");
        String[] arrPermission = request.getParameterValues("permissionIDs");
        List<Integer> permissionIds = Arrays.stream(arrPermission).map(Integer:: parseInt).toList();
        
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();
        int eTypeId = eDao.insertEmployeeType(typeName);
        
        PermissionDAO pDao = new PermissionDAO();
        boolean success = pDao.insertPermissionForEmployeeType(eTypeId, permissionIds);
        
        request.setAttribute("addEmployeeType", success);
        request.getRequestDispatcher("view/jsp/admin/RoleManagement/List_role.jsp").forward(request, response);
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
