/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.ColorDAO;
import salepro.dao.ProductVariantDAO;
import salepro.dao.SizeDAO;
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
        this.sku = generateSKU();
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

    public int AverageQuantity;

    public ProductVariants(int id, String productCode, int sizeId, int colorId, String sku, String unit, int AverageQuantity) {
        this.id = id;
        this.productCode = productCode;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.unit = unit;
        if (sku == null) {
            this.sku = generateSKU();
        } else {
            this.sku = sku;
        }
        this.AverageQuantity = AverageQuantity;
    }

    public int getAverageQuantity() {
        return AverageQuantity;
    }

    public void setAverageQuantity(int AverageQuantity) {
        this.AverageQuantity = AverageQuantity;
    }

    public String getColornameByID() {
        ColorDAO cdao = new ColorDAO();
        return cdao.getColornameByID(this.colorId);
    }

    public String getSizenameByID() {
        SizeDAO sdao = new SizeDAO();
        return sdao.getSizenameByID(this.sizeId);
    }

    public String generateSKU() {
        String code = (productCode != null) ? productCode.toUpperCase() : "NA";

        // Lấy 1 lần duy nhất
        String sizeName = getSizenameByID();
        String colorName = getColornameByID();

        String size = (sizeName != null)
                ? sizeName.toUpperCase()
                : "XX";

        String color = (colorName != null && colorName.length() >= 2)
                ? colorName.substring(0, 2).toUpperCase()
                : "XX";

        String unitInitial = (unit != null && !unit.trim().isEmpty())
                ? unit.trim().substring(0, 1).toUpperCase()
                : "UN";
        System.out.println(">>> unitInitial: " + unitInitial);
        return String.join("-", code, size, color, unitInitial);
    }

    public String productVarianttoString() {
        ProductVariantDAO pvdao = new ProductVariantDAO();
        return pvdao.productVarianttoString(id);
    }

    // For testing
    public static void main(String[] args) {
        ProductVariants pv = new ProductVariants();
        pv.productCode = "prd123";
        pv.sizeId = 1;
        pv.colorId = 1;
        pv.unit = "cai";
        System.out.println(">>> unit: " + pv.unit);
        pv.generateSKU();
        System.out.println("Generated SKU: " + pv.generateSKU()); // Output: PRD123-42-RE-B
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
