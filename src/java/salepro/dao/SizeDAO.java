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
            stm.setString(1, "%" + kw + "%");
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

    public String getSizenameByID(int sizeId) {
        String name = "";
        try {
            String strSQL = "select * from Sizes where SizeID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, sizeId);
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
