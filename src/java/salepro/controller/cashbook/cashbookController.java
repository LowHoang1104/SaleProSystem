/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.cashbook;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.FundTransactionDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.FundTransactions;
import salepro.models.StoreFund;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "cashbookController", urlPatterns = {"/cashbookController"})
public class cashbookController extends HttpServlet {

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
            out.println("<title>Servlet cashbookController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cashbookController at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FundTransactionDAO da = new FundTransactionDAO();
        StoreFundDAO fundDA = new StoreFundDAO();
        HttpSession session = request.getSession();
        int pagecurrent = 1;
        ArrayList<Stores> store = (ArrayList<Stores>) session.getAttribute("storecurrent");
        ArrayList<FundTransactions> data = new ArrayList<>();

        if (request.getParameter("storeid") == null) {
            session.setAttribute("funds", fundDA.getFundsByStoreId(store.get(0).getStoreID()));
        }

        Users a = (Users) session.getAttribute("user");
        if (a.getRoleId() == 1) {
            data = da.getDataByStoreId(store.get(0).getStoreID());

            //if nay xu ly viec chua kich vao nut selected nao
            if (request.getParameter("storeid") != null || session.getAttribute("shopcurrentIDa") != null) {
                if (session.getAttribute("shopcurrentIDa") == null && request.getParameter("storeid") != null) {
                    System.out.println("di lan dau");
                    session.setAttribute("shopcurrentIDa", request.getParameter("storeid"));
                    request.setAttribute("storeid", request.getParameter("storeid"));

                } else {
                    //neu nhu storeid gui tu jsp khac voi session shop current thi se doi lai funds  bat ca loi khi dung the a chuyen sang thi storeid = null ma 
                    if (!session.getAttribute("shopcurrentIDa").equals((String) request.getParameter("storeid")) && request.getParameter("storeid") != null) {
                        System.out.println("Khi khac nhau");
                        session.removeAttribute("fund");
                        session.setAttribute("shopcurrentIDa", request.getParameter("storeid"));
                    }

                    //gui lai len jsp de selected
                    request.setAttribute("storeid", session.getAttribute("shopcurrentIDa"));
                }
                session.setAttribute("funds", fundDA.getFundsByStoreId(Integer.parseInt((String) session.getAttribute("shopcurrentIDa"))));

                data = da.getDataByStoreId(Integer.parseInt((String) session.getAttribute("shopcurrentIDa")));
                System.out.println(session.getAttribute("fund"));

                //bat loi khi kich vao all nhung van con session cua thang fund
                if (request.getParameter("fund") != null && request.getParameter("fund").equals("")) {
                    session.removeAttribute("fund");
                }

                if (request.getParameter("fund") != null && !request.getParameter("fund").equals("")) {
                    session.setAttribute("fund", request.getParameter("fund"));

                }
                if (session.getAttribute("fund") != null) {
                    data = da.getDataByFundId(Integer.parseInt((String) session.getAttribute("fund")));
                    request.setAttribute("fundid", session.getAttribute("fund"));
                }
            }
        } else {
            data = da.getDataByStoreId(store.get(0).getStoreID());
        }

        //so lieu cho total 
        int outcome = 0, income = 0;
        for (FundTransactions temp : data) {
            if (temp.getTransactionType().equals("Income")) {
                income += temp.getAmount();
            }
            if (temp.getTransactionType().equals("Expense")) {
                outcome += temp.getAmount();
            }

        }
        request.setAttribute("totalIncome", income);
        request.setAttribute("totalOutcome", outcome);

        //phan trang
        int totalpage = 0;
        if ((data.size() % 10) == 0) {
            totalpage = data.size() / 10;
        } else {
            totalpage = (data.size() / 10) + 1;
        }
        request.setAttribute("totalpage", totalpage);
        if (request.getParameter("page") != null) {
            pagecurrent = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("pagecurrent", pagecurrent);

        }
        int pagesize = 10;
        int start = (pagecurrent - 1) * pagesize;
        int end = Integer.min(start + pagesize, data.size());
        data = new ArrayList<>(data.subList(start, end));

        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/CashBookManagement/cashbook.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        String op = request.getParameter("op");
        if (op != null && op.equals("listFundByStoreId")) {
            StoreFundDAO da = new StoreFundDAO();
            String shopId = request.getParameter("shopId");
            String type = request.getParameter("type");
            List<StoreFund> data = da.getFundsByStoreId(Integer.parseInt(shopId));
            if (type.equals("2")) {
                data = da.getFundsByStoreIdCash(Integer.parseInt(shopId));
            }
            StringBuilder sb = new StringBuilder();
            for (StoreFund f : data) {
                sb.append("<option value='").append(f.getFundID()).append("'>")
                        .append(f.getFundName())
                        .append("</option>");
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(sb.toString());
        } else if (op != null && op.equals("createIncomce")) {
            String store = request.getParameter("store");
            String fund = request.getParameter("fund");
            String description = request.getParameter("description");
            String amount = request.getParameter("amount");
            amount = amount.replace(".", "");
            try {
                Integer.parseInt(amount);
                if (Double.parseDouble(amount) < 0) {
                    response.getWriter().write("Giá trị không được nhỏ hơn 0");
                } else if (fund.equals("All")) {
                    response.getWriter().write("Hãy chọn quỹ");
                } else {
                    Users a = (Users) session.getAttribute("user");
                    FundTransactions temp = new FundTransactions(Integer.parseInt(fund), "Income", Double.parseDouble(amount), description, a.getUserId());
                    FundTransactionDAO da = new FundTransactionDAO();
                    da.createIncome(temp);
                    response.getWriter().write("OKE");
                }
            } catch (Exception e) {
                response.getWriter().write("Vui lòng chỉ điền số ở giá trị");
            }

        } else if (op != null && op.equals("createExpense")) {
            String store = request.getParameter("store");
            String fund = request.getParameter("fund");
            String description = request.getParameter("description");
            String amount = request.getParameter("amount");
            amount = amount.replace(".", "");
            if (Double.parseDouble(amount) < 0) {
                response.getWriter().write("Giá trị không được nhỏ hơn 0");
            } else if (fund.equals("All")) {
                response.getWriter().write("Hãy chọn quỹ");
            } else {
                Users a = (Users) session.getAttribute("user");
                FundTransactions temp = new FundTransactions(Integer.parseInt(fund), "Expense", Double.parseDouble(amount), description, a.getUserId());
                FundTransactionDAO da = new FundTransactionDAO();
                da.createExpense(temp);
                response.getWriter().write("OKE");
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
