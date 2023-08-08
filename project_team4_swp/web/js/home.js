var productListE = document.querySelector(".wraper_bodyContent .product_listItem");
var paginationE = document.getElementById('pagination');
var searchElement = document.getElementById("search_product_name");

//console.log(productListE);

var buyProductElement = document.querySelector(".buyProduct_wrapper");
var notificationE = document.getElementById('notification');
var balanceE = document.querySelector(".header-right .balance");

var viewProductDetailContaineElement = document.querySelector(".container .viewProductDetail_container");



// Xem chi tiết sản phẩm

function viewProductDetail(button)
{
    var name = button.getAttribute("data-name");
    var productId = button.getAttribute("data-productId");
    var quantity = button.getAttribute("data-quantity");
    var price = button.getAttribute("data-price");
    var intP = parseInt(price);
    var format = intP.toLocaleString() + "đ";
    var description = button.getAttribute("data-des");
    var category = button.getAttribute("data-category");
    var imgURL = button.getAttribute("data-img");

    var htmls = `  <div class="viewProduct_blur"></div>

                <div class="viewProductDetail">

                    <h3>Chi tiết sản phẩm</h3>
                    <div class ="contentview">
                        <div class="items">
                            <img src="image/${imgURL}" alt="" "/>
                        </div>

                        <div class="items"> 
                            <label>Sản phẩm:</label>
                            <input class="readOnly" type="text" name="nameProduct" value="${name}" readonly="">
                        </div>
                        <div class="items"> 
                            <label>Thể loại:</label>
                            <input class="readOnly" type="text" name="nameProduct" value="${category}" readonly="">
                        </div>
                        <div class="items"> 
                            <label>Giá:</label>
                            <input class="readOnly" type="text" name="price" value="${format}" readonly="">
                        </div>
                        <div class="items"> 
                            <label>Số lượng:</label>
                            <input class="readOnly" type="text" name="price" value="${quantity}" readonly="">
                        </div>

                        <div class="items"> 
                            <label>Mô tả:</label>
                            <p>${description}</p>
                        </div>
                    </div>
                    <div class="groupButton">
                        <button class="btn close-btn" onclick="closeProductDetail()">Hủy</button>
                        <button onclick="buyProductOnList(this)" 
                                data-productId="${productId}"  
                                data-name="${name}" 
                                data-quantity="${quantity}" 
                                data-price="${price}" 
                                class=" buy_btn btn "> Mua </button>
                    </div>
                </div>`;

    viewProductDetailContaineElement.innerHTML = htmls;
    viewProductDetailContaineElement.style.display = "flex";

    var viewProductDetailElement = document.querySelector(".viewProductDetail_container .viewProductDetail")
    animationFade_Down(viewProductDetailElement);
}

function closeProductDetail()
{
    var viewProductDetailElement = document.querySelector(".viewProductDetail_container .viewProductDetail")
    var blurE = document.querySelector(".viewProductDetail_container .viewProduct_blur");

    blurE.style.display = "none";

    animationFade_Up(viewProductDetailContaineElement, viewProductDetailElement);

    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCatagoryValue = document.getElementById('select_filter_category').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;

//        console.log("Number: " + pageNumber + " size: " + pageSize);
    loadProductFilter(pageSize, pageNumber, filterPriceValue, filterCatagoryValue)


}

// In ra pop-up mua hàng 
function buyProductOnList(button)
{
    var price = button.getAttribute("data-price");
    var name = button.getAttribute("data-name");
    var productId = button.getAttribute("data-productId");
    var quantity = button.getAttribute("data-quantity");
    var intP = parseInt(price);
    var format = intP.toLocaleString() + "đ";


    $.ajax({
        url: "./orderController",
        type: "get",
        data: {
            name: name,
            price: format,
            quantity: quantity,
            productId: productId
        },
        success: function (data) {
            buyProductElement.innerHTML = data.htmls;
            buyProductElement.style.display = "flex";

            var productViewElement = document.querySelector(".buyProduct_wrapper .productView");

            animationFade_Down(productViewElement);
        },
        error: function (xhr) {

        }
    });
}


