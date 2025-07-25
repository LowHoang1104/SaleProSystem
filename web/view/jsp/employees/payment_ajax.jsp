<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .points-btn {
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        cursor: pointer;
        margin-left: 10px;
    }

    .points-btn:hover {
        background-color: #45a049;
    }

    .use-points-btn {
        background-color: #2196F3;
    }

    .use-points-btn:hover {
        background-color: #1976D2;
    }

    .shortage-amount {
        color: #f44336;
        font-weight: bold;
    }

    .change-amount {
        color: #4CAF50;
        font-weight: bold;
    }


    .points-btn {
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 6px 10px;
        border-radius: 4px;
        font-size: 12px;
        cursor: pointer;
        margin-left: 10px;
        transition: background-color 0.3s;
        display: inline-flex;
        align-items: center;
        gap: 4px;
    }

    .points-btn:hover {
        background-color: #45a049;
    }

    .points-btn i {
        font-size: 11px;
    }

    .use-points-btn {
        background-color: #2196F3;
    }

    .use-points-btn:hover {
        background-color: #1976D2;
    }

    .shortage-amount {
        color: #f44336;
        font-weight: bold;
    }

    .change-amount {
        color: #4CAF50;
        font-weight: bold;
    }

    .points-info {
        font-size: 11px;
        color: #666;
        margin-left: 10px;
        font-style: italic;
    }

    .points-used-info,
    .points-add-info {
        background-color: #e8f5e8;
        padding: 8px;
        border-radius: 4px;
        border-left: 3px solid #4CAF50;
    }

    .points-used-info {
        background-color: #e3f2fd;
        border-left-color: #2196F3;
    }

    .points-used,
    .points-add {
        color: #2e7d32;
        font-weight: 500;
    }

    .points-used {
        color: #1565C0;
    }

    /* Responsive design cho các nút điểm */
    @media (max-width: 768px) {
        .points-btn {
            padding: 4px 6px;
            font-size: 10px;
            margin-left: 5px;
        }

        .points-info {
            display: block;
            margin-left: 0;
            margin-top: 5px;
        }

        .points-used-info,
        .points-add-info {
            padding: 6px;
            font-size: 12px;
        }
    }

    /* Animation cho các thay đổi điểm */
    .points-btn.loading {
        opacity: 0.7;
        cursor: not-allowed;
    }

    .points-btn.loading:after {
        content: '';
        width: 12px;
        height: 12px;
        border: 2px solid transparent;
        border-top: 2px solid #fff;
        border-radius: 50%;
        animation: spin 1s linear infinite;
        margin-left: 5px;
    }

    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }

</style>

<button class="payment-close" onclick="hidePaymentPanel()" title="Đóng">
    <i class="fas fa-times"></i>
