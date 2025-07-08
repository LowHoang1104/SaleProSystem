/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import salepro.dal.DBContext2;
import salepro.models.Customers;
import salepro.models.Invoices;

/**
 *
 * @author MY PC
 */
public class InvoiceDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_ALL_INVOICES = "SELECT * FROM Invoices";
    private static final String GET_INVOICE_BY_ID = "SELECT * FROM Invoices WHERE InvoiceID = ?";
    private static final String GET_INVOICES_BY_CUSTOMER_ID = "SELECT * FROM Invoices WHERE CustomerID = ?";
    private static final String GET_INVOICES_BY_STORE = "SELECT * FROM Invoices WHERE StoreID = ?";
    private static final String INSERT_INVOICE = "INSERT INTO Invoices (StoreID, SaleID, CreatedBy, CustomerID, TotalAmount, SubTotal,DiscountPercent,DiscountAmount, VATAmount,PaidAmount, PaymentMethodID) \n"
            + "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_DAILY_SALES = "SELECT SUM(TotalAmount) AS DailySales FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(GETDATE() AS DATE)";
    private static final String GET_MONTHLY_SALES = "SELECT SUM(TotalAmount) AS MonthlySales FROM Invoices WHERE MONTH(InvoiceDate) = MONTH(GETDATE()) AND YEAR(InvoiceDate) = YEAR(GETDATE())";
    private static final String GET_INVOICEID_MAX = "SELECT MAX(InvoiceID) FROM Invoices;";
    private static final String GET_INVOICES_BY_DATE = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(? AS DATE)";
    private static final String GET_INVOICES_BY_DATE_PAGING = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(? AS DATE) ORDER BY InvoiceDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    // NEW: Time-based query constants
    private static final String GET_INVOICES_TODAY = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(GETDATE() AS DATE) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_YESTERDAY = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) = CAST(DATEADD(day, -1, GETDATE()) AS DATE) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_THIS_WEEK = "SELECT * FROM Invoices WHERE DATEPART(week, InvoiceDate) = DATEPART(week, GETDATE()) AND DATEPART(year, InvoiceDate) = DATEPART(year, GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_LAST_WEEK = "SELECT * FROM Invoices WHERE DATEPART(week, InvoiceDate) = DATEPART(week, DATEADD(week, -1, GETDATE())) AND DATEPART(year, InvoiceDate) = DATEPART(year, DATEADD(week, -1, GETDATE())) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_7_DAYS = "SELECT * FROM Invoices WHERE InvoiceDate >= DATEADD(day, -7, GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_THIS_MONTH = "SELECT * FROM Invoices WHERE MONTH(InvoiceDate) = MONTH(GETDATE()) AND YEAR(InvoiceDate) = YEAR(GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_LAST_MONTH = "SELECT * FROM Invoices WHERE MONTH(InvoiceDate) = MONTH(DATEADD(month, -1, GETDATE())) AND YEAR(InvoiceDate) = YEAR(DATEADD(month, -1, GETDATE())) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_30_DAYS = "SELECT * FROM Invoices WHERE InvoiceDate >= DATEADD(day, -30, GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_THIS_QUARTER = "SELECT * FROM Invoices WHERE DATEPART(quarter, InvoiceDate) = DATEPART(quarter, GETDATE()) AND YEAR(InvoiceDate) = YEAR(GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_LAST_QUARTER = "SELECT * FROM Invoices WHERE DATEPART(quarter, InvoiceDate) = DATEPART(quarter, DATEADD(quarter, -1, GETDATE())) AND YEAR(InvoiceDate) = YEAR(DATEADD(quarter, -1, GETDATE())) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_THIS_YEAR = "SELECT * FROM Invoices WHERE YEAR(InvoiceDate) = YEAR(GETDATE()) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_LAST_YEAR = "SELECT * FROM Invoices WHERE YEAR(InvoiceDate) = YEAR(DATEADD(year, -1, GETDATE())) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_DATE_RANGE = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) ORDER BY InvoiceDate DESC";

    private static final String GET_INVOICES_DATE_RANGE_PAGING = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) ORDER BY InvoiceDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    private static final String GET_INVOICES_WITH_FILTERS = "SELECT * FROM Invoices WHERE CAST(InvoiceDate AS DATE) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";

    private static final String GET_INVOICES_COUNT_DATE_RANGE = "SELECT COUNT(*) as TotalCount FROM Invoices WHERE CAST(InvoiceDate AS DATE) BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";

    // Queries cho InvoiceDetails (existing)
    private static final String GET_TOTAL_QUANTITY_BY_INVOICE = "SELECT SUM(Quantity) AS TotalQuantity FROM InvoiceDetails WHERE InvoiceID = ?";
    private static final String GET_QUANTITIES_FOR_INVOICES = "SELECT InvoiceID, SUM(Quantity) AS TotalQuantity FROM InvoiceDetails WHERE InvoiceID IN (%s) GROUP BY InvoiceID";

    private static final String SEARCH_INVOICES_QUICK = """
    SELECT DISTINCT i.*, 
           CASE 
               WHEN i.CustomerID IS NOT NULL AND i.CustomerID > 0 
               THEN c.CustomerName 
               ELSE 'Khách lẻ' 
           END as CustomerName
    FROM Invoices i
    LEFT JOIN Customers c ON i.CustomerID = c.CustomerID
    LEFT JOIN InvoiceDetails id ON i.InvoiceID = id.InvoiceID
    LEFT JOIN Products p ON id.ProductID = p.ProductID
    WHERE i.InvoiceID LIKE ? 
       OR c.CustomerName LIKE ? 
       OR c.PhoneNumber LIKE ?
       OR c.CustomerCode LIKE ?
       OR p.ProductName LIKE ?
       OR p.ProductCode LIKE ?
       OR CAST(i.TotalAmount AS VARCHAR) LIKE ?
    ORDER BY i.InvoiceDate DESC
    """;

    private static final String SEARCH_INVOICES_ADVANCED = """
    SELECT DISTINCT i.*, 
           CASE 
               WHEN i.CustomerID IS NOT NULL AND i.CustomerID > 0 
               THEN c.CustomerName 
               ELSE 'Khách lẻ' 
           END as CustomerName
    FROM Invoices i
    LEFT JOIN Customers c ON i.CustomerID = c.CustomerID
    LEFT JOIN InvoiceDetails id ON i.InvoiceID = id.InvoiceID
    LEFT JOIN Products p ON id.ProductID = p.ProductID
    WHERE 1=1
    """;

    public List<Invoices> getData() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_ALL_INVOICES);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
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
                invoice = mapResultSetToInvoice(rs);
            }
        } catch (Exception ex) {

        }
        return invoice;
    }

    public List<Invoices> getInvoicesByCustomerId(int customerId) {
        List<Invoices> invoices = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_CUSTOMER_ID);
            stm.setInt(1, customerId);
            rs = stm.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            System.err.println("Lỗi trong getInvoicesByCustomerId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return invoices;
    }

    public List<Invoices> getInvoicesByStoreId(int storeId) {
        List<Invoices> invoices = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_STORE);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            System.err.println("Lỗi trong getInvoicesByStoreId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return invoices;
    }

    public Customers getCustomerByInvoiceID(int id) {
        try {
            String strSQL = "select b.* from Invoices a join Customers b on a.CustomerID=b.CustomerID  where a.InvoiceID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customers temp = new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10), rs.getDouble(11), rs.getDate(12));
                return temp;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean insertInvoice(int storeID, int userId, int createBy, int customerID, double TotalAmount, double subTotal, double discount, double discountAmount, double VATAmount, double paidAmount, int paymentMethodID) {
        try {
            stm = connection.prepareStatement(INSERT_INVOICE);
            stm.setInt(1, storeID);
            stm.setInt(2, userId);
            stm.setInt(3, createBy);
            stm.setInt(4, customerID);
            stm.setDouble(5, TotalAmount);
            stm.setDouble(6, subTotal);
            stm.setDouble(7, discount);
            stm.setDouble(8, discountAmount);
            stm.setDouble(9, VATAmount);
            stm.setDouble(10, paidAmount);
            stm.setInt(11, paymentMethodID);
            int succ = stm.executeUpdate();
            return succ > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public int getInvoiceIdMax() {
        int id = 0;
        try {
            stm = connection.prepareStatement(GET_INVOICEID_MAX);
            rs = stm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;

    }

    public List<Invoices> getInvoicesByDate(String date) {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_BY_DATE);
            stm.setString(1, date);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // Thêm method lấy invoices với paging
    public List<Invoices> getInvoicesByDateWithPaging(String date, int page, int pageSize) {
        List<Invoices> data = new ArrayList<>();
        try {
            int offset = (page - 1) * pageSize;

            System.out.println("Getting invoices for date: " + date + ", page: " + page + ", pageSize: " + pageSize);
            stm = connection.prepareStatement(GET_INVOICES_BY_DATE_PAGING);
            stm.setString(1, date);
            stm.setInt(2, offset);
            stm.setInt(3, pageSize);

            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
            System.out.println("Found " + data.size() + " invoices for page " + page);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting invoices with paging: " + e.getMessage());
        }
        return data;
    }

    public int getTotalQuantityByInvoice(int invoiceId) {
        int totalQuantity = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_QUANTITY_BY_INVOICE);
            stm.setInt(1, invoiceId);
            rs = stm.executeQuery();
            if (rs.next()) {
                totalQuantity = rs.getInt("TotalQuantity");
                if (rs.wasNull()) {
                    totalQuantity = 0;
                }
            }
            System.out.println("Invoice " + invoiceId + " has total quantity: " + totalQuantity);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting total quantity for invoice " + invoiceId + ": " + e.getMessage());
        }
        return totalQuantity;
    }

    public List<Invoices> getInvoicesToday() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_TODAY);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for yesterday
     */
    public List<Invoices> getInvoicesYesterday() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_YESTERDAY);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for this week
     */
    public List<Invoices> getInvoicesThisWeek() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_THIS_WEEK);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last week
     */
    public List<Invoices> getInvoicesLastWeek() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_LAST_WEEK);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last 7 days
     */
    public List<Invoices> getInvoicesLast7Days() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_7_DAYS);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for this month
     */
    public List<Invoices> getInvoicesThisMonth() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_THIS_MONTH);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last month
     */
    public List<Invoices> getInvoicesLastMonth() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_LAST_MONTH);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last 30 days
     */
    public List<Invoices> getInvoicesLast30Days() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_30_DAYS);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for this quarter
     */
    public List<Invoices> getInvoicesThisQuarter() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_THIS_QUARTER);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last quarter
     */
    public List<Invoices> getInvoicesLastQuarter() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_LAST_QUARTER);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for this year
     */
    public List<Invoices> getInvoicesThisYear() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_THIS_YEAR);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices for last year
     */
    public List<Invoices> getInvoicesLastYear() {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_LAST_YEAR);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices by date range
     */
    public List<Invoices> getInvoicesByDateRange(String startDate, String endDate) {
        List<Invoices> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_INVOICES_DATE_RANGE);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices by date range with pagination
     */
    public List<Invoices> getInvoicesByDateRangeWithPaging(String startDate, String endDate, int page, int pageSize) {
        List<Invoices> data = new ArrayList<>();
        try {
            int offset = (page - 1) * pageSize;
            stm = connection.prepareStatement(GET_INVOICES_DATE_RANGE_PAGING);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            stm.setInt(3, offset);
            stm.setInt(4, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices count by date range
     */
    public int getInvoicesCountByDateRange(String startDate, String endDate) {
        int count = 0;
        try {
            stm = connection.prepareStatement(GET_INVOICES_COUNT_DATE_RANGE);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Get invoices with filters (status, invoice type)
     */
    public List<Invoices> getInvoicesWithFilters(String startDate, String endDate,
            List<String> statuses, List<String> invoiceTypes,
            int page, int pageSize) {
        List<Invoices> data = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder(GET_INVOICES_WITH_FILTERS);

            // Add status filter
            if (statuses != null && !statuses.isEmpty()) {
                query.append(" AND Status IN (");
                for (int i = 0; i < statuses.size(); i++) {
                    query.append("?");
                    if (i < statuses.size() - 1) {
                        query.append(",");
                    }
                }
                query.append(")");
            }

            // Add invoice type filter if needed
            // This depends on how you store invoice types in your database
            // For example, if you have a separate field for delivery type:
            // if (invoiceTypes != null && !invoiceTypes.isEmpty()) {
            //     query.append(" AND DeliveryType IN (");
            //     for (int i = 0; i < invoiceTypes.size(); i++) {
            //         query.append("?");
            //         if (i < invoiceTypes.size() - 1) query.append(",");
            //     }
            //     query.append(")");
            // }
            query.append(" ORDER BY InvoiceDate DESC");

            // Add pagination
            if (page > 0 && pageSize > 0) {
                int offset = (page - 1) * pageSize;
                query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            }

            stm = connection.prepareStatement(query.toString());

            int paramIndex = 1;
            stm.setString(paramIndex++, startDate);
            stm.setString(paramIndex++, endDate);

            // Set status parameters
            if (statuses != null && !statuses.isEmpty()) {
                for (String status : statuses) {
                    stm.setString(paramIndex++, status);
                }
            }

            // Set invoice type parameters if needed
            // if (invoiceTypes != null && !invoiceTypes.isEmpty()) {
            //     for (String type : invoiceTypes) {
            //         stm.setString(paramIndex++, type);
            //     }
            // }
            // Set pagination parameters
            if (page > 0 && pageSize > 0) {
                int offset = (page - 1) * pageSize;
                stm.setInt(paramIndex++, offset);
                stm.setInt(paramIndex++, pageSize);
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToInvoice(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get invoices by time filter type
     */
    public List<Invoices> getInvoicesByTimeFilter(String timeFilter, int page, int pageSize) {
        switch (timeFilter.toLowerCase()) {
            case "today":
                return getInvoicesWithPaging(getInvoicesToday(), page, pageSize);
            case "yesterday":
                return getInvoicesWithPaging(getInvoicesYesterday(), page, pageSize);
            case "this_week":
                return getInvoicesWithPaging(getInvoicesThisWeek(), page, pageSize);
            case "last_week":
                return getInvoicesWithPaging(getInvoicesLastWeek(), page, pageSize);
            case "7_days":
                return getInvoicesWithPaging(getInvoicesLast7Days(), page, pageSize);
            case "this_month":
                return getInvoicesWithPaging(getInvoicesThisMonth(), page, pageSize);
            case "last_month":
                return getInvoicesWithPaging(getInvoicesLastMonth(), page, pageSize);
            case "30_days":
                return getInvoicesWithPaging(getInvoicesLast30Days(), page, pageSize);
            case "this_quarter":
                return getInvoicesWithPaging(getInvoicesThisQuarter(), page, pageSize);
            case "last_quarter":
                return getInvoicesWithPaging(getInvoicesLastQuarter(), page, pageSize);
            case "this_year":
                return getInvoicesWithPaging(getInvoicesThisYear(), page, pageSize);
            case "last_year":
                return getInvoicesWithPaging(getInvoicesLastYear(), page, pageSize);
            default:
                return getInvoicesWithPaging(getInvoicesToday(), page, pageSize);
        }
    }

    /**
     * Helper method to apply pagination to a list
     */
    private List<Invoices> getInvoicesWithPaging(List<Invoices> allInvoices, int page, int pageSize) {
        if (page <= 0 || pageSize <= 0) {
            return allInvoices;
        }

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allInvoices.size());

        if (startIndex >= allInvoices.size()) {
            return new ArrayList<>();
        }

        return allInvoices.subList(startIndex, endIndex);
    }

    /**
     * Helper method to map ResultSet to Invoice object
     */
    private Invoices mapResultSetToInvoice(ResultSet rs) throws Exception {
        int id = rs.getInt(1);
        String code = rs.getString(2);
        Date invoiceDate = rs.getTimestamp(3);
        Date updateDate = rs.getTimestamp(4);
        int storeId = rs.getInt(5);
        int userId = rs.getInt(6);
        int createdBy = rs.getInt(7);
        int customerId = rs.getInt(8);
        double totalAmount = rs.getDouble(9);
        double subTotal = rs.getDouble(10);
        double discountPercent = rs.getDouble(11);
        double discountAmount = rs.getDouble(12);
        double VATPercent = rs.getDouble(13);
        double VATAmount = rs.getDouble(14);
        double paidAmount = rs.getDouble(15);
        int paymentId = rs.getInt(16);
        String status = rs.getString(17);

        return new Invoices(id, code, invoiceDate, updateDate, storeId, userId, createdBy,
                customerId, totalAmount, subTotal, discountPercent, discountAmount, VATPercent, VATAmount, paidAmount, paymentId, status);
    }

    public boolean insertInvoice(Invoices invoice) {
        String sql = "INSERT INTO Invoices ("
                + "InvoiceDate, UpdateDate, StoreID, SaleID, CreatedBy, CustomerID, "
                + "TotalAmount, SubTotal, DiscountPercent, DiscountAmount, "
                + "VATPercent, VATAmount, PaidAmount, PaymentMethodID, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            stm = connection.prepareStatement(sql);

            // Set parameters
            stm.setTimestamp(1, new java.sql.Timestamp(invoice.getInvoiceDate().getTime()));
            stm.setTimestamp(2, new java.sql.Timestamp(invoice.getUpdateDate().getTime()));

            stm.setInt(3, invoice.getStoreId());
            stm.setInt(4, invoice.getUserId()); // SaleID
            stm.setInt(5, invoice.getCreatedBy());

            stm.setInt(6, invoice.getCustomerId());

            stm.setDouble(7, invoice.getTotalAmount());
            stm.setDouble(8, invoice.getSubTotal());
            stm.setDouble(9, invoice.getDiscount());
            stm.setDouble(10, invoice.getDiscountAmount());
            stm.setDouble(11, invoice.getVATPercent());
            stm.setDouble(12, invoice.getVATAmount());
            stm.setDouble(13, invoice.getPaidAmount());
            stm.setInt(14, invoice.getPaymentMethodId());
            stm.setString(15, invoice.getStatus());

            int result = stm.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.err.println("Error inserting invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSoldBy(int invoiceId, int soldById) {
        String sql = "UPDATE Invoices SET SaleID = ?, UpdateDate = GETDATE() WHERE InvoiceID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, soldById);
            stm.setInt(2, invoiceId);

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Error updating sold by for invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
