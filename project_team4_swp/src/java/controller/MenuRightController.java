/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author phamtung
 */
public class MenuRightController extends HttpServlet {

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
            out.println("<title>Servlet MenuRightController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuRightController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);
        JSONObject jsonObject = new JSONObject();

//       Lấy role từ session 1 -> Admin , 2 -> Customer
        int roleUser = (int) session.getAttribute("role");
        String htmls = "";
        if (roleUser == 1) {
            htmls = "                          <li onclick=\"redirectToServelet('./homeController', false)\">Trang chủ</li>\n"
                    + "                        <li onclick=\"redirectToServelet('./manageUserController', false)\">Quản lý người dùng</li> \n"
                    + "                        <li onclick=\"redirectToServelet('./manageProductController', false)\">Quản lý sản phẩm</li> \n"
                    + "                        <li onclick=\"redirectToServelet('./addProductController', false)\">Thêm sản phẩm</li> \n"
                    + "                        <li onclick=\"redirectToServelet('./manageStorageController', false)\">Quản lý kho thẻ</li> \n"
                    + "                        <li onclick=\"redirectToServelet('./viewTransactionController', false)\">Lịch sử giao dịch</li> \n"
                    + "                        <li onclick=\"redirectToServelet('./purchaseHistory', false)\">Lịch sử mua hàng</li> \n";

        } else if (roleUser == 2) {
            htmls = "                        <li onclick=\"redirectToServelet('./homeController', false)\">Trang chủ</li>\n"
                    + "";
        }

        jsonObject.put("htmls", htmls);
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
