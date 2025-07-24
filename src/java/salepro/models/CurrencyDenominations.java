/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class CurrencyDenominations {
    int denominationID;
    double value;
    String displayName;
    int sortOrder;
    boolean status;

    public CurrencyDenominations() {
    }

    public CurrencyDenominations(int denominationID, double value, String displayName, int sortOrder, boolean status) {
        this.denominationID = denominationID;
        this.value = value;
        this.displayName = displayName;
        this.sortOrder = sortOrder;
        this.status = status;
    }

    public int getDenominationID() {
        return denominationID;
    }

    public void setDenominationID(int denominationID) {
        this.denominationID = denominationID;
    }

    

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
