/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Purchases;
import salepro.models.StoreFund;

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
                Purchases temp= new Purchases(rs.getInt(1), rs.getString(2),rs.getDate(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6));
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }
    
    public String getNameSupplierByID(int id){
        try {
            String strSQL = "select top 1 b.SupplierName from Purchases a join Suppliers b on a.SupplierID=b.SupplierID where a.PurchaseID=?";
            stm = connection.prepareStatement(strSQL); 
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return null;
    }
    
     public String getNameWarehouseByID(int id){
        try {
            String strSQL = "select top 1 b.WarehouseName from Purchases a join Warehouses b on a.WarehouseID=b.WarehouseID where a.PurchaseID=?";
            stm = connection.prepareStatement(strSQL); 
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {  
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return null;
    }
     public static void main(String[] args) {
//        if (op != null && op.equals("listFundByStoreId")) {
            StoreFundDAO da = new StoreFundDAO();
            String shopId = "1";//request.getParameter("shopId");
            List<StoreFund> data = da.getFundsByStoreId(Integer.parseInt(shopId));        
            System.out.println(data.size());
    }
}
