/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.customers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListCustomerServlet", urlPatterns = {"/ListCustomerServlet"})
public class ListCustomerServlet extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 10;

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
            out.println("<title>Servlet ListCustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCustomerServlet at " + request.getContextPath() + "</h1>");
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
        CustomerDAO daoCustomer = new CustomerDAO();
        //Phân trang
        List<Customers> allCustomers = daoCustomer.getData();
        int totalRecords = allCustomers.size();

        int recordsPerPage = 10;
        int currentPage = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            currentPage = Integer.parseInt(pageStr);
        }

        int startIndex = (currentPage - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);

        // Lấy sublist từ list gốc
        List<Customers> customers = allCustomers.subList(startIndex, endIndex);
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        request.setAttribute("currentPage", pageStr);
        request.setAttribute("totalPages", totalPages);

        // Gửi listUser sang JSP
        request.setAttribute("listCustomer", customers);

        // Forward đến form add user (ví dụ: List_customer.jsp)
        request.getRequestDispatcher("view/jsp/admin/CustomerManagement/List_customer.jsp").forward(request, response);
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
        CustomerDAO daoUser = new CustomerDAO();
        List<Customers> listCustomer = daoUser.getData();

        // Gửi listUser sang JSP
        request.setAttribute("listCustomer", listCustomer);

        //Kiểm tra add thành công hay thất bại.
        String addSuccess = request.getParameter("addSuccess");
        if (addSuccess != null && !addSuccess.isBlank()) {
            request.setAttribute("addSuccess", addSuccess);
        }

        //Kiểm tra add thành công hay thất bại.
        String updateSuccess = request.getParameter("updateSuccess");
        if (addSuccess != null && !addSuccess.isBlank()) {
            request.setAttribute("updateSuccess", updateSuccess);
        }
        // Forward đến form add user (ví dụ: List_customer.jsp)
        request.getRequestDispatcher("view/jsp/admin/CustomerManagement/List_customer.jsp").forward(request, response);
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
