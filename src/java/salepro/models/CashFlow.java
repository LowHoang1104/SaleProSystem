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
public class CashFlow {
    private int cashFlowID;
    private int storeID;
    private String flowType;
    private double amount;
    private String description;
    private Date createdAt;

    public CashFlow() {
    }

    public CashFlow(int cashFlowID, int storeID, String flowType, double amount, String description, Date createdAt) {
        this.cashFlowID = cashFlowID;
        this.storeID = storeID;
        this.flowType = flowType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }
    
    public int getCashFlowID() {
        return cashFlowID;
    }
    
    public void setCashFlowID(int cashFlowID) {
        this.cashFlowID = cashFlowID;
    }
    
    public int getStoreID() {
        return storeID;
    }
    
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    
    public String getFlowType() {
        return flowType;
    }
    
    public void setFlowType(String flowType) {
        this.flowType = flowType;
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
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
