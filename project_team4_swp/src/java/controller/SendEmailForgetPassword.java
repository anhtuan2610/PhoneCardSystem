/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import services.SendEmaill;

/**
 *
 * @author trana
 */
public class SendEmailForgetPassword extends HttpServlet {

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
            out.println("<title>Servlet SendEmail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendEmail at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("forgetPassword.jsp").forward(request, response);
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
        String account = request.getParameter("account");
//        String newPassword = request.getParameter("newPassword");
        String captchaInput = request.getParameter("captchaInput");

        UserDAO user = new UserDAO();
        SendEmaill sm = new SendEmaill();

        String email = user.getEmailByAccount(account);

        String otp = sm.getRandom();

        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);

        if (email == null) {
            request.setAttribute("error1", "Tài khoản này không tồn tại trong hệ thống");
            request.getRequestDispatcher("./forgetPassword.jsp").forward(request, response);
        } else {

            if (captchaInput.equals(session.getAttribute("captchaCode"))) {

                Runnable sendEmailTask = () -> {

                    sm.send("ledungb12509@gmail.com", "begagbngrmebbdco",
                            email, "Yêu cầu thay đổi mật khẩu",
                            "Mã xác thực OTP của bạn là: "
                            + otp);

                };

                Thread sendEmailThread = new Thread(sendEmailTask);
                sendEmailThread.start();

                session.setAttribute("account", account);
                session.setAttribute("email", email);

                RequestDispatcher dispatcher = request.getRequestDispatcher("./forgetPasswordVerify.jsp");
                dispatcher.forward(request, response);

            } else {

                request.setAttribute("error1", "Captcha nhập không đúng");
                request.getRequestDispatcher("./forgetPassword.jsp").forward(request, response);

            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