</button>
<div class="payment-header">
    <div class="staff-select">
        <i class="fas fa-user"></i>
        <select id="staffDropdown" name="staffId">
            <c:forEach var="user" items="${sessionScope.listUsers}">
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
        <span id="originalAmountDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getSubTotal()}" type="number" pattern="#,###"/> đ</span>     
        <input type="hidden" id="originalAmount" name="originalAmount" value="${sessionScope.currentInvoice.getSubTotal()}" />
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
        <span id="subtotalDisplay"><fmt:formatNumber value="${sessionScope.currentInvoice.getAfterdiscountAmount()}" type="number" pattern="#,###"/> đ</span>
        <input type="hidden" id="subtotal" name="subtotal" value="${sessionScope.currentInvoice.getAfterdiscountAmount()}" />
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
            <input type="number" id="paidAmount" name="paidAmount" 
                   value="<fmt:formatNumber value='${sessionScope.currentInvoice.getPaidAmount()}' type='number' pattern='#,###'/>" 
                   min="0" 
                   max="999999999999"
                   step="1">
            <span class="currency-label">đ</span>
        </div>
    </div>

    <!-- Tiền thiếu - CHỈ hiển thị với khách hàng có tài khoản (customerId > 1) -->
    <c:set var="customerId" value="${sessionScope.currentInvoice.getCustomer() != null ? sessionScope.currentInvoice.getCustomer().getCustomerId() : 0}" />
    <c:if test="${customerId != null && customerId > 1}">
        <c:choose>
            <c:when test="${not empty sessionScope.currentInvoice.getShortAmount() and sessionScope.currentInvoice.getShortAmount() > 0}">
                <div class="payment-row" id="shortageRow" style="display: block;">
                    <label>Tiền thiếu:</label>
                    <span id="shortageAmount" class="shortage-amount">
                        <fmt:formatNumber value="${sessionScope.currentInvoice.getShortAmount()}" type="number" pattern="#,###"/> đ
                    </span>
                    <c:set var="customerPoints" value="${sessionScope.currentInvoice.getCustomer().getPoints() != null ? sessionScope.currentInvoice.getCustomer().getPoints() : 0}" />
                    <c:set var="maxUsablePoints" value="${customerPoints > 0 ? Math.floor(sessionScope.currentInvoice.getShortAmount() / 1000) : 0}" />
                    <c:set var="actualUsablePoints" value="${maxUsablePoints > customerPoints ? customerPoints : maxUsablePoints}" />

                    <c:if test="${actualUsablePoints > 0}">
                        <button type="button" class="points-btn use-points-btn" id="use-points" title="Sử dụng điểm">
                            <i class="fas fa-star"></i> Sử dụng <fmt:formatNumber value="${actualUsablePoints}" type="number" pattern="#,###"/> điểm
                        </button>
                    </c:if>

                    <span class="points-info" id="pointsInfo">
                        (Điểm hiện có: 
                        <span id="customerPoints">
                            <fmt:formatNumber value="${customerPoints}" type="number" pattern="#,###"/>
                        </span> điểm = <span>
                            <fmt:formatNumber value="${customerPoints * 1000}" type="number" pattern="#,###"/>
                        </span> VND)
                    </span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="payment-row" id="shortageRow" style="display: none;">
                    <label>Tiền thiếu:</label>
                    <span id="shortageAmount" class="shortage-amount">0 đ</span>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <!-- Tiền thừa - Cập nhật điều kiện tương tự -->
    <c:choose>
        <c:when test="${not empty sessionScope.currentInvoice.getChangeAmount() and sessionScope.currentInvoice.getChangeAmount() > 0}">
            <div class="payment-row" id="changeRow" style="display: block;">
                <label>Tiền thừa:</label>
                <span id="changeAmount" class="change-amount">
                    <fmt:formatNumber value="${sessionScope.currentInvoice.getChangeAmount()}" type="number" pattern="#,###"/> đ
                </span>
                <c:if test="${customerId != null && customerId > 1}">
                    <c:set var="pointsFromChange" value="${Math.floor(sessionScope.currentInvoice.getChangeAmount() / 50000)}" />
                    <c:if test="${pointsFromChange > 0}">
                        <button type="button" class="points-btn" id="add-points" title="Tích điểm">
                            <i class="fas fa-plus"></i> Tích <fmt:formatNumber value="${pointsFromChange}" type="number" pattern="#,###"/> điểm
                        </button>
                    </c:if>
                </c:if>
            </div>
        </c:when>
        <c:otherwise>
            <div class="payment-row" id="changeRow" style="display: none;">
                <label>Tiền thừa:</label>
                <span id="changeAmount" class="change-amount">0 đ</span>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Hiển thị thông tin điểm đã sử dụng/sẽ tích (nếu có) -->
    <c:if test="${customerId != null && customerId > 1}">
        <c:if test="${sessionScope.currentInvoice.getPointsUsed() > 0}">
            <div class="payment-row points-used-info">
                <label>Điểm đã sử dụng:</label>
                <span class="points-used">
                    <fmt:formatNumber value="${sessionScope.currentInvoice.getPointsUsed()}" type="number" pattern="#,###"/> điểm
                    = <fmt:formatNumber value="${sessionScope.currentInvoice.getPointsUsed() * 1000}" type="number" pattern="#,###"/> đ
                </span>
            </div>
        </c:if>

        <c:if test="${sessionScope.currentInvoice.getPointsToAdd() > 0}">
            <div class="payment-row points-add-info">
                <label>Điểm sẽ tích:</label>
                <span class="points-add">
                    <fmt:formatNumber value="${sessionScope.currentInvoice.getPointsToAdd()}" type="number" pattern="#,###"/> điểm
                    (từ <fmt:formatNumber value="${sessionScope.currentInvoice.getPointsToAdd() * 1000}" type="number" pattern="#,###" /> đ tiền thừa)
                </span>
            </div>
        </c:if>
    </c:if>
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

    <!-- Cash Funds -->
    <div id="cashFunds" style="display: block; margin-top: 15px;">
        <select id="cashFundSelect" name="cashFundId" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 6px;">
            <option value="">Chọn quỹ tiền mặt</option>
            <c:forEach var="fund" items="${sessionScope.cashs}">
                <c:if test="${fund.fundType eq 'Cash'}">
                    <option value="${fund.fundID}">
                        ${fund.fundName}
                    </option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- Bank Accounts -->
    <div id="bankAccounts" style="display: none; margin-top: 15px;">
        <select id="bankAccountSelect" name="bankFundId" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 6px;">
            <option value="">Chọn tài khoản ngân hàng</option>
            <c:forEach var="bank" items="${sessionScope.banks}">
                <c:if test="${bank.fundType eq 'Bank'}">
                    <option value="${bank.fundID}">
                        ${bank.bankName} - ${bank.accountNumber}
                    </option>
                </c:if>
            </c:forEach>
        </select>
    </div>
</div>

<div class="payment-actions">
    <button class="payment-btn" id="checkout">
        <i class="fas fa-check"></i> XÁC NHẬN THANH TOÁN
    </button>
</div>

<script>
    $(document).ready(function () {
        // Toggle payment methods
        $('input[name="paymentMethod"]').change(function () {
            if ($(this).val() === '1') {
                $('#cashFunds').show();
                $('#bankAccounts').hide();
            } else {
                $('#cashFunds').hide();
                $('#bankAccounts').show();
            }
        });
    });
</script>