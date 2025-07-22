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
import salepro.models.EmployeeSalaryAssignments;

/**
 *
 * @author Thinhnt
 */
public class EmployeeSalaryAssignmentDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public EmployeeSalaryAssignments getSalaryByEmployeeId(int empId) {
        String sql = "SELECT * FROM EmployeeSalaryAssignments\n"
                + "where EmployeeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, empId);
            rs = stm.executeQuery();
            if (rs.next()) {
                EmployeeSalaryAssignments empSalary = new EmployeeSalaryAssignments(rs.getInt("AssignmentID"), rs.getInt("EmployeeID"), rs.getString("SalaryType"),
                        rs.getDouble("SalaryAmount"),
                        rs.getDouble("OvertimeRate"),
                        rs.getDouble("OvertimeRate_Saturday"),
                        rs.getDouble("OvertimeRate_Sunday"),
                        rs.getDouble("OvertimeRate_Holiday"),
                        rs.getDouble("AllowanceAmount"),
                        rs.getDouble("Penalty_LateArrival"),
                        rs.getDouble("Penalty_EarlyLeave"),
                        rs.getDouble("Penalty_Others"),
                        rs.getDouble("CommissionRate"),
                        rs.getDate("EffectiveDate"),
                        rs.getBoolean("IsActive"));
                return empSalary;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertSalaryAssignment(int employeeId,
            String salaryType,
            double salaryRate,
            double OvertimeRate,
            double OvertimeRateSaturday,
            double OvertimeRateSunday,
            double OvertimeRateHoliday,
            double allowanceAmount,
            double penaltyLateArrival,
            double penaltyEarlyLeave,
            double penaltyOthers, 
            double commissionRate
    ) {
        StringBuilder sql = new StringBuilder("INSERT INTO EmployeeSalaryAssignments (EmployeeID");
        StringBuilder values = new StringBuilder("VALUES (?"); // tương ứng với EmployeeID
        List<Object> params = new ArrayList<>();
        params.add(employeeId);

        if (salaryType != null && !salaryType.trim().isEmpty()) {
            sql.append(", SalaryType");
            values.append(", ?");
            params.add(salaryType);
        }

        if (salaryRate != -1) {
            sql.append(", SalaryAmount");
            values.append(", ?");
            params.add(salaryRate);
        }
        if (OvertimeRate != -1) {
            sql.append(", OvertimeRate");
            values.append(", ?");
            params.add(salaryRate);
        }
        if (salaryRate != -1) {
            sql.append(", OvertimeRate_Saturday");
            values.append(", ?");
            params.add(salaryRate);
        }
        if (salaryRate != -1) {
            sql.append(", OvertimeRate_Sunday");
            values.append(", ?");
            params.add(OvertimeRateSunday);
        }
        if (salaryRate != -1) {
            sql.append(", OvertimeRate_Holiday");
            values.append(", ?");
            params.add(OvertimeRateHoliday);
        }
        if (allowanceAmount != -1) {
            sql.append(", AllowanceAmount");
            values.append(", ?");
            params.add(allowanceAmount);
        }
        if (penaltyLateArrival != -1) {
            sql.append(", Penalty_LateArrival");
            values.append(", ?");
            params.add(penaltyLateArrival);
        }
        if (penaltyEarlyLeave != -1) {
            sql.append(", Penalty_EarlyLeave");
            values.append(", ?");
            params.add(penaltyEarlyLeave);
        }
        if (penaltyOthers != -1) {
            sql.append(", Penalty_Others");
            values.append(", ?");
            params.add(penaltyOthers);
        }
           if (commissionRate != -1) {
            sql.append(", CommissionRate");
            values.append(", ?");
            params.add(commissionRate);
        }

        sql.append(") ");
        values.append(")");
        sql.append(values);

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

    public boolean updateSalaryAssignment(int employeeId,
            String salaryType,
            double salaryRate,
            double overtimeRate,
            double overtimeRateSaturday,
            double overtimeRateSunday,
            double overtimeRateHoliday,
            double allowanceAmount,
            double penaltyLateArrival,
            double penaltyEarlyLeave,
            double penaltyOthers, 
            double commissionRate) {

        StringBuilder sql = new StringBuilder("UPDATE EmployeeSalaryAssignments SET ");
        List<Object> params = new ArrayList<>();

        if (salaryType != null && !salaryType.trim().isEmpty()) {
            sql.append("SalaryType = ?, ");
            params.add(salaryType);
        }

        if (salaryRate != -1) {
            sql.append("SalaryAmount = ?, ");
            params.add(salaryRate);
        }

        if (overtimeRate != -1) {
            sql.append("OvertimeRate = ?, ");
            params.add(overtimeRate);
        }

        if (overtimeRateSaturday != -1) {
            sql.append("OvertimeRate_Saturday = ?, ");
            params.add(overtimeRateSaturday);
        }

        if (overtimeRateSunday != -1) {
            sql.append("OvertimeRate_Sunday = ?, ");
            params.add(overtimeRateSunday);
        }

        if (overtimeRateHoliday != -1) {
            sql.append("OvertimeRate_Holiday = ?, ");
            params.add(overtimeRateHoliday);
        }

        if (allowanceAmount != -1) {
            sql.append("AllowanceAmount = ?, ");
            params.add(allowanceAmount);
        }

        if (penaltyLateArrival != -1) {
            sql.append("Penalty_LateArrival = ?, ");
            params.add(penaltyLateArrival);
        }

        if (penaltyEarlyLeave != -1) {
            sql.append("Penalty_EarlyLeave = ?, ");
            params.add(penaltyEarlyLeave);
        }

        if (penaltyOthers != -1) {
            sql.append("Penalty_Others = ?, ");
            params.add(penaltyOthers);
        }
        if (commissionRate != -1) {
            sql.append("CommissionRate = ?, ");
            params.add(commissionRate);
        }

        if (params.isEmpty()) {
            return false; // Không có gì để cập nhật
        }

        // Xóa dấu phẩy cuối
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE EmployeeID = ?");
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

    public boolean saveSalaryAssignment(int employeeId,
            String salaryType,
            double salaryRate,
            double overtimeRate,
            double overtimeRateSaturday,
            double overtimeRateSunday,
            double overtimeRateHoliday,
            double allowanceAmount,
            double penaltyLateArrival,
            double penaltyEarlyLeave,
            double penaltyOthers, 
            double commissionRate) {

        if (existsAssignment(employeeId)) {
            return updateSalaryAssignment(employeeId, salaryType, salaryRate, overtimeRate,
                    overtimeRateSaturday, overtimeRateSunday, overtimeRateHoliday,
                    allowanceAmount, penaltyLateArrival, penaltyEarlyLeave, penaltyOthers, commissionRate);
        } else {
            return insertSalaryAssignment(employeeId, salaryType, salaryRate, overtimeRate,
                    overtimeRateSaturday, overtimeRateSunday, overtimeRateHoliday,
                    allowanceAmount, penaltyLateArrival, penaltyEarlyLeave, penaltyOthers, commissionRate);
        }
    }

    private boolean existsAssignment(int employeeId) {
        String sql = "SELECT 1 FROM EmployeeSalaryAssignments WHERE EmployeeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        EmployeeSalaryAssignmentDAO dao = new EmployeeSalaryAssignmentDAO();
            System.out.println(dao.getSalaryByEmployeeId(1).getSalaryRate());
        
    }
}
