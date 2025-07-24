/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.shifts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import salepro.dao.HolidayDAO;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "CheckHolidayServlet", urlPatterns = {"/CheckHolidayServlet"})
public class CheckHolidayServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckHoilidayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckHoilidayServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        //Các DAO
        HolidayDAO holidayDao = new HolidayDAO();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        try {
            // Lấy dữ liệu từ request
            String workDateStr = request.getParameter("workDate");
            String endDateStr = request.getParameter("endDate");
            String selectedDaysJson = request.getParameter("selectedDays");
            Gson gson = new Gson();
            List<Integer> selectedDays = gson.fromJson(selectedDaysJson, new TypeToken<List<Integer>>() {
            }.getType());
            LocalDate workDate = LocalDate.parse(workDateStr);
             LocalDate endDate = LocalDate.parse(endDateStr);

            boolean isHoliday = false;
            LocalDate currentDate = workDate;
            while (!currentDate.isAfter(endDate)) {
                int dayOfWeekValue = currentDate.getDayOfWeek().getValue(); // Thứ 1 = 1, Chủ nhật = 7
                int normalizedDay = (dayOfWeekValue % 7); // Normalize về 0 (Chủ nhật) đến 6 (Thứ bảy)

                if (selectedDays.contains(normalizedDay)) {
                    if (holidayDao.checkHolidayDate(currentDate)) {
                        isHoliday = true;
                        break;
                    }
                }
                currentDate = currentDate.plusDays(1);

            }
            if (isHoliday) {
                out.print("holiday");
            } else {
                out.print("No holiday");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("error:" + e.getMessage());
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
