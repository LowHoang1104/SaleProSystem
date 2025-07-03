/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package salepro.controller.invoice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.InvoiceDetailDAO;
import salepro.dao.UserDAO;
import salepro.models.Customers;
import salepro.models.InvoiceDetails;
import salepro.models.Invoices;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "InvoiceDetailServlet", urlPatterns = {"/InvoiceDetailServlet"})
public class InvoiceDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String invoiceIdStr = request.getParameter("invoiceId");
        
        if (action == null || invoiceIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }
        
        try {
            int invoiceId = Integer.parseInt(invoiceIdStr);
            
            switch (action) {
                case "getDetail":
                    getInvoiceDetail(request, response, invoiceId);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid invoice ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error");
        }
    }
    
    /**
     * Get invoice detail and return as JSON
     */
    private void getInvoiceDetail(HttpServletRequest request, HttpServletResponse response, int invoiceId) 
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Get invoice basic info
            Invoices invoice = invoiceDAO.getInvoiceById(invoiceId);
            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
                return;
            }
            
            // Create response object with fields needed by modal
            InvoiceDetailResponse detailResponse = new InvoiceDetailResponse();
            
            // Basic invoice info
            detailResponse.invoiceId = invoice.getInvoiceId();
            detailResponse.invoiceDate = invoice.getInvoiceDate();
            detailResponse.updateDate = invoice.getUpdateDate();
            detailResponse.status = invoice.getStatus();
            detailResponse.totalAmount = invoice.getTotalAmount();
            detailResponse.VATAmount = invoice.getVATAmount();
            detailResponse.subTotal = invoice.getSubTotal();
            
            
            detailResponse.listUser = userDAO.getData();
            System.out.println(detailResponse.listUser.size());
            // Customer info
            detailResponse.customerName = customerDAO.getCustomerNameByID(invoiceId);
            
            // User names as strings
            detailResponse.createdBy = getUserNameById(invoice.getCreatedBy(), detailResponse.listUser);
            detailResponse.soldBy = getUserNameById(invoice.getUserId(), detailResponse.listUser);
            
            // For dropdown - keep user IDs
            detailResponse.createdById = invoice.getCreatedBy();
            detailResponse.soldById = invoice.getUserId();
            
            detailResponse.invoiceDetails = new java.util.ArrayList<>();
            try {
                List<InvoiceDetails> invoiceDetails = invoiceDetailDAO.getInvoiceDetailByID(invoiceId);
                if (invoiceDetails != null && !invoiceDetails.isEmpty()) {
                    detailResponse.invoiceDetails = invoiceDetails;
                    detailResponse.totalItems = invoiceDetails.size();
                } else {
                    detailResponse.totalItems = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                detailResponse.totalItems = 0;
            }
            
            // Static values for now (can be made dynamic later)
            detailResponse.discount = 0.0;
            detailResponse.branch = "Chi nhánh trung tâm";
            detailResponse.salesChannel = "Bán trực tiếp";
            detailResponse.priceList = "Bảng giá chung";
            detailResponse.paymentMethod = invoice.getPaymentMethodName();
            
            // Convert to JSON and send response
            Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm")
                .create();
            
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(detailResponse));
            out.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving invoice details");
        }
    }
    
    /**
     * Helper method to get user name by ID
     */
    private String getUserNameById(Integer userId, List<Users> userList) {
        if (userId == null || userList == null) return "N/A";
        
        for (Users user : userList) {
            if (user.getUserId() == userId) {
                return user.getFullName();
            }
        }
        return "N/A";
    }
    
    // Response class matching modal requirements
    private static class InvoiceDetailResponse {
        public int invoiceId;
        public java.util.Date invoiceDate;
        public java.util.Date updateDate;
        public String status;
        public Double totalAmount;
        public Double VATAmount;
        public Double subTotal;
        public Double discount;
        public String customerName;
        public String createdBy;        
        public String soldBy;           
        public Integer createdById;     
        public Integer soldById;        
        public String branch;
        public String salesChannel;
        public String priceList;
        public String paymentMethod;
        public Integer totalItems;
        public List<InvoiceDetails> invoiceDetails;
        public List<Users> listUser;    
    }
}