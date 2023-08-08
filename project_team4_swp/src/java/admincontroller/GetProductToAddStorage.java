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
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author phamtung
 */
@WebServlet(name = "GetProductToAddStorage", urlPatterns = {"/getProductToAddStorage"})
public class GetProductToAddStorage extends HttpServlet {

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
            out.println("<title>Servlet GetProductToAddStorage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetProductToAddStorage at " + request.getContextPath() + "</h1>");
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
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession(false);

        int updatedById = (int) session.getAttribute("userId");
        String updatedBy = updatedById + "";

        String productId = request.getParameter("productId");
        int pId = Integer.parseInt(productId);

        ProductDAO pDAO = new ProductDAO();

        Product p = pDAO.getProductById(pId);

        String htmls = "<h3>Thêm thẻ từ Excel</h3>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Mã số sản phẩm</label>\n"
                + "                            <input  class=\"readOnly\" id=\"productIdAdd\" readonly=\"\" value=\"" + p.getId() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Tên sản phẩm: </label>\n"
                + "                            <input title=\"\" class=\"readOnly\" id=\"nameProductAdd\" readonly=\"\" value=\"" + p.getName() + "\">\n"
                + "                        </div>\n"
                + "\n"
                + "                        <div class=\"items\"> \n"
                + "                            <label>Số lượng:</label>\n"
                + "                            <input class=\"readOnly\" id=\"quantityAdd\" readonly=\"\" value=\"" + p.getQuantity() + "\">\n"
                + "                        </div>\n"
                + "                        <div class=\"items choseFile\"> \n"
                + "                            <label>Chọn file excel:</label>\\\n"
                + "                            <input id=\"fileExcel\" type=\"file\"  accept=\"excel/*\" name=\"avatar\">\n"
                + "                        </div>\n"
                + "                        <div class=\"group_button\">\n"
                + "                            <button class=\"ok_btn btn_1\" onclick=\"closeAddStorage()\">Close</button>\n"
                + "                            <button data-createdby=\""+updatedBy+"\" data-id=\"" + p.getId() + "\" class=\"ok_btn btn_2\" onclick=\"addStorageExcel(this)\">Add</button>\n"
                + "                        </div>";

        jsonObject.put("htmls", htmls);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
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
        processRequest(request, response);
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
