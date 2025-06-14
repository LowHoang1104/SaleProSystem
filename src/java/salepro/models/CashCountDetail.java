/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class CashCountDetail {

    private int countDetailID;
    private int sessionID;
    private int denominationID;
    private int quantity;
    private double amount;

    // Constructors
    public CashCountDetail() {
        
    }

    public CashCountDetail(int sessionID, int denominationID, int quantity, double amount) {
        this.sessionID = sessionID;
        this.denominationID = denominationID;
        this.quantity = quantity;
        this.amount = amount;
    }

    // Getters and Setters
    public int getCountDetailID() {
        return countDetailID;
    }

    public void setCountDetailID(int countDetailID) {
        this.countDetailID = countDetailID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getDenominationID() {
        return denominationID;
    }

    public void setDenominationID(int denominationID) {
        this.denominationID = denominationID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

