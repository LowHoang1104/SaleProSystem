/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class TokenForgetPassword {

    private int id;
    private String token;
    private LocalDateTime expiryTime;
    private boolean isUsed;
    private String otpCode;
    private boolean isReseted;

    public TokenForgetPassword(int id, String token, LocalDateTime expiryTime, boolean isUsed, String otpCode) {
        this.id = id;
        this.token = token;
        this.expiryTime = expiryTime;
        this.isUsed = isUsed;
        this.otpCode = otpCode;
        this.isReseted = false;
    }

    public TokenForgetPassword(String token, LocalDateTime expiryTime, boolean isUsed, String otpCode) {
        this.token = token;
        this.expiryTime = expiryTime;
        this.isUsed = isUsed;
        this.otpCode = otpCode;
        this.isReseted = false;
    }

    public TokenForgetPassword(int id, String token, LocalDateTime expiryTime, boolean isUsed, String otpCode, boolean isReseted) {
        this.id = id;
        this.token = token;
        this.expiryTime = expiryTime;
        this.isUsed = isUsed;
        this.otpCode = otpCode;
        this.isReseted = isReseted;
    }

    public TokenForgetPassword() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public boolean isIsReseted() {
        return isReseted;
    }

    public void setIsReseted(boolean isReseted) {
        this.isReseted = isReseted;
    }

}
