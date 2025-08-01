/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.EmployeeDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author MY PC
 */
public class Users {

    private int userId;
    private String userCode;
    private String username;
    private String passwordHash;
    private int roleId;
    private String avatar;
    private String email;
    private boolean isActive;
    private Date createdAt;
    private String fullName;

    public Users(int userId, String userCode, String username, String passwordHash, int roleId, String avatar, String email, boolean isActive, Date createdAt, String fullName) {

        this.userId = userId;
        this.userCode = userCode;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.avatar = avatar;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.fullName = fullName;
        
        this.fullName = new UserDAO().getFullNameByUserId(userId);
    }

    public Users(int userId, String userCode, String username, String passwordHash, int roleId, String avatar, String email, boolean isActive, Date createdAt) {
        this.userId = userId;
        this.userCode = userCode;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getFullName(){
        return new UserDAO().getFullNameByUserId(getUserId());
    }

    public Employees getEmployeeByUserId() {
        EmployeeDAO da = new EmployeeDAO();
        return da.getEmployeeByUserId(userId);
    }

    public String getRoleName() {
        return new UserDAO().getRoleNameByUserId(userId);
    }

    public int getEmpTypeId() {
        return new EmployeeDAO().getEmployeeByUserId(userId).getEmployeeTypeID();
    }

    public String getPhoneEmployee() {
        EmployeeDAO da = new EmployeeDAO();
        return da.getEmployeeByUserId(userId).getPhone();
    }

    public Stores getStoreByUserId() {
        StoreDAO da = new StoreDAO();
        return da.getStoreByUserId(userId);
    }

    public String getEmpTypeName() {
        Employees emp = getEmployeeByUserId();
        if (emp != null) {
            return emp.getEmployeeTypeName();
        }
        return "";
    }

    public static void main(String[] args) {
        Users user = new Users();
        user.setUserId(1);
        System.out.println(user.getEmpTypeName());
    }

}
