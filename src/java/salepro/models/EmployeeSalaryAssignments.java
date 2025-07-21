/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;

/**
 *
 * @author Thinhnt
 */
public class EmployeeSalaryAssignments {

    private int assignmentId;
    private int employeeId;
    private String salaryType;
    private double salaryRate;
    private double overtimeRate;
    private double overtimeSaturdayRate;
    private double overtimeSundayRate;
    private double overtimeHolidayRate;
    private double allowanceRate;
    private double penaltyLateArrival;
    private double penaltyEarlyLeave;
    private double penaltyOthers;
    private double comissionRate;
    private Date effectiveDate;
    private boolean isActive;

    public EmployeeSalaryAssignments() {
    }

    public EmployeeSalaryAssignments(int assignmentId, int employeeId, String salaryType, double salaryRate, double overtimeRate, double overtimeSaturdayRate, double overtimeSundayRate, double overtimeHolidayRate, double allowanceRate, double penaltyLateArrival, double penaltyEarlyLeave, double penaltyOthers, double comissionRate, Date effectiveDate, boolean isActive) {
        this.assignmentId = assignmentId;
        this.employeeId = employeeId;
        this.salaryType = salaryType;
        this.salaryRate = salaryRate;
        this.overtimeRate = overtimeRate;
        this.overtimeSaturdayRate = overtimeSaturdayRate;
        this.overtimeSundayRate = overtimeSundayRate;
        this.overtimeHolidayRate = overtimeHolidayRate;
        this.allowanceRate = allowanceRate;
        this.penaltyLateArrival = penaltyLateArrival;
        this.penaltyEarlyLeave = penaltyEarlyLeave;
        this.penaltyOthers = penaltyOthers;
        this.comissionRate = comissionRate;
        this.effectiveDate = effectiveDate;
        this.isActive = isActive;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public double getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(double salaryRate) {
        this.salaryRate = salaryRate;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public double getOvertimeSaturdayRate() {
        return overtimeSaturdayRate;
    }

    public void setOvertimeSaturdayRate(double overtimeSaturdayRate) {
        this.overtimeSaturdayRate = overtimeSaturdayRate;
    }

    public double getOvertimeSundayRate() {
        return overtimeSundayRate;
    }

    public void setOvertimeSundayRate(double overtimeSundayRate) {
        this.overtimeSundayRate = overtimeSundayRate;
    }

    public double getOvertimeHolidayRate() {
        return overtimeHolidayRate;
    }

    public void setOvertimeHolidayRate(double overtimeHolidayRate) {
        this.overtimeHolidayRate = overtimeHolidayRate;
    }

    public double getAllowanceRate() {
        return allowanceRate;
    }

    public void setAllowanceRate(double allowanceRate) {
        this.allowanceRate = allowanceRate;
    }

    public double getPenaltyLateArrival() {
        return penaltyLateArrival;
    }

    public void setPenaltyLateArrival(double penaltyLateArrival) {
        this.penaltyLateArrival = penaltyLateArrival;
    }

    public double getPenaltyEarlyLeave() {
        return penaltyEarlyLeave;
    }

    public void setPenaltyEarlyLeave(double penaltyEarlyLeave) {
        this.penaltyEarlyLeave = penaltyEarlyLeave;
    }

    public double getPenaltyOthers() {
        return penaltyOthers;
    }

    public void setPenaltyOthers(double penaltyOthers) {
        this.penaltyOthers = penaltyOthers;
    }

    public double getComissionRate() {
        return comissionRate;
    }

    public void setComissionRate(double comissionRate) {
        this.comissionRate = comissionRate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getSalaryTypeVietsub() {
        if (salaryType.equalsIgnoreCase("hourly")) {
            return "Làm việc theo giờ";
        }
        if (salaryType.equalsIgnoreCase("shiftly")) {
            return "Làm việc theo ca";
        }
        return "";
    }

}
