/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kiotfpt.model.Users;

/**
 *
 * @author ADMIN
 */
public class Users {
    private String UserID,Username,PasswordHash,RoleID,IsActive,CreatedAt;

    public Users(String UserID, String Username, String PasswordHash, String RoleID, String IsActive, String CreatedAt) {
        this.UserID = UserID;
        this.Username = Username;
        this.PasswordHash = PasswordHash;
        this.RoleID = RoleID;
        this.IsActive = IsActive;
        this.CreatedAt = CreatedAt;
    }

    public Users() {
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String RoleID) {
        this.RoleID = RoleID;
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
