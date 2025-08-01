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
import salepro.dao.PayrollCalculationDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.dao.StoreDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.PayrollCalculation;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "PayrollDetailServlet", urlPatterns = {"/PayrollDetailServlet"})
public class PayrollDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet PayrollDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayrollDetailServlet at " + request.getContextPath() + "</h1>");
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
        PayrollCalculationDAO payrollCalculationDAO = new PayrollCalculationDAO();
        EmployeeDAO empDao = new EmployeeDAO();
        StoreFundDAO storeFund = new StoreFundDAO();

        //Lấy danh sách quỹ 
        request.setAttribute("funds", storeFund.getData());
        
        //Lấy PayrollCaculation theo ParollPeriodId
        String payrollPeriodIdStr = request.getParameter("payrollPeriodId");
        int payrollPeriodId = Integer.parseInt(payrollPeriodIdStr);
        request.setAttribute("payrollPeriodId", payrollPeriodId);
        List<PayrollCalculation> payrollCalculations = payrollCalculationDAO.getPayrollCalculationByPayrollPeriodID(payrollPeriodId);
        request.setAttribute("payrollCalculations", payrollCalculations);
        
        request.getRequestDispatcher("view/jsp/admin/SalaryManagement/PayrollDetail.jsp").forward(request, response);
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
