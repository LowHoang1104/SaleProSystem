/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.shifts;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import salepro.dao.HolidayDAO;
import salepro.models.Holiday;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListHolidayServlet", urlPatterns = {"/ListHolidayServlet"})
public class ListHolidayServlet extends HttpServlet {

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
            out.println("<title>Servlet ListHolidayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListHolidayServlet at " + request.getContextPath() + "</h1>");
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
        //Các DAO
        HolidayDAO holidayDao = new HolidayDAO();

        //Lấy danh sách holiday
        String search = request.getParameter("search");
        List<Holiday> holidays;
        if (search != null && !search.isBlank()) {
            holidays = holidayDao.searchByKey(search);
        } else {
            holidays = holidayDao.getAllHoliday();
        }
        request.setAttribute("holidays", holidays);
        request.getRequestDispatcher("view/jsp/admin/ShiftManagement/ListHoliday.jsp").forward(request, response);
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
        // Các DAO 
        HolidayDAO holidayDao = new HolidayDAO();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if ("save".equals(action)) {
            try {
                String idStr = request.getParameter("id");
                String dateStr = request.getParameter("date");
                String name = request.getParameter("name");
                String isPublishStr = request.getParameter("isPublish");
                boolean isPublish = Boolean.getBoolean(isPublishStr);
                LocalDate date = LocalDate.parse(dateStr);
                if (idStr.equalsIgnoreCase("null")) {
                    if (holidayDao.checkHolidayDate(date)) {
                        out.print("Ngày lễ đã tồn tại");
                        return;
                    }
                    if (holidayDao.checkName(name)) {
                        out.print("Tên ngày lễ đã tồn tại");
                        return;
                    }
                    boolean add = holidayDao.insertHoliday(new Holiday(date, name, isPublish));
                    if (add) {
                        out.print("addsuccess");
                    } else {
                        out.print("Thêm thất bại");
                    }
                } else {
                    int id = Integer.parseInt(idStr);
                    boolean add = holidayDao.updateHoliday(new Holiday(id, date, name, isPublish));
                    if (add) {
                        out.print("success");
                    } else {
                        out.print("Cập nhật thất bại");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.print("error:" + e.getMessage());
            }
        }
        if ("delete".equals(action)) {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            boolean delete = holidayDao.deleteHoliday(id);
            if (delete) {
                out.print("success");
            } else {
                out.print("Xóa thất bại");
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
