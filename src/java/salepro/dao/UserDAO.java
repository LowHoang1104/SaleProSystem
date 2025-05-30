/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Users;
import salepro.dal.DBContext2;
import salepro.models.Employees;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
public class UserDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_DATA = "select*from Users";
    private static final String GET_USER_BY_ID = "select*from Users";
    private static final String GET_FULLNAME_BY_USERID = "SELECT FullName FROM Employees WHERE UserID = ?";

    public List<Users> getData() {
        List<Users> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int roleId = rs.getInt(4);
                String avt = rs.getString(5);
                String email = rs.getString(6);

                boolean isActive = rs.getBoolean(4);
                Date createDate = rs.getDate(5);
                Users user = new Users(roleId, username, password, roleId, avt, email, isActive, createDate);
                data.add(user);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public boolean checkCashier(String account, String password) {
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

    public boolean checkManager(String account, String password) {
        try {
            String strSQL = "select * from Users where Username=? and PasswordHash=? and RoleID=1";
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

    public Users getUserById(int id) {
        try {
            stm = connection.prepareStatement(GET_USER_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String username = rs.getString(2);
                String password = rs.getString(3);
                int roleId = rs.getInt(4);
                String avt = rs.getString(5);
                String email = rs.getString(6);
                boolean isActive = rs.getBoolean(4);
                Date createDate = rs.getDate(5);
                return new Users(id, username, password, roleId, avt, email, isActive, createDate);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Users getUserbyAccountAndPass(String account, String password) {
        try {
            String strSQL = "select * from Users where Username=? and PasswordHash=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, account);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                return new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), (rs.getInt(7) == 1) ? true : false, rs.getDate(8));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getFullNameByUserId(int userId) {
        String fullName = null;
        try {
            stm = connection.prepareStatement(GET_FULLNAME_BY_USERID);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                fullName = rs.getString("FullName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fullName;
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
            stm.setInt(3, user.getRoleId());
            stm.setBoolean(4, user.isIsActive());
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
            stm.setInt(3, user.getRoleId());
            stm.setBoolean(4, user.isIsActive());
            stm.setString(5, user.getEmail());
            stm.setString(6, user.getAvatar());
            stm.setInt(7, user.getUserId());
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

    public List<Users> filterUsers(String userName, String email, String isActive) {
        List<Users> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT u.*, r.RoleName FROM Users u JOIN Roles r ON u.RoleID = r.RoleID";

        // Xây dựng điều kiện động
        if (userName != null && !userName.trim().isEmpty()) {
            conditions.add("u.Username LIKE ?");
            params.add("%" + userName.trim() + "%");
        }
        if (email != null && !email.trim().isEmpty()) {
            conditions.add("u.Email LIKE ?");
            params.add("%" + email.trim() + "%");
        }
        if (isActive != null && !isActive.trim().isEmpty()) {
            conditions.add("u.IsActive = ?");
            params.add(Boolean.parseBoolean(isActive.trim()));
        }

        // Gắn WHERE nếu có điều kiện
        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else if (param instanceof Boolean) {
                    stm.setBoolean(i + 1, (Boolean) param);
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setRoleId(rs.getInt("RoleID"));
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
            stm.setInt(3, user.getRoleId());
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
            stm.setInt(3, emp.getStoreID());
            stm.setInt(4, emp.getEmployeeTypeID());
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
            stm.setInt(4, user.getRoleId());
            stm.setInt(5, user.getUserId());

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
            stm.setInt(3, emp.getStoreID());
            stm.setInt(4, emp.getEmployeeTypeID());
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
    public String getRoleNameByUserId(int userId) {
        String sql = "select r.RoleName from Users u \n"
                + "join Roles r on u.RoleID = r.RoleID\n"
                + "where u.UserID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            if(rs.next()){
                return rs.getString("RoleName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(new UserDAO().getRoleNameByUserId(2));
    }

}
