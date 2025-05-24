/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Warehouse {
    private int warehouseID;
    private String warehouseName;
    private int storeID;

    public Warehouse() {
    }

    public Warehouse(int warehouseID, String warehouseName, int storeID) {
        this.warehouseID = warehouseID;
        this.warehouseName = warehouseName;
        this.storeID = storeID;
    }
    
    public int getWarehouseID() {
        return warehouseID;
    }
    
    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public int getStoreID() {
        return storeID;
    }
    
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
}
