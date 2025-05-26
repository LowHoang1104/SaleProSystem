<%-- 
    Document   : LoginShopOwner
    Created on : May 22, 2025, 12:33:37 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập hệ thống</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Dùng nguyên các file css hệ thống -->
       
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
    </head>
    <body>
        <form action="<%=path%>/Login" method="get"> 
            <div class="d-flex align-items-center justify-content-center" style="min-height:100vh; background: linear-gradient(135deg, #d7ffe0 0%, #ffffff 70%);">
                <div class="card shadow border-0 p-0" style="max-width:410px; width:100%; border-radius:20px;">
                    <div class="position-relative overflow-hidden">
                        <div style="background:linear-gradient(110deg,#28b463 60%,#e8ffe7 100%);height:82px; border-radius: 20px 20px 0 40px"></div>
                        <div class="position-absolute start-50 translate-middle-x" style="top:41px;">
                            <span class="rounded-circle shadow" style="background:#fff;padding:17px 21px;box-shadow:0 4px 16px #28b46322;">
                                <i class="fas fa-store fa-2x" style="color:#28b463;"></i>
                            </span>
                        </div>
                    </div>
                    <div class="card-body pt-5 pb-4 px-4">
                        <h3 class="fw-bold text-center mb-2" style="color:#28b463;">Đăng nhập hệ thống</h3>
                        <div class="text-center mb-4 text-success small" style="opacity:.84;">Truy cập & quản lý cửa hàng mọi lúc, dễ dàng, bảo mật!</div>
                        <p style="color: red; font-weight: 660">${error}</p>
                        <form id="loginForm" autocomplete="off">
                            <div class="mb-3">
                                <label for="storeName"  class="form-label fw-semibold text-success">Tên Cửa Hàng</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white" style="color:#28b463;"><i class="fas fa-store-alt"></i></span>
                                    <input name="nameshop" value="${nameshop}" type="text" class="form-control border-success" id="storeName" placeholder="Tên Shop" required />
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="account" class="form-label fw-semibold text-success">Tài khoản</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white" style="color:#28b463;"><i class="fas fa-user"></i></span>
                                    <input name="account" value="${account}" type="text" class="form-control border-success" id="account" placeholder="Tên đăng nhập" required />
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label fw-semibold text-success">Mật khẩu</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white" style="color:#28b463;"><i class="fas fa-lock"></i></span>
                                    <input name="password" type="password" class="form-control border-success" id="password" placeholder="••••••••••" required />
                                </div>
                            </div>
                            <button type="submit" name="login" class="btn btn-success w-100 fw-bold mt-1 mb-1" style="border-radius:9px;">
                                <i class="fas fa-sign-in-alt me-2"></i>Đăng nhập
                            </button>
                            <div class="d-flex justify-content-between align-items-center mt-3">
                                <a href="#" class="small" style="color:#28b463;">Quên mật khẩu?</a>
                                <a href="<%=path%>/view/jsp/sign_up.jsp" class="small" style="color:#28b463;">Tạo tài khoản mới</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </form>

        <!-- Giữ nguyên các file js hệ thống -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%=path%>/view/assets/js/script.js"></script>
    </body>
</html>