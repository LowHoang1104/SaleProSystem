/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.FundTransactions;
import salepro.models.Invoices;
import java.sql.*;

/**
 *
 * @author MY PC
 */
public class FundTransactionDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String INSERT_FUND_TRANSACTION_WITH_INVOICE = "INSERT INTO FundTransactions (FundID, TransactionType, Amount, Description, ReferenceType, ReferenceID, CreatedBy,ApprovedBy, Status, Notes) \n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    public boolean insertFundTransactionWithInvoice(int storeFundID, double amount, Invoices invoices) {
        try {
            stm = connection.prepareStatement(INSERT_FUND_TRANSACTION_WITH_INVOICE);
            stm.setInt(1, storeFundID);
            stm.setString(2, "Income");
            stm.setDouble(3, amount);
            stm.setString(4, "Doanh thu bán hàng");
            stm.setString(5, "Invoice");
            stm.setInt(6, invoices.getInvoiceId());
            stm.setInt(7, invoices.getUserId());
            stm.setInt(8, invoices.getUserId());
            stm.setString(9, "Approved");
            if (invoices.getPaymentMethodId() == 1) {
                stm.setString(10, "Thu tiền mặt từ hóa đơn " + invoices.getInvoiceId());
            } else {
                stm.setString(10, "Thu chuyển khoản từ hóa đơn " + invoices.getInvoiceId());
            }
            int succ = stm.executeUpdate();
            return succ > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertFundTransactionWithInvoice2(int storeFundID, double amount, Invoices invoices) {
        try {
            stm = connection.prepareStatement(INSERT_FUND_TRANSACTION_WITH_INVOICE);
            stm.setInt(1, storeFundID);
            stm.setString(2, "Expense");
            stm.setDouble(3, amount);
            stm.setString(4, "Trả tiền thừa");
            stm.setString(5, "Invoice");
            stm.setInt(6, invoices.getInvoiceId());
            stm.setInt(7, invoices.getUserId());
            stm.setInt(8, invoices.getUserId());
            stm.setString(9, "Approved");
            if (invoices.getPaymentMethodId() == 1) {
                stm.setString(10, "Trả tiền thừa hóa đơn " + invoices.getInvoiceId());
            } else {
                stm.setString(10, "Thu chuyển khoản từ hóa đơn " + invoices.getInvoiceId());
            }
            int succ = stm.executeUpdate();
            return succ > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<FundTransactions> getData() {
        ArrayList<FundTransactions> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from FundTransactions order by TransactionDate desc");
            rs = stm.executeQuery();
            while (rs.next()) {
                int transactionID = rs.getInt(1);
                int fundID = rs.getInt(2);
                String transactionType = rs.getString(3);
                double amount = rs.getDouble(4);
                String description = rs.getString(5);
                String referenceType = rs.getString(6);
                Integer referenceID = rs.getInt(7);
                Date transactionDate = rs.getDate(8);
                int createdBy = rs.getInt(9);
                Integer approvedBy = rs.getInt(10);
                String status = rs.getString(11);
                String notes = rs.getString(12);
                FundTransactions a = new FundTransactions(transactionID, fundID, transactionType, amount, description, referenceType, referenceID, transactionDate, createdBy, approvedBy, status, notes);
                data.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<FundTransactions> getDataByStoreId(int storeId) {
        ArrayList<FundTransactions> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select a.* from FundTransactions a join StoreFunds b on a.FundID=b.FundID join Stores c on b.StoreID=c.StoreID where c.StoreID=?");
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int transactionID = rs.getInt(1);
                int fundID = rs.getInt(2);
                String transactionType = rs.getString(3);
                double amount = rs.getDouble(4);
                String description = rs.getString(5);
                String referenceType = rs.getString(6);
                Integer referenceID = rs.getInt(7);
                Date transactionDate = rs.getDate(8);
                int createdBy = rs.getInt(9);
                Integer approvedBy = rs.getInt(10);
                String status = rs.getString(11);
                String notes = rs.getString(12);
                FundTransactions a = new FundTransactions(transactionID, fundID, transactionType, amount, description, referenceType, referenceID, transactionDate, createdBy, approvedBy, status, notes);
                data.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<FundTransactions> getDataByFundId(int fundId) {
        ArrayList<FundTransactions> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select a.* from FundTransactions a join StoreFunds b on a.FundID=b.FundID where b.FundID=?");
            stm.setInt(1, fundId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int transactionID = rs.getInt(1);
                int fundID = rs.getInt(2);
                String transactionType = rs.getString(3);
                double amount = rs.getDouble(4);
                String description = rs.getString(5);
                String referenceType = rs.getString(6);
                Integer referenceID = rs.getInt(7);
                Date transactionDate = rs.getDate(8);
                int createdBy = rs.getInt(9);
                Integer approvedBy = rs.getInt(10);
                String status = rs.getString(11);
                String notes = rs.getString(12);
                FundTransactions a = new FundTransactions(transactionID, fundID, transactionType, amount, description, referenceType, referenceID, transactionDate, createdBy, approvedBy, status, notes);
                data.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void createIncome(FundTransactions temp) {
        try {
            stm = connection.prepareStatement("insert into FundTransactions(FundID,TransactionType,Amount,Description,CreatedBy)\n"
                    + "values (?,?,?,?,?)");
            stm.setInt(1, temp.getFundID());
            stm.setString(2, "Income");
            stm.setDouble(3, temp.getAmount());
            stm.setString(4, temp.getDescription());
            stm.setInt(5, temp.getCreatedBy());
            stm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createExpense(FundTransactions temp) {
        try {
            stm = connection.prepareStatement("insert into FundTransactions(FundID,TransactionType,Amount,Description,CreatedBy)\n"
                    + "values (?,?,?,?,?)");
            stm.setInt(1, temp.getFundID());
            stm.setString(2, "Expense");
            stm.setDouble(3, temp.getAmount());
            stm.setString(4, temp.getDescription());
            stm.setInt(5, temp.getCreatedBy());
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createSalary(FundTransactions temp, int periodId) {
        try {
            stm = connection.prepareStatement("insert into FundTransactions(FundID,TransactionType,Amount,Description,ReferenceType,ReferenceID,Status,CreatedBy,Notes)\n"
                    + "values (?,?,?,?,?,?,?,?,?)");
            stm.setInt(1, temp.getFundID());
            stm.setString(2, "Expense");
            stm.setDouble(3, temp.getAmount());
            stm.setString(4, temp.getDescription());
            stm.setString(5, "Salary");
            stm.setInt(6, periodId);
            stm.setString(7, "Pending");
            stm.setInt(8, temp.getCreatedBy());
            stm.setString(9, temp.getNotes());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<FundTransactions> getTransactionsByDateAndStore(LocalDate date, int storeId) {
        List<FundTransactions> transactions = new ArrayList<>();
        String sql = """
        SELECT ft.* FROM FundTransactions ft 
        JOIN StoreFunds sf ON ft.FundID = sf.FundID 
        WHERE CAST(ft.TransactionDate AS DATE) = ? AND sf.StoreID = ?
        ORDER BY ft.TransactionDate DESC
        """;

        try {
            stm = connection.prepareStatement(sql);
            stm.setDate(1, java.sql.Date.valueOf(date));
            stm.setInt(2, storeId);
            rs = stm.executeQuery();

            while (rs.next()) {
                FundTransactions transaction = new FundTransactions();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setFundID(rs.getInt("FundID"));
                transaction.setTransactionType(rs.getString("TransactionType"));
                transaction.setAmount(rs.getDouble("Amount"));
                transaction.setDescription(rs.getString("Description"));
                transaction.setReferenceType(rs.getString("ReferenceType"));

                // Handle NULL values properly
                int refId = rs.getInt("ReferenceID");
                transaction.setReferenceID(rs.wasNull() ? null : refId);

                transaction.setTransactionDate(rs.getTimestamp("TransactionDate"));
                transaction.setCreatedBy(rs.getInt("CreatedBy"));

                int approvedBy = rs.getInt("ApprovedBy");
                transaction.setApprovedBy(rs.wasNull() ? null : approvedBy);

                transaction.setStatus(rs.getString("Status"));
                transaction.setNotes(rs.getString("Notes"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getTransactionsByDateAndStore: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
    public void ApproveStatus(int id,int userIdApproved){
         try {
            stm = connection.prepareStatement("Update FundTransactions set Status='Approved', ApprovedBy=? where TransactionID=?");
            stm.setInt(1, userIdApproved);
            stm.setInt(2, id);
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FundTransactionDAO da = new FundTransactionDAO();

    }

    public void createPurchase(FundTransactions ft) {
        try {
            String sql = "INSERT INTO FundTransactions "
                    + "(FundID, TransactionType, Amount, Description, ReferenceType, ReferenceID, TransactionDate, CreatedBy, ApprovedBy, Status, Notes) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, ft.getFundID());
            stm.setString(2, ft.getTransactionType());
            stm.setBigDecimal(3, new BigDecimal(String.valueOf(ft.getAmount())));
            stm.setString(4, ft.getDescription());
            stm.setString(5, ft.getReferenceType());
            stm.setInt(6, ft.getReferenceID());
            stm.setTimestamp(7, new Timestamp(ft.getTransactionDate().getTime()));
            stm.setInt(8, ft.getCreatedBy());
            stm.setInt(9, ft.getApprovedBy());
            stm.setString(10, ft.getStatus());
            stm.setString(11, ft.getNotes()); // nếu muốn để null thì set null tại đây

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public FundTransactions getFundById(int id){
         String sql = "select * from FundTransactions where TransactionID=?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            while (rs.next()) {
                FundTransactions transaction = new FundTransactions();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setFundID(rs.getInt("FundID"));
                transaction.setTransactionType(rs.getString("TransactionType"));
                transaction.setAmount(rs.getDouble("Amount"));
                transaction.setDescription(rs.getString("Description"));
                transaction.setReferenceType(rs.getString("ReferenceType"));

                // Handle NULL values properly
                int refId = rs.getInt("ReferenceID");
                transaction.setReferenceID(rs.wasNull() ? null : refId);

                transaction.setTransactionDate(rs.getTimestamp("TransactionDate"));
                transaction.setCreatedBy(rs.getInt("CreatedBy"));

                int approvedBy = rs.getInt("ApprovedBy");
                transaction.setApprovedBy(rs.wasNull() ? null : approvedBy);

                transaction.setStatus(rs.getString("Status"));
                transaction.setNotes(rs.getString("Notes"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getTransactionsByDateAndStore: " + e.getMessage());
        } 
        return null;
    }

}
