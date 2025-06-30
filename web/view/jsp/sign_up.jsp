<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="Hệ thống quản lý">
        <meta name="keywords" content="admin, bootstrap, business, corporate, creative, management">
        <meta name="author" content="ADMIN">
        <meta name="robots" content="noindex, nofollow">
        <title>Đăng ký tài khoản</title>
        <style>
            .google-btn {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
                width: 100%;
                padding: 12px 15px;
                border: 1px solid #ddd;
                border-radius: 6px;
                background-color: #fff;
                cursor: pointer;
                font-size: 15px;
                font-weight: 500;
                transition: all 0.3s ease;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                margin: 15px 0;
            }

            .google-btn img {
                width: 20px;
                height: 20px;
            }

            .google-btn:hover {
                background-color: #f5f5f5;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
            }

            .divider {
                display: flex;
                align-items: center;
                text-align: center;
                margin: 15px 0;
            }

            .divider::before,
            .divider::after {
                content: '';
                flex: 1;
                border-bottom: 1px solid #ddd;
            }

            .divider span {
                padding: 0 10px;
                color: #666;
                font-size: 14px;
            }
        </style>
    </style>
    <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">
    <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
</head>
<body class="account-page">      
    <div class="main-wrapper">
        <div class="account-content">
            <div class="login-wrapper">
                <div class="login-content">
                    <div class="login-userset">
                        <div class="login-logo">
                            <img src="<%=path%>/view/assets/img/logo.png" alt="Logo hệ thống">
                        </div>
                        <div class="login-userheading">
                            <h3>Tạo tài khoản mới</h3>
                            <h4>Đăng ký để truy cập hệ thống quản lý</h4>
                        </div>
                        <form id="signupForm" action="<%=path%>/Login" method="post">
                            <div class="form-login">
                                <label>Họ và tên</label>
                                <div class="form-addons">
                                    <input type="text" name="name" value="${name}" placeholder="Nhập họ và tên" required>
                                    <img src="<%=path%>/view/assets/img/icons/users1.svg" alt="user">
                                </div>
                            </div>
                            <div class="form-login">
                                <label>Shop</label>
                                <div class="form-addons">
                                    <input type="text" name="shop" value="${storeName}" placeholder="Nhập tên shop" required>
                                    <img src="<%=path%>/view/assets/img/icons/store.svg" alt="store">
                                </div>
                            </div>
                            <div class="form-login">
                                <label>Email</label>
                                <div class="form-addons">
                                    <input type="email" name="email" value="${email}" placeholder="Nhập email" required>
                                    <img src="<%=path%>/view/assets/img/icons/mail.svg" alt="email">
                                </div>
                            </div>
                            <div class="form-login">
                                <label>Số điện thoại</label>
                                <div class="form-addons">
                                    <input type="tel" name="phone" value="${phone}" placeholder="Nhập số điện thoại" pattern="0[0-9]{9,10}" required>
                                    <img src="<%=path%>/view/assets/img/icons/phone.svg" alt="phone">
                                </div>
                            </div>
                            <div class="form-login">
                                <label>Mật khẩu</label>
                                <div class="pass-group">
                                    <input type="password" name="password" class="pass-input" placeholder="Chọn mật khẩu (tối thiểu 8 ký tự)" minlength="8" required>
                                    <span class="fas toggle-password fa-eye-slash" onclick="togglePassword()"></span>
                                </div>
                            </div>
                            <div class="form-login">
                                <label>Nhập lại mật khẩu</label>
                                <div class="pass-group">
                                    <input type="password" id="password2" class="pass-input" placeholder="Nhập lại mật khẩu" minlength="8" required>
                                    <span class="fas toggle-password fa-eye-slash" onclick="togglePassword2()"></span>
                                </div>
                            </div>
                            <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                            <% } %>
                            <div class="form-login">
                                <button type="submit" class="btn btn-login">Tạo tài khoản</button>
                            </div>
                            <div class="divider">
                                <span>Hoặc</span>
                            </div>
                            <div class="form-login">
                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/Mg2/GoogleAuthController&response_type=code&client_id=658582390145-7a1kldnc1o52bchroovprre47v6jqoft.apps.googleusercontent.com&approval_prompt=force" class="google-btn">
                                    <img src="https://www.svgrepo.com/show/475656/google-color.svg" alt="Google icon" />
                                    <span>Tiếp tục với Google</span>
                                </a>
                            </div>
                            <div class="signinform text-center">
                                <h4>Đã có tài khoản? <a href="<%=path%>/view/jsp/Homepage.jsp" class="hover-a">Đăng nhập</a></h4>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="login-img">
                    <img src="<%=path%>/view/assets/img/login.jpg" alt="background">
                </div>
            </div>
        </div>
    </div>

    <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
    <script src="<%=path%>/view/assets/js/feather.min.js"></script>
    <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
    <script src="<%=path%>/view/assets/js/script.js"></script>
    <script>
                                        .google - btn {
                                        display: flex;
                                        align - items: center;
                                        justify - content: center;
                                        gap: 10px;
                                        width: 100 % ;
                                        padding: 12px 15px;
                                        border: 1px solid #ddd;
                                        border - radius: 6px;
                                        background - color: #fff;
                                        cursor: pointer;
                                        font - size: 15px;
                                        font - weight: 500;
                                        transition: all 0.3s ease;
                                        box - shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                                        margin: 15px 0;
                                        text - decoration: none; /* Thêm dòng này */
                                        color: #333; /* Thêm dòng này */
                                        }

                                        .google - btn:hover {
                                        background - color: #f5f5f5;
                                        box - shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
                                        text - decoration: none; /* Thêm dòng này */
                                        color: #333; /* Thêm dòng này */
                                        }
                                        function togglePassword() {
                                        var passInput = document.querySelector('input[name="password"]');
                                        var eyeIcon = passInput.nextElementSibling;
                                        if (passInput.type === "password") {
                                        passInput.type = "text";
                                        eyeIcon.classList.remove("fa-eye-slash");
                                        eyeIcon.classList.add("fa-eye");
                                        } else {
                                        passInput.type = "password";
                                        eyeIcon.classList.remove("fa-eye");
                                        eyeIcon.classList.add("fa-eye-slash");
                                        }
                                        }

                                        function togglePassword2() {
                                        var passInput = document.getElementById("password2");
                                        var eyeIcon = passInput.nextElementSibling;
                                        if (passInput.type === "password") {
                                        passInput.type = "text";
                                        eyeIcon.classList.remove("fa-eye-slash");
                                        eyeIcon.classList.add("fa-eye");
                                        } else {
                                        passInput.type = "password";
                                        eyeIcon.classList.remove("fa-eye");
                                        eyeIcon.classList.add("fa-eye-slash");
                                        }
                                        }

                                        $('#signupForm').submit(function (e) {
                                        const pw = $('input[name="password"]').val();
                                        const pw2 = $('#password2').val();
                                        const phone = $('input[name="phone"]').val();
                                        if (pw.length < 8) {
                                        alert("Mật khẩu phải có ít nhất 8 ký tự!");
                                        $('input[name="password"]').focus();
                                        e.preventDefault();
                                        return;
                                        }
                                        if (pw !== pw2) {
                                        alert('Mật khẩu nhập lại không khớp!');
                                        $('#password2').focus();
                                        e.preventDefault();
                                        return;
                                        }
                                        var phonePattern = /^0\d{9,10}$/;
                                        if (!phonePattern.test(phone)) {
                                        alert("Số điện thoại không hợp lệ!");
                                        $('input[name="phone"]').focus();
                                        e.preventDefault();
                                        return;
                                        }
                                        });
    </script>
</body>
</html>