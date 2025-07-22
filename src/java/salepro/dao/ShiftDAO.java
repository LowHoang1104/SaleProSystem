/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Shifts;

/**
 *
 * @author Thinhnt
 */
public class ShiftDAO extends DBContext2 {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public List<Shifts> getData() {
        String sql = "SELECT * FROM Shifts";
        List<Shifts> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Shifts shift = new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
                list.add(shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Shifts getShiftById(int shiftId) {
        String sql = "Select * from Shifts"
                + " where ShiftID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, shiftId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shifts> searchShiftByNameAndStoreId(String shiftName, String storeId) {
        List<Shifts> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        String sql = "Select * from Shifts";
        if (shiftName != null && !shiftName.isBlank()) {
            conditions.add("ShiftName Like ?");
            params.add("%" + shiftName.trim() + "%");
        }
        if (storeId != null && !storeId.isBlank()) {
            conditions.add("StoreID = ?");
            params.add(storeId.trim());
        }
        if (!conditions.isEmpty()) {
            sql += " where " + String.join(" and ", conditions);
        }
        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                }
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                Shifts shift = new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
                list.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isShiftExist(String shiftName, int storeId) {
        String sql = "select * from Shifts"
                + " where ShiftName = ? and StoreID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, shiftName);
            stm.setInt(2, storeId);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean insertShift(Shifts shift) {
        String sql = "INSERT INTO [dbo].[Shifts]([ShiftName], [StartTime], [EndTime], [StoreID], [IsActive])\n"
                + " VALUES(?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, shift.getShiftName());
            stm.setTime(2, shift.getStartTime());
            stm.setTime(3, shift.getEndTime());
            stm.setInt(4, shift.getStoreID());
            stm.setBoolean(5, shift.isIsActive());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertShiftForAllStore(Shifts shift) {
        String sql = "INSERT INTO [dbo].[Shifts] ([ShiftName], [StartTime], [EndTime], [StoreID], [IsActive])\n"
                + "select ?, ?, ?, s.StoreID, ?\n"
                + "from Stores s";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, shift.getShiftName());
            stm.setTime(2, shift.getStartTime());
            stm.setTime(3, shift.getEndTime());
            stm.setBoolean(4, shift.isIsActive());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShift(Shifts shift) {
        String sql = "UPDATE [dbo].[Shifts]\n"
                + "   SET [ShiftName] = ?\n"
                + "      ,[StartTime] = ?\n"
                + "      ,[EndTime] = ?\n"
                + "      ,[StoreID] = ?\n"
                + "      ,[IsActive] = ?\n"
                + " WHERE ShiftID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, shift.getShiftName());
            stm.setTime(2, shift.getStartTime());
            stm.setTime(3, shift.getEndTime());
            stm.setInt(4, shift.getStoreID());
            stm.setBoolean(5, shift.isIsActive());
            stm.setInt(6, shift.getShiftID());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Shifts> getShiftByStoreId(int storeId) {
        String sql = "select * from Shifts "
                + "where storeId = ?";
        List<Shifts> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Shifts shift = new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
                list.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Shifts> getShiftByWeek(Date startWeek, Date endWeek) {
        String sql = "SELECT distinct s.ShiftID, s.ShiftName, s.StartTime, s.EndTime, s.StoreID, s.IsActive FROM Attendance a"
                + " join Shifts s on s.ShiftID = a.ShiftID"
                + " where a.WorkDate between ? and ?";
        List<Shifts> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, startWeek);
            stm.setDate(2, endWeek);
            rs = stm.executeQuery();
            while (rs.next()) {
                Shifts shift = new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
                list.add(shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Shifts> filterShifts(int storeId, int empId, String empName, Date startWeek, Date endWeek) {
        List<Shifts> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT distinct s.ShiftID, s.ShiftName, s.StartTime, s.EndTime, s.StoreID, s.IsActive FROM Attendance a"
                + " join Shifts s on s.ShiftID = a.ShiftID"
                + " join Employees e on e.EmployeeID = a.EmployeeID"
                + " where a.WorkDate between ? and ?";
        if (empId != 0) {
            conditions.add("a.EmployeeID = ?");
            params.add(empId);
        }
        if (storeId != 0) {
            conditions.add("s.StoreID = ?");
            params.add(storeId);
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
                Shifts shift = new Shifts(rs.getInt("ShiftID"), rs.getString("ShiftName"), rs.getTime("StartTime"), rs.getTime("EndTime"), rs.getInt("StoreID"), rs.getBoolean("IsActive"));
                list.add(shift);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteShift(int shiftId){
        String sql = "delete from Shifts"
                + " where shiftID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, shiftId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
