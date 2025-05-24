/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import salepro.dal.DBContext;
import salepro.models.Customers;

/**
 *
 * @author MY PC
 */
public class CustomerDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String FIND_BY_PHONE = "SELECT * FROM Customers WHERE Phone = ?";
    private static final String INSERT_CUSTOMER = "";

    public Customers findByPhone(String phone) {
        try {
            stm = connection.prepareStatement(FIND_BY_PHONE);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("CustomerID");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent  =rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                return new Customers(id, fullName, phone, email, gender, birthDate, totalSpent, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getCustomerNameByID(int id){
        try {
            String strSQL = "select a.FullName from Customers a where a.CustomerID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1,id );
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }
    
}
