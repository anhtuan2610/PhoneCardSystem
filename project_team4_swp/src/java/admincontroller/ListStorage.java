/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.StorageDAO;
import entity.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
@WebServlet(name = "ListStorage", urlPatterns = {"/listStorage"})
public class ListStorage extends HttpServlet {

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
            out.println("<title>Servlet ListStorage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListStorage at " + request.getContextPath() + "</h1>");
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
        JSONObject jsonObject = new JSONObject();

        int pagingNumber = 5;

        StorageDAO stoDAO = new StorageDAO();
        String sqlFilter = "SELECT * FROM `storage` order by id DESC LIMIT 5 OFFSET 0";

        List<Storage> list = stoDAO.getListStorageFilter(sqlFilter);
        String sql = " select count(*) from `storage` order by id DESC";
        double totalProduct = stoDAO.countStorage(sql);

        int totalPaging = (int) Math.ceil(totalProduct / pagingNumber);

        String htmls = "<div class=\"wraper_item title\">\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Tên sản phẩm</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Nhà mạng</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Mệnh giá</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Status</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>isDelete</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";
        String htmlPaging = "";

        if (!list.isEmpty()) {
            for (Storage st : list) {
                htmls += "<div class=\"wraper_item \">\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p>" + st.getId() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p>" + st.getProduct().getName() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p>" + st.getProduct().getCategory() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p class=\"moneyFormat\"><span>" + st.getProduct().getPrice() + "</span>đ</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p class=\"statusIsShow\">" + st.getStatus() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p class=\"statusIsDelete\">" + st.getIsDelete() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2 groupButtons\">\n"
                        + "                                <button data-id=\"" + st.getId() + "\" onclick=\"viewStorageDetail(this)\">Chi tiết</button>\n"
                        + "                            </div>\n"
                        + "                        </div>";

            }

            String nextBtn = "";
            if (totalPaging <= 1) {
                nextBtn = "                                    <div class=\"btn_pagination next_btn\">\n"
                        + "                                        <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                        + "                                    </div>";
            } else {
                nextBtn = "                                    <div class=\"btn_pagination next_btn\">\n"
                        + "                                        <button  data-number=\"1\">Sau</button>\n"
                        + "                                    </div>";
            }

            htmlPaging = "<div class=\"btn_pagination pre_btn\">\n"
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
                    + "\n" + nextBtn;

        }

//        String balance = (String) session.getAttribute("balance");
//
//        String htmlBalance = "Số dư: <span>" + balance + "</span>";
        jsonObject.put("htmls", htmls);
        jsonObject.put("pagination", htmlPaging);
//        jsonObject.put("htmlBalance", htmlBalance);

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

        JSONObject jsonObject = new JSONObject();
        StorageDAO stoDAO = new StorageDAO();

        GetCurrentTime getTime = new GetCurrentTime();
//
//        long currentTime = getTime.getCurrentTime();
//        long dateBegin = 0;

        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");

        int pageSize = Integer.parseInt(pageSizeString); // Số lượng bản ghi hiển thị
        int pageNumber = Integer.parseInt(pageNumberString); // số trang hiện tại đang muốn hiển thị.

        int offset = (pageNumber - 1) * pageSize;

        String filterPriceValue = request.getParameter("filterPriceValue");
        String filterStatusValue = request.getParameter("filterIsShowValue");
        String filterIsDeleteValue = request.getParameter("filterIsDeleteValue");
        String filterCategoryValue = request.getParameter("filterCategoryValue");
        String searchContent = request.getParameter("searchContent");

        String statusSql = "";
        String isDeleteSql = "";
        String searchSql = "";
//        String priceSql = "";
//        String categorySql = "";

        if (searchContent.equals("")) {
            searchSql = "";
        } else {
            if (getTime.isInteger(searchContent)) {
                searchContent = Integer.parseInt(searchContent) + "";
            }
            searchSql = " AND id LIKE '%" + searchContent + "%'";
        }

        String sqlGetProductId = "";
        String sqlProductIn = "";

        if (!filterPriceValue.equals("price") && !filterCategoryValue.equals("category")) {
            sqlGetProductId = " SELECT `id` FROM `product` WHERE price = '" + filterPriceValue + "' AND category = '" + filterCategoryValue + "';";
        } else if (!filterPriceValue.equals("price") && filterCategoryValue.equals("category")) {
            sqlGetProductId = " SELECT `id` FROM `product` WHERE price = '" + filterPriceValue + "';";
        } else if (filterPriceValue.equals("price") && !filterCategoryValue.equals("category")) {
            sqlGetProductId = " SELECT `id` FROM `product` WHERE category = '" + filterCategoryValue + "';";
        } else {
            sqlGetProductId = "";
        }

