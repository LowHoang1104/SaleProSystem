/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

import salepro.dao.ProductDAO;

/**
 *
 * @author ADMIN
 */
public class InvoiceDetails {
    private int InvoiceID;
    private int ProductID;
    private int Quantity;
    private double UnitPrice;
    private double DiscountPercent;

    public InvoiceDetails(int InvoiceID, int ProductID, int Quantity, double UnitPrice, double DiscountPercent) {
        this.InvoiceID = InvoiceID;
        this.ProductID = ProductID;
        this.Quantity = Quantity;
        this.UnitPrice = UnitPrice;
        this.DiscountPercent = DiscountPercent;
    }

    public InvoiceDetails() {
    }

    public int getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(int InvoiceID) {
        this.InvoiceID = InvoiceID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }

    public double getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(double DiscountPercent) {
        this.DiscountPercent = DiscountPercent;
    }
    
    public String getNameProductNameByID(){
        ProductDAO da= new ProductDAO();
        return da.getProductNameByID(ProductID);       
    }
    
}
