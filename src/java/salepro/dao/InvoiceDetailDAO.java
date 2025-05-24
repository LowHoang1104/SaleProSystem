/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.model.InvoiceDetails;

/**
 *
 * @author ADMIN
 */
public class InvoiceDetailDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<InvoiceDetails> getInvoiceDetailByID(int id) {
        ArrayList<InvoiceDetails> data = new ArrayList<>();
        try {
            String strSQL = "select * from InvoiceDetails where InvoiceID=?";
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
