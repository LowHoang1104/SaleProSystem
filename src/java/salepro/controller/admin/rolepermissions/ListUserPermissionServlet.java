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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import salepro.dao.CategoryPermissionDAO;
import salepro.dao.EmployeeTypeDAO;
import salepro.dao.PermissionDAO;
import salepro.models.CategoryPermissions;
import salepro.models.EmployeeTypes;
import salepro.models.Permissions;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.PurchaseDAO;
import salepro.dao.ShopOwnerDAO;
import salepro.dao.StoreDAO;
import salepro.dao.UserDAO;

/**
 *
 * @author Thinhnt
 */
@WebServlet(name = "ListUserPermissionServlet", urlPatterns = {"/ListUserPermissionServlet"})
public class ListUserPermissionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * ======== public class LoginOnwerShopController extends HttpServlet {
     *
     * /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. >>>>>>>>
     * main:src/java/salepro/controller/LoginOnwerShopController.java
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
            out.println("<title>Servlet ListRolePermissionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListRolePermissionServlet at " + request.getContextPath() + "</h1>");
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
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();
        List<EmployeeTypes> listE = eDao.getData();

        PermissionDAO pDao = new PermissionDAO();
        List<Permissions> listP = pDao.getData();

        Map<Integer, List<Permissions>> userPemissions = new HashMap<>();
        for (EmployeeTypes employeeType : listE) {
            int empTypeId = employeeType.getEmployeeTypeID();
            userPemissions.put(empTypeId, pDao.getPermissionsByEmployeeType(empTypeId));
        }

        CategoryPermissionDAO cDao = new CategoryPermissionDAO();
        List<CategoryPermissions> categoryPer = cDao.getData();

        Map<Integer, List<Permissions>> perByCategory = new HashMap<>();
        for (CategoryPermissions categoryPermissions : categoryPer) {
            int categoryId = categoryPermissions.getCategoryPerId();
            perByCategory.put(categoryId, pDao.getAllPermissionByCategoryId(categoryId));
        }

        String addSuccess = request.getParameter("addEmployeeType");
        if (addSuccess != null) {
            request.setAttribute("addEmployeeType", addSuccess.equalsIgnoreCase("true")); // nếu cần truyền qua JSP
        }

        String updateEmployeeType = request.getParameter("updateEmployeeType");
        if (updateEmployeeType != null) {
            request.setAttribute("updateEmployeeType", updateEmployeeType.equalsIgnoreCase("true"));
        }

        String isEditAdmin = request.getParameter("isEditAdmin");
        if (isEditAdmin != null) {
            request.setAttribute("isEditAdmin", isEditAdmin.equalsIgnoreCase("false")); // nếu cần truyền qua JSP
        }

        request.setAttribute("categories", categoryPer);
        request.setAttribute("perByCategory", perByCategory);
        request.setAttribute("userPemissions", userPemissions);
        request.setAttribute("employeeTypes", listE);
        request.setAttribute("permissions", listP);

        String empTypeIdStr = request.getParameter("empTypeId");
        String openUpdateRole = request.getParameter("openUpdateRole");
        String action = request.getParameter("action");

        if (action != null) {
            int empTypeId = Integer.parseInt(empTypeIdStr);
            if (action.equalsIgnoreCase("update") && empTypeIdStr != null && openUpdateRole != null) {
                EmployeeTypes empType = eDao.getEmployeeTypeById(empTypeId);
                List<Permissions> perByEmpType = pDao.getPermissionsByEmployeeType(empTypeId);
                List<Integer> perByEmpTypeId = new ArrayList<>();
                for (Permissions permissions : perByEmpType) {
                    perByEmpTypeId.add(permissions.getPermissionID());
                }
                request.setAttribute("empTypeId", empTypeId);
                request.setAttribute("empTypeName", empType.getTypeName());
                request.setAttribute("openUpdateRole", openUpdateRole);
                request.setAttribute("perByEmpTypeId", perByEmpTypeId);
            }
            if (action.equalsIgnoreCase("delete")) {
                boolean delete = eDao.deleteEmpTypeById(empTypeId);
                request.setAttribute("deleteEmpTypeFail", !delete);
                request.setAttribute("deleteEmpTypeSuccess", delete);
            }
        }

        request.getRequestDispatcher("view/jsp/admin/RoleManagement/List_role.jsp").forward(request, response);

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
        EmployeeTypeDAO eDao = new EmployeeTypeDAO();
        List<EmployeeTypes> listE = eDao.getData();

        PermissionDAO pDao = new PermissionDAO();
        List<Permissions> listP = pDao.getData();

        Map<Integer, List<Permissions>> userPemissions = new HashMap<>();
        for (EmployeeTypes employeeType : listE) {
            int empTypeId = employeeType.getEmployeeTypeID();
            userPemissions.put(empTypeId, pDao.getPermissionsByEmployeeType(empTypeId));
        }

        CategoryPermissionDAO cDao = new CategoryPermissionDAO();
        List<CategoryPermissions> categoryPer = cDao.getData();

        Map<Integer, List<Permissions>> perByCategory = new HashMap<>();
        for (CategoryPermissions categoryPermissions : categoryPer) {
            int categoryId = categoryPermissions.getCategoryPerId();
            perByCategory.put(categoryId, pDao.getAllPermissionByCategoryId(categoryId));
        }

        Boolean openAddRole = (Boolean) request.getAttribute("openAddRole");
        request.setAttribute("openAddRole", openAddRole); // nếu cần truyền qua JSP

        request.setAttribute("categories", categoryPer);
        request.setAttribute("perByCategory", perByCategory);
        request.setAttribute("userPemissions", userPemissions);
        request.setAttribute("employeeTypes", listE);
        request.setAttribute("permissions", listP);

        request.getRequestDispatcher("view/jsp/admin/RoleManagement/List_role.jsp").forward(request, response);

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
