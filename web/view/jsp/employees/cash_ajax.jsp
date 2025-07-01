<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="cashSection">
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
                    <input type="radio" name="sessionType" value="Opening" checked>
                    <i class="fas fa-sun"></i>
                    <span>Mở ca</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Closing">
                    <i class="fas fa-moon"></i>
                    <span>Đóng ca</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Update">
                    <i class="fas fa-sync-alt"></i>
                    <span>Cập nhật</span>
                </label>
                <label class="session-type-option">
                    <input type="radio" name="sessionType" value="Manual">
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
                    <option value="" <c:if test="${empty currentFundId or currentFundId eq ''}">
                            selected="selected"</c:if>>Tổng</option>
                    <c:forEach var="item" items="${storeFundCash}">
                        <option value="${item.fundID}" <c:if test="${currentFundId == item.fundID}">selected="selected"</c:if>>
                            ${item.fundName} (<fmt:formatNumber value="${item.currentBalance}" type="number" pattern="#,###"/> VND)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="system-balance">
                <h4>Số dư hệ thống</h4>
                <div class="balance-amount" id="systemBalanceAmount"><fmt:formatNumber value="${amount}" type="number" pattern="#,###"/> đ</div>
                <input type="hidden" id="systemBalance" value="${amount}">
            </div>
        </div>
    </div>

    <!-- Session Statistics -->
    <div id="sessionStats" class="session-stats">
        <h4>
            <i class="fas fa-chart-line"></i> Thống kê phiên làm việc
        </h4>
        <div class="stats-grid">
            <div class="stat-item">
                <div class="stat-label">Hóa đơn bán</div>
                <div class="stat-value" id="invoiceCount">0</div>
            </div>
            <div class="stat-item">
                <div class="stat-label">Tổng thu</div>
                <div class="stat-value" id="totalRevenue">0 đ</div>
            </div>
            <div class="stat-item">
                <div class="stat-label">Thời gian</div>
                <div class="stat-value" id="sessionDuration">0h 0m</div>
            </div>
        </div>
    </div>

    <!-- Invoice Reconciliation (Đối chiếu hóa đơn) -->
    <div id="invoiceReconciliation" class="invoice-reconciliation">
        <div class="reconciliation-header">
            <h4>
                <i class="fas fa-file-invoice-dollar"></i> Đối chiếu hóa đơn
            </h4>
            <button onclick="loadInvoiceList()" class="load-invoices-btn">
                <i class="fas fa-sync"></i> Tải danh sách
            </button>
        </div>

        <div class="time-range">
            <div class="time-input">
                <label>Từ thời gian:</label>
                <input type="datetime-local" id="fromTime">
            </div>
            <div class="time-input">
                <label>Đến thời gian:</label>
                <input type="datetime-local" id="toTime">
            </div>
        </div>

        <div class="invoice-table-container">
            <table class="invoice-table">
                <thead>
                    <tr>
                        <th>Mã HĐ</th>
                        <th>Thời gian</th>
                        <th>Tiền mặt</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody id="invoiceListBody">
                    <tr>
                        <td colspan="4" class="no-data">
                            Chưa có dữ liệu hóa đơn
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="invoice-summary">
            <span><strong>Tổng HĐ:</strong> <span id="totalInvoices">0</span></span>
            <span><strong>Tổng tiền mặt:</strong> <span id="totalCashFromInvoices">0 đ</span></span>
        </div>
    </div>

    <!-- Cash Count Form -->
    <div class="cash-count-form">
        <h4><i class="fas fa-calculator"></i> Đếm tiền thực tế</h4>
        <div class="denomination-grid">
            <!-- 500,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">500.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="500000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 200,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">200.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="200000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 100,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">100.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="100000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 50,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">50.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="50000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 20,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">20.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="20000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 10,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">10.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="10000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 5,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">5.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="5000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 2,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">2.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="2000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>

            <!-- 1,000 VND -->
            <div class="denomination-item">
                <span class="denomination-value">1.000 đ</span>
                <div class="quantity-input-group">
                    <input type="number" class="quantity-input" min="0" value="0" 
                           data-value="1000" onchange="calculateAmount(this)">
                    <span>tờ</span>
                </div>
                <span class="amount-display">0 đ</span>
            </div>
        </div>
    </div>

    <!-- Total Summary -->
    <div class="total-summary">
        <div class="summary-row">
            <span>Số dư hệ thống:</span>
            <span id="summarySystemBalance">0 đ</span>
        </div>
        <div class="summary-row">
            <span>Tổng đếm được:</span>
            <span id="totalCounted">0 đ</span>
        </div>
        <div class="summary-row">
            <span>Chênh lệch:</span>
            <span id="difference">0 đ</span>
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
        <button class="cash-btn secondary" onclick="hideCashPanel()">
            <i class="fas fa-arrow-left"></i> Hủy bỏ
        </button>
        <button class="cash-btn primary" id="saveCashCount" onclick="saveCashCount()">
            <i class="fas fa-save"></i> Lưu kiểm kê
        </button>
    </div>
</div>