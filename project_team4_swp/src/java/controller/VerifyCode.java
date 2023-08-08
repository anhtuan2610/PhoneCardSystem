/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author ADMIN
 */
public class VerifyCode extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User k = (User) session.getAttribute("authcode");
        User kh = (User) session.getAttribute("acc");

        String otpInput = request.getParameter("otpInput");
        JSONObject jsonObject = new JSONObject();

        boolean insertStatus = false;
        String Notify = "                                                            <div class=\"icon\" style=\"color: yellow\"> \n"
                + "                                                                <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                + "                                                                <ion-icon name=\"warning-outline\"></ion-icon>\n"
                + "                                                            </div>\n"
                + "                                                            <h3>Cập nhật thông tin thành công</h3>\n"
                + "                                        \n"
                + "                                                            <div class=\"buttons\">\n"
                + "                                                                <button data-id=\"30\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                + "                                                            </div>";

        if (session.getAttribute("authcode") == null) {
//                Notify = "OPT is Wrong!!!";

            Notify = "";
            
        } if (otpInput.equals(k.getCode())) {
            try {
                UserDAO khachHangDAO = new UserDAO();
                insertStatus = khachHangDAO.insert(kh);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DangKy.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (insertStatus) {
//                    response.sendRedirect("./login.jsp");

                session.invalidate();

//                    baoLoi = "Đăng ký thành công";
                Notify = "<div class=\"icon\" style=\"color: yellow\"> \n"
                        + "                                            <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                        + "                                        </div>\n"
                        + "                                        <h3>Đăng ký thành công</h3>\n"
                        + "                    \n"
                        + "                                        <div class=\"buttons\">\n"
                        + "                                            <button data-id=\"30\" onclick=\"redirectToServelet('./login', false)\"  class=\"btn\">Login</button>\n"
                        + "                                        </div>";

            } else {
                Notify = "<div class=\"icon\" style=\"color: yellow\"> \n"
                        + "                                            <ion-icon name=\"warning-outline\"></ion-icon>\n"
                        + "                                        </div>\n"
                        + "                                        <h3>Gặp lỗi trong quá trình đăng ký</h3>\n"
                        + "                    \n"
                        + "                                        <div class=\"buttons\">\n"
                        + "                                            <button data-id=\"30\" onclick=\"closePopup()\"  class=\"btn\">OK</button>\n"
                        + "                                        </div>";
            }
        } else {
//                baoLoi = "OPT is Wrong!!!";

            Notify = "<div class=\"icon\" style=\"color: yellow\"> \n"
                    + "                                            <ion-icon name=\"warning-outline\"></ion-icon>\n"
                    + "                                        </div>\n"
                    + "                                        <h3>OPT is Wrong!!!</h3>\n"
                    + "                    \n"
                    + "                                        <div class=\"buttons\">\n"
                    + "                                            <button data-id=\"30\" onclick=\"closePopup()\"  class=\"btn\">OK</button>\n"
                    + "                                        </div>";
        }
       

        jsonObject.put("Notify", Notify);
       
//        jsonObject.put("htmlBalance", htmlBalance);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
