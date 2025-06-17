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

        StoreFundDAO sfDao = new StoreFundDAO();
        List<StoreFund> listStoreFundCash = sfDao.getFundsByStoreAndType(1, "Cash");

        StoreFund currentStoreFund = (StoreFund) session.getAttribute("currentStoreFund");
        if (currentStoreFund == null && !listStoreFundCash.isEmpty()) {
            currentStoreFund = listStoreFundCash.get(0);
            session.setAttribute("currentStoreFund", currentStoreFund);
            System.out.println("Set default currentStoreFund: " + currentStoreFund.getFundID());
        }

        String sessionTypeStr = request.getParameter("sessionType");
        if (sessionTypeStr == null) {
            sessionTypeStr = "Opening";
        }

        CashCountSessionDAO ccDao = new CashCountSessionDAO();
        List<CurrencyDenominations> currencyDenominations = ccDao.getDenominationses();
        session.setAttribute("currencyDenominations", currencyDenominations);

        List<CashCountDetail> cashCountDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

        if (cashCountDetails == null && currentStoreFund != null) {
            System.out.println("Loading cashCountDetails from database for fundId: " + currentStoreFund.getFundID());
            int sessionId = ccDao.getSessionIdMaxWithFundId(currentStoreFund.getFundID());
            System.out.println("Latest sessionId: " + sessionId);

            if (sessionId > 0) {
                cashCountDetails = ccDao.getCashCountDetailsBySessionId(sessionId);
                System.out.println("Loaded details from DB: " + (cashCountDetails == null ? "NULL" : cashCountDetails.size()));
            }
        }

        if (cashCountDetails == null) {
            cashCountDetails = new ArrayList<>();
            System.out.println("Created empty cashCountDetails list");
        }

        double totalCounted = calculateTotalCounted(cashCountDetails);
        double difference = 0;
        if (currentStoreFund != null) {
            difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());
        }

        request.setAttribute("storeFundCash", listStoreFundCash);
        request.setAttribute("cashCountDetails", cashCountDetails); // FIXED: Use consistent naming
        request.setAttribute("sessionType", sessionTypeStr);
        request.setAttribute("totalCounted", totalCounted);
        request.setAttribute("difference", difference);

        System.out.println("Forwarding to JSP with " + cashCountDetails.size() + " details, total: " + totalCounted);
        request.getRequestDispatcher(CASH_AJAX).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("=== doPost() called with action: " + action + " ===");

        if ("changeFund".equals(action)) {
            changeFund(request, response);
        } else if ("calculateAmount".equals(action)) {
            calculateAmount(request, response);
        } else if ("saveCashCount".equals(action)) {
            saveCashCount(request, response);
        } else if ("clearTempData".equals(action)) {
            clearTempData(request, response);
        } else {
            System.out.println("Unknown action: " + action);
            doGet(request, response);
        }
    }

    private double calculateTotalCounted(List<CashCountDetail> cashCountDetails) {
        if (cashCountDetails == null || cashCountDetails.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (CashCountDetail detail : cashCountDetails) {
            if (detail != null) {
                total += detail.getAmount();
            }
        }
        return total;
    }

    private void changeFund(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String fundIdStr = request.getParameter("fundId");
        String sessionTypeStr = request.getParameter("sessionType");

        System.out.println("changeFund - fundId: " + fundIdStr + ", sessionType: " + sessionTypeStr);

        if (fundIdStr == null || fundIdStr.isEmpty()) {
            doGet(request, response);
            return;
        }

        try {
            int fundId = Integer.parseInt(fundIdStr);

            StoreFundDAO sfDao = new StoreFundDAO();
            StoreFund currentStoreFund = sfDao.getFundById(fundId);

            if (currentStoreFund == null) {
                System.out.println("Fund not found with ID: " + fundId);
                doGet(request, response);
                return;
            }

            session.removeAttribute("tempCashCountDetails");
            session.setAttribute("currentStoreFund", currentStoreFund);

            request.setAttribute("sessionType", sessionTypeStr);
            request.setAttribute("currentFundId", fundId);

            System.out.println("Fund changed to: " + fundId + " (" + currentStoreFund.getFundName() + ")");
            doGet(request, response);

        } catch (NumberFormatException e) {
            System.out.println("Invalid fundId format: " + fundIdStr);
            doGet(request, response);
        }
    }

    private void calculateAmount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        System.out.println("=== calculateAmount() called ===");

        try {
            String fundIdStr = request.getParameter("fundId");
            String quantityStr = request.getParameter("quantity");
            String denominationIdStr = request.getParameter("denominationId");
            String denominationValueStr = request.getParameter("denominationValue");
            String sessionTypeStr = request.getParameter("sessionType");

            System.out.println("Params - fundId: " + fundIdStr + ", quantity: " + quantityStr
                    + ", denomId: " + denominationIdStr + ", value: " + denominationValueStr);

            int fundId = Integer.parseInt(fundIdStr);
            int quantity = Integer.parseInt(quantityStr);
            int denominationId = Integer.parseInt(denominationIdStr);
            int denominationValue = Integer.parseInt(denominationValueStr);

            List<CashCountDetail> tempDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

            if (tempDetails == null) {
                System.out.println("Creating new tempDetails list");
                tempDetails = new ArrayList<>();

                CashCountSessionDAO ccDao = new CashCountSessionDAO();
                int sessionId = ccDao.getSessionIdMaxWithFundId(fundId);

                if (sessionId > 0) {
                    List<CashCountDetail> existingDetails = ccDao.getCashCountDetailsBySessionId(sessionId);
                    if (existingDetails != null) {

                        for (CashCountDetail existing : existingDetails) {
                            CashCountDetail temp = new CashCountDetail();
                            temp.setDenominationID(existing.getDenominationID());
                            temp.setQuantity(existing.getQuantity());
                            temp.setAmount(existing.getAmount());
                            tempDetails.add(temp);
                        }
                        System.out.println("Loaded " + tempDetails.size() + " existing details");
                    }
                }
            }

            boolean found = false;
            for (CashCountDetail detail : tempDetails) {
                if (detail.getDenominationID() == denominationId) {
                    detail.setQuantity(quantity);
                    detail.setAmount(quantity * denominationValue);
                    found = true;
                    System.out.println("Updated existing detail for denomId " + denominationId);
                    break;
                }
            }

            if (!found) {
                CashCountDetail newDetail = new CashCountDetail();
                newDetail.setDenominationID(denominationId);
                newDetail.setQuantity(quantity);
                newDetail.setAmount(quantity * denominationValue);
                tempDetails.add(newDetail);
                System.out.println("Added new detail for denomId " + denominationId);
            }

            session.setAttribute("tempCashCountDetails", tempDetails);

            StoreFundDAO sfDao = new StoreFundDAO();
            StoreFund currentStoreFund = sfDao.getFundById(fundId);
            session.setAttribute("currentStoreFund", currentStoreFund);

            double totalCounted = calculateTotalCounted(tempDetails);
            double difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());

            System.out.println("Total counted: " + totalCounted + ", Difference: " + difference);

            // Set attributes for JSP
            request.setAttribute("sessionType", sessionTypeStr);
            request.setAttribute("currentFundId", fundId);

            doGet(request, response);

        } catch (Exception e) {
            System.out.println("Error in calculateAmount: " + e.getMessage());
            e.printStackTrace();
            doGet(request, response);
        }
    }

    private void saveCashCount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        System.out.println("=== SAVE CASH COUNT STARTED ===");

        try {
            String sessionType = request.getParameter("sessionType");
            String fundIdStr = request.getParameter("fundId");
            String notes = request.getParameter("notes");

            System.out.println("Save params - FundId: " + fundIdStr + ", SessionType: " + sessionType + ", Notes: " + notes);

            if (fundIdStr == null || sessionType == null) {
                System.out.println("Missing required parameters!");
                doGet(request, response);
                return;
            }

            int fundId = Integer.parseInt(fundIdStr);

            List<CashCountDetail> tempDetails = (List<CashCountDetail>) session.getAttribute("tempCashCountDetails");

            if (tempDetails == null) {
                tempDetails = new ArrayList<>();
                System.out.println("No temp data - creating empty session");
            }

            System.out.println("Final tempDetails size: " + tempDetails.size());

            StoreFund currentStoreFund = (StoreFund) session.getAttribute("currentStoreFund");
            if (currentStoreFund == null) {
                StoreFundDAO sfDao = new StoreFundDAO();
                currentStoreFund = sfDao.getFundById(fundId);
                session.setAttribute("currentStoreFund", currentStoreFund);
            }

            if (currentStoreFund == null) {
                System.out.println("ERROR: Cannot find fund with ID: " + fundId);
                doGet(request, response);
                return;
            }

            double totalCounted = calculateTotalCounted(tempDetails);

            System.out.println("Total counted: " + totalCounted);
            System.out.println("Current balance: " + currentStoreFund.getCurrentBalance());

            // Save to database
            int userId = 1; // TODO: Get from session
            CashCountSessionDAO ccDao = new CashCountSessionDAO();
            java.util.Date countDate = new java.util.Date();
            java.util.Date approvedAt = new java.util.Date();

            System.out.println("Calling insertCountSession...");
            boolean succ = ccDao.insertCountSession(fundId, sessionType, countDate, userId,
                    totalCounted, currentStoreFund.getCurrentBalance(), "Approved", notes, userId, approvedAt);

            System.out.println("insertCountSession result: " + succ);

            if (succ) {
                // Get the new session ID
                int newSessionId = ccDao.getSessionIdMaxWithFundId(fundId);
                System.out.println("New sessionId after insert: " + newSessionId);

                // Save details
                int savedDetails = 0;
                for (CashCountDetail detail : tempDetails) {
                    if (detail.getQuantity() > 0) {
                        System.out.println("Saving detail - DenomID: " + detail.getDenominationID()
                                + ", Quantity: " + detail.getQuantity() + ", Amount: " + detail.getAmount());

                        boolean detailResult = ccDao.insertCashCountDetail(newSessionId, detail.getDenominationID(),
                                detail.getQuantity(), detail.getAmount());
                        System.out.println("Detail save result: " + detailResult);

                        if (detailResult) {
                            savedDetails++;
                        }
                    }
                }
                System.out.println("Total details saved: " + savedDetails);

                session.removeAttribute("tempCashCountDetails");
                System.out.println("Cleared tempCashCountDetails from session");

                System.out.println("Cash count session saved successfully!");
            } else {
                System.out.println("Failed to save cash count session!");
            }

            doGet(request, response);

        } catch (Exception e) {
            System.out.println("EXCEPTION in saveCashCount: " + e.getMessage());
            e.printStackTrace();
            doGet(request, response);
        }
    }

    private void clearTempData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        HttpSession session = request.getSession();
//        session.removeAttribute("tempCashCountDetails");
//        System.out.println("Cleared temp data");
//
//        response.setContentType("application/json");
//        response.getWriter().write("{\"success\": true}");
    }
}
