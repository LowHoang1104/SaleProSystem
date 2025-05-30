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
public class Customers {


    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String description; 
    private String rank;        
    private String gender;      
    private Date birthDate;
    private double totalSpent;
    private Date createdAt;

    public Customers() {
    }


    public Customers(int customerId, String fullName, String phone, String email, String address, String description, String rank, String gender, Date birthDate, double totalSpent, Date createdAt) {
        this.customerId = customerId;

        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;


        this.description = description;

        this.rank = rank;
        this.gender = gender;
        this.birthDate = birthDate;
        this.totalSpent = totalSpent;
        this.createdAt = createdAt;
    }

    // getters & setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
