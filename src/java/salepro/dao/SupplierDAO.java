/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.models.Stores;
import salepro.models.Suppliers;

/**
 *
 * @author tungd
 */
public class SupplierDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Suppliers> getData() {
        ArrayList<Suppliers> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement("select * from [Suppliers]");
            rs = stm.executeQuery();
            while (rs.next()) {
                Suppliers s = new Suppliers(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9));
                data.add(s);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public String getNameById(int supplierID) {

        try {
            String strSQL = "select [SupplierName] from [Suppliers] where [SupplierID] = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, supplierID);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void add(Suppliers s) {
        try {
            String str = "INSERT INTO Suppliers (SupplierName, ContactPerson, Phone, Email, Address, Description, CreatedAt)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
            stm = connection.prepareStatement(str);
            stm.setString(1, s.getSupplierName());
            stm.setString(2, s.getContactPerson());
            stm.setString(3, s.getPhone());
            stm.setString(4, s.getEmail());
            stm.setString(5, s.getAddress());
            stm.setString(6, s.getDescription());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Suppliers s) {
        try {
            String str = "UPDATE Suppliers SET SupplierName = ?, ContactPerson = ?, Phone = ?, Email = ?, Address = ?, Description = ?\n"
                    + "WHERE SupplierID = ?";
            stm = connection.prepareStatement(str);
            stm.setString(1, s.getSupplierName());
            stm.setString(2, s.getContactPerson());
            stm.setString(3, s.getPhone());
            stm.setString(4, s.getEmail());
            stm.setString(5, s.getAddress());
            stm.setString(6, s.getDescription());
            stm.setInt(7, s.getSupplierID());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int supplierID) {
        try {
            String str = "DELETE FROM Suppliers WHERE SupplierID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, supplierID);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkExists(String name, String phone) {
        try {
            String str = "SELECT * FROM Suppliers WHERE SupplierName = ? AND Phone = ?";
            stm = connection.prepareStatement(str);
            stm.setString(1, name);
            stm.setString(2, phone);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isInPurchase(int supplierID) {
        try {
            String str = "SELECT TOP 1 * FROM Purchases WHERE SupplierID = ?";
            stm = connection.prepareStatement(str);
            stm.setInt(1, supplierID);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Suppliers> searchByName(String kw) {
    List<Suppliers> list = new ArrayList<>();
    try {
        String str = "SELECT * FROM Suppliers WHERE SupplierName LIKE ?";
        stm = connection.prepareStatement(str);
        stm.setString(1, "%" + kw + "%");
        rs = stm.executeQuery();
        while (rs.next()) {
            Suppliers s = new Suppliers(
                rs.getInt("SupplierID"),
                rs.getString("SupplierName"),
                rs.getString("ContactPerson"),
                rs.getString("Phone"),
                rs.getString("Email"),
                rs.getString("Address"),
                rs.getString("Description"),
                rs.getTimestamp("CreatedAt")
            );
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
