var listUserElement = document.querySelector(".list_wraper .list_content");
var paginationElement = document.getElementById("pagination");
var searchContentE = document.getElementById("search_content");
var checkboxSearchE = document.getElementById("checkBoxSearch");


var notificationContainer = document.getElementById('notification');
var notificationContent = document.querySelector("#notification .notificationBox")


function isEmail(str) {
    const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
//    const emailRegex = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
    return emailRegex.test(str);
}
//console.log(isEmail("sontungnd98@gmail.com"));

function formatDate(timestamp) {
    var year = timestamp.substring(0, 4);
    var month = timestamp.substring(4, 6);
    var day = timestamp.substring(6, 8);
    var hour = timestamp.substring(8, 10);
    var minute = timestamp.substring(10, 12);

    var formattedDate = hour + ":" + minute + " - " + day + " - " + month + " - " + year;

    return formattedDate;
}

function formatRole(role) {
    var roleString = "";
    if (parseInt(role) === 1) {
        roleString = "Admin";
    } else if (parseInt(role) === 2) {
        roleString = "Customer";
    }
    return roleString;
}

var formatStatusUser = function (elements) {

    elements.forEach(function (item) {
        var itemValue = item.textContent;
        if (parseInt(itemValue) === 0)
        {
            item.textContent = "Active";
            item.style.color = "green";
        } else if (parseInt(itemValue) === 1)
        {
            item.textContent = "Banned";
            item.style.color = "red";

        }
    });
}
var loadUserList = function () {
    $.ajax({
        url: './listUser', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {
            listUserElement.innerHTML = data.htmls;
            paginationElement.innerHTML = data.pagination;

            var statusUserElements = document.querySelectorAll(".wraper_item  .statusUser p");

            formatStatusUser(statusUserElements);

            buttonPagination('./listUser', 'post', listUserElement, paginationElement);
            selectdropPagination('./listUser', 'post', listUserElement, paginationElement);
            inputPagination('./listUser', 'post', listUserElement, paginationElement);

        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - User List');
        }
    });
}


window.onload = function ()
{
    loadUserList();
};

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
            var filterDateValue = document.getElementById('filterDate').value;
            var filterStatusValue = document.getElementById('filterStatus').value;
            var filterSearchValue = document.getElementById('filterSearch').value;
            var searchContent = searchContentE.value;
            var searchCheckbox = "";

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
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue,
                    filterSearchValue: filterSearchValue,
                    searchContent: searchContent,
                    searchCheckbox: searchCheckbox
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;

                    pagingElement.innerHTML = data.htmlPaging;

                    var statusUserElements = document.querySelectorAll(".wraper_item  .statusUser p");
                    formatStatusUser(statusUserElements);



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
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentE.value;
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
                filterDateValue: filterDateValue,
                filterStatusValue: filterStatusValue,
                filterSearchValue: filterSearchValue,
                searchContent: searchContent,
                searchCheckbox: searchCheckbox
            },
            success: function (data) {

                listElement.innerHTML = data.htmls;

                pagingElement.innerHTML = data.htmlPaging;

                var statusUserElements = document.querySelectorAll(".wraper_item  .statusUser p");
                formatStatusUser(statusUserElements);

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
            var filterDateValue = document.getElementById('filterDate').value;
            var filterStatusValue = document.getElementById('filterStatus').value;
            var filterSearchValue = document.getElementById('filterSearch').value;
            var searchContent = searchContentE.value;
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
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue,
                    filterSearchValue: filterSearchValue,
                    searchContent: searchContent,
                    searchCheckbox: searchCheckbox
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;

                    pagingElement.innerHTML = data.htmlPaging;

                    var statusUserElements = document.querySelectorAll(".wraper_item  .statusUser p");
                    formatStatusUser(statusUserElements);

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


// Xử lý filter select drop
var customSelectElement = document.querySelectorAll(".custom_select");


customSelectElement.forEach(function (item) {
    item.onchange = function () {


        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentE.value;
        var searchCheckbox = "";

        if (checkboxSearchE.checked) {
            searchCheckbox = "checked";
        }
//        console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);

        loadUserFilter(pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox);

    };

});
// Xử lý  Search
searchContentE.addEventListener("input", function () {
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = 1;
    var filterDateValue = document.getElementById('filterDate').value;
    var filterStatusValue = document.getElementById('filterStatus').value;
    var filterSearchValue = document.getElementById('filterSearch').value;
    var searchContent = searchContentE.value;
    var searchCheckbox = "";

    if (checkboxSearchE.checked) {
        searchCheckbox = "checked";
    }
//    console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);

    loadUserFilter(pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox);


});

// Xử lý checkbox - checked : Tìm kiếm theo nguyên mẫu, Unchecked thì tìm theo LIKE

checkboxSearchE.addEventListener("change", function (event) {
    if (event.target.checked) {
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentE.value;
        var searchCheckbox = "";

        if (checkboxSearchE.checked) {
            searchCheckbox = "checked";
        }

        loadUserFilter(pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox);

    } else {
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var filterSearchValue = document.getElementById('filterSearch').value;
        var searchContent = searchContentE.value;
        var searchCheckbox = "";

        if (checkboxSearchE.checked) {
            searchCheckbox = "checked";
        }

        loadUserFilter(pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox);

    }
});


var loadUserFilter = function (pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox)
{

    $.ajax({
        url: "./listUser",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterDateValue: filterDateValue,
            filterStatusValue: filterStatusValue,
            filterSearchValue: filterSearchValue,
            searchContent: searchContent,
            searchCheckbox: searchCheckbox
        },
        success: function (data) {

            listUserElement.innerHTML = data.htmls;

            paginationElement.innerHTML = data.htmlPaging;


            var statusUserElements = document.querySelectorAll(".wraper_item  .statusUser p");
            formatStatusUser(statusUserElements);

            buttonPagination('./listUser', 'post', listUserElement, paginationElement);
            selectdropPagination('./listUser', 'post', listUserElement, paginationElement);
            inputPagination('./listUser', 'post', listUserElement, paginationElement);


        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - Select drop filter');
        }
    });
}

