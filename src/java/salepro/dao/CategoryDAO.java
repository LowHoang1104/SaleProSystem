/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.Categories;
import salepro.models.ProductMasters;
import salepro.models.ProductTypes;

/**
 *
 * @author tungd
 */
public class CategoryDAO extends DBContext {

    PreparedStatement stm; //Thực hiện câu lệnh SQL
    ResultSet rs; //Lưu trữ và xử lý dữ liệu 

    private final static String GET_ALL_PRODUCT_TYPE = "SELECT TypeID, TypeName FROM ProductTypes";
//    private final static String GET_ALL_CATEGORY = "SELECT TypeID, TypeName FROM ProductTypes ORDER BY TypeName";

    public List<Categories> getCategory() {
        List<Categories> data = new ArrayList<>();
        try {
            String strSQL = "SELECT  * FROM Categories";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int typeID = rs.getInt(3);
                Categories b = new Categories(id, name, typeID);
                data.add(b);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return data;
    }

    public String getNameByID(int id) {
        String name = "";
        try {
            String strSQL = "SELECT  * FROM Categories where CategoryID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString(2);
            }
        } catch (Exception e) {
            System.out.println("newBooks" + e.getMessage());
        }
        return name;
    }

    public List<ProductTypes> getAllProductTypes() throws SQLException {
        List<ProductTypes> productTypes = new ArrayList<>();

        try {
            stm = connection.prepareStatement(GET_ALL_PRODUCT_TYPE);
            rs = stm.executeQuery();

            while (rs.next()) {
                ProductTypes type = new ProductTypes(
                        rs.getInt("TypeID"),
                        rs.getString("TypeName")
                );
                productTypes.add(type);
            }
        } catch (SQLException e) {

        }
        return productTypes;
    }

    public List<Categories> getAllCategories() throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String sql = """
            SELECT c.CategoryID, c.CategoryName, c.TypeID 
            FROM Categories c 
            INNER JOIN ProductTypes pt ON c.TypeID = pt.TypeID 
            ORDER BY pt.TypeName, c.CategoryName
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categories category = new Categories(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getInt("TypeID")
                );
                categories.add(category);
            }
        }

        return categories;
    }

    public List<Categories> getCategoriesByTypeID(int typeID) throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String sql = """
            SELECT CategoryID, CategoryName, TypeID 
            FROM Categories 
            WHERE TypeID = ? 
            ORDER BY CategoryName
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, typeID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categories category = new Categories(
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName"),
                            rs.getInt("TypeID")
                    );
                    categories.add(category);
                }
            }
        }

        return categories;
    }
}
