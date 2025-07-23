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
import salepro.models.Inventories;

/**
 *
 * @author MY PC
 */
public class InventoryDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_INVENTORY_BY_PRODUCT = "SELECT * FROM Inventory WHERE ProductVariantID = ?";
    private static final String GET_INVENTORY_BY_WAREHOUSE = "SELECT * FROM Inventory WHERE WarehouseID = ?";
    private static final String UPDATE_INVENTORY_QUANTITY = "UPDATE Inventory SET Quantity = ? WHERE ProductVariantID = ? AND WarehouseID = ?";
    private static final String INSERT_INVENTORY = "INSERT INTO Inventory(ProductVariantID, WarehouseID, Quantity) VALUES (?, ?, ?)";
    private static final String GET_LOW_STOCK_PRODUCTS = "SELECT i.ProductVariantID, i.WarehouseID, i.Quantity FROM Inventory i WHERE i.Quantity <= 10";
    private static final String GET_QUANTITY = "SELECT Quantity FROM Inventory WHERE WarehouseID = ? AND ProductVariantID = ?";

    public List<Inventories> getInventoryByProductVariantId(int productVariantId) {
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVENTORY_BY_PRODUCT);
            stm.setInt(1, productVariantId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int warehouseId = rs.getInt("WarehouseID");
                int quantity = rs.getInt("Quantity");
                data.add(new Inventories(productVariantId, warehouseId, quantity));
            }
        } catch (Exception e) {
            System.out.println("getInventoryByProductVariantId: " + e.getMessage());
        }
        return data;
    }

    public List<Inventories> getInventoryByWarehouseId(int warehouseId) {
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("SELECT * FROM Inventory WHERE WarehouseID = ?");
            stm.setInt(1, warehouseId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("ProductVariantID");
                int quantity = rs.getInt("Quantity");
                Inventories inventory = new Inventories(productId, warehouseId, quantity);
                data.add(inventory);
            }
        } catch (Exception e) {
            System.out.println("sql err");; // giúp bạn debug nếu lỗi
        }
        return data;
    }


    public void updateInventory(int quantity, int productId, int warehouseId) {
        try {
            stm = connection.prepareStatement(UPDATE_INVENTORY_QUANTITY);
            stm.setInt(1, quantity);
            stm.setInt(2, productId);
            stm.setInt(3, warehouseId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateInventory: " + e.getMessage());
        } finally {

        }
    }

    public void insertInventory(int productId, int warehouseId, int quantity) {
        try {
            stm = connection.prepareStatement(INSERT_INVENTORY);
            stm.setInt(1, productId);
            stm.setInt(2, warehouseId);
            stm.setInt(3, quantity);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("insertInventory: " + e.getMessage());
        }
    }

    public List<Inventories> getLowStockProducts() {
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_LOW_STOCK_PRODUCTS);
            rs = stm.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt(1);
                int warehouseId = rs.getInt(2);
                int quantity = rs.getInt(3);
                Inventories inventory = new Inventories(productId, warehouseId, quantity);
                data.add(inventory);
            }
        } catch (Exception e) {
            System.out.println("getLowStockProducts: " + e.getMessage());
        }
        return data;
    }
    
    public int getQuantityByWarehouseAndVariant(int warehouseId, int productVariantId) {
    int quantity = 0;
    
    try {
        stm = connection.prepareStatement(GET_QUANTITY);
        stm.setInt(1, warehouseId);
        stm.setInt(2, productVariantId);
        rs = stm.executeQuery();
        if (rs.next()) {
            quantity = rs.getInt("Quantity");
        }
    } catch (Exception e) {
        System.out.println("getQuantityByWarehouseAndVariant: " + e.getMessage());
    }
    return quantity;
}
    
    public static void main(String[] args) {
        int stock = new InventoryDAO().getQuantityByWarehouseAndVariant(1, 1);
        System.out.println(stock);
    }

}
