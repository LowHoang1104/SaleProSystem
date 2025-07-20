package salepro.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext;
import salepro.models.ProductReportModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<ProductReportModel> getData() {
        ArrayList<ProductReportModel> list = new ArrayList<>();
        String sql = "SELECT "
                + "    C.CategoryName, "
                + "    SUM(I.Quantity) AS TotalQuantity, "
                + "    SUM(I.Quantity * PM.Price) AS StockValue, "
                + "    CAST(100.0 * SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0) AS DECIMAL(5,2)) AS PercentBelowMin, "
                + "    SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) AS CountBelowMin, "
                + "    SUM(CASE WHEN I.Quantity >= PV.AverageQuantity THEN 1 ELSE 0 END) AS CountAboveMin "
                + "FROM Inventory I "
                + "JOIN ProductVariants PV ON I.ProductVariantID = PV.ProductVariantID "
                + "JOIN ProductMaster PM ON PV.ProductCode = PM.ProductCode "
                + "JOIN Categories C ON PM.CategoryID = C.CategoryID "
                + "GROUP BY C.CategoryName "
                + "ORDER BY C.CategoryName";

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                ProductReportModel pr = new ProductReportModel();
                pr.setName(rs.getString("CategoryName"));
                pr.setQuantity(String.valueOf(rs.getInt("TotalQuantity")));
                pr.setTotalmoney(String.valueOf(rs.getBigDecimal("StockValue")));
                pr.setPercentbelowmin(String.valueOf(rs.getBigDecimal("PercentBelowMin")));
                pr.setNumberbelowmin(String.valueOf(rs.getInt("CountBelowMin")));
                pr.setNumbergreatermin(String.valueOf(rs.getInt("CountAboveMin")));
                list.add(pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<ProductReportModel> getDataByDateRange(String rangeKey) {
        ArrayList<ProductReportModel> list = new ArrayList<>();
        try {
            //Ngày mở shop cố định để test
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            //Trường hợp chính xác ngày mở shop
            //LocalDate startDate = getShopOpeningDate();
            LocalDate endDate = LocalDate.now();
            System.out.println("Start: " + startDate);
            System.out.println("End: " + endDate);

            switch (rangeKey) {
                case "today":
                    // endDate là hôm nay, startDate là ngày mở shop
                    break;

                case "yesterday":
                    endDate = endDate.minusDays(1);
                    break;

                case "thisWeek":
                    startDate = endDate.with(java.time.DayOfWeek.MONDAY);
                    break;

                case "lastWeek":
                    endDate = endDate.with(java.time.DayOfWeek.MONDAY).minusDays(1);
                    startDate = endDate.with(java.time.DayOfWeek.MONDAY);
                    break;

                case "last7Days":
                    startDate = endDate.minusDays(6);
                    break;

                case "thisMonth":
                    startDate = endDate.withDayOfMonth(1);
                    break;

                case "lastMonth":
                    endDate = endDate.minusMonths(1).withDayOfMonth(endDate.minusMonths(1).lengthOfMonth());
                    startDate = endDate.withDayOfMonth(1);
                    break;

                case "last30Days":
                    startDate = endDate.minusDays(29);
                    break;

                case "thisQuarter":
                    int currentMonth = endDate.getMonthValue();
                    int startMonth = ((currentMonth - 1) / 3) * 3 + 1;
                    startDate = LocalDate.of(endDate.getYear(), startMonth, 1);
                    break;

                case "lastQuarter":
                    int quarter = ((endDate.getMonthValue() - 1) / 3);
                    int lastQuarterMonth = quarter * 3 - 2;
                    int year = endDate.getYear();
                    if (lastQuarterMonth <= 0) {
                        lastQuarterMonth += 12;
                        year--;
                    }
                    startDate = LocalDate.of(year, lastQuarterMonth, 1);
                    endDate = startDate.plusMonths(2).withDayOfMonth(startDate.plusMonths(2).lengthOfMonth());
                    break;

                case "thisYear":
                    startDate = LocalDate.of(endDate.getYear(), 1, 1);
                    break;

                case "lastYear":
                    startDate = LocalDate.of(endDate.getYear() - 1, 1, 1);
                    endDate = LocalDate.of(endDate.getYear() - 1, 12, 31);
                    break;

                default:
                    return getData();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String sql = "SELECT C.CategoryName, "
                    + "SUM(I.Quantity) AS Quantity, "
                    + "SUM(I.Quantity * PM.Price) AS TotalValue, "
                    + "CAST(100.0 * SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0) AS DECIMAL(5,2)) AS PercentBelowMin, "
                    + "SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) AS BelowMin, "
                    + "SUM(CASE WHEN I.Quantity >= PV.AverageQuantity THEN 1 ELSE 0 END) AS AboveMin "
                    + "FROM Inventory I "
                    + "JOIN ProductVariants PV ON I.ProductVariantID = PV.ProductVariantID "
                    + "JOIN ProductMaster PM ON PV.ProductCode = PM.ProductCode "
                    + "JOIN Categories C ON PM.CategoryID = C.CategoryID "
                    + "WHERE (EXISTS (SELECT 1 FROM InvoiceDetails id JOIN Invoices inv ON id.InvoiceID = inv.InvoiceID "
                    + "WHERE id.ProductVariantID = I.ProductVariantID AND inv.InvoiceDate BETWEEN ? AND ?) "
                    + "OR EXISTS (SELECT 1 FROM PurchaseDetails pd JOIN Purchases p ON pd.PurchaseID = p.PurchaseID "
                    + "WHERE pd.ProductVariantID = I.ProductVariantID AND p.PurchaseDate BETWEEN ? AND ?)) "
                    + "GROUP BY C.CategoryName";

            stm = connection.prepareStatement(sql);
            stm.setString(1, startDate.format(formatter));
            stm.setString(2, endDate.format(formatter));
            stm.setString(3, startDate.format(formatter));
            stm.setString(4, endDate.format(formatter));

            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductReportModel(
                        rs.getString("CategoryName"),
                        String.valueOf(rs.getInt("Quantity")),
                        String.valueOf(rs.getBigDecimal("TotalValue")),
                        String.valueOf(rs.getBigDecimal("PercentBelowMin")),
                        String.valueOf(rs.getInt("AboveMin")),
                        String.valueOf(rs.getInt("BelowMin"))
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private LocalDate getShopOpeningDate() {
        try {
            String sql = "SELECT TOP 1 CreatedAt FROM Users WHERE RoleID = 1 ORDER BY CreatedAt ASC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("CreatedAt").toLocalDateTime().toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Trường hợp không có dữ liệu, mặc định trả về ngày 5 năm trước
        return LocalDate.now().minusYears(5);
    }

    public ArrayList<ProductReportModel> getDataByCustomFilter(String startDate, String endDate, double minPercentBelowMin, double minStockValue) {
        ArrayList<ProductReportModel> list = new ArrayList<>();
        try {
            String sql = "SELECT C.CategoryName, "
                    + "SUM(I.Quantity) AS Quantity, "
                    + "SUM(I.Quantity * PM.Price) AS TotalValue, "
                    + "CAST(100.0 * SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0) AS DECIMAL(5,2)) AS PercentBelowMin, "
                    + "SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) AS BelowMin, "
                    + "SUM(CASE WHEN I.Quantity >= PV.AverageQuantity THEN 1 ELSE 0 END) AS AboveMin "
                    + "FROM Inventory I "
                    + "JOIN ProductVariants PV ON I.ProductVariantID = PV.ProductVariantID "
                    + "JOIN ProductMaster PM ON PV.ProductCode = PM.ProductCode "
                    + "JOIN Categories C ON PM.CategoryID = C.CategoryID "
                    + "WHERE (EXISTS (SELECT 1 FROM InvoiceDetails id JOIN Invoices inv ON id.InvoiceID = inv.InvoiceID "
                    + "WHERE id.ProductVariantID = I.ProductVariantID AND inv.InvoiceDate BETWEEN ? AND ?) "
                    + "OR EXISTS (SELECT 1 FROM PurchaseDetails pd JOIN Purchases p ON pd.PurchaseID = p.PurchaseID "
                    + "WHERE pd.ProductVariantID = I.ProductVariantID AND p.PurchaseDate BETWEEN ? AND ?)) "
                    + "GROUP BY C.CategoryName "
                    + "HAVING CAST(100.0 * SUM(CASE WHEN I.Quantity < PV.AverageQuantity THEN 1 ELSE 0 END) / NULLIF(COUNT(*), 0) AS DECIMAL(5,2)) >= ? "
                    + "AND SUM(I.Quantity * PM.Price) >= ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            stm.setString(3, startDate);
            stm.setString(4, endDate);
            stm.setDouble(5, minPercentBelowMin);
            stm.setDouble(6, minStockValue);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductReportModel(
                        rs.getString("CategoryName"),
                        String.valueOf(rs.getInt("Quantity")),
                        String.valueOf(rs.getBigDecimal("TotalValue")),
                        String.valueOf(rs.getBigDecimal("PercentBelowMin")),
                        String.valueOf(rs.getInt("AboveMin")),
                        String.valueOf(rs.getInt("BelowMin"))
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
