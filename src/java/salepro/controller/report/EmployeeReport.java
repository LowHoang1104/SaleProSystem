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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import salepro.dao.ReportEmployeeDAO;
import salepro.models.EmployeeReportModel;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "EmployeeReport", urlPatterns = {"/EmployeeReport"})
public class EmployeeReport extends HttpServlet {

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
            out.println("<title>Servlet EmployeeReport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeReport at " + request.getContextPath() + "</h1>");
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
        ReportEmployeeDAO rpdao = new ReportEmployeeDAO();
        ArrayList<EmployeeReportModel> data;
        data = rpdao.getReport();
        HttpSession session = request.getSession();
        session.setAttribute("viewMode", "table");
        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/Reports/EmployeeReport.jsp").forward(request, response);
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
        ReportEmployeeDAO rpdao = new ReportEmployeeDAO();
        ArrayList<EmployeeReportModel> data = null;
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
        session.setAttribute("lastFilter", detectFilter(request)); // lưu filter dưới dạng chuỗi
        session.setAttribute("lastKeyword", keyword);              // nếu có keyword tìm kiếm

        request.setAttribute("data", data);
        request.getRequestDispatcher("view/jsp/admin/Reports/EmployeeReport.jsp").forward(request, response);
    }

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

    private String detectFilter(HttpServletRequest request) {
        if (request.getParameter("today") != null) {
            return "today";
        }
        if (request.getParameter("yesterday") != null) {
            return "yesterday";
        }
        if (request.getParameter("thisWeek") != null) {
            return "thisWeek";
        }
        if (request.getParameter("lastWeek") != null) {
            return "lastWeek";
        }
        if (request.getParameter("last7days") != null) {
            return "last7days";
        }
        if (request.getParameter("thisMonth") != null) {
            return "thisMonth";
        }
        if (request.getParameter("lastMonth") != null) {
            return "lastMonth";
        }
        if (request.getParameter("last30days") != null) {
            return "last30days";
        }
        if (request.getParameter("thisQuarter") != null) {
            return "thisQuarter";
        }
        if (request.getParameter("lastQuarter") != null) {
            return "lastQuarter";
        }
        if (request.getParameter("thisYear") != null) {
            return "thisYear";
        }
        if (request.getParameter("lastYear") != null) {
            return "lastYear";
        }
        if (request.getParameter("search") != null) {
            return "search";
        }
        return "all"; // mặc định
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
