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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import salepro.dao.InvoiceDAO;
import salepro.dao.StoreDAO;
import salepro.models.Invoices;

/**
 *
 * @author ADMIN
 */
public class InvoiceController extends HttpServlet {

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
            out.println("<title>Servlet InvoiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceController at " + request.getContextPath() + "</h1>");
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
        InvoiceDAO invoiceDA = new InvoiceDAO();
        StoreDAO storeDA= new StoreDAO();
//        try (PrintWriter out = response.getWriter()) {
//            out.print(da.getData().size());
//        }
        request.setAttribute("stores", storeDA.getData());
        request.setAttribute("data", invoiceDA.getData());
        request.getRequestDispatcher("view/jsp/admin/InvoiceManager/invoicelist.jsp").forward(request, response);
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
        String sort = request.getParameter("sort");
        InvoiceDAO da = new InvoiceDAO();
        if (sort != null) {
            if (sort.equals("1")) {
                request.setAttribute("data", da.getSortByDateDesc());
            }
//            if (sort.equals("2")) {
//                request.setAttribute("data", da.getSortByCustomerNameDesc());
//            }
//            if (sort.equals("3")) {
//                request.setAttribute("data", da.getSortByPaymentDesc());
//            }
//            if (sort.equals("4")) {
//                request.setAttribute("data", da.getSortByStoreDesc());
//            }
//            if (sort.equals("5")) {
//                request.setAttribute("data", da.getSortByTotalDesc());
//            }
//            if (sort.equals("6")) {
//                request.setAttribute("data", da.getSortByBillerDesc());
//            }
        }

        request.getRequestDispatcher("view/jsp/admin/InvoiceManager/invoicelist.jsp").forward(request, response);
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
