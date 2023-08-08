

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/homePrivateStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/historyStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/animationStyle.css">

        <title>History</title>
    </head>

    <body>
        <div class="container">

            <div class="listStorageContainer" >
                <div class="blur"></div>

                <div class="listStorage">
                    <button onclick="submitOk()" class="close-btn">X</button>
                    <div  class="listStorage_content"> 
                        <!--                        <h3>Chi tiết đơn hàng</h3>
                                                <div class="items"> 
                                                    <label>Mã số đơn hàng</label>
                                                    <p>129</p>
                                                </div>
                                                <div class="items"> 
                                                    <label>Sản phẩm:</label>
                                                    <p>the viettel 20k</p>
                                                </div>
                                                <div class="items"> 
                                                    <label>Số lượng:</label>
                                                    <p>30</p>
                                                </div>
                                                <div class="items"> 
                                                    <label>Đơn giá</label>
                                                    <p>10000</p>
                                                </div>
                                                <div class="items"> 
                                                    <label>Thành tiền:</label>
                                                    <p>300000</p>
                                                </div>
                                                <div class="items"> 
                                                    <label>Trạng thái đơn:</label>
                                                    <p>Giao dịch thành công</p>
                                                </div>
                                                <h4>Thông tin thẻ đã mua</h4>
                                                <table border=" 1px solid">
                                                    
                        
                                                </table>
                                                <button onclick="submitOk()">OK</button>-->
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
                    <div class="balance">Số dư: <span class="balance_value">${sessionScope.balance}</span> </div>
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
                        <!--<li onclick="window.location.href = './login.jsp'">1</li>-->
                    </ul>
                    <div class="menu_blur"></div>
                </div>
            </div>
            <!--<h3 class="test_session" style="padding-top: 300px;font-size: 40px; color: red">Lịch sử mua hàng </h3>-->


            <div class="content">
                <h3> Lịch sử mua hàng </h3>

                <div class="list_wraper">
                    <div class="filter">
                        <div class="dropdown_container">
                            <select id="filterPrice" class="custom_select">
                                <option selected="" value="price" style="color: black;">Loại thẻ</option>
                                <option value="10000" style="color: black;">10,000đ</option>
                                <option value="20000" style="color: black;">20,000đ</option>
                                <option value="50000" style="color: black;">50,000đ</option>
                                <option value="100000" style="color: black;">100,000đ</option>
                                <option value="200000" style="color: black;">200,000đ</option>
                                <option value="500000" style="color: black;">500,000đ</option>
                            </select>
                        </div>

                        <!--                        <div class="dropdown_container">
                        
                                                    <select id="filterCategory" class="custom_select">
                                                        <option selected="" value="category" style="color: black;">Nhà phát hành</option>
                                                        <option value="Viettel" style="color: black;">Viettel</option>
                                                        <option value="Vinaphone" style="color: black;">Vinaphone</option>
                                                        <option value="Mobifone" style="color: black;">Mobifone</option>
                                                        <option value="Vietnamobile" style="color: black;">Vietnamobile</option>
                                                        <option value="G-mobile" style="color: black;">G-mobile</option>
                                                        <option value="Reddi" style="color: black;">Reddi</option>
                                                    </select>
                        
                                                </div>-->

                        <div class="dropdown_container">
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
                                <option selected="" value="statusOrder" style="color: black;">Trạng thái đơn hàng</option>
                                <option value="1" style="color: black;">Giao dịch thành công</option>
                                <option value="2" style="color: black;">Đang xử lý</option>
                                <option value="0" style="color: black;">Giao dịch thất bại</option>
                            </select>
                        </div>
                    </div>

                    <div class="list_content">

                        <div class="title">
                            <div class="title_item">
                                <p>Mã đơn hàng</p>
                            </div>
                            <div class="title_item">
                                <p>Sản phẩm</p>
                            </div>
                            <div class="title_item">
                                <p>Đơn giá</p>
                            </div>
                            <div class="title_item">
                                <p>Số lượng</p>
                            </div>
                            <div class="title_item">
                                <p>Thành tiền</p>

                            </div>
                            <div class="title_item">
                                <p>Thời gian tạo đơn </p>

                            </div>
                            <div class="title_item">
                                <p>Trạng thái</p>

                            </div>
                            <div class="title_item">
                                <p>Hành động</p>
                            </div>
                        </div>
                        <div class="list_container">
                            <div class="list_items">

<!--                                <div data-orderId="${o.id}" class="item">
                                    <div class="item_attribute">
                                        <p>${o.id}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p>${o.product.getName()}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p class="formatDouble">${o.price}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p >${o.quantity}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p class="formatDouble">${o.totalPrice}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p class="createdAt">${o.createdAt}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <p class="statusOrder">${o.status}</p>
                                    </div>
                                    <div class="item_attribute">
                                        <button class="button_view" onclick="viewDetail(this)" data-id="${o.id}">Chi tiết</button>
                                    </div>
                                </div>-->

                            </div>
                        </div>

                    </div>

                    <div id="pagination" class="pagination">
<!--                                                <div class="btn_pagination pre_btn">
                                                    <button data-number="-1">Trước</button>
                                                </div>
                        
                                                <div class="page_number">
                                                    <span>Trang </span>
                                                    <input type="number" value="1">
                                                    <span>/ <p>1</p></span>
                        
                                                </div>
                        
                                                <div class="dropdown_container">
                        
                                                    <select id="select_pagination" class="pagination_drop">
                                                        <option selected="" style="color: black;" value="5">5 bản ghi</option>
                                                        <option value="10" style="color: black;">10 bản ghi</option>
                                                        <option value="20" style="color: black;">20 bản ghi</option>
                                                        <option value="50" style="color: black;">50 bản ghi</option>
                                                        <option value="100" style="color: black;">100 bản ghi</option>
                                                    </select>
                        
                                                </div>
                                                <div class="btn_pagination next_btn">
                                                    <button disabled="" data-number="1">Sau</button>
                                                </div>-->
                    </div>

                </div>





            </div>
             <footer>
                <div class="footter_container">
                    <p>&copy; 2023 - phone card system</p>
                    <ul>
                        <li><a href="#">Dịch vụ</a></li>
                        <li><a href="#">Chăm sóc khách hàng</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                    <h3 id="test_1" style="display: none"></h3>
                </div>
            </footer>
        </div>

        <script src="./js/home1.js"></script>
        <script src="./js/history.js"></script>
        <script src="./js/pop_up_animation.js"></script>
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



                            var javascriptCode = "<%= session.getAttribute("javascriptCode") %>";
                            if (javascriptCode) {
                                eval(javascriptCode); // Thực thi mã JavaScript từ servlet
                            }









        </script>
    </body>
</html>
