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
    private static final String GET_NAME_BY_CODE = "select ProductName from ProductMaster where Status = 1 and ProductCode = ? ";
    private static final String GET_PRICE_BY_CODE = "select Price from ProductMaster where Status = 1 and ProductCode = ? ";
    private static final String GET_DESCRIPTION_BY_CODE = "select Description from ProductMaster where Status = 1 and ProductCode = ? ";
    private static final String GET_CATEGORY_NAME_BY_CODE = " select c.CategoryName from ProductMaster p join Categories c  on p.Status = 1 and p.ProductCode = ? and c.CategoryID = p.CategoryID ";

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

    public String getNameByCode(String code) {
        String name = null;
        try {
            stm = connection.prepareStatement(GET_NAME_BY_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return name;
    }
    
    public double getPriceByCode(String code) {
        double price = 0;
        try {
            stm = connection.prepareStatement(GET_PRICE_BY_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                price = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return price;
    }
    public String getDescriptionByCode(String code) {
        String description = null;
        try {
            stm = connection.prepareStatement(GET_DESCRIPTION_BY_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                description = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return description;
    }
    public String getCategoryByCode(String code) {
        String category = null;
        try {
            stm = connection.prepareStatement(GET_CATEGORY_NAME_BY_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                category = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return category;
    }

    public static void main(String[] args) {
        System.out.println(new ProductMasterDAO().getCategoryByCode("PM001"));
    }
}
