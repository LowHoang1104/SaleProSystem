/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author Thinhnt
 */
public class CategoryPermissions {

    private int categoryPerId;
    private String categoryPerName;

    public CategoryPermissions() {
    }

    public CategoryPermissions(int categoryPerId, String categoryPerName) {
        this.categoryPerId = categoryPerId;
        this.categoryPerName = categoryPerName;
    }

    public int getCategoryPerId() {
        return categoryPerId;
    }

    public void setCategoryPerId(int categoryPerId) {
        this.categoryPerId = categoryPerId;
    }

    public String getCategoryPerName() {
        return categoryPerName;
    }

    public void setCategoryPerName(String categoryPerName) {
        this.categoryPerName = categoryPerName;
    }

    
    

}
