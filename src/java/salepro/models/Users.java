/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.EmployeeDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author MY PC
 */
public class Users {

    private int userId;
    private String username;
    private String passwordHash;
    private int roleId;
    private String avatar;
    private String email;
    private boolean isActive;
    private Date createdAt;

    public Users(int userId, String username, String passwordHash, int roleId, String avatar, String email, boolean isActive, Date createdAt) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.avatar = avatar;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Users() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getFullName(){
        return new UserDAO().getFullNameByUserId(getUserId());
    }
    
    public Employees getEmployeeByUserId(){
        EmployeeDAO da= new EmployeeDAO();
        return da.getEmployeeByUserId(userId);
    }
    
    public String getRoleName(){
        return new UserDAO().getRoleNameByUserId(userId);
    }
}
