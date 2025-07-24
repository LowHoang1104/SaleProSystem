<%-- 
    Document   : Homepage
    Created on : Jun 5, 2025, 7:36:27 PM
    Author     : ADMIN
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Phần mềm quản lý bán hàng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Sử dụng file css đã có trong dự án -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <style>
            body {
                background: linear-gradient(90deg, #EAF6FF 60%, #F7F9FC 100%);
            }
            .hero-section {
                display: flex;
                align-items: center;
                min-height: 80vh;
            }
            .hero-left {
                flex: 1;
                padding: 60px 0 60px 60px;
            }
            .hero-title {
                font-size: 2.5rem;
                font-weight: bold;
                line-height: 1.15;
                margin-bottom: 20px;
                color: #222;
            }
            .hero-title span {
                color: #08947A;
            }
            .hero-desc {
                font-size: 1.1rem;
                margin-bottom: 30px;
            }
            .hero-ctas .btn {
                margin-right: 12px;
            }
            .hero-ctas .btn-primary {
                border-radius: 30px;
            }
            .hero-ctas .btn-outline-secondary {
                border-radius: 30px;
            }
            .hero-stats {
                margin-top: 25px;
            }
            .hero-stats span strong {
                color: #08947A;
                margin-right: 8px;
            }
            .hero-right {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                position: relative;
            }
            .main-image {
                width: 350px;
                border-radius: 50%;
                box-shadow: 0 8px 48px rgba(40, 100, 170, 0.13);
            }
            .bubble {
                position: absolute;
                background: #fff;
                padding: 8px 17px;
                border-radius: 16px;
                box-shadow: 0 3px 15px #a6c1ee29;
                font-size: 0.96rem;
            }
            .bubble1 {
                top: 38px;
                left: 60px;
            }
            .bubble2 {
                top: 62%;
                left: 32px;
            }
            .bubble3 {
                top: 52px;
                right: 10px;
            }
            .header-bar {
                padding: 15px 35px;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            /* Căn chỉnh các liên kết và nút đăng nhập ngang hàng */
            .header-links {
                display: flex;
                align-items: center;
            }
            .header-links a {
                margin-left: 30px;
                color: #3a3a3a;
                font-weight: 500;
                text-decoration: none;
            }
            .header-login-btn {
                background: #08947A;
                color: #fff;
                font-weight: 500;
                border-radius: 30px;
                padding: 7px 30px;
                border: none;
                margin-left: 20px;
                vertical-align: middle;
            }
            .header-login-btn:hover {
                background: #0ccda3;
            }
            @media (max-width: 900px) {
                .hero-section {
                    flex-direction: column;
                    padding: 12px 0;
                }
                .hero-left {
                    padding: 32px 0 0 20px;
                }
                .main-image {
                    width: 210px;
                }
            }
            /* Modal chỉnh thêm cho giống hơn */
            .modal-content {
                border-radius: 15px;
            }
            .modal-header {
                border-bottom: none;
            }
            .modal-title {
                font-weight: 600;
            }
            .input-group-text {
                border-radius: 0 8px 8px 0;
                background: #f7f7f7;
            }
            /* Căn giữa modal theo cả chiều ngang và dọc */
            .modal-dialog {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
            }
        </style>
    </head>
    <body>
        <!-- Header -->

        <div class="header-bar">
            <div style="font-weight: bold; font-size: 1.3rem; color: #08947A;">
                <img src="<%=path%>/view/assets/img/logo.png" alt="Logo" style="max-width: 100%; height: 32px; margin-right: 8px; vertical-align: middle;">
            </div>
            <div class="header-links">
                <a href="#">Sản phẩm</a>
                <a href="#">Bảng giá</a>
                <a href="#">Tài nguyên</a>
                <a href="#">Hỗ trợ</a>
                <button type="button" class="header-login-btn" data-bs-toggle="modal" data-bs-target="#loginModal">Đăng nhập</button>
            </div>
        </div>

        <!-- Hero Section -->
        <div class="hero-section container-fluid">
            <div class="hero-left">
                <div class="hero-title">
                    Phần mềm<br>quản lý bán hàng<br><span>phổ biến nhất</span>
                </div>
                <div class="hero-desc">
                    Công cụ đơn giản & mạnh mẽ giúp bạn quản lý bán hàng hiệu quả mọi lúc mọi nơi.
                </div>
                <div class="hero-ctas mb-2">
                    <a href="sign_up.jsp" class="btn btn-outline-secondary">Đăng ký tài khoản ngay</a>
                </div>
                <div class="hero-stats">
                    <span><strong>300.000+</strong> cửa hàng sử dụng</span>
                    <span><strong>10.000+</strong> dịch vụ triển khai thực tế</span>
                </div>
            </div>
        </div>

        <!-- Modal Login -->
        <div class="modal fade" id="loginModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <form class="modal-content px-3 py-1" style="max-width: 360px;">
                    <div class="modal-header pb-0">
                        <h5 class="modal-title">Đăng nhập tài khoản Salepro</h5>
                        <button type="button" style="border-radius: 50%; border: none;" class="btn-close-custom" data-bs-dismiss="modal">X</button>
                    </div>
                    <div class="modal-body pt-2">
                        <span style="color: red" id="error"></span>
                        <div class="input-group mb-2">
                            <input type="text" id="nameshop" class="form-control" placeholder="Địa chỉ truy cập cửa hàng">
                        </div>
                    </div>
                    <div class="modal-footer pt-2 flex-column align-items-start">
                        <span class="text-muted mb-2" style="font-size: 95%;">Bạn chưa có gian hàng trên Salepro? <a href="sign_up.jsp" class="text-primary fw-bold">Đăng ký</a></span>
                        <button type="button" class="btn btn-primary w-100" onclick="checkExistShop()">Vào cửa hàng</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            function checkExistShop() {
                var nameshop = document.getElementById("nameshop").value;
                $.ajax({
                    url: "/Mg2/LoginOnwerShop",
                    type: 'post',
                    data: {nameshop: nameshop},
                    success: function (data) {
                        if (data === "OK") {
                            window.location.href = "/Mg2/LoginOnwerShop";
                        } 
                        else if(data =="Subscription")
                        {
                            window.location.href = "/Mg2/SubscriptionController";
                        }
                        else {
                            document.getElementById("error").innerHTML = data;
                        }
                    }
                });
            }
        </script>
        <!-- Sử dụng các file js sẵn có -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%=path%>/view/assets/js/script.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <%
   String msg = request.getParameter("msg");
   if (msg != null) {
        %>
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: '<%= java.net.URLDecoder.decode(msg, "UTF-8") %>',
                confirmButtonColor: '#3085d6'
            });
        </script>
        <%
            }
        %>
    </body>
</html>