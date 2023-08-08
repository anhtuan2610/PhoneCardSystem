/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import admincontroller.ManageUserController;
import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class UserActivityLog extends HttpServlet {

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
            out.println("<title>Servlet UserActivityLog</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserActivityLog at " + request.getContextPath() + "</h1>");
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

        String userId = request.getParameter("userId");
        int id = Integer.parseInt(userId);
        int pageSize = 5;
        JSONObject jsonObject = new JSONObject();

        UserDAO uDAO = new UserDAO();

        User u = uDAO.getUserById(id);

        String activityLog = u.getActivityLog();

        String htmls = " <div class=\"wraper_item_activity title\">\n"
                + "                            <div class=\"item W_1_activity\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Loại hành động</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Thời gian</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";
        String htmlPaging = "";
        String nextBtnHtml = "";
        if (activityLog != null) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = null;
            try {
                jsonArray = (JSONArray) parser.parse(activityLog);
            } catch (ParseException ex) {
                Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            double totalItems = jsonArray.size();
            int totalPaging = (int) Math.ceil(totalItems / pageSize);

            int count = 0;

            for (Object obj : jsonArray) {
                JSONObject activity = (JSONObject) obj;

                long idActivity = (long) activity.get("id");
                String type = (String) activity.get("type");
                long createdAt = (long) activity.get("createdAt");

                htmls += "                        <div class=\"wraper_item_activity \">\n"
                        + "                            <div class=\"item W_1_activity\">\n"
                        + "                                <p>" + idActivity + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2_activity\">\n"
                        + "                                <p>" + type + "</p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"item W_2_activity\">\n"
                        + "                                <p class=\"date_activity\" >" + createdAt + "</p>\n"
                        + "                            </div>\n"
                        + "\n"
                        + "                            <div class=\"item W_2_activity\">\n"
                        + "                                <button data-id=\"" + idActivity + "\" onclick=\"viewActivityDetail(this)\">Chi tiết</button>\n"
                        + "                            </div>\n"
                        + "                        </div> ";

                count++;
                if (count == pageSize) {
                    break; // Kết thúc vòng lặp
                }
            }
            if (totalPaging <= 1) {
                nextBtnHtml = " <div class=\"btn_pagination_activity next_btn_activity\">\n"
                        + "                                                    <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                        + "                                                </div>";
            } else {
                nextBtnHtml = " <div class=\"btn_pagination_activity next_btn_activity\">\n"
                        + "                                                    <button  data-number=\"1\">Sau</button>\n"
                        + "                                                </div>";
            }

            htmlPaging = "<div  class=\"btn_pagination_activity pre_btn_activity\">\n"
                    + "                            <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"page_number_activity\">\n"
                    + "                            <span>Trang </span>\n"
                    + "                            <input type=\"number\" value=\"1\">\n"
                    + "                            <span>/ <p>" + totalPaging + "</p></span>\n"
                    + "\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"dropdown_container_activity\">\n"
                    + "\n"
                    + "                            <select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                            </select>\n"
                    + "\n"
                    + "                        </div>\n"
                    + nextBtnHtml
                    + "                        </div>";

//            System.out.println(jsonArray.size());
        } else {
            htmls += "<h4> Không tìm thấy bản ghi nào</h4>\n";

            htmlPaging = "<div  class=\"btn_pagination_activity pre_btn_activity\">\n"
                    + "                            <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"page_number_activity\">\n"
                    + "                            <span>Trang </span>\n"
                    + "                            <input type=\"number\" value=\"0\">\n"
                    + "                            <span>/ <p>0</p></span>\n"
                    + "\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"dropdown_container_activity\">\n"
                    + "\n"
                    + "                            <select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                            </select>\n"
                    + "\n"
                    + "                        </div>\n"
                    + "                        <div class=\"btn_pagination_activity next_btn_activity\">\n"
                    + "                            <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                    + "                        </div>";
        }

        jsonObject.put("htmls", htmls);
        jsonObject.put("pagination", htmlPaging);
        jsonObject.put("userId", id);

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

        JSONObject jsonObject = new JSONObject();

        String userId = request.getParameter("userId");
        int id = Integer.parseInt(userId);

        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        String filterTypeValue = request.getParameter("filterTypeValue");
        String filterDateValue = request.getParameter("filterDateValue");

//        System.out.println(id + "pageSize " + pageSize + "pageNumber " + pageNumber + "filterTypeValue " + filterTypeValue + "filterDateValue " + filterDateValue + " ");

        int startItem = (pageNumber - 1) * pageSize;  // Bắt đầu lấy từ item này
        int endItem = startItem + pageSize; // Item Kết thúc 

        GetCurrentTime getTime = new GetCurrentTime();

        long currentTime = getTime.getCurrentTime();
        long dateBegin = 0;
        if (filterDateValue.equals("7ngay")) {
            dateBegin = getTime.subtractDays(currentTime, 7);
        } else if (filterDateValue.equals("30ngay")) {
            dateBegin = getTime.subtractDays(currentTime, 30);
        } else if (filterDateValue.equals("3thang")) {
            dateBegin = getTime.subtractMonths(currentTime, 3);
        } else if (filterDateValue.equals("6thang")) {
            dateBegin = getTime.subtractMonths(currentTime, 6);
        }

        UserDAO uDAO = new UserDAO();

        User u = uDAO.getUserById(id);

        String activityLog = u.getActivityLog();

        String htmls = " <div class=\"wraper_item_activity title\">\n"
                + "                            <div class=\"item W_1_activity\">\n"
                + "                                <p>Id</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Loại hành động</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Thời gian</p>\n"
                + "                            </div>\n"
                + "                            <div class=\"item W_2_activity\">\n"
                + "                                <p>Hành động</p>\n"
                + "                            </div>\n"
                + "                        </div>";
        String htmlPaging = "";

        String htmlSelectPaging = "";

        if (pageSize == 5) {
            htmlSelectPaging = "<select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                            <option selected=\"\" style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 10) {
            htmlSelectPaging = "<select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 20) {
            htmlSelectPaging = "<select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 50) {
            htmlSelectPaging = "<select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        } else if (pageSize == 100) {
            htmlSelectPaging = "<select id=\"select_pagination_activity\" class=\"pagination_drop_activity\">\n"
                    + "                                            <option  style=\"color: black;\" value=\"5\">5 bản ghi</option>\n"
                    + "                                            <option  value=\"10\" style=\"color: black;\">10 bản ghi</option>\n"
                    + "                                            <option  value=\"20\" style=\"color: black;\">20 bản ghi</option>\n"
                    + "                                            <option  value=\"50\" style=\"color: black;\">50 bản ghi</option>\n"
                    + "                                            <option selected=\"\" value=\"100\" style=\"color: black;\">100 bản ghi</option>\n"
                    + "                                        </select>";
        }

        if (activityLog != null) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = null;
            try {
                jsonArray = (JSONArray) parser.parse(activityLog);
            } catch (ParseException ex) {
                Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            int count = 0;

            for (Object obj : jsonArray) {
                JSONObject activity = (JSONObject) obj;

                long createdAt = (long) activity.get("createdAt");
                long idActivity = (long) activity.get("id");
                String type = (String) activity.get("type");

                boolean dateFlag = false;

                if ((dateBegin <= createdAt) && (createdAt <= currentTime)) {
                    dateFlag = true;
                }

//                System.out.println(dateFlag);
                if (filterTypeValue.equals("statusUser") && dateFlag) {
                    if (count >= endItem) {
                        htmls += "";
                    } else if (count >= startItem) {
                        htmls += "                        <div class=\"wraper_item_activity \">\n"
                                + "                            <div class=\"item W_1_activity\">\n"
                                + "                                <p>" + idActivity + "</p>\n"
                                + "                            </div>\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <p>" + type + "</p>\n"
                                + "                            </div>\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <p class=\"date_activity\" >" + createdAt + "</p>\n"
                                + "                            </div>\n"
                                + "\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <button data-id=\"" + idActivity + "\" onclick=\"viewActivityDetail(this)\">Chi tiết</button>\n"
                                + "                            </div>\n"
                                + "                        </div> ";
                    }

                    count++;
                } else if (activity.get("type").equals(filterTypeValue) && dateFlag) {

                    if (count >= endItem) {
                        htmls += "";
                    } else if (count >= startItem) {
                        htmls += "                        <div class=\"wraper_item_activity \">\n"
                                + "                            <div class=\"item W_1_activity\">\n"
                                + "                                <p>" + idActivity + "</p>\n"
                                + "                            </div>\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <p>" + type + "</p>\n"
                                + "                            </div>\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <p class=\"date_activity\" >" + createdAt + "</p>\n"
                                + "                            </div>\n"
                                + "\n"
                                + "                            <div class=\"item W_2_activity\">\n"
                                + "                                <button data-id=\"" + idActivity + "\" onclick=\"viewActivityDetail(this)\">Chi tiết</button>\n"
                                + "                            </div>\n"
                                + "                        </div> ";
                    }

                    count++;
//                    System.out.println("Count++: " + count);
                }

            }

            double totalItems = (double) count;
            int totalPaging = (int) Math.ceil(totalItems / pageSize);
//            System.out.println("Count: " + count + " total: " + totalItems + " paging: " + totalPaging);

            if (totalItems == 0) {
                htmls += "<h4> Không tìm thấy bản ghi nào</h4>\n";

                htmlPaging = "<div  class=\"btn_pagination_activity pre_btn_activity\">\n"
                        + "                            <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                        + "                        </div>\n"
                        + "\n"
                        + "                        <div class=\"page_number_activity\">\n"
                        + "                            <span>Trang </span>\n"
                        + "                            <input type=\"number\" value=\"0\">\n"
                        + "                            <span>/ <p>0</p></span>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "\n"
                        + "                        <div class=\"dropdown_container_activity\">\n"
                        + "\n"
                        + htmlSelectPaging
                        + "                        </div>\n"
                        + "                        <div class=\"btn_pagination_activity next_btn_activity\">\n"
                        + "                            <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                        + "                        </div>";
            } else {
                String prebuttonHtml = "";
                if (pageNumber <= 1) {
                    prebuttonHtml = " <div class=\"btn_pagination_activity pre_btn_activity\">\n"
                            + "                                        <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                            + "                                    </div>";
                } else {
                    prebuttonHtml = "<div class=\"btn_pagination_activity pre_btn_activity\">\n"
                            + "                                        <button data-number=\"-1\">Trước</button>\n"
                            + "                                    </div>";
                }
                String nextbuttonHtml = "";

                if (pageNumber == totalPaging) {
                    nextbuttonHtml = " <div class=\"btn_pagination_activity pre_btn_activity\">\n"
                            + "                                        <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                            + "                                    </div>";
                } else {
                    nextbuttonHtml = " <div class=\"btn_pagination_activity pre_btn_activity\">\n"
                            + "                                        <button data-number=\"1\">Sau</button>\n"
                            + "                                    </div>";
                }

                htmlPaging = ""
                        + prebuttonHtml
                        + "\n"
                        + "                        <div class=\"page_number_activity\">\n"
                        + "                            <span>Trang </span>\n"
                        + "                            <input type=\"number\" value=\"" + pageNumber + "\">\n"
                        + "                            <span>/ <p>" + totalPaging + "</p></span>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "\n"
                        + "                        <div class=\"dropdown_container_activity\">\n"
                        + "\n"
                        + htmlSelectPaging
                        + "\n"
                        + "                        </div>\n"
                        + nextbuttonHtml
                        + "                        </div>";
            }

//            System.out.println(jsonArray.size());
        } else {
            htmls += "<h4> Không tìm thấy bản ghi nào</h4>\n";

            htmlPaging = "<div  class=\"btn_pagination_activity pre_btn_activity\">\n"
                    + "                            <button disabled=\"\" data-number=\"-1\">Trước</button>\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"page_number_activity\">\n"
                    + "                            <span>Trang </span>\n"
                    + "                            <input type=\"number\" value=\"0\">\n"
                    + "                            <span>/ <p>0</p></span>\n"
                    + "\n"
                    + "                        </div>\n"
                    + "\n"
                    + "                        <div class=\"dropdown_container_activity\">\n"
                    + "\n"
                    + htmlSelectPaging
                    + "                        </div>\n"
                    + "                        <div class=\"btn_pagination_activity next_btn_activity\">\n"
                    + "                            <button disabled=\"\" data-number=\"1\">Sau</button>\n"
                    + "                        </div>";
        }

        jsonObject.put("htmls", htmls);
        jsonObject.put("pagination", htmlPaging);
        jsonObject.put("userId", id);

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
