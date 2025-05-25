/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.model;

/**
 *
 * @author tungd
 */
public class Colors {
    private int colorID;
    private String colorName;

    // Constructors
    public Colors() {}

    public Colors(int colorID, String colorName) {
        this.colorID = colorID;
        this.colorName = colorName;
    }

    // Getters and Setters
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

