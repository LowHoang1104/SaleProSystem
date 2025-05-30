/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.function.Supplier;
import salepro.dal.DBContext2;
import salepro.models.Stores;
import salepro.models.Suppliers;

/**
 *
 * @author ADMIN
 */
public class SupplierDAO extends DBContext2{
     PreparedStatement stm;
    ResultSet rs;
    public ArrayList<Suppliers> getData() {
        ArrayList<Suppliers> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from Suppliers");
            rs = stm.executeQuery();
            while (rs.next()) {
               Suppliers temp= new Suppliers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8));
               data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }
}
