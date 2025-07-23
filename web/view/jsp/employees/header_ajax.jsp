<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="headerSection">   
    <div class="header">
        <div class="search-section">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" placeholder="Tìm hàng hóa (F3)" id="productSearch">
            </div>
        </div>
        <div class="tab-section">
            <c:forEach var="invoice" items="${sessionScope.invoices}">
                <div class="invoice-tab ${invoice.id == sessionScope.currentInvoiceId ? 'active' : ''}" data-id="${invoice.id}">
                    <i class="fas fa-file-invoice"></i>
                    <span>${invoice.name}</span>
                    <button class="removeInvoiceBtn" data-id="${invoice.id}" title="Xóa hóa đơn">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </c:forEach>
        </div>

        <div class="header-actions">
            <i class="fas fa-plus" id="addInvoiceBtn" title="Thêm hóa đơn"></i>
            <i class="fas fa-undo" title="Hoàn tác"></i>
            <i class="fas fa-print" title="In"></i>
            <i class="fas fa-money-bill-wave" id="cashManagementBtn" title="Quản lý tiền mặt" onclick="showCashPanel()" style="color: #ff5722; cursor: pointer;"></i>
            <span class="phone-number">${phoneNumber}</span>
            <i class="fas fa-bars" id="menuBtn" title="Menu"></i>
        </div>
    </div>
</div>

<!-- Side Panel Overlay -->
<div class="side-panel-overlay" id="sidePanelOverlay">
    <div class="side-panel" id="sidePanel">
        <div class="panel-header">
            <h3>Menu</h3>
            <button class="close-panel" id="closePanelBtn">
                <i class="fas fa-times"></i>
            </button>
        </div>

        <div class="panel-content">
            <!-- Báo cáo section -->
            <div class="menu-section">
                <div class="menu-section-title">Báo cáo</div>
                <div class="menu-item">
                    <i class="fas fa-chart-line"></i>
                    <span>Xem báo cáo cuối ngày</span>
                </div>
            </div>

            <!-- Quản lý section -->
            <div class="menu-section"> 
                <a href="${pageContext.request.contextPath}/InvoiceManagementServlet"">
                    <div class="menu-item">
                        <i class="fas fa-cog"></i>
                        <span>Quản lý</span>
                    </div>
                </a>
            </div>

            <!-- Hệ thống section -->
            <div class="menu-section">
                <div class="menu-item danger">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Đăng xuất</span>
                </div>
            </div>
        </div>
    </div>
</div>