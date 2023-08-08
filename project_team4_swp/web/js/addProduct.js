var notificationContainer = document.getElementById('notification');
var notificationContent = document.querySelector("#notification .notificationBox")


var imagePreviewE = document.getElementById("preview");
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
// Xử lý khi focus tiền
var priceElement = document.getElementById('price');
console.log(priceElement);
priceElement.addEventListener('focus', function () {
    priceElement.value = priceElement.value.replace('đ', '').replace(/,/g, '').replace(/\./g, '');
});
priceElement.addEventListener('blur', function () {

    var valueInput = priceElement.value;
//    console.log(isNaN(valueInput))

    if (valueInput == "" || valueInput == null) {
        priceElement.value = "";
        priceElement.placeholder = "Vui lòng nhập giá tiền cho sản phẩm";
    } else
    {
        priceElement.value = parseInt(valueInput).toLocaleString() + "đ";
    }

    if (isNaN(valueInput) || parseInt(valueInput) < 0) {
        priceElement.value = "";
        priceElement.placeholder = "Vui lòng nhập kiểu dữ liệu số";
    }
});
// Xử lý xem ảnh khi chọn file từ máy
function displayImage(event) {
    var input = event.target;
    var containerImageE = document.getElementById("preview");
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


// Load list category từ data
var loadListFilter = function () {
    var listCategoryFilterE = document.getElementById("select_category_detail");
    $.ajax({
        url: './getListFilter', // Đường dẫn đến controller
        method: 'GET', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {
            listCategoryFilterE.innerHTML = data.listCategory;
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX - List Price Product');
        }
    });
}

window.onload = function () {
    loadListFilter();
}

var addNewProduct = function ()
{
    var productName = document.getElementById("nameProduct").value;
    var categorySelect = document.getElementById("select_category_detail").value;
    var categoryOther = document.getElementById("category_other").value;
    var productPrice = document.getElementById('price').value.replace('đ', '').replace(/,/g, '').replace(/\./g, '');

    console.log(productPrice);

    var productDes = document.getElementById("text_des").value;
//    xỬ LÝ ẢNH 
    var fileInput = document.getElementById('avatar');
    var file = fileInput.files[0];
    var imageName = "";

//    console.log(productPrice);


    if (file != undefined) {
        imageName = fileInput.files[0].name;
    }

    var categoryChose = "";
    if (categoryOther != "") {
        categoryChose = categoryOther;
    } else if (categorySelect != "category" && categoryOther == "") {
        categoryChose = categorySelect;
    }

    if (productName == "" || categoryChose == ""
            || productPrice == "" || productDes == ""
            || imageName == "") {
        notificationContainer.style.display = "flex";
        notificationContent.innerHTML = `<div class="icon" style="color: yellow"> 
                                            <ion-icon name="warning-outline"></ion-icon>
                                        </div>
                                        <h3>Vui lòng điền đầy đủ thông tin của sản phẩm.</h3>
                    
                                        <div class="buttons">
                                            <button data-id="30" onclick="closeNotify()"  class="btn">OK</button>
                                        </div>`;
        animationFade_Down(notificationContent);
    } else {

        console.log("start change");

        $.ajax({
            url: './addProductController', // Đường dẫn đến 
            method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
            data: {
                productName: productName,
                categoryChose: categoryChose,
                productPrice: productPrice,
                productDes: productDes,
                imageName: imageName
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

    }
}
var closeNotify = function ()
{
    animationFade_Up(notificationContainer, notificationContent);

}