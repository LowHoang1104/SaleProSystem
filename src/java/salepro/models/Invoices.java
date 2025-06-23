/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.CustomerDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.PaymentMethodDAO;
import salepro.dao.StoreDAO;

/**
 *
 * @author MY PC
 */
public class Invoices {
    private int invoiceId;
    private Date invoiceDate;
    private int storeId, userId,CustomerId;
    private double totalAmount, subTotal, VATPercent, VATAmount;
    private int paymentMethodId;

    public Invoices() {
    }

    public Invoices(int invoiceId, Date invoiceDate, int storeId, int userId, int CustomerId, double totalAmount, double subTotal, double VATPercent, double VATAmount, int paymentMethodId) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.storeId = storeId;
        this.userId = userId;
        this.CustomerId = CustomerId;
        this.totalAmount = totalAmount;
        this.subTotal = subTotal;
        this.VATPercent = VATPercent;
        this.VATAmount = VATAmount;
        this.paymentMethodId = paymentMethodId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getVATPercent() {
        return VATPercent;
    }

    public void setVATPercent(double VATPercent) {
        this.VATPercent = VATPercent;
    }

    public double getVATAmount() {
        return VATAmount;
    }

    public void setVATAmount(double VATAmount) {
        this.VATAmount = VATAmount;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
        public String getStoreNameByID() {
        StoreDAO da = new StoreDAO();
        return da.getStoreNameByID(storeId);
    }

    public String getCustomerNameByID() {
        CustomerDAO da = new CustomerDAO();
        return da.getCustomerNameByID(CustomerId);
    }
    
    public String getPaymentMethodNameByID(){
        PaymentMethodDAO da= new PaymentMethodDAO();
        return da.getMethodNameByID(paymentMethodId);
    }

}
