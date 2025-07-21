/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.ProductVariantDAO;
import salepro.dao.StockTakeDAO;

/**
 *
 * @author tungd
 */
public class StockTakeDetail {
    private int stockTakeDetailID;
    private int stockTakeID;
    private int productVariantID;
    private int actualQuantity;

    public StockTakeDetail() {}

    public StockTakeDetail(int stockTakeDetailID, int stockTakeID, int productVariantID, int actualQuantity) {
        this.stockTakeDetailID = stockTakeDetailID;
        this.stockTakeID = stockTakeID;
        this.productVariantID = productVariantID;
        this.actualQuantity = actualQuantity;
    }

    public int getStockTakeDetailID() {
        return stockTakeDetailID;
    }

    public void setStockTakeDetailID(int stockTakeDetailID) {
        this.stockTakeDetailID = stockTakeDetailID;
    }

    public int getStockTakeID() {
        return stockTakeID;
    }

    public void setStockTakeID(int stockTakeID) {
        this.stockTakeID = stockTakeID;
    }

    public int getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(int productVariantID) {
        this.productVariantID = productVariantID;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }
    
    public int recordedQuantity(){
        StockTakeDAO stkdao = new StockTakeDAO();
        return stkdao.getQuantityById(productVariantID);
    }
    
    public String productVarianttoString(){
        ProductVariantDAO pvdao = new ProductVariantDAO();
        return pvdao.productVarianttoString(productVariantID);
    }
}

