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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import salepro.dao.ShiftDAO;
import salepro.models.Attendances;
import salepro.models.Shifts;

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
        //Các DAO
        AttendanceDAO aDAO = new AttendanceDAO();
        ShiftDAO shiftDAO = new ShiftDAO();
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

        System.out.println(jsonObject);
        //Lấy các giá trị từ JSON
        //Lấy storeId và weekStart 
        int storeId = jsonObject.get("storeId").getAsInt();
        String weekStart = jsonObject.get("weekStart").getAsString();
        //Kiểm tra employeeId
        int employeeId = 0;
        if (jsonObject.has("employeeId") && !jsonObject.get("employeeId").isJsonNull()) {
            try {
                employeeId = jsonObject.get("employeeId").getAsInt();
            } catch (Exception e) {
                sendErrorResponse(response, "ID nhân viên không hợp lệ!");
                return;
            }
        } else {
            sendErrorResponse(response, "Vui lòng cung cấp ID nhân viên!");
            return;
        }
        int shiftId = 0;
        if (jsonObject.has("shiftId") && !jsonObject.get("shiftId").isJsonNull()) {
            try {
                shiftId = jsonObject.get("shiftId").getAsInt();
            } catch (Exception e) {
                sendErrorResponse(response, "Vui lòng chọn ca làm việc");
                return;
            }
        } else {
            sendErrorResponse(response, "Vui lòng chọn ca làm việc!");
            return;
        }
        String workDate = jsonObject.get("workDate").getAsString();
        String endDate = jsonObject.get("endDate").getAsString();
        boolean isMultiEmployee = jsonObject.get("isMultiEmployee").getAsBoolean();
        int[] selectedDays = gson.fromJson(jsonObject.get("selectedDays"), int[].class);
        String[] selectedEmployeeIds = gson.fromJson(jsonObject.get("selectedEmployeeIds"), String[].class);

        System.out.println("Work Date: " + workDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Is Multi Employee: " + isMultiEmployee);

// In mảng selectedDays
        System.out.print("Selected Days: ");
        for (int day : selectedDays) {
            System.out.print(day + " ");
        }
        System.out.println();

// In mảng selectedEmployeeIds
        System.out.print("Selected Employee IDs: ");
        for (String id : selectedEmployeeIds) {
            System.out.print(id + " ");
        }
        System.out.println();
        //Kiểm tra validate
        String error = validateInput(employeeId, shiftId, workDate, endDate, isMultiEmployee, selectedEmployeeIds, selectedDays);
        if (!error.isEmpty()) {
            sendErrorResponse(response, error);
            return;
        }

        //Kiểm tra ca mới thêm vào có trùng với ca đã có
        LocalDate workDateParsed = LocalDate.parse(workDate);
        //Ca làm mới thêm vào
        Shifts shift = shiftDAO.getShiftById(shiftId);

        LocalDateTime newStartDateTime = LocalDateTime.of(workDateParsed, shift.getStartTime().toLocalTime());
        LocalDateTime newEndDateTime = LocalDateTime.of(workDateParsed, shift.getEndTime().toLocalTime());
        // Xử lý nếu ca làm vượt qua nửa đêm
        if (!newStartDateTime.isBefore(newEndDateTime)) {
            newEndDateTime = newEndDateTime.plusDays(1);
        }

        // Kiểm tra trùng lặp
        boolean isOverlap = false;
        //Danh sách các ca đã được thêm cho nhân viên
        for (Attendances attendance : aDAO.getAttendaceByEmpId(employeeId)) {
            if (attendance.getWorkDate() == workDateParsed) {
                LocalDateTime existingStart = LocalDateTime.of(workDateParsed, attendance.getShift().getStartTime().toLocalTime());
                LocalDateTime existingEnd = LocalDateTime.of(workDateParsed, attendance.getShift().getEndTime().toLocalTime());
                if (!attendance.getShift().getStartTime().toLocalTime().isBefore(attendance.getShift().getEndTime().toLocalTime())) {
                    existingEnd = existingEnd.plusDays(1);
                }

                // Kiểm tra trùng thời gian
                if (newStartDateTime.isBefore(existingEnd) && newEndDateTime.isAfter(existingStart)) {
                    isOverlap = true;
                    break;
                }
            }
        }

        if (isOverlap) {
            sendErrorResponse(response, "Ca mới bị trùng với ca cũ.");
            return;
        }

        List<Attendances> attendances = aDAO.getAttendaceByEmpId(employeeId);
        for (Attendances attendance : attendances) {
            LocalDateTime startTimeShift = LocalDateTime.of(LocalDate.parse(workDate), attendance.getShift().getStartTime().toLocalTime());
            LocalDateTime endTimeShift = LocalDateTime.of(LocalDate.parse(workDate), attendance.getShift().getEndTime().toLocalTime());
            if (!startTimeShift.isBefore(endTimeShift)) {
                endTimeShift.plusDays(1);
            }
        }
        if (aDAO.hasAttendance(employeeId, shiftId, workDate)) {
            sendErrorResponse(response, "Nhân viên đã có ca làm việc này. Vui lòng chọn ca khác!");
            return;
        }
        try {
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
                    processAttendance(aDAO, Integer.parseInt(empId), shiftId, sqlWorkDate, selectedDays, sqlEndDate);
                }
            } else {
                processAttendance(aDAO, employeeId, shiftId, sqlWorkDate, selectedDays, sqlEndDate);
            }
            //GỬi JSon từ servlet sang jsp 
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("status", "success");
            jsonResponse.addProperty("message", "Ca làm việc được thêm thành công");
            jsonResponse.addProperty("storeId", storeId);
            jsonResponse.addProperty("weekStart", weekStart);
            response.getWriter().write(jsonResponse.toString());
