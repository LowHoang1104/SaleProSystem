/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author tungd
 */
public class SupplierDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

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
