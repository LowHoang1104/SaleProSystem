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
import salepro.models.ProductVariants;
import java.sql.*;

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

    public List<ProductVariants> getLowStockProducts(int storeId) {
        List<ProductVariants> lowStockProducts = new ArrayList<>();
        String sql = """
        SELECT pv.ProductVariantID, pv.ProductCode, pv.SizeID, pv.ColorID, 
               pv.SKU, pv.Unit, pv.AverageQuantity,
               pm.ProductName, ISNULL(i.Quantity, 0) as CurrentQuantity
        FROM ProductVariants pv 
        JOIN ProductMaster pm ON pv.ProductCode = pm.ProductCode 
        LEFT JOIN Inventory i ON pv.ProductVariantID = i.ProductVariantID 
        LEFT JOIN Warehouses w ON i.WarehouseID = w.WarehouseID 
        WHERE (w.StoreID = ? OR w.StoreID IS NULL) 
          AND ISNULL(i.Quantity, 0) <= ISNULL(pv.AverageQuantity, 10)
          AND ISNULL(pv.AverageQuantity, 10) > 0
        ORDER BY ISNULL(i.Quantity, 0) ASC
        """;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();

            while (rs.next()) {
                ProductVariants variant = new ProductVariants();
                variant.setId(rs.getInt("ProductVariantID"));
                variant.setProductCode(rs.getString("ProductCode"));
                variant.setSizeId(rs.getInt("SizeID"));
                variant.setColorId(rs.getInt("ColorID"));
                variant.setSku(rs.getString("SKU"));
                variant.setUnit(rs.getString("Unit"));
                variant.setAverageQuantity(rs.getInt("AverageQuantity"));

                // For display purposes
                variant.setProductName(rs.getString("ProductName"));
                variant.setQuantity(rs.getInt("CurrentQuantity"));

                lowStockProducts.add(variant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getLowStockProducts: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lowStockProducts;
    }

    public static void main(String[] args) {
        int stock = new InventoryDAO().getQuantityByWarehouseAndVariant(1, 1);
        System.out.println(stock);
    }

}
