<%-- 
    Document   : Add_user
    Created on : May 22, 2025, 12:39:56 AM
    Author     : Thinhnt
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath();%>
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

        <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="view/assets/css/animate.css">

        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="view/assets/css/style.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>User Management</h4>
                            <h6>Add/Update User</h6>
                        </div>
                    </div>
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String message = (String) request.getAttribute("error"); %>
                    <% if (message != null) { %>
                    <div class="alert <%= message.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= message %>
                    </div>
                    <% } %>
                    <div class="card">
                        <div class="card-body">
                            <form action="UpdateUserServlet" method="POST">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <input type="hidden" name="UserId" value="${employee.getUserID()}">
                                        <div class="form-group">
                                            <label>User Name</label>
                                            <input type="text" name="username" value="${user.getUsername()}">
                                        </div>
                                        <div class="form-group">
                                            <label>Full Name</label>
                                            <input type="text" name="fullName" value="${employee.getFullName()}">
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <div class="pass-group">
                                                <input type="password" class="pass-input" name="password" value="">
                                                <span class="fas toggle-password fa-eye-slash"></span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Mobile</label>
                                            <input type="text" name="phone" value="${employee.getPhone()}">
                                        </div>                       
                                        <div class="form-group">
                                            <label>Chi nhánh</label>
                                            <select class="select" name="storeId">
                                                <c:forEach var="stores" items="${stores}">
                                                    <option value="${stores.getStoreID()}" 
                                                            ${employee.getStoreID() == stores.getStoreID() ? 'selected' : ''}>
                                                        ${stores.getStoreName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Confirm Password</label>
                                            <div class="pass-group">
                                                <input type="password" class="pass-inputs" name="confirmPassword" value="">
                                                <span class="fas toggle-passworda fa-eye-slash"></span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="text" name="email" value="${user.getEmail()}">
                                        </div>
                                        <div class="form-group">
                                            <label>Role</label>
                                            <select class="select" name="employeeTypeId">
                                                <c:forEach var="employeeTypes" items="${employeeTypes}">
                                                    <option value="${employeeTypes.getEmployeeTypeID()}" 
                                                            ${employee.getEmployeeTypeID() == employeeTypes.getEmployeeTypeID() ? 'selected' : ''}>
                                                        ${employeeTypes.getTypeName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Profile Picture</label>
                                            <div class="image-upload image-upload-new">
                                                <input type="file" name="avatar" onchange="previewImage(this.files, 'img')">
                                                <div class="image-uploads">
                                                    <img id="img" src="${user.getAvatar()}" alt="img">
                                                    <!-- Gửi lại avatar cũ nếu không upload ảnh mới -->
                                                    <input type="hidden" name="oldAvatar" value="${user.getAvatar()}">
                                                    <h4>Drag and drop a file to upload</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-12">
                                        <button type="submit" name="action" value="submit" class="btn btn-submit me-2">Submit</button>
                                        <a href="ListUserServlet" class="btn btn-cancel">Cancel</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <c:if test="${updateSuccess}">
            <script>
                                                    Swal.fire({
                                                        icon: 'success',
                                                        title: 'Thành công',
                                                        text: 'Cập nhật người dùng thành công!',
                                                        confirmButtonText: 'OK'
                                                    }).then((result) => {
                                                        if (result.isConfirmed) {
                                                            window.location.href = 'ListUserServlet';
                                                        }
                                                    });
            </script>
        </c:if>

        <script>
            function previewImage(files, id) {
                const reader = new FileReader();

                // Lắng nghe sự kiện load hoàn tất
                reader.addEventListener("load", function () {
                    // Lấy dữ liệu base64 của ảnh và gán vào src của thẻ img
                    const previewSrc = reader.result;
                    document.getElementById(id).src = previewSrc;
                }, false);

                // Đọc file nếu có file được chọn
                if (files[0]) {
                    reader.readAsDataURL(files[0]);
                }
            }



        </script>


        <script src="view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="view/assets/js/feather.min.js"></script>

        <script src="view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="view/assets/js/jquery.dataTables.min.js"></script>
        <script src="view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="view/assets/js/script.js"></script>
    </body>
</html>
