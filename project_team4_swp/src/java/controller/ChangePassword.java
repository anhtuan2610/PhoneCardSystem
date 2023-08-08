/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import admincontroller.ManageUserController;
import dal.UserDAO;
import services.MaHoa;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ChangePassword extends HttpServlet {

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
            out.println("<title>Servlet changePassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changePassword at " + request.getContextPath() + "</h1>");
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

//        User u = new User();
//        u = (User) session.getAttribute("User");
//
//        String fullname = u.getName();
//        String phonenumber = u.getPhone();
//        int userId = u.getId();
//        String email = u.getEmail();
//
//        Boolean isLogin = false;
//        if (session != null && (boolean) session.getAttribute("isLogin") == true
//                && session.getAttribute("role") != null) {
//
//            response.sendRedirect("./changePassword.jsp");
//
//        } else {
//            response.sendRedirect("./login.jsp");
//
//        }
        response.sendRedirect("./changePassword.jsp");
    }

//    public static String GetCurrentTime() {
//        String currentTime = "";
//        Date updateAt = new Date();
//
//        // Định dạng 
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd | hh:mm:ss-a");
//
//        currentTime = formatter.format(updateAt);
//
//        return currentTime;
//    }
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

        HttpSession session = request.getSession();

        // Lấy giá trị từ các trường input
        String account = request.getParameter("account");
        String oldPassword = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        String re_password = request.getParameter("re_password");

        String oldPassEncode = MaHoa.toSHA1(oldPassword);

        //get time 
        GetCurrentTime getTime = new GetCurrentTime();

        long currentTime = getTime.getCurrentTime(); // CreatedAt của Order

        String captchaInput = request.getParameter("captchaInput");

        // Xử lý logic của servlet, ví dụ:
        // Kiểm tra CAPTCHA và gán giá trị cho "notifyValue"
        String notifyValue = "";
//        String colorNotify = "red";
//        User u = new User(userID, fullname, phonenumber, email, userId, currentTime);
        UserDAO uDAO = new UserDAO();

        if (account == "" || oldPassword == ""
                || new_password == "" || re_password == "" || captchaInput == "") {
            notifyValue = "Vui lòng kiểm tra và nhập đầy đủ thông tin";

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("type", "updateFail");
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
        } else if (uDAO.checkAccountExist(account, oldPassEncode)) {
            if (new_password.equals(re_password)) {

                if (captchaInput.equals(session.getAttribute("captchaCode"))) {

                    String newPassEndcode = MaHoa.toSHA1(re_password);

                    int userId = uDAO.getUserIdByAccount(account);

                    int userIdChange = (int) session.getAttribute("userId");
                    String updatedBy = Integer.toString(userIdChange);

                    JSONObject jsonAcctivityLog = new JSONObject();
                    User uBeforeChange = uDAO.getUserById(userId);
                    //            Xử lý Acctivity Log

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

                    jsonAcctivityLog.put("id", idActivityLog);
                    jsonAcctivityLog.put("type", "Change password");
                    jsonAcctivityLog.put("createdAt", currentTime);
                    jsonAcctivityLog.put("createdby", updatedBy);

                    String activityLogJson = jsonAcctivityLog.toJSONString();
                    String activityLog = "";

                    if (uBeforeChange.getActivityLog() == null) {
                        activityLog = "[" + activityLogJson + "]";
                    } else {
                        activityLog = uBeforeChange.getActivityLog().replaceAll("]", "") + "," + activityLogJson + "]";
                    }

                    User u = new User(newPassEndcode, updatedBy, currentTime, userId, activityLog);

//                    System.out.println(uBeforeChange.getActivityLog());
                    try {
                        if (uDAO.updatePasswordById(u)) {
                            notifyValue = "Thay đổi mật khẩu thành công!!";

                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("type", "updateFail");
                            jsonObject.put("blur", "<div class=\"blur\"></div>");
                            jsonObject.put("notificationBox", " <div class=\"notificationBox\">\n"
                                    + "                    <div class=\"icon\"> \n"
                                    + "                        <ion-icon name=\"warning-outline\"></ion-icon>\n"
                                    + "                    </div>\n"
                                    + "                    <h3>" + notifyValue + "</h3>\n"
                                    + "                    <button onclick=\"submitUpdate()\" class=\"btn_ok\">OK</button>\n"
                                    + "                </div>");

                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(jsonObject.toString());
                        } else {
                            notifyValue = "Thay đổi mật khẩu thất bại!!!";

                            JSONObject jsonObject = new JSONObject();

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

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    notifyValue = "Captcha của bạn đang nhập sai, vui lòng nhập lại.";

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("type", "updateFail");
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

            } else {
                notifyValue = "Mật khẩu mới và nhập lại đang không trùng khớp!!";

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("type", "updateFail");
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
        } else {
            notifyValue = "Bạn nhập sai mật khẩu cũ, vui lòng nhập lại.";

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("type", "updateFail");
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

//        request.setAttribute("colorNotify", colorNotify);
//        request.setAttribute("notifyValue", notifyValue);
//        request.setAttribute("oldPassword", oldPassword);
//        request.setAttribute("new_password", new_password);
//        request.setAttribute("re_password", re_password);
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("./changePassword.jsp");
//        dispatcher.forward(request, response);
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
