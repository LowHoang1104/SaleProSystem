/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Employees {

    private int employeeID;
    private String fullName;
    private String phone;
    private Stores store;
    private EmployeeTypes employeeType;
    private Users user;
    private boolean isActive;

    public Employees() {
    }

    public Employees(int employeeID, String fullName, String phone, Stores store, EmployeeTypes employeeType, Users user, boolean isActive) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phone = phone;
        this.store = store;
        this.employeeType = employeeType;
        this.user = user;
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

    public Stores getStore() {
        return store;
    }

    public void setStore(Stores store) {
        this.store = store;
    }

    public EmployeeTypes getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeTypes employeeType) {
        this.employeeType = employeeType;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
