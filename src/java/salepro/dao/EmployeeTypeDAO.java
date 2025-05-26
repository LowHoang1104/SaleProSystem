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

    public List<EmployeeTypes> getAllEmployeeTypes() {

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

    public EmployeeTypes getEmployeeTypeById(int id) {
        try {
            String sql = "SELECT * FROM EmployeeTypes WHERE EmployeeTypeID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                EmployeeTypes et = new EmployeeTypes();
                et.setEmployeeTypeID(rs.getInt("EmployeeTypeID"));
                et.setTypeName(rs.getString("TypeName"));
                return et;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args) {
        EmployeeTypeDAO e = new EmployeeTypeDAO();
        for (EmployeeTypes allEmployeeType : e.getAllEmployeeTypes()) {
            System.out.println(allEmployeeType.getTypeName());
        }
    }
}
