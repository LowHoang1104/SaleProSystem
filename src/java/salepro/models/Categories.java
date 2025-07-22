/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.TypeDAO;

/**
 *
 * @author MY PC
 */
public class Categories {
    private int categoryID;
    private String categoryName;
    private int typeID;

    public Categories() {
    }

    
    public Categories(int categoryID, String categoryName, int typeID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.typeID = typeID;
    }
    
    
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
    public String getTypeNameById(){
        TypeDAO cdao = new TypeDAO();
        return cdao.getNameByID(this.typeID);
    }
}
