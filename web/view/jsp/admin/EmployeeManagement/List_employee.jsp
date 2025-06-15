<%-- 
    Document   : List_employee
    Created on : Jun 2, 2025, 9:05:41 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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

        <link rel="stylesheet" href="view/assets/css/bootstrap-datetimepicker.min.css">

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

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Employee List</h4>
                            <h6>Manage your Employee</h6>
                        </div>
                        <div class="page-btn">
                            <a href="AddUserServlet" class="btn btn-added"><img src="view/assets/img/icons/plus.svg" alt="img">Add User</a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-path">
                                        <a class="btn btn-filter" id="filter_search">
                                            <img src="view/assets/img/icons/filter.svg" alt="img">
                                            <span><img src="view/assets/img/icons/closes.svg" alt="img"></span>
                                        </a>
                                    </div>
                                    <div class="search-input">
                                        <a class="btn btn-searchset"><img src="view/assets/img/icons/search-white.svg" alt="img"></a>
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img src="view/assets/img/icons/pdf.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img src="view/assets/img/icons/excel.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img src="view/assets/img/icons/printer.svg" alt="img"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <form action="FilterUserServlet" method="POST" style="display: flex">
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" placeholder="Enter User Name" name="userName">
                                                </div>
                                            </div>
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" placeholder="Enter Email" name="email">
                                                </div>
                                            </div>
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="isActive">
                                                        <option value="false">Disable</option>
                                                        <option value="true">Enable</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg- col-sm-6 col-12">
                                                <div class="form-group">
                                                    <button type="submit" class="btn btn-filters ms-auto">
                                                        <img src="view/assets/img/icons/search-whites.svg" alt="img">
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <table class="table datanew">
                                    <thead>
                                        <tr>
                                            <th>
                                                <label class="checkboxs">
                                                    <input type="checkbox">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </th>
                                            <th>Profile</th>
                                            <th>User name </th>
                                            <th>email</th>
                                            <th>Role</th>
                                            <th>Created On</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="users" items="${listUser}">
                                            <tr>
                                                <td>
                                                    <label class="checkboxs">
                                                        <input type="checkbox">
                                                        <span class="checkmarks"></span>
                                                    </label>
                                                </td>
                                                <td>
                                                    <a href="javascript:void(0);" class="product-img">
                                                        <img src="${users.getAvatar()}">
                                                    </a>
                                                </td>
                                                <td>${users.getUsername()}</td>
                                                <td>${users.getEmail()}</td>
                                                <td>${users.getRoleName()}</td>
                                                <td>${users.getCreatedAt()}</td>
                                                <td><span class="${users.isIsActive()?"bg-lightgreen badges":"bg-lightred badges"}">${users.isIsActive()?"Active":"Restricted"}</span></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${users.getRoleId() != 1}">
                                                            <a class="me-3" href="UpdateUserServlet?UserId=${users.getUserId()}" title="chỉnh sửa">
                                                                <img src="view/assets/img/icons/edit.svg" alt="img">
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="me-3 disabled" href="javascript:void(0);" style="pointer-events: none; opacity: 0.5;" title="chỉnh sửa">
                                                                <img src="view/assets/img/icons/edit.svg" alt="img">
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a href="javascript:void(0);" 
                                                       class="me-3 confirm-lock-btn" 
                                                       data-userid="${users.getUserId()}">
                                                        <img src="view/assets/img/icons/lock.svg" alt="Block"
                                                             title="Khóa">
                                                    </a>
                                                    <a href="javascript:void(0);" 
                                                       class="me-3 confirm-unlock-btn" 
                                                       data-userid="${users.getUserId()}"
                                                       title="Mở khóa">
                                                        <img src="view/assets/img/icons/unlock.svg" alt="Unblock">
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>





        <!-- Thêm jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            $(document).ready(function () {
                $('.confirm-lock-btn').click(function () {
                    var userId = $(this).data('userid');

                    Swal.fire({
                        title: 'Cảnh báo',
                        text: "Bạn có muốn vô hiệu hóa tài khoản này không ?",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#28a745',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Có'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Điều hướng đến servlet xử lý
                            window.location.href = 'LockUserServlet?action=lock&userId=' + userId;
                        }
                    });
                });
            });

        </script>
        <script>
            $(document).ready(function () {
                $('.confirm-unlock-btn').click(function () {
                    var userId = $(this).data('userid');

                    Swal.fire({
                        title: 'Cảnh báo',
                        text: "Bạn có muốn mở khóa tài khoản này không ?",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#28a745',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Có'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Điều hướng đến servlet xử lý
                            window.location.href = 'LockUserServlet?action=unlock&userId=' + userId;
                        }
                    });
                });
            });

        </script>

        <c:if test="${addUser}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Tạo người dùng mới thành công!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${lockAdminNotAllowed}">
            <script>
                Swal.fire({
                    icon: 'warning',
                    title: 'Cảnh báo',
                    text: 'Bạn không có quyền chặn admin này!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${unlockAdminNotAllowed}">
            <script>
                Swal.fire({
                    icon: 'warning',
                    title: 'Cảnh báo',
                    text: 'Bạn không có quyền thay đổi admin này!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>

        <c:if test="${lockSuccess}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Chặn người dùng thành công!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>

        <c:if test="${lockFail}">
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Thất bại',
                    text: 'Không thể chặn người dùng!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>
        <c:if test="${unlockSuccess}">
            <script>
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Kích hoạt người dùng thành công!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>

        <c:if test="${unlockFail}">
            <script>
                Swal.fire({
                    icon: 'error',
                    title: 'Thất bại',
                    text: 'Kích hoạt người dùng thất bại!',
                    confirmButtonText: 'OK'
                });
            </script>
        </c:if>



        <script data-cfasync="false" src="../../cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script>

        <script src="view/assets/js/feather.min.js"></script>

        <script src="view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="view/assets/js/jquery.dataTables.min.js"></script>
        <script src="view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="view/assets/js/moment.min.js"></script>
        <script src="view/assets/js/bootstrap-datetimepicker.min.js"></script>

        <script src="view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="view/assets/js/script.js"></script>
    </body>
</html>
