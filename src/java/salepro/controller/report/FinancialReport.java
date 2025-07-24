/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import salepro.dao.FundTransactionDAO;
import salepro.dao.ReportDAO;
import salepro.dao.StoreDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "FinancialReport", urlPatterns = {"/FinancialReport"})
public class FinancialReport extends HttpServlet {

    ReportDAO da = new ReportDAO();
    StoreDAO storeDA = new StoreDAO();
    FundTransactionDAO funDA = new FundTransactionDAO();

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
            out.println("<title>Servlet FinancialReport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FinancialReport at " + request.getContextPath() + "</h1>");
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

        ArrayList<Integer> years = da.getYear();
        String month = "";
        int idStore = 0;
        String Funtype = "";
        int FundID = 0;
        String year = years.get(years.size() - 1).toString();

        //lấy giữ liệu từ jsp
        if (request.getParameter("yearFilter") != null) {
            year = request.getParameter("yearFilter");
        }
        if (request.getParameter("monthFilter") != null) {
            month = request.getParameter("monthFilter");
        }
        if (request.getParameter("storeFilter") != null) {
            idStore = Integer.parseInt(request.getParameter("storeFilter"));
        }
        if (request.getParameter("fundTypeFilter") != null) {
            Funtype = request.getParameter("fundTypeFilter");
        }
        //gửi dữ liệu cho thẻ select
        request.setAttribute("yearSelected", year);
        request.setAttribute("storeSelected", idStore);
        request.setAttribute("fundTypeSeleted", Funtype);
        request.setAttribute("monthSelected", month);

        System.out.println("Month: " + month + ", year:" + year + ",Store: " + idStore + ", Funtype: " + Funtype);

        //lấy month theo year
        ArrayList<Integer> months = da.getMonthByYear(year);

        // dữ liệu cho thà store
        request.setAttribute("stores", storeDA.getData());

        // dữ liệu cho thà years
        request.setAttribute("years", years);
        // dữ liệu cho thà months
        request.setAttribute("months", months);

        //du lieu for line chart
        request.setAttribute("monthsForChart", makeDataByArrayListMonth(months));
        request.setAttribute("monthsForDetail", dataToArraylist(makeDataByArrayListMonth(months)));
        request.setAttribute("doanhthuForChart", makeDataForChartLineDoanhThu(months, year));
        request.setAttribute("doanhthuForDetail", dataToArraylist(makeDataForChartLineDoanhThu(months, year)));
        request.setAttribute("chiphiForChart", makeDataForChartLineChiPhi(months, year));
        request.setAttribute("chiphiForDetail", dataToArraylist(makeDataForChartLineChiPhi(months, year)));
        request.setAttribute("loinhuanForChart", makeDataForChartLineLoiNhuan(months, year));
        request.setAttribute("loinhuanForDetail", dataToArraylist(makeDataForChartLineLoiNhuan(months, year)));

        String totleAmount = da.getIncomeByDate(month, year, idStore, 0, Funtype);
        String totalExpense = da.getExpenseByDate(month, year, idStore, 0, Funtype);

        //giữ liệu hiện thị ở head
        request.setAttribute("totalAmount", totleAmount);
        request.setAttribute("totalExpense", totalExpense);
        request.setAttribute("profit", String.format("%.2f", Double.parseDouble(totleAmount) - Double.parseDouble(totalExpense)));
        request.setAttribute("percent", String.format("%.2f", Double.parseDouble(totleAmount) / Double.parseDouble(totalExpense)) + "%");

        //giữ liệu cho chart donut và details của nó
        String[] arr = new String[3];
        arr[0] = da.getTotalPurchase(month, year, idStore, FundID, Funtype);
        arr[1] = da.getTotalSalary(month, year, idStore, FundID, Funtype);
        arr[2] = da.getTotalPurchaseOtherType(month, year, idStore, FundID, Funtype);

        request.setAttribute("DetailcircleData", arr);
        request.setAttribute("circleData", makeDataByArray(arr));

        //giữ liệu hiện thị ở foot
        String totalIncomeCash = da.getIncomeByDate("", "", 0, 0, "Cash");
        String totalExpenseCash = da.getExpenseByDate("", "", 0, 0, "Cash");
        request.setAttribute("totalAmountCash", String.format("%.2f", Double.parseDouble(totalIncomeCash) - Double.parseDouble(totalExpenseCash)));

        String totleIncomeBank = da.getIncomeByDate(month, year, 0, 0, "Bank");
        String totalExpenseBank = da.getExpenseByDate(month, year, 0, 0, "Bank");
        request.setAttribute("totalAmountBank", String.format("%.2f", Double.parseDouble(totleIncomeBank) - Double.parseDouble(totalExpenseBank)));

        request.setAttribute("totalAmountFund", String.format("%.2f", (Double.parseDouble(totalIncomeCash) - Double.parseDouble(totalExpenseCash)) + (Double.parseDouble(totleIncomeBank) - Double.parseDouble(totalExpenseBank))));
        //du liệu foot của foot
        request.setAttribute("FundTransactions", funDA.getData());
        request.getRequestDispatcher("view/jsp/admin/Reports/FinancialReport.jsp").forward(request, response);

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
        processRequest(request, response);
    }

    public String makeDataByArray(String[] arr) {
        String result = "[";
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                result += arr[i] + "";
                break;
            }
            result += arr[i] + ",";
        }
        result += "]";
        return result;
    }

    public String makeDataByArrayListMonth(ArrayList<Integer> list) {
        String result = "[";
        for (int i = 0; i < list.size(); i++) {
            result += "'T";
            if (i == list.size() - 1) {
                result += list.get(i) + "'";
                break;
            }
            result += list.get(i) + "',";
        }
        result += "]";
        return result;
    }

    public String makeDataForChartLineDoanhThu(ArrayList<Integer> list, String year) {
        String result = "[";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                result += String.format("%.0f", Double.valueOf(da.getIncomeByDate(list.get(i).toString(), year, 0, 0, ""))) + "";
                break;
            }
            result += String.format("%.0f", Double.valueOf(da.getIncomeByDate(list.get(i).toString(), year, 0, 0, ""))) + ",";
        }
        result += "]";
        return result;
    }

    public String makeDataForChartLineChiPhi(ArrayList<Integer> list, String year) {
        String result = "[";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
//                System.out.println(i);
                result += String.format("%.0f", Double.valueOf(da.getExpenseByDate(list.get(i).toString(), year, 0, 0, ""))) + "";
                break;
            }
            result += String.format("%.0f", Double.valueOf(da.getExpenseByDate(list.get(i).toString(), year, 0, 0, ""))) + ",";
        }
        result += "]";
        return result;
    }

    public String makeDataForChartLineLoiNhuan(ArrayList<Integer> list, String year) {
        String result = "[";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                result += String.format("%.0f", Double.parseDouble(da.getIncomeByDate(list.get(i).toString(), year, 0, 0, ""))
                        - Double.parseDouble(da.getExpenseByDate(list.get(i).toString(), year, 0, 0, ""))) + "";
                break;
            }
            result += String.format("%.0f", Double.parseDouble(da.getIncomeByDate(list.get(i).toString(), year, 0, 0, ""))
                    - Double.parseDouble(da.getExpenseByDate(list.get(i).toString(), year, 0, 0, ""))) + ",";
        }
        result += "]";
        return result;
    }

    public ArrayList<String> dataToArraylist(String str) {
        ArrayList<String> data = new ArrayList<>();
        String[] arr = str.substring(1, str.length() - 1).split(",");
        for (int i = 0; i < arr.length; i++) {
            data.add(arr[i]);
        }
        return data;
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
