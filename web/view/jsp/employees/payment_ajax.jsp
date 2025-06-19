<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<button class="payment-close" onclick="hidePaymentPanel()" title="Đóng">
    <i class="fas fa-times"></i>
</button>
<div class="payment-header">
    <div class="staff-select">
        <i class="fas fa-user"></i>
        <select id="staffDropdown" name="staffId">
            <c:forEach var="user" items="${listUsers}">
                <option value="${user.getUserId()}"
                        <c:if test="${user.getUserId() == sessionScope.currentInvoice.getUser().getUserId()}">selected</c:if>>
                    ${user.getFullName()}
                </option>
            </c:forEach>
        </select>
    </div>
    <span id="saleTime">${currentTime}</span>
</div>

<!-- Customer Selection Section -->
<div class="customer-section">
    <h4><i class="fas fa-users"></i> Khách hàng</h4>

    <div class="selected-customer" id="selectedCustomer" style="display: block; margin-top: 10px;">
        <i class="fas fa-check-circle"></i>
        <span id="selectedCustomerInfo">
            <c:choose>
                <c:when test="${not empty sessionScope.currentInvoice.getCustomer().getFullName()}">
                    ${sessionScope.currentInvoice.getCustomer().getFullName()}
                </c:when>
                <c:otherwise>
                    Khách lẻ
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <input type="hidden" id="selectedCustomerId" name="selectedCustomerId"
           value="${sessionScope.currentInvoice.getCustomer().getCustomerId() != null ? sessionScope.currentInvoice.getCustomer().getCustomerId() : ''}" />
</div>

<!-- Order Details with VAT -->
<div class="order-details">
    <div class="detail-row">
        <span>Tổng tiền hàng:</span>
        <span id="originalAmountDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getOriginalAmount()}" type="number" pattern="#,###"/> đ</span>     
        <input type="hidden" id="originalAmount" name="originalAmount" value="${sessionScope.currentInvoice.getOriginalAmount()}" />
    </div>

    <div class="detail-row">
        <span>Giảm giá:</span>
        <div class="payment-input-group">
            <input type="number" name="discount" id="discountInput" value="${sessionScope.currentInvoice.getDiscount()}" min="0" max="100">
            <span class="currency-label">%</span>
        </div>
    </div>

    <div class="detail-row">
        <span>Tiền hàng (sau giảm giá):</span>
        <span id="subtotalDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getSubTotal()}" type="number" pattern="#,###"/> đ</span>
        <input type="hidden" id="subtotal" name="subtotal" value="${sessionScope.currentInvoice.getSubTotal()}" />
    </div>

    <div class="detail-row vat-row">
        <span>VAT (${sessionScope.currentInvoice.getVATPercent()}%):</span>
        <span id="vatAmountDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getVATAmount()}" type="number" pattern="#,###"/> đ</span>
        <input type="hidden" id="vatPercent" name="vatPercent" value="${sessionScope.currentInvoice.getVATPercent()}" />
        <input type="hidden" id="vatAmount" name="vatAmount" value="${sessionScope.currentInvoice.getVATAmount()}" />
    </div>

    <div class="detail-row highlight total-row">
        <span>Tổng thanh toán:</span>
        <span id="totalAmountDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getTotalAmount()}" type="number" pattern="#,###"/> đ</span>
        <input type="hidden" id="totalAmount" name="totalAmount" value="${sessionScope.currentInvoice.getTotalAmount()}" />
    </div>
</div>

<!-- Payment Amount Section -->
<div class="payment-amount-section">
    <div class="payment-row">
        <label for="paidAmount">Khách thanh toán:</label>
        <div class="payment-input-group">
            <input type="text" id="paidAmount" name="paidAmount" 
                   value="<fmt:formatNumber value='${sessionScope.currentInvoice.getPaidAmount()}' type='number' pattern='#,###'/>" 
                   min="0">
            <span class="currency-label">đ</span>
        </div>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.currentInvoice.getChangeAmount() and sessionScope.currentInvoice.getChangeAmount() > 0}">
            <div class="payment-row" id="changeRow" style="display: block;">
                <label>Tiền thừa:</label>
                <span id="changeAmount" class="change-amount">
                    <fmt:formatNumber value="${sessionScope.currentInvoice.getChangeAmount()}" type="number" pattern="#,###"/> đ
                </span>
            </div>
        </c:when>
        <c:otherwise>
            <div class="payment-row" id="changeRow" style="display: none;">
                <label>Tiền thừa:</label>
                <span id="changeAmount" class="change-amount">0 đ</span>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- Payment Methods -->
<div class="payment-methods">
    <h4><i class="fas fa-credit-card"></i> Phương thức thanh toán</h4>
    <div class="payment-method-grid">
        <label>
            <input type="radio" name="paymentMethod" value="1" checked>
            <span><i class="fas fa-money-bill-wave"></i> Tiền mặt</span>
        </label>
        <label>
            <input type="radio" name="paymentMethod" value="2">
            <span><i class="fas fa-university"></i> Chuyển khoản</span>
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
    <button class="payment-btn" id="checkout">
        <i class="fas fa-check"></i> XÁC NHẬN THANH TOÁN
    </button>
</div>