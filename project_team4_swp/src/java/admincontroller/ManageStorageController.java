/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.StorageDAO;
import entity.Storage;
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
@WebServlet(name = "ManageStorageController", urlPatterns = {"/manageStorageController"})
public class ManageStorageController extends HttpServlet {

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
            out.println("<title>Servlet ManageStorageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageStorageController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("./manageStorage.jsp").forward(request, response);
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

        String storageIdS = request.getParameter("storageId");
        int storageId = Integer.parseInt(storageIdS);

        StorageDAO stoDAO = new StorageDAO();

        Storage s = stoDAO.getStorageById(storageId);

        String htmlIsDelete = "";
        String htmlStatus = "";

        int isDelete = s.getIsDelete();
        int status = s.getStatus();

        if (isDelete == 0) {
            htmlIsDelete = "<div class=\"items\"> \n"
                    + "                            <label>isDelete:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input  type=\"radio\"  name=\"group_isDelete\" id=\"isDelete1\" value=\"1\">\n"
                    + "                                <label for=\"isDelete1\">Deleted</label>\n"
                    + "\n"
                    + "                                <input data-datasql=\"checked\" checked=\"\" type=\"radio\" checked=\"\" name=\"group_isDelete\" id=\"isDelete2\" value=\"0\">\n"
                    + "                                <label for=\"isDelete2\">Undeleted</label>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                        </div>";
        } else if (isDelete == 1) {
            htmlIsDelete = "<div class=\"items\"> \n"
                    + "                            <label>isDelete:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input data-datasql=\"checked\" checked=\"\" type=\"radio\"  name=\"group_isDelete\" id=\"isDelete1\" value=\"1\">\n"
                    + "                                <label for=\"isDelete1\">Deleted</label>\n"
                    + "\n"
                    + "                                <input  type=\"radio\"  name=\"group_isDelete\" id=\"isDelete2\" value=\"0\">\n"
                    + "                                <label for=\"isDelete2\">Undeleted</label>\n"
                    + "                            </div>\n"
                    + "\n"
                    + "                        </div>";
        }
        if (status == 0) {
            htmlStatus = "<div class=\"items\"> \n"
                    + "                            <label>Status:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input type=\"radio\" name=\"group_Status\" id=\"Status1\" value=\"1\">\n"
                    + "                                <label for=\"Status1\">On sale</label>\n"
                    + "                                <input data-datasql=\"checked\" checked=\"\"  type=\"radio\" name=\"group_Status\" id=\"Status2\" value=\"0\">\n"
                    + "                                <label for=\"Status2\">Sold out</label>\n"
                    + "                            </div>\n"
                    + "                        </div>";
        } else if (status == 1) {
            htmlStatus = "<div class=\"items\"> \n"
                    + "                            <label>Status:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input data-datasql=\"checked\" checked=\"\" type=\"radio\" name=\"group_Status\" id=\"Status1\" value=\"1\">\n"
                    + "                                <label for=\"Status1\">On sale</label>\n"
                    + "                                <input  type=\"radio\" name=\"group_Status\" id=\"Status2\" value=\"0\">\n"
                    + "                                <label for=\"Status2\">Sold out</label>\n"
                    + "                            </div>\n"
                    + "                        </div>";
        }
        String htmls = "<h3>Chi tiết thẻ</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số thẻ:</label>\n"
                + "                            <input  class=\"readOnly\" id=\"storageId\" readonly=\"\" value=\""+s.getId()+"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Tên sản phẩm: </label>\n"
                + "                            <input title=\"\" class=\"readOnly\" id=\"nameProduct\" readonly=\"\" value=\""+s.getProduct().getName()+"\">\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số seri:</label>\n"
                + "                            <input data-sqldb=\""+s.getSeri()+"\" class=\"\" id=\"seri\"  value=\""+s.getSeri()+"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Code:</label>\n"
                + "                            <input data-sqldb=\""+s.getCode()+"\" class=\"\" id=\"code\"  value=\""+s.getCode()+"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Expiry date:</label>\n"
                + "                            <input data-sqldb=\""+s.getExpiryDate()+"\" class=\"\" id=\"expridyDate\"  value=\""+s.getExpiryDate()+"\">\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Ngày tạo: </label>\n"
                + "                            <input title=\"\" class=\"readOnly\" id=\"createdAdSto\" readonly=\"\" value=\""+s.getCreatedAt()+"\">\n"
                + "                        </div>\n"
                + htmlStatus
                + htmlIsDelete
                + "                       \n"
                + "                        <div class=\"group_button\">\n"
                + "                            <button class=\"ok_btn btn_1\" onclick=\"submitOk()\">Close</button>\n"
                + "                            <button data-id=\""+s.getId()+"\" data-pid=\""+s.getProduct().getId()+"\" class=\"ok_btn btn_2\" onclick=\"updateInfoStorage(this)\">Update</button>\n"
                + "                        </div>";

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
