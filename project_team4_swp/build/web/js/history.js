var orderListE = document.querySelector(".list_container .list_items");
var paginationE = document.getElementById("pagination");

// Lấy tham số orderId từ URL
var urlParams = new URLSearchParams(window.location.search);
var orderId = urlParams.get('orderid');
//console.log('OrderId:', orderId);



var formatStatus = function (statusE)
{
//    console.log(statusOrderElements);

    statusE.forEach(function (item) {
        var valueItem = item.textContent;
        if (valueItem == "SUCCESS")
        {
            item.textContent = "Giao dịch thành công";
            item.style.color = "green";
        } else if (valueItem == "PROCESSING")
        {
            item.textContent = "Đang xử lý";
            item.style.color = "blue";


        } else {
            item.textContent = "Giao dịch thất bại";
            item.style.color = "red";
        }
    });
}

function formatDate(timestamp) {
    var year = timestamp.substring(0, 4);
    var month = timestamp.substring(4, 6);
    var day = timestamp.substring(6, 8);
    var hour = timestamp.substring(8, 10);
    var minute = timestamp.substring(10, 12);

    var formattedDate = day + " - " + month + " - " + year + " - " + hour + ":" + minute;

    return formattedDate;
}

var formatedDate = function ()
{
    var createdAtElements = document.querySelectorAll(".createdAt");

    createdAtElements.forEach(function (i) {
        i.textContent = formatDate(i.textContent);
    });
};

var formatMoney = function (elementsMoney)
{
    elementsMoney.forEach(function (i) {
        var intP = parseInt(i.textContent).toLocaleString();
        var format = intP.toLocaleString();
        i.textContent = format + "đ";
//        console.log(intP);
    });
}


