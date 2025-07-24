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
import jakarta.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import salepro.dao.AttendanceDAO;
import salepro.dao.ShiftDAO;
import salepro.models.Attendances;
import salepro.models.Shifts;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import salepro.dao.EmployeeDAO;
import salepro.dao.StoreDAO;
import salepro.models.Employees;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListAttendanceServlet", urlPatterns = {"/ListAttendanceServlet"})
public class ListAttendanceServlet extends HttpServlet {

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
            out.println("<title>Servlet ListAttendanceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListAttendanceServlet at " + request.getContextPath() + "</h1>");
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
        AttendanceDAO attendanceDao = new AttendanceDAO();
        ShiftDAO shiftDao = new ShiftDAO();
        StoreDAO storeDAO = new StoreDAO();
                EmployeeDAO employeeDAO = new EmployeeDAO();


        //Session
        //Lấy tên của tài khoản đang đăng nhập
        String empName = "";
        Employees emp =null;
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("view/jsp/Homepage.jsp");
        } else {
            //Lấy dữ liệu từ session để check phân quyền
            int roleId = user.getRoleId();
            int empTypeId = 0;
            List<Stores> stores = new ArrayList<>();
            if (user.getRoleId() == 1) {
                stores = storeDAO.getData();
            } else {
                emp = employeeDAO.getEmployeeByUserId(user.getUserId());
                empTypeId = user.getEmpTypeId();
                empName = user.getFullName();
                stores.add(storeDAO.getStoreByID(user.getStoreByUserId().getStoreID()));
            }
            request.setAttribute("stores", stores);
            request.setAttribute("empTypeId", empTypeId);
            request.setAttribute("roleId", roleId);
        }

        //Xử lí dữ liệu theo storeId
        String storeIdStr = request.getParameter("storeId");
        int storeId;
        if (storeIdStr == null || storeIdStr.isBlank()) {
            storeId = 1;
        } else {
            storeId = Integer.parseInt(storeIdStr);
        }
        request.setAttribute("storeId", storeId);

        //Lấy ngày hiện tại 
        request.setAttribute("today", LocalDate.now());
        // Lấy tuần cần hiển thị
        String weekStartParam = request.getParameter("weekStart");
        LocalDate weekStart;
        if (weekStartParam != null) {
            weekStart = LocalDate.parse(weekStartParam);
        } else {
            //Lấy ngày thứ hai đầu tuần
            weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        }
        request.setAttribute("weekStart", weekStart);
        LocalDate weekEnd = weekStart.plusDays(6);

        //Đẩy ra các ngày trong tuần hiện tại
        List<LocalDate> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDays.add(weekStart.plusDays(i));
        }
        request.setAttribute("weekDays", weekDays);
        //Lấy danh sách ca làm việc trong ngày 

        List<Shifts> shifts;
        shifts = shiftDao.filterShifts(storeId, 0, "", Date.valueOf(weekStart), Date.valueOf(weekEnd));
        request.setAttribute("shifts", shifts);

        //Lấy danh sách ca làm viêcj của nhân viên
        Map<Integer, List<Attendances>> attendanceByShiftId = new HashMap<>();
        //Lọc theo tên nhân viên nếu có
        String empNameStr = request.getParameter("empName");
        if(emp.getEmployeeTypeID() == 2){
            empName = empNameStr;
        }
        request.setAttribute("empName", empName);
        List<Attendances> attendances;
        for (Shifts shift : shifts) {
            int shiftId = shift.getShiftID();
            if (empName != null && !empName.isBlank()) {
                empName = empName.replaceAll("\\s+", " ").trim();
                attendances = attendanceDao.filterAttendance(storeId, shiftId, empName, Date.valueOf(weekStart), Date.valueOf(weekEnd));
            } else {
                attendances = attendanceDao.filterAttendance(0, shiftId, "", Date.valueOf(weekStart), Date.valueOf(weekEnd));
            }
            attendanceByShiftId.put(shiftId, attendances);
        }
        request.setAttribute("attendanceByShiftId", attendanceByShiftId);

        //Xử lí add, update, delete
//        String action = request.getParameter("action");
//        if (action != null && !action.isBlank()) {
//            String attendanceIdStr = request.getParameter("attendanceId");
//            if (attendanceIdStr != null && !attendanceIdStr.isBlank()) {
//                int attendanceId = Integer.parseInt(attendanceIdStr);
//                if (action.equalsIgnoreCase("update")) {
//                    Attendances attendance = attendanceDao.getAttendanceById(attendanceId);
//                    //Ca làm việc cho nhân viên chọn
//                    shifts = shiftDao.filterShifts(storeId, attendance.getEmployeeId(), "", Date.valueOf(weekStart), Date.valueOf(weekEnd));
//                    request.setAttribute("shiftsForEmp", shifts);
//                    request.setAttribute("attendance", attendance);
//                    request.setAttribute("openModal", true);
//                }
//            }
//        }
        request.getRequestDispatcher("view/jsp/admin/ShiftManagement/List_attendance.jsp").forward(request, response);

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
