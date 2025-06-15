/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class SuperAdmins {
      private int superAdminID;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private Date createdAt;

    // Constructor rỗng
    public SuperAdmins() {
    }

    // Constructor đầy đủ
    public SuperAdmins(int superAdminID, String username, String passwordHash, String fullName, String email, Date createdAt) {
        this.superAdminID = superAdminID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getSuperAdminID() {
        return superAdminID;
    }

    public void setSuperAdminID(int superAdminID) {
        this.superAdminID = superAdminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
