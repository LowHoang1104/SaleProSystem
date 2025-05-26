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
import salepro.models.Stores;

/**
 *
 * @author ADMIN
 */
public class StoreDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public String getStoreNameByID(int id) {
        try {
            String strSQL = "select a.StoreName from Stores a where a.StoreID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Stores getStoreByID(int id) {
        Stores store = null;
        try {
            String sql = "SELECT StoreID, StoreName, Address, Phone FROM Stores WHERE StoreID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                store = new Stores(
                        rs.getInt("StoreID"),
                        rs.getString("StoreName"),
                        rs.getString("Address"),
                        rs.getString("Phone")
                );
                return store;
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi để debug, hoặc ghi log nếu cần
        }
        return null;
    }

    public List<Stores> getAllStore() {
        List<Stores> list = new ArrayList<>();
        try {
            String sql = "SELECT StoreID, StoreName, Address, Phone FROM Stores";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Stores store = new Stores(
                        rs.getInt("StoreID"),
                        rs.getString("StoreName"),
                        rs.getString("Address"),
                        rs.getString("Phone")
                );
                list.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log hoặc thông báo lỗi
        }
        return list;
    }

    public static void main(String[] args) {
        StoreDAO dao = new StoreDAO();
        for (Stores stores : dao.getAllStore()) {
            System.out.println(stores.getStoreName());
        }
    }

}
