

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/profile.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation.css">

        <title>Profile</title>
        <style>


            .notificationText{
                position: absolute;
                font-size: 2.7rem;
                top: 52rem;
                display: flex;
                justify-content: center;
                align-items: center;
            }
        </style>
    </head>
    <body>





        <div class="container">
            <div class="header">
                <div class="header-left">
                    <div  class="btn-menu">
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
                            <h2 title="${sessionScope.user.getName()}" id="fullName">${sessionScope.user.getName()}</h2>
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
                                                         <ion-icon name="warning-outline"></ion-icon>                                   
                                                    </div>
                                                    <h3>Họ và tên không được vượt quá 50 ký tự.</h3>
                                                    <button onclick="submitUpdate()"  class="btn_ok">OK</button>
                                                </div>-->
            </div>          



            <div class="main_content">

                <!--<form  action="./updateProfile" method="post">-->
                <div class="updateForm">
                    <h3>Thông tin tài khoản</h3>


                    <div class="content">
                        <div class="input_data input_readonly">
                            <label>Mã số tài khoản</label>
                            <input id="userID" type="text" readonly="" name="userID" value="${sessionScope.user.getId()}">
                        </div>
                        <div class="input_data input_change">
                            <label>họ và tên</label>
                            <input id="fullname" type="text" name="fullname" value="${sessionScope.user.getName()}">
                        </div>
                        <div class="input_data input_change">
                            <label>số điện thoại</label>
                            <input id="phone" type="text" name="phonenumber" value="${sessionScope.user.getPhone()}">
                        </div>
                        <div class="input_data input_readonly">
                            <label>Email</label>
                            <input id="email" type="text" readonly="" name="email" value="${sessionScope.user.getEmail()}">
                        </div>

<!--<label class="notify" style="color: ${requestScope.colorNotify}" name="notify">${requestScope.notifyValue}</label>-->





                    </div>

                    <!-- <div class="input_data input_readonly notificationText">
                        <label style="color: ${requestScope.colorNotify}">${requestScope.notifyValue}</label>
                    <!--<button>Ok</button>-->


                    <input onclick="onUpdate()" class="btn_submit" type="submit" value="Update">

                </div> 

                <!-- Nút gửi form -->
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


                        var inputs = document.querySelectorAll('.input_change');
                        var notify = document.querySelector('.notification');

                        inputs.forEach(function (i) {
                            i.addEventListener('click', function () {
                                notify.textContent = "";
                            });
                        });



//                        var balanceElement = document.querySelector(".balance p");
//                        var balance = parseInt(balanceElement.textContent);
//
//                        var format = balance.toLocaleString();
//
//                        balanceElement.textContent = format +"đ";


//                                Xử lý thông báo
                        var notifyButton = document.querySelector(".notification .btn_ok");
                        var notifyElement = document.querySelector(".notification");

//                        if (notifyButton != null)
//                        {
//                            console.log(notifyElement);
//                            console.log(notifyButton);
//
//                            notifyElement.addEventListener("click", function () {
//                                notifyElement.style.display = "none";
//                            });
//                        }





                        function onUpdate() {
                            console.log('click Update');


                            var fullname = document.getElementById('fullname').value;
                            var phonenumber = document.getElementById('phone').value;
                            var userID = document.getElementById('userID').value;
                            var email = document.getElementById('email').value;

                            console.log(fullname + phonenumber + "  ID:  " + userID);
                            $.ajax({
                                url: "./updateProfile",
                                type: "post",
                                data: {
                                    fullname: fullname,
                                    phonenumber: phonenumber,
                                    userID: userID,
                                    email: email
                                },
                                success: function (data) {

                                    notifyElement.innerHTML = data.blur + data.notificationBox;
                                    notifyElement.style.display = "flex";
                                    if (data.type == 'successful')
                                    {
                                        document.getElementById('fullName').textContent = fullname;
                                    }

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
