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
import java.util.List;
import salepro.dao.EmployeeDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.dao.StoreDAO;
import salepro.models.Employees;
import salepro.models.PayrollPeriods;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "PayrollServlet", urlPatterns = {"/PayrollServlet"})
public class PayrollServlet extends HttpServlet {

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
            out.println("<title>Servlet PayrollServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayrollServlet at " + request.getContextPath() + "</h1>");
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
        PayrollPeriodDAO payrollPeriodDao = new PayrollPeriodDAO();
        EmployeeDAO empDao = new EmployeeDAO();
        StoreDAO storeDao = new StoreDAO();
        //Danh sách tất cả Store 
        request.setAttribute("stores", storeDao.getData());
        //Lấy storeId
        String storeIdStr = request.getParameter("storeId");
        int storeId = 1;
        if (storeIdStr != null && !storeIdStr.isBlank()) {
            storeId = Integer.parseInt(storeIdStr);
        }
        request.setAttribute("storeId", storeId);
        //Lấy danh sách employee
        List<Employees> employees;
        employees = empDao.getEmployeeByStoreId(storeId);
        request.setAttribute("employees", employees);

        //Các bảng lương
        List<PayrollPeriods> payrollPeriods;
        payrollPeriods = payrollPeriodDao.getAllPayrollPeriodsByStoreId(storeId);
        request.setAttribute("payrollPeriods", payrollPeriods);
        request.getRequestDispatcher("view/jsp/admin/SalaryManagement/Payroll.jsp").forward(request, response);
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
