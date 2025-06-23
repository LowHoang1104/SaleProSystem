/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Customers;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
public class InvoiceDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_ALL_INVOICES = "SELECT * FROM Invoices";
    private static final String GET_INVOICE_BY_ID = "SELECT * FROM Invoices WHERE InvoiceID = ?";
    private static final String GET_INVOICES_BY_CUSTOMER = "SELECT * FROM Invoices WHERE CustomerID = ?";
    private static final String GET_INVOICES_BY_DATE_RANGE = "SELECT * FROM Invoices WHERE InvoiceDate BETWEEN ? AND ?";
    private static final String GET_INVOICES_BY_STORE = "SELECT * FROM Invoices WHERE StoreID = ?";
    private static final String INSERT_INVOICE = "INSERT INTO Invoices (StoreID, UserID, CustomerID, TotalAmount,SubTotal,VATAmount, PaymentMethodID) \n"
            + "  VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_DAILY_SALES = "SELECT SUM(TotalAmount) AS DailySales FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(GETDATE() AS DATE)";
    private static final String GET_MONTHLY_SALES = "SELECT SUM(TotalAmount) AS MonthlySales FROM Invoices WHERE MONTH(InvoiceDate) = MONTH(GETDATE()) AND YEAR(InvoiceDate) = YEAR(GETDATE())";
    private static final String GET_INVOICEID_MAX = "SELECT MAX(InvoiceID) FROM Invoices;";

    public List<Invoices> getData() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ALL_INVOICES);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);
                int userId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double totalAmount = rs.getDouble(6);
                double subTotal = rs.getDouble(7);
                double VATPercent = rs.getDouble(8);
                double VATAmount = rs.getDouble(9);
                int paymentId = rs.getInt(10);

                Invoices invoice = new Invoices(id, invoiceDate, storeId, userId, customerId, totalAmount, subTotal, VATPercent, VATAmount, paymentId);
                data.add(invoice);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public Invoices getInvoiceById(int id) {
        Invoices invoice = null;
        try {
            stm = connection.prepareStatement(GET_INVOICE_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);
                int userId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double totalAmount = rs.getDouble(6);
                double subTotal = rs.getDouble(7);
                double VATPercent = rs.getDouble(8);
                double VATAmount = rs.getDouble(9);
                int paymentId = rs.getInt(10);

                invoice = new Invoices(id, invoiceDate, storeId, userId, customerId, totalAmount, subTotal, VATPercent, VATAmount, paymentId);
            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public Invoices getInvoiceByCustomerId(int customerId) {
        Invoices invoice = null;
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_CUSTOMER);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);
                int userId = rs.getInt(4);
                double totalAmount = rs.getDouble(6);
                double subTotal = rs.getDouble(7);
                double VATPercent = rs.getDouble(8);
                double VATAmount = rs.getDouble(9);
                int paymentId = rs.getInt(10);

                invoice = new Invoices(id, invoiceDate, storeId, userId, customerId, totalAmount, subTotal, VATPercent, VATAmount, paymentId);

            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public Invoices getInvoiceByStoreId(int storeId) {
        Invoices invoice = null;
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_STORE);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date invoiceDate = rs.getDate(2);
                int userId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double totalAmount = rs.getDouble(6);
                double subTotal = rs.getDouble(7);
                double VATPercent = rs.getDouble(8);
                double VATAmount = rs.getDouble(9);
                int paymentId = rs.getInt(10);

                invoice = new Invoices(id, invoiceDate, storeId, userId, customerId, totalAmount, subTotal, VATPercent, VATAmount, paymentId);

            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public Customers getCustomerByInvoiceID(int id) {
        try {
            String strSQL = "select b.* from Invoices a join Customers b on a.CustomerID=b.CustomerID  where a.InvoiceID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customers temp = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDouble(10), rs.getDate(11));
                return temp;     
            }
        } catch (Exception e) {
        }
        return null;
    }


    public boolean insertInvoice(int storeID, int userId, int customerID, double TotalAmount, double subTotal, double VATAmount, int paymentMethodID) {
        try {
            stm = connection.prepareStatement(INSERT_INVOICE);
            stm.setInt(1, storeID);
            stm.setInt(2, userId);
            stm.setInt(3, customerID);
            stm.setDouble(4, TotalAmount);
            stm.setDouble(5, subTotal);
            stm.setDouble(6, VATAmount);
            stm.setInt(7, paymentMethodID);
            int succ = stm.executeUpdate();
            return succ > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public int getInvoiceIdMax() {
        int id = 0;
        try {
            stm = connection.prepareStatement(GET_INVOICEID_MAX);
            rs = stm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
