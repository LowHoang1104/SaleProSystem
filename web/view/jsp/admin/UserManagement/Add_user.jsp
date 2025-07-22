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
                            <form action="SaveUserServlet" method="POST" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <input type="hidden" name="UserId" value="${empId}">
                                        <div class="form-group">
                                            <label>User Name</label>
                                            <input type="text" name="username" value="${empUserName}">
                                        </div>
                                        <div class="form-group">
                                            <label>Full Name</label>
                                            <input type="text" name="fullName" value="${empFullName}">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Mobile</label>
                                            <input type="text" name="phone" value="${empPhone}">
                                        </div>                       
                                        <div class="form-group">
                                            <label>Chi nhánh</label>
                                            <select class="select" name="storeId">
                                                <c:forEach var="stores" items="${stores}">
                                                    <option value="${stores.getStoreID()}" 
                                                            ${empStoreId == stores.getStoreID() ? 'selected' : ''}>
                                                        ${stores.getStoreName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </div>

                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="text" name="email" value="${empEmail}">
                                        </div>
                                        <div class="form-group">
                                            <label>Role</label>
                                            <select class="select" name="employeeTypeId">
                                                <c:forEach var="employeeTypes" items="${employeeTypes}">
                                                    <option value="${employeeTypes.getEmployeeTypeID()}" 
                                                            ${empTypeId == employeeTypes.getEmployeeTypeID() ? 'selected' : ''}>
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
                                                    <img id="img" src="view/assets/img/user/${empAvatar}" alt="img">
                                                    <!-- Gửi lại avatar cũ nếu không upload ảnh mới -->
                                                    <input type="hidden" name="oldAvatar" value="${empAvatar}">
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
                // Kiểm tra xem có file được chọn không

                const file = files[0];

                // Danh sách các loại file ảnh được phép
                const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp', 'image/bmp'];

                // Kiểm tra loại file
                if (!allowedTypes.includes(file.type)) {
                    alert("Lỗi: Chỉ chấp nhận file ảnh (JPEG, JPG, PNG, GIF, WebP, BMP)!");
                    // Reset input file
                    const inputElement = document.querySelector(`input[onchange*="${id}"]`);
                    if (inputElement) {
                        inputElement.value = '';
                    }
                    return;
                }

                // Kiểm tra kích thước file (ví dụ: tối đa 5MB)
                const maxSize = 5 * 1024 * 1024; // 5MB trong bytes
                if (file.size > maxSize) {
                    alert("Lỗi: File quá lớn! Kích thước tối đa là 5MB.");
                    // Reset input file
                    const inputElement = document.querySelector(`input[onchange*="${id}"]`);
                    if (inputElement) {
                        inputElement.value = '';
                    }
                    return;
                }

                const reader = new FileReader();

                // Xử lý lỗi khi đọc file
                reader.addEventListener("error", function () {
                    alert("Lỗi: Không thể đọc file!");
                });

                // Lắng nghe sự kiện load hoàn tất
                reader.addEventListener("load", function () {
                    try {
                        // Lấy dữ liệu base64 của ảnh và gán vào src của thẻ img
                        const previewSrc = reader.result;
                        const imgElement = document.getElementById(id);

                        if (imgElement) {
                            imgElement.src = previewSrc;
                            imgElement.style.display = 'block'; // Hiển thị ảnh preview
                        } else {
                            alert("Lỗi: Không tìm thấy element để hiển thị ảnh!");
                        }
                    } catch (error) {
                        alert("Lỗi: Không thể hiển thị ảnh!");
                        console.error("Preview error:", error);
                    }
                });

                // Đọc file
                reader.readAsDataURL(file);
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
