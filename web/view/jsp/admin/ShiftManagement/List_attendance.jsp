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
            .main-content {
                padding: 20px;
            }

            .calendar-container {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            .calendar-scroll {
                overflow-x: auto;
                width: 100%;
            }
            .calendar-table {
                width: 100%;
                border-collapse: collapse;
            }

            .calendar-header {
                background: #f8f9fa;
                border-bottom: 1px solid #e0e0e0;
            }

            .calendar-header th {
                padding: 12px 8px;
                text-align: center;
                font-weight: 700;
                font-size: 14px;
                color: #333;
                border-right: 1px solid #e0e0e0;
            }

            .shift-row {
                border-bottom: 1px solid #e0e0e0;
            }

            .shift-label {
                background: #f8f9fa;
                padding: 12px;
                font-weight: 700;
                color: #333;
                border-right: 1px solid #e0e0e0;
                vertical-align: top;
            }

            .shift-time {
                font-size: 12px;
                color: #666;
                font-weight: normal;
            }

            .day-cell {
                border-right: 1px solid #e0e0e0;
                padding: 8px;
                vertical-align: top;
                /*                width: 100px;*/
                position: relative;
            }

            .day-cell:hover {
                background-color: rgba(66, 133, 244, 0.1);
            }

            .day-header {
                font-weight: 500;
                margin-bottom: 4px;
                color: #333;
            }

            .day-time {
                font-size: 12px;
                color: #666;
            }

            .status-Pending {
                background: #fff3cd;
                color: #856404;
            }

            .status-Present {
                background: #d1ecf1;
                color: #0c5460;
            }

            .status-Approved-Leave {
                background: #d4edda;
                color: #155724;
            }

            .status-Unapproved-Leave {
                background: #f8d7da;
                color: #721c24;
            }
            .status-Late, .status-Early-Leave {
                background: #D8B4FE;
                color: #721c24;
            }
            .status-NotAllowed {
                background: #f5f5f5;
                color: #721c24;
            }
            .day-cell {
                border-right: 1px solid #e0e0e0;
                padding: 8px;
                margin: 4px;
                vertical-align: top;
                width: 120px;
                height: 100px;
                position: relative;
            }
            .day-time {
                font-size: 12px;
                color: #666;
            }
            /*Thanh chú thích*/
            .legend {
                display: flex;
                justify-content: center;
                gap: 20px;
                padding: 16px;
                background: #f8f9fa;
                border-top: 1px solid #e0e0e0;
            }

            .legend-item {
                display: flex;
                align-items: center;
                gap: 8px;
                font-size: 14px;
            }

            .legend-dot {
                width: 12px;
                height: 12px;
                border-radius: 50%;
            }

            .dot-on-time {
                background: #4285f4;
            }
            .dot-late {
                background: #9c27b0;
            }
            .dot-absent {
                background: #f44336;
            }
            .dot-approved-leave {
                background: #006400;
            }
            .dot-pending {
                background: #ff9800;
            }
            .dot-off {
                background: #9e9e9e;
            }
            /*Thanh Modal chấm công*/
            .modal-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: none;
                justify-content: center;
                align-items: center;
                z-index: 9999;
            }

            .modal-content {
                background: white;
                border-radius: 8px;
                width: 90%;
                max-width: 800px;
                max-height: 90vh;
                overflow-y: auto;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
            }

            .modal-header {
                padding: 20px;
                border-bottom: 1px solid #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .modal-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
            }

            .close-btn {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                color: #666;
                padding: 0;
                width: 30px;
                height: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .close-btn:hover {
                color: #333;
                background: #f5f5f5;
                border-radius: 50%;
            }

            .modal-body {
                padding: 20px;
            }

            .employee-info {
                display: flex;
                align-items: center;
                gap: 15px;
                margin-bottom: 20px;
                padding: 15px;
                background: #f8f9fa;
                border-radius: 6px;
            }

            .employee-avatar {
                width: 60px;
                height: 60px;
            }

            .employee-details h3 {
                margin: 0;
                color: #333;
                font-size: 16px;
            }

            .employee-details p {
                margin: 2px 0;
                color: #666;
                font-size: 14px;
            }

            .work-info {
                display: flex;
                gap: 30px;
                margin-bottom: 25px;
            }

            .work-info div {
                flex: 1;
            }

            .work-info label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
                color: #333;
            }

            .work-info select, .work-info span {
                background: #f8f9fa;
                border: 1px solid #ddd;
                padding: 8px 12px;
                border-radius: 4px;
                width: 100%;
                display: block;
            }

            .notes-section {
                margin: 25px 0;
            }

            .notes-section h4 {
                margin-bottom: 15px;
                color: #333;
                font-size: 16px;
            }

            .tabs {
                display: flex;
                border-bottom: 2px solid #e0e0e0;
                margin-bottom: 20px;
            }

            .tab {
                padding: 10px 20px;
                background: none;
                border: none;
                cursor: pointer;
                color: #666;
                font-size: 14px;
                border-bottom: 2px solid transparent;
            }

            .tab.active {
                color: #4285f4;
                border-bottom-color: #4285f4;
            }

            .attendance-options {
                margin: 20px 0;
            }

            .radio-group {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }

            .radio-item {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .radio-item input[type="radio"] {
                margin: 0;
            }

            .time-section {
                background: #f8f9fa;
                padding: 15px;
                border-radius: 6px;
                margin: 15px 0;
            }

            .time-row {
                display: flex;
                align-items: center;
                gap: 15px;
                margin-bottom: 10px;
            }

            .time-row input[type="checkbox"] {
                margin: 0;
            }

            .time-input {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .time-input input {
                border: 1px solid #ddd;
                padding: 6px 10px;
                border-radius: 4px;
                width: 130px;
            }

            .modal-footer {
                padding: 20px;
                border-top: 1px solid #e0e0e0;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
                font-size: 14px;
            }

            .btn-primary- {
                background: #4285f4;
                color: white;
            }

            .btn-success {
                background: #34a853;
                color: white;
            }

            .btn-secondary {
                background: #6c757d;
                color: white;
            }

            .btn-danger {
                background: #ea4335;
                color: white;
            }

            .btn:hover {
                opacity: 0.9;
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
                            <h4>Bảng chấm công</h4>
                            <h6>Chấm công cho nhân viên</h6>
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

                                <div class="col-md-4">
                                    <div  class="d-flex justify-content-start">
                                        <form id="employeeSearchForm" action="ListAttendanceServlet" style="display: flex">
                                            <input type="hidden" name="storeId" value="${storeId}">
                                            <input type="hidden" name="weekStart" value="${weekStart}">
                                            <input  type="text" name="empName" value="${empName}" placeholder="Tìm kiếm nhân viên">
                                            <input type="submit" value="Search">
                                        </form>   
                                    </div>
                                </div>        


                                <div class="col-md-3">
                                    <div class="d-flex justify-content-end align-items-center gap-2">
                                        <select style="width: 200px" class="select me-2" id="departmentFilter" onchange="window.location.href = 'ListAttendanceServlet?storeId=' + this.value">
                                            <c:forEach var="store" items="${stores}">
                                                <option value="${store.getStoreID()}" ${storeId==store.getStoreID()?'selected':''}>${store.getStoreName()}</option>
                                            </c:forEach>
                                        </select>
                                        <!-- Nhận kết quả filtered store -->
                                        <div id="filteredResult"></div>
                                        <button class="btn btn-primary" id="todayBtn">
                                            Hôm nay
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="main-content">
                        <div class="calendar-container">
                            <div class="calendar-scroll">
                                <table class="calendar-table">
                                    <thead class="calendar-header">
                                        <tr>
                                            <th>Ca làm việc</th>
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
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="shift" items="${shifts}">
                                            <tr class="shift-row">

                                                <td class="shift-label">

                                                    <div>${shift.getShiftName()}</div>
                                                    <div class="shift-time">${shift.getTime()}</div>

                                                </td>

                                                <c:forEach var="day" items="${weekDays}">
                                                    <td >
                                                        <c:forEach var="shiftEmp" items="${attendanceByShiftId[shift.getShiftID()]}">
                                                            <c:if test="${shiftEmp.getWorkDate()==day}">
                                                                <c:choose>
                                                                    <c:when test="${shiftEmp.getWorkDate() <= today}">
                                                                        <div class="day-cell status-${shiftEmp.getStatus().replace(" ", "-")}" onclick="openTimesheetModal('${shiftEmp.getAttendanceId()}', '${shiftEmp.getEmpName()}', '${shiftEmp.getEmp().getCode()}', '${shiftEmp.getEmp().getAvatar()}', '${shiftEmp.getWorkDate()}', '${shiftEmp.getShiftName()} (${shiftEmp.getShift().getTime()})', '${shiftEmp.getShift().getStartTimeFormat()}', '${shiftEmp.getShift().getEndTimeFormat()}', '${shiftEmp.getEmployeeId()}', '${shiftEmp.getShiftId()}', '${shiftEmp.getNotes()}')">
                                                                            <div class="day-header employee-name">${shiftEmp.getEmpName()}</div>
                                                                            <div class="day-time">${shiftEmp.getcheckInOut()}</div>
                                                                            <div style="color: #856404; font-size: 12px;">${shiftEmp.getStatusVietsub()}</div>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="day-cell status-NotAllowed">
                                                                            <div class="day-header employee-name">${shiftEmp.getEmpName()}</div>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="legend">
                                <div class="legend-item">
                                    <div class="legend-dot dot-on-time"></div>
                                    <span>Đúng giờ</span>
                                </div>
                                <div class="legend-item">
                                    <div class="legend-dot dot-late"></div>
                                    <span>Đi muộn / Về sớm</span>
                                </div>
                                <div class="legend-item">
                                    <div class="legend-dot dot-absent"></div>
                                    <span>Nghỉ không phép</span>
                                </div>
                                <div class="legend-item">
                                    <div class="legend-dot dot-approved-leave"></div>
                                    <span>Nghỉ có phép</span>
                                </div>
                                <div class="legend-item">
                                    <div class="legend-dot dot-pending"></div>
                                    <span>Chưa chấm công</span>
                                </div>
                                <div class="legend-item">
                                    <div class="legend-dot dot-off"></div>
                                    <span>Chưa được phép chấm</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal chấm công -->
                    <div class="modal-overlay" id="timesheetModal">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2 class="modal-title">Chấm công</h2>
                                <button class="close-btn" onclick="closeTimesheetModal()">&times;</button>
                            </div>
                            <div class="modal-body">
                                <input type="hidden" id="attendanceId">
                                <div class="employee-info">
                                    <div class="employee-avatar">
                                        <img id="employeeAvatar" src="" >
                                    </div>
                                    <div class="employee-details">
                                        <h3 id="employeeName"></h3>
                                        <p id="employeeId" data-emp-id></p>
                                        <p id="status" style="color: #ff9800; font-weight: 500;"></p>
                                    </div>
                                </div>

                                <div class="work-info">
                                    <div>
                                        <label>Thời gian</label>
                                        <span id="workDate"></span>
                                    </div>
                                    <div>
                                        <label>Ca làm việc</label>
                                        <span id="workShift" data-shift-id></span>
                                    </div>
                                </div>

                                <div class="notes-section">
                                    Ghi chú
                                    <input name="note" id="note" class="form-control">
                                    <div class="tabs">
                                        <button class="tab active">Chấm công</button>
                                        <!--                                        <button class="tab">Lịch sử chấm công</button>
                                        -->                                        <button class="tab">Phạt vi phạm</button>
                                        <button class="tab">Thưởng</button>
                                    </div>

                                    <div class="attendance-options">
                                        <div class="radio-group">
                                            <div class="radio-item">
                                                <input type="radio" id="working" name="attendance" value="Present" checked>
                                                <label for="working">Đi làm</label>
                                            </div>
                                            <div class="radio-item">
                                                <input type="radio" id="approved-leave" name="attendance" value="Approved Leave">
                                                <label for="approved-leave">Nghỉ có phép</label>
                                                <span style="color: #666; margin-left: 5px;">ℹ️</span>
                                            </div>
                                            <div class="radio-item">
                                                <input type="radio" id="unapproved-leave" name="attendance" value="Unapproved Leave">
                                                <label for="unapproved-leave">Nghỉ không phép</label>
                                                <span style="color: #666; margin-left: 5px;">ℹ️</span>
                                            </div>
                                        </div>

                                        <div class="time-section">
                                            <div class="time-row">
                                                <input type="checkbox" id="checkIn">
                                                <label for="checkIn">Vào</label>
                                                <div class="time-input">
                                                    <input type="time" id="checkInTime" data-start-time="" disabled>

                                                </div>
                                            </div>
                                            <div class="time-row">
                                                <input type="checkbox" id="checkOut">
                                                <label for="checkOut">Ra</label>
                                                <div class="time-input">
                                                    <input type="time" id="checkOutTime" data-end-time="" disabled>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <!--                                <button class="btn btn-primary-modal">🔄 Đổi ca</button>-->
                                <button class="btn btn-success">💾 Lưu</button>
                                <!--                                <button class="btn btn-secondary">🚫 Bỏ qua</button>-->
                                <button class="btn btn-danger">🗑️ Hủy</button>
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
                    <c:if test="${openModal}">
                        <script>
                                    document.getElementById('timesheetModal').style.display = 'flex';
                        </script>
                    </c:if>
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
                        // Mở modal chấm công
                        function openTimesheetModal(attendanceId, empName, empCode, empAvatar, date, shift, shiftStartTime, shiftEndTime, empId, shiftId, notes) {
                            document.getElementById('attendanceId').value = attendanceId;
                            document.getElementById('note').value = notes;
                            document.getElementById('employeeName').textContent = empName;
                            document.getElementById('employeeId').textContent = empCode;
                            document.getElementById('employeeId').dataset.empId = empId;
                            document.getElementById('workDate').textContent = date;
                            document.getElementById('workShift').textContent = shift;
                            document.getElementById('workShift').dataset.shiftId = shiftId;
                            document.getElementById('employeeAvatar').src = 'view/assets/img/user/' + empAvatar;
                            document.getElementById('checkInTime').dataset.startTime = shiftStartTime;
                            document.getElementById('checkOutTime').dataset.endTime = shiftEndTime;
                            document.getElementById('timesheetModal').style.display = 'flex';
                        }

                        // Đóng modal chấm công
                        function closeTimesheetModal() {
                            document.getElementById('timesheetModal').style.display = 'none';
                        }

                        // Đóng modal khi click bên ngoài
                        document.getElementById('timesheetModal').addEventListener('click', function (e) {
                            if (e.target === this) {
                                closeTimesheetModal();
                            }
                        });
                        // Xử lý checkbox thời gian
                        document.getElementById('checkIn').addEventListener('change', function () {
                            const checkInTime = document.getElementById('checkInTime');
                            checkInTime.disabled = !this.checked;
                            if (this.checked) {
                                checkInTime.value = checkInTime.dataset.startTime;
                            } else {
                                checkInTime.value = '';
                            }
                        });
                        document.getElementById('checkOut').addEventListener('change', function () {
                            const checkOutTime = document.getElementById('checkOutTime');
                            checkOutTime.disabled = !this.checked;
                            if (this.checked) {
                                checkOutTime.value = checkOutTime.dataset.endTime;
                            } else {
                                checkOutTime.value = '';
                            }
                        });
                        //Xử lí tabs
                        document.querySelectorAll('.tab').forEach(tab => {
                            tab.addEventListener('click', function () {
                                document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
                                this.classList.add('active');
                            });
                        });
                        //Xử lí radio buttons 
                        document.querySelectorAll('input[name="attendance"]').forEach(radio => {
                            radio.addEventListener('change', function () {
                                const timeSection = document.querySelector('.time-section');
                                if (this.value === 'working') {
                                    timeSection.style.display = 'block';
                                } else {
                                    timeSection.style.display = 'none';
                                }
                            });
                        });
                        //Xử lí khi bấm hủy 
                        document.querySelector('.btn-danger').addEventListener('click', function () {
                            closeTimesheetModal();
                        });
                        //Xử lí khi bấm lưu
                        document.querySelector('.btn-success').addEventListener('click', function () {
                            //Lấy dữ liệu từ modal
                            const attendanceId = document.getElementById('attendanceId').value;
//                        const empId = document.getElementById('employeeId').dataset.empId;
//                        const workDate = document.getElementById('workDate').textContent;
//                        const shiftId = document.getElementById('workShift').dataset.shiftId;
                            const attendanceStatus = document.querySelector('input[name="attendance"]:checked').value;
                            const note = document.getElementById('note').value;
                            const checkIn = document.getElementById('checkIn').checked;
                            const checkOut = document.getElementById('checkOut').checked;
                            const checkInTime = document.getElementById('checkInTime').value;
                            const checkOutTime = document.getElementById('checkOutTime').value;
                            // Tạo object dữ liệu
                            const data = {
                                attendanceId,
                                attendanceStatus,
                                note,
                                checkIn: checkIn ? checkInTime : '',
                                checkOut: checkOut ? checkOutTime : ''
                            };
                            //Gửi POST đến servlet
                            fetch('SaveAttendanceServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(data)
                            }
                            ).then(res => res.json())
                                    .then(response => {
                                        if (response.status === 'error') {
                                            alert('Lỗi: ' + response.message);
                                        } else {
                                            alert('Lưu thành công!');

                                            window.location.href = `ListAttendanceServlet?empName=${empName}&storeId=${storeId}&weekStart=${weekStart}`;
                                        }

                                    });
                        });
                    </script>

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
                                        window.location.href = 'ListAttendanceServlet?empName=${empName}&storeId=${storeId}&weekStart=' + formatDateToParam(currentWeekStart) + '&action=delete&attendanceId=' + attendanceId;
                                    }
                                });
                            });
                            let currentWeekStart = new Date("<%= weekStart.toString()%>");
                            function formatDateToParam(date) {
                                return date.toISOString().split('T')[0];
                            }
                            // Week navigation events
                            $("#prevWeek").click(function () {
                                currentWeekStart.setDate(currentWeekStart.getDate() - 7);
                                window.location.href = "ListAttendanceServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(currentWeekStart);
                            });
                            $("#nextWeek").click(function () {
                                currentWeekStart.setDate(currentWeekStart.getDate() + 7);
                                window.location.href = "ListAttendanceServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(currentWeekStart);
                            });
                            $("#todayBtn").click(function () {
                                let today = new Date();
                                let monday = new Date(today.setDate(today.getDate() - today.getDay() + 1));
                                window.location.href = "ListAttendanceServlet?empName=${empName}&storeId=${storeId}&weekStart=" + formatDateToParam(monday);
                            });
                        });
                    </script>

                    <%
                    int empTypeId = (int) request.getAttribute("empTypeId"); 
                    int roleId = (int) request.getAttribute("roleId");
                    %>
                    <script>
                        const roleId = '<%= roleId %>';
                        const empTypeId = '<%= empTypeId %>';
                        if (parseInt(roleId) === 2 && parseInt(empTypeId) !== 2) {
                            //Ân search                     
                            document.getElementById("employeeSearchForm").style.display = "none";
                            //Tắt onclick của chấm                     
                            document.querySelectorAll('.day-cell').forEach(el => {
                                el.onclick = null;
                            });
                        }
</script>
</body>
</html>

