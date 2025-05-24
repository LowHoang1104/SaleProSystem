/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;

/**
 *
 * @author MY PC
 */
public class Invoices {
    private int invoiceId;
    private Date invoiceDate;
    private int storeId, employeeId,CustomerId;
    private double totalAmount;
    private int paymentMethodId;

    public Invoices() {
    }

    public Invoices(int invoiceId, Date invoiceDate, int storeId, int employeeId, int CustomerId, double totalAmount, int paymentMethodId) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.storeId = storeId;
        this.employeeId = employeeId;
        this.CustomerId = CustomerId;
        this.totalAmount = totalAmount;
        this.paymentMethodId = paymentMethodId;
    }

    
    public int getId() {
        return invoiceId;
    }

    public void setId(int id) {
        this.invoiceId = id;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
}
