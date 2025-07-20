/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.salary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import salepro.dao.PayrollCalculationDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.models.PayrollPeriods;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SavePayrollServlet", urlPatterns = {"/SavePayrollServlet"})
public class SavePayrollServlet extends HttpServlet {

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
            out.println("<title>Servlet SavePayrollServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SavePayrollServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        // Các DAO 
        PayrollPeriodDAO payrollPeriodDAO = new PayrollPeriodDAO();
        PayrollCalculationDAO payrollCalculationDAO = new PayrollCalculationDAO();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if ("saveSalaryRecord".equals(action)) {
            try {
                // Lấy dữ liệu từ request
                String typeMonthlySalary = request.getParameter("period");
                String startDateStr = request.getParameter("startDate"); // "2025-07-01"
                String endDateStr = request.getParameter("endDate");     // "2025-07-31"

                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate endDate = LocalDate.parse(endDateStr);

                //Tạo bảng lương
                PayrollPeriods payrollPeriods = payrollPeriodDAO.insertPayrollPeriod(typeMonthlySalary, startDate, endDate);
                int payrollPeriodId = payrollPeriods.getPayrollPeriodId();
                // Lấy danh sách employeeIds từ JSON
                String employeeIdsJson = request.getParameter("employeeIds");
                Gson gson = new Gson();
                List<Integer> employeeIds = gson.fromJson(employeeIdsJson, new TypeToken<List<Integer>>() {
                }.getType());

                boolean allSuccess = true;
                for (Integer empId : employeeIds) {
                    boolean success = payrollCalculationDAO.insertPayrollCalculation(empId, payrollPeriodId);
                    if (!success) {
                        allSuccess = false;
                        break;
                    }
                }
                if (allSuccess) {
                    out.print("success");
                } else {
                    out.print("insert_failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
                out.print("error:" + e.getMessage());
            }

        } else {
            out.print("invalid_action");
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
