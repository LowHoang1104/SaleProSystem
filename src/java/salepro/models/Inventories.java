/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Inventories {
    private int productID;
    private int warehouseID;
    private int quantity;

    public Inventories() {
    }

    public Inventories(int productID, int warehouseID, int quantity) {
        this.productID = productID;
        this.warehouseID = warehouseID;
        this.quantity = quantity;
    }
    
    public int getProductID() {
        return productID;
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getWarehouseID() {
        return warehouseID;
    }
    
    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
