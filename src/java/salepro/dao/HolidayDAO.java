/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Holiday;
import java.sql.*;
import java.util.Arrays;

/**
 *
 * @author Thinhnt
 */
public class HolidayDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public List<Holiday> getAllHoliday() {
        List<Holiday> list = new ArrayList<>();
        String sql = "  select *\n"
                + "  from Holidays \n"
                + "  order by HolidayDate";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Holiday holiday = new Holiday(rs.getInt("HolidayID"), rs.getDate("HolidayDate").toLocalDate(), rs.getString("Name"), rs.getBoolean("IsPublicHoliday"));
                list.add(holiday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertHoliday(Holiday holiday) {
        String sql = "INSERT INTO Holidays (HolidayDate, Name, IsPublicHoliday) VALUES (?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, java.sql.Date.valueOf(holiday.getHolidayDate())); // LocalDate -> java.sql.Date
            stm.setString(2, holiday.getName());
            stm.setBoolean(3, holiday.isIsPublish());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean updateHoliday(Holiday holiday) {
        String sql = "UPDATE Holidays SET HolidayDate = ?, Name = ?, IsPublicHoliday = ? WHERE HolidayID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, Date.valueOf(holiday.getHolidayDate()));
            stm.setString(2, holiday.getName());
            stm.setBoolean(3, holiday.isIsPublish());
            stm.setInt(4, holiday.getHolidayId());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHoliday(int holidayID) {
        String sql = "DELETE FROM Holidays WHERE HolidayID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, holidayID);
            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<Holiday> searchByKey(String keyword) {
        keyword = keyword.replace("//s+", " ");
        List<Holiday> list = new ArrayList<>();
        String sql = "SELECT * FROM Holidays "
                + "WHERE Name LIKE ? "
                + "   OR MONTH(HolidayDate) = ? "
                + "   OR YEAR(HolidayDate) = ? "
                + "ORDER BY HolidayDate";

        try {
            stm = connection.prepareStatement(sql);
            // Gán điều kiện tìm theo tên
            stm.setString(1, "%" + keyword + "%");

            // Cố gắng chuyển keyword thành số để gán cho tháng/năm nếu có thể
            int numKeyword = -1;
            try {
                numKeyword = Integer.parseInt(keyword);
            } catch (NumberFormatException e) {
                // Không cần làm gì nếu keyword không phải số
            }

            if (numKeyword >= 1 && numKeyword <= 12) {
                stm.setInt(2, numKeyword); // tháng
            } else {
                stm.setNull(2, java.sql.Types.INTEGER);
            }

            if (numKeyword >= 1000 && numKeyword <= 9999) {
                stm.setInt(3, numKeyword); // năm
            } else {
                stm.setNull(3, java.sql.Types.INTEGER);
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Holiday holiday = new Holiday(
                        rs.getInt("HolidayID"),
                        rs.getDate("HolidayDate").toLocalDate(),
                        rs.getString("Name"),
                        rs.getBoolean("IsPublicHoliday")
                );
                list.add(holiday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean isColumnValueExists(String column, Object value) {
        // Chỉ cho phép một số cột hợp lệ để tránh SQL Injection
        List<String> allowedColumns = Arrays.asList("HolidayDate", "Name", "IsPublicHoliday", "HolidayID");
        if (!allowedColumns.contains(column)) {
            throw new IllegalArgumentException("Cột không hợp lệ: " + column);
        }

        String sql = "SELECT 1 FROM Holidays WHERE " + column + " = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);

            // Set kiểu dữ liệu tương ứng
            if (value instanceof LocalDate) {
                stm.setDate(1, java.sql.Date.valueOf((LocalDate) value));
            } else if (value instanceof String) {
                stm.setString(1, (String) value);
            } else if (value instanceof Integer) {
                stm.setInt(1, (Integer) value);
            } else if (value instanceof Boolean) {
                stm.setBoolean(1, (Boolean) value);
            } else {
                throw new IllegalArgumentException("Kiểu dữ liệu không hỗ trợ: " + value.getClass());
            }

            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkHolidayDate(LocalDate date) {
        return isColumnValueExists("HolidayDate", date);
    }

    public boolean checkName(String name) {
        return isColumnValueExists("Name", name);
    }
    
    public static void main(String[] args) {
        HolidayDAO dao = new HolidayDAO();
        System.out.println(dao.checkHolidayDate(LocalDate.parse("2025-07-24")));
    }

}