//            response.getWriter().write("{\"status\":\"success\",\"message\":\"Ca làm việc đã được thêm thành công\"}");
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi định dạng ngày
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Định dạng ngày không hợp lệ: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Xử lý các lỗi khác
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    private String validateInput(int employeeId, int shiftId, String workDate, String endDate, boolean isMultiEmployee, String[] selectedEmployeeIds, int[] selectedDays) {
        if (employeeId <= 0 && (!isMultiEmployee || selectedEmployeeIds.length == 0)) {
            return "Vui lòng chọn it nhất một nhân viên!";
        }
        if (shiftId <= 0) {
            return "Vui lòng chọn ca làm việc";
        }
        if (workDate == null || workDate.isBlank()) {
            return "Vui lòng chọn ngày làm việc!";
        }
        if (endDate != null && !endDate.isBlank() && workDate.compareTo(endDate) > 0) {
            return "Ngày kết thúc phải sau ngày bắt đầu!";
        }
        return "";
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"status\":\"error\",\"message\":\"" + message + "\"}");
    }

    private void processAttendance(AttendanceDAO aDAO, int empId, int shiftId, Date workDate, int[] selectedDays, Date endDate) throws Exception {

        Calendar calendar = Calendar.getInstance();
        //Ngày bắt đầu làm việc
        calendar.setTime(workDate);

        //Thêm bản ghi ngay lập tức nếu không có lặp lại
        if (selectedDays == null || selectedDays.length == 0) {
            aDAO.insertAttendanceByShiftIdAndEmpId(shiftId, empId, workDate);
            return;
        }

        //Giới hạn số lần lặp theo tuần
        int maxIterations = 10;
        for (int day : selectedDays) {
            //Lưu bản sao của ngày bắt đầu làm việc workDate
            Calendar cal = (Calendar) calendar.clone();
            //Tìm day của workDate
            int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
            //Số day cần cộng so với workDate
            int daysToAdd = (day - currentDayOfWeek + 7) % 7;
            if (daysToAdd == 0 && day != currentDayOfWeek) {
                daysToAdd = 7;
            }
            //Ngày cần thêm ca làm việc
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
