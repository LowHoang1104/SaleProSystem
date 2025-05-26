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
import salepro.models.Customers;
import salepro.models.Employees;

/**
 *
 * @author MY PC
 */
public class CustomerDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String FIND_BY_PHONE = "SELECT * FROM Customers WHERE Phone = ?";
    private static final String INSERT_CUSTOMER = "";

    public ArrayList<Customers> getData() {
        ArrayList<Customers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Customers";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customers temp = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDouble(10), rs.getDate(11));
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public Customers findByPhone(String phone) {
        try {
            stm = connection.prepareStatement(FIND_BY_PHONE);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDouble(10), rs.getDate(11));            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCustomerNameByID(int id) {
        try {
            String strSQL = "select a.FullName from Customers a where a.CustomerID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

}