window.onload = function ()
{
//    formatMoney();
    $.ajax({
        url: './listOrder', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {

            orderListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.htmlPaging;

            var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");
            formatStatus(statusOrderElements);

            formatedDate();
            var formatDoubleElements = document.querySelectorAll(".item_attribute .formatMoneyList");
            formatMoney(formatDoubleElements);

            buttonPagination('./listOrder', 'post', orderListE, paginationE);
            selectdropPagination('./listOrder', 'post', orderListE, paginationE);
            inputPagination('./listOrder', 'post', orderListE, paginationE);

            if (orderId !== null)
            {
                // Tìm phần tử nút cần bấm
                var orderButtons = document.querySelectorAll(".button_view");

                orderButtons.forEach(function (item) {
                    var orderIdButton = item.getAttribute("data-id");
                    if (orderIdButton == orderId)
                    {
                        item.click();
                    }
                });
            }
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
   
}

//=========== Xử lý các filter select drop===========

var filterSelectElements = document.querySelectorAll(".dropdown_container .custom_select");

//console.log(filterSelectElements);

filterSelectElements.forEach(function (item) {
    item.onchange = function ()
    {
        var filterPriceValue = document.getElementById('filterPrice').value;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;

//        console.log(filterPriceValue + filterDateValue +
//                filterStatusValue + pageSize);

        loadOrderFilter(pageSize, pageNumber, filterPriceValue, filterDateValue, filterStatusValue);

    }

});

var testElement = document.getElementById("test_1");

var loadOrderFilter = function (pageSize, pageNumber, filterPriceValue, filterDateValue, filterStatusValue)
{

    $.ajax({
        url: "./listOrder",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterPriceValue: filterPriceValue,
            filterDateValue: filterDateValue,
            filterStatusValue: filterStatusValue
        },
        success: function (data) {

            orderListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.htmlPaging;
            testElement.innerHTML = data.sqlListOrder;

            var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");
            formatStatus(statusOrderElements);
            formatedDate();

            var formatDoubleElements = document.querySelectorAll(".item_attribute .formatMoneyList");
            formatMoney(formatDoubleElements);

            buttonPagination('./listOrder', 'post', orderListE, paginationE);
            selectdropPagination('./listOrder', 'post', orderListE, paginationE);
            inputPagination('./listOrder', 'post', orderListE, paginationE);


        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
}

// ========== Xử lý phần thao tác phân trang ============


// 2 Button
function buttonPagination(url, method, listElement, pagingElement) {
    var buttonPree = document.querySelectorAll(".pagination .btn_pagination button");

    buttonPree.forEach(function (button) {

        button.onclick = function () {

            var pageSize = document.getElementById('select_pagination').value;
            var pageNumberCurrent = document.querySelector(".page_number input").value;
            var pageNumber = parseInt(button.getAttribute('data-number')) + parseInt(pageNumberCurrent);
            var filterPriceValue = document.getElementById('filterPrice').value;
            var filterDateValue = document.getElementById('filterDate').value;
            var filterStatusValue = document.getElementById('filterStatus').value;

//            console.log(pageNumber + " size: " + pageSize);
//            console.log(filterPriceValue + " :::: " + filterCatagoryValue);


            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterPriceValue: filterPriceValue,
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue
                },
                success: function (data) {

                    orderListE.innerHTML = data.htmls;
                    paginationE.innerHTML = data.htmlPaging;

                    testElement.innerHTML = data.sqlListOrder;

                    var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");
                    formatStatus(statusOrderElements);
                    formatedDate();
                    var formatDoubleElements = document.querySelectorAll(".item_attribute .formatMoneyList");
                    formatMoney(formatDoubleElements);
//Call back
                    buttonPagination('./listOrder', 'post', listElement, pagingElement);
                    selectdropPagination('./listOrder', 'post', listElement, pagingElement);
                    inputPagination('./listOrder', 'post', listElement, pagingElement);

                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });


        }
    });

}

// Select drop 

function selectdropPagination(url, method, listElement, pagingElement) {
    var selectPaginationE = document.getElementById("select_pagination");

//    console.log(selectPaginationE);

    selectPaginationE.onchange = function ()
    {
        var pageSize = selectPaginationE.value;
        var pageNumber = document.querySelector(".page_number input").value;
        var filterPriceValue = document.getElementById('filterPrice').value;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;

//        console.log(pageNumber + " size: " + pageSize);
//        console.log(filterPriceValue + " :::: " + filterCatagoryValue);


        $.ajax({
            url: url,
            type: method,
            data: {
                pageSize: pageSize,
                pageNumber: 1,
                filterPriceValue: filterPriceValue,
                filterDateValue: filterDateValue,
                filterStatusValue: filterStatusValue
            },
            success: function (data) {

                orderListE.innerHTML = data.htmls;
                paginationE.innerHTML = data.htmlPaging;

                testElement.innerHTML = data.sqlListOrder;

                var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");
                formatStatus(statusOrderElements);
                formatedDate();
                var formatDoubleElements = document.querySelectorAll(".item_attribute .formatMoneyList");
                formatMoney(formatDoubleElements);

//Call back
                buttonPagination('./listOrder', 'post', listElement, pagingElement);
                selectdropPagination('./listOrder', 'post', listElement, pagingElement);
                inputPagination('./listOrder', 'post', listElement, pagingElement);

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
            var filterPriceValue = document.getElementById('filterPrice').value;
            var filterDateValue = document.getElementById('filterDate').value;
            var filterStatusValue = document.getElementById('filterStatus').value;
            var toltalNumber = parseInt(document.querySelector(".page_number span p").textContent);

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
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue
                },
                success: function (data) {

                    orderListE.innerHTML = data.htmls;
                    paginationE.innerHTML = data.htmlPaging;

                    testElement.innerHTML = data.sqlListOrder;

                    var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");
                    formatStatus(statusOrderElements);
                    formatedDate();
                    var formatDoubleElements = document.querySelectorAll(".item_attribute .formatMoneyList");
                    formatMoney(formatDoubleElements);

//Call back
                    buttonPagination('./listOrder', 'post', listElement, pagingElement);
                    selectdropPagination('./listOrder', 'post', listElement, pagingElement);
                    inputPagination('./listOrder', 'post', listElement, pagingElement);

                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });
        }

    });

}




// Nút xem chi tiết sản phẩm
var listStorageElement = document.querySelector(".listStorage .listStorage_content");
var listStorageContainerEl = document.querySelector(".listStorageContainer");
var listStorageContentElement = document.querySelector(".listStorage ");

function viewDetail(button)
{
    var id = button.getAttribute("data-id");



    $.ajax({
        url: "./purchaseHistory",
        type: "post",
        data: {
            id: id
        },
        success: function (data) {
            listStorageElement.innerHTML = data.htmls;
            listStorageContainerEl.style.display = "flex";

            var formatDoubleElements = document.querySelectorAll(".formatDouble");
            formatMoney(formatDoubleElements);

            var statusOrderElement = document.querySelectorAll(".items .statusOrder");

            formatStatus(statusOrderElement);
//            console.log(statusOrderElement);


            animationFade_Down(listStorageContentElement);


            document.addEventListener("keyup", function (event) {
                if (event.key === 'Escape') {
                    // Xử lý sự kiện khi nhấn nút "Esc" ở đây
//                    console.log('Esc key pressed!');
                    submitOk();
                }
            });

        },
        error: function (xhr) {

        }
    });
}


function submitOk() {
//    listStorageContainerEl.style.display = "none";

    var filterPriceValue = document.getElementById('filterPrice').value;
    var filterDateValue = document.getElementById('filterDate').value;
    var filterStatusValue = document.getElementById('filterStatus').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;


//    console.log(filterPriceValue + filterDateValue +
//            filterStatusValue + pageSize);

    loadOrderFilter(pageSize, pageNumber, filterPriceValue, filterDateValue, filterStatusValue);


    animationFade_Up(listStorageContainerEl, listStorageContentElement);

//    console.log("OK")
}





