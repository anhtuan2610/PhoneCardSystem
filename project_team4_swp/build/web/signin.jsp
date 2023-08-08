<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Đăng ký</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
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
    </head>
    <body>
        <%
                String tenDangNhap= request.getAttribute("tenDangNhap")+"";	
                tenDangNhap = (tenDangNhap.equals("null"))?"":tenDangNhap;
		
                String hoVaTen= request.getAttribute("hoVaTen")+"";
                hoVaTen = (hoVaTen.equals("null"))?"":hoVaTen;
		
                String dienThoai= request.getAttribute("dienThoai")+"";
                dienThoai = (dienThoai.equals("null"))?"":dienThoai;
		
                String email= request.getAttribute("email")+"";
                email = (email.equals("null"))?"":email;
		
                String dongYNhanMail= request.getAttribute("dongYNhanMail")+"";
                dongYNhanMail = (dongYNhanMail.equals("null"))?"":dongYNhanMail;
        %>
        <div class="container">
            <img class="d-block mx-auto mb-4" src="./image/logoLogin.jpg" alt="" width="160" height="100"/>
            <div class="text-center">
                <h1>ĐĂNG KÝ TÀI KHOẢN</h1>
            </div>
            <form class="form" action="./dangky" method="post">
                <div class="row">
                    <div class="col-sm-6">
                        <h3>Tài khoản</h3>
                        <div class="mb-3">
                            <label for="tenDangNhap" class="form-label">Tên đăng nhập<span
                                    class="red">*</span></label> <input type="text" class="form-control"
                                                                id="tenDangNhap" name="tenDangNhap" required="required" value="<%=tenDangNhap%>">
                        </div>
                        <div class="mb-3">
                            <label for="matKhau" class="form-label">Mật khẩu<span
                                    class="red">*</span></label> <input type="password" class="form-control"
                                                                id="matKhau" name="matKhau" matKhau" name="matrequired="required" onkeyup="kiemTraMatKhau()">
                        </div>
                        <div class="mb-3">
                            <label for="matKhauNhapLai" class="form-label" >Nhập lại mật khẩu
                                <span class="red">*</span> <span id="msg" class="red" ></span>
                            </label> <input type="password" class="form-control" id="matKhauNhapLai"
                                            name="matKhauNhapLai" required="required" onkeyup="kiemTraMatKhau()">
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <h3>Thông tin khách hàng</h3>
                        <div class="mb-3">
                            <label for="hoVaTen" class="form-label">Họ và tên</label> <input
                                type="text" class="form-control" id="hoVaTen" name="hoVaTen" value="<%=hoVaTen%>">
                        </div>
                        <div class="mb-3">
                            <label for="dienThoai" class="form-label">Điện thoại</label> <input
                                type="tel" class="form-control" id="dienThoai" name="dienThoai" value="<%=dienThoai%>">
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label> <input
                                type="email" class="form-control" id="email" name="email"  value="<%=email%>">
                        </div>
                        <div class="form-floating"> <!<!-- tao. captcha -->
                            <div class="captcha">

                                <div class="captcha_container">
                                    <!-- Hiển thị hình ảnh CAPTCHA -->
                                    <img src="./createCaptcha" alt="CAPTCHA Image">
                                    <!-- Nút đổi CAPTCHA -->
                                    <button type="button" class="captchaReload" onclick="refreshCaptcha()">
                                        <ion-icon name="repeat-outline"></ion-icon>
                                    </button>
                                    <!-- Ô nhập CAPTCHA -->
                                    <input type="text" name="captchaInput">

                                </div>
                                <lable>${requestScope.baoLoi}</lable>
                            </div>
                        </div>
                        <hr />
                        <input class="btn btn-primary form-control" type="submit"
                               value="Đăng ký" name="submit" id="submit"/>
                    </div>
                </div>
            </form>
        </div>
    </body>
    <script src="js/signin.js" type="text/javascript"></script>
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
    </script>
</html>