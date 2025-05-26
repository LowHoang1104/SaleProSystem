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

import salepro.models.ProductMasters;



/**
 *
 * @author tungd
 */
public class ProductDAO extends DBContext2 {

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
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String description = rs.getString(5);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                ProductMasters product = new ProductMasters(code, name, categoryId, typeId, description, price, costPrice, images, status, date);
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public ProductMasters getProductById(String id) {
        ProductMasters product = new ProductMasters();
        try {
            String str = "select * from ProductMaster where ProductCode = ? and Status = 1";
            stm = connection.prepareStatement(str);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int typeId = rs.getInt(4);
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String description = rs.getString(5);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                product = new ProductMasters(id, name, categoryId, typeId, description, price, costPrice, images, status, date);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return product;
    }

    public List<ProductMasters> getProductByTypeIdList(int typeId) {
        List<ProductMasters> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_TYPE);
            stm.setInt(1, typeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String description = rs.getString(5);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                ProductMasters product = new ProductMasters(code, name, categoryId, typeId, description, price, costPrice, images, status, date);
                        data.add(product);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<ProductMasters> getProductByCategoryIddList(int categoryId) {
        List<ProductMasters> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY);
            stm.setInt(1, categoryId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                int cateId = rs.getInt(3);
                int typeId = rs.getInt(4);
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String description = rs.getString(5);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                ProductMasters product = new ProductMasters(id, name, categoryId, typeId, description, price, costPrice, images, status, date);
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

    public List<ProductMasters> filterProduct(String category, String type, String store) {
        ArrayList<ProductMasters> data = new ArrayList<>();
        try {
            String strSQL = "SELECT DISTINCT pm.ProductCode, pm.ProductName, pm.Price, pm.CostPrice, pm.Description, pm.Images, pm.Status, pm.ReleaseDate,\n"
                    + "       pm.CategoryID, pm.TypeID\n"
                    + "FROM ProductMaster pm\n"
                    + "JOIN Categories c ON pm.CategoryID = c.CategoryID\n"
                    + "JOIN ProductTypes pt ON pm.TypeID = pt.TypeID\n"
                    + "JOIN ProductVariants pv ON pm.ProductCode = pv.ProductCode\n"
                    + "JOIN Inventory i ON pv.ProductVariantID = i.ProductVariantID\n"
                    + "JOIN Warehouses w ON i.WarehouseID = w.WarehouseID\n"
                    + "JOIN Stores st ON w.StoreID = st.StoreID\n"
                    + "WHERE (? = 0 OR c.CategoryID = ?)\n"
                    + "AND (? = 0 OR pt.TypeID = ?)\n"
                    + "AND (? = 0 OR st.StoreID = ?)\n"
                    + "AND pm.Status=1";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, category);
            stm.setString(2, category);
            stm.setString(3, type);
            stm.setString(4, type);
            stm.setString(5, store);
            stm.setString(6, store);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(9);
                int typeId = rs.getInt(10);
                double price = rs.getDouble(3);
                double costPrice = rs.getDouble(4);
                String description = rs.getString(5);
                String images = rs.getString(6);
                boolean status = rs.getBoolean(7);
                Date date = rs.getDate(8);
                ProductMasters product = new ProductMasters(id, name, categoryId, typeId, description, price, costPrice, images, status, date);
                        data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void delProductById(String id) {
        try {
            String str = "UPDATE ProductMaster\n"
                    + "SET Status = 0\n"
                    + "WHERE ProductCode = ?;";
            stm = connection.prepareStatement(str);
            stm.setString(1, id);
            rs = stm.executeQuery();
        } catch (Exception e) {
            System.out.println("delProducts: " + e.getMessage());
        }
    }

    public void addProduct(ProductMasters pm) {
      try {
            String strSQL = "INSERT INTO ProductMaster(ProductCode,ProductName,CategoryID,TypeID,[Description],Price,CostPrice,Images) VALUES (?,?,?,?,?,?,?,?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, pm.getCode());
            stm.setString(2, pm.getName());
            stm.setInt(3, pm.getCategoryId());
            stm.setInt(4, pm.getTypeId());
            stm.setString(5, pm.getDescription());
            stm.setDouble(6, pm.getPrice());
            stm.setDouble(7, pm.getCostPrice());
            stm.setString(8, pm.getImage());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
  
    }

}
