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
        <title>Báo cáo cuối ngày - ShopT System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo cuối ngày</li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <!-- Bộ lọc -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Bộ lọc báo cáo</h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Ngày</label>
                                        <input type="date" class="form-control" id="reportDate" value="<%=java.time.LocalDate.now()%>">
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
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
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>Nhân viên</label>
                                        <select class="form-control" id="employeeFilter">
                                            <option value="">Tất cả nhân viên</option>
                                            <option value="1">Thu ngân 1</option>
                                            <option value="2">Thu ngân 2</option>
                                            <option value="3">Quản lý kho</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-sm-6 col-12">
                                    <div class="form-group">
                                        <label>&nbsp;</label>
                                        <button class="btn btn-primary btn-block" onclick="filterDailyReport()">
                                            <i class="fas fa-search"></i> Lọc báo cáo
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
                                    <h4>15,420,000</h4>
                                    <h5>Doanh thu hôm nay</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="dollar-sign"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>156</h4>
                                    <h5>Hóa đơn</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="shopping-cart"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>89</h4>
                                    <h5>Khách hàng mới</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="users"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>12</h4>
                                    <h5>Nhân viên làm việc</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user-check"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ và bảng dữ liệu -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Biểu đồ doanh thu theo giờ</h4>
                                </div>
                                <div class="card-body">
                                    <div id="sales-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Top sản phẩm bán chạy</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Sản phẩm</th>
                                                    <th>Số lượng</th>
                                                    <th>Doanh thu</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Áo thun nam</td>
                                                    <td>25</td>
                                                    <td>2,500,000</td>
                                                </tr>
                                                <tr>
                                                    <td>Quần jean nữ</td>
                                                    <td>18</td>
                                                    <td>1,800,000</td>
                                                </tr>
                                                <tr>
                                                    <td>Váy đầm</td>
                                                    <td>12</td>
                                                    <td>3,600,000</td>
                                                </tr>
                                                <tr>
                                                    <td>Áo khoác</td>
                                                    <td>15</td>
                                                    <td>2,250,000</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng chi tiết giao dịch -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Chi tiết hóa đơn hôm nay</h4>
                            <div class="card-options">
                                <button class="btn btn-primary btn-sm" onclick="exportToExcel()">
                                    <i class="fas fa-download"></i> Xuất Excel
                                </button>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="invoiceTable">
                                    <thead>
                                        <tr>
                                            <th>Mã hóa đơn</th>
                                            <th>Thời gian</th>
                                            <th>Khách hàng</th>
                                            <th>Thu ngân</th>
                                            <th>Tổng tiền</th>
                                            <th>Giảm giá</th>
                                            <th>Thuế VAT</th>
                                            <th>Thanh toán</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>HD000001</td>
                                            <td>09:15</td>
                                            <td>Nguyễn Văn A</td>
                                            <td>Thu ngân 1</td>
                                            <td>1,250,000</td>
                                            <td>50,000</td>
                                            <td>120,000</td>
                                            <td>Tiền mặt</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                        </tr>
                                        <tr>
                                            <td>HD000002</td>
                                            <td>10:30</td>
                                            <td>Trần Thị B</td>
                                            <td>Thu ngân 2</td>
                                            <td>2,100,000</td>
                                            <td>100,000</td>
                                            <td>200,000</td>
                                            <td>Chuyển khoản</td>
                                            <td><span class="badge bg-success">Hoàn thành</span></td>
                                        </tr>
                                        <tr>
                                            <td>HD000003</td>
                                            <td>11:45</td>
                                            <td>Lê Văn C</td>
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

                    <!-- Thống kê quỹ -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Thống kê quỹ</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Loại quỹ</th>
                                                    <th>Số dư đầu ngày</th>
                                                    <th>Thu vào</th>
                                                    <th>Chi ra</th>
                                                    <th>Số dư cuối ngày</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Tiền mặt</td>
                                                    <td>5,000,000</td>
                                                    <td>8,500,000</td>
                                                    <td>2,000,000</td>
                                                    <td>11,500,000</td>
                                                </tr>
                                                <tr>
                                                    <td>Ngân hàng</td>
                                                    <td>50,000,000</td>
                                                    <td>6,920,000</td>
                                                    <td>0</td>
                                                    <td>56,920,000</td>
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
                                    <h4 class="card-title">Thống kê chấm công</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Nhân viên</th>
                                                    <th>Ca làm việc</th>
                                                    <th>Giờ vào</th>
                                                    <th>Giờ ra</th>
                                                    <th>Tổng giờ</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Thu ngân 1</td>
                                                    <td>Sáng</td>
                                                    <td>08:00</td>
                                                    <td>17:00</td>
                                                    <td>9h</td>
                                                    <td><span class="badge bg-success">Đúng giờ</span></td>
                                                </tr>
                                                <tr>
                                                    <td>Thu ngân 2</td>
                                                    <td>Chiều</td>
                                                    <td>13:00</td>
                                                    <td>22:00</td>
                                                    <td>9h</td>
                                                    <td><span class="badge bg-success">Đúng giờ</span></td>
                                                </tr>
                                                <tr>
                                                    <td>Quản lý kho</td>
                                                    <td>Toàn ngày</td>
                                                    <td>08:30</td>
                                                    <td>17:30</td>
                                                    <td>9h</td>
                                                    <td><span class="badge bg-warning">Muộn 30p</span></td>
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
            // Biểu đồ doanh thu
            var options = {
                series: [{
                    name: 'Doanh thu',
                    data: [2100000, 1800000, 2500000, 3200000, 2800000, 3500000, 4200000, 3800000, 4500000, 5200000, 4800000, 5500000]
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
                    categories: ['8h', '9h', '10h', '11h', '12h', '13h', '14h', '15h', '16h', '17h', '18h', '19h']
                },
                tooltip: {
                    x: {
                        format: 'dd/MM/yy HH:mm'
                    },
                },
            };

            var chart = new ApexCharts(document.querySelector("#sales-chart"), options);
            chart.render();

            // Khởi tạo DataTable
            $(document).ready(function() {
                $('#invoiceTable').DataTable({
                    "pageLength": 10,
                    "order": [[1, "desc"]]
                });
            });

            // Hàm lọc báo cáo
            function filterDailyReport() {
                var date = $('#reportDate').val();
                var store = $('#storeFilter').val();
                var employee = $('#employeeFilter').val();
                
                // Logic lọc báo cáo sẽ được thêm sau
                alert('Tính năng lọc báo cáo sẽ được thêm sau!');
            }

            // Hàm xuất Excel
            function exportToExcel() {
                alert('Tính năng xuất Excel sẽ được thêm sau!');
            }
        </script>
    </body>
</html> 