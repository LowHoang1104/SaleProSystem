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
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <title>Báo cáo khách hàng - SalePro System</title>
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
                                    <li class="breadcrumb-item active">Báo cáo khách hàng</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Statistics Cards -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${totalCustomers}" type="number"/></h4>
                                    <h5>Tổng khách hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="users"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${newCustomersThisMonth}" type="number"/></h4>
                                    <h5>KH mới tháng này</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user-plus"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${totalRevenue}" pattern="###,###" /> VND</h4>
                                    <h5>Tổng doanh thu</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="trending-up"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${averageOrderValue}" pattern="###,###" /> VND</h4>
                                    <h5>Giá trị TB/đơn</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-cart"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Main Charts Section -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Xu hướng khách hàng mới</h4>
                                    <div class="card-toolbar">
                                        <button class="btn btn-sm btn-outline-primary" onclick="refreshChartData()">
                                            <i data-feather="refresh-cw"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div id="monthly-registration-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố theo hạng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="customer-rank-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Secondary Charts -->
                    <div class="row">
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố giới tính</h4>
                                </div>
                                <div class="card-body">
                                    <div id="gender-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố độ tuổi</h4>
                                </div>
                                <div class="card-body">
                                    <div id="age-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Chỉ số chính</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row text-center">
                                        <div class="col-6 mb-3">
                                            <h3 class="text-success"><fmt:formatNumber value="${averageOrderValue}" pattern="###,### "/> VND</h3>
                                            <p class="mb-0">AOV trung bình</p>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <h3 class="text-info">
                                                <c:choose>
                                                    <c:when test="${totalCustomers > 0}">
                                                        <fmt:formatNumber value="${(vipCustomers / totalCustomers) * 100}" pattern="##.#"/>%
                                                    </c:when>
                                                    <c:otherwise>0%</c:otherwise>
                                                </c:choose>
                                            </h3>
                                            <p class="mb-0">Tỷ lệ VIP</p>
                                        </div>
                                        <div class="col-6">
                                            <h3 class="text-warning">
                                                <c:choose>
                                                    <c:when test="${totalCustomers > 0}">
                                                        <fmt:formatNumber value="${newCustomersThisMonth / totalCustomers * 100}" pattern="##.#"/>%
                                                    </c:when>
                                                    <c:otherwise>0%</c:otherwise>
                                                </c:choose>
                                            </h3>
                                            <p class="mb-0">Tăng trưởng tháng</p>
                                        </div>
                                        <div class="col-6">
                                            <h3 class="text-primary">
                                                <fmt:formatNumber value="${totalRevenue / (totalCustomers > 0 ? totalCustomers : 1)}" pattern="###,###"/> VND
                                            </h3>
                                            <p class="mb-0">Doanh thu/KH</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Top Customers Section -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Top 10 khách hàng VIP</h4>
                                    <div class="card-toolbar">
                                        <button class="btn btn-sm btn-primary" onclick="exportTopCustomers()">
                                            <i data-feather="download"></i> Export
                                        </button>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Khách hàng</th>
                                                    <th>Liên hệ</th>
                                                    <th>Tổng mua</th>
                                                    <th>Hạng</th>
                                                    <th>Lần mua gần nhất</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${topCustomers}" var="customer" varStatus="status" begin="0" end="9">
                                                    <tr>
                                                        <td>
                                                            <span class="badge-${status.index < 3 ? 'warning' : 'light'}">
                                                                ${status.index + 1}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <div class="d-flex align-items-center">
                                                                <div>
                                                                    <h6 class="mb-0">${customer.fullName}</h6>
                                                                    <small class="text-muted">${customer.customerCode}</small>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="mb-1">${customer.phone}</div>
                                                            <small class="text-muted">${customer.email}</small>
                                                        </td>
                                                        <td>
                                                            <h6 class="text-success mb-0">
                                                                <fmt:formatNumber value="${customer.totalSpent}" pattern="###,###,###" /> VNĐ
                                                            </h6>
                                                            <small class="text-muted">
                                                                <fmt:formatNumber value="${customer.points}" type="number"/> điểm
                                                            </small>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${customer.rank == 'VIP'}">
                                                                    <span>VIP</span>
                                                                </c:when>
                                                                <c:when test="${customer.rank == 'Gold'}">
                                                                    <span>Gold</span>
                                                                </c:when>
                                                                <c:when test="${customer.rank == 'Silver'}">
                                                                    <span>Silver</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span>Bronze</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <fmt:formatDate value="${customer.createdAt}" pattern="dd/MM/yyyy"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
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
                                                    <h6 class="mb-0">Khách hàng VIP</h6>
                                                    <small class="text-muted">Hạng cao nhất</small>
                                                </div>
                                                <h4 class="text-danger mb-0">
                                                    <fmt:formatNumber value="${vipCustomers}" type="number"/>
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Khách hàng Gold</h6>
                                                    <small class="text-muted">Tiềm năng VIP</small>
                                                </div>
                                                <h4 class="text-warning mb-0">
                                                    <fmt:formatNumber value="${goldCustomers}" type="number"/>
                                                </h4>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-3">
                                            <div class="d-flex justify-content-between align-items-center p-3 bg-light rounded">
                                                <div>
                                                    <h6 class="mb-0">Khách hàng mới</h6>
                                                    <small class="text-muted">7 ngày qua</small>
                                                </div>
                                                <h4 class="text-info mb-0">
                                                    <fmt:formatNumber value="${newCustomersWeek}" type="number"/>
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
                                    <h4 class="card-title">Xuất báo cáo</h4>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <p class="mb-0">Xuất báo cáo chi tiết để phân tích sâu hơn hoặc chia sẻ với đồng nghiệp.</p>
                                        </div>
                                        <div class="col-md-4 text-md-right">
                                            <div class="btn-group">
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
            window.monthlyDataJson = '${monthlyDataArray}';
            window.rankDataJson = '${rankDataArray}';
            window.genderDataJson = '${genderDataArray}';
            window.ageDataJson = '${ageDataArray}';
        </script>
        
        <!-- Customer Report Script -->
        <script src="<%=path%>/view/assets/js/reports/customer-report.js"></script>
    </body>
</html>