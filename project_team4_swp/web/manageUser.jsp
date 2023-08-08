

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation2.css">
        <link rel="stylesheet" type="text/css" href="./css/manageUserStyle.css"/>
        <link rel="stylesheet" type="text/css" href="./css/popupStyle.css"/>
        <link rel="stylesheet" type="text/css" href="./css/animationStyle.css"/>


        <title>Quản lý người dùng </title>
    </head>
    <body>
        <div class="container">


            <div id="notification" class="notification">
                                <div class="notify_blur"></div>
                                <div class="notificationBox">
<!--                                    <div class="icon"> 
                                        <ion-icon name="checkmark-circle-outline"></ion-icon>
                                    </div>
                                    <h3>Cập nhật thông tin thành công</h3>
                
                                    <div class="buttons">
                                        <button data-id="30" onclick="confirmUpdateSuccess(this)"  class="btn">OK</button>
                                    </div>-->
                                </div>
            </div> 


            <div class="box_container" >
                <div class="blur"></div>

                <div class="box_wraper_content">
                    <button onclick="submitOk()" class="close-btn">X</button>
                    <div  class="box_content"> 
                        <!--                        <h3>Thông tin người dùng</h3>
                                                <div class="items"> 
                                                    <label>Mã số tài khoản</label>
                                                    <input title="" class="readOnly" id="userId" readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Role:</label>
                                                    <input class="readOnly" id="role" readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Tài khoản:</label>
                                                    <input class="readOnly" id="account" readonly="" value="admin1">
                                                </div>
                        
                        
                                                <div class="items"> 
                                                    <label>Số dư:</label>
                                                    <div class="input_button">
                                                        <input class="readOnly input_button_content" readonly="" id="balance"  value="2323323">
                                                        <button data-id="" onclick="createTransaction(this)">Tạo giao dịch</button>
                                                    </div>
                                                </div>
                        
                                                <div class="items"> 
                                                    <label>Trạng thái:</label>
                                                    <div class="group_chose">
                                                        <input  type="radio"  name="group_Status" id="statusUser1" value="0">
                                                        <label for="statusUser1">Acctive</label>
                        
                                                        <input type="radio" checked="" name="group_Status" id="statusUser2" value="1">
                                                        <label for="statusUser2">Banned</label>
                                                    </div>
                        
                                                </div>
                                                <div class="items"> 
                                                    <label>Mật khẩu:</label>
                                                    <input class="" type="password" readonly="" id="password"  value="2323323">
                                                </div>
                                            </div>
                                            <div class="items"> 
                                                <label>Họ và Tên:</label>
                                                <input class="" id="name"  value="tung pham">
                                            </div>
                                            <div class="items"> 
                                                <label>Số điện thoại:</label>
                                                <input type="number" class="" id="phone"  value="0388282323">
                                            </div>
                                            <div class="items"> 
                                                <label>Email:</label>
                                                <input class="" id="email"  value="tung@gmail.com">
                                            </div>
                        
                                            <div class="items"> 
                                                <label>Ngày tạo:</label>
                                                <input class="readOnly" readonly="" id="createdAt"  value="202307172457">
                                            </div>
                        
                                            <div class="group_button">
                                                <button class="ok_btn btn_1" onclick="submitOk()">Close</button>
                                                <button class="ok_btn btn_2" onclick="updateInfo()">Update</button>
                                            </div>-->
                    </div>
                </div>
            </div>

            <div class="box_container2" >
                <div class="blur"></div>

                <div class="box_wraper_content2">
                    <button onclick="closeDetailActivity()" class="close-btn2">X</button>
                    <div  class="box_content2"> 
                        <!--                        <h3>Chi tiết hoạt động</h3>
                        
                                                <div class="items"> 
                                                    <label>Mã số:</label>
                                                    <input class="readOnly" i readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Loại:</label>
                                                    <input class="readOnly"  readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Người thực hiện:</label>
                                                    <input class="readOnly"  readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Ngày tạo:</label>
                                                    <input class="readOnly date_createdAt" readonly=""   value="202307172457">
                                                </div>
                                                <div class="items"> 
                                                    <label>Tên trước khi đổi:</label>
                                                    <input class="readOnly"  readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Email trước khi đổi:</label>
                                                    <input class="readOnly"  readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>SĐT trước khi đổi:</label>
                                                    <input class="readOnly"  readonly="" value="1">
                                                </div>
                                                <div class="group_button">
                                                    <button class="ok_btn btn_1" onclick="closeDetailActivity()">Close</button>
                                                </div>-->
                    </div>
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
            <!--=========== Hiển thị activity Log ==========-->
            <div class="content_activity">
                <div class="blur_activity"></div>

                <div class="list_wraper_activity">
                    <button data-id="" onclick="closeActivityList(this)" class="close-btn">X</button>
                    <div class="filter_activity">
                        <div class="dropdown_container_activity">
                            <select id="filterType" class="custom_select_activity">
                                <option selected="" value="statusUser" style="color: black;">Loại hành động</option>
                                <option value="Update profile" style="color: black;">Update profile</option>
                                <option value="Change password" style="color: black;">Change password</option>
                                <option value="Forget password" style="color: black;">Forget password</option>
                                <option value="Banned" style="color: black;">Banned</option>
                                <option value="Unbanned" style="color: black;">Unbanned</option>

                            </select>
                        </div>
                        <div  class="dropdown_container_activity">
                            <select id="filterDateActivityLog_activity" class="custom_select_activity">
                                <option selected="" value="alltime" style="color: black;">Mọi thời điểm</option>
                                <option value="7ngay" style="color: black;">7 ngày gần nhất</option>
                                <option value="30ngay" style="color: black;">30 ngày gần nhất</option>
                                <option value="3thang" style="color: black;">3 tháng gần nhất</option>
                                <option value="6thang" style="color: black;">6 tháng gần nhất</option>
                            </select>
                        </div>


                    </div>

                    <div class="list_content_activity">
                        <!--                        <div class="wraper_item_activity title">
                                                    <div class="item W_1_activity">
                                                        <p>Id</p>
                                                    </div>
                                                    <div class="item W_2_activity">
                                                        <p>Loại hành động</p>
                                                    </div>
                                                    <div class="item W_2_activity">
                                                        <p>Thời gian</p>
                                                    </div>
                                                    <div class="item W_2_activity">
                                                        <p>Hành động</p>
                                                    </div>
                                                </div>-->

                        <!--                        <div class="wraper_item_activity ">
                                                    <div class="item W_1_activity">
                                                        <p>Id</p>
                                                    </div>
                                                    <div class="item W_2_activity">
                                                        <p>Loại hành động</p>
                                                    </div>
                                                    <div class="item W_2_activity">
                                                        <p>Thời gian</p>
                                                    </div>
                        
                                                    <div class="item W_2_activity">
                                                        <button data-id="" onclick="viewActivityDetail(this)">Chi tiết</button>
                                                    </div>
                                                </div>-->

                    </div>

                    <div id="pagination_activity" class="pagination_activity">

                    </div>

                </div>

            </div>

            <!--==============List user display===========-->
            <div class="content">
                <h3> Quản lý người dùng </h3>

                <div class="list_wraper">
                    <div class="filter">

                        <div class="search_container">
                            <input id="checkBoxSearch" class="checkBox" type="checkbox">
                            <div class="search">
                                <input id="search_content" type="text" class="" placeholder="Tìm kiếm" >
                            </div>
                            <select id="filterSearch" class="search_selectDrop custom_select">
                                <option selected="" value="type" style="color: black;">Tìm kiếm</option>
                                <option value="id" style="color: black;">Id</option>
                                <option value="account" style="color: black;">Tài khoản</option>
                                <option value="phone" style="color: black;">Số ĐT</option>
                                <option value="email" style="color: black;">Email</option>
                            </select>
                        </div>


                        <div  class="dropdown_container">
                            <select id="filterDate" class="custom_select">
                                <option selected="" value="moinhat" style="color: black;">Mới nhất</option>
                                <option value="7ngay" style="color: black;">7 ngày gần nhất</option>
                                <option value="30ngay" style="color: black;">30 ngày gần nhất</option>
                                <option value="3thang" style="color: black;">3 tháng gần nhất</option>
                                <option value="6thang" style="color: black;">6 tháng gần nhất</option>
                            </select>
                        </div>

                        <div class="dropdown_container">
                            <select id="filterStatus" class="custom_select">
                                <option selected="" value="statusUser" style="color: black;">Trạng thái tài khoản</option>
                                <option value="0" style="color: black;">Hoạt động</option>
                                <option value="1" style="color: black;">Bị khóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="list_content">

                        <!--                        <div class="wraper_item title">
                                                    <div class="item W_1">
                                                        <p>Id</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Tên</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Số điện thoại</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Email</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Trạng thái</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Hành động</p>
                                                    </div>
                                                </div>-->

                        <!--                        <div class="wraper_item ">
                                                    <div class="item W_1">
                                                        <p>1</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>adimn</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Số điện thoại</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Email</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <p>Trạng thái</p>
                                                    </div>
                                                    <div class="item W_2">
                                                        <button data-id="" onclick="viewUserDetail(this)">Chi tiết</button>
                                                    </div>
                                                </div>-->

                    </div>

                    <div id="pagination" class="pagination">

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
        <!--<script src="./js/pagination.js"></script>-->
        <script src="./js/pop_up_animation.js"></script>
        <script src="./js/manageUser.js"></script>
        <script src="./js/activityLog.js"></script>

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
    </body>
</html>
