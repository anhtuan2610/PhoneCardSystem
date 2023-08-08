/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package services;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;

/**
 *
 * @author phamtung
 */
public class CreateCaptcha extends HttpServlet {

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
            out.println("<title>Servlet captcha</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet captcha at " + request.getContextPath() + "</h1>");
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
        // Tạo mã CAPTCHA ngẫu nhiên
        String captchaCode = generateCaptchaCode();

        // Lưu mã CAPTCHA vào session
        HttpSession session = request.getSession();
        session.setAttribute("captchaCode", captchaCode);

        // Tạo hình ảnh CAPTCHA
        BufferedImage captchaImage = generateCaptchaImage(captchaCode);

        // Gửi hình ảnh CAPTCHA về client
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(captchaImage, "png", outputStream);
        outputStream.close();
    }

    private String generateCaptchaCode() {
        // Tạo mã CAPTCHA ngẫu nhiên, ví dụ: sử dụng UUID
        return UUID.randomUUID().toString().substring(0, 4);
    }

    private BufferedImage generateCaptchaImage(String captchaCode) {
        int width = 140;
        int height = 50;
        Random random = new Random();
        // Tạo đối tượng BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Vẽ nền cho hình ảnh CAPTCHA
        graphics.setColor(Color.lightGray);
        graphics.fillRect(0, 0, width, height);

        // Vẽ mã CAPTCHA lên hình ảnh
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 36));
        graphics.drawString(captchaCode, 15, 40);

        // Vẽ  đường kẻ với opacity 0.7
        for (int i = 0; i < 36; i++) {
            float strokeWidth = 1.0f; // Độ dày mong muốn
            graphics.setStroke(new BasicStroke(strokeWidth));
            
            int startX = random.nextInt(width);
            int startY = random.nextInt(height);
            int endX = random.nextInt(width);
            int endY = random.nextInt(height);

            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            int alpha = 180; // Độ mờ

            graphics.setColor(new Color(red, green, blue, alpha));
            graphics.drawLine(startX, startY, endX, endY);
        }

        for (int i = 0; i < 15; i++) {
            float strokeWidth = 1.2f; // Độ dày mong muốn
            graphics.setStroke(new BasicStroke(strokeWidth));
            int startX = random.nextInt(width);
            int startY = random.nextInt(height);
            int endX = random.nextInt(width);
            int endY = random.nextInt(height);

            int red = random.nextInt(5);
            int green = random.nextInt(5);
            int blue = random.nextInt(5);
            int alpha = 180; // Độ mờ

            graphics.setColor(new Color(red, green, blue, alpha));
            graphics.drawLine(startX, startY, endX, endY);
        }

        graphics.dispose();

        return image;
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
