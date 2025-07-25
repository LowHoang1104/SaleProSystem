package salepro.controller.management.cashier;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import salepro.models.ProductMasters;
import com.google.gson.Gson;
import salepro.dao.ProductMasterDAO;

// Try different mapping patterns
@WebServlet(name = "ProductSearchServlet", urlPatterns = {
    "/ProductSearchServlet", 
    "/product-search",
    "/api/products/search"
})
public class ProductSearchServlet extends HttpServlet {
    
    private ProductMasterDAO productDAO;
    private Gson gson;
    
    @Override
    public void init() throws ServletException {
        productDAO = new ProductMasterDAO();
        gson = new Gson();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response headers FIRST
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        String keyword = request.getParameter("keyword");
        
        PrintWriter out = response.getWriter();
        
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                out.print("[]");
                out.flush();
                return;
            }
            
            List<ProductMasters> products = productDAO.serchByKeyword(keyword.trim());
            
            // Create simple objects for JSON to avoid circular references
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < products.size(); i++) {
                ProductMasters product = products.get(i);
                if (i > 0) json.append(",");
                json.append("{")
                    .append("\"code\":\"").append(escapeJson(product.getCode())).append("\",")
                    .append("\"name\":\"").append(escapeJson(product.getName())).append("\",")
                    .append("\"price\":").append(product.getPrice())
                    .append("}");
            }
            json.append("]");
            
            out.print(json.toString());
            out.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
            
            out.print("[]");
            out.flush();
        }
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"")
                  .replace("\\", "\\\\")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}