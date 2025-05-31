/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.models.Permissions;

/**
 *
 * @author Thinhnt
 */
public class PermissionDAO extends DBContext2 {

    private static final int ROLE_ID_EMPLOYEE = 2;
    private PreparedStatement stm;
    private ResultSet rs;
// Lấy tất cả các quyền (Permissions)

    public List<Permissions> getData() {
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

    public List<Permissions> getAllPermissionByCategoryId(int categoryId) {
        List<Permissions> list = new ArrayList<>();
        String sql = "SELECT [PermissionID]\n"
                + "      ,[PermissionName]\n"
                + "      ,p.[CategoryID] \n"
                + "	  ,c.CategoryName\n"
                + "  FROM [dbo].[Permissions] p \n"
                + "  join CategoryPermission c on p.CategoryID = c.CategoryID\n"
                + "  where p.CategoryID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Permissions p = new Permissions(rs.getInt("PermissionID"), rs.getString("PermissionName"), rs.getInt("CategoryID"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertPermissionForEmployeeType(int employeeTypeId, List<Integer> permissionIds) {
        String sql = "INSERT INTO RolePermissions (RoleID, EmployeeTypeID, PermissionID) VALUES (?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            for (Integer permissionId : permissionIds) {
                stm.setInt(1, ROLE_ID_EMPLOYEE);
                stm.setInt(2, employeeTypeId);
                stm.setInt(3, permissionId);
                stm.addBatch();
            }
            stm.executeBatch();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePermissionsForEmployeeType(int employeeTypeId, List<Integer> permissionIds) {
        String deleteSQL = "DELETE FROM RolePermissions WHERE EmployeeTypeID = ?";
        String insertSQL = "INSERT INTO RolePermissions (RoleID, EmployeeTypeID, PermissionID) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Xóa quyền cũ
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, employeeTypeId);
                deleteStmt.executeUpdate();
            }

            // Chèn quyền mới
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                for (Integer permissionId : permissionIds) {
                    insertStmt.setInt(1, ROLE_ID_EMPLOYEE);
                    insertStmt.setInt(2, employeeTypeId);
                    insertStmt.setInt(3, permissionId);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

            connection.commit(); // Commit nếu không lỗi
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback(); // Rollback nếu lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new PermissionDAO().updatePermissionsForEmployeeType(5, List.of(1, 2, 3, 4, 6, 7)));
    }
}
