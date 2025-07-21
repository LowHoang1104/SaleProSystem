/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.ArrayList;
import java.util.List;
import salepro.dao.PermissionDAO;

/**
 *
 * @author MY PC
 */
public class Permissions {

    private int permissionID;
    private String permissionName;
    private int categoryPerId;
    private String Url;

    public Permissions() {
    }

    public Permissions(int permissionID, String permissionName, int categoryPerId, String Url) {
        this.permissionID = permissionID;
        this.permissionName = permissionName;
        this.categoryPerId = categoryPerId;
        this.Url = Url;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
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

    public static void main(String[] args) {
        PermissionDAO p = new PermissionDAO();
        List<Permissions> perByEmpType = p.getPermissionsByEmployeeType(1);
        List<Integer> perByEmpTypeId = new ArrayList<>();
        for (Permissions permissions : perByEmpType) {
            System.out.println(permissions.getPermissionID());
            perByEmpTypeId.add(permissions.getPermissionID());
        }
        for (int i = 1; i < 10; i++) {
            System.out.println(perByEmpTypeId.contains(i));

        }
    }

}
