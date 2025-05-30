/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext;
import salepro.models.EmployeeTypes;

/**
 *
 * @author Thinhnt
 */
public class EmployeeTypeDAO extends DBContext {
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

}
