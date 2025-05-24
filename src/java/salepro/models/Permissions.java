/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Permissions {
    private int permissionID;
    private String permissionName;

    public Permissions() {
    }

    public Permissions(int permissionID, String permissionName) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
    }
    
    public int getPermissionID() {
        return permissionID;
    }
    
    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }
    
    public String getPermissionName() {
        return permissionName;
    }
    
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
