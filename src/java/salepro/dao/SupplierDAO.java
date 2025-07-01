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
import salepro.models.Suppliers;

/**
 *
 * @author tungd
 */
public class SupplierDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Suppliers> getData() {
        ArrayList<Suppliers> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from [Suppliers]");
            rs = stm.executeQuery();
            while (rs.next()) {
                Suppliers s = new Suppliers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDate(8));
                data.add(s);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public String getNameById(int supplierID) {

        try {
            String strSQL = "select [SupplierName] from [Suppliers] where [SupplierID] = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, supplierID);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
