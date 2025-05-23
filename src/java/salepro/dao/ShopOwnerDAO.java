/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext1;

/**
 *
 * @author ADMIN
 */
public class ShopOwnerDAO extends DBContext1{
    PreparedStatement stm;
    ResultSet rs;
     public boolean checkShopOwner(String nameShop,String account, String password) {
        try {
            String strSQL = "select * from ShopOwners where ShopName=? and Phone=? and PasswordHash= ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, nameShop);
            stm.setString(2, account);
            stm.setString(3, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }
    
    
    
}
