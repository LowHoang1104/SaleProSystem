<!DOCTYPE html>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập hệ thống</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <style>
            .input-icon-right {
                position: absolute;
                top: 50%;
                right: 16px;
                transform: translateY(-50%);
                color: #bbb;
                font-size: 1rem;
                pointer-events: none;
            }
            .form-login .form-control {
                padding-right: 38px;
            }
            /* Con mắt hiện/ẩn mật khẩu */
            .show-hide-eye {
                position: absolute;
                top: 50%;
                right: 16px;
                transform: translateY(-50%);
                cursor: pointer;
                color: #28b463;
                font-size: 1.1rem;
                z-index: 2;
            }
            .show-hide-eye:hover {
                color: #138d4b;
            }
            .input-password-group input {
                padding-right: 38px;
            }
        </style>
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
                                <h3>Đăng nhập hệ thống</h3>
                                <h2 style="color: green; text-align: center">Hello <%= session.getAttribute("currentShop") %></h2>
                                <%
                                    String error = (String) request.getAttribute("Error");
                                    if (error != null && !error.isEmpty()) {
                                %>
                                <h4 style="color: red"><%= error %></h4>
                                <%
                                    } else {
                                %>
                                <h4>Vui lòng nhập Số điện thoại &amp; Mật khẩu</h4>
                                <%
                                    }
                                %>
                            </div>
                            <form action="<%=path%>/LoginOnwerShop" method="get" >
                                <!-- SỐ ĐIỆN THOẠI (tài khoản): Icon bên phải -->
                                <div class="form-login">
                                    <label>Số điện thoại</label>
                                    <div class="position-relative">
                                        <input name="account" type="text" id="account" class="form-control" placeholder="Nhập tài khoản" >
                                        <span class="input-icon-right"><i class="fas fa-user"></i></span>
                                    </div>
                                </div>
                                <!-- MẬT KHẨU: Con mắt hiện/ẩn, yêu cầu tối thiểu 8 ký tự -->
                                <div class="form-login">
                                    <label>Mật khẩu</label>
                                    <div class="position-relative input-password-group">
                                        <input type="password" name="password" id="password" class="form-control" placeholder="Nhập mật khẩu" minlength="8" required>
                                        <span class="show-hide-eye" onclick="togglePassword()" title="Hiện/ẩn mật khẩu">
                                            <i class="fas fa-eye" id="eye-icon"></i>
                                        </span>
                                    </div>
                                    <small class="form-text text-muted">Mật khẩu phải có ít nhất 8 ký tự</small>
                                </div>
                                <!-- Hai nút Quản lý & Bán hàng -->
                                <div class="row" style="margin-top:18px; margin-bottom:8px;">
                                    <div class="col-6">
                                        <button name="login" type="submit" value="1" class="btn btn-success w-100 fw-bold" id="btnQuanLy">
                                            <i class="fas fa-user-shield me-1"></i> Quản lý
                                        </button>
                                    </div>
                                    <div class="col-6">
                                        <button name="login" value="2" type="submit" class="btn btn-outline-success w-100 fw-bold" id="btnBanHang">
                                            <i class="fas fa-store-alt me-1"></i> Bán hàng
                                        </button>
                                    </div>
                                </div>
                            </form>
                            <div class="login-forgetp">
                                <a href="forgetpassword.html">Quên mật khẩu?</a>
                            </div>
                        </div>
                    </div>
                    <div class="login-img">
                        <img src="<%=path%>/view/assets/img/login.jpg" alt="background">
                    </div>
                </div>
            </div>
        </div>
        <!-- JS hệ thống giữ nguyên -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/feather.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%=path%>/view/assets/js/script.js"></script>
        <script>
                                            // Ẩn/hiện mật khẩu
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
                                            // Thêm xác thực mật khẩu phải ít nhất 8 ký tự khi submit
                                            document.getElementById("loginForm").addEventListener("submit", function (e) {
                                                var passInput = document.getElementById("password");
                                                if (passInput.value.length < 8) {
                                                    alert("Mật khẩu phải có ít nhất 8 ký tự!");
                                                    passInput.focus();
                                                    e.preventDefault();
                                                }
                                                // Có thể kiểm tra thêm số điện thoại nếu cần.
                                            });
        </script>
    </body>
</html>