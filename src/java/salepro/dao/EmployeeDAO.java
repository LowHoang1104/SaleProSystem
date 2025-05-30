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
import salepro.models.ReportData;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO extends DBContext2{
    PreparedStatement stm;
    ResultSet rs;
    
    public ArrayList<Employees> getData(){
        ArrayList<Employees> data= new ArrayList<>();
        try {
            String strSQL = "select * from Employees";
            stm = connection.prepareStatement(strSQL);           
            rs = stm.executeQuery();
            while (rs.next()) {
                Employees temp= new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)==1?true:false);
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }
    
       public ArrayList<ReportData> getEmployeeRevenue(){
        ArrayList<ReportData> data= new ArrayList<>();
         try {
            String strSQL = "SELECT top 5 \n"
                    + "    a.FullName, \n"
                    + "    a.EmployeeID, \n"
                    + "    SUM(b.TotalAmount) AS TotalSales\n"
                    + "FROM \n"
                    + "    Employees a\n"
                    + "JOIN \n"
                    + "    Invoices b ON a.EmployeeID = b.EmployeeID\n"
                    + "GROUP BY \n"
                    + "    a.FullName, a.EmployeeID;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                ReportData temp = new ReportData(rs.getString(1)+" ID: "+String.valueOf(rs.getInt(2)), rs.getInt(3));
                data.add(temp);
            }
        } catch (Exception e) {

        }
        return data;
    }
    
    
    public String getEmployeeNameByID(int id){
        try {
            String strSQL = "select a.FullName from Employees a where a.EmployeeID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1,id );
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }
    public static void main(String[] args) {
        EmployeeDAO da= new EmployeeDAO();
        System.out.println(da.getData().size());
    }
}
