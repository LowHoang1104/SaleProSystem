/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import salepro.dal.DBContext2;
import salepro.models.TokenForgetPassword;
import salepro.models.Users;
import salepro.service.ResetPassword;

/**
 *
 * @author ADMIN
 */
public class TokenForgetPasswordDAO extends DBContext2 {

    PreparedStatement stm;
    ResultSet rs;

    public String getFormatDate(LocalDateTime time) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String myFormatTime = myFormatObj.format(time);
        return myFormatTime;
    }

    public void insertTokenForgetPassword(TokenForgetPassword newTfp, Users user) {
        try {
            stm = connection.prepareStatement("INSERT INTO TokenForgetPassword (token, expiryTime, isUsed, userId, otpCode)\n"
                    + "VALUES \n"
                    + "(?, ?, ?, ?, ?);");
            stm.setString(1, newTfp.getToken());
            stm.setString(2, getFormatDate(newTfp.getExpiryTime()));
            stm.setInt(3, 0);
            stm.setInt(4, user.getUserId());
            stm.setString(5, newTfp.getOtpCode());
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIsUsedByToken(String token) {
        String sql = "Update TokenForgetPassword set isUsed=1 where token=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TokenForgetPassword getTokenForgetPasswordByToken(String token) {
        String sql = "SELECT * FROM TokenForgetPassword WHERE token = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                TokenForgetPassword tfp = new TokenForgetPassword();
                tfp.setId(rs.getInt("id"));
                tfp.setToken(rs.getString("token"));
                tfp.setExpiryTime(rs.getTimestamp("expiryTime").toLocalDateTime());
                tfp.setIsUsed(rs.getBoolean("isUsed"));
                tfp.setOtpCode(rs.getString("otpCode"));
                return tfp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkTokenisReseted(String token) {
        String sql = "SELECT * FROM TokenForgetPassword WHERE token = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                if (rs.getInt(7) == 1) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setTokenisReseted(String token) {
        String sql = "UPDATE TokenForgetPassword\n"
                + "SET isReseted = 1 where token=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, token);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TokenForgetPasswordDAO a = new TokenForgetPasswordDAO();
        System.out.println(a.checkTokenisReseted("e0e69e3b-724c-4a76-b3a1-3d184ecf167b"));
    }
}
