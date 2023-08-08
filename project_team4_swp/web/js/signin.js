function kiemTraMatKhau() {
    matKhau = document.getElementById("matKhau").value;
    matKhauNhapLai = document.getElementById("matKhauNhapLai").value;
    if (matKhau != matKhauNhapLai) {
        document.getElementById("msg").innerHTML = "Mật khẩu không khớp!";
        return false;
    } else {
        document.getElementById("msg").innerHTML = "";
        return true;
    }
}

function xuLyChonDongY() {
    dongYDeuKhoan = document.getElementById("dongYDieuKhoan");
    if (dongYDeuKhoan.checked == true) {
        document.getElementById("submit").style.visibility = "visible";
    } else {
        document.getElementById("submit").style.visibility = "hidden";
    }
}


