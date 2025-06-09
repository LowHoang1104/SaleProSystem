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
        <title>Nhập tên shop</title>
        <style>
            body {
                background: linear-gradient(135deg, #e3fff1 0%, #ffffff 100%);
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .shop-container {
                display: flex;
                max-width: 1000px;
                margin: 20px;
                background: #fff;
                border-radius: 24px;
                overflow: hidden;
                box-shadow: 0 15px 40px rgba(40,180,99,0.1);
            }
            .shop-image {
                flex: 1;
                background: linear-gradient(135deg, #28b463 0%, #138d4b 100%);
                padding: 40px;
                display: flex;
                flex-direction: column;
                justify-content: center;
                color: #fff;
                position: relative;
                overflow: hidden;
            }
            .shop-image::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: url('<%=path%>/view/assets/img/pattern.png');
                opacity: 0.1;
            }
            .shop-image h2 {
                font-size: 32px;
                font-weight: 700;
                margin-bottom: 15px;
                position: relative;
            }
            .shop-image p {
                font-size: 16px;
                opacity: 0.9;
                line-height: 1.6;
                position: relative;
            }
            .shop-form {
                flex: 1;
                padding: 50px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }
            .form-header {
                text-align: center;
                margin-bottom: 40px;
            }
            .form-header h1 {
                color: #28b463;
                font-size: 28px;
                font-weight: 700;
                margin-bottom: 10px;
            }
            .form-header p {
                color: #718096;
                font-size: 16px;
            }
            .input-group {
                position: relative;
                margin-bottom: 25px;
            }
            .input-group input {
                width: 100%;
                padding: 16px 20px;
                padding-left: 50px;
                border: 2px solid #e2e8f0;
                border-radius: 12px;
                font-size: 16px;
                transition: all 0.3s ease;
                background: #f8fafc;
            }
            .input-group input:focus {
                border-color: #28b463;
                background: #fff;
                box-shadow: 0 0 0 4px rgba(40,180,99,0.1);
            }
            .input-group i {
                position: absolute;
                left: 20px;
                top: 50%;
                transform: translateY(-50%);
                color: #28b463;
                font-size: 18px;
            }
            .submit-btn {
                background: linear-gradient(135deg, #28b463 0%, #138d4b 100%);
                color: #fff;
                border: none;
                padding: 16px;
                border-radius: 12px;
                font-size: 16px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 10px;
            }
            .submit-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(40,180,99,0.3);
            }
            .error-message {
                background: #fff5f5;
                color: #e53e3e;
                padding: 12px 20px;
                border-radius: 8px;
                margin-bottom: 20px;
                font-size: 14px;
                display: flex;
                align-items: center;
                gap: 8px;
                border: 1px solid #fed7d7;
            }
            .error-message i {
                font-size: 16px;
            }
            .features {
                margin-top: 30px;
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 15px;
            }
            .feature-item {
                display: flex;
                align-items: center;
                gap: 10px;
                color: #4a5568;
                font-size: 14px;
            }
            .feature-item i {
                color: #28b463;
                font-size: 16px;
            }
            @media (max-width: 768px) {
                .shop-container {
                    flex-direction: column;
                }
                .shop-image {
                    padding: 30px;
                }
                .shop-form {
                    padding: 30px;
                }
            }
        </style>
        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
    </head>
    <body>
        <div class="shop-container">
            <div class="shop-image">
                <h2>Bắt đầu kinh doanh của bạn</h2>
                <p>Tạo shop online chuyên nghiệp chỉ trong vài phút. Quản lý đơn hàng, sản phẩm và khách hàng một cách dễ dàng.</p>
            </div>
            <div class="shop-form">
                <div class="form-header">
                    <h1>Tạo Shop Mới</h1>
                    <p>Đặt tên và mật khẩu cho shop của bạn</p>
                </div>

                <form id="shopForm" action="<%=path%>/GoogleAuthController" method="post">
                    <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message">
                        <i class="fas fa-exclamation-circle"></i>
                        ${error}
                    </div>
                    <% } %>

                    <div class="input-group">
                        <i class="fas fa-store"></i>
                        <input type="text" 
                               name="shop" 
                               value="${storeName}" 
                               placeholder="Nhập tên shop của bạn" 
                               required
                               autofocus>
                    </div>

                    <div class="input-group">
                        <i class="fas fa-lock"></i>
                        <input type="password" 
                               name="password" 
                               id="password"
                               class="pass-input"
                               placeholder="Nhập mật khẩu (tối thiểu 8 ký tự)" 
                               minlength="8"
                               required>
                        <span class="toggle-password" onclick="togglePassword()">
                            <i class="fas fa-eye-slash"></i>
                        </span>
                    </div>

                    <div class="input-group">
                        <i class="fas fa-lock"></i>
                        <input type="password" 
                               id="password2"
                               name="password2" 
                               class="pass-input"
                               placeholder="Nhập lại mật khẩu" 
                               minlength="8"
                               required>
                        <span class="toggle-password" onclick="togglePassword2()">
                            <i class="fas fa-eye-slash"></i>
                        </span>
                    </div>

                    <button type="submit" class="submit-btn">
                        <i class="fas fa-arrow-right"></i>
                        Tiếp tục
                    </button>

                    <div class="features">
                        <div class="feature-item">
                            <i class="fas fa-check-circle"></i>
                            <span>Quản lý đơn hàng</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-check-circle"></i>
                            <span>Báo cáo doanh thu</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-check-circle"></i>
                            <span>Quản lý sản phẩm</span>
                        </div>
                        <div class="feature-item">
                            <i class="fas fa-check-circle"></i>
                            <span>Hỗ trợ 24/7</span>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Thêm CSS mới -->
            <style>
                /* ... CSS cũ giữ nguyên ... */

                .input-group {
                    position: relative;
                    margin-bottom: 25px;
                }

                .toggle-password {
                    position: absolute;
                    right: 20px;
                    top: 50%;
                    transform: translateY(-50%);
                    color: #28b463;
                    cursor: pointer;
                    transition: all 0.3s ease;
                }

                .toggle-password:hover {
                    color: #138d4b;
                }

                .pass-input {
                    padding-right: 50px !important;
                }

                .password-strength {
                    margin-top: 5px;
                    font-size: 12px;
                    color: #718096;
                }

                .password-strength.weak {
                    color: #e53e3e;
                }

                .password-strength.medium {
                    color: #ed8936;
                }

                .password-strength.strong {
                    color: #28b463;
                }
            </style>

            <!-- Cập nhật JavaScript -->
            <script>
                $(document).ready(function () {
                    $('input[name="shop"]').focus();
                });

                function togglePassword() {
                    const input = document.getElementById('password');
                    const icon = input.nextElementSibling.querySelector('i');

                    if (input.type === 'password') {
                        input.type = 'text';
                        icon.classList.remove('fa-eye-slash');
                        icon.classList.add('fa-eye');
                    } else {
                        input.type = 'password';
                        icon.classList.remove('fa-eye');
                        icon.classList.add('fa-eye-slash');
                    }
                }

                function togglePassword2() {
                    const input = document.getElementById('password2');
                    const icon = input.nextElementSibling.querySelector('i');

                    if (input.type === 'password') {
                        input.type = 'text';
                        icon.classList.remove('fa-eye-slash');
                        icon.classList.add('fa-eye');
                    } else {
                        input.type = 'password';
                        icon.classList.remove('fa-eye');
                        icon.classList.add('fa-eye-slash');
                    }
                }
            </script>
    </body>
</html>