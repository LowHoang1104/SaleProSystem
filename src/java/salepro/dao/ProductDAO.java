/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.Products;

/**
 *
 * @author MY PC
 */
public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from Products where Status = 1 ";
    private static final String GET_PRODUCTS_BY_ID = "select * from Products where ProductID = ? and Status = 1";
    private static final String GET_PRODUCTS_BY_CATEGORY = "select * from Products where CategoryID = ? and Status = 1";
    private static final String GET_PRODUCTS_BY_TYPE = "select * from Products where TypeID = ? and Status = 1";
    private static final String GET_TOTAL_PRODUCTS = "select count(*) as Total from Products where Status=1";
    private static final String GET_PRODUCTS_NEW_RELEASE = "select * from Products where ReleaseDate >=DATEADD(MONTH,-1,GETDATE()) and Status = 1";
    private static final String INSERT_PRODUCT = "Insert into Products(ProductName, CategoryID, SizeID, ColorID, TypeID, Price, CostPrice, Unit, Description, Images, Status, ReleaseDate)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = " UPDATE Products SET ProductName = ?, CategoryID = ?, SizeID = ?, ColorID = ?, TypeID = ?, Price = ?, CostPrice = ?, Unit = ?, Description = ?,"
            + " Images = ?, Status = ?, ReleaseDate = ? WHERE ProductID = ?";
    private static final String DELETE_PRODUCT = "update Products SET Status = 0 where ProductID = ?";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Products WHERE Status = 1 AND REPLACE(ProductName, ' ', '') "
            + "COLLATE Latin1_General_CI_AI LIKE ?";

    public List<Products> getData() {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int sizeId = rs.getInt(4);
                int colorId = rs.getInt(5);
                int typeId = rs.getInt(6);
                double price = rs.getDouble(7);
                double costPrice = rs.getDouble(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                boolean status = rs.getBoolean(12);
                Date date = rs.getDate(13);
                Products product = new Products(id, name, categoryId, sizeId, colorId, typeId, price, costPrice, unit, description, images, status, date);
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public Products getProductById(int id) {
        Products product = new Products();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int sizeId = rs.getInt(4);
                int colorId = rs.getInt(5);
                int typeId = rs.getInt(6);
                double price = rs.getDouble(7);
                double costPrice = rs.getDouble(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                boolean status = rs.getBoolean(12);
                Date date = rs.getDate(13);
                product = new Products(id, name, categoryId, sizeId, colorId, typeId, price, costPrice, unit, description, images, status, date);

            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return product;
    }

    public List<Products> getProductByTypeIdList(int typeId) {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_TYPE);
            stm.setInt(1, typeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int sizeId = rs.getInt(4);
                int colorId = rs.getInt(5);

                double price = rs.getDouble(7);
                double costPrice = rs.getDouble(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                boolean status = rs.getBoolean(12);
                Date date = rs.getDate(13);
                Products product = new Products(id, name, categoryId, sizeId, colorId, typeId, price, costPrice, unit, description, images, status, date);
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<Products> getProductByCategoryIddList(int categoryId) {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                int sizeId = rs.getInt(4);
                int colorId = rs.getInt(5);
                int typeId = rs.getInt(6);
                double price = rs.getDouble(7);
                double costPrice = rs.getDouble(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                boolean status = rs.getBoolean(12);
                Date date = rs.getDate(13);
                Products product = new Products(id, name, categoryId, sizeId, colorId, typeId, price, costPrice, unit, description, images, status, date);
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public String getProductNameByID(int id) {
        try {
            String strSQL = "select ProductName from Products where ProductID=?";
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

    public List<Products> filterProduct(String category, String color, String type, String size, String store) {
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
                int cate = rs.getInt(3);
                int s = rs.getInt(4);
                int cl = rs.getInt(5);
                int typtpeId = rs.getInt(6);
                double price = rs.getDouble(7);
                double costprice = rs.getDouble(8);
                String unit = rs.getString(9);
                String description = rs.getString(10);
                String images = rs.getString(11);
                Boolean status = rs.getBoolean(12);
                Date releasedate = rs.getDate(13);
                Products b = new Products(id, name, cate, s, cl, typtpeId, price, costprice, unit, description, images, status, releasedate);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
