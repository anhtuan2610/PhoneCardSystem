var addStorageContainer = document.querySelector(".box_container2");
var addStorageContent = document.querySelector(".box_container2 .box_wraper_content2");
var addStorageContentDetail = document.querySelector(".box_wraper_content2 .box_content2")


var notifyAddStorageContainer = document.querySelector(".notifyAddStorageContainer");
var notifyAddStorageContent = document.querySelector(".content_wraper");

// Nút add trên list product
var addStorage = function (button) {
    var productId = button.getAttribute("data-id");

//    console.log(productId);
    $.ajax({
        url: './getProductToAddStorage', // Đường dẫn đến controller
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        data: {
            productId: productId
        },
        success: function (data) {
            addStorageContentDetail.innerHTML = data.htmls;
            addStorageContainer.style.display = "flex";
            animationZoomOut(addStorageContent);
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - Get product to Add storage');
        }
    });
};

var closeAddStorage = function ()
{
    animationZoomIn(addStorageContainer, addStorageContent);
}


// Xử lý add file Excel

var addStorageExcel = function (button)
{
    var productId = button.getAttribute("data-id");
    var createdBy = button.getAttribute("data-createdby");
    var fileExcelE = document.getElementById("fileExcel");
    var file = fileExcelE.files[0];
    var reader = new FileReader();


    if (file != undefined) {
        // Khởi tạo 3 mảng lưu giá trị từ 3 cột
        var seriList = [];
        var codeList = [];
        var dateList = [];

        // Đọc nội dung file
        reader.onload = function (e) {
            var data = new Uint8Array(e.target.result);
            var workbook = XLSX.read(data, {type: 'array'});

            // Lấy dữ liệu từ sheet đầu tiên của file Excel
            var firstSheetName = workbook.SheetNames[0];
            var worksheet = workbook.Sheets[firstSheetName];
            var jsonData = XLSX.utils.sheet_to_json(worksheet);

            // Lưu giá trị từng cột vào mảng tương ứng
            jsonData.forEach(function (row) {
                seriList.push(row['seri']);
                codeList.push(row['code']);
                dateList.push(row['expiryDate']);
            });

            // Hiển thị các mảng giá trị lên console
//            console.log('Seri: ', seriList);
//            console.log('Code: ', codeList);
//            console.log('Date: ', dateList);

            var totalRecord = seriList.length;
//            console.log(totalRecord);

            if (totalRecord >= 1) {
//            Xử lý câu lệnh sql

                var checkDuplicate = "SELECT COUNT(*) FROM `storage` WHERE `product` = " + productId + " AND `seri` IN (";
                var selectDuplicate = "SELECT `seri` FROM `storage` WHERE `product` = " + productId + " AND `seri` IN (";
                var insertStorage = "INSERT INTO `storage` (`seri`, `code`, `expiryDate`, `createdBy`, `createdAt`, `product`, `status`) VALUES";

                seriList.forEach(function (item, index) {
                    if (index == (totalRecord - 1))
                    {
                        checkDuplicate += "'" + item + "');";
                        selectDuplicate += "'" + item + "');";
                    } else {
                        checkDuplicate += "'" + item + "', ";
                        selectDuplicate += "'" + item + "', ";
                    }
                }
                );
//                console.log(checkDuplicate);
//                console.log(selectDuplicate);


                $.ajax({
                    url: './checkStorageController', // Đường dẫn đến controller
                    method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
                    data: {
                        checkDuplicate: checkDuplicate,
                        selectDuplicate: selectDuplicate
                    },
                    success: function (data) {

                        var arrayDuplicate = data.arrayDuplicate;
                        var countDuplicate = data.countDuplicate;
//                        console.log(arrayDuplicate[0]);
//                        console.log(countDuplicate);

                        if (countDuplicate == totalRecord)
                        {
                            notifyAddStorageContainer.style.display = "flex";
                            var htmlSeri = ``;
                            arrayDuplicate.forEach(function (item) {
                                htmlSeri += `<tr>
                            <td>${item}</td>
                                </tr>`;
                            });

                            var htmls = `
                                            <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn">X</button>

                                            <h3 >Đã thêm thành công <span style= "color: green">0</span>  thẻ</h3>
                                            <table>
                                                        <tr>
                                                             <th>Trùng ${countDuplicate} seri thẻ:</th>
                                                        </tr>
                                                ${htmlSeri}
                                            </table>
                                             <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn_OK">OK</button>
                                                `;

                            notifyAddStorageContent.innerHTML = htmls;
                            animationFade_Down(notifyAddStorageContent);
                        } else if (countDuplicate > 1 && countDuplicate < totalRecord) {
                            var createdAt = getCurrentTime();

                            seriList.forEach(function (item, index) {
//                                console.log(arrayDuplicate.includes(item));

                                if (!arrayDuplicate.includes(item)) {
                                    if (index == (seriList.length - 1)) {
                                        insertStorage += "( '" + item + "', '" + codeList[index] + "', '" + dateList[index] + "', '" + createdBy + "',  '" + createdAt + "','" + productId + +"','1');";
                                    } else {
                                        insertStorage += "( '" + item + "', '" + codeList[index] + "', '" + dateList[index] + "', '" + createdBy + "',  '" + createdAt + "','" + productId + "', '1'),";

                                    }
                                }
                            });
                            var recordSuccess = totalRecord - countDuplicate;
                            var sqlSendServlet = "";
                            if (insertStorage.includes("NaN")) {
                                sqlSendServlet = insertStorage.replace("NaN", "") + "','1');";
                            } else {
                                sqlSendServlet = insertStorage;
                            }
                            $.ajax({
                                url: './addStorageController', // Đường dẫn đến controller
                                method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
                                data: {
                                    sql: sqlSendServlet,
                                    productId: productId,
                                    recordSuccess: recordSuccess
                                },
                                success: function (data) {
                                    status = data.status;

                                    if (status == "success") {
                                        notifyAddStorageContainer.style.display = "flex";
                                        var htmlSeri = ``;
                                        arrayDuplicate.forEach(function (item) {
                                            htmlSeri += `<tr>
                            <td>${item}</td>
                                </tr>`;
                                        });

                                        var htmls = `
                                            <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn">X</button>

                                            <h3 >Đã thêm thành công <span style= "color: green">${recordSuccess}</span>  thẻ</h3>
                                            <table>
                                                        <tr>
                                                             <th>Trùng ${countDuplicate} seri thẻ:</th>
                                                        </tr>
                                                ${htmlSeri}
                                            </table>
                                             <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn_OK">OK</button>
                                                `;

                                        notifyAddStorageContent.innerHTML = htmls;
                                        animationFade_Down(notifyAddStorageContent);
                                    }
                                },
                                error: function () {
                                    console.log('Lỗi khi gửi yêu cầu AJAX -  Add storage to DB');
                                }
                            });

//                            console.log(insertStorage);
//                            console.log(sqlSendServlet);

                        } else if (countDuplicate == 0) {

//                            console.log("CountDuplicate = 0");
                            var createdAt = getCurrentTime();

                            seriList.forEach(function (item, index) {
                                if (index == (seriList.length - 1)) {
                                    insertStorage += "( '" + item + "', '" + codeList[index] + "', '" + dateList[index] + "', '" + createdBy + "',  '" + createdAt + "','" + productId + "','1');";
                                } else {
                                    insertStorage += "( '" + item + "', '" + codeList[index] + "', '" + dateList[index] + "', '" + createdBy + "',  '" + createdAt + "','" + productId + "','1'),";

                                }
                            });


                            var recordSuccess = totalRecord - countDuplicate;
                            var sqlSendServlet = "";
                            if (insertStorage.includes("NaN")) {
                                sqlSendServlet = insertStorage.replace("NaN", "") + "','1');";
                            } else {
                                sqlSendServlet = insertStorage;
                            }

                            $.ajax({
                                url: './addStorageController', // Đường dẫn đến controller
                                method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
                                data: {
                                    sql: sqlSendServlet,
                                    productId: productId,
                                    recordSuccess: recordSuccess
                                },
                                success: function (data) {
                                    status = data.status;

                                    if (status == "success") {
                                        notifyAddStorageContainer.style.display = "flex";


                                        var htmls = `
                                            <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn">X</button>

                                            <h3 >Đã thêm thành công <span style= "color: green">${recordSuccess}</span>  thẻ</h3>
                                            <table>
                                                        <tr>
                                                             <th>Trùng ${countDuplicate} seri thẻ:</th>
                                                        </tr>
                                               
                                            </table>
                                             <button data-id="${productId}" onclick="closeNotifiyAdd(this)" class="close_btn_OK">OK</button>
                                                `;

                                        notifyAddStorageContent.innerHTML = htmls;
                                        animationFade_Down(notifyAddStorageContent);
                                    }
                                },
                                error: function () {
                                    console.log('Lỗi khi gửi yêu cầu AJAX -  Add storage to DB');
                                }
                            });

//                            console.log(insertStorage);
//                            console.log(sqlSendServlet);

                        }

                    },
                    error: function () {
                        console.log('Lỗi khi gửi yêu cầu AJAX - Check duplicate seri storage ');
                    }
                });

//                console.log(checkDuplicate);
//                console.log(selectDuplicate);



            } else {
                notificationContainer.style.display = "flex";
                notificationContent.innerHTML = `<div class="icon" style="color: yellow"> 
                                            <ion-icon name="warning-outline"></ion-icon>
                                        </div>
                                        <h3>File Excel hiện tại đang rỗng</h3>
                    
                                        <div class="buttons">
                                            <button onclick="closeNotifycation()"  class="btn">OK</button>
                                        </div>`;
                animationFade_Down(notificationContent);
            }




        };

        reader.readAsArrayBuffer(file);
    } else {
        notificationContainer.style.display = "flex";
        notificationContent.innerHTML = `<div class="icon" style="color: yellow"> 
                                            <ion-icon name="warning-outline"></ion-icon>
                                        </div>
                                        <h3>Vui lòng chọn file Excel để thêm thẻ</h3>
                    
                                        <div class="buttons">
                                            <button onclick="closeNotifycation()"  class="btn">OK</button>
                                        </div>`;
        animationFade_Down(notificationContent);
    }
};
var closeNotifycation = function () {
    animationFade_Up(notificationContainer, notificationContent);
};

