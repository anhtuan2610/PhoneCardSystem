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
import org.json.simple.JSONObject;
import services.GetCurrentTime;

/**
 *
 * @author phamtung
 */
public class ListOrder extends HttpServlet {

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
            out.println("<title>Servlet listOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listOrder at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        JSONObject json = new JSONObject();

        OrderDAO oDAO = new OrderDAO();

        int userId = (int) session.getAttribute("userId");
        int pageSize = 5;

        List<Order> orderList = new ArrayList<>();
        String sqlGetList = "SELECT * FROM `order` WHERE user = " + userId
                + " AND isDelete = 0 ORDER BY createdAt DESC LIMIT " + pageSize + " OFFSET 0";

        orderList = oDAO.getListOrderByUserId(sqlGetList);

        String sqlCount = " SELECT COUNT(*) FROM `order` WHERE user = " + userId + " AND isDelete = 0 ";

        double totalProduct = oDAO.countOrder(sqlCount);

        int totalPaging = (int) Math.ceil(totalProduct / pageSize);

        String htmls = "";

        for (Order o : orderList) {
            htmls += " <div data-orderId=\"" + o.getId() + "\" class=\"item\">\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p>" + o.getId() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p>" + o.getProduct().getName() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"formatMoneyList\">" + o.getPrice() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p >" + o.getQuantity() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"formatMoneyList\">" + o.getTotalPrice() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"createdAt\">" + o.getCreatedAt() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"statusOrder\">" + o.getStatus() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <button class=\"button_view\" onclick=\"viewDetail(this)\" data-id=\"" + o.getId() + "\">Chi tiết</button>\n"
                    + "                                        </div>\n"
                    + "                                    </div>";

        }

        String htmlPaging = "<div class=\"btn_pagination pre_btn\">\n"
                + "                                        <button disabled=\"\" data-number=\"-1\" >Trước</button>\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"page_number\">\n"
                + "                                        <span>Trang </span>\n"
                + "                                        <input type=\"number\" value=\"1\">\n"
                + "                                        <span>/ <p>" + totalPaging + "</p></span>\n"
                + "\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"dropdown_container\">\n"
                + "\n"
                + "                                        <select id=\"select_pagination\" class=\"pagination_drop\">\n"
                + "                                            <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                + "                                            <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                + "                                        </select>\n"
                + "\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"btn_pagination next_btn\">\n"
                + "                                        <button data-number=\"1\">Sau</button>\n"
                + "                                    </div>";

        json.put("htmls", htmls);
        json.put("htmlPaging", htmlPaging);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());

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

        JSONObject jsonObject = new JSONObject();

        int userId = (int) session.getAttribute("userId");
        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");

        int pageSize = Integer.parseInt(pageSizeString); // Số lượng bản ghi hiển thị
        int pageNumber = Integer.parseInt(pageNumberString); // số trang hiện tại đang muốn hiển thị.

        int offset = (pageNumber - 1) * pageSize;

        String filterPrice = request.getParameter("filterPriceValue");
        String filterDate = request.getParameter("filterDateValue");
        String filterStatus = request.getParameter("filterStatusValue");

        String priceSql = "";
        String dateSql = "";
        String statusSql = "";

        if (filterPrice.equals("price")) {
            priceSql = " ";
        } else {
            priceSql = " AND price = " + filterPrice;
        }

        if (filterStatus.equals("statusOrder")) {
            statusSql = " ";
        } else {
            statusSql = " AND status = " + filterStatus;
        }

        GetCurrentTime getTime = new GetCurrentTime();

        long currentTime = getTime.getCurrentTime();
        long dateBegin = 0;

