/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.model.Customers;
import salepro.model.Invoices;

/**
 *
 * @author ADMIN
 */
public class InvoiceDAO extends DBContext2{
     PreparedStatement stm;
    ResultSet rs;
    
    public ArrayList<Invoices> listInvoice(){
        ArrayList<Invoices> data= new ArrayList<Invoices>();
         try {
            String strSQL = "select * from Invoices";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Invoices temp= new Invoices(rs.getInt(1),rs.getDate(2).toLocalDate(), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getInt(7));
                data.add(temp);
            }
        } catch (Exception e) {

        }
         return data;
    }
    
    public Customers getCustomerByInvoiceID(int id){
        try {
            String strSQL = "select b.* from Invoices a join Customers b on a.CustomerID=b.CustomerID  where a.InvoiceID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1,id );
            rs = stm.executeQuery();
            while (rs.next()) {
                Customers temp= new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toLocalDate(), rs.getDouble(7), rs.getDate(8).toLocalDate());
                return temp;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
