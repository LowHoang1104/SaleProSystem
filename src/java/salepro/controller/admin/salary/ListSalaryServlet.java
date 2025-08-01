/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.salary;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeSalaryAssignmentDAO;
import salepro.dao.StoreDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.EmployeeSalaryAssignments;
import salepro.models.Employees;
import salepro.models.StoreFund;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListSalaryServlet", urlPatterns = {"/ListSalaryServlet"})
public class ListSalaryServlet extends HttpServlet {

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
            out.println("<title>Servlet ListSalaryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListSalaryServlet at " + request.getContextPath() + "</h1>");
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
        //Các DAO
        EmployeeSalaryAssignmentDAO salaryDao = new EmployeeSalaryAssignmentDAO();
        EmployeeDAO empDao = new EmployeeDAO();
        StoreDAO storeDao = new StoreDAO();


        //Danh sách tất cả Store 
        request.setAttribute("stores", storeDao.getData());

        //Lấy danh sách employee
        String storeIdStr = request.getParameter("storeId");
        List<Employees> employees;
        if (storeIdStr != null && !storeIdStr.isBlank()) {
            int storeId = Integer.parseInt(storeIdStr);
            request.setAttribute("storeId", storeId);
            employees = empDao.getEmployeeByStoreId(storeId);
        } else {
            employees = empDao.getData();
        }
        request.setAttribute("employees", employees);

        //Lấy salary theo employeeId
        // Tạo HashMap employeeId → salary
        Map<Integer, EmployeeSalaryAssignments> salaryMap = new HashMap<>();
        for (Employees emp : employees) {
            EmployeeSalaryAssignments salary = salaryDao.getSalaryByEmployeeId(emp.getEmployeeID());
            if (salary != null) {
                salaryMap.put(emp.getEmployeeID(), salary);
            }
        }
        request.setAttribute("salaryMap", salaryMap);
        request.getRequestDispatcher("view/jsp/admin/SalaryManagement/SalarySetup.jsp").forward(request, response);
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
        processRequest(request, response);
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
