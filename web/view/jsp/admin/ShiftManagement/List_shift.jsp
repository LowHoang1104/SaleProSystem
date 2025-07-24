<%-- 
    Document   : List_shift
    Created on : Jun 3, 2025, 1:01:18 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
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
        <%@ include file="../HeadSideBar/header.jsp" %>
        <%@ include file="../HeadSideBar/sidebar.jsp" %>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">


            <!-- Main Content -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            ${deleteShift}
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
                        <div class="col-lg-6 col-sm-6 col-12">
                            <div class="stats-card">
                                <h3>${totalShifts}</h3>
                                <p>Tổng Ca Làm Việc</p>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-6 col-12">
                            <div class="stats-card" style="background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);">
                                <h3>${activeShifts}</h3>
                                <p>Ca Đang Hoạt Động</p>
                            </div>
                        </div>
                    </div>

                    <!-- Filter Section -->
                    <div class="filter-section">
                        <div class="row" style="align-items: flex-end;">
                            <form action="ListShiftServlet" method="post" style="display: flex; gap: 10px; align-items: flex-end; flex-wrap: wrap;">
                                <!-- Input tìm kiếm -->
                                <div style="flex: 2;">
                                    <label for="keyword" style="display: block; margin-bottom: 4px;">Tìm kiếm theo tên ca</label>
                                    <input type="text" name="keyword" value="${keyword}" placeholder="Tên ca"
                                           style="padding: 6px 12px; border: 1px solid #ced4da; border-radius: 4px; width: 100%;">
                                </div>
                                <!-- Nút submit -->
                                <div style="flex: 0;">
                                    <input type="submit" value="Search"
                                           style="padding: 6px 12px; background-color: #28a745; color: white; border: none; border-radius: 4px; height: 38px;">
                                </div>
                                <!-- Select chi nhánh -->
                                <div style="flex: 1;">
                                    <label for="storeId" style="display: block; margin-bottom: 4px;">Chi nhánh</label>
                                    <select name="storeId" onchange="this.form.submit()"
                                            style="padding: 6px 12px; border: 1px solid #ced4da; border-radius: 4px; width: 100%;">
                                        <option value="" ${storeIdStr==''?'selected':''}>Tất cả chi nhánh</option>
                                        <c:forEach var="stores" items="${stores}">
                                            <option value="${stores.getStoreID()}" ${storeId==stores.getStoreID()?'selected':''}>${stores.getStoreName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </form>
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
                                            <button class="btn btn-sm btn-light me-1" onclick="window.location.href = 'ListShiftServlet?action=update&shiftId=${shifts.getShiftID()}'" title="Chỉnh sửa">
                                                <i class="fas fa-edit text-dark"></i>
                                            </button>
                                            <button class="btn btn-sm btn-light" onclick="deleteShift(${shifts.getShiftID()}, '${shifts.getShiftName()}')" title="Xóa">
                                                <i class="fas fa-trash text-danger"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="shift-body">
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="shift-info">
                                                    <div class="info-label">Giờ làm</div>
                                                    <div class="info-value">${shifts.getTime()}</div>
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
                                            <div class="count-number">${shifts.countEmpByShiftId()}</div>
                                            <div class="count-label">Nhân viên</div>
                                        </div>
                                        <!--                                        <div class="mt-3">
                                                                                    <button class="btn btn-primary btn-sm w-100" onclick="window.location.href = 'ListShiftServlet?action=assignEmp&shiftId=${shifts.getShiftID()}'">
                                                                                        <i class="fas fa-users me-1"></i>Phân Công Nhân Viên
                                                                                    </button>
                                                                                </div>-->
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
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String messageAdd = (String) request.getAttribute("errorAdd"); %>
                    <% if (messageAdd != null) { %>
                    <div class="alert <%= messageAdd.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= messageAdd %>
                    </div>
                    <% } %>
                    <form action="ListShiftServlet?action=add" method="post">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="shiftName" value="${shiftName}" class="form-control" id="shiftName" placeholder="Tên ca làm việc" required>
                                        <label for="shiftName">Tên Ca Làm Việc *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" name="isActive" id="shiftStatus">
                                            <option value="active" value="${isActive=='active'?'selected':''}">Hoạt động</option>
                                            <option value="inactive" value="${isActive=='inactive'?'selected':''}">Tạm dừng</option>
                                        </select>
                                        <label for="shiftStatus">Trạng Thái</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" name="startTime" value="${startTime}" class="form-control" id="startTime" required>
                                        <label for="startTime">Giờ Bắt Đầu *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" name="endTime" value="${endTime}" class="form-control" id="endTime" required>
                                        <label for="endTime">Giờ Kết Thúc *</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" name="storeIdAdd" id="department">
                                            <option value="" ${storeIdAdd==''?'selected':''}>Tất cả chi nhánh</option>   
                                            <c:forEach var="stores" items="${stores}">
                                                <option value="${stores.getStoreID()}" ${storeIdAdd==stores.getStoreID()?'selected':''}>${stores.getStoreName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="department">Chi nhánh</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit" >Tạo Ca Làm Việc</button>
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
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String messageUp = (String) request.getAttribute("errorUp"); %>
                    <% if (messageUp != null) { %>
                    <div class="alert <%= messageUp.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= messageUp %>
                    </div>
                    <% } %>
                    <form action="ListShiftServlet?action=update" method="post">
                        <div class="modal-body">
                            <div class="row">
                                <input type="hidden" name="shiftIdUp" value="${shiftIdUp}">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="shiftNameUp" value="${shiftNameUp}" class="form-control" id="shiftName" placeholder="Tên ca làm việc" required>
                                        <label for="shiftNameUp">Tên Ca Làm Việc *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" name="isActiveUp" id="shiftStatus">
                                            <option value="active" value="${isActiveUp=='active'?'selected':''}">Hoạt động</option>
                                            <option value="inactive" value="${isActiveUp=='inactive'?'selected':''}">Tạm dừng</option>
                                        </select>
                                        <label for="shiftStatus">Trạng Thái</label>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" name="startTimeUp" value="${startTimeUp}" class="form-control" id="startTime" required>
                                        <label for="startTime">Giờ Bắt Đầu *</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating mb-3">
                                        <input type="time" name="endTimeUp" value="${endTimeUp}" class="form-control" id="endTime" required>
                                        <label for="endTime">Giờ Kết Thúc *</label>
                                    </div>
                                </div>
                            </div>

                            <!--                            <div class="break-time-section">
                                                            <h6><i class="fas me-2"></i>Thời Gian cho phép chấm công</h6>
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <div class="form-floating mb-3">
                                                                        <input type="time" name="checkInTimeUp" value="${checkInTimeUp}" class="form-control" id="breakStart">
                                                                        <label for="breakStart">Bắt Đầu</label>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-floating mb-3">
                                                                        <input type="time" name="checkOutTimeUp" value="${checkOutTimeUp}" class="form-control" id="breakEnd">
                                                                        <label for="breakEnd">Kết Thúc</label>
                                                                    </div>
                                                                </div>
                            
                                                            </div>
                                                        </div>-->

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" name="storeIdUp" id="department">
                                            <option value="" ${storeIdUp==''?'selected':''}>Tất cả chi nhánh</option>   
                                            <c:forEach var="stores" items="${stores}">
                                                <option value="${stores.getStoreID()}" ${storeIdUp==stores.getStoreID()?'selected':''}>${stores.getStoreName()}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="department">Chi nhánh</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Hủy</button>
                            <button type="submit" class="btn btn-submit">Chỉnh sửa ca Làm Việc</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <--<!-- Thêm script -->

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
        <c:if test="${addSuccess}">
            <script>
                                                Swal.fire({
                                                    title: 'Thành công',
                                                    text: `Thêm ca thành công.`,
                                                    icon: 'success',
                                                    showConfirmButton: true
                                                });
            </script>
        </c:if>
        <c:if test="${openAdd}">
            <script>
                const modal = new bootstrap.Modal(document.getElementById('addShiftModal'));
                modal.show();
            </script>
        </c:if>
        <c:if test="${openUpdate}">
            <script>
                const modal = new bootstrap.Modal(document.getElementById('editShiftModal'));
                modal.show();
            </script>
        </c:if>


        <script>
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
                        fetch('ListShiftServlet', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            },
                            body: new URLSearchParams({
                                action: 'delete',
                                shiftId: shiftId
                            })
                        })
                                .then(response => response.text())
                                .then(result => {
                                    console.log("Server response:", result);
                                    if (result.trim() === 'success') {
                                        Swal.fire({
                                            title: 'Cảnh báo!',
                                            text: `Ca đã được xóa thành công.`,
                                            icon: 'warning',
                                            confirmButtonText: 'OK'
                                        }).then(() => {
                                            location.reload();
                                        });
                                    } else {
                                        Swal.fire({
                                            title: '',
                                            text: `Ca này đang được sử dụng cho nhân viên. Không thể xóa!`,
                                            icon: 'warning',
                                            showConfirmButton: true
                                        });
                                    }
                                })
                                .catch(error => {
                                    alert('Lỗi khi gửi dữ liệu: ' + error.message);
                                    console.error('Error:', error);
                                });
                    }
                });
            }
        </script>
    </body>
</html>

