<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Shop Owners - Super Admin</title>
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

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
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

            /* Filters and Search */
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

            .search-btn {
                background: linear-gradient(45deg, #667eea, #764ba2);
                color: white;
                border: none;
                padding: 12px 20px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 600;
                transition: all 0.3s ease;
            }

            .search-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
            }

            /* Table */
            .table-container {
                background: white;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .table-header {
                padding: 20px;
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

            .table-actions {
                display: flex;
                gap: 10px;
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
                vertical-align: middle;
            }

            .table tbody tr:hover {
                background-color: #f8f9fa;
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

            .action-buttons {
                display: flex;
                gap: 8px;
            }

            .btn-sm {
                padding: 6px 12px;
                font-size: 12px;
                border-radius: 6px;
            }

            .btn-icon {
                width: 32px;
                height: 32px;
                padding: 0;
                display: flex;
                align-items: center;
                justify-content: center;
                border-radius: 6px;
            }

            /* Pagination */
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 20px;
                gap: 10px;
            }

            .page-link {
                padding: 8px 12px;
                border: 1px solid #dee2e6;
                background: white;
                color: #667eea;
                text-decoration: none;
                border-radius: 6px;
                transition: all 0.3s ease;
            }

            .page-link:hover,
            .page-link.active {
                background: #667eea;
                color: white;
                border-color: #667eea;
            }

            /* Modal */
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

            .modal-content {
                background-color: white;
                margin: 5% auto;
                padding: 0;
                border-radius: 15px;
                width: 90%;
                max-width: 600px;
                box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                animation: modalSlideIn 0.3s ease;
            }

            @keyframes modalSlideIn {
                from {
                    opacity: 0;
                    transform: translateY(-50px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .modal-header {
                padding: 20px 25px;
                border-bottom: 1px solid #eee;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .modal-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
            }

            .close {
                font-size: 24px;
                font-weight: bold;
                color: #aaa;
                cursor: pointer;
                transition: color 0.3s ease;
            }

            .close:hover {
                color: #333;
            }

            .modal-body {
                padding: 25px;
            }

            .modal-footer {
                padding: 20px 25px;
                border-top: 1px solid #eee;
                display: flex;
                justify-content: flex-end;
                gap: 10px;
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

                .filters-grid {
                    grid-template-columns: 1fr;
                }

                .header {
                    flex-direction: column;
                    gap: 15px;
                }

                .table {
                    font-size: 14px;
                }

                .action-buttons {
                    flex-direction: column;
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
                    <h1>Quản lý Shop Owners</h1>
                    <div class="header-actions">
                        <button class="btn btn-primary" onclick="openAddModal()">
                            <i class="fas fa-plus"></i>
                            Thêm Shop mới
                        </button>
                        <button class="btn btn-success">
                            <i class="fas fa-download"></i>
                            Xuất Excel
                        </button>
                    </div>
                </div>

                <!-- Filters -->
                <form action="ShopOwnerController" method="post">
                    <div class="filters-section">
                        <div class="filters-grid">
                            <div class="form-group">
                                <label for="searchShop">Tìm kiếm Shop</label>
                                <input value="${shop}" name="shop" type="text" id="searchShop" placeholder="Nhập tên shop...">
                            </div>
                            <div class="form-group">
                                <label  for="searchOwner">Tìm kiếm Owner</label>
                                <input value="${shopOwner}" name="shopOwner" type="text" id="searchOwner" placeholder="Nhập tên owner...">
                            </div>
                            <div class="form-group">
                                <label>Trạng thái</label>
                                <select name="status">
                                    <option value="" <c:if test="${empty status}">selected</c:if>>Tất cả</option>

                                        <option value="Active" <c:if test="${status eq 'Active'}">selected</c:if>>Hoạt động</option>

                                        <option value="Trial" <c:if test="${status eq 'Trial'}">selected</c:if>>Dùng thử</option>

                                        <option value="Expired" <c:if test="${status eq 'Expired'}">selected</c:if>>Hết hạn</option>

                                        <option value="Suspended" <c:if test="${status eq 'Suspended'}">selected</c:if>>Tạm ngưng</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="dateFilter">Ngày đăng ký</label>
                                    <input value="${date}" name="date" type="date" id="dateFilter">
                            </div>
                            <div class="form-group">
                                <button type="submit"  class="search-btn" >
                                    <i class="fas fa-search"></i>
                                    Tìm kiếm
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- Table -->
                <div class="table-container">
                    <div class="table-header">
                        <h3 class="table-title">Danh sách Shop Owners</h3>
                        <div class="table-actions">
                            <span>Tổng: <strong>${totalShops}</strong> shops</span>
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên Shop</th>
                                <th>Chủ sở hữu</th>
                                <th>Email</th>
                                <th>Số điện thoại</th>
                                <th>Trạng thái</th>
                                <th>Ngày đăng ký</th>
                                <th>Hết hạn</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="item">
                                <tr>
                                    <td>${item.getShopOwnerID()}</td>
                                    <td>
                                        <div style="font-weight: 600;">${item.getShopName()}</div>
                                    </td>
                                    <td>${item.getOwnerName()}</td>
                                    <td>${item.getEmail()}</td>
                                    <td>${item.getPhone()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${item.getSubscriptionStatus() eq 'Trial'}">
                                                <span class="status-badge status-trial">Dùng thử</span>
                                            </c:when>
                                            <c:when test="${item.getSubscriptionStatus() eq 'Active'}">
                                                <span class="status-badge status-active">Hoạt động</span>
                                            </c:when>
                                            <c:when test="${item.getSubscriptionStatus() eq 'Expired'}">
                                                <span class="status-badge status-expired">Hết hạn</span>
                                            </c:when>
                                            <c:when test="${item.getSubscriptionStatus() eq 'Suspended'}">
                                                <span class="status-badge status-suspended">Tạm ngưng</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-suspended">${item.getSubscriptionStatus()}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="date-cell">${item.getCreatedAt()}</td>
                                    <td class="date-cell">${item.getSubscriptionEndDate()}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <button class="btn btn-sm btn-primary btn-icon" onclick="viewShop(${item.getShopOwnerID()})" title="Xem chi tiết">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                            <button class="btn btn-sm btn-warning btn-icon" onclick="editShop(${item.getShopOwnerID()})" title="Chỉnh sửa">
                                                <i class="fas fa-edit"></i>
                                            </button>

                                            <c:choose>
                                                <c:when test="${item.getSubscriptionStatus() eq 'Active'}">
                                                    <button class="btn btn-sm btn-danger btn-icon" onclick="suspendShop(${item.getShopOwnerID()})" title="Tạm ngưng">
                                                        <i class="fas fa-ban"></i>
                                                    </button>
                                                </c:when>
                                                <c:when test="${item.getSubscriptionStatus() eq 'Suspended'}">
                                                    <button class="btn btn-sm btn-success btn-icon" onclick="activateShop(${item.getShopOwnerID()})" title="Kích hoạt">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                </c:when>
                                                <c:when test="${item.getSubscriptionStatus() eq 'Expired'}">
                                                    <button class="btn btn-sm btn-success btn-icon" onclick="renewShop(${item.getShopOwnerID()})" title="Gia hạn">
                                                        <i class="fas fa-sync"></i>
                                                    </button>
                                                </c:when>
                                                <c:when test="${item.getSubscriptionStatus() eq 'Trial'}">
                                                    <button class="btn btn-sm btn-success btn-icon" onclick="activateShop(${item.getShopOwnerID()})" title="Kích hoạt">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination">
                        <a href="#" class="page-link">«</a>
                        <a href="#" class="page-link active">1</a>
                        <a href="#" class="page-link">2</a>
                        <a href="#" class="page-link">3</a>
                        <a href="#" class="page-link">»</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Add Shop Modal -->
        <div id="addShopModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Thêm Shop mới</h3>
                    <span class="close" onclick="closeModal('addShopModal')">&times;</span>
                </div>
                <div class="modal-body">
                    <form id="addShopForm">
                        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                            <div class="form-group">
                                <label for="shopName">Tên Shop *</label>
                                <input type="text" id="shopName" name="shopName" required>
                            </div>
                            <div class="form-group">
                                <label for="ownerName">Tên chủ sở hữu *</label>
                                <input type="text" id="ownerName" name="ownerName" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email *</label>
                                <input type="email" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Số điện thoại</label>
                                <input type="tel" id="phone" name="phone">
                            </div>
                            <div class="form-group">
                                <label for="subscriptionStatus">Trạng thái gói</label>
                                <select id="subscriptionStatus" name="subscriptionStatus">
                                    <option value="Trial">Dùng thử</option>
                                    <option value="Active">Hoạt động</option>
                                    <option value="Expired">Hết hạn</option>
                                    <option value="Suspended">Tạm ngưng</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="subscriptionEndDate">Ngày hết hạn</label>
                                <input type="date" id="subscriptionEndDate" name="subscriptionEndDate">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-warning" onclick="closeModal('addShopModal')">Hủy</button>
                    <button class="btn btn-primary" onclick="saveShop()">Lưu</button>
                </div>
            </div>
        </div>

        <!-- Date Input Modal -->
        <div id="dateInputModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title" id="dateModalTitle">Nhập ngày</h3>
                    <span class="close" onclick="closeModal('dateInputModal')">&times;</span>
                </div>
                <div class="modal-body">
                    <form id="dateInputForm">
                        <div class="form-group">
                            <label for="newEndDate">Ngày hết hạn mới *</label>
                            <input type="date" id="newEndDate" name="newEndDate" required>
                        </div>
                                                 <div class="form-group">
                             <label for="actionType">Loại hành động</label>
                             <select id="actionType" name="actionType" disabled>
                                 <option value="activate">Kích hoạt/Gia hạn</option>
                             </select>
                         </div>
                        <input type="hidden" id="shopIdForAction" name="shopId">
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-warning" onclick="closeModal('dateInputModal')">Hủy</button>
                    <button class="btn btn-primary" onclick="confirmDateAction()">Xác nhận</button>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                        // Modal functions
                        function openAddModal() {
                            document.getElementById('addShopModal').style.display = 'block';
                        }

                        function closeModal(modalId) {
                            document.getElementById(modalId).style.display = 'none';
                        }

                        // Close modal when clicking outside
                        window.onclick = function (event) {
                            const modals = document.querySelectorAll('.modal');
                            modals.forEach(modal => {
                                if (event.target === modal) {
                                    modal.style.display = 'none';
                                }
                            });
                        }

                        // Shop management functions
                        function filterShops() {
                            const shopName = document.getElementById('searchShop').value;
                            const ownerName = document.getElementById('searchOwner').value;
                            const status = document.getElementById('statusFilter').value;
                            const date = document.getElementById('dateFilter').value;

                            console.log('Filtering shops:', {shopName, ownerName, status, date});
                            // Implement filtering logic here
                        }

                        function viewShop(shopId) {
                            console.log('Viewing shop:', shopId);
                            // Navigate to shop detail page
                            window.location.href = `shop-detail.jsp?id=${shopId}`;
                        }

                        function editShop(shopId) {
                            console.log('Editing shop:', shopId);
                            // Open edit modal or navigate to edit page
                        }

                                    function activateShop(shopId) {
                openDateModal(shopId, 'activate', 'Kích hoạt/Gia hạn Shop');
            }

            function suspendShop(shopId) {
                if (confirm('Bạn có chắc muốn tạm ngưng shop này?')) {
                    window.location.href = "/Mg2/UpdateStatusController?actionType=suspend&id=" + shopId;
                    console.log('Suspending shop:', shopId);
                }
            }

            function renewShop(shopId) {
                openDateModal(shopId, 'activate', 'Kích hoạt/Gia hạn Shop');
            }

            function openDateModal(shopId, actionType, title) {
                document.getElementById('dateModalTitle').textContent = title;
                document.getElementById('actionType').value = actionType;
                document.getElementById('shopIdForAction').value = shopId;
                
                // Set minimum date to today
                const today = new Date().toISOString().split('T')[0];
                document.getElementById('newEndDate').min = today;
                document.getElementById('newEndDate').value = '';
                
                // Enable the select and set the value
                const actionTypeSelect = document.getElementById('actionType');
                actionTypeSelect.disabled = false;
                actionTypeSelect.value = actionType;
                
                console.log('Opening modal with:', {shopId, actionType, title});
                
                document.getElementById('dateInputModal').style.display = 'block';
            }

            function confirmDateAction() {
                const form = document.getElementById('dateInputForm');
                const formData = new FormData(form);
                
                const shopId = formData.get('shopId');
                const actionType = formData.get('actionType');
                const newEndDate = formData.get('newEndDate');
                
                console.log('Shop ID:', shopId);
                console.log('Action Type:', actionType);
                console.log('New End Date:', newEndDate);
                
                if (!newEndDate) {
                    alert('Vui lòng chọn ngày hết hạn');
                    return;
                }
                
                // Validate date is not in the past
                const selectedDate = new Date(newEndDate);
                const today = new Date();
                today.setHours(0, 0, 0, 0);
                
                if (selectedDate < today) {
                    alert('Ngày hết hạn không thể là ngày trong quá khứ');
                    return;
                }
                
                // Send request to server
                $.ajax({
                    url: "/Mg2/UpdateStatusController",
                    type: 'GET',
                    data: {
                        actionType: actionType,
                        id: shopId,
                        endDate: newEndDate
                    },
                    success: function(result) {
                        if (result === "OKE") {
                            alert('Thao tác thành công!');
                            window.location.reload();
                        } else {
                            alert('Có lỗi xảy ra: ' + result);
                        }
                    }
                });
                
                closeModal('dateInputModal');
            }
                

                        function saveShop() {
                            const form = document.getElementById('addShopForm');
                            const formData = new FormData(form);

                            // Validate form
                            const phone = formData.get('phone');
                            const status = formData.get('subscriptionStatus');
                            const shopName = formData.get('shopName');
                            const ownerName = formData.get('ownerName');
                            const email = formData.get('email');
                            const date = formData.get('subscriptionEndDate');
                            if (!shopName || !ownerName || !email || !date) {
                                alert('Vui lòng điền đầy đủ thông tin bắt buộc');
                                return;
                            } else {
                                $.ajax({
                                    url: "/Mg2/AddShopOwner",
                                    type: 'POST',
                                    data: {shopName: shopName, ownerName: ownerName, email: email, phone: phone, status: status, enddate: date},
                                    success: function (result) {
                                        if (result === "OKE") {
                                            alert("Đăng ký thành công");
                                            window.location.href = "/Mg2/ShopOwnerController";
                                        } else {
                                            alert(result);
                                        }
                                    },
                                    error: function () {
                                        alert("Lỗi khi gửi dữ liệu đến server");
                                    }
                                });
                            }
                        }

                        // Format date function
                        function formatDate(dateString) {
                            if (!dateString)
                                return 'Chưa có';

                            try {
                                // Handle ISO date format (2025-07-18T00:39:14.070)
                                if (dateString.includes('T')) {
                                    const date = new Date(dateString);
                                    return date.toLocaleDateString('vi-VN', {
                                        day: '2-digit',
                                        month: '2-digit',
                                        year: 'numeric',
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    });
                                }

                                // Handle other date formats
                                const date = new Date(dateString);
                                return date.toLocaleDateString('vi-VN');
                            } catch (e) {
                                return dateString; // Return original if parsing fails
                            }
                        }

                        // Format date only (without time)
                        function formatDateOnly(dateString) {
                            if (!dateString)
                                return 'Chưa có';

                            try {
                                if (dateString.includes('T')) {
                                    const date = new Date(dateString);
                                    return date.toLocaleDateString('vi-VN', {
                                        day: '2-digit',
                                        month: '2-digit',
                                        year: 'numeric'
                                    });
                                }

                                const date = new Date(dateString);
                                return date.toLocaleDateString('vi-VN');
                            } catch (e) {
                                return dateString;
                            }
                        }

                        // Add some interactivity
                        document.addEventListener('DOMContentLoaded', function () {
                            // Format all date cells
                            const dateCells = document.querySelectorAll('.date-cell');
                            dateCells.forEach(cell => {
                                const originalText = cell.textContent.trim();
                                if (originalText && originalText !== 'null' && originalText !== '') {
                                    // Check if it's a date cell (contains T for ISO format)
                                    if (originalText.includes('T')) {
                                        if (originalText.includes(':')) {
                                            // Has time - format with time
                                            cell.textContent = formatDate(originalText);
                                        } else {
                                            // Date only
                                            cell.textContent = formatDateOnly(originalText);
                                        }
                                    }
                                } else {
                                    cell.textContent = 'Chưa có';
                                    cell.style.color = '#999';
                                }
                            });

                            // Add click handlers for navigation
                            const navLinks = document.querySelectorAll('.nav-link');
                            navLinks.forEach(link => {
                                link.addEventListener('click', function () {
                                    navLinks.forEach(l => l.classList.remove('active'));
                                    this.classList.add('active');
                                });
                            });

                            // Add table row hover effects
                            const tableRows = document.querySelectorAll('.table tbody tr');
                            tableRows.forEach(row => {
                                row.addEventListener('mouseenter', function () {
                                    this.style.backgroundColor = '#f8f9fa';
                                });
                                row.addEventListener('mouseleave', function () {
                                    this.style.backgroundColor = '';
                                });
                            });
                        });
        </script>
    </body>
</html> 