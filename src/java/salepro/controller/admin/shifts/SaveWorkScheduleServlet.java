/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.shifts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import org.apache.http.client.fluent.Request;
import java.util.Calendar;
import salepro.dao.AttendanceDAO;
import java.sql.Date;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SaveWorkScheduleServlet", urlPatterns = {"/SaveWorkScheduleServlet"})
public class SaveWorkScheduleServlet extends HttpServlet {

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
            out.println("<title>Servlet SaveWorkSchedule</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveWorkSchedule at " + request.getContextPath() + "</h1>");
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
        // Đặt encoding cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //Đọc dữ liệu JSON từ request
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        //Chuyển JSON thành đối tượng 
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

        //Lấy các giá trị từ JSON
        int employeeId = jsonObject.get("employeeId").getAsInt();
        int shiftId = jsonObject.get("shiftId").getAsInt();
        String workDate = jsonObject.get("workDate").getAsString();
        String endDate = jsonObject.get("endDate").getAsString();
        boolean isMultiEmployee = jsonObject.get("isMultiEmployee").getAsBoolean();
        int[] selectedDays = gson.fromJson(jsonObject.get("selectedDays"), int[].class);
        String[] selectedEmployeeIds = gson.fromJson(jsonObject.get("selectedEmployeeIds"), String[].class);
        try {
            AttendanceDAO aDAO = new AttendanceDAO();
            Date sqlWorkDate = null;
            if (workDate != null && !workDate.isBlank()) {
                sqlWorkDate = Date.valueOf(workDate);
            }
            Date sqlEndDate = null;
            if (endDate != null && !endDate.isBlank()) {
                sqlEndDate = Date.valueOf(endDate);
            }
            if (isMultiEmployee && selectedEmployeeIds.length > 0) {
                // Xử lí cho nhiều nhân viên
                for (String empId : selectedEmployeeIds) {
                    System.out.println(empId);
                    processAttendance(aDAO, Integer.parseInt(empId), shiftId, sqlWorkDate, selectedDays, sqlEndDate);
                }
            } else {
                System.out.println("Add");
                processAttendance(aDAO, employeeId, shiftId, sqlWorkDate, selectedDays, sqlEndDate);
            }
            response.getWriter().write("{\"status\":\"success\",\"message\":\"Ca làm việc đã được thêm thành công\"}");
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi định dạng ngày
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Định dạng ngày không hợp lệ: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Xử lý các lỗi khác
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private void processAttendance(AttendanceDAO aDAO, int empId, int shiftId, Date workDate, int[] selectedDays, Date endDate) throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workDate);

        //Thêm bản ghi ngay lập tức nếu không có lặp lại
        if (selectedDays == null || selectedDays.length == 0) {
            aDAO.insertAttendanceByShiftIdAndEmpId(shiftId, empId, workDate);
            return;
        }

        //Giới hạn số lần lặp theo tuần
        int maxIterations = 10;
        for (int day : selectedDays) {
            Calendar cal = (Calendar) calendar.clone();
            int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
            int daysToAdd = (day - currentDayOfWeek + 7) % 7;
            if (daysToAdd == 0 && day != currentDayOfWeek) {
                daysToAdd = 7;
            }
            cal.add(Calendar.DAY_OF_MONTH, daysToAdd);

            Date repeatedDate = new Date(cal.getTimeInMillis());
            int iterationCount = 0;
            while (iterationCount < maxIterations && (endDate == null || repeatedDate.before(endDate) || repeatedDate.equals(endDate))) {
                aDAO.insertAttendanceByShiftIdAndEmpId(shiftId, empId, repeatedDate);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                repeatedDate = new Date(cal.getTimeInMillis());
                iterationCount++;
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
