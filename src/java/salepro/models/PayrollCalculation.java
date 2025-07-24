/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author Thinhnt
 */
import java.time.LocalDateTime;
import salepro.dao.EmployeeDAO;

public class PayrollCalculation {

    private int payrollCalculationId;
    private int payrollPeriodId;
    private int employeeId;
    private String type; // hourly, shiftly, etc.
    private int totalShifts;
    private double totalWorkHours;
    private double salaryAmount;
    private double commissionAmount;
    private double totalOvertimeHours;
    private double overtimeAmount;
    private double allowanceAmount;
    private double deductionAmount;
    private LocalDateTime calculatedAt;

    public PayrollCalculation(int payrollCalculationId, int payrollPeriodId, int employeeId, String type, int totalShifts, double totalWorkHours, double salaryAmount, double commissionAmount, double totalOvertimeHours, double overtimeAmount, double allowanceAmount, double deductionAmount, LocalDateTime calculatedAt) {
        this.payrollCalculationId = payrollCalculationId;
        this.payrollPeriodId = payrollPeriodId;
        this.employeeId = employeeId;
        this.type = type;
        this.totalShifts = totalShifts;
        this.totalWorkHours = totalWorkHours;
        this.salaryAmount = salaryAmount;
        this.commissionAmount = commissionAmount;
        this.totalOvertimeHours = totalOvertimeHours;
        this.overtimeAmount = overtimeAmount;
        this.allowanceAmount = allowanceAmount;
        this.deductionAmount = deductionAmount;
        this.calculatedAt = calculatedAt;
    }
    
    

    // Computed fields (không lưu trong DB)
    public double getGrossSalary() {
        return salaryAmount + allowanceAmount + commissionAmount;
    }

    public double getNetSalary() {
        return getGrossSalary() - deductionAmount;
    }

    // Getters and Setters

    public int getPayrollCalculationId() {
        return payrollCalculationId;
    }

    public void setPayrollCalculationId(int payrollCalculationId) {
        this.payrollCalculationId = payrollCalculationId;
    }

    public int getPayrollPeriodId() {
        return payrollPeriodId;
    }

    public void setPayrollPeriodId(int payrollPeriodId) {
        this.payrollPeriodId = payrollPeriodId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalShifts() {
        return totalShifts;
    }

    public void setTotalShifts(int totalShifts) {
        this.totalShifts = totalShifts;
    }

    public double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public double getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public void setTotalOvertimeHours(double totalOvertimeHours) {
        this.totalOvertimeHours = totalOvertimeHours;
    }

    public double getOvertimeAmount() {
        return overtimeAmount;
    }

    public void setOvertimeAmount(double overtimeAmount) {
        this.overtimeAmount = overtimeAmount;
    }

    public double getAllowanceAmount() {
        return allowanceAmount;
    }

    public void setAllowanceAmount(double allowanceAmount) {
        this.allowanceAmount = allowanceAmount;
    }

    public double getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(double deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }


    public Employees getEmployee() {
        return new EmployeeDAO().getEmployeeById(employeeId);
    }
}
