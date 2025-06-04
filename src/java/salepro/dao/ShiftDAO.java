/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
