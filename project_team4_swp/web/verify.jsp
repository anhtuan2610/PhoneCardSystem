<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/verify.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation2.css">

        <title>JSP Page</title>

    </head>
    <body>
        <div class="container">
            <div id="notification" class="notification">
                <div class="notify_blur"></div>
                <div class="notificationBox">
                    <!--                    <div class="icon" style="color: yellow"> 
                                            <ion-icon name="checkmark-circle-outline"></ion-icon>
                                            <ion-icon name="warning-outline"></ion-icon>
                                        </div>
                                        <h3>Cập nhật thông tin thành công</h3>
                    
                                        <div class="buttons">
                                            <button data-id="30" onclick="confirmUpdateSuccess(this)"  class="btn">OK</button>
                                        </div>-->
                </div>
            </div> 


            <div class="wraper_content">
                <button onclick="redirectToServelet('./login', false)" class="return_login">Quay về trang đăng nhập</button>

                <h3>Chúng tôi đã gửi mã xác minh đến email của bạn</h3>
                <!--<form action="verifycode" method="post">-->
                <div id="verify">
                    <div class="inputOTP">
                        <input onclick="clearLable()"  type="text" name="authcode" id="otpInput">
                        <button onclick="reSendEmail()" class="re_send_btn" type="button"> Re-send </button><!-- comment -->
                        <label id="notify" style="color: red; font-size: 30px">${requestScope.baoLoi}</label>

                    </div>




                </div>
                <input  onclick="verifyOTP()"  type="submit" value="Verify" class="verify_btn"><br>

                <!--</form>-->
            </div>
        </div>
    </body>

    <script src="./js/verifyResigter.js"></script>
    <script src="./js/pop_up_animation.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>


    <script>

                    var button = document.querySelector('.re_send_btn');
                    var countdown;
                    var remainingTime = 60;
//                var authcodeSession = "${sessionScope.authcode}";

                    function clearLable()
                    {
                        var label = document.getElementById("notify");
                        label.textContent = "";
                    }

                    window.onload = function () {

                        disableButton();
                    };
                    function disableButton() {
                        button.disabled = true;
                        countdown = setInterval(updateCountdown, 1000);
                    }

                    function reSendEmail() {
                        redirectToServelet("./reSendSiginController", true);

                    }
                    function updateCountdown() {

                        button.innerText = "Chờ đợi (" + remainingTime + " giây)";
                        remainingTime--;
                        if (remainingTime === -2) {
                            clearInterval(countdown);
                            button.disabled = false;
                            button.innerText = "Re-send";
                            remainingTime = 60;
                        }

                    }




                    function verifyOTP() {
                        console.log('click verify');


                        var otpInput = document.getElementById('otpInput').value;
                        $.ajax({
                            url: "./verifycode",
                            type: "get",
                            data: {
                                otpInput: otpInput
                            },
                            success: function (data) {
                                var output = document.getElementById('verify');

                                var notifyContainer = document.getElementById("notification");
                                var notifyContent = document.querySelector(".notificationBox");

                                notifyContainer.style.display = "flex";
                                notifyContent.innerHTML = data.Notify;

                                animationFade_Down(notifyContent);

//                            if (data.type === "wrongOTP")
//                            {
//                                output.innerHTML = data.authcodeInput + "<br>" + data.notifyLabel;
//                            } else if (data.type === "success")
//                            {
//                                output.innerHTML = data.authcodeInput
//                                        + data.notifyLabel + data.notificationBox + data.blur;
//
//                            } else
//                            {
//                                output.innerHTML = data.authcodeInput
//                                        + data.notifyLabel + data.notificationBox + data.blur;
//                            }



                            },
                            error: function (xhr) {

                            }
                        });
                    }
                    ;

                    var closePopup = function ()
                    {
                        var notifyContainer = document.getElementById("notification");
                        var notifyContent = document.querySelector(".notificationBox");
                        animationFade_Up(notifyContainer, notifyContent);
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
