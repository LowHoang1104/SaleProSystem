/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import salepro.dao.EmployeeDAO;
import salepro.dao.HolidayDAO;
import salepro.dao.ShiftDAO;

/**
 *
 * @author Thinhnt
 */
public class Attendances {

    private int attendanceId;
    private int employeeId;
    private int shiftId;
    private LocalDate workDate;
    private Timestamp checkInTime;
    private Timestamp checkOutTime;
    private double workHours;
    private double overTimeHours;
    private String status;
    private String notes;
    private Timestamp createAt;

    public Attendances() {
    }

    public Attendances(int attendanceId, int employeeId, int shiftId, LocalDate workDate, Timestamp checkInTime, Timestamp checkOutTime, double workHours, double overTimeHours, String status, String notes, Timestamp createAt) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.shiftId = shiftId;
        this.workDate = workDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.workHours = workHours;
        this.overTimeHours = overTimeHours;
        this.status = status;
        this.notes = notes;
        this.createAt = createAt;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(double overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Shifts getShift() {
        return new ShiftDAO().getShiftById(shiftId);
    }

    public String getShiftName() {
        return new ShiftDAO().getShiftById(shiftId).getShiftName();
    }

    public String getEmpName() {
        return new EmployeeDAO().getEmployeeNameByID(employeeId);
    }

    public Employees getEmp() {
        return new EmployeeDAO().getEmployeeById(employeeId);
    }

    public String getcheckInOut() {
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
            return ((checkInTime != null) ? formatDate.format(checkInTime) : "--") 
       + " - " 
       + ((checkOutTime != null) ? formatDate.format(checkOutTime) : "--");
    }

    public String getStatusVietsub() {
        if (status.equals("Pending")) {
            return "Chưa chấm công";
        } else if (status.equals("Present")) {
            return "Đi làm";
        } else if (status.equals("Approved Leave")) {
            return "Nghỉ có phép";
        } else if (status.equals("Unapproved Leave")) {
            return "Nghỉ không phép";
        } else if (status.equals("Late")) {
            return "Đi muộn";
        } else if (status.equals("Early Leave")) {
            return "Về sớm";
        }
        return status;
    }
    public double getSaturedayHour(){
        if(workDate.getDayOfWeek() == DayOfWeek.SATURDAY){
            return workHours + overTimeHours;
        }
        return 0;
    }

    public double getSundayHour(){
        if(workDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            return workHours + overTimeHours;
        }
        return 0;
    }
    
    public double getHoildayHour(){
        if(new HolidayDAO().checkHolidayDate(workDate)){
            return workHours + overTimeHours;
        }
        return 0;
    }

}
