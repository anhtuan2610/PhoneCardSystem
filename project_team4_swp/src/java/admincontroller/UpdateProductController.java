/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.UUID;
import org.json.simple.JSONObject;
import services.GetCurrentTime;

/**
 *
 * @author phamtung
 */
@WebServlet(name = "UpdateProductController", urlPatterns = {"/updateProductController"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UpdateProductController extends HttpServlet {

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
            out.println("<title>Servlet UpdateProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductController at " + request.getContextPath() + "</h1>");
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
    private static final String UPLOAD_DIRECTORY = "image";

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
        // Tạo thư mục lưu trữ nếu chưa tồn tại
//        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }
//
//        String webPath = getServletContext().getRealPath("");
//
//        String uploadPath2 = webPath.replace("\\build\\", "\\") + "image";
//
//        File uploadDir2 = new File(uploadPath2);
//        if (!uploadDir2.exists()) {
//            uploadDir2.mkdirs();
//        }
//
//        // Xử lý tệp ảnh được tải lên
//        Part filePart = request.getPart("image");
//
//        if (filePart != null) {
//            String fileName = filePart.getSubmittedFileName();
//            String filePath = uploadPath + File.separator + fileName;
//            String filePath2 = uploadPath2 + File.separator + fileName;
//            filePart.write(filePath);
//            filePart.write(filePath2);
//        System.out.println(filePath);
//        System.out.println(filePath2);

//        }
//
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession(false);
        ProductDAO pDAO = new ProductDAO();

        GetCurrentTime getTime = new GetCurrentTime();
        long updatedAt = getTime.getCurrentTime();
        int updatedById = (int) session.getAttribute("userId");
        String updatedBy = updatedById + "";

        String id = request.getParameter("id");
        int productId = Integer.parseInt(id);
        String imageName = request.getParameter("imageName");
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String priceS = request.getParameter("price");
        int price = Integer.parseInt(priceS);
        String isShowSelectedS = request.getParameter("isShowSelected");
        int isShowSelected = Integer.parseInt(isShowSelectedS);
        String isDeleteSelectedS = request.getParameter("isDeleteSelected");
        int isDeleteSelected = Integer.parseInt(isDeleteSelectedS);
        String nameIsChange = request.getParameter("nameIsChange");

//        System.out.println(id);
//        System.out.println(imageName);
//        System.out.println(productName);
//        System.out.println(category);
//        System.out.println(description);
//        System.out.println(price);
//        System.out.println(isShowSelected);
//        System.out.println(isDeleteSelected);
        String sqlCheckName = "SELECT * FROM `product` WHERE `name` = '" + productName + "' ";
        String htmlNotification = "";

        if (nameIsChange.equals("false")) {
            String sql = sql = "UPDATE `product` SET `image` = '" + imageName + "',"
                    + "`name` = '" + productName + "',"
                    + "`category` = '" + category + "',"
                    + "`description` = '" + description + "',"
                    + "`price` = '" + price + "',"
                    + "`isShow` = '" + isShowSelected + "',"
                    + "`updatedAt` = '" + updatedAt + "',"
                    + "`updatedBy` = '" + updatedBy + "',"
                    + "`isDelete` = '" + isDeleteSelected + "' WHERE id = '" + productId + "'";

            boolean statusSQL = pDAO.runSQL(sql);

//            System.out.println(sql);
            if (statusSQL) {
                htmlNotification = "                                    <div class=\"icon\"> \n"
                        + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                        + "                                    </div>\n"
                        + "                                    <h3>Cập nhật thông tin thành công</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button data-id=\"" + productId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            } else {
                htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                        + "<ion-icon name=\"warning-outline\"></ion-icon>"
                        + "                                    </div>\n"
                        + "                                    <h3>Cập nhật thông tin thất bại</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button data-id=\"" + productId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";
            }
        } else {
            if (pDAO.runSQLExecuteQuery(sqlCheckName)) {
                htmlNotification = "                                    <div class=\"icon\"> \n"
                        + "<ion-icon name=\"warning-outline\"></ion-icon>"
                        + "                                    </div>\n"
                        + "                                    <h3>Tên sản phẩm đã tồn tại</h3>\n"
                        + "                \n"
                        + "                                    <div class=\"buttons\">\n"
                        + "                                        <button data-id=\"" + productId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                        + "                                    </div>";

            } else {
                String sql = sql = "UPDATE `product` SET `image` = '" + imageName + "',"
                        + "`name` = '" + productName + "',"
                        + "`category` = '" + category + "',"
                        + "`description` = '" + description + "',"
                        + "`price` = '" + price + "',"
                        + "`isShow` = '" + isShowSelected + "',"
                        + "`updatedAt` = '" + updatedAt + "',"
                        + "`updatedBy` = '" + updatedBy + "',"
                        + "`isDelete` = '" + isDeleteSelected + "' WHERE id = '" + productId + "'";

                boolean statusSQL = pDAO.runSQL(sql);

//            System.out.println(sql);
                if (statusSQL) {
                    htmlNotification = "                                    <div class=\"icon\"> \n"
                            + "                                        <ion-icon name=\"checkmark-circle-outline\"></ion-icon>\n"
                            + "                                    </div>\n"
                            + "                                    <h3>Cập nhật thông tin thành công</h3>\n"
                            + "                \n"
                            + "                                    <div class=\"buttons\">\n"
                            + "                                        <button data-id=\"" + productId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
                            + "                                    </div>";
                } else {
                    htmlNotification = "                                    <div style=\"color: yellow\" class=\"icon\"> \n"
                            + "<ion-icon name=\"warning-outline\"></ion-icon>"
                            + "                                    </div>\n"
                            + "                                    <h3>Cập nhật thông tin thất bại</h3>\n"
                            + "                \n"
                            + "                                    <div class=\"buttons\">\n"
                            + "                                        <button data-id=\"" + productId + "\" onclick=\"confirmUpdateSuccess(this)\"  class=\"btn\">OK</button>\n"
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
