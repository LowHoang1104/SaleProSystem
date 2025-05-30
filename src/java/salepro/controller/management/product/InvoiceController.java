/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
package salepro.controller.admin.customers;
========

package salepro.controller.management.product;
>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
import java.util.List;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;
========
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import salepro.dao.InvoiceDAO;
import salepro.models.Invoices;
>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java

/**
 *
 * @author Thinhnt
 */
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
@WebServlet(name = "FilterCustomerServlet", urlPatterns = {"/FilterCustomerServlet"})
public class FilterCustomerServlet extends HttpServlet {
========
public class InvoiceController extends HttpServlet {
>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java

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
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
            out.println("<title>Servlet FilterCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterCustomerServlet at " + request.getContextPath() + "</h1>");
========
            out.println("<title>Servlet InvoiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceController at " + request.getContextPath() + "</h1>");
>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java
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
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
        processRequest(request, response);
    }

========
        InvoiceDAO da = new InvoiceDAO();
//        try (PrintWriter out = response.getWriter()) {
//            out.print(da.getData().size());
//        }
        request.setAttribute("data", da.getData());

        request.getRequestDispatcher("view/jsp/admin/InvoiceManager/invoicelist.jsp").forward(request, response);
    }

>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java
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
<<<<<<<< HEAD:src/java/salepro/controller/admin/customers/FilterCustomerServlet.java
        String fullName = request.getParameter("customerName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");

        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("gender", gender);

        CustomerDAO dao = new CustomerDAO();
        List<Customers> filteredList = dao.filterCustomers(fullName, email, phone, gender);
        request.setAttribute("listCustomer", filteredList);
        request.getRequestDispatcher("view/jsp/admin/CustomerManagement/List_customer.jsp").forward(request, response);

========
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        java.sql.Date date = java.sql.Date.valueOf(request.getParameter("date"));
        int stores = Integer.parseInt(request.getParameter("stores"));
        InvoiceDAO da= new InvoiceDAO();
        if(request.getParameter("update")!=null){
            //update invoice ở đây
        }
        
>>>>>>>> main:src/java/salepro/controller/management/product/InvoiceController.java
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
