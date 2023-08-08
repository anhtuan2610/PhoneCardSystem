/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package services;

import controller.OrderController;
import dal.OrderDAO;
import dal.ProductDAO;
import dal.StorageDAO;
import dal.TransactionDAO;
import dal.UserDAO;
import entity.Order;
import entity.Storage;
import entity.Transaction;
import entity.User;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author phamtung
 */
@WebServlet(name = "QueueOrderProcesss", urlPatterns = {"/QueueOrderProcesss"})
public class QueueOrderProcesss extends HttpServlet {

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
            out.println("<title>Servlet QueueOrderProcesss</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QueueOrderProcesss at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private ScheduledExecutorService scheduler;
    private BlockingQueue<Integer> queue;

    public void startProcessing() {
        int time = 0;
        int numThreads = 1; // Số lượng luồng xử lý
        queue = new LinkedBlockingQueue<>(); // Tạo hàng chờ (queue) để lưu trữ các phần tử
        scheduler = Executors.newScheduledThreadPool(numThreads);

        long delay = 0; // Không có độ trễ ban đầu
        long period = 20; // Chu kỳ 20 giây

        scheduler.scheduleAtFixedRate(this::processQueue, delay, period, TimeUnit.SECONDS);
        System.out.println("Start queue order");
    }

    public void stopProcessing() {
        scheduler.shutdown();
        queue.clear();
        System.out.println("Clear queue order thanh cong");
    }

    public void addToQueue(int item) {
        queue.add(item);
        System.out.println("Add thanh cong Order:" + item);
    }

    public int processQueue() {
        int balanceReturn = -1;
        while (!queue.isEmpty()) {
            int orderId = queue.poll();
            // Xử lý phần tử từ hàng chờ
            System.out.println("Processing order: " + orderId);
            //          Xử lý Transaction
//            HttpSession session = request.getSession(false);
            OrderDAO oDAO = new OrderDAO();
            ProductDAO pDAO = new ProductDAO();
            UserDAO uDAO = new UserDAO();
            StorageDAO stDAO = new StorageDAO();

            TransactionDAO tranDAO = new TransactionDAO();

            GetCurrentTime getTime = new GetCurrentTime();
            long currentTime = getTime.getCurrentTime();

            Order order = oDAO.getOrderById(orderId);

//            System.out.println("order: " + order.getProduct().getCategory() + " " + order.getQuantity());
            String desciption = "Mua sản phẩm " + order.getProduct().getName();
            int quantityOrder = order.getQuantity();
            int productId = order.getProduct().getId();
            int quantityDB = pDAO.getQuantityById(productId);
            int userId = order.getUser().getId();
            int totalPrice = order.getTotalPrice();
            int balance = uDAO.getBalanceById(userId);

            int balanceAfterBuy = balance - totalPrice;

//            Xử lý Transaction trên hàng đợi 
//            ServletContext context = getServletContext();
//            QueueTransactionProcess queueTransaction = (QueueTransactionProcess) context.getAttribute("queueTransaction");
//            queueTransaction.addToQueue(userId);
//            QueueTransactionProcess queueTran = (QueueTransactionProcess) getServletContext().getAttribute("queueTransaction");
//            queueTran.addToQueue(userId);
            User userBuy = order.getUser();

//            1 = Admin(Chi co 1 tai khoan admin) 2 = Customer
            User admin = uDAO.getUserByRole(1);

            int quantityAfterBuy = quantityDB - quantityOrder;

            Connection conn = null;
            boolean commitTransaction = false;

            try {
                // Kết nối đến cơ sở dữ liệu
                conn = dbcontext.DBContext1.getConnection();

                // Bắt đầu giao dịch
                conn.setAutoCommit(false);

                // Thực hiện các thao tác cơ sở dữ liệu
                if (quantityAfterBuy >= 0 && balanceAfterBuy >= 0) {
                    //Xử lý StorageLog
                    List<Storage> listStorage = new ArrayList<>();

                    listStorage = stDAO.getStorageByIdAndQuantity(productId, quantityOrder);

                    JSONArray jsonStorageArray = new JSONArray();

                    int[] arrayId = new int[quantityOrder];
                    int i = 0;

                    for (Storage st : listStorage) {
                        JSONObject jsonStorageLogItem = new JSONObject();

                        arrayId[i] = st.getId();

                        jsonStorageLogItem.put("id", st.getId());
                        jsonStorageLogItem.put("status", st.getStatus());
                        jsonStorageLogItem.put("isDelete", st.getIsDelete());
                        jsonStorageLogItem.put("product", st.getProduct().getId());
                        jsonStorageLogItem.put("seri", st.getSeri());
                        jsonStorageLogItem.put("code", st.getCode());
                        jsonStorageLogItem.put("createdBy", st.getCreatedBy());
                        jsonStorageLogItem.put("updatedBy", st.getUpdatedBy());
                        jsonStorageLogItem.put("deletedBy", st.getDeletedBy());
                        jsonStorageLogItem.put("expiryDate", st.getExpiryDate());
                        jsonStorageLogItem.put("createdAt", st.getCreatedAt());
                        jsonStorageLogItem.put("deletedAt", st.getDeletedAt());
                        jsonStorageLogItem.put("updatedAt", st.getUpdatedAt());

                        jsonStorageArray.add(jsonStorageLogItem);
                        i++;
                    }
                    String storageLog = jsonStorageArray.toJSONString();

//                    System.out.println(storageLog);
//            Câu lệnh dể thay đổi status cho List Storage
                    String sql = "UPDATE storage\n"
                            + "SET status = 0\n"
                            + "WHERE id IN (";
                    for (int c = 0; c < quantityOrder; c++) {

                        if (c == quantityOrder - 1) {
                            sql += arrayId[c] + ");";
                        } else {
                            sql += arrayId[c] + ",";

                        }
                    }
//                System.out.println(sql);
//              Update status cho Storage
                    stDAO.updateListStorage(sql);

//              Xử lý giao dịch
                    Transaction tranCus = new Transaction(userBuy, order, userId + "",
                            desciption, "Trừ tiền", currentTime, totalPrice);

                    Transaction tranAd = new Transaction(admin, order, userId + "",
                            desciption, "Cộng tiền", currentTime, totalPrice);

                    tranDAO.insertTransaction(tranCus);
                    tranDAO.insertTransaction(tranAd);

//                   Xử lý product quantity và status nếu = 0
                    pDAO.updateQuantityById(productId, quantityAfterBuy);
                    if (quantityAfterBuy == 0) {
                        pDAO.updateStatusNoneById(productId);
                    }

//                Xử lý số dư cho người mua và admin
                    uDAO.updateBalanceById(balanceAfterBuy, userId);
                    double balanceAdmin = uDAO.getBalanceById(admin.getId());
                    uDAO.updateBalanceById(balanceAdmin + totalPrice, admin.getId());

//                Cập nhật trang thái đơn hàng
                    boolean flag = oDAO.updateStatusByOrderId(1, orderId, storageLog);
                    System.out.println(flag);
                    System.out.println("Mua thanh cong");

                    balanceReturn = (int) balanceAfterBuy;
                    conn.commit();
                    commitTransaction = true;
                    System.out.println("Transaction committed!");

                } else {

//              Cập nhật trang thái đơn hàng
                    boolean flag = oDAO.updateStatusByOrderId(0, orderId, "");
                    System.out.println("Don mua hang fail" + flag);

                    System.out.println("Mua that bai");
                    conn.rollback();
                }

            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Đóng kết nối cơ sở dữ liệu
                try {
                    if (conn != null && !conn.isClosed()) {
                        if (!commitTransaction) {
                            conn.rollback();
                            System.out.println("Transaction rolled back in the final block!");
                        } else {
                            conn.commit();
                            System.out.println("Transaction committed in the final block!");
                        }
//                            conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
//        System.out.println(status);
        return balanceReturn;
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
        processRequest(request, response);
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
