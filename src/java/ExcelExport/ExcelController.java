/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ExcelExport;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import salepro.dao.FundTransactionDAO;
import salepro.dao.StoreDAO;
import salepro.models.FundTransactions;
import salepro.models.Stores;
import salepro.models.Users;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ExcelController", urlPatterns = {"/ExcelController"})
public class ExcelController extends HttpServlet {

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
            out.println("<title>Servlet ExcelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExcelController at " + request.getContextPath() + "</h1>");
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
        String mode = request.getParameter("mode");
        if (mode != null && mode.equals("excel_cashbook")) {
            StoreDAO storeda = new StoreDAO();
            FundTransactionDAO da = new FundTransactionDAO();
            HttpSession session = request.getSession();
            ArrayList<Stores> store = (ArrayList<Stores>) session.getAttribute("storecurrent");
            ArrayList<FundTransactions> data = new ArrayList<>();
            int storeId1;
            storeId1 = store.get(0).getStoreID();
            Users a = (Users) session.getAttribute("user");
            String fundId = request.getParameter("fundId");

            if (a.getRoleId() == 1) {
                data = da.getDataByStoreId(store.get(0).getStoreID());
                String storeId = request.getParameter("storeId");
                System.out.println(storeId);
                if (storeId != null) {

                    storeId1 = Integer.parseInt(storeId);
                    data = da.getDataByStoreId(Integer.parseInt(storeId));
                }
            } else {
                data = da.getDataByStoreId(store.get(0).getStoreID());
                
            }
            if(fundId!=null&&!fundId.equals("")){
                data= da.getDataByFundId(Integer.parseInt(request.getParameter("fundId")));
            }

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Transaction của" + storeda.getStoreNameByID(storeId1));
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(1).setCellValue("Name Fund");
            headerRow.createCell(2).setCellValue("TransactionType");
            headerRow.createCell(3).setCellValue("Amount");
            headerRow.createCell(4).setCellValue("Description");
            headerRow.createCell(5).setCellValue("Object recived");
            headerRow.createCell(6).setCellValue("TransactionDate");
            headerRow.createCell(7).setCellValue("CreatedBy");
            headerRow.createCell(8).setCellValue("ApprovedBy");
            headerRow.createCell(9).setCellValue("Status");
            headerRow.createCell(10).setCellValue("Notes");

            String filename = "Transaction.xlsx";
            String uploadPath = getServletContext().getRealPath("").split("build")[0] + "web\\uploadedFiles";
            System.out.println(uploadPath);

            File excelfile = new File(uploadPath, filename);

            for (int i = 0; i < data.size(); i++) {
                Row bodyRow = sheet.createRow(i + 1);

                bodyRow.createCell(1).setCellValue(data.get(i).getStoreFundbyFundID().getFundName());
                bodyRow.createCell(2).setCellValue(data.get(i).getTransactionType());
                bodyRow.createCell(3).setCellValue(data.get(i).getAmount());
                bodyRow.createCell(4).setCellValue(data.get(i).getDescription());
                bodyRow.createCell(5).setCellValue(data.get(i).getReferenceType());
                bodyRow.createCell(6).setCellValue(data.get(i).getTransactionDate());
                bodyRow.createCell(7).setCellValue(data.get(i).getCreatedBy());
                bodyRow.createCell(8).setCellValue(data.get(i).getApprovedBy());
                bodyRow.createCell(9).setCellValue(data.get(i).getStatus());
                bodyRow.createCell(10).setCellValue(data.get(i).getNotes());
            }
            try {
                FileOutputStream fileout = new FileOutputStream(excelfile);
                workbook.write(fileout);
                workbook.close();

                //lam cham qua trinh gui lai lên jsp để đủ thời gian tạo file
//                int maxtries = 50;
//                int tries = 0;
//                int delayMs = 200;
//                while (!excelfile.exists() && tries < maxtries) {
//                    Thread.sleep(delayMs);
//                    System.out.println();
//                    tries++;
//                }
                Thread.sleep(2000);

                if (excelfile.exists()) {
                    response.getWriter().write("uploadedFiles/Transaction.xlsx");
                } else {
                    response.getWriter().write("error");
                }
//                boolean deleted = excelfile.delete();
//                if(deleted){
//                    System.out.println("OKE");
//                }else{
//                    System.out.println("ERR");
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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
