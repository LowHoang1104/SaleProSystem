/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

import java.util.List;
import salepro.dao.ProductVariantDAO;
import salepro.models.Customers;
import salepro.models.ProductVariants;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
public class CartItem {

    private String productCode;
    private String productName;
    private double price;
    private int quantity;
    private String color;
    private String size;
    private int stock;
    private String status;
    private double discount;
    

    private int productVariantId;

    public CartItem() {
    }

    public CartItem(String productCode, String productName, double price, int quantity) {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getColorListByMasterCode() {
        return new ProductVariantDAO().geColorListByMasterCode(productCode);
    }

    public List<String> getSizeListByMasterCode() {
        return new ProductVariantDAO().geSizeListByMasterCode(productCode);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
       
}
