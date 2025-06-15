/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author tungd
 */
import java.util.Date;

public class StockTake {

    private int stockTakeID;
    private int warehouseID;
    private Date checkDate;
    private int checkedBy;
    private String note;

    public StockTake() {
    }

    public StockTake(int stockTakeID, int warehouseID, Date checkDate, int checkedBy, String note) {
        this.stockTakeID = stockTakeID;
        this.warehouseID = warehouseID;
        this.checkDate = checkDate;
        this.checkedBy = checkedBy;
        this.note = note;
    }

    public int getStockTakeID() {
        return stockTakeID;
    }

    public void setStockTakeID(int stockTakeID) {
        this.stockTakeID = stockTakeID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public int getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(int checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
