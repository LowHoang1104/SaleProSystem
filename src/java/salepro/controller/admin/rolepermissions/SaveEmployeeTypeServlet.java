/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.admin.rolepermissions;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.PermissionDAO;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "SaveEmployeeTypeServlet", urlPatterns = {"/SaveEmployeeTypeServlet"})
public class SaveEmployeeTypeServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateEmployeeTypeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateEmployeeTypeServlet at " + request.getContextPath() + "</h1>");
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
        //xử lí DAO
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();

        String empTypeId = request.getParameter("empTypeId");
        //Xử lí xóa         
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(empTypeId);
            boolean delete = eDao.deleteEmpTypeById(id);
            response.sendRedirect("ListUserPermissionServlet?isDelete=" + delete);
        }
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
        //xử lí DAO
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();
        //Nhận các value từ form
        String typeName = request.getParameter("typeName");
        String empTypeId = request.getParameter("empTypeId");
        String error = "";
        String[] arrPermission = request.getParameterValues("permissionIDs");
        List<Integer> permissionIds = Arrays.stream(arrPermission).map(Integer::parseInt).toList();
        boolean success = false;
        //Xử lí xóa         
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(empTypeId);
            boolean delete = eDao.deleteEmpTypeById(id);
            response.sendRedirect("ListUserPermissionServlet?isDelete=" + delete);
        }
        //Xử lí update
        if (empTypeId != null && !empTypeId.isBlank()) {
            int id = Integer.parseInt(empTypeId);
            if (typeName != null && !typeName.isBlank() && !typeName.equals(eDao.getEmployeeTypeById(id).getTypeName())) {
                if (eDao.checkEmployeeTypeName(typeName)) {
                    error = "Vai trò đã tồn tại. Vui lòng nhập lại vai trò!";
                }
            }
            if (error != null && !error.isBlank()) {
                request.setAttribute("error", error);
                request.setAttribute("empTypeId", empTypeId);
                request.setAttribute("empTypeName", typeName);
                request.setAttribute("selectedPermissions", permissionIds); // ✅ gửi lại danh sách đã chọn
                request.setAttribute("updateEmployeeType", success);
                request.setAttribute("openUpdateRole", true);
                request.getRequestDispatcher("ListUserPermissionServlet").forward(request, response);
                return;
            } else {
                PermissionDAO pDao = new PermissionDAO();
                success = pDao.updatePermissionsForEmployeeType(id, permissionIds);
                if (success) {
                    eDao.updateEmpTypeNameById(id, typeName);
                }
//                request.setAttribute("updateEmployeeType", success);
//                request.getRequestDispatcher("ListUserPermissionServlet").forward(request, response);

                response.sendRedirect("ListUserPermissionServlet?updateEmployeeType=" + success);
            }
        } else {
            if (eDao.checkEmployeeTypeName(typeName)) {
                error = "Vai trò đã tồn tại. Vui lòng nhập lại vai trò!";
                request.setAttribute("error", error);
                request.setAttribute("selectedPermissions", permissionIds);
                request.setAttribute("typeName", typeName);
                request.setAttribute("openAddRole", true);
                request.getRequestDispatcher("ListUserPermissionServlet?openAddRole=true").forward(request, response);
            } else {
                int eTypeId = eDao.insertEmployeeType(typeName);
                PermissionDAO pDao = new PermissionDAO();
                success = pDao.insertPermissionForEmployeeType(eTypeId, permissionIds);

                response.sendRedirect("ListUserPermissionServlet?addEmployeeType=" + success);
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
