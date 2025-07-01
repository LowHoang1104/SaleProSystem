/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Roles;

/**
 *
 * @author Thinhnt
 */
public class RoleDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    // Lấy tất cả các Role
    public List<Roles> getData() {
        List<Roles> list = new ArrayList<>();
        String sql = "SELECT * FROM Roles";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Roles role = new Roles();
                role.setRoleID(rs.getInt("RoleID"));
                role.setRoleName(rs.getString("RoleName"));
                list.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // Thêm mới Role
    public boolean insertRole(Roles role) {
        String sql = "INSERT INTO Roles (RoleName) VALUES (?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, role.getRoleName());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật Role
    public boolean updateRole(Roles role) {
        String sql = "UPDATE Roles SET RoleName = ? WHERE RoleID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, role.getRoleName());
            stm.setInt(2, role.getRoleID());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa Role
    public boolean deleteRole(int id) {
        String sql = "DELETE FROM Roles WHERE RoleID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        String path = "D:/SWP/SaleProSystem/web/uploadedFiles/Transaction.xlsx";
                File file = new File(path);
                System.out.println(file.delete());
    }
}
