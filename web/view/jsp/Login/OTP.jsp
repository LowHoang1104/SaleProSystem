<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Xác nhận mã OTP</title>
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
                margin-bottom: 18px;
            }
            .otp-inputs {
                display: flex;
                gap: 10px;
                justify-content: center;
                margin-bottom: 25px;
            }
            .otp-inputs input {
                width: 48px;
                height: 48px;
                text-align: center;
                font-size: 22px;
                border: 2px solid #e2e8f0;
                border-radius: 10px;
                background: #f8fafc;
                transition: border-color 0.2s;
            }
            .otp-inputs input:focus {
                border-color: #28b463;
                box-shadow: 0 0 0 4px rgba(40,180,99,0.1);
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
            .btn-green:disabled {
                background: #b7e6c7;
                color: #fff;
                cursor: not-allowed;
            }
            .btn-green:hover:enabled {
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
            .otp-timer {
                color: #229954;
                font-size: 15px;
                margin-bottom: 10px;
                font-weight: 500;
                letter-spacing: 0.5px;
            }
            .otp-timer.expired {
                color: #e53e3e;
            }
        </style>
    </head>
    <body>
        <div class="bg-overlay"></div>
        <div class="d-flex align-items-center justify-content-center" style="min-height:100vh;position:relative;z-index:1;">
            <div class="form-card" id="otpForm">
                <div class="form-title">Nhập mã xác nhận</div>
                <div class="form-desc">Vui lòng nhập mã 6 số đã được gửi đến email của bạn</div>
                <div class="otp-timer" id="countdown">03:00</div>
                <div style="color: red" id="mess"></div>
                <div class="otp-inputs">
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                    <input type="text" maxlength="1" pattern="[0-9]" required>
                </div>
                <input type="hidden" name="otp" id="otp-hidden">
                <button type="button" onclick="messagesListener()" class="btn btn-green" id="btn-otp">Xác nhận</button>
                <a onclick="resetToken()" class="back-link">Gửi lại mã</a>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                    function messagesListener() {
                        var otpValue = updateOTP();
                        var token = '<%= request.getParameter("token")%>'
                        $.ajax({
                            type: 'GET', // Gửi yêu cầu HTTP dạng GET
                            url: '/Mg2/ResetPasswordController', // Gửi đến URL 'message' (có thể là `/message`)
                            data: {otpValue: otpValue, tokenValue: token},
                            success: function (result) { // Nếu yêu cầu thành công
                                // Gắn nội dung nhận được vào phần tử có id="messages"
                                if (result === "OK") {
                                    window.location.href = "/Mg2/view/jsp/Login/ResetPassword.jsp?token=" + token;
                                } else if (result === "TIME") {
                                    timeLeft = 0;
                                } else {
                                    document.getElementById('mess').innerHTML = 'Mã OTP bạn nhập không đúng';
                                }
                            }
                        });
                    }

                    function resetToken() {
                        var token = '<%= request.getParameter("token")%>'
                        $.ajax({
                            type: 'POST', // Gửi yêu cầu HTTP dạng GET
                            url: '/Mg2/ResetPasswordController', // Gửi đến URL 'message' (có thể là `/message`)
                            data: {op: 'resetOTP', oldToken: token},
                            success: function (result) {
                                if (result !== "FAIL") {
                                    window.location.href = "/Mg2/view/jsp/Login/OTP.jsp?token=" + result;
//                            document.getElementById('mess').innerHTML = result;
                                } else {
                                    document.getElementById('mess').innerHTML = 'Quá trình gửi mã OTP bị lỗi, Vui lòng thử lại';
                                }
                            }
                        });
                    }
                    let timeLeft = 180; // 180 giây = 3 phút
                    function updateCountdown() {
                        let minutes = Math.floor(timeLeft / 60);
                        let seconds = timeLeft % 60;

                        let display =
                                (minutes < 10 ? '0' + minutes : minutes) + ':' +
                                (seconds < 10 ? '0' + seconds : seconds);

                        document.getElementById('countdown').textContent = display;

                        if (timeLeft > 0) {
                            timeLeft--;
                        } else {
                            clearInterval(timer);
                            const countdownEl = document.getElementById('countdown');
                            countdownEl.textContent = "Mã đã hết hạn, vui lòng gửi lại mã!";
                            countdownEl.classList.add('expired');
                        }
                    }

                    updateCountdown(); // hiển thị lần đầu ngay khi load
                    let timer = setInterval(updateCountdown, 1000);

// Tự động chuyển focus khi nhập OTP và gộp thành 1 chuỗi
                    const inputs = document.querySelectorAll('.otp-inputs input');
                    inputs.forEach((input, idx) => {
                        input.addEventListener('input', function () {
                            if (this.value.length === 1 && idx < inputs.length - 1) {
                                inputs[idx + 1].focus();
                            }
                            updateOTP();
                        });
                        input.addEventListener('keydown', function (e) {
                            if (e.key === "Backspace" && this.value === "" && idx > 0) {
                                inputs[idx - 1].focus();
                            }
                        });
                    });

                    function updateOTP() {
                        let otp = '';
                        inputs.forEach(input => otp += input.value);
                        document.getElementById('otp-hidden').value = otp;
                        return otp;
                    }

        </script>
    </body>
</html>