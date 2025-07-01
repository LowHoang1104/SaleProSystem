/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.cashier;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import salepro.dao.FundTransactionDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.StoreFund;

@WebServlet(name = "CashServlet", urlPatterns = {"/CashServlet"})
public class CashServlet extends HttpServlet {

    private static final String CASH_AJAX = "view/jsp/employees/cash_ajax.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
//        int storeId = (Integer) session.getAttribute("storeId");

        StoreFundDAO sfDao = new StoreFundDAO();
        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        double amount = sfDao.getTotalBalanceByStoreAndType(1, "Cash");

        if (session.getAttribute("selectedFundId") == null) {
            session.setAttribute("selectedFundId", "");
        }

        request.setAttribute("storeFundCash", listStoreFundCash);
        request.setAttribute("amount", amount);
        request.getRequestDispatcher(CASH_AJAX).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        StoreFundDAO sfDao = new StoreFundDAO();

        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        request.setAttribute("storeFundCash", listStoreFundCash);
        if ("changeFund".equals(action)) {
            String fundIdStr = request.getParameter("fundId");
            if (fundIdStr == null || fundIdStr.isEmpty()) {
                double amount = sfDao.getTotalBalanceByStoreAndType(1, "Cash");
                request.setAttribute("amount", amount);

                request.setAttribute("selectedFundId", "");
                request.getRequestDispatcher(CASH_AJAX).forward(request, response);
            } else {
                int fundId = Integer.parseInt(fundIdStr);
                double amount = sfDao.getFundBalance(fundId);
                request.setAttribute("amount", amount);
                request.setAttribute("currentFundId", fundId);
                request.getRequestDispatcher(CASH_AJAX).forward(request, response);
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
