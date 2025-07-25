<%-- 
    Document   : List_shift
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
            .header-table{
                padding: 20px;
                background: #fafafa;
                border-bottom: 1px solid #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 20px;
            }

            .filter-group {
                display: flex;
                align-items: center;
                gap: 15px;
                flex: 0 0 auto; /* Không co giãn, kích thước tự động */
                min-width: 0; /* Cho phép shrink nếu cần */
            }

            .filter-label {
                font-weight: 600;
                color: #495057;
                font-size: 14px;
                white-space: nowrap;
                flex-shrink: 0; /* Không co lại */
            }

            .filter-select {
                padding: 8px 12px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                font-size: 14px;
                background: white;
                cursor: pointer;
                transition: border-color 0.3s ease;
                min-width: 120px; /* Tăng chiều rộng tối thiểu */
                max-width: 180px; /* Giới hạn chiều rộng tối đa */
                height: 38px; /* Chiều cao cố định */
                line-height: 1.2;
            }

            .filter-select:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
            }

            .search-container {
                display: flex;
                align-items: center;
                gap: 12px;
                flex: 1; /* Chiếm không gian còn lại */
                max-width: 500px; /* Giới hạn chiều rộng tối đa */
                min-width: 250px; /* Chiều rộng tối thiểu */
            }

            .search-box {
                position: relative;
                flex: 1;
                min-width: 200px; /* Chiều rộng tối thiểu của search box */
            }

            .search-box input {
                width: 100%;
                padding: 10px 15px 10px 40px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 14px;
                outline: none;
                height: 38px; /* Chiều cao cố định, khớp với filter select */
                box-sizing: border-box;
            }

            .search-box input:focus {
                border-color: #4CAF50;
            }

            .search-icon {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #999;
            }

            .filter-btn {
                padding: 10px 15px;
                border: 1px solid #ddd;
                background: white;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                height: 38px; /* Chiều cao cố định, khớp với các element khác */
                display: flex;
                align-items: center;
                white-space: nowrap;
                flex-shrink: 0; /* Không co lại */
            }

            .filter-btn:hover {
                background: #f5f5f5;
            }

            .action-buttons {
                display: flex;
                gap: 10px;
                flex-shrink: 0; /* Không co lại */
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                transition: background 0.3s;
                height: 38px; /* Chiều cao cố định */
                display: flex;
                align-items: center;
                white-space: nowrap;
                box-sizing: border-box;
            }

            .btn-primary {
                background: #4CAF50;
                color: white;
            }

            .btn-primary:hover {
                background: #45a049;
            }

            .btn-secondary {
                background: #f0f0f0;
                color: #333;
                border: 1px solid #ddd;
            }

            .btn-secondary:hover {
                background: #e0e0e0;
            }

            .table-container {
                overflow-x: auto;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                font-size: 14px;
            }

            th, td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #eee;
            }

            th {
                background: #f8f9fa;
                font-weight: 600;
                color: #555;
                position: sticky;
                top: 0;
                z-index: 10;
            }

            tr:hover {
                background: #f8f9fa;
            }

            .checkbox {
                width: 16px;
                height: 16px;
                accent-color: #4CAF50;
            }

            .status {
                padding: 4px 8px;
                border-radius: 4px;
                font-size: 12px;
                font-weight: 500;
            }

            .status.Pending {
                background: #fff3e0;
                color: #f57c00;
            }

            .status.Approved {
                background: #e3f2fd;
                color: #1565c0;
            }
            .status.Paid {
                background: #e3f2fd;
                color: #1565c0;
            }

            .salary-amount {
                font-weight: 600;
                color: #2e7d32;
            }

            .footer {
                padding: 20px;
                background: #fafafa;
                border-top: 1px solid #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

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

            .total-summary {
                font-weight: 600;
                color: #333;
            }

            .empty-state {
                text-align: center;
                padding: 40px;
                color: #999;
            }

            /* Modal Styles */
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

            .modal-add {
                background: white;
                border-radius: 8px;
                width: 90%;
                max-width: 800px;
                max-height: 90vh;
                overflow-y: auto;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            }

            .modal-header {
                padding: 20px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .modal-title {
                font-size: 18px;
                font-weight: 600;
                color: #333;
            }

            .close-btn {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                color: #999;
                padding: 0;
                width: 30px;
                height: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .close-btn:hover {
                color: #333;
            }

            .modal-body {
                padding: 20px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-label {
                display: block;
                margin-bottom: 8px;
                font-weight: 500;
                color: #333;
            }

            .form-control {
                width: 100%;
                padding: 10px 12px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 14px;
                outline: none;
            }

            .form-control:focus {
                border-color: #4CAF50;
                box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
            }

            .form-row {
                display: flex;
                gap: 20px;
            }

            .form-row .form-group {
                flex: 1;
            }

            .radio-group {
                display: flex;
                gap: 20px;
                align-items: center;
            }

            .radio-option {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .radio-option input[type="radio"] {
                width: 16px;
                height: 16px;
                accent-color: #4CAF50;
            }

            .employee-search {
                position: relative;
                margin-bottom: 15px;
            }

            .employee-search input {
                padding-left: 40px;
            }

            .employee-search .search-icon {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #999;
            }

            .employee-list {
                border: 1px solid #ddd;
                border-radius: 6px;
                max-height: 200px;
                overflow-y: auto;
            }

            .employee-item {
                display: flex;
                align-items: center;
                padding: 10px 15px;
                border-bottom: 1px solid #eee;
            }

            .employee-item:last-child {
                border-bottom: none;
            }

            .employee-item:hover {
                background: #f8f9fa;
            }

            .employee-info {
                flex: 1;
                margin-left: 10px;
            }

            .employee-name {
                font-weight: 500;
                color: #333;
            }

            .employee-id {
                font-size: 12px;
                color: #666;
            }

            .employee-department {
                font-size: 12px;
                color: #999;
            }

            .modal-footer {
                padding: 20px;
                border-top: 1px solid #eee;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
            }

            .btn-cancel {
                background: #f5f5f5;
                color: #666;
                border: 1px solid #ddd;
            }

            .btn-cancel:hover {
                background: #e0e0e0;
            }

            .btn-save {
                background: #4CAF50;
                color: white;
            }

            .btn-save:hover {
                background: #45a049;
            }

            /* Responsive Design */
            @media (max-width: 1024px) {
                .header-table {
                    flex-wrap: wrap;
                    gap: 15px;
                }

                .filter-group {
                    min-width: 0;
                }

                .search-container {
                    min-width: 200px;
                    max-width: 400px;
                }
            }

            @media (max-width: 768px) {
                .header-table {
                    flex-direction: column;
                    gap: 15px;
                }

                .filter-group {
                    width: 100%;
                    justify-content: flex-start;
                }

                .search-container {
                    width: 100%;
                    max-width: none;
                    min-width: 0;
                }

                .action-buttons {
                    width: 100%;
                    justify-content: center;
                }

                th, td {
                    padding: 8px 10px;
                    font-size: 12px;
                }

                .modal-add {
                    width: 95%;
                    margin: 10px;
                }

                .form-row {
                    flex-direction: column;
                    gap: 0;
                }

                .radio-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }
            }

            @media (max-width: 480px) {
                .filter-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }

                .filter-select {
                    width: 100%;
                    max-width: none;
                }

                .search-container {
                    gap: 8px;
                }

                .filter-btn {
                    width: 100%;
                    justify-content: center;
                }
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
                            <h4>Bảng lương</h4>
                            <h6>Tạo bảng lương cho nhân viên</h6>
                        </div>
                        <div class="header-table">
                            <div class="search-container">
                                <div class="search-box">
                                    <span class="search-icon">🔍</span>
                                    <input type="text" id="searchInput" placeholder="Theo mã, tên bảng lương">
                                </div>
                                <div class="filter-group">
                                    <select class="filter-select" onchange="window.location.href = 'PayrollServlet?storeId=' + this.value">
                                        <c:forEach var="store" items="${stores}">
                                            <option ${storeId==store.getStoreID()?'selected':''} value="${store.getStoreID()}">${store.getStoreName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="action-buttons">
                                <button class="btn btn-primary" onclick="addSalaryRecord()">+ Bảng tính lương</button>
                                <button class="btn btn-secondary" onclick="exportData()">📊 Xuất file</button>
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
                                            <th>Mã</th>
                                            <th>Tên</th>
                                            <th>Kỳ hạn trả</th>
                                            <th>Kỳ làm việc</th>
                                            <th>Tổng lương</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody id="salaryTableBody">
                                        <fmt:setLocale value="en_US" />
                                        <c:forEach var="payrollPeriod" items="${payrollPeriods}">
                                            <tr onclick="window.location.href = 'PayrollDetailServlet?payrollPeriodId=' + ${payrollPeriod.payrollPeriodId}">
                                                <td>${payrollPeriod.getCode()}</td>
                                                <td>${payrollPeriod.getName()}</td>
                                                <td>Hàng tháng</td>
                                                <td>${payrollPeriod.getPeriod() }</td>
                                                <td class="salary-amount">
                                                    <fmt:formatNumber value="${payrollPeriod.getTotalSalary()}" type="number" groupingUsed="true" />
                                                </td>
                                                <c:choose>
                                                    <c:when test="${payrollPeriod.status == 'Approved'}"><td><span class="status Approved">Đã chốt lương</span></td></c:when>
                                                    <c:when test="${payrollPeriod.status == 'Pending'}"><td><span class="status Pending">Chờ xử lí</span></td></c:when>
                                                    <c:when test="${payrollPeriod.status == 'Paid'}"><td><span class="status Paid">Đã thanh toán</span></td></c:when>
                                                    <c:otherwise><td><span class="status"></span></td></c:otherwise>
                                                    </c:choose>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
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
        </div>

        <!-- Modal Thêm bảng tính lương -->
        <div class="modal-overlay" id="addSalaryModal">
            <div class="modal-add">
                <div class="modal-header">
                    <h2 class="modal-title">Thêm bảng tính lương</h2>
                    <button class="close-btn" onclick="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <form id="addSalaryForm">
                        <div class="form-row">
                            <div class="form-group">
                                <label class="form-label">Kỳ hạn trả lương</label>
                                <select class="form-control" id="paymentPeriod" onchange="updateWorkPeriod()">
                                    <option value="monthly">Hàng tháng</option>
                                    <option value="custom">Tùy chọn</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Kỳ làm việc</label>
                                <div id="workPeriodSelect">
                                    <select class="form-control" id="workPeriodDropdown">
                                    </select>
                                </div>
                                <div id="workPeriodRange" style="display: none;">
                                    <div class="form-row">
                                        <div class="form-group">
                                            <label class="form-label">Từ ngày</label>
                                            <input type="date" class="form-control" id="startDate" >
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Đến ngày</label>
                                            <input type="date" class="form-control" id="endDate">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Phạm vi áp dụng</label>
                            <div class="radio-group">
                                <label class="radio-option">
                                    <input type="radio" name="applyScope" value="all" checked>
                                    <span>Tất cả nhân viên</span>
                                </label>
                                <label class="radio-option">
                                    <input type="radio" name="applyScope" value="selected">
                                    <span>Tùy chọn</span>
                                </label>
                            </div>
                        </div>
                        <div class="form-group" id="employeeSelection" style="display: none;">
                            <div class="employee-search">
                                <input type="text" id="employeeSearchInput" placeholder="Tìm theo mã, tên nhân viên" class="form-control">
                            </div>
                            <div class="employee-list" id="employeeList">
                                <c:forEach var="employee" items="${employees}">
                                    <div class="employee-item" data-employee-id="${employee.getEmployeeID()}">
                                        <input type="checkbox" class="checkbox" id="emp1">
                                        <div class="employee-info">
                                            <div class="employee-name">${employee.fullName}</div>
                                            <div class="employee-id">${employee.getCode()}</div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-cancel" onclick="closeModal()">Bỏ qua</button>
                    <button type="button" class="btn btn-save" onclick="saveSalaryRecord()">Lưu</button>
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
                        document.addEventListener('DOMContentLoaded', function () {
                            const dropdown = document.getElementById('workPeriodDropdown');
                            dropdown.innerHTML = ''; // Clear old options

                            const now = new Date();

                            for (let i = 0; i < 3; i++) {
                                //get.Month() tháng 1 bắt đầu là 0
                                const startDate = new Date(now.getFullYear(), now.getMonth() + i, 1);
                                const year = startDate.getFullYear();
                                const month = startDate.getMonth() + 1;
                                //Chuyển thành số có 2 chữ số
                                const paddedMonth = month.toString().padStart(2, '0');
                                const start = '01/' + paddedMonth + '/' + year;
                                //Tạo ra ngày cuối của tháng tiếp theo là ngày đầu của tháng hiện tại
                                const lastDay = new Date(year, month, 0).getDate();
                                const end = lastDay.toString().padStart(2, '0') + '/' + paddedMonth + '/' + year;

                                const option = document.createElement('option');
                                option.value = startDate;
                                option.textContent = start + ' - ' + end;
                                dropdown.appendChild(option);
                            }

                        });
                        function formatCurrency(amount) {
                            return new Intl.NumberFormat('vi-VN').format(amount);
                        }

                        function updatePaginationInfo() {
                            const totalPages = Math.ceil(filteredData.length / itemsPerPage);
                            document.getElementById('prevBtn').disabled = currentPage === 1;
                            document.getElementById('nextBtn').disabled = currentPage === totalPages || totalPages === 0;
                        }

                        function updateTotalSalary() {
                            const total = filteredData.reduce((sum, item) => sum + item.totalSalary, 0);
                            document.getElementById('totalSalary').textContent = formatCurrency(total);
                        }

                        function searchData() {
                            const searchTerm = document.getElementById('searchInput').value.toLowerCase();
                            filteredRows = salaryData.filter(item =>
                                item.id.toLowerCase().includes(searchTerm) ||
                                        item.name.toLowerCase().includes(searchTerm)
                            );
                            currentPage = 1;
                            renderTable();
                        }

                        function toggleSelectAll() {
                            const selectAllCheckbox = document.getElementById('selectAll');
                            const checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');

                            checkboxes.forEach(checkbox => {
                                checkbox.checked = selectAllCheckbox.checked;
                            });
                        }

                        function previousPage() {
                            if (currentPage > 1) {
                                currentPage--;
                                renderTable();
                            }
                        }

                        function nextPage() {
                            const totalPages = Math.ceil(filteredData.length / itemsPerPage);
                            if (currentPage < totalPages) {
                                currentPage++;
                                renderTable();
                            }
                        }

                        function updateItemsPerPage() {
                            itemsPerPage = parseInt(document.getElementById('itemsPerPage').value);
                            currentPage = 1;
                            renderTable();
                        }

                        function toggleFilter() {
                            alert('Chức năng lọc sẽ được phát triển trong phiên bản tiếp theo!');
                        }

                        function addSalaryRecord() {
                            document.getElementById('addSalaryModal').style.display = 'flex';
                        }
                        function updateWorkPeriod() {
                            const paymentPeriod = document.getElementById('paymentPeriod').value;
                            const workPeriodSelect = document.getElementById('workPeriodSelect');
                            const workPeriodRange = document.getElementById('workPeriodRange');
                            const dropdown = document.getElementById('workPeriodDropdown');

                            const now = new Date();
                            if (paymentPeriod === 'custom') {
                                workPeriodSelect.style.display = 'none';
                                workPeriodRange.style.display = 'block';
                                const yyyy = now.getFullYear();
                                const mm = String(now.getMonth() + 1).padStart(2, '0');
                                const dd = String(now.getDate()).padStart(2, '0');
                                const today = yyyy + '-' + mm + '-' + dd;
                                document.getElementById('startDate').value = today;
                                document.getElementById('endDate').value = today;
                            } else {
                                workPeriodSelect.style.display = 'block';
                                workPeriodRange.style.display = 'none';

                                // Update dropdown options based on payment period
                                dropdown.innerHTML = ''; // Clear old options


                                for (let i = 0; i < 3; i++) {
                                    //get.Month() tháng 1 bắt đầu là 0
                                    const startDate = new Date(now.getFullYear(), now.getMonth() + i, 1);
                                    const year = startDate.getFullYear();
                                    const month = startDate.getMonth() + 1;
                                    //Chuyển thành số có 2 chữ số
                                    const paddedMonth = month.toString().padStart(2, '0');
                                    const start = '01/' + paddedMonth + '/' + year;
                                    //Tạo ra ngày cuối của tháng tiếp theo là ngày đầu của tháng hiện tại
                                    const lastDay = new Date(year, month, 0).getDate();
                                    const end = lastDay.toString().padStart(2, '0') + '/' + paddedMonth + '/' + year;

                                    const option = document.createElement('option');
                                    console.log(startDate);
                                    option.value = startDate;
                                    option.textContent = start + ' - ' + end;
                                    dropdown.appendChild(option);
                                }
                            }
                        }
                        function closeModal() {
                            document.getElementById('addSalaryModal').style.display = 'none';
                            // Reset form
                            document.getElementById('addSalaryForm').reset();
                            document.getElementById('employeeSelection').style.display = 'none';

                            // Reset work period display
                            document.getElementById('workPeriodSelect').style.display = 'block';
                            document.getElementById('workPeriodRange').style.display = 'none';
                            document.getElementById('paymentPeriod').value = 'monthly';
                            updateWorkPeriod();
                        }

                        function getWorkPeriodValue() {
                            const paymentPeriod = document.getElementById('paymentPeriod').value;
                            if (paymentPeriod === 'custom') {
                                const startDate = document.getElementById('startDate').value;
                                const endDate = document.getElementById('endDate').value;

                                if (startDate && endDate) {
                                    const startFormatted = new Date(startDate).toLocaleDateString('vi-VN');
                                    const endFormatted = new Date(endDate).toLocaleDateString('vi-VN');
                                    return ``;
                                }
                                return '';
                            } else {
                                return document.getElementById('workPeriodDropdown').value;
                            }
                        }
                        // Handle radio button change for employee selection
                        document.querySelectorAll('input[name="applyScope"]').forEach(radio => {
                            radio.addEventListener('change', function () {
                                const employeeSelection = document.getElementById('employeeSelection');
                                if (this.value === 'selected') {
                                    employeeSelection.style.display = 'block';
                                } else {
                                    employeeSelection.style.display = 'none';
                                }
                            });
                        });
                        //Gắn mặc định là tất cả nhân viên thuộc chi nhánh
                        document.addEventListener("DOMContentLoaded", function () {
                            // Gắn sự kiện khi thay đổi radio applyScope
                            //radio của appyScope là all thì checked tất cả input của empolyee-item
                            document.querySelectorAll('input[name="applyScope"]').forEach(radio => {
                                radio.addEventListener('change', function () {
                                    //nếu không pfalsehải all thì checked là                             
                                    const isAllSelected = this.value === 'all';
                                    document.querySelectorAll('.employee-item input[type="checkbox"]').forEach(checkbox => {
                                        checkbox.checked = isAllSelected;
                                    });
                                });
                            });

                            // Mặc định: nếu radio 'all' đang được chọn khi load trang thì check tất cả
                            const selectedRadio = document.querySelector('input[name="applyScope"]:checked');
                            if (selectedRadio && selectedRadio.value === 'all') {
                                document.querySelectorAll('.employee-item input[type="checkbox"]').forEach(checkbox => {
                                    checkbox.checked = true;
                                });
                            }
                        });


                        function getSelectedEmployeeIds() {
                            const selectedIds = [];

                            document.querySelectorAll('.employee-item').forEach(item => {
                                const checkbox = item.querySelector('input[type="checkbox"]');
                                if (checkbox && checkbox.checked) {
                                    const empId = item.getAttribute('data-employee-id');
                                    if (empId) {
                                        selectedIds.push(empId);
                                    }
                                }
                            });

                            return selectedIds;
                        }

                        function saveSalaryRecord() {
                            const paymentPeriod = document.getElementById('paymentPeriod').value;
                            let startDate;
                            let endDate;
                            if (paymentPeriod === 'custom') {
                                startDate = document.getElementById('startDate').value;
                                endDate = document.getElementById('endDate').value;
                            } else {
                                const startDateStr = new Date(document.getElementById('workPeriodDropdown').value);
                                startDate = startDateStr.toLocaleDateString('en-CA');
                                endDate = new Date(startDateStr.getFullYear(), startDateStr.getMonth() + 1, 0).toLocaleDateString('en-CA');
                            }

                            console.log("Payment Period:", paymentPeriod);
                            console.log("Start Date:", startDate);
                            console.log("End Date:", endDate);
                            //Lấy danh sách empcheckedloyeeId được                     
                            const selectedEmployeeIds = getSelectedEmployeeIds();

                            if (!startDate || !endDate) {
                                alert('Vui lòng chọn kỳ việc làm');
                                return;
                            }
                            if (new Date(startDate) > new Date(endDate)) {
                                alert('Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!');
                                return;
                            }
                            if (selectedEmployeeIds.length === 0) {
                                alert('Vui lòng chọn ít nhất một nhân viên!');
                                return;
                            }
                            console.log('Danh sách employeeId được chọn:', selectedEmployeeIds);
                            // Gửi dữ liệu bằng fetch
                            fetch('SavePayrollServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    action: 'saveSalaryRecord',
                                    period: paymentPeriod,
                                    startDate: startDate, //2025-07-01
                                    endDate: endDate, //2025-07-31
                                    employeeIds: JSON.stringify(selectedEmployeeIds) // gửi danh sách id
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã tạo bảng lương mới thành công!');
                                            closeModal();
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


                        function exportData() {
                            const csvContent = "data:text/csv;charset=utf-8," +
                                    "Mã,Tên,Kỳ hạn trả,Kỳ làm việc,Tổng lương,Đã trả nhân viên,Còn cần trả,Trạng thái\n" +
                                    filteredData.map(item =>
                                            ``
                                            ).join('\n');

                            const link = document.createElement('a');
                            link.setAttribute('href', encodeURI(csvContent));
                            link.setAttribute('download', 'bang_luong.csv');
                            link.click();
                        }

                        // Event listeners
                        document.getElementById('searchInput').addEventListener('input', searchData);



                        // Employee search functionality
                        document.getElementById('employeeSearchInput').addEventListener('input', function () {
                            const searchTerm = this.value.toLowerCase();
                            const employeeItems = document.querySelectorAll('.employee-item');

                            employeeItems.forEach(item => {
                                const name = item.querySelector('.employee-name').textContent.toLowerCase();
                                const id = item.querySelector('.employee-id').textContent.toLowerCase();

                                if (name.includes(searchTerm) || id.includes(searchTerm)) {
                                    item.style.display = 'flex';
                                } else {
                                    item.style.display = 'none';
                                }
                            });
                        });

                        // Close modal when clicking outside
                        document.getElementById('addSalaryModal').addEventListener('click', function (e) {
                            if (e.target === this) {
                                closeModal();
                            }
                        });

        </script>
        <script>
            let allRows = [];
            let filteredRows = [];
            let currentPage = 1;
            let itemsPerPage = 5; // Số dòng mỗi trang

// Tải dữ liệu ban đầu từ DOM
            function loadTableRows() {
                const tbody = document.getElementById('salaryTableBody');
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
                const tbody = document.getElementById('salaryTableBody');
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

