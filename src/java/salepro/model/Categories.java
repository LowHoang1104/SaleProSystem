/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

/**
 *
 * @author tungd
 */
public class Categories {
    private int categoryID;
    private String categoryName;
    private int typeID;

    // Constructors
    public Categories() {}

    public Categories(int categoryID, String categoryName, int typeID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.typeID = typeID;
    }

    // Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
}

