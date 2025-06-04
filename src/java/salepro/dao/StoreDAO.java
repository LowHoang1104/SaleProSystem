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
import salepro.dal.DBContext2;
import salepro.models.Invoices;
import salepro.dal.DBContext;
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
                Stores store = new Stores(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
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

    public List<Stores> getStores() {
        List<Stores> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Stores";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String add = rs.getString(3);
                String phone = rs.getString(4);
                Stores b = new Stores(id, name, add, phone);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
