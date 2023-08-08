/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.json.simple.JSONObject;

public class ListProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        ProductDAO dao = new ProductDAO();
//        List<Product> list = dao.getListProduct();
//        request.setAttribute("data", list);
//        request.getRequestDispatcher("body.jsp").forward(request, response);
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

        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getListProductPagination(pagingNumber, 0);
        String sql = " select count(*) from product WHERE isShow = 1 AND isDelete = 0";
        double totalProduct = dao.countProduct(sql);

        int totalPaging = (int) Math.ceil(totalProduct / pagingNumber);

        String htmls = "";

        for (Product pro : list) {
            htmls += "<div class=\"wraper_productItem\">\n"
                    + "                                        <div class=\"productItem\">\n"
                    + "                                            <div class=\"product_logo\">\n"
                    + "                                                <img src=\"image/" + pro.getImage() + "\" />\n"
                    + "\n"
                    + "                                            </div>\n"
                    + "                                            <div class=\"productItem_info\">\n"
                    + "                                                <h5 >" + pro.getName() + "</h5>\n"
                    + "\n"
                    + "                                                <h5 >" + pro.getCategory() + "</h5>\n"
                    + "                                                <h5  class=\"formatDouble\">Giá: <span>" + pro.getPrice() + "</span></h5>\n"
                    + "                                                <h5 >Số lượng: " + pro.getQuantity() + "</h5>\n"
                    + "\n"
                    + "                                            </div>\n"
                    + "                                        </div>\n"
                    + "\n"
                    + "                                        <div class=\"group_buttons\">\n"
                    + "<button onclick=\"viewProductDetail(this)\" \n"
                    + "                                                data-productId=\"" + pro.getId() + "\"  \n"
                    + "                                                data-name=\"" + pro.getName() + "\" \n"
                    + "                                                data-quantity=\"" + pro.getQuantity() + "\" \n"
                    + "                                                data-price=\"" + pro.getPrice() + "\"\n"
                    + "                                                data-des=\"" + pro.getDescription() + "\" "
                    + "                                                data-img=\"" + pro.getImage() + "\" "
                    + "                                                 data-category=\"" + pro.getCategory() + "\" class=\" view_btn btn \">Thông tin</button>"
                    + "\n"
                    + "                                            <button onclick=\"buyProductOnList(this)\" \n"
                    + "                                                    data-productId=\"" + pro.getId() + "\"  \n"
                    + "                                                    data-name=\"" + pro.getName() + "\" \n"
                    + "                                                    data-quantity=\"" + pro.getQuantity() + "\" \n"
                    + "                                                    data-price=\"" + pro.getPrice() + "\" \n"
                    + "                                                    class=\" buy_btn btn \"> Mua </button>\n"
                    + "\n"
                    + "                                        </div>\n"
                    + "\n"
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

        JSONObject jsonObject2 = new JSONObject();

        String pageSizeString = request.getParameter("pageSize");
        String pageNumberString = request.getParameter("pageNumber");
        String searchContent = request.getParameter("searchContent");

        int pageSize = Integer.parseInt(pageSizeString); // Số lượng bản ghi hiển thị
        int pageNumber = Integer.parseInt(pageNumberString); // số trang hiện tại đang muốn hiển thị.

        int offset = (pageNumber - 1) * pageSize;

        String filterPrice = request.getParameter("filterPriceValue");
        String filterCategory = request.getParameter("filterCatagoryValue");
        String sql = "";
        String sqlCountProduct = "";
        String sqlSearchContent = "";

        if (!searchContent.equals("")) {
            sqlSearchContent = " AND `name` LIKE '%" + searchContent + "%'";
        } else {
            sqlSearchContent = "";
        }

        if (!filterPrice.equals("price") && filterCategory.equals("category")) {

            sql = "SELECT * FROM `product`"
                    + " WHERE isShow = 1 AND isDelete = 0 "
                    + "AND price = " + filterPrice + sqlSearchContent + " LIMIT " + pageSize + " OFFSET " + offset + ";";

            sqlCountProduct = "SELECT COUNT(*) FROM `product` WHERE isShow = 1 AND isDelete = 0 AND price = " + filterPrice + sqlSearchContent + ";";

        } else if (filterPrice.equals("price") && !filterCategory.equals("category")) {
            sql = "SELECT * FROM `product`"
                    + " WHERE isShow = 1 AND isDelete = 0 "
                    + "AND category = '" + filterCategory + "'" + sqlSearchContent + " LIMIT " + pageSize + " OFFSET " + offset + ";";

            sqlCountProduct = "SELECT COUNT(*) FROM `product` WHERE isShow = 1 AND isDelete = 0 AND category = '" + filterCategory + "'" + sqlSearchContent + ";";

        } else if (filterPrice.equals("price") && filterCategory.equals("category")) {
            sql = "SELECT * FROM `product` WHERE isShow = 1 AND isDelete = 0 " + sqlSearchContent + " LIMIT " + pageSize + " OFFSET " + offset + ";";

            sqlCountProduct = "select count(*) from product WHERE isShow = 1 AND isDelete = 0 " + sqlSearchContent + ";";

        } else {

            sql = "SELECT * FROM `product`"
                    + " WHERE isShow = 1 AND isDelete = 0 "
                    + "AND price = " + filterPrice
                    + " AND category = '" + filterCategory + "'" + sqlSearchContent
                    + " LIMIT " + pageSize + " OFFSET " + offset + ";";

            sqlCountProduct = "SELECT COUNT(*) FROM `product` WHERE isShow = 1 AND category = '" + filterCategory + "'" + " AND price = " + filterPrice + sqlSearchContent + ";";

        }
//        System.out.println(sqlCountProduct);
//
//        System.out.println(sql);

        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getListProductFilter(sql);

        double totalProduct = dao.countProduct(sqlCountProduct);

        int totalPaging = (int) Math.ceil(totalProduct / pageSize);
//        int currentPage = 1;
        String html = "";
        if (list.isEmpty() && searchContent != null) {
            html = "<h3>Không tìm thấy sản phẩm <p title=\"" + searchContent + "\">" + searchContent + "</p>, vui lòng kiểm tra lại</h3>\n";
            pageNumber = 0;
        } else if (list.isEmpty()) {
            pageNumber = 0;
            html = "<h3>Sản phẩm đã được bán hết, vui lòng quay lại sau</h3>\n";
        } else {

            for (Product pro : list) {
                html += "<div class=\"wraper_productItem\">\n"
                        + "                                        <div class=\"productItem\">\n"
                        + "                                            <div class=\"product_logo\">\n"
                        + "                                                <img src=\"image/" + pro.getImage() + "\" />\n"
                        + "\n"
                        + "                                            </div>\n"
                        + "                                            <div class=\"productItem_info\">\n"
                        + "                                                <h5 >" + pro.getName() + "</h5>\n"
                        + "\n"
                        + "                                                <h5 >" + pro.getCategory() + "</h5>\n"
                        + "                                                <h5  class=\"formatDouble\">Giá: <span>" + pro.getPrice() + "</span></h5>\n"
                        + "                                                <h5 >Số lượng: " + pro.getQuantity() + "</h5>\n"
                        + "\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "\n"
                        + "                                        <div class=\"group_buttons\">\n"
                        + "<button onclick=\"viewProductDetail(this)\" \n"
                        + "                                                data-productId=\"" + pro.getId() + "\"  \n"
                        + "                                                data-name=\"" + pro.getName() + "\" \n"
                        + "                                                data-quantity=\"" + pro.getQuantity() + "\" \n"
                        + "                                                data-price=\"" + pro.getPrice() + "\"\n"
                        + "                                                data-des=\"" + pro.getDescription() + "\" "
                        + "                                                data-img=\"" + pro.getImage() + "\" "
                        + "                                                 data-category=\"" + pro.getCategory() + "\""
                        + "                                                 class=\" view_btn btn \">Thông tin</button>"
                        + "\n"
                        + "                                            <button onclick=\"buyProductOnList(this)\" \n"
                        + "                                                    data-productId=\"" + pro.getId() + "\"  \n"
                        + "                                                    data-name=\"" + pro.getName() + "\" \n"
                        + "                                                    data-quantity=\"" + pro.getQuantity() + "\" \n"
                        + "                                                    data-price=\"" + pro.getPrice() + "\" \n"
                        + "                                                    class=\" buy_btn btn \"> Mua </button>\n"
                        + "\n"
                        + "                                        </div>\n"
                        + "\n"
                        + "                                    </div>";
            }
        }

//        String htmlSelectPaging = "";
//
//        if (pageSize == 5) {
//            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
//                    + "                                            <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
//                    + "                                            <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
//                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
//                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
//                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
//                    + "                                        </select>";
//        } else if (pageSize == 10) {
//            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
//                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
//                    + "                                            <option selected=\"\" value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
//                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
//                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
//                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
//                    + "                                        </select>";
//        } else if (pageSize == 20) {
//            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
//                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
//                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
//                    + "                                            <option selected=\"\" value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
//                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
//                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
//                    + "                                        </select>";
//        } else if (pageSize == 50) {
//            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
//                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
//                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
//                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
//                    + "                                            <option selected=\"\" value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
//                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
//                    + "                                        </select>";
//        } else if (pageSize == 100) {
//            htmlSelectPaging = "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
//                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
//                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
//                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
//                    + "                                            <option  value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
//                    + "                                            <option selected=\"\" value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
//                    + "                                        </select>";
//        }

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
                + "\n" + "<select id=\"select_pagination\" class=\"pagination_drop\">\n"
                + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                + "                                            <option  value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                + "                                        </select>"
                + "\n"
                + "                                    </div>\n" + nextbuttonHtml;

        String balance = (String) session.getAttribute("balance");

        String htmlBalance = "Số dư: <span>" + balance + "</span>";

        jsonObject2.put("html", html);
        jsonObject2.put("htmlPaging", htmlPaging);
        jsonObject2.put("pagesize", pageSize);
        
        
        jsonObject2.put("htmlBalance", htmlBalance);

//        jsonObject2.put("sql", sqlCountProduct);
        jsonObject2.put("pageSizeString", offset);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject2.toString());

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
