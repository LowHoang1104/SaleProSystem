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
        <title>Báo cáo hàng hóa - SalePro System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo hàng hóa</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê tổng quan -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4>1,856</h4>
                                    <h5>Tổng sản phẩm</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="package"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>1,245</h4>
                                    <h5>Đang bán</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="check-circle"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>89</h4>
                                    <h5>Hết hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="x-circle"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>522</h4>
                                    <h5>Sắp hết hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="alert-triangle"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ hàng hóa -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Doanh thu theo danh mục sản phẩm</h4>
                                </div>
                                <div class="card-body">
                                    <div id="category-revenue-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Trạng thái tồn kho</h4>
                                </div>
                                <div class="card-body">
                                    <div id="inventory-status-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Top sản phẩm bán chạy -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Top 10 sản phẩm bán chạy</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Sản phẩm</th>
                                            <th>Danh mục</th>
                                            <th>Số lượng bán</th>
                                            <th>Doanh thu</th>
                                            <th>Tồn kho</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/product/product1.jpg" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">iPhone 15 Pro</h6>
                                                        <small class="text-muted">PRD001</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Điện thoại</td>
                                            <td>156</td>
                                            <td>3,900,000,000 VNĐ</td>
                                            <td>45</td>
                                            <td><span class="badge bg-success">Còn hàng</span></td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/product/product2.jpg" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Samsung Galaxy S24</h6>
                                                        <small class="text-muted">PRD002</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Điện thoại</td>
                                            <td>142</td>
                                            <td>2,556,000,000 VNĐ</td>
                                            <td>23</td>
                                            <td><span class="badge bg-warning">Sắp hết</span></td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/product/product3.jpg" class="rounded me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">MacBook Air M2</h6>
                                                        <small class="text-muted">PRD003</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Laptop</td>
                                            <td>89</td>
                                            <td>2,670,000,000 VNĐ</td>
                                            <td>12</td>
                                            <td><span class="badge bg-warning">Sắp hết</span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Sản phẩm sắp hết hàng -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Sản phẩm sắp hết hàng</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Danh mục</th>
                                            <th>Tồn kho</th>
                                            <th>Định mức tối thiểu</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Samsung Galaxy S24</td>
                                            <td>Điện thoại</td>
                                            <td>23</td>
                                            <td>50</td>
                                            <td><span class="badge bg-warning">Cần nhập hàng</span></td>
                                            <td><button class="btn btn-primary btn-sm">Nhập hàng</button></td>
                                        </tr>
                                        <tr>
                                            <td>MacBook Air M2</td>
                                            <td>Laptop</td>
                                            <td>12</td>
                                            <td>30</td>
                                            <td><span class="badge bg-danger">Khẩn cấp</span></td>
                                            <td><button class="btn btn-primary btn-sm">Nhập hàng</button></td>
                                        </tr>
                                        <tr>
                                            <td>iPad Pro</td>
                                            <td>Máy tính bảng</td>
                                            <td>8</td>
                                            <td>25</td>
                                            <td><span class="badge bg-danger">Khẩn cấp</span></td>
                                            <td><button class="btn btn-primary btn-sm">Nhập hàng</button></td>
                                        </tr>
                                    </tbody>
                                </table>
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
            // Biểu đồ doanh thu theo danh mục
            var categoryOptions = {
                series: [{
                    name: 'Doanh thu',
                    data: [3900000000, 2556000000, 2670000000, 890000000, 450000000]
                }],
                chart: {
                    height: 350,
                    type: 'bar',
                    toolbar: {
                        show: false
                    }
                },
                colors: ['#7367F0'],
                plotOptions: {
                    bar: {
                        horizontal: false,
                        columnWidth: '55%',
                        endingShape: 'rounded'
                    },
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    show: true,
                    width: 2,
                    colors: ['transparent']
                },
                xaxis: {
                    categories: ['Điện thoại', 'Laptop', 'Máy tính bảng', 'Phụ kiện', 'Khác'],
                },
                yaxis: {
                    title: {
                        text: 'Doanh thu (VNĐ)'
                    }
                },
                fill: {
                    opacity: 1
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val.toLocaleString() + " VNĐ"
                        }
                    }
                }
            };

            var categoryChart = new ApexCharts(document.querySelector("#category-revenue-chart"), categoryOptions);
            categoryChart.render();

            // Biểu đồ trạng thái tồn kho
            var inventoryOptions = {
                series: [1245, 89, 522],
                chart: {
                    width: 380,
                    type: 'donut',
                },
                labels: ['Còn hàng', 'Hết hàng', 'Sắp hết'],
                colors: ['#28C76F', '#EA5455', '#FF9F43'],
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

            var inventoryChart = new ApexCharts(document.querySelector("#inventory-status-chart"), inventoryOptions);
            inventoryChart.render();
        </script>
    </body>
</html> 