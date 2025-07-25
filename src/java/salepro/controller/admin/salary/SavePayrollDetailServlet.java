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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.AttendanceDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeSalaryAssignmentDAO;
import salepro.dao.FundTransactionDAO;
import salepro.dao.HolidayDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PayrollCalculationDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.dao.UserDAO;
import salepro.models.EmployeeSalaryAssignments;
import salepro.models.FundTransactions;
import salepro.models.Holiday;
import salepro.models.PayrollPeriods;
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
        HolidayDAO holidayDao = new HolidayDAO();

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

                PayrollPeriods period = payrollPeriodDAO.getById(periodId);
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
                    //Thời gian đã chốt lương cho nhân viên
                    LocalDateTime closeTime = payrollCalculationDAO.getTimePayrollClose(empId);
                    System.out.println(closeTime);
                    if (closeTime != null && fromDate.isBefore(closeTime)) {
                        fromDate = closeTime;
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
                    double overtimeRateSat = employeeSalaryAssignments.getOvertimeSaturdayRate();
                    double overtimeRateSun = employeeSalaryAssignments.getOvertimeSundayRate();
                    double overtimeRateHoli = employeeSalaryAssignments.getOvertimeHolidayRate();

                    //Phụ cấp theo tháng
                    double allowanceAmount = employeeSalaryAssignments.getAllowanceRate();
                    if (period.getStatus().equalsIgnoreCase("Approved") && fromDate.getMonth() == today.getMonth()) {
                        allowanceAmount = 0;
                    }

                    double penaltyEarlyLeave = employeeSalaryAssignments.getPenaltyEarlyLeave();
                    double penaltyLateArrival = employeeSalaryAssignments.getPenaltyLateArrival();
                    double commissionRate = employeeSalaryAssignments.getComissionRate();

                    //Lấy thời gian làm việc từ chấm công
                    List<Holiday> holidays = holidayDao.getAllHoliday();
                    List<LocalDate> holidayDates = new ArrayList<>();
                    if (holidays != null) {
                        for (Holiday holiday : holidays) {
                            if (holiday != null && holiday.getHolidayDate() != null) {
                                holidayDates.add(holiday.getHolidayDate());
                            }
                        }
                    }
                    //Ngày thường 
                    int totalShift = attendanceDAO.getTotalShift("Present", empId, fromDate, today, holidayDates);
                    double totalWorkHours = attendanceDAO.getTotalWorkHour("WorkHours", empId, fromDate, today, holidayDates);
                    double totalOvertimeHours = attendanceDAO.getTotalWorkHour("OvertimeHours", empId, fromDate, today, holidayDates);
                    //Ngày đặc biệt
                    double totalSaturdayHours = attendanceDAO.getTotalWorkHour("WorkHours", empId, fromDate, today, holidayDates, true, false, false);
                    double totalSundayHours = attendanceDAO.getTotalWorkHour("WorkHours", empId, fromDate, today, holidayDates, false, true, false);
                    double totalHolidayHours = attendanceDAO.getTotalWorkHour("WorkHours", empId, fromDate, today, holidayDates, false, false, true);

                    double totalSaturdayHoursOver = attendanceDAO.getTotalWorkHour("OvertimeHours", empId, fromDate, today, holidayDates, true, false, false);
                    double totalSundayHoursOver = attendanceDAO.getTotalWorkHour("OvertimeHours", empId, fromDate, today, holidayDates, false, true, false);
                    double totalHolidayHoursOver = attendanceDAO.getTotalWorkHour("OvertimeHours", empId, fromDate, today, holidayDates, false, false, true);

                    double totalSaturdayCombined = totalSaturdayHours + totalSaturdayHoursOver;
                    double totalSundayCombined = totalSundayHours + totalSundayHoursOver;
                    double totalHolidayCombined = totalHolidayHours + totalHolidayHoursOver;

                    //Tính lương 
                    double salaryAmount = 0;
                    if (typeSalary.equalsIgnoreCase("Hourly")) {
                        salaryAmount = salaryRate * totalWorkHours;
                    } else {
                        salaryAmount = salaryRate * totalShift;
                    }
                    //Tính lương tăng ca
                    double overtimeAmount = 0;
                    overtimeAmount = salaryRate * (overtimeRate * totalOvertimeHours + overtimeRateSat * totalSaturdayCombined + overtimeRateSun * totalSundayCombined + overtimeRateHoli * totalHolidayCombined);

                    double totalInvoiceAmount = invoiceDao.getTotalAmountByEmpId(empId, fromDate, today);
                    System.out.println("Total invoice amount: " + totalInvoiceAmount);

//Tính tiền hoa hồng
                    double commissionAmount = 0;
                    commissionAmount = commissionRate * totalInvoiceAmount / 100;
                    //Tính tiền phạt
                    double deductionAmount = penaltyEarlyLeave * attendanceDAO.getTotalShift("Early Leave", empId, fromDate, today, holidayDates) + penaltyLateArrival * attendanceDAO.getTotalShift("Late", empId, fromDate, today, holidayDates);
                    boolean success = payrollCalculationDAO.updatePayrollCalculation(periodId, empId, typeSalary, totalShift, totalWorkHours, totalOvertimeHours, salaryAmount, overtimeAmount, allowanceAmount, deductionAmount, commissionAmount, totalSaturdayCombined, totalSundayHours, totalHolidayCombined);
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

                if (payrollPeriodDAO.payrollClose(periodId, user.getUserId(), "Approved")) {
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

        //Đã thanh toán
        if ("paymentcompleted".equals(action)) {
            try {
                // Lấy dữ liệu từ request
                String periodIdStr = request.getParameter("periodId");
                int periodId = Integer.parseInt(periodIdStr);

                String paymentMethodStr = request.getParameter("paymentMethod");
                int paymentMethod = Integer.parseInt(paymentMethodStr);

                if (payrollPeriodDAO.getById(periodId).getStatus().equals("Paid")) {
                    out.print("Đã thanh toán trước đó");
                    return;
                }
                if (!payrollPeriodDAO.getById(periodId).getStatus().equals("Approved")) {
                    out.print("Không thể thanh toán cần chốt lương trước");
                    return;
                }

                FundTransactions fund = new FundTransactions();
                fund.setFundID(paymentMethod);
                fund.setAmount(payrollPeriodDAO.getById(periodId).getTotalSalary());
                fund.setDescription("Thanh toán trả lương " + payrollPeriodDAO.getById(periodId).getName());
                fund.setCreatedBy(user.getUserId());
                fund.setDescription("Thanh toán trả lương " + payrollPeriodDAO.getById(periodId).getName());
                fund.setNotes("Khác");
                if (fundTransactionDAO.createSalary(fund, periodId) && payrollPeriodDAO.salaryPaid(periodId, user.getUserId(), "Paid", LocalDateTime.now())) {
                    out.print("success");
                } else {
                    out.print("Chọn quỹ thanh toán lương thất bại");
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
