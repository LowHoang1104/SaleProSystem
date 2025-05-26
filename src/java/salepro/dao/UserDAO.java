/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.Statement;
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
public class UserDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public boolean checkUser(String account, String password) {
        try {
            String strSQL = "select * from Users where Username=? and PasswordHash=? and RoleID=2";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, account);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean checkUserName(String account) {
        try {
            String strSQL = "select * from Users where Username=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, account);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean checkEmail(String email) {
        try {
            String strSQL = "select * from Users where Email=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean insertUser(Users user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, RoleID, IsActive, CreatedAt, Email, Avatar) "
                + "VALUES (?, ?, ?, ?, GETDATE(), ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPasswordHash());
            stm.setInt(3, user.getRoles().getRoleID());
            stm.setBoolean(4, user.getIsActive());
            stm.setString(5, user.getEmail());
            stm.setString(6, user.getAvatar());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE Users SET Username=?, PasswordHash=?, RoleID=?, IsActive=?, Email=?, Avatar=? WHERE UserID=?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPasswordHash());
            stm.setInt(3, user.getRoles().getRoleID());
            stm.setBoolean(4, user.getIsActive());
            stm.setString(5, user.getEmail());
            stm.setString(6, user.getAvatar());
            stm.setInt(7, user.getUserID());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int userID) {
        String sql = "DELETE FROM Users WHERE UserID=?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userID);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Users> getAllUsers() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users u JOIN Roles r ON u.RoleID = r.RoleID";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRoles(new Roles(rs.getInt("RoleID"), rs.getString("RoleName")));
                u.setIsActive(rs.getBoolean("IsActive"));
                u.setCreatedAt(rs.getDate("CreatedAt"));
                u.setEmail(rs.getString("Email"));
                u.setAvatar(rs.getString("Avatar"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Users getUserById(int userID) {
        String sql = "SELECT * FROM Users u JOIN Roles r ON u.RoleID = r.RoleID WHERE u.UserID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userID);
            rs = stm.executeQuery();
            if (rs.next()) {
                Users u = new Users();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRoles(new Roles(rs.getInt("RoleID"), rs.getString("RoleName")));
                u.setIsActive(rs.getBoolean("IsActive"));
                u.setCreatedAt(rs.getDate("CreatedAt"));
                u.setEmail(rs.getString("Email"));
                u.setAvatar(rs.getString("Avatar"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // nếu không tìm thấy user hoặc lỗi thì trả về null
    }

    public boolean blockUser(int id) {
        String sql = "UPDATE Users SET IsActive = 0 WHERE UserID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUserAndEmployee(Users user, Employees emp) {
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Thêm user
            String sqlInsertUser = "INSERT INTO Users (Username, PasswordHash, RoleID, Avatar, Email) "
                    + "VALUES (?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sqlInsertUser, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPasswordHash());
            stm.setInt(3, user.getRoles().getRoleID());
            stm.setString(4, user.getAvatar());
            stm.setString(5, user.getEmail());

            int rows = stm.executeUpdate();
            if (rows == 0) {
                connection.rollback();
                return false;
            }

            // Lấy userID vừa insert
            rs = stm.getGeneratedKeys();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                connection.rollback();
                return false;
            }

            // Thêm employee
            String sqlInsertEmp = "INSERT INTO Employees (FullName, Phone, StoreID, EmployeeTypeID, UserID) "
                    + "VALUES (?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sqlInsertEmp);
            stm.setString(1, emp.getFullName());
            stm.setString(2, emp.getPhone());
            stm.setInt(3, emp.getStore().getStoreID());
            stm.setInt(4, emp.getEmployeeType().getEmployeeTypeID());
            stm.setInt(5, userId);

            rows = stm.executeUpdate();
            if (rows == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserAndEmployee(Users user, Employees emp) {
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Update bảng Users
            String sqlUpdateUser = "UPDATE Users SET Username = ?, Email = ?, Avatar = ?, RoleID = ? WHERE UserID = ?";
            stm = connection.prepareStatement(sqlUpdateUser);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getEmail());
            stm.setString(3, user.getAvatar());
            stm.setInt(4, user.getRoles().getRoleID());
            stm.setInt(5, user.getUserID());

            int rows = stm.executeUpdate();
            if (rows == 0) {
                connection.rollback();
                return false;
            }

            // Update bảng Employees
            String sqlUpdateEmp = "UPDATE Employees SET FullName = ?, Phone = ?, StoreID = ?, EmployeeTypeID = ? WHERE EmployeeID = ?";
            stm = connection.prepareStatement(sqlUpdateEmp);
            stm.setString(1, emp.getFullName());
            stm.setString(2, emp.getPhone());
            stm.setInt(3, emp.getStore().getStoreID());
            stm.setInt(4, emp.getEmployeeType().getEmployeeTypeID());
            stm.setInt(5, emp.getEmployeeID());

            rows = stm.executeUpdate();
            if (rows == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }



}
