/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import salepro.dal.DBContext2;
import salepro.models.ReportData;

/**
 *
 * @author ADMIN
 */
public class ProductMasterDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
    
    public ArrayList<ReportData> getTop5UnitHigh() {
        ArrayList<ReportData> data = new ArrayList<>();
        try {
            String sql = "SELECT TOP 5 a.ProductName, COUNT(*) as Num " +
                        "FROM ProductMaster a " +
                        "JOIN ProductVariants b ON a.ProductCode = b.ProductCode " +
                        "GROUP BY a.ProductName " +
                        "ORDER BY Num DESC";
            
            System.out.println("SQL Query: " + sql);
            
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("ProductName");
                int value = rs.getInt("Num");
                System.out.println("Đọc dữ liệu: " + name + " - " + value);
                ReportData temp = new ReportData(name, value);
                data.add(temp);
            }
            
            System.out.println("Tổng số bản ghi: " + data.size());
            
        } catch (Exception e) {
            System.out.println("Lỗi trong getTop5UnitHigh: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
    
    public static void main(String[] args) {
        ProductMasterDAO dao = new ProductMasterDAO();
        ArrayList<ReportData> list = dao.getTop5UnitHigh();
        System.out.println("Số lượng sản phẩm: " + list.size());
        for (ReportData r : list) {
            System.out.println("Tên: " + r.getName() + ", Số lượng: " + r.getValue());
        }
    }
}
