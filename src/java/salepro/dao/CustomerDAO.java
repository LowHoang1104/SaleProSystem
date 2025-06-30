/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private static final String FIND_BY_ID = "SELECT * FROM Customers WHERE CustomerID = ?";
    private static final String INSERT_CUSTOMER = "INSERT INTO Customers (FullName, Phone, Email,Address, Description, Gender, BirthDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                int id = rs.getInt("CustomerID");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String description = rs.getString("Description");
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                return new Customers(id, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customers findById(int id) {
        try {
            stm = connection.prepareStatement(FIND_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String description = rs.getString("Description");
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");

                return new Customers(id, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt);
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
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                String address = rs.getString("Address");
                String description = rs.getString("Description");

                customer = new Customers(id, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean insertCustomer2(String fullName, String phone, String email, String address, String description, String gender, String birthDateStr) {
        try {
            stm = connection.prepareStatement(INSERT_CUSTOMER);
            stm.setString(1, fullName);
            stm.setString(2, phone);
            stm.setString(3, email);
            stm.setString(4, address);
            stm.setString(5, description);
            stm.setString(6, gender);

            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr); // định dạng: yyyy-MM-dd
            stm.setDate(7, birthDate);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("insertCustomer: " + e.getMessage());
        }
        return false;
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

    public boolean updateCustomer(int id, String fullName, String phone, String email, String gender, String birthDateStr, double totalSpent, String address, String description) {
        String sql = "UPDATE Customers SET FullName = ?, Phone = ?, Email = ?, Gender = ?, BirthDate = ?, TotalSpent = ?,  Address = ?, Description = ? WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, phone);
            stm.setString(3, email);
            stm.setString(4, gender);
            java.sql.Date birthDate = java.sql.Date.valueOf(birthDateStr);
            stm.setDate(5, birthDate);
            stm.setDouble(6, totalSpent);
            stm.setString(7, address);
            stm.setString(8, description);
            stm.setInt(9, id);
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

    public List<Customers> filterCustomers(String fullName, String email, String phone, String gender) {
        List<Customers> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT * FROM Customers";

        // Xây dựng điều kiện WHERE động
        if (fullName != null && !fullName.trim().isEmpty()) {
            conditions.add("FullName LIKE ?");
            params.add("%" + fullName.trim() + "%");
        }
        if (email != null && !email.trim().isEmpty()) {
            conditions.add("Email LIKE ?");
            params.add("%" + email.trim() + "%");
        }
        if (phone != null && !phone.trim().isEmpty()) {
            conditions.add("Phone LIKE ?");
            params.add("%" + phone.trim() + "%");
        }
        if (gender != null && !gender.trim().isEmpty()) {
            conditions.add("Gender = ?");
            params.add(gender.trim()); // Giả sử Gender là kiểu chuỗi ("Male", "Female")
        }

        // Thêm điều kiện vào SQL
        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Customers c = new Customers();
                c.setCustomerId(rs.getInt("CustomerID"));
                c.setFullName(rs.getString("FullName"));
                c.setPhone(rs.getString("Phone"));
                c.setEmail(rs.getString("Email"));
                c.setAddress(rs.getString("Address"));
                c.setDescription(rs.getString("Description"));
                c.setGender(rs.getString("Gender"));
                c.setBirthDate(rs.getDate("BirthDate"));
                c.setTotalSpent(rs.getDouble("TotalSpent"));
                c.setCreatedAt(rs.getDate("CreatedAt"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customers> searchCustomerByKeyword(String keyword) {
        List<Customers> list = new ArrayList<>();
        String sql = "SELECT * FROM Customers WHERE FullName LIKE ? OR Email LIKE ?";

        try {
            stm = connection.prepareStatement(sql);
            String key = "%" + (keyword != null ? keyword.trim() : "") + "%";
            stm.setString(1, key);
            stm.setString(2, key);

            rs = stm.executeQuery();
            while (rs.next()) {
                Customers c = new Customers();
                c.setCustomerId(rs.getInt("CustomerID"));
                c.setFullName(rs.getString("FullName"));
                c.setPhone(rs.getString("Phone"));
                c.setEmail(rs.getString("Email"));
                c.setAddress(rs.getString("Address"));
                c.setDescription(rs.getString("Description"));
                c.setGender(rs.getString("Gender"));
                c.setBirthDate(rs.getDate("BirthDate"));
                c.setTotalSpent(rs.getDouble("TotalSpent"));
                c.setCreatedAt(rs.getDate("CreatedAt"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean existsByColumn(String columnName, String value) {
        if (columnName == null || value == null || columnName.isBlank() || value.isBlank()) {
            return false;
        }
        String[] allowedColumns = {"Email", "Phone"};
        boolean isValidColumn = false;
        for (String allowed : allowedColumns) {
            if (allowed.equalsIgnoreCase(columnName)) {
                isValidColumn = true;
                break;
            }
        }
        if (!isValidColumn) {
            throw new IllegalArgumentException("Invalid column name: " + columnName);
        }

        String sql = "select * from Customers"
                + " where " + columnName + " = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, value);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmailExists(String email) {
        return existsByColumn("Email", email);
    }

    public boolean checkPhoneExists(String phone) {
        return existsByColumn("Phone", phone);
    }

    public static void main(String[] args) {
        CustomerDAO c = new CustomerDAO();
        System.out.println(c.checkPhoneExists("0905678901"));

    }

}
