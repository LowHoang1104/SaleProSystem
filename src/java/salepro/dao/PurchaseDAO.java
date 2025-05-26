/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.models.Employees;
import salepro.models.Purchases;

/**
 *
 * @author ADMIN
 */
public class PurchaseDAO extends DBContext2{
     PreparedStatement stm;
    ResultSet rs;
    
    public ArrayList<Purchases> getData(){
        ArrayList<Purchases> data= new ArrayList<>();
        try {
            String strSQL = "select * from Purchases";
            stm = connection.prepareStatement(strSQL);           
            rs = stm.executeQuery();
            while (rs.next()) {
                Purchases temp= new Purchases(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getDouble(5));
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }
}
