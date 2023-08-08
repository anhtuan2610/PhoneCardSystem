<%-- 
    Document   : profile.jsp
    Created on : May 21, 2023, 8:48:39 PM
    Author     : phamtung
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Product"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/homeStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/homePrivateStyle.css">
        <link rel="stylesheet" type="text/css" href="./css/notifycation2.css">
        <link rel="stylesheet" type="text/css" href="./css/maincontentHomeStyle.css">




        <title>Home</title>
    </head>
    <body>
        <div class="container">

            <!--Thông tin chi tiết sản phẩm-->
            <div class="viewProductDetail_container">
                <!--                                <div class="viewProduct_blur"></div>
                                
                                                <div class="viewProductDetail">
                                
                                                    <h3>Chi tiết sản phẩm</h3>
                                                    <div class ="contentview">
                                                        <div class="items">
                                                            <img src="image/Viettel.png" alt="" style="width:200px;height:80px"/>
                                                        </div>
                                
                                                        <div class="items"> 
                                                            <label>Sản phẩm:</label>
                                                            <input class="readOnly" type="text" name="nameProduct" value="the viettel 50k" readonly="">
                                                        </div>
                                                        <div class="items"> 
                                                            <label>Thể loại:</label>
                                                            <input class="readOnly" type="text" name="nameProduct" value="the viettel 50k" readonly="">
                                                        </div>
                                                        <div class="items"> 
                                                            <label>Giá:</label>
                                                            <input class="readOnly" type="text" name="price" value="the viettel 50k" readonly="">
                                                        </div>
                                                        <div class="items"> 
                                                            <label>Số lượng:</label>
                                                            <input class="readOnly" type="text" name="price" value="the viettel 50k" readonly="">
                                                        </div>
                                
                                                        <div class="items"> 
                                                            <label>Mô tả:</label>
                                                            <p>30 ngày sử dụng dịch vụ lấy thông tin biến động số dư ngân hàng vietcombank. <br>
                                                                Giới hạn gọi api: 5 request / minute</p>
                                                        </div>
                                                    </div>
                                                    <div class="groupButton">
                                                        <button class="btn close-btn" onclick="closeProductDetail()">Hủy</button>
                                                        <button onclick="buyProductOnList(this)" 
                                                                data-productId="${o.getId()}"  
                                                                data-name="${o.getName()}" 
                                                                data-quantity="${o.getQuantity()}" 
                                                                data-price="${o.getPrice()}" 
                                                                class=" buy_btn btn "> Mua </button>
                                                    </div>
                                                </div>-->
            </div>



            <!--Thông báo-->
            <div id="notification" class="notification">
                <!--                <div class="notify_blur"></div>
                                <div class="notificationBox">
                                    <div class="icon"> 
                                        <ion-icon name="checkmark-circle-outline"></ion-icon>
                                    </div>
                                    <h3>Họ và tên không được vượt quá 50 ký tự.</h3>
                
                                    <div class="buttons">
                                        <button onclick="confirmNotifyOK()"  class="btn">OK</button>
                                    </div>
                                </div>-->
            </div>   


            <!--Mua hang-->

            <div class="buyProduct_wrapper" >
                <!--                                <div class="productView">
                                                    <h3>Thông tin sản phẩm</h3>
                                                    <div class="contentProduct">
                                
                                                        <div class="item ">
                                                            <label>Sản phẩm: </label>
                                                            <input  class="readOnly" type="text" name="nameProduct" value="the viettel 50k" readonly="">
                                                        </div>
                                                        <div class="item ">
                                                            <label>Giá: </label>
                                                            <input class="readOnly" type="text" name="price" value="the viettel 50k" readonly="">
                                                        </div>
                                                        <div class="item">
                                                            <label>Số lượng: </label>
                                                            <input  type="number" name="price" value="the viettel 50k" >
                                                        </div>
                                                    </div>
                                
                                
                                                    <div class="groupButton">
                                                        <button class="btn buy-btn" onclick="submitOk()">Mua</button>
                                                        <button class="btn close-btn" onclick="submitOk()">Hủy</button>
                                                    </div>
                                
                                                </div>
                                                <div class="productView_blur"></div>-->
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



            <div class="main_content">
                <div class="display_productList"> 
                    <div class="top_content">
                        <div class="wraper_topContent">
                            <h3>Sản phẩm</h3>
                            <!--<p >Website bán thẻ cào điện thoại uy tín và chất lượng</p>-->
                        </div>
                    </div>

                    <div class="body_content">
                        <div class="wraper_bodyContent">
                            <div class="filter_container"> 

                                <div class="dropdown_container">

                                    <select id="select_filter_price" class="custom_select">
                                        <!--                                    <option value="price" style="color: black;">Giá</option>
                                                                                <option value="10000" style="color: black;">10,000đ</option>
                                                                                <option value="20000" style="color: black;">20,000đ</option>
                                                                                <option value="50000" style="color: black;">50,000đ</option>
                                                                                <option value="100000" style="color: black;">100,000đ</option>
                                                                                <option value="200000" style="color: black;">200,000đ</option>
                                                                                <option value="500000" style="color: black;">500,000đ</option>-->
                                    </select>

                                </div>
                                <div class="dropdown_container">

                                    <select id="select_filter_category" class="custom_select">
                                        <!--                                        <option value="category" style="color: black;">Nhà phát hành</option>
                                                                                <option value="Viettel" style="color: black;">Viettel</option>
                                                                                <option value="Vinaphone" style="color: black;">Vinaphone</option>
                                                                                <option value="Mobifone" style="color: black;">Mobifone</option>
                                                                                <option value="Vietnamobile" style="color: black;">Vietnamobile</option>
                                                                                <option value="G-mobile" style="color: black;">G-mobile</option>
                                                                                <option value="Reddi" style="color: black;">Reddi</option>-->
                                    </select>

                                </div>

                                <div class="input_search">
                                    <input id="search_product_name" type="text" class="form-control" placeholder="Tìm kiếm theo tên sản phẩm" ">
                                </div>
  
                            </div>       
                            <div class="product_listItem">
                                <%--<c:forEach items="${data}" var="o">--%>
                                <!--                                <div class="wraper_productItem">
                                                                    <div class="productItem">
                                                                        <div class="product_logo">
                                                                            <img src="image/${o.getImage()}" />
                                
                                                                        </div>
                                                                        <div class="productItem_info">
                                                                            <h5 >${o.getName()}</h5>
                                
                                                                            <h5 >${o.getCategory()}</h5>
                                                                            <h5  class="formatDouble">Giá: <span>${o.getPrice()}</span></h5>
                                                                            <h5 >Số lượng: ${o.getQuantity()}</h5>
                                
                                                                        </div>
                                                                    </div>
                                
                                                                    <div class="group_buttons">
                                                                        <button onclick="viewProductDetail()" 
                                                                                data-productId="${o.getId()}"  
                                                                                data-name="${o.getName()}" 
                                                                                data-quantity="${o.getQuantity()}" 
                                                                                data-price="${o.getPrice()}"
                                                                                data-des="" data-category="" class=" view_btn btn ">Thông tin</button>
                                
                                                                        <button onclick="buyProductOnList(this)" 
                                                                                data-productId="${o.getId()}"  
                                                                                data-name="${o.getName()}" 
                                                                                data-quantity="${o.getQuantity()}" 
                                                                                data-price="${o.getPrice()}" 
                                                                                class=" buy_btn btn "> Mua </button>
                                
                                                                    </div>
                                
                                                                </div>-->
                                <%--</c:forEach>--%>

                            </div>
                            <div id="pagination" class="pagination">
<!--                                                                    <div class="btn_pagination pre_btn">
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
                    <h1 id="testCuocDoi" style="display: none"></h1>

                </div>
            </footer>
            

        </div>

        <script src="./js/home.js"></script>
        <script src="./js/home1.js"></script>
        <script src="./js/pagination.js"></script>
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


        </script>
    </body>
</html>
