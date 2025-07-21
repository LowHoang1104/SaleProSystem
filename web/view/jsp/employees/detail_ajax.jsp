<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="detailSection">
    <button class="detail-close" onclick="hideDetailPanel()" title="Đóng">
        <i class="fas fa-times"></i>
    </button>

    <div class="detail-header">
        <h3 id="productDetailTitle">${productVariants.getName()}</h3>
    </div>

    <!-- Tabs Navigation -->
    <div class="detail-tabs">
        <button class="tab-btn active" onclick="switchTab('general')">Thông tin chung</button>
        <button class="tab-btn" onclick="switchTab('specs')">Mô tả chi tiết</button>
    </div>

    <!-- Tab Content - Thông tin chung -->
    <div id="generalTab" class="tab-content active">
        <!-- Product Image -->
        <div class="product-detail-image">
            <img id="productDetailImage" src="view/assets/img/img-03.jpg" alt="Product Image" />
        </div>

        <!-- Product Info -->
        <div class="product-detail-info">
            <div class="info-row">
                <span class="info-label">Giá bán:</span>
                <span class="info-value price" id="productDetailPrice">
                    <fmt:formatNumber value="${productVariants.getPrice()}" type="number" pattern="#"/></span>
            </div>

            <div class="info-row">
                <span class="info-label">Số lượng:</span>
                <div class="quantity-section">
                    <button class="quantity-btn" onclick="updateDetailQuantity(-1)">
                        <i class="fas fa-minus"></i>
                    </button>
                    <input type="number" id="detailQuantity" class="quantity-input" value="1" min="1">
                    <button class="quantity-btn" onclick="updateDetailQuantity(1)">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <div class="stock-info">
                    <span>Tồn: <span id="stockQuantity">${productVariants.getStockByWarehouse(1)}</span></span>
                </div>
            </div>
        </div>
    </div>

    <!-- Tab Content - Mô tả chi tiết -->
    <div id="specsTab" class="tab-content">
        <div class="specs-content">
            <div class="specs-section">
                <h4>Thông tin sản phẩm</h4>
                <div class="specs-list">
                    <div class="spec-item">
                        <span class="spec-label">Mã sản phẩm:</span>
                        <span class="spec-value" id="productCode">${productVariants.getProductCode()}</span>
                    </div>
                    <div class="spec-item">
                        <span class="spec-label">Danh mục:</span>
                        <span class="spec-value" id="productCategory">${productVariants.getCategory()}</span>
                    </div>
                </div>
            </div>

            <div class="specs-section">
                <h4>Mô tả chi tiết</h4>
                <div class="product-description" id="productDescription">
                    ${productVariants.getDescription()}
                </div>
            </div>
        </div>
    </div>

    <div class="detail-actions">
        <button class="detail-btn primary" onclick="hideDetailPanel()">
            <i class="fas fa-cart-plus"></i> Xong
        </button>
    </div>
</div>