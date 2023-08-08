

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation2.css">
        <link rel="stylesheet" type="text/css" href="./css/addProductStyle.css"/>
        <link rel="stylesheet" type="text/css" href="./css/animationStyle.css"/>


        <title>Quản lý người dùng </title>
    </head>
    <body>
        <div class="container">

            <div id="notification2" class="notification2">
                <!--<h3>Không có dữ liệu mới để Update</h3>-->
            </div> 


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






            <div class="header">
                <div class="header-left">
                    <div class="btn-menu">
                        <ion-icon name="menu-outline"></ion-icon>

                    </div>
                    <img class="logo"  onclick="redirectToServelet('./homeController', false)" src="./image/new_logo_napthenay.png" alt="alt"/>

                    <!--<img class="logo" onclick="redirectToServelet('./homeController', true)" src="./image/new_logo_napthenay.png" alt="alt"/>-->
                </div>

                <div class="menu_left">
                    <div class="top_menu">
                        <img class="logo"  onclick="redirectToServelet('./homeController', false)" src="./image/new_logo_napthenay_black.png" alt="alt"/>
                    </div>
                    <ul>
                        <!--  <li onclick="window.location.href = './profile.jsp'">Thông tin tài khoản</li>
                              <li onclick="redirectToServelet('./changePassword', false)">Đổi mật khẩu</li> -->

                    </ul>


                    <div class="menu_blur"></div>
                </div>

                <div class="header-right">
                    <div class="balance ">Số dư: <span>${sessionScope.balance}</span> </div>
                    <div class="profile" ">
                        <!--onclick="window.location.href = 'profile.jsp'-->

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
            </div>






            <div class="beforeBanner"></div>
            <div id="carouselExampleControls" class="carousel slide banner " data-ride="carousel">

            </div>


            <!--==============List product display===========-->
            <div class="content">
                <h3> Thêm sản phẩm </h3>
                <div class="wraper">
                    <div class="items"> 
                        <label>Tên sản phẩm: </label>
                        <input title=""  class="" id="nameProduct" value="">
                    </div>
                    <div class="items">
                        <label>Nhà mạng: </label>
                        <div class="items_categoty_select">
                            <select id="select_category_detail" class="category_select">"
                            </select>
                        </div>
                    </div>
                    <div class="items">
                        <label>Nhà mạng khác: </label>
                        <input title="" class="" id="category_other" value="">
                    </div>
                    <div class="items"> 
                        <label>Đơn giá:</label>
                        <input  title="" class="" id="price" value="">
                    </div>
                    <div class="items position_top"> 
                        <label class="">Mô tả:</label>
                        <textarea  id="text_des" rows="4">
                                                                               
                        </textarea>
                    </div>

                    <div class="items"> 
                        <label>Chọn ảnh:</label>\
                        <input id="avatar"  type="file" onchange="displayImage(event)" accept="image/*" name="avatar">
                    </div>
                    <div class="items previewImage" > 
                        <label></label>
                        <img id="preview" src="#" alt="Ảnh xem trước">
                    </div>
                    <div class="group_button">
                        <button class="ok_btn btn_2" onclick="addNewProduct()">Add</button>
                    </div>
                </div>

            </div>







            <!--            <div class="fotter">
                        </div>-->
           <footer>
                <div class="footter_container">
                    <p>&copy; 2023 - phone card system</p>
                    <ul>
                        <li><a href="#">Dịch vụ</a></li>
                        <li><a href="#">Chăm sóc khách hàng</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
            </footer>

        </div>




        <script src="./js/home1.js"></script>
        <script src="./js/pop_up_animation.js"></script>
        <script src="./js/addProduct.js"></script>

        <!--<script src="./js/manageProduct.js"></script>-->

        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>


        <script>
                            function redirectToServelet(servletURL, post) {
                                var form = document.createElement('form');
                                form.method = post ? 'POST' : 'GET';
                                form.action = servletURL;
                                document.body.appendChild(form);
                                form.submit();
                            }


        </script>
</html>
