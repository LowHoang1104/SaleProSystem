<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="salepro.models.Users"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Dreams Pos admin template</title>

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">

            <%@include file="HeadSideBar/header.jsp" %>
            <%@include file="HeadSideBar/sidebar.jsp" %>


            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Profile</h4>
                            <h6>User Profile</h6>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <form action="<%=path%>/ProfileController" method="post" enctype="multipart/form-data" >
                                <div class="profile-set">
                                    <div class="profile-head">
                                    </div>
                                    <div class="profile-top">
                                        <div class="profile-content">
                                            <div class="profile-contentimg">
                                               <img src="<%= path %>/${sessionScope.user.getAvatar()}?rand=<%= Math.random() %>" alt="img" id="blah">
                                                <div class="profileupload">
                                                    <input type="file" name="avt" id="imgInp">
                                                    <a><img src="<%=path%>/view/assets/img/icons/edit-set.svg" alt="img"></a>
                                                </div>
                                            </div>
                                            <div class="profile-contentname">
                                                <h2>William Castillo</h2>
                                                <h4>Updates Your Photo and Personal Details.</h4>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div>${error}</div>
                                <div class="row">
                                    <div class="col-lg-6 col-sm-12">
                                        <div class="form-group">
                                            <label>Tên</label>
                                            <input <c:if test="${sessionScope.user.getRoleId() eq 1 }">readonly</c:if> <c:if test="${sessionScope.user.getRoleId() ne 1 }"> value="${sessionScope.user.getFullName()}" </c:if> name="name" type="text">
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-sm-12">
                                            <div class="form-group">
                                                <label>Email</label>
                                                    <input <c:if test="${sessionScope.user.getRoleId() eq 1 }">readonly</c:if> <c:if test="${sessionScope.user.getRoleId() ne 1 }"> value="${sessionScope.user.getEmail()}" </c:if> name="email"  type="text">
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-sm-12">
                                            <div class="form-group">
                                                <label>Điện thoại</label>
                                                    <input <c:if test="${sessionScope.user.getRoleId() eq 1 }">readonly</c:if> <c:if test="${sessionScope.user.getRoleId() ne 1 }">value="${sessionScope.user.getPhoneEmployee()}" </c:if>  name="phone"  type="text">
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-sm-12">
                                            <div class="form-group">
                                                <label>Vai trò</label>
                                                    <input value="${sessionScope.user.getRoleName()}" readonly type="text">
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-sm-12">

                                    </div>
                                    <div class="col-12">
                                        <div class="text-end mt-3 d-flex justify-content-end gap-2">
                                            <button type="submit" name="op" value="save" class="btn btn-submit">Lưu</button>
                                            <button type="button" onclick="location.href = '<%=path%>/view/jsp/admin/Home_admin.jsp'" class="btn btn-cancel">Bỏ qua</button>
                                            <button type="submit" name="op" value="logout" class="btn btn-outline-primary">
                                                <i class="fas fa-sign-out-alt me-1"></i> Đăng xuất
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Đăng nhập và bảo mật -->
                    <div class="card mt-4 border border-success shadow-sm">
                        <div class="card-body p-3">
                            <h5 class="mb-3" style="color:#12c512;font-weight:700;">
                                <i class="fas fa-shield-alt me-2"></i>Đăng nhập và bảo mật
                            </h5>
                            <div class="row align-items-center py-2 border-bottom">
                                <div class="col-auto">
                                    <span class="bg-light rounded-circle p-2 me-2"><i class="fas fa-lock fa-lg text-success"></i></span>
                                </div>
                                <div class="col">
                                    <div class="fw-bold"">Đổi mật khẩu</div>
                                    <div class="text-muted small">Sử dụng mật khẩu mạnh và thay đổi định kỳ 6 tháng/lần.</div>
                                </div>
                                <div class="col-auto">
                                    <button onclick="location.href = '<%=path%>/view/jsp/admin/Changepassword.jsp'" class="btn btn-outline-primary px-3"><i class="fas fa-pen me-1"></i>Chỉnh sửa</button>
                                </div>
                            </div>
                            <div class="row align-items-center py-2">
                                <div class="col-auto">
                                    <span class="bg-light rounded-circle p-2 me-2"><i class="fas fa-shield-alt fa-lg text-success"></i></span>
                                </div>
                                <div class="col">
                                    <div class="fw-bold">Sử dụng xác thực 2 lớp <i class="fas fa-info-circle text-muted" data-bs-toggle="tooltip" title="Bảo vệ tài khoản bằng xác thực 2 lớp"></i></div>
                                    <div class="text-muted small">Tắt &nbsp; <span class="ms-2">Số điện thoại nhận mã xác thực :</span></div>
                                </div>
                                <div class="col-auto">
                                    <button class="btn btn-outline-primary px-3"><i class="fas fa-power-off me-1"></i>Bật tính năng</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="<%=path%>/view/assets/js/feather.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="<%=path%>/view/assets/js/script.js"></script>
    </body>
</html>