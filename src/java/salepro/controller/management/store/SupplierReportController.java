/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.store;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import salepro.dao.ReportSupplierDAO;
import salepro.models.SupplierReportModel;

/**
 *
 * @author tungd
 */
@WebServlet(name = "ReportController", urlPatterns = {"/supplierreport"})
public class SupplierReportController extends HttpServlet {

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
            out.println("<title>Servlet ReportController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportController at " + request.getContextPath() + "</h1>");
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
        ReportSupplierDAO rpdao = new ReportSupplierDAO();
        ArrayList<SupplierReportModel> data;
        data = rpdao.getReport();
        HttpSession session = request.getSession();
        session.setAttribute("viewMode", "table");
        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/InventoryReport/SupplierReport.jsp").forward(request, response);
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

        ReportSupplierDAO rpdao = new ReportSupplierDAO();
        ArrayList<SupplierReportModel> data = null;
        String keyword = request.getParameter("search");

        HttpSession session = request.getSession();
        String view = request.getParameter("view");
        if (view != null && (view.equals("chart") || view.equals("table"))) {
            session.setAttribute("viewMode", view);
        }

        if (request.getParameter("today") != null) {
            data = rpdao.getReportToday();
        } else if (request.getParameter("yesterday") != null) {
            data = rpdao.getReportYesterday();
        } else if (request.getParameter("thisWeek") != null) {
            data = rpdao.getReportThisWeek();
        } else if (request.getParameter("lastWeek") != null) {
            data = rpdao.getReportLastWeek();
        } else if (request.getParameter("last7days") != null) {
            data = rpdao.getReportLast7Days();
        } else if (request.getParameter("thisMonth") != null) {
            data = rpdao.getReportThisMonth();
        } else if (request.getParameter("lastMonth") != null) {
            data = rpdao.getReportLastMonth();
        } else if (request.getParameter("last30days") != null) {
            data = rpdao.getReportLast30Days();
        } else if (request.getParameter("thisQuarter") != null) {
            data = rpdao.getReportThisQuarter();
        } else if (request.getParameter("lastQuarter") != null) {
            data = rpdao.getReportLastQuarter();
        } else if (request.getParameter("thisYear") != null) {
            data = rpdao.getReportThisYear();
        } else if (request.getParameter("lastYear") != null) {
            data = rpdao.getReportLastYear();
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            data = rpdao.getReportByKeyword(validateKeyword(keyword));
        } else {
            data = rpdao.getReport(); // hoặc mặc định nào đó
        }
        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/InventoryReport/SupplierReport.jsp").forward(request, response);
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

    private String validateKeyword(String kw) {
        String[] list = kw.trim().split("[^\\p{L}]+");
        String key = "";
        for (int i = 0; i < list.length; i++) {
            if (i == list.length - 1) {
                key += list[i];
            } else {
                key += list[i] + " ";
            }
        }
        return key;
    }
}
