/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author MY PC
 */
public class Colors {
    private int colorID;
    private String colorName;

    public Colors() {
    }

    public Colors(int colorID, String colorName) {
        this.colorID = colorID;
        this.colorName = colorName;
    }
    
    public int getColorID() {
        return colorID;
    }
    
    public void setColorID(int colorID) {
        this.colorID = colorID;
    }
    
    public String getColorName() {
        return colorName;
    }
    
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
