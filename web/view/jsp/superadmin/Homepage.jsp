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

        .stat-card.total { border-left-color: #3b82f6; }
        .stat-card.new { border-left-color: #10b981; }
        .stat-card.blocked { border-left-color: #ef4444; }

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
                    <button class="btn btn-primary" onclick="openModal()">
                        <i>➕</i> Thêm Shop Owner
                    </button>
                </div>

                <table id="shopOwnersTable">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên Shop</th>
                            <th>Chủ Shop</th>
                            <th>Email</th>
                            <th>Số Điện Thoại</th>
                            <th>Trạng Thái</th>
                            <th>Ngày Tạo</th>
                            <th>Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody id="shopOwnersTableBody">
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div id="shopOwnerModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">Thêm Shop Owner</h3>
                <span class="close" onclick="closeModal()">×</span>
            </div>
            <div class="modal-body">
                <form id="shopOwnerForm">
                    <div class="form-group">
                        <label>Tên Shop:</label>
                        <input type="text" id="shopName" required>
                    </div>
                    <div class="form-group">
                        <label>Tên Chủ Shop:</label>
                        <input type="text" id="ownerName" required>
                    </div>
                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" id="email" required>
                    </div>
                    <div class="form-group">
                        <label>Số Điện Thoại:</label>
                        <input type="text" id="phone">
                    </div>
                    <div class="form-group">
                        <label>Mật Khẩu:</label>
                        <input type="password" id="password" required>
                    </div>
                    <div class="form-group">
                        <label>Trạng Thái:</label>
                        <select id="isActive">
                            <option value="true">Hoạt động</option>
                            <option value="false">Bị khóa</option>
                        </select>
                    </div>
                    <div style="text-align: right; margin-top: 20px;">
                        <button type="button" class="btn" onclick="closeModal()" style="background: #6b7280; color: white; margin-right: 10px;">Hủy</button>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Sample data
        let shopOwners = [
            {
                id: 1,
                shopName: 'Shop',
                ownerName: 'Nguyễn Văn Chú',
                email: 'owner@myfashionshop.com',
                phone: '0812345678',
                passwordHash: '$2b$10$encrypted_password_here',
                isActive: true,
                createdAt: '2025-06-10 13:52:22.100'
            },
            {
                id: 2,
                shopName: 'Shop2',
                ownerName: 'Nguyễn Văn Chú 2',
                email: 'owner2@myfashionshop.com',
                phone: 'NULL',
                passwordHash: '$2b$10$encrypted_password_here',
                isActive: true,
                createdAt: '2025-06-10 13:52:22.717'
            },
            {
                id: 3,
                shopName: 'Shopa',
                ownerName: 'Phạm yến nhi',
                email: 'chai255204@gmail.com',
                phone: '03737883554',
                passwordHash: 'TG9uZzEyMzRA',
                isActive: true,
                createdAt: '2025-06-10 13:53:19.423'
            },
            {
                id: 4,
                shopName: 'Shopc',
                ownerName: 'Phạm yến nhu',
                email: 'chai255204@gmail.com',
                phone: '03737883552',
                passwordHash: 'TG9uZzEyMzRA',
                isActive: true,
                createdAt: '2025-06-10 13:55:37.637'
            },
            {
                id: 5,
                shopName: 'Shopd',
                ownerName: 'Nguyen van b',
                email: 'chaa255204@gmail.com',
                phone: '0373788311',
                passwordHash: 'TG9uZzEyMzRA',
                isActive: false,
                createdAt: '2025-06-10 14:05:09.407'
            }
        ];

        let editingId = null;
        let pieChart = null;
        let barChart = null;

        // Navigation
        function showPage(pageId) {
            // Hide all pages
            document.querySelectorAll('.page').forEach(page => {
                page.classList.remove('active');
            });
            
            // Remove active class from nav items
            document.querySelectorAll('.nav-item').forEach(item => {
                item.classList.remove('active');
            });
            
            // Show selected page
            document.getElementById(pageId).classList.add('active');
            
            // Add active class to clicked nav item
            event.target.closest('.nav-item').classList.add('active');
            
            if (pageId === 'dashboard') {
                updateDashboard();
            } else if (pageId === 'shopowners') {
                renderTable();
            }
        }

        // Dashboard functions
        function updateDashboard() {
            const totalShops = shopOwners.length;
            const currentMonth = new Date().getMonth();
            const newShops = shopOwners.filter(function(shop) {
                return new Date(shop.createdAt).getMonth() === currentMonth;
            }).length;
            const blockedShops = shopOwners.filter(function(shop) {
                return !shop.isActive;
            }).length;

            document.getElementById('totalShops').textContent = totalShops;
            document.getElementById('newShops').textContent = newShops;
            document.getElementById('blockedShops').textContent = blockedShops;

            updateCharts();
        }

        function updateCharts() {
            const activeShops = shopOwners.filter(function(shop) {
                return shop.isActive;
            }).length;
            const inactiveShops = shopOwners.filter(function(shop) {
                return !shop.isActive;
            }).length;

            // Pie Chart
            const pieCtx = document.getElementById('pieChart').getContext('2d');
            if (pieChart) {
                pieChart.destroy();
            }
            pieChart = new Chart(pieCtx, {
                type: 'pie',
                data: {
                    labels: ['Shop hoạt động', 'Shop bị khóa'],
                    datasets: [{
                        data: [activeShops, inactiveShops],
                        backgroundColor: ['#10B981', '#EF4444'],
                        borderWidth: 2,
                        borderColor: '#fff'
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                padding: 20,
                                usePointStyle: true
                            }
                        }
                    }
                }
            });

            // Bar Chart
            const barCtx = document.getElementById('barChart').getContext('2d');
            if (barChart) {
                barChart.destroy();
            }
            barChart = new Chart(barCtx, {
                type: 'bar',
                data: {
                    labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6'],
                    datasets: [{
                        label: 'Số shop mới',
                        data: [2, 3, 4, 6, 8, shopOwners.length],
                        backgroundColor: '#3B82F6',
                        borderColor: '#2563EB',
                        borderWidth: 1,
                        borderRadius: 8
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                color: '#F3F4F6'
                            }
                        },
                        x: {
                            grid: {
                                display: false
                            }
                        }
                    }
                }
            });
        }

        // Table functions
        function renderTable(data) {
            if (!data) data = shopOwners;
            const tbody = document.getElementById('shopOwnersTableBody');
            tbody.innerHTML = '';

            data.forEach(function(shop) {
                const row = document.createElement('tr');
                row.innerHTML = 
                    '<td>' + shop.id + '</td>' +
                    '<td>' + shop.shopName + '</td>' +
                    '<td>' + shop.ownerName + '</td>' +
                    '<td>' + shop.email + '</td>' +
                    '<td>' + (shop.phone === 'NULL' ? '-' : shop.phone) + '</td>' +
                    '<td><span class="status-badge ' + (shop.isActive ? 'status-active' : 'status-inactive') + '">' +
                    (shop.isActive ? 'Hoạt động' : 'Bị khóa') + '</span></td>' +
                    '<td>' + new Date(shop.createdAt).toLocaleDateString('vi-VN') + '</td>' +
                    '<td><div class="action-buttons">' +
                    '<button class="btn btn-warning" onclick="editShop(' + shop.id + ')">✏️</button>' +
                    '<button class="btn btn-danger" onclick="deleteShop(' + shop.id + ')">🗑️</button>' +
                    '</div></td>';
                tbody.appendChild(row);
            });
        }

        // Search function
        document.getElementById('searchInput').addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const filteredData = shopOwners.filter(function(shop) {
                return shop.shopName.toLowerCase().includes(searchTerm) ||
                       shop.ownerName.toLowerCase().includes(searchTerm) ||
                       shop.email.toLowerCase().includes(searchTerm);
            });
            renderTable(filteredData);
        });

        // Modal functions
        function openModal(shop = null) {
            editingId = shop ? shop.id : null;
            document.getElementById('modalTitle').textContent = shop ? 'Chỉnh Sửa Shop Owner' : 'Thêm Shop Owner';
            
            if (shop) {
                document.getElementById('shopName').value = shop.shopName;
                document.getElementById('ownerName').value = shop.ownerName;
                document.getElementById('email').value = shop.email;
                document.getElementById('phone').value = shop.phone === 'NULL' ? '' : shop.phone;
                document.getElementById('password').value = '';
                document.getElementById('isActive').value = shop.isActive.toString();
            } else {
                document.getElementById('shopOwnerForm').reset();
            }
            
            document.getElementById('shopOwnerModal').style.display = 'block';
        }

        function closeModal() {
            document.getElementById('shopOwnerModal').style.display = 'none';
            editingId = null;
        }

        function editShop(id) {
            const shop = shopOwners.find(s => s.id === id);
            if (shop) {
                openModal(shop);
            }
        }

        function deleteShop(id) {
            if (confirm('Bạn có chắc chắn muốn xóa shop owner này?')) {
                shopOwners = shopOwners.filter(shop => shop.id !== id);
                renderTable();
                updateDashboard();
            }
        }

        // Form submission
        document.getElementById('shopOwnerForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = {
                shopName: document.getElementById('shopName').value,
                ownerName: document.getElementById('ownerName').value,
                email: document.getElementById('email').value,
                phone: document.getElementById('phone').value || 'NULL',
                passwordHash: '$2b$10$' + document.getElementById('password').value + '_encrypted',
                isActive: document.getElementById('isActive').value === 'true',
                createdAt: new Date().toISOString().replace('T', ' ').slice(0, 23)
            };

            if (editingId) {
                // Update existing
                const index = shopOwners.findIndex(shop => shop.id === editingId);
                if (index !== -1) {
                    shopOwners[index] = { ...shopOwners[index], ...formData };
                }
            } else {
                // Add new
                const newId = shopOwners.length > 0 ? Math.max(...shopOwners.map(s => s.id)) + 1 : 1;
                shopOwners.push({ id: newId, ...formData });
            }

            renderTable();
            updateDashboard();
            closeModal();
        });

        // Close modal when clicking outside
        window.onclick = function(event) {
            const modal = document.getElementById('shopOwnerModal');
            if (event.target === modal) {
                closeModal();
            }
        }

        // Initialize
        document.addEventListener('DOMContentLoaded', function() {
            updateDashboard();
            renderTable();
        });
    </script>
</body>
</html>