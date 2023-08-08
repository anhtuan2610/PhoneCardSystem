<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Buy Card Online</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
        crossorigin="anonymous"></script>
        <style>
            .captcha_container button
            {
                width: 40px;
                height: 40px;
                border-radius: 10px;
                margin:0 20px 0 5px;
            }
            .check_btn
            {
                margin: 10px 0;
            }
        </style>
    </head>
    <body >
        <div class="container">
            <h1>QUÊN MẬT KHẨU</h1>
            <span style="color: red">
                ${err}
            </span>
            <form action="sendEmail" method="post">
                <div class="mb-3">
                    <label for="matKhauMoi" class="form-label">Tài khoản</label>
                    <input type="text" class="form-control" id="account" name="account">
                </div>

                <div class="captcha_container">
                    <!-- Hiển thị hình ảnh CAPTCHA -->
                    <img src="./createCaptcha" alt="CAPTCHA Image">
                    <!-- Nút đổi CAPTCHA -->
                    <button type="button" class="btn_captcha" onclick="refreshCaptcha()">
                        <ion-icon name="repeat-outline"></ion-icon>
                    </button>
                    <!-- Ô nhập CAPTCHA -->
                    <input type="text" name="captchaInput" required>

                    
                </div>

                <button type="submit" class="btn btn-primary check_btn">CheckAccount</button>
                <div class="mb-3" style="color: red; font-size: 30px;"><p >${error1}</p></div>
            </form>
        </div>

            

        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
        <script>
                        // Hàm gọi serverlet để lấy CAPTCHA mới và cập nhật hình ảnh CAPTCHA
                        function refreshCaptcha() {
                            var captchaImage = document.querySelector("img[alt='CAPTCHA Image']");
                            captchaImage.src = "./createCaptcha?" + new Date().getTime();
                        }



        </script>
    </body>
</html>