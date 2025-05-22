/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kiotfpt.model.ShopOwners;

/**
 *
 * @author ADMIN
 */
public class ShopOwners {
    private String ShopOwnerID,ShopName,OwnerName,Email,Phone,PasswordHash,IsActive,CreatedAt;

    public ShopOwners(String ShopOwnerID, String ShopName, String OwnerName, String Email, String Phone, String PasswordHash, String IsActive, String CreatedAt) {
        this.ShopOwnerID = ShopOwnerID;
        this.ShopName = ShopName;
        this.OwnerName = OwnerName;
        this.Email = Email;
        this.Phone = Phone;
        this.PasswordHash = PasswordHash;
        this.IsActive = IsActive;
        this.CreatedAt = CreatedAt;
    }

    public ShopOwners() {
    }

    public String getShopOwnerID() {
        return ShopOwnerID;
    }

    public void setShopOwnerID(String ShopOwnerID) {
        this.ShopOwnerID = ShopOwnerID;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String OwnerName) {
        this.OwnerName = OwnerName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String IsActive) {
        this.IsActive = IsActive;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
    
}
