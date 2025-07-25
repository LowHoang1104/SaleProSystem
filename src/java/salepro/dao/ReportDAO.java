/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Purchases;

/**
 *
 * @author ADMIN
 */
public class ReportDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public String getIncomeByDate(String month, String year, int idStore, int FundID, String Funtype) {
        String strSQL = "select Sum(Amount) from FundTransactions a join StoreFunds b on a.FundID=b.FundID where TransactionType='Income' and a.Status = 'Approved'";
        if (!year.isEmpty()) {
            strSQL += " and YEAR(TransactionDate)=?";
        }
        if (!month.isEmpty()) {
            strSQL += " and Month(TransactionDate)=?";
        }
        if (idStore != 0) {
            strSQL += " and b.StoreID=?";
        }
        if (FundID != 0) {
            strSQL += " and b.FundID=?";
        }
        if (!Funtype.isEmpty()) {
            strSQL += " and b.FundType=?";
        }
        try {
            int i = 1;
            stm = connection.prepareStatement(strSQL);
            if (!year.isEmpty()) {
                stm.setString(i, year);
                i++;
            }
            if (!month.isEmpty()) {
                stm.setString(i, month);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (FundID != 0) {
                stm.setInt(i, FundID);
                i++;
            }
            if (!Funtype.isEmpty()) {
                stm.setString(i, Funtype);
                i++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                String result = rs.getString(1);
                return result != null ? result : "0";
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public ArrayList<Integer> getMonthByYear(String year) {
        ArrayList<Integer> months = new ArrayList<Integer>();
        String strSQL = "SELECT \n"
                + "    MONTH(TransactionDate) AS [month]\n"
                + "FROM \n"
                + "    FundTransactions\n"
                + "Where Year(TransactionDate)=?\n"
                + "GROUP BY \n"
                + "    MONTH(TransactionDate);";
        try {
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, year);
            rs = stm.executeQuery();
            while (rs.next()) {
                months.add(rs.getInt(1));
            }
        } catch (Exception e) {

        }
        return months;
    }

    public ArrayList<Integer> getYear() {
        ArrayList<Integer> years = new ArrayList<Integer>();
        String strSQL = "SELECT \n"
                + "    YEAR(TransactionDate) AS [year]\n"
                + "FROM \n"
                + "    FundTransactions\n"
                + "GROUP BY \n"
                + "    YEAR(TransactionDate);";
        try {
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                years.add(rs.getInt(1));
            }
        } catch (Exception e) {

        }
        return years;
    }

    public String getExpenseByDate(String month, String year, int idStore, int FundID, String Funtype) {
        String strSQL = "select Sum(Amount) from FundTransactions a join StoreFunds b on a.FundID=b.FundID where TransactionType='Expense' and a.Status = 'Approved'";
        if (!year.isEmpty()) {
            strSQL += " and YEAR(TransactionDate)=?";
        }
        if (!month.isEmpty()) {
            strSQL += " and Month(TransactionDate)=?";
        }
        if (idStore != 0) {
            strSQL += " and b.StoreID=?";
        }
        if (FundID != 0) {
            strSQL += " and b.FundID=?";
        }
        if (!Funtype.isEmpty()) {
            strSQL += " and b.FundType=?";
        }
        try {
            int i = 1;
            stm = connection.prepareStatement(strSQL);
            if (!year.isEmpty()) {
                stm.setString(i, year);
                i++;
            }
            if (!month.isEmpty()) {
                stm.setString(i, month);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (FundID != 0) {
                stm.setInt(i, FundID);
                i++;
            }
            if (!Funtype.isEmpty()) {
                stm.setString(i, Funtype);
                i++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                String result = rs.getString(1);
                return result != null ? result : "0";
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public String getTotalPurchase(String month, String year, int idStore, int FundID, String Funtype) {
        String strSQL = "select Sum(Amount) from FundTransactions a join StoreFunds b on a.FundID=b.FundID where TransactionType='Expense' and ReferenceType='Purchase' and a.Status = 'Approved' ";
        if (!year.isEmpty()) {
            strSQL += " and YEAR(TransactionDate)=?";
        }
        if (!month.isEmpty()) {
            strSQL += " and Month(TransactionDate)=?";
        }
        if (idStore != 0) {
            strSQL += " and b.StoreID=?";
        }
        if (FundID != 0) {
            strSQL += " and b.FundID=?";
        }
        if (!Funtype.isEmpty()) {
            strSQL += " and b.FundType=?";
        }
        try {
            int i = 1;
            stm = connection.prepareStatement(strSQL);
            if (!year.isEmpty()) {
                stm.setString(i, year);
                i++;
            }
            if (!month.isEmpty()) {
                stm.setString(i, month);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (FundID != 0) {
                stm.setInt(i, FundID);
                i++;
            }
            if (!Funtype.isEmpty()) {
                stm.setString(i, Funtype);
                i++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                String result = rs.getString(1);
                return result != null ? result : "0";
            }
        } catch (Exception e) {
        }
        return "0";
    }
    
      public String getTotalSalary(String month, String year, int idStore, int FundID, String Funtype) {
        String strSQL = "select Sum(Amount) from FundTransactions a join StoreFunds b on a.FundID=b.FundID where TransactionType='Expense' and ReferenceType='Salary' and a.Status = 'Approved'";
        if (!year.isEmpty()) {
            strSQL += " and YEAR(TransactionDate)=?";
        }
        if (!month.isEmpty()) {
            strSQL += " and Month(TransactionDate)=?";
        }
        if (idStore != 0) {
            strSQL += " and b.StoreID=?";
        }
        if (FundID != 0) {
            strSQL += " and b.FundID=?";
        }
        if (!Funtype.isEmpty()) {
            strSQL += " and b.FundType=?";
        }
        try {
            int i = 1;
            stm = connection.prepareStatement(strSQL);
            if (!year.isEmpty()) {
                stm.setString(i, year);
                i++;
            }
            if (!month.isEmpty()) {
                stm.setString(i, month);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (FundID != 0) {
                stm.setInt(i, FundID);
                i++;
            }
            if (!Funtype.isEmpty()) {
                stm.setString(i, Funtype);
                i++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                String result = rs.getString(1);
                return result != null ? result : "0";
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public String getTotalPurchaseOtherType(String month, String year, int idStore, int FundID, String Funtype) {
        String strSQL = "select Sum(Amount) from FundTransactions a join StoreFunds b on a.FundID=b.FundID where TransactionType='Expense' and ReferenceType is null and a.Status = 'Approved'";
        if (!year.isEmpty()) {
            strSQL += " and YEAR(TransactionDate)=?";
        }
        if (!month.isEmpty()) {
            strSQL += " and Month(TransactionDate)=?";
        }
        if (idStore != 0) {
            strSQL += " and b.StoreID=?";
        }
        if (FundID != 0) {
            strSQL += " and b.FundID=?";
        }
        if (!Funtype.isEmpty()) {
            strSQL += " and b.FundType=?";
        }
        try {
            int i = 1;
            stm = connection.prepareStatement(strSQL);
            if (!year.isEmpty()) {
                stm.setString(i, year);
                i++;
            }
            if (!month.isEmpty()) {
                stm.setString(i, month);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (FundID != 0) {
                stm.setInt(i, idStore);
                i++;
            }
            if (idStore != 0) {
                stm.setInt(i, FundID);
                i++;
            }
            if (!Funtype.isEmpty()) {
                stm.setString(i, Funtype);
                i++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                String result = rs.getString(1);
                return result != null ? result : "0";
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public static void main(String[] args) {
        String a= "/ListUserPermissionServlet";
        System.out.println(a.contains("ListUserPermissionServlet"));
    }
}
