package salepro.dao.SuperAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext1;
import salepro.models.SuperAdmin.PaymentTransaction;
import java.sql.*;
import java.time.LocalDateTime;
import salepro.models.SuperAdmin.ServicePackage;
import salepro.models.SuperAdmin.ShopOwner;

public class PaymentTransactionDAO extends DBContext1 {

    PreparedStatement stm;
    ResultSet rs;

    public boolean createTransaction(PaymentTransaction transaction) {
        String sql = "INSERT INTO PaymentTransactions (TransactionCode, ShopOwnerID, PackageID, Amount, Status, CreatedAt, ExpiredAt, VNPayOrderInfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stm.setString(1, transaction.getTransactionCode());
            stm.setInt(2, transaction.getShopOwner().getShopOwnerId());
            stm.setInt(3, transaction.getServicePackage().getPackageId());
            stm.setDouble(4, transaction.getAmount());
            stm.setString(5, transaction.getStatus());
            stm.setTimestamp(6, Timestamp.valueOf(transaction.getCreatedAt()));
            stm.setTimestamp(7, Timestamp.valueOf(transaction.getExpiredAt()));
            stm.setString(8, transaction.getVnPayOrderInfo());
            int result = stm.executeUpdate();

            if (result > 0) {
                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    transaction.setTransactionId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
        }

        return false;
    }

    public PaymentTransaction findByTransactionCode(String transactionCode) {
        String sql = """
            SELECT pt.*, 
                   sp.PackageName, sp.DurationMonths, sp.Price as PackagePrice, sp.Description as PackageDescription,
                   so.ShopName, so.OwnerName, so.Email, so.Phone, so.SubscriptionStatus, 
                   so.SubscriptionStartDate, so.SubscriptionEndDate, so.LastPaymentDate
            FROM PaymentTransactions pt 
            LEFT JOIN ServicePackages sp ON pt.PackageID = sp.PackageID 
            LEFT JOIN ShopOwners so ON pt.ShopOwnerID = so.ShopOwnerID 
            WHERE pt.TransactionCode = ?
            """;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, transactionCode);
            rs = stm.executeQuery();
            
            if (rs.next()) {
                PaymentTransaction transaction = mapResultSetToTransaction(rs);
                
                // Map ServicePackage
                ServicePackage servicePackage = new ServicePackage();
                servicePackage.setPackageId(rs.getInt("PackageID"));
                servicePackage.setPackageName(rs.getString("PackageName"));
                servicePackage.setDurationMonths(rs.getInt("DurationMonths"));
                servicePackage.setPrice(rs.getDouble("PackagePrice"));
                servicePackage.setDescription(rs.getString("PackageDescription"));
                transaction.setServicePackage(servicePackage);
                
                // Map ShopOwner - FIX: Ensure ShopOwner is properly populated
                ShopOwner shopOwner = new ShopOwner();
                shopOwner.setShopOwnerId(rs.getInt("ShopOwnerID"));
                shopOwner.setShopName(rs.getString("ShopName"));
                shopOwner.setOwnerName(rs.getString("OwnerName"));
                shopOwner.setEmail(rs.getString("Email"));
                shopOwner.setPhone(rs.getString("Phone"));
                shopOwner.setSubscriptionStatus(rs.getString("SubscriptionStatus"));
                
                // Handle nullable timestamps properly
                Timestamp subscriptionStart = rs.getTimestamp("SubscriptionStartDate");
                if (subscriptionStart != null) {
                    shopOwner.setSubscriptionStartDate(subscriptionStart.toLocalDateTime());
                }
                
                Timestamp subscriptionEnd = rs.getTimestamp("SubscriptionEndDate");
                if (subscriptionEnd != null) {
                    shopOwner.setSubscriptionEndDate(subscriptionEnd.toLocalDateTime());
                }
                
                Timestamp lastPayment = rs.getTimestamp("LastPaymentDate");
                if (lastPayment != null) {
                    shopOwner.setLastPaymentDate(lastPayment.toLocalDateTime());
                }
                
                transaction.setShopOwner(shopOwner);
                
                System.out.println("✅ Transaction found with ShopOwner: " + shopOwner.getShopOwnerId() + 
                                 " (" + shopOwner.getShopName() + ")");
                
                return transaction;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error in findByTransactionCode: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("❌ Transaction not found for code: " + transactionCode);
        return null;
    }

