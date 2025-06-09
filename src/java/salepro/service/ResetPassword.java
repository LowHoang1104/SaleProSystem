/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ADMIN
 */
public class ResetPassword {

    //Email: longnh110604@gmail.com
    //Password: iamq qncq ukyb nrnl
    private final int LIMIT_MINUS = 3;
    static final String from = "longnh110604@gmail.com";
    static final String password = "iamq qncq ukyb nrnl";

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireDateTime() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }

    public boolean isExpireTime(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(1_000_000);
        return String.format("%06d", otp);
    }

    public boolean sendEmail(String to, String otp, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // dung authenticator de dang nhap gmail
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        //phien lam viec
        Session session = Session.getInstance(props, auth);

        //tao 1 tin nhan
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/html; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Reset Password", "UTF-8");
            String content = String.format("""
        
    <body>
        <div class="container">
            <div class="header">Mã Xác Nhận Google</div>
            <div class="content">
                <p>Kính gửi Người dùng Google,</p>
                <p>Chúng tôi đã nhận được yêu cầu truy cập vào tài khoản Google của bạn <strong>longnh110604@gmail.com</strong> qua địa chỉ email của bạn. Mã xác nhận Google của bạn là:</p>
                <div class="code">%s</div>
                <p>Nếu bạn không yêu cầu mã này, có thể có người khác đang cố gắng truy cập vào tài khoản Google <strong>longnh110604@gmail.com</strong>. Vui lòng không chia sẻ hoặc gửi mã này cho bất kỳ ai.</p>
                <p>Bạn nhận được email này vì địa chỉ email này được liệt kê là email khôi phục cho tài khoản Google <strong>longnh110604@gmail.com</strong>. Nếu thông tin này không đúng, vui lòng <a href="#">nhấn vào đây</a> để xóa địa chỉ email của bạn khỏi tài khoản Google đó.</p>
                <p class="footer">Trân trọng,</p>
                <p>Đội ngũ Google</p>
            </div>
        </div>
    </body>
    </html>
    """, otp);
            msg.setContent(content, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Send successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Send error");
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {
        ResetPassword a = new ResetPassword();
        System.out.println(a.generateOTP());
    }

}
