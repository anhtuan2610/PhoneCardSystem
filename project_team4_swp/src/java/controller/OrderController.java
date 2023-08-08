/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import dal.ProductDAO;
import dal.StorageDAO;
import dal.TransactionDAO;
import dal.UserDAO;
import entity.Order;
import entity.Product;
import entity.Storage;
import entity.Transaction;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.GetCurrentTime;
//import services.QueueOrderProcess;
import services.QueueOrderProcesss;
import services.QueueTransactionProcess;

/**
 *
 * @author phamtung
 */
public class OrderController extends HttpServlet {

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
            out.println("<title>Servlet orderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet orderController at " + request.getContextPath() + "</h1>");
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
        JSONObject jsonObject = new JSONObject();

        String productPrice = request.getParameter("price");
        String productName = request.getParameter("name");
        String productQuantity = request.getParameter("quantity");
        String productId = request.getParameter("productId");

        jsonObject.put("htmls", "   <div class=\"productView\">\n"
                + "                    <h3>Thông tin sản phẩm</h3>\n"
                + "                    <div class=\"contentProduct\">\n"
                + "\n"
                + "                        <div class=\"item \">\n"
                + "                            <label>Sản phẩm: </label>\n"
                + "                            <input  class=\"readOnly\" type=\"text\" name=\"nameProduct\" value=\"" + productName + "\" readonly=\"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"item \">\n"
                + "                            <label>Giá: </label>\n"
                + "                            <input class=\"readOnly\" type=\"text\" name=\"price\" value=\"" + productPrice + "\" readonly=\"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"item\">\n"
                + "                            <label>Số lượng: </label>\n"
                + "                            <input  id=\"quantityInput\"  type=\"number\" name=\"price\" value=\"1\" >\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "\n"
                + "\n"
                + "                    <div class=\"groupButton\">\n"
                + "                        <button class=\"btn close-btn\" onclick=\"closeBuyProduct()\">Hủy</button>\n"
                + "                        <button data-productId=\"" + productId + "\" data-quantity=\"" + productQuantity + "\" class=\"btn buy-btn\" onclick=\"buyProductButton(this)\">Mua</button>\n"
                + "                    </div>\n"
                + "\n"
                + "                </div>\n"
                + "                <div class=\"productView_blur\"></div>");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
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
        UserDAO uDAO = new UserDAO();
        OrderDAO oDAO = new OrderDAO();
        StorageDAO stDAO = new StorageDAO();
        ProductDAO productDAO = new ProductDAO();
        Connection conn = null;

        JSONObject jsonObject = new JSONObject();
//     
        GetCurrentTime getTime = new GetCurrentTime();

        String productId = request.getParameter("productId");
        int quantityInput = Integer.parseInt(request.getParameter("productQuantityInput"));
//        int productQuantity = Integer.parseInt(request.getParameter("productQuantity"));

        int productQuantity = productDAO.getQuantityById(Integer.parseInt(productId));

        Product p = new Product();
        p = productDAO.getProductById(Integer.parseInt(productId));
        int price = p.getPrice();
        int totalPrice = price * quantityInput;

        String notification = "";
        int userId = (int) session.getAttribute("userId");

//        String balanceSession = (String) session.getAttribute("balance");
//        Double balance = Double.parseDouble(balanceSession);
        double balance = uDAO.getBalanceById(userId);

