/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class ProductTypes {
    private int typeID;
    private String typeName;

    public ProductTypes() {
    }

    public ProductTypes(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }
    
    public int getTypeID() {
        return typeID;
    }
    
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