var closeNotifiyAdd = function (button) {
    var productId = button.getAttribute("data-id");

    animationFade_Up(notifyAddStorageContainer, notifyAddStorageContent);

//    console.log(productId);
    $.ajax({
        url: './getProductToAddStorage', // Đường dẫn đến controller
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        data: {
            productId: productId
        },
        success: function (data) {
            addStorageContentDetail.innerHTML = data.htmls;
            addStorageContainer.style.display = "flex";
//            animationZoomOut(addStorageContent);
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - Get product to Add storage');
        }
    });
};

var getCurrentTime = function () {
    var currentDate = new Date();

// Lấy các thành phần ngày tháng
    var year = currentDate.getFullYear(); // Lấy năm
    var month = currentDate.getMonth() + 1; // Lấy tháng (chú ý phải cộng thêm 1 vì tháng trong JavaScript tính từ 0-11)
    var day = currentDate.getDate(); // Lấy ngày
    var hours = currentDate.getHours(); // Lấy giờ
    var minutes = currentDate.getMinutes(); // Lấy phút

// Định dạng lại thành chuỗi yyyymmddhhmm
    var formattedDate = year.toString() + (month < 10 ? '0' : '') + month.toString() + (day < 10 ? '0' : '') + day.toString() + (hours < 10 ? '0' : '') + hours.toString() + (minutes < 10 ? '0' : '') + minutes.toString();

    return formattedDate;
};



