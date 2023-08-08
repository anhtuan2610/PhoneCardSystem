/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admincontroller;

import dal.ProductDAO;
import entity.Product;
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
@WebServlet(name = "ManageProductController", urlPatterns = {"/manageProductController"})
public class ManageProductController extends HttpServlet {

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
            out.println("<title>Servlet ManageProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageProductController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("manageProduct.jsp").forward(request, response);

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

        String productId = request.getParameter("productId");
        int pId = Integer.parseInt(productId);

        ProductDAO pDAO = new ProductDAO();

        Product pro = pDAO.getProductById(pId);

        String htmlIsDelete = "";
        String htmlIsShow = "";

        int isDelete = pro.getIsDelete();
        int isShow = pro.getIsShow();

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
        if (isShow == 0) {
            htmlIsShow = " <div class=\"items\"> \n"
                    + "                            <label>isShow:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input   type=\"radio\"  name=\"group_isShow\" id=\"isShow1\" value=\"1\">\n"
                    + "                                <label for=\"isShow1\">Showing</label>\n"
                    + "                                <input data-datasql=\"checked\" type=\"radio\" checked=\"\" name=\"group_isShow\" id=\"isShow2\" value=\"0\">\n"
                    + "                                <label for=\"isShow2\">Hiding</label>\n"
                    + "                            </div>\n"
                    + "                        </div>";
        } else if (isShow == 1) {
            htmlIsShow = " <div class=\"items\"> \n"
                    + "                            <label>isShow:</label>\n"
                    + "                            <div class=\"group_chose\">\n"
                    + "                                <input data-datasql=\"checked\" checked=\"\"  type=\"radio\"  name=\"group_isShow\" id=\"isShow1\" value=\"1\">\n"
                    + "                                <label for=\"isShow1\">Showing</label>\n"
                    + "                                <input type=\"radio\"  name=\"group_isShow\" id=\"isShow2\" value=\"0\">\n"
                    + "                                <label for=\"isShow2\">Hiding</label>\n"
                    + "                            </div>\n"
                    + "                        </div>";
        }
        String htmls = "<h3>Thông tin sản phẩm</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số sản phẩm</label>\n"
                + "                            <input  class=\"readOnly\" id=\"productId\" readonly=\"\"  value=\"" + pro.getId() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Tên sản phẩm: </label>\n"
                + "                            <input title=\"\" class=\"\" id=\"nameProduct\"  data-datasql=\"" + pro.getName() + "\" value=\"" + pro.getName() + "\">\n"
                + "                        </div>\n"
                + "<div class=\"items \"> \n"
                + "                            <label>Nhà mạng:</label>\n"
                + "                            <div class=\"items_categoty_select\">\n"
                + "                                <select id=\"select_category_detail\" class=\"category_select\">\n"
                + "                                </select>\n"
                + "                            </div>\n"
                + "                        </div>"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Nhà mạng khác:</label>\n"
                + "                            <input title=\"\" class=\"\" id=\"otherCategory\"  data-datasql=\"" + pro.getCategory()+ "\" value=\"\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Đơn giá:</label>\n"
                + "                            <input title=\"\" class=\"\" id=\"price\"  data-datasql=\"" + pro.getPrice() + "\" value=\"" + pro.getPrice() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số lượng:</label>\n"
                + "                            <input class=\"readOnly\" id=\"quantity\" readonly=\"\" value=\"" + pro.getQuantity() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Ngày tạo:</label>\n"
                + "                            <input class=\"readOnly\" readonly=\"\" id=\"createdAt\"  value=\"" + pro.getCreatedAt() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items position_top\"> \n"
                + "                            <label class=\"\">Mô tả:</label>\n"
                + "                            <textarea data-datasql=\"" + pro.getDescription() + "\" id=\"text_des\" rows=\"4\">\n"
                + "                                \n " + pro.getDescription() + ""
                + "                            </textarea>\n"
                + "                        </div>\n"
                + "\n"
                + "\n"
                + htmlIsShow
                + htmlIsDelete
                + "\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Chọn ảnh:</label>\\\n"
                + "                            <input id=\"avatar\" type=\"file\" onchange=\"displayImage(event)\" accept=\"image/*\" name=\"avatar\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items previewImage\" > \n"
                + "                            <label></label>\n"
                + "                            <img data-name=\""+pro.getImage()+"\" id=\"preview\" src=\"image/" + pro.getImage() + "\" alt=\"Ảnh xem trước\">\n"
                + "                        </div>\n"
                + "                        <div class=\"group_button\">\n"
                + "                            <button class=\"ok_btn btn_1\" onclick=\"submitOk()\">Close</button>\n"
                + "                            <button data-id=\"" + pro.getId() + "\" class=\"ok_btn btn_2\" onclick=\"updateInfoProduct(this)\">Update</button>\n"
                + "                        </div>";

        jsonObject.put("htmls", htmls);
        jsonObject.put("category", pro.getCategory());
        
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
