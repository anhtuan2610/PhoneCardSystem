<%-- 
    Document   : login
    Created on : May 28, 2023, 7:43:21 PM
    Author     : trana
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Đăng nhập</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
            rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
            crossorigin="anonymous">
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
            integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
        crossorigin="anonymous"></script>
        <style>
            .red {
                color: red;
            }
        </style>
        <!-- Custom styles for this template -->
        <!-- getScheme() là http hoặc https-->
        <!-- getServerName() là tên server-->
        <!-- getServerPort()là 8080 hoặc .. -->
        <!-- getContextPath())tên dự án -->
        <link href="css/signin.css" rel="stylesheet">
    </head>
    <body style="background: #95afc0">

        


        <main class="form-signin w-100 m-auto" style="background: whitesmoke;border-radius:25px;width: 800px">
            <!-- Tạo form đăng nhập -->
            <form class="text-center" action="login" method="post">
                <a href="home"><img class="mb-4" src="./image/logoLogin.jpg" alt="" width="100" height="65"/></a>

                <h1 class="h3 mb-3 fw-normal">ĐĂNG NHẬP</h1>
                <div class="text-center"></div><span class="red">${baoLoi}</span>
                <div class="form-floating">
                    <input type="text" class="form-control" id="account"
                           placeholder="Tên đăng nhập" name="account" required="required" value="${requestScope.account}" > <label for="tenDangNhap">Tên đăng nhập</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" id="password"
                           placeholder="Mật khẩu" name="password"  required="required" value="${requestScope.password}"> <label for="matKhau">Mật khẩu</label>
                </div>
                <div class="form-floating">
                    <p id = "error" style="color: red">${error}</p>
<!--                    <p id = "error" style="color: red">${error2}</p>
                    <p id = "error" style="color: red">${error3}</p>-->
                    <p id = "error">${success1}</p>
                    <div class="form-floating"> <!<!-- tao. captcha -->
                        <div class="captcha">

                            <div class="captcha_container">
                                <!-- Hiển thị hình ảnh CAPTCHA -->
                                <img src="./createCaptcha" alt="CAPTCHA Image">
                                <!-- Nút đổi CAPTCHA -->
                                <button type="button" style="width: 40px; height: 40px; border-radius: 10px" class="captchaReload" onclick="refreshCaptcha()">
                                    <ion-icon name="repeat-outline"></ion-icon>
                                </button>
                                <!-- Ô nhập CAPTCHA -->
                                <input type="text" style="margin-top: 5px; " name="captchaInput" required>

                            </div>

                        </div>
                    </div>
                    <button class="w-100 btn btn-lg btn-primary" style="margin-top: 10px" type="submit">Đăng nhập</button>
                    <a href="./forgetPassword">Quên mật khẩu</a><br>
                    <a href="./dangky">Đăng ký tài khoản mới</a>
            </form>
        </main>
    </body>
    <!--<script src="./js/home.js"></script>-->
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>                        
    <script>
                                    // Hàm gọi serverlet để lấy CAPTCHA mới và cập nhật hình ảnh CAPTCHA
                                    function refreshCaptcha() {
                                        var captchaImage = document.querySelector("img[alt='CAPTCHA Image']");
                                        captchaImage.src = "./createCaptcha?" + new Date().getTime();
                                    }
                                    function redirectToServelet(servletURL, post) {
                                        var form = document.createElement('form');
                                        form.method = post ? 'POST' : 'GET';
                                        form.action = servletURL;

                                        document.body.appendChild(form);
                                        form.submit();
                                    }
                                    
                                  window.onload = function (){
                                      console.log("Login JSP");
                                      var refreshCaptchaE = document.querySelector(".captcha_container button");
//                                      console.log(refreshCaptchaE);
                                      refreshCaptchaE.click();
                                  }; 
                                    
    </script>
</html>