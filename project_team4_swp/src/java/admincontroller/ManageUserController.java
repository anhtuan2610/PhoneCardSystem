/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author phamtung
 */
@WebServlet(name = "ManageUserController", urlPatterns = {"/manageUserController"})
public class ManageUserController extends HttpServlet {

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
            out.println("<title>Servlet ManageUserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageUserController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("manageUser.jsp").forward(request, response);

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
        UserDAO uDAO = new UserDAO();

        User u = uDAO.getUserById(id);

        String htmlStatus = "";
        int statusUser = u.getIsDelete();
        int roleUser = u.getRole();
        String htmlStatusByRole = "";
        if(roleUser == 1){
            htmlStatus = "disabled=\"\"";
        }else{
            htmlStatus = "";
        }
        
        if (statusUser == 0) {
            htmlStatus = " <input "+htmlStatus+" type=\"radio\" data-datasql=\"checked\" checked=\"\"  name=\"group_Status\" id=\"statusUser1\" value=\"0\">\n"
                    + "                                <label style=\"color:green\" for=\"statusUser1\" >Active</label>\n"
                    + "\n"
                    + "                                <input "+htmlStatus+" type=\"radio\"  name=\"group_Status\" id=\"statusUser2\" value=\"1\">\n"
                    + "                                <label style=\"color:red\" for=\"statusUser2\">Banned</label>";
        } else if (statusUser == 1) {
            htmlStatus = " <input "+htmlStatus+" type=\"radio\"   name=\"group_Status\" id=\"statusUser1\" value=\"0\">\n"
                    + "                                <label style=\"color:green\" for=\"statusUser1\">Active</label>\n"
                    + "\n"
                    + "                                <input "+htmlStatus+" type=\"radio\" data-datasql=\"checked\" checked=\"\" name=\"group_Status\" id=\"statusUser2\" value=\"1\">\n"
                    + "                                <label style=\"color:red\" for=\"statusUser2\">Banned</label>";
        }

        String htmls = "<button onclick=\"closeBox()\" class=\"close-btn\">X</button>\n"
                + "                    <div  class=\"box_content\"> \n"
                + "                        <h3>Thông tin người dùng</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số tài khoản</label>\n"
                + "                            <input class=\"readOnly\" id=\"userId\" readonly=\"\" value=\"" + u.getId() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Role:</label>\n"
                + "                            <input class=\"readOnly\" id=\"role\" readonly=\"\" value=\"" + u.getRole() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Tài khoản:</label>\n"
                + "                            <input class=\"readOnly\" id=\"account\" readonly=\"\" value=\"" + u.getAccount() + "\">\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số dư:</label>\n"
                + "                            <div class=\"input_button\">\n"
                + "                                <input class=\"readOnly input_button_content\" readonly=\"\" id=\"balance\"  value=\"" + u.getBalance() + "\">\n"
                + "                                <button data-id=\"" + u.getId() + "\" onclick=\"createTransaction(this)\">Tạo giao dịch</button>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Email:</label>\n"
                + "                            <input  class=\"readOnly\" id=\"email\" readonly=\"\" value=\"" + u.getEmail() + "\">\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Ngày tạo:</label>\n"
                + "                            <input class=\"readOnly\" readonly=\"\" id=\"createdAt\"  value=\"" + u.getCreatedAt() + "\">\n"
                + "                        </div>\n"
                + "\n"
                + "<div class=\"items\"> \n"
                + "                            <label>Mật khẩu:</label>\n"
                + "                            <input  class=\"\" type=\"password\" id=\"password\" value=\"\">\n"
                + "                        </div>"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Trạng thái:</label>\n"
                + "                            <div class=\"group_chose\">\n"
                + htmlStatus
                + "                            </div>\n"
                + "\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Họ và Tên:</label>\n"
                + "                            <input data-datasql=\"" + u.getName() + "\" class=\"\" id=\"name\"  value=\"" + u.getName() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số điện thoại:</label>\n"
                + "                            <input data-datasql=\"" + u.getPhone() + "\" type=\"number\" class=\"\" id=\"phone\"  value=\"" + u.getPhone() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"group_button\">\n"
                + "                            <button class=\"ok_btn btn_1\" onclick=\"closeBox()\">Close</button>\n"
                + "                            <button class=\"ok_btn btn_2\" onclick=\"updateInfo()\">Update</button>\n"
                + "                        </div>\n"
                + "                    </div>";

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
