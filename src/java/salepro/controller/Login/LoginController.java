package salepro.controller.Login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.catalina.User;
import salepro.dal.DBContext1;
import salepro.dal.DBContext2;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PermissionDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;
import salepro.models.Permissions;
import salepro.models.Stores;
import salepro.models.SuperAdmin.ShopOwner;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginController", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        CustomerDAO customerDA = new CustomerDAO();
        StoreDAO storeDA = new StoreDAO();
        PurchaseDAO purchaseDA = new PurchaseDAO();
        InvoiceDAO invoiceDA = new InvoiceDAO();

//mã hóa 
//        String encoded = Base64.getEncoder().encodeToString(password.getBytes());
//        password=encoded;
        if (session.getAttribute("ShopOwner") == null) {
            request.setAttribute("Error", "Hãy vào Shop của bạn trước khi đăng nhập!");
            request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
        }
        if (login.equals("1")) {
            UserDAO userda = new UserDAO();
            if (userda.checkUser(account, password)) {
                //lưu session storecurrent trước khi đăng nhập            
                if (userda.getUserbyAccountAndPass(account, password).getRoleId() != 1) {
                    ArrayList<Stores> data = new ArrayList<>();
                    data.add(userda.getUserbyAccountAndPass(account, password).getStoreByUserId());
                    session.setAttribute("storecurrent", data);
                } else {
                    session.setAttribute("storecurrent", storeDA.getData());
                }
                session.setAttribute(login, login);
                session.setAttribute("user", userda.getUserbyAccountAndPass(account, password));
                request.getRequestDispatcher("HomepageController").forward(request, response);
            } else {
                request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
            }
        } else if (login.equals("2")) {
            UserDAO userda = new UserDAO();
            if (userda.checkAdmin(account, password)) {
                if (userda.getUserbyAccountAndPass(account, password).getRoleId() != 1) {
                    ArrayList<Stores> data = new ArrayList<>();
                    data.add(userda.getUserbyAccountAndPass(account, password).getStoreByUserId());
                    session.setAttribute("storecurrent", data);
                } else {
                    session.setAttribute("storecurrent", storeDA.getData());
                }
                session.setAttribute("user", userda.getUserbyAccountAndPass(account, password));
                response.sendRedirect("CashierServlet");
            } else {
                PermissionDAO perDA = new PermissionDAO();
                List<Permissions> perMissionDATA = new ArrayList<>();
                Users temp = userda.getUserbyAccountAndPass(account, password);
                if (temp != null) {
                    session.setAttribute("user", userda.getUserbyAccountAndPass(account, password));

                    perMissionDATA = perDA.getPermissionsByEmployeeType(temp.getEmployeeByUserId().getEmployeeID());
                    boolean check = true;
                    for (int i = 0; i < perMissionDATA.size(); i++) {
                        //check neu la admin hoac la employee co quyen tao hoa don va thanh toan ko moi cho dang nhap
                        if (perMissionDATA.get(i).getPermissionID() == 9 || perMissionDATA.get(i).getPermissionID() == 8) {
                            response.sendRedirect("CashierServlet");
                            check = false;
                        }
                    }
                    if (check) {
                        request.setAttribute("Error", "Bạn đã bị giới hạn quyền!");
                        request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
                    }
                } else {
                    //luu store vao session
                    session.setAttribute("storecurrent", userda.getUserbyAccountAndPass(account, password).getStoreByUserId());

                    request.setAttribute("Error", "Tài khoản hoặc mật khẩu không đúng!");
                    request.getRequestDispatcher("view/jsp/Login.jsp").forward(request, response);
                }
            }
        } else {
            response.sendRedirect("view/jsp/Login.jsp");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String shop = request.getParameter("shop");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        ShopOwnerDAO da = new ShopOwnerDAO();
        String error = "";
        if (da.checkExistShopOwner(shop)) {
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            error += "Tên cửa hàng đã tồn tại";
        } else if (da.checkExistEmail(email)) {
            error += "Email đã tồn tại";
            request.setAttribute("storeName", shop);
            request.setAttribute("phone", phone);
        } else if (da.checkExistPhone(phone)) {
            error += "Số điện thoại đã tồn tại";
            request.setAttribute("storeName", shop);
            request.setAttribute("email", email);
        } else if (!(password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[^a-zA-Z0-9].*"))) {
            error = "Sai format password"
                    + "<br>có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số";
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("storeName", shop);
        }
        if (!error.isEmpty()) {
            request.setAttribute("error", error);
            request.setAttribute("name", name);
            request.getRequestDispatcher("view/jsp/sign_up.jsp").forward(request, response);

        } else {
            String encoded = Base64.getEncoder().encodeToString(password.getBytes());

            ShopOwner newshop = new ShopOwner(shop, name, email, phone, encoded, 1, new Date());
            String message = "";
            try {
                da.createShopOwner(newshop);
                message = URLEncoder.encode("Tạo Tài Khoản Thành Công!", "UTF-8");
            } catch (Exception e) {
                message = URLEncoder.encode("Tạo Tài Khoản Chưa Thành Công! Vui lòng thử lại!", "UTF-8");
            }
            response.sendRedirect("view/jsp/Homepage.jsp?msg=" + message);

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
