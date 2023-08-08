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

function getCurrentTimestamp() {
    var now = new Date();
    var year = now.getFullYear();
    var month = String(now.getMonth() + 1).padStart(2, '0');
    var day = String(now.getDate()).padStart(2, '0');
    var hour = String(now.getHours()).padStart(2, '0');
    var minute = String(now.getMinutes()).padStart(2, '0');

    return year + month + day + hour + minute;
}

function isValidTimestampFormat(timestamp) {
    // Kiểm tra định dạng bằng biểu thức chính quy
    var regex = /^\d{12}$/; // Biểu thức chính quy cho chuỗi gồm 12 chữ số
    var currentTimestamp = getCurrentTimestamp();

    if (!regex.test(timestamp) || timestamp <= currentTimestamp) {
        return false;
    } else {
        return true;
    }


}

var formatIsShow = function (elements)
{
    elements.forEach(function (item) {
        var intItem = parseInt(item.textContent);
        var format;

        if (intItem == 1) {
            format = "On sale";
            item.style.color = "green";
        } else if (intItem == 0) {
            format = "Sold out";
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
var loadStorageListAdmin = function () {
    $.ajax({
        url: './listStorage', // Đường dẫn đến 
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


            buttonPagination('./listStorage', 'post', productListE, paginationE);
            selectdropPagination('./listStorage', 'post', productListE, paginationE);
            inputPagination('./listStorage', 'post', productListE, paginationE);

        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - Load list storage - Onload');
        }
    });
}
window.onload = function () {
    loadListFilter();
    loadStorageListAdmin();
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
            var searchContent = searchContentProductE.value;


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
                    searchContent: searchContent
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
        var filterPriceValue = document.getElementById('select_filter_price').value;
        var filterCategoryValue = document.getElementById('select_filter_category').value;
        var filterIsShowValue = document.getElementById('filterisShow').value;
        var filterIsDeleteValue = document.getElementById('filterisDelete').value;
        var searchContent = searchContentProductE.value;

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
                searchContent: searchContent
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
            var searchContent = searchContentProductE.value;


//            console.log(typeof pageNumber + " size: " + typeof toltalNumber);

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
                    searchContent: searchContent
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
    var searchContent = searchContentProductE.value;


//    console.log("pageSize " + pageSize +
//            " pageNumber " + pageNumber + " filterPriceValue " + filterPriceValue +
//            " filterCategoryValue " + filterCategoryValue + " filterIsShowValue " + filterIsShowValue +
//            " filterIsDeleteValue " + filterIsDeleteValue + " filterSearchValue " + filterSearchValue +
//            " searchContent " + searchContent + " searchCheckbox " + searchCheckbox
//            );

    loadStorageFilter(pageSize, pageNumber,
            filterPriceValue, filterIsShowValue, filterIsDeleteValue,
            filterCategoryValue, searchContent);


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
        var searchContent = searchContentProductE.value;


//    console.log("pageSize " + pageSize +
//            " pageNumber " + pageNumber + " filterPriceValue " + filterPriceValue +
//            " filterCategoryValue " + filterCategoryValue + " filterIsShowValue " + filterIsShowValue +
//            " filterIsDeleteValue " + filterIsDeleteValue + " filterSearchValue " + "filterSearchValue" +
//            " searchContent " + searchContent + " searchCheckbox " + "searchCheckbox"
//            );

        loadStorageFilter(pageSize, pageNumber,
                filterPriceValue, filterIsShowValue, filterIsDeleteValue,
                filterCategoryValue, searchContent);


    };

});




var loadStorageFilter = function (pageSize, pageNumber,
        filterPriceValue, filterIsShowValue, filterIsDeleteValue,
        filterCategoryValue, searchContent)
{


//    console.log("AJAX:  " + " pageSize " + pageSize +
//            " pageNumber " + pageNumber + " filterPriceValue " + filterPriceValue +
//            " filterCategoryValue " + filterCategoryValue + " filterIsShowValue " + filterIsShowValue +
//            " filterIsDeleteValue " + filterIsDeleteValue + " filterSearchValue " + filterSearchValue +
//            " searchContent " + searchContent + " searchCheckbox " + searchCheckbox
//            );
    $.ajax({
        url: "./listStorage",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterPriceValue: filterPriceValue,
            filterIsShowValue: filterIsShowValue,
            filterIsDeleteValue: filterIsDeleteValue,
            filterCategoryValue: filterCategoryValue,
            searchContent: searchContent
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

            buttonPagination('./listStorage', 'post', productListE, paginationE);
            selectdropPagination('./listStorage', 'post', productListE, paginationE);
            inputPagination('./listStorage', 'post', productListE, paginationE);

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - filter');
        }
    });
}

// ======== Xử lý nút xem chi tiết thẻ =============

