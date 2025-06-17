<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sổ Quỹ Tiền Mặt</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            background-color: #f5f5f5;
            color: #333;
        }

        .container1 {
            max-width: 90%;
            margin: 0 auto;
            background: white;
            min-height: 100vh;
        }

        /* Để lại chỗ cho header và sidebar1 của bạn */
        .main-wrapper {
            margin-top: 60px; /* Điều chỉnh theo chiều cao header của bạn */
            margin-left: 250px; /* Điều chỉnh theo chiều rộng sidebar1 của bạn */
        }

        .header1 {
            background: white;
            padding: 15px 20px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: relative;
        }

        .header1 h1 {
            font-size: 24px;
            font-weight: 600;
            color: #333;
        }

        .header1-right {
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 6px;
            border: none;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .btn-primary {
            background: #28a745;
            color: white;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .main-content {
            display: flex;
            height: calc(100vh - 135px); /* Điều chỉnh để phù hợp với header nhỏ hơn */
        }

        .sidebar1 {
            width: 280px;
            background: #f8f9fa;
            border-left: 1px solid #e0e0e0;
            padding: 20px;
            order: 2;
        }

        .sidebar1-section {
            margin-bottom: 30px;
        }

        .sidebar1-title {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 15px;
            color: #333;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .sidebar1-item {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            cursor: pointer;
        }

        .sidebar1-item input[type="radio"] {
            margin-right: 10px;
        }

        .sidebar1-item.active {
            color: #28a745;
            font-weight: 500;
        }

        .content-area {
            flex: 1;
            display: flex;
            flex-direction: column;
            order: 1;
        }

        .search-bar {
            padding: 20px;
            background: white;
            border-bottom: 1px solid #e0e0e0;
        }

        .search-input {
            width: 300px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
        }

        .summary-bar {
            padding: 20px;
            background: #f8f9fa;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .summary-item {
            text-align: center;
        }

        .summary-label {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
        }

        .summary-value {
            font-size: 18px;
            font-weight: 600;
        }

        .summary-value.positive {
            color: #007bff;
        }

        .summary-value.negative {
            color: #dc3545;
        }

        .table-container1 {
            flex: 1;
            overflow: auto;
            padding: 20px;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .table th {
            background: #f8f9fa;
            padding: 15px;
            text-align: left;
            font-weight: 600;
            border-bottom: 1px solid #e0e0e0;
            font-size: 14px;
            color: #333;
        }

        .table td {
            padding: 15px;
            border-bottom: 1px solid #f0f0f0;
            font-size: 14px;
        }

        .table tr:hover {
            background: #f8f9fa;
        }

        .checkbox-cell {
            width: 40px;
            text-align: center;
        }

        .amount-positive {
            color: #28a745;
            font-weight: 600;
        }

        .amount-negative {
            color: #dc3545;
            font-weight: 600;
        }

        .pagination {
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            border-top: 1px solid #e0e0e0;
        }

        .pagination button {
            padding: 8px 12px;
            border: 1px solid #ddd;
            background: white;
            border-radius: 4px;
            cursor: pointer;
        }

        .pagination button.active {
            background: #28a745;
            color: white;
            border-color: #28a745;
        }

        .pagination button:hover:not(.active) {
            background: #f8f9fa;
        }

        .sidebar1-chevron {
            transform: rotate(180deg);
            font-size: 12px;
            color: #666;
        }

        .support-chat {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background: #007bff;
            color: white;
            padding: 12px 20px;
            border-radius: 25px;
            cursor: pointer;
            box-shadow: 0 4px 12px rgba(0,123,255,0.3);
        }

        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .modal.show {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .modal-content {
            background: white;
            border-radius: 12px;
            width: 90%;
            max-width: 800px;
            max-height: 90vh;
            overflow-y: auto;
            position: relative;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }

        .modal-header1 {
            padding: 20px 30px;
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
            color: #999;
            cursor: pointer;
            padding: 0;
            width: 30px;
            height: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .close-btn:hover {
            color: #666;
        }

        .modal-body {
            padding: 30px;
        }

        .form-row {
            display: flex;
            gap: 30px;
            margin-bottom: 25px;
        }

        .form-group {
            flex: 1;
        }

        .form-group.full-width {
            flex: 2;
        }

        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #333;
            font-size: 14px;
        }

        .form-input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.2s;
        }

        .form-input:focus {
            outline: none;
            border-color: #28a745;
            box-shadow: 0 0 0 2px rgba(40, 167, 69, 0.1);
        }

        .form-input.auto-generated {
            background: #f8f9fa;
            border-color: #28a745;
            color: #28a745;
        }

        .form-select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            background: white;
            cursor: pointer;
        }

        .form-select:focus {
            outline: none;
            border-color: #28a745;
        }

        .form-textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            min-height: 80px;
            resize: vertical;
        }

        .form-checkbox {
            display: flex;
            align-items: center;
            margin-top: 15px;
        }

        .form-checkbox input {
            margin-right: 8px;
        }

        .form-checkbox label {
            font-size: 14px;
            color: #28a745;
            font-weight: 500;
        }

        .modal-footer {
            padding: 20px 30px;
            border-top: 1px solid #e0e0e0;
            display: flex;
            justify-content: flex-end;
            gap: 15px;
        }

        .btn-modal {
            padding: 10px 20px;
            border-radius: 6px;
            border: none;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            min-width: 100px;
        }

        .btn-save {
            background: #28a745;
            color: white;
        }

        .btn-save-print {
            background: #28a745;
            color: white;
        }

        .btn-cancel {
            background: #6c757d;
            color: white;
        }

        .add-btn {
            background: none;
            border: none;
            color: #28a745;
            font-size: 18px;
            cursor: pointer;
            padding: 5px;
            margin-left: 10px;
        }

        .search-btn {
            background: none;
            border: none;
            color: #6c757d;
            font-size: 16px;
            cursor: pointer;
            padding: 5px;
            margin-left: 10px;
        }

        .datetime-group {
            display: flex;
            gap: 10px;
        }

        .datetime-group .form-input {
            flex: 1;
        }

        .icon-btn {
            background: none;
            border: none;
            color: #6c757d;
            font-size: 16px;
            cursor: pointer;
            padding: 8px;
        }
    </style>
</head>
<body>
       
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern,  html5, responsive">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css" type="text/css"/>
    
    <%@include file="../HeadSideBar/header.jsp" %>
    <%@include file="../HeadSideBar/sidebar.jsp" %>
    
    <div class="main-wrapper">
        <div class="container1">
        <div class="header1">
            <h1>Sổ quỹ tiền mặt</h1>
            <div class="header1-right">
                <button class="btn btn-primary" onclick="openModal('receiptModal')">📝 Lập phiếu thu</button>
                <button class="btn btn-primary" onclick="openModal('paymentModal')">📝 Lập phiếu chi</button>
                <button class="btn btn-secondary">📤 Xuất file</button>
                <button class="btn btn-secondary">☰</button>
            </div>
        </div>

        <div class="main-content">
            <div class="sidebar1">
                <div class="sidebar1-section">
                    <div class="sidebar1-title">
                        Quỹ tiền
                        <span class="sidebar1-chevron">▼</span>
                    </div>
                    <div class="sidebar1-item active">
                        <input type="radio" name="fund" checked>
                        <span>Tiền mặt</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="radio" name="fund">
                        <span>Ngân hàng</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="radio" name="fund">
                        <span>Ví điện tử</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="radio" name="fund">
                        <span>Tổng quỹ</span>
                    </div>
                </div>

                <div class="sidebar1-section">
                    <div class="sidebar1-title">
                        Thời gian
                        <span class="sidebar1-chevron">▼</span>
                    </div>
                    <div class="sidebar1-item active">
                        <input type="radio" name="time" checked>
                        <span>Tháng này</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="radio" name="time">
                        <span>Lựa chọn khác</span>
                    </div>
                </div>

                <div class="sidebar1-section">
                    <div class="sidebar1-title">
                        Loại chứng từ
                        <span class="sidebar1-chevron">▼</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="checkbox">
                        <span>Phiếu thu</span>
                    </div>
                    <div class="sidebar1-item">
                        <input type="checkbox">
                        <span>Phiếu chi</span>
                    </div>
                </div>

                <div class="sidebar1-section">
                    <div class="sidebar1-title">
                        Loại thu chi
                        <span class="sidebar1-chevron">▼</span>
                    </div>
                    <div class="sidebar1-item">
                        <span>Loại thu chi</span>
                    </div>
                </div>
            </div>

            <div class="content-area">
                <div class="search-bar">
                    <input type="text" class="search-input" placeholder="Theo mã phiếu">
                </div>

                <div class="summary-bar">
                    <div class="summary-item">
                        <div class="summary-label">Quỹ đầu kỳ</div>
                        <div class="summary-value">0</div>
                    </div>
                    <div class="summary-item">
                        <div class="summary-label">Tổng thu</div>
                        <div class="summary-value positive">185,265,300</div>
                    </div>
                    <div class="summary-item">
                        <div class="summary-label">Tổng chi</div>
                        <div class="summary-value negative">-185,265,300</div>
                    </div>
                    <div class="summary-item">
                        <div class="summary-label">Tồn quỹ</div>
                        <div class="summary-value">0</div>
                    </div>
                </div>

                <div class="table-container1">
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="checkbox-cell"><input type="checkbox"></th>
                                <th>Mã phiếu</th>
                                <th>Thời gian</th>
                                <th>Loại thu chi</th>
                                <th>Người nộp/nhận</th>
                                <th>Giá trị</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTPN000046</td>
                                <td>15/06/2025 16:10</td>
                                <td>Chi Tiền trả NCC</td>
                                <td>Công ty Cổ phần<br>Thới Trang Việt Danh</td>
                                <td class="amount-negative">-4,478,600</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTHD000046</td>
                                <td>15/06/2025 16:10</td>
                                <td>Thu Tiền khách trả</td>
                                <td>Anh Hoàng - Sài Gòn</td>
                                <td class="amount-positive">4,478,600</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTPN000045</td>
                                <td>14/06/2025 16:09</td>
                                <td>Chi Tiền trả NCC</td>
                                <td>Công ty cổ phần thời<br>trang Thiên Quang</td>
                                <td class="amount-negative">-10,477,200</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTHD000045</td>
                                <td>14/06/2025 16:09</td>
                                <td>Thu Tiền khách trả</td>
                                <td>Tuân - Hà Nội</td>
                                <td class="amount-positive">10,477,200</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTPN000044</td>
                                <td>13/06/2025 16:08</td>
                                <td>Chi Tiền trả NCC</td>
                                <td>Công ty cổ phần thời<br>trang Thiên Quang</td>
                                <td class="amount-negative">-11,074,400</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTHD000044</td>
                                <td>13/06/2025 16:08</td>
                                <td>Thu Tiền khách trả</td>
                                <td>Nguyễn Văn Hải</td>
                                <td class="amount-positive">11,074,400</td>
                            </tr>
                            <tr>
                                <td class="checkbox-cell"><input type="checkbox"></td>
                                <td>TTPN000043</td>
                                <td>12/06/2025 16:07</td>
                                <td>Chi Tiền trả NCC</td>
                                <td>Công ty Thời Trang<br>Việt</td>
                                <td class="amount-negative">-6,446,500</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="pagination">
                    <button>⟨</button>
                    <button>⟨</button>
                    <button class="active">1</button>
                    <button>2</button>
                    <button>3</button>
                    <button>⟩</button>
                    <button>⟩</button>
                    <span style="margin-left: 20px;">Hiện thi 1 - 10 / Tổng số 30 phiếu</span>
                </div>
            </div>
        </div>

        <div class="support-chat">
            💬 1900 6622
        </div>

        <!-- Modal Lập Phiếu Thu -->
        <div id="receiptModal" class="modal">
            <div class="modal-content">
                <div class="modal-header1">
                    <h2 class="modal-title">Lập phiếu thu (tiền mặt)</h2>
                    <button class="close-btn" onclick="closeModal('receiptModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Mã phiếu</label>
                            <input type="text" class="form-input auto-generated" value="Mã phiếu tự động" readonly>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Đối tượng nộp</label>
                            <select class="form-select">
                                <option>Khác</option>
                                <option>Khách hàng</option>
                                <option>Nhà cung cấp</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Thời gian</label>
                            <div class="datetime-group">
                                <input type="text" class="form-input" value="15/06/2025 17:58">
                                <button class="icon-btn">📅</button>
                                <button class="icon-btn">🕐</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Tên người nộp</label>
                            <div style="display: flex; align-items: center;">
                                <input type="text" class="form-input" placeholder="Tìm kiếm">
                                <button class="search-btn">🔍</button>
                                <button class="add-btn">+</button>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Loại thu</label>
                            <div style="display: flex; align-items: center;">
                                <select class="form-select">
                                    <option>--Chọn loại thu--</option>
                                    <option>Thu tiền khách trả</option>
                                    <option>Thu khác</option>
                                </select>
                                <button class="add-btn">+</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Ghi chú</label>
                            <div style="display: flex; align-items: center;">
                                <input type="text" class="form-input">
                                <button class="icon-btn">✏️</button>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Giá trị</label>
                            <input type="number" class="form-input" value="0">
                        </div>
                        <div class="form-group">
                            <div class="form-checkbox">
                                <input type="checkbox" id="businessResult" checked>
                                <label for="businessResult">Hạch toán vào kết quả hoạt động kinh doanh ℹ️</label>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Nhân viên thu</label>
                            <select class="form-select">
                                <option>nghuhfaw</option>
                                <option>Nhân viên khác</option>
                            </select>
                        </div>
                        <div class="form-group">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn-modal btn-save">💾 Lưu</button>
                    <button class="btn-modal btn-save-print">💾 Lưu & In</button>
                    <button class="btn-modal btn-cancel" onclick="closeModal('receiptModal')">🚫 Bỏ qua</button>
                </div>
            </div>
        </div>

        <!-- Modal Lập Phiếu Chi -->
        <div id="paymentModal" class="modal">
            <div class="modal-content">
                <div class="modal-header1">
                    <h2 class="modal-title">Lập phiếu chi (tiền mặt)</h2>
                    <button class="close-btn" onclick="closeModal('paymentModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Mã phiếu</label>
                            <input type="text" class="form-input auto-generated" value="Mã phiếu tự động" readonly>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Đối tượng nhận</label>
                            <select class="form-select">
                                <option>Khác</option>
                                <option>Khách hàng</option>
                                <option>Nhà cung cấp</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Thời gian</label>
                            <div class="datetime-group">
                                <input type="text" class="form-input" value="15/06/2025 17:59">
                                <button class="icon-btn">📅</button>
                                <button class="icon-btn">🕐</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Tên người nhận</label>
                            <div style="display: flex; align-items: center;">
                                <input type="text" class="form-input" placeholder="Tìm kiếm">
                                <button class="search-btn">🔍</button>
                                <button class="add-btn">+</button>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Loại chi</label>
                            <div style="display: flex; align-items: center;">
                                <select class="form-select">
                                    <option>--Chọn loại chi--</option>
                                    <option>Chi tiền trả NCC</option>
                                    <option>Chi khác</option>
                                </select>
                                <button class="add-btn">+</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="form-label">Ghi chú</label>
                            <div style="display: flex; align-items: center;">
                                <input type="text" class="form-input">
                                <button class="icon-btn">✏️</button>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Giá trị</label>
                            <input type="number" class="form-input" value="0">
                        </div>
                        <div class="form-group">
                            <div class="form-checkbox">
                                <input type="checkbox" id="businessResultPayment" checked>
                                <label for="businessResultPayment">Hạch toán vào kết quả hoạt động kinh doanh ℹ️</label>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label class="form-label">Nhân viên chi</label>
                            <select class="form-select">
                                <option>nghuhfaw</option>
                                <option>Nhân viên khác</option>
                            </select>
                        </div>
                        <div class="form-group">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn-modal btn-save">💾 Lưu</button>
                    <button class="btn-modal btn-save-print">💾 Lưu & In</button>
                    <button class="btn-modal btn-cancel" onclick="closeModal('paymentModal')">🚫 Bỏ qua</button>
                </div>
            </div>
        </div>

        <script>
            function openModal(modalId) {
                document.getElementById(modalId).classList.add('show');
            }

            function closeModal(modalId) {
                document.getElementById(modalId).classList.remove('show');
            }

            // Đóng modal khi click bên ngoài
            window.onclick = function(event) {
                if (event.target.classList.contains('modal')) {
                    event.target.classList.remove('show');
                }
            }
        </script>
         <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="<%=path%>/view/assets/js/feather.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/apexchart/apexcharts.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/apexchart/chart-data.js"></script>

        <script src="<%=path%>/view/assets/js/script.js"></script>
        </div>
    </div>
</body>
</html>