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
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/footer.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/header.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/invoices.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/detail.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/cash.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/assets/css/employees/filter.css" rel="stylesheet">
    </head>
    <body>
        ${error}
        ${message}
        <!-- Header -->

        <div >
            <jsp:include page="header_ajax.jsp" />
        </div>

        <!-- Main Container -->
        <div class="main-container">
            <!-- Left Panel - Invoice -->
            <div class="invoice-panel" id="cartSection">
                <jsp:include page="cart_ajax.jsp" />
            </div>

            <!-- Right Panel - Products -->

            <div class="products-panel">
                <div class="product-search" style="position: relative; display: flex; align-items: center; gap: 8px;">
                    <div style="position: relative; flex: 1;">
                        <input type="text" id="customerInput" placeholder="Tìm khách hàng" autocomplete="off" style="width: 100%; padding-right: 24px;"
                               value="" 
                               />
                        <button id="clearBtn" type="button" title="Xóa khách hàng đã chọn" style="position: absolute; right: 6px; top: 50%; transform: translateY(-50%); display: none; border: none; background: transparent; font-size: 16px; color: #dc3545; cursor: pointer; padding: 2px; line-height: 1; border-radius: 50%; transition: all 0.2s ease;">
                            <i class="fas fa-times-circle"></i>
                        </button>  
                        <button onclick="showAddCustomerPanel()" id="addCustomerBtn" type="button" title="Thêm khách hàng mới" style="position: absolute; right: 6px; top: 50%; transform: translateY(-50%); display: block; border: none; background: transparent; font-size: 16px; color: #007bff; cursor: pointer; padding: 2px; line-height: 1;">
                            <i class="fas fa-user-plus"></i>
                        </button>
                    </div>

                    <div id="customerResult" style="position: absolute; top: 100%; left: 0; right: 0; background: white; border: 1px solid #ccc; max-height: 200px; overflow-y: auto; display: none; z-index: 1000;">
                        <!-- Kết quả sẽ được đổ vào đây -->
                    </div>

                    <div class="product-actions">
                        <button class="action-btn" title="Lọc" onclick="showFilterPanel()">
                            <i class="fas fa-filter"></i>
                        </button>
                        <button class="action-btn" title="Giỏ hàng">
                            <i class="fas fa-shopping-cart"></i>
                        </button>
                        <button class="action-btn" title="Đồng bộ">
                            <i class="fas fa-sync"></i>
                        </button>
                    </div>
                </div>

                <c:if test="${not empty selectedCategoryIds or not empty selectedTypeIds}">
                    <div style="background: #e3f2fd; border: 1px solid #1976d2; border-radius: 8px; padding: 12px; margin-bottom: 15px; display: flex; justify-content: space-between; align-items: center;">
                        <div style="display: flex; align-items: center; gap: 8px;">
                            <i class="fas fa-filter" style="color: #1976d2;"></i>
                            <span style="font-weight: 500; color: #333;">
                                Đang lọc: <strong>${totalProducts}</strong> sản phẩm
                                <c:if test="${not empty selectedCategoryIds}">
                                    từ <strong>${fn:length(selectedCategoryIds)}</strong> danh mục
                                </c:if>
                                <c:if test="${not empty selectedTypeIds}">
                                    thuộc <strong>${fn:length(selectedTypeIds)}</strong> nhóm hàng
                                </c:if>
                            </span>
                        </div>
                        <button onclick="clearFilters()" 
                                style="background: #f44336; color: white; border: none; padding: 6px 12px; border-radius: 6px; cursor: pointer; font-size: 12px;">
                            <i class="fas fa-times"></i> Xóa bộ lọc
                        </button>
                    </div>
                </c:if>

                <div class="products-grid">
                    <!-- Hiển thị các sản phẩm thật -->
                    <c:forEach var="product" items="${products}">
                        <div class="product-card" onclick="addToCart('${product.code}', '${product.name}', ${product.price})">
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

                <!-- Detail Product Panel (hidden by default) -->
                <div class="detail-overlay" id="detailOverlay"></div>
                <div class="detail-panel" id="detailPanel"> 
                    <jsp:include page="detail_ajax.jsp" />
                </div>


                <!-- Cash Management Panel (hidden by default) -->
                <div class="cash-overlay" id="cashOverlay"></div>
                <div class="cash-panel" id="cashPanel"> 
                    <jsp:include page="cash_ajax.jsp" />
                </div>

                <!-- Filter Panel (hidden by default) -->
                <div class="filter-overlay" id="filterOverlay"></div>
                <div class="filter-panel" id="filterPanel"> 
                    <jsp:include page="filter_panel.jsp" />
                </div>

                <!-- Add Customer Panel (hidden by default) -->
                <div class="add-customer-overlay" id="addCustomerOverlay"></div>
                <div class="add-customer-panel" id="addCustomerPanel"> 
                    <jsp:include page="add_customer_panel.jsp" />
                </div>

                <!-- Report end day (hidden by default) -->
                <div class="report-overlay" id="reportOverlay"></div>
                <div class="report-panel" id="reportPanel"> 
                    <jsp:include page="end_of_day_report.jsp" />
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
            $(document).ready(function () {
                if (typeof loadCart === 'function')
                    loadCart();
                if (typeof loadCustomerInfo === 'function')
                    loadCustomerInfo();
            });
        </script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/cashier.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/cart_ajax.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/payment_ajax.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/header_ajax.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/cash_ajax.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/employees/filter.js"></script>
    </body>
</html>

