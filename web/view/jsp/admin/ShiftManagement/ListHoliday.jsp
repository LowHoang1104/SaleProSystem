<%-- 
    Document   : List_work_schedule
    Created on : Jun 8, 2025, 10:49:26 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            .header {
                text-align: center;
                margin-bottom: 30px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: all 0.3s ease;
                margin: 5px;
            }

            .btn-primary {
                background-color: #007bff;
                color: white;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            .btn-success {
                background-color: #28a745;
                color: white;
            }

            .btn-success:hover {
                background-color: #218838;
            }

            .btn-danger {
                background-color: #dc3545;
                color: white;
            }

            .btn-danger:hover {
                background-color: #c82333;
            }

            .btn-secondary {
                background-color: #6c757d;
                color: white;
            }

            .btn-secondary:hover {
                background-color: #545b62;
            }

            /* Modal styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
                animation: fadeIn 0.3s ease;
                z-index: 9999;

            }

            .modal-content {
                background-color: white;
                margin: 5% auto;
                padding: 0;
                border-radius: 10px;
                width: 90%;
                max-width: 600px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.3);
                animation: slideIn 0.3s ease;
            }

            .modal-header {
                padding: 20px;
                border-bottom: 1px solid #dee2e6;
                display: flex;
                justify-content: space-between;
                align-items: center;
                background-color: #f8f9fa;
                border-radius: 10px 10px 0 0;
            }

            .modal-body {
                padding: 20px;
            }

            .modal-footer {
                padding: 15px 20px;
                border-top: 1px solid #dee2e6;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
                background-color: #f8f9fa;
                border-radius: 0 0 10px 10px;
            }

            .close {
                color: #aaa;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover {
                color: #000;
            }

            /* Form styles */
            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #333;
            }

            .form-control {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
            }

            .form-control:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0,123,255,0.3);
            }

            .checkbox-container {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .checkbox-container input[type="checkbox"] {
                transform: scale(1.2);
            }

            /* Table styles */
            .table-container {
                margin-top: 30px;
                overflow-x: auto;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background: white;
                border-radius: 5px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #28a745;
                color: white;
                font-weight: bold;
            }

            tr:hover {
                background-color: #f5f5f5;
            }

            .status-badge {
                padding: 4px 8px;
                border-radius: 4px;
                font-size: 12px;
                font-weight: bold;
            }

            .status-public {
                background-color: #28a745;
                color: white;
            }

            .status-internal {
                background-color: #6c757d;
                color: white;
            }

            .no-data {
                text-align: center;
                color: #666;
                font-style: italic;
                padding: 20px;
            }

            /* Animations */
            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }

            @keyframes slideIn {
                from {
                    opacity: 0;
                    transform: translateY(-50px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Search box */
            .search-container {
                width: 100%; /* hoặc 600px, 800px, v.v. */
                max-width: 600px;
                margin-bottom: 20px;
                display: flex;
                gap: 10px;
                align-items: center;
            }

            .search-input {
                flex: 1;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .modal-content {
                    width: 95%;
                    margin: 10% auto;
                }

                .search-container {
                    flex-direction: column;
                }

                .search-input {
                    width: 100%;
                }
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
                            <h4>🎉 Quản lý ngày lễ</h4>
                            <h6>Thêm mới và quản lý các ngày lễ trong hệ thống</h6>
                        </div>
                        <div class="search-container">
                            <input type="text" id="searchInput" class="search-input" placeholder="Tìm kiếm ngày lễ theo tên, tháng, năm">
                            <button class="btn btn-primary" onclick="openAddModal()">+ Thêm ngày lễ</button>
                        </div>
                    </div>

                    <!-- Schedule Table -->
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table id="holidayTable">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Ngày</th>
                                            <th>Tên ngày lễ</th>
                                            <th>Loại</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody id="holidayTableBody">
                                        <c:forEach var="holiday" items="${holidays}" varStatus="status">
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td class="holiday-date">${holiday.holidayDate}</td>
                                                <td>${holiday.name}</td>
                                                <td>
                                                    <span class="status-badge ${holiday.isPublish ? 'status-public' : 'status-internal'}">
                                                        ${holiday.isPublish ? 'Toàn quốc' : 'Nội bộ'}
                                                    </span>
                                                </td>
                                                <td>
                                                    <button class="btn btn-primary btn-sm" onclick="editHoliday(${holiday.holidayId}, '${holiday.name}', '${holiday.holidayDate}', ${holiday.isPublish})">Sửa</button>
                                                    <button class="btn btn-danger btn-sm" onclick="deleteHoliday(${holiday.holidayId})">Xóa</button>
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
            <!-- Add/Edit Holiday Modal -->
            <div id="holidayModal" class="modal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 id="modalTitle">Thêm ngày lễ mới</h2>
                        <span class="close" onclick="closeModal()">&times;</span>
                    </div>
                    <div class="modal-body">
                        <form id="holidayForm">
                            <div class="form-group">
                                <label for="holidayDate">Ngày lễ:</label>
                                <input type="date" id="holidayDate" name="holidayDate" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="holidayName">Tên ngày lễ:</label>
                                <input type="text" id="holidayName" name="holidayName" class="form-control" 
                                       placeholder="Ví dụ: Tết Nguyên Đán" required>
                            </div>

                            <div class="form-group">
                                <div class="checkbox-container">
                                    <input type="checkbox" id="isPublicHoliday" name="isPublicHoliday">
                                    <label for="isPublicHoliday">Ngày nghỉ toàn quốc</label>
                                </div>
                                <small style="color: #666; margin-top: 5px; display: block;">
                                    Bỏ chọn nếu chỉ là ngày nghỉ nội bộ
                                </small>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Hủy</button>
                        <button type="button" class="btn btn-success" onclick="saveHoliday()">Lưu</button>
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
        <script>
                            let editingId = null;
                            //Enter thì search                         
                            document.getElementById('searchInput').addEventListener('keydown', function (event) {
                                if (event.key === 'Enter') {
                                    event.preventDefault(); // Ngăn việc reload nếu trong form
                                    searchHoliday(); // Gọi hàm tìm kiếm
                                }
                            });
                            function searchHoliday() {
                                const keyword = document.getElementById('searchInput').value.trim();
                                // Viết logic tìm kiếm ở đây. Ví dụ:
                                console.log("Tìm kiếm ngày lễ với từ khóa:", keyword);
                                window.location.href = 'ListHolidayServlet?search=' + keyword;
                            }

                            // Format ngày tháng
                            function formatDate(dateString) {
                                const date = new Date(dateString);
                                return date.toLocaleDateString('vi-VN', {
                                    day: '2-digit',
                                    month: '2-digit',
                                    year: 'numeric'
                                });
                            }

                            document.addEventListener("DOMContentLoaded", function () {
                                document.querySelectorAll('.holiday-date').forEach(function (td) {
                                    const rawDate = td.textContent.trim();
                                    td.textContent = formatDate(rawDate);
                                });
                            });

                            // Mở modal thêm mới
                            function openAddModal() {
                                editingId = null;
                                document.getElementById('modalTitle').textContent = 'Thêm ngày lễ mới';
                                document.getElementById('holidayForm').reset();
                                document.getElementById('holidayModal').style.display = 'block';
                            }

                            // Mở modal chỉnh sửa
                            function editHoliday(id, name, date, isPublish) {
                                editingId = id;
                                document.getElementById('modalTitle').textContent = 'Chỉnh sửa ngày lễ';
                                document.getElementById('holidayDate').value = date;
                                document.getElementById('holidayName').value = name;
                                document.getElementById('isPublicHoliday').checked = isPublish;
                                document.getElementById('holidayModal').style.display = 'block';
                            }

                            // Đóng modal
                            function closeModal() {
                                document.getElementById('holidayModal').style.display = 'none';
                                editingId = null;
                            }

                            // Lưu ngày lễ
                            function saveHoliday() {
                                const form = document.getElementById('holidayForm');
                                const formData = new FormData(form);
                                const Date = formData.get('holidayDate');
                                const Name = formData.get('holidayName');
                                const isPublish = document.getElementById('isPublicHoliday').checked;
                                // Validate

                                if (!Date || !Name) {
                                    alert('Vui lòng điền đầy đủ thông tin!');
                                    return;
                                }

                                fetch('ListHolidayServlet', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                    body: new URLSearchParams({
                                        action: 'save',
                                        id: editingId,
                                        date: Date,
                                        name: Name,
                                        isPublish: isPublish
                                    })
                                })
                                        .then(response => response.text())
                                        .then(result => {
                                            if (result.includes('success')) {
                                                if (result.includes('add')) {
                                                    alert('Thêm ngày lễ thành công');
                                                } else {
                                                    alert('Chỉnh sửa ngày lễ thành công');
                                                }
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

                            // Xóa ngày lễ
                            function deleteHoliday(id) {

                                Swal.fire({
                                    title: 'Xác nhận xóa ?',
                                    text: `Bạn có chắc chắn muốn xóa ngày lễ này?`,
                                    icon: 'warning',
                                    showCancelButton: true,
                                    confirmButtonColor: '#d33',
                                    cancelButtonColor: '#3085d6',
                                    confirmButtonText: 'Xóa',
                                    cancelButtonText: 'Hủy'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        fetch('ListHolidayServlet', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            body: new URLSearchParams({
                                                action: 'delete',
                                                id: id
                                            })
                                        })
                                                .then(response => response.text())
                                                .then(result => {
                                                    if (result.includes('success')) {
                                                        alert('Xóa ngày lễ thành công');
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


                            // Đóng modal khi click outside
                            window.onclick = function (event) {
                                const modal = document.getElementById('holidayModal');
                                if (event.target === modal) {
                                    closeModal();
                                }
                            };

                            // Xử lý phím Escape
                            document.addEventListener('keydown', function (event) {
                                if (event.key === 'Escape') {
                                    closeModal();
                                }
                            });

        </script>
    </body>
</html>

