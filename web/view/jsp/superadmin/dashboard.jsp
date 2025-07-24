<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Super Admin Dashboard</title>
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

            .user-info {
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .user-avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background: linear-gradient(45deg, #667eea, #764ba2);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: bold;
            }

            /* Stats Cards */
            .stats-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
                margin-bottom: 30px;
            }

            .stat-card {
                background: white;
                padding: 25px;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                position: relative;
                overflow: hidden;
            }

            .stat-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            }

            .stat-card::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                height: 4px;
                background: linear-gradient(90deg, #667eea, #764ba2);
            }

            .stat-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 15px;
            }

            .stat-title {
                font-size: 16px;
                color: #666;
                font-weight: 500;
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
            }

            .stat-value {
                font-size: 32px;
                font-weight: 700;
                color: #333;
                margin-bottom: 5px;
            }

            .stat-change {
                font-size: 14px;
                display: flex;
                align-items: center;
                gap: 5px;
            }

            .stat-change.positive {
                color: #28a745;
            }

            .stat-change.negative {
                color: #dc3545;
            }

            /* Recent Activities */
            .activities-section {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .section-header {
                padding: 20px 25px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .section-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
            }

            .view-all {
                color: #667eea;
                text-decoration: none;
                font-weight: 500;
                transition: color 0.3s ease;
            }

            .view-all:hover {
                color: #764ba2;
            }

            .activity-list {
                padding: 0;
            }

            .activity-item {
                padding: 20px 25px;
                border-bottom: 1px solid #f8f9fa;
                display: flex;
                align-items: center;
                gap: 15px;
                transition: background-color 0.3s ease;
            }

            .activity-item:hover {
                background-color: #f8f9fa;
            }

            .activity-item:last-child {
                border-bottom: none;
            }

            .activity-icon {
                width: 40px;
                height: 40px;
                border-radius: 10px;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 16px;
                color: white;
            }

            .activity-content {
                flex: 1;
            }

            .activity-title {
                font-weight: 600;
                color: #333;
                margin-bottom: 5px;
            }

            .activity-time {
                font-size: 14px;
                color: #666;
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

                .stats-grid {
                    grid-template-columns: 1fr;
                }

                .header {
                    flex-direction: column;
                    gap: 15px;
                    text-align: center;
                }
            }

            /* Color themes for stat cards */
            .stat-card.blue .stat-icon {
                background: linear-gradient(45deg, #667eea, #764ba2);
            }

            .stat-card.green .stat-icon {
                background: linear-gradient(45deg, #28a745, #20c997);
            }

            .stat-card.orange .stat-icon {
                background: linear-gradient(45deg, #fd7e14, #ffc107);
            }

            .stat-card.red .stat-icon {
                background: linear-gradient(45deg, #dc3545, #e83e8c);
            }

            .activity-icon.blue {
                background: linear-gradient(45deg, #667eea, #764ba2);
            }

            .activity-icon.green {
                background: linear-gradient(45deg, #28a745, #20c997);
            }

            .activity-icon.orange {
                background: linear-gradient(45deg, #fd7e14, #ffc107);
            }

            .activity-icon.red {
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
                    <h1>Dashboard</h1>
                    <div class="user-info">
                        <span>Xin chào, Super Admin</span>
                        <div class="user-avatar">
                            <i class="fas fa-user"></i>
                        </div>
                    </div>
                </div>

                <!-- Stats Cards -->
                <div class="stats-grid">
                    <div class="stat-card blue">
                        <div class="stat-header">
                            <div class="stat-title">Tổng số Shop</div>
                            <div class="stat-icon">
                                <i class="fas fa-store"></i>
                            </div>
                        </div>
                        <div class="stat-value">${totalShops}</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +12% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card green">
                        <div class="stat-header">
                            <div class="stat-title">Shop Hoạt động</div>
                            <div class="stat-icon">
                                <i class="fas fa-check-circle"></i>
                            </div>
                        </div>
                        <div class="stat-value">${activeShops}</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +8% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card orange">
                        <div class="stat-header">
                            <div class="stat-title">Shop Hết hạn</div>
                            <div class="stat-icon">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>
                        </div>
                        <div class="stat-value">${expiredShops}</div>
                        <div class="stat-change negative">
                            <i class="fas fa-arrow-down"></i>
                            -5% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card red">
                        <div class="stat-header">
                            <div class="stat-title">Doanh thu tháng</div>
                            <div class="stat-icon">
                                <i class="fas fa-dollar-sign"></i>
                            </div>
                        </div>
                        <div class="stat-value">${monthlyRevenue}</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +15% so với tháng trước
                        </div>
                    </div>
                </div>

                <!-- Recent Activities -->
                <div class="activities-section">
                    <div class="section-header">
                        <h3 class="section-title">Hoạt động gần đây</h3>
                        <a href="#" class="view-all">Xem tất cả</a>
                    </div>
                    <div class="activity-list">
                        <div class="activity-item">
                            <div class="activity-icon green">
                                <i class="fas fa-plus"></i>
                            </div>
                            <div class="activity-content">
                                <div class="activity-title">Shop mới đăng ký: Fashion Store ABC</div>
                                <div class="activity-time">2 giờ trước</div>
                            </div>
                        </div>

                        <div class="activity-item">
                            <div class="activity-icon orange">
                                <i class="fas fa-exclamation"></i>
                            </div>
                            <div class="activity-content">
                                <div class="activity-title">Shop "Tech Shop XYZ" sắp hết hạn gói dịch vụ</div>
                                <div class="activity-time">5 giờ trước</div>
                            </div>
                        </div>

                        <div class="activity-item">
                            <div class="activity-icon blue">
                                <i class="fas fa-credit-card"></i>
                            </div>
                            <div class="activity-content">
                                <div class="activity-title">Thanh toán thành công từ Shop "Beauty Store"</div>
                                <div class="activity-time">1 ngày trước</div>
                            </div>
                        </div>

                        <div class="activity-item">
                            <div class="activity-icon red">
                                <i class="fas fa-times"></i>
                            </div>
                            <div class="activity-content">
                                <div class="activity-title">Shop "Old Store" đã bị tạm ngưng do vi phạm</div>
                                <div class="activity-time">2 ngày trước</div>
                            </div>
                        </div>

                        <div class="activity-item">
                            <div class="activity-icon green">
                                <i class="fas fa-check"></i>
                            </div>
                            <div class="activity-content">
                                <div class="activity-title">Shop "New Fashion" đã được kích hoạt</div>
                                <div class="activity-time">3 ngày trước</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            // Add some interactivity
            document.addEventListener('DOMContentLoaded', function () {
                // Animate stat cards on load
                const statCards = document.querySelectorAll('.stat-card');
                statCards.forEach((card, index) => {
                    setTimeout(() => {
                        card.style.opacity = '0';
                        card.style.transform = 'translateY(20px)';
                        card.style.transition = 'all 0.5s ease';

                        setTimeout(() => {
                            card.style.opacity = '1';
                            card.style.transform = 'translateY(0)';
                        }, 100);
                    }, index * 100);
                });

                // Add click handlers for navigation
                const navLinks = document.querySelectorAll('.nav-link');
                navLinks.forEach(link => {
                    link.addEventListener('click', function () {
                        navLinks.forEach(l => l.classList.remove('active'));
                        this.classList.add('active');
                    });
                });
            });
        </script>
    </body>
</html> 