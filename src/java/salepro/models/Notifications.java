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
public class Notifications {
    private int notificationID;
    private String title;
    private String message;
    private boolean isRead;
    private Date createdAt;

    public Notifications() {
    }

    public Notifications(int notificationID, String title, String message, boolean isRead, Date createdAt) {
        this.notificationID = notificationID;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
    
    
    public int getNotificationID() {
        return notificationID;
    }
    
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean getIsRead() {
        return isRead;
    }
    
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
