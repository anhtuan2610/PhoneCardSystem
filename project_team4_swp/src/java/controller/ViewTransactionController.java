/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TransactionDAO;
import entity.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author phamtung
 */
public class ViewTransactionController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewTransactionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewTransactionController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("./viewTransaction.jsp").forward(request, response);
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
        JSONObject jsonObject = new JSONObject();
        TransactionDAO tranDAO = new TransactionDAO();

        String id = request.getParameter("id");
        int transactionId = Integer.parseInt(id);

        Transaction tran = tranDAO.getTransactionById(transactionId);
        
        String htmls = "<h3>Chi tiết giao dịch</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số giao dịch:</label>\n"
                + "                            <p>" + tran.getId() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Sản phẩm:</label>\n"
                + "                            <p>" + tran.getOrder().getProduct().getName() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mô tả:</label>\n"
                + "                            <p class=\"des\">" + tran.getDescription() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã đơn hàng:</label>\n"
                + "                            <p id=\"orderId\" onclick=\"getOrderDetail(this)\"  data-id=\""+tran.getOrder().getId()+"\">" + tran.getOrder().getId() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Loại giao dịch</label>\n"
                + "                            <p >" + tran.getType() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số tiền:</label>\n"
                + "                            <p class=\"formatMoneyTranDetail\">" + tran.getMoney() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Trạng thái giao dịch:</label>\n"
                + "                            <p class=\"statusTran\">" + tran.getStatus() + "</p>\n"
                + "                        </div>\n"
                + "                       \n"
                + "                        <button onclick=\"submitOkTran()\">OK</button>";

        jsonObject.put("htmls", htmls);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
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
