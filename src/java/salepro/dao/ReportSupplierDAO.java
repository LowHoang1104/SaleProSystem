/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext;
import salepro.models.SupplierReportModel;

/**
 *
 * @author tungd
 */
public class ReportSupplierDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public ArrayList<SupplierReportModel> getReport() {
        ArrayList<SupplierReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                s.SupplierID,
                s.SupplierName,
                SUM(pd.Quantity) AS TotalQuantity,
                SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM 
                Suppliers s
            JOIN 
                Purchases p ON s.SupplierID = p.SupplierID
            JOIN 
                PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            GROUP BY 
                s.SupplierID, s.SupplierName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");

                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                SupplierReportModel model = new SupplierReportModel(id, name, amount, quantity);
                list.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<SupplierReportModel> getReportToday() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = """
            SELECT s.SupplierID, s.SupplierName,
                   SUM(pd.Quantity) AS TotalQuantity,
                   SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM Suppliers s
            JOIN Purchases p ON s.SupplierID = p.SupplierID
            JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            WHERE CONVERT(DATE, p.PurchaseDate) = CONVERT(DATE, GETDATE())
            GROUP BY s.SupplierID, s.SupplierName
            ORDER BY TotalAmount DESC
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");
                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();
                data.add(new SupplierReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportYesterday() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = """
            SELECT s.SupplierID, s.SupplierName,
                   SUM(pd.Quantity) AS TotalQuantity,
                   SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM Suppliers s
            JOIN Purchases p ON s.SupplierID = p.SupplierID
            JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            WHERE CONVERT(DATE, p.PurchaseDate) = CONVERT(DATE, DATEADD(DAY, -1, GETDATE()))
            GROUP BY s.SupplierID, s.SupplierName
            ORDER BY TotalAmount DESC
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");
                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();
                data.add(new SupplierReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLast7Days() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = """
            SELECT s.SupplierID, s.SupplierName,
                   SUM(pd.Quantity) AS TotalQuantity,
                   SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM Suppliers s
            JOIN Purchases p ON s.SupplierID = p.SupplierID
            JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            WHERE p.PurchaseDate >= DATEADD(DAY, -7, GETDATE())
            GROUP BY s.SupplierID, s.SupplierName
            ORDER BY TotalAmount DESC
        """;
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");
                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();
                data.add(new SupplierReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportThisMonth() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = """
            SELECT s.SupplierID, s.SupplierName,
                   SUM(pd.Quantity) AS TotalQuantity,
                   SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM Suppliers s
            JOIN Purchases p ON s.SupplierID = p.SupplierID
            JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            WHERE MONTH(p.PurchaseDate) = MONTH(GETDATE()) AND YEAR(p.PurchaseDate) = YEAR(GETDATE())
            GROUP BY s.SupplierID, s.SupplierName
            ORDER BY TotalAmount DESC
        """;
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");
                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();
                data.add(new SupplierReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLastYear() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = """
            SELECT s.SupplierID, s.SupplierName,
                   SUM(pd.Quantity) AS TotalQuantity,
                   SUM(pd.Quantity * pd.CostPrice) AS TotalAmount
            FROM Suppliers s
            JOIN Purchases p ON s.SupplierID = p.SupplierID
            JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID
            WHERE YEAR(p.PurchaseDate) = YEAR(GETDATE()) - 1
            GROUP BY s.SupplierID, s.SupplierName
            ORDER BY TotalAmount DESC
        """;
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("SupplierID"));
                String name = rs.getString("SupplierName");
                String quantity = String.valueOf(rs.getInt("TotalQuantity"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();
                data.add(new SupplierReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportThisWeek() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "    s.SupplierID, "
                    + "    s.SupplierName, "
                    + "    SUM(pd.Quantity) AS TotalQuantity, "
                    + "    SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE DATEPART(ISO_WEEK, p.PurchaseDate) = DATEPART(ISO_WEEK, GETDATE()) "
                    + "  AND YEAR(p.PurchaseDate) = YEAR(GETDATE()) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLastWeek() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE DATEPART(ISO_WEEK, p.PurchaseDate) = DATEPART(ISO_WEEK, DATEADD(WEEK, -1, GETDATE())) "
                    + "AND YEAR(p.PurchaseDate) = YEAR(DATEADD(WEEK, -1, GETDATE())) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLastMonth() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE MONTH(p.PurchaseDate) = MONTH(DATEADD(MONTH, -1, GETDATE())) "
                    + "AND YEAR(p.PurchaseDate) = YEAR(DATEADD(MONTH, -1, GETDATE())) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLast30Days() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE p.PurchaseDate >= DATEADD(DAY, -30, GETDATE()) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportThisQuarter() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE DATEPART(QUARTER, p.PurchaseDate) = DATEPART(QUARTER, GETDATE()) "
                    + "AND YEAR(p.PurchaseDate) = YEAR(GETDATE()) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportLastQuarter() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE DATEPART(QUARTER, p.PurchaseDate) = DATEPART(QUARTER, DATEADD(QUARTER, -1, GETDATE())) "
                    + "AND YEAR(p.PurchaseDate) = YEAR(DATEADD(QUARTER, -1, GETDATE())) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportThisYear() {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE YEAR(p.PurchaseDate) = YEAR(GETDATE()) "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<SupplierReportModel> getReportByKeyword(String keyword) {
        ArrayList<SupplierReportModel> data = new ArrayList<>();
        try {
            String sql = "SELECT s.SupplierID, s.SupplierName, "
                    + "SUM(pd.Quantity) AS TotalQuantity, "
                    + "SUM(pd.Quantity * pd.CostPrice) AS TotalAmount "
                    + "FROM Suppliers s "
                    + "JOIN Purchases p ON s.SupplierID = p.SupplierID "
                    + "JOIN PurchaseDetails pd ON p.PurchaseID = pd.PurchaseID "
                    + "WHERE s.SupplierCode LIKE ? OR s.SupplierName LIKE ? OR s.Phone LIKE ? "
                    + "GROUP BY s.SupplierID, s.SupplierName "
                    + "ORDER BY TotalAmount DESC";
            stm = connection.prepareStatement(sql);
            String kw = "%" + keyword + "%";
            stm.setString(1, kw);
            stm.setString(2, kw);
            stm.setString(3, kw);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("SupplierID") + "";
                String name = rs.getString("SupplierName");
                String product = rs.getInt("TotalQuantity") + "";
                String money = rs.getBigDecimal("TotalAmount") + "";
                data.add(new SupplierReportModel(id, name, money, product));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
