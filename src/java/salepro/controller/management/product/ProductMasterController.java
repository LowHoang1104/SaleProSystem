package salepro.controller.management.product;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            ProductVariantDAO pvdao = new ProductVariantDAO();
            List<ProductVariants> pvdata = pvdao.getProductVariantByID(id);
            ProductMasters p = pdao.getProductById(id);
            ColorDAO cdao = new ColorDAO();
            List<Colors> cldata = cdao.getColors();
            SizeDAO sdao = new SizeDAO();
            List<Sizes> sdata = sdao.getSize();
            request.setAttribute("cldata", cldata);
            request.setAttribute("sdata", sdata);
            request.setAttribute("p", p);
            request.setAttribute("pvdata", pvdata);
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
        if (price != null && cost != null) {
            price = price.replace(".", "").replace(" ", "");
            cost = cost.replace(".", "").replace(" ", "");
        }
        String store = request.getParameter("store");
        String kw = request.getParameter("kw");
        String err = "";
        boolean isError = true;
        Part filePart = null;
        boolean isMultipart = request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart/");

        if (isMultipart) {
            filePart = request.getPart("image");
        }

        String imageSrc = "";

        if (filePart != null && filePart.getSize() > 0) {
            String fileType = filePart.getContentType();

            if (fileType.startsWith("image/")) {
                if (filePart.getSize() > 1048576) {
                    err += "Kích thước ảnh không được vượt quá 1MB.";
                    isError = false;
                } else {
                    try {
                        String projectPath = "C:/Users/tungd/OneDrive/Máy tính/SWP/SaleProSystem";
                        String uploadDir = projectPath + "/web/view/assets/img/upload";

                        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                        File file = new File(uploadDir, fileName);
                        file.getParentFile().mkdirs();

                        try (InputStream input = filePart.getInputStream()) {
                            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }

                        imageSrc = request.getContextPath() + "/view/assets/img/upload/" + fileName;
                        System.out.println("Đã lưu ảnh vào: " + file.getAbsolutePath());

                    } catch (Exception ex) {
                        err += "Lỗi khi lưu ảnh: " + ex.getMessage();
                        isError = false;
                    }
                }
            } else {
                err += "Vui lòng sử dụng đúng file ảnh.";
                isError = false;
            }
        } else {
            imageSrc = request.getParameter("oldImage");

            if (imageSrc == null || imageSrc.isBlank()) {
                imageSrc = request.getContextPath() + "/view/assets/img/product/default.png";
            }
        }

        Date date = new Date();
        ProductMasterDAO pdao = new ProductMasterDAO();
        List<ProductMasters> pdata = new ArrayList();
        if (name == null || name.trim().isEmpty()) {
            err += "Tên sản phẩm ko thể bị bỏ trống";
            isError = false;
        }
        if (price == null || price.trim().isEmpty() || cost == null || cost.trim().isEmpty()) {
            err += "Tiền vốn và giá bán ko thể để trống";
            isError = false;
        } else {
            if (Double.parseDouble(price) < 0 || Double.parseDouble(cost) < 0) {
                err += "Tiền vốn và giá bán ko thể âm";
                isError = false;
            }
        }
        if (action.equals("add")) {
            if (pdao.exitID(id)) {
                err += "ID đã tồn tại";
                isError = false;
            }
        }

        switch (action) {
            case "filter" ->
                pdata = pdao.filterProduct(category, type, store);

            case "add" -> {
                if (isError == true) {
                    int cate = Integer.parseInt(category);
                    int tp = Integer.parseInt(type);
                    double price1 = Double.parseDouble(price);
                    double cost1 = Double.parseDouble(cost);
                    ProductMasters pm = new ProductMasters(id, validateKeyword(name), cate, tp, des, price1, cost1, imageSrc, true, date);
                    pdao.addProduct(pm);
                    pdata = pdao.getData();
                } else if (isError == false) {
                    request.setAttribute("err", err);
                    setCommonAttributes(request);
                    request.getRequestDispatcher("view/jsp/admin/ProductManagement/addproduct.jsp").forward(request, response);
                    return;
                }
            }

            case "update" -> {
                if (name == null || name.isBlank()) {
                    ProductMasters p = pdao.getProductById(id);
                    err = "Vui lòng nhập tên sản phẩm!";
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
                    ProductMasters pm = new ProductMasters(id, validateKeyword(name), cate, tp, des, price1, cost1, imageSrc, true, date);
                    pdao.updateProduct(pm);
                    pdata = pdao.getData();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProductMasterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    @Override
    public String getServletInfo() {
        return "Product Management Controller";
    }
}
