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
import salepro.models.ProductTypes;

/**
 *
 * @author tungd
 */
public class TypeDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public List<ProductTypes> getTypes() {
        List<ProductTypes> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM ProductTypes";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                ProductTypes b = new ProductTypes(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public String getNameByID(int id) {
        String name = "";
        try {
            String strSQL = "SELECT  * FROM ProductTypes where TypeID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return name;
    }

    public void addType(String name) {
        try {
            String strSQL = "INSERT INTO ProductTypes (TypeName)\n"
                    + "VALUES (?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ProductTypes> searchByKw(String kw) {
        List<ProductTypes> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM ProductTypes where TypeName like ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%"+kw+"%");
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                ProductTypes b = new ProductTypes(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
