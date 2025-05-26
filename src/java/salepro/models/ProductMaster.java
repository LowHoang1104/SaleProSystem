/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.CategoryDAO;
import salepro.dao.TypeDAO;

/**
 *
 * @author MY PC
 */
public class ProductMaster {

    String productCode;
    String productName;
    int categoryId, TypeId;
    double price, costPrice;
    String description, images;
    boolean status;
    Date releaseDate;

    public ProductMaster() {
    }

    public ProductMaster(String productCode, String productName, int categoryId, int TypeId, double price, double costPrice, String description, String images, boolean status, Date releaseDate) {
        this.productCode = productCode;
        this.productName = productName;
        this.categoryId = categoryId;
        this.TypeId = TypeId;
        this.price = price;
        this.costPrice = costPrice;
        this.description = description;
        this.images = images;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getCategoryNameById()
    {
        CategoryDAO cdao = new CategoryDAO();
        return cdao.getNameByID(this.categoryId);
    }
    public String getTypeNameById()
    {
        TypeDAO cdao = new TypeDAO();
        return cdao.getNameByID(this.TypeId);
    }
}
