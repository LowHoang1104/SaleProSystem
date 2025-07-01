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
import salepro.models.ProductAdmin;
import salepro.dal.DBContext2;
import salepro.models.ProductAdmin;
import salepro.models.ProductMasters;

/**
 *
 * @author tungd
 */
public class ProductMasterDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from ProductMaster where Status = 1 ";
    private static final String GET_PRODUCTS_BY_CATEGORY = "select * from Products where CategoryID = ? and Status = 1";
    private static final String GET_PRODUCTS_BY_TYPE = "select * from Products where TypeID = ? and Status = 1";

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

    public List<ProductMasters> serchByKeyword(String kw){
        String a = validateKeyword(kw);
        List<ProductMasters> data = new ArrayList<>();
        System.out.println();
        try {
            String sql = "select * from ProductMaster where ProductName like ? and Status = 1;";
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%"+a+"%");
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

    public void updateProduct(ProductMasters pm) {
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
            stm.setString(8, pm.getCode());
            stm.setString(1, pm.getName());
            stm.setInt(2, pm.getCategoryId());
            stm.setInt(3, pm.getTypeId());
            stm.setString(4, pm.getDescription());
            stm.setDouble(5, pm.getPrice());
            stm.setDouble(6, pm.getCostPrice());
            stm.setString(7, pm.getImage());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<ProductAdmin> GetTop10BestSellingProductsLast7Days() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n" +
"                    join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n" +
"                    join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n" +
"                    join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n" +
"                    join ProductTypes m on d.TypeID=m.TypeID WHERE a.InvoiceDate >= DATEADD(DAY, -7, GETDATE())  group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
     public ArrayList<ProductAdmin> GetTop10BestSellingProductsLast1Month() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n" +
"                    join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n" +
"                    join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n" +
"                    join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n" +
"                    join ProductTypes m on d.TypeID=m.TypeID WHERE a.InvoiceDate >= DATEADD(DAY, -30, GETDATE())  group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
     public ArrayList<ProductAdmin> GetTop10BestSellingProductsLast3Months() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n" +
"                    join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n" +
"                    join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n" +
"                    join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n" +
"                    join ProductTypes m on d.TypeID=m.TypeID WHERE a.InvoiceDate >= DATEADD(DAY, -90, GETDATE())  group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
     public ArrayList<ProductAdmin> GetTop10BestSellingProductsLast6Months() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n" +
"                    join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n" +
"                    join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n" +
"                    join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n" +
"                    join ProductTypes m on d.TypeID=m.TypeID WHERE a.InvoiceDate >= DATEADD(DAY, -120, GETDATE())  group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
    
    public ArrayList<ProductAdmin> GetTop10BestSellingProducts() {
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
     public ArrayList<ProductAdmin> GetTop10BestSellingProductsLast12Months() {
        ArrayList<ProductAdmin> data = new ArrayList<ProductAdmin>();
        try {
            String strSQL = "select top 10 d.ProductCode,d.ProductName,m.TypeName,n.SupplierName, count(d.ProductCode) as 'Number sales'  from Invoices a \n" +
"                    join InvoiceDetails b on a.InvoiceID=b.InvoiceID join ProductVariants c on b.ProductVariantID=c.ProductVariantID \n" +
"                    join ProductMaster d on c.ProductCode=d.ProductCode join PurchaseDetails e on c.ProductVariantID=e.ProductVariantID \n" +
"                    join Purchases f on e.PurchaseID=f.PurchaseID join Suppliers n on f.SupplierID=n.SupplierID \n" +
"                    join ProductTypes m on d.TypeID=m.TypeID WHERE a.InvoiceDate >= DATEADD(DAY, -365, GETDATE())  group by  d.ProductCode,d.ProductName,m.TypeName,n.SupplierName order by  [Number sales] desc ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                data.add(new ProductAdmin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (Exception e) {

        }
        return data;
    }
    private String validateKeyword(String kw){
        String[] list=kw.trim().split("[^\\p{L}]+");
        String key="";
        for(int i=0; i< list.length;i++){
            if(i==list.length-1){
                key+=list[i];
            }else
                key+=list[i]+" ";
        }
        return key;
    } 

}

