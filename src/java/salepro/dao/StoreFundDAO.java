/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.StoreFund;

/**
 *
 * @author MY PC
 */
public class StoreFundDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_ALL = "SELECT * FROM StoreFunds WHERE IsActive = 1 ORDER BY FundType, FundName";
    private static final String GET_BY_ID = "SELECT * FROM StoreFunds WHERE FundID = ?";
    private static final String GET_BY_STORE_ID = "SELECT * FROM StoreFunds WHERE StoreID = ? AND IsActive = 1 ORDER BY FundType, FundName";
    private static final String GET_BY_STORE_ID_CASH = "SELECT * FROM StoreFunds WHERE StoreID = ? AND FundType='Cash' and IsActive = 1 ORDER BY FundType, FundName ";
    private static final String GET_BY_STORE_AND_TYPE = "SELECT * FROM StoreFunds WHERE StoreID = ? AND FundType = ? AND IsActive = 1 ORDER BY FundID";
    private static final String UPDATE_BALANCE = "UPDATE StoreFunds SET CurrentBalance = ? WHERE FundID = ?";
    private static final String INSERT_FUND = "INSERT INTO StoreFunds (StoreID, FundName, FundType, BankName, AccountNumber, AccountHolder, CurrentBalance, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_FUND = "UPDATE StoreFunds SET FundName = ?, FundType = ?, BankName = ?, AccountNumber = ?, AccountHolder = ?, CurrentBalance = ? WHERE FundID = ?";
    private static final String DEACTIVATE_FUND = "UPDATE StoreFunds SET IsActive = 0 WHERE FundID = ?";
    private static final String ACTIVATE_FUND = "UPDATE StoreFunds SET IsActive = 1 WHERE FundID = ?";
    private static final String GET_FUND_BALANCE = "SELECT CurrentBalance FROM StoreFunds WHERE FundID = ?";
    
    private static final String GET_TOTAL_BALANCE_BY_STORE_TYPE = "SELECT SUM(CurrentBalance) as TotalBalance FROM StoreFunds WHERE StoreID = ? AND FundType = ? AND IsActive = 1";
    private static final String GET_FUND_BY_ID_DETAILED = "SELECT * FROM StoreFunds WHERE FundID = ? AND IsActive = 1";

    // lấy all
    public List<StoreFund> getData() {
        List<StoreFund> funds = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ALL);
            rs = stm.executeQuery();
            while (rs.next()) {
                funds.add(mapRowToStoreFund(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return funds;
    }

    // Theo id
    public StoreFund getFundById(int fundId) {
        StoreFund fund = null;
        try {
            stm = connection.prepareStatement(GET_BY_ID);
            stm.setInt(1, fundId);
            rs = stm.executeQuery();
            if (rs.next()) {
                fund = mapRowToStoreFund(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fund;
    }

    // Theo id cửa hàng
    public List<StoreFund> getFundsByStoreId(int storeId) {
        List<StoreFund> funds = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_BY_STORE_ID);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                funds.add(mapRowToStoreFund(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funds;
    }

    public List<StoreFund> getFundsByStoreIdCash(int storeId) {
        List<StoreFund> funds = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_BY_STORE_ID_CASH);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                funds.add(mapRowToStoreFund(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funds;
    }


    // Theo storeId và fundTyoe
    public List<StoreFund> getFundsByStoreAndType(int storeId, String fundType) {
        List<StoreFund> funds = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_BY_STORE_AND_TYPE);
            stm.setInt(1, storeId);
            stm.setString(2, fundType);
            rs = stm.executeQuery();
            while (rs.next()) {
                funds.add(mapRowToStoreFund(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funds;
    }

    // Update 
    public boolean updateFundBalance(int fundId, double newBalance) {
        try {
            stm = connection.prepareStatement(UPDATE_BALANCE);
            stm.setDouble(1, newBalance);
            stm.setInt(2, fundId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert
    public boolean insertFund(StoreFund fund) {
        try {
            stm = connection.prepareStatement(INSERT_FUND);
            stm.setInt(1, fund.getStoreID());
            stm.setString(2, fund.getFundName());
            stm.setString(3, fund.getFundType());
            stm.setString(4, fund.getBankName());
            stm.setString(5, fund.getAccountNumber());
            stm.setString(6, fund.getAccountHolder());
            stm.setDouble(7, fund.getCurrentBalance());
            stm.setBoolean(8, fund.isActive());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update 
    public boolean updateFund(StoreFund fund) {
        try {
            stm = connection.prepareStatement(UPDATE_FUND);
            stm.setString(1, fund.getFundName());
            stm.setString(2, fund.getFundType());
            stm.setString(3, fund.getBankName());
            stm.setString(4, fund.getAccountNumber());
            stm.setString(5, fund.getAccountHolder());
            stm.setDouble(6, fund.getCurrentBalance());
            stm.setInt(7, fund.getFundID());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //deactive
    public boolean deactivateFund(int fundId) {
        try {
            stm = connection.prepareStatement(DEACTIVATE_FUND);
            stm.setInt(1, fundId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //active
    public boolean activateFund(int fundId) {
        try {
            stm = connection.prepareStatement(ACTIVATE_FUND);
            stm.setInt(1, fundId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // lấy số dư
    public double getFundBalance(int fundId) {
        try {
            stm = connection.prepareStatement(GET_FUND_BALANCE);
            stm.setInt(1, fundId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("CurrentBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    //kiểm tra đủ số dư không
    public boolean hasSufficientBalance(int fundId, double amount) {
        double currentBalance = getFundBalance(fundId);
        return currentBalance >= amount;
    }

    //tăng số dư
    public boolean increaseFundBalance(int fundId, double amount) {
        try {
            connection.setAutoCommit(false);

            double currentBalance = getFundBalance(fundId);
            double newBalance = currentBalance + amount;

            boolean success = updateFundBalance(fundId, newBalance);

            if (success) {
                connection.commit();
            } else {
                connection.rollback();
            }

            connection.setAutoCommit(true);
            return success;
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    //giảm số dư
    public boolean decreaseFundBalance(int fundId, double amount) {
        try {
            connection.setAutoCommit(false);

            double currentBalance = getFundBalance(fundId);
            if (currentBalance < amount) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false; // Không đủ số dư
            }

            double newBalance = currentBalance - amount;
            boolean success = updateFundBalance(fundId, newBalance);

            if (success) {
                connection.commit();
            } else {
                connection.rollback();
            }

            connection.setAutoCommit(true);
            return success;
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    //Chuyển giữa 2 quỹ
    public boolean transferFunds(int fromFundId, int toFundId, double amount) {
        try {
            connection.setAutoCommit(false);

            // Kiểm tra số dư quỹ nguồn
            if (!hasSufficientBalance(fromFundId, amount)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // Trừ tiền từ quỹ nguồn
            boolean decreaseSuccess = decreaseFundBalance(fromFundId, amount);
            if (!decreaseSuccess) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // Cộng tiền vào quỹ đích
            boolean increaseSuccess = increaseFundBalance(toFundId, amount);
            if (!increaseSuccess) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    // tổng tiền của store theo type -- cash or bank
    public double getTotalBalanceByStoreAndType(int storeId, String fundType) {
        try {
            stm = connection.prepareStatement(GET_TOTAL_BALANCE_BY_STORE_TYPE);
            stm.setInt(1, storeId);
            stm.setString(2, fundType);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TotalBalance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    
    public StoreFund getFundByIdDetailed(int fundId) {
        StoreFund fund = null;
        try {
            stm = connection.prepareStatement(GET_FUND_BY_ID_DETAILED);
            stm.setInt(1, fundId);
            rs = stm.executeQuery();
            if (rs.next()) {
                fund = mapRowToStoreFund(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fund;
    }

    private StoreFund mapRowToStoreFund(ResultSet rs) throws SQLException {
        StoreFund fund = new StoreFund();
        fund.setFundID(rs.getInt("FundID"));
        fund.setStoreID(rs.getInt("StoreID"));
        fund.setFundName(rs.getString("FundName"));
        fund.setFundType(rs.getString("FundType"));
        fund.setBankName(rs.getString("BankName"));
        fund.setAccountNumber(rs.getString("AccountNumber"));
        fund.setAccountHolder(rs.getString("AccountHolder"));
        fund.setCurrentBalance(rs.getDouble("CurrentBalance"));
        fund.setActive(rs.getBoolean("IsActive"));
        fund.setCreatedAt(rs.getTimestamp("CreatedAt"));
        return fund;
    }

    public static void main(String[] args) {
        StoreFundDAO sfDao = new StoreFundDAO();
        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        System.out.println(listStoreFundCash.size());
    }
}
