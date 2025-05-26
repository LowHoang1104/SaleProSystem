/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.models.Permissions;

/**
 *
 * @author Thinhnt
 */
public class PermissionDAO extends DBContext {

    private PreparedStatement stm;
    private ResultSet rs;
// Lấy tất cả các quyền (Permissions)

    public List<Permissions> getAllPermissions() {
        List<Permissions> list = new ArrayList<>();
        String sql = "SELECT * FROM Permissions";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Permissions permission = new Permissions();
                permission.setPermissionID(rs.getInt("PermissionID"));
                permission.setPermissionName(rs.getString("PermissionName"));
                list.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách quyền theo EmployeeTypeID
    public List<Permissions> getPermissionsByEmployeeType(int employeeTypeID) {
        List<Permissions> list = new ArrayList<>();
        String sql = "SELECT p.PermissionID, p.PermissionName "
                + "FROM Permissions p "
                + "JOIN RolePermissions rp ON p.PermissionID = rp.PermissionID "
                + "WHERE rp.EmployeeTypeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeTypeID); // Gán giá trị cho tham số
            rs = stm.executeQuery();
            while (rs.next()) {
                Permissions permission = new Permissions();
                permission.setPermissionID(rs.getInt("PermissionID"));
                permission.setPermissionName(rs.getString("PermissionName"));
                list.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        PermissionDAO dao = new PermissionDAO();
        for (Permissions allPermission : dao.getPermissionsByEmployeeType(1)) {
            System.out.println(allPermission.getPermissionName());
        }
    }
}
