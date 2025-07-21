/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.Login;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import salepro.dao.TokenForgetPasswordDAO;
import salepro.dao.UserDAO;
import salepro.models.TokenForgetPassword;
import salepro.models.Users;
import salepro.service.ResetPassword;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ResetPasswordController", urlPatterns = {"/ResetPasswordController"})
public class ResetPasswordController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResetPasswordController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //goget check about data of OTP.jsp return value to doGet then they validate 2 cases, that is about overtime and OTP was wrong
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otpValue = request.getParameter("otpValue");
        String tokenValue = request.getParameter("tokenValue");
        TokenForgetPasswordDAO tokDA = new TokenForgetPasswordDAO();
        ResetPassword reset = new ResetPassword();
        TokenForgetPassword tfp = tokDA.getTokenForgetPasswordByToken(tokenValue);
        response.setContentType("text/html;charset=UTF-8");
        
        if (reset.isExpireTime(tfp.getExpiryTime()) || tokDA.checkTokenisReseted(tokenValue)) {
            response.getWriter().write("TIME");
        } else if (!tfp.getOtpCode().equals(otpValue)) {
            response.getWriter().write("OTP");
        } else {
            response.getWriter().write("OK");
        }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //this doPost was redirect by forgetPassword.jsp when a person try login on Page Login.jsp then they click on text"Quên mật khẩu" 
    //and they was redirect to forgetPassword.jsp first then redirect this page, so this method creats for check email exist
    //and generate token and opt while redection to OTP.jsp
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("op") != null && request.getParameter("op").equals("resetOTP")) {
            try {
                String oldToken = request.getParameter("oldToken");
                
                TokenForgetPasswordDAO tokDA = new TokenForgetPasswordDAO();
                ResetPassword reset = new ResetPassword();
                UserDAO userDA = new UserDAO();
                
                Users user = userDA.getUserbyOldToken(oldToken);
                //set token cu thanh token nay bi reseted
                tokDA.setTokenisReseted(oldToken);

                //tao ra token moi
                TokenForgetPassword fp = new TokenForgetPassword(reset.generateToken(),
                        reset.expireDateTime(), true, reset.generateOTP());

                //insert vao bang tokenForget
                tokDA.insertTokenForgetPassword(fp, user);
                reset.sendEmail(user.getEmail(), fp.getOtpCode(), user.getFullName());

                //gui token lai thong qua ajax
                response.getWriter().write(fp.getToken());
            } catch (Exception e) {
                response.getWriter().write("FAIL");
            }
            
        } else if (request.getParameter("op") != null && request.getParameter("op").equals("resetPassword")) {
            try {
                ResetPassword reset = new ResetPassword();
                TokenForgetPasswordDAO tokDA = new TokenForgetPasswordDAO();
                String token = request.getParameter("token");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");
                TokenForgetPassword tfp=tokDA.getTokenForgetPasswordByToken(token);
                if(tfp==null||reset.isExpireTime(tfp.getExpiryTime())){
                    response.getWriter().write("Time");
                }
                else if (!password1.equals(password2)) {
                    response.getWriter().write("Same");
                }
                else if (!(password1.matches(".*[A-Z].*") && password1.matches(".*[0-9].*") && password1.matches(".*[^a-zA-Z0-9].*"))) {
                    response.getWriter().write("Format");
                } else {
                    UserDAO userDA = new UserDAO();
                    
                    userDA.updatePasswordByToken(token, password1);
                    tokDA.updateIsUsedByToken(token);
                    
                    response.getWriter().write("OKE");
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
            
            UserDAO userDA = new UserDAO();
            String email = request.getParameter("email");
            Users user = userDA.getUserByEmail(email);
            if (user == null) {
                request.setAttribute("mess", "Email không tồn tại");
                request.getRequestDispatcher("view/jsp/Login/forgetPassword.jsp").forward(request, response);
            } else {
                ResetPassword reset = new ResetPassword();
                TokenForgetPasswordDAO tokDA = new TokenForgetPasswordDAO();
                TokenForgetPassword fp = new TokenForgetPassword(reset.generateToken(),
                        reset.expireDateTime(), true, reset.generateOTP());
                //insert vao bang tokenForget
                tokDA.insertTokenForgetPassword(fp, user);
                reset.sendEmail(email, fp.getOtpCode(), user.getFullName());
                response.sendRedirect("view/jsp/Login/OTP.jsp?token=" + fp.getToken());
            }
        }
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
