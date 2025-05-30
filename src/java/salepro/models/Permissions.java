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
    private CategoryPermissions categoryPermission;

    public Permissions() {
    }

    public Permissions(int permissionID, String permissionName, CategoryPermissions categoryPermission) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.categoryPermission = categoryPermission;
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

    public CategoryPermissions getCategoryPermission() {
        return categoryPermission;
    }

    public void setCategoryPermission(CategoryPermissions categoryPermission) {
        this.categoryPermission = categoryPermission;
    }

}
