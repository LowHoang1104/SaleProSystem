/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext2;

/**
 *
 * @author ADMIN
 */
public class UserDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
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
    public static void main(String[] args) {
        UserDAO da= new UserDAO();
        
    }
 
    
}
