/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.EmployeeTypes;

/**
 *
 * @author Thinhnt
 */
public class EmployeeTypeDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public List<EmployeeTypes> getData() {

        List<EmployeeTypes> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM EmployeeTypes";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                EmployeeTypes et = new EmployeeTypes();
                et.setEmployeeTypeID(rs.getInt("EmployeeTypeID"));
                et.setTypeName(rs.getString("TypeName"));
                list.add(et);
            }

        } catch (Exception e) {
            e.printStackTrace(); // hoặc log nếu muốn
        }

        return list;
    }

    public int insertEmployeeType(String employeeTypeName) {
        String sql = "INSERT INTO EmployeeTypes (TypeName) VALUES (?)";
        int generatedId = -1;
        try {
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, employeeTypeName);
            stm.executeUpdate();
            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public boolean checkEmployeeTypeName(String typeName) {
        String sql = "select * from EmployeeTypes\n"
                + "where TypeName = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, typeName);
            rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public EmployeeTypes getEmployeeTypeById(int empTypeId) {
        String sql = "select * from EmployeeTypes\n"
                + "where EmployeeTypeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, empTypeId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new EmployeeTypes(rs.getInt("EmployeeTypeID"), rs.getString("TypeName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmpTypeNameById(int id, String empTypeName) {
        String sql = "UPDATE [dbo].[EmployeeTypes]\n"
                + "   SET [TypeName] = ?\n"
                + " WHERE EmployeeTypeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, empTypeName);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmpTypeById(int id) {
        String sql = "DELETE FROM [dbo].[EmployeeTypes]\n"
                + "      WHERE EmployeeTypeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
