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
import salepro.models.Employees;
import salepro.models.PurchaseDetails;
import salepro.models.Purchases;

/**
 *
 * @author ADMIN
 */
public class PurchaseDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Purchases> getData() {
        ArrayList<Purchases> data = new ArrayList<>();
        try {
            String strSQL = "select * from Purchases";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Purchases temp = new Purchases(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5));
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public List<PurchaseDetails> getDetailById(int parseInt) {
        List<PurchaseDetails> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("select * from PurchaseDetails where PurchaseID = ?;");
            stm.setInt(1, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pcid = rs.getInt(1);
                int pvid = rs.getInt(2);
                int quantity = rs.getInt(3);
                Double costPrice = rs.getDouble(4);
                PurchaseDetails st = new PurchaseDetails(pcid, pvid, quantity, costPrice);
                data.add(st);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public void addDetail(PurchaseDetails pd) {
        try {
        String strSQL = "INSERT INTO PurchaseDetails (PurchaseID, ProductVariantID, Quantity, CostPrice) "
                      + "VALUES (?, ?, ?, ?)";
        stm = connection.prepareStatement(strSQL);
        stm.setInt(1, pd.getPurchaseID());
        stm.setInt(2, pd.getProductID());
        stm.setInt(3, pd.getQuantity());
        stm.setDouble(4, pd.getCostPrice());
        stm.execute();
    } catch (Exception e) {
        System.out.println("Add PurchaseDetail Error: " + e.getMessage());
    }
    }
}
