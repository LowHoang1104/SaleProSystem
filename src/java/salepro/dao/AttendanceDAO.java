/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.models.Attendances;

/**
 *
 * @author Thinhnt
 */
public class AttendanceDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public List<Attendances> getAttendaceByEmpId(int employeeId) {
        List<Attendances> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Attendance]\n"
                + "where [EmployeeID] = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Attendances a = new Attendances();
                a.setAttendanceId(rs.getInt("AttendanceID"));
                a.setEmployeeId(rs.getInt("EmployeeID"));
                a.setShiftId(rs.getInt("ShiftID"));
                a.setWorkDate(rs.getDate("WorkDate").toLocalDate());
                a.setCheckInTime(rs.getTimestamp("CheckInTime"));
                a.setCheckOutTime(rs.getTimestamp("CheckOutTime"));
                a.setWorkHours(rs.getDouble("WorkHours"));
                a.setOverTimeHours(rs.getDouble("OvertimeHours"));
                a.setStatus(rs.getString("Status"));
                a.setNotes(rs.getString("Notes"));
                a.setCreateAt(rs.getTimestamp("CreatedAt"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertAttendanceByShiftIdAndEmpId(int shiftId, int empId, Date workDate) {
        String sql = "INSERT INTO [dbo].[Attendance]\n"
                + "([EmployeeID],[ShiftID],[WorkDate],[CheckInTime],[CheckOutTime],[WorkHours],[OvertimeHours],[Status],[Notes])\n"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, empId);
            stm.setInt(2, shiftId);
            stm.setDate(3, workDate);
            stm.setTime(4, null);
            stm.setTime(5, null);
            stm.setDouble(6, 0);
            stm.setDouble(7, 0);
            stm.setString(8, "Pending");
            stm.setString(9, null);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

}
