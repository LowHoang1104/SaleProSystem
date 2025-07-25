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
   
    public boolean updateSubscription(ShopOwner shopOwner) {
        try {
            String sql = "UPDATE ShopOwners SET "
                    + "SubscriptionStartDate = ?, SubscriptionEndDate = ?, "
                    + "SubscriptionStatus = ?, LastPaymentDate = ?, IsActive = ? "
                    + "WHERE ShopOwnerID = ?";

            stm = connection.prepareStatement(sql);

            // Set SubscriptionStartDate
            if (shopOwner.getSubscriptionStartDate() != null) {
                stm.setTimestamp(1, Timestamp.valueOf(shopOwner.getSubscriptionStartDate()));
            } else {
                stm.setTimestamp(1, null);
            }

            // Set SubscriptionEndDate
            if (shopOwner.getSubscriptionEndDate() != null) {
                stm.setTimestamp(2, Timestamp.valueOf(shopOwner.getSubscriptionEndDate()));
            } else {
                stm.setTimestamp(2, null);
            }

            stm.setString(3, shopOwner.getSubscriptionStatus());

            // Set LastPaymentDate
            if (shopOwner.getLastPaymentDate() != null) {
                stm.setTimestamp(4, Timestamp.valueOf(shopOwner.getLastPaymentDate()));
            } else {
                stm.setTimestamp(4, null);
            }

            stm.setBoolean(5, shopOwner.getIsActive());
            stm.setInt(6, shopOwner.getShopOwnerId());

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extend subscription từ ngày hết hạn hiện tại Dùng cho gia hạn khi
     * subscription còn hiệu lực
     */
    public boolean extendSubscription(Integer shopOwnerId, Integer monthsToAdd) {
        try {
            String sql = "UPDATE ShopOwners SET "
                    + "SubscriptionEndDate = CASE "
                    + "   WHEN SubscriptionEndDate IS NULL OR SubscriptionEndDate < GETDATE() "
                    + "   THEN DATEADD(MONTH, ?, GETDATE()) "
                    + "   ELSE DATEADD(MONTH, ?, SubscriptionEndDate) "
                    + "END, "
                    + "SubscriptionStatus = 'Active', "
                    + "LastPaymentDate = GETDATE(), "
                    + "IsActive = 1 "
                    + "WHERE ShopOwnerID = ?";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, monthsToAdd);
            stm.setInt(2, monthsToAdd);
            stm.setInt(3, shopOwnerId);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy danh sách shop sắp hết hạn subscription (cho reminder)
     */
    public List<ShopOwner> findShopsExpiringInDays(int days) {
        List<ShopOwner> shops = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ShopOwners "
                    + "WHERE SubscriptionEndDate IS NOT NULL "
                    + "AND SubscriptionEndDate BETWEEN GETDATE() AND DATEADD(DAY, ?, GETDATE()) "
                    + "AND SubscriptionStatus = 'Active' "
                    + "AND IsActive = 1";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, days);
            rs = stm.executeQuery();

            while (rs.next()) {
                shops.add(mapResultSetToShopOwner(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shops;
    }

    /**
     * Đếm số shop theo trạng thái subscription (cho dashboard)
     */
    public int countShopsByStatus(String status) {
        try {
            String sql = "SELECT COUNT(*) FROM ShopOwners WHERE SubscriptionStatus = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy tổng doanh thu từ subscription (cho báo cáo)
     */
    public double getTotalRevenue() {
        try {
            String sql = "SELECT SUM(pt.Amount) "
                    + "FROM PaymentTransactions pt "
                    + "WHERE pt.Status = 'Completed'";

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Cập nhật trạng thái expired cho các shop hết hạn
     */
    public int updateExpiredSubscriptions() {
        try {
            String sql = "UPDATE ShopOwners SET "
                    + "SubscriptionStatus = 'Expired' "
                    + "WHERE SubscriptionEndDate < GETDATE() "
                    + "AND SubscriptionStatus IN ('Active', 'Trial')";

            stm = connection.prepareStatement(sql);
            return stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Kiểm tra xem shop có subscription hợp lệ không
     */
    public boolean hasValidSubscription(Integer shopOwnerId) {
        try {
            String sql = "SELECT COUNT(*) FROM ShopOwners "
                    + "WHERE ShopOwnerID = ? "
                    + "AND SubscriptionEndDate > GETDATE() "
                    + "AND SubscriptionStatus IN ('Active', 'Trial') "
                    + "AND IsActive = 1";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, shopOwnerId);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tìm shop theo email (cho quên mật khẩu, etc.)
     */
    public ShopOwner findByEmail(String email) {
        try {
            String sql = "SELECT * FROM ShopOwners WHERE Email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();

            if (rs.next()) {
                return mapResultSetToShopOwner(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    /**
     * Test method cho VNPay integration
     */
    public void testVNPayIntegration() {
        try {
            // Test 1: Tìm shop
            ShopOwner shop = findById(1);
            if (shop != null) {
                System.out.println("✓ Found shop: " + shop.getShopName());
                System.out.println("  Status: " + shop.getSubscriptionStatus());
                System.out.println("  End Date: " + shop.getSubscriptionEndDate());
                System.out.println("  Remaining Days: " + shop.getRemainingDays());
            }

            // Test 2: Check valid subscription
            boolean isValid = hasValidSubscription(1);
            System.out.println("✓ Has valid subscription: " + isValid);

            // Test 3: Count shops by status
            int activeCount = countShopsByStatus("Active");
            int trialCount = countShopsByStatus("Trial");
            int expiredCount = countShopsByStatus("Expired");

            System.out.println("✓ Shop counts:");
            System.out.println("  Active: " + activeCount);
            System.out.println("  Trial: " + trialCount);
            System.out.println("  Expired: " + expiredCount);

        } catch (Exception e) {
            System.err.println("✗ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ShopOwnerDAO dao = new ShopOwnerDAO();

        // Test VNPay integration methods
        dao.testVNPayIntegration();

        // Test extend subscription
        boolean extended = dao.extendSubscription(1, 3); // Gia hạn 3 tháng
        System.out.println("Extended subscription: " + extended);

        // Test find expiring shops
        List<ShopOwner> expiring = dao.findShopsExpiringInDays(7);
        System.out.println("Shops expiring in 7 days: " + expiring.size());
    }
}
