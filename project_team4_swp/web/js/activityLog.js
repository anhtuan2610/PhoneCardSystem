
// =============  Activity Log ===============
var activityLogContainerE = document.querySelector(".content_activity");
var contentActivitWraperE = document.querySelector(".content_activity .list_wraper_activity");
var listActivityLogE = document.querySelector(".list_wraper_activity .list_content_activity");
var paginationActivityE = document.getElementById("pagination_activity")
var btnCloseListactivityE = document.querySelector(".list_wraper_activity .close-btn");

var loadListActivity = function (userId)
{

    $.ajax({
        url: "./userActivityLog",
        type: "get",
        data: {
            userId: userId
        },
        success: function (data) {
            listActivityLogE.innerHTML = data.htmls;
            paginationActivityE.innerHTML = data.pagination;
            btnCloseListactivityE.dataset.id = data.userId;

            activityLogContainerE.style.display = "flex";

            animationFade_Down(contentActivitWraperE);

            var dateElements = document.querySelectorAll(".wraper_item_activity .item .date_activity ");

            dateElements.forEach(function (item) {
                item.textContent = formatDate(item.textContent);
            });

            buttonPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);
            selectdropPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);
            inputPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - Load list activityLog');
        }
    });
}

var viewActivityLog = function (button) {
    var userId = button.getAttribute("data-id");

    var filterTypeE = document.getElementById("filterType");
    filterTypeE.value = "statusUser";

    var filterDateActivityLogE = document.getElementById("filterDateActivityLog_activity");
    filterDateActivityLogE.value = "alltime";

    loadListActivity(userId);
}


var closeActivityList = function ()
{
    animationFade_Up(activityLogContainerE, contentActivitWraperE);

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
}

var viewDetailActivityContainerE = document.querySelector(".box_container2");
var viewDetailActivityWraperE = document.querySelector(".box_container2 .box_wraper_content2");
var viewDetailActivityContentE = document.querySelector(".box_container2 .box_wraper_content2 .box_content2");

var viewActivityDetail = function (button)
{
    var activityLogId = button.getAttribute("data-id");
    var userId = btnCloseListactivityE.dataset.id;

    $.ajax({
        url: "./viewDetailActivityLog",
        type: "get",
        data: {
            activityLogId: activityLogId,
            userId: userId
        },
        success: function (data) {
            viewDetailActivityContentE.innerHTML = data.htmls;

            viewDetailActivityContainerE.style.display = "flex";

            animationZoomOut(viewDetailActivityWraperE);
            
            var dateCreateE = document.querySelector(".box_content2 .items .date_createdAt");
            
            dateCreateE.value = formatDate(dateCreateE.value);
            
        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - View detail Activity');
        }
    });
}

var closeDetailActivity = function (){
    animationZoomIn(viewDetailActivityContainerE,viewDetailActivityWraperE);
}


// ============ Phân trang =================
// 
// 2 Button
function buttonPagination_activity(url, method, listElement, pagingElement) {
    var buttonPree = document.querySelectorAll(".pagination_activity .btn_pagination_activity  button");

    buttonPree.forEach(function (button) {

        button.onclick = function () {

            var pageSize = document.getElementById('select_pagination_activity').value;
            var pageNumberCurrent = document.querySelector(".page_number_activity input").value;
            var pageNumber = parseInt(button.getAttribute('data-number')) + parseInt(pageNumberCurrent);
            var filterTypeValue = document.getElementById('filterType').value;
            var filterDateValue = document.getElementById('filterDateActivityLog_activity').value;
            var userId = btnCloseListactivityE.dataset.id;


//            console.log(pageNumber + pageSize + filterTypeValue + filterDateValue + userId);



            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterTypeValue: filterTypeValue,
                    filterDateValue: filterDateValue,
                    userId: userId
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;
                    pagingElement.innerHTML = data.pagination;
                    btnCloseListactivityE.dataset.id = data.userId;

                    var dateElements = document.querySelectorAll(".wraper_item_activity .item .date_activity ");

                    dateElements.forEach(function (item) {
                        item.textContent = formatDate(item.textContent);
                    });


//Call back : Bởi sau mỗi lần bấm thì sẽ generate ra 1 giao diện và cần add lại Event
                    buttonPagination_activity(url, method, listElement, pagingElement);
                    selectdropPagination_activity(url, method, listElement, pagingElement);
                    inputPagination_activity(url, method, listElement, pagingElement);

                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });




        }
    });

}

// Select

