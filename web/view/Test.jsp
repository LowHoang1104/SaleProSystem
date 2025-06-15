<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lucide/0.263.1/umd/lucide.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }

        .admin-container {
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 280px;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-right: 1px solid rgba(255, 255, 255, 0.2);
            padding: 2rem 0;
            position: fixed;
            height: 100vh;
            left: 0;
            top: 0;
            z-index: 1000;
            box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .sidebar.collapsed {
            width: 80px;
        }

        .logo {
            padding: 0 2rem;
            margin-bottom: 2rem;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .logo h1 {
            font-size: 1.5rem;
            font-weight: 700;
            background: linear-gradient(135deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            transition: opacity 0.3s ease;
        }

        .sidebar.collapsed .logo h1 {
            opacity: 0;
        }

        .nav-menu {
            list-style: none;
        }

        .nav-item {
            margin-bottom: 8px;
        }

        .nav-link {
            display: flex;
            align-items: center;
            gap: 16px;
            padding: 16px 2rem;
            text-decoration: none;
            color: #555;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .nav-link:hover, .nav-link.active {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            transform: translateX(8px);
        }

        .nav-link::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            width: 4px;
            height: 100%;
            background: linear-gradient(135deg, #667eea, #764ba2);
            transform: scaleY(0);
            transition: transform 0.3s ease;
        }

        .nav-link.active::before {
            transform: scaleY(1);
        }

        .sidebar.collapsed .nav-link span {
            opacity: 0;
        }

        /* Main Content */
        .main-content {
            flex: 1;
            margin-left: 280px;
            padding: 2rem;
            transition: margin-left 0.3s ease;
        }

        .sidebar.collapsed + .main-content {
            margin-left: 80px;
        }

        /* Header */
        .header {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            padding: 1.5rem 2rem;
            border-radius: 20px;
            margin-bottom: 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        }

        .header-left {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .toggle-btn {
            background: none;
            border: none;
            cursor: pointer;
            padding: 8px;
            border-radius: 12px;
            transition: all 0.3s ease;
        }

        .toggle-btn:hover {
            background: rgba(102, 126, 234, 0.1);
        }

        .header-right {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .user-profile {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 8px 16px;
            background: rgba(102, 126, 234, 0.1);
            border-radius: 25px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .user-profile:hover {
            background: rgba(102, 126, 234, 0.2);
            transform: translateY(-2px);
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
        }

        /* Dashboard Cards */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
            margin-bottom: 2rem;
        }

        .dashboard-card {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            padding: 2rem;
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .dashboard-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(135deg, #667eea, #764ba2);
        }

        .dashboard-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 16px 48px rgba(0, 0, 0, 0.15);
        }

        .card-header {
            display: flex;
            justify-content: between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .card-icon {
            width: 48px;
            height: 48px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 1rem;
        }

        .card-icon.users { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .card-icon.orders { background: rgba(59, 130, 246, 0.1); color: #3b82f6; }
        .card-icon.revenue { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }
        .card-icon.products { background: rgba(239, 68, 68, 0.1); color: #ef4444; }

        .card-title {
            font-size: 0.9rem;
            color: #666;
            margin-bottom: 0.5rem;
        }

        .card-value {
            font-size: 2rem;
            font-weight: 700;
            color: #333;
            margin-bottom: 0.5rem;
        }

        .card-change {
            font-size: 0.85rem;
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .card-change.positive { color: #22c55e; }
        .card-change.negative { color: #ef4444; }

        /* Content Area */
        .content-area {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 20px;
            padding: 2rem;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        }

        .content-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        }

        .content-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #333;
        }

        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
        }

        /* Table */
        .table-container {
            overflow-x: auto;
            border-radius: 12px;
            border: 1px solid rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            text-align: left;
            padding: 16px;
            border-bottom: 1px solid rgba(0, 0, 0, 0.1);
        }

        th {
            background: rgba(102, 126, 234, 0.05);
            font-weight: 600;
            color: #555;
        }

        tr:hover {
            background: rgba(102, 126, 234, 0.02);
        }

        .status {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 500;
        }

        .status.active { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .status.inactive { background: rgba(239, 68, 68, 0.1); color: #ef4444; }
        .status.pending { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }

        /* Chart Container */
        .chart-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 20px;
            padding: 2rem;
            margin-top: 2rem;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        }

        .page-content {
            display: none;
        }

        .page-content.active {
            display: block;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .sidebar {
                width: 80px;
            }
            
            .main-content {
                margin-left: 80px;
            }
            
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
            
            .header {
                padding: 1rem;
                flex-direction: column;
                gap: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="admin-container">
        <!-- Sidebar -->
        <nav class="sidebar" id="sidebar">
            <div class="logo">
                <div class="card-icon users">
                    <i data-lucide="shield-check" size="24"></i>
                </div>
                <h1>Admin Panel</h1>
            </div>
            
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="#" class="nav-link active" data-page="dashboard">
                        <i data-lucide="layout-dashboard" size="20"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" data-page="users">
                        <i data-lucide="users" size="20"></i>
                        <span>Người dùng</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" data-page="orders">
                        <i data-lucide="shopping-cart" size="20"></i>
                        <span>Đơn hàng</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" data-page="products">
                        <i data-lucide="package" size="20"></i>
                        <span>Sản phẩm</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" data-page="analytics">
                        <i data-lucide="bar-chart-3" size="20"></i>
                        <span>Thống kê</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link" data-page="settings">
                        <i data-lucide="settings" size="20"></i>
                        <span>Cài đặt</span>
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Main Content -->
        <main class="main-content">
            <!-- Header -->
            <header class="header">
                <div class="header-left">
                    <button class="toggle-btn" id="toggleSidebar">
                        <i data-lucide="menu" size="20"></i>
                    </button>
                    <h2 id="pageTitle">Dashboard</h2>
                </div>
                <div class="header-right">
                    <button class="btn btn-primary">
                        <i data-lucide="plus" size="16"></i>
                        Thêm mới
                    </button>
                    <div class="user-profile">
                        <div class="user-avatar">A</div>
                        <div>
                            <div style="font-weight: 600; font-size: 0.9rem;">Admin</div>
                            <div style="font-size: 0.8rem; color: #666;">admin@example.com</div>
                        </div>
                    </div>
                </div>
            </header>

            <!-- Dashboard Content -->
            <div class="page-content active" id="dashboard">
                <!-- Dashboard Cards -->
                <div class="dashboard-grid">
                    <div class="dashboard-card">
                        <div class="card-icon users">
                            <i data-lucide="users" size="24"></i>
                        </div>
                        <div class="card-title">Tổng người dùng</div>
                        <div class="card-value">2,347</div>
                        <div class="card-change positive">
                            <i data-lucide="trending-up" size="16"></i>
                            +12% so với tháng trước
                        </div>
                    </div>

                    <div class="dashboard-card">
                        <div class="card-icon orders">
                            <i data-lucide="shopping-cart" size="24"></i>
                        </div>
                        <div class="card-title">Đơn hàng</div>
                        <div class="card-value">1,234</div>
                        <div class="card-change positive">
                            <i data-lucide="trending-up" size="16"></i>
                            +8% so với tháng trước
                        </div>
                    </div>

                    <div class="dashboard-card">
                        <div class="card-icon revenue">
                            <i data-lucide="dollar-sign" size="24"></i>
                        </div>
                        <div class="card-title">Doanh thu</div>
                        <div class="card-value">₫89.2M</div>
                        <div class="card-change positive">
                            <i data-lucide="trending-up" size="16"></i>
                            +15% so với tháng trước
                        </div>
                    </div>

                    <div class="dashboard-card">
                        <div class="card-icon products">
                            <i data-lucide="package" size="24"></i>
                        </div>
                        <div class="card-title">Sản phẩm</div>
                        <div class="card-value">567</div>
                        <div class="card-change negative">
                            <i data-lucide="trending-down" size="16"></i>
                            -3% so với tháng trước
                        </div>
                    </div>
                </div>

                <!-- Chart -->
                <div class="chart-container">
                    <h3 style="margin-bottom: 1rem;">Biểu đồ doanh thu</h3>
                    <canvas id="revenueChart" width="400" height="200"></canvas>
                </div>
            </div>

            <!-- Users Content -->
            <div class="page-content" id="users">
                <div class="content-area">
                    <div class="content-header">
                        <h3 class="content-title">Quản lý người dùng</h3>
                        <button class="btn btn-primary">
                            <i data-lucide="user-plus" size="16"></i>
                            Thêm người dùng
                        </button>
                    </div>
                    
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên</th>
                                    <th>Email</th>
                                    <th>Vai trò</th>
                                    <th>Trạng thái</th>
                                    <th>Ngày tạo</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>#001</td>
                                    <td>Nguyễn Văn A</td>
                                    <td>nguyenvana@email.com</td>
                                    <td>Admin</td>
                                    <td><span class="status active">Hoạt động</span></td>
                                    <td>12/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Sửa</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>#002</td>
                                    <td>Trần Thị B</td>
                                    <td>tranthib@email.com</td>
                                    <td>User</td>
                                    <td><span class="status active">Hoạt động</span></td>
                                    <td>11/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Sửa</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>#003</td>
                                    <td>Lê Văn C</td>
                                    <td>levanc@email.com</td>
                                    <td>User</td>
                                    <td><span class="status inactive">Tạm khóa</span></td>
                                    <td>10/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Sửa</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Orders Content -->
            <div class="page-content" id="orders">
                <div class="content-area">
                    <div class="content-header">
                        <h3 class="content-title">Quản lý đơn hàng</h3>
                        <button class="btn btn-primary">
                            <i data-lucide="plus" size="16"></i>
                            Tạo đơn hàng
                        </button>
                    </div>
                    
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Mã đơn</th>
                                    <th>Khách hàng</th>
                                    <th>Sản phẩm</th>
                                    <th>Tổng tiền</th>
                                    <th>Trạng thái</th>
                                    <th>Ngày đặt</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>#DH001</td>
                                    <td>Nguyễn Văn A</td>
                                    <td>iPhone 15 Pro</td>
                                    <td>₫29.990.000</td>
                                    <td><span class="status active">Đã giao</span></td>
                                    <td>12/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Chi tiết</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>#DH002</td>
                                    <td>Trần Thị B</td>
                                    <td>Samsung Galaxy S24</td>
                                    <td>₫22.990.000</td>
                                    <td><span class="status pending">Đang xử lý</span></td>
                                    <td>11/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Chi tiết</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>#DH003</td>
                                    <td>Lê Văn C</td>
                                    <td>MacBook Pro M3</td>
                                    <td>₫52.990.000</td>
                                    <td><span class="status inactive">Đã hủy</span></td>
                                    <td>10/06/2025</td>
                                    <td>
                                        <button class="btn btn-primary" style="padding: 6px 12px; font-size: 0.8rem;">Chi tiết</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Products Content -->
            <div class="page-content" id="products">
                <div class="content-area">
                    <div class="content-header">
                        <h3 class="content-title">Quản lý sản phẩm</h3>
                        <button class="btn btn-primary">
                            <i data-lucide="package-plus" size="16"></i>
                            Thêm sản phẩm
                        </button>
                    </div>
                    
                    <div class="dashboard-grid">
                        <div class="dashboard-card">
                            <div class="card-icon products">
                                <i data-lucide="smartphone" size="24"></i>
                            </div>
                            <div class="card-title">iPhone 15 Pro</div>
                            <div class="card-value">₫29.990.000</div>
                            <div class="card-change positive">Còn hàng: 45</div>
                        </div>
                        <div class="dashboard-card">
                            <div class="card-icon orders">
                                <i data-lucide="laptop" size="24"></i>
                            </div>
                            <div class="card-title">MacBook Pro M3</div>
                            <div class="card-value">₫52.990.000</div>
                            <div class="card-change positive">Còn hàng: 23</div>
                        </div>
                        <div class="dashboard-card">
                            <div class="card-icon users">
                                <i data-lucide="headphones" size="24"></i>
                            </div>
                            <div class="card-title">AirPods Pro</div>
                            <div class="card-value">₫6.990.000</div>
                            <div class="card-change negative">Hết hàng</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Analytics Content -->
            <div class="page-content" id="analytics">
                <div class="content-area">
                    <div class="content-header">
                        <h3 class="content-title">Thống kê & Báo cáo</h3>
                    </div>
                    
                    <div class="dashboard-grid">
                        <div class="dashboard-card">
                            <div class="card-icon revenue">
                                <i data-lucide="trending-up" size="24"></i>
                            </div>
                            <div class="card-title">Tăng trưởng doanh thu</div>
                            <div class="card-value">+15.2%</div>
                            <div class="card-change positive">So với tháng trước</div>
                        </div>
                        <div class="dashboard-card">
                            <div class="card-icon users">
                                <i data-lucide="users" size="24"></i>
                            </div>
                            <div class="card-title">Người dùng mới</div>
                            <div class="card-value">+234</div>
                            <div class="card-change positive">Tuần này</div>
                        </div>
                        <div class="dashboard-card">
                            <div class="card-icon orders">
                                <i data-lucide="activity" size="24"></i>
                            </div>
                            <div class="card-title">Tỷ lệ chuyển đổi</div>
                            <div class="card-value">3.4%</div>
                            <div class="card-change positive">+0.8% so với trước</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Settings Content -->
            <div class="page-content" id="settings">
                <div class="content-area">
                    <div class="content-header">
                        <h3 class="content-title">Cài đặt hệ thống</h3>
                    </div>
                    
                    <div style="display: grid; gap: 2rem;">
                        <div style="padding: 2rem; background: rgba(102, 126, 234, 0.05); border-radius: 16px;">
                            <h4 style="margin-bottom: 1rem; color: #333;">Cài đặt chung</h4>
                            <div style="margin-bottom: 1rem;">
                                <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Tên website</label>
                                <input type="text" value="Admin Dashboard" style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px;">
                            </div>
                            <div style="margin-bottom: 1rem;">
                                <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Email liên hệ</label>
                                <input type="email" value="admin@example.com" style="width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px;">
                            </div>
                        </div>
                        
                        <div style="padding: 2rem; background: rgba(34, 197, 94, 0.05); border-radius: 16px;">
                            <h4 style="margin-bottom: 1rem; color: #333;">Bảo mật</h4>
                            <button class="btn btn-primary">Đổi mật khẩu</button>
                            <button class="btn btn-primary" style="margin-left: 1rem;">Bật xác thực 2FA</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script>
        // Initialize Lucide icons
        lucide.createIcons();

        // Sidebar toggle
        const sidebar = document.getElementById('sidebar');
        const toggleBtn = document.getElementById('toggleSidebar');
        
        toggleBtn.addEventListener('click', () => {
            sidebar.classList.toggle('collapsed');
        });

        // Navigation
        const navLinks = document.querySelectorAll('.nav-link');
        const pageContents = document.querySelectorAll('.page-content');
        const pageTitle = document.getElementById('pageTitle');

        navLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                
                // Remove active class from all links
                navLinks.forEach(l => l.classList.remove('active'));
                
                // Add active class to clicked link
                link.classList.add('active');
                
                // Hide all page contents
                pageContents.forEach(content => content.classList.remove('active'));
                
                // Show selected page content
                const targetPage = link.getAttribute('data-page');
                document.getElementById(targetPage).classList.add('active');
                
                // Update page title
                const pageTitles = {
                    'dashboard': 'Dashboard',
                    'users': 'Quản lý người dùng',
                    'orders': 'Quản lý đơn hàng',
                    'products': 'Quản lý sản phẩm',
                    'analytics': 'Thống kê & Báo cáo',
                    'settings': 'Cài đặt hệ thống'
                };
                pageTitle.textContent = pageTitles[targetPage];
            });
        });

        // Chart initialization
        const ctx = document.getElementById('revenueChart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
                datasets: [{
                    label: 'Doanh thu (triệu VNĐ)',
                    data: [65, 72, 68, 85, 92, 89],
                    borderColor: 'rgb(102, 126, 234)',
                    backgroundColor: 'rgba(102, 126, 234, 0.1)',
                    tension: 0.4,
                    fill: true
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
                            color: 'rgba(0, 0, 0, 0.1)'
                        }
                    },
                    x: {
                        grid: {
                            color: 'rgba(0, 0, 0, 0.1)'
                        }
                    }
                }
            }
        });

        // Add some interactive effects
        document.querySelectorAll('.dashboard-card').forEach(card => {
            card.addEventListener('mouseenter', () => {
                card.style.transform = 'translateY(-8px) scale(1.02)';
            });
            
            card.addEventListener('mouseleave', () => {
                card.style.transform = 'translateY(0) scale(1)';
            });
        });

        // Add click effects to buttons
        document.querySelectorAll('.btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                // Create ripple effect
                const ripple = document.createElement('span');
                const rect = btn.getBoundingClientRect();
                const size = Math.max(rect.width, rect.height);
                const x = e.clientX - rect.left - size / 2;
                const y = e.clientY - rect.top - size / 2;
                
                ripple.style.cssText = `
                    position: absolute;
                    width: ${size}px;
                    height: ${size}px;
                    left: ${x}px;
                    top: ${y}px;
                    background: rgba(255, 255, 255, 0.5);
                    border-radius: 50%;
                    transform: scale(0);
                    animation: ripple 0.6s ease-out;
                    pointer-events: none;
                `;
                
                btn.style.position = 'relative';
                btn.style.overflow = 'hidden';
                btn.appendChild(ripple);
                
                setTimeout(() => {
                    ripple.remove();
                }, 600);
            });
        });

        // Add CSS animation for ripple effect
        const style = document.createElement('style');
        style.textContent = `
            @keyframes ripple {
                to {
                    transform: scale(2);
                    opacity: 0;
                }
            }
            
            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            
            .dashboard-card {
                animation: fadeIn 0.6s ease-out;
            }
            
            .dashboard-card:nth-child(1) { animation-delay: 0.1s; }
            .dashboard-card:nth-child(2) { animation-delay: 0.2s; }
            .dashboard-card:nth-child(3) { animation-delay: 0.3s; }
            .dashboard-card:nth-child(4) { animation-delay: 0.4s; }
        `;
        document.head.appendChild(style);

        // Add notification system
        function showNotification(message, type = 'success') {
            const notification = document.createElement('div');
            const bgColor = type === 'success' 
                ? 'linear-gradient(135deg, #22c55e, #16a34a)' 
                : 'linear-gradient(135deg, #ef4444, #dc2626)';
            
            notification.style.cssText = `
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 16px 24px;
                background: ${bgColor};
                color: white;
                border-radius: 12px;
                box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
                z-index: 10000;
                font-weight: 500;
                transform: translateX(100%);
                transition: transform 0.3s ease;
            `;
            notification.textContent = message;
            
            document.body.appendChild(notification);
            
            setTimeout(() => {
                notification.style.transform = 'translateX(0)';
            }, 100);
            
            setTimeout(() => {
                notification.style.transform = 'translateX(100%)';
                setTimeout(() => {
                    notification.remove();
                }, 300);
            }, 3000);
        }

        // Add click handlers for action buttons
        document.addEventListener('click', (e) => {
            if (e.target.textContent.includes('Thêm')) {
                showNotification('Tính năng thêm mới đang được phát triển!');
            } else if (e.target.textContent.includes('Sửa')) {
                showNotification('Tính năng chỉnh sửa đang được phát triển!');
            } else if (e.target.textContent.includes('Chi tiết')) {
                showNotification('Đang tải chi tiết...');
            }
        });

        // Add real-time clock
        function updateClock() {
            const now = new Date();
            const timeString = now.toLocaleTimeString('vi-VN');
            const dateString = now.toLocaleDateString('vi-VN');
            
            // You can add a clock element if needed
            console.log(`${dateString} ${timeString}`);
        }
        
        setInterval(updateClock, 1000);
    </script>
</body>
</html>