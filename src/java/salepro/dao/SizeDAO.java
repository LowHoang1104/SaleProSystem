/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.Sizes;

/**
 *
 * @author tungd
 */
public class SizeDAO extends DBContext {

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

    public void addSize(String name) {
        try {
            String strSQL = "INSERT INTO Sizes (SizeName)\n"
                    + "VALUES (?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Sizes> searchByKw(String kw) {
        List<Sizes> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Sizes where SizeName like ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%"+kw+"%");
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
