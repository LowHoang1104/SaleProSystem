/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext1;
import salepro.models.SuperAdmin.ShopOwner;
import salepro.service.ResetPassword;

/**
 *
 * @author ADMIN
 */
public class ShopOwnerDAO extends DBContext1 {

    PreparedStatement stm;
    ResultSet rs;

    public boolean checkShopOwner(String shopName, String account, String password) {
        try {
            String strSQL = "SELECT * FROM ShopOwners WHERE ShopName = ? AND (Phone = ? OR Email = ?) AND PasswordHash = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, shopName);
            stm.setString(2, account);
            stm.setString(3, account);
            stm.setString(4, password);
            rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistShopOwner(String shopName) {
        try {
            String strSQL = "SELECT * FROM ShopOwners WHERE ShopName = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, shopName);
            rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistPhone(String phoneNumber) {
        try {
            String strSQL = "SELECT * FROM ShopOwners WHERE Phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phoneNumber);
            rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistEmail(String email) {
        try {
            String strSQL = "SELECT * FROM ShopOwners WHERE Email = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createShopOwner(ShopOwner newshop) throws Exception {
        try {
            String strSQL = "INSERT INTO ShopOwners (ShopName, OwnerName, Email, Phone, PasswordHash, IsActive, CreatedAt, SubscriptionStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newshop.getShopName());
            stm.setString(2, newshop.getOwnerName());
            stm.setString(3, newshop.getEmail());
            stm.setString(4, newshop.getPhone());
            stm.setString(5, newshop.getPasswordHash());
            stm.setBoolean(6, newshop.getIsActive());
            stm.setTimestamp(7, Timestamp.valueOf(newshop.getCreatedAt()));
            stm.setString(8, newshop.getSubscriptionStatus());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void createShopOwnerByEmail(ShopOwner newshop) {
        try {
            String strSQL = "INSERT INTO ShopOwners (ShopName, OwnerName, Email, PasswordHash, IsActive, CreatedAt, SubscriptionStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newshop.getShopName());
            stm.setString(2, newshop.getOwnerName());
            stm.setString(3, newshop.getEmail());
            stm.setString(4, newshop.getPasswordHash());
            stm.setBoolean(5, newshop.getIsActive());
            stm.setTimestamp(6, Timestamp.valueOf(newshop.getCreatedAt()));
            stm.setString(7, newshop.getSubscriptionStatus());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActiveStatus(int shopownerid, String enddate) throws Exception {
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
            String strSQL = "SELECT * FROM ShopOwners WHERE ShopName = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, name);
            rs = stm.executeQuery();
            if (rs.next()) {
                return mapResultSetToShopOwner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ShopOwner getShopOwnerById(int id) {
        try {
            String strSQL = "SELECT * FROM ShopOwners WHERE ShopOwnerID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                return mapResultSetToShopOwner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ShopOwner> getData() {
        List<ShopOwner> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM ShopOwners";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToShopOwner(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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

    public ShopOwner findById(Integer shopOwnerId) {
        try {
            String sql = "SELECT * FROM ShopOwners WHERE ShopOwnerID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, shopOwnerId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return mapResultSetToShopOwner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSubscription(Integer shopOwnerId, LocalDateTime startDate, LocalDateTime endDate, String status) {
        try {
            String sql = "UPDATE ShopOwners SET SubscriptionStartDate = ?, SubscriptionEndDate = ?, SubscriptionStatus = ?, LastPaymentDate = ? WHERE ShopOwnerID = ?";
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, Timestamp.valueOf(startDate));
            stm.setTimestamp(2, Timestamp.valueOf(endDate));
            stm.setString(3, status);
            stm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stm.setInt(5, shopOwnerId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public List<ShopOwner> findExpiredTrialShops() {
        List<ShopOwner> shops = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ShopOwners WHERE SubscriptionStatus = 'Trial' AND SubscriptionEndDate < ?";
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            rs = stm.executeQuery();
            while (rs.next()) {
                shops.add(mapResultSetToShopOwner(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }

    private ShopOwner mapResultSetToShopOwner(ResultSet rs) throws SQLException {
        ShopOwner shop = new ShopOwner();
        shop.setShopOwnerId(rs.getInt("ShopOwnerID"));
        shop.setShopName(rs.getString("ShopName"));
        shop.setOwnerName(rs.getString("OwnerName"));
        shop.setEmail(rs.getString("Email"));
        shop.setPhone(rs.getString("Phone"));
        shop.setPasswordHash(rs.getString("PasswordHash"));
        shop.setIsActive(rs.getBoolean("IsActive"));
        shop.setSubscriptionStatus(rs.getString("SubscriptionStatus"));

        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            shop.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp subscriptionStartDate = rs.getTimestamp("SubscriptionStartDate");
        if (subscriptionStartDate != null) {
            shop.setSubscriptionStartDate(subscriptionStartDate.toLocalDateTime());
        }

        Timestamp subscriptionEndDate = rs.getTimestamp("SubscriptionEndDate");
        if (subscriptionEndDate != null) {
            shop.setSubscriptionEndDate(subscriptionEndDate.toLocalDateTime());
        }

        Timestamp lastPaymentDate = rs.getTimestamp("LastPaymentDate");
        if (lastPaymentDate != null) {
            shop.setLastPaymentDate(lastPaymentDate.toLocalDateTime());
        }

        return shop;
    }

    // SỬA LẠI HÀM getDataBysearch - CHÍNH SỬA INDEX PARAMETER VÀ CONSTRUCTOR
    public ArrayList<ShopOwner> getDataBysearch(String shop, String shopOwnerName, String status, String date) {
        String strSQL = "SELECT * FROM ShopOwners WHERE ShopName IS NOT NULL";
        
        // Đếm số parameter để set đúng index
        int paramIndex = 1;
        
        if (!shop.isEmpty()) {
            strSQL += " AND ShopName COLLATE Latin1_General_CI_AI LIKE ?";
        }
        if (!shopOwnerName.isEmpty()) {
            strSQL += " AND OwnerName COLLATE Latin1_General_CI_AI LIKE ?";
        }
        if (!status.isEmpty()) {
            strSQL += " AND SubscriptionStatus LIKE ?";
        }
        if (!date.isEmpty()) {
            strSQL += " AND CreatedAt >= ?";
        }
        
        ArrayList<ShopOwner> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(strSQL);
            
            // Set parameter theo đúng thứ tự
            paramIndex = 1;
            if (!shop.isEmpty()) {
                stm.setString(paramIndex++, "%" + shop + "%");
            }
            if (!shopOwnerName.isEmpty()) {
                stm.setString(paramIndex++, "%" + shopOwnerName + "%");
            }
            if (!status.isEmpty()) {
                stm.setString(paramIndex++, "%" + status + "%");
            }
            if (!date.isEmpty()) {
                stm.setString(paramIndex++, date);
            }
            
            rs = stm.executeQuery();
            while (rs.next()) {
                // SỬ DỤNG mapResultSetToShopOwner THAY VÌ CONSTRUCTOR PHỨC TạP
                data.add(mapResultSetToShopOwner(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void updateTrial(String name, String date) {
        try {
            String updateSQL = "UPDATE ShopOwners SET SubscriptionStatus='Trial', SubscriptionEndDate=? WHERE ShopName=?";
            stm = connection.prepareStatement(updateSQL);
            stm.setString(1, date);
            stm.setString(2, name);
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^0\\d{9}$"; // Bắt đầu bằng số 0, theo sau là 9 chữ số
        return phone != null && phone.matches(regex);
    }

    public static void main(String[] args) {
        ShopOwnerDAO da = new ShopOwnerDAO();
        ResetPassword re = new ResetPassword();
        re.sendEmailAdminShopOwner("Long110604@gmail.com", "abc", "long");
    }
}