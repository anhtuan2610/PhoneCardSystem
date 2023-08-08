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

/**
 *
 * @author phamtung
 */
public class ViewDetailActivityLog extends HttpServlet {

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
            out.println("<title>Servlet ViewDetailActivityLog</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewDetailActivityLog at " + request.getContextPath() + "</h1>");
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
        String activityLogId = request.getParameter("activityLogId");
        int id = Integer.parseInt(activityLogId);
        String userID = request.getParameter("userId");

        int userId = Integer.parseInt(userID);

        UserDAO uDAO = new UserDAO();

        User u = uDAO.getUserById(userId);

        String activityLog = u.getActivityLog();
        String htmls = "";

        if (activityLog != null) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = null;
            try {
                jsonArray = (JSONArray) parser.parse(activityLog);
            } catch (ParseException ex) {
                Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

            int countId = 1;

            for (Object obj : jsonArray) {
                JSONObject activity = (JSONObject) obj;

                System.out.println(id + "  " + countId);
//                System.out.println(countId == id);

                if (countId == id) {
                    String type = (String) activity.get("type");
                    System.out.println(type);

                    long createdAt = (long) activity.get("createdAt");
                    String createdby = (String) activity.get("createdby");
                    String nameBeforeChange = (String) activity.get("nameBeforeChange");
                    String phoneBeforeChange = (String) activity.get("phoneBeforeChange");
                    String emailBeforeChange = (String) activity.get("emailBeforeChange");

                    if (type.equals("Forget password")) {

                        htmls = "<h3>Chi tiết hoạt động</h3>\n"
                                + "                                            \n"
                                + "                                            <div class=\"items\"> \n"
                                + "                                                <label>Mã số:</label>\n"
                                + "                                                <input class=\"readOnly\" i readonly=\"\" value=\"" + id + "\">\n"
                                + "                                            </div>\n"
                                + "                                            <div class=\"items\"> \n"
                                + "                                                <label>Loại:</label>\n"
                                + "                                                <input class=\"readOnly\"  readonly=\"\" value=\"" + type + "\">\n"
                                + "                                            </div>\n"
                                + "                                            <div class=\"items\"> \n"
                                + "                                                <label>Ngày tạo:</label>\n"
                                + "                                                <input class=\"readOnly date_createdAt\" readonly=\"\"   value=\"" + createdAt + "\">\n"
                                + "                                            </div>\n"
                                + "                    \n"
                                + "                                            <div class=\"group_button\">\n"
                                + "                                                <button class=\"ok_btn btn_1\" onclick=\"closeDetailActivity()\">Close</button>\n"
                                + "                                            </div>";

                    } else if (type.equals("Change password")) {
                        htmls = "<h3>Chi tiết hoạt động</h3>\n"
                                + "\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Mã số:</label>\n"
                                + "                            <input class=\"readOnly\" i readonly=\"\" value=\"" + id + "\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Loại:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\"" + type + "\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Người thực hiện:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\"" + createdby + "\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Ngày tạo:</label>\n"
                                + "                            <input class=\"readOnly date_createdAt\" readonly=\"\"   value=\"" + createdAt + "\">\n"
                                + "                        </div>\n"
                                + "\n"
                                + "                        <div class=\"group_button\">\n"
                                + "                            <button class=\"ok_btn btn_1\" onclick=\"closeDetailActivity()\">Close</button>\n"
                                + "                        </div>";
                    } else if (type.equals("Update profile")) {
                        htmls = "<h3>Chi tiết hoạt động</h3>\n"
                                + "\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Mã số:</label>\n"
                                + "                            <input class=\"readOnly\" i readonly=\"\" value=\""+id+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Loại:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\""+type+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Người thực hiện:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\""+createdby+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Ngày tạo:</label>\n"
                                + "                            <input class=\"readOnly date_createdAt\" readonly=\"\"   value=\""+createdAt+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Tên trước khi đổi:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\""+nameBeforeChange+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>Email trước khi đổi:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\""+emailBeforeChange+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"items\"> \n"
                                + "                            <label>SĐT trước khi đổi:</label>\n"
                                + "                            <input class=\"readOnly\"  readonly=\"\" value=\""+phoneBeforeChange+"\">\n"
                                + "                        </div>\n"
                                + "                        <div class=\"group_button\">\n"
                                + "                            <button class=\"ok_btn btn_1\" onclick=\"closeDetailActivity()\">Close</button>\n"
                                + "                        </div>";
                    }
                    break;
                } else {
                    countId++;
                }

            }
        }

        jsonObject.put("htmls", htmls);

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
