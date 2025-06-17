/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.models.CashCountDetail;
import salepro.models.CashCountSession;
import salepro.models.CurrencyDenominations;

public class CashCountSessionDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_SESSIONID_MAX_BY_FUND_ID = "SELECT TOP 1 SessionID FROM CashCountSessions WHERE FundID = ? \n"
            + "AND Status = 'Approved' ORDER BY CountDate DESC";
    private static final String GET_CASH_COUNT_DETAILS_BY_SESSION_ID = "select * from CashCountDetails where SessionID = ?";
    private static final String GET_DENOMINATIONS_DATA = "select * FROM CurrencyDenominations";
    private static final String INSERT_CASH_COUNT_SESSION = "INSERT INTO CashCountSessions (FundID, SessionType, CountDate, CountedBy, TotalCounted, SystemBalance, Status, Notes, ApprovedBy,ApprovedAt) VALUES\n"
            + "   (?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_CASH_COUNT_DETAILS = "INSERT INTO CashCountDetails (SessionID, DenominationID, Quantity, Amount) VALUES"
            + "(?,?,?,?)";

    public int getSessionIdMaxWithFundId(int fundId) {
        try {
            stm = connection.prepareStatement(GET_SESSIONID_MAX_BY_FUND_ID);
            stm.setInt(1, fundId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int sessionId = rs.getInt(1);
                return sessionId;
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<CashCountDetail> getCashCountDetailsBySessionId(int sessionId) {
        List<CashCountDetail> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_CASH_COUNT_DETAILS_BY_SESSION_ID);
            stm.setInt(1, sessionId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int countDetailId = rs.getInt(1);
                int denominationId = rs.getInt(3);
                int quantity = rs.getInt(4);
                double amount = rs.getInt(5);

                CashCountDetail cashCountDetail = new CashCountDetail(countDetailId, sessionId, denominationId, quantity, amount);
                data.add(cashCountDetail);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public List<CurrencyDenominations> getDenominationses() {
        List<CurrencyDenominations> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DENOMINATIONS_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int denominationId = rs.getInt(1);
                double value = rs.getDouble(2);
                String displayName = rs.getString(3);
                int sortOrder = rs.getInt(4);
                boolean status = rs.getBoolean(5);
                CurrencyDenominations currencyDenominations = new CurrencyDenominations(denominationId, value, displayName, sortOrder, status);
                data.add(currencyDenominations);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public boolean insertCountSession(int fundID, String sessionType, Date countDate, int countedBy, double totalCounted, double SystemBalance, String status, String notes, int approvedBy, Date approvedAt) {
        try {
            stm = connection.prepareStatement(INSERT_CASH_COUNT_SESSION);
            stm.setInt(1, fundID);
            stm.setString(2, sessionType);
            stm.setDate(3, new java.sql.Date(countDate.getTime()));
            stm.setInt(4, countedBy);
            stm.setDouble(5, totalCounted);
            stm.setDouble(6, SystemBalance);
            stm.setString(7, status);
            stm.setString(8, notes);
            stm.setInt(9, approvedBy);
            stm.setDate(10, new java.sql.Date(approvedAt.getTime()));
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertCashCountDetail(int sesionId, int denominationID, int quantity, double amount) {
        try {
            stm = connection.prepareStatement(INSERT_CASH_COUNT_DETAILS);
            stm.setInt(1, sesionId);
            stm.setInt(2, denominationID);
            stm.setInt(3, quantity);
            stm.setDouble(4, amount);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        CashCountSessionDAO ccDao = new CashCountSessionDAO();
        List<CurrencyDenominations> currencyDenominations = ccDao.getDenominationses();
        System.out.println(currencyDenominations.size());
    }
}
