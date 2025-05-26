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

import salepro.dal.DBContext2;
import salepro.models.ProductAdmin;

import salepro.models.Products;

/**
 *
 * @author MY PC
 */
public class ProductDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_PRODUCT_STATISTIC = "SELECT TOP 10 \n"
            + "    pv.ProductVariantID,\n"
            + "    pm.ProductCode,\n"
            + "    pm.ProductName,\n"
            + "    SUM(id.Quantity) AS TotalQuantitySold\n"
            + "FROM InvoiceDetails id\n"
            + "JOIN Invoices i ON id.InvoiceID = i.InvoiceID\n"
            + "JOIN ProductVariants pv ON id.ProductVariantID = pv.ProductVariantID\n"
            + "JOIN ProductMaster pm ON pv.ProductCode = pm.ProductCode\n"
            + "WHERE YEAR(i.InvoiceDate) = ?\n"
            + "  AND MONTH(i.InvoiceDate) = ?\n"
            + "GROUP BY pv.ProductVariantID, pm.ProductCode, pm.ProductName\n"
            + "ORDER BY TotalQuantitySold DESC;";
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

    public List<Products> productTop10(int year, int month) {
        List<Products> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_PRODUCT_STATISTIC);
            stm.setInt(1, year);
            stm.setInt(2, month);
            rs = stm.executeQuery();
            while (rs.next()) {
                int ProductVariantID = rs.getInt(1);
                String ProductCode = rs.getString(2);
                String ProductName = rs.getString(3);
                int TotalQuantitySold = rs.getInt(4);

                ProductChart pc = new ProductChart(ProductVariantID, ProductCode, ProductName, TotalQuantitySold);
                data.add(pc);
            }

        } catch (Exception e) {
            System.out.println("getProductsStatistic: " + e.getMessage());
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

    public ArrayList<ProductAdmin> getTopTenBestProduct() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n"
                    + "join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n"
                    + "join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n"
                    + "join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n"
                    + "join ProductTypes m on d.TypeID=m.TypeID group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
}
