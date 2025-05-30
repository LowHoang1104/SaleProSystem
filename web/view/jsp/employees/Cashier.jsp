<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hệ thống bán hàng</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

        <!-- Link tới file CSS riêng -->
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/cashier.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/payment.css" rel="stylesheet">

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
                                            <div class="item-name">${item.getProductName()}</div>
                                            <div class="item-price">
                                                <fmt:formatNumber value="${item.getPrice()}" type="number" pattern="#,###"/> đ
                                            </div>
                                        </div>
                                        <div class="item-actions">
                                            <div class="quantity-control">
                                                <button class="quantity-btn" onclick="updateQuantity('${item.getProductCode()}', ${item.getQuantity() - 1})"
                                                        <c:if test="${item.getQuantity() == 1}">disabled</c:if>>
                                                            <i class="fas fa-minus"></i>
                                                        </button>
                                                        <input type="number" class="quantity-input" value="${item.getQuantity()}" min="1" onchange="changeQuantity(this, '${item.getProductCode()}')">
                                                <button class="quantity-btn" onclick="updateQuantity('${item.getProductCode()}', ${item.getQuantity() + 1})">
                                                    <i class="fas fa-plus"></i>
                                                </button>
                                            </div>
                                            <button class="remove-btn" onclick="removeFromCart('${item.getProductCode()}')">
                                                <i class="fas fa-trash"></i>
                                            </button>

                                            <div class="cart-options">
                                                <button class="cart-menu-btn" onclick="toggleCartMenu(this)">
                                                    <i class="fas fa-ellipsis-v"></i>
                                                </button>
                                                <div class="cart-menu">
                                                    <button onclick="showDetailPanel('${item.getProductCode()}')">ℹ️ Xem chi tiết</button>
                                                </div>
                                            </div>
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
                        <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> VND</span>
                    </div>
                    <div class="summary-row total-row">
                        <span>Tổng cộng:</span>
                        <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> VND</span>
                    </div>
                    <div class="summary-row">
                        <span>Số lượng:</span>
                        <span><fmt:formatNumber value="${totalItems}" type="number" pattern="#"/></span>
                    </div>

                    <div class="order-note">
                        <textarea placeholder="Ghi chú đơn hàng..."></textarea>
                    </div>
                </div>
            </div>

            <!-- Right Panel - Products -->

            <div class="products-panel" >
                <div class="product-search" style="position: relative; display: flex; align-items: center; gap: 8px;">
                    <input type="text" id="productInput" placeholder="Tìm sản phẩm" autocomplete="off" style="flex:1;" />
                    <button id="productSearchBtn" type="button" style="padding: 8px 12px; cursor: pointer;">
                        <i class="fas fa-search"></i> Tìm
                    </button>
                </div>

                <div class="products-grid">
                    <!-- Hiển thị các sản phẩm thật -->
                    <c:forEach var="product" items="${products}">
                        <div class="product-card" onclick="addToCart('${product.code}', '${product.name}', ${product.price})">
                            <div class="product-image">
                                <img src="view/assets/img/img-03.jpg" alt="${product.name}" />
                            </div>
                            <div class="price-badge">
                                <fmt:formatNumber value="${product.price}" type="number" pattern="#,###" /> đ
                            </div>
                            <div class="product-info">
                                <div class="product-name">${product.name}</div>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Thêm các ô trống để luôn đủ 12 ô -->
                    <c:set var="count" value="${fn:length(products)}" />
                    <c:if test="${count < 12}">
                        <c:forEach begin="1" end="${12 - count}">
                            <div class="product-card empty-card"></div>
                        </c:forEach>
                    </c:if>
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
                    <button class="checkout-btn" onclick="showPaymentPanel()" >
                        <i class="fas fa-credit-card"></i> THANH TOÁN
                    </button>
                </div>

                <!-- Payment Panel (hidden by default) -->
                <div class="payment-overlay" id="paymentOverlay"></div>
                
                <div class="payment-panel" id="paymentPanel"> 
                    <button class="payment-close" onclick="hidePaymentPanel()" title="Đóng">
                        <i class="fas fa-times"></i>
                    </button>
                    
                    <div class="payment-header">
                        <div class="staff-select">
                            <i class="fas fa-user"></i>
                            <select id="staffDropdown" name="staffId">
                                <c:forEach var="user" items="${listUsers}">
                                    <option value="${user.getUserId()}"
                                            <c:if test="${user.getUserId() == selectedUserId}">selected</c:if>>
                                        ${user.getFullName()}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <span id="saleTime">${currentTime}</span>
                    </div>

                    <!-- Customer Selection Section -->
                    <div class="customer-section">
                        <h4><i class="fas fa-users"></i> Loại khách hàng</h4>
                        <div class="customer-options">
                            <label class="customer-option">
                                <input type="radio" name="customerType" value="guest" checked onchange="toggleCustomerSearch()">
                                <span><i class="fas fa-user"></i> Khách lẻ</span>
                            </label>
                            <label class="customer-option">
                                <input type="radio" name="customerType" value="member" onchange="toggleCustomerSearch()">
                                <span><i class="fas fa-star"></i> Khách hàng</span>
                            </label>
                        </div>
                        
                        <div class="customer-search" id="customerSearchSection">
                            <input type="text" id="customerSearchInput" placeholder="Tìm kiếm khách hàng..." oninput="searchCustomer(this.value)">
                            <i class="fas fa-search"></i>
                            <div class="customer-dropdown" id="customerDropdown">
                                <!-- Customer list will be populated here -->
                            </div>
                        </div>
                        
                        <div class="selected-customer" id="selectedCustomer" style="display: none;">
                            <i class="fas fa-check-circle"></i>
                            <span id="selectedCustomerInfo">Khách lẻ</span>
                        </div>
                    </div>

                    <!-- Order Details -->
                    <div class="order-details">
                        <div class="detail-row">
                            <span>Tổng tiền hàng:</span>
                            <span><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                        </div>
                        <div class="detail-row">
                            <span>Giảm giá:</span>
                            <div class="payment-input-group">
                                <input type="number" name="discount" id="discountInput" value="0" min="0" max="100" onchange="updatePayment()">
                                <span class="currency-label">%</span>
                            </div>
                        </div>
                        <div class="detail-row highlight">
                            <span>Khách cần trả:</span>
                            <span id="payableAmount"><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> đ</span>
                        </div>
                    </div>

                    <!-- Payment Amount Section -->
                    <div class="payment-amount-section">
                        <div class="payment-row">
                            <label for="paidAmount">Khách thanh toán:</label>
                            <div class="payment-input-group">
                                <input type="number" id="paidAmount" name="totalAmount" value="${totalAmount}" min="0" onchange="updatePayment()">
                                <span class="currency-label">đ</span>
                            </div>
                        </div>
                        <div class="payment-row" id="changeRow" style="display: none;">
                            <label>Tiền thừa:</label>
                            <span id="changeAmount" style="font-weight: 600; color: #2e7d32;">0 đ</span>
                        </div>
                    </div>

                    <!-- Payment Methods -->
                    <div class="payment-methods">
                        <h4><i class="fas fa-credit-card"></i> Phương thức thanh toán</h4>
                        <div class="payment-method-grid">
                            <label>
                                <input type="radio" name="paymentMethod" value="cash" checked>
                                <span><i class="fas fa-money-bill-wave"></i> Tiền mặt</span>
                            </label>
                            <label>
                                <input type="radio" name="paymentMethod" value="transfer">
                                <span><i class="fas fa-university"></i> Chuyển khoản</span>
                            </label>
                            <label>
                                <input type="radio" name="paymentMethod" value="card">
                                <span><i class="far fa-credit-card"></i> Thẻ</span>
                            </label>
                            <label>
                                <input type="radio" name="paymentMethod" value="wallet">
                                <span><i class="fas fa-wallet"></i> Ví điện tử</span>
                            </label>
                        </div>
                        
                        <div id="bankAccounts" style="display: none; margin-top: 15px;">
                            <select id="bankAccountSelect" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 6px;">
                                <option value="">Chọn tài khoản</option>
                                <option value="bank1">Vietcombank - 123456789</option>
                                <option value="bank2">Techcombank - 987654321</option>
                            </select>
                            <button onclick="addBankAccount()" style="margin-top: 10px; padding: 8px 12px; background: #1976d2; color: white; border: none; border-radius: 4px; cursor: pointer;">+ Thêm tài khoản</button>
                        </div>
                        <div id="noBankMsg" style="display: none; color: #666; margin-top: 10px; text-align: center;">
                            Bạn chưa có tài khoản ngân hàng 
                            <button onclick="addBankAccount()" style="margin-left: 10px; padding: 4px 8px; background: #1976d2; color: white; border: none; border-radius: 4px; cursor: pointer;">+ Thêm tài khoản</button>
                        </div>
                    </div>

                    <div class="payment-actions">
                        <button class="payment-btn" onclick="checkout()">
                            <i class="fas fa-check"></i> XÁC NHẬN THANH TOÁN
                        </button>
                    </div>
                </div>


                <!-- Product Detail Panel -->
                <div class="detail-overlay" id="detailOverlay"></div>
                <div class="detail-panel" id="detailPanel">
                    <button class="detail-close" onclick="hideDetailPanel()" title="Đóng">
                        <i class="fas fa-times"></i>
                    </button>

                    <h2 id="detailTitle">Tên sản phẩm</h2>

                    <div class="detail-tabs">
                        <button class="tab-btn active" onclick="showTab('general')">Thông tin chung</button>
                        <button class="tab-btn" onclick="showTab('description')">Mô tả chi tiết</button>
                    </div>

                    <div class="tab-content" id="generalTab">
                        <div class="detail-image">
                            <img id="detailImage" src="" alt="Ảnh sản phẩm">
                        </div>
                        <div class="detail-info">
                            <div><strong>Giá bán:</strong> <span id="detailPrice">0 đ</span></div>
                            <div><strong>Số lượng:</strong> 
                                <button onclick="changeDetailQuantity(-1)">−</button>
                                <input type="number" id="detailQuantity" value="1" min="1" readonly>
                                <button onclick="changeDetailQuantity(1)">+</button>
                            </div>
                            <div><strong>Tồn:</strong> <span id="detailStock">0</span></div>
                            <div><strong>Có thể bán:</strong> <span id="detailAvailable">0</span></div>
                            <div><strong>Thương hiệu:</strong> <span id="detailBrand"></span></div>
                            <div><strong>Vị trí:</strong> <span id="detailLocation"></span></div>
                        </div>
                    </div>

                    <div class="tab-content hidden" id="descriptionTab">
                        <p id="detailDescription">Chưa có mô tả chi tiết.</p>
                    </div>

                    <div class="detail-actions">
                        <button onclick="hideDetailPanel()">Bỏ qua</button>
                        <button onclick="confirmDetail()">Xong</button>
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
            window.appData = {
                totalAmount: ${totalAmount != null ? totalAmount : 0},
                totalItems: ${totalItems != null ? totalItems : 0},
            };

        </script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/cashier.js"></script>
    </body>
</html>