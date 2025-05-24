/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salepro.dal.DBContext2;

/**
 *
 * @author ADMIN
 */
public class PaymentMethodDAO extends DBContext2{
     PreparedStatement stm;
    ResultSet rs;
    public String getMethodNameByID(int id){
        try {
            String strSQL = "select a.MethodName from PaymentMethods a where a.PaymentMethodID=?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1,id );
            rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
