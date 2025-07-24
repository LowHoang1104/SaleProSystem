<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Báo cáo - Super Admin</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

            /* Date Filters */
            .filters-section {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }

            .filters-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 15px;
                align-items: end;
            }

            .form-group {
                display: flex;
                flex-direction: column;
            }

            .form-group label {
                font-weight: 600;
                margin-bottom: 8px;
                color: #555;
            }

            .form-group input,
            .form-group select {
                padding: 10px 12px;
                border: 2px solid #e9ecef;
                border-radius: 8px;
                font-size: 14px;
                transition: border-color 0.3s ease;
            }

            .form-group input:focus,
            .form-group select:focus {
                outline: none;
                border-color: #667eea;
            }

            .filter-btn {
                background: linear-gradient(45deg, #667eea, #764ba2);
                color: white;
                border: none;
                padding: 12px 20px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .filter-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
            }

            /* Charts Grid */
            .charts-grid {
                display: grid;
                grid-template-columns: 2fr 1fr;
                gap: 20px;
                margin-bottom: 30px;
            }

            .chart-card {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .chart-header {
                padding: 20px 25px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .chart-title {
                font-size: 18px;
                font-weight: 600;
                color: #333;
            }

            .chart-actions {
                display: flex;
                gap: 10px;
            }

            .chart-btn {
                padding: 6px 12px;
                border: 1px solid #ddd;
                background: white;
                border-radius: 6px;
                cursor: pointer;
                font-size: 12px;
                transition: all 0.3s ease;
            }

            .chart-btn:hover {
                background: #f8f9fa;
            }

            .chart-body {
                padding: 20px;
                height: 300px;
                position: relative;
            }

            /* Stats Cards */
            .stats-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
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

            /* Table */
            .table-container {
                background: white;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .table-header {
                padding: 20px 25px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .table-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
            }

            .table {
                width: 100%;
                border-collapse: collapse;
            }

            .table th {
                background: #f8f9fa;
                padding: 15px 12px;
                text-align: left;
                font-weight: 600;
                color: #555;
                border-bottom: 2px solid #dee2e6;
            }

            .table td {
                padding: 15px 12px;
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

                .charts-grid {
                    grid-template-columns: 1fr;
                }

                .filters-grid {
                    grid-template-columns: 1fr;
                }

                .header {
                    flex-direction: column;
                    gap: 15px;
                }

                .stats-grid {
                    grid-template-columns: repeat(2, 1fr);
                }
            }

            /* Color themes */
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
                    <h1>Báo cáo & Thống kê</h1>
                    <div class="header-actions">
                        <button class="btn btn-success">
                            <i class="fas fa-download"></i>
                            Xuất báo cáo
                        </button>
                        <button class="btn btn-primary">
                            <i class="fas fa-print"></i>
                            In báo cáo
                        </button>
                    </div>
                </div>

                <!-- Date Filters -->
                <div class="filters-section">
                    <div class="filters-grid">
                        <div class="form-group">
                            <label for="reportType">Loại báo cáo</label>
                            <select id="reportType">
                                <option value="revenue">Báo cáo doanh thu</option>
                                <option value="shops">Báo cáo shop</option>
                                <option value="transactions">Báo cáo giao dịch</option>
                                <option value="subscriptions">Báo cáo đăng ký</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="startDate">Từ ngày</label>
                            <input type="date" id="startDate" value="2024-01-01">
                        </div>
                        <div class="form-group">
                            <label for="endDate">Đến ngày</label>
                            <input type="date" id="endDate" value="2024-02-15">
                        </div>
                        <div class="form-group">
                            <button class="filter-btn" onclick="generateReport()">
                                <i class="fas fa-search"></i>
                                Tạo báo cáo
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Stats Cards -->
                <div class="stats-grid">
                    <div class="stat-card blue">
                        <div class="stat-header">
                            <div class="stat-title">Tổng doanh thu</div>
                            <div class="stat-icon">
                                <i class="fas fa-dollar-sign"></i>
                            </div>
                        </div>
                        <div class="stat-value">2.5M VND</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +15% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card green">
                        <div class="stat-header">
                            <div class="stat-title">Shop mới</div>
                            <div class="stat-icon">
                                <i class="fas fa-plus"></i>
                            </div>
                        </div>
                        <div class="stat-value">12</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +8% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card orange">
                        <div class="stat-header">
                            <div class="stat-title">Giao dịch thành công</div>
                            <div class="stat-icon">
                                <i class="fas fa-check-circle"></i>
                            </div>
                        </div>
                        <div class="stat-value">89%</div>
                        <div class="stat-change positive">
                            <i class="fas fa-arrow-up"></i>
                            +5% so với tháng trước
                        </div>
                    </div>

                    <div class="stat-card red">
                        <div class="stat-header">
                            <div class="stat-title">Shop hết hạn</div>
                            <div class="stat-icon">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>
                        </div>
                        <div class="stat-value">3</div>
                        <div class="stat-change negative">
                            <i class="fas fa-arrow-down"></i>
                            -2% so với tháng trước
                        </div>
                    </div>
                </div>

                <!-- Charts -->
                <div class="charts-grid">
                    <!-- Revenue Chart -->
                    <div class="chart-card">
                        <div class="chart-header">
                            <h3 class="chart-title">Biểu đồ doanh thu theo tháng</h3>
                            <div class="chart-actions">
                                <button class="chart-btn" onclick="changeChartType('line')">Đường</button>
                                <button class="chart-btn" onclick="changeChartType('bar')">Cột</button>
                            </div>
                        </div>
                        <div class="chart-body">
                            <canvas id="revenueChart"></canvas>
                        </div>
                    </div>

                    <!-- Shop Status Chart -->
                    <div class="chart-card">
                        <div class="chart-header">
                            <h3 class="chart-title">Trạng thái Shop</h3>
                        </div>
                        <div class="chart-body">
                            <canvas id="shopStatusChart"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Top Performing Shops -->
                <div class="table-container">
                    <div class="table-header">
                        <h3 class="table-title">Top Shop có doanh thu cao nhất</h3>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Thứ hạng</th>
                                <th>Tên Shop</th>
                                <th>Chủ sở hữu</th>
                                <th>Doanh thu tháng</th>
                                <th>Tăng trưởng</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>
                                    <div style="font-weight: 600;">Fashion Store ABC</div>
                                    <div style="font-size: 12px; color: #666;">fashionstoreabc</div>
                                </td>
                                <td>Nguyễn Văn A</td>
                                <td>450,000 VND</td>
                                <td style="color: #28a745;">+25%</td>
                                <td><span style="background: #d4edda; color: #155724; padding: 4px 8px; border-radius: 12px; font-size: 12px;">Active</span></td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>
                                    <div style="font-weight: 600;">Tech Shop XYZ</div>
                                    <div style="font-size: 12px; color: #666;">techshopxyz</div>
                                </td>
                                <td>Trần Thị B</td>
                                <td>380,000 VND</td>
                                <td style="color: #28a745;">+18%</td>
                                <td><span style="background: #fff3cd; color: #856404; padding: 4px 8px; border-radius: 12px; font-size: 12px;">Trial</span></td>
                            </tr>
                            <tr>
                                <td>3</td>
                                <td>
                                    <div style="font-weight: 600;">Beauty Store</div>
                                    <div style="font-size: 12px; color: #666;">beautystore</div>
                                </td>
                                <td>Lê Văn C</td>
                                <td>320,000 VND</td>
                                <td style="color: #dc3545;">-5%</td>
                                <td><span style="background: #f8d7da; color: #721c24; padding: 4px 8px; border-radius: 12px; font-size: 12px;">Expired</span></td>
                            </tr>
                            <tr>
                                <td>4</td>
                                <td>
                                    <div style="font-weight: 600;">Food Store</div>
                                    <div style="font-size: 12px; color: #666;">foodstore</div>
                                </td>
                                <td>Phạm Thị D</td>
                                <td>280,000 VND</td>
                                <td style="color: #28a745;">+12%</td>
                                <td><span style="background: #d4edda; color: #155724; padding: 4px 8px; border-radius: 12px; font-size: 12px;">Active</span></td>
                            </tr>
                            <tr>
                                <td>5</td>
                                <td>
                                    <div style="font-weight: 600;">Sport Store</div>
                                    <div style="font-size: 12px; color: #666;">sportstore</div>
                                </td>
                                <td>Hoàng Văn E</td>
                                <td>250,000 VND</td>
                                <td style="color: #28a745;">+8%</td>
                                <td><span style="background: #d4edda; color: #155724; padding: 4px 8px; border-radius: 12px; font-size: 12px;">Active</span></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script>
            // Revenue Chart
            const revenueCtx = document.getElementById('revenueChart').getContext('2d');
            const revenueChart = new Chart(revenueCtx, {
                type: 'line',
                data: {
                    labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
                    datasets: [{
                            label: 'Doanh thu (triệu VND)',
                            data: [1.2, 1.5, 1.8, 2.1, 2.3, 2.5, 2.2, 2.4, 2.6, 2.8, 2.9, 3.1],
                            borderColor: '#667eea',
                            backgroundColor: 'rgba(102, 126, 234, 0.1)',
                            borderWidth: 3,
                            fill: true,
                            tension: 0.4
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
                                color: 'rgba(0,0,0,0.1)'
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

            // Shop Status Chart
            const shopStatusCtx = document.getElementById('shopStatusChart').getContext('2d');
            const shopStatusChart = new Chart(shopStatusCtx, {
                type: 'doughnut',
                data: {
                    labels: ['Hoạt động', 'Dùng thử', 'Hết hạn', 'Tạm ngưng'],
                    datasets: [{
                            data: [65, 20, 10, 5],
                            backgroundColor: [
                                '#28a745',
                                '#ffc107',
                                '#dc3545',
                                '#6c757d'
                            ],
                            borderWidth: 0
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

            // Functions
            function changeChartType(type) {
                revenueChart.config.type = type;
                revenueChart.update();
            }

            function generateReport() {
                const reportType = document.getElementById('reportType').value;
                const startDate = document.getElementById('startDate').value;
                const endDate = document.getElementById('endDate').value;

                console.log('Generating report:', {reportType, startDate, endDate});
                // Implement report generation logic here
                alert('Báo cáo đang được tạo...');
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

                // Add hover effects to stat cards
                const statCards = document.querySelectorAll('.stat-card');
                statCards.forEach(card => {
                    card.addEventListener('mouseenter', function () {
                        this.style.transform = 'translateY(-5px)';
                    });
                    card.addEventListener('mouseleave', function () {
                        this.style.transform = 'translateY(0)';
                    });
                });

                // Add chart button interactions
                const chartBtns = document.querySelectorAll('.chart-btn');
                chartBtns.forEach(btn => {
                    btn.addEventListener('click', function () {
                        chartBtns.forEach(b => b.style.background = 'white');
                        this.style.background = '#667eea';
                        this.style.color = 'white';
                    });
                });
            });
        </script>
    </body>
</html> 