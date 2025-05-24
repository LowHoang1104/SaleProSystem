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
public class Products {
    int productId;
    String productName;
    int categoryId, sizeId, ColorId,TypeId;
    double price, costPrice;
    String unit, description,images;
    boolean status;
    Date releaseDate;

    public Products() {
    }

    public Products(int productId, String productName, int categoryId, int sizeId, int ColorId, int TypeId, double price, double costPrice, String unit, String description, String images, boolean status, Date releaseDate) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.sizeId = sizeId;
        this.ColorId = ColorId;
        this.TypeId = TypeId;
        this.price = price;
        this.costPrice = costPrice;
        this.unit = unit;
        this.description = description;
        this.images = images;
        this.status = status;
        this.releaseDate = releaseDate;
    }
    
    

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return ColorId;
    }

    public void setColorId(int ColorId) {
        this.ColorId = ColorId;
    }

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int TypeId) {
        this.TypeId = TypeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    
}
