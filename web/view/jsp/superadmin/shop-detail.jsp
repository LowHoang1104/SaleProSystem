<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết Shop - Super Admin</title>
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

            .btn-secondary {
                background: #6c757d;
                color: white;
            }

            /* Shop Info Cards */
            .shop-info-grid {
                display: grid;
                grid-template-columns: 2fr 1fr;
                gap: 20px;
                margin-bottom: 30px;
            }

            .info-card {
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

            .info-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 12px 0;
                border-bottom: 1px solid #f8f9fa;
            }

            .info-row:last-child {
                border-bottom: none;
            }

            .info-label {
                font-weight: 600;
                color: #555;
            }

            .info-value {
                color: #333;
            }

            .status-badge {
                padding: 6px 12px;
                border-radius: 20px;
                font-size: 12px;
                font-weight: 600;
                text-transform: uppercase;
            }

            .status-active {
                background: #d4edda;
                color: #155724;
            }

            .status-expired {
                background: #f8d7da;
                color: #721c24;
            }

            .status-trial {
                background: #fff3cd;
                color: #856404;
            }

            .status-suspended {
                background: #f5c6cb;
                color: #721c24;
            }

            /* Quick Stats */
            .quick-stats {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
                gap: 15px;
                margin-bottom: 30px;
            }

            .stat-item {
                background: white;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                text-align: center;
                transition: transform 0.3s ease;
            }

            .stat-item:hover {
                transform: translateY(-5px);
            }

            .stat-icon {
                width: 50px;
                height: 50px;
                border-radius: 12px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 24px;
                color: white;
                margin: 0 auto 15px;
            }

            .stat-value {
                font-size: 24px;
                font-weight: 700;
                color: #333;
                margin-bottom: 5px;
            }

            .stat-label {
                font-size: 14px;
                color: #666;
            }

            /* Tabs */
            .tabs-container {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .tabs-header {
                display: flex;
                border-bottom: 1px solid #eee;
            }

            .tab-button {
                flex: 1;
                padding: 15px 20px;
                background: none;
                border: none;
                cursor: pointer;
                font-weight: 600;
                color: #666;
                transition: all 0.3s ease;
                border-bottom: 3px solid transparent;
            }

            .tab-button.active {
                color: #667eea;
                border-bottom-color: #667eea;
                background: #f8f9fa;
            }

            .tab-content {
                display: none;
                padding: 25px;
            }

            .tab-content.active {
                display: block;
            }

            /* Table */
            .table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }

            .table th {
                background: #f8f9fa;
                padding: 12px;
                text-align: left;
                font-weight: 600;
                color: #555;
                border-bottom: 2px solid #dee2e6;
            }

            .table td {
                padding: 12px;
                border-bottom: 1px solid #eee;
            }

            .table tbody tr:hover {
                background-color: #f8f9fa;
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

                .shop-info-grid {
                    grid-template-columns: 1fr;
                }

                .header {
                    flex-direction: column;
                    gap: 15px;
                }

                .quick-stats {
                    grid-template-columns: repeat(2, 1fr);
                }

                .tabs-header {
                    flex-direction: column;
                }
            }

            /* Color themes */
            .stat-item.blue .stat-icon {
                background: linear-gradient(45deg, #667eea, #764ba2);
            }

            .stat-item.green .stat-icon {
                background: linear-gradient(45deg, #28a745, #20c997);
            }

            .stat-item.orange .stat-icon {
                background: linear-gradient(45deg, #fd7e14, #ffc107);
            }

            .stat-item.red .stat-icon {
                background: linear-gradient(45deg, #dc3545, #e83e8c);
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
                    <div>
                        <h1>Chi tiết Shop</h1>
                        <p style="color: #666; margin-top: 5px;">Fashion Store ABC - ID: 1</p>
                    </div>
                    <div class="header-actions">
                        <button class="btn btn-secondary" onclick="history.back()">
                            <i class="fas fa-arrow-left"></i>
                            Quay lại
                        </button>
                        <button class="btn btn-warning">
                            <i class="fas fa-edit"></i>
                            Chỉnh sửa
                        </button>
                        <button class="btn btn-success">
                            <i class="fas fa-sync"></i>
                            Gia hạn
                        </button>
                        <button class="btn btn-danger">
                            <i class="fas fa-ban"></i>
                            Tạm ngưng
                        </button>
                    </div>
                </div>

                <!-- Quick Stats -->
                <div class="quick-stats">
                    <div class="stat-item blue">
                        <div class="stat-icon">
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="stat-value">1,234</div>
                        <div class="stat-label">Khách hàng</div>
                    </div>
                    <div class="stat-item green">
                        <div class="stat-icon">
                            <i class="fas fa-shopping-cart"></i>
                        </div>
                        <div class="stat-value">567</div>
                        <div class="stat-label">Đơn hàng</div>
                    </div>
                    <div class="stat-item orange">
                        <div class="stat-icon">
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                        <div class="stat-value">89.5M</div>
                        <div class="stat-label">Doanh thu</div>
                    </div>
                    <div class="stat-item red">
                        <div class="stat-icon">
                            <i class="fas fa-box"></i>
                        </div>
                        <div class="stat-value">234</div>
                        <div class="stat-label">Sản phẩm</div>
                    </div>
                </div>

                <!-- Shop Info Grid -->
                <div class="shop-info-grid">
                    <!-- Main Info -->
                    <div class="info-card">
                        <div class="card-header">
                            <div class="card-title">Thông tin Shop</div>
                            <div class="card-subtitle">Chi tiết cửa hàng và chủ sở hữu</div>
                        </div>
                        <div class="card-body">
                            <div class="info-row">
                                <span class="info-label">Tên Shop:</span>
                                <span class="info-value">Fashion Store ABC</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Database Name:</span>
                                <span class="info-value">fashionstoreabc</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Chủ sở hữu:</span>
                                <span class="info-value">Nguyễn Văn A</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Email:</span>
                                <span class="info-value">nguyenvana@email.com</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Số điện thoại:</span>
                                <span class="info-value">0901234567</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Ngày đăng ký:</span>
                                <span class="info-value">15/01/2024</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Trạng thái:</span>
                                <span class="status-badge status-active">Active</span>
                            </div>
                        </div>
                    </div>

                    <!-- Subscription Info -->
                    <div class="info-card">
                        <div class="card-header">
                            <div class="card-title">Gói dịch vụ</div>
                            <div class="card-subtitle">Thông tin đăng ký</div>
                        </div>
                        <div class="card-body">
                            <div class="info-row">
                                <span class="info-label">Gói hiện tại:</span>
                                <span class="info-value">Gói 1 tháng</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Giá gói:</span>
                                <span class="info-value">199,000 VND</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Ngày bắt đầu:</span>
                                <span class="info-value">15/01/2024</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Ngày hết hạn:</span>
                                <span class="info-value">15/02/2024</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Ngày thanh toán cuối:</span>
                                <span class="info-value">15/01/2024</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Còn lại:</span>
                                <span class="info-value" style="color: #dc3545; font-weight: 600;">15 ngày</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tabs -->
                <div class="tabs-container">
                    <div class="tabs-header">
                        <button class="tab-button active" onclick="showTab('transactions')">
                            <i class="fas fa-credit-card"></i> Lịch sử giao dịch
                        </button>
                        <button class="tab-button" onclick="showTab('activities')">
                            <i class="fas fa-history"></i> Hoạt động
                        </button>
                        <button class="tab-button" onclick="showTab('settings')">
                            <i class="fas fa-cog"></i> Cài đặt
                        </button>
                    </div>

                    <!-- Transactions Tab -->
                    <div id="transactions" class="tab-content active">
                        <h3>Lịch sử giao dịch thanh toán</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Mã GD</th>
                                    <th>Gói dịch vụ</th>
                                    <th>Số tiền</th>
                                    <th>Trạng thái</th>
                                    <th>Ngày tạo</th>
                                    <th>Ngày thanh toán</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>TXN001</td>
                                    <td>Gói 1 tháng</td>
                                    <td>199,000 VND</td>
                                    <td><span class="status-badge status-active">Completed</span></td>
                                    <td>15/01/2024</td>
                                    <td>15/01/2024</td>
                                </tr>
                                <tr>
                                    <td>TXN002</td>
                                    <td>Gói 3 tháng</td>
                                    <td>499,000 VND</td>
                                    <td><span class="status-badge status-trial">Pending</span></td>
                                    <td>10/02/2024</td>
                                    <td>-</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- Activities Tab -->
                    <div id="activities" class="tab-content">
                        <h3>Lịch sử hoạt động</h3>
                        <div style="padding: 20px 0;">
                            <div style="border-left: 3px solid #667eea; padding-left: 20px; margin-bottom: 20px;">
                                <div style="font-weight: 600; color: #333;">Shop được kích hoạt</div>
                                <div style="color: #666; font-size: 14px;">15/01/2024 10:30 AM</div>
                                <div style="color: #555; margin-top: 5px;">Shop đã được kích hoạt thành công sau khi thanh toán</div>
                            </div>
                            <div style="border-left: 3px solid #28a745; padding-left: 20px; margin-bottom: 20px;">
                                <div style="font-weight: 600; color: #333;">Thanh toán thành công</div>
                                <div style="color: #666; font-size: 14px;">15/01/2024 10:25 AM</div>
                                <div style="color: #555; margin-top: 5px;">Thanh toán gói 1 tháng qua VNPay</div>
                            </div>
                            <div style="border-left: 3px solid #fd7e14; padding-left: 20px; margin-bottom: 20px;">
                                <div style="font-weight: 600; color: #333;">Shop được tạo</div>
                                <div style="color: #666; font-size: 14px;">15/01/2024 10:20 AM</div>
                                <div style="color: #555; margin-top: 5px;">Shop mới được đăng ký bởi Nguyễn Văn A</div>
                            </div>
                        </div>
                    </div>

                    <!-- Settings Tab -->
                    <div id="settings" class="tab-content">
                        <h3>Cài đặt Shop</h3>
                        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-top: 20px;">
                            <div>
                                <h4>Thông tin cơ bản</h4>
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label>Tên Shop</label>
                                    <input type="text" value="Fashion Store ABC" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                                </div>
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label>Email liên hệ</label>
                                    <input type="email" value="nguyenvana@email.com" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                                </div>
                            </div>
                            <div>
                                <h4>Cài đặt hệ thống</h4>
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label>Trạng thái Shop</label>
                                    <select style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                                        <option value="active">Hoạt động</option>
                                        <option value="suspended">Tạm ngưng</option>
                                        <option value="expired">Hết hạn</option>
                                    </select>
                                </div>
                                <div class="form-group" style="margin-bottom: 15px;">
                                    <label>Gói dịch vụ</label>
                                    <select style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                                        <option value="1">Gói 1 tháng</option>
                                        <option value="3">Gói 3 tháng</option>
                                        <option value="6">Gói 6 tháng</option>
                                        <option value="12">Gói 1 năm</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div style="margin-top: 20px;">
                            <button class="btn btn-primary">Lưu thay đổi</button>
                            <button class="btn btn-secondary" style="margin-left: 10px;">Hủy</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function showTab(tabName) {
                // Hide all tab contents
                const tabContents = document.querySelectorAll('.tab-content');
                tabContents.forEach(content => {
                    content.classList.remove('active');
                });

                // Remove active class from all tab buttons
                const tabButtons = document.querySelectorAll('.tab-button');
                tabButtons.forEach(button => {
                    button.classList.remove('active');
                });

                // Show selected tab content
                document.getElementById(tabName).classList.add('active');

                // Add active class to clicked button
                event.target.classList.add('active');
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

                // Add hover effects to stat items
                const statItems = document.querySelectorAll('.stat-item');
                statItems.forEach(item => {
                    item.addEventListener('mouseenter', function () {
                        this.style.transform = 'translateY(-5px)';
                    });
                    item.addEventListener('mouseleave', function () {
                        this.style.transform = 'translateY(0)';
                    });
                });
            });
        </script>
    </body>
</html> 