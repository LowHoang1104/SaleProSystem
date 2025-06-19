<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <button class="cash-close" onclick="hideCashPanel()" title="Đóng">
        <i class="fas fa-times"></i>
    </button>

    <div class="cash-header">
        <h3>
            <i class="fas fa-money-bill-wave"></i>
            Quản lý tiền mặt
        </h3>
        <span id="cashSessionTime">${currentTime}</span>
    </div>

    <!-- Top Section: Session Type + Fund Selection + System Balance -->
    <div class="top-section">
        <!-- Session Type Selection -->
        <div class="session-type-section">
            <h4><i class="fas fa-clock"></i> Loại phiên kiểm kê</h4>
            <div class="session-type-options">
                <label class="session-type-option selected">
                    <input type="radio" name="sessionType" value="Opening" 
                           ${sessionType == 'Opening' || sessionType == null ? 'checked' : ''}>
                    <i class="fas fa-sun"></i>
                    <span>Mở ca</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Closing"
                           ${sessionType == 'Closing' ? 'checked' : ''}>
                    <i class="fas fa-moon"></i>
                    <span>Đóng ca</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Update"
                           ${sessionType == 'Update' ? 'checked' : ''}>
                    <i class="fas fa-sync-alt"></i>
                    <span>Cập nhật</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Manual"
                           ${sessionType == 'Manual' ? 'checked' : ''}>
                    <i class="fas fa-search"></i>
                    <span>Kiểm tra</span>
                </label>
            </div>
        </div>

        <!-- Fund Selection + System Balance -->
        <div class="fund-balance-section">
            <div class="fund-selection">
                <h4><i class="fas fa-wallet"></i> Chọn quỹ</h4>
                <select id="fundSelect" class="fund-select" onchange="onFundSelectionChange(this.value)">
                    <c:forEach var="item" items="${storeFundCash}">
                        <option value="${item.fundID}" <c:if test="${sessionScope.currentStoreFund.fundID == item.fundID}">selected="selected"</c:if>>
                            ${item.fundName} (<fmt:formatNumber value="${item.currentBalance}" type="number" pattern="#,###"/> VND)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="system-balance">
                <h4>Số dư hệ thống</h4>
                <div class="balance-amount" id="systemBalanceAmount">
                    <fmt:formatNumber value="${sessionScope.currentStoreFund.currentBalance}" type="number" pattern="#,###"/> đ
                </div>
                <input type="hidden" id="systemBalance" value="${sessionScope.currentStoreFund.currentBalance}">
            </div>
        </div>
    </div>

    <!-- Cash Count Form -->
    <div class="cash-count-form">
        <h4><i class="fas fa-calculator"></i> Đếm tiền thực tế</h4>
        <div class="denomination-grid">
            <c:forEach var="denomination" items="${sessionScope.currencyDenominations}">
                <div class="denomination-item">
                    <span class="denomination-value">${denomination.displayName}</span>
                    <div class="quantity-input-group">
                        <c:set var="currentQuantity" value="0"/>
                        <c:set var="currentAmount" value="0"/>

                        <c:forEach var="detail" items="${cashCountDetails}">
                            <c:if test="${detail.denominationID == denomination.denominationID}">
                                <c:set var="currentQuantity" value="${detail.quantity}"/>
                                <c:set var="currentAmount" value="${detail.amount}"/>
                            </c:if>
                        </c:forEach>

                        <input type="number" class="quantity-input" min="0" 
                               value="${currentQuantity}" 
                               data-value="${denomination.value}" 
                               data-denomination-id="${denomination.denominationID}"
                               onchange="calculateAmount(this)">
                        <span>tờ</span>
                    </div>
                    <span class="amount-display">
                        <fmt:formatNumber value="${currentAmount}" type="number" pattern="#,###"/> VND
                    </span>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Total Summary -->
    <div class="total-summary">
        <div class="summary-row">
            <span>Số dư hệ thống:</span>
            <span id="summarySystemBalance"><fmt:formatNumber value="${sessionScope.currentStoreFund.currentBalance}" type="number" pattern="#,###"/> VND</span>
        </div>
        <div class="summary-row">
            <span>Tổng đếm được:</span>
            <span id="totalCounted"><fmt:formatNumber value="${totalCounted}" type="number" pattern="#,###"/> VND</span>
        </div>
        <div class="summary-row">
            <span>Chênh lệch:</span>
            <span id="difference"><fmt:formatNumber value="${difference}" type="number" pattern="#,###"/> VND</span>
        </div>
    </div>

    <!-- Difference Alert -->
    <div class="difference-alert" id="differenceAlert">
        <h5 id="differenceTitle">Có chênh lệch</h5>
        <p id="differenceMessage">Vui lòng kiểm tra lại số tiền đếm được.</p>
    </div>

    <!-- Notes Section -->
    <div class="notes-section">
        <h4><i class="fas fa-sticky-note"></i> Ghi chú</h4>
        <textarea class="notes-textarea" id="sessionNotes" 
                  placeholder="Nhập ghi chú cho phiên kiểm kê (nếu có)..."></textarea>
    </div>

    <!-- Action Buttons -->
    <div class="cash-actions">
        <button class="cash-btn secondary" onclick="clearTempData()">
            <i class="fas fa-arrow-left"></i> Hủy bỏ
        </button>
        <button class="cash-btn primary" id="saveCashCount" onclick="saveCashCount()">
            <i class="fas fa-save"></i> Lưu kiểm kê
        </button>
    </div>