function selectdropPagination_activity(url, method, listElement, pagingElement) {
    var selectPaginationActivityE = document.getElementById("select_pagination_activity");

//    console.log(selectPaginationActivityE);

    selectPaginationActivityE.onchange = function ()
    {
        var pageSize = document.getElementById('select_pagination_activity').value;
        var pageNumber = document.querySelector(".page_number_activity input").value;
        var filterTypeValue = document.getElementById('filterType').value;
        var filterDateValue = document.getElementById('filterDateActivityLog_activity').value;
        var userId = btnCloseListactivityE.dataset.id;

//        console.log(pageNumber + pageSize + filterTypeValue + filterDateValue + userId);

        $.ajax({
            url: url,
            type: method,
            data: {
                pageSize: pageSize,
                pageNumber: pageNumber,
                filterTypeValue: filterTypeValue,
                filterDateValue: filterDateValue,
                userId: userId
            },
            success: function (data) {

                listElement.innerHTML = data.htmls;
                pagingElement.innerHTML = data.pagination;
                btnCloseListactivityE.dataset.id = data.userId;

                var dateElements = document.querySelectorAll(".wraper_item_activity .item .date_activity ");

                dateElements.forEach(function (item) {
                    item.textContent = formatDate(item.textContent);
                });


//Call back : Bởi sau mỗi lần bấm thì sẽ generate ra 1 giao diện và cần add lại Event
                buttonPagination_activity(url, method, listElement, pagingElement);
                selectdropPagination_activity(url, method, listElement, pagingElement);
                inputPagination_activity(url, method, listElement, pagingElement);

            },
            error: function (xhr) {
                console.log('Lỗi khi gửi yêu cầu AJAX');
            }
        });

    }


}


// Input

function inputPagination_activity(url, method, listElement, pagingElement) {

    var inputPaginationActivityE = document.querySelector(".page_number_activity input");

//    console.log(inputPaginationActivityE);


    inputPaginationActivityE.addEventListener("keypress", function (event) {
        if (event.key === "Enter")
        {
            var pageSize = document.getElementById('select_pagination_activity').value;
            var pageNumber = document.querySelector(".page_number_activity input").value;
            var filterTypeValue = document.getElementById('filterType').value;
            var filterDateValue = document.getElementById('filterDateActivityLog_activity').value;
            var userId = btnCloseListactivityE.dataset.id;
            var toltalNumber = parseInt(document.querySelector(".page_number_activity span p").textContent);

//            console.log(typeof pageNumber + " size: " + typeof toltalNumber);

            if (pageNumber <= 0)
            {
                pageNumber = 1;
            } else if (pageNumber >= toltalNumber)
            {
                pageNumber = toltalNumber;
            }
            console.log(pageNumber + pageSize + filterTypeValue + filterDateValue + userId);

//            console.log(pageNumber + " size: " + typeof  toltalNumber);
//            console.log(filterPriceValue + " :::: " + filterCatagoryValue);


            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterTypeValue: filterTypeValue,
                    filterDateValue: filterDateValue,
                    userId: userId
                },
                success: function (data) {

                    listElement.innerHTML = data.htmls;
                    pagingElement.innerHTML = data.pagination;
                    btnCloseListactivityE.dataset.id = data.userId;

                    var dateElements = document.querySelectorAll(".wraper_item_activity .item .date_activity ");

                    dateElements.forEach(function (item) {
                        item.textContent = formatDate(item.textContent);
                    });
// Call back
                    buttonPagination_activity(url, method, listElement, pagingElement);
                    selectdropPagination_activity(url, method, listElement, pagingElement);
                    inputPagination_activity(url, method, listElement, pagingElement);

                },
                error: function (xhr) {
                    console.log('Lỗi khi gửi yêu cầu AJAX');
                }
            });
        }

    });

}

// Xử lý filter select drop
var customSelectActivityElement = document.querySelectorAll(".custom_select_activity");


customSelectActivityElement.forEach(function (item) {
    item.onchange = function () {


        var pageSize = document.getElementById('select_pagination_activity').value;
        var pageNumber = 1;
        var filterTypeValue = document.getElementById('filterType').value;
        var filterDateValue = document.getElementById('filterDateActivityLog_activity').value;
        var userId = btnCloseListactivityE.dataset.id;



        loadActivityLogFilter(pageSize, pageNumber, filterTypeValue, filterDateValue, userId);

    };

});

var loadActivityLogFilter = function (pageSize, pageNumber, filterTypeValue, filterDateValue, userId)
{

    $.ajax({
        url: "./userActivityLog",
        type: "post",
        data: {
            pageSize: pageSize,
            pageNumber: pageNumber,
            filterTypeValue: filterTypeValue,
            filterDateValue: filterDateValue,
            userId: userId
        },
        success: function (data) {

            listActivityLogE.innerHTML = data.htmls;
            paginationActivityE.innerHTML = data.pagination;
            btnCloseListactivityE.dataset.id = data.userId;

            var dateElements = document.querySelectorAll(".wraper_item_activity .item .date_activity ");

            dateElements.forEach(function (item) {
                item.textContent = formatDate(item.textContent);
            });

            buttonPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);
            selectdropPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);
            inputPagination_activity('./userActivityLog', 'post', listActivityLogE, paginationActivityE);

        },
        error: function (xhr) {
            console.log('Lỗi khi gửi yêu cầu AJAX - Select drop filter');
        }
    });
}