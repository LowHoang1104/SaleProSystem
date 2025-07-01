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
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import salepro.dao.AttendanceDAO;
import salepro.models.Attendances;
import salepro.models.Shifts;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SaveAttendanceServlet", urlPatterns = {"/SaveAttendanceServlet"})
public class SaveAttendanceServlet extends HttpServlet {

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
            out.println("<title>Servlet SaveAttendanceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveAttendanceServlet at " + request.getContextPath() + "</h1>");
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
        AttendanceDAO attendanceDao = new AttendanceDAO();
        // Đặt encoding cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //đọc dữ liệu từ request body của json
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String json = sb.toString();
        System.out.println(json);
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(json, JsonObject.class);
        //Truy cập thủ công từng phần tử
        int attendanceId = data.get("attendanceId").getAsInt();
        String status = data.get("attendanceStatus").getAsString();
        String note = data.get("note").getAsString();
        String checkInStr = data.get("checkIn").getAsString();
        String checkOutStr = data.get("checkOut").getAsString();
        String error;
        //Lấy ca cần chấm công
        Attendances attendance = attendanceDao.getAttendanceById(attendanceId);
        Shifts shift = attendance.getShift();
        Time startTimeShift = shift.getStartTime();
        Time endTimeShift = shift.getEndTime();
        //Chuyển Time sang LocalDateTime
        LocalDateTime shiftStartLocalDateTime = LocalDateTime.of(LocalDate.now(), startTimeShift.toLocalTime());
        LocalDateTime shiftEndLocalDateTime = LocalDateTime.of(LocalDate.now(), endTimeShift.toLocalTime());
        //Update attendance
        LocalDateTime checkIn = null;
        LocalDateTime checkOut = null;
        double standardHour = 0;
        double overTimeHours = 0;

        if (status.equalsIgnoreCase("Present")) {
            //Kiểm tra validate
            error = validateInput(checkInStr);
            if (!error.isBlank()) {
                sendErrorResponse(response, error);
                return;
            }

            if (checkInStr != null && !checkInStr.isBlank()) {
                checkIn = LocalDateTime.of(LocalDate.now(), LocalTime.parse(checkInStr));
            }
            if (checkOutStr != null && !checkOutStr.isBlank()) {
                checkOut = LocalDateTime.of(LocalDate.now(), LocalTime.parse(checkOutStr));
            }
            //Kiểm tra xem ca phải ca đêm không
            if (endTimeShift.before(startTimeShift)) {
                //Ca đêm sang ngày khác
                shiftEndLocalDateTime = shiftEndLocalDateTime.plusDays(1);
                if (checkOut != null && checkOut.isBefore(checkIn)) {
                    // Giờ về có sang ngày hôm sai 
                    checkOut = checkOut.plusDays(1);
                }
            }
            if (checkOut != null && checkOut.isBefore(checkIn)) {
                sendErrorResponse(response, "Giờ ra phải sau giờ vào!");
                return;
            }
            //Đi muộn
            if(checkIn.isAfter(shiftStartLocalDateTime)){
                status = "Late";
            }
            //Về sớm
            if(checkOut.isBefore(shiftEndLocalDateTime)){
                status = "Early Leave";
            }

            if (checkIn != null && checkOut != null) {
                //Tính thời gian làm việc
                Duration workedDuration = Duration.between(checkIn, checkOut);
                double workedHours = workedDuration.toMinutes() / 60.0;
                //Nếu đi sớm hơn giờ bắt đầu lấy giờ bắt đầu ca
                LocalDateTime effectiveStart = (checkIn.isBefore(shiftStartLocalDateTime)) ? shiftStartLocalDateTime : checkIn;
                //Về muônj lấy thời gian kết thúc ca
                LocalDateTime effectiveEnd = (checkOut.isAfter(shiftEndLocalDateTime)) ? shiftEndLocalDateTime : checkOut;
                //Tính giờ làm trong ca
                Duration standardDuration = Duration.between(effectiveStart, effectiveEnd);
                standardHour = Math.max(0, standardDuration.toMinutes() / 60.0);

                //Giờ tăng ca
                overTimeHours = Math.max(0, workedHours - standardHour);
            }
        }
        boolean updateAttendance = attendanceDao.updateAttendance(attendanceId, status, note, checkIn, checkOut, standardHour, overTimeHours);

        if (updateAttendance) {
            JsonObject success = new JsonObject();
            success.addProperty("status", "success");
            response.getWriter().write(success.toString());
        } else {
            sendErrorResponse(response, "Không thể cập nhật chấm công!");
        }
    }

    private String validateInput(String checkInStr) {

        if (checkInStr == null || checkInStr.isBlank()) {
            return "Vui lòng chọn thời gian vào làm việc!";
        }

        return "";
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"status\":\"error\",\"message\":\"" + message + "\"}");
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
