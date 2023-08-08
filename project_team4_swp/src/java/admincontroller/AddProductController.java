/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.ProductDAO;
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
@WebServlet(name = "AddProductController", urlPatterns = {"/addProductController"})
public class AddProductController extends HttpServlet {

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
            out.println("<title>Servlet AddProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("addProduct.jsp").forward(request, response);

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

        long createdAt = GetCurrentTime.getCurrentTime();
        int createdById = (int) session.getAttribute("userId");
        String createdBy = createdById + "";
//        System.out.println(createdBy);
        ProductDAO pDAO = new ProductDAO();

        String productName = request.getParameter("productName");
        String category = request.getParameter("categoryChose");
        String productDes = request.getParameter("productDes");
        String imageName = request.getParameter("imageName");

        String productPrice = request.getParameter("productPrice");
        int price = Integer.parseInt(productPrice);
        
        int quantity = 0;
        int isShow = 0; // False -> không hiển thị trên web
        int isDelete = 0; // False -> Trạng thái không xóa 

        String sqlCheckName = "SELECT * FROM `product` WHERE `name` = '" + productName + "' ";

//        System.out.println(sqlCheckName);
//        System.out.println( "Status: " +pDAO.runSQLExecuteQuery(sqlCheckName));
        
        
        String htmlNotification = "";
        if (pDAO.runSQLExecuteQuery(sqlCheckName)) {
            htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                    + "<ion-icon name=\"warning-outline\"></ion-icon>"
                    + "                                    </div>\n"
                    + "                                    <h3>Tên sản phẩm này đã tồn tại trong kho.</h3>\n"
                    + "                \n"
                    + "                                    <div class=\"buttons\">\n"
                    + "                                        <button  onclick=\"closeNotify()\"  class=\"btn\">OK</button>\n"
                    + "                                    </div>";
        } else {
            String sql = sql = "INSERT INTO `product`(`createdBy`, `price`,"
                    + " `createdAt`, `name`, `description`, `image`, `quantity`, `category`"
                    + ",`isShow`,`isDelete`) "
                    + "VALUES ("
                    + "'" + createdBy + "',"
                    + "'" + price + "',"
                    + "'" + createdAt + "',"
                    + "'" + productName + "',"
                    + "'" + productDes + "',"
                    + "'" + imageName + "',"
                    + "'" + quantity + "',"
                    + "'" + category + "',"
                    + "'" + isShow + "',"
                    + "'" + isDelete + "'"
                    + ")";

            System.out.println(sql);

            boolean statusSQL = pDAO.runSQL(sql);

            System.out.println(statusSQL);

            if (statusSQL) {
                htmlNotification = "                                    <div class=\"icon\"> \n"
                        + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                        + "                                    </div>\n"
                        + "                                    <h3>Thêm sản phẩm thành công</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button  onclick=\"closeNotify()\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            } else {
                htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                        + "                                   <ion-icon name=\"warning-outline\"></ion-icon>"
                        + "                                    </div>\n"
                        + "                                    <h3>Thêm sản phẩm thất bại</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button  onclick=\"closeNotify()\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            }
        }
        
//        String htmlNotification = "";
//        htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
//                + "<ion-icon name=\"warning-outline\"></ion-icon>"
//                + "                                    </div>\n"
//                + "                                    <h3>Thêm sản phẩm thất bại</h3>\n"
//                + "                \n"
//                + "                                    <div class=\"buttons\">\n"
//                + "                                        <button  onclick=\"closeNotify()\"  class=\"btn\">OK</button>\n"
//                + "                                    </div>";

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
