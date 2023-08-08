var productListE = document.querySelector(".list_wraper .list_content");
var paginationE = document.getElementById('pagination');
var searchContentProductE = document.getElementById('search_content');
var checkboxSearchE = document.getElementById("checkBoxSearch");
var containerUserDetailE = document.querySelector(".box_container");
var contentUserDetailE = document.querySelector(".box_container .box_wraper_content");
var productDeatilE = document.querySelector(".box_container .box_wraper_content .box_content");




var notificationContainer = document.getElementById('notification');
var notificationContent = document.querySelector("#notification .notificationBox")

var notification2Container = document.getElementById('notification2');

function formatDate(timestamp) {
    var year = timestamp.substring(0, 4);
    var month = timestamp.substring(4, 6);
    var day = timestamp.substring(6, 8);
    var hour = timestamp.substring(8, 10);
    var minute = timestamp.substring(10, 12);

    var formattedDate = hour + ":" + minute + " - " + day + " - " + month + " - " + year;

    return formattedDate;
}
var formatIsShow = function (elements)
{
    elements.forEach(function (item) {
        var intItem = parseInt(item.textContent);
        var format;

        if (intItem == 1) {
            format = "Showing";
            item.style.color = "green";
        } else if (intItem == 0) {
            format = "Hiding";
            item.style.color = "red";
        }

        item.textContent = format;
    });
}
var formatIsDelete = function (elements)
{
    elements.forEach(function (item) {
        var intItem = parseInt(item.textContent);
        var format;

        if (intItem == 1) {
            format = "Deleted";
            item.style.color = "red";
        } else if (intItem == 0) {
            format = "Undeleted";
            item.style.color = "green";
        }

        item.textContent = format;
    });
}
var loadListFilter = function () {
    var listPriceFilterE = document.getElementById("select_filter_price");
    var listCategoryFilterE = document.getElementById("select_filter_category");

    $.ajax({
        url: './getListFilter', // Đường dẫn đến controller
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
var loadProductListAdmin = function () {
    $.ajax({
        url: './listProductAdmin', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {

            productListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.pagination;
//            document.getElementById('testCuocDoi').innerHTML = data.pagination;

            var formatDoubleElements = document.querySelectorAll(".item .moneyFormat span");


            formatDoubleElements.forEach(function (i) {
                var intP = parseInt(i.textContent);
                var format = intP.toLocaleString();
                i.textContent = format;
            });
            var formatStatusIsShowE = document.querySelectorAll(".item .statusIsShow");
            var formatStatusIsDeleteE = document.querySelectorAll(".item .statusIsDelete");

            formatIsShow(formatStatusIsShowE);
            formatIsDelete(formatStatusIsDeleteE);


            buttonPagination('./listProductAdmin', 'post', productListE, paginationE);
            selectdropPagination('./listProductAdmin', 'post', productListE, paginationE);
            inputPagination('./listProductAdmin', 'post', productListE, paginationE);

        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
}
window.onload = function () {
    loadListFilter();
    loadProductListAdmin();
}

// ========= Xử lý phân trang và filter =================

// Phân trang 



// 2 Button
function buttonPagination(url, method, listElement, pagingElement) {
    var buttonPree = document.querySelectorAll(".pagination .btn_pagination button");

    buttonPree.forEach(function (button) {

        button.onclick = function () {

            var pageSize = document.getElementById('select_pagination').value;
            var pageNumberCurrent = document.querySelector(".page_number input").value;
            var pageNumber = parseInt(button.getAttribute('data-number')) + parseInt(pageNumberCurrent);
            var filterPriceValue = document.getElementById('select_filter_price').value;
            var filterCategoryValue = document.getElementById('select_filter_category').value;
            var filterIsShowValue = document.getElementById('filterisShow').value;
            var filterIsDeleteValue = document.getElementById('filterisDelete').value;
            var filterSearchValue = document.getElementById('filterSearch').value;
            var searchContent = searchContentProductE.value;
            var searchCheckbox = "";

            console.log(searchContent);

            if (checkboxSearchE.checked) {
                searchCheckbox = "checked";
            }

//            console.log(pageNumber + " size: " + pageSize);
//            console.log(filterPriceValue + " :::: " + filterCatagoryValue);


            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterPriceValue: filterPriceValue,
                    filterIsShowValue: filterIsShowValue,
                    filterIsDeleteValue: filterIsDeleteValue,
                    filterCategoryValue: filterCategoryValue,
                    filterSearchValue: filterSearchValue,
                    searchContent: searchContent,
                    searchCheckbox: searchCheckbox
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;

                    pagingElement.innerHTML = data.htmlPaging;

                    var formatDoubleElements = document.querySelectorAll(".item .moneyFormat span");


                    formatDoubleElements.forEach(function (i) {
                        var intP = parseInt(i.textContent);
                        var format = intP.toLocaleString();
                        i.textContent = format;
                    });
                    var formatStatusIsShowE = document.querySelectorAll(".item .statusIsShow");
                    var formatStatusIsDeleteE = document.querySelectorAll(".item .statusIsDelete");

                    formatIsShow(formatStatusIsShowE);
                    formatIsDelete(formatStatusIsDeleteE);

//Call back : Bởi sau mỗi lần bấm thì sẽ generate ra 1 giao diện và cần add lại Event
                    buttonPagination(url, method, listElement, pagingElement);
                    selectdropPagination(url, method, listElement, pagingElement);
                    inputPagination(url, method, listElement, pagingElement);
                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });


        }
    });

}

// Select

function selectdropPagination(url, method, listElement, pagingElement) {
    var selectPaginationE = document.getElementById("select_pagination");

//    console.log(selectPaginationE);

    selectPaginationE.onchange = function ()
    {
        var pageSize = selectPaginationE.value;
        var pageNumber = document.querySelector(".page_number input").value;
        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCategoryValue = document.getElementById('select_filter_category').value;
        var filterIsShowValue = document.getElementById('filterisShow').value;
        var filterIsDeleteValue = document.getElementById('filterisDelete').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentProductE.value;
        var searchCheckbox = "";

        if (checkboxSearchE.checked) {
            searchCheckbox = "checked";
        }
//        console.log(pageNumber + " size: " + pageSize);
//        console.log(filterPriceValue + " :::: " + filterCatagoryValue);


        $.ajax({
            url: url,
            type: method,
            data: {
                pageSize: pageSize,
                pageNumber: 1,
                filterPriceValue: filterPriceValue,
                filterIsShowValue: filterIsShowValue,
                filterIsDeleteValue: filterIsDeleteValue,
                filterCategoryValue: filterCategoryValue,
                filterSearchValue: filterSearchValue,
                searchContent: searchContent,
                searchCheckbox: searchCheckbox
            },
            success: function (data) {

                listElement.innerHTML = data.htmls;

                pagingElement.innerHTML = data.htmlPaging;

                var formatDoubleElements = document.querySelectorAll(".item .moneyFormat span");


                formatDoubleElements.forEach(function (i) {
                    var intP = parseInt(i.textContent);
                    var format = intP.toLocaleString();
                    i.textContent = format;
                });
                var formatStatusIsShowE = document.querySelectorAll(".item .statusIsShow");
                var formatStatusIsDeleteE = document.querySelectorAll(".item .statusIsDelete");

                formatIsShow(formatStatusIsShowE);
                formatIsDelete(formatStatusIsDeleteE);
// Call back
                buttonPagination(url, method, listElement, pagingElement);
                selectdropPagination(url, method, listElement, pagingElement);
                inputPagination(url, method, listElement, pagingElement);
            },
            error: function (xhr) {
                console.log('Lỗi khi gửi yêu cầu AJAX');
            }
        });
    }
}


// Input

function inputPagination(url, method, listElement, pagingElement) {

    var inputPaginationE = document.querySelector(".page_number input");

//    console.log(inputPaginationE);

    function inputPaging() {

    }

    inputPaginationE.addEventListener("keypress", function (event) {
        if (event.key === "Enter")
        {
            var pageSize = document.getElementById('select_pagination').value;
            var pageNumber = parseInt(inputPaginationE.value);
            var toltalNumber = parseInt(document.querySelector(".page_number span p").textContent);
            var filterPriceValue = document.getElementById('select_filter_price').value;
            var filterCategoryValue = document.getElementById('select_filter_category').value;
            var filterIsShowValue = document.getElementById('filterisShow').value;
            var filterIsDeleteValue = document.getElementById('filterisDelete').value;
            var filterSearchValue = document.getElementById('filterSearch').value;
            var searchContent = searchContentProductE.value;
            var searchCheckbox = "";

            if (checkboxSearchE.checked) {
                searchCheckbox = "checked";
            }
//            console.log(toltalNumber);

            console.log(typeof pageNumber + " size: " + typeof toltalNumber);

            if (pageNumber <= 0)
            {
                pageNumber = 1;
            } else if (pageNumber >= toltalNumber)
            {
                pageNumber = toltalNumber;
            }

//            console.log(pageNumber + " size: " + typeof  toltalNumber);
//            console.log(filterPriceValue + " :::: " + filterCatagoryValue);

            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterPriceValue: filterPriceValue,
                    filterIsShowValue: filterIsShowValue,
                    filterIsDeleteValue: filterIsDeleteValue,
                    filterCategoryValue: filterCategoryValue,
                    filterSearchValue: filterSearchValue,
                    searchContent: searchContent,
                    searchCheckbox: searchCheckbox
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;

                    pagingElement.innerHTML = data.htmlPaging;

                    var formatDoubleElements = document.querySelectorAll(".item .moneyFormat span");


                    formatDoubleElements.forEach(function (i) {
                        var intP = parseInt(i.textContent);
                        var format = intP.toLocaleString();
                        i.textContent = format;
                    });
                    var formatStatusIsShowE = document.querySelectorAll(".item .statusIsShow");
                    var formatStatusIsDeleteE = document.querySelectorAll(".item .statusIsDelete");

                    formatIsShow(formatStatusIsShowE);
                    formatIsDelete(formatStatusIsDeleteE);
// Call back
                    buttonPagination(url, method, listElement, pagingElement);
                    selectdropPagination(url, method, listElement, pagingElement);
                    inputPagination(url, method, listElement, pagingElement);
                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });
        }

    });
}

// Xử lý  Search
searchContentProductE.addEventListener("input", function () {


    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = 1;
    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCategoryValue = document.getElementById('select_filter_category').value;
    var filterIsShowValue = document.getElementById('filterisShow').value;
    var filterIsDeleteValue = document.getElementById('filterisDelete').value;
    var filterSearchValue = document.getElementById('filterSearch').value;
    var searchContent = searchContentProductE.value;
    var searchCheckbox = "";



    if (checkboxSearchE.checked) {
        searchCheckbox = "checked";
    }

//    console.log("pageSize " + pageSize +
//            " pageNumber " + pageNumber + " filterPriceValue " + filterPriceValue +
//            " filterCategoryValue " + filterCategoryValue + " filterIsShowValue " + filterIsShowValue +
//            " filterIsDeleteValue " + filterIsDeleteValue + " filterSearchValue " + filterSearchValue +
//            " searchContent " + searchContent + " searchCheckbox " + searchCheckbox
//            );

    loadProductAdminFilter(pageSize, pageNumber,
            filterPriceValue, filterIsShowValue, filterIsDeleteValue,
            filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);


});



// Xử lý filter select drop
var customSelectElements = document.querySelectorAll(".custom_select");

//console.log(customSelectElements);

customSelectElements.forEach(function (item) {
    item.onchange = function () {


        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCategoryValue = document.getElementById('select_filter_category').value;
        var filterIsShowValue = document.getElementById('filterisShow').value;
        var filterIsDeleteValue = document.getElementById('filterisDelete').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentProductE.value;
        var searchCheckbox = "";


        if (checkboxSearchE.checked) {
            searchCheckbox = "checked";
        }
//        console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);

        loadProductAdminFilter(pageSize, pageNumber,
                filterPriceValue, filterIsShowValue, filterIsDeleteValue,
                filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);



    };

});


// Xử lý checkbox - checked : Tìm kiếm theo nguyên mẫu, Unchecked thì tìm theo LIKE

checkboxSearchE.addEventListener("change", function (event) {
    if (event.target.checked) {
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCategoryValue = document.getElementById('select_filter_category').value;
        var filterIsShowValue = document.getElementById('filterisShow').value;
        var filterIsDeleteValue = document.getElementById('filterisDelete').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentProductE.value;
        var searchCheckbox = "checked";


        loadProductAdminFilter(pageSize, pageNumber,
                filterPriceValue, filterIsShowValue, filterIsDeleteValue,
                filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);



    } else {
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCategoryValue = document.getElementById('select_filter_category').value;
        var filterIsShowValue = document.getElementById('filterisShow').value;
        var filterIsDeleteValue = document.getElementById('filterisDelete').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentProductE.value;
        var searchCheckbox = "";



        loadProductAdminFilter(pageSize, pageNumber,
                filterPriceValue, filterIsShowValue, filterIsDeleteValue,
                filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);

    }
});


var loadProductAdminFilter = function (pageSize, pageNumber,
        filterPriceValue, filterIsShowValue, filterIsDeleteValue,
        filterCategoryValue, filterSearchValue, searchContent, searchCheckbox)
{


//    console.log("AJAX:  " + " pageSize " + pageSize +
//            " pageNumber " + pageNumber + " filterPriceValue " + filterPriceValue +
//            " filterCategoryValue " + filterCategoryValue + " filterIsShowValue " + filterIsShowValue +
//            " filterIsDeleteValue " + filterIsDeleteValue + " filterSearchValue " + filterSearchValue +
//            " searchContent " + searchContent + " searchCheckbox " + searchCheckbox
//            );
    $.ajax({
        url: "./listProductAdmin",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterPriceValue: filterPriceValue,
            filterIsShowValue: filterIsShowValue,
            filterIsDeleteValue: filterIsDeleteValue,
            filterCategoryValue: filterCategoryValue,
            filterSearchValue: filterSearchValue,
            searchContent: searchContent,
            searchCheckbox: searchCheckbox
        },
        success: function (data) {

            productListE.innerHTML = data.htmls;

            paginationE.innerHTML = data.htmlPaging;


            var formatDoubleElements = document.querySelectorAll(".item .moneyFormat span");


            formatDoubleElements.forEach(function (i) {
                var intP = parseInt(i.textContent);
                var format = intP.toLocaleString();
                i.textContent = format;
            });


            var formatStatusIsShowE = document.querySelectorAll(".item .statusIsShow");
            var formatStatusIsDeleteE = document.querySelectorAll(".item .statusIsDelete");

            formatIsShow(formatStatusIsShowE);
            formatIsDelete(formatStatusIsDeleteE);

            buttonPagination('./listProductAdmin', 'post', productListE, paginationE);
            selectdropPagination('./listProductAdmin', 'post', productListE, paginationE);
            inputPagination('./listProductAdmin', 'post', productListE, paginationE);

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - filter');
        }
    });
}

// ============ Xử lý nút bấm xem chi tiết ===============


// Load  category filter khi mở xem chi tiết sản phẩm 
var loadListCategoryFilter = function (categorySelected) {
    var listCategoryFilterE = document.getElementById("select_category_detail");

    $.ajax({
        url: './getListFilter', // Đường dẫn đến controller
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {
            listCategoryFilterE.innerHTML = data.listCategory;



            // Lặp qua các option để tìm option có giá trị trùng khớp
            for (var i = 0; i < listCategoryFilterE.options.length; i++) {
                if (listCategoryFilterE.options[i].value == "category") {
                    listCategoryFilterE.options[i].style.display = 'none';
                } else if (listCategoryFilterE.options[i].value === categorySelected) {
                    // Đánh dấu option này là đã chọn bằng cách set thuộc tính selected thành true
                    listCategoryFilterE.options[i].selected = true;
                    listCategoryFilterE.options[i].dataset.datasql = categorySelected;
                } else {
                    listCategoryFilterE.options[i].dataset.datasql = categorySelected;
                }
            }
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - List Price Product');
        }
    });
}



var viewProductDetail = function (button) {
    var productId = button.getAttribute("data-id");

//    console.log(userId);
    $.ajax({
        url: "./manageProductController",
        type: "post",
        data: {
            productId: productId
        },
        success: function (data) {
            productDeatilE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";
            animationFade_Down(contentUserDetailE);


            // Xử lý event focus vào textarea
            var textArea_desE = document.getElementById('text_des');
            textArea_desE.value = textArea_desE.value.trim();

            textArea_desE.addEventListener('focus', function () {

                textArea_desE.focus(); // Đảm bảo textarea đã được focus
                const textLength = textArea_desE.value.trim().length;

                // Di chuyển con trỏ vào cuối nội dung của textarea

                setTimeout(function () {
                    textArea_desE.setSelectionRange(textLength, textLength);
                }, 0);

            });


            var createdAtElement = document.getElementById("createdAt");

            createdAtElement.value = formatDate(createdAtElement.value);


            var balanceE = document.getElementById("price");
            balanceE.value = parseInt(balanceE.value).toLocaleString() + "đ";

// Load list category từ DB lên 
            var categoryProductView = data.category;
            loadListCategoryFilter(categoryProductView);

// Xử lý khi focus tiền
            var priceElement = document.getElementById('price');
            priceElement.addEventListener('focus', function () {
                priceElement.value = priceElement.value.replace('đ', '').replace(/,/g, '').replace(/\./g, '');
                ;
            });
            priceElement.addEventListener('blur', function () {

                var valueInput = priceElement.value;
                console.log(isNaN(valueInput))

                if (valueInput == "" || valueInput == null
                        ) {
                    priceElement.value = "0đ";
                } else {
                    priceElement.value = parseInt(valueInput).toLocaleString() + "đ";
                }

                if (isNaN(valueInput) || parseInt(valueInput) < 0) {
                    priceElement.value = parseInt(priceElement.dataset.datasql).toLocaleString() + "đ";
                }
            });

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - View detail product');
        }
    });
}
var closeBox = function () {
    animationFade_Up(containerUserDetailE, contentUserDetailE);

    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = 1;
    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCategoryValue = document.getElementById('select_filter_category').value;
    var filterIsShowValue = document.getElementById('filterisShow').value;
    var filterIsDeleteValue = document.getElementById('filterisDelete').value;
    var filterSearchValue = document.getElementById('filterSearch').value;
    var searchContent = searchContentProductE.value;
    var searchCheckbox = "";

    if (checkboxSearchE.checked) {
        searchCheckbox = "checked";
    }
//    console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);
    loadProductAdminFilter(pageSize, pageNumber,
            filterPriceValue, filterIsShowValue, filterIsDeleteValue,
            filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);

}





// Xử lý xem ảnh khi chọn file từ máy
function displayImage(event) {
    var input = event.target;
    var containerImageE = document.querySelector(".box_content .previewImage");
//    containerImageE.style.display = "flex";
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var imagePreview = document.getElementById('preview');
            imagePreview.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// Update thông tin product
var getValueDBRadioButton = function (radiobuttons) {
    var selectValueDB;
    for (var i = 0; i < radiobuttons.length; i++) {
        if (radiobuttons[i].dataset.datasql == "checked") {
            selectValueDB = radiobuttons[i].value;
            break;
        }
    }
    return selectValueDB;
}
var getValueRadioButton = function (radioButtonsE) {
    var selectedValue;
    for (var i = 0; i < radioButtonsE.length; i++) {
        if (radioButtonsE[i].checked) {
            selectedValue = radioButtonsE[i].value;
            break;
        }
    }
    return selectedValue;
}


var updateInfoProduct = function (button)
{
    // truyen data sang servlet
//    var formData = new FormData();

    var fileInput = document.getElementById('avatar');
    var file = fileInput.files[0];

// Xử lý ảnh
    var imageName;

    if (file != null)
    {
//        formData.append('image', file);
        imageName = fileInput.files[0].name;
        console.log(imageName);

    } else {
        imageName = document.getElementById("preview").dataset.name;
    }
//    console.log('Image Name: ' + imageName);
//    console.log(imageName == document.getElementById("preview").dataset.name)

    var id = document.getElementById('productId').value;
    var productName = document.getElementById('nameProduct').value;

    var categorySelectDropE = document.getElementById('select_category_detail');
    var category = categorySelectDropE.value;
    var categoryDB = categorySelectDropE.options[categorySelectDropE.selectedIndex].dataset.datasql;

    var categoryOther = document.getElementById("otherCategory").value;
    if (categoryOther != "") {
        category = categoryOther;
    }

//    console.log(category);
    var price = document.getElementById('price').value.replace('đ', '').replace(/,/g, '').replace(/\./g, '');

    var description = document.getElementById('text_des').value;

// isShow
    var isShowRadioButtonsE = document.querySelectorAll('input[name="group_isShow"]');
    var isShowValueDB = getValueDBRadioButton(isShowRadioButtonsE)
    var isShowSelected = getValueRadioButton(isShowRadioButtonsE);
// isDelete    
    var isDeleteRadioButtonsE = document.querySelectorAll('input[name="group_isDelete"]');
    var isDeleteValueDB = getValueDBRadioButton(isDeleteRadioButtonsE)
    var isDeleteSelected = getValueRadioButton(isDeleteRadioButtonsE);


//    console.log("id: " + id + " imageName: " + imageName
//            + " productName: " + productName
//            + " category: " + category
//            + " Price: " + price
//            + " Description: " + description
//            + " isShow DB value: " + isShowValueDB
//            + " isShow value: " + isShowSelected
//
//            + " isDelete DB value: " + isDeleteValueDB
//            + " isDelete value: " + isDeleteSelected
//            + file);

//    console.log(productName != document.getElementById('nameProduct').dataset.datasql);
//    console.log(category != categoryDB);
//    console.log(price != document.getElementById('price').dataset.datasql);
//    console.log(description != document.getElementById('text_des').dataset.datasql);
//    console.log(isShowSelected != isShowValueDB);
//    console.log(isDeleteSelected != isDeleteValueDB);




    if (productName == "" || price == 0 || description == "") {
        notificationContainer.style.display = 'flex';
        var htmls = `<div class="icon" style="color: yellow"> 
                        <!--<ion-icon name="checkmark-circle-outline"></ion-icon>-->
                        <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>Dữ liệu đang để trống hoặc không đúng format, Vui lòng kiểm tra lại</h3>

                    <div class="buttons">
                        <button  onclick="confirmUpdateFalse()"  class="btn">OK</button>
                    </div>`;
        notificationContent.innerHTML = htmls;

        animationFade_Down(notificationContent);
    } else if (imageName != document.getElementById("preview").dataset.name
            || productName != document.getElementById('nameProduct').dataset.datasql
            || category != categoryDB
            || price != document.getElementById('price').dataset.datasql
            || description != document.getElementById('text_des').dataset.datasql
            || isShowSelected != isShowValueDB || isDeleteSelected != isDeleteValueDB) {


        var nameIsChange = "false";
        if (productName != document.getElementById('nameProduct').dataset.datasql) {
            nameIsChange = "true";
        }


//        console.log("changed");

        $.ajax({
            url: './updateProductController', // Đường dẫn đến homeController
            method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
            data: {
                id: id,
                imageName: imageName,
                productName: productName,
                category: category,
                price: price,
                description: description,
                isShowSelected: isShowSelected,
                isDeleteSelected: isDeleteSelected,
                nameIsChange: nameIsChange
            },
            success: function (data) {
                notificationContent.innerHTML = data.htmlNotification;
                notificationContainer.style.display = 'flex';

                animationFade_Down(notificationContent);

            },
            error: function () {
                console.log('Lỗi khi gửi yêu cầu AJAX');
            }
        });


//        var xhr = new XMLHttpRequest();
//        xhr.open('POST', 'updateProductController', true);
//        xhr.send(formData);
//
//        xhr.onreadystatechange = function () {
//            if (xhr.readyState === 4 && xhr.status === 200) {
//
//
//                // Xử lý kết quả từ servlet (nếu cần)
//                console.log('Upload thành công');
//            } else {
//                // Xử lý lỗi (nếu cần)
//                console.log('Upload thất bại');
//            }
//        };
    } else {
//        console.log("Khong co gi xay ra");
        notificationContainer.style.display = 'flex';
        var htmls = `<div class="icon" style="color: yellow"> 
                        <!--<ion-icon name="checkmark-circle-outline"></ion-icon>-->
                        <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>Không có dữ liệu mới để Update, Vui lòng kiểm tra lại</h3>

                    <div class="buttons">
                        <button  onclick="confirmUpdateFalse()"  class="btn">OK</button>
                    </div>`;
        notificationContent.innerHTML = htmls;

        animationFade_Down(notificationContent);

    }


}
var confirmUpdateFalse = function () {
    animationFade_Up(notificationContainer, notificationContent);
}




var popUpContainerE = document.querySelector(".box_container");

var confirmUpdateSuccess = function (button) {
    var productId = button.getAttribute("data-id");

    animationFade_Up(notificationContainer, notificationContent);

    $.ajax({
        url: "./manageProductController",
        type: "post",
        data: {
            productId: productId
        },
        success: function (data) {
            productDeatilE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";


            // Xử lý event focus vào textarea
            var textArea_desE = document.getElementById('text_des');
            textArea_desE.value = textArea_desE.value.trim();

            textArea_desE.addEventListener('focus', function () {

                textArea_desE.focus(); // Đảm bảo textarea đã được focus
                const textLength = textArea_desE.value.trim().length;

                // Di chuyển con trỏ vào cuối nội dung của textarea

                setTimeout(function () {
                    textArea_desE.setSelectionRange(textLength, textLength);
                }, 0);

            });


            var createdAtElement = document.getElementById("createdAt");

            createdAtElement.value = formatDate(createdAtElement.value);


            var balanceE = document.getElementById("price");
            balanceE.value = parseInt(balanceE.value).toLocaleString() + "đ";

// Load list category từ DB lên 
            var categoryProductView = data.category;
            loadListCategoryFilter(categoryProductView);

// Xử lý khi focus tiền
            var priceElement = document.getElementById('price');
            priceElement.addEventListener('focus', function () {
                priceElement.value = priceElement.value.replace('đ', '').replace(/,/g, '');
            });
            priceElement.addEventListener('blur', function () {

                var valueInput = priceElement.value;
                console.log(isNaN(valueInput))

                if (valueInput == "" || valueInput == null
                        ) {
                    priceElement.value = "0đ";
                } else {
                    priceElement.value = parseInt(valueInput).toLocaleString() + "đ";
                }

                if (isNaN(valueInput) || parseInt(valueInput) < 0) {
                    priceElement.value = parseInt(priceElement.dataset.datasql).toLocaleString() + "đ";
                }
            });

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - View detail product');
        }
    });
}

var submitOk = function ()
{
    animationFade_Up(containerUserDetailE, contentUserDetailE);

    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;
    var filterPriceValue = document.getElementById('select_filter_price').value;
    var filterCategoryValue = document.getElementById('select_filter_category').value;
    var filterIsShowValue = document.getElementById('filterisShow').value;
    var filterIsDeleteValue = document.getElementById('filterisDelete').value;
    var filterSearchValue = document.getElementById('filterSearch').value;
    var searchContent = searchContentProductE.value;
    var searchCheckbox = "";


    if (checkboxSearchE.checked) {
        searchCheckbox = "checked";
    }
//        console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);

    loadProductAdminFilter(pageSize, pageNumber,
            filterPriceValue, filterIsShowValue, filterIsDeleteValue,
            filterCategoryValue, filterSearchValue, searchContent, searchCheckbox);

};



