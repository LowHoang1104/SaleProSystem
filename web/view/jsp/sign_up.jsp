<%-- 
    Document   : sign_up
    Created on : May 25, 2025, 10:34:08 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Tạo tài khoản mới</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- CSS hệ thống -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <style>
            body {
                background: linear-gradient(135deg,#e3fff1 0%, #ffffff 80%);
                min-height: 100vh;
            }
            .green-card {
                border: 1.5px solid #28b463;
                border-radius: 16px;
                background: #fff;
                box-shadow: 0 6px 20px #28b46318;
                max-width: 410px;
                margin: 48px auto;
                padding: 1.8rem 2.2rem 2.1rem 2.2rem;
            }
            .signup-logo {
                display: flex;
                justify-content: center;
                margin-bottom: 22px;
            }
            .btn-green {
                background: #28b463;
                color: #fff;
                border-radius: 7px;
                font-weight: bold;
                transition: background .18s;
            }
            .btn-green:hover {
                background: #138d4b;
                color: #fff;
            }
            label {
                color: #229954;
                font-weight: 500;
            }
            .green-link {
                color: #28b463;
            }
            .green-link:hover {
                color: #138d4b;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <form action="<%=path%>/Login" method="post">
            <div class="green-card">
                <div class="signup-logo">
                    <img src="<%=path%>/view/assets/img/logo.png" alt="Logo hệ thống" width="54">
                </div>
                <h2 class="text-center mb-2" style="color:#28b463; font-weight:bold;">Tạo tài khoản mới</h2>
                <p class="text-center text-success mb-4">Đăng ký để truy cập hệ thống quản lý</p>
                <p style="color: red; font-weight: 660;">                ${error}
                </p>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Họ và tên</label>
                    <input type="text" name="name" value="${name}"  class="form-control" id="fullName" placeholder="Nhập họ và tên" required>
                </div>
                <div class="mb-3">
                    <label for="shop" class="form-label">Shop</label>
                    <input type="text" name="shop" value="${storeName}" class="form-control" placeholder="Nhập Tên Shop" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" value="${email}" name="email" class="form-control" id="email" placeholder="Nhập email" required>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel" value="${phone}" name="phone" class="form-control" id="phone" placeholder="Nhập số điện thoại" pattern="0[0-9]{9,10}" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <div class="position-relative">
                        <input name="password" type="password" class="form-control" id="password" placeholder="Chọn mật khẩu (tối thiểu 8 ký tự)" minlength="8" required>
                        <span class="show-hide-eye" onclick="togglePassword()" style="position:absolute;top:50%;right:14px;transform:translateY(-50%);cursor:pointer;color:#28b463;">
                            <i class="fas fa-eye" id="eye-icon"></i>
                        </span>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="password2" class="form-label">Nhập lại mật khẩu</label>
                    <input type="password" class="form-control" id="password2" placeholder="Nhập lại mật khẩu" minlength="8" required>
                </div>
                <button type="submit" class="btn btn-green w-100 mt-2 mb-1">Tạo tài khoản</button>
                <div class="text-center mt-3">
                    <span>Đã có tài khoản?</span> <a class="green-link" href="<%=path%>/view/jsp/LoginShopOwner.jsp">Đăng nhập</a>
                </div>
            </div>
        </form>

        <!-- Script hệ thống -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script>
                            function togglePassword() {
                                var passInput = document.getElementById("password");
                                var eyeIcon = document.getElementById("eye-icon");
                                if (passInput.type === "password") {
                                    passInput.type = "text";
                                    eyeIcon.classList.remove("fa-eye");
                                    eyeIcon.classList.add("fa-eye-slash");
                                } else {
                                    passInput.type = "password";
                                    eyeIcon.classList.remove("fa-eye-slash");
                                    eyeIcon.classList.add("fa-eye");
                                }
                            }
                            $('#signupForm').submit(function (e) {
                                e.preventDefault();
                                const pw = $('#password').val();
                                const pw2 = $('#password2').val();
                                const phone = $('#phone').val();
                                if (pw.length < 8) {
                                    alert("Mật khẩu phải có ít nhất 8 ký tự!");
                                    $('#password').focus();
                                    return;
                                }
                                if (pw !== pw2) {
                                    alert('Mật khẩu nhập lại không khớp!');
                                    $('#password2').focus();
                                    return;
                                }
                                // Kiểm tra SĐT là số, độ dài đúng (10 hoặc 11 số), bắt đầu bằng 0
                                var phonePattern = /^0\d{9,10}$/;
                                if (!phonePattern.test(phone)) {
                                    alert("Số điện thoại không hợp lệ!");
                                    $('#phone').focus();
                                    return;
                                }

                                // Xử lý gửi dữ liệu về backend tại đây...
                            });
        </script>
    </body>
</html>