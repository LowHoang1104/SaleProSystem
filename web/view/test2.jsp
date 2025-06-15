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
        /* === GLOBAL STYLES === */
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

        /* === SIDEBAR === */
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
            pointer-events: none;
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
        }

        .sidebar:not(.collapsed) .nav-link:hover,
        .sidebar:not(.collapsed) .nav-link.active {
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
            width: 0;
            pointer-events: none;
        }

        /* === MAIN CONTENT & HEADER === */
        .main-content {
            flex: 1;
            margin-left: 280px;
            padding: 2rem;
            transition: margin-left 0.3s ease;
        }

        .sidebar.collapsed + .main-content {
            margin-left: 80px;
        }

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

        /* === DASHBOARD & CONTENT CARDS === */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
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
        
        .card-icon {
            width: 48px; height: 48px; border-radius: 12px;
            display: flex; align-items: center; justify-content: center;
            margin-bottom: 1rem;
        }
        .card-icon.users { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .card-icon.orders { background: rgba(59, 130, 246, 0.1); color: #3b82f6; }
        .card-icon.revenue { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }
        .card-icon.products { background: rgba(239, 68, 68, 0.1); color: #ef4444; }

        .card-title { font-size: 0.9rem; color: #666; margin-bottom: 0.5rem; }
        .card-value { font-size: 2rem; font-weight: 700; color: #333; margin-bottom: 0.5rem; }
        .card-change { font-size: 0.85rem; display: flex; align-items: center; gap: 4px; }
        .card-change.positive { color: #22c55e; }
        .card-change.negative { color: #ef4444; }

        /* === GENERAL CONTENT & TABLE STYLES === */
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

        .content-title { font-size: 1.5rem; font-weight: 600; color: #333; }

        .btn {
            padding: 12px 24px; border: none; border-radius: 12px;
            cursor: pointer; font-weight: 500; transition: all 0.3s ease;
            display: inline-flex; align-items: center; gap: 8px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
        }

        .table-container {
            overflow-x: auto;
            border-radius: 12px;
            border: 1px solid rgba(0, 0, 0, 0.1);
        }

        table { width: 100%; border-collapse: collapse; }
        th, td { text-align: left; padding: 16px; border-bottom: 1px solid rgba(0, 0, 0, 0.1); vertical-align: middle; }
        th { background: rgba(102, 126, 234, 0.05); font-weight: 600; color: #555; }
        tr:hover { background: rgba(102, 126, 234, 0.02); }

        .status { padding: 6px 12px; border-radius: 20px; font-size: 0.8rem; font-weight: 500; }
        .status.active { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .status.inactive { background: rgba(239, 68, 68, 0.1); color: #ef4444; }
        .status.pending { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }

        .chart-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(20px);
            border-radius: 20px;
            padding: 2rem;
            margin-top: 2rem;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        }
        
        .page-content { display: none; }
        .page-content.active { display: block; }

        /* === CSS FOR NEW USER MANAGEMENT INTERFACE === */
        .table-toolbar {
            display: flex; justify-content: space-between; align-items: center;
            margin-bottom: 1.5rem; flex-wrap: wrap; gap: 1rem;
        }
        .search-wrapper { position: relative; display: flex; align-items: center; }
        .search-wrapper i { position: absolute; left: 16px; color: #999; }
        .search-wrapper input {
            padding: 12px 16px 12px 48px; border: 1px solid #ddd; border-radius: 12px;
            width: 300px; font-size: 0.9rem; transition: all 0.3s ease;
        }
        .search-wrapper input:focus {
            outline: none; border-color: #764ba2;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
        }
        .filter-group { display: flex; gap: 1rem; }
        .filter-group select {
            padding: 12px 16px; border: 1px solid #ddd; border-radius: 12px;
            background-color: white; font-size: 0.9rem; cursor: pointer;
        }
        .user-info-cell { display: flex; align-items: center; gap: 12px; }
        .user-avatar-table {
            width: 36px; height: 36px; border-radius: 50%;
            color: white; font-weight: 600; font-size: 0.9rem;
            display: flex; align-items: center; justify-content: center;
            flex-shrink: 0;
        }
        .user-name { font-weight: 600; color: #333; }
        .user-email { font-size: 0.85rem; color: #666; }
        
        .actions-menu { position: relative; display: inline-block; }
        .actions-btn {
            background: none; border: none; padding: 8px; border-radius: 50%;
            cursor: pointer; transition: background-color 0.2s;
        }
        .actions-btn:hover { background-color: rgba(0, 0, 0, 0.05); }
        .actions-dropdown {
            position: absolute; right: 0; top: 100%;
            background: white; border-radius: 12px; box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            z-index: 10; width: 160px; padding: 8px 0;
            display: none; opacity: 0; transform: translateY(10px);
            transition: opacity 0.2s ease, transform 0.2s ease;
        }
        .actions-dropdown.show { display: block; opacity: 1; transform: translateY(0); }
        .actions-dropdown a {
            display: flex; align-items: center; gap: 12px;
            padding: 10px 16px; text-decoration: none; color: #333; font-size: 0.9rem;
        }
        .actions-dropdown a:hover { background-color: rgba(102, 126, 234, 0.05); color: #667eea; }

        .pagination-controls {
            display: flex; justify-content: space-between; align-items: center;
            margin-top: 2rem; padding-top: 1rem; border-top: 1px solid rgba(0, 0, 0, 0.1);
            color: #666; font-size: 0.9rem;
        }
        .page-buttons { display: flex; align-items: center; gap: 8px; }
        .btn-page {
            background: none; border: 1px solid #ddd; border-radius: 8px;
            padding: 8px 14px; cursor: pointer; font-weight: 500;
            transition: all 0.2s ease; display: inline-flex; align-items: center; gap: 4px;
        }
        .btn-page:hover { border-color: #667eea; color: #667eea; }
        .btn-page.active {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white; border-color: transparent;
        }

        /* === RESPONSIVE STYLES === */
        @media (max-width: 768px) {
            .sidebar { width: 80px; }
            .main-content { margin-left: 80px; padding: 1rem; }
            .dashboard-grid { grid-template-columns: 1fr; }
            .header { padding: 1rem; flex-direction: column; align-items: flex-start; gap: 1rem; }
            .header-right { width: 100%; justify-content: space-between; }
            .table-toolbar { flex-direction: column; align-items: stretch; }
            .search-wrapper input { width: 100%; }
            .pagination-controls { flex-direction: column; gap: 1rem; }
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
                <div class="dashboard-grid" style="margin-bottom: 2rem;">
                    <div class="dashboard-card">
                        <div class="card-icon users"><i data-lucide="users" size="24"></i></div>
                        <div class="card-title">Tổng người dùng</div><div class="card-value">2,347</div>
                        <div class="card-change positive"><i data-lucide="trending-up" size="16"></i>+12% so với tháng trước</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon orders"><i data-lucide="shopping-cart" size="24"></i></div>
                        <div class="card-title">Đơn hàng</div><div class="card-value">1,234</div>
                        <div class="card-change positive"><i data-lucide="trending-up" size="16"></i>+8% so với tháng trước</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon revenue"><i data-lucide="dollar-sign" size="24"></i></div>
                        <div class="card-title">Doanh thu</div><div class="card-value">₫89.2M</div>
                        <div class="card-change positive"><i data-lucide="trending-up" size="16"></i>+15% so với tháng trước</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon products"><i data-lucide="package" size="24"></i></div>
                        <div class="card-title">Sản phẩm</div><div class="card-value">567</div>
                        <div class="card-change negative"><i data-lucide="trending-down" size="16"></i>-3% so với tháng trước</div>
                    </div>
                </div>
                <div class="chart-container">
                    <h3 style="margin-bottom: 1rem;">Biểu đồ doanh thu</h3>
                    <canvas id="revenueChart" width="400" height="200"></canvas>
                </div>
            </div>

            <!-- Users Content -->
            <div class="page-content" id="users">
                <div class="dashboard-grid" style="margin-bottom: 2rem;">
                    <div class="dashboard-card">
                        <div class="card-icon users"><i data-lucide="users" size="24"></i></div>
                        <div class="card-title">Tổng người dùng</div><div class="card-value">2,347</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon" style="background: rgba(34, 197, 94, 0.1); color: #22c55e;"><i data-lucide="user-check" size="24"></i></div>
                        <div class="card-title">Đang hoạt động</div><div class="card-value">2,180</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon" style="background: rgba(239, 68, 68, 0.1); color: #ef4444;"><i data-lucide="user-x" size="24"></i></div>
                        <div class="card-title">Tạm khóa</div><div class="card-value">167</div>
                    </div>
                    <div class="dashboard-card">
                        <div class="card-icon revenue"><i data-lucide="user-cog" size="24"></i></div>
                        <div class="card-title">Quản trị viên</div><div class="card-value">5</div>
                    </div>
                </div>
                <div class="content-area">
                    <div class="content-header"><h3 class="content-title">Danh sách người dùng</h3></div>
                    <div class="table-toolbar">
                        <div class="search-wrapper">
                            <i data-lucide="search" size="18"></i><input type="text" placeholder="Tìm kiếm người dùng...">
                        </div>
                        <div class="filter-group">
                            <select><option value="">Tất cả vai trò</option><option value="admin">Admin</option><option value="user">User</option><option value="editor">Editor</option></select>
                            <select><option value="">Tất cả trạng thái</option><option value="active">Hoạt động</option><option value="inactive">Tạm khóa</option></select>
                        </div>
                        <button class="btn btn-primary"><i data-lucide="user-plus" size="16"></i>Thêm người dùng</button>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th><input type="checkbox" id="selectAllCheckbox"></th><th>Tên người dùng</th><th>Vai trò</th><th>Trạng thái</th><th>Ngày tham gia</th><th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><input type="checkbox" class="row-checkbox"></td>
                                    <td><div class="user-info-cell"><div class="user-avatar-table" style="background-color: #7b2cbf;">N</div><div><div class="user-name">Nguyễn Văn A</div><div class="user-email">nguyenvana@email.com</div></div></div></td>
                                    <td>Admin</td><td><span class="status active">Hoạt động</span></td><td>12/06/2025</td>
                                    <td><div class="actions-menu"><button class="actions-btn"><i data-lucide="more-vertical" size="18"></i></button><div class="actions-dropdown"><a href="#"><i data-lucide="edit-3" size="14"></i> Sửa</a><a href="#"><i data-lucide="trash-2" size="14"></i> Xóa</a><a href="#"><i data-lucide="eye" size="14"></i> Xem chi tiết</a></div></div></td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" class="row-checkbox"></td>
                                    <td><div class="user-info-cell"><div class="user-avatar-table" style="background-color: #d90429;">T</div><div><div class="user-name">Trần Thị B</div><div class="user-email">tranthib@email.com</div></div></div></td>
                                    <td>User</td><td><span class="status active">Hoạt động</span></td><td>11/06/2025</td>
                                    <td><div class="actions-menu"><button class="actions-btn"><i data-lucide="more-vertical" size="18"></i></button><div class="actions-dropdown"><a href="#"><i data-lucide="edit-3" size="14"></i> Sửa</a><a href="#"><i data-lucide="trash-2" size="14"></i> Xóa</a><a href="#"><i data-lucide="eye" size="14"></i> Xem chi tiết</a></div></div></td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" class="row-checkbox"></td>
                                    <td><div class="user-info-cell"><div class="user-avatar-table" style="background-color: #fca311;">L</div><div><div class="user-name">Lê Văn C</div><div class="user-email">levanc@email.com</div></div></div></td>
                                    <td>User</td><td><span class="status inactive">Tạm khóa</span></td><td>10/06/2025</td>
                                    <td><div class="actions-menu"><button class="actions-btn"><i data-lucide="more-vertical" size="18"></i></button><div class="actions-dropdown"><a href="#"><i data-lucide="edit-3" size="14"></i> Sửa</a><a href="#"><i data-lucide="trash-2" size="14"></i> Xóa</a><a href="#"><i data-lucide="eye" size="14"></i> Xem chi tiết</a></div></div></td>
                                </tr>
                                <tr>
                                    <td><input type="checkbox" class="row-checkbox"></td>
                                    <td><div class="user-info-cell"><div class="user-avatar-table" style="background-color: #00b4d8;">P</div><div><div class="user-name">Phạm Thị D</div><div class="user-email">phamthid@email.com</div></div></div></td>
                                    <td>Editor</td><td><span class="status active">Hoạt động</span></td><td>09/06/2025</td>
                                    <td><div class="actions-menu"><button class="actions-btn"><i data-lucide="more-vertical" size="18"></i></button><div class="actions-dropdown"><a href="#"><i data-lucide="edit-3" size="14"></i> Sửa</a><a href="#"><i data-lucide="trash-2" size="14"></i> Xóa</a><a href="#"><i data-lucide="eye" size="14"></i> Xem chi tiết</a></div></div></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination-controls">
                        <span>Hiển thị 1-4 trên 2,347 người dùng</span>
                        <div class="page-buttons"><button class="btn-page"><i data-lucide="arrow-left" size="16"></i> Trước</button><button class="btn-page active">1</button><button class="btn-page">2</button><button class="btn-page">3</button><span>...</span><button class="btn-page">587</button><button class="btn-page">Sau <i data-lucide="arrow-right" size="16"></i></button></div>
                    </div>
                </div>
            </div>

            <!-- Orders Content -->
            <div class="page-content" id="orders">
                <div class="content-area"><h3 class="content-title">Quản lý đơn hàng sắp ra mắt...</h3></div>
            </div>
            <!-- Products Content -->
            <div class="page-content" id="products">
                <div class="content-area"><h3 class="content-title">Quản lý sản phẩm sắp ra mắt...</h3></div>
            </div>
            <!-- Analytics Content -->
            <div class="page-content" id="analytics">
                <div class="content-area"><h3 class="content-title">Trang thống kê sắp ra mắt...</h3></div>
            </div>
            <!-- Settings Content -->
            <div class="page-content" id="settings">
                <div class="content-area"><h3 class="content-title">Trang cài đặt sắp ra mắt...</h3></div>
            </div>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            // Initialize Lucide icons
            lucide.createIcons();

            // === CACHED DOM ELEMENTS ===
            const sidebar = document.getElementById('sidebar');
            const toggleBtn = document.getElementById('toggleSidebar');
            const navLinks = document.querySelectorAll('.nav-link');
            const pageContents = document.querySelectorAll('.page-content');
            const pageTitle = document.getElementById('pageTitle');

            // === EVENT LISTENERS ===
            
            // Sidebar toggle
            toggleBtn.addEventListener('click', () => {
                sidebar.classList.toggle('collapsed');
            });

            // Navigation
            navLinks.forEach(link => {
                link.addEventListener('click', handleNavClick);
            });

            // Global click listener for dynamic elements like action menus
            document.body.addEventListener('click', handleGlobalClick);
            
            // === FUNCTIONS ===

            // Handle Navigation
            function handleNavClick(e) {
                e.preventDefault();
                const link = e.currentTarget;
                const targetPageId = link.getAttribute('data-page');
                
                navLinks.forEach(l => l.classList.remove('active'));
                link.classList.add('active');
                
                pageContents.forEach(content => content.classList.remove('active'));
                
                const targetPage = document.getElementById(targetPageId);
                if (targetPage) {
                    targetPage.classList.add('active');
                    lucide.createIcons(); // Re-create icons for the new content
                }
                
                const pageTitles = {
                    'dashboard': 'Dashboard', 'users': 'Quản lý người dùng',
                    'orders': 'Quản lý đơn hàng', 'products': 'Quản lý sản phẩm',
                    'analytics': 'Thống kê & Báo cáo', 'settings': 'Cài đặt hệ thống'
                };
                pageTitle.textContent = pageTitles[targetPageId] || 'Dashboard';
            }
            
            // Handle various clicks throughout the document
            function handleGlobalClick(e) {
                // Action menu toggle
                const isActionsBtn = e.target.closest('.actions-btn');
                document.querySelectorAll('.actions-dropdown.show').forEach(openDropdown => {
                    if (!openDropdown.parentElement.contains(isActionsBtn)) {
                        openDropdown.classList.remove('show');
                    }
                });
                if (isActionsBtn) {
                    isActionsBtn.nextElementSibling.classList.toggle('show');
                }
                
                // Button ripple effect
                const btn = e.target.closest('.btn');
                if (btn) {
                    createRipple(e, btn);
                }

                // Notification Triggers
                if(e.target.closest('.btn-primary') && e.target.closest('.btn-primary').textContent.includes('Thêm')) {
                    showNotification('Tính năng thêm mới đang được phát triển!');
                } else if (e.target.closest('a') && e.target.closest('a').textContent.includes('Sửa')) {
                    showNotification('Tính năng chỉnh sửa đang được phát triển!');
                } else if (e.target.closest('a') && e.target.closest('a').textContent.includes('Xem chi tiết')) {
                    showNotification('Đang tải chi tiết người dùng...');
                } else if (e.target.closest('a') && e.target.closest('a').textContent.includes('Xóa')) {
                    showNotification('Tính năng xóa đang được phát triển!', 'error');
                }
            }

            // Create Ripple Effect
            function createRipple(e, btn) {
                const ripple = document.createElement('span');
                const rect = btn.getBoundingClientRect();
                const size = Math.max(rect.width, rect.height);
                const x = e.clientX - rect.left - size / 2;
                const y = e.clientY - rect.top - size / 2;
                ripple.style.cssText = `position: absolute; width: ${size}px; height: ${size}px; left: ${x}px; top: ${y}px; background: rgba(255, 255, 255, 0.5); border-radius: 50%; transform: scale(0); animation: ripple 0.6s ease-out; pointer-events: none;`;
                btn.style.position = 'relative'; btn.style.overflow = 'hidden';
                btn.appendChild(ripple);
                setTimeout(() => ripple.remove(), 600);
            }

            // Notification System
            function showNotification(message, type = 'success') {
                const notification = document.createElement('div');
                const bgColor = type === 'success' ? 'linear-gradient(135deg, #22c55e, #16a34a)' : 'linear-gradient(135deg, #ef4444, #dc2626)';
                notification.style.cssText = `position: fixed; top: 20px; right: 20px; padding: 16px 24px; background: ${bgColor}; color: white; border-radius: 12px; box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2); z-index: 10000; font-weight: 500; transform: translateX(120%); transition: transform 0.4s cubic-bezier(0.25, 0.46, 0.45, 0.94);`;
                notification.textContent = message;
                document.body.appendChild(notification);
                setTimeout(() => notification.style.transform = 'translateX(0)', 10);
                setTimeout(() => {
                    notification.style.transform = 'translateX(120%)';
                    setTimeout(() => notification.remove(), 400);
                }, 3000);
            }

            // Chart.js Initialization
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
                    responsive: true, maintainAspectRatio: false,
                    plugins: { legend: { display: false } },
                    scales: { y: { beginAtZero: true }, x: {} }
                }
            });

            // CSS Animations Injection
            const style = document.createElement('style');
            style.textContent = `@keyframes ripple { to { transform: scale(2); opacity: 0; } }`;
            document.head.appendChild(style);
        });
    </script>
</body>
</html>