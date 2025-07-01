/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext2;
import salepro.models.Employees;
import salepro.models.Roles;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
public class EmployeeDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Employees> getData() {
        ArrayList<Employees> data = new ArrayList<>();
        try {
            String strSQL = "select * from Employees";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                Employees temp = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7) == 1 ? true : false);
                data.add(temp);
            }
        } catch (Exception e) {
        }
        return data;
    }

    public String getEmployeeNameByID(int id) {
        try {
            String strSQL = "select a.FullName from Employees a where a.EmployeeID=?";
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

    public Employees getEmployeeByUserId(int userId) {
        Employees emp = null;
        String sql = "SELECT e.EmployeeID, e.FullName, e.Phone, e.StoreID, e.EmployeeTypeID, "
                + "u.UserID, u.Username, u.Email, u.Avatar, u.RoleID "
                + "FROM Employees e "
                + "JOIN Users u ON e.UserID = u.UserID "
                + "WHERE u.UserID = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng User
                Users user = new Users();
                int userId1 = rs.getInt("UserID");
                user.setUserId(userId);
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setAvatar(rs.getString("Avatar"));

                // Nếu có Role
                user.setRoleId(rs.getInt("RoleID"));

                // Tạo đối tượng Employee
                emp = new Employees();
                emp.setEmployeeID(rs.getInt("EmployeeID"));
                emp.setFullName(rs.getString("FullName"));
                emp.setPhone(rs.getString("Phone"));
                emp.setStoreID(rs.getInt("StoreID"));
                emp.setEmployeeTypeID(rs.getInt("EmployeeTypeID"));
                emp.setUserID(userId1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    public boolean insertEmployee(Employees emp) {
        String sql = "INSERT INTO [dbo].[Employees]\n"
                + "           ([FullName]\n"
                + "           ,[Phone]\n"
                + "           ,[StoreID]\n"
                + "           ,[EmployeeTypeID]\n"
                + "           ,[UserID]\n"
                + "           ,[IsActive])\n"
                + "     VALUES(?, ?, ?, ?, ?, 1)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, emp.getFullName());
            stm.setString(2, emp.getPhone());
            stm.setInt(3, emp.getStoreID());
            stm.setInt(4, emp.getEmployeeTypeID());
            stm.setInt(5, emp.getUserID());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(Employees emp) {
        String sql = "UPDATE [dbo].[Employees]\n"
                + "   SET [FullName] = ?\n"
                + "      ,[Phone] = ?\n"
                + "      ,[StoreID] = ?\n"
                + "      ,[EmployeeTypeID] = ?\n"
                + "      ,[UserID] = ?\n"
                + "      ,[IsActive] = ?\n"
                + " WHERE EmployeeID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, emp.getFullName());
            stm.setString(2, emp.getPhone());
            stm.setInt(3, emp.getStoreID());
            stm.setInt(4, emp.getEmployeeTypeID());
            stm.setInt(5, emp.getUserID());
            stm.setBoolean(6, emp.getIsActive());
            stm.setInt(7, emp.getEmployeeID());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Employees> getEmployeeByStoreId(int storeId) {
        String sql = "select * from Employees "
                + "where StoreID = ?";
        List<Employees> list = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, storeId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Employees temp = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7) == 1 ? true : false);
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employees> filterEmployee(int storeId, String empName) {
        List<Employees> list = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "select * from Employees";

        if (storeId != 0) {
            conditions.add("StoreID = ?");
            params.add(storeId);
        }
        if (empName != null && !empName.isBlank()) {
            conditions.add("FullName like ?");
            params.add("%" + empName.trim() + "%");
        }

        //Gắn Where nếu có điều kiện
        if (!conditions.isEmpty()) {
            sql += " where " + String.join(" and ", conditions);
        }

        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String) {
                    stm.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    stm.setInt(i + 1, (Integer) param);
                }
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                Employees temp = new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7) == 1 ? true : false);
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Employees getEmployeeById(int empId) {
        try {
            String strSQL = "select * from Employees"
                    + " where EmployeeID = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, empId);
            rs = stm.executeQuery();
            if (rs.next()) {
                return new Employees(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7) == 1 ? true : false);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean checkPhone(String phone) {
        String sql = "select * from Employees"
                + " where Phone = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        EmployeeDAO e  = new EmployeeDAO();
        System.out.println(e.checkPhone("0911111111"));
    }

}
