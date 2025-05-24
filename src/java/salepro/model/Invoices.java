/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

import java.time.LocalDate;
import salepro.dao.CustomerDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.PaymentMethodDAO;
import salepro.dao.StoreDAO;

/**
 *
 * @author ADMIN
 */
public class Invoices {

    private int InvoiceID;
    private LocalDate InvoiceDate;
    private int StoreID;
    private int EmployeeID;
    private int CustomerID;
    private double TotalAmount;
    private int PaymentMethodID;

    public Invoices(int InvoiceID, LocalDate InvoiceDate, int StoreID, int EmployeeID, int CustomerID, double TotalAmount, int PaymentMethodID) {
        this.InvoiceID = InvoiceID;
        this.InvoiceDate = InvoiceDate;
        this.StoreID = StoreID;
        this.EmployeeID = EmployeeID;
        this.CustomerID = CustomerID;
        this.TotalAmount = TotalAmount;
        this.PaymentMethodID = PaymentMethodID;
    }

    public int getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(int InvoiceID) {
        this.InvoiceID = InvoiceID;
    }

    public LocalDate getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(LocalDate InvoiceDate) {
        this.InvoiceDate = InvoiceDate;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int StoreID) {
        this.StoreID = StoreID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public int getPaymentMethodID() {
        return PaymentMethodID;
    }

    public void setPaymentMethodID(int PaymentMethodID) {
        this.PaymentMethodID = PaymentMethodID;
    }

    public Invoices() {
    }

    public String getStoreNameByID() {
        StoreDAO da = new StoreDAO();
        return da.getStoreNameByID(StoreID);
    }

    public String getCustomerNameByID() {
        CustomerDAO da = new CustomerDAO();
        return da.getCustomerNameByID(CustomerID);
    }

    public String getEmployeeNameByID() {
        EmployeeDAO da = new EmployeeDAO();
        return da.getEmployeeNameByID(EmployeeID);
    }
    
    public String getPaymentMethodNameByID(){
        PaymentMethodDAO da= new PaymentMethodDAO();
        return da.getMethodNameByID(PaymentMethodID);
    }
    

}
