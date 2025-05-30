/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Sizes {
    private int sizeID;
    private String sizeName;

    public Sizes() {
    }

    public Sizes(int sizeID, String sizeName) {
        this.sizeID = sizeID;
        this.sizeName = sizeName;
    }
    
    public int getSizeID() {
        return sizeID;
    }
    
    public void setSizeID(int sizeID) {
        this.sizeID = sizeID;
    }
    
    public String getSizeName() {
        return sizeName;
    }
    
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
