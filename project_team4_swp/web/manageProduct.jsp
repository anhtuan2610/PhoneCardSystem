

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


        <title>Quản lý sản phẩm </title>
    </head>
    <body>
        <div class="container">


            <div class="notifyAddStorageContainer">
                <div class="notifyStorage_blur"></div>

                <div  class="content_wraper">
<!--                    <button onclick="submitOk()" class="close_btn">X</button>

                    <h3 >Đã thêm thành công <span style= "color: green">3</span> thẻ</h3>
                    <table>
                        <tr>
                            <th>seri trungf lap </th>
                        </tr>
                        <tr>
                            <td>1</td>
                        </tr>

                    </table>

                    <button onclick="submitOk()" class="close_btn_OK">OK</button>-->

                </div>
            </div>

            <div id="notification2" class="notification2">
                <!--<h3>Không có dữ liệu mới để Update</h3>-->
            </div> 


            <div id="notification" class="notification">
                <div class="notify_blur"></div>
                <div class="notificationBox">
                    <!--                                        <div class="icon" style="color: yellow"> 
                                                                <ion-icon name="checkmark-circle-outline"></ion-icon>
                                                                <ion-icon name="warning-outline"></ion-icon>
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
                        <!--                                                <h3>Thêm thẻ từ Excel</h3>
                                                                        <div class="items"> 
                                                                            <label>Mã số sản phẩm</label>
                                                                            <input  class="readOnly" id="productId" readonly="" value="1">
                                                                        </div>
                                                                        <div class="items"> 
                                                                            <label>Tên sản phẩm: </label>
                                                                            <input title="" class="" id="nameProduct" readonly="" value="vitets">
                                                                        </div>
                                                                        
                                                                        <div class="items"> 
                                                                            <label>Số lượng:</label>
                                                                            <input class="readOnly" id="quantity" readonly="" value="98">
                                                                        </div>
                                                                        <div class="items choseFile"> 
                                                                            <label>Chọn file excel:</label>\
                                                                            <input id="avatar" type="file" onchange="displayImage(event)" accept="image/*" name="avatar">
                                                                        </div>
                                                                        <div class="group_button">
                                                                            <button class="ok_btn btn_1" onclick="submitOk()">Close</button>
                                                                            <button class="ok_btn btn_2" onclick="updateInfoProduct(this)">Update</button>
                                                                        </div>-->
                    </div>
                </div>
            </div>


            <!--Thêm danh sách thẻ từ excel-->
            <div class="box_container2" >
                <div class="blur"></div>

                <div class="box_wraper_content2 w_content_2">
                    <button onclick="closeAddStorage()" class="close-btn2">X</button>
                    <div  class="box_content2 "> 
                        <!--                        <h3>Thêm thẻ từ Excel</h3>
                                                <div class="items"> 
                                                    <label>Mã số sản phẩm</label>
                                                    <input  class="readOnly" id="productId" readonly="" value="1">
                                                </div>
                                                <div class="items"> 
                                                    <label>Tên sản phẩm: </label>
                                                    <input title="" class="" id="nameProduct" readonly="" value="vitets">
                                                </div>
                        
                                                <div class="items"> 
                                                    <label>Số lượng:</label>
                                                    <input class="readOnly" id="quantity" readonly="" value="98">
                                                </div>
                                                <div class="items choseFile"> 
                                                    <label>Chọn file excel:</label>\
                                                    <input id="fileExcel" type="file"  accept="excel/*" name="avatar">
                                                </div>
                                                <div class="group_button">
                                                    <button class="ok_btn btn_1" onclick="closeAddStorage()">Close</button>
                                                    <button data-id="" class="ok_btn btn_2" onclick="addStorageExcel(this)">Add</button>
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


            <!--==============List product display===========-->
            <div class="content">
                <h3> Quản lý sản phẩm </h3>

                <div class="list_wraper">
                    <div class="filter">

                        <div class="search_container">
                            <input id="checkBoxSearch" class="checkBox" type="checkbox">
                            <div class="search">
                                <input id="search_content" type="text" class="" placeholder="Tìm kiếm" >
                            </div>
                            <select id="filterSearch" class="search_selectDrop custom_select">
                                <option selected="" value="type" style="color: black;">Tìm theo</option>
                                <option value="id" style="color: black;">Id</option>
                                <option value="name" style="color: black;">Tên sản phẩm</option>
                            </select>
                        </div>


                        <div  class="dropdown_container">
                            <select id="select_filter_price" class="custom_select">
                            </select>
                        </div>

                        <div class="dropdown_container">
                            <select id="select_filter_category" class="custom_select">
                            </select>
                        </div>

                        <div class="dropdown_container">
                            <select id="filterisShow" class="custom_select">
                                <option selected="" value="isShow" style="color: black;">Trạng thái trên web</option>
                                <option value="1" style="color: black;">Đang bán</option>
                                <option value="0" style="color: black;">Chưa bán</option>
                            </select>
                        </div>
                        <div class="dropdown_container">
                            <select id="filterisDelete" class="custom_select">
                                <option selected="" value="isDelete" style="color: black;">Trạng thái sản phẩm</option>
                                <option value="1" style="color: black;">Đã xóa</option>
                                <option value="0" style="color: black;">Chưa xóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="list_content">





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
        <script src="./js/pop_up_animation.js"></script>
        <script src="./js/manageProduct.js"></script>
        <script src="./js/addStorage.js"></script>


        <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.4/xlsx.full.min.js"></script>


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
