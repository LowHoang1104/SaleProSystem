package salepro.controller.cashbook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import salepro.dao.FundTransactionDAO;
import salepro.dao.PayrollPeriodDAO;
import salepro.models.FundTransactions;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(urlPatterns={"/ComfirmFundTransaction"})
public class ComfirmFundTransaction extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ComfirmFundTransaction</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComfirmFundTransaction at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        FundTransactionDAO da= new FundTransactionDAO();
        PayrollPeriodDAO perolldao= new PayrollPeriodDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session= request.getSession();
        Users user= (Users) session.getAttribute("user");
        da.ApproveStatus( id,user.getUserId());
        request.getRequestDispatcher("cashbookController").forward(request, response);
        FundTransactions trans= da.getFundById(id);
        if(trans.getReferenceType()!=null&& trans.getReferenceType().equals("Salary")){
            perolldao.salaryPaid(trans.getReferenceID(), user.getUserId(), "Paid", LocalDateTime.now());
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
