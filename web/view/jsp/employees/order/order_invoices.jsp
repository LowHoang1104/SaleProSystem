<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="16kb" autoFlush="true" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <title>Order Invoices - Quản lý hóa đơn</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <!-- Third-party CSS -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">

        <!-- FontAwesome -->
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">

        <!-- Main theme styles -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">

        <!-- Invoice Management Styles -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/employees/order/invoice-management.css">  </head>
    <body>     
        <%@include file="/view/jsp/admin/HeadSideBar/header.jsp" %>
        <%@include file="/view/jsp/admin/HeadSideBar/sidebar.jsp" %>

        <!-- Main Invoice Management Content -->
        <div class="page-wrapper">
            <div class="content">
                <!-- Page Header -->
                <div class="page-header">
                    <div class="page-title">
                        <h4>Hóa đơn</h4>
                        <h6>Quản lý hóa đơn bán hàng</h6>
                    </div>

                    <div class="header-search">
                        <div class="search-container">
                            <div class="search-box">
                                <input type="text" class="form-control" id="mainSearchInput" 
                                       placeholder="Tìm kiếm mã hóa đơn, khách hàng...">
                                <button class="btn-search" type="button" id="searchBtn" title="Tìm kiếm">
                                    <i class="fas fa-bars"></i>
                                </button>
                            </div>

                            <!-- Search Popup -->
                            <div class="search-popup" id="searchPopup">
                                <h6><i class="fas fa-filter me-2"></i>Tùy chọn tìm kiếm</h6>

                                <div class="search-option">
                                    <input type="text" id="searchInvoiceCode" placeholder="Theo mã hóa đơn">
                                </div>

                                <div class="search-option">
                                    <input type="text" id="searchProductName" placeholder="Theo mã, tên hàng">
                                </div>

                                <div class="search-option">
                                    <input type="text" id="searchCustomerInfo" placeholder="Theo mã, tên, số điện thoại khách hàng">
                                </div>

                                <div class="search-actions">
                                    <button type="button" class="btn btn-primary btn-sm" id="executeSearchBtn">
                                        <i class="fas fa-search me-1"></i>Tìm kiếm
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="page-btn">
                        <a href="#" class="btn btn-added" id="btn-added">
                            <i class="fas fa-plus me-1"></i>
                            Tạo mới
                        </a>

                        <!-- Import File Popup Trigger -->
                        <a href="#" class="btn btn-outline-secondary" id="importFileTrigger" data-bs-toggle="modal" data-bs-target="#importModal">
                            <i class="fas fa-file-import me-1"></i>Import file
                        </a>

                        <!-- Import Modal -->
                        <div class="modal fade" id="importModal" tabindex="-1" aria-labelledby="importModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="importModalLabel">Import File Excel</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="importForm" action="<%=path%>/ImportExportServlet" method="post" enctype="multipart/form-data">
                                            <div class="mb-3">
                                                <label for="excelFile" class="form-label">Chọn file Excel (.xls, .xlsx):</label>
                                                <input type="file" class="form-control" id="excelFile" name="excelFile" accept=".xls,.xlsx" required>
                                            </div>
                                            <input type="hidden" name="action" value="import">
                                            <button type="submit" class="btn btn-primary" id="importSubmit">Import</button>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Export File Button -->
                        <a href="#" class="btn btn-outline-secondary" id="exportFileTrigger">
                            <i class="fas fa-file-export me-1"></i>Xuất file
                        </a>

                        <!-- Column Settings Button với Popup -->
                        <div class="column-settings-container">
                            <button class="btn btn-outline-secondary" type="button" id="columnSettingsBtn" title="Tùy chọn cột">
                                <i class="fas fa-columns"></i>
                            </button>

                            <!-- Column Settings Popup -->
                            <div class="column-settings-popup" id="columnSettingsPopup">
                                <h6><i class="fas fa-columns me-2"></i>Tùy chọn hiển thị cột</h6>

                                <div class="column-checkboxes">
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="col-invoice-code" checked disabled>
                                            <label class="form-check-label" for="col-invoice-code">Mã hóa đơn</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-create-time" data-column="create-time" checked>
                                            <label class="form-check-label" for="col-create-time">Thời gian tạo</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-update-time" data-column="update-time">
                                            <label class="form-check-label" for="col-update-time">Ngày cập nhật</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-customer" data-column="customer" checked>
                                            <label class="form-check-label" for="col-customer">Khách hàng</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-email" data-column="email">
                                            <label class="form-check-label" for="col-email">Email</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-phone" data-column="phone">
                                            <label class="form-check-label" for="col-phone">Điện thoại</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-birthday" data-column="birthday">
                                            <label class="form-check-label" for="col-birthday">Ngày sinh</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-branch" data-column="branch">
                                            <label class="form-check-label" for="col-branch">Chi nhánh</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-total" data-column="total" checked>
                                            <label class="form-check-label" for="col-total">Tổng tiền hàng</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-discount" data-column="discount" checked>
                                            <label class="form-check-label" for="col-discount">Giảm giá</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-vat" data-column="vat">
                                            <label class="form-check-label" for="col-vat">VAT</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-need-pay" data-column="need-pay">
                                            <label class="form-check-label" for="col-need-pay">Khách cần trả</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-paid" data-column="paid" checked>
                                            <label class="form-check-label" for="col-paid">Khách đã trả</label>
                                        </div>
                                    </div>
                                    <div class="column-option">
                                        <div class="form-check">
                                            <input class="form-check-input column-toggle" type="checkbox" id="col-status" data-column="status" checked>
                                            <label class="form-check-label" for="col-status">Trạng thái</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



                <!-- Main Layout: Sidebar + Content -->
                <div class="invoice-layout">
                    <!-- Left Sidebar Filters -->
                    <div class="invoice-sidebar">
                        <div class="filter-card">

                            <!-- Time Filter Section - SIMPLE VERSION -->
                            <div class="filter-section time-enhanced">
                                <h6><i class="fas fa-calendar-alt"></i>Thời gian</h6>

                                <!-- Theo ngày -->
                                <div class="time-filter-section">
                                    <label class="time-filter-label">Theo ngày</label>
                                    <div class="time-options">
                                        <button class="time-btn active" data-value="today">
                                            <i class="fas fa-calendar-day"></i>Hôm nay
                                        </button>
                                        <button class="time-btn" data-value="yesterday">
                                            <i class="fas fa-history"></i>Hôm qua
                                        </button>
                                    </div>
                                </div>

                                <!-- Theo tuần -->
                                <div class="time-filter-section">
                                    <label class="time-filter-label">Theo tuần</label>
                                    <div class="time-options">
                                        <button class="time-btn" data-value="this_week">
                                            <i class="fas fa-calendar-week"></i>Tuần này
                                        </button>
                                        <button class="time-btn" data-value="last_week">Tuần trước</button>
                                        <button class="time-btn" data-value="7_days">7 ngày qua</button>
                                    </div>
                                </div>

                                <!-- Theo tháng -->
                                <div class="time-filter-section">
                                    <label class="time-filter-label">Theo tháng</label>
                                    <div class="time-options">
                                        <button class="time-btn" data-value="this_month">
                                            <i class="fas fa-calendar"></i>Tháng này
                                        </button>
                                        <button class="time-btn" data-value="last_month">Tháng trước</button>
                                        <button class="time-btn" data-value="30_days">30 ngày qua</button>
                                    </div>
                                </div>

                                <!-- Theo quý -->
                                <div class="time-filter-section">
                                    <label class="time-filter-label">Theo quý</label>
                                    <div class="time-options">
                                        <button class="time-btn" data-value="this_quarter">Quý này</button>
                                        <button class="time-btn" data-value="last_quarter">Quý trước</button>
                                    </div>
                                </div>

                                <!-- Theo năm -->
                                <div class="time-filter-section">
                                    <label class="time-filter-label">Theo năm</label>
                                    <div class="time-options">
                                        <button class="time-btn" data-value="this_year">
                                            <i class="fas fa-calendar-alt"></i>Năm nay
                                        </button>
                                        <button class="time-btn" data-value="last_year">Năm trước</button>
                                    </div>
                                </div>
                            </div>

                            <!-- Invoice Type Filter -->
                            <div class="filter-section">
                                <h6><i class="fas fa-file-invoice"></i>Loại hóa đơn</h6>
                                <div class="filter-checkbox-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="noDelivery" checked>
                                        <label class="form-check-label" for="noDelivery">
                                            <i class="fas fa-store me-1"></i>Không giao hàng
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="delivery" checked>
                                        <label class="form-check-label" for="delivery">
                                            <i class="fas fa-truck me-1"></i>Giao hàng
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <!-- Status Filter -->
                            <div class="filter-section">
                                <h6><i class="fas fa-tasks"></i>Trạng thái hóa đơn</h6>
                                <div class="filter-checkbox-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="processing" checked>
                                        <label class="form-check-label" for="processing">
                                            <i class="fas fa-clock text-warning me-1"></i>Đang xử lý
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="completed" checked>
                                        <label class="form-check-label" for="completed">
                                            <i class="fas fa-check-circle text-success me-1"></i>Hoàn thành
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="cancelled">
                                        <label class="form-check-label" for="cancelled">
                                            <i class="fas fa-times-circle text-danger me-1"></i>Không giao được
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="refunded">
                                        <label class="form-check-label" for="refunded">
                                            <i class="fas fa-undo text-secondary me-1"></i>Đã hủy
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <!-- Additional Filters -->
                            <div class="filter-section">
                                <h6><i class="fas fa-shipping-fast"></i>Trạng thái giao hàng</h6>
                                <select class="form-select">
                                    <option value="">Chọn trạng thái</option>
                                    <option value="pending">Đang chờ</option>
                                    <option value="shipping">Đang giao</option>
                                    <option value="delivered">Đã giao</option>
                                </select>
                            </div>

                            <div class="filter-section">
                                <h6><i class="fas fa-map-marker-alt"></i>Khu vực giao hàng</h6>
                                <input type="text" class="form-control" placeholder="Chọn Tỉnh/TP - Quận/Huyện">
                            </div>

                            <!-- Phương thức thanh toán -->
                            <div class="filter-section">
                                <h6><i class="fas fa-credit-card"></i>Phương thức thanh toán</h6>
                                <select class="form-select" data-filter="paymentMethod">
                                    <option value="">Tất cả phương thức</option>
                                    <option value="1">Tiền mặt</option>
                                    <option value="2">Chuyển khoản</option>
                                </select>
                            </div>

                            <!-- Người tạo -->
                            <div class="filter-section">
                                <h6><i class="fas fa-user-plus"></i>Người tạo</h6>
                                <select class="form-select" data-filter="createdBy">
                                    <option value="">Tất cả người tạo</option>
                                    <c:forEach var="user" items="${listUsers}">
                                        <option value="${user.getUserId()}">${user.getFullName()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Người bán -->
                            <div class="filter-section">
                                <h6><i class="fas fa-user-tie"></i>Người bán</h6>
                                <select class="form-select" data-filter="soldBy">
                                    <option value="">Tất cả người bán</option>
                                    <c:forEach var="user" items="${listUsers}">
                                        <option value="${user.getUserId()}">${user.getFullName()}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="filter-section">
                                <h6><i class="fas fa-store"></i>Kênh bán</h6>
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Chọn kênh bán">
                                    <button class="btn btn-outline-primary btn-sm" type="button">
                                        <i class="fas fa-plus"></i>Tạo mới
                                    </button>
                                </div>
                            </div>

                            <!-- Filter Actions -->
                            <div class="filter-actions">
                                <button type="button" class="btn btn-outline-secondary w-100" id="resetFilter">
                                    <i class="fas fa-undo me-1"></i>Đặt lại bộ lọc
                                </button>
                            </div>

                            <!-- Filter Results -->
                            <div class="filter-results" id="filterResults" style="display: none;">
                                <div class="result-info">
                                    <i class="fas fa-info-circle me-1"></i>
                                    Tìm thấy <span class="result-count" id="resultCount">0</span> hóa đơn
                                    từ <strong class="result-range" id="dateRange">hôm nay</strong>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Main Content Area -->
                    <div class="invoice-content">
                        <!-- Table Container with Fixed Height Structure -->
                        <div class="table-container">
                            <!-- Table Wrapper for synchronized scrolling -->
                            <div class="table-wrapper">
                                <!-- Table Header - Fixed with sync scroll -->
                                <!-- Enhanced Table Header with Direct Sales columns only -->
                                <div class="table-header">
                                    <div class="table-header-scroll">
                                        <table class="invoice-table">
                                            <!-- Simplified colgroup for direct sales -->
                                            <colgroup>
                                                <col class="col-checkbox">
                                                <col class="col-invoice-code" >
                                                <col class="col-create-time" data-column="create-time" >
                                                <col class="col-update-time" data-column="update-time" >
                                                <col class="col-customer" data-column="customer">
                                                <col class="col-email" data-column="email">
                                                <col class="col-phone" data-column="phone">
                                                <col class="col-birthday" data-column="birthday" >
                                                <col class="col-branch" data-column="branch" >
                                                <col class="col-total" data-column="total" >
                                                <col class="col-discount" data-column="discount" >
                                                <col class="col-vat" data-column="vat" >
                                                <col class="col-need-pay" data-column="need-pay" >
                                                <col class="col-paid" data-column="paid" >
                                                <col class="col-status" data-column="status">
                                                <col class="col-actions">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th class="col-checkbox">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="checkbox" id="selectAll">
                                                        </div>
                                                    </th>

                                                    <th class="col-invoice-code sortable" data-column="id">Mã hóa đơn</th>
                                                    <th class="col-create-time sortable" data-column="create-time">Thời gian tạo</th>
                                                    <th class="col-update-time sortable" data-column="update-time">Ngày cập nhật</th>
                                                    <th class="col-customer sortable" data-column="customer">Khách hàng</th>
                                                    <th class="col-email sortable" data-column="email">Email</th>
                                                    <th class="col-phone sortable" data-column="phone">Điện thoại</th>
                                                    <th class="col-birthday sortable" data-column="birthday">Ngày sinh</th>
                                                    <th class="col-branch sortable" data-column="branch">Chi nhánh</th>
                                                    <th class="col-total sortable" data-column="total">Tổng tiền hàng</th>
                                                    <th class="col-discount sortable" data-column="discount">Giảm giá</th>
                                                    <th class="col-vat sortable" data-column="vat">VAT</th>  
                                                    <th class="col-need-pay sortable" data-column="need-pay">Khách cần trả</th>
                                                    <th class="col-paid sortable" data-column="paid">Khách đã trả</th>

                                                    <th class="col-status sortable" data-column="status">Trạng thái</th>
                                                    <th class="col-actions">Thao tác</th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>

                                <!-- Enhanced Table Body -->
                                <div class="table-body-container">
                                    <div class="table-responsive">
                                        <table class="invoice-table" id="invoiceTable">
                                            <!-- Matching colgroup -->
                                            <colgroup>
                                                <col class="col-checkbox">

                                                <col class="col-invoice-code" >
                                                <col class="col-create-time" data-column="create-time" >
                                                <col class="col-update-time" data-column="update-time" >
                                                <col class="col-customer" data-column="customer">
                                                <col class="col-email" data-column="email">
                                                <col class="col-phone" data-column="phone">
                                                <col class="col-birthday" data-column="birthday" >
                                                <col class="col-branch" data-column="branch" >
                                                <col class="col-total" data-column="total" >
                                                <col class="col-discount" data-column="discount" >
                                                <col class="col-vat" data-column="vat" >
                                                <col class="col-need-pay" data-column="need-pay" >
                                                <col class="col-paid" data-column="paid" >

                                                <col class="col-status" data-column="status">
                                                <col class="col-actions">
                                            </colgroup>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty listInvoice}">
                                                        <c:forEach var="invoice" items="${listInvoice}" varStatus="status">
                                                            <tr class="invoice-row" data-invoice-id="${invoice.invoiceId}">
                                                                <!-- Checkbox -->
                                                                <td><div class="form-check"><input class="form-check-input" type="checkbox" value="${invoice.invoiceId}"></div></td>

                                                                <!-- Invoice Code -->
                                                                <td data-column="invoice-code"><a href="#" class="invoice-link text-primary">${invoice.invoiceCode}</a></td>

                                                                <!-- Create Time -->
                                                                <td data-column="create-time">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.invoiceDate != null}">
                                                                            <fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy HH:mm" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">N/A</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Update Time -->
                                                                <td data-column="update-time">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.updateDate != null}">
                                                                            <fmt:formatDate value="${invoice.updateDate}" pattern="dd/MM/yyyy HH:mm" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">N/A</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Customer -->
                                                                <td data-column="customer" title="${invoice.getCustomerNameByID() != null ? invoice.getCustomerNameByID() : 'Khách lẻ'}">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.customerId != null && invoice.customerId > 0}">
                                                                            ${invoice.getCustomerNameByID()}
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">Khách lẻ</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Email -->
                                                                <td data-column="email">
                                                                    <span class="text-muted">
                                                                        <c:choose>
                                                                            <c:when test="${not empty invoice.getCustomer().email}">
                                                                                ${invoice.getCustomer().email}
                                                                            </c:when>
                                                                            <c:otherwise>--</c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </td>

                                                                <!-- Phone -->
                                                                <td data-column="phone">
                                                                    <span class="text-muted">
                                                                        <c:choose>
                                                                            <c:when test="${not empty invoice.getCustomer().phone}">
                                                                                ${invoice.getCustomer().phone}
                                                                            </c:when>
                                                                            <c:otherwise>--</c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </td>

                                                                <!-- Birthday -->
                                                                <td data-column="birthday">
                                                                    <span class="text-muted">
                                                                        <c:choose>
                                                                            <c:when test="${not empty invoice.getCustomer().birthDate}">
                                                                                <fmt:formatDate value="${invoice.getCustomer().birthDate}" pattern="dd/MM/yyyy" />
                                                                            </c:when>
                                                                            <c:otherwise>--</c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </td>

                                                                <!-- Branch -->
                                                                <td data-column="branch"><span class="text-muted">Chi nhánh trung tâm</span></td>

                                                                <!-- Total Amount -->
                                                                <td data-column="total" class="text-end">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.subTotal != null}">
                                                                            <fmt:formatNumber value="${invoice.subTotal}" pattern="#,###" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">0</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Discount -->
                                                                <td data-column="discount" class="text-end">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.discountAmount != null}">
                                                                            <fmt:formatNumber value="${invoice.discountAmount}" pattern="#,###" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">0</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- VAT Amount -->
                                                                <td data-column="vat" class="text-end">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.VATAmount != null}">
                                                                            <fmt:formatNumber value="${invoice.VATAmount}" pattern="#,###" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">0</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Need Pay -->
                                                                <td data-column="need-pay" class="text-end">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.totalAmount != null}">
                                                                            <fmt:formatNumber value="${invoice.totalAmount}" pattern="#,###" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">0</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Paid Amount -->
                                                                <td data-column="paid" class="text-end">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.paidAmount != null}">
                                                                            <fmt:formatNumber value="${invoice.paidAmount}" pattern="#,###" />
                                                                        </c:when>
                                                                        <c:otherwise><span class="text-muted">0</span></c:otherwise>
                                                                    </c:choose>
                                                                </td>



                                                                <!-- Status -->
                                                                <td data-column="status">
                                                                    <c:choose>
                                                                        <c:when test="${invoice.status == 'Completed'}">
                                                                            <span class="badge badge-success">Hoàn thành</span>
                                                                        </c:when>
                                                                        <c:when test="${invoice.status == 'PROCESSING'}">
                                                                            <span class="badge badge-warning">Đang xử lý</span>
                                                                        </c:when>
                                                                        <c:when test="${invoice.status == 'CANCELLED'}">
                                                                            <span class="badge badge-danger">Không giao được</span>
                                                                        </c:when>
                                                                        <c:when test="${invoice.status == 'REFUNDED'}">
                                                                            <span class="badge badge-secondary">Đã hủy</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="badge badge-info">${invoice.status}</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>

                                                                <!-- Actions -->
                                                                <td data-column="actions">
                                                                    <div class="action-buttons">
                                                                        <button class="btn btn-action" title="Xem chi tiết" data-invoice-id="${invoice.invoiceId}">
                                                                            <i class="fas fa-eye"></i>
                                                                        </button>
                                                                        <c:if test="${sessionScope.canEditInvoice != false}">
                                                                            <button class="btn btn-action" title="Chỉnh sửa" data-invoice-id="${invoice.invoiceId}">
                                                                                <i class="fas fa-edit"></i>
                                                                            </button>
                                                                        </c:if>
                                                                        <c:if test="${sessionScope.canDeleteInvoice != false}">
                                                                            <button class="btn btn-action text-danger" title="Xóa" data-invoice-id="${invoice.invoiceId}">
                                                                                <i class="fas fa-trash"></i>
                                                                            </button>
                                                                        </c:if>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="18" class="text-center text-muted" style="padding: 40px;">
                                                                <i class="fas fa-inbox fa-2x mb-3 d-block"></i>
                                                                <p class="mb-2">Không có dữ liệu hóa đơn</p>
                                                                <small>Hãy tạo hóa đơn đầu tiên của bạn</small>
                                                            </td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <!-- Fixed Pagination -->
                                <div class="fixed-pagination">
                                    <div class="pagination-info">
                                        <span>Hiển thị</span>
                                        <select class="pagination-select">
                                            <option value="5" ${pageSize == 5 ? 'selected' : ''}>5 dòng</option>
                                            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10 dòng</option>
                                            <option value="30" ${pageSize == 30 ? 'selected' : ''}>30 dòng</option>
                                            <option value="50" ${pageSize == 50 ? 'selected' : ''}>50 dòng</option>
                                        </select>
                                        <span>trên tổng <strong>${totalItems != null ? totalItems : fn:length(listInvoice)}</strong> kết quả</span>
                                    </div>

                                    <div class="pagination-controls">
                                        <ul class="page-numbers">
                                            <!-- First Page -->
                                            <li class="${currentPage <= 1 ? 'disabled' : ''}">
                                                <a class="page-link" href="#" data-action="first" title="Trang đầu">
                                                    <i class="fas fa-angle-double-left"></i>
                                                </a>
                                            </li>
                                            <!-- Previous Page -->
                                            <li class="${currentPage <= 1 ? 'disabled' : ''}">
                                                <a class="page-link" href="#" data-action="prev" title="Trang trước">
                                                    <i class="fas fa-angle-left"></i>
                                                </a>
                                            </li>

                                            <!-- Page Numbers -->
                                            <c:choose>
                                                <c:when test="${totalPages != null && totalPages > 0}">
                                                    <c:set var="startPage" value="${currentPage - 2 > 1 ? currentPage - 2 : 1}" />
                                                    <c:set var="endPage" value="${startPage + 4 <= totalPages ? startPage + 4 : totalPages}" />
                                                    <c:if test="${endPage - startPage < 4}">
                                                        <c:set var="startPage" value="${endPage - 4 > 1 ? endPage - 4 : 1}" />
                                                    </c:if>

                                                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                                        <li class="${i == currentPage ? 'active' : ''}">
                                                            <a class="page-link" href="#" data-page="${i}">${i}</a>
                                                        </li>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Default single page -->
                                                    <li class="active">
                                                        <a class="page-link" href="#" data-page="1">1</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>

                                            <!-- Next Page -->
                                            <li class="${currentPage >= totalPages ? 'disabled' : ''}">
                                                <a class="page-link" href="#" data-action="next" title="Trang sau">
                                                    <i class="fas fa-angle-right"></i>
                                                </a>
                                            </li>
                                            <!-- Last Page -->
                                            <li class="${currentPage >= totalPages ? 'disabled' : ''}">
                                                <a class="page-link" href="#" data-action="last" title="Trang cuối">
                                                    <i class="fas fa-angle-double-right"></i>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Invoice Detail Modal -->
        <div class="modal fade" id="invoiceModal" tabindex="-1" aria-labelledby="invoiceModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="invoiceModalLabel">Chi tiết hóa đơn</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <!-- Modal Tabs -->
                        <ul class="nav nav-tabs" id="invoiceTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="info-tab" data-bs-toggle="tab" data-bs-target="#info-content" type="button" role="tab">
                                    Thông tin
                                </button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="payment-tab" data-bs-toggle="tab" data-bs-target="#payment-content" type="button" role="tab">
                                    Lịch sử thanh toán
                                </button>
                            </li>
                        </ul>

                        <div class="tab-content" id="invoiceTabsContent">
                            <!-- Info Tab -->
                            <div class="tab-pane fade show active" id="info-content" role="tabpanel">
                                <!-- Invoice Header -->
                                <div class="invoice-header">
                                    <div class="row align-items-center">
                                        <div class="col-md-8">
                                            <div class="customer-info">
                                                <div class="invoice-title-row">
                                                    <h4 class="customer-name">
                                                        <span class="name-text">Đang tải...</span>
                                                        <i class="fas fa-external-link-alt ms-2"></i>
                                                    </h4>
                                                    <span class="invoice-status badge">Đang tải...</span>
                                                </div>
                                                <div class="invoice-code">HD000000</div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 text-end">
                                            <span class="branch-info">Chi nhánh trung tâm</span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Invoice Info Grid -->
                                <div class="invoice-info-section">
                                    <div class="row">
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Người tạo:</label>
                                                <span class="info-value created-by">Đang tải...</span>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Người bán:</label>
                                                <select class="form-select form-select-sm sold-by-select">
                                                    <option>Đang tải...</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Ngày bán:</label>
                                                <div class="datetime-field">
                                                    <span class="info-value invoice-date">Đang tải...</span>
                                                    <i class="fas fa-calendar-alt ms-1"></i>
                                                    <i class="fas fa-clock ms-1"></i>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Cập nhật:</label>
                                                <div class="datetime-field">
                                                    <span class="info-value update-date">Đang tải...</span>
                                                    <i class="fas fa-calendar-alt ms-1"></i>
                                                    <i class="fas fa-clock ms-1"></i>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Kênh bán:</label>
                                                <select class="form-select form-select-sm">
                                                    <option>Bán trực tiếp</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6 col-lg-3">
                                            <div class="info-field">
                                                <label>Bảng giá:</label>
                                                <span class="info-value">Bảng giá chung</span>
                                            </div>
                                        </div>
                                    </div>  
                                </div>

                                <!-- Products Table -->
                                <div class="products-section">
                                    <div class="table-responsive">
                                        <table class="table invoice-products-table">
                                            <thead>
                                                <tr>
                                                    <th>Mã hàng</th>
                                                    <th>Tên hàng</th>
                                                    <th width="100" class="text-center">Số lượng</th>
                                                    <th width="120" class="text-end">Đơn giá</th>
                                                    <th width="100" class="text-end">Giảm giá</th>
                                                    <th width="120" class="text-end">Giá bán</th>
                                                    <th width="140" class="text-end">Thành tiền</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td colspan="7" class="text-center text-muted py-4">Đang tải...</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <!-- Summary Section -->
                                <div class="invoice-summary-section">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="notes-section">
                                                <label>Ghi chú...</label>
                                                <textarea class="form-control" rows="4" placeholder="Ghi chú về hóa đơn..."></textarea>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="summary-table">
                                                <div class="summary-row">
                                                    <span class="summary-label">Tổng tiền hàng (<span class="total-items">0</span>):</span>
                                                    <span class="summary-value total-amount">0</span>
                                                </div>
                                                <div class="summary-row">
                                                    <span class="summary-label">Giảm giá hóa đơn:</span>
                                                    <span class="summary-value discount-amount">0</span>
                                                </div>
                                                <div class="summary-row">
                                                    <span class="summary-label">Thuế :</span>
                                                    <span class="summary-value VAT-amount">0</span>
                                                </div>
                                                <div class="summary-row">
                                                    <span class="summary-label">Khách cần trả:</span>
                                                    <span class="summary-value need-to-pay">0</span>
                                                </div>
                                                <div class="summary-row total-row">
                                                    <span class="summary-label">Khách đã trả:</span>
                                                    <span class="summary-value paid-amount">0</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Payment History Tab -->
                            <div class="tab-pane fade" id="payment-content" role="tabpanel">
                                <div class="table-responsive">
                                    <table class="table payment-history-table">
                                        <thead>
                                            <tr>
                                                <th>Mã phiếu</th>
                                                <th>Thời gian</th>
                                                <th>Người tạo</th>
                                                <th width="140" class="text-end">Giá trị phiếu</th>
                                                <th>Phương thức</th>
                                                <th>Trạng thái</th>
                                                <th width="140" class="text-end">Tiền thu/chi</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td colspan="7" class="text-center text-muted py-4">Đang tải...</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-1"></i>Hủy
                        </button>
                        <button type="button" class="btn btn-outline-primary">
                            <i class="fas fa-copy me-1"></i>Sao chép
                        </button>
                        <button type="button" class="btn btn-outline-info btn-export">
                            <i class="fas fa-file-export me-1"></i>Xuất file
                        </button>
                        <button type="button" class="btn btn-primary">
                            <i class="fas fa-edit me-1"></i>Chỉnh sửa
                        </button>
                        <button type="button" class="btn btn-outline-secondary btn-save">
                            <i class="fas fa-save me-1"></i>Lưu
                        </button>
                        <button type="button" class="btn btn-outline-dark">
                            <i class="fas fa-print me-1"></i>In
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/feather.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>

        <!-- Main theme script -->
        <script src="<%=path%>/view/assets/js/script.js"></script>

        <!-- Invoice Management JavaScript -->
        <script src="<%=path%>/view/assets/js/employees/order/invoice-management.js"></script>

        <!-- Time Filter JavaScript -->
        <script src="<%=path%>/view/assets/js/employees/order/invoice_filter.js"></script>


    </body>
</html>