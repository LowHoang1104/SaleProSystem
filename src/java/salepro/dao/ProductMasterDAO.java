/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.ProductMasters;

/**
 *
 * @author MY PC
 */
public class ProductMasterDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from ProductMaster where Status = 1";

    public List<ProductMasters> getData() {
        List<ProductMasters> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int typeId = rs.getInt(4);
                String description = rs.getString(5);
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                ProductMasters productMasters = new ProductMasters(code, name, categoryId, typeId, description, price, costPrice, images, status, date);
                data.add(productMasters);
            }

        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }
    public static void main(String[] args) {
        var list = new ProductMasterDAO().getData();
        
        for (ProductMasters item : list) {
            System.out.println(item.getCategoryId());
        }
    }
}

