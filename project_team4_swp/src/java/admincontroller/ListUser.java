/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.simple.JSONObject;
import services.GetCurrentTime;

/**
 *
 * @author phamtung
 */
public class ListUser extends HttpServlet {

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
            out.println("<title>Servlet ListUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListUser at " + request.getContextPath() + "</h1>");
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

        String sql = "SELECT * FROM `user` ORDER BY id DESC LIMIT 5 OFFSET 0";
        String sqlCount = "select count(*) from `user`";

        UserDAO uDAO = new UserDAO();
        List<User> list = uDAO.getListUserPagination(sql);

        double totalProduct = uDAO.countUser(sqlCount);

        int totalPaging = (int) Math.ceil(totalProduct / pagingNumber);

        String htmls = "<div class=\"wraper_item title\">\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Tài khoản</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Số điện thoại</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Email</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Trạng thái</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";
        if (list.isEmpty()) {
            htmls += "<h3>không tìm thấy bản ghi nào</h3>";
        }
        for (User u : list) {
            htmls += "<div class=\"wraper_item \">\n"
                    + "                            <div class=\"item W_1\">\n"
                    + "                                <p>" + u.getId() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"item W_3\">\n"
                    + "                                <p title=\"" + u.getAccount() + "\" >" + u.getAccount() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"item W_2\">\n"
                    + "                                <p>" + u.getPhone() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"item W_3\">\n"
                    + "                                <p title=\"" + u.getEmail() + "\">" + u.getEmail() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"item W_2 statusUser\">\n"
                    + "                                <p>" + u.getIsDelete() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"item W_3\">\n"
                    + "                                <button class=\"button1\" data-id=\"" + u.getId() + "\" onclick=\"viewUserDetail(this)\">Chi tiết</button>\n"
                    + "                                <button class=\"button2\" data-id=\"" + u.getId() + "\" onclick=\"viewActivityLog(this)\">Hoạt động</button>\n"
                    + "                            </div>\n"
                    + "                        </div>";

        }

        String htmlPaging = "                        <div class=\"btn_pagination pre_btn\">\n"
                + "                            <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"page_number\">\n"
                + "                            <span>Trang </span>\n"
                + "                            <input type=\"number\" value=\"1\">\n"
                + "                            <span>/ <p>" + totalPaging + "</p></span>\n"
                + "\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"dropdown_container\">\n"
                + "\n"
                + "                            <select id=\"select_pagination\" class=\"pagination_drop\">\n"
                + "                                <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                + "                                <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                + "                                <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                + "                                <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                + "                                <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                + "                            </select>\n"
                + "\n"
                + "                        </div>\n"
                + "                        <div class=\"btn_pagination next_btn\">\n"
                + "                            <button  data-number=\"1\">Sau</button>\n"
                + "                        </div>";

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
        GetCurrentTime getTime = new GetCurrentTime();

        long currentTime = getTime.getCurrentTime();
        long dateBegin = 0;

        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");

        int pageSize = Integer.parseInt(pageSizeString); // Số lượng bản ghi hiển thị
        int pageNumber = Integer.parseInt(pageNumberString); // số trang hiện tại đang muốn hiển thị.

        int offset = (pageNumber - 1) * pageSize;

        String filterDate = request.getParameter("filterDateValue");
        String filterStatusValue = request.getParameter("filterStatusValue");
        String filterSearchValue = request.getParameter("filterSearchValue");
        String searchContent = request.getParameter("searchContent");
        String checkboxStatus = request.getParameter("searchCheckbox");

        String dateSql = "";
        String statusSql = "";
        String searchSql = "";

        if (searchContent.equals("")) {
            searchSql = "";
        } else if (checkboxStatus.equals("checked") && filterSearchValue.equals("type")) {
            if (getTime.isInteger(searchContent)) {
                searchContent = Integer.parseInt(searchContent) + "";
            }
            searchSql = " AND id = '" + searchContent + "'";

        } else if (checkboxStatus.equals("checked") && !filterSearchValue.equals("type")) {

            searchSql = " AND " + filterSearchValue + " = '" + searchContent + "'";

        } else if (filterSearchValue.equals("type") || filterSearchValue.equals("id")) {
            if (getTime.isInteger(searchContent)) {
                searchContent = Integer.parseInt(searchContent) + "";
            }
            searchSql = " AND id LIKE '%" + searchContent + "%'";

        } else {
            searchSql = " AND " + filterSearchValue + " LIKE '%" + searchContent + "%'";
        }

