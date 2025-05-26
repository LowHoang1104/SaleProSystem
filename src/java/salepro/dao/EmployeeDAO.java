/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.Employees;
import salepro.models.Roles;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

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

    public List<Employees> getAllInfoEmployee() {
        List<Employees> list = new ArrayList<>();
        String sql = "SELECT e.EmployeeID, e.FullName, e.Phone, e.StoreID, e.EmployeeTypeID, "
                + "u.UserID, u.Username, u.Email, u.Avatar, u.RoleID "
                + "FROM Employees e "
                + "JOIN Users u ON e.UserID = u.UserID";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                // Tạo user từ bảng Users
                Users user = new Users();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setAvatar(rs.getString("Avatar"));

                // Nếu bạn có Roles thì cần xử lý thêm
                Roles role = new Roles();
                role.setRoleID(rs.getInt("RoleID"));
                user.setRoles(role);

                // Tạo employee từ bảng Employees
                Employees emp = new Employees();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setFullName(rs.getString("FullName"));
                emp.setPhone(rs.getString("Phone"));
                emp.setStore(new StoreDAO().getStoreByID(rs.getInt("StoreID")));
                emp.setEmployeeType(new EmployeeTypeDAO().getEmployeeTypeById(rs.getInt("EmployeeTypeID")));
                emp.setUser(user);  // Gắn user vào employee

                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setAvatar(rs.getString("Avatar"));

                // Nếu có Role
                Roles role = new Roles();
                role.setRoleID(rs.getInt("RoleID"));
                user.setRoles(role);

                // Tạo đối tượng Employee
                emp = new Employees();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setFullName(rs.getString("FullName"));
                emp.setPhone(rs.getString("Phone"));
                emp.setStore(new StoreDAO().getStoreByID(rs.getInt("StoreID")));
                emp.setEmployeeType(new EmployeeTypeDAO().getEmployeeTypeById(rs.getInt("EmployeeTypeID")));
                emp.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emp;
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        System.out.println(dao.getEmployeeByUserId(2));
    }

}
