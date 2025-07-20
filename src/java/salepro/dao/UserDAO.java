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
import salepro.dal.DBContext;
import salepro.dal.DBContext2;
import salepro.models.Users;
import salepro.dal.DBContext2;
import salepro.models.Customers;
import salepro.models.Employees;
import salepro.models.TokenForgetPassword;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
public class UserDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select*from Users";
    private static final String GET_USER_BY_ID = "select*from Users WHERE UserID = ?";
    private static final String GET_FULLNAME_BY_USERID = "SELECT FullName FROM Employees WHERE UserID = ?";
    private static final String GET_FULLNAME_BY_EMAIL = "select * from Users where Email=?";

    public List<Users> getData() {
        List<Users> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToUser(rs));
            }
        } catch (Exception e) {

        }
        return data;
    }

    private Users mapResultSetToUser(ResultSet rs) throws Exception {
        int id = rs.getInt(1);
        String code = rs.getString(2);
        String username = rs.getString(3);
        String password = rs.getString(4);
        int roleId = rs.getInt(5);
        String avt = rs.getString(6);
        String email = rs.getString(7);
        boolean isActive = rs.getBoolean(8);
        Date createDate = rs.getDate(9);

        return new Users(id, code, username, password, roleId, avt, email, isActive, createDate, username);
    }

    public boolean checkUser(String account, String password) {
        try {
            String strSQL = "select * from Users where Username=? and PasswordHash=?";
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

    public boolean checkAdmin(String account, String password) {
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
                return mapResultSetToUser(rs);
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
                return mapResultSetToUser(rs);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String getFullNameByUserId(int userId) {
        String fullName = null;
        if (userId == 1) {
            fullName = "admin";
        } else {
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

    public int insertUser(Users user) {
        String sql = "INSERT INTO Users (Username, PasswordHash, RoleID, IsActive, CreatedAt, Email, Avatar) "
                + "VALUES (?, ?, ?, ?, GETDATE(), ?, ?)";
        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPasswordHash());
            stm.setInt(3, user.getRoleId());
            stm.setBoolean(4, user.isIsActive());
            stm.setString(5, user.getEmail());
            stm.setString(6, user.getAvatar());
            if (stm.executeUpdate() != 0) {
                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  // trả về userId vừa tạo
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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

    public boolean blockUser(int id, boolean isBlock) {
        String sql = "UPDATE Users SET IsActive = ? WHERE UserID = ?";
        try {
            stm = connection.prepareStatement(sql);
            if (isBlock) {
                stm.setInt(1, 0);
            } else {
                stm.setInt(1, 1);
            }
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUpdateUser(boolean checkUserName, boolean checkEmail, boolean checkPass, Users user) {
        String sql = "UPDATE Users SET RoleID = ?, Avatar = ?, IsActive = ?";
        if (!checkUserName) {
            sql += " ,UserName = ?";
        }
        if (!checkEmail) {
            sql += " ,Email = ?";
        }
        if (!checkPass) {
            sql += " ,PasswordHash = ?";
        }
        sql += " WHERE UserID = ?";
        try {
            int i = 3;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getRoleId());
            ps.setString(2, user.getAvatar());
            ps.setBoolean(3, user.isIsActive());
            if (!checkUserName) {
                ps.setString(++i, user.getUsername());
            }
            if (!checkEmail) {
                ps.setString(++i, user.getEmail());
            }
            if (!checkPass) {
                ps.setString(++i, user.getPasswordHash());
            }
            ps.setInt(++i, user.getUserId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
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
            if (rs.next()) {
                return rs.getString("RoleName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Users> searchUserByKeyword(String keyword) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Username LIKE ? OR Email LIKE ?";

        try {
            stm = connection.prepareStatement(sql);
            String key = "%" + (keyword != null ? keyword.trim() : "") + "%";
            stm.setString(1, key);
            stm.setString(2, key);

            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Users getUserByEmail(String Email) {
        try {
            stm = connection.prepareStatement(GET_FULLNAME_BY_EMAIL);
            stm.setString(1, Email);
            rs = stm.executeQuery();
            while (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Users getUserbyOldToken(String token) {
        try {
            stm = connection.prepareStatement("select top 1 a.* from Users a join TokenForgetPassword b on a.UserID=b.userId where b.token=?");
            stm.setString(1, token);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int roleId = rs.getInt(4);
                String avt = rs.getString(5);
                String email = rs.getString(6);
                boolean isActive = rs.getBoolean(7);
                Date createDate = rs.getDate(8);
                return mapResultSetToUser(rs);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void updatePasswordByToken(String token, String newPass) {
        try {
            String sql = "Update Users set PasswordHash=? from TokenForgetPassword a join Users b on a.userId=b.UserID where a.token=? and a.isUsed=0";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPass);
            stm.setString(2, token);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String email, int userID) {
        try {
            String sql = "update Users set Email=? where UserID=?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, userID);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePasswordbyId(String newPassword, int userId) {
        try {
            String sql = "update Users set PasswordHash=? where UserID=?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAvt(String avt, int userID) {
        try {
            String sql = "update Users set Avatar=? where UserID=?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, avt);
            stm.setInt(2, userID);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUserIdByCode(String userCode) {
        String sql = "SELECT UserID FROM Users WHERE UserCode = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, userCode);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (Exception e) {
            System.err.println("Error getting user ID by code: " + e.getMessage());
        }

        return 1;
    }

}
