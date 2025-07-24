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

<%
    String path = request.getContextPath();
%>
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
            /* Nút xóa - ẩn mặc định */
            .remove-shift-btn {
                background: none;
                border: none;
                cursor: pointer;
                padding: 2px 4px;
                margin-left: 6px;
                border-radius: 3px;
                font-size: 10px;
                opacity: 0;
                visibility: hidden;
                transition: all 0.2s ease;
                color: inherit;
            }

            /* Hiện nút xóa khi hover vào shift-badge */
            .shift-badge:hover .remove-shift-btn {
                opacity: 1;
                visibility: visible;
            }

            /* Hiệu ứng hover cho nút xóa */
            .remove-shift-btn:hover {
                background: rgba(255, 255, 255, 0.3);
                transform: scale(1.1);
            }


            .shift-badge {
                display: inline-flex;
                align-items: center;
                gap: 4px;
                /* các style khác của bạn */
            }
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
            /* Vùng cho nút thêm ca - chỉ hiện khi hover */
            .add-shift-area {
                opacity: 0;
                transition: opacity 0.3s ease;
                margin-top: 4px;
            }

            .shift-cell:hover .add-shift-area {
                opacity: 1;
            }

            /* Nút thêm ca */
            .add-shift-btn {
                background: transparent;
                color: #007bff;
                border: 2px dashed #007bff;
                border-radius: 6px;
                padding: 8px 12px;
                font-size: 13px;
                cursor: pointer;
                transition: all 0.2s ease;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 6px;
                width: 100%;
                opacity: 0.8;
            }

            .add-shift-btn:hover {
                background: #007bff;
                color: white;
                opacity: 1;
                border-style: solid;
                transform: translateY(-1px);
            }

            .add-shift-btn i {
                font-size: 12px;
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
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <!-- Page Wrapper -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Lịch làm việc</h4>
                            <h6>Quản lý ca làm việc cho nhân viên</h6>
                        </div>
                        <div class="page-btn">
                            <a href="ListHolidayServlet" class="btn btn-added" >
                                Quản lí Ngày lễ
                            </a>
                        </div>

                    </div>

                    <!-- Week Navigation -->
                    <div class="card">
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-md-5">
                                    <div class="d-flex align-items-center justify-content-start">
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

                                <div class="col-md-4 p-0">
                                    <div class="d-flex justify-content-start">
                                        <form action="ListWorkScheduleServlet" style="display: flex">
                                            <input type="hidden" name="storeId" value="${storeId}">
                                            <input type="hidden" name="weekStart" value="${weekStart}">
                                            <input  type="text" name="empName" value="${empName}" placeholder="Tìm kiếm nhân viên">
                                            <input type="submit" value="Search">
                                        </form>   
                                    </div>
                                </div>        


                                <div class="col-md-3">
                                    <div class="d-flex justify-content-end align-items-center gap-2">
                                        <select style="width: 200px" class="select me-2" id="departmentFilter" onchange="window.location.href = 'ListWorkScheduleServlet?storeId=' + this.value">
                                            <c:forEach var="store" items="${stores}">
                                                <option value="${store.getStoreID()}" ${storeId==store.getStoreID()?'selected':''}>${store.getStoreName()}</option>
                                            </c:forEach>
                                        </select>
                                        <!-- Nhận kết quả filtered store -->
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
                                                            <img src="view/assets/img/user/${emp.getAvatar()}" >
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
                                                                        <span class="shift-badge shift-${shiftEmp.getShiftId()}">
                                                                            ${shiftEmp.getShiftName()}
                                                                            <button 
                                                                                class="remove-shift-btn" 
                                                                                data-attendance-id="${shiftEmp.getAttendanceId()}"
                                                                                >
                                                                                <i class="fas fa-times"></i>
                                                                            </button>
                                                                        </span>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <div class="add-shift-area">
                                                                    <button class="add-shift-btn"
                                                                            data-employee="${emp.getEmployeeID()}"
                                                                            data-date="${day}">
                                                                        <i class="fas fa-plus"></i>
                                                                        <span>Thêm ca</span>
                                                                    </button>
                                                                </div>
                                                            </div>
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
                                    <div class="day-btn" data-day="1">Thứ 2</div>
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

                                <!--                                     Holiday Option 
                                                                    <div class="checkbox-option">
                                                                        <input type="checkbox" id="includeHolidays" />
                                                                        <label for="includeHolidays">Làm việc cả ngày lễ tết</label>
                                                                    </div>-->
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        <c:if test="${deleteSuccess}">
            <script>
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Thành công',
                                                text: 'Ca làm việc đã được xóa thành công',
                                                confirmButtonText: 'OK'
                                            });
            </script>
        </c:if>
        <script>
            //Chạy khi toàn bộ dữ liệu trang được load
            $(document).ready(function () {
                //Xóa ca làm việc        
                $('.remove-shift-btn').click(function () {
                    var attendanceId = $(this).data('attendanceId');

                    Swal.fire({
                        title: 'Cảnh báo',
                        text: "Bạn có muốn ca làm việc này không ?",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#28a745',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Có'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Điều hướng đến servlet xử lý
                            window.location.href = 'ListWorkScheduleServlet?empName=${empName}&storeId=${storeId}&weekStart=' + formatDateToParam(currentWeekStart) + '&action=delete&attendanceId=' + attendanceId;
                        }
                    });
                });
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
                    window.location.href = "ListWorkScheduleServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(currentWeekStart);
                });
                $("#nextWeek").click(function () {
                    currentWeekStart.setDate(currentWeekStart.getDate() + 7);
                    window.location.href = "ListWorkScheduleServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(currentWeekStart);
                });
                $("#todayBtn").click(function () {
                    let today = new Date();
                    let monday = new Date(today.setDate(today.getDate() - today.getDay() + 1));
                    window.location.href = "ListWorkScheduleServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(monday);
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
                // Khi click vào một day-btn[data-day] chuyển sang active
                $(document).on("click", ".day-btn[data-day]", function () {
                    //Chuyển đổi class nếu chưa có active thì thêm vào và ngược lại            
                    $(this).toggleClass("active");
                    updateRepeatInfo();
                    updateSelectAllButton();
                });
                // Select All button
                $(document).on("click", "#selectAllBtn", function () {
                    if ($(this).hasClass("select-all-btn")) {
                        // Select all days
                        //add Class nếu có active rồi thì giữ nguyên
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
                    //Tổng số ngày của thuộc tính data-day
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
                        6: "Thứ 7"
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
                //Nhấn vào input id = employeeSearch
                $("#employeeSearch").on("focus", function () {
                    $("#employeeDropdown").show();
                    filterEmployees();
                });
                //Lắng nghe sự kiện gõ chữ vào input
                $("#employeeSearch").on("input", function () {
                    filterEmployees();
                });
                //click vào bất cứ đâu
                $(document).on("click", function (e) {
                    //Không nằm trong employee-search-container sẽ ẩn đi 
                    if (!$(e.target).closest(".employee-search-container").length) {
                        $("#employeeDropdown").hide();
                    }
                });
                function filterEmployees() {
                    //Lấy giá trị ở ô search
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
                    //Lấy thông tin nhân viên đã được click            
                    var employeeId = $(this).data("employee");
                    var employeeName = $(this).find(".employee-name").text();
                    var employeeCode = $(this).find(".employee-code").text();
                    if ($(this).hasClass("selected")) {
                        // Remove from selection
                        $(this).removeClass("selected");
                        //Lọc ra các nhân viên còn lại
                        selectedEmployees = selectedEmployees.filter(function (emp) {
                            return emp.id !== employeeId;
                        });
                    } else {
                        // Add to selection
                        $(this).addClass("selected");
                        selectedEmployees.push({
                            id: employeeId,
                            name: employeeName,
                            code: employeeCode
                        });
                    }
                    updateSelectedEmployeesDisplay();
                    //Xóa nội dung ô tìm kiếm
                    $("#employeeSearch").val("");
                    //Hiển thị lại danh sách nhân viên
                    filterEmployees();
                });
                // Remove employee from selection
                $(document).on("click", ".remove-employee", function (e) {
                    //ngăn sự kiện lan ra các phần tử cha             
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
                    //Xóa nội dụng cũ             
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
                        //Thêm vào container là #selectedEmployees 
                        container.append(tag);
                    });
                }


                // Add shift button click (both overlay and empty cell buttons)
                $(document).on(
                        "click",
                        ".add-shift-btn",
                        function () {
                            //Lấy ngày, nhân viên của ô được chọn
                            var employee = $(this).data("employee");
                            var date = $(this).data("date");
                            //Chuyển đổi date thành đối tượng Date
                            var dateObj = new Date(date);
                            //Lấy ngày trong tuần 
                            var dayOfWeek = dateObj.getDay();
                            //Gán giá trị employee và date vào các thẻ input
                            $("#employeeSelect").val(employee).trigger("change");
                            $("#workDate").val(date);
                            // Reset modal state
                            $(".toggle-switch").removeClass("active");
                            $("#weeklyScheduleSection").hide();
                            $("#employeeSelectionSection").hide();
                            //Đánh dấu ngày trong tuần được chọn
                            $(".day-btn[data-day]").removeClass("active");
                            $('.day-btn[data-day="' + dayOfWeek + '"]').addClass("active"); // active day-btn
                            //Cập nhật lại danh sách nhân viên đã chọn                    
                            selectedEmployees = [];
                            //Hiển thị lại
                            updateSelectedEmployeesDisplay();
                            $(".employee-item").removeClass("selected");
                            //Cập nhật lại số lần lặp các thứ trong tuần
                            updateRepeatInfo();
                            updateSelectAllButton();
                            var modal = new bootstrap.Modal(document.getElementById('addShiftModal'));
                            modal.show();
                        }
                );

                // Save new shift
                async function saveShift() {
                    // Lấy dữ liệu từ input
                    const employee = $("#employeeSelect").val();
                    const shiftType = $("#shiftType").val();
                    const workDate = $("#workDate").val();
                    const endDate = $("#endDate").val();

                    const isWeeklyRepeat = $("#weeklyRepeatToggle").hasClass("active");
                    const isMultiEmployee = $("#multiEmployeeToggle").hasClass("active");

                    // Kiểm tra dữ liệu đầu vào
                    if (!employee || !shiftType || !workDate) {
                        alert("Vui lòng điền đầy đủ thông tin bắt buộc!");
                        return;
                    }

                    // Lấy các ngày trong tuần nếu có lặp
                    let selectedDays = [];
                    if (isWeeklyRepeat) {
                        if (!endDate) {
                            alert("Vui lòng chọn ngày kết thúc!");
                            return;
                        }

                        $(".day-btn.active[data-day]").each(function () {
                            selectedDays.push($(this).data("day"));
                        });

                        if (selectedDays.length === 0) {
                            alert("Vui lòng chọn ít nhất một ngày trong tuần!");
                            return;
                        }
                    }

                    // Kiểm tra có phải ngày nghỉ không
                    const isConfirmed = await isHoliday(workDate, endDate, isWeeklyRepeat, selectedDays);
                    console.log("isConfirmed result:", isConfirmed);

                    if (!isConfirmed) {
                        console.log("Không lưu vì người dùng không xác nhận khi là ngày nghỉ");
                        return;
                    }
                    console.log("Tiếp tục lưu vì đã xác nhận hoặc không phải ngày nghỉ");


                    // Chuẩn bị dữ liệu để gửi
                    const dataToSend = {
                        employeeId: employee,
                        shiftId: shiftType,
                        workDate: workDate,
                        endDate: endDate,
                        selectedDays: selectedDays,
                        isMultiEmployee: isMultiEmployee,
                        selectedEmployeeIds: isMultiEmployee ? selectedEmployees.map(emp => emp.id) : [],
                        storeId: ${storeId},
                        weekStart: ${weekStart}
                    };

                    actuallySaveShift(dataToSend);
                }

                function actuallySaveShift(dataToSend) {
                    $.ajax({
                        url: "SaveWorkScheduleServlet",
                        type: "POST",
                        //Dữ liệu gửi đi là kiểu JSON
                        contentType: "application/json",
                        //Chuyển Object JSON thành String JSON
                        data: JSON.stringify(dataToSend),
                        success: function (response) {
                            //Reset các thẻ select đã chọn của nhân vilàmên và ca                     
                            $(".select").val("").trigger("change");
                            $(".toggle-switch").removeClass("active");
                            $("#weeklyScheduleSection").hide();
                            $("#employeeSelectionSection").hide();
                            selectedEmployees = [];
                            updateSelectedEmployeesDisplay();
                            //Reset các selected item của nhân viên 
                            $(".employee-item").removeClass("selected");
                            const storeId = response.storeId;
                            const weekStart = response.weekStart;
                            showToast(response.message, "success");
                            window.location.href = `ListWorkScheduleServlet?empName=${empName}&storeId=${storeId}&weekStart=${weekStart}`;

                        },
                        error: function (xhr, status, error) {
                            let errorMessage = "Đã xảy ra lỗi khi thêm ca làm việc!";
                            try {
                                //Phân tích JSON nếu có                    
                                const response = JSON.parse(xhr.responseText);
                                if (response && response.message) {
                                    errorMessage = response.message;
                                }
                            } catch (e) {
                                console.log("không thể phân tích phản hồi dưới dạng JSON: ", e);
                            }
                            showToast(errorMessage, "error");
                        }
                    });
                }
                async function isHoliday(workDate, endDate, isWeeklyRepeat, selectedDays) {
                    try {
                        const response = await fetch('CheckHolidayServlet', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            body: new URLSearchParams({
                                workDate: workDate,
                                endDate: endDate,
                                isWeeklyRepeat: isWeeklyRepeat,
                                selectedDays: JSON.stringify(selectedDays)
                            })
                        });

                        const result = await response.text();
                        if (result.includes("holiday")) {
                            const date = result.split(" ")[1];
                            const confirmed = confirm("Ngày " + date+ " được chọn là ngày nghỉ lễ. Bạn có muốn tiếp tục không?");
                            return confirmed; // true hoặc false tùy người dùng chọn
                        } else {
                            return true; // Không phải ngày nghỉ thì tiếp tục
                        }
                    } catch (error) {
                        alert('Lỗi khi kiểm tra ngày nghỉ: ' + error.message);
                        console.error('Error:', error);
                        return false; // Trong trường hợp lỗi, không tiếp tục
                    }
                }

                $("#saveShiftBtn").click(function () {
                    saveShift();
                });

                function showToast(message, type) {
                    // Simple toast notification using alert for now
                    alert(message);
                }

                // Initialize repeat info and select all button
                updateRepeatInfo();
                updateSelectAllButton();
            });
        </script>
    </body>
</html>

