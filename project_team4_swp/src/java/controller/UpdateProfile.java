package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import admincontroller.ManageUserController;
import dal.UserDAO;
import java.sql.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import static java.lang.Math.random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;
import entity.User;
import java.util.Objects;
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
@WebServlet(urlPatterns = {"/updateProfile"})
public class UpdateProfile extends HttpServlet {

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
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
//        dispatcher.forward(request, response);

        HttpSession session = request.getSession(false);
//        
//        User u = new User();
//        u = (User) session.getAttribute("User");
//        
//        String fullname = u.getName();
//        String phonenumber = u.getPhone();
//        int userId = u.getId();
//        String email = u.getEmail();
//        
//        Boolean isLogin = false;

        if (session != null && (boolean) session.getAttribute("isLogin") == true
                && session.getAttribute("role") != null) {

//            System.out.println("DoGet updateInfo: " + fullname + "  " + phonenumber + "  " + userId);
            response.sendRedirect("./profile.jsp");

        } else {
            response.sendRedirect("./login.jsp");
        }

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonAcctivityLog = new JSONObject();

            UserDAO uDAO = new UserDAO();

            // Lấy giá trị từ các trường input
            String fullname = request.getParameter("fullname");
            String phonenumber = request.getParameter("phonenumber");

            String userId = request.getParameter("userID");

            String email = request.getParameter("email");

            int userID = Integer.parseInt(userId);

            int userIdSession = (int) session.getAttribute("userId");
            String updatedBy = Integer.toString(userIdSession);

//            System.out.println(updatedBy);

            GetCurrentTime getTime = new GetCurrentTime();

            long currentTime = getTime.getCurrentTime();

            String notifyValue = "";

            User uBeforeChange = uDAO.getUserById(userID);

            String activityLogDB = uBeforeChange.getActivityLog();

           
           

            long idActivityLog = 0;
            
            
            if (!Objects.isNull(activityLogDB)) {

                JSONParser parser = new JSONParser();
                JSONArray jsonArray = null;
                try {
                    jsonArray = (JSONArray) parser.parse(activityLogDB);
                } catch (ParseException ex) {
                    Logger.getLogger(ManageUserController.class.getName()).log(Level.SEVERE, null, ex);
                }

                int lastIndex = jsonArray.size() - 1;
                JSONObject lastActivity = (JSONObject) jsonArray.get(lastIndex);
                long lastId = (long) lastActivity.get("id");

                idActivityLog = lastId + 1;
                System.out.println(idActivityLog);
            } else {
                idActivityLog = 1;
            }

//            Xử lý Acctivity Log
            jsonAcctivityLog.put("id", idActivityLog);
            jsonAcctivityLog.put("type", "Update profile");
            jsonAcctivityLog.put("createdAt", currentTime);
            jsonAcctivityLog.put("createdby", updatedBy);
            jsonAcctivityLog.put("nameBeforeChange", uBeforeChange.getName());
            jsonAcctivityLog.put("phoneBeforeChange", uBeforeChange.getPhone());
            jsonAcctivityLog.put("emailBeforeChange", uBeforeChange.getEmail());

            String activityLogJson = jsonAcctivityLog.toJSONString();
            String activityLog = "";

            if (uBeforeChange.getActivityLog() == null) {
                activityLog = "[" + activityLogJson + "]";
            } else {
                activityLog = uBeforeChange.getActivityLog().replaceAll("]", "") + "," + activityLogJson + "]";
            }

//            System.out.println(uBeforeChange.getActivityLog());
            User u = new User(userID, fullname, phonenumber, email, updatedBy, currentTime, activityLog);

//        if (captchaInput.equals(session.getAttribute("captchaCode"))) {
            try {
                if (fullname.length() <= 50) {
                    if (uDAO.updateById(u)) {

                        User userSession = (User) session.getAttribute("user");
                        userSession.setName(fullname);
                        userSession.setPhone(phonenumber);
                        session.setAttribute("user", userSession);

                        notifyValue = "Thay đổi thông tin thành công!!!";
//                        colorNotify = "green";

                        jsonObject.put("type", "successful");
                        jsonObject.put("blur", "<div class=\"blur\"></div>");
                        jsonObject.put("notificationBox", " <div class=\"notificationBox\">\n"
                                + "                    <div class=\"icon\"> \n"
                                + "                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                                + "                    </div>\n"
                                + "                    <h3>" + notifyValue + "</h3>\n"
                                + "                    <button onclick=\"submitUpdate()\" class=\"btn_ok\">OK</button>\n"
                                + "                </div>");

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(jsonObject.toString());

                    } else {

                        notifyValue = "Thay đổi thông tin thất bại!!";

//                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", "updateFail");
                        jsonObject.put("blur", "<div class=\"blur\"></div>");
                        jsonObject.put("notificationBox", " <div class=\"notificationBox\">\n"
                                + "                    <div class=\"icon_wrong\"> \n"
                                + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                                + "                    </div>\n"
                                + "                    <h3>" + notifyValue + "</h3>\n"
                                + "                    <button onclick=\"submitUpdate()\" class=\"btn_ok\">OK</button>\n"
                                + "                </div>");

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(jsonObject.toString());
                    }
                } else {
                    notifyValue = "Họ và tên không được vượt quá 50 ký tự.";

//                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", "fullNameWrong");
                    jsonObject.put("blur", "<div class=\"blur\"></div>");
                    jsonObject.put("notificationBox", " <div class=\"notificationBox\">\n"
                            + "                    <div class=\"icon_warning\"> \n"
                            + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                            + "                    </div>\n"
                            + "                    <h3>" + notifyValue + "</h3>\n"
                            + "                    <button onclick=\"submitUpdate()\" class=\"btn_ok\">OK</button>\n"
                            + "                </div>");

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(jsonObject.toString());

                }

            } catch (Exception e) {
                System.out.println(e);
            }

//        } 
//        else {
//            notifyValue = "Invalid CAPTCHA";
//            colorNotify = "red";
//        }
//            request.setAttribute("colorNotify", colorNotify);
//            request.setAttribute("notifyValue", notifyValue);
//        request.setAttribute("javascriptCode", javascriptCode);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("./profile.jsp");
//            dispatcher.forward(request, response);
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
