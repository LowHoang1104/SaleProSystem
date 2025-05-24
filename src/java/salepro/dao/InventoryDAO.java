/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import kiotfpt.dal.DBContext;
import salepro.models.Inventories;

/**
 *
 * @author MY PC
 */
public class InventoryDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_INVENTORY_BY_PRODUCT = "select * from Inventory where ProductID = ?";
    private static final String GET_INVENTORY_BY_WAREHOUSE = "select * from Inventory where WarehouseID = ?";
    private static final String UPDATE_INVENTORY_QUANTITY = "update Inventory set Quantity = ? where ProductID = ? and WarehouseID = ?";
    private static final String GET_LOW_STOCK_PRODUCTS = "select p.ProductID, p.ProductName, i.Quantity from Products p inner join Inventory i on i.Quantity <= 10 and p.Status = 1;";
    private static final String INSERT_INVENTORY = "insert into Inventory(ProductID,WarehouseID,Quantity) values(?,?,?)";

    public List<Inventories> getInventoryByProductId(int productId) {
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVENTORY_BY_PRODUCT);
            stm.setInt(1, productId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int waresouseId = rs.getInt(2);
                int quantity = rs.getInt(3);
                Inventories inventory = new Inventories(productId, waresouseId, quantity);
                data.add(inventory);
            }
        } catch (Exception e) {
            
        }
        return data;
    }
    
    public List<Inventories> getInventoryByWarehouseId(int warehouseId){
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVENTORY_BY_WAREHOUSE);
            stm.setInt(1, warehouseId);
            rs = stm.executeQuery();
            while(rs.next()){
                int productId = rs.getInt(1);
                int quantity = rs.getInt(3);
                Inventories inventory = new Inventories(productId, warehouseId, quantity);
                data.add(inventory);
            }
        } catch (Exception e) {
            
        }       
        return data;
    }
    
    public void updateInventory(int quantity, int productId, int warehouseId){
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
    
    public List<Inventories> getLowStockProducts(){
        List<Inventories> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_LOW_STOCK_PRODUCTS);
            rs = stm.executeQuery();
            while(rs.next()){
                int productId = rs.getInt(1);
                int warehouseId = rs.getInt(2);
                int quantity = rs.getInt(3);
                Inventories inventory = new Inventories(productId, warehouseId, quantity);
                data.add(inventory);
            }
        } catch (Exception e) {
        }
        return data;
    }

}
