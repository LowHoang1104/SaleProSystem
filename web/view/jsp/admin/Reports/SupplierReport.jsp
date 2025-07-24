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
        <title>Báo cáo nhà cung cấp - SalePro System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo nhà cung cấp</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê tổng quan -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4>45</h4>
                                    <h5>Tổng nhà cung cấp</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="truck"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>38</h4>
                                    <h5>Đang hoạt động</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="check-circle"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>1,245</h4>
                                    <h5>Đơn hàng nhập</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-bag"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>2,456,800,000</h4>
                                    <h5>Tổng giá trị nhập</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="dollar-sign"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ nhà cung cấp -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Giá trị nhập hàng theo tháng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="supplier-purchase-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Top nhà cung cấp</h4>
                                </div>
                                <div class="card-body">
                                    <div id="top-suppliers-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Danh sách nhà cung cấp -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Danh sách nhà cung cấp</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="supplierTable">
                                    <thead>
                                        <tr>
                                            <th>Nhà cung cấp</th>
                                            <th>Liên hệ</th>
                                            <th>Sản phẩm</th>
                                            <th>Đơn hàng</th>
                                            <th>Giá trị nhập</th>
                                            <th>Đánh giá</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/brand/apple.png" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Apple Vietnam</h6>
                                                        <small class="text-muted">SUP001</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div>
                                                    <p class="mb-0">0901234567</p>
                                                    <small class="text-muted">apple@vn.com</small>
                                                </div>
                                            </td>
                                            <td>iPhone, iPad, MacBook</td>
                                            <td>156</td>
                                            <td>856,400,000 VNĐ</td>
                                            <td><span class="badge bg-success">5.0</span></td>
                                            <td><span class="badge bg-success">Hoạt động</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/brand/samsung.png" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Samsung Vietnam</h6>
                                                        <small class="text-muted">SUP002</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div>
                                                    <p class="mb-0">0901234568</p>
                                                    <small class="text-muted">samsung@vn.com</small>
                                                </div>
                                            </td>
                                            <td>Galaxy, Tab, Laptop</td>
                                            <td>142</td>
                                            <td>645,200,000 VNĐ</td>
                                            <td><span class="badge bg-success">4.8</span></td>
                                            <td><span class="badge bg-success">Hoạt động</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/brand/xiaomi.png" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Xiaomi Vietnam</h6>
                                                        <small class="text-muted">SUP003</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div>
                                                    <p class="mb-0">0901234569</p>
                                                    <small class="text-muted">xiaomi@vn.com</small>
                                                </div>
                                            </td>
                                            <td>Redmi, Mi, POCO</td>
                                            <td>89</td>
                                            <td>234,600,000 VNĐ</td>
                                            <td><span class="badge bg-warning">4.2</span></td>
                                            <td><span class="badge bg-success">Hoạt động</span></td>
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
                                    <h4 class="card-title">Thống kê theo loại sản phẩm</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Loại sản phẩm</th>
                                                    <th>Số nhà cung cấp</th>
                                                    <th>Giá trị nhập</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Điện thoại</td>
                                                    <td>15</td>
                                                    <td>1,200,000,000</td>
                                                    <td>48.9%</td>
                                                </tr>
                                                <tr>
                                                    <td>Laptop</td>
                                                    <td>12</td>
                                                    <td>800,000,000</td>
                                                    <td>32.6%</td>
                                                </tr>
                                                <tr>
                                                    <td>Máy tính bảng</td>
                                                    <td>8</td>
                                                    <td>300,000,000</td>
                                                    <td>12.2%</td>
                                                </tr>
                                                <tr>
                                                    <td>Phụ kiện</td>
                                                    <td>10</td>
                                                    <td>156,800,000</td>
                                                    <td>6.3%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Đánh giá nhà cung cấp</h4>
                                </div>
                                <div class="card-body">
                                    <div id="supplier-rating-chart"></div>
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
            // Biểu đồ giá trị nhập hàng
            var purchaseOptions = {
                series: [{
                    name: 'Giá trị nhập',
                    data: [180000000, 220000000, 195000000, 250000000, 280000000, 320000000, 290000000, 350000000, 380000000, 420000000, 450000000, 480000000]
                }],
                chart: {
                    height: 350,
                    type: 'area',
                    toolbar: {
                        show: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'smooth'
                },
                colors: ['#7367F0'],
                xaxis: {
                    categories: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12']
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val.toLocaleString() + " VNĐ"
                        }
                    }
                },
            };

            var purchaseChart = new ApexCharts(document.querySelector("#supplier-purchase-chart"), purchaseOptions);
            purchaseChart.render();

            // Biểu đồ top nhà cung cấp
            var suppliersOptions = {
                series: [45, 35, 20],
                chart: {
                    width: 380,
                    type: 'donut',
                },
                labels: ['Apple Vietnam', 'Samsung Vietnam', 'Xiaomi Vietnam'],
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

            var suppliersChart = new ApexCharts(document.querySelector("#top-suppliers-chart"), suppliersOptions);
            suppliersChart.render();

            // Biểu đồ đánh giá nhà cung cấp
            var ratingOptions = {
                series: [15, 12, 8, 5, 5],
                chart: {
                    width: 380,
                    type: 'bar',
                    toolbar: {
                        show: false
                    }
                },
                colors: ['#7367F0'],
                plotOptions: {
                    bar: {
                        horizontal: true,
                        barHeight: '70%',
                        distributed: true
                    }
                },
                dataLabels: {
                    enabled: false
                },
                xaxis: {
                    categories: ['5 sao', '4 sao', '3 sao', '2 sao', '1 sao'],
                },
                yaxis: {
                    title: {
                        text: 'Số lượng nhà cung cấp'
                    }
                }
            };

            var ratingChart = new ApexCharts(document.querySelector("#supplier-rating-chart"), ratingOptions);
            ratingChart.render();

            // Khởi tạo DataTable
            $(document).ready(function() {
                $('#supplierTable').DataTable({
                    "pageLength": 10,
                    "order": [[4, "desc"]]
                });
            });
        </script>
    </body>
</html> 