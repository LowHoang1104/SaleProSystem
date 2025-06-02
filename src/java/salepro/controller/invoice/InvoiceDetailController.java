package salepro.controller.invoice;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */



import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import salepro.dao.CustomerDAO;
import salepro.dao.InvoiceDAO;
import salepro.dao.InvoiceDetailDAO;
import salepro.dao.StoreDAO;
import salepro.models.InvoiceDetails;

/**
 *
 * @author ADMIN
 */
public class InvoiceDetailController extends HttpServlet {

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
            out.println("<title>Servlet InvoiceDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceDetailController at " + request.getContextPath() + "</h1>");
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
        String invoiceID = request.getParameter("invoiceID");
        ArrayList<InvoiceDetails> data = new ArrayList<>();
        InvoiceDetailDAO invoiceDetailDA = new InvoiceDetailDAO();
        data = invoiceDetailDA.getInvoiceDetailByID(Integer.parseInt(invoiceID));
        InvoiceDAO invoiceDA = new InvoiceDAO();
        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {
            request.setAttribute("invoiceID", invoiceID);
            request.setAttribute("data", data);
            request.setAttribute("customer", invoiceDA.getCustomerByInvoiceID(Integer.parseInt(invoiceID)));
            request.getRequestDispatcher("view/jsp/admin/InvoiceManager/invoicedetail.jsp").forward(request, response);
            
        } else if (request.getParameter("mode") != null && request.getParameter("mode").equals("2")) {
            CustomerDAO customerDA = new CustomerDAO();   
            StoreDAO storeDA=new StoreDAO();
            request.setAttribute("data", data);
            request.setAttribute("invoice", invoiceDA.getInvoiceById(Integer.parseInt(invoiceID)));
            request.setAttribute("customers", customerDA.getData());
            request.setAttribute("stores", storeDA.getData());
            request.getRequestDispatcher("view/jsp/admin/InvoiceManager/edit_invoice.jsp").forward(request, response);
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
        processRequest(request, response);
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
