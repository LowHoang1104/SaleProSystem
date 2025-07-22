/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.SuperAdmin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author MY PC
 */
public class PaymentTransaction {
   private Integer transactionId;
    private String transactionCode;
    private ShopOwner shopOwner;
    private ServicePackage servicePackage;
    private Double amount;
    private String vnPayTxnRef;
    private String vnPayTransactionNo;
    private String vnPayOrderInfo;
    private String vnPayResponseCode;
    private String vnPayTransactionStatus;
    private String vnPayPayDate;
    private String vnPaySecureHash;
    private String status = "Pending";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime paidAt;
    private LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(15);
    private String notes;

    // Constructors
    public PaymentTransaction() {}

    public PaymentTransaction(Integer transactionId, String transactionCode, ShopOwner shopOwner, 
                            ServicePackage servicePackage, Double amount, String vnPayTxnRef, 
                            String vnPayTransactionNo, String vnPayOrderInfo, String vnPayResponseCode, 
                            String vnPayTransactionStatus, String vnPayPayDate, String vnPaySecureHash, 
                            LocalDateTime paidAt, String notes) {
        this.transactionId = transactionId;
        this.transactionCode = transactionCode;
        this.shopOwner = shopOwner;
        this.servicePackage = servicePackage;
        this.amount = amount;
        this.vnPayTxnRef = vnPayTxnRef;
        this.vnPayTransactionNo = vnPayTransactionNo;
        this.vnPayOrderInfo = vnPayOrderInfo;
        this.vnPayResponseCode = vnPayResponseCode;
        this.vnPayTransactionStatus = vnPayTransactionStatus;
        this.vnPayPayDate = vnPayPayDate;
        this.vnPaySecureHash = vnPaySecureHash;
        this.paidAt = paidAt;
        this.notes = notes;
    }

    // Getters and Setters (giữ nguyên như code bạn đã có)
    public Integer getTransactionId() { return transactionId; }
    public void setTransactionId(Integer transactionId) { this.transactionId = transactionId; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }

    public ShopOwner getShopOwner() { return shopOwner; }
    public void setShopOwner(ShopOwner shopOwner) { this.shopOwner = shopOwner; }

    public ServicePackage getServicePackage() { return servicePackage; }
    public void setServicePackage(ServicePackage servicePackage) { this.servicePackage = servicePackage; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getVnPayTxnRef() { return vnPayTxnRef; }
    public void setVnPayTxnRef(String vnPayTxnRef) { this.vnPayTxnRef = vnPayTxnRef; }

    public String getVnPayTransactionNo() { return vnPayTransactionNo; }
    public void setVnPayTransactionNo(String vnPayTransactionNo) { this.vnPayTransactionNo = vnPayTransactionNo; }

    public String getVnPayOrderInfo() { return vnPayOrderInfo; }
    public void setVnPayOrderInfo(String vnPayOrderInfo) { this.vnPayOrderInfo = vnPayOrderInfo; }

    public String getVnPayResponseCode() { return vnPayResponseCode; }
    public void setVnPayResponseCode(String vnPayResponseCode) { this.vnPayResponseCode = vnPayResponseCode; }

    public String getVnPayTransactionStatus() { return vnPayTransactionStatus; }
    public void setVnPayTransactionStatus(String vnPayTransactionStatus) { this.vnPayTransactionStatus = vnPayTransactionStatus; }

    public String getVnPayPayDate() { return vnPayPayDate; }
    public void setVnPayPayDate(String vnPayPayDate) { this.vnPayPayDate = vnPayPayDate; }

    public String getVnPaySecureHash() { return vnPaySecureHash; }
    public void setVnPaySecureHash(String vnPaySecureHash) { this.vnPaySecureHash = vnPaySecureHash; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public LocalDateTime getExpiredAt() { return expiredAt; }
    public void setExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Helper methods - FIXED
    public boolean isExpired() {
        return expiredAt != null && LocalDateTime.now().isAfter(expiredAt);
    }

    public boolean isCompleted() {
        return "Completed".equals(status);
    }

    public boolean isPending() {
        return "Pending".equals(status);
    }

    public String getFormattedAmount() {
        return String.format("%,.0f", amount);
    }

    public long getMinutesUntilExpiry() {
        if (expiredAt == null) return 0;
        return ChronoUnit.MINUTES.between(LocalDateTime.now(), expiredAt);
    }
}
