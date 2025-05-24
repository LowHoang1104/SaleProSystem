/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class RolePermission {
    private int roleID;
    private int employeeTypeID;
    private int permissionID;

    public RolePermission() {
    }

    public RolePermission(int roleID, int employeeTypeID, int permissionID) {
        this.roleID = roleID;
        this.employeeTypeID = employeeTypeID;
        this.permissionID = permissionID;
    }
    
    public int getRoleID() {
        return roleID;
    }
    
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    
    public int getEmployeeTypeID() {
        return employeeTypeID;
    }
    
    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }
    
    public int getPermissionID() {
        return permissionID;
    }
    
    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }
}
