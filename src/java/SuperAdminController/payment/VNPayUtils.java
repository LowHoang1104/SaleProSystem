package SuperAdminController.payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Tiện ích VNPay - chuẩn hóa tạo chữ ký và URL
 */
public class VNPayUtils {

    /**
     * Tạo chữ ký HMAC SHA512
     */
    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(result.length * 2);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Tạo query string từ Map parameters (URL encode)
     */
    public static String buildQuery(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if (query.length() > 0) {
                    query.append("&");
                }
                try {
                    query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
                         .append("=")
                         .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return query.toString();
    }

    /**
     * Tạo chuỗi hash (KHÔNG encode value)
     */
    public static String buildHashData(Map<String, String> params) {
        StringBuilder hashData = new StringBuilder();
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if (hashData.length() > 0) {
                    hashData.append("&");
                }
                hashData.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return hashData.toString();
    }

    /**
     * Generate transaction reference
     */
    public static String generateTxnRef() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Format date cho VNPay
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
