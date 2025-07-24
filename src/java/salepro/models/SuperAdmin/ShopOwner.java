/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.SuperAdmin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 * @author MY PC
 */
public class ShopOwner {
   private Integer shopOwnerId;
    private String shopName;
    private String ownerName;
    private String phone;
    private String email;
    private String passwordHash;
    private Boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;
    private String subscriptionStatus = "Trial";
    private LocalDateTime lastPaymentDate;

  // Constructors

    public ShopOwner(Integer shopOwnerId, String shopName, String ownerName, String phone, 
                    String email, String passwordHash, LocalDateTime subscriptionEndDate, 
                    LocalDateTime lastPaymentDate) {
        this.shopOwnerId = shopOwnerId;
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
        this.subscriptionEndDate = subscriptionEndDate;
        this.lastPaymentDate = lastPaymentDate;
    }

    // New constructors based on provided usage
    public ShopOwner(String shopName, String ownerName, String email, String phone, String passwordHash, int isActive, Date createdAt) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.isActive = isActive == 1;
        this.createdAt = createdAt != null ? createdAt.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : LocalDateTime.now();
    }

    public ShopOwner(String shopName, String ownerName, String email, String phone, String passwordHash, Integer isActive, Date createdAt) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.isActive = isActive != null && isActive == 1;
        this.createdAt = createdAt != null ? createdAt.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : LocalDateTime.now();
    }

    // Getters and Setters (giữ nguyên như code bạn đã có)
    public Integer getShopOwnerId() { return shopOwnerId; }
    public void setShopOwnerId(Integer shopOwnerId) { this.shopOwnerId = shopOwnerId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getSubscriptionStartDate() { return subscriptionStartDate; }
    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) { this.subscriptionStartDate = subscriptionStartDate; }

    public LocalDateTime getSubscriptionEndDate() { return subscriptionEndDate; }
    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) { this.subscriptionEndDate = subscriptionEndDate; }

    public String getSubscriptionStatus() { return subscriptionStatus; }
    public void setSubscriptionStatus(String subscriptionStatus) { this.subscriptionStatus = subscriptionStatus; }

    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public void setLastPaymentDate(LocalDateTime lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; }

    // Helper methods - THÊM MỚI
    public boolean isSubscriptionExpired() {
        if (subscriptionEndDate == null) return false;
        return LocalDateTime.now().isAfter(subscriptionEndDate);
    }

    public long getRemainingDays() {
        if (subscriptionEndDate == null) return 0;
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), subscriptionEndDate);
        return Math.max(0, days);
    }

    public long getRemainingHours() {
        if (subscriptionEndDate == null) return 0;
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), subscriptionEndDate);
        return Math.max(0, hours % 24);
    }

    public boolean isTrialExpired() {
        return "Trial".equals(subscriptionStatus) && isSubscriptionExpired();
    }

    public boolean needsRenewal() {
        return "Expired".equals(subscriptionStatus) || isTrialExpired();
    }

    public String getSubscriptionStatusBadge() {
        switch (subscriptionStatus) {
            case "Active": return "success";
            case "Trial": return isSubscriptionExpired() ? "danger" : "warning";
            case "Expired": return "danger";
            case "Suspended": return "secondary";
            default: return "secondary";
        }
    }

    public String getSubscriptionStatusText() {
        switch (subscriptionStatus) {
            case "Active": return "Đang hoạt động";
            case "Trial": return "Dùng thử";
            case "Expired": return "Hết hạn";
            case "Suspended": return "Tạm khóa";
            default: return subscriptionStatus;
        }
    }
    
    
    
    
    
    
    
    
    
    
     public enum SubscriptionStatus {
        TRIAL("Trial"),
        ACTIVE("Active"),
        EXPIRED("Expired"),
        SUSPENDED("Suspended");

        private final String value;

        SubscriptionStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SubscriptionStatus fromString(String text) {
            for (SubscriptionStatus status : SubscriptionStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }

    // Constructors
    public ShopOwner() {
        this.isActive = true;
        this.subscriptionStatus = SubscriptionStatus.TRIAL.getValue();
        this.createdAt = LocalDateTime.now();
    }

    public ShopOwner(String shopName, String ownerName, String email, String passwordHash) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true;
        this.subscriptionStatus = SubscriptionStatus.TRIAL.getValue();
        this.createdAt = LocalDateTime.now();
    }

    public ShopOwner(String shopName, String ownerName, String email, String phone, String passwordHash, LocalDateTime created) {
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true;
        this.phone = phone;
        this.createdAt = created;
        this.subscriptionStatus = SubscriptionStatus.TRIAL.getValue();
    }

    public ShopOwner(Integer shopOwnerID, String shopName, String ownerName, String email, String phone,
            String passwordHash, Boolean isActive, LocalDateTime createdAt,
            LocalDateTime subscriptionStartDate, LocalDateTime subscriptionEndDate,
            String subscriptionStatus, LocalDateTime lastPaymentDate) {
        this.shopOwnerId = shopOwnerID;
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
        this.subscriptionStatus = subscriptionStatus;
        this.lastPaymentDate = lastPaymentDate;
    }

    // Getters and Setters
    public Integer getShopOwnerID() {
        return shopOwnerId;
    }

    public void setShopOwnerID(Integer shopOwnerID) {
        this.shopOwnerId = shopOwnerID;
    }

    // Business Methods
    /**
     * Kiểm tra xem shop có đang hoạt động không
     */
    public boolean isShopActive() {
        return isActive != null && isActive
                && (subscriptionStatus.equals(SubscriptionStatus.ACTIVE.getValue())
                || subscriptionStatus.equals(SubscriptionStatus.TRIAL.getValue()));
    }

    /**
     * Kiểm tra xem shop có hết hạn không
     */
    public boolean isExpired() {
        return subscriptionEndDate != null
                && LocalDateTime.now().isAfter(subscriptionEndDate);
    }

    /**
     * Kiểm tra xem shop có sắp hết hạn không (trong vòng 7 ngày)
     */
    public boolean isExpiringSoon() {
        if (subscriptionEndDate == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysFromNow = now.plusDays(7);

        return subscriptionEndDate.isAfter(now)
                && subscriptionEndDate.isBefore(sevenDaysFromNow);
    }

    /**
     * Tính số ngày còn lại của gói dịch vụ
     */
    public long getDaysRemaining() {
        if (subscriptionEndDate == null) {
            return 0;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(subscriptionEndDate)) {
            return 0;
        }

        return java.time.Duration.between(now, subscriptionEndDate).toDays();
    }

    /**
     * Kích hoạt shop
     */
    public void activate() {
        this.isActive = true;
        this.subscriptionStatus = SubscriptionStatus.ACTIVE.getValue();
    }

    /**
     * Tạm ngưng shop
     */
    public void suspend() {
        this.isActive = false;
        this.subscriptionStatus = SubscriptionStatus.SUSPENDED.getValue();
    }

    /**
     * Gia hạn gói dịch vụ
     */
    public void renewSubscription(int months) {
        LocalDateTime now = LocalDateTime.now();
        this.subscriptionStartDate = now;
        this.subscriptionEndDate = now.plusMonths(months);
        this.subscriptionStatus = SubscriptionStatus.ACTIVE.getValue();
        this.lastPaymentDate = now;
        this.isActive = true;
    }

    /**
     * Lấy tên database của shop
     */
    public String getDatabaseName() {
        return shopName != null ? shopName.toLowerCase().replaceAll("\\s+", "") : null;
    }

}
