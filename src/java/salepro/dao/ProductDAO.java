/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import salepro.dal.DBContext;
import salepro.model.Products;

/**
 *
 * @author tungd
 */
public class ProductDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    public ArrayList<Products> getProducts() {
        ArrayList<Products> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Products p join Categories c on p.CategoryID = c.CategoryID join Colors cl on p.ColorID = cl.ColorID join ProductTypes pt on p.TypeID = pt.TypeID join Sizes s on p.SizeID = s.SizeID";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String cate = rs.getString(15);
                String size = rs.getString(22);
                String color = rs.getString(18);
                String type = rs.getString(20);
                BigDecimal price = rs.getBigDecimal(7);
                BigDecimal costprice = rs.getBigDecimal(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                Boolean status = rs.getBoolean(12);
                LocalDate releasedate = LocalDate.parse(rs.getString(13));
                Products b = new Products(id, name, cate, size, color, type, price, costprice, unit, description, images, status, releasedate);

                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ArrayList<Products> filterProduct(String category, String color, String type, String size, String store) {
        ArrayList<Products> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Products p "
                    + "JOIN Categories c ON p.CategoryID = c.CategoryID "
                    + "JOIN Colors cl ON p.ColorID = cl.ColorID "
                    + "JOIN ProductTypes pt ON p.TypeID = pt.TypeID "
                    + "JOIN Sizes s ON p.SizeID = s.SizeID "
                    + "JOIN Inventory i ON p.ProductID = i.ProductID "
                    + "JOIN Warehouses w ON i.WarehouseID = w.WarehouseID "
                    + "JOIN Stores st ON w.StoreID = st.StoreID "
                    + "WHERE (? = 0 OR c.CategoryID = ?) "
                    + "AND (? = 0 OR s.SizeID = ?) "
                    + "AND (? = 0 OR cl.ColorID = ?) "
                    + "AND (? = 0 OR pt.TypeID = ?) "
                    + "AND (? = 0 OR st.StoreID = ?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, category);
            stm.setString(2, category);
            stm.setString(3, size);
            stm.setString(4, size);
            stm.setString(5, color);
            stm.setString(6, color);
            stm.setString(7, type);
            stm.setString(8, type);
            stm.setString(9, store);
            stm.setString(10, store);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String cate = rs.getString(15);
                String s = rs.getString(22);
                String cl = rs.getString(18);
                String tp = rs.getString(20);
                BigDecimal price = rs.getBigDecimal(7);
                BigDecimal costprice = rs.getBigDecimal(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                Boolean status = rs.getBoolean(12);
                LocalDate releasedate = LocalDate.parse(rs.getString(13));
                Products b = new Products(id, name, cate, s, cl, tp, price, costprice, unit, description, images, status, releasedate);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public Products getProductById(String id) {
        Products p = null;
        try {
            String strSQL = "SELECT  * FROM Products p join Categories c on p.CategoryID = c.CategoryID join Colors cl on p.ColorID = cl.ColorID join ProductTypes pt on p.TypeID = pt.TypeID join Sizes s on p.SizeID = s.SizeID where ProductID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                int pid = rs.getInt(1);
                String name = rs.getString(2);
                String cate = rs.getString(15);
                String size = rs.getString(22);
                String color = rs.getString(18);
                String type = rs.getString(20);
                BigDecimal price = rs.getBigDecimal(7);
                BigDecimal costprice = rs.getBigDecimal(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                Boolean status = rs.getBoolean(12);
                LocalDate releasedate = LocalDate.parse(rs.getString(13));
                p = new Products(pid, name, cate, size, color, type, price, costprice, unit, description, images, status, releasedate);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public void delProductById(String id) {
        try {
            String strSQL = "DELETE FROM Products WHERE ProductID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
