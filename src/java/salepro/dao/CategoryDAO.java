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
import salepro.models.Categories;

/**
 *
 * @author tungd
 */
public class CategoryDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public List<Categories> getCategory() {
        List<Categories> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Categories";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int typeID = rs.getInt(3);
                Categories b = new Categories(id, name, typeID);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return data;
    }
    
    public String getNameByID(int id){
        String name="";
       try {
            String strSQL = "SELECT  * FROM Categories where CategoryID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if(rs.next()) {
                name = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return name;
    }
}
