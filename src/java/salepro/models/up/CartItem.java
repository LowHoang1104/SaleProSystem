/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

/**
 *
 * @author MY PC
 */
public class CartItem {
    private String productCode;
    private String productName;
    private double price;
    private int quantity;
    

    public CartItem() {
    }

    public CartItem(String productCode, String productName,double price, int quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productId) {
        this.productCode = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
