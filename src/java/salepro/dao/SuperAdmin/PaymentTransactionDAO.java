/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao.SuperAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext1;
import salepro.models.SuperAdmin.PaymentTransaction;
import java.sql.*; 
import java.time.LocalDateTime;
import salepro.models.SuperAdmin.ServicePackage;
/**
 *
 * @author MY PC
 */
public class PaymentTransactionDAO extends DBContext1{
     PreparedStatement stm;
    ResultSet rs;
    public boolean createTransaction(PaymentTransaction transaction) {
        String sql = "INSERT INTO PaymentTransactions (TransactionCode, ShopOwnerID, PackageID, Amount, Status, CreatedAt, ExpiredAt, VNPayOrderInfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            stm = connection.prepareStatement(sql);
            
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
        }
        
        return false;
    }
    
    public PaymentTransaction findByTransactionCode(String transactionCode) {
        String sql = "SELECT pt.*, sp.PackageName, sp.DurationMonths, sp.Price as PackagePrice, " +
                    "so.ShopName, so.OwnerName, so.Email " +
                    "FROM PaymentTransactions pt " +
                    "LEFT JOIN ServicePackages sp ON pt.PackageID = sp.PackageID " +
                    "LEFT JOIN ShopOwners so ON pt.ShopOwnerID = so.ShopOwnerID " +
                    "WHERE pt.TransactionCode = ?";
        
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, transactionCode);
            rs = stm.executeQuery();
            if (rs.next()) {
                return mapResultSetToTransactionWithDetails(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
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
            
            return stm.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateVnPayInfo(Integer transactionId, String vnPayTxnRef, String vnPayTransactionNo, 
                                  String vnPayOrderInfo, String vnPayResponseCode, String vnPayTransactionStatus) {
        String sql = "UPDATE PaymentTransactions SET VNPayTxnRef = ?, VNPayTransactionNo = ?, " +
                    "VNPayOrderInfo = ?, VNPayResponseCode = ?, VNPayTransactionStatus = ? WHERE TransactionID = ?";
        try  {
            stm = connection.prepareStatement(sql);
            stm.setString(1, vnPayTxnRef);
            stm.setString(2, vnPayTransactionNo);
            stm.setString(3, vnPayOrderInfo);
            stm.setString(4, vnPayResponseCode);
            stm.setString(5, vnPayTransactionStatus);
            stm.setInt(6, transactionId);
            
            return stm.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private PaymentTransaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionId(rs.getInt("TransactionID"));
        transaction.setTransactionCode(rs.getString("TransactionCode"));
        transaction.setAmount(rs.getDouble("Amount"));
        transaction.setStatus(rs.getString("Status"));
        
        // Convert Timestamps to LocalDateTime
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
    
    private PaymentTransaction mapResultSetToTransactionWithDetails(ResultSet rs) throws SQLException {
        PaymentTransaction transaction = mapResultSetToTransaction(rs);
        
        // Map ServicePackage info
        if (rs.getString("PackageName") != null) {
            ServicePackage pkg = new ServicePackage();
            pkg.setPackageId(rs.getInt("PackageID"));
            pkg.setPackageName(rs.getString("PackageName"));
            pkg.setDurationMonths(rs.getInt("DurationMonths"));
            pkg.setPrice(rs.getDouble("PackagePrice"));
            transaction.setServicePackage(pkg);
        }
        
        return transaction;
    }
}
