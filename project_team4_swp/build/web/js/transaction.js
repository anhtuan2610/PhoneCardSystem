var transactionListE = document.querySelector(".list_container .list_items");
var paginationE = document.getElementById("pagination");

// Lấy tham số orderId từ URL
//var urlParams = new URLSearchParams(window.location.search);
//var orderId = urlParams.get('orderid');
//console.log('OrderId:', orderId);



var formatStatus = function ()
{
    var statusOrderElements = document.querySelectorAll(".item_attribute .statusOrder");

//    console.log(statusOrderElements);

    statusOrderElements.forEach(function (item) {
        var valueItem = item.textContent;
        if (valueItem == "SUCCESS")
        {
            item.textContent = "Giao dịch thành công";
            item.style.color = "green";
        } else if (valueItem == 'PROCESSING')
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



var formatMoney = function (MoneyElements)
{
//    var formatDoubleElements = document.querySelectorAll(".formatMoney");

    MoneyElements.forEach(function (i) {
        var intP = parseInt(i.textContent);
        var format = intP.toLocaleString();
        i.textContent = format + "đ";
    });
}
window.onload = function ()
{

//    formatMoney(formatMoneyListElements);

    $.ajax({
        url: './listTransaction', // Đường dẫn đến homeController
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {

            transactionListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.htmlPaging;

            formatStatus();
            formatedDate();
            var formatMoneyListElements = document.querySelectorAll(".formatMoney");

            formatMoney(formatMoneyListElements);

            buttonPagination('./listTransaction', 'post', transactionListE, paginationE);
            selectdropPagination('./listTransaction', 'post', transactionListE, paginationE);
            inputPagination('./listTransaction', 'post', transactionListE, paginationE);


        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });

}



//=========== Xử lý các filter select drop ===========

var filterSelectElements = document.querySelectorAll(".dropdown_container .custom_select");

//console.log(filterSelectElements);

filterSelectElements.forEach(function (item) {
    item.onchange = function ()
    {
        var filterTypeValue = document.getElementById('filterType').value;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;
        var pageSize = document.getElementById('select_pagination').value;
        var pageNumber = 1;

        console.log(filterTypeValue + filterDateValue +
                filterStatusValue + pageSize);

        loadTransactionFilter(pageSize, pageNumber, filterTypeValue, filterDateValue, filterStatusValue);
    }

});

var testElement = document.getElementById("test_1");

var loadTransactionFilter = function (pageSize, pageNumber, filterTypeValue, filterDateValue, filterStatusValue)
{

    $.ajax({
        url: "./listTransaction",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterTypeValue: filterTypeValue,
            filterDateValue: filterDateValue,
            filterStatusValue: filterStatusValue
        },
        success: function (data) {

            transactionListE.innerHTML = data.htmls;
            paginationE.innerHTML = data.htmlPaging;
//            testElement.innerHTML = data.sqlListTransaction;

            formatStatus();
            formatedDate();
            var formatMoneyListElements = document.querySelectorAll(".formatMoney");

            formatMoney(formatMoneyListElements);


            buttonPagination('./listTransaction', 'post', transactionListE, paginationE);
            selectdropPagination('./listTransaction', 'post', transactionListE, paginationE);
            inputPagination('./listTransaction', 'post', transactionListE, paginationE);


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
            var filterTypeValue = document.getElementById('filterType').value;
            var filterDateValue = document.getElementById('filterDate').value;
            var filterStatusValue = document.getElementById('filterStatus').value;

//            console.log(pageNumber + " size: " + pageSize);
//            console.log(filterTypeValue + " :::: " + filterCatagoryValue);


            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterTypeValue: filterTypeValue,
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue
                },
                success: function (data) {

                    transactionListE.innerHTML = data.htmls;
                    paginationE.innerHTML = data.htmlPaging;

//                    testElement.innerHTML = data.sqlListTransaction;

                    formatStatus();
                    formatedDate();
                    var formatMoneyListElements = document.querySelectorAll(".formatMoney");

                    formatMoney(formatMoneyListElements);

//Call back
                    buttonPagination('./listTransaction', 'post', listElement, pagingElement);
                    selectdropPagination('./listTransaction', 'post', listElement, pagingElement);
                    inputPagination('./listTransaction', 'post', listElement, pagingElement);

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
        var filterTypeValue = document.getElementById('filterType').value;
        var filterDateValue = document.getElementById('filterDate').value;
        var filterStatusValue = document.getElementById('filterStatus').value;

//        console.log(pageNumber + " size: " + pageSize);
//        console.log(filterTypeValue + " :::: " + filterCatagoryValue);


        $.ajax({
            url: url,
            type: method,
            data: {
                pageSize: pageSize,
                pageNumber: 1,
                filterTypeValue: filterTypeValue,
                filterDateValue: filterDateValue,
                filterStatusValue: filterStatusValue
            },
            success: function (data) {

                transactionListE.innerHTML = data.htmls;
                paginationE.innerHTML = data.htmlPaging;

//                testElement.innerHTML = data.sqlListTransaction;

                formatStatus();
                formatedDate();
                var formatMoneyListElements = document.querySelectorAll(".formatMoney");

                formatMoney(formatMoneyListElements);
//Call back
                buttonPagination('./listTransaction', 'post', listElement, pagingElement);
                selectdropPagination('./listTransaction', 'post', listElement, pagingElement);
                inputPagination('./listTransaction', 'post', listElement, pagingElement);

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
            var filterTypeValue = document.getElementById('filterType').value;
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
//            console.log(filterTypeValue + " :::: " + filterCatagoryValue);

            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterTypeValue: filterTypeValue,
                    filterDateValue: filterDateValue,
                    filterStatusValue: filterStatusValue
                },
                success: function (data) {

                    transactionListE.innerHTML = data.htmls;
                    paginationE.innerHTML = data.htmlPaging;

//                    testElement.innerHTML = data.sqlListTransaction;

                    formatStatus();
                    formatedDate();
                    var formatMoneyListElements = document.querySelectorAll(".formatMoney");

                    formatMoney(formatMoneyListElements);
//Call back
                    buttonPagination('./listTransaction', 'post', listElement, pagingElement);
                    selectdropPagination('./listTransaction', 'post', listElement, pagingElement);
                    inputPagination('./listTransaction', 'post', listElement, pagingElement);

                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });
        }

    });

}


















// Nút xem chi tiết sản phẩm

var listTransactionElement = document.querySelector(".listTransaction .listTransaction_content");
var listTransactionContentContainer = document.querySelector(".listTransaction");
var listTransactionContainerEl = document.querySelector(".listTransactionContainer");

function viewDetail(button)
{
    var id = button.getAttribute("data-id");
    console.log(id);


    $.ajax({
        url: "./viewTransactionController",
        type: "post",
        data: {
            id: id
        },
        success: function (data) {
            listTransactionElement.innerHTML = data.htmls;
            listTransactionContainerEl.style.display = "flex";
            
             var formatMoneyTranDetailElements = document.querySelectorAll(".formatMoneyTranDetail");

            formatMoney(formatMoneyTranDetailElements);


            var statusOrderElement = document.querySelector(".items .statusTran");

//            console.log(statusOrderElement);

            var valueItem = statusOrderElement.textContent;
            if (valueItem == "SUCCESS")
            {
                statusOrderElement.textContent = "Giao dịch thành công";
                statusOrderElement.style.color = "green";
            } else if (valueItem == "PROCESSING")
            {
                statusOrderElement.textContent = "Đang xử lý";
                statusOrderElement.style.color = "blue";


            } else {
                statusOrderElement.textContent = "Giao dịch thất bại";
                statusOrderElement.style.color = "red";
            }
            document.addEventListener("keyup", function (event) {
                if (event.key === 'Escape') {
                    // Xử lý sự kiện khi nhấn nút "Esc" ở đây
                    console.log('Esc key pressed!');
                    submitOkTran();
                }
            });


            animationFade_Down(listTransactionContentContainer);

        },
        error: function (xhr) {

        }
    });
}

function submitOkTran() {
//    listTransactionContainerEl.style.display = "none";
//    console.log("OK");

    var filterTypeValue = document.getElementById('filterType').value;
    var filterDateValue = document.getElementById('filterDate').value;
    var filterStatusValue = document.getElementById('filterStatus').value;
    var pageSize = document.getElementById('select_pagination').value;
    var pageNumber = document.querySelector(".page_number input").value;

    animationFade_Up(listTransactionContainerEl, listTransactionContentContainer)

    loadTransactionFilter(pageSize, pageNumber, filterTypeValue, filterDateValue, filterStatusValue);
}



var listStorageElement = document.querySelector(" .listStorage .listStorage_content");
var listStorageContentElement = document.querySelector(".listStorage ");

var listStorageContainerEl = document.querySelector(".listStorageContainer");

function getOrderDetail(button) {

    var orderId = button.getAttribute("data-id");

    console.log("OK Men");
    $.ajax({
        url: "./purchaseHistory",
        type: "post",
        data: {
            id: orderId
        },
        success: function (data) {
            listStorageElement.innerHTML = data.htmls;
            listStorageContainerEl.style.display = "flex";


            var formatDoubleElements = document.querySelectorAll(".formatDouble");

            formatMoney(formatDoubleElements);

            var statusOrderElement = document.querySelector(".items .statusOrder");

//            console.log(statusOrderElement);

            var valueItem = statusOrderElement.textContent;
//            console.log(valueItem);
            if (valueItem == "SUCCESS")
            {
                statusOrderElement.textContent = "Giao dịch thành công";
                statusOrderElement.style.color = "green";
            } else if (valueItem == "PROCESSING")
            {
                statusOrderElement.textContent = "Đang xử lý";
                statusOrderElement.style.color = "blue";


            } else {
                statusOrderElement.textContent = "Giao dịch thất bại";
                statusOrderElement.style.color = "red";
            }



            document.addEventListener("keyup", function (event) {
                if (event.key === 'Escape') {
                    // Xử lý sự kiện khi nhấn nút "Esc" ở đây
//                    console.log('Esc key pressed!');
                    submitOk();
                }
            });

            animationZoomOut(listStorageContentElement);

        },
        error: function (xhr) {

        }
    });


    document.addEventListener("keyup", function (event) {
        if (event.key === 'Escape') {
            // Xử lý sự kiện khi nhấn nút "Esc" ở đây
            console.log('Esc key pressed!');
            submitOk();
        }
    });
}

function submitOk() {
//    listStorageContainerEl.style.display = "none";

    animationZoomIn(listStorageContainerEl, listStorageContentElement);

}
