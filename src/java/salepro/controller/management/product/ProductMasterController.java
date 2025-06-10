package salepro.controller.management.product;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.util.*;
import salepro.dao.*;
import salepro.models.*;

@WebServlet("/upload")
@MultipartConfig
public class ProductMasterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String mode = request.getParameter("mode");

        ProductMasterDAO pdao = new ProductMasterDAO();

        if (mode.equals("1")) {
            ProductMasters p = pdao.getProductById(id);
            request.setAttribute("p", p);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/product_detail.jsp").forward(request, response);

        } else if (mode.equals("2")) {
            ProductMasters p = pdao.getProductById(id);
            List<Categories> cdata = new CategoryDAO().getCategory();
            List<ProductTypes> tdata = new TypeDAO().getTypes();
            List<Stores> stdata = new StoreDAO().getStores();

            request.setAttribute("p", p);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("stdata", stdata);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/product_update.jsp").forward(request, response);

        } else if (mode.equals("3")) {
            pdao.delProductById(id);

            List<ProductMasters> pdata = pdao.getData();
            List<Categories> cdata = new CategoryDAO().getCategory();
            List<ProductTypes> tdata = new TypeDAO().getTypes();
            List<Stores> stdata = new StoreDAO().getStores();

            request.setAttribute("pdata", pdata);
            request.setAttribute("cdata", cdata);
            request.setAttribute("tdata", tdata);
            request.setAttribute("stdata", stdata);
            request.getRequestDispatcher("view/jsp/admin/ProductManagement/productlist.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("filter") != null ? "filter"
                : request.getParameter("add") != null ? "add"
                : request.getParameter("update") != null ? "update"
                : request.getParameter("search") != null ? "search"
                : "";

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        String des = request.getParameter("des");
        String price = request.getParameter("price");
        String cost = request.getParameter("cost");
        String store = request.getParameter("store");
        String kw = request.getParameter("kw");

        String image;
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            InputStream inputStream = filePart.getInputStream();
            byte[] fileBytes = inputStream.readAllBytes();
            image = Base64.getEncoder().encodeToString(fileBytes);
        } else {
            image = request.getParameter("oldImage");
            if (image == null) {
                String relativePath = "/view/assets/img/product/product1.jpg";
                String realPath = getServletContext().getRealPath(relativePath); // chuyển sang đường dẫn thật

                FileInputStream fis = new FileInputStream(realPath);
                byte[] fileBytes = fis.readAllBytes();
                fis.close();

                image = Base64.getEncoder().encodeToString(fileBytes);
            }
        }

        Date date = new Date();
        ProductMasterDAO pdao = new ProductMasterDAO();
        List<ProductMasters> pdata;

        switch (action) {
            case "filter" ->
                pdata = pdao.filterProduct(category, type, store);

            case "add" -> {
                int cate = Integer.parseInt(category);
                int tp = Integer.parseInt(type);
                double price1 = Double.parseDouble(price);
                double cost1 = Double.parseDouble(cost);
                ProductMasters pm = new ProductMasters(id, name, cate, tp, des, price1, cost1, image, true, date);
                pdao.addProduct(pm);
                pdata = pdao.getData();
            }

            case "update" -> {
                if (name == null || name.isBlank()) {
                    ProductMasters p = pdao.getProductById(id);
                    String err = "Vui lòng nhập tên sản phẩm!";
                    setCommonAttributes(request);
                    request.setAttribute("err", err);
                    request.setAttribute("p", p);
                    request.getRequestDispatcher("view/jsp/admin/ProductManagement/product_update.jsp").forward(request, response);
                    return;
                } else {
                    int cate = Integer.parseInt(category);
                    int tp = Integer.parseInt(type);
                    double price1 = Double.parseDouble(price);
                    double cost1 = Double.parseDouble(cost);
                    ProductMasters pm = new ProductMasters(id, name, cate, tp, des, price1, cost1, image, true, date);
                    pdao.updateProduct(pm);
                    pdata = pdao.getData();
                }
            }

            case "search" ->
                pdata = pdao.serchByKeyword(kw);

            default ->
                pdata = pdao.getData();
        }

        setCommonAttributes(request);
        request.setAttribute("pdata", pdata);
        request.getRequestDispatcher("view/jsp/admin/ProductManagement/productlist.jsp").forward(request, response);
    }

    private void setCommonAttributes(HttpServletRequest request) {
        CategoryDAO cdao = new CategoryDAO();
        TypeDAO tdao = new TypeDAO();
        StoreDAO stdao = new StoreDAO();
        request.setAttribute("cdata", cdao.getCategory());
        request.setAttribute("tdata", tdao.getTypes());
        request.setAttribute("stdata", stdao.getStores());
    }

    @Override
    public String getServletInfo() {
        return "Product Management Controller";
    }
}
