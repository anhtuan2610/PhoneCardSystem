

// 2 Button
function buttonPagination(url, method, listElement, pagingElement) {
    var buttonPree = document.querySelectorAll(".pagination .btn_pagination button");

    buttonPree.forEach(function (button) {

        button.onclick = function () {

            var pageSize = document.getElementById('select_pagination').value;
            var pageNumberCurrent = document.querySelector(".page_number input").value;
            var pageNumber = parseInt(button.getAttribute('data-number')) + parseInt(pageNumberCurrent);
            var filterPriceValue = document.getElementById('select_filter_price').value;
            var filterCatagoryValue = document.getElementById('select_filter_category').value;
            var searchContent = searchElement.value;


//            console.log(pageNumber + " size: " + pageSize);
//            console.log(filterPriceValue + " :::: " + filterCatagoryValue);


            $.ajax({
                url: url,
                type: method,
                data: {
                    pageSize: pageSize,
                    pageNumber: pageNumber,
                    filterPriceValue: filterPriceValue,
                    filterCatagoryValue: filterCatagoryValue,
                    searchContent: searchContent
                },
                success: function (data) {

                    listElement.innerHTML = data.html;

                    pagingElement.innerHTML = data.htmlPaging;

                    document.getElementById('testCuocDoi').innerHTML = data.pageSizeString;

                    var formatDoubleElements = document.querySelectorAll(".formatDouble span");


                    formatDoubleElements.forEach(function (i) {
                        var intP = parseInt(i.textContent);
                        var format = intP.toLocaleString();
                        i.textContent = format + "đ";
                    });


                    var pageSize = data.pagesize;

                    loadListPagesizeFilter(pageSize);

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
        var filterCatagoryValue = document.getElementById('select_filter_category').value;
        var searchContent = searchElement.value;
//        console.log(pageNumber + " size: " + pageSize);
//        console.log(filterPriceValue + " :::: " + filterCatagoryValue);


        $.ajax({
            url: url,
            type: method,
            data: {
                pageSize: pageSize,
                pageNumber: 1,
                filterPriceValue: filterPriceValue,
                filterCatagoryValue: filterCatagoryValue,
                searchContent: searchContent
            },
            success: function (data) {

                listElement.innerHTML = data.html;

                pagingElement.innerHTML = data.htmlPaging;

                document.getElementById('testCuocDoi').innerHTML = data.pageSizeString;

                var formatDoubleElements = document.querySelectorAll(".formatDouble span");


                formatDoubleElements.forEach(function (i) {
                    var intP = parseInt(i.textContent);
                    var format = intP.toLocaleString();
                    i.textContent = format + "đ";
                });


                var pageSize = data.pagesize;

                loadListPagesizeFilter(pageSize);

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
            var filterPriceValue = document.getElementById('select_filter_price').value;
            var filterCatagoryValue = document.getElementById('select_filter_category').value;
            var toltalNumber = parseInt(document.querySelector(".page_number span p").textContent);
            var searchContent = searchElement.value;
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
                    filterCatagoryValue: filterCatagoryValue,
                    searchContent: searchContent
                },
                success: function (data) {

                    listElement.innerHTML = data.html;

                    pagingElement.innerHTML = data.htmlPaging;

                    document.getElementById('testCuocDoi').innerHTML = data.pageSizeString;

                    var formatDoubleElements = document.querySelectorAll(".formatDouble span");


                    formatDoubleElements.forEach(function (i) {
                        var intP = parseInt(i.textContent);
                        var format = intP.toLocaleString();
                        i.textContent = format + "đ";
                    });

                    var pageSize = data.pagesize;

                    loadListPagesizeFilter(pageSize);
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
//    inputPaginationE.addEventListener("blur", inputPaging());



}
