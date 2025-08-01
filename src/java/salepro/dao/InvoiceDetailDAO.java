/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import salepro.dal.DBContext2;
import salepro.models.InvoiceDetails;
import salepro.models.up.CartItem;
import salepro.models.up.InvoiceItem;

/**
 *
 * @author MY PC
 */
public class InvoiceDetailDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_INVOICE_DETAILS = "SELECT * FROM InvoiceDetails WHERE InvoiceID = ?";
    private static final String INSERT_INVOICE_DETAIL = "INSERT INTO InvoiceDetails (InvoiceID, ProductVariantID, Quantity, UnitPrice, DiscountPercent) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_PRODUCT_SALES = "SELECT SUM(id.Quantity) AS TotalSold, SUM(id.Quantity * id.UnitPrice * (1 - id.DiscountPercent/100)) AS TotalRevenue FROM InvoiceDetails id INNER JOIN Invoices i ON id.InvoiceID = i.InvoiceID WHERE id.ProductID = ? AND i.InvoiceDate BETWEEN ? AND ?";

    public boolean insert(int invoiceId, int productId, int quantity, double unitPrice, double discount) {
        try {
            stm = connection.prepareStatement(INSERT_INVOICE_DETAIL);
            stm.setInt(1, invoiceId);
            stm.setInt(2, productId);
            stm.setInt(3, quantity);
            stm.setDouble(4, unitPrice);
            stm.setDouble(5, discount);
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
            stm = connection.prepareStatement(GET_INVOICE_DETAILS);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                InvoiceDetails temp = new InvoiceDetails(
                        rs.getInt("InvoiceID"),
                        rs.getInt( "ProductVariantID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("UnitPrice"),
                        rs.getDouble("DiscountPercent")
                );
                data.add(temp);
                System.out.println("  Added to list: " + temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println(new InvoiceDetailDAO().getInvoiceDetailByID(1).size());
    }

}
