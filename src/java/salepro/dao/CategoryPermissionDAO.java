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
import salepro.dal.DBContext2;
import salepro.models.CategoryPermissions;

/**
 *
 * @author Thinhnt
 */
public class CategoryPermissionDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public List<CategoryPermissions> getData() {
        List<CategoryPermissions> list = new ArrayList<>();
        String sql = "SELECT [CategoryID]\n"
                + "      ,[CategoryName]\n"
                + "  FROM [dbo].[CategoryPermission]";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                CategoryPermissions c = new CategoryPermissions(rs.getInt("CategoryID"), rs.getString("CategoryName"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public CategoryPermissions getCategoryPermissionByID(int id) {
        String sql = "SELECT [CategoryID]\n"
                + "      ,[CategoryName]\n"
                + "  FROM [dbo].[CategoryPermission]"
                + " where CategoryID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new CategoryPermissions(rs.getInt("CategoryID"), rs.getString("CategoryName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
