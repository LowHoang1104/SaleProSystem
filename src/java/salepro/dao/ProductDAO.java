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
import org.apache.catalina.startup.Catalina;
import salepro.dal.DBContext2;
import salepro.model.Products;

/**
 *
 * @author tungd
 */
public class ProductDAO extends DBContext2 {

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
            System.out.println("newBooks" + e.getMessage());
        }
        return data;
    }

}
