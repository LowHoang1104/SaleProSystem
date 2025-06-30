<!DOCTYPE html>
<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - Đăng Nhập Quản Trị</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
            background-size: 400% 400%;
            animation: gradientShift 15s ease infinite;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            position: relative;
        }

        @keyframes gradientShift {
            0% {
                background-position: 0% 50%;
            }
            50% {
                background-position: 100% 50%;
            }
            100% {
                background-position: 0% 50%;
            }
        }

        /* Animated background particles */
        .particles {
            position: absolute;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .particle {
            position: absolute;
            display: block;
            pointer-events: none;
            width: 6px;
            height: 6px;
            background: rgba(255, 255, 255, 0.6);
            border-radius: 50%;
            animation: float 6s ease-in-out infinite;
        }

        .particle:nth-child(1) {
            left: 10%;
            animation-delay: 0s;
            animation-duration: 6s;
        }

        .particle:nth-child(2) {
            left: 20%;
            animation-delay: 2s;
            animation-duration: 12s;
        }

        .particle:nth-child(3) {
            left: 25%;
            animation-delay: 4s;
            animation-duration: 8s;
        }

        .particle:nth-child(4) {
            left: 40%;
            animation-delay: 0s;
            animation-duration: 10s;
        }

        .particle:nth-child(5) {
            left: 70%;
            animation-delay: 1s;
            animation-duration: 7s;
        }

        .particle:nth-child(6) {
            left: 80%;
            animation-delay: 3s;
            animation-duration: 9s;
        }

        .particle:nth-child(7) {
            left: 32%;
            animation-delay: 7s;
            animation-duration: 11s;
        }

        .particle:nth-child(8) {
            left: 55%;
            animation-delay: 15s;
            animation-duration: 6s;
        }

        .particle:nth-child(9) {
            left: 25%;
            animation-delay: 2s;
            animation-duration: 10s;
        }

        .particle:nth-child(10) {
            left: 90%;
            animation-delay: 11s;
            animation-duration: 13s;
        }

        @keyframes float {
            0% {
                opacity: 0;
                transform: translateY(100vh) rotate(0deg);
            }
            25% {
                opacity: 1;
            }
            50% {
                transform: translateY(50vh) rotate(180deg);
            }
            75% {
                opacity: 1;
            }
            100% {
                opacity: 0;
                transform: translateY(-100vh) rotate(360deg);
            }
        }

        .login-container {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(20px);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 24px;
            padding: 50px 40px;
            width: 100%;
            max-width: 450px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.2);
            text-align: center;
            animation: slideUp 0.8s ease-out;
            position: relative;
            z-index: 10;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(50px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .logo {
            font-size: 48px;
            margin-bottom: 10px;
            animation: bounce 2s ease-in-out infinite;
        }

        @keyframes bounce {
            0%, 20%, 50%, 80%, 100% {
                transform: translateY(0);
            }
            40% {
                transform: translateY(-10px);
            }
            60% {
                transform: translateY(-5px);
            }
        }

        .login-title {
            color: white;
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 10px;
            text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }

        .login-subtitle {
            color: rgba(255, 255, 255, 0.8);
            font-size: 16px;
            margin-bottom: 40px;
            font-weight: 300;
        }

        .form-group {
            margin-bottom: 25px;
            text-align: left;
            position: relative;
        }

        .form-group label {
            display: block;
            color: rgba(255, 255, 255, 0.9);
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
            text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
        }

        .input-container {
            position: relative;
        }

        .form-group input {
            width: 100%;
            padding: 16px 20px 16px 50px;
            border: 2px solid rgba(255, 255, 255, 0.2);
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.1);
            color: white;
            font-size: 16px;
            transition: all 0.3s ease;
            backdrop-filter: blur(10px);
        }

        .form-group input::placeholder {
            color: rgba(255, 255, 255, 0.6);
        }

        .form-group input:focus {
            outline: none;
            border-color: rgba(255, 255, 255, 0.6);
            background: rgba(255, 255, 255, 0.15);
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
        }

        .input-icon {
            position: absolute;
            left: 18px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 18px;
            color: rgba(255, 255, 255, 0.7);
        }

        .login-btn {
            width: 100%;
            padding: 18px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(45deg, #ff6b6b, #ee5a24, #ff9ff3, #54a0ff);
            background-size: 300% 300%;
            color: white;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
            animation: gradientBtn 3s ease infinite;
            position: relative;
            overflow: hidden;
        }

        @keyframes gradientBtn {
            0% {
                background-position: 0% 50%;
            }
            50% {
                background-position: 100% 50%;
            }
            100% {
                background-position: 0% 50%;
            }
        }

        .login-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.4);
        }

        .login-btn:active {
            transform: translateY(-1px);
        }

        .login-btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
            transition: left 0.5s;
        }

        .login-btn:hover::before {
            left: 100%;
        }

        .forgot-password {
            margin-top: 25px;
            text-align: center;
        }

        .forgot-password a {
            color: rgba(255, 255, 255, 0.8);
            text-decoration: none;
            font-size: 14px;
            font-weight: 300;
            transition: all 0.3s ease;
        }

        .forgot-password a:hover {
            color: white;
            text-shadow: 0 0 10px rgba(255, 255, 255, 0.5);
        }

        .error-message {
            background: rgba(255, 107, 107, 0.2);
            border: 1px solid rgba(255, 107, 107, 0.5);
            color: #ff6b6b;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            display: none;
            animation: shake 0.5s ease-in-out;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            75% { transform: translateX(5px); }
        }

        .loading {
            display: none;
            margin-top: 20px;
        }

        .spinner {
            border: 3px solid rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            border-top: 3px solid white;
            width: 30px;
            height: 30px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        /* Responsive */
        @media (max-width: 768px) {
            .login-container {
                margin: 20px;
                padding: 40px 30px;
            }
            
            .login-title {
                font-size: 28px;
            }
            
            .logo {
                font-size: 40px;
            }
        }

        /* Additional floating elements */
        .floating-shape {
            position: absolute;
            opacity: 0.1;
            animation: floatShape 20s infinite linear;
        }

        .floating-shape:nth-child(1) {
            top: 20%;
            left: 10%;
            width: 80px;
            height: 80px;
            background: white;
            border-radius: 50%;
            animation-delay: -5s;
        }

        .floating-shape:nth-child(2) {
            top: 60%;
            right: 10%;
            width: 60px;
            height: 60px;
            background: white;
            clip-path: polygon(50% 0%, 0% 100%, 100% 100%);
            animation-delay: -10s;
        }

        .floating-shape:nth-child(3) {
            bottom: 20%;
            left: 20%;
            width: 100px;
            height: 100px;
            background: white;
            clip-path: polygon(20% 0%, 80% 0%, 100% 100%, 0% 100%);
            animation-delay: -15s;
        }

        @keyframes floatShape {
            0% {
                transform: translateY(0px) rotate(0deg);
            }
            50% {
                transform: translateY(-20px) rotate(180deg);
            }
            100% {
                transform: translateY(0px) rotate(360deg);
            }
        }
    </style>
</head>
<body>
    <!-- Animated particles -->
    <div class="particles">
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
        <span class="particle"></span>
    </div>

    <!-- Floating shapes -->
    <div class="floating-shape"></div>
    <div class="floating-shape"></div>
    <div class="floating-shape"></div>

    <div class="login-container">
        <div class="logo">🔐</div>
        <h1 class="login-title">Admin Portal</h1>
        <p class="login-subtitle">Đăng nhập để truy cập hệ thống quản trị</p>

        <div class="error-message" <c:if test="${error ne null}">style="display:block"</c:if>id="errorMessage">
            ${error}
        </div>
        <form id="loginForm" action="<%=path%>/LoginSuperAdminController" method="post">
            <div class="form-group">
                <label for="username">Tài khoản</label>
                <div class="input-container">
                    <span class="input-icon">👤</span>
                    <input type="text" id="username" name="username" placeholder="Nhập tài khoản..." required>
                </div>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <div class="input-container">
                    <span class="input-icon">🔒</span>
                    <input type="password" id="password" name="password" placeholder="Nhập mật khẩu..." required>
                </div>
            </div>

            <button type="submit" class="login-btn" id="loginBtn">
                <span id="btnText">Đăng Nhập</span>
            </button>

            <div class="loading" id="loading">
                <div class="spinner"></div>
                <p style="color: rgba(255,255,255,0.8); margin-top: 10px;">Đang xác thực...</p>
            </div>
        </form>

        <div class="forgot-password">
            <a href="#" >Quên mật khẩu?</a>
        </div>
    </div
</body>
</html>