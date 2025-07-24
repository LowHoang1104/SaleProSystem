/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext1;
import salepro.models.SuperAdmin.ShopOwner;
import salepro.service.ResetPassword;
import java.sql.*;

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

    public void createShopOwner(ShopOwner newshop) throws Exception {
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
            throw e;
        }
    }

    public void createShopOwnerByEmail(ShopOwner newshop) {
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

    public void ActiveStatus(int shopownerid,String enddate) throws Exception {
        try {
            String strSQL = "UPDATE ShopOwners \n"
                    + "SET \n"
                    + "    SubscriptionStatus = 'Active',\n"
                    + "    SubscriptionStartDate = GETDATE(),\n"
                    + "    SubscriptionEndDate = ?\n"
                    + "WHERE ShopOwnerID = ?;";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, enddate);
            stm.setInt(2, shopownerid);
            stm.execute();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void SuspendedStatus(int shopownerid) throws Exception {
        try {
            String strSQL = "UPDATE ShopOwners \n"
                    + "SET \n"
                    + "    SubscriptionStatus = 'Suspended' \n"
                    + "WHERE ShopOwnerID = ?;";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, shopownerid);
            stm.execute();
        } catch (Exception e) {
            throw e;
        }
    }

    public ShopOwner getShopOwnerByName(String name) {
        try {
            String strSQL = "select * from ShopOwners where ShopName=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);
            System.out.println("vao day r");
            rs = stm.executeQuery();
            while (rs.next()) {
                return new ShopOwner(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7) == 1,
                        rs.getTimestamp(8) != null ? rs.getTimestamp(8).toLocalDateTime() : null,
                        rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null,
                        rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null,
                        rs.getString(11),
                        rs.getTimestamp(12) != null ? rs.getTimestamp(12).toLocalDateTime() : null
                );
            }
        } catch (Exception e) {

        }
        return null;
    }

    public ShopOwner getShopOwnerById(int id) {
        try {
            String strSQL = "select * from ShopOwners where ShopOwnerID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);

            rs = stm.executeQuery();
            while (rs.next()) {
                return new ShopOwner(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7) == 1,
                        rs.getTimestamp(8) != null ? rs.getTimestamp(8).toLocalDateTime() : null,
                        rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null,
                        rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null,
                        rs.getString(11),
                        rs.getTimestamp(12) != null ? rs.getTimestamp(12).toLocalDateTime() : null
                );
            }
        } catch (Exception e) {

        }
        return null;
    }

    public ArrayList<ShopOwner> getData() {
        ArrayList<ShopOwner> data = new ArrayList<>();
        try {
            String strSQL = "select * from ShopOwners";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                ShopOwner shopOwner = new ShopOwner(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7) == 1,
                        rs.getTimestamp(8) != null ? rs.getTimestamp(8).toLocalDateTime() : null,
                        rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null,
                        rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null,
                        rs.getString(11),
                        rs.getTimestamp(12) != null ? rs.getTimestamp(12).toLocalDateTime() : null
                );
                data.add(shopOwner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ShopOwner> getDataBysearch(String shop, String ShopOwner, String status, String date) {
        String strSQL = "select * from ShopOwners a where a.ShopName  is not null";
        if (!shop.isEmpty()) {
            strSQL += " and a.ShopName COLLATE Latin1_General_CI_AI LIKE ?";
        }
        if (!ShopOwner.isEmpty()) {
            strSQL += " and a.OwnerName COLLATE Latin1_General_CI_AI LIKE ?";
        }
        if (!status.isEmpty()) {
            strSQL += " and a.SubscriptionStatus like ?";
        }
        if (!date.isEmpty()) {
            strSQL += " and CreatedAt>=?";
        }
        ArrayList<ShopOwner> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(strSQL);
            if (!shop.isEmpty()) {
                stm.setString(1, "%" + shop + "%");
            }
            if (!ShopOwner.isEmpty()) {
                stm.setString(2, "%" + ShopOwner + "%");
            }
            if (!status.isEmpty()) {
                stm.setString(1, status);
            }
            if (!date.isEmpty()) {
                stm.setString(1, date);
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                ShopOwner shopOwner = new ShopOwner(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7) == 1,
                        rs.getTimestamp(8) != null ? rs.getTimestamp(8).toLocalDateTime() : null,
                        rs.getTimestamp(9) != null ? rs.getTimestamp(9).toLocalDateTime() : null,
                        rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null,
                        rs.getString(11),
                        rs.getTimestamp(12) != null ? rs.getTimestamp(12).toLocalDateTime() : null
                );
                data.add(shopOwner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

//    public void updateShopowner(ShopOwner a, String oldname) {
//        try {
//            String strSQL = "update ShopOwner set ShopName=?,OwnerName=?,Email=?,Phone=?,PasswordHash=?,IsActive=?;";
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
    public void updateTrial(String name, String date) {

        try {
            // 1. Cập nhật thông tin shop owner
            String updateSQL = "UPDATE ShopOwners SET SubscriptionStatus='Trial', SubscriptionEndDate=? WHERE ShopName=?";

            PreparedStatement pst = connection.prepareStatement(updateSQL);
            pst.setString(1, date);
            pst.setString(2, name);
            pst.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^0\\d{9}$"; // Bắt đầu bằng số 0, theo sau là 9 chữ số
        return phone != null && phone.matches(regex);
    }

    public void updateShopOwner(ShopOwner shopOwner, String oldName) {
        try {
            // Cập nhật thông tin shop owner
            String updateSQL = "UPDATE ShopOwners SET ShopName = ?, OwnerName = ?, Email = ?, Phone = ?, PasswordHash = ?, IsActive = ?, SubscriptionStatus = ?, SubscriptionStartDate = ?, SubscriptionEndDate = ?, LastPaymentDate = ? WHERE ShopName = ?";
            stm = connection.prepareStatement(updateSQL);
            stm.setString(1, shopOwner.getShopName());
            stm.setString(2, shopOwner.getOwnerName());
            stm.setString(3, shopOwner.getEmail());
            stm.setString(4, shopOwner.getPhone());
            stm.setString(5, shopOwner.getPasswordHash());
            stm.setBoolean(6, shopOwner.getIsActive());
            stm.setString(7, shopOwner.getSubscriptionStatus());
            stm.setTimestamp(8, shopOwner.getSubscriptionStartDate() != null ? Timestamp.valueOf(shopOwner.getSubscriptionStartDate()) : null);
            stm.setTimestamp(9, shopOwner.getSubscriptionEndDate() != null ? Timestamp.valueOf(shopOwner.getSubscriptionEndDate()) : null);
            stm.setTimestamp(10, shopOwner.getLastPaymentDate() != null ? Timestamp.valueOf(shopOwner.getLastPaymentDate()) : null);
            stm.setString(11, oldName);
            stm.executeUpdate();

            // Đổi tên database nếu tên mới khác tên cũ
            if (!oldName.equalsIgnoreCase(shopOwner.getShopName())) {
                String alterSQL = "ALTER DATABASE [" + oldName + "] MODIFY NAME = [" + shopOwner.getShopName() + "]";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(alterSQL);
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean updateSubscriptionStatus(Integer shopOwnerId, String status) {
        try {
            String sql = "UPDATE ShopOwners SET SubscriptionStatus = ? WHERE ShopOwnerID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            stm.setInt(2, shopOwnerId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        ShopOwnerDAO da = new ShopOwnerDAO();
        ResetPassword re = new ResetPassword();
        re.sendEmailAdminShopOwner("Long110604@gmail.com", "abc", "long");
    }
}
