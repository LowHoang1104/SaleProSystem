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

    PreparedStatement stm; 
    ResultSet rs; 
    
    private  static final String GET_DATA = "SELECT  * FROM Sizes";
    private  static final String GET_ID_BY_NAME = " SELECT  * FROM Sizes where SizeName like ? ";
    
    public List<Sizes> getSize() {
        List<Sizes> data = new ArrayList<>();
        try {
            
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Sizes b = new Sizes(id, name);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println("err" + e.getMessage());
        }
        return data;
    }
    
    public int getIdByName(String name) {
        try {
            
            stm = connection.prepareStatement(GET_ID_BY_NAME);
            stm.setString(1, name);
            rs = stm.executeQuery();
            while (rs.next()) {
                 int id = rs.getInt(1);
                return id;
            }
        } catch (Exception e) {
            System.out.println("err" + e.getMessage());
        }
        return 0;
    }
}
