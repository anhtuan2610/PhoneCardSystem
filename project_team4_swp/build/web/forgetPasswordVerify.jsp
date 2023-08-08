<%-- 
    Document   : SendEmail
    Created on : May 29, 2023, 11:00:16 AM
    Author     : trana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Shop Card</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" >
        <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
        crossorigin="anonymous"></script>
    </head>
    <body >
        <div class="container">
            <h1>NHẬP MÃ OTP GỬI TRONG MAIL ĐÃ ĐĂNG KÝ THEO TÀI KHOẢN CỦA BẠN</h1>
            <!--            <span style="color: red">
            ${err}
        </span>-->
            <form action="checkOtp" method="post">
                <div class="mb-3">
                    <label for="OTP" class="form-label">Mã Otp</label>
                    <input type="text" class="form-control" id="otp" name="otp" value="${requestScope.otp}">
                </div>

                <button onclick="reSendEmail()" class="re_send_btn" type="button"> Re-send </button><!-- comment -->

                <div class="mb-3">
                    <label for="matKhauMoi" class="form-label">Mật khẩu mới</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" value="${requestScope.newPassword}">
                </div>
                <button type="submit" class="btn btn-primary">OK</button>

                <div class="mb-3">${error1}</div>

<!--                <input id="email" type="hidden" name="email" value="${requestScope.email}">
                <input id="account" type="hidden" name="account" value="${requestScope.account}">-->

                <!--<input type="hidden" name="newPassword" value="${newPassword}">-->
            </form>


        </div>
    </body>
    <script>
        var button = document.querySelector('.re_send_btn');
        var countdown;
        var remainingTime = 15;
       


        window.onload = function () {

            disableButton();
        };
        function disableButton() {
            button.disabled = true;
            countdown = setInterval(updateCountdown, 1000);
        }

        function reSendEmail() {
            redirectToServelet("./resendForgetPassController", true);

        }
        function updateCountdown() {

            button.innerText = "Chờ đợi (" + remainingTime + " giây)";
            remainingTime--;
            if (remainingTime === -2) {
                clearInterval(countdown);
                button.disabled = false;
                button.innerText = "Re-send";
                remainingTime = 15;
            }

        }

        function redirectToServelet(servletURL, post) {
            var form = document.createElement('form');
            form.method = post ? 'POST' : 'GET';
            form.action = servletURL;
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</html>
