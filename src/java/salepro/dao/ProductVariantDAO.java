/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import salepro.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.models.ProductMasters;
import salepro.models.ProductVariants;

/**
 *
 * @author MY PC
 */
public class ProductVariantDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<ProductVariants> getProductVariantByID(String id) {
        List<ProductVariants> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("select * from ProductVariants where ProductCode = ?");
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pvid = rs.getInt(1);
                String pmid = rs.getString(2);
                int sid = rs.getInt(3);
                int cid = rs.getInt(4);
                String sku = rs.getString(5);
                String unit = rs.getString(6);
                int avgquantity = rs.getInt(7);
                ProductVariants pv = new ProductVariants(pvid, pmid, sid, cid, sku, unit, avgquantity);
                data.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<ProductVariants> getProductVariantStockTake(int stkid) {
        List<ProductVariants> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("SELECT *\n"
                    + "FROM ProductVariants pv\n"
                    + "WHERE NOT EXISTS (\n"
                    + "    SELECT 1\n"
                    + "    FROM StockTakeDetails sd\n"
                    + "    WHERE sd.ProductVariantID = pv.ProductVariantID\n"
                    + "      AND sd.StockTakeID = ?\n"
                    + ")");
            stm.setInt(1, stkid);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pvid = rs.getInt(1);
                String pmid = rs.getString(2);
                int sid = rs.getInt(3);
                int cid = rs.getInt(4);
                String sku = rs.getString(5);
                String unit = rs.getString(6);
                int avgquantity = rs.getInt(7);
                ProductVariants pv = new ProductVariants(pvid, pmid, sid, cid, sku, unit, avgquantity);
                data.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public void add(ProductVariants pv) {
        try {
            String strSQL = "INSERT INTO ProductVariants (ProductCode, SizeID, ColorID, SKU, Unit, AverageQuantity)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, pv.getProductCode());
            stm.setInt(2, pv.getSizeId());
            stm.setInt(3, pv.getColorId());
            stm.setString(4, pv.getSku());
            stm.setString(5, pv.getUnit());
            stm.setInt(6, pv.getAverageQuantity());
            stm.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String productVarianttoString(int productVariantID) {
        try {
            String strSQL = "select pm.ProductName, s.SizeName,c.ColorName from ProductVariants pv join Sizes  s on pv.SizeID = s.SizeID join Colors c on pv.ColorID = c.ColorID join ProductMaster pm on pv.ProductCode = pm.ProductCode where pv.ProductVariantID = ?;";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, productVariantID);
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public List<ProductVariants> getProductVariantPurchase(int parseInt) {
        List<ProductVariants> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("SELECT * \n"
                    + "FROM ProductVariants pv\n"
                    + "WHERE NOT EXISTS (\n"
                    + "    SELECT 1\n"
                    + "    FROM PurchaseDetails pd\n"
                    + "    WHERE pd.ProductVariantID = pv.ProductVariantID\n"
                    + "      AND pd.PurchaseID = ?\n"
                    + ");");
            stm.setInt(1, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pvid = rs.getInt(1);
                String pmid = rs.getString(2);
                int sid = rs.getInt(3);
                int cid = rs.getInt(4);
                String sku = rs.getString(5);
                String unit = rs.getString(6);
                int avgquantity = rs.getInt(7);
                ProductVariants pv = new ProductVariants(pvid, pmid, sid, cid, sku, unit, avgquantity);
                data.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

    public List<ProductVariants> getProductVariantNotInWarehouse(int wid) {
        List<ProductVariants> data = new ArrayList<>();
        try {
            String sql = "SELECT * \n"
                    + "FROM ProductVariants pv\n"
                    + "WHERE NOT EXISTS (\n"
                    + "    SELECT 1 \n"
                    + "    FROM Inventory i\n"
                    + "    WHERE i.ProductVariantID = pv.ProductVariantID\n"
                    + "      AND i.WarehouseID = ?\n"
                    + ");";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, wid);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pvid = rs.getInt("ProductVariantID");
                String pmid = rs.getString("ProductCode");
                int sid = rs.getInt("SizeID");
                int cid = rs.getInt("ColorID");
                String sku = rs.getString("SKU");
                String unit = rs.getString("Unit");
                int avgquantity = rs.getInt("AverageQuantity");
                ProductVariants pv = new ProductVariants(pvid, pmid, sid, cid, sku, unit, avgquantity);
                data.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getProductVariantNotInWarehouse: " + e.getMessage());
        }
        return data;
    }

    public List<ProductVariants> getProductVariantPurchaseByCode(int parseInt, String productcode) {
        List<ProductVariants> data = new ArrayList<>();
        try {

            stm = connection.prepareStatement("SELECT * \n"
                    + "FROM ProductVariants pv\n"
                    + "JOIN ProductMaster pm ON pv.ProductCode = pm.ProductCode\n"
                    + "WHERE pm.ProductCode = ?\n"
                    + "  AND NOT EXISTS (\n"
                    + "      SELECT 1\n"
                    + "      FROM PurchaseDetails pd\n"
                    + "      WHERE pd.ProductVariantID = pv.ProductVariantID\n"
                    + "        AND pd.PurchaseID = ?\n"
                    + ");");
            stm.setString(1, productcode);
            stm.setInt(2, parseInt);
            rs = stm.executeQuery();
            while (rs.next()) {
                int pvid = rs.getInt(1);
                String pmid = rs.getString(2);
                int sid = rs.getInt(3);
                int cid = rs.getInt(4);
                String sku = rs.getString(5);
                String unit = rs.getString(6);
                int avgquantity = rs.getInt(7);
                ProductVariants pv = new ProductVariants(pvid, pmid, sid, cid, sku, unit, avgquantity);
                data.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getProducts: " + e.getMessage());
        }
        return data;
    }

}
