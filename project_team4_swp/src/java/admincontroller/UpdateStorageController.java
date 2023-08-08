/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.StorageDAO;
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

/**
 *
 * @author phamtung
 */
@WebServlet(name = "UpdateStorageController", urlPatterns = {"/updateStorageController"})
public class UpdateStorageController extends HttpServlet {

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
            out.println("<title>Servlet UpdateStorageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStorageController at " + request.getContextPath() + "</h1>");
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
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession(false);
        StorageDAO stDAO = new StorageDAO();

        GetCurrentTime getTime = new GetCurrentTime();
        long updatedAt = getTime.getCurrentTime();
        int updatedById = (int) session.getAttribute("userId");
        String updatedBy = updatedById + "";

        String id = request.getParameter("storageId");
        int storageId = Integer.parseInt(id);

        String productId = request.getParameter("productId");

        String expriryDateValue = request.getParameter("expriryDateValue");
        long expriDate = Long.parseLong(expriryDateValue);

        String seriValue = request.getParameter("seriValue");
        String codeValue = request.getParameter("codeValue");

        String statusSelected = request.getParameter("statusSelected");
//        int status = Integer.parseInt(statusSelected);

        String isDeleteSelected = request.getParameter("isDeleteSelected");
//        int isDelete = Integer.parseInt(isDeleteSelected);

        String changeSeri = request.getParameter("changeSeri");

        String sqlCheckSeri = "SELECT * FROM `storage` WHERE `product` = '" + productId + "' AND `seri` = '" + seriValue + "';";
        String htmlNotification = "";

        System.out.println("sqlCheckSeri: " + sqlCheckSeri);

        if (changeSeri.equals("false")) {
            String sql = sql = "UPDATE `storage` SET `seri` = '" + seriValue + "',"
                    + "`code` = '" + codeValue + "',"
                    + "`expiryDate` = '" + expriDate + "',"
                    + "`status` = '" + statusSelected + "',"
                    + "`isDelete` = '" + isDeleteSelected + "',"
                    + "`updatedBy` = '" + updatedBy + "',"
                    + "`updatedAt` = '" + updatedAt + "'"
                    + " WHERE id = '" + storageId + "'";

            boolean statusSQL = stDAO.runSQL(sql);

//            System.out.println(sql);
            if (statusSQL) {
                htmlNotification = "                                    <div class=\"icon\"> \n"
                        + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                        + "                                    </div>\n"
                        + "                                    <h3>Cập nhật thông tin thành công</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button data-id=\"" + storageId + "\" onclick=\"confirmUpdateStoSuccess(this)\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            } else {
                htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                        + "<ion-icon name=\"warning-outline\"></ion-icon>"
                        + "                                    </div>\n"
                        + "                                    <h3>Cập nhật thông tin thất bại</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button  onclick=\"confirmUpdateFalse()\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            }
        } else {
            if (stDAO.runSQLExecuteQuery(sqlCheckSeri)) {
                htmlNotification = "                                    <div class=\"icon\"> \n"
                        + "<ion-icon name=\"warning-outline\"></ion-icon>"
                        + "                                    </div>\n"
                        + "                                    <h3>Seri này đã tồn tại</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button  onclick=\"confirmUpdateFalse()\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            } else {
                String sql = sql = "UPDATE `storage` SET `seri` = '" + seriValue + "',"
                        + "`code` = '" + codeValue + "',"
                        + "`expiryDate` = '" + expriDate + "',"
                        + "`status` = '" + statusSelected + "',"
                        + "`isDelete` = '" + isDeleteSelected + "',"
                        + "`updatedBy` = '" + updatedBy + "',"
                        + "`updatedAt` = '" + updatedAt + "'"
                        + " WHERE id = '" + storageId + "'";

                boolean statusSQL = stDAO.runSQL(sql);

//            System.out.println(sql);
                if (statusSQL) {
                    htmlNotification = "                                    <div class=\"icon\"> \n"
                            + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                            + "                                    </div>\n"
                            + "                                    <h3>Cập nhật thông tin thành công</h3>\n"
                            + "                \n"
                            + "                                    <div class=\"buttons\">\n"
                            + "                                        <button data-id=\"" + storageId + "\" onclick=\"confirmUpdateStoSuccess(this)\"  class=\"btn\">OK</button>\n"
                            + "                                    </div>";
                } else {
                    htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                            + "<ion-icon name=\"warning-outline\"></ion-icon>"
                            + "                                    </div>\n"
                            + "                                    <h3>Cập nhật thông tin thất bại</h3>\n"
                            + "                \n"
                            + "                                    <div class=\"buttons\">\n"
                            + "                                        <button  onclick=\"confirmUpdateFalse()\"  class=\"btn\">OK</button>\n"
                            + "                                    </div>";
                }
            }
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
