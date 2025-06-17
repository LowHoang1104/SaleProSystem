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
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.User;
import salepro.dao.CashCountSessionDAO;
import salepro.dao.FundTransactionDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.CashCountDetail;
import salepro.models.CashCountSession;
import salepro.models.CurrencyDenominations;
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
        StoreFund currentStoreFund = (StoreFund) session.getAttribute("currentStoreFund");
        if (currentStoreFund == null) {
            currentStoreFund = listStoreFundCash.get(0);
        }

        String sessionTypeStr = "Opening";

        request.setAttribute("sessionType", sessionTypeStr);

        CashCountSessionDAO ccDao = new CashCountSessionDAO();
        List<CurrencyDenominations> currencyDenominations = ccDao.getDenominationses();
        session.setAttribute("currencyDenominations", currencyDenominations);

        List<CashCountDetail> cashCountDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

        if (cashCountDetails == null) {
            int sessionId = ccDao.getSessionIdMaxWithFundId(currentStoreFund.getFundID());
            cashCountDetails = ccDao.getCashCountDetailsBySessionId(sessionId);
        }

        request.setAttribute("storeFundCash", listStoreFundCash);
        request.setAttribute("tempCashCountDetails", cashCountDetails);

        double totalCounted = calculateTotalCounted(cashCountDetails);
        request.setAttribute("totalCounted", totalCounted);

        double difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());
        request.setAttribute("difference", difference);

        session.setAttribute("currentStoreFund", currentStoreFund);
        request.getRequestDispatcher(CASH_AJAX).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("changeFund".equals(action)) {
            changeFund(request, response);
        } else if ("calculateAmount".equals(action)) {
            calculateAmount(request, response);
        } else if ("saveCashCount".equals(action)) {
            saveCashCount(request, response);
        } else if ("clearTempData".equals(action)) {
            clearTempData(request, response);
        }

    }

    private double calculateTotalCounted(List<CashCountDetail> cashCountDetails) {
        double total = 0;
        for (CashCountDetail detail : cashCountDetails) {
            total += detail.getAmount();
        }
        return total;
    }

    private void changeFund(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fundIdStr = request.getParameter("fundId");
        String sessionTypeStr = request.getParameter("sessionType");

        if (fundIdStr == null || fundIdStr.isEmpty()) {
            request.getRequestDispatcher(CASH_AJAX).forward(request, response);
            return;
        }

        int fundId = Integer.parseInt(fundIdStr);
        StoreFundDAO sfDao = new StoreFundDAO();
        StoreFund currentStoreFund = sfDao.getFundById(fundId);

        // Clear temp data khi doi fundID
        session.removeAttribute("tempCashCountDetails");

        CashCountSessionDAO ccDao = new CashCountSessionDAO();
        int sessionId = ccDao.getSessionIdMaxWithFundId(fundId);
        List<CashCountDetail> cashCountDetails = ccDao.getCashCountDetailsBySessionId(sessionId);

        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        request.setAttribute("storeFundCash", listStoreFundCash);
        request.setAttribute("cashCountDetails", cashCountDetails);
        request.setAttribute("currentFundId", fundId);
        request.setAttribute("sessionType", sessionTypeStr);

        double totalCounted = calculateTotalCounted(cashCountDetails);
        request.setAttribute("totalCounted", totalCounted);

        double difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());
        request.setAttribute("difference", difference);

        session.setAttribute("currentStoreFund", currentStoreFund);
        request.getRequestDispatcher(CASH_AJAX).forward(request, response);
    }

    private void calculateAmount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fundIdStr = request.getParameter("fundId");
        String quantityStr = request.getParameter("quantity");
        String denominationIdStr = request.getParameter("denominationId");
        String denominationValueStr = request.getParameter("denominationValue");
        String sessionTypeStr = request.getParameter("sessionType");

        int fundId = Integer.parseInt(fundIdStr);
        int quantity = Integer.parseInt(quantityStr);
        int denominationId = Integer.parseInt(denominationIdStr);
        int denominationValue = Integer.parseInt(denominationValueStr);

        List<CashCountDetail> tempDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

        if (tempDetails == null) {
            tempDetails = new ArrayList<>();

            // load các record có sẵn db 
            CashCountSessionDAO ccDao = new CashCountSessionDAO();
            int sessionId = ccDao.getSessionIdMaxWithFundId(fundId);
            List<CashCountDetail> existingDetails = ccDao.getCashCountDetailsBySessionId(sessionId);

            // Copy vô tempDetails
            for (CashCountDetail existing : existingDetails) {
                CashCountDetail temp = new CashCountDetail();
                temp.setDenominationID(existing.getDenominationID());
                temp.setQuantity(existing.getQuantity());
                temp.setAmount(existing.getAmount());
                tempDetails.add(temp);
            }
        }

        boolean found = false;
        for (CashCountDetail detail : tempDetails) {
            if (detail.getDenominationID() == denominationId) {
                detail.setQuantity(quantity);
                detail.setAmount(quantity * denominationValue);
                found = true;
                break;
            }
        }

        if (!found) {
            CashCountDetail newDetail = new CashCountDetail();
            newDetail.setDenominationID(denominationId);
            newDetail.setQuantity(quantity);
            newDetail.setAmount(quantity * denominationValue);
            tempDetails.add(newDetail);
        }

        double totalCounted = calculateTotalCounted(tempDetails);

        StoreFundDAO sfDao = new StoreFundDAO();
        StoreFund currentStoreFund = sfDao.getFundById(fundId);
        double difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());

        session.setAttribute("tempCashCountDetails", tempDetails);

        request.setAttribute("cashCountDetails", tempDetails); // Dùng cái này thay vì sessionScope
        request.setAttribute("totalCounted", totalCounted);
        request.setAttribute("difference", difference);
        request.setAttribute("currentFundId", fundId);
        request.setAttribute("sessionType", sessionTypeStr);

        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");
        request.setAttribute("storeFundCash", listStoreFundCash);

        session.setAttribute("currentStoreFund", currentStoreFund);

        request.getRequestDispatcher(CASH_AJAX).forward(request, response);
    }

    private void saveCashCount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            String sessionType = request.getParameter("sessionType");
            int fundId = Integer.parseInt(request.getParameter("fundId"));
            String notes = request.getParameter("notes");

            List<CashCountDetail> tempDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

            if (tempDetails == null) {
                System.out.println("No temp data, loading from database...");
                tempDetails = new ArrayList<>();

                CashCountSessionDAO ccDao = new CashCountSessionDAO();
                int sessionId = ccDao.getSessionIdMaxWithFundId(fundId);
                System.out.println("Latest sessionId for fundId " + fundId + ": " + sessionId);

                if (sessionId > 0) {
                    List<CashCountDetail> existingDetails = ccDao.getCashCountDetailsBySessionId(sessionId);
                    System.out.println("Existing details size: " + (existingDetails == null ? "NULL" : existingDetails.size()));

                    if (existingDetails != null && !existingDetails.isEmpty()) {
                        tempDetails.addAll(existingDetails);
                    }
                }

                System.out.println("Final tempDetails size: " + tempDetails.size());
            }

            double totalCounted = calculateTotalCounted(tempDetails);
            StoreFund currentStoreFund = (StoreFund) session.getAttribute("currentStoreFund");
//            double difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());

//            User user  =(User) session.getAttribute("user");
//            if(user == null){
//                
//            }
            int userId = 1;
            // Lưu vào database
            CashCountSessionDAO ccDao = new CashCountSessionDAO();
            java.util.Date countDate = new java.util.Date();
            java.util.Date approvedAt = new java.util.Date();

            boolean succ = ccDao.insertCountSession(fundId, sessionType, countDate, userId, totalCounted, currentStoreFund.getCurrentBalance(), "Approved", notes, userId, approvedAt);
            int sessionId = ccDao.getSessionIdMaxWithFundId(fundId);
            if (succ) {
                // Lưu từng detail
                for (CashCountDetail detail : tempDetails) {
                    if (detail.getQuantity() > 0) {
                        ccDao.insertCashCountDetail(sessionId, detail.getDenominationID(),
                                detail.getQuantity(), detail.getAmount());
                    }
                }
                session.removeAttribute("tempCashCountDetails");
            }

            doGet(request, response);
        } catch (Exception e) {
            doGet(request, response);
        }
    }

    private void clearTempData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("tempCashCountDetails");

        response.setContentType("application/json");
        response.getWriter().write("{\"success\": true}");
    }
}
