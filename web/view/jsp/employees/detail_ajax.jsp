<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="detailSection">
    <button class="detail-close" onclick="hideDetailPanel()" title="Đóng">
        <i class="fas fa-times"></i>
    </button>
    
    <div class="detail-header">
        <h3 id="productDetailTitle">Áo vest nam màu xanh lá</h3>
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
                <span class="info-value price" id="productDetailPrice">3,899,000</span>
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
                    <span>Tồn: <span id="stockQuantity">0</span></span>
                    <span style="margin-left: 15px;">Có thể bán: <span id="availableQuantity">0</span></span>
                </div>
            </div>

            <div class="info-row">
                <span class="info-label">Thương hiệu:</span>
                <span class="info-value" id="productBrand">-</span>
            </div>

            <div class="info-row">
                <span class="info-label">Vị trí:</span>
                <span class="info-value" id="productLocation">-</span>
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
                        <span class="spec-value" id="productCode">-</span>
                    </div>
                    <div class="spec-item">
                        <span class="spec-label">Danh mục:</span>
                        <span class="spec-value" id="productCategory">-</span>
                    </div>
                    <div class="spec-item">
                        <span class="spec-label">Chất liệu:</span>
                        <span class="spec-value" id="productMaterial">-</span>
                    </div>
                    <div class="spec-item">
                        <span class="spec-label">Xuất xứ:</span>
                        <span class="spec-value" id="productOrigin">-</span>
                    </div>
                </div>
            </div>

            <div class="specs-section">
                <h4>Mô tả chi tiết</h4>
                <div class="product-description" id="productDescription">
                    Áo vest nam cao cấp với thiết kế hiện đại, phù hợp cho các dịp trang trọng và công việc. 
                    Chất liệu vải cao cấp, form dáng ôm vừa vặn, tạo vẻ lịch lãm và sang trọng.
                </div>
            </div>
        </div>
    </div>

    <!-- Action Buttons -->
    <div class="detail-actions">
        <button class="detail-btn secondary" onclick="hideDetailPanel()">
            <i class="fas fa-arrow-left"></i> Quay lại
        </button>
        <button class="detail-btn primary" onclick="addToCartFromDetail()">
            <i class="fas fa-cart-plus"></i> Thêm vào giỏ
        </button>
    </div>
</div>