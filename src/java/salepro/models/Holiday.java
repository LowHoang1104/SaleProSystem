/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.time.LocalDate;

/**
 *
 * @author Thinhnt
 */
public class Holiday {
    private int holidayId;
    private LocalDate holidayDate;
    private String name;
    private boolean isPublish;

    public Holiday(int holidayId, LocalDate holidayDate, String name, boolean isPublish) {
        this.holidayId = holidayId;
        this.holidayDate = holidayDate;
        this.name = name;
        this.isPublish = isPublish;
    }

    public Holiday(LocalDate holidayDate, String name, boolean isPublish) {
        this.holidayDate = holidayDate;
        this.name = name;
        this.isPublish = isPublish;
    }
    

    public int getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(int holidayId) {
        this.holidayId = holidayId;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsPublish() {
        return isPublish;
    }

    public void setIsPublish(boolean isPublish) {
        this.isPublish = isPublish;
    }
    
    
}
