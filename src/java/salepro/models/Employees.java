/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.EmployeeDAO;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author MY PC
 */
public class Employees {
    private int employeeID;
    private String fullName;
    private String phone;
    private int storeID;
    private int employeeTypeID;
    private int userID;
    private boolean isActive;

    public Employees() {
    }

    public Employees(int employeeID, String fullName, String phone, int storeID, int employeeTypeID, int userID, boolean isActive) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phone = phone;
        this.storeID = storeID;
        this.employeeTypeID = employeeTypeID;
        this.userID = userID;
        this.isActive = isActive;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getStoreID() {
        return storeID;
    }
    
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    public int getEmployeeTypeID() {
        return employeeTypeID;
    }
    
    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public Users getUser(){
        return new UserDAO().getUserById(userID);
    }
    
    public String getAvatar(){
        Users user = this.getUser();
        if(user != null){
            return user.getAvatar();
        }
        return "";
    }

    public String getEmployeeTypeName(){
        return new EmployeeTypeDAO().getEmployeeTypeById(employeeID).getTypeName();
    }
    
    public String getCode(){
        return String.format("NV%06d", employeeID);
    }


}
