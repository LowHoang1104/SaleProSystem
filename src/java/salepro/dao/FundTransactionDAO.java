/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
public class FundTransactionDAO extends DBContext {

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
}
