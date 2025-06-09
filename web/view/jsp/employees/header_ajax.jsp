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
            <i class="fas fa-list" title="Danh sách hóa đơn"></i>
            <i class="fas fa-undo" title="Hoàn tác"></i>
            <i class="fas fa-print" title="In"></i>
            <span class="phone-number">${phoneNumber}</span>
            <i class="fas fa-bars" title="Menu"></i>
        </div>
    </div>
</div>

