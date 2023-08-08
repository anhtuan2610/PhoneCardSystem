/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import services.GetCurrentTime;
import services.MaHoa;

/**
 *
 * @author phamtung
 */
@WebServlet(name = "UpdateUserController", urlPatterns = {"/updateUserController"})
public class UpdateUserController extends HttpServlet {

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
            out.println("<title>Servlet UpdateUserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserController at " + request.getContextPath() + "</h1>");
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
        String userIdS = request.getParameter("userId");
        int userId = Integer.parseInt(userIdS);
        
        HttpSession session = request.getSession(false);
        
        int updatedById = (int) session.getAttribute("userId");
        String updatedBy = updatedById + "";
        
        GetCurrentTime getTime = new GetCurrentTime();
        long updatedAt = getTime.getCurrentTime();

        String password = request.getParameter("passwordInput");
        String name = request.getParameter("nameInput");
        String phone = request.getParameter("phoneInput");
        String statusAccountS = request.getParameter("selectedValue");
        int statusAccount = Integer.parseInt(statusAccountS);

        JSONObject jsonObject = new JSONObject();

        UserDAO uDAO = new UserDAO();
        String sql = "";

        if (!password.equals("")) {
            MaHoa mahoa = new MaHoa();
            String passwordMH = mahoa.toSHA1(password);

            sql = "UPDATE `user` SET `password` = '" + passwordMH + "',"
                    + "`name` = '" + name + "',"
                    + "`phone` = '" + phone + "',"
                    + "`updatedBy` = '" + updatedBy + "',"
                    + "`updatedAt` = '" + updatedAt + "',"
                    + "`isDelete` = '" + statusAccount + "' WHERE id = '" + userId + "'";
        } else {
            sql = "UPDATE `user` SET "
                    + "`name` = '" + name + "',"
                    + "`phone` = '" + phone + "',"
                    + "`updatedBy` = '" + updatedBy + "',"
                    + "`updatedAt` = '" + updatedAt + "',"
                    + "`isDelete` = '" + statusAccount + "' WHERE id = '" + userId + "'";
        }

        System.out.println(sql);

        boolean statusSQL = uDAO.runSQL(sql);
        System.out.println(statusSQL);
        String htmlNotification = "";
        if (statusSQL) {
            htmlNotification = "                                    <div class=\"icon\"> \n"
                    + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                    + "                                    </div>\n"
                    + "                                    <h3>Cập nhật thông tin thành công</h3>\n"
                    + "                \n"
                    + "                                    <div class=\"buttons\">\n"
                    + "                                        <button data-id=\"" + userId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                    + "                                    </div>";
        } else {
            htmlNotification = "                                    <div class=\"icon\"> \n"
                    + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                    + "                                    </div>\n"
                    + "                                    <h3>Cập nhật thông tin thất bại</h3>\n"
                    + "                \n"
                    + "                                    <div class=\"buttons\">\n"
                    + "                                        <button data-id=\"" + userId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                    + "                                    </div>";
        }

        jsonObject.put("htmlNotification", htmlNotification);

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
