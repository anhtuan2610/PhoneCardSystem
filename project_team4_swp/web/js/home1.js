
var avatarEl = document.querySelector(".profile");
var menuProfileEl = document.querySelector(".header .menu_right");
var menuProfileBlur = document.querySelector(".menu_right .menu_blur");

var menuBtnLeftE = document.querySelector(".header .header-left");
var menuLeftContainerE = document.querySelector(".header .menu_left");
var menuLeftBlur = document.querySelector(".menu_left .menu_blur");

menuBtnLeftE.addEventListener("click", function (){
//    console.log("Menu left");
    if (!menuLeftContainerE.className.includes("active")) {

        menuLeftContainerE.className += " active";

    }

    menuLeftBlur.addEventListener("click", function () {
        if (menuLeftContainerE.className.includes("active")) {

            menuLeftContainerE.className = menuLeftContainerE.className.replace("active", "").trim();

        }
    });
});

// Check role để hiển thị menu cho người dùng
var menuListItem = document.querySelector(".menu_left ul");

document.addEventListener("DOMContentLoaded", function() {
  // Đoạn mã xử lý sau khi trang web được tải
  console.log("Trang web đã được tải.");
  // Gọi các hàm khác, thao tác với các phần tử trên trang, v.v.
  $.ajax({
        url: './menuRightController', // Đường dẫn đến homeController
        method: 'POST', // Phương thức của yêu cầu (có thể là GET hoặc POST tùy thuộc vào thiết kế của homeController)
        success: function (data) {
            menuListItem.innerHTML = data.htmls;
           
        },
        error: function () {
            console.log('Lỗi khi gửi yêu cầu AJAX');
        }
    });
});



avatarEl.addEventListener("click", function () {
    if (!menuProfileEl.className.includes("active")) {

        menuProfileEl.className += " active";

    }

    menuProfileBlur.addEventListener("click", function () {
        if (menuProfileEl.className.includes("active")) {

            menuProfileEl.className = menuProfileEl.className.replace("active", "").trim();

        }
    });
//    console.log(menuProfileBlur);
});




//console.log(avatarEl);

var balanceElement = document.querySelector(".balance span");
var balance = balanceElement.textContent;

var format = parseInt(balance).toLocaleString() + "đ";

balanceElement.textContent = format;
//console.log(typeof balanceElement.textContent);