/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;

/**
 *
 * @author MY PC
 */
public class Purchases {
    private int purchaseID;
    private Date purchaseDate;
    private String supplierName;
    private int warehouseID;
    private double totalAmount;

    public Purchases() {
    }

    public Purchases(int purchaseID, Date purchaseDate, String supplierName, int warehouseID, double totalAmount) {
        this.purchaseID = purchaseID;
        this.purchaseDate = purchaseDate;
        this.supplierName = supplierName;
        this.warehouseID = warehouseID;
        this.totalAmount = totalAmount;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
