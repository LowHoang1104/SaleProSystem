/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Stores {
    private int storeID;
    private String storeCode;
    private String storeName;
    private String address;
    private String phone;

    public Stores() {
    }

    public Stores(int storeID, String storeCode, String storeName, String address, String phone) {
        this.storeID = storeID;
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
    }

    public Stores(int storeID, String storeName, String address, String phone) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.address = address;
        this.phone = phone;
    }

   
    
    public int getStoreID() {
        return storeID;
    }
    
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
    
}