        if (filterDate.equals("moinhat")) {
            dateSql = " ";
        } else {
            if (filterDate.equals("7ngay")) {
                dateBegin = getTime.subtractDays(currentTime, 7);
                dateSql = " AND createdAt BETWEEN " + dateBegin + " AND " + currentTime;
            } else if (filterDate.equals("30ngay")) {
                dateBegin = getTime.subtractDays(currentTime, 30);
                dateSql = " AND createdAt BETWEEN " + dateBegin + " AND " + currentTime;
            } else if (filterDate.equals("3thang")) {
                dateBegin = getTime.subtractMonths(currentTime, 3);
                dateSql = " AND createdAt BETWEEN " + dateBegin + " AND " + currentTime;
            } else if (filterDate.equals("6thang")) {
                dateBegin = getTime.subtractMonths(currentTime, 6);
                dateSql = " AND createdAt BETWEEN " + dateBegin + " AND " + currentTime;
            }

        }

        String sqlListOrder = "SELECT * FROM `order` WHERE isDelete = 0 AND user = " + userId
                + priceSql + statusSql
                + dateSql
                + " order by id DESC LIMIT "+ pageSize + " OFFSET " + offset ;
        String sqlCountOrder = "SELECT COUNT(*) FROM `order` WHERE isDelete = 0 AND user = " + userId
                + priceSql + statusSql
                + dateSql
                + " order by id DESC";

        
        OrderDAO dao = new OrderDAO();
        List<Order> list = dao.getListOrderByUserId(sqlListOrder);

        double totalProduct = dao.countOrder(sqlCountOrder);

        int totalPaging = (int) Math.ceil(totalProduct / pageSize);
        
        String htmls = "";

        if (list.isEmpty()) {
            pageNumber = 0;
            htmls = "<h3>Không tìm thấy đơn hàng nào</h3>\n";
        } else {

            for (Order o : list) {
                 htmls += " <div data-orderId=\"" + o.getId() + "\" class=\"item\">\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p>" + o.getId() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p>" + o.getProduct().getName() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"formatMoneyList\">" + o.getPrice() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p >" + o.getQuantity() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"formatMoneyList\">" + o.getTotalPrice() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"createdAt\">" + o.getCreatedAt() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <p class=\"statusOrder\">" + o.getStatus() + "</p>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"item_attribute\">\n"
                    + "                                            <button class=\"button_view\" onclick=\"viewDetail(this)\" data-id=\"" + o.getId() + "\">Chi tiết</button>\n"
                    + "                                        </div>\n"
                    + "                                    </div>";
            }
        }

        String htmlSelectPaging = "";

        if (pageSize == 5) {
            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                    + "                                            <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 10) {
            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 20) {
            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 50) {
            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 100) {
            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option  value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        }

        String prebuttonHtml = "";
        if (pageNumber <= 1) {
            prebuttonHtml = " <div class=\"btn_pagination pre_btn\">\n"
                    + "                                        <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                    + "                                    </div>";
        } else {
            prebuttonHtml = "<div class=\"btn_pagination pre_btn\">\n"
                    + "                                        <button data-number=\"-1\">Trước</button>\n"
                    + "                                    </div>";
        }
        String nextbuttonHtml = "";

        if (pageNumber == totalPaging) {
            nextbuttonHtml = " <div class=\"btn_pagination next_btn\">\n"
                    + "                                        <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                    + "                                    </div>";
        } else {
            nextbuttonHtml = " <div class=\"btn_pagination next_btn\">\n"
                    + "                                        <button data-number=\"1\">Sau</button>\n"
                    + "                                    </div>";
        }

        String htmlPaging
                = prebuttonHtml
                + "                                    <div class=\"page_number\">\n"
                + "                                        <span>Trang </span>\n"
                + "                                        <input type=\"number\" value=\"" + pageNumber + "\">\n"
                + "                                        <span>/ <p>" + totalPaging + "</p></span>\n"
                + "\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"dropdown_container\">\n"
                + "\n" + htmlSelectPaging
                + "\n"
                + "                                    </div>\n" + nextbuttonHtml;

//        String balance = (String) session.getAttribute("balance");
//
//        String htmlBalance = "Số dư: <span>" + balance + "</span>";
        jsonObject.put("htmls", htmls);
        jsonObject.put("htmlPaging", htmlPaging);

//        jsonObject2.put("htmlBalance", htmlBalance);
//        jsonObject2.put("sql", sqlCountProduct);
        jsonObject.put("sqlListOrder", sqlListOrder);

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
