/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.util.Date;

/**
 *
 * @author tungd
 */

public class Suppliers {
    int id;
    String supplierCode;
    String supplierName;
    String contactPerson;
    String phone;
    String email;
    String address;
    String description;
    Date createdAt;

    public Suppliers(int id,String supplierCode, String supplierName, String contactPerson, String phone, String email, String address, String description, Date createdAt) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Suppliers(int id, String supplierName, String contactPerson, String phone, String email, String address, String description, Date createdAt) {
        this.id = id;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.createdAt = createdAt;
    }


    // Getters and Setters
    public int getSupplierID() {
        return id;
    }

    public void setSupplierID(int supplierID) {
        this.id = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
   
}
