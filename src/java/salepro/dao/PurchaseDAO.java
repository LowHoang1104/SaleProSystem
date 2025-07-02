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
import salepro.models.PurchaseDetails;
import salepro.models.Purchases;
import java.sql.Timestamp;

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

    public void addPurchase(Purchases p) {
        try {
            String strSQL = "INSERT INTO Purchases (PurchaseDate, SupplierID, WarehouseID, TotalAmount)\n"
                    + "VALUES (?, ?, ?, 0);";
            stm = connection.prepareStatement(strSQL);
            stm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stm.setInt(2, p.getSupplierID());
            stm.setInt(3, p.getWarehouseID());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTotalAmountById(int purchaseID) {
        try {
            // B1: Tính tổng tiền từ PurchaseDetails
            String sqlSum = "SELECT SUM(Quantity * CostPrice) AS TotalAmount FROM PurchaseDetails WHERE PurchaseID = ?";
            PreparedStatement ps = connection.prepareStatement(sqlSum);
            ps.setInt(1, purchaseID);
            ResultSet rs = ps.executeQuery();

            double total = 0;
            if (rs.next()) {
                total = rs.getDouble("TotalAmount");
            }

            // B2: Cập nhật lại vào bảng Purchases
            String sqlUpdate = "UPDATE Purchases SET TotalAmount = ? WHERE PurchaseID = ?";
            PreparedStatement updatePs = connection.prepareStatement(sqlUpdate);
            updatePs.setDouble(1, total);
            updatePs.setInt(2, purchaseID);
            updatePs.executeUpdate();

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật tổng tiền: " + e.getMessage());
        }
    }

    public void updateDetail(PurchaseDetails updated) {
        try {
            String sql = "UPDATE PurchaseDetails SET Quantity = ?, CostPrice = ? WHERE PurchaseID = ? AND ProductVariantID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, updated.getQuantity());
            stm.setDouble(2, updated.getCostPrice());
            stm.setInt(3, updated.getPurchaseID());
            stm.setInt(4, updated.getProductID());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật chi tiết đơn mua hàng: " + e.getMessage());
        }
    }

    public void deleteDetail(int purchaseId, int variantId) {
        try {
            String sql = "DELETE FROM PurchaseDetails WHERE PurchaseID = ? AND ProductVariantID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, purchaseId);
            stm.setInt(2, variantId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa chi tiết đơn mua hàng: " + e.getMessage());
        }
    }

    public void delete(int purchaseID) {
        try {
            // Xóa chi tiết trước
            String delDetail = "DELETE FROM PurchaseDetails WHERE PurchaseID = ?";
            stm = connection.prepareStatement(delDetail);
            stm.setInt(1, purchaseID);
            stm.executeUpdate();

            // Xóa purchase chính
            String delPurchase = "DELETE FROM Purchases WHERE PurchaseID = ?";
            stm = connection.prepareStatement(delPurchase);
            stm.setInt(1, purchaseID);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Purchases> searchByWarehouseID(int warehouseID) {
        List<Purchases> list = new ArrayList<>();
        try {
            String str = "SELECT * FROM Purchases WHERE WarehouseID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, warehouseID);
            rs = stm.executeQuery();
            while (rs.next()) {
                Purchases temp = new Purchases(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5));
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
