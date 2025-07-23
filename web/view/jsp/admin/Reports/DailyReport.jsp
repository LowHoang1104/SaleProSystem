<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Báo cáo cuối ngày - SalePro System</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <!-- Third-party CSS -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">
        <!-- FontAwesome -->
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <!-- Main theme styles -->
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <!-- ApexCharts -->
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/apexchart/apexcharts.min.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <div class="main-wrapper">
            <%@include file="/view/jsp/admin/HeadSideBar/header.jsp" %>
            <%@include file="/view/jsp/admin/HeadSideBar/sidebar.jsp" %>

            <div class="page-wrapper">
                <div class="content">
                    <!-- Page Header -->
                    <div class="page-header">
                        <div class="row">
                            <div class="col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="<%=path%>/dashboard">Tổng quan</a></li>
                                    <li class="breadcrumb-item"><i class="feather-chevron-right"></i></li>
                                    <li class="breadcrumb-item active">Báo cáo cuối ngày</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Filter Section -->
                    <div class="row mb-4">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Chọn thông tin báo cáo</h5>
                                </div>
                                <div class="card-body">
                                    <form id="reportForm" method="GET" action="<%=path%>/DailyReportServlet">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label for="reportDate">Ngày báo cáo:</label>
                                                <input type="date" id="reportDate" name="reportDate" class="form-control" 
                                                       value="${selectedDate}" max="${java.time.LocalDate.now()}">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="storeId">Chi nhánh:</label>
                                                <select id="storeId" name="storeId" class="form-control">
                                                    <c:forEach items="${stores}" var="store">
                                                        <option value="${store.storeID}" ${selectedStoreId == store.storeID ? 'selected' : ''}>
                                                            ${store.storeName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label>&nbsp;</label><br>
                                                <button type="button" class="btn btn-primary" id="generateReportBtn">
                                                    <i data-feather="refresh-cw"></i> Tạo báo cáo
                                                </button>
                                            </div>
                                            <div class="col-md-3">
                                                <label>&nbsp;</label><br>
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-success" id="exportExcelBtn">
                                                        <i data-feather="download"></i> Excel
                                                    </button>
                                                    <button type="button" class="btn btn-info" id="printBtn">
                                                        <i data-feather="printer"></i> In
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Statistics Cards -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${reportData.totalRevenue}" pattern="###,###,###" /> VNĐ</h4>
                                    <h5>Tổng doanh thu</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="trending-up"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${reportData.totalOrders}" type="number"/></h4>
                                    <h5>Tổng đơn hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-cart"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${reportData.averageOrderValue}" pattern="###,###" /> VNĐ</h4>
                                    <h5>Giá trị TB/đơn</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="bar-chart-2"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${reportData.customerCount}" type="number"/></h4>
                                    <h5>Số khách hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="users"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Main Charts Section -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Doanh thu theo giờ</h4>
                                    <div class="card-toolbar">
                                        <button class="btn btn-sm btn-outline-primary" onclick="refreshChartData()">
                                            <i data-feather="refresh-cw"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div id="hourlyRevenueChart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phương thức thanh toán</h4>
                                </div>
                                <div class="card-body">
                                    <div id="paymentMethodChart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Employee Performance & Cash Flow -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Hiệu suất nhân viên</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Nhân viên</th>
                                                    <th class="text-end">Doanh số</th>
                                                    <th class="text-center">Đơn hàng</th>
                                                    <th class="text-end">TB/đơn</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty reportData.employeePerformance}">
                                                        <c:forEach items="${reportData.employeePerformance}" var="emp">
                                                            <tr>
                                                                <td>
                                                                    <div>
                                                                        <h6 class="mb-0">${emp.key}</h6>
                                                                    </div>
                                                                </td>
                                                                <td class="text-end">
                                                                    <h6 class="text-success mb-0">
                                                                        <fmt:formatNumber value="${emp.value.revenue}" pattern="###,###,###" /> VNĐ
                                                                    </h6>
                                                                </td>
                                                                <td class="text-center">${emp.value.orderCount}</td>
                                                                <td class="text-end">
                                                                    <fmt:formatNumber value="${emp.value.averageOrderValue}" pattern="###,###" /> VNĐ
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="4" class="text-center text-muted">
                                                                Chưa có dữ liệu nhân viên bán hàng
                                                            </td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Luồng tiền mặt</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row text-center">
                                        <div class="col-4 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Tiền vào</h6>
                                                    <small class="text-muted">Thu nhập</small>
                                                </div>
                                                <h4 class="text-success mb-0">
                                                    <fmt:formatNumber value="${reportData.cashInflow}" pattern="###,###,###" />
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-4 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Tiền ra</h6>
                                                    <small class="text-muted">Chi tiêu</small>
                                                </div>
                                                <h4 class="text-danger mb-0">
                                                    <fmt:formatNumber value="${reportData.cashOutflow}" pattern="###,###,###" />
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-4 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Ròng</h6>
                                                    <small class="text-muted">Chênh lệch</small>
                                                </div>
                                                <h4 class="text-primary mb-0">
                                                    <fmt:formatNumber value="${reportData.netCashFlow}" pattern="###,###,###" />
                                                </h4>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mt-4">
                                        <h6 class="mb-3">Chi tiết theo phương thức thanh toán:</h6>
                                        <c:forEach items="${reportData.paymentMethodBreakdown}" var="payment">
                                            <div class="d-flex justify-content-between align-items-center mb-2">
                                                <span class="text-muted">${payment.key}:</span>
                                                <strong>
                                                    <fmt:formatNumber value="${payment.value}" pattern="###,###,###" /> VNĐ
                                                </strong>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Inventory Status and Summary -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Tình trạng tồn kho</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h6 class="mb-3">Sản phẩm đã bán hôm nay:</h6>
                                            <div class="row text-center">
                                                <div class="col-6">
                                                    <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                        <div>
                                                            <h6 class="mb-0">Loại SP</h6>
                                                            <small class="text-muted">Đã bán</small>
                                                        </div>
                                                        <h4 class="text-primary mb-0">${reportData.totalProductsSold}</h4>
                                                    </div>
                                                </div>
                                                <div class="col-6">
                                                    <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                        <div>
                                                            <h6 class="mb-0">Số lượng</h6>
                                                            <small class="text-muted">Tổng cộng</small>
                                                        </div>
                                                        <h4 class="text-info mb-0">${reportData.totalQuantitySold}</h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <h6 class="mb-3">Sản phẩm số lượng thấp</h6>
                                            <c:choose>
                                                <c:when test="${not empty reportData.lowStockProducts}">
                                                    <div class="table-responsive" style="max-height: 200px;">
                                                        <table class="table table-sm">
                                                            <thead>
                                                                <tr>
                                                                    <th>Sản phẩm</th>
                                                                    <th class="text-end">Tồn kho</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${reportData.lowStockProducts}" var="product" begin="0" end="4">
                                                                    <tr>
                                                                        <td>
                                                                            <div>
                                                                                <h6 class="mb-0">${product.productName}</h6>
                                                                                <small class="text-muted">${product.productCode}</small>
                                                                            </div>
                                                                        </td>
                                                                        <td class="text-end">
                                                                            <span class="badge bg-warning">${product.quantity}</span>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center text-muted py-3">
                                                        <i class="fas fa-check-circle fa-2x mb-2 text-success"></i>
                                                        <p class="mb-0">Tất cả sản phẩm đều đủ hàng</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê nhanh</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Tăng trưởng doanh thu</h6>
                                                    <small class="text-muted">So với hôm qua</small>
                                                </div>
                                                <h4 class="mb-0 ${reportData.revenueGrowth >= 0 ? 'text-success' : 'text-danger'}">
                                                    <c:choose>
                                                        <c:when test="${reportData.revenueGrowth != null}">
                                                            <fmt:formatNumber value="${reportData.revenueGrowth}" pattern="##.#"/>%
                                                        </c:when>
                                                        <c:otherwise>0%</c:otherwise>
                                                    </c:choose>
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Tăng trưởng đơn hàng</h6>
                                                    <small class="text-muted">So với ngày trước</small>
                                                </div>
                                                <h4 class="mb-0 ${reportData.orderGrowth >= 0 ? 'text-success' : 'text-danger'}">
                                                    <c:choose>
                                                        <c:when test="${reportData.orderGrowth != null}">
                                                            <c:choose>
                                                                <c:when test="${reportData.orderGrowth == 999}">
                                                                    <span class="text-success">Mới</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <fmt:formatNumber value="${reportData.orderGrowth}" pattern="##.#"/>%
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                        <c:otherwise>0%</c:otherwise>
                                                    </c:choose>
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Doanh thu/KH</h6>
                                                    <small class="text-muted">Trung bình</small>
                                                </div>
                                                <h4 class="text-primary mb-0">
                                                    <fmt:formatNumber value="${reportData.totalRevenue / (reportData.customerCount > 0 ? reportData.customerCount : 1)}" pattern="###,###"/> VNĐ
                                                </h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Export Section -->
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Tóm tắt báo cáo cuối ngày</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <h6 class="mb-3">
                                                Báo cáo ngày ${formattedDate} - 
                                                <c:forEach items="${stores}" var="store">
                                                    <c:if test="${store.storeID == selectedStoreId}">
                                                        ${store.storeName}
                                                    </c:if>
                                                </c:forEach>
                                            </h6>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <ul class="list-unstyled">
                                                        <li><strong>Tổng doanh thu:</strong> 
                                                            <span class="text-success">
                                                                <fmt:formatNumber value="${reportData.totalRevenue}" pattern="###,###,###" /> VNĐ
                                                            </span>
                                                        </li>
                                                        <li><strong>Tổng đơn hàng:</strong> ${reportData.totalOrders}</li>
                                                        <li><strong>Khách hàng phục vụ:</strong> ${reportData.customerCount}</li>
                                                    </ul>
                                                </div>
                                                <div class="col-md-6">
                                                    <ul class="list-unstyled">
                                                        <li><strong>Giá trị TB/đơn:</strong> 
                                                            <fmt:formatNumber value="${reportData.averageOrderValue}" pattern="###,###" /> VNĐ
                                                        </li>
                                                        <li><strong>Sản phẩm đã bán:</strong> ${reportData.totalProductsSold} loại</li>
                                                        <li><strong>Thời gian tạo:</strong> 
                                                            <fmt:formatDate value="${java.util.Date()}" pattern="dd/MM/yyyy HH:mm" />
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4 text-md-right">
                                            <p class="mb-3">Xuất báo cáo để lưu trữ hoặc chia sẻ với đồng nghiệp.</p>
                                            <div class="btn-group">
                                                <button class="btn btn-success" onclick="exportReport('excel')">
                                                    <i data-feather="download"></i> Excel
                                                </button>
                                                <button class="btn btn-info" id="printReport">
                                                    <i data-feather="printer"></i> In
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/feather.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <!-- ApexCharts -->
        <script src="<%=path%>/view/assets/plugins/apexchart/apexcharts.min.js"></script>
        <!-- Main Script -->
        <script src="<%=path%>/view/assets/js/script.js"></script>

        <!-- Chart Data from Servlet -->
        <script>
                                                    // Pass chart data from servlet to JavaScript
                                                    window.hourlyRevenueData = ${hourlyRevenueData != null ? hourlyRevenueData : '{}'};
                                                    window.paymentMethodData = ${paymentMethodData != null ? paymentMethodData : '{}'};
                                                    window.contextPath = '<%=path%>';
        </script>

        <!-- Daily Report Script -->
        <script src="<%=path%>/view/assets/js/reports/daily-report.js"></script>
    </body>
</html>