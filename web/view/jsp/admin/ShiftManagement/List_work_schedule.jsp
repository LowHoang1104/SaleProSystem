<%-- 
    Document   : List_work_schedule
    Created on : Jun 8, 2025, 10:49:26 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, user-scalable=0"
            />
        <meta name="description" content="POS - Bootstrap Admin Template" />
        <meta
            name="keywords"
            content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects"
            />
        <meta name="author" content="Dreamguys - Bootstrap Admin Template" />
        <meta name="robots" content="noindex, nofollow" />
        <title>Lịch làm việc - Quản lý ca làm việc</title>

        <!-- Favicon -->
        <link
            rel="shortcut icon"
            type="image/x-icon"
            href="view/assets/img/favicon.jpg"
            />

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css" />

        <!-- animation CSS -->
        <link rel="stylesheet" href="view/assets/css/animate.css" />

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css" />

        <!-- Datepicker CSS -->
        <link rel="stylesheet" href="view/assets/css/bootstrap-datetimepicker.min.css" />

        <!-- Fontawesome CSS -->
        <link
            rel="stylesheet"
            href="view/assets/plugins/fontawesome/css/fontawesome.min.css"
            />
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css" />

        <!-- Main CSS -->
        <link rel="stylesheet" href="view/assets/css/style.css" />

        <style>
            .shift-cell {
                height: 100px;
                vertical-align: top !important;
                position: relative;
                padding: 5px !important;
                border: 1px solid #e9ecef;
            }

            .shift-container {
                width: 100%;
                height: 100%;
                position: relative;
            }

            .shift-badges {
                display: flex;
                flex-direction: column;
                gap: 2px;
                height: 100%;
            }

            .shift-badge {
                display: block !important;
                width: 100% !important;
                padding: 4px 8px;
                border-radius: 4px;
                font-size: 11px;
                font-weight: 500;
                text-align: center;
                cursor: pointer;
                margin: 0 !important;
                flex-shrink: 0;
            }

            .shift-1,
            .shift-6 {
                background-color: #e0f7fa;
                color: #00838f;
                border: 1px solid #4dd0e1;
            }

            .shift-2,
            .shift-7 {
                background-color: #f1f8e9;
                color: #689f38;
                border: 1px solid #aed581;
            }

            .shift-3,
            .shift-8 {
                background-color: #fbe9e7;
                color: #d84315;
                border: 1px solid #ffab91;
            }

            .shift-4,
            .shift-9 {
                background-color: #ede7f6;
                color: #673ab7;
                border: 1px solid #d1c4e9;
            }

            .shift-5,
            .shift-10 {
                background-color: #efebe9;
                color: #4e342e;
                border: 1px solid #bcaaa4;
            }
            .add-shift-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 123, 255, 0.1);
                display: none;
                align-items: center;
                justify-content: center;
                border-radius: 4px;
                border: 2px dashed #007bff;
                z-index: 10;
            }

            .shift-cell:hover .add-shift-overlay {
                display: flex !important;
            }

            .add-shift-btn-overlay {
                background: #007bff;
                color: white;
                border: none;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 14px;
                cursor: pointer;
                transition: all 0.2s;
            }

            .add-shift-btn-overlay:hover {
                background: #0056b3;
                transform: scale(1.1);
            }

            .empty-shift-cell {
                height: 100px;
                vertical-align: middle;
                position: relative;
                border: 1px solid #e9ecef;
                padding: 5px !important;
            }

            .employee-cell {
                background-color: #f8f9fa !important;
                min-width: 150px;
                vertical-align: middle;
            }

            .employee-info {
                display: flex;
                align-items: center;
            }

            .employee-avatar {
                width: 45px;
                height: 45px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: bold;
                font-size: 14px;
                margin-right: 10px;
            }

            /* Override Bootstrap table styles */
            .table td {
                vertical-align: top !important;
            }

            /* Custom styles for schedule options */
            .schedule-options {
                background: #f8f9fa;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
            }

            .schedule-option {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 15px;
                padding-bottom: 15px;
                border-bottom: 1px solid #e9ecef;
            }

            .schedule-option:last-child {
                margin-bottom: 0;
                padding-bottom: 0;
                border-bottom: none;
            }

            .option-info h6 {
                margin: 0;
                font-weight: 600;
                color: #333;
            }

            .option-info p {
                margin: 0;
                font-size: 14px;
                color: #6c757d;
            }

            .toggle-switch {
                position: relative;
                width: 50px;
                height: 24px;
                background: #ccc;
                border-radius: 12px;
                cursor: pointer;
                transition: background 0.3s;
            }

            .toggle-switch.active {
                background: #007bff;
            }

            .toggle-switch::after {
                content: "";
                position: absolute;
                top: 2px;
                left: 2px;
                width: 20px;
                height: 20px;
                background: white;
                border-radius: 50%;
                transition: transform 0.3s;
            }

            .toggle-switch.active::after {
                transform: translateX(26px);
            }

            .week-days {
                display: flex;
                gap: 5px;
                margin-bottom: 15px;
            }

            .day-btn {
                padding: 8px 12px;
                border: 1px solid #dee2e6;
                background: white;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                transition: all 0.2s;
            }

            .day-btn.active {
                background: #007bff;
                color: white;
                border-color: #007bff;
            }

            .day-btn:hover {
                border-color: #007bff;
                color: #007bff;
            }

            .day-btn.active:hover {
                color: white;
            }

            .select-all-btn {
                background: #28a745 !important;
                color: white !important;
                border-color: #28a745 !important;
            }

            .select-all-btn:hover {
                background: #218838 !important;
                color: white !important;
                border-color: #1e7e34 !important;
            }

            .clear-all-btn {
                background: #dc3545 !important;
                color: white !important;
                border-color: #dc3545 !important;
            }

            .clear-all-btn:hover {
                background: #c82333 !important;
                color: white !important;
                border-color: #bd2130 !important;
            }

            .repeat-info {
                font-size: 14px;
                color: #6c757d;
                margin-bottom: 15px;
            }

            .date-range {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-bottom: 15px;
            }

            .date-range input {
                flex: 1;
            }

            .checkbox-option {
                display: flex;
                align-items: center;
                gap: 10px;
            }
            /* Employee Selection Styles */
            .employee-search-container {
                position: relative;
                margin-bottom: 15px;
            }

            .employee-search {
                width: 100%;
                padding: 8px 12px;
                border: 1px solid #dee2e6;
                border-radius: 4px;
                font-size: 14px;
            }

            .selected-employees {
                display: flex;
                flex-wrap: wrap;
                gap: 5px;
                margin-bottom: 10px;
            }

            .employee-tag {
                display: inline-flex;
                align-items: center;
                background: #007bff;
                color: white;
                padding: 4px 8px;
                border-radius: 12px;
                font-size: 12px;
                gap: 5px;
            }

            .employee-tag .remove-employee {
                cursor: pointer;
                font-weight: bold;
                margin-left: 5px;
            }

            .employee-tag .remove-employee:hover {
                color: #ffcccc;
            }

            .employee-dropdown {
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                background: white;
                border: 1px solid #dee2e6;
                border-top: none;
                border-radius: 0 0 4px 4px;
                max-height: 200px;
                overflow-y: auto;
                z-index: 1000;
                display: none;
            }

            .employee-item {
                padding: 10px 12px;
                cursor: pointer;
                border-bottom: 1px solid #f8f9fa;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .employee-item:hover {
                background: #f8f9fa;
            }

            .employee-item.selected {
                background: #e3f2fd;
            }

            .employee-item .employee-details {
                flex: 1;
            }

            .employee-item .employee-name {
                font-weight: 500;
                color: #333;
            }

            .employee-item .employee-code {
                font-size: 12px;
                color: #6c757d;
            }

            .employee-item .check-icon {
                color: #007bff;
                display: none;
            }

            .employee-item.selected .check-icon {
                display: block;
            }
        </style>
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <!-- Header -->
            <div class="header">
                <!-- Logo -->
                <div class="header-left active">
                    <a href="index.html" class="logo">
                        <img src="view/assets/img/logo.png" alt="" />
                    </a>
                    <a href="index.html" class="logo-small">
                        <img src="view/assets/img/logo-small.png" alt="" />
                    </a>
                    <a id="toggle_btn" href="javascript:void(0);"></a>
                </div>
                <!-- /Logo -->
                <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                    <span class="bar-icon">
                        <span></span>
                        <span></span>
                        <span></span>
                    </span>
                </a>

                <!-- Header Menu -->
                <ul class="nav user-menu">
                    <!-- Search -->
                    <li class="nav-item">
                        <div class="top-nav-search">
                            <a href="javascript:void(0);" class="responsive-search">
                                <i class="fa fa-search"></i>
                            </a>
                            <form action="#">
                                <div class="searchinputs">
                                    <input type="text" placeholder="Tìm kiếm..." />
                                    <div class="search-addon">
                                        <span
                                            ><img src="view/assets/img/icons/closes.svg" alt="img"
                                              /></span>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>
                    <!-- /Search -->

                    <li class="nav-item dropdown has-arrow flag-nav">
                        <a
                            class="nav-link dropdown-toggle"
                            data-bs-toggle="dropdown"
                            href="javascript:void(0);"
                            role="button"
                            >
                            <img src="view/assets/img/flags/us1.png" alt="" height="20" />
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/us.png" alt="" height="16" /> English
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="view/assets/img/flags/fr.png" alt="" height="16" /> French
                            </a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a
                            href="javascript:void(0);"
                            class="dropdown-toggle nav-link"
                            data-bs-toggle="dropdown"
                            >
                            <img src="view/assets/img/icons/notification-bing.svg" alt="img" />
                            <span class="badge rounded-pill">4</span>
                        </a>
                        <div class="dropdown-menu notifications">
                            <div class="topnav-dropdown-header">
                                <span class="notification-title">Thông báo</span>
                                <a href="javascript:void(0)" class="clear-noti"> Xóa tất cả </a>
                            </div>
                            <div class="noti-content">
                                <ul class="notification-list">
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="view/assets/img/profiles/avatar-02.jpg" />
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details">
                                                        <span class="noti-title">John Doe</span> đã thêm ca
                                                        làm việc mới
                                                    </p>
                                                    <p class="noti-time">
                                                        <span class="notification-time">4 phút trước</span>
                                                    </p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="topnav-dropdown-footer">
                                <a href="activities.html">Xem tất cả thông báo</a>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item dropdown has-arrow main-drop">
                        <a
                            href="javascript:void(0);"
                            class="dropdown-toggle nav-link userset"
                            data-bs-toggle="dropdown"
                            >
                            <span class="user-img"
                                  ><img src="view/assets/img/profiles/avator1.jpg" alt="" /><span
                                    class="status online"
                                    ></span
                                ></span>
                        </a>
                        <div class="dropdown-menu menu-drop-user">
                            <div class="profilename">
                                <div class="profileset">
                                    <span class="user-img"
                                          ><img src="view/assets/img/profiles/avator1.jpg" alt="" /><span
                                            class="status online"
                                            ></span
                                        ></span>
                                    <div class="profilesets">
                                        <h6>John Doe</h6>
                                        <h5>Admin</h5>
                                    </div>
                                </div>
                                <hr class="m-0" />
                                <a class="dropdown-item" href="profile.html"
                                   ><i class="me-2" data-feather="user"></i> Hồ sơ của tôi</a
                                >
                                <a class="dropdown-item" href="generalsettings.html"
                                   ><i class="me-2" data-feather="settings"></i>Cài đặt</a
                                >
                                <hr class="m-0" />
                                <a class="dropdown-item logout pb-0" href="signin.html"
                                   ><img
                                        src="view/assets/img/icons/log-out.svg"
                                        class="me-2"
                                        alt="img"
                                        />Đăng xuất</a
                                >
                            </div>
                        </div>
                    </li>
                </ul>
                <!-- /Header Menu -->
            </div>
            <!-- /Header -->

            <!-- Sidebar -->
            <div class="sidebar" id="sidebar">
                <div class="sidebar-inner slimscroll">
                    <div id="sidebar-menu" class="sidebar-menu">
                        <ul>
                            <li class="submenu">
                                <a href="javascript:void(0);"
                                   ><img src="view/assets/img/icons/dashboard.svg" alt="img" /><span>
                                        Dashboard</span
                                    >
                                    <span class="menu-arrow"></span
                                    ></a>
                                <ul>
                                    <li><a href="index.html">Admin Dashboard</a></li>
                                    <li><a href="dashboard-sales.html">Sales Dashboard</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"
                                   ><img src="view/assets/img/icons/users1.svg" alt="img" /><span>
                                        Nhân viên</span
                                    >
                                    <span class="menu-arrow"></span
                                    ></a>
                                <ul>
                                    <li><a href="userlist.html">Danh sách nhân viên</a></li>
                                    <li><a href="adduser.html">Thêm nhân viên</a></li>
                                    <li><a href="work-shifts.html">Ca làm việc</a></li>
                                    <li>
                                        <a href="work-schedule.html" class="active"
                                           >Lịch làm việc</a
                                        >
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- /Sidebar -->

            <!-- Page Wrapper -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Lịch làm việc</h4>
                            <h6>Quản lý ca làm việc cho nhân viên</h6>
                        </div>
                        <div class="page-btn">
                            <a
                                href="#"
                                class="btn btn-added"
                                data-bs-toggle="modal"
                                data-bs-target="#addShiftModal"
                                >
                                <img
                                    src="view/assets/img/icons/plus.svg"
                                    alt="img"
                                    class="me-2"
                                    />Thêm ca làm việc
                            </a>
                        </div>
                    </div>

                    <!-- Statistics Cards -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="dash-widget">
                                <div class="dash-widgetimg">
                                    <span
                                        ><img src="view/assets/img/icons/dash1.svg" alt="img"
                                          /></span>
                                </div>
                                <div class="dash-widgetcontent">
                                    <h5><span class="counters" data-count="24">24</span></h5>
                                    <h6>Tổng nhân viên</h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="dash-widget dash1">
                                <div class="dash-widgetimg">
                                    <span
                                        ><img src="view/assets/img/icons/dash2.svg" alt="img"
                                          /></span>
                                </div>
                                <div class="dash-widgetcontent">
                                    <h5><span class="counters" data-count="156">156</span></h5>
                                    <h6>Ca làm việc tuần này</h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="dash-widget dash2">
                                <div class="dash-widgetimg">
                                    <span
                                        ><img src="view/assets/img/icons/dash3.svg" alt="img"
                                          /></span>
                                </div>
                                <div class="dash-widgetcontent">
                                    <h5><span class="counters" data-count="18">18</span></h5>
                                    <h6>Đang làm việc</h6>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12">
                            <div class="dash-widget dash3">
                                <div class="dash-widgetimg">
                                    <span
                                        ><img src="view/assets/img/icons/dash4.svg" alt="img"
                                          /></span>
                                </div>
                                <div class="dash-widgetcontent">
                                    <h5><span class="counters" data-count="6">6</span></h5>
                                    <h6>Nghỉ phép</h6>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Week Navigation -->
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-md-6">
                                    <div class="d-flex align-items-center">
                                        <button class="btn btn-outline-primary me-3" id="prevWeek">
                                            <i class="fas fa-chevron-left"></i>
                                        </button>

                                        <div>
                                            <h5 class="mb-0 text-primary" id="currentWeekText">
                                                <%
                                                    String weekStartStr = request.getParameter("weekStart"); 
                                                    LocalDate weekStart = (weekStartStr != null && !weekStartStr.isEmpty())
                                                    ? LocalDate.parse(weekStartStr)
                                                    : LocalDate.now().with(java.time.DayOfWeek.MONDAY);
                                                    DateTimeFormatter weekFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                                    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM");
                                                    LocalDate weekEnd = weekStart.plusDays(6); 
                                                    String currentWeek = weekStart.format(weekFmt) + " - " + weekEnd.format(weekFmt);
                                                %>
                                                <%= currentWeek %>
                                            </h5>
                                        </div>
                                        <button class="btn btn-outline-primary ms-3" id="nextWeek">
                                            <i class="fas fa-chevron-right"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-md-6">

                                    <div class="d-flex justify-content-end">
                                        <select class="select me-2" id="departmentFilter">
                                            <option value="">Tất cả phòng ban</option>
                                            <option value="sales">Bán hàng</option>
                                            <option value="warehouse">Kho</option>
                                            <option value="admin">Hành chính</option>
                                        </select>
                                        <button class="btn btn-primary" id="todayBtn">
                                            Hôm nay
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Schedule Table -->
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th class="employee-cell">Nhân viên</th>
                                                <%
                                                    for(int i = 0; i < 7; i++){
                                                        LocalDate day = weekStart.plusDays(i);
                                                        String weekDay = day.getDayOfWeek().getValue() == 7 ? "Chủ Nhật" : "Thứ " + (day.getDayOfWeek().getValue() + 1);
                                                %>
                                            <th class="text-center"><%= weekDay %><br>
                                                <small class="text-muted">
                                                    <%= day.format(dateFmt)%>
                                                </small>
                                            </th>
                                            <% } %>
                                            <th >

                                        </tr>
                                    </thead>
                                    <tbody id="scheduleTableBody">
                                        <c:forEach var="emp" items="${employees}">
                                            <tr>
                                                <td class="employee-cell">
                                                    <div class="employee-info">
                                                        <div
                                                            class="employee-avatar"
                                                            >
                                                            <img src="${emp.getAvatar()}" >
                                                        </div>
                                                        <div>
                                                            <h6 class="mb-0" style="color: #000000;">${emp.getFullName()}</h6>
                                                            <small class="text-muted">${emp.getCode()}</small>
                                                        </div>
                                                    </div>
                                                </td>
                                                <c:forEach var="day" items="${weekDays}">
                                                    <td
                                                        class="shift-cell"
                                                        data-employee="${emp.getEmployeeID()}"
                                                        data-date="${day}"
                                                        >
                                                        <div class="shift-container">
                                                            <div class="shift-badges">
                                                                <c:forEach var="shiftEmp" items="${attendanceByEmpId[emp.getEmployeeID()]}">
                                                                    <c:if test="${shiftEmp.getWorkDate()==day}">
                                                                        <span class="shift-badge shift-${shiftEmp.getShiftId()}">${shiftEmp.getShiftName()}</span>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        <div class="add-shift-overlay">
                                                            <button
                                                                class="add-shift-btn-overlay"
                                                                data-employee="${emp.getEmployeeID()}"
                                                                data-date="${day}"
                                                                >
                                                                <i class="fas fa-plus"></i>
                                                            </button>
                                                        </div>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Page Wrapper -->
        </div>
        <!-- /Main Wrapper -->

        <!-- Add Shift Modal -->
        <div
            class="modal fade"
            id="addShiftModal"
            tabindex="-1"
            aria-labelledby="addShiftModalLabel"
            aria-hidden="true"
            >
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addShiftModalLabel">
                            Thêm ca làm việc
                        </h5>
                        <button
                            type="button"
                            class="btn-close"
                            data-bs-dismiss="modal"
                            aria-label="Close"
                            ></button>
                    </div>
                    <div class="modal-body">
                        <form action="SaveWorkScheduleServlet" method="post">
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Nhân viên <span class="manitory">*</span></label>
                                        <select class="select" id="employeeSelect" required>
                                            <option value="">Chọn nhân viên</option>
                                            <c:forEach var="emp" items="${employees}">
                                                <option value="${emp.getEmployeeID()}">${emp.getFullName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Ca làm việc <span class="manitory">*</span></label>
                                        <select class="select" id="shiftType" required>
                                            <option value="">Chọn ca làm việc</option>
                                            <c:forEach var="shift" items="${shifts}">
                                                <option value="${shift.getShiftID()}">${shift.getShiftName()} (${shift.getStartTime()} - ${shift.getEndTime()})</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label>Ngày làm việc <span class="manitory">*</span></label>
                                        <input type="date" class="form-control" id="workDate" name="workDate" required>
                                    </div>
                                </div>
                            </div>

                            <!-- Schedule Options -->
                            <div class="schedule-options">
                                <!-- Repeat Weekly Option -->
                                <div class="schedule-option">
                                    <div class="option-info">
                                        <h6>Lặp lại hàng tuần</h6>
                                        <p>
                                            Lịch làm việc sẽ được áp dụng lặp lại vào các ngày trong
                                            tuần
                                        </p>
                                    </div>
                                    <div class="toggle-switch" id="weeklyRepeatToggle"></div>
                                </div>

                                <!-- Weekly Schedule Section -->
                                <div id="weeklyScheduleSection" style="display: none">
                                    <div class="week-days">
                                        <div class="day-btn active" data-day="1">Thứ 2</div>
                                        <div class="day-btn" data-day="2">Thứ 3</div>
                                        <div class="day-btn" data-day="3">Thứ 4</div>
                                        <div class="day-btn" data-day="4">Thứ 5</div>
                                        <div class="day-btn" data-day="5">Thứ 6</div>
                                        <div class="day-btn" data-day="6">Thứ 7</div>
                                        <div class="day-btn" data-day="0">Chủ nhật</div>
                                        <div class="day-btn select-all-btn" id="selectAllBtn">
                                            Chọn tất cả
                                        </div>
                                    </div>
                                    <div class="repeat-info">Lặp lại thứ 2 hàng tuần</div>

                                    <!-- Date Range -->
                                    <div class="date-range">
                                        <label style="min-width: 60px">Kết thúc</label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" />
                                    </div>

                                    <!-- Holiday Option -->
                                    <div class="checkbox-option">
                                        <input type="checkbox" id="includeHolidays" />
                                        <label for="includeHolidays">Làm việc cả ngày lễ tết</label>
                                    </div>
                                </div>

                                <!-- Employee Assignment Option -->
                                <div class="schedule-option">
                                    <div class="option-info">
                                        <h6>Áp dụng cho nhân viên khác</h6>
                                        <p>
                                            Lịch làm việc sẽ được áp dụng cho các nhân viên được chọn
                                        </p>
                                    </div>
                                    <div class="toggle-switch" id="multiEmployeeToggle"></div>
                                </div>

                                <!-- Employee Selection Section -->
                                <div id="employeeSelectionSection" style="display: none">
                                    <!-- Selected Employees Tags -->
                                    <div class="selected-employees" id="selectedEmployees"></div>

                                    <!-- Employee Search -->
                                    <div class="employee-search-container">
                                        <input
                                            type="text"
                                            class="employee-search"
                                            placeholder="Tìm kiếm nhân viên"
                                            id="employeeSearch"
                                            />
                                        <div class="employee-dropdown" id="employeeDropdown">
                                            <c:forEach var="emp" items="${employees}">
                                                <div class="employee-item" data-employee="${emp.getEmployeeID()}">
                                                    <div class="employee-details">
                                                        <div class="employee-name">${emp.getFullName()}</div>
                                                        <div class="employee-code">${emp.getCode()}</div>
                                                    </div>
                                                    <div class="check-icon">
                                                        <i class="fas fa-check"></i>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-cancel"
                            data-bs-dismiss="modal"
                            >
                            Hủy
                        </button>
                        <button type="button" class="btn btn-submit" id="saveShiftBtn">
                            Lưu ca làm việc
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Shift Modal -->
        <div
            class="modal fade"
            id="editShiftModal"
            tabindex="-1"
            aria-labelledby="editShiftModalLabel"
            aria-hidden="true"
            >
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editShiftModalLabel">
                            Chỉnh sửa ca làm việc
                        </h5>
                        <button
                            type="button"
                            class="btn-close"
                            data-bs-dismiss="modal"
                            aria-label="Close"
                            ></button>
                    </div>
                    <div class="modal-body">
                        <form id="editShiftForm">
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Nhân viên</label>
                                        <input
                                            type="text"
                                            class="form-control"
                                            id="editEmployeeName"
                                            readonly
                                            />
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Ngày làm việc</label>
                                        <input
                                            type="text"
                                            class="form-control"
                                            id="editWorkDate"
                                            readonly
                                            />
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Ca làm việc <span class="manitory">*</span></label>
                                        <select class="select" id="editShiftType" required>
                                            <option value="morning">Ca sáng (6:00 - 14:00)</option>
                                            <option value="afternoon">
                                                Ca chiều (14:00 - 22:00)
                                            </option>
                                            <option value="evening">Ca tối (18:00 - 02:00)</option>
                                            <option value="night">Ca đêm (22:00 - 06:00)</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Vị trí làm việc</label>
                                        <select class="select" id="editWorkLocation">
                                            <option value="">Chọn vị trí</option>
                                            <option value="store1">Cửa hàng 1</option>
                                            <option value="store2">Cửa hàng 2</option>
                                            <option value="warehouse">Kho hàng</option>
                                            <option value="office">Văn phòng</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" id="deleteShiftBtn">
                            Xóa ca
                        </button>
                        <button
                            type="button"
                            class="btn btn-cancel"
                            data-bs-dismiss="modal"
                            >
                            Hủy
                        </button>
                        <button type="button" class="btn btn-submit" id="updateShiftBtn">
                            Cập nhật
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery -->
        <script src="view/assets/js/jquery-3.6.0.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="view/assets/js/feather.min.js"></script>

        <!-- Slimscroll JS -->
        <script src="view/assets/js/jquery.slimscroll.min.js"></script>

        <!-- Select2 JS -->
        <script src="view/assets/plugins/select2/js/select2.min.js"></script>

        <!-- Datepicker Core JS -->
        <script src="view/assets/js/moment.min.js"></script>
        <script src="view/assets/js/bootstrap-datetimepicker.min.js"></script>

        <!-- Bootstrap Core JS -->
        <script src="view/assets/js/bootstrap.bundle.min.js"></script>

        <!-- Custom JS -->
        <script src="view/assets/js/script.js"></script>

        <script>
            $(document).ready(function () {
                // Initialize Select2
                $(".select").select2();

                // Selected employees array
                var selectedEmployees = [];
                // Thêm js 

                let currentWeekStart = new Date("<%= weekStart.toString()%>");
                function formatDateToParam(date) {
                    return date.toISOString().split('T')[0];
                }
                // Week navigation events
                $("#prevWeek").click(function () {
                    currentWeekStart.setDate(currentWeekStart.getDate() - 7);
                    window.location.href = "ListWorkScheduleServlet?weekStart=" + formatDateToParam(currentWeekStart);
                });
                $("#nextWeek").click(function () {
                    currentWeekStart.setDate(currentWeekStart.getDate() + 7);
                    window.location.href = "ListWorkScheduleServlet?weekStart=" + formatDateToParam(currentWeekStart);
                });
                $("#todayBtn").click(function () {
                    let today = new Date();
                    let monday = new Date(today.setDate(today.getDate() - today.getDay() + 1));
                    window.location.href = "ListWorkScheduleServlet?weekStart=" + formatDateToParam(monday);
                });
                // Toggle switches
                $(".toggle-switch").click(function () {
                    $(this).toggleClass("active");
                    if ($(this).attr("id") === "weeklyRepeatToggle") {
                        if ($(this).hasClass("active")) {
                            $("#weeklyScheduleSection").show();
                        } else {
                            $("#weeklyScheduleSection").hide();
                        }
                    }

                    if ($(this).attr("id") === "multiEmployeeToggle") {
                        if ($(this).hasClass("active")) {
                            $("#employeeSelectionSection").show();
                        } else {
                            $("#employeeSelectionSection").hide();
                            selectedEmployees = [];
                            updateSelectedEmployeesDisplay();
                        }
                    }
                });
                // Day buttons and Select All functionality
                $(document).on("click", ".day-btn[data-day]", function () {
                    $(this).toggleClass("active");
                    updateRepeatInfo();
                    updateSelectAllButton();
                });
                // Select All button
                $(document).on("click", "#selectAllBtn", function () {
                    if ($(this).hasClass("select-all-btn")) {
                        // Select all days
                        $(".day-btn[data-day]").addClass("active");
                        $(this).removeClass("select-all-btn").addClass("clear-all-btn");
                        $(this).text("Xóa tất cả");
                    } else {
                        // Clear all days
                        $(".day-btn[data-day]").removeClass("active");
                        $(this).removeClass("clear-all-btn").addClass("select-all-btn");
                        $(this).text("Chọn tất cả");
                    }
                    updateRepeatInfo();
                });
                function updateSelectAllButton() {
                    var totalDays = $(".day-btn[data-day]").length;
                    var selectedDays = $(".day-btn[data-day].active").length;
                    var selectAllBtn = $("#selectAllBtn");
                    if (selectedDays === totalDays && selectedDays > 0) {
                        // All days selected - show "Clear All"
                        selectAllBtn
                                .removeClass("select-all-btn")
                                .addClass("clear-all-btn");
                        selectAllBtn.text("Xóa tất cả");
                    } else {
                        // Not all days selected - show "Select All"
                        selectAllBtn
                                .removeClass("clear-all-btn")
                                .addClass("select-all-btn");
                        selectAllBtn.text("Chọn tất cả");
                    }
                }

                function updateRepeatInfo() {
                    var selectedDays = [];
                    var dayNames = {
                        0: "Chủ nhật",
                        1: "Thứ 2",
                        2: "Thứ 3",
                        3: "Thứ 4",
                        4: "Thứ 5",
                        5: "Thứ 6",
                        6: "Thứ 7",
                    };
                    $(".day-btn.active[data-day]").each(function () {
                        var day = $(this).data("day");
                        selectedDays.push(dayNames[day]);
                    });
                    if (selectedDays.length > 0) {
                        $(".repeat-info").text(
                                "Lặp lại " + selectedDays.join(", ") + " hàng tuần"
                                );
                    } else {
                        $(".repeat-info").text("Chưa chọn ngày nào");
                    }
                }

                // Employee search functionality
                $("#employeeSearch").on("focus", function () {
                    $("#employeeDropdown").show();
                    filterEmployees();
                });
                $("#employeeSearch").on("input", function () {
                    filterEmployees();
                });
                $(document).on("click", function (e) {
                    if (!$(e.target).closest(".employee-search-container").length) {
                        $("#employeeDropdown").hide();
                    }
                });
                function filterEmployees() {
                    var searchTerm = $("#employeeSearch").val().toLowerCase();
                    $(".employee-item").each(function () {
                        var employeeName = $(this)
                                .find(".employee-name")
                                .text()
                                .toLowerCase();
                        var employeeCode = $(this)
                                .find(".employee-code")
                                .text()
                                .toLowerCase();
                        if (
                                employeeName.includes(searchTerm) ||
                                employeeCode.includes(searchTerm)
                                ) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });
                }

                // Employee selection
                $(document).on("click", ".employee-item", function () {
                    var employeeId = $(this).data("employee");
                    var employeeName = $(this).find(".employee-name").text();
                    var employeeCode = $(this).find(".employee-code").text();
                    if ($(this).hasClass("selected")) {
                        // Remove from selection
                        $(this).removeClass("selected");
                        selectedEmployees = selectedEmployees.filter(function (emp) {
                            return emp.id !== employeeId;
                        });
                    } else {
                        // Add to selection
                        $(this).addClass("selected");
                        selectedEmployees.push({
                            id: employeeId,
                            name: employeeName,
                            code: employeeCode,
                        });
                    }
                    updateSelectedEmployeesDisplay();
                    $("#employeeSearch").val("");
                    filterEmployees();
                });
                // Remove employee from selection
                $(document).on("click", ".remove-employee", function (e) {
                    e.stopPropagation();
                    var employeeId = $(this).data("employee");
                    // Remove from selected array
                    selectedEmployees = selectedEmployees.filter(function (emp) {
                        return emp.id !== employeeId;
                    });
                    // Remove selected class from dropdown item
                    $('.employee-item[data-employee="' + employeeId + '"]').removeClass(
                            "selected"
                            );
                    updateSelectedEmployeesDisplay();
                });
                function updateSelectedEmployeesDisplay() {
                    var container = $("#selectedEmployees");
                    container.empty();
                    selectedEmployees.forEach(function (employee) {
                        var tag = $(
                                '<div class="employee-tag">' +
                                employee.name +
                                ' <span class="remove-employee" data-employee="' +
                                employee.id +
                                '">×</span>' +
                                "</div>"
                                );
                        container.append(tag);
                    });
                }


                // Add shift button click (both overlay and empty cell buttons)
                $(document).on(
                        "click",
                        ".add-shift-btn-overlay",
                        function () {
                            var employee = $(this).data("employee");
                            var date = $(this).data("date");
                            //Chuyển đổi date thành đối tượng Date
                            var dateObj = new Date(date);
                            //Lấy ngày trong tuần 
                            var dayOfWeek = dateObj.getDay();
                            $("#employeeSelect").val(employee).trigger("change");
                            $("#workDate").val(date);
                            // Reset modal state
                            $(".toggle-switch").removeClass("active");
                            $("#weeklyScheduleSection").hide();
                            $("#employeeSelectionSection").hide();
                            $(".day-btn[data-day]").removeClass("active");
                            $('.day-btn[data-day="' + dayOfWeek + '"]').addClass("active"); // Default to Monday
                            selectedEmployees = [];
                            updateSelectedEmployeesDisplay();
                            $(".employee-item").removeClass("selected");
                            updateRepeatInfo();
                            updateSelectAllButton();
                            var modal = new bootstrap.Modal(document.getElementById('addShiftModal'));
                            modal.show();
                        }
                );
                // Shift badge click for editing
                $(document).on("click", ".shift-badge", function () {
                    var cell = $(this).closest(".shift-cell");
                    var employee = cell.data("employee");
                    var date = cell.data("date");
                    var shiftType = "morning";
                    if ($(this).hasClass("shift-afternoon"))
                        shiftType = "afternoon";
                    else if ($(this).hasClass("shift-evening"))
                        shiftType = "evening";
                    else if ($(this).hasClass("shift-night"))
                        shiftType = "night";
                    // Populate edit modal
                    $("#editEmployeeName").val(getEmployeeName(employee));
                    var dateObj = new Date(date);
                    var formattedDate = dateObj.toLocaleDateString("vi-VN");
                    $("#editWorkDate").val(formattedDate);
                    $("#editShiftType").val(shiftType).trigger("change");
                    $("#editShiftModal").modal("show");
                });
                // Save new shift
                // Save new shift
                function saveShift() {
                    var employee = $("#employeeSelect").val();
                    var shiftType = $("#shiftType").val();
                    var workDate = $("#workDate").val(); // Khai báo workDate
                    var endDate = $("#endDate").val();
                    console.log("end date:", endDate);
                    console.log("Work date:", workDate);
                    var isWeeklyRepeat = $("#weeklyRepeatToggle").hasClass("active");
                    console.log("isWeeklyRepeat:", isWeeklyRepeat);
                    var isMultiEmployee = $("#multiEmployeeToggle").hasClass("active");

                    if (!employee || !shiftType || !workDate) {
                        alert("Vui lòng điền đầy đủ thông tin bắt buộc!");
                        return;
                    }

                    var selectedDays = [];
                    if (isWeeklyRepeat) {
                        $(".day-btn.active[data-day]").each(function () {
                            selectedDays.push($(this).data("day"));
                        });
                        if (selectedDays.length === 0) {
                            alert("Vui lòng chọn ít nhất một ngày trong tuần!");
                            return;
                        }
                        console.log("Selected days for repeat:", selectedDays);
                    }
                    console.log("selectedDaysNull", selectedDays);

                    var dataToSend = {
                        employeeId: employee,
                        shiftId: shiftType,
                        workDate: workDate,
                        endDate: endDate,
                        selectedDays: selectedDays,
                        isMultiEmployee: isMultiEmployee,
                        selectedEmployeeIds: isMultiEmployee ? selectedEmployees.map(emp => emp.id) : []
                    };

                    // In selectedEmployeeIds ra console
                    console.log("Selected Employee IDs:", dataToSend.selectedEmployeeIds);
                    $.ajax({
                        url: "SaveWorkScheduleServlet",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(dataToSend),
                        success: function (response) {
                            console.log("Success:", response);
                            $("#addShiftForm")[0].reset();
                            $(".select").val("").trigger("change");
                            $(".toggle-switch").removeClass("active");
                            $("#weeklyScheduleSection").hide();
                            $("#employeeSelectionSection").hide();
                            selectedEmployees = [];
                            updateSelectedEmployeesDisplay();
                            $(".employee-item").removeClass("selected");
                            $("#addShiftModal").modal("hide");
                            showToast("Thêm ca làm việc thành công!", "success");
                        },
                        error: function (xhr, status, error) {
                            console.log("Error details - Status:", status);
                            console.log("Error message:", error);
                            console.log("Response text:", xhr.responseText); // Hiển thị phản hồi từ server
                            showToast("Đã xảy ra lỗi khi thêm ca làm việc!", "error");
                        }
                    });

                    if (isMultiEmployee && selectedEmployees.length > 0) {
                        selectedEmployees.forEach(function (emp) {
                            addShiftToEmployee(emp.id, shiftType);
                        });
                    } else {
                        addShiftToEmployee(employee, shiftType);
                    }
                }
                $("#saveShiftBtn").click(function () {
                    saveShift();
                });

                function addShiftToEmployee(employeeId, shiftType) {
                    var currentDate = new Date();
                    var isoDate = currentDate.toISOString().split("T")[0];
                    var cell = $(
                            '.shift-cell[data-employee="' +
                            employeeId +
                            '"], .empty-shift-cell[data-employee="' +
                            employeeId +
                            '"]'
                            ).first();
                    if (cell.length) {
                        var shiftClass = getShiftClass(shiftType);
                        var shiftText = getShiftText(shiftType);
                        // Convert empty cell to shift cell if needed
                        if (cell.hasClass("empty-shift-cell")) {
                            cell.removeClass("empty-shift-cell").addClass("shift-cell");
                            cell.html(
                                    '<div class="shift-container"><div class="shift-badges"></div></div><div class="add-shift-overlay"><button class="add-shift-btn-overlay" data-employee="' +
                                    employeeId +
                                    '" data-date="' +
                                    isoDate +
                                    '"><i class="fas fa-plus"></i></button></div>'
                                    );
                        }

                        // Add new shift badge to container
                        cell
                                .find(".shift-badges")
                                .append(
                                        '<span class="shift-badge ' +
                                        shiftClass +
                                        '">' +
                                        shiftText +
                                        "</span>"
                                        );
                    }
                }

                // Update shift
                $("#updateShiftBtn").click(function () {
                    $("#editShiftModal").modal("hide");
                    showToast("Cập nhật ca làm việc thành công!", "success");
                });
                // Delete shift
                $("#deleteShiftBtn").click(function () {
                    if (confirm("Bạn có chắc chắn muốn xóa ca làm việc này?")) {
                        $("#editShiftModal").modal("hide");
                        showToast("Xóa ca làm việc thành công!", "success");
                    }
                });
                // Helper functions
                function getEmployeeName(employeeId) {
                    var employees = {
                        nam: "Nam - Nhân viên bán hàng",
                        thanh: "Thành - Nhân viên kho",
                        linh: "Linh - Nhân viên bán hàng",
                        hung: "Hùng - Quản lý",
                        tung: "Tùng - Nhân viên",
                    };
                    return employees[employeeId] || "";
                }
                function getShiftText(shiftType) {
                    var shifts = {
                        morning: "Sáng",
                        afternoon: "Chiều",
                        evening: "Tối",
                        night: "Đêm",
                    };
                    return shifts[shiftType] || "";
                }
                function getShiftClass(shiftType) {
                    var classes = {
                        morning: "shift-morning",
                        afternoon: "shift-afternoon",
                        evening: "shift-evening",
                        night: "shift-night",
                    };
                    return classes[shiftType] || "shift-morning";
                }
                function showToast(message, type) {
                    // Simple toast notification using alert for now
                    alert(message);
                }
                // Department filter
                $("#departmentFilter").change(function () {
                    var selectedDept = $(this).val();
                    console.log("Filter by department:", selectedDept);
                });
                // Initialize repeat info and select all button
                updateRepeatInfo();
                updateSelectAllButton();
            });
        </script>
    </body>
</html>