var viewStorageDetail = function (button) {
    var storageId = button.getAttribute("data-id");

//    console.log(userId);
    $.ajax({
        url: "./manageStorageController",
        type: "post",
        data: {
            storageId: storageId
        },
        success: function (data) {
            productDeatilE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";
            animationFade_Down(contentUserDetailE);


            var createdAtElement = document.getElementById("createdAdSto");
            var expriryDateE = document.getElementById("expridyDate");

            createdAtElement.value = formatDate(createdAtElement.value);
            expriryDateE.value = formatDate(expriryDateE.value);

            expriryDateE.addEventListener("focus", function () {

                var inputString = expriryDateE.value;
//                console.log(inputString);
                // Tách chuỗi thành các thành phần
                var parts = inputString.replace(/\s/g, "").split(/[\s:-]/g);

//                console.log(parts);


                expriryDateE.value = parts[4] + parts[3] + parts[2] + parts[0] + parts[1];

            });
            expriryDateE.addEventListener("blur", function () {

                if (isValidTimestampFormat(expriryDateE.value)) {
                    expriryDateE.value = formatDate(expriryDateE.value);
                } else {
                    expriryDateE.value = expriryDateE.dataset.sqldb;

                    expriryDateE.value = formatDate(expriryDateE.dataset.sqldb);

                }

            });



        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - View detail storage');
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
    var searchContent = searchContentProductE.value;



    loadStorageFilter(pageSize, pageNumber,
            filterPriceValue, filterIsShowValue, filterIsDeleteValue,
            filterCategoryValue, searchContent);

};

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


var updateInfoStorage = function (button) {
    var storageId = button.getAttribute("data-id");
    var productId = button.getAttribute("data-pid");
    var expriryDateE = document.getElementById("expridyDate");
    var parts = expriryDateE.value.replace(/\s/g, "").split(/[\s:-]/g);

    var expriryDateValue = parts[4] + parts[3] + parts[2] + parts[0] + parts[1];
    var seriValue = document.getElementById("seri").value;
    var codeValue = document.getElementById("code").value;

//    console.log(expriryDateValue);

    // isShow
    var statusRadioButtonsE = document.querySelectorAll('input[name="group_Status"]');
    var statusValueDB = getValueDBRadioButton(statusRadioButtonsE)
    var statusSelected = getValueRadioButton(statusRadioButtonsE);
// isDelete    
    var isDeleteRadioButtonsE = document.querySelectorAll('input[name="group_isDelete"]');
    var isDeleteValueDB = getValueDBRadioButton(isDeleteRadioButtonsE)
    var isDeleteSelected = getValueRadioButton(isDeleteRadioButtonsE);

    var changeSeri = "false";
    if (seriValue != document.getElementById('seri').dataset.sqldb)
    {
        changeSeri = "true";
    }

//    
//    console.log(  typeof  document.getElementById('seri').dataset.sqldb);
//    
//    console.log(expriryDateValue != document.getElementById("expridyDate").dataset.sqldb);
//    console.log(seriValue != document.getElementById('seri').dataset.sqldb);
//    console.log(codeValue != document.getElementById('code').dataset.sqldb);
//    console.log(statusSelected != statusValueDB);
//    console.log(isDeleteSelected != isDeleteValueDB);


    if (expriryDateValue == "" || seriValue == "" || codeValue == "") {
        notificationContainer.style.display = 'flex';
        var htmls = `<div class="icon" style="color: yellow"> 
                        <!--<ion-icon name="checkmark-circle-outline"></ion-icon>-->
                        <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>Dữ liệu nhập vào đang để trống, Vui lòng kiểm tra lại</h3>

                    <div class="buttons">
                        <button  onclick="confirmUpdateFalse()"  class="btn">OK</button>
                    </div>`;
        notificationContent.innerHTML = htmls;

        animationFade_Down(notificationContent);
    } else if (expriryDateValue != document.getElementById("expridyDate").dataset.sqldb
            || seriValue != document.getElementById('seri').dataset.sqldb
            || codeValue != document.getElementById('code').dataset.sqldb
            || statusSelected != statusValueDB || isDeleteSelected != isDeleteValueDB) {
//        console.log("Updating");

        $.ajax({
            url: './updateStorageController', // Đường dẫn đến homeController
            method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
            data: {
                storageId: storageId,
                productId: productId,
                expriryDateValue: expriryDateValue,
                seriValue: seriValue,
                codeValue: codeValue,
                statusSelected: statusSelected,
                isDeleteSelected: isDeleteSelected,
                changeSeri: changeSeri
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
    } else {
//        console.log("Not update");

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

var confirmUpdateStoSuccess = function (button) {
    var storageId = button.getAttribute("data-id");

//    console.log(userId);
    $.ajax({
        url: "./manageStorageController",
        type: "post",
        data: {
            storageId: storageId
        },
        success: function (data) {
            productDeatilE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";
//            animationFade_Down(contentUserDetailE);

            animationFade_Up(notificationContainer, notificationContent);

            var createdAtElement = document.getElementById("createdAdSto");
            var expriryDateE = document.getElementById("expridyDate");

            createdAtElement.value = formatDate(createdAtElement.value);
            expriryDateE.value = formatDate(expriryDateE.value);

            expriryDateE.addEventListener("focus", function () {

                var inputString = expriryDateE.value;
//                console.log(inputString);
                // Tách chuỗi thành các thành phần
                var parts = inputString.replace(/\s/g, "").split(/[\s:-]/g);

//                console.log(parts);


                expriryDateE.value = parts[4] + parts[3] + parts[2] + parts[0] + parts[1];

            });
            expriryDateE.addEventListener("blur", function () {

                if (isValidTimestampFormat(expriryDateE.value)) {
                    expriryDateE.value = formatDate(expriryDateE.value);
                } else {
                    expriryDateE.value = expriryDateE.dataset.sqldb;

                    expriryDateE.value = formatDate(expriryDateE.dataset.sqldb);

                }

            });



        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - View detail storage');
        }
    });
}