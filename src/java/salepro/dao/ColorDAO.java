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
import salepro.models.Colors;

/**
 *
 * @author tungd
 */
public class ColorDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public List<Colors> getColors() {
        List<Colors> data = new ArrayList<>();
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
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void addColor(String name) {
        try {
            String strSQL = "INSERT INTO Colors (ColorName)\n"
                    + "VALUES (?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Colors> searchByKw(String kw) {
        List<Colors> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Colors where ColorName like ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + kw + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Colors b = new Colors(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void delColorById(String id) {
        try {
            String strSQL = "DELETE FROM Colors WHERE ColorID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, Integer.parseInt(id));
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean exidColorInProduct(String id) {
        try {
            String strSQL = "select * from ProductVariants where ColorID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, Integer.parseInt(id));
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getColornameByID(int colorId) {
        String name = "";
        try {
            String strSQL = "select * from Colors where ColorID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, colorId);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
}
