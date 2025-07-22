/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.PayrollPeriods;

/**
 *
 * @author Thinhnt
 */
public class PayrollPeriodDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<PayrollPeriods> getAllPayrollPeriods() {
        List<PayrollPeriods> list = new ArrayList<>();
        String sql = "SELECT * FROM PayrollPeriods ORDER BY PayrollPeriodID DESC";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                PayrollPeriods pp = new PayrollPeriods();
                pp.setPayrollPeriodId(rs.getInt("PayrollPeriodID"));
                pp.setStartDate(rs.getDate("StartDate").toLocalDate());
                pp.setEndDate(rs.getDate("EndDate").toLocalDate());
                pp.setSalaryCalculateType(rs.getString("SalaryCalculateType"));
                pp.setStatus(rs.getString("Status"));
                pp.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
                pp.setApprovedAt(rs.getTimestamp("ApprovedAt") != null ? rs.getTimestamp("ApprovedAt").toLocalDateTime() : null);
                pp.setPaidAt(rs.getTimestamp("PaidAt") != null ? rs.getTimestamp("PaidAt").toLocalDateTime() : null);
                pp.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                list.add(pp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PayrollPeriods getById(int payrollPeriodId) {
        String sql = "SELECT * FROM PayrollPeriods where PayrollPeriodID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, payrollPeriodId);
            rs = stm.executeQuery();
            if (rs.next()) {
                PayrollPeriods pp = new PayrollPeriods();
                pp.setPayrollPeriodId(rs.getInt("PayrollPeriodID"));
                pp.setStartDate(rs.getDate("StartDate").toLocalDate());
                pp.setEndDate(rs.getDate("EndDate").toLocalDate());
                pp.setSalaryCalculateType(rs.getString("SalaryCalculateType"));
                pp.setStatus(rs.getString("Status"));
                pp.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
                pp.setApprovedAt(rs.getTimestamp("ApprovedAt") != null ? rs.getTimestamp("ApprovedAt").toLocalDateTime() : null);
                pp.setPaidAt(rs.getTimestamp("PaidAt") != null ? rs.getTimestamp("PaidAt").toLocalDateTime() : null);
                pp.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                return pp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PayrollPeriods> getAllPayrollPeriodsByStoreId(int storeId) {
        List<PayrollPeriods> list = new ArrayList<>();
        String sql = "SELECT DISTINCT pr.PayrollPeriodID, pr.StartDate, pr.EndDate, pr.SalaryCalculateType, "
                + "pr.Status, pr.ApprovedBy, pr.ApprovedAt, pr.PaidAt, pr.CreatedAt "
                + "FROM PayrollPeriods pr "
                + "JOIN PayrollCalculation pc ON pr.PayrollPeriodID = pc.PayrollPeriodID "
                + "JOIN Employees e ON e.EmployeeID = pc.EmployeeID "
                + "WHERE e.StoreID = ? "
                + "ORDER BY pr.PayrollPeriodID DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, storeId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PayrollPeriods pp = new PayrollPeriods();
                pp.setPayrollPeriodId(rs.getInt("PayrollPeriodID"));
                pp.setStartDate(rs.getDate("StartDate").toLocalDate());
                pp.setEndDate(rs.getDate("EndDate").toLocalDate());
                pp.setSalaryCalculateType(rs.getString("SalaryCalculateType"));
                pp.setStatus(rs.getString("Status"));
                pp.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
                pp.setApprovedAt(rs.getTimestamp("ApprovedAt") != null ? rs.getTimestamp("ApprovedAt").toLocalDateTime() : null);
                pp.setPaidAt(rs.getTimestamp("PaidAt") != null ? rs.getTimestamp("PaidAt").toLocalDateTime() : null);
                pp.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                list.add(pp);
            }
            rs.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PayrollPeriods insertPayrollPeriod(String typeMonthlySalary, LocalDate startDate, LocalDate endDate) {
        String sql = "INSERT INTO PayrollPeriods (StartDate, EndDate, SalaryCalculateType, Status, CreatedAt) "
                + "VALUES (?, ?, ?, ?, GETDATE())";
        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setDate(1, Date.valueOf(startDate));
            stm.setDate(2, Date.valueOf(endDate));
            stm.setString(3, typeMonthlySalary);
            stm.setString(4, "Pending");

            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    PayrollPeriods period = new PayrollPeriods();
                    period.setPayrollPeriodId(generatedId);
                    period.setStartDate(startDate);
                    period.setEndDate(endDate);
                    period.setSalaryCalculateType(typeMonthlySalary);
                    period.setStatus("Pending");
                    return period;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean payrollClose(int periodId, int approveBy) {
        String sql = "update PayrollPeriods\n"
                + "set ApprovedAt = GetDate(),\n"
                + "	Status = N'Approved',"
                + "     ApprovedBy = ?"
                + " where PayrollPeriodID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, approveBy);
            stm.setInt(2, periodId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PayrollPeriodDAO dao = new PayrollPeriodDAO();
        System.out.println(dao.payrollClose(7, 1));
    }

}
