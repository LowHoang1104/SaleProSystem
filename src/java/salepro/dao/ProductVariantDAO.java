/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.ProductVariants;
public class ProductVariantDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_SIZES_BY_MASTER_CODE = " SELECT DISTINCT s.SizeID,s.SizeName FROM ProductVariants pv JOIN Sizes s ON pv.SizeID = s.SizeID WHERE pv.ProductCode = ?;";
    private static final String GET_COLORS_BY_MASTER_CODE = "SELECT DISTINCT s.ColorID,s.ColorName FROM ProductVariants pv JOIN Colors s ON pv.ColorID = s.ColorID WHERE pv.ProductCode = ?; ";
    private static final String GET_VARIANT_ID_BY_CODE_AND_SIZE_AND_COLOR = "SELECT pv.ProductVariantID "
            + "FROM ProductVariants pv "
            + "JOIN Sizes s ON pv.SizeID = s.SizeID "
            + "JOIN Colors c ON pv.ColorID = c.ColorID "
            + "WHERE pv.ProductCode = ? AND s.SizeName = ? AND c.ColorName = ?";
    private static final String GET_VARIANT_BY_ID = "SELECT * FROM ProductVariants WHERE ProductVariantID = ?";

    public List<String> geSizeListByMasterCode(String code) {
        List<String> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_SIZES_BY_MASTER_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                data.add(name);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public List<String> geColorListByMasterCode(String code) {
        List<String> data = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_COLORS_BY_MASTER_CODE);
            stm.setString(1, code);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                data.add(name);
            }
        } catch (Exception e) {

        }
        return data;
    }

    public int getProductVariantId(String productCode, String size, String color) {
        int variantId = -1;
        try {
            stm = connection.prepareStatement(GET_VARIANT_ID_BY_CODE_AND_SIZE_AND_COLOR);
            stm.setString(1, productCode);
            stm.setString(2, size);
            stm.setString(3, color);
            rs = stm.executeQuery();
            if (rs.next()) {
                variantId = rs.getInt("ProductVariantID");
            }
        } catch (Exception e) {
            System.out.println("getProductVariantId: " + e.getMessage());
        }
        return variantId;
    }
    

    public ProductVariants getProductVariantByID(int id) {
        ProductVariants productVariants = null;
        try {
            stm = connection.prepareStatement(GET_VARIANT_BY_ID);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String productCode = rs.getString("ProductCode");
                int size = rs.getInt("SizeID");
                int color = rs.getInt("ColorID");
                int sku = rs.getInt("SKU");
                int unit = rs.getInt("Unit");
                
                productVariants = new ProductVariants(id, productCode, size, color, productCode, productCode);
            }
        } catch (Exception e) {
            System.out.println("getProductVariantId: " + e.getMessage());
        }
        return productVariants;
    }
    
   
    
    public static void main(String[] args) {
        List<String> list = new ProductVariantDAO().geColorListByMasterCode("PM001");
        int variantId = new ProductVariantDAO().getProductVariantId("PM001", "", null);
        System.out.println(variantId);
        for (String string : list) {
            System.out.println(string);
        }
    }
}
