package salepro.models;

import java.util.Date;
import salepro.dao.CustomerDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PaymentMethodDAO;
import salepro.dao.StoreDAO;

public class Invoices {

    private int invoiceId;
    private String invoiceCode;// Auto
    private Date invoiceDate, updateDate;
    private int storeId, userId, createdBy;
    private int customerId;
    
    private double totalAmount, subTotal,discount,discountAmount, VATPercent, VATAmount, paidAmount;
    private int paymentMethodId;
    String status;
    
    // THÊM: Fields để cache tên, tránh gọi DAO nhiều lần
    private String customerName;
    private String storeName;
    private String paymentMethodName;

    public Invoices() {
    }

    public Invoices(int invoiceId,String invoiceCode, Date invoiceDate, Date updateDate, int storeId, int userId, int createdBy, int customerId, double totalAmount, double subTotal,double discount,double discountAmount, double VATPercent, double VATAmount, double paidAmount, int paymentMethodId, String status) {
        this.invoiceId = invoiceId;
        this.invoiceCode = invoiceCode;
        this.invoiceDate = invoiceDate;
        this.updateDate = updateDate;
        this.storeId = storeId;
        this.userId = userId;
        this.createdBy = createdBy;
        this.customerId = customerId;  
        this.totalAmount = totalAmount;
        this.subTotal = subTotal;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.VATPercent = VATPercent;
        this.VATAmount = VATAmount;
        this.paidAmount = paidAmount;
        this.paymentMethodId = paymentMethodId;
        this.status = status;
    }

    // Getters/Setters cơ bản
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    // SỬA: CustomerId -> customerId
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    // THÊM: Getters/Setters cho cached names
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getStoreNameByID() {
        if (storeName != null) {
            return storeName;
        }
        StoreDAO da = new StoreDAO();
        return da.getStoreNameByID(storeId);
    }

    public String getCustomerNameByID() {
        if (customerName != null) {
            return customerName;
        }
        CustomerDAO da = new CustomerDAO();
        return da.getCustomerNameByID(customerId);  // SỬA: customerId
    }

    public String getPaymentMethodNameByID() {
        if (paymentMethodName != null) {
            return paymentMethodName;
        }
        PaymentMethodDAO da = new PaymentMethodDAO();
        return da.getMethodNameByID(paymentMethodId);
    }
    
    public int getQuantityById(){
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        return invoiceDAO.getTotalQuantityByInvoice(invoiceId);
    }
}