        ArrayList<String> listProductId = null;
        if (sqlGetProductId != "") {
            listProductId = stoDAO.getListProductIdByPriceAndCategory(sqlGetProductId);
        }

//        System.out.println(listProductId == null || listProductId.isEmpty());
        if (!(listProductId == null) && !listProductId.isEmpty()) {
            for (int i = 0; i < listProductId.size(); i++) {
                if (i == 0 && (i == listProductId.size() - 1)) {
                    sqlProductIn = " AND `product` IN(" + listProductId.get(i) + ")";
                } else if (i == 0) {
                    sqlProductIn = " AND `product` IN(" + listProductId.get(i) + ",";
                } else if (i == listProductId.size() - 1) {
                    sqlProductIn += listProductId.get(i) + ") ";
                } else {
                    sqlProductIn += listProductId.get(i) + ",";
                }
            }
        } else if (sqlGetProductId.isEmpty()) {
            sqlProductIn = "";
        } else {
            sqlProductIn = " AND `product` IN(-1) ";
        }

//        System.out.println("List: " + listProductId);
//        System.out.println("sqlGetProductId: " + sqlGetProductId);
//        System.out.println("sqlProductIn: " + sqlProductIn);

//        System.out.println(sqlProductIn.equals(""));

        if (filterStatusValue.equals("status")) {
            statusSql = "";
        } else {
            statusSql = " AND status = '" + filterStatusValue + "'";
        }
        if (filterIsDeleteValue.equals("isDelete")) {
            isDeleteSql = "";
        } else {
            isDeleteSql = " AND isDelete = '" + filterIsDeleteValue + "'";
        }

        String sqlListStorage = "";
        String sqlCountProduct = "";

        if (filterStatusValue.equals("status")
                && filterIsDeleteValue.equals("isDelete")
                && searchContent.equals("")
                && filterCategoryValue.equals("category")
                && filterPriceValue.equals("price")) {

            sqlListStorage = "SELECT * FROM `storage`"
                    + " order by id DESC LIMIT " + pageSize + " OFFSET " + offset;
            sqlCountProduct = "SELECT COUNT(*) FROM `storage`" + " order by id DESC";

        } else {
            sqlListStorage = "SELECT * FROM `storage` WHERE" + searchSql
                    + statusSql + isDeleteSql + sqlProductIn
                    + " order by id DESC LIMIT " + pageSize + " OFFSET " + offset;
            sqlCountProduct = "SELECT COUNT(*) FROM `storage` WHERE" + searchSql
                    + statusSql + isDeleteSql + sqlProductIn
                    + " order by id DESC";
        }

        if (sqlListStorage.contains("WHERE")) {
            // Tìm vị trí của từ khóa "WHERE"
            int whereIndex = sqlListStorage.indexOf("WHERE");
            // Kiểm tra xem từ khóa "AND" có xuất hiện ngay sau từ khóa "WHERE" hay không
            if (sqlListStorage.substring(whereIndex + 6, whereIndex + 9).equals("AND")) {
                // Xóa từ khóa "AND" khỏi câu truy vấn
                sqlListStorage = sqlListStorage.substring(0, whereIndex + 5) + sqlListStorage.substring(whereIndex + 9);
            }
        }

        if (sqlListStorage.contains("WHERE order")) {

            sqlListStorage = sqlListStorage.replace("WHERE", "").trim();

        }

        if (sqlCountProduct.contains("WHERE")) {
            // Tìm vị trí của từ khóa "WHERE"
            int whereIndex = sqlCountProduct.indexOf("WHERE");
            // Kiểm tra xem từ khóa "AND" có xuất hiện ngay sau từ khóa "WHERE" hay không
            if (sqlCountProduct.substring(whereIndex + 6, whereIndex + 9).equals("AND")) {
                // Xóa từ khóa "AND" khỏi câu truy vấn
                sqlCountProduct = sqlCountProduct.substring(0, whereIndex + 5) + sqlCountProduct.substring(whereIndex + 9);
            }
        }

        if (sqlCountProduct.contains("WHERE order")) {

            sqlCountProduct = sqlListStorage.replace("WHERE", "").trim();

        }

//        System.out.println(sqlListStorage);
//        System.out.println(sqlCountProduct);

        List<Storage> list = stoDAO.getListStorageFilter(sqlListStorage);

        double totalUser = stoDAO.countStorage(sqlCountProduct);

        int totalPaging = (int) Math.ceil(totalUser / pageSize);

        String htmls = "<div class=\"wraper_item title\">\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Tên sản phẩm</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Nhà mạng</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Mệnh giá</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Status</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>isDelete</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";

//        System.out.println(list.isEmpty());
        if (list.isEmpty() || list == null) {
            pageNumber = 0;
            htmls += "<h3>Không tìm thấy bản ghi nào</h3>\n";
        } else {

            for (Storage st : list) {
                htmls += "<div class=\"wraper_item \">\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p>" + st.getId() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p>" + st.getProduct().getName() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p>" + st.getProduct().getCategory() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p class=\"moneyFormat\"><span>" + st.getProduct().getPrice() + "</span>đ</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p class=\"statusIsShow\">" + st.getStatus() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p class=\"statusIsDelete\">" + st.getIsDelete() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2 groupButtons\">\n"
                        + "                                <button data-id=\"" + st.getId() + "\" onclick=\"viewStorageDetail(this)\">Chi tiết</button>\n"
                        + "                            </div>\n"
                        + "                        </div>";
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

        jsonObject.put("htmls", htmls);
        jsonObject.put("htmlPaging", htmlPaging);

//        jsonObject.put("sqlListOrder", sqlListOrder);
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
