package salepro.models.up;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model class cho Payment Transaction
 * Tương ứng với bảng PaymentTransactions trong database
 */
public class PaymentTransaction {
    private Integer transactionID;
    private String transactionCode;
    private Integer shopOwnerID;
    private Integer packageID;
    private BigDecimal amount;
    
    // VNPay fields
    private String vnPayTxnRef;
    private String vnPayTransactionNo;
    private String vnPayOrderInfo;
    private String vnPayResponseCode;
    private String vnPayTransactionStatus;
    private String vnPayPayDate;
    private String vnPaySecureHash;
    
    // Status
    private String status;
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private LocalDateTime expiredAt;
    
    // Notes
    private String notes;
    
    // Related objects
    private ShopOwner shopOwner;
    private ServicePackage servicePackage;

    // Enum cho Transaction Status
    public enum TransactionStatus {
        PENDING("Pending"),
        COMPLETED("Completed"),
        FAILED("Failed"),
        CANCELLED("Cancelled");

        private final String value;

        TransactionStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static TransactionStatus fromString(String text) {
            for (TransactionStatus status : TransactionStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }

    // Constructors
    public PaymentTransaction() {
        this.status = TransactionStatus.PENDING.getValue();
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(15); // Hết hạn sau 15 phút
    }

    public PaymentTransaction(Integer shopOwnerID, Integer packageID, BigDecimal amount) {
        this.shopOwnerID = shopOwnerID;
        this.packageID = packageID;
        this.amount = amount;
        this.status = TransactionStatus.PENDING.getValue();
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(15);
        this.transactionCode = generateTransactionCode();
    }

    public PaymentTransaction(Integer transactionID, String transactionCode, Integer shopOwnerID, 
                            Integer packageID, BigDecimal amount, String status, 
                            LocalDateTime createdAt, LocalDateTime paidAt, LocalDateTime expiredAt) {
        this.transactionID = transactionID;
        this.transactionCode = transactionCode;
        this.shopOwnerID = shopOwnerID;
        this.packageID = packageID;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.expiredAt = expiredAt;
    }

    // Getters and Setters
    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Integer getShopOwnerID() {
        return shopOwnerID;
    }

    public void setShopOwnerID(Integer shopOwnerID) {
        this.shopOwnerID = shopOwnerID;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVnPayTxnRef() {
        return vnPayTxnRef;
    }

    public void setVnPayTxnRef(String vnPayTxnRef) {
        this.vnPayTxnRef = vnPayTxnRef;
    }

    public String getVnPayTransactionNo() {
        return vnPayTransactionNo;
    }

    public void setVnPayTransactionNo(String vnPayTransactionNo) {
        this.vnPayTransactionNo = vnPayTransactionNo;
    }

    public String getVnPayOrderInfo() {
        return vnPayOrderInfo;
    }

    public void setVnPayOrderInfo(String vnPayOrderInfo) {
        this.vnPayOrderInfo = vnPayOrderInfo;
    }

    public String getVnPayResponseCode() {
        return vnPayResponseCode;
    }

    public void setVnPayResponseCode(String vnPayResponseCode) {
        this.vnPayResponseCode = vnPayResponseCode;
    }

    public String getVnPayTransactionStatus() {
        return vnPayTransactionStatus;
    }

    public void setVnPayTransactionStatus(String vnPayTransactionStatus) {
        this.vnPayTransactionStatus = vnPayTransactionStatus;
    }

    public String getVnPayPayDate() {
        return vnPayPayDate;
    }

    public void setVnPayPayDate(String vnPayPayDate) {
        this.vnPayPayDate = vnPayPayDate;
    }

    public String getVnPaySecureHash() {
        return vnPaySecureHash;
    }

    public void setVnPaySecureHash(String vnPaySecureHash) {
        this.vnPaySecureHash = vnPaySecureHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ShopOwner getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(ShopOwner shopOwner) {
        this.shopOwner = shopOwner;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    // Business Methods
    /**
     * Tạo mã giao dịch tự động
     */
    private String generateTransactionCode() {
        return "TXN" + System.currentTimeMillis();
    }

    /**
     * Kiểm tra xem giao dịch có đang chờ xử lý không
     */
    public boolean isPending() {
        return TransactionStatus.PENDING.getValue().equals(status);
    }

    /**
     * Kiểm tra xem giao dịch đã hoàn thành chưa
     */
    public boolean isCompleted() {
        return TransactionStatus.COMPLETED.getValue().equals(status);
    }

    /**
     * Kiểm tra xem giao dịch có bị thất bại không
     */
    public boolean isFailed() {
        return TransactionStatus.FAILED.getValue().equals(status);
    }

    /**
     * Kiểm tra xem giao dịch có bị hủy không
     */
    public boolean isCancelled() {
        return TransactionStatus.CANCELLED.getValue().equals(status);
    }

    /**
     * Kiểm tra xem giao dịch có hết hạn không
     */
    public boolean isExpired() {
        return expiredAt != null && LocalDateTime.now().isAfter(expiredAt);
    }

    /**
     * Đánh dấu giao dịch thành công
     */
    public void markAsCompleted() {
        this.status = TransactionStatus.COMPLETED.getValue();
        this.paidAt = LocalDateTime.now();
    }

    /**
     * Đánh dấu giao dịch thất bại
     */
    public void markAsFailed() {
        this.status = TransactionStatus.FAILED.getValue();
    }

    /**
     * Đánh dấu giao dịch bị hủy
     */
    public void markAsCancelled() {
        this.status = TransactionStatus.CANCELLED.getValue();
    }

    /**
     * Cập nhật thông tin VNPay
     */
    public void updateVNPayInfo(String txnRef, String transactionNo, String responseCode, 
                               String transactionStatus, String payDate, String secureHash) {
        this.vnPayTxnRef = txnRef;
        this.vnPayTransactionNo = transactionNo;
        this.vnPayResponseCode = responseCode;
        this.vnPayTransactionStatus = transactionStatus;
        this.vnPayPayDate = payDate;
        this.vnPaySecureHash = secureHash;
    }

    /**
     * Lấy thông tin hiển thị của giao dịch
     */
    public String getDisplayInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Giao dịch: ").append(transactionCode);
        
        if (shopOwner != null) {
            info.append(" - Shop: ").append(shopOwner.getShopName());
        }
        
        if (servicePackage != null) {
            info.append(" - Gói: ").append(servicePackage.getPackageName());
        }
        
        if (amount != null) {
            info.append(" - Số tiền: ").append(String.format("%,.0f VND", amount));
        }
        
        info.append(" - Trạng thái: ").append(getStatusDisplayName());
        
        return info.toString();
    }

    /**
     * Lấy tên hiển thị của trạng thái
     */
    public String getStatusDisplayName() {
        switch (status) {
            case "Pending": return "Chờ xử lý";
            case "Completed": return "Thành công";
            case "Failed": return "Thất bại";
            case "Cancelled": return "Đã hủy";
            default: return status;
        }
    }

    /**
     * Lấy thời gian còn lại trước khi hết hạn (phút)
     */
    public long getMinutesUntilExpiry() {
        if (expiredAt == null) return 0;
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(expiredAt)) return 0;
        
        return java.time.Duration.between(now, expiredAt).toMinutes();
    }

    /**
     * Kiểm tra xem giao dịch có thể được xử lý không
     */
    public boolean canProcess() {
        return isPending() && !isExpired();
    }

    /**
     * Lấy thông tin VNPay để hiển thị
     */
    public String getVNPayInfo() {
        if (vnPayTxnRef == null) return "Chưa có thông tin VNPay";
        
        return String.format("VNPay Ref: %s, Status: %s", 
                           vnPayTxnRef, 
                           vnPayTransactionStatus != null ? vnPayTransactionStatus : "N/A");
    }

} 