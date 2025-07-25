package vnpay.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

public class Config {

    // VNPay Configuration
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_ReturnUrl = "http://localhost:9999/Mg2/payment/return";
    public static String vnp_TmnCode = "4YUP19I4";
    public static String secretKey = "MDUIFDCRAKLNBPOFIAFNEKFRNMFBYEPX";
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    /**
     * ✅ PHƯƠNG THỨC CHO VIỆC GỬI REQUEST (có encode trong hash data)
     * Dùng cho tạo payment URL
     */
    public static String hashAllFieldsForSending(Map<String, String> fields) {
        System.out.println("\n=== HASH FOR SENDING (WITH ENCODING) ===");
        
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    // ✅ ENCODE cho việc gửi (giống Method 3 của bạn)
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    hashData.append(fieldValue);
                }
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }
        
        String hashDataString = hashData.toString();
        String hash = hmacSHA512(secretKey, hashDataString);
        
        System.out.println("Hash data (encoded): " + hashDataString);
        System.out.println("Generated hash: " + hash);
        System.out.println("=== END HASH FOR SENDING ===\n");
        
        return hash;
    }

    /**
     * ✅ PHƯƠNG THỨC CHO VIỆC NHẬN RESPONSE (không encode trong hash data)
     * Dùng cho validate return/IPN
     */
    public static String hashAllFieldsForReceiving(Map<String, String> fields) {
        System.out.println("\n=== HASH FOR RECEIVING (NO ENCODING) ===");
        
        // Loại bỏ vnp_SecureHash và vnp_SecureHashType
        Map<String, String> cleanFields = new java.util.HashMap<>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (!entry.getKey().equals("vnp_SecureHash") && !entry.getKey().equals("vnp_SecureHashType")) {
                cleanFields.put(entry.getKey(), entry.getValue());
            }
        }
        
        List<String> fieldNames = new ArrayList<>(cleanFields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        
        boolean first = true;
        for (String fieldName : fieldNames) {
            String fieldValue = cleanFields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                if (!first) {
                    hashData.append('&');
                }
                hashData.append(fieldName);
                hashData.append('=');
                // ✅ KHÔNG encode cho việc nhận
                hashData.append(fieldValue);
                first = false;
            }
        }
        
        String hashDataString = hashData.toString();
        String hash = hmacSHA512(secretKey, hashDataString);
        
        System.out.println("Hash data (raw): " + hashDataString);
        System.out.println("Generated hash: " + hash);
        System.out.println("=== END HASH FOR RECEIVING ===\n");
        
        return hash;
    }

    /**
     * ✅ Build query string cho URL (có encode)
     */
    public static String buildQueryString(Map<String, String> params) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();
        
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return query.toString();
    }

    /**
     * ✅ BACKWARD COMPATIBILITY - Dùng method cũ cho sending
     */
    public static String hashAllFields(Map<String, String> fields) {
        return hashAllFieldsForSending(fields);
    }

    /**
     * ✅ Validate VNPay response signature (dùng method cho receiving)
     */
    public static boolean validateSignature(Map<String, String> params, String receivedSignature) {
        System.out.println("\n=== SIGNATURE VALIDATION ===");
        System.out.println("Received signature: " + receivedSignature);
        
        // Log tất cả parameters nhận được
        System.out.println("Received parameters:");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        String calculatedSignature = hashAllFieldsForReceiving(params);
        System.out.println("Calculated signature: " + calculatedSignature);
        
        boolean isValid = calculatedSignature.equals(receivedSignature);
        System.out.println("Signatures match: " + isValid);
        System.out.println("=== END VALIDATION ===\n");
        
        return isValid;
    }

    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            System.err.println("Error in hmacSHA512: " + ex.getMessage());
            ex.printStackTrace();
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP:" + e.getMessage();
        }
        return ipAddress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Utility methods (giữ nguyên)
    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }
}