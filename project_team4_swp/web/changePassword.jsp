

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/profile.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation.css">

        <title>Change password</title>


    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="header-left">
                    <div class="btn-menu">
                        <ion-icon name="menu-outline"></ion-icon>
                    </div>
                    <img class="logo"  onclick="redirectToServelet('./homeController', false)" src="./image/new_logo_napthenay.png" alt="alt"/>

                </div>
                <div class="menu_left">
                    <div class="top_menu">
                        <img class="logo"  onclick="redirectToServelet('./homeController', false)" src="./image/new_logo_napthenay_black.png" alt="alt"/>
                    </div>
                    <ul>
                        <!--  <li onclick="window.location.href = './profile.jsp'">Thông tin tài khoản</li>
                              <li onclick="redirectToServelet('./changePassword', false)">Đổi mật khẩu</li> -->
                        <li onclick="redirectToServelet('./updateProfile', false)">Thông tin tài khoản</li>
                        <li onclick="redirectToServelet('./changePassword', false)">Đổi mật khẩu</li> 
                        <li onclick="redirectToServelet('./purchaseHistory', false)">Lịch sử mua hàng</li> 
                        <li onclick="redirectToServelet('./viewTransactionController', false)">Lịch sử giao dịch</li> 
                        <li onclick="redirectToServelet('./logoutController', true)">Đăng xuất</li> 
                        <!--<li onclick="redirectToServelet('./listproduct', false)">BODY</li>--> 



                        <!--<li onclick="window.location.href = './login.jsp'">1</li>-->
                    </ul>


                    <div class="menu_blur"></div>
                </div>
                <div class="header-right">
                    <div class="balance">Số dư: <span>${sessionScope.balance}</span> </div>

                    <div class="profile" ">
                        <!--onclick="window.location.href = 'profile.jsp'-->
                        <!--<img src="./image/avatar.jpg" alt="alt"/>-->
                        <div class="profile_icon">
                            <ion-icon name="person-circle-outline"></ion-icon>
                        </div>

                    </div>  
                </div>

                <div class="menu_right">
                    <div class="top_menu">
                        <div class="icon">
                            <ion-icon name="person-outline"></ion-icon>
                        </div>
                        <div class="fullName">
                            <h2 title="${sessionScope.user.getName()}">${sessionScope.user.getName()}</h2>
                        </div>
                    </div>
                    <ul>
                        <!--                        <li onclick="window.location.href = './profile.jsp'">Thông tin tài khoản</li>
                                                <li onclick="redirectToServelet('./changePassword', false)">Đổi mật khẩu</li> -->
                        <li onclick="redirectToServelet('./updateProfile', false)">Thông tin tài khoản</li>
                        <li onclick="redirectToServelet('./changePassword', false)">Đổi mật khẩu</li> 
                        <li onclick="redirectToServelet('./purchaseHistory', false)">Lịch sử mua hàng</li> 
                        <li onclick="redirectToServelet('./viewTransactionController', false)">Lịch sử giao dịch</li> 
                        <li onclick="redirectToServelet('./logoutController', true)">Đăng xuất</li> 
                    </ul>
                    <div class="menu_blur"></div>
                </div>
            </div>

            <!--Thông báo-->
            <div class="notification">
                <!--                            <div class="blur"></div>
                                                <div class="notificationBox">
                                                    <div class="icon"> 
                                                        <ion-icon name="checkmark-circle-outline"></ion-icon>
                                                    </div>
                                                    <h3>Họ và tên không được vượt quá 50 ký tự.</h3>
                                                    <button onclick="submitUpdate()"  class="btn_ok">OK</button>
                                                </div>-->
            </div>    


            <div class="main_content">
                <!--<form action="./changePassword" method="post">-->
                <div class="updateForm">
                    <h3 style="background: linear-gradient(to bottom, grey, green, red);-webkit-background-clip: text;
                        background-clip: text; color: transparent;">
                        Đổi mật khẩu
                    </h3>


                    <div class="content">
                        <div class="input_data input_readonly">
                            <label>Tài khoản</label>
                            <input id="account" type="text" readonly name="account" value="${sessionScope.user.getAccount()}">
                        </div>
                        <div class="input_data input_change">
                            <label>Mật khẩu cũ</label>
                            <input id="oldPassword" type="password" placeholder="Mật khẩu cũ" name="old_password" value="${requestScope.oldPassword}" required>
                        </div>
                        <div class="input_data input_change">
                            <label>Mật khẩu mới</label>
                            <input id="new_password" type="password" placeholder="Mật khẩu mới" name="new_password" 
                                   value="${requestScope.new_password}" required> 
                        </div>
                        <div class="input_data input_change" >
                            <label>Nhập lại</label>
                            <input id="re_password" type="password" placeholder="Nhập lại"  name="re_password" 
                                   value="${requestScope.re_password}" required>
                        </div>


                        <div class="captcha">

                            <label>Captcha</label> 

                            <div class="captcha_container">
                                <!-- Hiển thị hình ảnh CAPTCHA -->
                                <img src="./createCaptcha" alt="CAPTCHA Image">
                                <!-- Nút đổi CAPTCHA -->
                                <button type="button" class="btn_captcha" onclick="refreshCaptcha()">
                                    <ion-icon name="repeat-outline"></ion-icon>
                                </button>
                                <!-- Ô nhập CAPTCHA -->
                                <input id="captchaInput" type="text" name="captchaInput" required>



                            </div>

                        </div>


                    </div>
                    <div class="input_data input_readonly notificationText">
                        <label style="color: ${requestScope.colorNotify};">${requestScope.notifyValue}</label>
                        <!--<button>Ok</button>-->
                    </div>
                    <!-- Nút gửi form -->
                    <input onclick="onChange()" class="btn_submit" type="submit" value="Change">
                </div>
                <!--</form>-->
            </div>
            <div class="fotter">

            </div>
        </div>

        <script src="./js/home1.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

        <script>
//                        var balanceElement = document.querySelector(".balance p");
//                        var balance = parseInt(balanceElement.textContent);
//
//                        var format = balance.toLocaleString() + "đ";
//
//                        balanceElement.textContent = format;

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

                        var inputs = document.querySelectorAll('.input_change');
                        var notify = document.querySelector('.notificationText');

                        inputs.forEach(function (i) {
                            i.addEventListener('click', function () {
                                notify.textContent = "";
                            });
                        });


                        var notifyButton = document.querySelector(".notification .btn_ok");
                        var notifyElement = document.querySelector(".notification");


                        function onChange() {
                            var account = document.getElementById('account').value;
                            var old_password = document.getElementById('oldPassword').value;
                            var new_password = document.getElementById('new_password').value;
                            var re_password = document.getElementById('re_password').value;
                            var captchaInput = document.getElementById('captchaInput').value;

//                            console.log(account + " " + old_password
//                                    + "  " + new_password + "  "
//                                    + re_password + "  "
//                                    + captchaInput);

                            $.ajax({
                                url: "./changePassword",
                                type: "post",
                                data: {
                                    account: account,
                                    old_password: old_password,
                                    new_password: new_password,
                                    re_password: re_password,
                                    captchaInput: captchaInput
                                },
                                success: function (data) {
                                    notifyElement.innerHTML = data.blur + data.notificationBox;
                                    notifyElement.style.display = "flex";

                                    var resfreshCaptcha = document.querySelector(".btn_captcha");
                                    resfreshCaptcha.click();

                                },
                                error: function (xhr) {

                                }
                            });
                        }

                        function submitUpdate() {
                            notifyElement.style.display = "none";
                        }

        </script>
    </body>
</html>
