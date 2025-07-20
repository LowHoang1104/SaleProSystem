/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author Thinhnt
 */
import salepro.dao.PayrollCalculationDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PayrollPeriods {
    private int payrollPeriodId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String salaryCalculateType;   // Monthly, Custom
    private String status;                // Pending, Approved, Paid
    private Integer approvedBy;           // Nullable FK to Users
    private LocalDateTime approvedAt;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

    // Constructors
    public PayrollPeriods() {}

    public PayrollPeriods(int payrollPeriodId, LocalDate startDate, LocalDate endDate,
                         String salaryCalculateType, String status, Integer approvedBy,
                         LocalDateTime approvedAt, LocalDateTime paidAt, LocalDateTime createdAt) {
        this.payrollPeriodId = payrollPeriodId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salaryCalculateType = salaryCalculateType;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    public int getPayrollPeriodId() {
        return payrollPeriodId;
    }

    public void setPayrollPeriodId(int payrollPeriodId) {
        this.payrollPeriodId = payrollPeriodId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSalaryCalculateType() {
        return salaryCalculateType;
    }

    public void setSalaryCalculateType(String salaryCalculateType) {
        this.salaryCalculateType = salaryCalculateType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
        public String getName() {
        if (salaryCalculateType.equalsIgnoreCase("monthly")) {
            return "Bảng lương tháng " + startDate.getMonthValue() + "/" + startDate.getYear();
        }
        return "Bảng lương tùy chọn";
    }

    public String getPeriod() {
        // Format ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startFormatted = startDate.format(formatter);
        String endFormatted = endDate.format(formatter);
        return startFormatted + " - " + endFormatted;
    }
    
    public double getTotalSalary(){
        PayrollCalculationDAO dao = new PayrollCalculationDAO();
        double totalSalary = 0;
        for (PayrollCalculation payrollCalculation : dao.getPayrollCalculationByPayrollPeriodID(payrollPeriodId)) {
            totalSalary += payrollCalculation.getNetSalary();
        }
        return totalSalary;
    }

    
}

