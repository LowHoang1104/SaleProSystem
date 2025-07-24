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
        <title>Báo cáo khách hàng - SalePro System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo khách hàng</li>
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
                                    <h4>856</h4>
                                    <h5>Khách hàng mới</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user-plus"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>389</h4>
                                    <h5>Khách VIP</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="star"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>4.2</h4>
                                    <h5>Đánh giá TB</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="thumbs-up"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ khách hàng -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Tăng trưởng khách hàng theo tháng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="customer-growth-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân loại khách hàng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="customer-type-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Top khách hàng VIP -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Top 10 khách hàng VIP</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Khách hàng</th>
                                            <th>Số điện thoại</th>
                                            <th>Tổng mua hàng</th>
                                            <th>Số đơn hàng</th>
                                            <th>Lần mua gần nhất</th>
                                            <th>Hạng VIP</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/customer/customer1.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Nguyễn Văn A</h6>
                                                        <small class="text-muted">VIP001</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>0901234567</td>
                                            <td>125,680,000 VNĐ</td>
                                            <td>45</td>
                                            <td>15/01/2024</td>
                                            <td><span class="badge bg-warning">Gold</span></td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/customer/customer2.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Trần Thị B</h6>
                                                        <small class="text-muted">VIP002</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>0901234568</td>
                                            <td>98,450,000 VNĐ</td>
                                            <td>32</td>
                                            <td>14/01/2024</td>
                                            <td><span class="badge bg-secondary">Silver</span></td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/customer/customer3.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Lê Văn C</h6>
                                                        <small class="text-muted">VIP003</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>0901234569</td>
                                            <td>87,320,000 VNĐ</td>
                                            <td>28</td>
                                            <td>13/01/2024</td>
                                            <td><span class="badge bg-secondary">Silver</span></td>
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
                                    <h4 class="card-title">Thống kê theo độ tuổi</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Độ tuổi</th>
                                                    <th>Số lượng</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>18-25</td>
                                                    <td>245</td>
                                                    <td>19.7%</td>
                                                </tr>
                                                <tr>
                                                    <td>26-35</td>
                                                    <td>456</td>
                                                    <td>36.6%</td>
                                                </tr>
                                                <tr>
                                                    <td>36-45</td>
                                                    <td>389</td>
                                                    <td>31.2%</td>
                                                </tr>
                                                <tr>
                                                    <td>46+</td>
                                                    <td>155</td>
                                                    <td>12.5%</td>
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
                                    <h4 class="card-title">Thống kê theo giới tính</h4>
                                </div>
                                <div class="card-body">
                                    <div id="gender-chart"></div>
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
            // Biểu đồ tăng trưởng khách hàng
            var growthOptions = {
                series: [{
                    name: 'Khách hàng mới',
                    data: [45, 52, 38, 24, 33, 26, 21, 20, 6, 8, 15, 10]
                }, {
                    name: 'Khách hàng tổng',
                    data: [45, 97, 135, 159, 192, 218, 239, 259, 265, 273, 288, 298]
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
                colors: ['#7367F0', '#28C76F'],
                xaxis: {
                    categories: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12']
                },
                tooltip: {
                    y: {
                        formatter: function (val) {
                            return val + " khách hàng"
                        }
                    }
                },
            };

            var growthChart = new ApexCharts(document.querySelector("#customer-growth-chart"), growthOptions);
            growthChart.render();

            // Biểu đồ phân loại khách hàng
            var typeOptions = {
                series: [65, 25, 10],
                chart: {
                    width: 380,
                    type: 'donut',
                },
                labels: ['Khách thường', 'Khách VIP', 'Khách VIP+'],
                colors: ['#7367F0', '#FF9F43', '#EA5455'],
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

            var typeChart = new ApexCharts(document.querySelector("#customer-type-chart"), typeOptions);
            typeChart.render();

            // Biểu đồ giới tính
            var genderOptions = {
                series: [65, 35],
                chart: {
                    width: 380,
                    type: 'pie',
                },
                labels: ['Nam', 'Nữ'],
                colors: ['#7367F0', '#EA5455'],
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

            var genderChart = new ApexCharts(document.querySelector("#gender-chart"), genderOptions);
            genderChart.render();
        </script>
    </body>
</html> 