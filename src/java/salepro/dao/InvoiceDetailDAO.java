/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import salepro.dal.DBContext2;
import salepro.models.InvoiceDetails;

/**
 *
 * @author MY PC
 */
public class InvoiceDetailDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_INVOICE_DETAILS = "SELECT * FROM InvoiceDetails WHERE InvoiceID = ?";
    private static final String INSERT_INVOICE_DETAIL = "INSERT INTO InvoiceDetails (InvoiceID, ProductID, Quantity, UnitPrice, DiscountPercent) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_PRODUCT_SALES = "SELECT SUM(id.Quantity) AS TotalSold, SUM(id.Quantity * id.UnitPrice * (1 - id.DiscountPercent/100)) AS TotalRevenue FROM InvoiceDetails id INNER JOIN Invoices i ON id.InvoiceID = i.InvoiceID WHERE id.ProductID = ? AND i.InvoiceDate BETWEEN ? AND ?";

    public boolean insert(InvoiceDetails detail) {
        try {
            stm = connection.prepareStatement(INSERT_INVOICE_DETAIL);
            stm.setInt(1, detail.getInvoiceId());
            stm.setInt(2, detail.getProductId());
            stm.setInt(3, detail.getQuantity());
            stm.setDouble(4, detail.getUnitPrice());
            stm.setDouble(5, detail.getDiscountPercent());
            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<InvoiceDetails> getInvoiceDetailByID(int id) {
        ArrayList<InvoiceDetails> data = new ArrayList<>();
        try {
            String strSQL = GET_INVOICE_DETAILS;
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                InvoiceDetails temp = new InvoiceDetails(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5));
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

}
