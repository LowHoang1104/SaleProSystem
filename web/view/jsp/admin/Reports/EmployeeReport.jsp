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
        <title>Báo cáo nhân viên - SalePro System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo nhân viên</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Bộ lọc -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Bộ lọc báo cáo nhân viên</h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Phòng ban</label>
                                        <select class="form-control" id="departmentFilter">
                                            <option value="">Tất cả phòng ban</option>
                                            <option value="sales">Bán hàng</option>
                                            <option value="warehouse">Kho</option>
                                            <option value="accounting">Kế toán</option>
                                            <option value="management">Quản lý</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Tháng</label>
                                        <select class="form-control" id="monthFilter">
                                            <option value="">Tất cả tháng</option>
                                            <option value="1">Tháng 1</option>
                                            <option value="2">Tháng 2</option>
                                            <option value="3">Tháng 3</option>
                                            <option value="4">Tháng 4</option>
                                            <option value="5">Tháng 5</option>
                                            <option value="6">Tháng 6</option>
                                            <option value="7">Tháng 7</option>
                                            <option value="8">Tháng 8</option>
                                            <option value="9">Tháng 9</option>
                                            <option value="10">Tháng 10</option>
                                            <option value="11">Tháng 11</option>
                                            <option value="12">Tháng 12</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Loại báo cáo</label>
                                        <select class="form-control" id="reportType">
                                            <option value="performance">Hiệu suất</option>
                                            <option value="attendance">Chấm công</option>
                                            <option value="salary">Lương</option>
                                            <option value="sales">Bán hàng</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>&nbsp;</label>
                                        <button class="btn btn-primary btn-block" onclick="filterEmployeeReport()">
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
                                    <h4>25</h4>
                                    <h5>Tổng nhân viên</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="users"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>22</h4>
                                    <h5>Đang làm việc</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user-check"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>95.2%</h4>
                                    <h5>Tỷ lệ chấm công</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="clock"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>8.5</h4>
                                    <h5>Giờ làm TB/ngày</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="activity"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ nhân viên -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Hiệu suất bán hàng theo nhân viên</h4>
                                </div>
                                <div class="card-body">
                                    <div id="employee-performance-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố nhân viên theo phòng ban</h4>
                                </div>
                                <div class="card-body">
                                    <div id="department-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng chi tiết nhân viên -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Chi tiết hiệu suất nhân viên</h4>
                            <div class="card-options">
                                <button class="btn btn-primary btn-sm" onclick="exportEmployeeReport()">
                                    <i class="fas fa-download"></i> Xuất Excel
                                </button>
                                <button class="btn btn-success btn-sm" onclick="printEmployeeReport()">
                                    <i class="fas fa-print"></i> In báo cáo
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="employeeTable">
                                    <thead>
                                        <tr>
                                            <th>Nhân viên</th>
                                            <th>Phòng ban</th>
                                            <th>Ngày làm việc</th>
                                            <th>Giờ làm</th>
                                            <th>Đơn hàng</th>
                                            <th>Doanh thu</th>
                                            <th>Hiệu suất</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/profiles/avatar-02.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Nguyễn Văn A</h6>
                                                        <small class="text-muted">NV001</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Bán hàng</td>
                                            <td>22</td>
                                            <td>176</td>
                                            <td>45</td>
                                            <td>125,680,000</td>
                                            <td><span class="badge bg-success">95%</span></td>
                                            <td><span class="badge bg-success">Đang làm việc</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/profiles/avatar-03.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Trần Thị B</h6>
                                                        <small class="text-muted">NV002</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Bán hàng</td>
                                            <td>20</td>
                                            <td>160</td>
                                            <td>38</td>
                                            <td>98,450,000</td>
                                            <td><span class="badge bg-warning">85%</span></td>
                                            <td><span class="badge bg-success">Đang làm việc</span></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=path%>/view/assets/img/profiles/avatar-04.jpg" class="rounded-circle me-2" width="40">
                                                    <div>
                                                        <h6 class="mb-0">Lê Văn C</h6>
                                                        <small class="text-muted">NV003</small>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>Kho</td>
                                            <td>21</td>
                                            <td>168</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td><span class="badge bg-info">90%</span></td>
                                            <td><span class="badge bg-success">Đang làm việc</span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng lương nhân viên -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Bảng lương tháng này</h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Nhân viên</th>
                                            <th>Lương cơ bản</th>
                                            <th>Thưởng</th>
                                            <th>Phụ cấp</th>
                                            <th>Khấu trừ</th>
                                            <th>Thực lãnh</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Nguyễn Văn A</td>
                                            <td>15,000,000</td>
                                            <td>2,500,000</td>
                                            <td>500,000</td>
                                            <td>1,500,000</td>
                                            <td><strong>16,500,000</strong></td>
                                        </tr>
                                        <tr>
                                            <td>Trần Thị B</td>
                                            <td>12,000,000</td>
                                            <td>1,800,000</td>
                                            <td>300,000</td>
                                            <td>1,200,000</td>
                                            <td><strong>12,900,000</strong></td>
                                        </tr>
                                        <tr>
                                            <td>Lê Văn C</td>
                                            <td>10,000,000</td>
                                            <td>0</td>
                                            <td>200,000</td>
                                            <td>1,000,000</td>
                                            <td><strong>9,200,000</strong></td>
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
            // Biểu đồ hiệu suất nhân viên
            var performanceOptions = {
                series: [{
                    name: 'Doanh thu',
                    data: [125680000, 98450000, 0, 0, 0, 0, 0, 0, 0, 0]
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
                    categories: ['NV001', 'NV002', 'NV003', 'NV004', 'NV005', 'NV006', 'NV007', 'NV008', 'NV009', 'NV010'],
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

            var performanceChart = new ApexCharts(document.querySelector("#employee-performance-chart"), performanceOptions);
            performanceChart.render();

            // Biểu đồ phân bố phòng ban
            var departmentOptions = {
                series: [12, 8, 3, 2],
                chart: {
                    width: 380,
                    type: 'pie',
                },
                labels: ['Bán hàng', 'Kho', 'Kế toán', 'Quản lý'],
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

            var departmentChart = new ApexCharts(document.querySelector("#department-chart"), departmentOptions);
            departmentChart.render();

            // Khởi tạo DataTable
            $(document).ready(function() {
                $('#employeeTable').DataTable({
                    "pageLength": 10,
                    "order": [[5, "desc"]]
                });
            });

            // Hàm lọc báo cáo nhân viên
            function filterEmployeeReport() {
                var department = $('#departmentFilter').val();
                var month = $('#monthFilter').val();
                var reportType = $('#reportType').val();
                
                // Logic lọc báo cáo nhân viên sẽ được thêm sau
                alert('Tính năng lọc báo cáo nhân viên sẽ được thêm sau!');
            }

            // Hàm xuất báo cáo nhân viên
            function exportEmployeeReport() {
                alert('Tính năng xuất báo cáo nhân viên sẽ được thêm sau!');
            }

            // Hàm in báo cáo nhân viên
            function printEmployeeReport() {
                window.print();
            }
        </script>
    </body>
</html> 