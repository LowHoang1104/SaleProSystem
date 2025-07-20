<%-- 
    Document   : List_user
    Created on : May 22, 2025, 1:15:31 PM
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

        <link rel="stylesheet" href="view/assets/css/bootstrap-datetimepicker.min.css">

        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="view/assets/css/style.css">
        <style>
            /* Pagination Container */
            .pagination-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 10px;
                margin: 20px 0;
                padding: 15px;
                background: linear-gradient(145deg, #f8fff8 0%, #e8ffe8 100%);
                border-radius: 12px;
                box-shadow: 0 4px 16px rgba(40, 167, 69, 0.1);
                border: 1px solid rgba(40, 167, 69, 0.05);
                transition: all 0.3s ease;
            }

            .pagination-container:hover {
                box-shadow: 0 8px 20px rgba(40, 167, 69, 0.15);
                transform: translateY(-2px);
            }

            /* Pagination Wrapper */
            .pagination {
                display: flex;
                align-items: center;
                gap: 4px;
                flex-wrap: wrap;
                justify-content: center;
                padding: 5px;
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
                border: 1px solid rgba(0, 0, 0, 0.03);
            }

            /* Pagination Item */
            .pagination a {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                padding: 6px 8px;
                margin: 0 1px;
                text-decoration: none;
                background: white;
                color: #28a745;
                border: 1px solid #c3e6cb;
                border-radius: 6px;
                font-size: 10px;
                font-weight: 600;
                min-width: 24px;
                height: 24px;
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
            }

            /* Ripple Effect */
            .pagination a::before {
                content: '';
                position: absolute;
                top: 50%;
                left: 50%;
                width: 0;
                height: 0;
                background: rgba(40, 167, 69, 0.2);
                border-radius: 50%;
                transform: translate(-50%, -50%);
                transition: all 0.4s ease;
                z-index: 0;
            }

            .pagination a:hover::before {
                width: 50px;
                height: 50px;
            }

            /* Hover */
            .pagination a:hover {
                color: #1e7e34;
                border-color: #28a745;
                transform: translateY(-2px) scale(1.05);
                box-shadow: 0 4px 10px rgba(40, 167, 69, 0.25);
                background: linear-gradient(145deg, #f0fff4 0%, #d4edda 100%);
            }

            /* Active */
            .pagination a.active {
                background: #1b5e20;
                color: white;
                border-color: #1b5e20;
                box-shadow: 0 4px 12px rgba(27, 94, 32, 0.5);
                transform: translateY(-1px);
                font-weight: 700;
                z-index: 1;
            }

            .pagination a.active:hover {
                background: #104d17;
                box-shadow: 0 6px 20px rgba(16, 77, 23, 0.6);
                transform: translateY(-2px) scale(1.05);
            }

            .pagination a.active::before {
                background: rgba(255, 255, 255, 0.15);
            }


            /* First/Last Buttons */
            .pagination a:first-child,
            .pagination a:last-child {
                background: linear-gradient(145deg, #e6f9ec 0%, #c3e6cb 100%);
                border-color: #28a745;
                color: #1e7e34;
            }

            .pagination a:first-child:hover,
            .pagination a:last-child:hover {
                transform: translateY(-2px) scale(1.1);
            }

            /* Even page styling (optional) */
            .pagination a:nth-child(even):not(.active) {
                background: linear-gradient(145deg, #eafbea 0%, #d4edda 100%);
                border-color: #a5d6a7;
                color: #388e3c;
            }

            .pagination a:nth-child(even):not(.active):hover {
                box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
            }

            /* Focus */
            .pagination a:focus {
                outline: none;
                box-shadow: 0 0 0 2px rgba(40, 167, 69, 0.3);
                transform: translateY(-1px);
            }

            /* Loading state */
            .pagination a.loading {
                pointer-events: none;
                opacity: 0.7;
                animation: pulse 1.5s infinite;
            }
        </style>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}">
        </c:set>
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
                            <h4>Danh sách nhân viên</h4>
                        </div>
                        <div class="page-btn">
                            <a href="SaveUserServlet" class="btn btn-added"><img src="view/assets/img/icons/plus.svg" alt="img">Thêm nhân viên</a>
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
                                    <div>
                                        <form action="ListUserServlet"style="display: flex">
                                            <input  type="text" name="keyword" value="${keyword}" placeholder="Search...">
                                            <input type="submit" value="Search">
                                        </form>                                   
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
                                        <form action="ListUserServlet" style="display: flex">
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" placeholder="Nhập tên" name="fullName">
                                                </div>
                                            </div>
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <input type="text" placeholder="Nhập Email" name="email">
                                                </div>
                                            </div>
                                            <div class="col-lg-2 col-sm-6 col-12">
                                                <div class="form-group">
                                                    <select class="select" name="isActive">
                                                        <option value="">Trạng thái</option>
                                                        <option value="true">Hoạt động</option>
                                                        <option value="false">Khóa</option>
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
                                            <th>Avatar</th>
                                            <th>Họ và tên</th>
                                            <th>Email</th>
                                            <th>Vai trò</th>
                                            <th>Ngày tạo</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="users" items="${listUser}">
                                            <c:if test="${users.getRoleId() != 1}">
                                                <tr>
                                                    <td>
                                                        <a href="javascript:void(0);" class="product-img">
                                                            <img src="view/assets/img/user/${users.getAvatar()}">
                                                        </a>
                                                    </td>
                                                    <td>${users.getFullName()}</td>
                                                    <td>${users.getEmail()}</td>
                                                    <td>${users.getEmpTypeName()}</td>
                                                    <td>${users.getCreatedAt()}</td>
                                                    <td><span class="${users.isIsActive()?"bg-lightgreen badges":"bg-lightred badges"}">${users.isIsActive()?"Active":"Restricted"}</span></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${users.getRoleId() != 1}">
                                                                <a class="me-3" href="SaveUserServlet?UserId=${users.getUserId()}" title="chỉnh sửa">
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
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="pagination">
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <a href="ListUserServlet?page=${i}"
                                           class="${i == currentPage ? 'active' : ''}">
                                            ${i}
                                        </a>
                                    </c:forEach>
                                </div>
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
                            window.location.href = 'LockUserServlet?keyword=${keyword}&page=${currentPage}&action=lock&userId=' + userId;
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
                            window.location.href = 'LockUserServlet?keyword=${keyword}&page=${currentPage}&action=unlock&userId=' + userId;
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
