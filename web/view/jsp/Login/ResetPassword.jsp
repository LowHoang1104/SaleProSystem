<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Đặt lại mật khẩu</title>
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
            .form-login label {
                color: #229954;
                font-weight: 500;
                margin-bottom: 6px;
                float: left;
            }
            .form-addons {
                position: relative;
            }
            .form-control {
                border-radius: 10px;
                border: 2px solid #e2e8f0;
                background: #f8fafc;
                font-size: 16px;
                padding: 12px 10px 12px 16px;
                margin-bottom: 18px;
                transition: border-color 0.2s;
            }
            .form-control:focus {
                border-color: #28b463;
                background: #fff;
                box-shadow: 0 0 0 2px rgba(40,180,99,0.08);
            }
            .toggle-password {
                position: absolute;
                right: 16px;
                top: 65%;
                transform: translateY(-50%);
                color: #28b463;
                cursor: pointer;
                font-size: 20px;
                transition: color 0.2s;
                z-index: 2;
            }
            .toggle-password:hover {
                color: #138d4b;
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
        </style>
    </head>
    <body>
        <div class="bg-overlay"></div>
        <div class="d-flex align-items-center justify-content-center" style="min-height:100vh;position:relative;z-index:1;">
            <form class="form-card" id="resetForm" >

                <div class="form-title">Đặt lại mật khẩu</div>
                <div id="mess"></div>
                <div class="form-desc">Nhập mật khẩu mới cho tài khoản của bạn</div>
                <div class="form-login">
                    <label>Mật khẩu mới</label>
                    <div class="form-addons">
                        <input type="password" name="password" id="password1" class="form-control" placeholder="Mật khẩu mới (tối thiểu 8 ký tự)" minlength="8" required>
                        <span onclick="togglePassword('password', this)">
                        </span>
                    </div>
                </div>
                <div class="form-login">
                    <label>Nhập lại mật khẩu mới</label>
                    <div class="form-addons">
                        <input type="password" id="password2" class="form-control" placeholder="Nhập lại mật khẩu mới" minlength="8" required>
                        <span  onclick="togglePassword('password2', this)">
                        </span>
                    </div>
                </div>
                <button type="button" onclick="ResetPassword()" class="btn btn-green">Đặt lại mật khẩu</button>
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                    function ResetPassword() {
                        var token = '<%= request.getParameter("token")%>'
                        $.ajax({
                            type: 'POST', // Gửi yêu cầu HTTP dạng GET
                            url: '/Mg2/ResetPasswordController', // Gửi đến URL 'message' (có thể là `/message`)
                            data: {op: "resetPassword", token: token, password1 : document.getElementById('password1').value,password2 : document.getElementById('password2').value},
                            success: function (result) { // Nếu yêu cầu thành công
                                // Gắn nội dung nhận được vào phần tử có id="messages"
                                if (result === "Time") {
                                    document.getElementById('mess').innerHTML = "Mã của bạn đã hết hạn";
                                } else if(result === "Same") {
                                    document.getElementById('mess').innerHTML = "Mật khẩu không trùng khớp";
                                } else if (result === "Format") {
                                    document.getElementById('mess').innerHTML = "Sai format password <br>có ít nhất 1 ký tự đặc biệt , 1 chữ hoa, 1 số";
                                } else {
                                    window.location.href = "/Mg2/view/jsp/Login.jsp?mess=Đổi Mật Khẩu Thành Công";
                                }
                            }
                        });
                    }

        </script>
    </body>
</html>