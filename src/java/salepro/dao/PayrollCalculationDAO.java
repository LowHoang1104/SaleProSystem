/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext;
import java.sql.*;
import java.time.LocalDateTime;
import salepro.models.PayrollCalculation;
import java.sql.Timestamp;

/**
 *
 * @author Thinhnt
 */
public class PayrollCalculationDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<PayrollCalculation> getPayrollCalculationByPayrollPeriodID(int payrollPeriodId) {
        List<PayrollCalculation> list = new ArrayList<>();
        String sql = "SELECT * FROM PayrollCalculation WHERE PayrollPeriodID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, payrollPeriodId);
            rs = stm.executeQuery();

            while (rs.next()) {
                PayrollCalculation pc = new PayrollCalculation(
                        rs.getInt("PayrollCalculationID"),
                        rs.getInt("PayrollPeriodID"),
                        rs.getInt("EmployeeID"),
                        rs.getString("Type"),
                        rs.getInt("TotalShifts"),
                        rs.getDouble("TotalWorkHours"),
                        rs.getDouble("SalaryAmount"),
                        rs.getDouble("CommissionAmount"),
                        rs.getDouble("TotalOvertimeHours"),
                        rs.getDouble("OvertimeAmount"),
                        rs.getDouble("AllowanceAmount"),
                        rs.getDouble("DeductionAmount"),
                        rs.getTimestamp("CalculatedAt").toLocalDateTime(),
                        rs.getDouble("TotalSaturdayHour"), // thêm vào
                        rs.getDouble("TotalSundayHour"), // thêm vào
                        rs.getDouble("TotalHolidayHour") // thêm vào
                );
                list.add(pc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertPayrollCalculation(int employeeId, int payrollPeriodID) {
        String sql = "INSERT INTO PayrollCalculation "
                + "(EmployeeID, PayrollPeriodID) "
                + "VALUES (?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            stm.setInt(2, payrollPeriodID);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePayrollCalculation(
            int payrollPeriodId,
            int employeeId,
            String typeSalary,
            int totalShifts,
            double totalWorkHours,
            double totalOvertimeHours,
            double salaryAmount,
            double overtimeAmount,
            double allowanceAmount,
            double deductionAmount,
            double commisionAmount,
            double totalSaturdayHour,
            double totalSundayHour,
            double totalHolidayHour) {

        StringBuilder sql = new StringBuilder("UPDATE PayrollCalculation SET ");
        List<Object> params = new ArrayList<>();

        if (typeSalary != null && !typeSalary.isBlank()) {
            sql.append("Type = ?, ");
            params.add(typeSalary);
        }
        if (totalShifts != -1) {
            sql.append("TotalShifts = ?, ");
            params.add(totalShifts);
        }

        if (totalWorkHours != -1) {
            sql.append("TotalWorkHours = ?, ");
            params.add(totalWorkHours);
        }

        if (totalOvertimeHours != -1) {
            sql.append("TotalOvertimeHours = ?, ");
            params.add(totalOvertimeHours);
        }

        if (salaryAmount != -1) {
            sql.append("SalaryAmount = ?, ");
            params.add(salaryAmount);
        }

        if (overtimeAmount != -1) {
            sql.append("OvertimeAmount = ?, ");
            params.add(overtimeAmount);
        }

        if (allowanceAmount != -1) {
            sql.append("AllowanceAmount = ?, ");
            params.add(allowanceAmount);
        }

        if (deductionAmount != -1) {
            sql.append("DeductionAmount = ?, ");
            params.add(deductionAmount);
        }

        if (commisionAmount != -1) {
            sql.append("CommissionAmount = ?, ");
            params.add(commisionAmount);
        }

        // ✅ Thêm 3 trường mới
        if (totalSaturdayHour != -1) {
            sql.append("TotalSaturdayHour = ?, ");
            params.add(totalSaturdayHour);
        }

        if (totalSundayHour != -1) {
            sql.append("TotalSundayHour = ?, ");
            params.add(totalSundayHour);
        }

        if (totalHolidayHour != -1) {
            sql.append("TotalHolidayHour = ?, ");
            params.add(totalHolidayHour);
        }

        if (params.isEmpty()) {
            return false;
        }

        // Bỏ dấu ',' cuối cùng
        sql.setLength(sql.length() - 2);

        sql.append(" ,CalculatedAt = CURRENT_TIMESTAMP WHERE PayrollPeriodId = ? and EmployeeID = ?");
        params.add(payrollPeriodId);
        params.add(employeeId);

        try {
            stm = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public LocalDateTime getTimePayrollClose(int empId) {
        String sql = "  select pr.ApprovedAt\n"
                + "  from PayrollCalculation pc \n"
                + "  join PayrollPeriods pr on pc.PayrollPeriodID = pr.PayrollPeriodID\n"
                + "  where pc.EmployeeID = ?\n"
                + "  order by pr.ApprovedAt desc";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, empId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp(1).toLocalDateTime();
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return null;
    }

    public boolean deleteEmpIdOfPayroll(int empId, int periodId) {
        String sql = " delete PayrollCalculation\n"
                + "  where PayrollPeriodID = ? and EmployeeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, periodId);
            stm.setInt(2, empId);
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PayrollCalculationDAO dao = new PayrollCalculationDAO();
   
    }
}
