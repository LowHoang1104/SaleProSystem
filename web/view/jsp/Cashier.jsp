<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<%
    String base = request.getContextPath();
%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ thống bán hàng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            height: 100vh;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        /* Header */
        .header {
            background: linear-gradient(135deg, #4285f4, #34a853);
            color: white;
            padding: 10px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .search-section {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .search-box {
            display: flex;
            align-items: center;
            background: white;
            border-radius: 8px;
            padding: 0 15px;
            width: 300px;
        }

        .search-box input {
            border: none;
            outline: none;
            padding: 10px;
            flex: 1;
            font-size: 14px;
        }

        .search-box i {
            color: #666;
        }

        .tab-section {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .invoice-tab {
            background: rgba(255,255,255,0.2);
            padding: 8px 15px;
            border-radius: 6px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .header-actions {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .header-actions i {
            font-size: 18px;
            cursor: pointer;
            padding: 8px;
            border-radius: 6px;
            transition: background 0.3s;
        }

        .header-actions i:hover {
            background: rgba(255,255,255,0.2);
        }

        .phone-number {
            font-weight: 600;
            font-size: 14px;
        }

        /* Main container */
        .main-container {
            display: flex;
            flex: 1;
            height: calc(100vh - 110px); /* Adjusted for header and footer */
        }

        /* Left panel - Invoice */
        .invoice-panel {
            width: 60%; /* 6/10 of the width */
            background: white;
            display: flex;
            flex-direction: column;
            border-right: 2px solid #e0e0e0;
        }

        .invoice-content {
            flex: 1;
            padding: 20px;
            overflow-y: auto;
        }

        .cart-items {
            min-height: 300px;
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #f0f0f0;
            background: #fafafa;
            margin-bottom: 10px;
            border-radius: 8px;
        }

        .item-info {
            flex: 1;
        }

        .item-name {
            font-weight: 600;
            color: #333;
            margin-bottom: 5px;
        }

        .item-price {
            color: #4285f4;
            font-weight: 600;
        }

        .item-actions {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            gap: 8px;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 5px;
        }

        .quantity-btn {
            background: #f0f0f0;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 4px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .quantity-input {
            border: none;
            width: 50px;
            text-align: center;
            font-weight: 600;
        }

        .remove-btn {
            background: #ff4444;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 6px;
            cursor: pointer;
        }

        .empty-cart {
            text-align: center;
            color: #666;
            font-style: italic;
            padding: 40px 20px;
        }

        .order-summary {
            background: #f8f9fa;
            padding: 20px;
            border-top: 1px solid #e0e0e0;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .total-row {
            font-size: 18px;
            font-weight: bold;
            color: #4285f4;
            border-top: 2px solid #4285f4;
            padding-top: 10px;
        }

        .order-note {
            margin-top: 15px;
        }

        .order-note textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            resize: vertical;
            min-height: 60px;
        }

        /* Right panel - Products */
        .products-panel {
            width: 40%; /* 4/10 of the width as requested */
            background: white;
            display: flex;
            flex-direction: column;
            position: relative;
        }

        .customer-search {
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
        }

        .customer-search input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
        }

        .products-grid {
            flex: 1;
            padding: 15px;
            overflow-y: auto;
            display: grid;
            grid-template-columns: repeat(3, 1fr); /* Adjusted to 3 columns */
            gap: 10px;
        }

        .product-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
            cursor: pointer;
            transition: all 0.3s;
            background: white;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 10px 0;
        }

        .product-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            transform: translateY(-2px);
        }

        .product-image {
            width: 100%;
            height: 120px; /* Increased to match the image height */
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        .product-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .price-badge {
            background-color: #4285f4;
            color: white;
            padding: 5px 10px;
            border-radius: 12px;
            margin: 10px 0;
            font-weight: bold;
            font-size: 14px;
        }

        .product-info {
            padding: 0 10px;
            text-align: center;
        }

        .product-name {
            font-size: 12px;
            font-weight: 600;
            color: #333;
            margin-bottom: 5px;
            line-height: 1.3;
            height: 32px;
            overflow: hidden;
        }

        .product-price {
            display: none; /* Hidden since price is now in badge */
        }

        .pagination {
            padding: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            border-top: 1px solid #e0e0e0;
        }

        .page-btn {
            background: #f0f0f0;
            border: none;
            padding: 6px 10px;
            border-radius: 6px;
            cursor: pointer;
        }

        .page-btn.active {
            background: #4285f4;
            color: white;
        }

        .checkout-section {
            padding: 10px;
            border-top: 2px solid #e0e0e0;
        }

        .checkout-btn {
            width: 100%;
            background: #4285f4;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }

        .checkout-btn:hover {
            background: #3367d6;
        }

        /* Payment Panel */
        .payment-panel {
            width: 100%;
            height: 100%;
            background: white;
            display: none; /* Hidden by default */
            flex-direction: column;
            padding: 15px;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 10;
        }

        .payment-close {
            position: absolute;
            top: 10px;
            right: 10px;
            background: #ff4444;
            color: white;
            border: none;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            font-size: 14px;
            transition: background 0.3s ease;
        }

        .payment-close:hover {
            background: #cc3333;
        }

        .payment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 12px;
            border-bottom: 1px solid #e0e0e0;
            margin-bottom: 12px;
        }

        .staff-select select {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 4px;
            font-size: 12px;
            color: #666;
        }

        #saleTime {
            font-size: 12px;
            color: #666;
        }

        .customer-info {
            padding: 12px 0;
            border-bottom: 1px solid #e0e0e0;
            margin-bottom: 12px;
            font-size: 14px;
            color: #333;
        }

        .order-details {
            flex: 1;
            padding-bottom: 12px;
            border-bottom: 1px solid #e0e0e0;
            margin-bottom: 12px;
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            font-size: 14px;
            color: #333;
        }

        .detail-row span:first-child {
            text-align: left;
        }

        .detail-row span:last-child {
            text-align: right;
        }

        .detail-row.highlight {
            font-size: 16px;
            color: #4285f4;
            font-weight: 600;
        }

        .detail-row input {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 4px;
            font-size: 14px;
            color: #333;
        }

        .payment-methods {
            padding: 12px 0;
            border-bottom: 1px solid #e0e0e0;
            margin-bottom: 12px;
        }

        .payment-methods label {
            display: inline-flex;
            align-items: center;
            margin-right: 12px;
            font-size: 14px;
            color: #333;
            cursor: pointer;
        }

        .payment-methods input {
            margin-right: 6px;
        }

        .payment-methods i {
            margin-right: 6px;
            color: #666;
        }

        .payment-actions {
            padding: 12px 0;
        }

        .payment-btn {
            width: 100%;
            background: #1a73e8;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            text-transform: uppercase;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .payment-btn:hover {
            background: #1557b0;
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }

        .payment-btn:disabled {
            background: #cccccc;
            cursor: not-allowed;
            box-shadow: none;
        }

        /* Footer */
        .footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
            padding: 15px;
            height: 50px;
            width: 100%;
        }

        .bottom-tabs {
            display: flex;
        }

        .tab {
            padding: 10px 20px;
            text-align: center;
            background: #f8f9fa;
            border: none;
            cursor: pointer;
            transition: background 0.3s;
        }

        .tab.active {
            background: #4285f4;
            color: white;
        }

        .tab:hover:not(.active) {
            background: #e8f0fe;
        }

        .support-section {
            display: flex;
            gap: 10px;
        }

        .support-btn {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: none;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            transition: all 0.3s;
        }

        .support-call {
            background: #34a853;
            color: white;
        }

        .support-chat {
            background: #ff9800;
            color: white;
        }

        .support-notification {
            background: #2196f3;
            color: white;
        }

        .support-btn:hover {
            transform: scale(1.1);
        }

        /* Responsive */
        @media (max-width: 768px) {
            .main-container {
                flex-direction: column;
            }

            .invoice-panel {
                width: 100%;
                height: 50%;
            }

            .products-panel {
                width: 100%;
                height: 50%;
            }

            .products-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .footer {
                flex-direction: column;
                height: auto;
                padding: 10px;
            }

            .bottom-tabs {
                width: 100%;
                justify-content: space-around;
            }

            .support-section {
                margin-top: 10px;
            }
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="search-section">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" placeholder="Tìm hàng hóa (F3)" id="productSearch">
            </div>
        </div>

        <div class="tab-section">
            <div class="invoice-tab">
                <i class="fas fa-file-invoice"></i>
                <span>Hóa đơn 1</span>
                <i class="fas fa-times"></i>
            </div>
        </div>

        <div class="header-actions">
            <i class="fas fa-plus" title="Thêm hóa đơn"></i>
            <i class="fas fa-list" title="Danh sách hóa đơn"></i>
            <i class="fas fa-filter" title="Lọc"></i>
            <i class="fas fa-shopping-cart" title="Giỏ hàng"></i>
            <i class="fas fa-sync" title="Đồng bộ"></i>
            <i class="fas fa-undo" title="Hoàn tác"></i>
            <i class="fas fa-print" title="In"></i>
            <span class="phone-number">${phoneNumber}</span>
            <i class="fas fa-bars" title="Menu"></i>
        </div>
    </div>

    <!-- Main Container -->
    <div class="main-container">
        <!-- Left Panel - Invoice -->
        <div class="invoice-panel">
            <div class="invoice-content">
                <div class="cart-items">
                    <c:choose>
                        <c:when test="${empty cart}">
                            <div class="empty-cart">
                                <i class="fas fa-shopping-cart" style="font-size: 48px; color: #ddd; margin-bottom: 15px;"></i>
                                <p>Chưa có sản phẩm nào trong hóa đơn</p>
                                <p>Hãy chọn sản phẩm từ danh sách bên phải</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="item" items="${cart}">
                                <div class="cart-item">
                                    <div class="item-info">
                                        <div class="item-name">${item.productName}</div>
                                        <div class="item-price">
                                            <fmt:formatNumber value="${item.price}" type="number" pattern="#,###"/> đ
                                        </div>
                                    </div>
                                    <div class="item-actions">
                                        <div class="quantity-control">
                                            <button class="quantity-btn" onclick="updateQuantity(${item.productId}, ${item.quantity - 1})">
                                                <i class="fas fa-minus"></i>
                                            </button>
                                            <input type="text" class="quantity-input" value="${item.quantity}" readonly>
                                            <button class="quantity-btn" onclick="updateQuantity(${item.productId}, ${item.quantity + 1})">
                                                <i class="fas fa-plus"></i>
                                            </button>
                                        </div>
                                        <button class="remove-btn" onclick="removeFromCart(${item.productId})">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="order-summary">
                <div class="summary-row">
                    <span>Tổng tiền hàng:</span>
                    <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                </div>
                <div class="summary-row total-row">
                    <span>Tổng cộng:</span>
                    <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                </div>
                <div class="summary-row">
                    <span>Số lượng:</span>
                    <span>${totalItems}</span>
                </div>

                <div class="order-note">
                    <textarea placeholder="Ghi chú đơn hàng..."></textarea>
                </div>
            </div>
        </div>

        <!-- Right Panel - Products -->
        <div class="products-panel">
            <div class="customer-search">
                <input type="text" placeholder="Tìm khách hàng (F4)">
            </div>

            <div class="products-grid">
                <c:forEach var="product" items="${products}">
                    <div class="product-card" onclick="addToCart(${product.getProductId()}, '${product.getProductName()}', ${product.getPrice()})">
                        <div class="product-image">
                            <img src="view/assets/img/img-03.jpg" alt="${product.getProductName()}"/>
                        </div>
                        <div class="price-badge">
                            <fmt:formatNumber value="${product.getPrice()}" type="number" pattern="#,###"/> đ
                        </div>
                        <div class="product-info">
                            <div class="product-name">${product.getProductName()}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <button class="page-btn" onclick="goToPage(${currentPage - 1})">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                </c:if>
                <span>${currentPage}/${totalPages}</span>
                <c:if test="${currentPage < totalPages}">
                    <button class="page-btn" onclick="goToPage(${currentPage + 1})">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </c:if>
            </div>

            <div class="checkout-section">
                <button class="checkout-btn" onclick="showPaymentPanel()" ${totalItems == 0 ? 'disabled' : ''}>
                    <i class="fas fa-credit-card"></i> THANH TOÁN
                </button>
            </div>

            <!-- Payment Panel (hidden by default) -->
            <div class="payment-panel" id="paymentPanel">
                <button class="payment-close" onclick="hidePaymentPanel()" title="Đóng">
                    <i class="fas fa-times"></i>
                </button>
                <div class="payment-header">
                    <div class="staff-select">
                        <i class="fas fa-user" style="margin-right: 8px;"></i>
                        <select id="staffDropdown">
                            <option value="staff1">Nguyễn Văn A</option>
                            <option value="staff2">Trần Thị B</option>
                            <option value="staff3">Lê Văn C</option>
                        </select>
                    </div>
                    <span id="saleTime">${currentTime}</span>
                </div>

                <div class="customer-info">
                    <span>Khách lẻ</span>
                </div>

                <div class="order-details">
                    <div class="detail-row">
                        <span>Tổng tiền hàng:</span>
                        <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                    </div>
                    <div class="detail-row">
                        <span>Giảm giá:</span>
                        <input type="number" id="discountInput" value="0" min="0" style="width: 100px; text-align: right;" onchange="updatePayment()" />
                        <span> đ</span>
                    </div>
                    <div class="detail-row highlight">
                        <span>Khách cần trả:</span>
                        <span id="payableAmount"><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                    </div>
                    <div class="detail-row">
                        <span>Khách thanh toán:</span>
                        <input type="number" id="paidAmount" value="${totalAmount}" min="0" style="width: 100px; text-align: right;" onchange="updatePayment()" />
                        <span> đ</span>
                    </div>
                </div>

                <div class="payment-methods">
                    <label><input type="radio" name="paymentMethod" value="cash" checked> <i class="fas fa-money-bill-wave"></i> Tiền mặt</label>
                    <label><input type="radio" name="paymentMethod" value="transfer"> <i class="fas fa-university"></i> Chuyển khoản</label>
                    <label><input type="radio" name="paymentMethod" value="card"> <i class="far fa-credit-card"></i> Thẻ</label>
                    <label><input type="radio" name="paymentMethod" value="wallet"> <i class="fas fa-wallet"></i> Ví</label>
                    <div id="bankAccounts" style="display: none; margin-top: 10px;">
                        <select id="bankAccountSelect">
                            <option value="">Chọn tài khoản</option>
                            <option value="bank1">Vietcombank - 123456789</option>
                            <option value="bank2">Techcombank - 987654321</option>
                        </select>
                        <button onclick="addBankAccount()" style="margin-left: 10px;">+ Thêm tài khoản</button>
                    </div>
                    <div id="noBankMsg" style="display: none; color: #666; margin-top: 10px;">
                        Bạn chưa có tài khoản ngân hàng <button onclick="addBankAccount()">+ Thêm tài khoản</button>
                    </div>
                </div>

                <div class="payment-actions">
                    <button class="payment-btn" onclick="checkout()" ${totalItems == 0 ? 'disabled' : ''}>
                        <i class="fas fa-check"></i> XÁC NHẬN THANH TOÁN
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <div class="bottom-tabs">
            <button class="tab">
                <i class="fas fa-bolt"></i> Bán nhanh
            </button>
            <button class="tab active">
                <i class="fas fa-shopping-bag"></i> Bán thường
            </button>
            <button class="tab">
                <i class="fas fa-truck"></i> Bán giao hàng
            </button>
        </div>
        <div class="support-section">
            <button class="support-btn support-call" title="Gọi hỗ trợ: 1900 6522">
                <i class="fas fa-phone"></i>
            </button>
            <button class="support-btn support-chat" title="Chat hỗ trợ">
                <i class="fas fa-comment"></i>
            </button>
            <button class="support-btn support-notification" title="Thông báo">
                <i class="fas fa-bell"></i>
            </button>
        </div>
    </div>

    <script>
        // Show Payment Panel
        function showPaymentPanel() {
            const totalItems = ${totalItems};
            if (totalItems === 0) {
                alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán!');
                return;
            }

            if (confirm('Xác nhận thanh toán với tổng số tiền: ' +
                    new Intl.NumberFormat('vi-VN').format(${totalAmount}) + ' đ?')) {
                document.querySelector('.products-grid').style.display = 'none';
                document.querySelector('.pagination').style.display = 'none';
                document.querySelector('.checkout-section').style.display = 'none';
                document.getElementById('paymentPanel').style.display = 'flex';
            }
        }

        // Hide Payment Panel and show Products Panel
        function hidePaymentPanel() {
            document.getElementById('paymentPanel').style.display = 'none';
            document.querySelector('.products-grid').style.display = 'grid';
            document.querySelector('.pagination').style.display = 'flex';
            document.querySelector('.checkout-section').style.display = 'block';
        }

        // Add to cart function
        function addToCart(productId, productName, price) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'CashierServelet';

            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'addToCart';

            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'productId';
            idInput.value = productId;

            const nameInput = document.createElement('input');
            nameInput.type = 'hidden';
            nameInput.name = 'productName';
            nameInput.value = productName;

            const priceInput = document.createElement('input');
            priceInput.type = 'hidden';
            priceInput.name = 'price';
            priceInput.value = price;

            form.appendChild(actionInput);
            form.appendChild(idInput);
            form.appendChild(nameInput);
            form.appendChild(priceInput);

            document.body.appendChild(form);
            form.submit();
        }

        // Remove from cart function
        function removeFromCart(productId) {
            if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'CashierServelet';

                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'removeFromCart';

                const idInput = document.createElement('input');
                idInput.type = 'hidden';
                idInput.name = 'productId';
                idInput.value = productId;

                form.appendChild(actionInput);
                form.appendChild(idInput);

                document.body.appendChild(form);
                form.submit();
            }
        }

        // Update quantity function
        function updateQuantity(productId, newQuantity) {
            if (newQuantity < 0) return;

            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'CashierServelet';

            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'updateQuantity';

            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'productId';
            idInput.value = productId;

            const quantityInput = document.createElement('input');
            quantityInput.type = 'hidden';
            quantityInput.name = 'quantity';
            quantityInput.value = newQuantity;

            form.appendChild(actionInput);
            form.appendChild(idInput);
            form.appendChild(quantityInput);

            document.body.appendChild(form);
            form.submit();
        }

        // Go to page function
        function goToPage(page) {
            window.location.href = 'CashierServelet?page=' + page;
        }

        // Update payment details dynamically
        function updatePayment() {
            const totalAmount = ${totalAmount};
            const discount = parseInt(document.getElementById('discountInput').value) || 0;
            const payable = totalAmount - discount;
            document.getElementById('payableAmount').textContent = new Intl.NumberFormat('vi-VN').format(payable) + ' đ';

            const paidAmount = parseInt(document.getElementById('paidAmount').value) || payable;
            document.getElementById('paidAmount').value = paidAmount;
        }

        // Handle bank account visibility
        document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
            radio.addEventListener('change', function() {
                if (this.value === 'transfer') {
                    const bankAccounts = document.getElementById('bankAccounts');
                    const noBankMsg = document.getElementById('noBankMsg');
                    if (bankAccounts.children.length > 1) {
                        bankAccounts.style.display = 'block';
                        noBankMsg.style.display = 'none';
                    } else {
                        bankAccounts.style.display = 'none';
                        noBankMsg.style.display = 'block';
                    }
                } else {
                    document.getElementById('bankAccounts').style.display = 'none';
                    document.getElementById('noBankMsg').style.display = 'none';
                }
            });
        });

        // Add bank account function (placeholder)
        function addBankAccount() {
            alert('Chức năng thêm tài khoản ngân hàng chưa được cài đặt.');
        }

        // Checkout function
        function checkout() {
            const totalItems = ${totalItems};
            if (totalItems === 0) {
                alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán!');
                return;
            }

            if (confirm('Xác nhận thanh toán với tổng số tiền: ' +
                    new Intl.NumberFormat('vi-VN').format(${totalAmount}) + ' đ?')) {
                alert('Đang xử lý thanh toán...');
                window.location.href = 'payment?total=' + ${totalAmount};
            }
        }

        // Search products
        document.getElementById('productSearch').addEventListener('input', function (e) {
            const searchTerm = e.target.value.toLowerCase();
            const productCards = document.querySelectorAll('.product-card');

            productCards.forEach(card => {
                const productName = card.querySelector('.product-name').textContent.toLowerCase();
                if (productName.includes(searchTerm)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });

        // Keyboard shortcuts
        document.addEventListener('keydown', function (e) {
            if (e.key === 'F3') {
                e.preventDefault();
                document.getElementById('productSearch').focus();
            }
            if (e.key === 'F4') {
                e.preventDefault();
                document.querySelector('.customer-search input').focus();
            }
            if (e.key === 'Enter' && e.ctrlKey) {
                e.preventDefault();
                showPaymentPanel();
            }
        });

        // Auto-refresh every 30 seconds
        setInterval(function () {
            console.log('Auto-sync check...');
        }, 30000);

        function formatCurrency(amount) {
            return new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(amount);
        }

        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('productSearch').focus();
            console.log('Trang bán hàng đã sẵn sàng');
        });
    </script>
</body>
</html>