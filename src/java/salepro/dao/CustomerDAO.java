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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            String strSQL = "SELECT * FROM Customers";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customers temp = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getDouble(11), rs.getDate(12), rs.getDouble(13));
                data.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace(); // nên in ra lỗi để dễ debug
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
                String code = rs.getString("CustomerCode");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String description = rs.getString("Description");
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                double points = rs.getDouble("Points");
                return new Customers(id, code, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt, points);
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

                String code = rs.getString("CustomerCode");
                String fullName = rs.getString("FullName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String description = rs.getString("Description");
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                double points = rs.getDouble("Points");
                System.out.println(points);
                return new Customers(id, code, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt, points);
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
                String code = rs.getString("CustomerCode");
                String fullName = rs.getString("FullName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String description = rs.getString("Description");
                String rank = rs.getString("Rank");
                String gender = rs.getString("Gender");
                Date birthDate = rs.getDate("BirthDate");
                double totalSpent = rs.getDouble("TotalSpent");
                Date createdAt = rs.getDate("CreatedAt");
                double points = rs.getDouble("Points");
                return new Customers(id, code, fullName, phone, email, address, description, rank, gender, birthDate, totalSpent, createdAt, points);
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

    public int getCustomerIdByCode(String code) {
        String sql = "SELECT CustomerID FROM Customers WHERE CustomerCode = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

// ================ REPORT FUNCTIONS - FIXED ================
    /**
     * Get total number of customers (excluding guest customer)
     */
    public int getTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM Customers WHERE CustomerID != 1"; // Exclude "Khách lẻ"
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get new customers this month (excluding guest customer)
     */
    public int getNewCustomersThisMonth() {
        String sql = "SELECT COUNT(*) FROM Customers "
                + "WHERE CustomerID != 1 "
                + "AND MONTH(CreatedAt) = MONTH(GETDATE()) "
                + "AND YEAR(CreatedAt) = YEAR(GETDATE())";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get total revenue from registered customers only (excluding guest
     * transactions)
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(TotalSpent) FROM Customers WHERE CustomerID != 1"; // Exclude "Khách lẻ"
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Get REAL Average Order Value from Invoice table This is the actual AOV,
     * not average customer spending
     */
    public double getAverageOrderValue() {
        String sql = "SELECT AVG(TotalAmount) FROM Invoices WHERE Status = 'Completed'";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Get average customer lifetime value (what was previously called AOV)
     */
    public double getAverageCustomerLifetimeValue() {
        String sql = "SELECT AVG(TotalSpent) FROM Customers WHERE CustomerID != 1 AND TotalSpent > 0";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Get customer distribution by rank (excluding guest customer)
     */
    public Map<String, Integer> getCustomersByRank() {
        Map<String, Integer> rankData = new HashMap<>();
        String sql = "SELECT ISNULL(Rank, 'Thường') as RankName, COUNT(*) as Count "
                + "FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "GROUP BY Rank";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                rankData.put(rs.getString("RankName"), rs.getInt("Count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankData;
    }

    /**
     * Get customer distribution by gender (excluding guest customer)
     */
    public Map<String, Integer> getCustomersByGender() {
        Map<String, Integer> genderData = new HashMap<>();
        String sql = "SELECT ISNULL(Gender, 'Khác') as GenderName, COUNT(*) as Count "
                + "FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "GROUP BY Gender";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                genderData.put(rs.getString("GenderName"), rs.getInt("Count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderData;
    }

    /**
     * Get monthly registration statistics (excluding guest customer)
     */
    public Map<String, Integer> getMonthlyRegistrations() {
        Map<String, Integer> monthlyData = new HashMap<>();
        String sql = "SELECT MONTH(CreatedAt) as Month, COUNT(*) as Count "
                + "FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "AND YEAR(CreatedAt) = YEAR(GETDATE()) "
                + "GROUP BY MONTH(CreatedAt) "
                + "ORDER BY MONTH(CreatedAt)";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                monthlyData.put(String.valueOf(rs.getInt("Month")), rs.getInt("Count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyData;
    }

    /**
     * Get customer age distribution (excluding guest customer)
     */
    public Map<String, Integer> getCustomerAgeDistribution() {
        Map<String, Integer> ageData = new HashMap<>();
        String sql = "SELECT "
                + "CASE "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) < 25 THEN 'Dưới 25' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 25 AND 35 THEN '25-35' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 36 AND 45 THEN '36-45' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 46 AND 55 THEN '46-55' "
                + "    ELSE 'Trên 55' "
                + "END as AgeGroup, "
                + "COUNT(*) as Count "
                + "FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "AND BirthDate IS NOT NULL "
                + "GROUP BY "
                + "CASE "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) < 25 THEN 'Dưới 25' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 25 AND 35 THEN '25-35' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 36 AND 45 THEN '36-45' "
                + "    WHEN DATEDIFF(YEAR, BirthDate, GETDATE()) BETWEEN 46 AND 55 THEN '46-55' "
                + "    ELSE 'Trên 55' "
                + "END";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ageData.put(rs.getString("AgeGroup"), rs.getInt("Count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ageData;
    }

    /**
     * Get customer count by rank (excluding guest customer)
     */
    public int getCustomerCountByRank(String rank) {
        String sql = "SELECT COUNT(*) FROM Customers WHERE CustomerID != 1 AND Rank = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, rank);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get new customers this week (excluding guest customer)
     */
    public int getNewCustomersThisWeek() {
        String sql = "SELECT COUNT(*) FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "AND CreatedAt >= DATEADD(DAY, -7, GETDATE())";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get top customers with better sorting (excluding guest customer)
     */
    public List<Customers> getTopCustomers(int limit) {
        List<Customers> topCustomers = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Customers "
                + "WHERE CustomerID != 1 "
                + // Exclude "Khách lẻ"
                "AND TotalSpent > 0 "
                + "ORDER BY TotalSpent DESC, Points DESC";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, limit);
            rs = stm.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerCode(rs.getString("CustomerCode"));
                customer.setFullName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customer.setDescription(rs.getString("Description"));
                customer.setRank(rs.getString("Rank"));
                customer.setGender(rs.getString("Gender"));
                customer.setBirthDate(rs.getDate("BirthDate"));
                customer.setTotalSpent(rs.getDouble("TotalSpent"));
                customer.setCreatedAt(rs.getDate("CreatedAt"));
                customer.setPoints(rs.getDouble("Points"));
                topCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topCustomers;
    }

    /**
     * Get customers for reports with filtering (excluding guest customer by
     * default)
     */
    public List<Customers> getCustomersForReport(String dateFrom, String dateTo, String rank,
            String gender, Double minSpent, Double maxSpent,
            String searchKeyword, int storeId) {
        List<Customers> customers = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT * FROM Customers WHERE CustomerID != 1"; // Always exclude "Khách lẻ"

        // Date range filter
        if (dateFrom != null && !dateFrom.isEmpty()) {
            conditions.add("CreatedAt >= ?");
            params.add(dateFrom);
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            conditions.add("CreatedAt <= ?");
            params.add(dateTo);
        }

        // Rank filter
        if (rank != null && !rank.isEmpty()) {
            if (rank.equals("Thường")) {
                conditions.add("(Rank IS NULL OR Rank = '')");
            } else {
                conditions.add("Rank = ?");
                params.add(rank);
            }
        }

        // Gender filter
        if (gender != null && !gender.isEmpty()) {
            conditions.add("Gender = ?");
            params.add(gender);
        }

        // Spending range filter
        if (minSpent != null) {
            conditions.add("TotalSpent >= ?");
            params.add(minSpent);
        }
        if (maxSpent != null) {
            conditions.add("TotalSpent <= ?");
            params.add(maxSpent);
        }

        // Search keyword
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            conditions.add("(FullName LIKE ? OR Email LIKE ? OR Phone LIKE ?)");
            String keyword = "%" + searchKeyword.trim() + "%";
            params.add(keyword);
            params.add(keyword);
            params.add(keyword);
        }

        // Add conditions to SQL
        for (String condition : conditions) {
            sql += " AND " + condition;
        }

        // Order by total spent descending
        sql += " ORDER BY TotalSpent DESC";

        try {
            stm = connection.prepareStatement(sql);

            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    stm.setDouble(i + 1, (Double) param);
                } else if (param instanceof Integer) {
                    stm.setInt(i + 1, (Integer) param);
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerCode(rs.getString("CustomerCode"));
                customer.setFullName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customer.setDescription(rs.getString("Description"));
                customer.setRank(rs.getString("Rank"));
                customer.setGender(rs.getString("Gender"));
                customer.setBirthDate(rs.getDate("BirthDate"));
                customer.setTotalSpent(rs.getDouble("TotalSpent"));
                customer.setCreatedAt(rs.getDate("CreatedAt"));
                customer.setPoints(rs.getDouble("Points"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Get customer detail with purchase history for modal
     */
    public Map<String, Object> getCustomerDetailForReport(int customerId) {
        Map<String, Object> customerDetail = new HashMap<>();

        // Get customer info
        String customerSql = "SELECT * FROM Customers WHERE CustomerID = ? AND CustomerID != 1"; // Exclude guest
        try {
            stm = connection.prepareStatement(customerSql);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();

            if (rs.next()) {
                Customers customer = new Customers();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerCode(rs.getString("CustomerCode"));
                customer.setFullName(rs.getString("FullName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customer.setDescription(rs.getString("Description"));
                customer.setRank(rs.getString("Rank"));
                customer.setGender(rs.getString("Gender"));
                customer.setBirthDate(rs.getDate("BirthDate"));
                customer.setTotalSpent(rs.getDouble("TotalSpent"));
                customer.setCreatedAt(rs.getDate("CreatedAt"));
                customer.setPoints(rs.getDouble("Points"));

                customerDetail.put("customer", customer);
            }

            // Get purchase statistics
            String statsSql = "SELECT COUNT(*) as OrderCount, "
                    + "MAX(InvoiceDate) as LastPurchase, "
                    + "AVG(TotalAmount) as AvgOrderValue "
                    + "FROM Invoices WHERE CustomerID = ? AND CustomerID != 1"; // Exclude guest

            stm = connection.prepareStatement(statsSql);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();

            if (rs.next()) {
                customerDetail.put("orderCount", rs.getInt("OrderCount"));
                customerDetail.put("lastPurchase", rs.getDate("LastPurchase"));
                customerDetail.put("avgOrderValue", rs.getDouble("AvgOrderValue"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerDetail;
    }

// ================ ADDITIONAL HELPER METHODS ================
    /**
     * Get guest customer transactions statistics (for comparison)
     */
    public Map<String, Object> getGuestCustomerStats() {
        Map<String, Object> guestStats = new HashMap<>();

        // Guest customer total spent
        String guestSpentSql = "SELECT TotalSpent FROM Customers WHERE CustomerID = 1";

        // Guest customer order count and average
        String guestOrdersSql = "SELECT COUNT(*) as OrderCount, AVG(TotalAmount) as AvgOrderValue "
                + "FROM Invoices WHERE CustomerID = 1";

        try {
            // Get guest spending
            stm = connection.prepareStatement(guestSpentSql);
            rs = stm.executeQuery();
            if (rs.next()) {
                guestStats.put("totalSpent", rs.getDouble("TotalSpent"));
            }

            // Get guest orders
            stm = connection.prepareStatement(guestOrdersSql);
            rs = stm.executeQuery();
            if (rs.next()) {
                guestStats.put("orderCount", rs.getInt("OrderCount"));
                guestStats.put("avgOrderValue", rs.getDouble("AvgOrderValue"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestStats;
    }

    /**
     * Get total business revenue (including guest customers)
     */
    public double getTotalBusinessRevenue() {
        String sql = "SELECT SUM(TotalAmount) FROM Invoices WHERE Status = 'Completed'";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Cập nhật điểm tích lũy cho khách hàng (1 điểm = 1000 VND)
     */
    public boolean updateCustomerPoints(int customerId, double points) {
        String sql = "UPDATE Customers SET Points = ? WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDouble(1, points);
            stm.setInt(2, customerId);

            System.out.println("=== UPDATE POINTS ===");
            System.out.println("Customer ID: " + customerId);
            System.out.println("New Points: " + points);
            System.out.println("==================");

            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thêm điểm tích lũy cho khách hàng
     */
    public boolean addPointsToCustomer(int customerId, double additionalPoints) {
        String sql = "UPDATE Customers SET Points = COALESCE(Points, 0) + ? WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDouble(1, additionalPoints);
            stm.setInt(2, customerId);

            System.out.println("=== ADD POINTS ===");
            System.out.println("Customer ID: " + customerId);
            System.out.println("Adding Points: " + additionalPoints);
            System.out.println("================");

            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Trừ điểm từ khách hàng (sử dụng điểm)
     */
    public boolean deductPointsFromCustomer(int customerId, double pointsToDeduct) {
        String sql = "UPDATE Customers SET Points = Points - ? WHERE CustomerID = ? AND Points >= ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDouble(1, pointsToDeduct);
            stm.setInt(2, customerId);
            stm.setDouble(3, pointsToDeduct);

            System.out.println("=== DEDUCT POINTS ===");
            System.out.println("Customer ID: " + customerId);
            System.out.println("Deducting Points: " + pointsToDeduct);
            System.out.println("===================");

            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy điểm hiện tại của khách hàng
     */
    public double getCustomerPoints(int customerId) {
        String sql = "SELECT COALESCE(Points, 0) FROM Customers WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();
            if (rs.next()) {
                double points = rs.getDouble(1);
                System.out.println("=== GET POINTS ===");
                System.out.println("Customer ID: " + customerId);
                System.out.println("Current Points: " + points);
                System.out.println("================");
                return points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Cập nhật tổng chi tiêu của khách hàng
     */
    public boolean updateCustomerTotalSpent(int customerId, double additionalAmount) {
        String sql = "UPDATE Customers SET TotalSpent = TotalSpent + ? WHERE CustomerID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setDouble(1, additionalAmount);
            stm.setInt(2, customerId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateCustomerRank(int customerId, CustomerDAO customerDAO) {
        try {
            Customers customer = customerDAO.findById(customerId);
            if (customer != null) {
                double totalSpent = customer.getTotalSpent();
                String newRank = determineCustomerRank(totalSpent);

                if (!newRank.equals(customer.getRank())) {
                    String sql = "UPDATE Customers SET Rank = ? WHERE CustomerID = ?";
                    stm = connection.prepareStatement(sql);
                    stm.setString(1, newRank);
                    stm.setInt(2, customerId);
                    stm.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determineCustomerRank(double totalSpent) {
        if (totalSpent >= 50000000) { // 50 triệu VND
            return "VIP";
        } else if (totalSpent >= 20000000) { // 20 triệu VND
            return "Gold";
        } else if (totalSpent >= 10000000) { // 10 triệu VND
            return "Silver";
        } else if (totalSpent >= 5000000) { // 5 triệu VND
            return "Bronze";
        } else {
            return "Thường";
        }
    }
}
