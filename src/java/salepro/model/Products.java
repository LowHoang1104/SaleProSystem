/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author tungd
 */
public class Products {


    private int productID;
    private String productName;
    private String category;
    private String size;
    private String color;
    private String type;
    private BigDecimal price;
    private BigDecimal costPrice;
    private String unit;
    private String description;
    private String images;
    private Boolean status;
    private LocalDate releaseDate;

    public Products() {
    }

    public Products(int productID, String productName, String category, String size, String color, String type, BigDecimal price, BigDecimal costPrice, String unit, String description, String images, Boolean status, LocalDate releaseDate) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.size = size;
        this.color = color;
        this.type = type;
        this.price = price;
        this.costPrice = costPrice;
        this.unit = unit;
        this.description = description;
        this.images = images;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public String getImages() {
        return images;
    }

    public Boolean getStatus() {
        return status;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

}
