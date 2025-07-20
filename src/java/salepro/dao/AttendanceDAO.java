/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext2;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import salepro.models.Attendances;
import salepro.models.Shifts;

/**
 *
 * @author Thinhnt
 */
public class AttendanceDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public List<Attendances> getAttendaceByEmpId(int employeeId) {
        List<Attendances> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Attendance] a\n"
                + " join Shifts s on a.ShiftID = s.ShiftID"
                + " where [EmployeeID] = ?"
                + " order by s.StartTime";
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

    public int countEmpByShiftId(int shiftId) {
        String sql = "select count(DISTINCT EmployeeID) from Attendance\n"
                + "where ShiftID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, shiftId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteAttendanceById(int attendanceId) {
        String sql = "delete from Attendance"
                + " where AttendanceId = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, attendanceId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasAttendance(int empId, int shiftId, String workDate) {
        String sql = "select 1 from Attendance"
                + " where EmployeeID = ?"
                + " and ShiftID = ? and WorkDate = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, empId);
            stm.setInt(2, shiftId);
            stm.setString(3, workDate);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Attendances getAttendanceById(int attendanceId) {
        String sql = "SELECT * FROM [dbo].[Attendance]"
                + " where AttendanceID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, attendanceId);
            rs = stm.executeQuery();
            if (rs.next()) {
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
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attendances> getAttendaceByShiftId(int shiftId) {
        List<Attendances> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Attendance] a\n"
                + " join Shifts s on a.ShiftID = s.ShiftID"
                + " where a.ShiftID = ?"
                + " order by s.StartTime";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, shiftId);
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

    public List<Attendances> filterAttendance(int storeId, int shiftId, String empName, Date startWeek, Date endWeek) {
        List<Attendances> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT a.* FROM Attendance a"
                + " join Employees e on e.EmployeeID = a.EmployeeID"
                + " join Shifts s on s.ShiftID = a.ShiftID"
                + " where a.WorkDate between ? and ?";
        if (storeId != 0) {
            conditions.add("s.StoreID = ?");
            params.add(shiftId);
        }
        if (shiftId != 0) {
            conditions.add("a.ShiftID = ?");
            params.add(shiftId);
        }
        if (empName != null && !empName.isBlank()) {
            conditions.add("e.FullName like ?");
            params.add("%" + empName.trim() + "%");
        }
        //Gắn Where nếu có điều kiện
        if (!conditions.isEmpty()) {
            sql += " and " + String.join(" and ", conditions);
        }
        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, startWeek);
            stm.setDate(2, endWeek);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 3, (String) param);
                } else if (param instanceof Integer) {
                    stm.setInt(i + 3, (Integer) param);
                }
            }
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

    public boolean updateAttendance(int attendanceId, String status, String note, LocalDateTime checkIn, LocalDateTime checkOut, double workHours, double OverTimeHours) {
        String sql = "UPDATE Attendance"
                + " SET Status = ?, Notes = ?, CheckInTime = ?, checkOutTime = ?, WorkHours = ?, OvertimeHours = ?, CreatedAt = CURRENT_TIMESTAMP"
                + " WHERE AttendanceID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            stm.setString(2, note);
            stm.setTimestamp(3, checkIn != null ? Timestamp.valueOf(checkIn) : null);
            stm.setTimestamp(4, checkOut != null ? Timestamp.valueOf(checkOut) : null);
            stm.setDouble(5, workHours);
            stm.setDouble(6, OverTimeHours);

            stm.setInt(7, attendanceId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalWorkHour(String typeWorkHour, int employeeId, LocalDateTime fromDate, LocalDateTime toDateTime) {
        String sql = "SELECT SUM(" + typeWorkHour + ")"
                + "FROM [Shop].[dbo].[Attendance] "
                + "WHERE EmployeeID = ? AND CreatedAt <= ? and CreatedAt > ? and WorkDate >= ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            stm.setTimestamp(2, java.sql.Timestamp.valueOf(toDateTime));
            stm.setTimestamp(3, java.sql.Timestamp.valueOf(fromDate));
            stm.setTimestamp(4, java.sql.Timestamp.valueOf(fromDate));

            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalShift(String status, int employeeId, LocalDateTime fromDate, LocalDateTime toDateTime) {
        String sql = "SELECT COUNT(ShiftID) "
                + "FROM [Shop].[dbo].[Attendance] "
                + "WHERE EmployeeID = ? AND CreatedAt <= ? and CreatedAt > ? and WorkDate >= ? AND Status = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            stm.setTimestamp(2, java.sql.Timestamp.valueOf(toDateTime));
            stm.setTimestamp(3, java.sql.Timestamp.valueOf(fromDate));
            stm.setTimestamp(4, java.sql.Timestamp.valueOf(fromDate));
            stm.setString(5, status); // Late, Early Leave, "Present"
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        AttendanceDAO a = new AttendanceDAO();
        System.out.println(a.getTotalShift("Present", 1, LocalDateTime.parse("2025-07-20T19:26:36.533"), LocalDateTime.now()));
    }
}