        if (productQuantity == 0) {

            notification = "Xin lỗi, hiện tại số lượng sản phẩm đã được bán hết, vui lòng mua sản phẩm khác";

            jsonObject.put("notify", "<div class=\"notify_blur\"></div>\n"
                    + "                <div class=\"notificationBox\">\n"
                    + "                    <div class=\"icon_wrong\"> \n"
                    + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                    + "                    </div>\n"
                    + "                    <h3>" + notification + "</h3>\n"
                    + "\n"
                    + "                    <div class=\"buttons\">\n"
                    + "                        <button onclick=\"confirmQuantityZero()\"  class=\"btn\">OK</button>\n"
                    + "                    </div>\n"
                    + "                </div>");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } else if (quantityInput > productQuantity) {
            notification = "Xin lỗi, hiện tại số lượng sản phẩm chỉ còn lại " + productQuantity;

            jsonObject.put("notify", "<div class=\"notify_blur\"></div>\n"
                    + "                <div class=\"notificationBox\">\n"
                    + "                    <div class=\"icon_wrong\"> \n"
                    + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                    + "                    </div>\n"
                    + "                    <h3>" + notification + "</h3>\n"
                    + "\n"
                    + "                    <div class=\"buttons\">\n"
                    + "                        <button onclick=\"confirmNotifyOK()\"  class=\"btn\">OK</button>\n"
                    + "                    </div>\n"
                    + "                </div>");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
        } else {

            if (balance < totalPrice) {

                notification = "Số dư của bạn không đủ để thực hiện giao dịch";

                jsonObject.put("notify", "<div class=\"notify_blur\"></div>\n"
                        + "                <div class=\"notificationBox\">\n"
                        + "                    <div class=\"icon_wrong\"> \n"
                        + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                        + "                    </div>\n"
                        + "                    <h3>" + notification + "</h3>\n"
                        + "\n"
                        + "                    <div class=\"buttons\">\n"
                        + "                        <button onclick=\"confirmNotifyOK()\"  class=\"btn\">OK</button>\n"
                        + "                    </div>\n"
                        + "                </div>");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toString());
//            request.setAttribute("notification", notification);
//            request.getRequestDispatcher("./home.jsp").forward(request, response);
            } else {

                long currentTime = getTime.getCurrentTime(); // CreatedAt của Order
                User userSession = (User) session.getAttribute("user");
                int role = (int) session.getAttribute("role");
                String createdBy = "";

                if (role == 1) {
                    createdBy = "Admin" + userId;
                } else {
                    createdBy = userId + "";
                }

                //        Xử lý productLog
                jsonObject.put("id", p.getId());
                jsonObject.put("name", p.getName());
                jsonObject.put("description", p.getDescription());
                jsonObject.put("category", p.getCategory());
                jsonObject.put("price", p.getPrice());
                jsonObject.put("createdAt", p.getCreatedAt());
                jsonObject.put("createdBy", p.getCreatedBy());
                jsonObject.put("updatedBy", p.getUpdatedBy());
                jsonObject.put("quantity", p.getQuantity());
                jsonObject.put("image", p.getImage());

                String productLog = jsonObject.toJSONString();



//              User uById = uDAO.getUserById(userId);
                Product pByID = productDAO.getProductById(Integer.parseInt(productId));

//              Tạo Order
                Order o = new Order(quantityInput, Order.OrderStatus.PROCESSING, userSession, createdBy, productLog,
                        "", pByID, price, totalPrice, currentTime);

//              Update vào bảng Order và lấy id của Order đó cho Transaction
                int orderID = oDAO.insertOrder(o);

//                // Lấy thể hiện của QueueOrderProcess từ ServletContext
                QueueOrderProcesss queueOrder = (QueueOrderProcesss) getServletContext().getAttribute("queueOrder");
                int statusOrder = -1;
                if (queueOrder != null) {
                    queueOrder.addToQueue(orderID);

                    statusOrder = queueOrder.processQueue();
                    if(statusOrder != -1)
                    {
                        session.setAttribute("balance", statusOrder + "");
                    }
//                    System.out.println("Controller: " + statusOrder);
                }


//                boolean commitTransaction = false;
//                int statusOrder = -1;
////                 Xử lý status của thẻ và xử lý Order vào hàng chờ
//                try {
//                    // Kết nối đến cơ sở dữ liệu
//                    conn = dbcontext.DBContext1.getConnection();
//
//                   // Bắt đầu giao dịch
//                    conn.setAutoCommit(false);
//                    // Thực hiện các thao tác cơ sở dữ liệu
//                    // Update status cho Storage
////                    stDAO.updateListStorage(sql);
//
//                    // Lấy thể hiện của QueueOrderProcess từ ServletContext
//                    QueueOrderProcesss queueOrder = (QueueOrderProcesss) getServletContext().getAttribute("queueOrder");
//                    
//                    if (queueOrder != null) {
//                        queueOrder.addToQueue(orderID);
//
//                        statusOrder = queueOrder.processQueue();
//
////                        System.out.println("Controller: " + statusOrder);
//
//                        if (statusOrder != -1) {
//
////                            session.setAttribute("balance", statusOrder + "");
////                            String blanceSession = (String) session.getAttribute("balance");
////                            System.out.println(blanceSession);
//                            try {
//
//                                // Nếu statusOrder là true, thực hiện commit
//                                conn.commit();
//                            } catch (SQLException ex) {
//                                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            commitTransaction = true;
//                            System.out.println("Transaction committed!");
//                        } else {
//                            try {
//                                // Nếu statusOrder là false, thực hiện rollback
//                                conn.rollback();
//                            } catch (SQLException ex) {
//                                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            System.out.println("Transaction rolled back!");
//                        }
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
//                } finally {
//                    // Đóng kết nối cơ sở dữ liệu
//                    try {
//                        if (conn != null && !conn.isClosed()) {
//                            if (!commitTransaction) {
//                                conn.rollback();
//                                System.out.println("Transaction rolled back in the final block!");
//                            } else {
//                                conn.commit();
//                                System.out.println("Transaction committed in the final block!");
//                            }
////                            conn.close();
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }

                jsonObject.put("notify", "<div class=\"notify_blur\"></div>\n"
                        + "                <div class=\"notificationBox\">\n"
                        + "                    <div class=\"icon\"> \n"
                        + "                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                        + "                    </div>\n"
                        + "                    <h3>Đơn hàng đã được ghi nhận. Vào lịch sử mua hàng để <a href=\"./purchaseHistory?orderid=" + orderID + "\" >xem chi tiết</a></h3>\n"
                        + "\n"
                        + "                    <div class=\"buttons\">\n"
                        + "                        <button onclick=\"confirmNotifyBuySuccess()\"  class=\"btn\">OK</button>\n"
                        + "                    </div>\n"
                        + "                </div>");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toString());

            }
        }

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
