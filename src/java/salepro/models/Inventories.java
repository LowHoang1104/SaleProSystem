/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.ProductVariantDAO;

/**
 *
 * @author MY PC
 */
public class Inventories {
    private int productVariantID;
    private int warehouseID;
    private int quantity;

    public Inventories() {}

    public Inventories(int productVariantID, int warehouseID, int quantity) {
        this.productVariantID = productVariantID;
        this.warehouseID = warehouseID;
        this.quantity = quantity;
    }

    public int getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(int productVariantID) {
        this.productVariantID = productVariantID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String productVarianttoString(){
        ProductVariantDAO pvdao = new ProductVariantDAO();
        return pvdao.productVarianttoString(productVariantID);
    }
}
