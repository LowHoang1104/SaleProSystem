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
import java.util.List;
import salepro.dao.EmployeeDAO;
import salepro.dao.ShiftDAO;
import salepro.dao.StoreDAO;
import salepro.models.Employees;
import salepro.models.Shifts;
import salepro.models.Stores;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListShiftServlet", urlPatterns = {"/ListShiftServlet"})
public class ListShiftServlet extends HttpServlet {

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
            out.println("<title>Servlet ListShiftServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListShiftServlet at " + request.getContextPath() + "</h1>");
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
        ShiftDAO sDao = new ShiftDAO();
        //Đẩy danh sách chi nhánh sang jsp
        StoreDAO storeDao = new StoreDAO();
        List<Stores> stores = storeDao.getData();
        request.setAttribute("stores", stores);

        //Đẩy data của shift cần update
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("update")) {
            String shiftId = request.getParameter("shiftId");
            Shifts shift = sDao.getShiftById(Integer.parseInt(shiftId));
            request.setAttribute("shiftIdUp", shiftId);
            request.setAttribute("shiftNameUp", shift.getShiftName());
            request.setAttribute("isActiveUp", shift.isIsActive());
            request.setAttribute("startTimeUp", shift.getStartTime());
            request.setAttribute("endTimeUp", shift.getEndTime());
            request.setAttribute("checkInTimeUp", "");
            request.setAttribute("checkOutTimeUp", "");
            request.setAttribute("storeIdUp", shift.getStoreID());
            System.out.println(shift.getStoreID());
            request.setAttribute("openUpdate", true);
        }

        //Đẩy danh sách ca ra jsp
        List<Shifts> shifts = sDao.getData();
        request.setAttribute("shifts", shifts);
        request.getRequestDispatcher("view/jsp/admin/ShiftManagement/List_shift.jsp").forward(request, response);
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
        ShiftDAO sDao = new ShiftDAO();
        StoreDAO storeDao = new StoreDAO();

        //Đẩy danh sách chi nhánh sang jsp
        List<Stores> stores = storeDao.getData();
        request.setAttribute("stores", stores);

        //Thực hiên add, updadte shift
        String action = request.getParameter("action");
        if (action != null && !action.isBlank()) {
            //Thực hiện add
            if (action.equalsIgnoreCase("add")) {
                String shiftName = request.getParameter("shiftName");
                String isActive = request.getParameter("isActive");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                String checkInTime = request.getParameter("checkInTime");
                String checkOutTime = request.getParameter("checkOutTime");
                String storeIdAdd = request.getParameter("storeIdAdd");
                String errorAdd = "";

                //Trả lại dữ liệu nếu add và update lỗi 
                request.setAttribute("shiftName", shiftName);
                request.setAttribute("isActive", isActive);
                request.setAttribute("startTime", startTime);
                request.setAttribute("endTime", endTime);
                request.setAttribute("checkInTime", checkInTime);
                request.setAttribute("checkOutTime", checkOutTime);
                request.setAttribute("storeIdAdd", storeIdAdd);
                boolean addSuccess = false;

                //kiểm tra giờ làm việc cớ lớn hơn 1 giờ và nhỏ hơn 24 giờ
                LocalTime start = LocalTime.parse(startTime);
                LocalTime end = LocalTime.parse(endTime);
                Duration duration;
                if (end.isBefore(start)) {
                    duration = Duration.between(start, end.plusHours(24));
                } else {
                    duration = Duration.between(start, end);
                }
                long minute = duration.toMinutes();
                if (minute > 60 && minute < 24 * 60) {
                    // Khởi tạo shift để add
                    Shifts shift = new Shifts();
                    shift.setShiftName(shiftName);
                    shift.setStartTime(Time.valueOf(start));
                    shift.setEndTime(Time.valueOf(end));
                    shift.setIsActive(isActive.equalsIgnoreCase("active"));
                    if (storeIdAdd != null && !storeIdAdd.isEmpty()) {
                        int storeId = Integer.parseInt(storeIdAdd);
                        shift.setStoreID(storeId);
                        if (sDao.isShiftExist(shiftName, storeId)) {
                            errorAdd = "Tên ca đã tồn tại, Vui lòng nhập lại tên ca!";
                        } else {
                            addSuccess = sDao.insertShift(shift);
                        }
                    } else {
                        boolean checkShiftName = false;
                        String storeName = "";
                        for (Stores store : stores) {
                            int storeID = store.getStoreID();
                            if (sDao.isShiftExist(shiftName, storeID)) {
                                checkShiftName = true;
                                storeName = store.getStoreName();
                                break;
                            }
                        }
                        if (checkShiftName) {
                            errorAdd = "Tên ca đã tồn tại trong " + storeName + ". Vui lòng nhập lại tên ca!";
                        } else {
                            addSuccess = sDao.insertShiftForAllStore(shift);
                        }
                    }
                    request.setAttribute("addSuccess", addSuccess);
                } else {
                    errorAdd = "Giờ làm việc phải lớn hơn 1 giờ và nhỏ hơn 24 giờ. Vui lòng nhập lại!";
                }
                if (!errorAdd.isEmpty()) {
                    request.setAttribute("errorAdd", errorAdd);
                    request.setAttribute("openAdd", true);
                }
            }

            //Thực hiện update
            if (action.equalsIgnoreCase("update")) {
                String shiftId = request.getParameter("shiftIdUp");
                String shiftName = request.getParameter("shiftNameUp");
                String isActive = request.getParameter("isActiveUp");
                String startTime = request.getParameter("startTimeUp");
                String endTime = request.getParameter("endTimeUp");
                String checkInTime = request.getParameter("checkInTimeUp");
                String checkOutTime = request.getParameter("checkOutTimeUp");
                String storeIdUp = request.getParameter("storeIdUp");
                String errorUp = "";

                //Trả lại dữ liệu nếu update lỗi 
                request.setAttribute("shiftIdUp", shiftId);
                request.setAttribute("shiftNameUp", shiftName);
                request.setAttribute("isActiveUp", isActive);
                request.setAttribute("startTimeUp", startTime);
                request.setAttribute("endTimeUp", endTime);
                request.setAttribute("checkInTimeUp", checkInTime);
                request.setAttribute("checkOutTimeUp", checkOutTime);
                request.setAttribute("storeIdUp", storeIdUp);
                boolean updateSuccess = false;

                //kiểm tra giờ làm việc cớ lớn hơn 1 giờ và nhỏ hơn 24 giờ
                LocalTime start = LocalTime.parse(startTime);
                LocalTime end = LocalTime.parse(endTime);
                Duration duration;
                if (end.isBefore(start)) {
                    duration = Duration.between(start, end.plusHours(24));
                } else {
                    duration = Duration.between(start, end);
                }
                long minute = duration.toMinutes();
                if (minute > 60 && minute < 24 * 60) {
                    //Update shift theo id
                    Shifts shift = sDao.getShiftById(Integer.parseInt(shiftId));
                    //Kiểm tra có thay đổi shiftName không
                    boolean isChangeName = false;
                    if (shiftName != null && !shiftName.isBlank() && !shiftName.equalsIgnoreCase(shift.getShiftName())) {
                        isChangeName = true;
                        shift.setShiftName(shiftName);
                    }

                    shift.setStartTime(Time.valueOf(start));
                    shift.setEndTime(Time.valueOf(end));
                    shift.setIsActive(isActive.equalsIgnoreCase("active"));
                    int storeId = Integer.parseInt(storeIdUp);
                    shift.setStoreID(storeId);
                    if (isChangeName && sDao.isShiftExist(shiftName, storeId)) {
                        errorUp = "Tên ca đã tồn tại, Vui lòng nhập lại tên ca!";
                    } else {
                        updateSuccess = sDao.updateShift(shift);
                    }
                    request.setAttribute("addSuccess", updateSuccess);
                } else {
                    errorUp = "Giờ làm việc phải lớn hơn 1 giờ và nhỏ hơn 24 giờ. Vui lòng nhập lại!";
                }
                if (!errorUp.isEmpty()) {
                    request.setAttribute("errorUp", errorUp);
                    request.setAttribute("openUpdate", true);
                }
            }

        }
        String keyword = request.getParameter("keyword");
        String storeId = request.getParameter("storeId");
        if (storeId != null || keyword != null) {
            List<Shifts> shifts = sDao.searchShiftByNameAndStoreId(keyword, storeId);
            request.setAttribute("storeId", storeId);
            request.setAttribute("keyword", keyword);
            request.setAttribute("shifts", shifts);
            request.getRequestDispatcher("view/jsp/admin/ShiftManagement/List_shift.jsp").forward(request, response);
            return;
        }

        List<Shifts> shifts = sDao.getData();
        request.setAttribute("shifts", shifts);
        request.getRequestDispatcher("view/jsp/admin/ShiftManagement/List_shift.jsp").forward(request, response);

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
