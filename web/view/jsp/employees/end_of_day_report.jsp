<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Report Modal -->
<div class="report-modal" id="reportModal">
    <!-- Header -->
    <div class="report-header">
        <div class="report-title">Báo cáo cuối ngày</div>
        <div class="report-filters">
            <div class="filter-group">
                <input type="date" class="date-input" value="${selectedDate}">
                <i class="fas fa-calendar-alt" style="margin-left: 5px; color: #4285f4;"></i>
            </div>
            <div class="filter-group">
                <select class="filter-select" id="warehouseSelect">
                    <option value="A4" <c:if test="${selectedWarehouse == 'A4' or empty selectedWarehouse}">selected</c:if>>Khổ A4</option>
                    <option value="K80" <c:if test="${selectedWarehouse == 'K80'}">selected</c:if>>Khổ K80</option>
                    </select>
                </div>
                <div class="filter-group">
                    <select class="filter-select" id="employeeSelect">
                        <option value="">Nhân viên</option>
                    <c:forEach var="user" items="${listUsers}">
                        <option value="${user.getUserId()}"
                                <c:if test="${user.getUserId() == selectedEmployeeId}">selected</c:if>>
                            ${user.getFullName()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="filter-group">
                <select class="filter-select" id="creatorSelect">
                    <option value="">Người tạo</option>
                    <c:forEach var="user" items="${listUsers}">
                        <option value="${user.getUserId()}"
                                <c:if test="${user.getUserId() == selectedCreatorId}">selected</c:if>>
                            ${user.getFullName()}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <button class="close-report" onclick="hideEndOfDayReport()">
            <i class="fas fa-times"></i>
        </button>
    </div>

    <!-- Toolbar -->
    <div class="report-toolbar">
        <div class="toolbar-left">
            <button class="toolbar-btn" title="Hoàn tác" disabled>
                <i class="fas fa-undo"></i>
            </button>
            <button class="toolbar-btn" title="Làm lại" disabled>
                <i class="fas fa-redo"></i>
            </button>
            <button class="toolbar-btn" title="Làm mới" onclick="refreshReport()">
                <i class="fas fa-sync"></i>
            </button>
            <div class="toolbar-divider"></div>
            <button class="toolbar-btn" title="Trang đầu" onclick="goToFirstPage()" ${currentPage <= 1 ? 'disabled' : ''}>
                <i class="fas fa-step-backward"></i>
            </button>
            <button class="toolbar-btn" title="Trang trước" onclick="goToPrevPage()" ${currentPage <= 1 ? 'disabled' : ''}>
                <i class="fas fa-chevron-left"></i>
            </button>
        </div>

        <div class="page-info">
            <input type="text" class="page-input" value="${currentPage}" onchange="goToPage(this.value)">
            <span class="page-text">/ ${totalPages}</span>
            <button class="toolbar-btn" title="Trang sau" onclick="goToNextPage()" ${currentPage >= totalPages ? 'disabled' : ''}>
                <i class="fas fa-chevron-right"></i>
            </button>
            <button class="toolbar-btn" title="Trang cuối" onclick="goToLastPage()" ${currentPage >= totalPages ? 'disabled' : ''}>
                <i class="fas fa-step-forward"></i>
            </button>
        </div>

        <div class="toolbar-left">
            <div class="export-dropdown">
                <button class="export-btn" onclick="toggleExportMenu()">
                    <i class="fas fa-save"></i>
                </button>
                <div class="export-menu" id="exportMenu">
                    <div class="export-option" onclick="exportReport('pdf')">
                        <i class="fas fa-file-pdf" style="color: #d32f2f;"></i>
                        Acrobat (PDF) file
                    </div>
                    <div class="export-option" onclick="exportReport('excel')">
                        <i class="fas fa-file-excel" style="color: #2e7d32;"></i>
                        Excel 97-2003
                    </div>
                    <div class="export-option" onclick="exportReport('word')">
                        <i class="fas fa-file-word" style="color: #1976d2;"></i>
                        Word Document
                    </div>
                </div>
            </div>
            <button class="toolbar-btn" title="In báo cáo" onclick="printReport()">
                <i class="fas fa-print"></i>
            </button>
            <button class="toolbar-btn" title="Tìm kiếm" onclick="searchInReport()">
                <i class="fas fa-search"></i>
            </button>
            <button class="toolbar-btn" title="Phóng to" onclick="zoomIn()">
                <i class="fas fa-search-plus"></i>
            </button>
            <button class="toolbar-btn" title="Thu nhỏ" onclick="zoomOut()">
                <i class="fas fa-search-minus"></i>
            </button>
        </div>
    </div>

    <!-- Content -->
    <div class="report-content">

        <div class="report-info">
            <div class="creation-time">
                <jsp:useBean id="now" class="java.util.Date" />
                Ngày lập: <fmt:formatDate value="${now}" pattern="dd/MM/yyyy HH:mm" />
            </div>
            <h2 id="reportTitle">Báo cáo cuối ngày về bán hàng</h2>
            <div class="report-meta">
                <div class="meta-item">
                    <span class="meta-label">Ngày bán:</span> 
                    <c:choose>
                        <c:when test="${not empty selectedDate}">
                            <fmt:parseDate value="${selectedDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" />
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="meta-item">
                    <span class="meta-label">Ngày thanh toán:</span> 
                    <c:choose>
                        <c:when test="${not empty selectedDate}">
                            <fmt:parseDate value="${selectedDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${now}" pattern="dd/MM/yyyy" />
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="meta-item">
                    <span class="meta-label">Chi nhánh:</span> Chi nhánh trung tâm
                </div>

                <c:if test="${not empty selectedEmployeeId}">
                    <div class="meta-item">
                        <span class="meta-label">Nhân viên:</span> 
                        <c:forEach var="user" items="${listUsers}">
                            <c:if test="${user.getUserId() == selectedEmployeeId}">
                                ${user.getFullName()}
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty selectedCreatorId}">
                    <div class="meta-item">
                        <span class="meta-label">Người tạo:</span> 
                        <c:forEach var="user" items="${listUsers}">
                            <c:if test="${user.getUserId() == selectedCreatorId}">
                                ${user.getFullName()}
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Summary Report View -->
        <div id="summaryView" style="display: none;">
            <div class="summary-section">
                <div class="summary-header">Tổng kết thu chi</div>
                <c:set var="totalIncome" value="0" />
                <c:set var="quantity" value="0" />
                <c:forEach var="invoice" items="${listInvoice}">
                    <c:set var="totalIncome" value="${totalIncome + invoice.totalAmount}" />
                    <c:set var="quantity" value="${quantity + invoice.getQuantityById()}" />
                </c:forEach>

                <div class="summary-row">
                    <span class="summary-label">Tổng thu</span>
                    <span class="summary-value"><fmt:formatNumber value="${totalIncome}" type="number" pattern="#,###" /></span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Tổng chi</span>
                    <span class="summary-value negative">0</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Thu - chi</span>
                    <span class="summary-value"><fmt:formatNumber value="${totalIncome}" type="number" pattern="#,###" /></span>
                </div>
            </div>

            <div class="summary-section">
                <div class="summary-header">Phương thức thanh toán</div>
                <c:set var="totalCash" value="0" />
                <c:set var="totalBank" value="0" />
                <c:forEach var="invoice" items="${listInvoice}">
                    <c:choose>
                        <c:when test="${invoice.paymentMethodId == 1}">
                            <c:set var="totalCash" value="${totalCash + invoice.totalAmount}" />
                        </c:when>
                        <c:when test="${invoice.paymentMethodId == 2}">
                            <c:set var="totalBank" value="${totalBank + invoice.totalAmount}" />
                        </c:when>
                    </c:choose>
                </c:forEach>
                <div class="summary-row">
                    <span class="summary-label">Tiền mặt</span>
                    <span class="summary-value <c:if test='${totalCash == 0}'>zero</c:if>">
                        <c:choose>
                            <c:when test="${totalCash > 0}">
                                <fmt:formatNumber value="${totalCash}" type="number" pattern="#,###" />
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Chuyển khoản</span>
                    <span class="summary-value <c:if test='${totalBank == 0}'>zero</c:if>">
                        <c:choose>
                            <c:when test="${totalBank > 0}">
                                <fmt:formatNumber value="${totalBank}" type="number" pattern="#,###" />
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Voucher</span>
                    <span class="summary-value">0</span>
                </div>
            </div>

            <div class="summary-section">
                <div class="summary-header">Tổng kết bán hàng</div>
                <div class="summary-row highlight">
                    <span class="summary-label">Đặt hàng</span>
                    <span class="summary-value">0</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Giá trị</span>
                    <span class="summary-value">0</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">SL sản phẩm</span>
                    <span class="summary-value">0</span>
                </div>
                <div class="summary-row highlight">
                    <span class="summary-label">Hóa đơn</span>
                    <span class="summary-value">${fn:length(listInvoice)}</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Giá trị</span>
                    <span class="summary-value"><fmt:formatNumber value="${totalIncome}" type="number" pattern="#,###" /></span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">SL sản phẩm</span>
                    <span class="summary-value"><fmt:formatNumber value="${quantity}" type="number" pattern="#,###" /></span>
                </div>
                <div class="summary-row highlight">
                    <span class="summary-label">Phiếu trả</span>
                    <span class="summary-value">0</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">Giá trị</span>
                    <span class="summary-value">0</span>
                </div>
                <div class="summary-row">
                    <span class="summary-label">SL sản phẩm</span>
                    <span class="summary-value">0</span>
                </div>
            </div>
        </div>

        <!-- Detail Table View -->
        <div id="detailView" class="table-section">
            <table class="report-table">
                <thead>
                    <tr>
                        <th>Mã giao dịch</th>
                        <th>Thời gian</th>
                        <th>SL</th>
                        <th>Doanh thu</th>
                        <th>VAT</th>
                        <th>Làm tròn</th>
                        <th>Phí trả hàng</th>
                        <th>Thực thu</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="invoice" items="${listInvoice}">
                        <tr class="highlight">
                            <td>
                                <button class="expand-btn">
                                    <i class="fas fa-plus-square"></i>
                                </button>
                                Hóa đơn: ${invoice.invoiceId}
                            </td>
                            <td><fmt:formatDate value="${invoice.invoiceDate}" pattern="HH:mm" /></td>
                            <td><fmt:formatNumber value="${invoice.getQuantityById()}" type="number" pattern="#,###" /></td>
                            <td><fmt:formatNumber value="${invoice.subTotal}" type="number" pattern="#,###" /></td>
                            <td><fmt:formatNumber value="${invoice.VATAmount}" type="number" pattern="#,###" /></td>
                            <td class="zero-amount">0</td>
                            <td class="zero-amount">0</td>
                            <td class="amount"><fmt:formatNumber value="${invoice.totalAmount}" type="number" pattern="#,###" /></td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty listInvoice}">
                        <tr>
                            <td colspan="9" style="text-align: center; padding: 20px; color: #666;">
                                Báo cáo không có dữ liệu
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- Hiển thị thông tin tổng kết ở cuối -->
        <c:if test="${not empty listInvoice}">
            <div style="text-align: center; margin-top: 20px; padding: 15px; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                <div style="font-size: 14px; color: #666;">
                    Hiển thị <strong>${(currentPage-1)*pageSize + 1}</strong> - <strong>${(currentPage-1)*pageSize + fn:length(listInvoice)}</strong> 
                    trong tổng số <strong>${totalInvoices}</strong> hóa đơn
                </div>
            </div>
        </c:if>
    </div>
</div>


