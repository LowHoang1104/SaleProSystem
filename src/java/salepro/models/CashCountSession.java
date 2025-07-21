/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;

/**
 *
 * @author MY PC
 */
public class CashCountSession {
    private int sessionID;
    private int fundID;
   private String sessionType;
    private Date countDate;   
    private int countedBy;
    private double totalCounted;
    private double systemBalance;
  private String status;
   private String notes;   
    private Integer approvedBy;
    private Date approvedAt;

    public CashCountSession() {
        this.status = "Draft";
    }

    public CashCountSession(int fundID, String sessionType, int countedBy) {
        this();
        this.fundID = fundID;
        this.sessionType = sessionType;
        this.countedBy = countedBy;
    }

    public double getDifference() {
        return totalCounted - systemBalance;
    }

    // Getters and Setters
    public int getSessionID() { return sessionID; }
    public void setSessionID(int sessionID) { this.sessionID = sessionID; }

    public int getFundID() { return fundID; }
    public void setFundID(int fundID) { this.fundID = fundID; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public Date getCountDate() { return countDate; }
    public void setCountDate(Date countDate) { this.countDate = countDate; }

    public int getCountedBy() { return countedBy; }
    public void setCountedBy(int countedBy) { this.countedBy = countedBy; }

    public double getTotalCounted() { return totalCounted; }
    public void setTotalCounted(double totalCounted) { this.totalCounted = totalCounted; }

    public double getSystemBalance() { return systemBalance; }
    public void setSystemBalance(double systemBalance) { this.systemBalance = systemBalance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Integer approvedBy) { this.approvedBy = approvedBy; }

    public Date getApprovedAt() { return approvedAt; }
    public void setApprovedAt(Date approvedAt) { this.approvedAt = approvedAt; }
}
