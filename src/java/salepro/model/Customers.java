/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class Customers {
    private int CustomerID;
    private String FullName;
    private String Phone;
    private String Email;
    private String Gender;
    private LocalDate BirthDate;
    private double TotalSpent;
    private LocalDate CreatedAt;

    public Customers(int CustomerID, String FullName, String Phone, String Email, String Gender, LocalDate BirthDate, double TotalSpent, LocalDate CreatedAt) {
        this.CustomerID = CustomerID;
        this.FullName = FullName;
        this.Phone = Phone;
        this.Email = Email;
        this.Gender = Gender;
        this.BirthDate = BirthDate;
        this.TotalSpent = TotalSpent;
        this.CreatedAt = CreatedAt;
    }

    public Customers() {
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate BirthDate) {
        this.BirthDate = BirthDate;
    }

    public double getTotalSpent() {
        return TotalSpent;
    }

    public void setTotalSpent(double TotalSpent) {
        this.TotalSpent = TotalSpent;
    }

    public LocalDate getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDate CreatedAt) {
        this.CreatedAt = CreatedAt;
    }
    
    
    
}
