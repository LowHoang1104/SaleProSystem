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
import salepro.dal.DBContext2;
import salepro.models.ProductMaster;

/**
 *
 * @author MY PC
 */
public class ProductMasterDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from ProductMaster where Status = 1";

    public List<ProductMaster> getData() {
        List<ProductMaster> data = new ArrayList<>();
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
                ProductMaster productMasters = new ProductMaster(code, name, categoryId, typeId, price, costPrice, description, images, status, date);
                data.add(productMasters);
            }

        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<ProductMaster> filterProduct(String category, String type, String store) {
        ArrayList<ProductMaster> data = new ArrayList<>();
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
                ProductMaster product = new ProductMaster(id, name, categoryId, typeId, price, costPrice, description, images, status, date);
                data.add(product);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void addProduct(ProductMaster pm) {
        try {
            String strSQL = "INSERT INTO ProductMaster(ProductCode,ProductName,CategoryID,TypeID,[Description],Price,CostPrice,Images) VALUES (?,?,?,?,?,?,?,?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, pm.getProductCode());
            stm.setString(2, pm.getProductName());
            stm.setInt(3, pm.getCategoryId());
            stm.setInt(4, pm.getTypeId());
            stm.setString(5, pm.getDescription());
            stm.setDouble(6, pm.getPrice());
            stm.setDouble(7, pm.getCostPrice());
            stm.setString(8, pm.getImages());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateProduct(ProductMaster pm) {
        try {
            String strSQL = "UPDATE ProductMaster\n"
                    + "SET \n"
                    + "    ProductName = ?,\n"
                    + "    CategoryID = ?,\n"
                    + "    TypeID = ?,\n"
                    + "    Description = ?,\n"
                    + "    Price = ?,\n"
                    + "    CostPrice = ?,\n"
                    + "    Images = ?\n"
                    + "WHERE ProductCode = ?;";
            stm = connection.prepareStatement(strSQL);
            stm.setString(8, pm.getProductCode());
            stm.setString(1, pm.getProductName());
            stm.setInt(2, pm.getCategoryId());
            stm.setInt(3, pm.getTypeId());
            stm.setString(4, pm.getDescription());
            stm.setDouble(5, pm.getPrice());
            stm.setDouble(6, pm.getCostPrice());
            stm.setString(7, pm.getImages());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ProductMaster getProductById(String id) {
        ProductMaster product = new ProductMaster();
        try {
            String str = "select * from ProductMaster where ProductCode = ? and Status = 1";
            stm = connection.prepareStatement(str);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String pid = rs.getString(1);
                String name = rs.getString(2);
                int categoryId = rs.getInt(3);
                int typeId = rs.getInt(4);
                double price = rs.getDouble(6);
                double costPrice = rs.getDouble(7);
                String description = rs.getString(5);
                String images = rs.getString(8);
                boolean status = rs.getBoolean(9);
                Date date = rs.getDate(10);
                product = new ProductMaster(pid, name, categoryId, typeId, price, costPrice, description, images, status, date);

            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return product;
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

}
