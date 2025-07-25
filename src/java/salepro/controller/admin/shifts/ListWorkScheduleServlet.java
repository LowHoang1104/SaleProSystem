/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.shifts;

import com.google.gson.Gson;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import salepro.dao.AttendanceDAO;
import salepro.dao.EmployeeDAO;
import salepro.dao.ShiftDAO;
import salepro.dao.StoreDAO;
import salepro.models.Attendances;
import salepro.models.Employees;
import salepro.models.Shifts;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListWorkScheduleServlet", urlPatterns = {"/ListWorkScheduleServlet"})
public class ListWorkScheduleServlet extends HttpServlet {

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
            out.println("<title>Servlet ListWorkScheduleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListWorkScheduleServlet at " + request.getContextPath() + "</h1>");
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
        EmployeeDAO eDao = new EmployeeDAO();
        AttendanceDAO aDao = new AttendanceDAO();
        ShiftDAO sDao = new ShiftDAO();
        StoreDAO storeDao = new StoreDAO();
        //Session user 
        //Lấy tên của tài khoản đang đăng nhập
        String empName = "";
        Employees emp = null;
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
                stores = storeDao.getData();
            } else {
                emp = eDao.getEmployeeByUserId(user.getUserId());
                empTypeId = user.getEmpTypeId();
                empName = user.getFullName();
                stores.add(storeDao.getStoreByID(user.getStoreByUserId().getStoreID()));
            }
            request.setAttribute("stores", stores);
            request.setAttribute("empTypeId", empTypeId);
            request.setAttribute("roleId", roleId);
        }

        //Xóa ca làm việc 
        String action = request.getParameter("action");
        if (action != null && !action.isBlank()) {
            if (action.equalsIgnoreCase("delete")) {
                String attendanceIdStr = request.getParameter("attendanceId");
                boolean deleteSuccess = aDao.deleteAttendanceById(Integer.parseInt(attendanceIdStr));
                request.setAttribute("deleteSuccess", deleteSuccess);
            }
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

        // Lấy tuần cần hiển thị
        String weekStartParam = request.getParameter("weekStart");
        LocalDate weekStart;
        if (weekStartParam != null) {
            weekStart = LocalDate.parse(weekStartParam);
        } else {
            //Lấy ngày thứ hai đầu tuần
            weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        }
        LocalDate weekEnd = weekStart.plusDays(6);

        //Đẩy ra các ngày trong tuần hiện tại
        List<LocalDate> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDays.add(weekStart.plusDays(i));
        }
        request.setAttribute("weekDays", weekDays);

        //Lấy danh sách ca
        List<Shifts> shifts = sDao.getShiftByStoreId(storeId);
        request.setAttribute("shifts", shifts);

        //Lấy danh sách employees
        List<Employees> employees;
        String empNameStr = request.getParameter("empName");
        if(emp.getEmployeeTypeID() == 2){
            empName = empNameStr;
        }
        request.setAttribute("empName", empName);
        if (empName != null && !empName.isBlank()) {
            empName = empName.replaceAll("\\s+", " ").trim();            
            employees = eDao.filterEmployee(storeId, empName);
        } else {
            employees = eDao.getEmployeeByStoreId(storeId);
        }
        request.setAttribute("employees", employees);

        Map<Integer, List<Attendances>> attendanceByEmpId = new HashMap<>();
        for (Employees employee : employees) {
            int empId = employee.getEmployeeID();
            List<Attendances> fullAttendance = aDao.getAttendaceByEmpId(empId);

            //Lọc theo tuần
            List<Attendances> filtered = fullAttendance.stream()
                    .filter(att -> {
                        LocalDate date = att.getWorkDate();
                        return (!date.isBefore(weekStart) && !date.isAfter(weekEnd));
                    }).collect(Collectors.toList());
            attendanceByEmpId.put(empId, filtered);
        }

        request.setAttribute("attendanceByEmpId", attendanceByEmpId);
        request.setAttribute("weekStart", weekStart);
        request.getRequestDispatcher("view/jsp/admin/ShiftManagement/List_work_schedule.jsp").forward(request, response);
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
