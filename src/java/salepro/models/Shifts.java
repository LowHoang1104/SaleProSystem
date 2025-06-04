/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.sql.Time;
import java.text.SimpleDateFormat;
import salepro.dao.StoreDAO;

/**
 *
 * @author Thinhnt
 */
public class Shifts {

    private int shiftID;
    private String shiftName;
    private Time startTime;
    private Time endTime;
    private int storeID;
    private boolean isActive;

    public Shifts() {
    }

    public Shifts(int shiftID, String shiftName, Time startTime, Time endTime, int storeID, boolean isActive) {
        this.shiftID = shiftID;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.storeID = storeID;
        this.isActive = isActive;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStartTimeFormatted() {
        return new SimpleDateFormat("HH:mm").format(startTime);
    }

    public String getEndTimeFormatted() {
        return new SimpleDateFormat("HH:mm").format(endTime);
    }
    
    public String getTotalTimeFormatted(){
        long millionseconds = endTime.getTime() - startTime.getTime();
        long totalMinute = millionseconds / (1000 * 60);
        long hours = totalMinute / 60;
        long minutes = totalMinute % 60;
        return hours + " giờ " + (minutes > 0 ? minutes + " phút " : "");      
    }
    
    public String getStoreName(){
        return new StoreDAO().getStoreNameByID(storeID);
    }

}
