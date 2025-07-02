/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.models.Stores;
import salepro.models.Warehouse;

/**
 *
 * @author tungd
 */
public class WarehouseDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public ArrayList<Warehouse> getData() {
        ArrayList<Warehouse> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from Warehouses");
            rs = stm.executeQuery();
            while (rs.next()) {
                Warehouse wh = new Warehouse(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                data.add(wh);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public String getNameById(int warehouseID) {
        try {
            String strSQL = "select w.WarehouseName from Warehouses w where w.WarehouseID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, warehouseID);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void add(Warehouse w) {
        try {
            String strSQL = "INSERT INTO Warehouses (WarehouseName, Address, StoreID)\n"
                    + "VALUES (?, ?, ?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, w.getWarehouseName());
            stm.setString(2, w.getAddress());
            stm.setInt(3, w.getStoreID());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Warehouse w) {
        try {
            String sql = "UPDATE Warehouses SET WarehouseName = ?, Address = ?, StoreID = ? WHERE WarehouseID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, w.getWarehouseName());
            stm.setString(2, w.getAddress());
            stm.setInt(3, w.getStoreID());
            stm.setInt(4, w.getWarehouseID());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkExists(String name, String address, int storeID) {
        try {
            String sql = "SELECT WarehouseID FROM Warehouses WHERE WarehouseName = ? AND Address = ? AND StoreID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, address);
            stm.setInt(3, storeID);
            rs = stm.executeQuery();
            return rs.next(); // Nếu có dòng kết quả thì đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(int id) {
        try {
            String str = "DELETE FROM Warehouses WHERE WarehouseID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkStoreInWarehouse(int storeID) {
        try {
            String str = "SELECT * FROM Warehouses WHERE StoreID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, storeID);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
