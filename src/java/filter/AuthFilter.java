/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import salepro.dao.PermissionDAO;
import salepro.dao.UserDAO;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    //Những uri mà chỉ admin mới được truy cập
    private static final Set<String> adminOnlyUris = Set.of("/ListUserServlet", "/ListUserPermissionServlet", "/ListShiftServlet");
    private static final Set<String> employeeManagement = Set.of("/ListWorkScheduleServlet", "/ListAttendanceServlet");

    //Kiểm tra có phải file tĩnh không 
    private boolean isStaticResource(String uri) {
        return uri.endsWith("Homepage.jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif") || uri.endsWith(".svg") || uri.endsWith(".woff") || uri.endsWith(".ttf") || uri.endsWith("eot");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        //Các DAO 
        PermissionDAO permisionDao = new PermissionDAO();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
//        session.setAttribute("user", new UserDAO().getUserById(1));

//        String uri = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        String path = uri.substring(contextPath.length());
//
//        //Bỏ qua tài nguyên tĩnh 
//        if (isStaticResource(uri)) {
//            fc.doFilter(request, response);
//            return;
//        }
//
//        //Các trang không cần login
//        if (path.contains("Login")) {
//            fc.doFilter(request, response);
//            return;
//        }
//
//        //Kiểm tra đăng nhập
////        HttpSession session = req.getSession(false);
//        Users user = (session != null) ? (Users) session.getAttribute("user") : null;
//        boolean isLoggedIn = (user != null);
//        if (!isLoggedIn) {
//            res.sendRedirect(contextPath + "/view/jsp/Login.jsp");
//            return;
//        }
//
//        //Phân quyền 
//        int role = isLoggedIn ? user.getRoleId() : 0;
//        if (role != 1) {
//            if (adminOnlyUris.contains(path)) {
//                res.sendRedirect("accessDenied.jsp");
//                return;
//            }
//            if (employeeManagement.contains(path) && !permisionDao.getPermissionsByUserId(user.getUserId()).contains(permisionDao.getPermissionById(2))) {
//                res.sendRedirect("accessDenied.jsp");
//                return;
//            }
//        }
//        //Chuyển tiếp nếu hợp lệ
        fc.doFilter(request, response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
