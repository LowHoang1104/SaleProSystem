<%-- 
    Document   : List_role
    Created on : May 26, 2025, 12:44:04 PM
    Author     : Thinhnt
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="16kb" autoFlush="true" %>
<%@ page errorPage="" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="Inventory Management Admin Dashboard">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="PureCode AI">
        <meta name="robots" content="noindex, nofollow">
        <title>Quản Lý Vai Trò - Admin</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.png">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="view/assets/css/animate.css">

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">

        <!-- Datatable CSS -->
        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">
        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="view/assets/css/style.css">
        <style>
            .role-card {
                transition: all 0.3s ease;
                border: 1px solid #e9ecef;
                height: 350px; /* Cố định chiều cao card */
                display: flex;
                flex-direction: column;
            }

            .role-card:hover {
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transform: translateY(-2px);
            }

            .role-card .card-body {
                display: flex;
                flex-direction: column;
                height: 100%;
                padding: 20px;
            }

            .permission-badge {
                background-color: #f8f9fa;
                color: #6c757d;
                padding: 2px 8px;
                border-radius: 12px;
                font-size: 0.75rem;
                margin: 2px;
                display: inline-block;
            }

            .role-actions {
                position: absolute;
                top: 20px;
                right: 20px;
                z-index: 10; /* Đảm bảo nút luôn ở trên */
                display: flex;
                gap: 5px; /* Khoảng cách giữa các nút */
            }

            .role-header {
                position: relative;
                padding-bottom: 15px;
                border-bottom: 1px solid #e9ecef;
                margin-bottom: 15px;
                flex-shrink: 0; /* Không cho phép co lại */
                min-height: 80px; /* Chiều cao tối thiểu cho header */
                padding-right: 90px; /* Tạo không gian cho nút actions */
            }

            .role-header h5 {
                margin-bottom: 8px;
                font-size: 1.1rem;
                font-weight: 600;
                line-height: 1.3;
                word-wrap: break-word; /* Xuống dòng khi text quá dài */
                overflow-wrap: break-word;
            }

            .role-header p {
                font-size: 0.9rem;
                margin-bottom: 0;
                line-height: 1.4;
                word-wrap: break-word; /* Xuống dòng khi text quá dài */
                overflow-wrap: break-word;
            }

            .permission-list {
                flex: 1; /* Chiếm không gian còn lại */
                overflow-y: auto; /* Scroll khi quá nhiều permission */
                margin-bottom: 15px;
                max-height: 180px; /* Giới hạn chiều cao tối đa */
                min-height: 120px; /* Chiều cao tối thiểu */
            }

            .permission-list::-webkit-scrollbar {
                width: 4px;
            }

            .permission-list::-webkit-scrollbar-track {
                background: #f1f1f1;
                border-radius: 2px;
            }

            .permission-list::-webkit-scrollbar-thumb {
                background: #c1c1c1;
                border-radius: 2px;
            }

            .permission-list::-webkit-scrollbar-thumb:hover {
                background: #a8a8a8;
            }

            .role-footer {
                flex-shrink: 0; /* Không cho phép co lại */
                border-top: 1px solid #e9ecef;
                padding-top: 10px;
                margin-top: auto; /* Đẩy xuống dưới cùng */
            }

            /* Responsive cho các nút actions */
            @media (max-width: 768px) {
                .role-actions {
                    position: static;
                    margin-top: 10px;
                    justify-content: flex-end;
                }
                .role-header {
                    padding-right: 20px;
                }
            }

            /* Đảm bảo các nút không bị che khuất */
            .role-actions .btn {
                min-width: 32px;
                height: 32px;
                padding: 6px;
                display: flex;
                align-items: center;
                justify-content: center;
                border-radius: 6px;
                transition: all 0.2s ease;
            }

            .role-actions .btn:hover {
                transform: scale(1.05);
            }

            /* Alternative layout - nếu muốn đặt nút ở dưới header */
            .role-header-alt {
                position: relative;
                padding-bottom: 15px;
                border-bottom: 1px solid #e9ecef;
                margin-bottom: 15px;
                flex-shrink: 0;
                min-height: 100px; /* Tăng chiều cao để chứa nút */
            }

            .role-actions-alt {
                position: absolute;
                bottom: 15px;
                right: 0;
                display: flex;
                gap: 5px;
            }

            .modal-lg {
                max-width: 900px;
            }

            .permission-group {
                border: 1px solid #dee2e6;
                border-radius: 8px;
                margin-bottom: 15px;
            }

            .permission-group-header {
                background-color: #f8f9fa;
                padding: 10px 15px;
                border-bottom: 1px solid #dee2e6;
                font-weight: 600;
                color: #495057;
            }

            .permission-group-body {
                padding: 15px;
            }

            .form-check {
                margin-bottom: 8px;
            }

            /* Đảm bảo tất cả các card trong cùng một row có chiều cao bằng nhau */
            .row-equal-height {
                display: flex;
                flex-wrap: wrap;
            }

            .row-equal-height > [class*='col-'] {
                display: flex;
                flex-direction: column;
            }

            .row-equal-height .role-card {
                flex: 1;
            }
        </style>
    </head>

    <body>
        <!-- Loader -->
        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <div class="main-wrapper">
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <!-- Main Content -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Quản Lý Vai Trò</h4>
                            <h6>Tạo và quản lý các vai trò người dùng trong hệ thống</h6>
                        </div>
                        <div class="page-btn">
                            <a href="javascript:void(0);" class="btn btn-added" data-bs-toggle="modal"
                               data-bs-target="#addRoleModal">
                                <img src="view/assets/img/icons/plus.svg" alt="img">Thêm Vai Trò Mới
                            </a>
                        </div>
                    </div>

                    <!-- Roles Grid -->
                    <div class="row row-equal-height" id="rolesContainer">
                        <!-- Role Card 1 -->
                        <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                            <div class="card role-card">
                                <div class="card-body">
                                    <div class="role-header">
                                        <h5 class="card-title">Quản Trị Viên</h5>
                                        <div class="role-actions">
                                            <button class="btn btn-sm btn-primary" onclick="window.location.href = 'ListUserPermissionServlet?isEditAdmin=false'" title="Sửa quyền">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-sm btn-danger" onclick="window.location.href = 'ListUserPermissionServlet?isEditAdmin=false'"
                                                    title="Xóa vai trò">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="permission-list">
                                        <c:forEach var="permissions" items="${permissions}">
                                            <span class="permission-badge">${permissions.getPermissionName()}</span>
                                        </c:forEach>
                                    </div>
                                    <div class="role-footer">
                                        <small class="text-muted">
                                            <i class="fas fa-users me-1"></i>
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:forEach var="employeeTypes" items="${employeeTypes}">
                            <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                                <div class="card role-card">
                                    <div class="card-body">
                                        <div class="role-header">
                                            <h5 class="card-title">${employeeTypes.getTypeName()}</h5>
                                            <div class="role-actions">
                                                <button class="btn btn-sm btn-primary" onclick="window.location.href = 'ListUserPermissionServlet?action=update&empTypeId=${employeeTypes.getEmployeeTypeID()}&openUpdateRole=true'" title="Sửa quyền">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <button class="btn btn-sm btn-danger"  onclick="confirmDelete(${employeeTypes.getEmployeeTypeID()})"
                                                        title="Xóa vai trò">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="permission-list">
                                            <c:forEach var="permissionEmp" items="${userPemissions[employeeTypes. getEmployeeTypeID()]}">
                                                <span class="permission-badge">${permissionEmp.getPermissionName()}</span>
                                            </c:forEach>
                                        </div>
                                        <div class="role-footer">
                                            <small class="text-muted">
                                                <i class="fas fa-users me-1"></i>${employeeTypes.countEmp()} người dùng
                                            </small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!-- Add Role Modal -->
        <div class="modal fade" id="addRoleModal" tabindex="-1" aria-labelledby="addRoleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addRoleModalLabel">Thêm Vai Trò Mới</h5>
                    </div>
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String message = (String) request.getAttribute("error"); %>
                    <% if (message != null) { %>
                    <div class="alert <%= message.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= message %>
                    </div>
                    <% } %>
                    <form id="addEmployeeTypeForm" action="AddEmployeeTypeServlet" method="post">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Tên Vai Trò <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" id="roleName" name="typeName" value="${typeName}" required>
                                    </div>
                                </div>
                            </div>

                            <div class="row mt-3">
                                <div class="col-12">
                                    <h6>Phân Quyền</h6>
                                    <div class="row">
                                        <c:forEach var="category" items="${categories}">
                                            <div class="col-md-6">
                                                <div class="permission-group">
                                                    <div class="permission-group-header">
                                                        ${category.getCategoryPerName()}
                                                    </div>
                                                    <div class="permission-group-body">
                                                        <c:forEach var="perByCate" items="${perByCategory[category.getCategoryPerId()]}">
                                                            <div class="form-check">
                                                                <input class="form-check-input system-perm" type="checkbox"
                                                                       value="${perByCate.getPermissionID()}" name="permissionIDs" id="perm${perByCate.getPermissionID()}"
                                                                       <c:if test="${selectedPermissions != null && selectedPermissions.contains(perByCate.getPermissionID())}">checked</c:if>>
                                                                <label class="form-check-label" for="perm${perByCate.getPermissionID()}">${perByCate.getPermissionName()}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>


                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit">Tạo Vai Trò</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Edit Role Modal -->
        <div class="modal fade" id="editRoleModal" tabindex="-1" aria-labelledby="editRoleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editRoleModalLabel">Chỉnh Sửa Vai Trò</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String messageUp = (String) request.getAttribute("errorUp"); %>
                    <% if (messageUp != null) { %>
                    <div class="alert <%= messageUp.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= messageUp %>
                    </div>
                    <% } %>
                    <form id="editRoleForm" action="AddEmployeeTypeServlet" method="post">
                        <div class="modal-body">
                            <input type="hidden" id="editRoleId">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <label>ID</label>
                                        <input type="text" class="form-control" id="editRoleDescription" name="empTypeId" value="${empTypeId}" readonly>
                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <div class="form-group">
                                        <label>Tên Vai Trò <span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" id="editRoleName" name="typeName" value="${empTypeName}" required>
                                    </div>
                                </div>

                            </div>

                            <div class="row mt-3">
                                <div class="col-12">
                                    <h6>Phân Quyền</h6>
                                    <div class="row">
                                        <c:forEach var="category" items="${categories}">
                                            <div class="col-md-6">
                                                <div class="permission-group">
                                                    <div class="permission-group-header">
                                                        ${category.getCategoryPerName()}
                                                    </div>
                                                    <div class="permission-group-body">
                                                        <c:set var="perByEmpTypeId" value="${perByEmpTypeId}"></c:set>
                                                        <c:forEach var="perByCate" items="${perByCategory[category.getCategoryPerId()]}">
                                                            <div class="form-check">
                                                                <input class="form-check-input system-perm" type="checkbox"
                                                                       value="${perByCate.getPermissionID()}" name="permissionIDs" id="per${perByCate.getPermissionID()}"
                                                                       <c:if test="${(perByEmpTypeId != null && perByEmpTypeId.contains(perByCate.getPermissionID())) || (selectedPermissions != null && selectedPermissions.contains(perByCate.getPermissionID()))}">checked</c:if>>
                                                                <label class="form-check-label" for="per${perByCate.getPermissionID()}">${perByCate.getPermissionName()}</label>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit">Cập Nhật Vai Trò</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Thêm -->
        <!-- Bootstrap 5 -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- SweetAlert2 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Feather Icons -->
        <script src="view/assets/js/feather.min.js"></script>
        <!-- Slimscroll -->
        <script src="view/assets/js/jquery.slimscroll.min.js"></script>
        <!-- Datatable -->
        <script src="view/assets/js/jquery.dataTables.min.js"></script>
        <script src="view/assets/js/dataTables.bootstrap4.min.js"></script>
        <!-- Select2 -->
        <script src="view/assets/plugins/select2/js/select2.min.js"></script>
        <!-- Datetimepicker -->
        <script src="view/assets/js/moment.min.js"></script>
        <script src="view/assets/js/bootstrap-datetimepicker.min.js"></script>
        <!-- SweetAlert -->
        <script src="view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
        <!-- Main Script -->
        <script src="view/assets/js/script.js"></script>
        <script>
                                                    document.querySelector('#addEmployeeTypeForm').addEventListener('submit', function (e) {
                                                        const checked = document.querySelectorAll('input[name="permissionIDs"]:checked');
                                                        if (checked.length === 0) {
                                                            e.preventDefault(); // ngăn submit
                                                            Swal.fire({
                                                                icon: 'error',
                                                                title: 'Lỗi',
                                                                text: 'Chưa có tính năng vai trò nào được chọn',
                                                                showConfirmButton: 'OK'
                                                            });
                                                        }
                                                    });
        </script>
        <script>
            document.querySelector('#editRoleForm').addEventListener('submit', function (e) {
                const checked = document.querySelectorAll('input[name="permissionIDs"]:checked');
                if (checked.length === 0) {
                    e.preventDefault(); // ngăn submit
                    Swal.fire({
                        icon: 'error',
                        title: 'Lỗi',
                        text: 'Chưa có tính năng vai trò nào được chọn',
                        showConfirmButton: 'OK'
                    });
                }
            });
        </script>
        <c:if test="${addEmployeeType}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Tạo vai trò mới thành công!',
                    confirmButtonText: 'OK'

                });
            </script>
        </c:if>
        <c:if test="${isEditAdmin}">
            <script>
                Swal.fire({
                    icon: 'warning',
                    title: 'Cảnh báo',
                    text: 'Bạn không thể chỉnh sửa quyền admin !',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${updateEmployeeType}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Chỉnh sửa vai trò thành công!',
                    confirmButtonText: 'OK'

                });
            </script>
        </c:if>
        <c:if test="${deleteEmpTypeFail}">
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Thất bại',
                    text: 'Không thể xóa vai trò này!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${deleteEmpTypeSuccess}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Xóa vai trò thành công!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${openAddRole}">
            <script>
                var modal = new bootstrap.Modal(document.getElementById('addRoleModal'));
                modal.show();
            </script>
        </c:if>
        <c:if test="${openUpdateRole}">
            <script>
                var modal = new bootstrap.Modal(document.getElementById('editRoleModal'));
                modal.show();
            </script>
        </c:if>
        <script>
            function confirmDelete(empTypeId) {
                Swal.fire({
                    title: 'Cảnh báo',
                    text: "Bạn có chắc chắn muốn xóa vai trò này không?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#6c757d',
                    confirmButtonText: 'Xóa',
                    cancelButtonText: 'Hủy'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Chuyển hướng tới servlet xử lý xóa
                        window.location.href = 'ListUserPermissionServlet?action=delete&empTypeId=' + empTypeId;
                    }
                });
            }
        </script>
        <script>
            $(document).ready(function () {
            // Xử lý sự kiện xóa vai trò với SweetAlert
            $('.confirm-delete-btn').click(function () {
            var roleId = $(this).data('roleid');
                    Swal.fire({
                    title: 'Bạn có chắc không?',
                            text: "Hành động này không thể hoàn tác!",
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#28a745',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Có, xóa!',
                            cancelButtonText: 'Hủy'
                    }).then((result) => {
            if (result.isConfirmed) {
            // Giả lập xóa vai trò (có thể thay bằng yêu cầu AJAX thực tế)
            Swal.fire({
            icon: 'success',
                    title: 'Thành công',
                    text: 'Đã xóa vai trò!',
                    confirmButtonText: 'OK'
            });
            }
            });
            });
                    // Xử lý sự kiện thêm vai trò mới
                    $('.btn-added').click(function () {
            var modal = new bootstrap.Modal(document.getElementById('addRoleModal'));
                    modal.show();
            });
        </script>
    </body>

</html>
