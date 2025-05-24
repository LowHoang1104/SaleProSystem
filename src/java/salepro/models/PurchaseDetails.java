/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class PurchaseDetails {
    private int purchaseID;
    private int productID;
    private int quantity;
    private double costPrice;

    public PurchaseDetails() {
    }

    public PurchaseDetails(int purchaseID, int productID, int quantity, double costPrice) {
        this.purchaseID = purchaseID;
        this.productID = productID;
        this.quantity = quantity;
        this.costPrice = costPrice;
    }
    
    public int getPurchaseID() {
        return purchaseID;
    }
    
    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }
    
    public int getProductID() {
        return productID;
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
}
