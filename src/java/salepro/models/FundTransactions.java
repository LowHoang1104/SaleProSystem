/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;
import salepro.dao.StoreFundDAO;

/**
 *
 * @author MY PC
 */
public class FundTransactions {

    private int transactionID;
    private int fundID;
    private String transactionType;
    private double amount;
    private String description;
    private String referenceType;
    private Integer referenceID;
    private Date transactionDate;
    private int createdBy;
    private Integer approvedBy;
    private String status;
    private String notes;

    public FundTransactions() {
        this.status = "Pending";
    }


    public FundTransactions(int transactionID, int fundID, String transactionType, double amount, String description, String referenceType, Integer referenceID, Date transactionDate, int createdBy, Integer approvedBy, String status, String notes) {
        this.transactionID = transactionID;
        this.fundID = fundID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.referenceType = referenceType;
        this.referenceID = referenceID;
        this.transactionDate = transactionDate;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.status = status;
        this.notes = notes;
    }

    public FundTransactions(int fundID, String transactionType, double amount, String description, int createdBy) {
        this();
        this.fundID = fundID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdBy = createdBy;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getFundID() {
        return fundID;
    }

    public void setFundID(int fundID) {
        this.fundID = fundID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public StoreFund getStoreFundbyFundID(){
        StoreFundDAO da= new StoreFundDAO();
        return da.getFundById(fundID);
    }

}
