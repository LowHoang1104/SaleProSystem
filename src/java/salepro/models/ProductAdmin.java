/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author ADMIN
 */
public class ProductAdmin {

    String code;
    String productName;
    String typeName;
    String supplierNumber;
    int numSale;
    String image;

    public ProductAdmin(String code, String productName, String typeName, String supplierNumber, int numSale,String img) {
        this.code = code;
        this.productName = productName;
        this.typeName = typeName;
        this.supplierNumber = supplierNumber;
        this.numSale = numSale;
        this.image=img;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductAdmin() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public int getNumSale() {
        return numSale;
    }

    public void setNumSale(int numSale) {
        this.numSale = numSale;
    }

  
    
}
