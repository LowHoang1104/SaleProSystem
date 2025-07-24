/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext;
import salepro.models.EmployeeReportModel;

/**
 *
 * @author Thinhnt
 */
public class ReportEmployeeDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public ArrayList<EmployeeReportModel> getReport() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
     SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số lượng hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng số tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportYesterday() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                CAST(i.InvoiceDate AS DATE) = CAST(DATEADD(DAY, -1, GETDATE()) AS DATE)
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");
                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLast7Days() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                i.InvoiceDate >= DATEADD(DAY, -7, GETDATE()) -- 7 ngày gần nhất
                AND i.Status = 'Completed' -- nếu cần lọc hóa đơn đã hoàn tất
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số lượng hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng số tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportThisMonth() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                MONTH(i.InvoiceDate) = MONTH(GETDATE()) AND
                YEAR(i.InvoiceDate) = YEAR(GETDATE()) AND
                i.Status = 'Completed'
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số lượng hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng số tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLastYear() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                YEAR(i.InvoiceDate) = YEAR(GETDATE()) - 1
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");
                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số lượng hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng số tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportThisWeek() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                DATEPART(ISO_WEEK, i.InvoiceDate) = DATEPART(ISO_WEEK, GETDATE()) AND
                YEAR(i.InvoiceDate) = YEAR(GETDATE())
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLastWeek() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                DATEPART(ISO_WEEK, i.InvoiceDate) = DATEPART(ISO_WEEK, DATEADD(WEEK, -1, GETDATE()))
                AND YEAR(i.InvoiceDate) = YEAR(DATEADD(WEEK, -1, GETDATE()))
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLastMonth() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                MONTH(i.InvoiceDate) = MONTH(DATEADD(MONTH, -1, GETDATE())) AND
                YEAR(i.InvoiceDate) = YEAR(DATEADD(MONTH, -1, GETDATE()))
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice")); // số lượng hóa đơn
                String amount = rs.getBigDecimal("TotalAmount").toPlainString(); // tổng số tiền

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLast30Days() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                i.InvoiceDate >= DATEADD(DAY, -30, GETDATE())
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportThisQuarter() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                DATEPART(QUARTER, i.InvoiceDate) = DATEPART(QUARTER, GETDATE()) AND
                YEAR(i.InvoiceDate) = YEAR(GETDATE())
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportLastQuarter() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            WITH DateRanges AS (
                SELECT 
                    DATEADD(QUARTER, DATEDIFF(QUARTER, 0, GETDATE()) - 1, 0) AS StartDate,
                    DATEADD(DAY, -1, DATEADD(QUARTER, DATEDIFF(QUARTER, 0, GETDATE()), 0)) AS EndDate
            )
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            CROSS JOIN 
                DateRanges
            WHERE 
                i.InvoiceDate >= DateRanges.StartDate AND i.InvoiceDate <= DateRanges.EndDate
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportThisYear() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                YEAR(i.InvoiceDate) = YEAR(GETDATE())
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");

                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportByKeyword(String keyword) {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                e.EmployeeID LIKE ? OR e.FullName LIKE ? OR e.Phone LIKE ?
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            String kw = "%" + keyword + "%";
            stm.setString(1, kw);
            stm.setString(2, kw);
            stm.setString(3, kw);

            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");
                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount").toPlainString();

                list.add(new EmployeeReportModel(id, name, amount, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<EmployeeReportModel> getReportToday() {
        ArrayList<EmployeeReportModel> list = new ArrayList<>();

        try {
            String sql = """
            SELECT 
                e.EmployeeID, 
                e.FullName, 
                COUNT(i.InvoiceID) AS TotalInvoice, 
                SUM(i.TotalAmount) AS TotalAmount
            FROM 
                [Shop].[dbo].[Employees] e
            JOIN 
                [Shop].[dbo].[Invoices] i ON e.EmployeeID = i.SaleID
            WHERE 
                CAST(i.InvoiceDate AS DATE) = CAST(GETDATE() AS DATE)
            GROUP BY 
                e.EmployeeID, e.FullName
            ORDER BY 
                TotalAmount DESC;
        """;

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("EmployeeID"));
                String name = rs.getString("FullName");
                String quantity = String.valueOf(rs.getInt("TotalInvoice"));
                String amount = rs.getBigDecimal("TotalAmount") != null
                        ? rs.getBigDecimal("TotalAmount").toPlainString()
                        : "0";

                EmployeeReportModel model = new EmployeeReportModel(id, name, amount, quantity);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    

}
