/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.salary;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import salepro.dao.EmployeeSalaryAssignmentDAO;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SaveSalaryServlet", urlPatterns = {"/SaveSalaryServlet"})
public class SaveSalaryServlet extends HttpServlet {

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
            out.println("<title>Servlet SaveSalartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveSalartServlet at " + request.getContextPath() + "</h1>");
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
        EmployeeSalaryAssignmentDAO salaryDAO = new EmployeeSalaryAssignmentDAO();

        request.setCharacterEncoding("UTF-8"); // đảm bảo không lỗi tiếng Việt
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String employeeIdStr = request.getParameter("employeeId");
        int employeeId = Integer.parseInt(employeeIdStr);
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        if (action.equalsIgnoreCase("saveSalary")) {
            String salaryType = request.getParameter("salaryType");
            String salaryAmountStr = request.getParameter("salaryAmount");
            if (salaryType == null || salaryType.isEmpty() || salaryAmountStr == null || salaryAmountStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Thiếu dữ liệu");
                return;
            }
            try {
                double salaryAmount = Double.parseDouble(salaryAmountStr);
                if (salaryDAO.saveSalaryAssignment(employeeId, salaryType, salaryAmount, -1, -1, -1, -1, -1, -1, -1, -1, -1)) {
                    out.write("success");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Lỗi định dạng lương");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Lỗi server");
            }
        }
        if (action.equalsIgnoreCase("saveOvertime")) {
            try {
                // Lấy và parse dữ liệu
                double normalRate = Double.parseDouble(request.getParameter("normalRate"));
                double saturdayRate = Double.parseDouble(request.getParameter("saturdayRate"));
                double sundayRate = Double.parseDouble(request.getParameter("sundayRate"));
                double holidayRate = Double.parseDouble(request.getParameter("holidayRate"));

                // Gọi DAO để lưu hệ số lương làm thêm
                if (salaryDAO.saveSalaryAssignment(employeeId, "", -1, normalRate, saturdayRate, sundayRate, holidayRate, -1, -1, -1, -1, -1)) {
                    out.write("success");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("Lưu thất bại");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Dữ liệu không hợp lệ");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Lỗi server");
            }
        }
        if (action.equalsIgnoreCase("saveAllowance")) {
            String allowanceAmountStr = request.getParameter("allowanceAmount");
            if (allowanceAmountStr == null || allowanceAmountStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Vui lòng nhập tiền phụ cấp !");
                return;
            }
            try {
                double allowanceAmount = Double.parseDouble(allowanceAmountStr);
                if (salaryDAO.saveSalaryAssignment(employeeId, "", -1, -1, -1, -1, -1, allowanceAmount, -1, -1, -1, -1)) {
                    out.write("success");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Lỗi định dạng tiền phụ cấp");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Lỗi server");
            }
        }
        if (action.equalsIgnoreCase("saveDeductionAmount")) {
            try {
                // Lấy và parse dữ liệu
                double penaltyLateArrival = Double.parseDouble(request.getParameter("penaltyLateArrival"));
                double penaltyEarlyLeave = Double.parseDouble(request.getParameter("penaltyEarlyLeave"));
                double penaltyOthers = Double.parseDouble(request.getParameter("penaltyOthers"));

                // Gọi DAO để lưu hệ số lương làm thêm
                if (salaryDAO.saveSalaryAssignment(employeeId, "", -1, -1, -1, -1, -1, -1, penaltyLateArrival, penaltyEarlyLeave, penaltyOthers, -1)) {
                    out.write("success");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("Lưu thất bại");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Dữ liệu không hợp lệ");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Lỗi server");
            }
        }
        if (action.equalsIgnoreCase("saveCommission")) {
            try {
                // Lấy và parse dữ liệu
                double commissionRate = Double.parseDouble(request.getParameter("commissionRate"));

                // Gọi DAO để lưu hệ số lương làm thêm
                if (salaryDAO.saveSalaryAssignment(employeeId, "", -1, -1, -1, -1, -1, -1, -1, -1, -1, commissionRate)) {
                    out.write("success");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("Lưu thất bại");
                }

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("Dữ liệu không hợp lệ");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("Lỗi server");
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
