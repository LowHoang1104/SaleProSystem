/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;


/**
 *
 * @author MY PC
 */
public class InvoiceDetails {

    private int invoiceId;
    private int productId;
    private int quantity;
    private double unitPrice;
    private double discountPercent;

    public InvoiceDetails() {
    }

    public InvoiceDetails(int invoiceID, int productID, int quantity, double unitPrice, double discountPercent) {
        this.invoiceId = invoiceID;
        this.productId = productID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountPercent = discountPercent;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceID) {
        this.invoiceId = invoiceID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productID) {
        this.productId = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getNameProductNameByID() {
//        ProductDAO da= new ProductDAO();
//        return da.getProductNameByID(productId); 
        return null;
    }

}