// Nút close trong Pop-up mua sản phẩm
function closeBuyProduct() {

    var productViewElement = document.querySelector(".buyProduct_wrapper .productView");
    var productViewBlurElement = document.querySelector(".buyProduct_wrapper .productView_blur");

    productViewBlurElement.style.display = "none";

    animationFade_Up(buyProductElement, productViewElement);

    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCatagoryValue = document.getElementById('select_filter_category').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;

//        console.log("Number: " + pageNumber + " size: " + pageSize);
    loadProductFilter(pageSize, pageNumber, filterPriceValue, filterCatagoryValue)


}



// Thông báo mua hàng dưa ra thông báo khi nhập lỗi và điều hướng đến pop-up xác nhận mua hàng
function buyProductButton(button)
{
    var quantityElement = document.getElementById('quantityInput');
    var quantityInput = quantityElement.value;

    var productId = parseInt(button.getAttribute("data-productId"));
    var productQuantity = parseInt(button.getAttribute("data-quantity"));

//    console.log(productId + " quan: " + productQuantity);
//    console.log("quantity: " + quantityInput);

    var notification = "";

    if (parseInt(quantityInput) > productQuantity)
    {
        notification = "Số lượng sản phẩm chỉ còn " + productQuantity + " Vui lòng nhập lại";
    } else if (quantityInput === "")
    {
        notification = "Vui lòng nhập số lượng thẻ bạn muốn mua";

    } else if (parseInt(quantityInput) <= 0)
    {
        notification = "Nhập số lượng lớn hớn 0";
    } else {
//        console.log("else");
        htmls = `<div class="notify_blur"></div>
                <div class="notificationBox">
                    <div class="icon"> 
                       <ion-icon name="cart-outline"></ion-icon> 
                    </div>
                    <h3>Xác nhận mua hàng</h3>
                   
<div class="buttons">
 <button onclick="confirmNotifyBuy(this)"
                        data-productId="${productId}" data-productQuantity="${productQuantity}"
                    data-productQuantityInput="${quantityInput}"  class="btn">OK</button>
<button onclick="confirmNotifyOK()"  class="btn btn_close">Close</button>    </div>    
</div>`;
        notificationE.innerHTML = htmls;
        notificationE.style.display = "flex";

        var notificationContent = document.querySelector(".notification .notificationBox");
        animationFade_Down(notificationContent);
    }





    if (notification != "")
    {


        html = `<div class="notify_blur"></div>
                <div class="notificationBox">
                    <div class="icon_warning"> 
                    <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>${notification}</h3>
                    <button onclick="confirmNotifyOK()"  class="btn">OK</button>
                </div>`

        notificationE.innerHTML = html;

        notificationE.style.display = "flex";

        var notificationContent = document.querySelector(".notification .notificationBox");
        animationFade_Down(notificationContent);
    }

    console.log(notification);

}

function confirmNotifyOK() {

    var notificationContent = document.querySelector(".notification .notificationBox");
    var notificationBlur = document.querySelector(".notification .notify_blur");
    notificationBlur.style.display = "none";

    animationFade_Up(notificationE, notificationContent);
}

function confirmQuantityZero()
{
    var notificationContent = document.querySelector(".notification .notificationBox");
    var notificationBlur = document.querySelector(".notification .notify_blur");
    notificationBlur.style.display = "none";
    var productViewBlurElement = document.querySelector(".buyProduct_wrapper .productView_blur");

    productViewBlurElement.style.display = "none";
    buyProductElement.style.display = "none";

    animationFade_Up(notificationE, notificationContent);

    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCatagoryValue = document.getElementById('select_filter_category').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;

//        console.log("Number: " + pageNumber + " size: " + pageSize);
    loadProductFilter(pageSize, pageNumber, filterPriceValue, filterCatagoryValue)

}

