/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.StoreDAO;
import salepro.dao.WarehouseDAO;

/**
 *
 * @author MY PC
 */
public class Warehouse {
    private int warehouseID;
    private String warehouseName;
    private int storeID;
    private String address;
    public Warehouse() {
    }

    public Warehouse(int warehouseID, String warehouseName,String address, int storeID) {
        this.warehouseID = warehouseID;
        this.warehouseName = warehouseName;
        this.storeID = storeID;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public String getStoreNameById(){
        StoreDAO stdao = new StoreDAO();
        return stdao.getStoreNameById(this.storeID);
    }
}
