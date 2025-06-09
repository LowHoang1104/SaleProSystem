<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Quên mật khẩu</title>
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <style>
            body {
                min-height: 100vh;
                background: url('<%=path%>/view/assets/img/login.jpg') no-repeat center center fixed;
                background-size: cover;
                position: relative;
            }
            .bg-overlay {
                position: fixed;
                inset: 0;
                background: rgba(40,180,99,0.18);
                z-index: 0;
            }
            .form-card {
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 12px 40px 0 #28b46333;
                padding: 36px 32px 28px 32px;
                max-width: 400px;
                width: 100%;
                text-align: center;
                position: relative;
                z-index: 2;
                margin: 0 auto;
            }
            .form-title {
                color: #28b463;
                font-size: 24px;
                font-weight: 700;
                margin-bottom: 8px;
            }
            .form-desc {
                color: #666;
                font-size: 15px;
                margin-bottom: 24px;
            }
            .form-addons {
                position: relative;
            }
            .form-control {
                border-radius: 10px;
                border: 2px solid #e2e8f0;
                background: #f8fafc;
                font-size: 16px;
                padding: 12px 44px 12px 16px;
                margin-bottom: 18px;
                transition: border-color 0.2s;
            }
            .form-control:focus {
                border-color: #28b463;
                background: #fff;
                box-shadow: 0 0 0 2px rgba(40,180,99,0.08);
            }
            .form-addons img {
                position: absolute;
                right: 16px;
                top: 50%;
                transform: translateY(-50%);
                width: 22px;
                opacity: 0.7;
            }
            .btn-green {
                background: linear-gradient(135deg, #28b463 0%, #138d4b 100%);
                color: #fff;
                border-radius: 10px;
                font-weight: bold;
                width: 100%;
                padding: 12px 0;
                font-size: 16px;
                margin-top: 10px;
                transition: background 0.2s;
                border: none;
            }
            .btn-green:hover {
                background: #138d4b;
            }
            .back-link {
                margin-top: 18px;
                display: block;
                color: #28b463;
                text-decoration: none;
                font-weight: 500;
            }
            .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="bg-overlay"></div>
        <div class="d-flex align-items-center justify-content-center" style="min-height:100vh;position:relative;z-index:1;">
            <form class="form-card" action="<%=path%>/ResetPasswordController" method="post">
                <div class="form-title">Quên mật khẩu?</div>
                <div class="form-desc">Nhập email của bạn để nhận mã xác nhận</div>
                <div style="color: red;margin-bottom: 24px">${mess}</div>
                <div class="form-addons mb-3">
                    <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn" required>
                    <img src="<%=path%>/view/assets/img/icons/mail.svg" alt="email">
                </div>
                <button type="submit" class="btn btn-green">Gửi mã xác nhận</button>
                <a href="<%=path%>/view/jsp/Login.jsp" class="back-link"><i class="fas fa-arrow-left"></i> Quay lại đăng nhập</a>
            </form>
        </div>
    </body>
</html>