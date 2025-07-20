/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import salepro.dal.DBContext1;
import salepro.models.up.SuperAdmin;

/**
 *
 * @author ADMIN
 */
public class SuperAdminDAO extends DBContext1{  
    PreparedStatement stm;
    ResultSet rs;
    
    public SuperAdmin getSuperAdminByUserNameAndPass(String username, String pass){
         try {
            String strSQL = "select * from SuperAdmins where Username =? and PasswordHash=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, username);
            stm.setString(2, pass);
            rs = stm.executeQuery();
            while (rs.next()) {
                SuperAdmin temp= new SuperAdmin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6) != null ? rs.getTimestamp(6).toLocalDateTime() : null);
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        SuperAdminDAO da= new SuperAdminDAO();
        System.out.println(da.getSuperAdminByUserNameAndPass("rootmaster", "9e107d9d372bb6826bd81d354a2a419d6").getFullName());
    }
}
