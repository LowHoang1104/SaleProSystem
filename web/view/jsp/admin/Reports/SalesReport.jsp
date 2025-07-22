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
        <title>Báo cáo bán hàng - ShopT System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo bán hàng</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Bộ lọc -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Bộ lọc báo cáo bán hàng</h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Từ ngày</label>
                                        <input type="date" class="form-control" id="startDate">
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Đến ngày</label>
                                        <input type="date" class="form-control" id="endDate">
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Chi nhánh</label>
                                        <select class="form-control" id="storeFilter">
                                            <option value="">Tất cả chi nhánh</option>
                                            <option value="1">Chi nhánh 1</option>
                                            <option value="2">Chi nhánh 2</option>
                                            <option value="3">Chi nhánh 3</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Thu ngân</label>
                                        <select class="form-control" id="employeeFilter">
                                            <option value="">Tất cả thu ngân</option>
                                            <option value="1">Thu ngân 1</option>
                                            <option value="2">Thu ngân 2</option>
                                            <option value="3">Thu ngân 3</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Phương thức thanh toán</label>
                                        <select class="form-control" id="paymentFilter">
                                            <option value="">Tất cả</option>
                                            <option value="1">Tiền mặt</option>
                                            <option value="2">Chuyển khoản</option>
                                            <option value="3">Thẻ tín dụng</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-2 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>&nbsp;</label>
                                        <button class="btn btn-primary btn-block" onclick="filterSalesReport()">
                                            <i class="fas fa-search"></i> Lọc
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê tổng quan -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4>245,680,000</h4>
                                    <h5>Tổng doanh thu</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="dollar-sign"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>1,245</h4>
                                    <h5>Tổng hóa đơn</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-cart"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>856</h4>
                                    <h5>Khách hàng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="users"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>15.2%</h4>
                                    <h5>Tăng trưởng</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="trending-up"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Biểu đồ doanh thu theo thời gian</h4>
                                </div>
                                <div class="card-body">
                                    <div id="sales-trend-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố doanh thu theo danh mục</h4>
                                </div>
                                <div class="card-body">
                                    <div id="category-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng chi tiết -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Chi tiết bán hàng</h4>
                            <div class="card-options">
                                <button class="btn btn-primary btn-sm" onclick="exportToExcel()">
                                    <i class="fas fa-download"></i> Xuất Excel
                                </button>
                                <button class="btn btn-success btn-sm" onclick="printReport()">
                                    <i class="fas fa-print"></i> In báo cáo
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="salesTable">
                                    <thead>
                                        <tr>
                                            <th>Ngày</th>
                                            <th>Mã hóa đơn</th>
                                            <th>Khách hàng</th>
                                            <th>Chi nhánh</th>
                                            <th>Thu ngân</th>
                                            <th>Tổng tiền</th>
                                            <th>Giảm giá</th>
                                            <th>Thuế VAT</th>
                                            <th>Phương thức TT</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>15/01/2024</td>
                                            <td>HD000001</td>
                                            <td>Nguyễn Văn A</td>
                                            <td>Chi nhánh 1</td>
                                            <td>Thu ngân 1</td>
                                            <td>1,250,000</td>
                                            <td>50,000</td>
                                            <td>120,000</td>
                                            <td>Tiền mặt</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                        </tr>
                                        <tr>
                                            <td>15/01/2024</td>
                                            <td>HD000002</td>
                                            <td>Trần Thị B</td>
                                            <td>Chi nhánh 1</td>
                                            <td>Thu ngân 2</td>
                                            <td>2,100,000</td>
                                            <td>100,000</td>
                                            <td>200,000</td>
                                            <td>Chuyển khoản</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                        </tr>
                                        <tr>
                                            <td>14/01/2024</td>
                                            <td>HD000003</td>
                                            <td>Lê Văn C</td>
                                            <td>Chi nhánh 2</td>
                                            <td>Thu ngân 1</td>
                                            <td>850,000</td>
                                            <td>0</td>
                                            <td>85,000</td>
                                            <td>Tiền mặt</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê theo chi nhánh -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê theo chi nhánh</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Chi nhánh</th>
                                                    <th>Số hóa đơn</th>
                                                    <th>Doanh thu</th>
                                                    <th>Khách hàng</th>
                                                    <th>TB/hóa đơn</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Chi nhánh 1</td>
                                                    <td>456</td>
                                                    <td>89,600,000</td>
                                                    <td>312</td>
                                                    <td>196,491</td>
                                                </tr>
                                                <tr>
                                                    <td>Chi nhánh 2</td>
                                                    <td>389</td>
                                                    <td>76,200,000</td>
                                                    <td>267</td>
                                                    <td>195,887</td>
                                                </tr>
                                                <tr>
                                                    <td>Chi nhánh 3</td>
                                                    <td>400</td>
                                                    <td>79,880,000</td>
                                                    <td>277</td>
                                                    <td>199,700</td>
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
                                    <h4 class="card-title">Thống kê theo phương thức thanh toán</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Phương thức</th>
                                                    <th>Số giao dịch</th>
                                                    <th>Tổng tiền</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Tiền mặt</td>
                                                    <td>856</td>
                                                    <td>159,680,000</td>
                                                    <td>65.0%</td>
                                                </tr>
                                                <tr>
                                                    <td>Chuyển khoản</td>
                                                    <td>312</td>
                                                    <td>68,640,000</td>
                                                    <td>27.9%</td>
                                                </tr>
                                                <tr>
                                                    <td>Thẻ tín dụng</td>
                                                    <td>77</td>
                                                    <td>17,360,000</td>
                                                    <td>7.1%</td>
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
            // Biểu đồ doanh thu theo thời gian
            var salesOptions = {
                series: [{
                    name: 'Doanh thu',
                    data: [45000000, 52000000, 48000000, 61000000, 55000000, 67000000, 72000000, 68000000, 75000000, 82000000, 78000000, 85000000]
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
                            return val.toLocaleString() + " VNĐ"
                        }
                    }
                },
            };

            var salesChart = new ApexCharts(document.querySelector("#sales-trend-chart"), salesOptions);
            salesChart.render();

            // Biểu đồ phân bố danh mục
            var categoryOptions = {
                series: [44, 55, 13, 43],
                chart: {
                    width: 380,
                    type: 'pie',
                },
                labels: ['Áo', 'Quần', 'Váy', 'Phụ kiện'],
                colors: ['#7367F0', '#28C76F', '#EA5455', '#FF9F43'],
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

            var categoryChart = new ApexCharts(document.querySelector("#category-chart"), categoryOptions);
            categoryChart.render();

            // Khởi tạo DataTable
            $(document).ready(function() {
                $('#salesTable').DataTable({
                    "pageLength": 10,
                    "order": [[0, "desc"]]
                });
            });

            // Hàm lọc báo cáo bán hàng
            function filterSalesReport() {
                var startDate = $('#startDate').val();
                var endDate = $('#endDate').val();
                var store = $('#storeFilter').val();
                var employee = $('#employeeFilter').val();
                var payment = $('#paymentFilter').val();
                
                // Logic lọc báo cáo sẽ được thêm sau
                alert('Tính năng lọc báo cáo sẽ được thêm sau!');
            }

            // Hàm xuất Excel
            function exportToExcel() {
                alert('Tính năng xuất Excel sẽ được thêm sau!');
            }

            // Hàm in báo cáo
            function printReport() {
                window.print();
            }
        </script>
    </body>
</html> 