/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.Users;
import salepro.dal.DBContext2;

/**
 *
 * @author MY PC
 */
public class UserDAO extends DBContext {

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
        }catch( Exception e){
            
        }
        return data;
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
    
    public static void main(String[] args) {
        System.out.println(new UserDAO().getData().size());
    }
}
