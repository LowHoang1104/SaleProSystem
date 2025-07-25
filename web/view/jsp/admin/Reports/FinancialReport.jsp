<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>Báo cáo tài chính - ShopT System</title>

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
                                    <li class="breadcrumb-item active">Báo cáo tài chính</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <form action="FinancialReport">
                        <!-- Bộ lọc -->
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Bộ lọc báo cáo tài chính</h4>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Năm</label>
                                            <select class="form-control" name="yearFilter">
                                                <c:forEach items="${years}" var="year">
                                                    <option<c:if test="${yearSelected eq year}"> selected </c:if> value="${year}">${year}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Tháng</label>
                                            <select class="form-control" name="monthFilter">
                                                <option value="">All</option>
                                                <c:forEach items="${months}" var="month">
                                                    <option <c:if test="${monthSelected eq month}"> selected </c:if>  value="${month}">${month}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Chi nhánh</label>
                                            <select class="form-control" name="storeFilter">
                                                <c:forEach items="${stores}" var="store">
                                                    <option <c:if test="${storeSelected eq store.getStoreID()}"> selected </c:if>  value="${store.getStoreID()}">${store.getStoreName()}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Loại quỹ</label>
                                            <select class="form-control" name="fundTypeFilter">
                                                <option <c:if test="${fundTypeSeleted eq ''}">selected</c:if> value="">Tất cả</option>
                                                <option <c:if test="${fundTypeSeleted eq 'Cash'}">selected</c:if> value="Cash">Tiền mặt</option>
                                                <option <c:if test="${fundTypeSeleted eq 'Bank'}">selected</c:if> value="Bank">Ngân hàng</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <button class="btn btn-primary btn-block" type="submit">
                                                <i class="fas fa-search"></i> Lọc
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-lg-2 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <button class="btn btn-success btn-block" onclick="exportFinancialReport()">
                                                <i class="fas fa-download"></i> Xuất Excel
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- Thống kê tổng quan -->
                    <div class="row">
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${totalAmount}" type="number" pattern="#,###"/> VNĐ</h4>
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
                                    <h4><fmt:formatNumber value="${totalExpense}" type="number" pattern="#,###"/> VNĐ</h4>
                                    <h5>Chi phí</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="trending-down"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4><fmt:formatNumber value="${profit}" type="number" pattern="#,###"/> VNĐ</h4>
                                    <h5>Lợi nhuận</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="trending-up"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>${percent}</h4>
                                    <h5>Tỷ lệ lợi nhuận</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="percent"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Biểu đồ tài chính -->
                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Biểu đồ dòng tiền theo tháng</h4>
                                </div>
                                <div class="card-body">
                                    <div id="cashflow-chart"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Phân bố chi phí</h4>
                                </div>
                                <div class="card-body">
                                    <div id="expense-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bảng chi tiết tài chính -->
                    <div class="row">
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Chi tiết doanh thu</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Tháng</th>
                                                    <th>Doanh thu</th>
                                                    <th>Chi phí</th>
                                                    <th>Lợi nhuận</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="i" begin="0" end="${monthsForDetail.size() - 1}">
                                                    <tr>
                                                        <td>${monthsForDetail.get(i)}</td>
                                                        <td><fmt:formatNumber value="${doanhthuForDetail.get(i)}" type="number" pattern="#,###"/> VNĐ</td>
                                                        <td><fmt:formatNumber value="${chiphiForDetail.get(i)}" type="number" pattern="#,###"/> VNĐ</td>
                                                        <td><fmt:formatNumber value="${loinhuanForDetail.get(i)}" type="number" pattern="#,###"/> VNĐ</td>                                                    
                                                        <td></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-sm-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Chi tiết chi phí</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Danh mục</th>
                                                    <th>Số tiền</th>
                                                    <th>Tỷ lệ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>Giá vốn hàng bán</td>
                                                    <td><fmt:formatNumber value="${DetailcircleData[0]}" type="number" pattern="#,###"/> VNĐ</td>
                                                    <td><fmt:formatNumber value="${(DetailcircleData[0] * 100) / (DetailcircleData[0] + DetailcircleData[1] + DetailcircleData[2])}" type="number" maxFractionDigits="2" />%</td>
                                                </tr>
                                                <tr>
                                                    <td>Chi phí nhân viên</td>
                                                    <td><fmt:formatNumber value="${DetailcircleData[1]}" type="number" pattern="#,###"/> VNĐ</td>
                                                    <td><fmt:formatNumber value="${(DetailcircleData[1] * 100) / (DetailcircleData[0] + DetailcircleData[1] + DetailcircleData[2])}" type="number" maxFractionDigits="2" />%</td>
                                                </tr>
                                                <tr>
                                                    <td>Chi phí khác</td>
                                                    <td><fmt:formatNumber value="${DetailcircleData[2]}" type="number" pattern="#,###"/> VNĐ</td>
                                                    <td><fmt:formatNumber value="${(DetailcircleData[3] * 100) / (DetailcircleData[0] + DetailcircleData[1] + DetailcircleData[2])}" type="number" maxFractionDigits="2" />%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Báo cáo quỹ -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Báo cáo quỹ</h4>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-lg-4 col-sm-12">
                                    <div class="card bg-success text-white">
                                        <div class="card-body">
                                            <h5>Quỹ tiền mặt</h5>
                                            <h3><fmt:formatNumber value="${totalAmountCash}" type="number" pattern="#,###"/> VNĐ</h3>
                                            <p>Số dư hiện tại</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-12">
                                    <div class="card bg-primary text-white">
                                        <div class="card-body">
                                            <h5>Quỹ ngân hàng</h5>
                                            <h3><fmt:formatNumber value="${totalAmountBank}" type="number" pattern="#,###"/>VNĐ</h3>
                                            <p>Số dư hiện tại</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-12">
                                    <div class="card bg-info text-white">
                                        <div class="card-body">
                                            <h5>Tổng quỹ</h5>
                                            <h3><fmt:formatNumber value="${totalAmountFund}" type="number" pattern="#,###"/> VNĐ</h3>
                                            <p>Tổng số dư</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Giao dịch quỹ gần đây -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">Giao dịch quỹ gần đây</h4>
                            ${FundTransactions.size()}
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped" id="fundTransactionTable">
                                    <thead>
                                        <tr>
                                            <th>Ngày</th>
                                            <th>Loại quỹ</th>
                                            <th>Loại giao dịch</th>
                                            <th>Số tiền</th>
                                            <th>Mô tả</th>
                                            <th>Người tạo</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${FundTransactions}" var="item">
                                            <tr>
                                                <td>${item.getTransactionDate()}</td>
                                                <td>${item.getTypeOfStoreFund()}</td>
                                                <td><c:if test="${item.getTransactionType() eq 'Expense'}"> <span class="badge bg-danger">Chi ra</span> </c:if><c:if test="${item.getTransactionType() eq 'Income'}"> <span class="badge bg-success">Thu vào</span> </c:if></td>
                                                <td><fmt:formatNumber value="${item.getAmount()}" type="number" pattern="#,###"/>VNĐ</td>
                                                <td>${item.getDescription()}</td>
                                                <td>${item.getNameOfUserCreate()}</td>
                                                <td><c:if test="${item.getStatus() eq 'Approved'}"><span class="badge bg-success">Đã duyệt</span></c:if><c:if test="${item.getStatus() eq 'Rejected'}"><span class="badge bg-danger">Từ chối</span></c:if><c:if test="${item.getStatus() eq 'Pending'}"><span class="badge bg-primary">Chờ phản hồi</span></c:if></td>
                                                </tr>
                                        </c:forEach>
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
                                            // Biểu đồ dòng tiền
                                            var cashflowOptions = {
                                                series: [{
                                                        name: 'Doanh thu',
                                                        data: <%= request.getAttribute("doanhthuForChart")%>
                                                    }, {
                                                        name: 'Chi phí',
                                                        data: <%= request.getAttribute("chiphiForChart")%>
                                                    }, {
                                                        name: 'Lợi nhuận',
                                                        data: <%= request.getAttribute("loinhuanForChart")%>
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
                                                colors: ['#28C76F', '#EA5455', '#7367F0'],
                                                xaxis: {
                                                    categories: <%= request.getAttribute("monthsForChart")%>
                                                },
                                                tooltip: {
                                                    y: {
                                                        formatter: function (val) {
                                                            return val.toLocaleString() + " VNĐ"
                                                        }
                                                    }
                                                },
                                            };

                                            var cashflowChart = new ApexCharts(document.querySelector("#cashflow-chart"), cashflowOptions);
                                            cashflowChart.render();

                                            // Biểu đồ phân bố chi phí
                                            var expenseOptions = {
                                                series: <%= request.getAttribute("circleData")%>,
                                                chart: {
                                                    width: 380,
                                                    type: 'donut'
                                                },
                                                labels: ['Giá vốn hàng bán', 'Chi phí nhân viên', 'Chi phí khác'],
                                                colors: ['#EA5455', '#FF9F43', '#7367F0'],
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

                                            var expenseChart = new ApexCharts(document.querySelector("#expense-chart"), expenseOptions);
                                            expenseChart.render();

                                            // Khởi tạo DataTable
                                            $(document).ready(function () {
                                                $('#fundTransactionTable').DataTable({
                                                    "pageLength": 10,
                                                    "order": [[0, "desc"]]
                                                });
                                            });

                                            // Hàm lọc báo cáo tài chính
                                            function filterFinancialReport() {
                                                var year = $('#yearFilter').val();
                                                var month = $('#monthFilter').val();
                                                var store = $('#storeFilter').val();
                                                var fundType = $('#fundTypeFilter').val();

                                                // Logic lọc báo cáo tài chính sẽ được thêm sau
                                                alert('Tính năng lọc báo cáo tài chính sẽ được thêm sau!');
                                            }

                                            // Hàm xuất báo cáo tài chính
                                            function exportFinancialReport() {
                                                alert('Tính năng xuất báo cáo tài chính sẽ được thêm sau!');
                                            }
        </script>
    </body>
</html> 