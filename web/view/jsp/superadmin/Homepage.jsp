<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Quản Lý Shop Owners</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f5f5;
                color: #333;
            }

            .sidebar {
                position: fixed;
                left: 0;
                top: 0;
                width: 250px;
                height: 100vh;
                background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 20px;
                box-shadow: 2px 0 10px rgba(0,0,0,0.1);
            }

            .sidebar h2 {
                margin-bottom: 30px;
                text-align: center;
                font-size: 24px;
            }

            .nav-item {
                display: flex;
                align-items: center;
                padding: 15px 10px;
                margin: 10px 0;
                border-radius: 8px;
                cursor: pointer;
                transition: all 0.3s ease;
                text-decoration: none;
                color: white;
            }

            .nav-item:hover {
                background: rgba(255,255,255,0.2);
                transform: translateX(5px);
            }

            .nav-item.active {
                background: rgba(255,255,255,0.3);
            }

            .nav-item i {
                margin-right: 10px;
                font-size: 18px;
            }

            .main-content {
                margin-left: 250px;
                padding: 20px;
                min-height: 100vh;
            }

            .header {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }

            .stats-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
                margin-bottom: 30px;
            }

            .stat-card {
                background: white;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                border-left: 5px solid;
                transition: transform 0.3s ease;
            }

            .stat-card:hover {
                transform: translateY(-5px);
            }

            .stat-card.total {
                border-left-color: #3b82f6;
            }
            .stat-card.new {
                border-left-color: #10b981;
            }
            .stat-card.blocked {
                border-left-color: #ef4444;
            }

            .stat-number {
                font-size: 36px;
                font-weight: bold;
                margin-bottom: 10px;
            }

            .stat-label {
                color: #666;
                font-size: 14px;
            }

            .chart-container {
                background: white;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                margin-bottom: 20px;
                position: relative; /* Thêm để canvas hoạt động tốt hơn */
                height: 350px; /* Có thể cho một chiều cao cụ thể */
            }

            .chart-title {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 20px;
                color: #333;
            }

            .charts-grid {
                display: grid;
                grid-template-columns: 1fr 2fr;
                gap: 20px;
                margin-bottom: 30px;
            }

            .page {
                display: none;
            }

            .page.active {
                display: block;
            }

            .table-container {
                background: white;
                border-radius: 12px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow-x: auto; /* SỬA Ở ĐÂY */
            }

            .table-header {
                padding: 20px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .search-box {
                display: flex;
                align-items: center;
                background: #f8f9fa;
                border-radius: 8px;
                padding: 10px 15px;
                width: 300px;
            }

            .search-box input {
                border: none;
                background: none;
                outline: none;
                flex: 1;
                margin-left: 10px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 500;
                transition: all 0.3s ease;
                display: inline-flex;
                align-items: center;
                gap: 8px;
            }

            .btn-primary {
                background: #3b82f6;
                color: white;
            }

            .btn-primary:hover {
                background: #2563eb;
                transform: translateY(-2px);
            }

            .btn-success {
                background: #10b981;
                color: white;
                padding: 6px 12px;
                font-size: 12px;
            }

            .btn-danger {
                background: #ef4444;
                color: white;
                padding: 6px 12px;
                font-size: 12px;
            }

            .btn-warning {
                background: #f59e0b;
                color: white;
                padding: 6px 12px;
                font-size: 12px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                min-width: 800px; /* Đặt chiều rộng tối thiểu cho bảng để không bị co quá mức */
            }

            th, td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #eee;
                white-space: nowrap; /* Ngăn text xuống dòng đột ngột */
            }

            th {
                background: #f8f9fa;
                font-weight: 600;
                color: #333;
            }

            .status-badge {
                padding: 4px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: 500;
            }

            .status-active {
                background: #dcfce7;
                color: #166534;
            }

            .status-inactive {
                background: #fee2e2;
                color: #991b1b;
            }

            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background: rgba(0,0,0,0.5);
            }

            .modal-content {
                background: white;
                margin: 5% auto;
                padding: 0;
                border-radius: 12px;
                width: 90%;
                max-width: 500px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            }

            .modal-header {
                padding: 20px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .modal-body {
                padding: 20px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
                color: #333;
            }

            .form-group input, .form-group select {
                width: 100%;
                padding: 12px;
                border: 2px solid #e5e7eb;
                border-radius: 8px;
                font-size: 14px;
                transition: border-color 0.3s ease;
            }

            .form-group input:focus, .form-group select:focus {
                outline: none;
                border-color: #3b82f6;
            }

            .close {
                color: #aaa;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover {
                color: #333;
            }

            .action-buttons {
                display: flex;
                gap: 8px;
            }

            @media (max-width: 768px) {
                .sidebar {
                    transform: translateX(-100%);
                    transition: transform 0.3s ease;
                }

                .main-content {
                    margin-left: 0;
                }

                .charts-grid {
                    grid-template-columns: 1fr;
                }

                .stats-grid {
                    grid-template-columns: 1fr;
                }
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2>🏪 Admin Panel</h2>
            <div class="nav-item active" onclick="showPage('dashboard')">
                <i>🏠</i>
                <span>Dashboard</span>
            </div>
            <div class="nav-item" onclick="showPage('shopowners')">
                <i>👥</i>
                <span>Quản Lý Shop Owners</span>
            </div>
        </div>

        <div class="main-content">
            <!-- Dashboard Page -->
            <div id="dashboard" class="page active">
                <div class="header">
                    <h1>Dashboard - Tổng Quan</h1>
                    <p>Thống kê tổng quan về các shop owners</p>
                </div>

                <div class="stats-grid">
                    <div class="stat-card total">
                        <div class="stat-number" id="totalShops">5</div>
                        <div class="stat-label">Tổng số Shop</div>
                    </div>
                    <div class="stat-card new">
                        <div class="stat-number" id="newShops">5</div>
                        <div class="stat-label">Shop mới tháng này</div>
                    </div>
                    <div class="stat-card blocked">
                        <div class="stat-number" id="blockedShops">1</div>
                        <div class="stat-label">Shop bị khóa</div>
                    </div>
                </div>
                <br>
                <div class="charts-grid">
                    <div class="chart-container">
                        <div class="chart-title">Phân loại theo trạng thái</div>
                        <!-- SỬA Ở ĐÂY: Xóa width và height -->
                        <canvas id="pieChart"></canvas>
                    </div>
                    <div class="chart-container">
                        <div class="chart-title">Số shop mới theo từng tháng</div>
                        <!-- SỬA Ở ĐÂY: Xóa width và height -->
                        <canvas id="barChart"></canvas>
                    </div>
                </div>
            </div>

            <!-- Shop Owners Page -->
            <div id="shopowners" class="page">
                <div class="header">
                    <h1>Quản Lý Shop Owners</h1>
                    <p>Danh sách và quản lý các shop owners</p>
                </div>

                <div class="table-container">
                    <div class="table-header">
                        <div class="search-box">
                            <i>🔍</i>
                            <input type="text" id="searchInput" placeholder="Tìm kiếm theo tên shop, chủ shop, email...">
                        </div>
                        <button class="btn btn-primary" onclick="document.getElementById('create').style.display = 'block'">
                            <i>➕</i> Thêm Shop Owner
                        </button>
                    </div>

                    <table id="shopOwnersTable">
                        <thead>
                            <tr>
                                <th>ID</th
                                <th>Tên Shop</th>
                                <th>Chủ Shop</th>
                                <th>Email</th>
                                <th>Số Điện Thoại</th>
                                <th>Trạng Thái</th>
                                <th>Ngày Tạo</th>
                                <th>Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                        <form action="ShopOwnerController" ></form>
                        <c:forEach items="${sessionScope.listshopowner}" var="items">
                            <tr>
                                <th>${items.getId()}</th>
                                <th>${items.getShopName()}</th>
                                <th>${items.getOwnerName()}</th>
                                <th>${items.getEmail()}</th>
                                <th>${items.getPhone()}</th>
                                <th><c:if test="${items.getIsActive() eq '1'}">Hoạt động</c:if><c:if test="${items.getIsActive() eq '0'}">Không Hoạt động</c:if></th>
                                    <th><div class="action-buttons">
                                            <button class="btn btn-warning" 
                                                    onclick="openform('update',
                                            ${items.getId()},
                                                                    '${items.getShopName()}',
                                                                    '${items.getOwnerName()}',
                                                                    '${items.getEmail()}',
                                                                    '${items.getPhone()}',
                                                                    '${items.getPasswordHash()}',
                                            ${items.getIsActive()})">✏️</button>
                                        <button class="btn btn-danger" onclick="deleteShop(${shop.id})">🗑️</button>
                                    </div></th>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div id="create" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 id="modalTitle">Thêm Shop Owner</h3>
                    <span class="close" onclick="closeform('create')">×</span>
                </div>
                <div class="modal-body">
                    <div id='mess1' class="error-message" style="color: red"></div>
                    <div class="form-group">
                        <label>Tên Shop:</label>
                        <input type="text" id="shopName1" required>
                    </div>
                    <div class="form-group">
                        <label>Tên Chủ Shop:</label>
                        <input type="text" id="ownerName1" required>
                    </div>
                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" id="email1" required>
                    </div>
                    <div class="form-group">
                        <label>Số Điện Thoại:</label>
                        <input type="text" id="phone1">
                    </div>
                    <div class="form-group">
                        <label>Mật Khẩu:</label>
                        <input type="text" id="password1" required>
                    </div>
                    <div class="form-group">
                        <label>Trạng Thái:</label>
                        <select id="isActive1">
                            <option value="1">Hoạt động</option>
                            <option value="0">Bị khóa</option>
                        </select>
                    </div>
                    <div style="text-align: right; margin-top: 20px;">
                        <button type="button" class="btn" onclick="closeform('create')"  style="background: #6b7280; color: white; margin-right: 10px;">Hủy</button>
                        <button type="button" onclick="add()" class="btn btn-primary">Lưu</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="update" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 id="modalTitle">Update Shop Owner</h3>
                    <span class="close" onclick="closeform('update')">×</span>
                </div>
                <div class="modal-body">
                    <div id='mess2' class="error-message" style="color: red"></div>

                    <div class="form-group">
                        <label>Tên Shop: <span style="color: red;">*</span></label>
                        <input type="text" id="shopName2" name="shopName" required>
                        <div class="error-message" id="shopNameError"></div>
                    </div>

                    <div class="form-group">
                        <label>Tên Chủ Shop: <span style="color: red;">*</span></label>
                        <input type="text" id="ownerName2" name="ownerName" required>
                        <div class="error-message" id="ownerNameError"></div>
                    </div>

                    <div class="form-group">
                        <label>Email: <span style="color: red;">*</span></label>
                        <input type="email" id="email2" name="email" required>
                        <div class="error-message" id="emailError"></div>
                    </div>

                    <div class="form-group">
                        <label>Số Điện Thoại: <span style="color: red;">*</span></label>
                        <input type="text" id="phone2" name="phone" required>
                        <div class="error-message" id="phoneError"></div>
                    </div>

                    <div class="form-group" id="passwordGroup">
                        <label>Mật Khẩu: <span style="color: red;">*</span></label>
                        <input type="text" id="password2" name="password">
                        <div class="error-message" id="passwordError"></div>
                    </div>

                    <div class="form-group">
                        <label>Trạng Thái:</label>
                        <select id="isActive2" name="isActive">
                            <option value="1">Hoạt động</option>
                            <option value="0">Bị khóa</option>
                        </select>
                    </div>

                    <div style="text-align: right; margin-top: 20px;">
                        <button type="button" class="btn" onclick="closeform('update')" style="background: #6b7280; color: white; margin-right: 10px;">Hủy</button>
                        <button type="button" class="btn btn-primary" onclick="update()" id="submitBtn">Lưu</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                            // Navigation
                            var idshopowner = null;
                            function showPage(pageId) {
                                // Hide all pages
                                const pages = document.querySelectorAll('.page');
                                pages.forEach(page => page.classList.remove('active'));

                                // Show selected page
                                document.getElementById(pageId).classList.add('active');

                                // Update nav items
                                const navItems = document.querySelectorAll('.nav-item');
                                navItems.forEach(item => item.classList.remove('active'));
                                event.target.closest('.nav-item').classList.add('active');
                            }
                            function closeform(idform) {
                                document.getElementById(idform).style.display = 'none';
                                clearForm(idform);
                            }
                            function openform(idform, idShop, shopName, ownerName, email, phone, password, isActive) {
                                document.getElementById('shopName2').value = shopName;
                                document.getElementById('ownerName2').value = ownerName;
                                document.getElementById('email2').value = email;
                                document.getElementById('phone2').value = phone;
                                document.getElementById('password2').value = password;
                                document.getElementById('isActive2').value = isActive;
                                document.getElementById(idform).style.display = 'block';
                                idshopowner = idShop;

                            }
                            function add() {
                                var shopName1 = document.getElementById('shopName1').value;
                                var ownerName1 = document.getElementById('ownerName1').value;
                                var email1 = document.getElementById('email1').value;
                                var phone1 = document.getElementById('phone1').value;
                                var password1 = document.getElementById('password1').value;
                                var isActive1 = document.getElementById('isActive1').value;
                                $.ajax({
                                    type: 'POST',
                                    url: '/Mg2/SuperAdminController',
                                    data: {op: 'addShopOwner', shopName: shopName1, ownerName: ownerName1, email: email1, phone: phone1, password: password1, isActive: isActive1},
                                    success: function (result) {
                                        if (result === "OK") {
                                            location.href = '/Mg2/SuperAdminController';
                                        } else {
                                            document.getElementById('mess1').innerHTML = result;
                                        }
                                    }
                                });
                            }
                            function update() {
                                var shopName2 = document.getElementById('shopName2').value;
                                var ownerName2 = document.getElementById('ownerName2').value;
                                var email2 = document.getElementById('email2').value;
                                var phone2 = document.getElementById('phone2').value;
                                var password2 = document.getElementById('password2').value;
                                var isActive2 = document.getElementById('isActive2').value;
                                $.ajax({
                                    type: 'POST',
                                    url: '/Mg2/SuperAdminController',
                                    data: {op: 'updateShopOwner', id: idshopowner, shopName: shopName2, ownerName: ownerName2, email: email2, phone: phone2, password: password2, isActive: isActive2},
                                    success: function (result) {
                                        if (result === "OK") {
                                            location.href = '/Mg2/SuperAdminController';
                                        } else {
                                            document.getElementById('mess2').innerHTML = result;
                                        }
                                    }
                                });
                            }


                            function clearForm(modalId) {
                                const modal = document.getElementById(modalId);
                                const inputs = modal.querySelectorAll('input, select');
                                inputs.forEach(input => {
                                    if (input.type !== 'submit' && input.type !== 'button') {
                                        input.value = '';
                                    }
                                });
                                const errorMessages = modal.querySelectorAll('.error-message');
                                errorMessages.forEach(msg => msg.innerHTML = '');
                            }

        </script>
    </body>
</html>