        if (filterDate.equals("moinhat")) {
            dateSql = "";
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

        if (filterStatusValue.equals("statusUser")) {
            statusSql = "";
        } else {
            statusSql = " AND isDelete = " + filterStatusValue;
        }
        String sqlListUser = "";
        String sqlCountUser = "";

//        if để đảm báo nếu select Type search chọn mà khi chưa có 
//        Filter hoặc search rỗng thì sẽ không có tác dụng

        if (filterStatusValue.equals("statusUser")
                && filterDate.equals("moinhat")
                && searchContent.equals("")) {
            sqlListUser = "SELECT * FROM `user`"
                    + " order by id DESC LIMIT " + pageSize + " OFFSET " + offset;
            sqlCountUser = "SELECT COUNT(*) FROM `user`" + " order by id DESC";
        } else {
            sqlListUser = "SELECT * FROM `user` WHERE" + statusSql
                    + dateSql + searchSql
                    + " order by id DESC LIMIT " + pageSize + " OFFSET " + offset;
            sqlCountUser = "SELECT COUNT(*) FROM `user` WHERE" + statusSql
                    + dateSql + searchSql
                    + " order by id DESC";
        }

        if (sqlListUser.contains("WHERE")) {
            // Tìm vị trí của từ khóa "WHERE"
            int whereIndex = sqlListUser.indexOf("WHERE");
            // Kiểm tra xem từ khóa "AND" có xuất hiện ngay sau từ khóa "WHERE" hay không
            if (sqlListUser.substring(whereIndex + 6, whereIndex + 9).equals("AND")) {
                // Xóa từ khóa "AND" khỏi câu truy vấn
                sqlListUser = sqlListUser.substring(0, whereIndex + 5) + sqlListUser.substring(whereIndex + 9);
            }
        }

        if (sqlCountUser.contains("WHERE")) {
            // Tìm vị trí của từ khóa "WHERE"
            int whereIndex = sqlCountUser.indexOf("WHERE");
            // Kiểm tra xem từ khóa "AND" có xuất hiện ngay sau từ khóa "WHERE" hay không
            if (sqlCountUser.substring(whereIndex + 6, whereIndex + 9).equals("AND")) {
                // Xóa từ khóa "AND" khỏi câu truy vấn
                sqlCountUser = sqlCountUser.substring(0, whereIndex + 5) + sqlCountUser.substring(whereIndex + 9);
            }
        }

//        System.out.println(sqlListUser);
//        System.out.println(sqlCountUser);
        UserDAO uDAO = new UserDAO();
        List<User> list = uDAO.getListUserPagination(sqlListUser);

        double totalUser = uDAO.countUser(sqlCountUser);

        int totalPaging = (int) Math.ceil(totalUser / pageSize);

        String htmls = "                        <div class=\"wraper_item title\">\n"
                + "                            <div class=\"item W_1\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Tài khoản</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Số điện thoại</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Email</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2\">\n"
                + "                                <p>Trạng thái</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_3\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";

        if (list.isEmpty()) {
            pageNumber = 0;
            htmls = "<h3>Không tìm thấy bản ghi nào</h3>\n";
        } else {

            for (User u : list) {
                htmls += "<div class=\"wraper_item \">\n"
                        + "                            <div class=\"item W_1\">\n"
                        + "                                <p>" + u.getId() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_3\">\n"
                        + "                                <ptitle=\"" + u.getAccount() + "\">" + u.getAccount() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2\">\n"
                        + "                                <p>" + u.getPhone() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_3\">\n"
                        + "                                <p title=\"" + u.getEmail() + "\">" + u.getEmail() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2 statusUser\">\n"
                        + "                                <p>" + u.getIsDelete() + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_3\">\n"
                        + "                                <button class=\"button1\" data-id=\"" + u.getId() + "\" onclick=\"viewUserDetail(this)\">Chi tiết</button>\n"
                        + "                                <button class=\"button2\" data-id=\"" + u.getId() + "\" onclick=\"viewActivityLog(this)\">Hoạt động</button>\n"
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
