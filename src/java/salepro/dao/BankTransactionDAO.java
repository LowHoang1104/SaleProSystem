/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;
import java.sql.PreparedStatement;
import java.sql.Statement;
import salepro.dal.DBContext;
import salepro.dal.DBContext2;
import salepro.models.BankTransaction;
/**
 *
 * @author MY PC
 */
public class BankTransactionDAO extends DBContext2 {
    PreparedStatement stm;

    private static final String INSERT_BANK_TRANSACTION = 
        "INSERT INTO BankTransactions (InvoiceID, PaymentMethodID, BankName, AccountNumber, TransactionCode, Amount, Status, TransactionDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

//    public boolean insert(BankTransaction transaction) {
//        try {
//            stm = connection.prepareStatement(INSERT_BANK_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
//            if (transaction.getInvoiceID()!= null) {
//                stm.setInt(1, transaction.getInvoiceID());
//            } else {
//                stm.setNull(1, java.sql.Types.INTEGER);
//            }
//            stm.setInt(2, transaction.getPaymentMethodID());
//            stm.setString(3, transaction.getBankName());
//            stm.setString(4, transaction.getAccountNumber());
//            stm.setString(5, transaction.getTransactionCode());
//            stm.setDouble(6, transaction.getAmount());
//            stm.setString(7, transaction.getStatus());
//            stm.setTimestamp(8, new java.sql.Timestamp(transaction.getTransactionDate().getTime()));
//
//            int rows = stm.executeUpdate();
//            return rows > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
