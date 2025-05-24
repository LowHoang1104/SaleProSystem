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
import kiotfpt.dal.DBContext;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
public class InvoiceDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_ALL_INVOICES = "SELECT * FROM Invoices";
    private static final String GET_INVOICE_BY_ID = "SELECT * FROM Invoices WHERE InvoiceID = ?";
    private static final String GET_INVOICES_BY_CUSTOMER = "SELECT * FROM Invoices WHERE CustomerID = ?";
    private static final String GET_INVOICES_BY_DATE_RANGE = "SELECT * FROM Invoices WHERE InvoiceDate BETWEEN ? AND ?";
    private static final String GET_INVOICES_BY_STORE = "SELECT * FROM Invoices WHERE StoreID = ?";
    private static final String GET_INVOICES_BY_EMPLOYEE = "SELECT * FROM Invoices WHERE EmployeeID = ?";
    private static final String INSERT_INVOICE = "INSERT INTO Invoices (StoreID, EmployeeID, CustomerID, TotalAmount, PaymentMethodID) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_DAILY_SALES = "SELECT SUM(TotalAmount) AS DailySales FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(GETDATE() AS DATE)";
    private static final String GET_MONTHLY_SALES = "SELECT SUM(TotalAmount) AS MonthlySales FROM Invoices WHERE MONTH(InvoiceDate) = MONTH(GETDATE()) AND YEAR(InvoiceDate) = YEAR(GETDATE())";

    public List<Invoices> getData() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ALL_INVOICES);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);
                int employeeId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double total = rs.getDouble(6);
                int paymentId = rs.getInt(7);

                Invoices invoice = new Invoices(storeId, invoiceDate, storeId, employeeId, customerId, total, paymentId);
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
                int employeeId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double total = rs.getDouble(6);
                int paymentId = rs.getInt(7);

                invoice = new Invoices(id, invoiceDate, storeId, employeeId, customerId, total, paymentId);

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
                int employeeId = rs.getInt(4);
                double total = rs.getDouble(6);
                int paymentId = rs.getInt(7);

                invoice = new Invoices(id, invoiceDate, storeId, employeeId, customerId, total, paymentId);

            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public Invoices getInvoiceByStoreId(int id) {
        Invoices invoice = null;
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_STORE);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);
                int employeeId = rs.getInt(4);
                int customerId = rs.getInt(5);
                double total = rs.getDouble(6);
                int paymentId = rs.getInt(7);

                invoice = new Invoices(id, invoiceDate, storeId, employeeId, customerId, total, paymentId);

            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public Invoices getInvoiceByEmployeeId(int employeeId) {
        Invoices invoice = null;
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_EMPLOYEE);
            stm.setInt(1, employeeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date invoiceDate = rs.getDate(2);
                int storeId = rs.getInt(3);

                int customerId = rs.getInt(5);
                double total = rs.getDouble(6);
                int paymentId = rs.getInt(7);

                invoice = new Invoices(id, invoiceDate, storeId, employeeId, customerId, total, paymentId);

            }
        } catch (Exception ex) {

        }
        return invoice;
    }

}