    public PaymentTransaction findById(Integer transactionId) {
        String sql = "SELECT * FROM PaymentTransactions WHERE TransactionID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, transactionId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return mapResultSetToTransaction(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean updateTransactionStatus(Integer transactionId, String status) {
        String sql = "UPDATE PaymentTransactions SET Status = ?, PaidAt = ? WHERE TransactionID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, status);
            if ("Completed".equals(status)) {
                stm.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            } else {
                stm.setTimestamp(2, null);
            }
            stm.setInt(3, transactionId);

            int rowsAffected = stm.executeUpdate();
            boolean success = rowsAffected > 0;
            
            if (success) {
                System.out.println("✅ Transaction " + transactionId + " status updated to: " + status);
            } else {
                System.out.println("❌ Failed to update transaction " + transactionId + " status");
            }
            
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error in updateTransactionStatus: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stm != null) stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateVnPayInfo(Integer transactionId, String vnPayTxnRef, String vnPayTransactionNo,
            String vnPayOrderInfo, String vnPayResponseCode, String vnPayTransactionStatus) {
        String sql = """
            UPDATE PaymentTransactions 
            SET VNPayTxnRef = ?, VNPayTransactionNo = ?, VNPayOrderInfo = ?, 
                VNPayResponseCode = ?, VNPayTransactionStatus = ?, VNPaySecureHash = ?
            WHERE TransactionID = ?
            """;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, vnPayTxnRef);
            stm.setString(2, vnPayTransactionNo);
            stm.setString(3, vnPayOrderInfo);
            stm.setString(4, vnPayResponseCode);
            stm.setString(5, vnPayTransactionStatus);
            stm.setString(6, ""); // SecureHash can be empty for now
            stm.setInt(7, transactionId);

            int rowsAffected = stm.executeUpdate();
            boolean success = rowsAffected > 0;
            
            if (success) {
                System.out.println("✅ VNPay info updated for transaction: " + transactionId);
            } else {
                System.out.println("❌ Failed to update VNPay info for transaction: " + transactionId);
            }
            
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error in updateVnPayInfo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stm != null) stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private PaymentTransaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionId(rs.getInt("TransactionID"));
        transaction.setTransactionCode(rs.getString("TransactionCode"));
        transaction.setAmount(rs.getDouble("Amount"));
        transaction.setStatus(rs.getString("Status"));

        // Convert Timestamps to LocalDateTime safely
        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            transaction.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp paidAt = rs.getTimestamp("PaidAt");
        if (paidAt != null) {
            transaction.setPaidAt(paidAt.toLocalDateTime());
        }

        Timestamp expiredAt = rs.getTimestamp("ExpiredAt");
        if (expiredAt != null) {
            transaction.setExpiredAt(expiredAt.toLocalDateTime());
        }

        // VNPay fields
        transaction.setVnPayTxnRef(rs.getString("VNPayTxnRef"));
        transaction.setVnPayTransactionNo(rs.getString("VNPayTransactionNo"));
        transaction.setVnPayOrderInfo(rs.getString("VNPayOrderInfo"));
        transaction.setVnPayResponseCode(rs.getString("VNPayResponseCode"));
        transaction.setVnPayTransactionStatus(rs.getString("VNPayTransactionStatus"));
        transaction.setVnPayPayDate(rs.getString("VNPayPayDate"));
        transaction.setVnPaySecureHash(rs.getString("VNPaySecureHash"));
        transaction.setNotes(rs.getString("Notes"));

        return transaction;
    }
}