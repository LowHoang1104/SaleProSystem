/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import salepro.dal.DBContext2;
import salepro.models.FundTransactions;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
public class FundTransactionDAO extends DBContext2 {
    
    PreparedStatement stm;
    ResultSet rs;
    
    private static final String INSERT_FUND_TRANSACTION_WITH_INVOICE = "INSERT INTO FundTransactions (FundID, TransactionType, Amount, Description, ReferenceType, ReferenceID, CreatedBy,ApprovedBy, Status, Notes) \n"
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";
    
    public ArrayList<FundTransactions> getData() {
        ArrayList<FundTransactions> data= new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from FundTransactions");
            rs=stm.executeQuery();
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
        ArrayList<FundTransactions> data= new ArrayList<>();
        try {
            stm = connection.prepareStatement("select a.* from FundTransactions a join StoreFunds b on a.FundID=b.FundID join Stores c on b.StoreID=c.StoreID where c.StoreID=?");
            stm.setInt(1, storeId);
            rs=stm.executeQuery();
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
    public static void main(String[] args) {
        FundTransactionDAO da= new FundTransactionDAO();
        System.out.println(da.getData().size());
    }
}
