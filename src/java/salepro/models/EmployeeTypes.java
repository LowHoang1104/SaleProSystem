/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import salepro.dao.EmployeeTypeDAO;

/**
 *
 * @author MY PC
 */
public class EmployeeTypes {
    private int employeeTypeID;
    private String typeName;

    public EmployeeTypes() {
    }

    public EmployeeTypes(int employeeTypeID, String typeName) {
        this.employeeTypeID = employeeTypeID;
        this.typeName = typeName;
    }
    
    public int getEmployeeTypeID() {
        return employeeTypeID;
    }
    
    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
        
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public int countEmp(){
        return new EmployeeTypeDAO().countEmpByEmpTypeId(employeeTypeID);
    }
}
