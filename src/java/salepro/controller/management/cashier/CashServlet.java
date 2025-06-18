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
import salepro.dao.CashCountSessionDAO;
import salepro.dao.StoreFundDAO;
import salepro.models.CashCountDetail;
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
            System.out.println("No temp data - creating new temp list and loading from DB");
            cashCountDetails = new ArrayList<>();

            // Load từ DB nếu có
            int sessionId = ccDao.getSessionIdMaxWithFundId(currentStoreFund.getFundID());
            System.out.println("Latest sessionId: " + sessionId);

            if (sessionId > 0) {
                List<CashCountDetail> dbDetails = ccDao.getCashCountDetailsBySessionId(sessionId);
                if (dbDetails != null && !dbDetails.isEmpty()) {
                    
                    for (CashCountDetail dbDetail : dbDetails) {
                        CashCountDetail tempDetail = new CashCountDetail();
                        tempDetail.setDenominationID(dbDetail.getDenominationID());
                        tempDetail.setQuantity(dbDetail.getQuantity());
                        tempDetail.setAmount(dbDetail.getAmount());
                        cashCountDetails.add(tempDetail);
                    }
                    System.out.println("Loaded " + cashCountDetails.size() + " details from DB into temp");
                }
            }

            session.setAttribute("tempCashCountDetails", cashCountDetails);
            System.out.println("Saved temp data to session");

        } else if (cashCountDetails != null) {
            System.out.println("Using existing temp data: " + cashCountDetails.size() + " details");
        } else {
            cashCountDetails = new ArrayList<>();
            session.setAttribute("tempCashCountDetails", cashCountDetails);
            System.out.println("Created empty temp list");
        }

        double totalCounted = calculateTotalCounted(cashCountDetails);
        double difference = 0;
        if (currentStoreFund != null) {
            difference = Math.abs(totalCounted - currentStoreFund.getCurrentBalance());
        }

        request.setAttribute("storeFundCash", listStoreFundCash);
        request.setAttribute("cashCountDetails", cashCountDetails);
        request.setAttribute("sessionType", sessionTypeStr);
        request.setAttribute("totalCounted", totalCounted);
        request.setAttribute("difference", difference);

        System.out.println("=== doGet() Summary ===");
        System.out.println("Details count: " + cashCountDetails.size());
        System.out.println("Total counted: " + totalCounted);
        System.out.println("Temp data in session: " + (session.getAttribute("tempCashCountDetails") != null));

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
            doGet(request, response);
        }
    }

    private double calculateTotalCounted(List<CashCountDetail> cashCountDetails) {
        if (cashCountDetails == null || cashCountDetails.isEmpty()) {
            System.out.println("cash null");
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

            // XÓA temp data khi đổi fund 
            session.removeAttribute("tempCashCountDetails");
            session.setAttribute("currentStoreFund", currentStoreFund);
            System.out.println("=== CLEARED temp data when changing fund ===");

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
                System.out.println("ERROR: tempDetails should not be null here! Creating emergency empty list.");
                tempDetails = new ArrayList<>();
                session.setAttribute("tempCashCountDetails", tempDetails);
            }

            System.out.println("Current tempDetails size before update: " + tempDetails.size());

            // Cập nhật hoặc thêm mới detail cho denomination hiện tại
            boolean found = false;
            for (CashCountDetail detail : tempDetails) {
                if (detail.getDenominationID() == denominationId) {
                    detail.setQuantity(quantity);
                    detail.setAmount(quantity * denominationValue);
                    found = true;
                    System.out.println("Updated existing detail for denomId " + denominationId
                            + " - quantity: " + quantity + ", amount: " + (quantity * denominationValue));
                    break;
                }
            }

            if (!found) {
                CashCountDetail newDetail = new CashCountDetail();
                newDetail.setDenominationID(denominationId);
                newDetail.setQuantity(quantity);
                newDetail.setAmount(quantity * denominationValue);
                tempDetails.add(newDetail);
                System.out.println("Added new detail for denomId " + denominationId
                        + " - quantity: " + quantity + ", amount: " + (quantity * denominationValue));
            }

            // Cập nhật session
            session.setAttribute("tempCashCountDetails", tempDetails);
            System.out.println("Updated tempDetails size: " + tempDetails.size());

            // Load thông tin fund
            StoreFundDAO sfDao = new StoreFundDAO();
            StoreFund currentStoreFund = sfDao.getFundById(fundId);
            session.setAttribute("currentStoreFund", currentStoreFund);

            // Set attributes for JSP
            request.setAttribute("sessionType", sessionTypeStr);
            request.setAttribute("currentFundId", fundId);

            System.out.println("=== calculateAmount() finished - calling doGet() ===");
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

            int userId = 1; // TODO: Get from session
            CashCountSessionDAO ccDao = new CashCountSessionDAO();
            java.util.Date countDate = new java.util.Date();
            java.util.Date approvedAt = new java.util.Date();

            System.out.println("Calling insertCountSession...");
            int newSessionId = ccDao.insertCountSessionAndGetId(fundId, sessionType, countDate, userId,
                    totalCounted, currentStoreFund.getCurrentBalance(), "Approved", notes, userId, approvedAt);

            System.out.println("insertCountSession result: " + newSessionId);

            if (newSessionId > 0) {
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
        HttpSession session = request.getSession();
        session.removeAttribute("tempCashCountDetails");
        System.out.println("=== MANUALLY CLEARED tempCashCountDetails ===");

        doGet(request, response);

    }
}
