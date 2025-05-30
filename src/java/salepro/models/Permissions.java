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
    private int categoryPerId;

    public Permissions() {
    }

    public Permissions(int permissionID, String permissionName, int categoryPerId) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.categoryPerId = categoryPerId;
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

    public int getCategoryPerId() {
        return categoryPerId;
    }

    public void setCategoryPerId(int categoryPerId) {
        this.categoryPerId = categoryPerId;
    }

}
