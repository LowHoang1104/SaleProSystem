/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kiotfpt.dal.DBContext;
/**
 *
 * @author MY PC
 */
public class PaymentMethodDAO extends DBContext {
    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_METHOD_BY_ID = "SELECT MethodName FROM PaymentMethods WHERE PaymentMethodID = ?";

    public boolean isBankMethod(int paymentMethodId) {
        try {
            stm = connection.prepareStatement(GET_METHOD_BY_ID);
            stm.setInt(1, paymentMethodId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String methodName = rs.getString("MethodName").toLowerCase();
                
                if (methodName.contains("bank") || methodName.contains("card")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
