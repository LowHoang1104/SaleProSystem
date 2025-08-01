/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.PurchaseDAO;
import salepro.dao.SupplierDAO;
import salepro.dao.WarehouseDAO;

/**
 *
 * @author MY PC
 */
public class Purchases {

    private int purchaseID;
    private String purchaseCode;
    private Date purchaseDate;
    private int supplierID;
    private int warehouseID;
    private double totalAmount;

    public Purchases() {
    }

    public Purchases(int purchaseID, String purchaseCode, Date purchaseDate, int supplierID, int warehouseID, double totalAmount) {
        this.purchaseID = purchaseID;
        this.purchaseCode = purchaseCode;
        this.purchaseDate = purchaseDate;
        this.supplierID = supplierID;
        this.warehouseID = warehouseID;
        this.totalAmount = totalAmount;
    }

    public Purchases(int purchaseID, Date purchaseDate, int supplierID, int warehouseID, double totalAmount) {
        this.purchaseID = purchaseID;
        this.purchaseDate = purchaseDate;
        this.supplierID = supplierID;
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

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getWarehouseNameById() {
        WarehouseDAO wdao = new WarehouseDAO();
        return wdao.getNameById(warehouseID);
    }

    public String getSupplierNameById() {
        SupplierDAO wdao = new SupplierDAO();
        return wdao.getNameById(supplierID);
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getDescription() {
        return "Nhập kho ngày: " + String.valueOf(getPurchaseDate());
    }
    
    public boolean isConfirm(){
        PurchaseDAO pcdao = new PurchaseDAO();
        return pcdao.isConfirm(purchaseID);
    }
}
