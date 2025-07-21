/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.management.product;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import salepro.dao.CategoryDAO;
import salepro.dao.ColorDAO;
import salepro.dao.SizeDAO;
import salepro.dao.TypeDAO;
import salepro.models.Categories;
import salepro.models.Colors;
import salepro.models.ProductTypes;
import salepro.models.Sizes;

/**
 *
 * @author tungd
 */
@WebServlet(name = "AttributeController", urlPatterns = {"/attributecontroller"})
public class AttributeController extends HttpServlet {

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
            out.println("<title>Servlet AttributeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AttributeController at " + request.getContextPath() + "</h1>");
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
        CategoryDAO cdao = new CategoryDAO();
        TypeDAO tdao = new TypeDAO();
        SizeDAO sdao = new SizeDAO();
        ColorDAO cldao = new ColorDAO();
        List<Categories> cdata = cdao.getCategory();
        List<ProductTypes> tdata = tdao.getTypes();
        List<Sizes> sdata = sdao.getSize();
        List<Colors> cldata = cldao.getColors();
        String atb = request.getParameter("atb");
        HttpSession attribute = request.getSession();
        String mode = request.getParameter("mode");
        String successMessage = "";
        String errMessage = "";
        if (atb == null || atb.trim().isEmpty()) {
            atb = (String) attribute.getAttribute("atb");
            if (atb == null) {
                atb = "1"; // Chỉ gán "1" nếu session cũng không có
            }
        } else {
            // Nếu atb hợp lệ, cập nhật session
            attribute.setAttribute("atb", atb);
        }
        if (mode != null) {
///////////////////////////////////////////////////////////////////////////////////////////////////////////
            //DELETE
            if (mode.equals("2") && String.valueOf(attribute.getAttribute("atb")).equals("1")) {
                //Lấy Id
                String id = request.getParameter("id");
                //Xóa Attribute
            }
            if (mode.equals("2") && String.valueOf(attribute.getAttribute("atb")).equals("2")) {
                //Lấy Id
                String id = request.getParameter("id");
                //Xóa Attribute
            }
            if (mode.equals("2") && String.valueOf(attribute.getAttribute("atb")).equals("3")) {
                //Lấy Id
                String id = request.getParameter("id");
                //Xóa Attribute
            }
            if (mode.equals("2") && String.valueOf(attribute.getAttribute("atb")).equals("4")) {
                //Lấy Id
                String id = request.getParameter("id");
                //Xóa Attribute
                if (cldao.exidColorInProduct(id)) {
                    errMessage += "Product with this color exists and cannot be deleted";
                } else {
                    cldao.delColorById(id);
                    successMessage += "Xóa Thành Công";
                    cldata = cldao.getColors();
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        attribute.setAttribute("atb", atb);
        if ("1".equals(atb)) {
            request.setAttribute("tdata", tdata);
            request.setAttribute("cdata", cdata);
        }
        if ("2".equals(atb)) {
            request.setAttribute("tdata", tdata);
        }
        if ("3".equals(atb)) {
            request.setAttribute("sdata", sdata);
        }
        if ("4".equals(atb)) {
            request.setAttribute("cldata", cldata);
        }
        request.setAttribute("errMessage", errMessage);
        request.setAttribute("successMessage", successMessage);
        request.getRequestDispatcher("view/jsp/admin/ProductManagement/attributelist.jsp").forward(request, response);
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
        CategoryDAO cdao = new CategoryDAO();
        TypeDAO tdao = new TypeDAO();
        SizeDAO sdao = new SizeDAO();
        ColorDAO cldao = new ColorDAO();
        List<Categories> cdata = cdao.getCategory();
        List<ProductTypes> tdata = tdao.getTypes();
        List<Sizes> sdata = sdao.getSize();
        List<Colors> cldata = cldao.getColors();
        String atb = request.getParameter("atb");
        HttpSession attribute = request.getSession();
        String add = request.getParameter("add");
        String err = "";
        if (atb == null || atb.trim().isEmpty()) {
            atb = (String) attribute.getAttribute("atb");
            if (atb == null) {
                atb = "1"; // Chỉ gán "1" nếu session cũng không có
                attribute.setAttribute("atb", atb);
            }
        } else {
            // Nếu atb hợp lệ, cập nhật session
            attribute.setAttribute("atb", atb);
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ADD
        if (add != null && String.valueOf(attribute.getAttribute("atb")).equals("1")) {
            String name = request.getParameter("categoryName");
            if (name == null || name.isEmpty()) {
                err += "Điền đầy đủ thông tin";
            } else {
                String typeId = request.getParameter("typeID");
                // add thêm attribute mới
                cdao.addCategory(typeId, validateKeyword(name));
                cdata = cdao.getCategory();
            }
        }
        if (add != null && String.valueOf(attribute.getAttribute("atb")).equals("2")) {
            String name = request.getParameter("typeName");
            if (name == null || name.isEmpty()) {
                err += "Điền đầy đủ thông tin";
            } else {
                // add thêm attribute mới
                tdao.addType(validateKeyword(name));
                tdata = tdao.getTypes();
            }
        }
        if (add != null && String.valueOf(attribute.getAttribute("atb")).equals("3")) {
            String name = request.getParameter("sizeName");
            if (name == null || name.isEmpty()) {
                err += "Điền đầy đủ thông tin";
            } else {
                // add thêm attribute mới
                sdao.addSize(validateKeyword(name));
                sdata = sdao.getSize();
            }
        }
        if (add != null && String.valueOf(attribute.getAttribute("atb")).equals("4")) {
            String name = request.getParameter("colorName");
            if (name == null || name.isEmpty()) {
                err += "Điền đầy đủ thông tin";
            } else {
                // add thêm attribute mới
                cldao.addColor(validateKeyword(name));
                cldata = cldao.getColors();
            }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //SEARCH
        String search = request.getParameter("search");
        String kw = request.getParameter("kw");
        if (search != null && String.valueOf(attribute.getAttribute("atb")).equals("1")) {
            cdata = cdao.searchByKw(validateKeyword(kw));
        }
        if (search != null && String.valueOf(attribute.getAttribute("atb")).equals("2")) {
            tdata = tdao.searchByKw(validateKeyword(kw));
        }
        if (search != null && String.valueOf(attribute.getAttribute("atb")).equals("3")) {
            sdata = sdao.searchByKw(validateKeyword(kw));
        }
        if (search != null && String.valueOf(attribute.getAttribute("atb")).equals("4")) {
            cldata = cldao.searchByKw(validateKeyword(kw));
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //DELETE

///////////////////////////////////////////////////////////////////////////////////////////////////////////
        attribute.setAttribute("atb", atb);
        if ("1".equals(atb)) {
            request.setAttribute("tdata", tdata);
            request.setAttribute("cdata", cdata);
        }
        if ("2".equals(atb)) {
            request.setAttribute("tdata", tdata);
        }
        if ("3".equals(atb)) {
            request.setAttribute("sdata", sdata);
        }
        if ("4".equals(atb)) {
            request.setAttribute("cldata", cldata);
        }
        if (err.isBlank()) {
            response.sendRedirect("attributecontroller");
        } else {
            request.setAttribute("err", err);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/attributelist.jsp").forward(request, response);
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

    private String validateKeyword(String kw) {
        String[] list = kw.trim().split("[^\\p{L}]+");
        String key = "";
        for (int i = 0; i < list.length; i++) {
            if (i == list.length - 1) {
                key += list[i];
            } else {
                key += list[i] + " ";
            }
        }
        return key;
    }
}
