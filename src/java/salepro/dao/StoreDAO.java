/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import salepro.models.Invoices;
import salepro.dal.DBContext;
import salepro.dal.DBContext2;
import salepro.models.Stores;

/**
 *
 * @author tungd
 */
public class StoreDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Stores> getData() {
        ArrayList<Stores> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from Stores");
            rs = stm.executeQuery();
            while (rs.next()) {
                Stores store = new Stores(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5));
                data.add(store);
            }
        } catch (Exception e) {
        }
        return data;
    }

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
            String strSQL = "select * from Stores a where a.StoreID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                int storeID = rs.getInt(1);
                String storeCode = rs.getString(2);
                String storeName = rs.getString(3);
                String address = rs.getString(4);
                String phone = rs.getString(5);
                store = new Stores(storeID, storeCode, storeName, address, phone);
                return store;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public List<Stores> getStores() {
        List<Stores> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Stores";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Stores store = new Stores(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

                data.add(store);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public Stores getStoreByUserId(int userId) {
        try {
            String strSQL = "select c.* from Users a join Employees b on a.UserID=b.UserID join Stores c on b.StoreID=c.StoreID where a.UserID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Stores store = new Stores(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                return store;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getStoreNameById(int storeID) {
        String name = "";
        try {
            String strSQL = "SELECT  * FROM Stores where StoreID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, storeID);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return name;
    }

    public void add(Stores s) {
        try {
            String str = "INSERT INTO Stores (StoreName, Address, Phone) VALUES (?, ?, ?)";
            stm = connection.prepareStatement(str);
            stm.setString(1, s.getStoreName());
            stm.setString(2, s.getAddress());
            stm.setString(3, s.getPhone());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLastInsertID() {
        int id = -1;
        try {
            String str = "SELECT IDENT_CURRENT('Stores') AS LastID";
            stm = connection.prepareStatement(str);
            rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("LastID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void delete(int storeID) {
        try {
            String str = "DELETE FROM Stores WHERE StoreID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, storeID);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkExists(String name, String address) {
        try {
            String str = "SELECT * FROM Stores WHERE StoreName = ? AND Address = ?";
            stm = connection.prepareStatement(str);
            stm.setString(1, name);
            stm.setString(2, address);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(Stores s) {
        String str = "UPDATE Stores SET StoreName = ?, Address = ?, Phone = ? WHERE StoreID = ?";
        try {
            stm = connection.prepareStatement(str);
            stm.setString(1, s.getStoreName());
            stm.setString(2, s.getAddress());
            stm.setString(3, s.getPhone());
            stm.setInt(4, s.getStoreID());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Stores> searchByName(String kw) {
        List<Stores> list = new ArrayList<>();
        try {
            String str = "SELECT * FROM Stores WHERE StoreName LIKE ?";
            stm = connection.prepareStatement(str);
            stm.setString(1, "%" + kw + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                Stores s = new Stores(
                        rs.getInt("StoreID"),
                        rs.getString("StoreCode"),
                        rs.getString("StoreName"),
                        rs.getString("Address"),
                        rs.getString("Phone")
                );
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getStoreIdByName(String storeName) {
        try {
            String sql = "SELECT StoreID FROM Stores WHERE StoreName LIKE ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, storeName);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("StoreID");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return 1;

    }
}
