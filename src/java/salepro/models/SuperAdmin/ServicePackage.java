/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.SuperAdmin;

import java.time.LocalDateTime;

/**
 *
 * @author MY PC
 */
public class ServicePackage {

    private Integer packageId;
    private String packageName;
    private String description;
    private Integer durationMonths;
    private Double price;
    private Double discountPercent = 0.0;
    private Boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    private Boolean recommended = false;
    private Boolean popular = false;

    // Constructors
    public ServicePackage() {
    }

    public ServicePackage(Integer packageId, String packageName, String description,
            Integer durationMonths, Double price) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
    }

    // Getters and Setters (giữ nguyên như code bạn đã có)
    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
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

    // Fields
// Getters and Setters
    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

// Helper methods for JSP
    public boolean isRecommended() {
        return Boolean.TRUE.equals(recommended);
    }

    public boolean isPopular() {
        return Boolean.TRUE.equals(popular);
    }

}
