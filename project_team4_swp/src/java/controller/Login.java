/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import entity.User;
import services.CreateCaptcha;
import services.MaHoa;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;

/**
 *
 * @author trana
 */
public class Login extends HttpServlet {

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
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String password = request.getParameter("password");
        String captchaInput = request.getParameter("captchaInput");

        HttpSession session = request.getSession();

        UserDAO user = new UserDAO();

        String captcha = (String) session.getAttribute("captchaCode");
        String passEncode = MaHoa.toSHA1(password);

        User userInfo = user.checkUser(account, passEncode);
        boolean checkAccount = user.checkAccount(account);
        boolean userStatus = user.checkUserStatus(account);
        System.out.println(userStatus);

        Boolean isLogin = false;

        if (account.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (!(captcha.equals(captchaInput))) {
            request.setAttribute("error", "bạn nhập captcha không đúng");
            request.setAttribute("account", account);
            request.setAttribute("password", password);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (checkAccount == false) {
            request.setAttribute("error", "Tài khoản không tồn tại");
            request.setAttribute("account", account);
            request.setAttribute("password", password);
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else if (userInfo == null) {
            request.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");
            request.setAttribute("account", account);
            request.setAttribute("password", password);

            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (userStatus == false) {
            request.setAttribute("error", "Tài khoản của bạn hiện tại đang bị khóa ");
            request.setAttribute("account", account);
            request.setAttribute("password", password);

            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            isLogin = true;

            session.setAttribute("user", userInfo);
            session.setAttribute("userId", userInfo.getId());
            session.setAttribute("isLogin", isLogin);
            session.setAttribute("role", userInfo.getRole());

            double balance = userInfo.getBalance();

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedNumber = decimalFormat.format(balance);

            session.setAttribute("balance", formattedNumber);
//            session.setAttribute("pagingNumber", 5);

            response.sendRedirect("homeController");
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
