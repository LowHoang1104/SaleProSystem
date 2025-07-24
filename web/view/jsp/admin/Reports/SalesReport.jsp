<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" isErrorPage="true" buffer="16kb" autoFlush="true" %>
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
    <title>Báo cáo bán hàng - SalePro System</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
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
                                <li class="breadcrumb-item active">Báo cáo bán hàng</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- Store Selection Section -->
                <div class="row mb-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title">Chọn chi nhánh</h5>
                            </div>
                            <div class="card-body">
                                <form id="storeForm" method="GET" action="<%=path%>/SalesReportServlet">
                                    <input type="hidden" name="action" value="changeStore">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label for="storeId">Chi nhánh:</label>
                                            <select id="storeId" name="storeId" class="form-control" onchange="this.form.submit()">
                                                <c:forEach items="${stores}" var="store">
                                                    <option value="${store.storeID}" ${selectedStoreId == store.storeID ? 'selected' : ''}>
                                                        ${store.storeName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <label>&nbsp;</label><br>
                                            <button type="button" class="btn btn-success" onclick="exportExcel()">
                                                <i class="fas fa-file-excel"></i> Export Excel
                                            </button>
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
                                <h4><fmt:formatNumber value="${totalRevenue}" pattern="###,###,###" /> VNĐ</h4>
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
                                <h4><fmt:formatNumber value="${totalOrders}" type="number"/></h4>
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
                                <h4><fmt:formatNumber value="${averageOrderValue}" pattern="###,###" /> VNĐ</h4>
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
                                <h4><fmt:formatNumber value="${totalCustomers}" type="number"/></h4>
                                <h5>Số khách hàng</h5>
                            </div>
                            <div class="dash-imgs">
                                <i data-feather="users"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Charts Section -->
                <div class="row">
                    <div class="col-lg-8 col-sm-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Doanh thu 7 ngày gần nhất</h4>
                                <small class="text-muted">Doanh thu của chi nhánh trong 7 ngày qua</small>
                            </div>
                            <div class="card-body">
                                <div id="daily-revenue-chart"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Phương thức thanh toán</h4>
                                <small class="text-muted">Tổng doanh thu theo hình thức thanh toán</small>
                            </div>
                            <div class="card-body">
                                <div id="payment-method-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Hourly Chart and Recent Invoices -->
                <div class="row">
                    <div class="col-lg-6 col-sm-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Doanh thu theo giờ hôm nay</h4>
                                <small class="text-muted">Doanh thu theo từng giờ trong ngày hôm nay</small>
                            </div>
                            <div class="card-body">
                                <div id="hourly-chart"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-sm-12">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Hóa đơn gần đây</h4>
                                <small class="text-muted">10 hóa đơn mới nhất của chi nhánh</small>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Mã HĐ</th>
                                                <th>Ngày</th>
                                                <th>Tổng tiền</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>
                                                <c:when test="${not empty invoices}">
                                                    <c:forEach items="${invoices}" var="invoice" varStatus="status" begin="0" end="19">
                                                        <tr>
                                                            <td>${invoice.invoiceCode}</td>
                                                            <td>
                                                                <fmt:formatDate value="${invoice.invoiceDate}" pattern="dd/MM/yyyy HH:mm"/>
                                                            </td>
                                                            <td class="text-success">
                                                                <fmt:formatNumber value="${invoice.totalAmount}" pattern="###,###,###" /> VNĐ
                                                            </td>
                                                            <td>
                                                                <span class="badge ${invoice.status == 'Completed' ? 'bg-success' : 'bg-warning'}">
                                                                    ${invoice.status == 'Completed' ? 'Hoàn thành' : invoice.status}
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr>
                                                        <td colspan="4" class="text-center text-muted">
                                                            Chưa có hóa đơn nào cho chi nhánh này
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
                </div>

                <!-- Additional Info Section -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row text-center">
                                    <div class="col-md-3">
                                        <h6 class="text-muted">Biểu đồ 7 ngày</h6>
                                        <p class="mb-0"><small>Hiển thị doanh thu 7 ngày gần nhất của chi nhánh được chọn</small></p>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="text-muted">Phương thức thanh toán</h6>
                                        <p class="mb-0"><small>Tổng doanh thu theo hình thức thanh toán từ tất cả hóa đơn</small></p>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="text-muted">Doanh thu theo giờ</h6>
                                        <p class="mb-0"><small>Doanh thu theo từng giờ trong ngày hôm nay (8h-22h)</small></p>
                                    </div>
                                    <div class="col-md-3">
                                        <h6 class="text-muted">Hóa đơn gần đây</h6>
                                        <p class="mb-0"><small>10 hóa đơn mới nhất được sắp xếp theo thời gian</small></p>
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
        window.dailyRevenueData = ${dailyRevenueData != null ? dailyRevenueData : '{}'};
        window.paymentMethodData = ${paymentMethodData != null ? paymentMethodData : '{}'};
        window.hourlyData = ${hourlyData != null ? hourlyData : '{}'};
        window.contextPath = '<%=path%>';
        window.selectedStoreId = '${selectedStoreId}';
    </script>
    
    <!-- Sales Report Script -->
    <script src="<%=path%>/view/assets/js/reports/sales-report.js"></script>
</body>
</html>