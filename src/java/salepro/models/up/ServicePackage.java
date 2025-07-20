package salepro.models.up;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model class cho Service Package
 * Tương ứng với bảng ServicePackages trong database
 */
public class ServicePackage {
    private Integer packageID;
    private String packageName;
    private String description;
    private Integer durationMonths;
    private BigDecimal price;
    private BigDecimal discountPercent;
    private Boolean isActive;
    private LocalDateTime createdAt;

    // Constructors
    public ServicePackage() {
        this.isActive = true;
        this.discountPercent = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public ServicePackage(String packageName, String description, Integer durationMonths, BigDecimal price) {
        this.packageName = packageName;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
        this.isActive = true;
        this.discountPercent = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public ServicePackage(Integer packageID, String packageName, String description, Integer durationMonths, 
                         BigDecimal price, BigDecimal discountPercent, Boolean isActive, LocalDateTime createdAt) {
        this.packageID = packageID;
        this.packageName = packageName;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
        this.discountPercent = discountPercent;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Business Methods
    /**
     * Tính giá sau khi áp dụng giảm giá
     */
    public BigDecimal getDiscountedPrice() {
        if (price == null || discountPercent == null) {
            return price;
        }
        
        BigDecimal discountAmount = price.multiply(discountPercent)
                                        .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return price.subtract(discountAmount);
    }

    /**
     * Tính số tiền được giảm giá
     */
    public BigDecimal getDiscountAmount() {
        if (price == null || discountPercent == null) {
            return BigDecimal.ZERO;
        }
        
        return price.multiply(discountPercent)
                   .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Kiểm tra xem gói có đang hoạt động không
     */
    public boolean isPackageActive() {
        return isActive != null && isActive;
    }

    /**
     * Lấy thông tin hiển thị của gói
     */
    public String getDisplayInfo() {
        StringBuilder info = new StringBuilder();
        info.append(packageName);
        
        if (durationMonths != null) {
            if (durationMonths == 1) {
                info.append(" (1 tháng)");
            } else if (durationMonths == 12) {
                info.append(" (1 năm)");
            } else if (durationMonths == 24) {
                info.append(" (2 năm)");
            } else {
                info.append(" (").append(durationMonths).append(" tháng)");
            }
        }
        
        if (discountPercent != null && discountPercent.compareTo(BigDecimal.ZERO) > 0) {
            info.append(" - Giảm ").append(discountPercent).append("%");
        }
        
        return info.toString();
    }

    /**
     * Lấy giá hiển thị (có thể là giá gốc hoặc giá sau giảm)
     */
    public String getDisplayPrice() {
        if (price == null) return "0 VND";
        
        BigDecimal displayPrice = getDiscountedPrice();
        return String.format("%,.0f VND", displayPrice);
    }

    /**
     * Kiểm tra xem gói có phải là gói dài hạn không (6 tháng trở lên)
     */
    public boolean isLongTermPackage() {
        return durationMonths != null && durationMonths >= 6;
    }

    /**
     * Kiểm tra xem gói có phải là gói ngắn hạn không (dưới 6 tháng)
     */
    public boolean isShortTermPackage() {
        return durationMonths != null && durationMonths < 6;
    }

    /**
     * Tính giá trung bình mỗi tháng
     */
    public BigDecimal getPricePerMonth() {
        if (price == null || durationMonths == null || durationMonths <= 0) {
            return BigDecimal.ZERO;
        }
        
        return getDiscountedPrice().divide(BigDecimal.valueOf(durationMonths), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * So sánh giá trị của gói với gói khác
     */
    public int compareValue(ServicePackage other) {
        if (other == null) return 1;
        
        BigDecimal thisValue = getPricePerMonth();
        BigDecimal otherValue = other.getPricePerMonth();
        
        return thisValue.compareTo(otherValue);
    }

    /**
     * Kích hoạt gói
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * Tạm ngưng gói
     */
    public void deactivate() {
        this.isActive = false;
    }

  

} 