// =============== Xử lý các nút bấm ===============

var containerUserDetailE = document.querySelector(".box_container");
var contentUserDetailE = document.querySelector(".box_container .box_wraper_content");

var viewUserDetail = function (button) {
    var userId = button.getAttribute("data-id");

//    console.log(userId);
    $.ajax({
        url: "./manageUserController",
        type: "post",
        data: {
            userId: userId
        },
        success: function (data) {
            contentUserDetailE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";
            animationFade_Down(contentUserDetailE);

            var createdAtElement = document.getElementById("createdAt");

            createdAtElement.value = formatDate(createdAtElement.value);

            var roleValueE = document.getElementById("role");

            roleValueE.value = formatRole(roleValueE.value);

            var balanceE = document.getElementById("balance");
            balanceE.value = parseInt(balanceE.value).toLocaleString() + "đ";
        },
        error: function (xhr) {

        }
    });
}
var closeBox = function () {
    animationFade_Up(containerUserDetailE, contentUserDetailE);

    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;
    var filterDateValue = document.getElementById('filterDate').value;
    var filterStatusValue = document.getElementById('filterStatus').value;
    var filterSearchValue = document.getElementById('filterSearch').value;
    var searchContent = searchContentE.value;
    var searchCheckbox = "";

    if (checkboxSearchE.checked) {
        searchCheckbox = "checked";
    }
//    console.log(filterDateValue + " " + filterStatusValue + " " + filterSearchValue + " Content: " + searchContent);

    loadUserFilter(pageSize, pageNumber, filterDateValue, filterStatusValue, filterSearchValue, searchContent, searchCheckbox);
}



var updateInfo = function () {
    var passwordInputE = document.querySelector(".items #password");
    var nameInputE = document.querySelector(".items #name");
    var phoneInputE = document.querySelector(".items #phone");
    var radioButtonsE = document.querySelectorAll('input[name="group_Status"]');
    var selectValueDB;

    for (var i = 0; i < radioButtonsE.length; i++) {
        if (radioButtonsE[i].dataset.datasql == "checked") {
            selectValueDB = radioButtonsE[i].value;
            break;
        }
    }

    var userId = document.getElementById("userId").value;
    var passwordInput = passwordInputE.value;
    var nameInput = nameInputE.value;
    var phoneInput = phoneInputE.value;
    var selectedValue;
    for (var i = 0; i < radioButtonsE.length; i++) {
        if (radioButtonsE[i].checked) {
            selectedValue = radioButtonsE[i].value;
            break;
        }
    }

    if (nameInput == "" || phoneInput == "") {

        notificationContainer.style.display = 'flex';
        var htmls = `<div class="icon" style="color: yellow"> 
                        <!--<ion-icon name="checkmark-circle-outline"></ion-icon>-->
                        <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>Dữ liệu đang để trống, Vui lòng kiểm tra lại</h3>

                    <div class="buttons">
                        <button data-id="30" onclick="confirmUpdateFalse()"  class="btn">OK</button>
                    </div>`;
        notificationContent.innerHTML = htmls;
        animationFade_Down(notificationContent);
    } else if (passwordInput != "" || nameInput != nameInputE.dataset.datasql
            || phoneInput != phoneInputE.dataset.datasql
            || selectedValue != selectValueDB)
    {

        $.ajax({
            url: './updateUserController', // Đường dẫn đến homeController
            method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
            data: {
                passwordInput: passwordInput,
                nameInput: nameInput,
                phoneInput: phoneInput,
                selectedValue: selectedValue,
                userId: userId
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
//        console.log("Khong co gi xay ra")

        notificationContainer.style.display = 'flex';

        var htmls = `<div class="icon" style="color: yellow"> 
                        <!--<ion-icon name="checkmark-circle-outline"></ion-icon>-->
                        <ion-icon name="warning-outline"></ion-icon>
                    </div>
                    <h3>Không có dữ liệu mới để Update, Vui lòng kiểm tra lại</h3>

                    <div class="buttons">
                        <button data-id="30" onclick="confirmUpdateFalse()"  class="btn">OK</button>
                    </div>`;
        notificationContent.innerHTML = htmls;

        animationFade_Down(notificationContent);
    }
}
var confirmUpdateFalse = function () {
    animationFade_Up(notificationContainer, notificationContent);
}

// thông báo cập nhật thành công và sau đó click ok để hiển thị thông tin mới 

var confirmUpdateSuccess = function (button) {
    var userId = button.getAttribute("data-id");

    animationFade_Up(notificationContainer, notificationContent);

    $.ajax({
        url: "./manageUserController",
        type: "post",
        data: {
            userId: userId
        },
        success: function (data) {
            contentUserDetailE.innerHTML = data.htmls;

            containerUserDetailE.style.display = "flex";
//            animationFade_Down(contentUserDetailE);

            var createdAtElement = document.getElementById("createdAt");

            createdAtElement.value = formatDate(createdAtElement.value);

            var roleValueE = document.getElementById("role");

            roleValueE.value = formatRole(roleValueE.value);

            var balanceE = document.getElementById("balance");
            balanceE.value = parseInt(balanceE.value).toLocaleString() + "đ";
        },
        error: function (xhr) {

        }
    });
}