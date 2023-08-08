<%-- 
    Document   : login
    Created on : May 24, 2023, 9:40:07 PM
    Author     : phamtung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .login-container {
                width: 300px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            h2 {
                text-align: center;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                display: block;
                font-weight: bold;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 3px;
            }

            .btn-submit {
                display: block;
                width: 100%;
                padding: 8px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }

            button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Login</h2>
            <form action="./loginController" method="post">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="captcha">

                    <label>Captcha</label> 

                    <div class="captcha_container">
                        <!-- Hiển thị hình ảnh CAPTCHA -->
                        <img src="./captcha" alt="CAPTCHA Image">
                        <!-- Nút đổi CAPTCHA -->
                        <button type="button" class="btn_captcha" onclick="refreshCaptcha()">
                            <ion-icon name="repeat-outline"></ion-icon>
                        </button>
                        <!-- Ô nhập CAPTCHA -->
                        <input type="text" name="captchaInput">

                        <label class="notify" style="color: ${requestScope.colorNotify}" name="notify">${sessionScope.notifyCaptcha}</label>

                    </div>

                </div>
                <button class="btn-submit" type="submit">Login</button>
                <label class="notify" style="color: ${requestScope.colorNotify}" name="notify">${sessionScope.notifyLogin}</label>

                <label>${sessionScope.userId}  ${sessionScope.isLogin} </label>
                <label class="notify">${sessionScope.notifyLogin}</label>

                <%
                    // Xóa các thuộc tính session đã sử dụng
                    session.removeAttribute("notifyLogin");
                    session.removeAttribute("notifyCaptcha");
                    session.removeAttribute("captchaCode");
//                    session.removeAttribute("javascriptCode");
                %>
            </form>
        </div>


        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>    
        <script>
                            // Hàm gọi serverlet để lấy CAPTCHA mới và cập nhật hình ảnh CAPTCHA
                            function refreshCaptcha() {
                                var captchaImage = document.querySelector("img[alt='CAPTCHA Image']");
                                captchaImage.src = "./captcha?" + new Date().getTime();
                            }
                            function redirectToServelet(servletURL, post) {
                                var form = document.createElement('form');
                                form.method = post ? 'POST' : 'GET';
                                form.action = servletURL;

                                document.body.appendChild(form);
                                form.submit();
                            }

                            var javascriptCode = "<%= session.getAttribute("javascriptCode") %>";
                            if (javascriptCode) {
                                eval(javascriptCode); // Thực thi mã JavaScript từ servlet
                            }
                            
                            
        </script>
    </body>
</html>
