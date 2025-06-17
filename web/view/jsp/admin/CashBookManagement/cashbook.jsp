<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                overflow-x: hidden;
            }

            .container1 {
                max-width: 100%;
                margin: 0 auto;
                background: white;
                min-height: 100vh;
            }

            /* Responsive main wrapper */
            .main-wrapper {
                margin-top: 60px;
                margin-left: 0;
                transition: margin-left 0.3s ease;
            }

            /* Desktop layout */
            @media (min-width: 1200px) {
                .main-wrapper {
                    margin-left: 250px;
                }
            }

            .header1 {
                background: white;
                padding: 15px 20px;
                border-bottom: 1px solid #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
                position: relative;
                flex-wrap: wrap;
                gap: 10px;
            }

            .header1 h1 {
                font-size: 24px;
                font-weight: 600;
                color: #333;
                flex-shrink: 0;
            }

            .header1-right {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
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
                white-space: nowrap;
            }

            .btn-primary {
                background: #28a745;
                color: white;
            }

            .btn-secondary {
                background: #6c757d;
                color: white;
            }

            /* Responsive main content */
            .main-content {
                display: flex;
                min-height: calc(100vh - 135px);
                flex-direction: column;
            }

            /* Desktop: horizontal layout */
            @media (min-width: 1024px) {
                .main-content {
                    flex-direction: row;
                }
            }

            /* Responsive sidebar */
            .sidebar1 {
                width: 100%;
                background: #f8f9fa;
                border-bottom: 1px solid #e0e0e0;
                padding: 20px;
                order: 1;
                max-height: 300px;
                overflow-y: auto;
            }

            /* Desktop sidebar */
            @media (min-width: 1024px) {
                .sidebar1 {
                    width: 280px;
                    border-left: 1px solid #e0e0e0;
                    border-bottom: none;
                    order: 2;
                    max-height: none;
                    flex-shrink: 0;
                }
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

            .sidebar1-item input[type="radio"], 
            .sidebar1-item input[type="checkbox"] {
                margin-right: 10px;
            }

            .sidebar1-item.active {
                color: #28a745;
                font-weight: 500;
            }

            /* Responsive content area */
            .content-area {
                flex: 1;
                display: flex;
                flex-direction: column;
                order: 2;
                min-width: 0; /* Important for flex items */
            }

            @media (min-width: 1024px) {
                .content-area {
                    order: 1;
                }
            }

            .search-bar {
                padding: 20px;
                background: white;
                border-bottom: 1px solid #e0e0e0;
            }

            .search-input {
                width: 100%;
                max-width: 300px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 14px;
            }

            /* Responsive summary bar */
            .summary-bar {
                padding: 20px;
                background: #f8f9fa;
                border-bottom: 1px solid #e0e0e0;
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
                gap: 15px;
                text-align: center;
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

            /* Responsive table container */
            .table-container1 {
                flex: 1;
                overflow: auto;
                padding: 20px;
            }

            .table {
                width: 100%;
                min-width: 800px; /* Minimum width for table */
                border-collapse: collapse;
                background: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .table th {
                background: #f8f9fa;
                padding: 12px 8px;
                text-align: left;
                font-weight: 600;
                border-bottom: 1px solid #e0e0e0;
                font-size: 13px;
                color: #333;
                white-space: nowrap;
            }

            .table td {
                padding: 12px 8px;
                border-bottom: 1px solid #f0f0f0;
                font-size: 13px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 150px;
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
                flex-wrap: wrap;
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
                z-index: 1000;
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
                padding: 20px;
            }

            .modal-content {
                background: white;
                border-radius: 12px;
                width: 100%;
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
                flex-wrap: wrap;
                gap: 10px;
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
                flex-shrink: 0;
            }

            .close-btn:hover {
                color: #666;
            }

            .modal-body {
                padding: 30px;
            }

            /* Responsive form layout */
            .form-row {
                display: flex;
                gap: 30px;
                margin-bottom: 25px;
                flex-wrap: wrap;
            }

            .form-group {
                flex: 1;
                min-width: 250px;
            }

            .form-group.full-width {
                flex: 2;
                min-width: 100%;
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
                flex-wrap: wrap;
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
                flex-wrap: wrap;
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
                flex-shrink: 0;
            }

            .search-btn {
                background: none;
                border: none;
                color: #6c757d;
                font-size: 16px;
                cursor: pointer;
                padding: 5px;
                margin-left: 10px;
                flex-shrink: 0;
            }

            .datetime-group {
                display: flex;
                gap: 10px;
                align-items: center;
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
                flex-shrink: 0;
            }

            /* Mobile specific styles */
            @media (max-width: 768px) {
                .header1 {
                    padding: 10px 15px;
                }
                
                .header1 h1 {
                    font-size: 20px;
                }
                
                .btn {
                    padding: 6px 12px;
                    font-size: 12px;
                }
                
                .sidebar1 {
                    padding: 15px;
                }
                
                .table-container1 {
                    padding: 15px;
                }
                
                .modal-body {
                    padding: 20px;
                }
                
                .form-row {
                    gap: 15px;
                }
                
                .form-group {
                    min-width: 100%;
                }
                
                .modal-footer {
                    padding: 15px 20px;
                    justify-content: center;
                }
            }

            /* Tablet specific styles */
            @media (min-width: 769px) and (max-width: 1023px) {
                .main-content {
                    flex-direction: column;
                }
                
                .sidebar1 {
                    order: 1;
                    width: 100%;
                    max-height: 250px;
                }
                
                .content-area {
                    order: 2;
                }
            }

            /* Hide/show elements based on screen size */
            .mobile-hidden {
                display: none;
            }

            @media (min-width: 768px) {
                .mobile-hidden {
                    display: block;
                }
            }

            .desktop-hidden {
                display: block;
            }

            @media (min-width: 1024px) {
                .desktop-hidden {
                    display: none;
                }
            }

            /* Toggle button for mobile sidebar */
            .sidebar-toggle {
                display: none;
                background: #28a745;
                color: white;
                border: none;
                padding: 8px 12px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
            }

            @media (max-width: 1023px) {
                .sidebar-toggle {
                    display: block;
                }
                
                .sidebar1.collapsed {
                    display: none;
                }
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
                                <div class="summary-label">Tổng thu</div>
                                <div class="summary-value positive">0</div>
                            </div>
                            <div class="summary-item">
                                <div class="summary-label">Tổng chi</div>
                                <div class="summary-value negative">0</div>
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
                                        <th>TransactionID</th>
                                        <th>FundID</th>
                                        <th>TransactionType</th>
                                        <th>Amount</th>
                                        <th>Description</th>
                                        <th>ReferenceType</th>
                                        <th>ReferencedID</th>
                                        <th>TransactionDate</th>
                                        <th>CreatedBy</th>
                                        <th>ApprovedBy</th>
                                        <th>Status</th>
                                        <th>Notes</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${data}" var="item">
                                        <tr>
                                            <td>${item.getTransactionID()}</td>
                                            <td>${item.getFundID()}</td>
                                            <td>${item.getTransactionType()}</td>
                                            <td><fmt:formatNumber value="${item.getAmount()}" type="number" pattern="#,###"/> đ</td>
                                            <td>${item.getDescription()}</td>
                                            <td>${item.getReferenceType()}</td>
                                            <td>${item.getReferenceID()}</td>
                                            <td>${item.getTransactionDate()}</td>
                                            <td>${item.getCreatedBy()}</td>
                                            <td>${item.getApprovedBy()}</td>
                                            <td>${item.getStatus()}</td>
                                            <td>${item.getNotes()}</td>                                        
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

<!--                        <div class="pagination">
                            <button>⟨</button>
                            <button>⟨</button>
                            <button class="active">1</button>
                            <button>2</button>
                            <button>3</button>
                            <button>⟩</button>
                            <button>⟩</button>
                            <span style="margin-left: 20px;">Hiện thi 1 - 10 / Tổng số 30 phiếu</span>
                        </div>-->
                    </div>
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
                                    <label class="form-label">Người nộp</label>
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
                                    <label class="form-label">Người thu</label>
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
                    window.onclick = function (event) {
                        if (event.target.classList.contains('modal')) {
                            event.target.classList.remove('show');
                        }
                    }
                     function formatCurrency(amount) {
                        return new Intl.NumberFormat('vi-VN').format(amount);
                    }

                    // Hàm tính tổng thu chi
                    function calculateTotals() {
                        const table = document.querySelector('.table');
                        const rows = table.querySelectorAll('tbody tr');
                        
                        let totalIncome = 0;
                        let totalExpense = 0;

                        rows.forEach(row => {
                            const cells = row.querySelectorAll('td');
                            const amount = parseFloat(cells[3].textContent.replace(/,/g, '')) || 0;
                            const type = cells[2].textContent.trim();

                            if (type === 'Income') {
                                totalIncome += amount;
                            } else if (type === 'Expense') {
                                totalExpense += amount;
                            }
                        });

                        // Cập nhật UI
                        document.querySelector('.summary-value.positive').textContent = formatCurrency(totalIncome);
                        document.querySelector('.summary-value.negative').textContent = '-' + formatCurrency(totalExpense);
                        
                        // Tính tồn quỹ
                        const finalBalance =  totalIncome - totalExpense;
                        document.querySelector('.summary-item:last-child .summary-value').textContent = formatCurrency(finalBalance);
                    }

                    // Gọi hàm tính toán khi trang load xong
                    document.addEventListener('DOMContentLoaded', function() {
                        calculateTotals();
                    });

                    // Thêm event listener cho các radio button trong sidebar
                    document.querySelectorAll('.sidebar1-item input[type="radio"]').forEach(radio => {
                        radio.addEventListener('change', function() {
                            // Có thể thêm logic lọc dữ liệu ở đây
                            calculateTotals();
                        });
                    });
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