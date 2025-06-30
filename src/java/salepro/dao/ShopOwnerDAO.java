/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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

    public ShopOwners getShopOwnerById(int id) {
        try {
            String strSQL = "select * from ShopOwners where ShopOwnerID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);

            rs = stm.executeQuery();
            while (rs.next()) {
                return new ShopOwners(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getDate(8));
            }
        } catch (Exception e) {

        }
        return null;
    }

    public List<ShopOwners> getData() {
        ArrayList<ShopOwners> data = new ArrayList<>();
        try {
            String strSQL = "select * from ShopOwners";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ShopOwners(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getDate(8)));
            }
        } catch (Exception e) {

        }
        return data;
    }

//    public void updateShopowner(ShopOwners a, String oldname) {
//        try {
//            String strSQL = "update ShopOwners set ShopName=?,OwnerName=?,Email=?,Phone=?,PasswordHash=?,IsActive=?;";
//            stm = connection.prepareStatement(strSQL);
//            stm.setString(1, a.getShopName());
//            stm.setString(2, a.getOwnerName());
//            stm.setString(3, a.getEmail());
//            stm.setString(4, a.getPhone());
//            stm.setString(5, a.getPasswordHash());
//            stm.setInt(6, a.getIsActive());
//            stm.execute();
//            strSQL = "ALTER DATABASE [" + oldname + "] MODIFY NAME = [" + a.getShopName() + "];";
//            stm = connection.prepareStatement(strSQL);
//            stm.execute();
//                    
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public void updateShopowner(ShopOwners a, String oldname) {
    try {
        // 1. Cập nhật thông tin shop owner
        String updateSQL = "UPDATE ShopOwners SET ShopName=?, OwnerName=?, Email=?, Phone=?, PasswordHash=?, IsActive=? WHERE ShopName=?";
        PreparedStatement pst = connection.prepareStatement(updateSQL);
        pst.setString(1, a.getShopName());
        pst.setString(2, a.getOwnerName());
        pst.setString(3, a.getEmail());
        pst.setString(4, a.getPhone());
        pst.setString(5, a.getPasswordHash());
        pst.setInt(6, a.getIsActive());
        pst.setString(7, oldname); // điều kiện WHERE
        pst.executeUpdate();
        pst.close();

        // 2. Đổi tên database nếu tên mới khác tên cũ
        if (!oldname.equalsIgnoreCase(a.getShopName())) {
            String alterSQL = "ALTER DATABASE [" + oldname + "] MODIFY NAME = [" + a.getShopName() + "]";
            Statement stmt = (Statement) connection.createStatement();
            stmt.executeUpdate(alterSQL);
            stmt.close();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        ShopOwnerDAO da = new ShopOwnerDAO();
        ShopOwners a = da.getShopOwnerById(4);
        a.setOwnerName("Nguyen hoàng Long");
        a.setShopName("ABCCC");
        da.updateShopowner(a, "Shopc");
    }
}
