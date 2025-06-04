<%-- 
    Document   : List_shift
    Created on : Jun 3, 2025, 1:01:18 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="Work Shifts Management System">
        <meta name="keywords" content="admin, shifts, work, schedule, employee">
        <meta name="author" content="PureCode AI">
        <meta name="robots" content="noindex, nofollow">
        <title>Quản Lý Ca Làm Việc - Admin</title>

        <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.png">

        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="view/assets/css/animate.css">
        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="view/assets/css/style.css">

        <style>
            .shift-card {
                border: 1px solid #e9ecef;
                border-radius: 10px;
                transition: all 0.3s ease;
                overflow: hidden;
            }
            .shift-card:hover {
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                transform: translateY(-2px);
            }
            .shift-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 20px;
                position: relative;
            }
            .shift-type {
                font-size: 1.2rem;
                font-weight: 600;
                margin-bottom: 5px;
            }
            .shift-time {
                font-size: 0.9rem;
                opacity: 0.9;
            }
            .shift-actions {
                position: absolute;
                top: 15px;
                right: 15px;
            }
            .shift-body {
                padding: 20px;
            }
            .shift-info {
                margin-bottom: 15px;
            }
            .shift-info .info-label {
                font-weight: 600;
                color: #6c757d;
                font-size: 0.85rem;
                text-transform: uppercase;
                margin-bottom: 5px;
            }
            .shift-info .info-value {
                font-size: 1rem;
                color: #2c3e50;
            }
            .employee-count {
                background-color: #f8f9fa;
                padding: 10px;
                border-radius: 8px;
                text-align: center;
                margin-top: 15px;
            }
            .employee-count .count-number {
                font-size: 1.5rem;
                font-weight: 700;
                color: #495057;
            }
            .employee-count .count-label {
                font-size: 0.8rem;
                color: #6c757d;
                text-transform: uppercase;
            }
            .shift-status {
                padding: 4px 12px;
                border-radius: 20px;
                font-size: 0.75rem;
                font-weight: 600;
                text-transform: uppercase;
            }
            .status-active {
                background-color: #d4edda;
                color: #155724;
            }
            .status-inactive {
                background-color: #f8d7da;
                color: #721c24;
            }
            .stats-card {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border-radius: 10px;
                padding: 20px;
                margin-bottom: 20px;
                text-align: center;
            }
            .stats-card h3 {
                margin-bottom: 5px;
                font-weight: 700;
            }
            .stats-card p {
                margin-bottom: 0;
                opacity: 0.9;
            }
            .filter-section {
                background-color: #f8f9fa;
                padding: 20px;
                border-radius: 10px;
                margin-bottom: 20px;
            }
            .modal-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border-radius: 10px 10px 0 0;
            }
            .modal-header .btn-close {
                filter: invert(1);
            }
            .time-input-group {
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .break-time-section {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 8px;
                margin-top: 15px;
            }
            .employee-assignment {
                max-height: 300px;
                overflow-y: auto;
            }
            .employee-item {
                display: flex;
                align-items: center;
                padding: 10px;
                border: 1px solid #e9ecef;
                border-radius: 8px;
                margin-bottom: 10px;
            }
            .employee-avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                margin-right: 10px;
            }
            .shift-color-indicator {
                width: 20px;
                height: 20px;
                border-radius: 50%;
                display: inline-block;
                margin-right: 10px;
            }
            .color-morning {
                background-color: #28a745;
            }
            .color-afternoon {
                background-color: #17a2b8;
            }
            .color-evening {
                background-color: #ffc107;
            }
            .color-night {
                background-color: #6f42c1;
            }
            .color-full {
                background-color: #dc3545;
            }
        </style>
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">
            <!-- Header -->
            <div class="header">
                <div class="header-left active">
                    <a href="index.html" class="logo">
                        <img src="view/assets/img/logo.png" alt="">
                    </a>
                    <a href="index.html" class="logo-small">
                        <img src="view/assets/img/logo-small.png" alt="">
                    </a>
                    <a id="toggle_btn" href="javascript:void(0);"></a>
                </div>
                <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                    <span class="bar-icon">
                        <span></span>
                        <span></span>
                        <span></span>
                    </span>
                </a>
                <ul class="nav user-menu">
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
            </div>

            <!-- Sidebar -->
            <div class="sidebar" id="sidebar">
                <div class="sidebar-inner slimscroll">
                    <div id="sidebar-menu" class="sidebar-menu">
                        <ul>
                            <li><a href="index.html"><img src="view/assets/img/icons/dashboard.svg" alt="img"><span>Dashboard</span></a></li>
                            <li class="submenu">
                                <a href="javascript:void(0);" class="active subdrop"><img src="view/assets/img/icons/users1.svg" alt="img"><span> Nhân Viên</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="employee-management.html">Quản Lý Nhân Viên</a></li>
                                    <li><a href="work-shifts.html" class="active">Ca Làm Việc</a></li>
                                    <li><a href="employee-salary.html">Quản Lý Lương</a></li>
                                    <li><a href="employee-attendance.html">Chấm Công</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="productlist.html">Product List</a></li>
                                    <li><a href="addproduct.html">Add Product</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Main Content -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Quản Lý Ca Làm Việc</h4>
                            <h6>Tạo và quản lý các ca làm việc cho nhân viên</h6>
                        </div>
                        <div class="page-btn">
                            <a href="javascript:void(0);" class="btn btn-added" data-bs-toggle="modal" data-bs-target="#addShiftModal">
                                <img src="view/assets/img/icons/plus.svg" alt="img">Thêm Ca Mới
                            </a>
                        </div>
                    </div>

                    <!-- Statistics Cards -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="stats-card">
                                <h3>8</h3>
                                <p>Tổng Ca Làm Việc</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="stats-card" style="background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);">
                                <h3>6</h3>
                                <p>Ca Đang Hoạt Động</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="stats-card" style="background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);">
                                <h3>45</h3>
                                <p>Nhân Viên Được Phân Ca</p>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="stats-card" style="background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);">
                                <h3>168</h3>
                                <p>Tổng Giờ/Tuần</p>
                            </div>
                        </div>
                    </div>

                    <!-- Filter Section -->
                    <div class="filter-section">
                        <div class="row">
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Loại Ca</label>
                                    <select class="select" id="shiftTypeFilter">
                                        <option value="">Tất cả loại ca</option>
                                        <option value="morning">Ca Sáng</option>
                                        <option value="afternoon">Ca Chiều</option>
                                        <option value="evening">Ca Tối</option>
                                        <option value="night">Ca Đêm</option>
                                        <option value="full">Ca Ngày</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Trạng Thái</label>
                                    <select class="select" id="statusFilter">
                                        <option value="">Tất cả trạng thái</option>
                                        <option value="active">Đang hoạt động</option>
                                        <option value="inactive">Tạm dừng</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Phòng Ban</label>
                                    <select class="select" id="departmentFilter">
                                        <option value="">Tất cả phòng ban</option>
                                        <option value="sales">Kinh Doanh</option>
                                        <option value="tech">Kỹ Thuật</option>
                                        <option value="hr">Nhân Sự</option>
                                        <option value="accounting">Kế Toán</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Tìm Kiếm</label>
                                    <input type="text" class="form-control" placeholder="Tên ca, mô tả..." id="searchFilter">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Shifts Grid -->
                    <div class="row" id="shiftsContainer">
                        <c:forEach var="shifts" items="${shifts}">
                            <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                                <div class="card shift-card">
                                    <div class="shift-header" style="background: linear-gradient(135deg, #28a745 0%, #20c997 100%);">
                                        <div class="shift-type">
                                            <span class="shift-color-indicator color-morning"></span>
                                            ${shifts.getShiftName()}
                                        </div>
                                        <div class="shift-actions">
                                            <button class="btn btn-sm btn-light me-1" onclick="editShift(1)" title="Chỉnh sửa">
                                                <i class="fas fa-edit text-dark"></i>
                                            </button>
                                            <button class="btn btn-sm btn-light" onclick="deleteShift(1, 'Ca Sáng')" title="Xóa">
                                                <i class="fas fa-trash text-danger"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="shift-body">
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="shift-info">
                                                    <div class="info-label">Giờ làm</div>
                                                    <div class="info-value">${shifts.getStartTimeFormatted()} - ${shifts.getEndTimeFormatted()}</div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="shift-info">
                                                    <div class="info-label">Tổng Giờ</div>
                                                    <div class="info-value">${shifts.getTotalTimeFormatted()}</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="shift-info">
                                                    <div class="info-label">Trạng Thái</div>
                                                    <div class="info-value">
                                                        <span class="shift-status ${shifts.isIsActive()?'status-active':'status-inactive'}">${shifts.isIsActive()?'Hoạt động':'Đã đóng'}</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="shift-info">
                                                    <div class="info-label">Chi nhánh</div>
                                                    <div class="info-value">${shifts.getStoreName()}</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="employee-count">
                                            <div class="count-number">12</div>
                                            <div class="count-label">Nhân viên</div>
                                        </div>
                                        <div class="mt-3">
                                            <button class="btn btn-primary btn-sm w-100" onclick="window.location.href = 'ListShiftServlet?action=assignEmp&shiftId=${shifts.getShiftID()}'">
                                                <i class="fas fa-users me-1"></i>Phân Công Nhân Viên
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>


                    </div>
                </div>
            </div>
        </div>

        <!-- Add Shift Modal -->
        <div class="modal fade" id="addShiftModal" tabindex="-1" aria-labelledby="addShiftModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addShiftModalLabel">
                            <i class="fas fa-clock me-2"></i>Thêm Ca Làm Việc Mới
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="addShiftForm">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="shiftName" placeholder="Tên ca làm việc" required>
                                        <label for="shiftName">Tên Ca Làm Việc *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="shiftType" required>
                                            <option value="">Chọn loại ca</option>
                                            <option value="morning">Ca Sáng</option>
                                            <option value="afternoon">Ca Chiều</option>
                                            <option value="evening">Ca Tối</option>
                                            <option value="night">Ca Đêm</option>
                                            <option value="full">Ca Ngày</option>
                                        </select>
                                        <label for="shiftType">Loại Ca *</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" class="form-control" id="startTime" required>
                                        <label for="startTime">Giờ Bắt Đầu *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" class="form-control" id="endTime" required>
                                        <label for="endTime">Giờ Kết Thúc *</label>
                                    </div>
                                </div>
                            </div>

                            <div class="break-time-section">
                                <h6><i class="fas fa-coffee me-2"></i>Thời Gian Nghỉ</h6>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="time" class="form-control" id="breakStart">
                                            <label for="breakStart">Bắt Đầu Nghỉ</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="time" class="form-control" id="breakEnd">
                                            <label for="breakEnd">Kết Thúc Nghỉ</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="department">
                                            <option value="">Tất cả phòng ban</option>
                                            <option value="sales">Phòng Kinh Doanh</option>
                                            <option value="tech">Phòng Kỹ Thuật</option>
                                            <option value="hr">Phòng Nhân Sự</option>
                                            <option value="accounting">Phòng Kế Toán</option>
                                            <option value="security">Phòng Bảo Vệ</option>
                                        </select>
                                        <label for="department">Phòng Ban</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="shiftStatus">
                                            <option value="active">Hoạt động</option>
                                            <option value="inactive">Tạm dừng</option>
                                        </select>
                                        <label for="shiftStatus">Trạng Thái</label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-floating mb-3">
                                <textarea class="form-control" id="shiftDescription" placeholder="Mô tả
                                          <textarea class="form-control" id="shiftDescription" placeholder="Mô tả ca làm việc" style="height: 80px"></textarea>
                                <label for="shiftDescription">Mô Tả</label>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="allowOvertime">
                                <label class="form-check-label" for="allowOvertime">
                                    Cho phép làm thêm giờ
                                </label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit">Tạo Ca Làm Việc</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Edit Shift Modal -->
        <div class="modal fade" id="editShiftModal" tabindex="-1" aria-labelledby="editShiftModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editShiftModalLabel">
                            <i class="fas fa-edit me-2"></i>Chỉnh Sửa Ca Làm Việc
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="editShiftForm">
                        <div class="modal-body">
                            <input type="hidden" id="editShiftId">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" id="editShiftName" placeholder="Tên ca làm việc" required>
                                        <label for="editShiftName">Tên Ca Làm Việc *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="editShiftType" required>
                                            <option value="">Chọn loại ca</option>
                                            <option value="morning">Ca Sáng</option>
                                            <option value="afternoon">Ca Chiều</option>
                                            <option value="evening">Ca Tối</option>
                                            <option value="night">Ca Đêm</option>
                                            <option value="full">Ca Ngày</option>
                                        </select>
                                        <label for="editShiftType">Loại Ca *</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" class="form-control" id="editStartTime" required>
                                        <label for="editStartTime">Giờ Bắt Đầu *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" class="form-control" id="editEndTime" required>
                                        <label for="editEndTime">Giờ Kết Thúc *</label>
                                    </div>
                                </div>
                            </div>

                            <div class="break-time-section">
                                <h6><i class="fas fa-coffee me-2"></i>Thời Gian Nghỉ</h6>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="time" class="form-control" id="editBreakStart">
                                            <label for="editBreakStart">Bắt Đầu Nghỉ</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3">
                                            <input type="time" class="form-control" id="editBreakEnd">
                                            <label for="editBreakEnd">Kết Thúc Nghỉ</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="editDepartment">
                                            <option value="">Tất cả phòng ban</option>
                                            <option value="sales">Phòng Kinh Doanh</option>
                                            <option value="tech">Phòng Kỹ Thuật</option>
                                            <option value="hr">Phòng Nhân Sự</option>
                                            <option value="accounting">Phòng Kế Toán</option>
                                            <option value="security">Phòng Bảo Vệ</option>
                                        </select>
                                        <label for="editDepartment">Phòng Ban</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" id="editShiftStatus">
                                            <option value="active">Hoạt động</option>
                                            <option value="inactive">Tạm dừng</option>
                                        </select>
                                        <label for="editShiftStatus">Trạng Thái</label>
                                    </div>
                                </div>
                            </div>

                            <div class="form-floating mb-3">
                                <textarea class="form-control" id="editShiftDescription" placeholder="Mô tả ca làm việc" style="height: 80px"></textarea>
                                <label for="editShiftDescription">Mô Tả</label>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="editAllowOvertime">
                                <label class="form-check-label" for="editAllowOvertime">
                                    Cho phép làm thêm giờ
                                </label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit">Cập Nhật</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Assign Employees Modal -->
        <div class="modal fade" id="assignEmployeesModal" tabindex="-1" aria-labelledby="assignEmployeesModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="assignEmployeesModalLabel">
                            <i class="fas fa-users me-2"></i>Phân Công Nhân Viên - <span id="assignShiftName"></span>
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <h6>Danh Sách Nhân Viên</h6>
                                <div class="mb-3">
                                    <input type="text" class="form-control" placeholder="Tìm kiếm nhân viên..." id="employeeSearch">
                                </div>
                                <div class="employee-assignment" id="availableEmployees">
                                    <div class="employee-item" data-employee-id="1">
                                        <input type="checkbox" class="form-check-input me-2" id="emp1">
                                        <img src="view/assets/img/profiles/avator1.jpg" alt="Employee" class="employee-avatar">
                                        <div>
                                            <div class="fw-bold">Nguyễn Văn An</div>
                                            <small class="text-muted">Phòng Kinh Doanh</small>
                                        </div>
                                    </div>

                                    <div class="employee-item" data-employee-id="2">
                                        <input type="checkbox" class="form-check-input me-2" id="emp2">
                                        <img src="view/assets/img/profiles/avator2.jpg" alt="Employee" class="employee-avatar">
                                        <div>
                                            <div class="fw-bold">Trần Thị Bình</div>
                                            <small class="text-muted">Phòng Kỹ Thuật</small>
                                        </div>
                                    </div>

                                    <div class="employee-item" data-employee-id="3">
                                        <input type="checkbox" class="form-check-input me-2" id="emp3">
                                        <img src="view/assets/img/profiles/avator3.jpg" alt="Employee" class="employee-avatar">
                                        <div>
                                            <div class="fw-bold">Lê Văn Cường</div>
                                            <small class="text-muted">Phòng Nhân Sự</small>
                                        </div>
                                    </div>

                                    <div class="employee-item" data-employee-id="4">
                                        <input type="checkbox" class="form-check-input me-2" id="emp4">
                                        <img src="view/assets/img/profiles/avator4.jpg" alt="Employee" class="employee-avatar">
                                        <div>
                                            <div class="fw-bold">Phạm Thị Dung</div>
                                            <small class="text-muted">Phòng Kế Toán</small>
                                        </div>
                                    </div>

                                    <div class="employee-item" data-employee-id="5">
                                        <input type="checkbox" class="form-check-input me-2" id="emp5">
                                        <img src="view/assets/img/profiles/avator5.jpg" alt="Employee" class="employee-avatar">
                                        <div>
                                            <div class="fw-bold">Hoàng Văn Em</div>
                                            <small class="text-muted">Phòng Kinh Doanh</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <h6>Nhân Viên Đã Phân Công</h6>
                                <div class="employee-assignment" id="assignedEmployees">
                                    <div class="text-center text-muted py-4">
                                        <i class="fas fa-users fa-3x mb-3"></i>
                                        <p>Chưa có nhân viên nào được phân công</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-submit" onclick="saveEmployeeAssignment()">Lưu Phân Công</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
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
        <c:if test="${openAssignEmp}">
            <script>
                const modal = new bootstrap.Modal(document.getElementById('assignEmployeesModal'));
                modal.show();
            </script>
        </c:if>
        <script>
            // Shift data
            const shiftData = {
                1: {name: 'Ca Sáng', type: 'morning', startTime: '08:00', endTime: '12:00', breakStart: '10:00', breakEnd: '10:15'},
                2: {name: 'Ca Chiều', type: 'afternoon', startTime: '13:00', endTime: '17:00', breakStart: '15:00', breakEnd: '15:15'},
                3: {name: 'Ca Tối', type: 'evening', startTime: '18:00', endTime: '22:00', breakStart: '20:00', breakEnd: '20:15'},
                4: {name: 'Ca Đêm', type: 'night', startTime: '22:00', endTime: '06:00', breakStart: '02:00', breakEnd: '02:30'},
                5: {name: 'Ca Ngày', type: 'full', startTime: '08:00', endTime: '17:00', breakStart: '12:00', breakEnd: '13:00'}
            };

            // Edit Shift Function
            function editShift(shiftId) {
                const shift = shiftData[shiftId];
                if (!shift)
                    return;

                // Populate edit form
                document.getElementById('editShiftId').value = shiftId;
                document.getElementById('editShiftName').value = shift.name;
                document.getElementById('editShiftType').value = shift.type;
                document.getElementById('editStartTime').value = shift.startTime;
                document.getElementById('editEndTime').value = shift.endTime;
                document.getElementById('editBreakStart').value = shift.breakStart;
                document.getElementById('editBreakEnd').value = shift.breakEnd;

                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('editShiftModal'));
                modal.show();
            }

            // Delete Shift Function
            function deleteShift(shiftId, shiftName) {
                Swal.fire({
                    title: 'Xác nhận xóa?',
                    text: `Bạn có chắc chắn muốn xóa ca "${shiftName}"? Hành động này không thể hoàn tác!`,
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Xóa',
                    cancelButtonText: 'Hủy'
                }).then((result) => {
                    if (result.isConfirmed) {
                        Swal.fire({
                            title: 'Đã xóa!',
                            text: `Ca "${shiftName}" đã được xóa thành công.`,
                            icon: 'success',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            location.reload();
                        });
                    }
                });
            }



            // Save Employee Assignment
            function saveEmployeeAssignment() {
                const selectedEmployees = [];
                const checkboxes = document.querySelectorAll('#availableEmployees input[type="checkbox"]:checked');

                checkboxes.forEach(checkbox => {
                    const employeeItem = checkbox.closest('.employee-item');
                    const employeeId = employeeItem.dataset.employeeId;
                    const employeeName = employeeItem.querySelector('.fw-bold').textContent;
                    selectedEmployees.push({id: employeeId, name: employeeName});
                });

                if (selectedEmployees.length === 0) {
                    Swal.fire({
                        title: 'Thông báo',
                        text: 'Vui lòng chọn ít nhất một nhân viên.',
                        icon: 'warning'
                    });
                    return;
                }

                Swal.fire({
                    title: 'Thành công!',
                    text: `Đã phân công ${selectedEmployees.length} nhân viên vào ca làm việc.`,
                    icon: 'success',
                    timer: 2000,
                    showConfirmButton: false
                }).then(() => {
                    bootstrap.Modal.getInstance(document.getElementById('assignEmployeesModal')).hide();
                    location.reload();
                });
            }

            // Form submissions
            document.addEventListener('DOMContentLoaded', function () {
                // Add Shift Form
                const addShiftForm = document.getElementById('addShiftForm');
                if (addShiftForm) {
                    addShiftForm.addEventListener('submit', function (e) {
                        e.preventDefault();

                        Swal.fire({
                            title: 'Thành công!',
                            text: 'Ca làm việc mới đã được tạo thành công.',
                            icon: 'success',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            bootstrap.Modal.getInstance(document.getElementById('addShiftModal')).hide();
                            location.reload();
                        });
                    });
                }

                // Edit Shift Form
                const editShiftForm = document.getElementById('editShiftForm');
                if (editShiftForm) {
                    editShiftForm.addEventListener('submit', function (e) {
                        e.preventDefault();

                        Swal.fire({
                            title: 'Thành công!',
                            text: 'Ca làm việc đã được cập nhật thành công.',
                            icon: 'success',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            bootstrap.Modal.getInstance(document.getElementById('editShiftModal')).hide();
                            location.reload();
                        });
                    });
                }

                // Initialize Select2
                if (typeof $.fn.select2 !== 'undefined') {
                    $('.select').select2({
                        width: '100%'
                    });
                }

                // Filter functionality
                const filterInputs = document.querySelectorAll('.filter-section select, .filter-section input');
                filterInputs.forEach(input => {
                    input.addEventListener('change', function () {
                        console.log('Filter changed:', this.value);
                        // Implement filter logic here
                    });
                });

                // Employee search
                const employeeSearch = document.getElementById('employeeSearch');
                if (employeeSearch) {
                    employeeSearch.addEventListener('input', function () {
                        const searchTerm = this.value.toLowerCase();
                        const employeeItems = document.querySelectorAll('#availableEmployees .employee-item');

                        employeeItems.forEach(item => {
                            const employeeName = item.querySelector('.fw-bold').textContent.toLowerCase();
                            const department = item.querySelector('.text-muted').textContent.toLowerCase();

                            if (employeeName.includes(searchTerm) || department.includes(searchTerm)) {
                                item.style.display = 'flex';
                            } else {
                                item.style.display = 'none';
                            }
                        });
                    });
                }

                // Employee checkbox handling
                const employeeCheckboxes = document.querySelectorAll('#availableEmployees input[type="checkbox"]');
                employeeCheckboxes.forEach(checkbox => {
                    checkbox.addEventListener('change', function () {
                        updateAssignedEmployees();
                    });
                });
            });

            // Update assigned employees display
            function updateAssignedEmployees() {
                const assignedContainer = document.getElementById('assignedEmployees');
                const selectedCheckboxes = document.querySelectorAll('#availableEmployees input[type="checkbox"]:checked');

                if (selectedCheckboxes.length === 0) {
                    assignedContainer.innerHTML = `
                        <div class="text-center text-muted py-4">
                            <i class="fas fa-users fa-3x mb-3"></i>
                            <p>Chưa có nhân viên nào được phân công</p>
                        </div>
                    `;
                    return;
                }

                let assignedHTML = '';
                selectedCheckboxes.forEach(checkbox => {
                    const employeeItem = checkbox.closest('.employee-item');
                    const employeeName = employeeItem.querySelector('.fw-bold').textContent;
                    const department = employeeItem.querySelector('.text-muted').textContent;
                    const avatar = employeeItem.querySelector('.employee-avatar').src;

                    assignedHTML += `
                        <div class="employee-item">
                            <img src="${avatar}" alt="Employee" class="employee-avatar">
                            <div class="flex-grow-1">
                                <div class="fw-bold">${employeeName}</div>
                                <small class="text-muted">${department}</small>
                            </div>
                            <button class="btn btn-sm btn-outline-danger" onclick="removeEmployee('${checkbox.id}')">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    `;
                });

                assignedContainer.innerHTML = assignedHTML;
            }

            // Remove employee from assignment
            function removeEmployee(checkboxId) {
                const checkbox = document.getElementById(checkboxId);
                if (checkbox) {
                    checkbox.checked = false;
                    updateAssignedEmployees();
                }
            }

            // Calculate total hours
            function calculateTotalHours(startTime, endTime, breakStart, breakEnd) {
                // Implementation for calculating total working hours
                return 8; // Placeholder
            }
        </script>
    </body>
</html>

