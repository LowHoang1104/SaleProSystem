<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<%@ page import="salepro.models.SuperAdmin.ShopOwner" %>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập - Bee Shop</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Asset template -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                background: url('<%=path%>/view/assets/img/login.jpg') no-repeat center center fixed;
                background-size: cover;
                min-height: 100vh;
            }
            .login-bg-overlay {
                position: fixed;
                inset: 0;
                background: rgba(45,52,63,0.65);
                z-index: 0;
            }
            .login-box {
                z-index: 1;
                background: #fff;
                border-radius: 1rem;
                box-shadow: 0 7px 32px 0 rgba(140,170,255,0.13);
                padding: 34px 28px 28px 28px;
                max-width: 410px;
                width: 100%;
                margin: auto;
            }
            .btn-role {
                width: 48%;
                font-size: 1.11rem;
                font-weight: 500;
                padding: 10px 0;
                border-radius: 30px;
                border: none;
            }
            .btn-manager {
                background: #2196f3;
                color: #fff;
            }
            .btn-seller {
                background: #10c032;
                color: #fff;
            }
            .btn-manager:hover {
                background: #176cb0;
            }
            .btn-seller:hover {
                background: #0d9728;
            }
            @media (max-width: 500px) {
                .login-box {
                    padding: 18px 5vw;
                }
                .btn-role {
                    font-size: 1rem;
                }
            }
        </style>
    </head>
    <body>
        <div class="login-bg-overlay"></div>
        <div class="container d-flex justify-content-center align-items-center" style="min-height:100vh;position:relative;z-index:1;">
            <form class="login-box" action="<%=path%>/Login" method="get" id="loginForm">
                <div class="text-center mb-2">
                    <img src="<%=path%>/view/assets/img/logo.png" width="48" alt="Logo" class="mb-1">
                    <h2 style="font-weight: 660">Đăng nhập</h2>
                    <div class="fw-bold mb-2"  style=" color: red;font-size:1.16rem;"><%
                        ShopOwner a  = (ShopOwner) session.getAttribute("ShopOwner");
                        if (a != null) {
                            out.println("Chào " + a.getShopName());               
                        } else {
                            out.println("Bạn chưa vào cửa hàng");
                        }
                        %></div>    
                </div>
                <% if (request.getAttribute("Error") != null) { %>
                <div class="alert alert-danger d-flex align-items-center" role="alert" style="border-radius: 6px;">
                    <i class="fas fa-exclamation-circle me-2"></i>
                    <span><%= request.getAttribute("Error") %></span>
                </div>
                <% } %>

                <% if (request.getParameter("mess") != null) { %>
                <div class="alert alert-success d-flex align-items-center" role="alert" style="border-radius: 6px;">
                    <i class="fas fa-check-circle me-2"></i>
                    <span><%= request.getParameter("mess") %></span>
                </div>
                <% } %>
                <div class="mb-3">
                    <label for="username" class="form-label">Tên đăng nhập</label>
                    <input type="text" name="account" class="form-control" id="username" placeholder="Tên đăng nhập" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Mật khẩu</label>
                    <div class="input-group">
                        <input name="password" type="password" class="form-control" id="password" placeholder="••••••••" required>
                        <span class="input-group-text"><i class="fa fa-eye"></i></span>
                    </div>
                </div>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="rememberMe">
                        <label class="form-check-label" for="rememberMe">Duy trì đăng nhập</label>
                    </div>
                    <a href="<%=path%>/view/jsp/Login/forgetPassword.jsp" class="link-primary">Quên mật khẩu?</a>
                </div>
                <!-- 2 Nút Đăng nhập riêng -->
                <div class="d-flex justify-content-between gap-2 mb-2">
                    <button type="submit" class="btn btn-role btn-manager" name="login" value="1" >Quản lý</button>
                    <button type="submit" class="btn btn-role btn-seller" name="login" value="2">Bán hàng</button>
                </div> 
                <div class="align-center">
                    <button  class="btn btn-role btn-action "> <a href="<%=path%>/view/jsp/Homepage.jsp">
                        ← Quay lại
                        </a>
                    </button>
                </div> 

            </form>
        </div>
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>