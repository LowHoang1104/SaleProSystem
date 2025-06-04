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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/cashier.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/payment.css" rel="stylesheet">
    </head>
    <body>
        ${message}
        ${error}
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
            <div class="invoice-panel" id="cartSection">
                <jsp:include page="cart_ajax.jsp" />
            </div>

            <!-- Right Panel - Products -->

            <div class="products-panel" >
                <div class="product-search" style="position: relative; display: flex; align-items: center; gap: 8px;">
                    <div style="position: relative; flex:1;">
                        <input type="text" id="customerInput" placeholder="Tìm khách hàng" autocomplete="off" style="width: 100%; padding-right: 24px;" />
                        <button id="clearBtn" type="button" style="
                                position: absolute;
                                right: 6px;
                                top: 50%;
                                transform: translateY(-50%);
                                display: none;
                                border: none;
                                background: transparent;
                                font-size: 18px;
                                color: #999;
                                cursor: pointer;
                                padding: 0;
                                line-height: 1;
                                ">×</button>
                    </div>

                    <div id="customerResult" style="position: absolute; top: 100%; left: 0; right: 0; background: white; border: 1px solid #ccc; max-height: 200px; overflow-y: auto; display: none; z-index: 1000;">
                        <!-- Kết quả sẽ được đổ vào đây -->
                    </div>
                </div>

                <div class="products-grid">
                    <!-- Hiển thị các sản phẩm thật -->
                    <c:forEach var="product" items="${products}">
                        <div class="product-card" onclick="addToCart('${product.getCode()}', '${product.getName()}', ${product.getPrice()})">
                            <div class="product-image">
                                <img src="view/assets/img/img-03.jpg" alt="${product.getName()}" />
                            </div>
                            <div class="price-badge">
                                <fmt:formatNumber value="${product.getPrice()}" type="number" pattern="#,###" /> đ
                            </div>
                            <div class="product-info">
                                <div class="product-name">${product.getName()}</div>
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
                    <jsp:include page="payment_ajax.jsp" />
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
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/cart_ajax.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/payment_ajax.js"></script>
    </body>
</html>