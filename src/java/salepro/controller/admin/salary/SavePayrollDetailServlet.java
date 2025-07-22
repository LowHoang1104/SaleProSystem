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
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import salepro.dao.AttendanceDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeSalaryAssignmentDAO;
import salepro.dao.FundTransactionDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PayrollCalculationDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.dao.UserDAO;
import salepro.models.EmployeeSalaryAssignments;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SavePayrollDetailServlet", urlPatterns = {"/SavePayrollDetailServlet"})
public class SavePayrollDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet SavePayrollDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SavePayrollDetailServlet at " + request.getContextPath() + "</h1>");
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
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        EmployeeSalaryAssignmentDAO employeeSalaryAssignmentDAO = new EmployeeSalaryAssignmentDAO();
        FundTransactionDAO fundTransactionDAO = new FundTransactionDAO();
        InvoiceDAO invoiceDao = new InvoiceDAO();

        //Lấy session của user
        HttpSession session = request.getSession();
        session.setAttribute("user", new UserDAO().getUserById(1));
        Users user = (Users) session.getAttribute("user");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if ("ResetPayrollDetail".equals(action)) {
            try {
                // Lấy dữ liệu từ request
                String periodIdStr = request.getParameter("periodId");
                int periodId = Integer.parseInt(periodIdStr);
                //Không cập nhật khi bảng đã chốt
                if (payrollPeriodDAO.getById(periodId).getStatus().equals("Approved")) {
                    out.print("Bảng lương đã được chốt không thể cập nhật!");
                    return;
                }

                LocalDateTime fromDate = LocalDateTime.of(payrollPeriodDAO.getById(periodId).getStartDate(), LocalTime.MIN);

                // Lấy danh sách employeeIds từ JSON
                String employeeIdsJson = request.getParameter("employeeIds");
                Gson gson = new Gson();
                List<Integer> employeeIds = gson.fromJson(employeeIdsJson, new TypeToken<List<Integer>>() {
                }.getType());

                //Cập nhât lại số giờ làm việc đến hôm nay
                LocalDateTime today = LocalDateTime.now();
                boolean allSuccess = true;
                for (Integer empId : employeeIds) {
                    System.out.println("Nhân viên " + empId);
                    //Thời gian đã chốt lương cho nhân viên
                    if (fromDate.isBefore(payrollCalculationDAO.getTimePayrollClose(empId))) {
                        fromDate = payrollCalculationDAO.getTimePayrollClose(empId);
                    }
                    EmployeeSalaryAssignments employeeSalaryAssignments = employeeSalaryAssignmentDAO.getSalaryByEmployeeId(empId);
                    if (employeeSalaryAssignments == null) {
                        out.print("Vui lòng thiết lập cách tính lương cho nhân viên " + new EmployeeDAO().getEmployeeNameByID(empId));
                        return;
                    }
                    //Lấy thông tin đã thiết lập lương cho nhân viên
                    String typeSalary = employeeSalaryAssignments.getSalaryType();
                    double salaryRate = employeeSalaryAssignments.getSalaryRate();
                    double overtimeRate = employeeSalaryAssignments.getOvertimeRate();
                    System.out.println("Salary Type: " + typeSalary);
                    System.out.println("Salary Rate: " + salaryRate);
                    System.out.println("Overtime Rate: " + overtimeRate);
                    //Phụ cấp theo tháng
                    double allowanceAmount = employeeSalaryAssignments.getAllowanceRate();
                    if (fromDate.getMonth() == today.getMonth()) {
                        allowanceAmount = 0;
                    }

                    double penaltyEarlyLeave = employeeSalaryAssignments.getPenaltyEarlyLeave();
                    double penaltyLateArrival = employeeSalaryAssignments.getPenaltyLateArrival();
                    double commissionRate = employeeSalaryAssignments.getComissionRate();

                    System.out.println("Penalty for Early Leave: " + penaltyEarlyLeave);
                    System.out.println("Penalty for Late Arrival: " + penaltyLateArrival);
                    System.out.println("Commission Rate: " + commissionRate);
                    //Lấy thời gian làm việc từ chấm công
                    int totalShift = attendanceDAO.getTotalShift("Present", empId, fromDate, today);
                    double totalWorkHours = attendanceDAO.getTotalWorkHour("WorkHours", empId, fromDate, today);
                    double totalOvertimeHours = attendanceDAO.getTotalWorkHour("OvertimeHours", empId, fromDate, today);
                    //Tính lương 
                    double salaryAmount = 0;
                    if (typeSalary.equalsIgnoreCase("Hourly")) {
                        salaryAmount = salaryRate * totalWorkHours;
                    } else {
                        salaryAmount = salaryRate * totalShift;
                    }
                    //Tính lương tăng ca
                    double overtimeAmount = 0;
                    overtimeAmount = overtimeRate * totalOvertimeHours;
                    //Lấy doanh thu từ hóa đơn của nhân viên
                    System.out.println("From Date: " + fromDate);
                    System.out.println("Today: " + today);
                    double totalInvoiceAmount = invoiceDao.getTotalAmountByEmpId(empId, fromDate, today);
                    //Tính tiền hoa hồng
                    double commissionAmount = 0;
                    commissionAmount = commissionRate * totalInvoiceAmount / 100;
                    System.out.println("Commission Rate: " + commissionRate);
                    System.out.println("Total Invoice Amount: " + totalInvoiceAmount);
                    System.out.println("Commission Amount: " + commissionAmount);
                    //Tính tiền phạt
                    double deductionAmount = penaltyEarlyLeave * attendanceDAO.getTotalShift("Early Leave", 1, fromDate, today) + penaltyLateArrival * attendanceDAO.getTotalShift("Late", 1, fromDate, today);
                    boolean success = payrollCalculationDAO.updatePayrollCalculation(periodId, empId, typeSalary, totalShift, totalWorkHours, totalOvertimeHours, salaryAmount, overtimeAmount, allowanceAmount, deductionAmount, commissionAmount);
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
        }

        //Chốt lương
        if ("payrollClose".equals(action)) {
            try {
                // Lấy dữ liệu từ request
                String periodIdStr = request.getParameter("periodId");
                int periodId = Integer.parseInt(periodIdStr);

                if (payrollPeriodDAO.payrollClose(periodId, user.getUserId())) {
                    out.print("success");
                } else {
                    out.print("insert_failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.print("error:" + e.getMessage());
            }
        }

        //Xóa nhân viên
        if ("deleteEmployee".equals(action)) {
            try {
                // Lấy dữ liệu từ request
                String empIdStr = request.getParameter("empId");
                int empId = Integer.parseInt(empIdStr);
                String periodIdStr = request.getParameter("periodId");
                int periodId = Integer.parseInt(periodIdStr);

                //Đã chốt lương không thể xóa
                if (payrollPeriodDAO.getById(periodId).getStatus().equals("Approved")) {
                    out.print("Bảng lương đã được chốt không thể Xóa nhân viên!");
                    return;
                }
                if (payrollCalculationDAO.deleteEmpIdOfPayroll(empId, periodId)) {
                    out.print("success");
                } else {
                    out.print("insert_failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.print("error:" + e.getMessage());
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
