<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="invoice-content">
    <div class="cart-items">
        <c:choose>
            <c:when test="${empty sessionScope.currentInvoice.getCartItems()}">
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart" style="font-size: 48px; color: #ddd; margin-bottom: 15px;"></i>
                    <p>Chưa có sản phẩm nào trong hóa đơn</p>
                    <p>Hãy chọn sản phẩm từ danh sách bên phải</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${sessionScope.currentInvoice.getCartItems()}">
                    <div class="cart-item">
                        <div class="item-info">
                            <div class="item-name">${item.productName}</div>
                            <c:if test="${item.status == 'Hết hàng'}">
                                <div style="color: red; font-weight: bold; margin-top: 5px;">Hết hàng</div>
                            </c:if>

                            <c:if test="${item.status == 'Full_quantity'}">
                                <div style="color: red; font-weight: bold; margin-top: 5px;">Sản phẩm vượt quá số lượng trong kho</div>
                            </c:if>
                            <c:if test="${item.status == 'Need_Size_And_Color'}">
                                <div style="color: red; font-weight: bold; margin-top: 5px;">Cần chọn size và màu</div>
                            </c:if>
                            <div class="item-price" style="display: flex; gap: 20px;">
                                <span><fmt:formatNumber value="${item.price}" type="number" pattern="#,###"/> đ</span>
                                <span><fmt:formatNumber value="${item.price* item.quantity}" type="number" pattern="#,###"/> đ</span>
                            </div>

                            <div class="item-variants">
                                <div class="variant-group">
                                    <label for="${item.productCode}">Size:</label>
                                    <select id="${item.productCode}_size" class="variant-select" 
                                            onchange="updateVariant('${item.productCode}', 'size', this.value)">
                                        <option value="">Chọn size</option>
                                        <c:forEach var="size" items="${item.getSizeListByMasterCode()}">
                                            <option value="${size}" <c:if test="${size == item.size}">selected</c:if>>${size}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="variant-group">
                                    <label for="${item.productCode}">Màu:</label>
                                    <select id="${item.productCode}_color" class="variant-select" 
                                            onchange="updateVariant('${item.productCode}', 'color', this.value)">
                                        <option value="">Chọn màu</option>
                                        <c:forEach var="color" items="${item.getColorListByMasterCode()}">
                                            <option value="${color}" <c:if test="${color == item.color}">selected</c:if>>${color}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="item-actions">
                            <div class="quantity-control">
                                <button class="quantity-btn" onclick="updateQuantity('${item.productCode}', ${item.quantity - 1})"
                                        <c:if test="${item.quantity == 1 || item.status == 'Hết hàng'}">disabled</c:if>>
                                            <i class="fas fa-minus"></i>
                                        </button>
                                        <input type="number" class="quantity-input" value="${item.quantity}" min="1" 
                                       onchange="changeQuantity(this, '${item.productCode}')"
                                       <c:if test="${item.status == 'Hết hàng'}">disabled</c:if>>
                                <button class="quantity-btn" onclick="updateQuantity('${item.productCode}', ${item.quantity + 1})"
                                        <c:if test="${item.status == 'Hết hàng' || item.quantity == item.stock}">disabled</c:if>>
                                            <i class="fas fa-plus"></i>
                                        </button>
                                </div>
                                <button class="remove-btn" onclick="removeFromCart('${item.productCode}')">
                                <i class="fas fa-trash"></i>
                            </button>

                            <button class="info-btn" onclick="showDetail(${item.productVariantId}, '${item.productCode}')">
                                <i class="fas fa-info-circle"></i>
                                Xem chi tiết
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
        <span><fmt:formatNumber value="${sessionScope.currentInvoice.getOriginalAmount()}" type="number" pattern="#,###"/> VND</span>
    </div>

    <div class="summary-row total-row">
        <span>Tổng cộng:</span>
        <span><fmt:formatNumber value="${sessionScope.currentInvoice.getOriginalAmount()}" type="number" pattern="#,###"/> VND</span>
    </div>
    <div class="summary-row">
        <span>Số lượng:</span>
        <span><fmt:formatNumber value="${sessionScope.currentInvoice.getTotalItem()}" type="number" pattern="#"/></span>
    </div>

    <div class="order-note">
        <textarea placeholder="Ghi chú đơn hàng..."></textarea>
    </div>
</div>

<span id="totalAmountValue" style="display:none;">${totalAmount}</span>


