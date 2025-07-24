<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Đổi mật khẩu - Dreams Pos admin template</title>

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
                            <h4>Đổi mật khẩu</h4>
                            <h6>Quản lý mật khẩu tài khoản</h6>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-8 col-sm-12 mx-auto">
                                    <form action="<%=path%>/ProfileController" method="post">
                                        <div style="color: red">${error}<div>
                                        <div class="form-group">
                                            <label>Mật khẩu hiện tại</label>
                                            <div class="pass-group">
                                                <input type="password" class="form-control pass-input" name="currentPassword" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Mật khẩu mới</label>
                                            <div class="pass-group">
                                                <input type="password" class="form-control pass-input" name="newPassword" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>Xác nhận mật khẩu mới</label>
                                            <div class="pass-group">
                                                <input type="password" class="form-control pass-input" name="confirmPassword" required>
                                            </div>
                                        </div>
                                        <div class="text-end mt-3">
                                            <button type="button"  onclick="location.href = '<%=path%>/view/jsp/admin/Profile.jsp'" class="btn btn-cancel me-2">Hủy</button>
                                            <button type="submit" name="op" value="resetpassword" class="btn btn-submit">Lưu thay đổi</button>
                                        </div>
                                    </form>
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