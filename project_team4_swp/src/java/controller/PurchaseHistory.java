/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.GetCurrentTime;

/**
 *
 * @author phamtung
 */
public class PurchaseHistory extends HttpServlet {

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
            out.println("<title>Servlet purchaseHistory</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet purchaseHistory at " + request.getContextPath() + "</h1>");
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

        request.getRequestDispatcher("history.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);
        OrderDAO oDAO = new OrderDAO();
        String id = request.getParameter("id");

        int orderId = Integer.parseInt(id);

        Order o = oDAO.getOrderById(orderId);

        String storageLog = o.getStorageLog();

        // Phân tích cú pháp JSON
        JSONParser parser = new JSONParser();
        JSONArray storageArray = null;

        try {
            storageArray = (JSONArray) parser.parse(storageLog);
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseHistory.class.getName()).log(Level.SEVERE, null, ex);
        }

        String htmlsStorages = "<tr>\n"
                + "                        <th>Mã thẻ</th>\n"
                + "                        <th>Số seri</th>\n"
                + "                        <th>Hạn sử dụng</th>                 \n"
                + "                    </tr> ";

        // Lặp qua từng đối tượng "storage" trong danh sách
//        System.out.println("Order status: "+o.getStatus());
//        System.out.println("OrderStatus: " +Order.OrderStatus.SUCCESS);
//        System.out.println("check: " + (o.getStatus() == Order.OrderStatus.SUCCESS));
        if (o.getStatus() == Order.OrderStatus.SUCCESS) {
            for (Object storageObj : storageArray) {
                JSONObject storage = (JSONObject) storageObj;

                // Truy cập thuộc tính "name" của từng đối tượng "storage"
                String seri = (String) storage.get("seri");
                String code = (String) storage.get("code");
                long expiryDate = (long) storage.get("expiryDate");

                GetCurrentTime getT = new GetCurrentTime();

                String time = getT.formatExpiryDate(expiryDate + "");

                htmlsStorages += "<tr>\n"
                        + "                        <td>" + code + "</td>\n"
                        + "                        <td>" + seri + "</td>\n"
                        + "                        <td>" + time + "</td>                \n"
                        + "                    </tr> ";

            }
        } else {
            htmlsStorages += "";
        }

        String htmls
                = "                        <h3>Chi tiết đơn hàng</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số đơn hàng:</label>\n"
                + "                            <p>" + o.getId() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Sản phẩm:</label>\n"
                + "                            <p>" + o.getProduct().getName() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số lượng:</label>\n"
                + "                            <p>" + o.getQuantity() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Đơn giá</label>\n"
                + "                            <p class=\"formatDouble\">" + o.getPrice() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Thành tiền:</label>\n"
                + "                            <p class=\"formatDouble\">" + o.getTotalPrice() + "</p>\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Trạng thái đơn:</label>\n"
                + "                            <p class=\"statusOrder\">" + o.getStatus() + "</p>\n"
                + "                        </div>\n"
                + "                        <h4>Thông tin thẻ đã mua</h4>\n"
                + "                        <table border=\" 1px solid\">\n"
                + htmlsStorages
                + "                        </table>\n"
                + "                        <button onclick=\"submitOk()\">OK</button>\n";

//        System.out.println("orderID " + o.getId());

        JSONObject jsonObject = new JSONObject();

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
