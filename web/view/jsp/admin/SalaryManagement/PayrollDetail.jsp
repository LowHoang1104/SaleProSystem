<%-- 
    Document   : PayrollDetail
    Created on : Jun 3, 2025, 1:01:18 PM
    Author     : Thinhnt
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>Bảng lương chi tiết</title>

        <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.png">

        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="view/assets/css/animate.css">
        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="view/assets/css/style.css">

        <style>
            .pagination {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .pagination select {
                padding: 8px 12px;
                border: 1px solid #ddd;
                border-radius: 4px;
                background: white;
            }

            .pagination button {
                padding: 8px 12px;
                border: 1px solid #ddd;
                background: white;
                border-radius: 4px;
                cursor: pointer;
            }

            .pagination button:hover {
                background: #f5f5f5;
            }

            .pagination button:disabled {
                opacity: 0.5;
                cursor: not-allowed;
            }
            .modal-backdrop {
                background-color: rgba(0, 0, 0, 0.5);
            }

            .modal-dialog {
                max-width: 500px;
            }

            .btn-group {
                margin: 20px 0;
            }

            .form-select {
                margin-bottom: 15px;
            }

            .modal-header {
                background-color: #28a745;
                color: white;
            }

            .modal-header .btn-close {
                filter: invert(1);
            }
            .header-table {
                background-color: white;
                padding: 15px 20px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                border-bottom: 1px solid #e0e0e0;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .header-left {
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .back-btn {
                background: none;
                border: none;
                font-size: 18px;
                cursor: pointer;
                color: #666;
            }

            .title {
                font-size: 18px;
                font-weight: 600;
                color: #333;
            }

            .search-container {
                position: relative;
                flex: 1;
                min-width: 100px;
                max-width: 500px;
                margin: 0 20px;
            }

            .search-input {
                width: 100%;
                padding: 12px 40px 12px 15px;
                border: 1px solid #ddd;
                border-radius: 8px;
                font-size: 14px;
                background-color: #f8f9fa;
            }

            .search-icon {
                position: absolute;
                right: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #999;
            }

            .header-right {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                font-weight: 500;
                transition: all 0.2s;
            }

            .btn-secondary {
                background-color: #f8f9fa;
                color: #495057;
                border: 1px solid #dee2e6;
            }

            .btn-secondary:hover {
                background-color: #e2e6ea;
            }

            .btn-primary {
                background-color: #28a745;
                color: white;
            }

            .btn-primary:hover {
                background-color: #218838;
            }
            .btn-cancel {
                background-color: #dc3545; /* đỏ */
                color: white;
                border: none;
            }
            .btn-cancel:hover {
                background-color: #c82333;
            }


            .container {
                margin: 20px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }

            .table-container {
                overflow-x: auto;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                min-width: 1200px;
            }

            th,
            td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #f0f0f0;
            }

            th {
                background-color: #f8f9fa;
                font-weight: 600;
                color: #495057;
                font-size: 14px;
                position: sticky;
                top: 0;
                z-index: 10;
            }

            td {
                font-size: 14px;
                color: #333;
            }

            .employee-info {
                display: flex;
                flex-direction: column;
                gap: 4px;
            }

            .employee-name {
                font-weight: 600;
                color: #007bff;
            }

            .employee-id {
                font-size: 12px;
                color: #666;
            }

            .number-cell {
                text-align: right;
                /*font-family: monospace;*/
                font-weight: 500;
            }

            .editable-cell {
                background-color: #fff3cd;
                cursor: pointer;
                transition: background-color 0.2s;
            }

            .editable-cell:hover {
                background-color: #ffeaa7;
            }

            .delete-btn {
                background: none;
                border: none;
                color: #dc3545;
                cursor: pointer;
                font-size: 16px;
                padding: 4px 8px;
                border-radius: 4px;
                transition: all 0.2s;
            }

            .delete-btn:hover {
                background-color: #f8d7da;
            }

            .add-employee-btn {
                margin: 20px;
                padding: 12px 24px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                font-weight: 500;
                transition: background-color 0.2s;
            }

            .add-employee-btn:hover {
                background-color: #0056b3;
            }

            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 9999;
            }

            .modal-content {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                width: 90%;
                max-width: 500px;
                max-height: 80vh;
                overflow-y: auto;
            }

            .modal-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .modal-title {
                font-size: 18px;
                font-weight: 600;
            }

            .close-btn {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                color: #666;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
                color: #333;
            }

            .form-input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
            }

            .form-input:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
            }

            .form-actions {
                display: flex;
                gap: 10px;
                justify-content: flex-end;
                margin-top: 20px;
            }

            .btn-cancel {
                background-color: #6c757d;
                color: white;
            }

            .btn-cancel:hover {
                background-color: #5a6268;
            }

            .total-row {
                background-color: #f8f9fa;
                font-weight: 600;
            }

            .status-badge {
                padding: 4px 8px;
                border-radius: 12px;
                font-size: 12px;
                font-weight: 500;
            }

            .status-active {
                background-color: #d4edda;
                color: #155724;
            }

            .floating-save-btn {
                position: fixed;
                bottom: 30px;
                right: 30px;
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 50px;
                padding: 15px 25px;
                font-size: 14px;
                font-weight: 600;
                cursor: pointer;
                box-shadow: 0 4px 12px rgba(40, 167, 69, 0.3);
                transition: all 0.2s;
                z-index: 100;
            }

            .floating-save-btn:hover {
                background-color: #218838;
                transform: translateY(-2px);
                box-shadow: 0 6px 16px rgba(40, 167, 69, 0.4);
            }

            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 15px 20px;
                border-radius: 6px;
                color: white;
                font-weight: 500;
                z-index: 1001;
                transform: translateX(100%);
                transition: transform 0.3s ease;
            }

            .notification.show {
                transform: translateX(0);
            }

            .notification-success {
                background-color: #28a745;
            }

            .notification-error {
                background-color: #dc3545;
            }

            @media (max-width: 768px) {
                .header {
                    flex-direction: column;
                    gap: 10px;
                }

                .search-container {
                    max-width: none;
                    margin: 0;
                }

                .container {
                    margin: 10px;
                }

                .modal-content {
                    width: 95%;
                    padding: 20px;
                }
            }
            #salaryTable th:not(:nth-child(1)):not(:nth-child(2)),
            #salaryTable td:not(:nth-child(1)):not(:nth-child(2)) {
                text-align: center;
                vertical-align: middle;
            }
        </style>
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <!-- Main Content -->
            <!-- Page Wrapper -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Cập nhật bảng lương</h4>
                            <h6>Cập nhật theo tiền lương được thiết lập mới nhất</h6>
                        </div>
                        <div class="header-table">
                            <div class="search-container">
                                <input type="text" class="search-input" placeholder="Tìm nhân viên" id="searchInput">
                            </div>
                            <div class="header-right">
                                <!-- Export File Button -->
                                <a href="#" class="btn btn-secondary" id="exportFileTrigger">
                                    <i class="fas fa-file-export me-1"></i>Xuất file
                                </a>
                                <%
                                    String payrollPeriodId = request.getParameter("payrollPeriodId");
                                %>
                                <button class="btn btn-secondary" onclick="ResetSalary(${payrollPeriodId})">🔄Cập nhật</button>
                                <button class="btn btn-primary" onclick="PayrollClose(${payrollPeriodId})">✓ Chốt lương</button>
                                <button class="btn btn-primary" onclick="Paymentcompleted(${payrollPeriodId})">💰 Đã thanh toán</button>
                            </div>
                        </div>
                    </div>
                    <!-- Week Navigation -->
                    <div class="card">
                        <div class="card-body">
                            <div class="table-container">
                                <table id="salaryTable">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên nhân viên</th>
                                            <th>Lương chính</th>
                                            <th>Làm thêm</th>
                                            <th>Hoa hồng</th>
                                            <th>Phụ cấp</th>
                                            <th>Giảm trừ</th>
                                            <th>Tổng lương</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody id="tableBody">
                                        <fmt:setLocale value="en_US" />
                                        <c:forEach var="payrollCalculation" items="${payrollCalculations}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td> 
                                                <td>
                                                    <div class="employee-info">
                                                        <div class="employee-name">${payrollCalculation.getEmployee().fullName}</div>
                                                        <div class="employee-code">${payrollCalculation.getEmployee().getCode()}</div>
                                                        <div class="employee-id" style="display: none;">${payrollCalculation.getEmployee().getEmployeeID()}</div>
                                                    </div>
                                                </td>
                                                <td class="number-cell" onclick="editCell(${payrollCalculation.getEmployee().employeeID}, 'baseSalary')">                                                    <fmt:formatNumber value="${payrollPeriod.getTotalSalary()}" type="number" groupingUsed="true" />
                                                    <fmt:formatNumber value="${payrollCalculation.salaryAmount}" type="number" groupingUsed="true" />
                                                </td>
                                                <td class="number-cell" onclick="editCell(${payrollCalculation.getEmployee().employeeID}, 'overtime')">
                                                    <fmt:formatNumber value="${payrollCalculation.overtimeAmount}" type="number" groupingUsed="true" />
                                                </td>
                                                <td class="number-cell" onclick="editCell(${payrollCalculation.getEmployee().employeeID}, 'overtime')">
                                                    <fmt:formatNumber value="${payrollCalculation.commissionAmount}" type="number" groupingUsed="true" />
                                                </td>
                                                <td class="number-cell" onclick="editCell(${payrollCalculation.getEmployee().employeeID}, 'commission')">
                                                    <fmt:formatNumber value="${payrollCalculation.allowanceAmount}" type="number" groupingUsed="true" />
                                                </td>
                                                <td class="number-cell" onclick="editCell(${payrollCalculation.getEmployee().employeeID}, 'allowance')">
                                                    <fmt:formatNumber value="${payrollCalculation.deductionAmount}" type="number" groupingUsed="true" />

                                                </td>
                                                <td class="number-cell">
                                                    <strong>
                                                        <fmt:formatNumber value="${payrollCalculation.getNetSalary()}" type="number" groupingUsed="true" />
                                                    </strong>
                                                </td>
                                                <td>
                                                    <button class="delete-btn" onclick="deleteEmployee(${payrollCalculation.getEmployee().employeeID})" title="Xóa">🗑</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>
                            </div>

                        </div>
                        <div class="footer">
                            <div class="pagination">
                                <button onclick="previousPage()" id="prevBtn">‹</button>
                                <span id="pageInfo">1 / 1</span>
                                <button onclick="nextPage()" id="nextBtn">›</button>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- Modal Thanh Toán -->
        <div class="modal fade" id="paymentModal" tabindex="-1" aria-labelledby="paymentModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="paymentModalLabel">
                            <i class="fas fa-money-bill-wave me-2"></i>
                            Xác nhận quỹ thanh toán lương
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="paymentMethod" class="form-label">
                                <strong>Quỹ thanh toán:</strong>
                            </label>
                            <select class="form-select" id="paymentMethod" required>
                                <option value="">-- Chọn quỹ xử lí thanh toán --</option>
                                <c:forEach var="fund" items="${funds}">
                                    <option value="${fund.fundID}">${fund.fundName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-cancel" onclick="ClosePaymentcompleted()">
                            Hủy
                        </button>
                        <button type="button" class="btn btn-primary" onclick="savePayment()">
                            💾 Lưu thanh toán
                        </button>
                    </div>
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
        <script>
                            const periodId = '<%= payrollPeriodId %>';
                            function Paymentcompleted() {
                                const modal = new bootstrap.Modal(document.getElementById('paymentModal'));
                                modal.show();
                            }
                            function ClosePaymentcompleted() {
                                const modal = bootstrap.Modal.getInstance(document.getElementById('paymentModal'));
                                modal.hide();
                            }
                            // Hàm lưu thanh toán
                            function savePayment() {
                                const _paymentMethod = document.getElementById('paymentMethod').value;

                                // Validation
                                if (!_paymentMethod) {
                                    alert('Vui lòng chọn quỹ thanh toán!');
                                    return;
                                }
                                ClosePaymentcompleted();
                                Swal.fire({
                                    title: 'Xác nhận?',
                                    text: `Bạn có chắc chắn muốn là đã thanh toán bằng quỹ này không ?`,
                                    icon: 'warning',
                                    showCancelButton: true,
                                    confirmButtonColor: '#d33',
                                    cancelButtonColor: '#3085d6',
                                    confirmButtonText: 'Đồng ý',
                                    cancelButtonText: 'Hủy'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        fetch('SavePayrollDetailServlet', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            body: new URLSearchParams({
                                                action: 'paymentcompleted',
                                                periodId: periodId,
                                                paymentMethod: _paymentMethod
                                            })
                                        })
                                                .then(response => response.text())
                                                .then(result => {
                                                    if (result.trim() === 'success') {
                                                        alert('Xử lí đã thanh toán thành công');
                                                        location.reload();
                                                    } else {
                                                        alert('Thất bại: ' + result);
                                                    }
                                                })
                                                .catch(error => {
                                                    alert('Lỗi khi gửi dữ liệu: ' + error.message);
                                                    console.error('Error:', error);
                                                });
                                    }
                                });
                            }
                            $(document).ready(function () {
                                // Handle export button click
                                $('#exportFileTrigger').on('click', function (e) {
                                    e.preventDefault();
                                    const $button = $(this);
                                    $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang xuất...');

                                    $.ajax({
                                        url: 'ExportSalary',
                                        type: 'GET',
                                        data: {action: 'exportSalary',
                                            periodId: periodId},
                                        xhrFields: {responseType: 'blob'},
                                        success: function (data) {
                                            $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');

                                            // Tạo URL từ blob data
                                            const url = window.URL.createObjectURL(data);

                                            // Tạo link download
                                            const link = document.createElement('a');
                                            link.href = url;
                                            link.download = 'Salary_export_' + new Date().toISOString().split('T')[0] + '.xlsx';
                                            document.body.appendChild(link);
                                            link.click();
                                            document.body.removeChild(link);

                                            // Dọn dẹp memory
                                            window.URL.revokeObjectURL(url);

                                            alert('Xuất file thành công!');
                                        },
                                        error: function (xhr, status, error) {
                                            $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');
                                            alert('Lỗi khi xuất file: ' + (xhr.responseText || error));
                                        }
                                    });
                                });
                            });
                            function deleteEmployee(empId) {
                                const _empId = empId;
                                const _payrollPeriodId = periodId;
                                Swal.fire({
                                    title: 'Xác nhận xóa?',
                                    text: `Bạn có chắc chắn muốn xóa nhân viên này? Hành động này không thể hoàn tác!`,
                                    icon: 'warning',
                                    showCancelButton: true,
                                    confirmButtonColor: '#d33',
                                    cancelButtonColor: '#3085d6',
                                    confirmButtonText: 'Xóa',
                                    cancelButtonText: 'Hủy'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        fetch('SavePayrollDetailServlet', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            body: new URLSearchParams({
                                                action: 'deleteEmployee',
                                                periodId: _payrollPeriodId,
                                                empId: _empId
                                            })
                                        })
                                                .then(response => response.text())
                                                .then(result => {
                                                    if (result.trim() === 'success') {
                                                        alert('Xoá thông tin lương nhân viên thành công');
                                                        location.reload();
                                                    } else {
                                                        alert('Thất bại: ' + result);
                                                    }
                                                })
                                                .catch(error => {
                                                    alert('Lỗi khi gửi dữ liệu: ' + error.message);
                                                    console.error('Error:', error);
                                                });
                                    }
                                });
                            }
                            function getEmployeeIds() {
                                const employeeIdElements = document.querySelectorAll(".employee-id");
                                const employeeIds = Array.from(employeeIdElements).map(el => el.textContent.trim());
                                return employeeIds;
                            }
                            function ResetSalary(payrollPeriodId) {
                                const _payrollPeriodId = payrollPeriodId;
                                const _employeeIds = getEmployeeIds();

                                console.log('Danh sách employeeId được chọn:', _employeeIds);
                                // Gửi dữ liệu bằng fetch
                                fetch('SavePayrollDetailServlet', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                    body: new URLSearchParams({
                                        action: 'ResetPayrollDetail',
                                        periodId: _payrollPeriodId,
                                        employeeIds: JSON.stringify(_employeeIds) // gửi danh sách id
                                    })
                                })
                                        .then(response => response.text())
                                        .then(result => {
                                            if (result.trim() === 'success') {
                                                alert('Cập nhật thành công!');
                                                location.reload();
                                            } else {
                                                alert('Thất bại: ' + result);
                                            }
                                        })
                                        .catch(error => {
                                            alert('Lỗi khi gửi dữ liệu: ' + error.message);
                                            console.error('Error:', error);
                                        });
                            }
                            function PayrollClose(payrollPeriodId) {
                                // Gửi dữ liệu bằng fetch
                                Swal.fire({
                                    title: 'Xác nhận chốt lương?',
                                    text: `Hãy đảm bảo bảng lương đã được cập nhật trước khi chốt.Bạn có chắc chắn muốn chốt bảng lương này không?`,
                                    icon: 'warning',
                                    showCancelButton: true,
                                    confirmButtonColor: '#d33',
                                    cancelButtonColor: '#3085d6',
                                    confirmButtonText: 'Chốt lương',
                                    cancelButtonText: 'Hủy'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        fetch('SavePayrollDetailServlet', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            body: new URLSearchParams({
                                                action: 'payrollClose',
                                                periodId: periodId
                                            })
                                        })
                                                .then(response => response.text())
                                                .then(result => {
                                                    if (result.trim() === 'success') {
                                                        alert('Chốt lương thành công!');
                                                        window.location.href = 'PayrollServlet';
                                                    } else {
                                                        alert('Thất bại: ' + result);
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
        <script>
            $(document).ready(function () {
                // Search live khi nhập vào ô tìm kiếm
                $('#searchInput').on('input', function () {
                    const keyword = $(this).val().toLowerCase(); // chuyển về chữ thường để so sánh không phân biệt hoa thường

                    $('#tableBody tr').each(function () {
                        const employeeName = $(this).find('.employee-name').text().toLowerCase();
                        const employeeCode = $(this).find('.employee-code').text().toLowerCase();

                        if (employeeName.includes(keyword) || employeeCode.includes(keyword)) {
                            $(this).show();
                        } else {
                            $(this).hide();
                        }
                    });
                });
            });

        </script>
        <script>
            let allRows = [];
            let filteredRows = [];
            let currentPage = 1;
            let itemsPerPage = 5; // Số dòng mỗi trang

// Tải dữ liệu ban đầu từ DOM
            function loadTableRows() {
                const tbody = document.getElementById('tableBody');
                allRows = Array.from(tbody.querySelectorAll('tr'));
                filteredRows = [...allRows];
                renderTable();
            }

// Tìm kiếm theo mã hoặc tên
            function searchData() {
                const searchTerm = document.getElementById('searchInput').value.toLowerCase();
                filteredRows = allRows.filter(row => {
                    const code = row.children[0].textContent.toLowerCase();
                    const name = row.children[1].textContent.toLowerCase();
                    return code.includes(searchTerm) || name.includes(searchTerm);
                });
                currentPage = 1;
                renderTable();
            }

// Vẽ lại bảng với phân trang
            function renderTable() {
                const tbody = document.getElementById('tableBody');
                tbody.innerHTML = '';

                const totalPages = Math.ceil(filteredRows.length / itemsPerPage) || 1;
                const startIndex = (currentPage - 1) * itemsPerPage;
                const endIndex = startIndex + itemsPerPage;
                const pageRows = filteredRows.slice(startIndex, endIndex);

                pageRows.forEach(row => tbody.appendChild(row));

                // Cập nhật phân trang
                document.getElementById('pageInfo').textContent = currentPage + ' / ' + totalPages;
                document.getElementById('prevBtn').disabled = currentPage === 1;
                document.getElementById('nextBtn').disabled = currentPage === totalPages || totalPages === 0;
            }


            function previousPage() {
                if (currentPage > 1) {
                    currentPage--;
                    renderTable();
                }
            }

            function nextPage() {
                const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
                if (currentPage < totalPages) {
                    currentPage++;
                    renderTable();
                }
            }

// Gán sự kiện sau khi DOM đã sẵn sàng
            document.addEventListener('DOMContentLoaded', () => {
                loadTableRows();
                document.getElementById('searchInput').addEventListener('input', searchData);
                document.getElementById('prevBtn').addEventListener('click', previousPage);
                document.getElementById('nextBtn').addEventListener('click', nextPage);
            });
        </script>
    </body>
</html>