// Pop-up xác nhận mua hàng 
function confirmNotifyBuy(button)
{
    var productId = button.getAttribute("data-productId");
    var productQuantityInput = button.getAttribute("data-productQuantityInput");
    var productQuantity = button.getAttribute("data-productQuantity");

//    console.log("id: " + productId + " Quantity: " + productQuantityInput)

    $.ajax({
        url: "./orderController",
        type: "post",

        data: {
            productId: productId,
            productQuantityInput: productQuantityInput
//            productQuantity: productQuantity

        },
        success: function (data) {
            notificationE.innerHTML = data.notify;
            notificationE.style.display = "flex";

            var notificationContent = document.querySelector(".notification .notificationBox");
            console.log(notificationContent);
            animationFade_Down(notificationContent);

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
}

// Mua hàng thành công 
function confirmNotifyBuySuccess()
{
//    notificationE.style.display = "none";
    buyProductElement.style.display = "none";
    viewProductDetailContaineElement.style.display = "none";

    var notificationBlur = document.querySelector(".notification .notify_blur");
    notificationBlur.style.display = "none";

    var notificationContent = document.querySelector(".notification .notificationBox");

    animationFade_Up(notificationE, notificationContent);


//    console.log(balanceE);
    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCatagoryValue = document.getElementById('select_filter_category').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;

    loadProductFilter(pageSize, pageNumber, filterPriceValue, filterCatagoryValue)



//    console.log(pageSize + " Number " + pageNumber);
//    console.log(filterPriceValue + " Cata " + filterCatagoryValue);
//
//    $.ajax({
//        url: "./listproduct",
//        type: "post",
//        data: {
//            pageSize: pageSize,
//            pageNumber: pageNumber,
//            filterPriceValue: filterPriceValue,
//            filterCatagoryValue: filterCatagoryValue
//        },
//        success: function (data) {
//
//            productListE.innerHTML = data.html;
//
//            paginationE.innerHTML = data.htmlPaging;
//
//            balanceE.innerHTML = data.htmlBalance;
//
//            document.getElementById('testCuocDoi').innerHTML = data.pageSizeString;
//
//            var formatDoubleElements = document.querySelectorAll(".formatDouble span");
//
//
//            formatDoubleElements.forEach(function (i) {
//                var intP = parseInt(i.textContent);
//                var format = intP.toLocaleString();
//                i.textContent = format + "đ";
//            });
//
//            var balanceValue = parseInt(document.querySelector(".header-right .balance span").textContent);
//
//            var balanceFormat = balanceValue.toLocaleString();
//
//            document.querySelector(".header-right .balance span").textContent = balanceFormat + "đ";
//
//
//
//
//            buttonPagination('./listproduct', 'post', productListE, paginationE);
//            selectdropPagination('./listproduct', 'post', productListE, paginationE);
//            inputPagination('./listproduct', 'post', productListE, paginationE);
//
//
//        },
//        error: function (xhr) {
//            console.log('Lỗi khi gửi yêu cầu AJAX');
//        }
//    });

}


var loadListFilter = function () {
    var listPriceFilterE = document.getElementById("select_filter_price");
    var listCategoryFilterE = document.getElementById("select_filter_category");

    $.ajax({
        url: './getListFilter', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {
            listPriceFilterE.innerHTML = data.listPrice;
            listCategoryFilterE.innerHTML = data.listCategory;

            var listPrice = document.querySelectorAll("#select_filter_price option");

            listPrice.forEach(function (item) {
                var itemValue = item.textContent;
                var formatValue = parseInt(itemValue).toLocaleString() + "đ";

                if (formatValue == "NaNđ") {
                    formatValue = "Giá";
                }
                item.textContent = formatValue;

            });



        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - List Price Product');
        }
    });
}


// Get prodcutlist từ doGet homeController  cùng thao tác phân trang
var loadProductList = function () {
    $.ajax({
        url: './listproduct', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {

            productListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.pagination;
//            document.getElementById('testCuocDoi').innerHTML = data.pagination;

            var formatDoubleElements = document.querySelectorAll(".formatDouble span");


            formatDoubleElements.forEach(function (i) {
                var intP = parseInt(i.textContent);
                var format = intP.toLocaleString();
                i.textContent = format + "đ";
            });
            buttonPagination('./listproduct', 'post', productListE, paginationE);
            selectdropPagination('./listproduct', 'post', productListE, paginationE);
            inputPagination('./listproduct', 'post', productListE, paginationE);

        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
}


window.onload = function () {
    loadListFilter();
    loadProductList();
};



// Xử lý filter và phân trang 
var customSelectElement = document.querySelectorAll(".custom_select");

//console.log(customSelectElement);

customSelectElement.forEach(function (item) {
    item.onchange = function () {

        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCatagoryValue = document.getElementById('select_filter_category').value;
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var searchContent = searchElement.value;
//        console.log("Number: " + pageNumber + " size: " + pageSize);
        loadProductFilter(pageSize, pageNumber, filterPriceValue, filterCatagoryValue, searchContent)

//        console.log(filterPriceValue + " " + filterCatagoryValue);
    };

});

// Load  category filter khi mở xem chi tiết sản phẩm 
var loadListPagesizeFilter = function (pagesizeSelected) {
    var loadListPagesizeFilterE = document.getElementById("select_pagination");


    // Lặp qua các option để tìm option có giá trị trùng khớp
    for (var i = 0; i < loadListPagesizeFilterE.options.length; i++) {
        if (loadListPagesizeFilterE.options[i].value == pagesizeSelected) {

            // Đánh dấu option này là đã chọn bằng cách set thuộc tính selected thành true
            loadListPagesizeFilterE.options[i].selected = true;
            break; // Kết thúc vòng lặp nếu đã tìm thấy option cần chọn
        }

    }
}


var loadProductFilter = function (pageSize, pageNumber, filterPriceValue, filterCatagoryValue, searchContent)
{

    $.ajax({
        url: "./listproduct",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterPriceValue: filterPriceValue,
            filterCatagoryValue: filterCatagoryValue,
            searchContent: searchContent
        },
        success: function (data) {

            productListE.innerHTML = data.html;

            paginationE.innerHTML = data.htmlPaging;


            document.getElementById('testCuocDoi').innerHTML = data.pageSizeString;

            var formatDoubleElements = document.querySelectorAll(".formatDouble span");


            formatDoubleElements.forEach(function (i) {
                var intP = parseInt(i.textContent);
                var format = intP.toLocaleString();
                i.textContent = format + "đ";
            });

            var pageSize = data.pagesize;

            loadListPagesizeFilter(pageSize);


            buttonPagination('./listproduct', 'post', productListE, paginationE);
            selectdropPagination('./listproduct', 'post', productListE, paginationE);
            inputPagination('./listproduct', 'post', productListE, paginationE);


        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
}

// Xử lý search Name Product


searchElement.addEventListener("input", function () {
    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCatagoryValue = document.getElementById('select_filter_category').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = 1;
    var searchContent = searchElement.value;

    $.ajax({
        url: "./listproduct",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterPriceValue: filterPriceValue,
            filterCatagoryValue: filterCatagoryValue,
            searchContent: searchContent
        },
        success: function (data) {

            productListE.innerHTML = data.html;

            paginationE.innerHTML = data.htmlPaging;



            var formatDoubleElements = document.querySelectorAll(".formatDouble span");


            formatDoubleElements.forEach(function (i) {
                var intP = parseInt(i.textContent);
                var format = intP.toLocaleString();
                i.textContent = format + "đ";
            });



            buttonPagination('./listproduct', 'post', productListE, paginationE);
            selectdropPagination('./listproduct', 'post', productListE, paginationE);
            inputPagination('./listproduct', 'post', productListE, paginationE);


        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
});