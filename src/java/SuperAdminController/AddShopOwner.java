/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SuperAdminController;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static java.lang.System.Logger.Level.ALL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import static org.apache.poi.ss.formula.functions.TextFunction.LOWER;
import static org.apache.poi.ss.formula.functions.TextFunction.UPPER;
import salepro.dao.ShopOwnerDAO;
import salepro.models.up.ShopOwner;
import salepro.service.ResetPassword;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddShopOwner", urlPatterns = {"/AddShopOwner"})
public class AddShopOwner extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*";
    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("qua day roi");

        ShopOwnerDAO da = new ShopOwnerDAO();
        String shopName = request.getParameter("shopName");
        String ownerName = request.getParameter("ownerName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");
        String date = request.getParameter("enddate");
        System.out.println(date + " " + phone);

        if (da.checkExistShopOwner(shopName)) {
            response.getWriter().print("Tên shop đã tồn tại");
        } else if (!isValidEmail(email)) {
            response.getWriter().print("Email sai định dạng");
        } else if (da.checkExistEmail(email)) {
            response.getWriter().print("Email đã tồn tại");
        } else if (!isValidPhone(phone)) {
            response.getWriter().print("Số điện thoại sai định dạng");
        } else if (da.checkExistPhone(phone)) {
            response.getWriter().print("Số điện thoại đã tồn tại");
        } else if (isDateInPast(date)) {
            response.getWriter().print("Thời gian phải lớn hơn ngày hiện tại");
        } else {
            ShopOwner newshop = new ShopOwner();
            newshop.setEmail(email);
            newshop.setOwnerName(ownerName);
            newshop.setPhone(phone);
            newshop.setShopName(shopName);
            String pass = generatePassword();
            String passencode = Base64.getEncoder().encodeToString(pass.getBytes());
            newshop.setPasswordHash(passencode);
            try {
                da.createShopOwner(newshop);
                if (date != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
                    date = LocalDate.parse(date, formatter).toString();
                }
                ResetPassword send = new ResetPassword();
                send.sendEmailAdminShopOwner(email, shopName, pass);
                if (date != null) {
                    da.updateTrial(shopName, date);
                }
                response.getWriter().print("OKE");

            } catch (Exception e) {
                response.getWriter().print("Tạo mới không thành công vui lòng thử lại");
            }

        }
    }

    public static boolean isDateInPast(String dateStr) {
        try {
            LocalDate inputDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE); // yyyy-MM-dd
            LocalDate today = LocalDate.now();
            return inputDate.isBefore(today);
        } catch (Exception e) {
            return false; // lỗi format hoặc null thì coi như không hợp lệ
        }
    }

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        for (int i = 4; i < 8; i++) {
            passwordChars.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        // Xáo trộn để ngẫu nhiên vị trí
        Collections.shuffle(passwordChars);

        // Chuyển thành chuỗi
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(regex);
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^0\\d{9}$"; // Bắt đầu bằng số 0, theo sau là 9 chữ số
        return phone != null && phone.matches(regex);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
