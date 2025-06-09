/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Date;
import salepro.dal.DBContext1;
import salepro.models.up.ShopOwners;

/**
 *
 * @author ADMIN
 */
public class ShopOwnerDAO extends DBContext1 {

    PreparedStatement stm;
    ResultSet rs;

    public boolean checkShopOwner(String nameShop, String account, String password) {
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

    public boolean checkExistShopOwner(String nameShop) {
        try {
            String strSQL = "select * from ShopOwners where ShopName=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, nameShop);;
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean checkExistPhone(String phoneNumber) {
        try {
            String strSQL = "select * from ShopOwners where Phone=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phoneNumber);;
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean checkExistEmail(String Email) {
        try {
            String strSQL = "select * from ShopOwners where Email=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, Email);;
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public void createShopOwner(ShopOwners newshop) {
        try {
            String strSQL = "EXEC RegisterShopOwner @ShopName = ?, @OwnerName = ?, @Email = ?, @Phone = ?, @PasswordHash = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newshop.getShopName());
            stm.setString(2, newshop.getOwnerName());
            stm.setString(3, newshop.getEmail());
            stm.setString(4, newshop.getPhone());
            stm.setString(5, newshop.getPasswordHash());
            stm.execute();
        } catch (Exception e) {

        }
    }

    public void createShopOwnerByEmail(ShopOwners newshop) {
        try {
            String strSQL = "EXEC RegisterShopOwnerByEmail @ShopName = ?, @OwnerName = ?, @Email = ?, @PasswordHash = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newshop.getShopName());
            stm.setString(2, newshop.getOwnerName());
            stm.setString(3, newshop.getEmail());
            stm.setString(4, newshop.getPasswordHash());
            stm.execute();
        } catch (Exception e) {

        }
    }

    public ShopOwners getShopOwnerByName(String name) {
        try {
            String strSQL = "select * from ShopOwners where ShopName=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);

            rs = stm.executeQuery();
            while (rs.next()) {
                return new ShopOwners(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getDate(8));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static void main(String[] args) {
        ShopOwnerDAO da = new ShopOwnerDAO();
        System.out.println(da.getShopOwnerByName("Shop").getOwnerName());
    }
}
