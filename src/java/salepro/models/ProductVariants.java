/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.InventoryDAO;
import salepro.dao.ProductMasterDAO;

/**
 *
 * @author MY PC
 */
public class ProductVariants {
    int id;
    String productCode;
    int sizeId;
    int colorId;
    String sku;    
    String unit;
    
    public ProductVariants() {
    }

    public ProductVariants(int id, String productCode, int sizeId, int colorId, String sku, String unit) {
        this.id = id;
        this.productCode = productCode;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.sku = sku;
        this.unit = unit;

    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName(){
        return new ProductMasterDAO().getNameByCode(productCode);
    }
    
    public double getPrice(){
        return new ProductMasterDAO().getPriceByCode(productCode);
    }
    public String getDescription(){
        return new ProductMasterDAO().getDescriptionByCode(productCode);
    }
    public String getCategory(){
        return new ProductMasterDAO().getCategoryByCode(productCode);
    }
    
    public int getStockByWarehouse(int warehouseId){
        return new InventoryDAO().getQuantityByWarehouseAndVariant(warehouseId, id);
    }
    
    @Override
    public String toString() {
        return "ProductVariants{" + "id=" + id + ", productCode=" + productCode + ", sizeId=" + sizeId + ", colorId=" + colorId + ", sku=" + sku + ", unit=" + unit + '}';
    }
    
    
        
}
