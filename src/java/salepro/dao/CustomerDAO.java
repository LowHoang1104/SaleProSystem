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
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                return new Customers(id, fullName, phone, email, gender, birthDate, totalSpent, createdAt);
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

    public Customers getCustomerById(int id) {
        Customers customer = null;
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("FullName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");

                customer = new Customers(id, fullName, phone, email, gender, birthDate, totalSpent, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customers> getAllCustomers() {
        List<Customers> list = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("CustomerID");
                String fullName = rs.getString("FullName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");

                Customers customer = new Customers(id, fullName, phone, email, gender, birthDate, totalSpent, createdAt);
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertCustomer(String fullName, String phone, String email, String gender, String birthDateStr) {
        String sql = "INSERT INTO Customers (FullName, Phone, Email, Gender, BirthDate) VALUES (?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, phone);
            stm.setString(3, email);
            stm.setString(4, gender);

            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr); // định dạng: yyyy-MM-dd
            stm.setDate(5, birthDate);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("insertCustomer: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCustomer(int id, String fullName, String phone, String email, String gender, String birthDateStr, double totalSpent) {
        String sql = "UPDATE Customers SET FullName = ?, Phone = ?, Email = ?, Gender = ?, BirthDate = ?, TotalSpent = ? WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, phone);
            stm.setString(3, email);
            stm.setString(4, gender);

            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr);
            stm.setDate(5, birthDate);

            stm.setDouble(6, totalSpent);

            stm.setInt(7, id);

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("updateCustomer: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customers WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, customerId);
            int rows = stm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CustomerDAO customerDao = new CustomerDAO();
        System.out.println(customerDao.deleteCustomer(1));
    }

}
