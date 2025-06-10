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

    public static void main(String[] args) {
        AttendanceDAO a = new AttendanceDAO();
        for (Attendances attendances : a.getAttendaceByEmpId(1)) {
            System.out.println(attendances.getWorkDate());
        }
    }
}
