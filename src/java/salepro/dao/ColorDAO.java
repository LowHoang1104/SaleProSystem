/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.model.Colors;

/**
 *
 * @author tungd
 */
public class ColorDAO extends DBContext2 {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public ArrayList<Colors> getColors() {
        ArrayList<Colors> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Colors";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Colors b = new Colors(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return data;
    }
}
