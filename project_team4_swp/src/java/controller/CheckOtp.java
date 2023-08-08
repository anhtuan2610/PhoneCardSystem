/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import admincontroller.ManageUserController;
import dal.UserDAO;
import entity.User;
import services.MaHoa;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
 * @author trana
 */
public class CheckOtp extends HttpServlet {

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
            out.println("<title>Servlet CheckOtp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOtp at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        String otp = request.getParameter("otp");
        String account = (String) session.getAttribute("account");

        String newPassword = request.getParameter("newPassword");
//        System.out.println("checkotp: " + account + " " + newPassword);

        UserDAO uDAO = new UserDAO();

        String otpSend = (String) session.getAttribute("otp");

        String newPassEncode = MaHoa.toSHA1(newPassword);

        //get time 
        GetCurrentTime getTime = new GetCurrentTime();

        long currentTime = getTime.getCurrentTime(); // CreatedAt của Order

        if (otp.equals(otpSend)) {
            int userId = uDAO.getUserIdByAccount(account);

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
            jsonAcctivityLog.put("type", "Forget password");
            jsonAcctivityLog.put("createdAt", currentTime);

            String activityLogJson = jsonAcctivityLog.toJSONString();
            String activityLog = "";

            if (uBeforeChange.getActivityLog() == null) {
                activityLog = "[" + activityLogJson + "]";
            } else {
                activityLog = uBeforeChange.getActivityLog().replaceAll("]", "") + "," + activityLogJson + "]";
            }

            uDAO.changePassword(account, newPassEncode, activityLog);
//            session.invalidate();
            session.invalidate();

            request.setAttribute("success1", "Thay doi mat khau thanh cong");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("otp", otp);
            request.setAttribute("newPassword", newPassword);

            request.setAttribute("error1", "otp ban nhap khong chinh xac");
            request.getRequestDispatcher("forgetPasswordVerify.jsp").forward(request, response);
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
