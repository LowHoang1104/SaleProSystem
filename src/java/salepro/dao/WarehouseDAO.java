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
}
