<!DOCTYPE html>

<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, management, minimal, modern,  html5, responsive">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Báo cáo đặt hàng - SalePro System</title>

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/apexchart/apexcharts.min.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>
        <div class="main-wrapper">
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %> 
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="row">
                            <div class="col-sm-12">
                                <ul class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="<%=path%>/HomepageController">Tổng quan</a></li>
                                    <li class="breadcrumb-item"><i class="feather-chevron-right"></i></li>
                                    <li class="breadcrumb-item active">Báo cáo đặt hàng</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê tổng quan -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4>1,245</h4>
                                    <h5>Tổng đơn hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-cart"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>1,156</h4>
                                    <h5>Đã giao</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="check-circle"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>67</h4>
                                    <h5>Đang xử lý</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="clock"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>22</h4>
                                    <h5>Đã hủy</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="x-circle"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ đặt hàng -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê đơn hàng theo tháng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="order-trend-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Trạng thái đơn hàng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="order-status-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Danh sách đơn hàng -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Danh sách đơn hàng gần đây</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="orderTable">
                                    <thead>
                                        <tr>
                                            <th>Mã đơn hàng</th>
                                            <th>Khách hàng</th>
                                            <th>Sản phẩm</th>
                                            <th>Tổng tiền</th>
                                            <th>Ngày đặt</th>
                                            <th>Ngày giao</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>#ORD001</td>
                                            <td>Nguyễn Văn A</td>
                                            <td>iPhone 15 Pro x1</td>
                                            <td>25,000,000 VNĐ</td>
                                            <td>15/01/2024</td>
                                            <td>17/01/2024</td>
                                            <td><span class="badge bg-success">Đã giao</span></td>
                                            <td><button class="btn btn-info btn-sm">Chi tiết</button></td>
                                        </tr>
                                        <tr>
                                            <td>#ORD002</td>
                                            <td>Trần Thị B</td>
                                            <td>Samsung Galaxy S24 x2</td>
                                            <td>36,000,000 VNĐ</td>
                                            <td>16/01/2024</td>
                                            <td>18/01/2024</td>
                                            <td><span class="badge bg-warning">Đang giao</span></td>
                                            <td><button class="btn btn-info btn-sm">Chi tiết</button></td>
                                        </tr>
                                        <tr>
                                            <td>#ORD003</td>
                                            <td>Lê Văn C</td>
                                            <td>MacBook Air M2 x1</td>
                                            <td>30,000,000 VNĐ</td>
                                            <td>17/01/2024</td>
                                            <td>-</td>
                                            <td><span class="badge bg-info">Đang xử lý</span></td>
                                            <td><button class="btn btn-info btn-sm">Chi tiết</button></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê chi tiết -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê theo phương thức thanh toán</h4>
                                </div>
                                <div class="card-body">
                                    <div id="payment-method-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê theo khu vực</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Khu vực</th>
                                                    <th>Số đơn hàng</th>
                                                    <th>Doanh thu</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Hà Nội</td>
                                                    <td>456</td>
                                                    <td>456,000,000</td>
                                                    <td>36.6%</td>
                                                </tr>
                                                <tr>
                                                    <td>TP. HCM</td>
                                                    <td>389</td>
                                                    <td>389,000,000</td>
                                                    <td>31.2%</td>
                                                </tr>
                                                <tr>
                                                    <td>Đà Nẵng</td>
                                                    <td>234</td>
                                                    <td>234,000,000</td>
                                                    <td>18.8%</td>
                                                </tr>
                                                <tr>
                                                    <td>Khác</td>
                                                    <td>166</td>
                                                    <td>166,000,000</td>
                                                    <td>13.4%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/feather.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/apexchart/apexcharts.min.js"></script>
        <script src="<%=path%>/view/assets/js/script.js"></script>

        <script>
            // Biểu đồ xu hướng đơn hàng
            var trendOptions = {
                series: [{
                    name: 'Đơn hàng',
                    data: [45, 52, 38, 24, 33, 26, 21, 20, 6, 8, 15, 10]
                }],
                chart: {
                    height: 350,
                    type: 'line',
                    toolbar: {
                        show: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'smooth',
                    width: 3
                },
                colors: ['#7367F0'],
                xaxis: {
                    categories: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12']
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val + " đơn hàng"
                        }
                    }
                },
            };

            var trendChart = new ApexCharts(document.querySelector("#order-trend-chart"), trendOptions);
            trendChart.render();

            // Biểu đồ trạng thái đơn hàng
            var statusOptions = {
                series: [1156, 67, 22],
                chart: {
                    width: 380,
                    type: 'donut',
                },
                labels: ['Đã giao', 'Đang xử lý', 'Đã hủy'],
                colors: ['#28C76F', '#FF9F43', '#EA5455'],
                responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
            };

            var statusChart = new ApexCharts(document.querySelector("#order-status-chart"), statusOptions);
            statusChart.render();

            // Biểu đồ phương thức thanh toán
            var paymentOptions = {
                series: [65, 25, 10],
                chart: {
                    width: 380,
                    type: 'pie',
                },
                labels: ['Tiền mặt', 'Chuyển khoản', 'Thẻ tín dụng'],
                colors: ['#7367F0', '#28C76F', '#FF9F43'],
                responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
            };

            var paymentChart = new ApexCharts(document.querySelector("#payment-method-chart"), paymentOptions);
            paymentChart.render();

            // Khởi tạo DataTable
            $(document).ready(function() {
                $('#orderTable').DataTable({
                    "pageLength": 10,
                    "order": [[4, "desc"]]
                });
            });
        </script>
    </body>
</html> 