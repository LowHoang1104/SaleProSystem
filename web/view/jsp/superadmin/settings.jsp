<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cài đặt - Super Admin</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f4f6f9;
                color: #333;
            }

            .container {
                display: flex;
                min-height: 100vh;
            }

            /* Sidebar */
            .sidebar {
                width: 250px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 20px 0;
                position: fixed;
                height: 100vh;
                overflow-y: auto;
            }

            .sidebar-header {
                padding: 0 20px 20px;
                border-bottom: 1px solid rgba(255,255,255,0.1);
                margin-bottom: 20px;
            }

            .sidebar-header h2 {
                font-size: 24px;
                font-weight: 700;
            }

            .sidebar-header p {
                opacity: 0.8;
                font-size: 14px;
                margin-top: 5px;
            }

            .nav-menu {
                list-style: none;
            }

            .nav-item {
                margin-bottom: 5px;
            }

            .nav-link {
                display: flex;
                align-items: center;
                padding: 12px 20px;
                color: white;
                text-decoration: none;
                transition: all 0.3s ease;
                border-left: 3px solid transparent;
            }

            .nav-link:hover, .nav-link.active {
                background: rgba(255,255,255,0.1);
                border-left-color: #fff;
                transform: translateX(5px);
            }

            .nav-link i {
                margin-right: 12px;
                width: 20px;
                text-align: center;
            }

            /* Main Content */
            .main-content {
                flex: 1;
                margin-left: 250px;
                padding: 20px;
            }

            .header {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin-bottom: 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .header h1 {
                color: #333;
                font-size: 28px;
                font-weight: 700;
            }

            .header-actions {
                display: flex;
                gap: 15px;
                align-items: center;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 8px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                text-decoration: none;
                display: inline-flex;
                align-items: center;
                gap: 8px;
            }

            .btn-primary {
                background: linear-gradient(45deg, #667eea, #764ba2);
                color: white;
            }

            .btn-success {
                background: linear-gradient(45deg, #28a745, #20c997);
                color: white;
            }

            .btn-warning {
                background: linear-gradient(45deg, #fd7e14, #ffc107);
                color: white;
            }

            .btn-danger {
                background: linear-gradient(45deg, #dc3545, #e83e8c);
                color: white;
            }

            /* Settings Grid */
            .settings-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px;
                margin-bottom: 30px;
            }

            .settings-card {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .card-header {
                padding: 20px 25px;
                border-bottom: 1px solid #eee;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
            }

            .card-title {
                font-size: 18px;
                font-weight: 600;
                margin-bottom: 5px;
            }

            .card-subtitle {
                opacity: 0.9;
                font-size: 14px;
            }

            .card-body {
                padding: 25px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                font-weight: 600;
                margin-bottom: 8px;
                color: #555;
            }

            .form-group input,
            .form-group select,
            .form-group textarea {
                width: 100%;
                padding: 12px;
                border: 2px solid #e9ecef;
                border-radius: 8px;
                font-size: 14px;
                transition: border-color 0.3s ease;
            }

            .form-group input:focus,
            .form-group select:focus,
            .form-group textarea:focus {
                outline: none;
                border-color: #667eea;
            }

            .form-group textarea {
                resize: vertical;
                min-height: 100px;
            }

            /* Switch Toggle */
            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
                border-radius: 34px;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                transition: .4s;
                border-radius: 50%;
            }

            input:checked + .slider {
                background-color: #667eea;
            }

            input:checked + .slider:before {
                transform: translateX(26px);
            }

            .switch-label {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 15px;
            }

            .switch-text {
                font-weight: 600;
                color: #555;
            }

            /* Package Management */
            .package-list {
                margin-top: 20px;
            }

            .package-item {
                background: #f8f9fa;
                border: 1px solid #e9ecef;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 10px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .package-info h4 {
                margin-bottom: 5px;
                color: #333;
            }

            .package-info p {
                color: #666;
                font-size: 14px;
            }

            .package-actions {
                display: flex;
                gap: 10px;
            }

            .btn-sm {
                padding: 6px 12px;
                font-size: 12px;
                border-radius: 6px;
            }

            /* System Info */
            .system-info {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .info-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 15px 25px;
                border-bottom: 1px solid #f8f9fa;
            }

            .info-item:last-child {
                border-bottom: none;
            }

            .info-label {
                font-weight: 600;
                color: #555;
            }

            .info-value {
                color: #333;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .sidebar {
                    transform: translateX(-100%);
                    transition: transform 0.3s ease;
                }

                .sidebar.open {
                    transform: translateX(0);
                }

                .main-content {
                    margin-left: 0;
                }

                .settings-grid {
                    grid-template-columns: 1fr;
                }

                .header {
                    flex-direction: column;
                    gap: 15px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Sidebar -->
            <%@include file="sidebar.jsp" %> 
            <!-- Main Content -->
            <div class="main-content">
                <!-- Header -->
                <div class="header">
                    <h1>Cài đặt hệ thống</h1>
                    <div class="header-actions">
                        <button class="btn btn-success" onclick="saveAllSettings()">
                            <i class="fas fa-save"></i>
                            Lưu tất cả
                        </button>
                        <button class="btn btn-warning" onclick="resetSettings()">
                            <i class="fas fa-undo"></i>
                            Khôi phục mặc định
                        </button>
                    </div>
                </div>

                <!-- Settings Grid -->
                <div class="settings-grid">
                    <!-- General Settings -->
                    <div class="settings-card">
                        <div class="card-header">
                            <div class="card-title">Cài đặt chung</div>
                            <div class="card-subtitle">Cấu hình cơ bản hệ thống</div>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="systemName">Tên hệ thống</label>
                                <input type="text" id="systemName" value="SalePro Management System">
                            </div>
                            <div class="form-group">
                                <label for="adminEmail">Email quản trị</label>
                                <input type="email" id="adminEmail" value="admin@salesystem.com">
                            </div>
                            <div class="form-group">
                                <label for="timezone">Múi giờ</label>
                                <select id="timezone">
                                    <option value="Asia/Ho_Chi_Minh" selected>Asia/Ho_Chi_Minh (GMT+7)</option>
                                    <option value="UTC">UTC (GMT+0)</option>
                                    <option value="America/New_York">America/New_York (GMT-5)</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="language">Ngôn ngữ</label>
                                <select id="language">
                                    <option value="vi" selected>Tiếng Việt</option>
                                    <option value="en">English</option>
                                    <option value="zh">中文</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Payment Settings -->
                    <div class="settings-card">
                        <div class="card-header">
                            <div class="card-title">Cài đặt thanh toán</div>
                            <div class="card-subtitle">Cấu hình VNPay và các phương thức thanh toán</div>
                        </div>
                        <div class="card-body">
                            <div class="form-group">
                                <label for="vnpayTmnCode">VNPay TMN Code</label>
                                <input type="text" id="vnpayTmnCode" value="VNPAY_TEST">
                            </div>
                            <div class="form-group">
                                <label for="vnpayHashSecret">VNPay Hash Secret</label>
                                <input type="password" id="vnpayHashSecret" value="YOUR_SANDBOX_HASH_SECRET">
                            </div>
                            <div class="form-group">
                                <label for="vnpayUrl">VNPay Payment URL</label>
                                <input type="url" id="vnpayUrl" value="https://sandbox.vnpayment.vn/paymentv2/vpcpay.html">
                            </div>
                            <div class="form-group">
                                <label for="vnpayReturnUrl">VNPay Return URL</label>
                                <input type="url" id="vnpayReturnUrl" value="http://localhost:8080/vnpay-return">
                            </div>
                        </div>
                    </div>

                    <!-- System Features -->
                    <div class="settings-card">
                        <div class="card-header">
                            <div class="card-title">Tính năng hệ thống</div>
                            <div class="card-subtitle">Bật/tắt các tính năng</div>
                        </div>
                        <div class="card-body">
                            <div class="switch-label">
                                <span class="switch-text">Cho phép đăng ký shop mới</span>
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <span class="slider"></span>
                                </label>
                            </div>
                            <div class="switch-label">
                                <span class="switch-text">Gửi email thông báo</span>
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <span class="slider"></span>
                                </label>
                            </div>
                            <div class="switch-label">
                                <span class="switch-text">Tự động gia hạn</span>
                                <label class="switch">
                                    <input type="checkbox">
                                    <span class="slider"></span>
                                </label>
                            </div>
                            <div class="switch-label">
                                <span class="switch-text">Bảo trì hệ thống</span>
                                <label class="switch">
                                    <input type="checkbox">
                                    <span class="slider"></span>
                                </label>
                            </div>
                            <div class="switch-label">
                                <span class="switch-text">Backup tự động</span>
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <span class="slider"></span>
                                </label>
                            </div>
                        </div>
                    </div>

                    <!-- Package Management -->
                    <div class="settings-card">
                        <div class="card-header">
                            <div class="card-title">Quản lý gói dịch vụ</div>
                            <div class="card-subtitle">Cấu hình các gói đăng ký</div>
                        </div>
                        <div class="card-body">
                            <button class="btn btn-primary" style="margin-bottom: 20px;" onclick="addNewPackage()">
                                <i class="fas fa-plus"></i>
                                Thêm gói mới
                            </button>

                            <div class="package-list">
                                <div class="package-item">
                                    <div class="package-info">
                                        <h4>Gói 1 tháng</h4>
                                        <p>199,000 VND - 1 tháng</p>
                                    </div>
                                    <div class="package-actions">
                                        <button class="btn btn-sm btn-warning" onclick="editPackage(1)">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-danger" onclick="deletePackage(1)">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>

                                <div class="package-item">
                                    <div class="package-info">
                                        <h4>Gói 3 tháng</h4>
                                        <p>499,000 VND - 3 tháng (Giảm 15%)</p>
                                    </div>
                                    <div class="package-actions">
                                        <button class="btn btn-sm btn-warning" onclick="editPackage(2)">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-danger" onclick="deletePackage(2)">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>

                                <div class="package-item">
                                    <div class="package-info">
                                        <h4>Gói 6 tháng</h4>
                                        <p>899,000 VND - 6 tháng (Giảm 25%)</p>
                                    </div>
                                    <div class="package-actions">
                                        <button class="btn btn-sm btn-warning" onclick="editPackage(3)">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-danger" onclick="deletePackage(3)">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- System Information -->
                <div class="system-info">
                    <div class="card-header">
                        <div class="card-title">Thông tin hệ thống</div>
                        <div class="card-subtitle">Thông tin kỹ thuật và trạng thái</div>
                    </div>
                    <div class="card-body" style="padding: 0;">
                        <div class="info-item">
                            <span class="info-label">Phiên bản hệ thống</span>
                            <span class="info-value">v2.1.0</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Database</span>
                            <span class="info-value">SQL Server 2019</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Tổng số Shop</span>
                            <span class="info-value">156</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Shop hoạt động</span>
                            <span class="info-value">142</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Dung lượng sử dụng</span>
                            <span class="info-value">2.5 GB / 10 GB</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Thời gian hoạt động</span>
                            <span class="info-value">15 ngày 8 giờ 32 phút</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Lần cập nhật cuối</span>
                            <span class="info-value">15/02/2024 14:30</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            // Settings functions
            function saveAllSettings() {
                // Collect all form data
                const settings = {
                    systemName: document.getElementById('systemName').value,
                    adminEmail: document.getElementById('adminEmail').value,
                    timezone: document.getElementById('timezone').value,
                    language: document.getElementById('language').value,
                    vnpayTmnCode: document.getElementById('vnpayTmnCode').value,
                    vnpayHashSecret: document.getElementById('vnpayHashSecret').value,
                    vnpayUrl: document.getElementById('vnpayUrl').value,
                    vnpayReturnUrl: document.getElementById('vnpayReturnUrl').value
                };

                console.log('Saving settings:', settings);
                // Implement save logic here

                alert('Cài đặt đã được lưu thành công!');
            }

            function resetSettings() {
                if (confirm('Bạn có chắc muốn khôi phục tất cả cài đặt về mặc định?')) {
                    // Reset all form fields to default values
                    document.getElementById('systemName').value = 'SalePro Management System';
                    document.getElementById('adminEmail').value = 'admin@salesystem.com';
                    document.getElementById('timezone').value = 'Asia/Ho_Chi_Minh';
                    document.getElementById('language').value = 'vi';
                    document.getElementById('vnpayTmnCode').value = 'VNPAY_TEST';
                    document.getElementById('vnpayHashSecret').value = 'YOUR_SANDBOX_HASH_SECRET';
                    document.getElementById('vnpayUrl').value = 'https://sandbox.vnpayment.vn/paymentv2/vpcpay.html';
                    document.getElementById('vnpayReturnUrl').value = 'http://localhost:8080/vnpay-return';

                    // Reset switches
                    const switches = document.querySelectorAll('.switch input');
                    switches[0].checked = true; // Allow new shop registration
                    switches[1].checked = true; // Email notifications
                    switches[2].checked = false; // Auto renewal
                    switches[3].checked = false; // Maintenance mode
                    switches[4].checked = true; // Auto backup

                    alert('Đã khôi phục cài đặt về mặc định!');
                }
            }

            function addNewPackage() {
                // Open modal or navigate to add package page
                console.log('Adding new package');
                alert('Chức năng thêm gói mới sẽ được mở trong modal');
            }

            function editPackage(packageId) {
                console.log('Editing package:', packageId);
                alert('Chức năng chỉnh sửa gói sẽ được mở trong modal');
            }

            function deletePackage(packageId) {
                if (confirm('Bạn có chắc muốn xóa gói này?')) {
                    console.log('Deleting package:', packageId);
                    // Implement delete logic
                    alert('Gói đã được xóa!');
                }
            }

            // Add some interactivity
            document.addEventListener('DOMContentLoaded', function () {
                // Add click handlers for navigation
                const navLinks = document.querySelectorAll('.nav-link');
                navLinks.forEach(link => {
                    link.addEventListener('click', function () {
                        navLinks.forEach(l => l.classList.remove('active'));
                        this.classList.add('active');
                    });
                });

                // Add form validation
                const inputs = document.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    input.addEventListener('blur', function () {
                        if (this.hasAttribute('required') && !this.value) {
                            this.style.borderColor = '#dc3545';
                        } else {
                            this.style.borderColor = '#e9ecef';
                        }
                    });
                });

                // Add switch toggle effects
                const switches = document.querySelectorAll('.switch input');
                switches.forEach(switchInput => {
                    switchInput.addEventListener('change', function () {
                        const label = this.closest('.switch-label').querySelector('.switch-text');
                        if (this.checked) {
                            label.style.color = '#667eea';
                        } else {
                            label.style.color = '#555';
                        }
                    });
                });
            });
        </script>
    </body>
</html> 