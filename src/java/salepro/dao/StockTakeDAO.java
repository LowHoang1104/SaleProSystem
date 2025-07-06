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
import salepro.dal.DBContext;
import salepro.models.ProductVariants;
import salepro.models.StockTake;
import salepro.models.StockTakeDetail;

/**
 *
 * @author tungd
 */
public class StockTakeDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<StockTake> getStockTake() {
        List<StockTake> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("select * from StockTake");
            rs = stm.executeQuery();
            while (rs.next()) {
                int stid = rs.getInt(1);
                int whid = rs.getInt(2);
                Date date = rs.getDate(3);
                int checkBy = rs.getInt(4);
                String note = rs.getString(5);
                StockTake st = new StockTake(stid, whid, date, checkBy, note);
                data.add(st);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<StockTakeDetail> getDetailById(int parseInt) {
        List<StockTakeDetail> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("select * from StockTakeDetails where StockTakeID = ?;");
            stm.setInt(1, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int sdid = rs.getInt(1);
                int stkid = rs.getInt(2);
                int pvid = rs.getInt(3);
                int actualquantity = rs.getInt(4);
                StockTakeDetail st = new StockTakeDetail(sdid, stkid, pvid, actualquantity);
                data.add(st);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public int getQuantityById(int productVariantID) {
        try {
            String strSQL = "select Quantity from Inventory where ProductVariantID = ?;";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, productVariantID);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<StockTakeDetail> searchEqual(int parseInt) {
        List<StockTakeDetail> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select std.* from ProductVariants pv join Inventory i on pv.ProductVariantID = i.ProductVariantID join StockTakeDetails std on pv.ProductVariantID = std.ProductVariantID where std.ActualQuantity = i.Quantity and std.StockTakeID = ?;");
            stm.setInt(1, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int sdid = rs.getInt(1);
                int stkid = rs.getInt(2);
                int pvid = rs.getInt(3);
                int actualquantity = rs.getInt(4);
                StockTakeDetail st = new StockTakeDetail(sdid, stkid, pvid, actualquantity);
                data.add(st);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<StockTakeDetail> searchNotEqual(int parseInt) {
        List<StockTakeDetail> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select std.* from ProductVariants pv join Inventory i on pv.ProductVariantID = i.ProductVariantID join StockTakeDetails std on pv.ProductVariantID = std.ProductVariantID where std.ActualQuantity != i.Quantity and std.StockTakeID = ?;");
            stm.setInt(1, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int sdid = rs.getInt(1);
                int stkid = rs.getInt(2);
                int pvid = rs.getInt(3);
                int actualquantity = rs.getInt(4);
                StockTakeDetail st = new StockTakeDetail(sdid, stkid, pvid, actualquantity);
                data.add(st);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public void add(StockTakeDetail sdnew) {
        try {
            String strSQL = "INSERT INTO StockTakeDetails (StockTakeID, ProductVariantID, ActualQuantity)\n"
                    + "VALUES (?, ?, ?);";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, sdnew.getStockTakeID());
            stm.setInt(2, sdnew.getProductVariantID());
            stm.setInt(3, sdnew.getActualQuantity());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
