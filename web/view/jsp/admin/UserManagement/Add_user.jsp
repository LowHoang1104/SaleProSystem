<%-- 
    Document   : Add_user
    Created on : May 22, 2025, 12:39:56 AM
    Author     : Thinhnt
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="16kb" autoFlush="true" %>
<%@ page errorPage="" %>
<%
    String path = request.getContextPath();
%>
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

            <div class="header">

                <div class="header-left active">
                    <a href="index.html" class="logo">
                        <img src="view/assets/img/logo.png" alt="">
                    </a>
                    <a href="index.html" class="logo-small">
                        <img src="view/assets/img/logo-small.png" alt="">
                    </a>
                    <a id="toggle_btn" href="javascript:void(0);">
                    </a>
                </div>

                <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                    <span class="bar-icon">
                        <span></span>
                        <span></span>
                        <span></span>
                    </span>
                </a>

                <ul class="nav user-menu">

                    <li class="nav-item">
                        <div class="top-nav-search">
                            <a href="javascript:void(0);" class="responsive-search">
                                <i class="fa fa-search"></i>
                            </a>
                            <form action="#">
                                <div class="searchinputs">
                                    <input type="text" placeholder="Search Here ...">
                                    <div class="search-addon">
                                        <span><img src="view/assets/img/icons/closes.svg" alt="img"></span>
                                    </div>
                                </div>
                                <a class="btn" id="searchdiv"><img src="view/assets/img/icons/search.svg" alt="img"></a>
                            </form>
                        </div>
                    </li>


                    <li class="nav-item dropdown has-arrow flag-nav">
                        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="javascript:void(0);" role="button">
                            <img src="view/assets/img/flags/us1.png" alt="" height="20">
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/us.png" alt="" height="16"> English
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/fr.png" alt="" height="16"> French
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/es.png" alt="" height="16"> Spanish
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/de.png" alt="" height="16"> German
                            </a>
                        </div>
                    </li>


                    <li class="nav-item dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                            <img src="view/assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">4</span>
                        </a>
                        <div class="dropdown-menu notifications">
                            <div class="topnav-dropdown-header">
                                <span class="notification-title">Notifications</span>
                                <a href="javascript:void(0)" class="clear-noti"> Clear All </a>
                            </div>
                            <div class="noti-content">
                                <ul class="notification-list">
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-02.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">John Doe</span> added new task <span class="noti-title">Patient appointment booking</span></p>
                                                    <p class="noti-time"><span class="notification-time">4 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-03.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Tarah Shropshire</span> changed the task name <span class="noti-title">Appointment booking with payment gateway</span></p>
                                                    <p class="noti-time"><span class="notification-time">6 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-06.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Misty Tison</span> added <span class="noti-title">Domenic Houston</span> and <span class="noti-title">Claire Mapes</span> to project <span class="noti-title">Doctor available module</span></p>
                                                    <p class="noti-time"><span class="notification-time">8 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-17.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Rolland Webber</span> completed task <span class="noti-title">Patient and Doctor video conferencing</span></p>
                                                    <p class="noti-time"><span class="notification-time">12 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-13.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Bernardo Galaviz</span> added new task <span class="noti-title">Private chat module</span></p>
                                                    <p class="noti-time"><span class="notification-time">2 days ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="topnav-dropdown-footer">
                                <a href="activities.html">View all Notifications</a>
                            </div>
                        </div>
                    </li>

                    <li class="nav-item dropdown has-arrow main-drop">
                        <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                            <span class="user-img"><img src="view/assets/img/profiles/avator1.jpg" alt="">
                                <span class="status online"></span></span>
                        </a>
                        <div class="dropdown-menu menu-drop-user">
                            <div class="profilename">
                                <div class="profileset">
                                    <span class="user-img"><img src="view/assets/img/profiles/avator1.jpg" alt="">
                                        <span class="status online"></span></span>
                                    <div class="profilesets">
                                        <h6>John Doe</h6>
                                        <h5>Admin</h5>
                                    </div>
                                </div>
                                <hr class="m-0">
                                <a class="dropdown-item" href="profile.html"> <i class="me-2" data-feather="user"></i> My Profile</a>
                                <a class="dropdown-item" href="generalsettings.html"><i class="me-2" data-feather="settings"></i>Settings</a>
                                <hr class="m-0">
                                <a class="dropdown-item logout pb-0" href="signin.html"><img src="view/assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>


                <div class="dropdown mobile-user-menu">
                    <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="profile.html">My Profile</a>
                        <a class="dropdown-item" href="generalsettings.html">Settings</a>
                        <a class="dropdown-item" href="signin.html">Logout</a>
                    </div>
                </div>
            </div>
            <div class="sidebar" id="sidebar">
                <div class="sidebar-inner slimscroll">
                    <div id="sidebar-menu" class="sidebar-menu">
                        <ul>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span> People</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="<%=path%>/ListCustomerServlet">Customer List</a></li>
                                    <li><a href="<%=path%>/SaveCustomerServlet">Add Customer </a></li>
                                    <li><a href="<%=path%>/ListUserServlet">User List</a></li>
                                    <li><a href="<%=path%>/SaveUserServlet">Add User</a></li>
                                    <li><a href="<%=path%>/ListUserPermissionServlet">Manage Permissions</a></li>
                                </ul>
                            </li> 
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span>Nhân viên</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="<%=path%>/ListShiftServlet">Ca làm việc</a></li>
                                    <li><a href="<%=path%>/ListWorkScheduleServlet">Lịch làm việc</a></li>
                                </ul>
                            </li> 
                        </ul>
                    </div>
                </div>
            </div>

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
