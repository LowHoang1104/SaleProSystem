/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SuperAdminController.payment;

/**
 *
 * @author MY PC
 */
public class VNPayConfig {

    // VNPay Sandbox Test Configuration - Thông tin chính thức từ VNPay
    public static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String VNP_TMN_CODE = "7ICDPE1U"; // Merchant code sandbox
    public static final String VNP_HASH_SECRET = "UHVW8NP9BMP48TZUWGGF7ORNDK2CPCN8"; // Hash secret sandbox
    public static final String VNP_RETURN_URL = "http://localhost:9999/Mg2/payment/return";

    
    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_COMMAND = "pay";
    public static final String VNP_CURR_CODE = "VND";
    public static final String VNP_LOCALE = "vn";

    // Response Codes
    public static final String SUCCESS_CODE = "00";
}
