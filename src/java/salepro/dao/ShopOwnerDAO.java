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

    public void createShopOwner(ShopOwner newShop) {
        try {
            String strSQL = "INSERT INTO ShopOwners (ShopName, OwnerName, Email, Phone, PasswordHash, IsActive, CreatedAt, SubscriptionStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newShop.getShopName());
            stm.setString(2, newShop.getOwnerName());
            stm.setString(3, newShop.getEmail());
            stm.setString(4, newShop.getPhone());
            stm.setString(5, newShop.getPasswordHash());
            stm.setBoolean(6, newShop.getIsActive());
            stm.setTimestamp(7, Timestamp.valueOf(newShop.getCreatedAt()));
            stm.setString(8, newShop.getSubscriptionStatus());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createShopOwnerByEmail(ShopOwner newShop) {
        try {
            String strSQL = "INSERT INTO ShopOwners (ShopName, OwnerName, Email, PasswordHash, IsActive, CreatedAt, SubscriptionStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newShop.getShopName());
            stm.setString(2, newShop.getOwnerName());
            stm.setString(3, newShop.getEmail());
            stm.setString(4, newShop.getPasswordHash());
            stm.setBoolean(5, newShop.getIsActive());
            stm.setTimestamp(6, Timestamp.valueOf(newShop.getCreatedAt()));
            stm.setString(7, newShop.getSubscriptionStatus());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public boolean updateSubscriptionStatus(int shopOwnerId, String subscriptionStatus) {
        String sql = "UPDATE ShopOwners SET subscriptionStatus = ? WHERE ShopOwnerID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, subscriptionStatus);
            stm.setInt(2, shopOwnerId);

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
