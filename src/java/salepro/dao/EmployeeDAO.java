/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.models.Employees;
import salepro.models.Roles;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Employees> getData() {
        ArrayList<Employees> data = new ArrayList<>();
        try {
            String strSQL = "select * from Employees";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Employees temp = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7) == 1 ? true : false);
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public String getEmployeeNameByID(int id) {
        try {
            String strSQL = "select a.FullName from Employees a where a.EmployeeID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Employees getEmployeeByUserId(int userId) {
        Employees emp = null;
        String sql = "SELECT e.EmployeeID, e.FullName, e.Phone, e.StoreID, e.EmployeeTypeID, "
                + "u.UserID, u.Username, u.Email, u.Avatar, u.RoleID "
                + "FROM Employees e "
                + "JOIN Users u ON e.UserID = u.UserID "
                + "WHERE u.UserID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng User
                Users user = new Users();
                int userId1 = rs.getInt("UserID");
                user.setUserId(userId);
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setAvatar(rs.getString("Avatar"));

                // Nếu có Role
                user.setRoleId(rs.getInt("RoleID"));

                // Tạo đối tượng Employee
                emp = new Employees();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setFullName(rs.getString("FullName"));
                emp.setPhone(rs.getString("Phone"));
                emp.setStoreID(rs.getInt("StoreID"));
                emp.setEmployeeTypeID(rs.getInt("EmployeeTypeID"));
                emp.setUserID(userId1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }
}
