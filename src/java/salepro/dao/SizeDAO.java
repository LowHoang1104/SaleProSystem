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
import salepro.models.Sizes;

/**
 *
 * @author tungd
 */
public class SizeDAO extends DBContext2 {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public List<Sizes> getSize() {
        List<Sizes> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Sizes";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Sizes b = new Sizes(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return data;
    }
}
