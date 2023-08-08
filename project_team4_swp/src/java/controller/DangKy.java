/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import services.MaHoa;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.GetCurrentTime;
import services.SendEmaill;

/**
 *
 * @author ADMIN
 */
public class DangKy extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        response.sendRedirect("./signin.jsp");
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
        String name = request.getParameter("hoVaTen");
        String phone = request.getParameter("dienThoai");
        String email = request.getParameter("email");
        String account = request.getParameter("tenDangNhap");
        String password = request.getParameter("matKhau");
        String repassword = request.getParameter("matKhauNhapLai");
        String dongYNhanMail = request.getParameter("dongYNhanMail");
        String url = "";
        String baoLoi = "";
        UserDAO khachHangDAO = new UserDAO();
        try {
            if (khachHangDAO.kiemTraTenDangNhap(account)) {
                baoLoi += "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.<br/>";
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        if (!password.equals(repassword)) {
            baoLoi += "Mật khẩu không khớp.<br/>";
        } else {
            password = MaHoa.toSHA1(password);
        }
        if (name.isEmpty()) {
            baoLoi += "Họ và tên không được để trống";
        }
        if (phone.isEmpty()) {
            baoLoi += "Số điện thoại không để trống";
        }
        if (email.isEmpty()) {
            baoLoi += "Email không để trống";
        }
        request.setAttribute("baoLoi", baoLoi);
        if (baoLoi.length() > 0) {

            request.getRequestDispatcher("signin.jsp").forward(request, response);
        } else {
            SendEmaill sm = new SendEmaill();
            String code = sm.getRandom();
            User kh = new User(name, email, code);
//            boolean test = sm.send("ledungb12509@gmail.com", "begagbngrmebbdco", kh.getEmail(), "Xác thực Email người dùng", "Đăng ký thành công.Làm ơn xác thực tài khoản của bạn mã: " + kh.getCode());
//            if (true) {
//                HttpSession session = request.getSession();
//                session.setAttribute("authcode", kh);
//                response.sendRedirect("verify.jsp");
//                User k = new User(0, 2, 0, name, phone, email, account, password, "", "", "", "", "", "", 50000);
//                session.setAttribute("acc", k);
//            } else {
//                System.out.println("Again!!!");
//            }

            Runnable sendEmailTask = () -> {
                sm.send("ledungb12509@gmail.com", "begagbngrmebbdco",
                        kh.getEmail(), "Xác thực Email người dùng",
                        "Mã xác thực OTP của bạn là: "
                        + kh.getCode());
            };

            Thread sendEmailThread = new Thread(sendEmailTask);
            sendEmailThread.start();

            HttpSession session = request.getSession();
            session.setAttribute("authcode", kh);
            GetCurrentTime getTime = new GetCurrentTime();

            long currentTime = getTime.getCurrentTime();

            response.sendRedirect("verify.jsp");
//            2 => Customer Role
            User k = new User(0, 2, 0, name, phone, email, account, password, "", "", "", 0, currentTime, 0, 500000);
            session.setAttribute("acc", k);
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
