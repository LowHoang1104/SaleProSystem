<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- ✅ THÊM DÒNG NÀY --%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.StringBuilder" %>
<%@ page import="salepro.models.ProductReportModel" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Dreams Pos admin template</title>

        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/animate.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/logistics/product-report.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">

            <div class="header">

                <div class="header-left active">
                    <a href="index.html" class="logo">
                        <img src="${pageContext.request.contextPath}/view/assets/img/logo.png" alt="">
                    </a>
                    <a href="index.html" class="logo-small">
                        <img src="${pageContext.request.contextPath}/view/assets/img/logo-small.png" alt="">
                    </a>
                    <a id="toggle_btn" href="javascript:void(0);">
                    </a>
                </div>

                <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                    <span class="bar-icon">
                        <span></span>
                        <span></span>
                        <span></span>
                    </span>
                </a>

                <ul class="nav user-menu">

                    <li class="nav-item">
                        <div class="top-nav-search">
                            <a href="javascript:void(0);" class="responsive-search">
                                <i class="fa fa-search"></i>
                            </a>
                            <form action="#">
                                <div class="searchinputs">
                                    <input type="text" placeholder="Search Here ...">
                                    <div class="search-addon">
                                        <span><img src="${pageContext.request.contextPath}/view/assets/img/icons/closes.svg" alt="img"></span>
                                    </div>
                                </div>
                                <a class="btn" id="searchdiv"><img src="${pageContext.request.contextPath}/view/assets/img/icons/search.svg" alt="img"></a>
                            </form>
                        </div>
                    </li>


                    <li class="nav-item dropdown has-arrow flag-nav">
                        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="javascript:void(0);" role="button">
                            <img src="${pageContext.request.contextPath}/view/assets/img/flags/us1.png" alt="" height="20">
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="${pageContext.request.contextPath}/view/assets/img/flags/us.png" alt="" height="16"> English
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="${pageContext.request.contextPath}/view/assets/img/flags/fr.png" alt="" height="16"> French
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="${pageContext.request.contextPath}/view/assets/img/flags/es.png" alt="" height="16"> Spanish
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="${pageContext.request.contextPath}/view/assets/img/flags/de.png" alt="" height="16"> German
                            </a>
                        </div>
                    </li>


                    <li class="nav-item dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">4</span>
                        </a>
                        <div class="dropdown-menu notifications">
                            <div class="topnav-dropdown-header">
                                <span class="notification-title">Notifications</span>
                                <a href="javascript:void(0)" class="clear-noti"> Clear All </a>
                            </div>
                            <div class="noti-content">
                                <ul class="notification-list">
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="${pageContext.request.contextPath}/view/assets/img/profiles/avatar-02.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">John Doe</span> added new task <span class="noti-title">Patient appointment booking</span></p>
                                                    <p class="noti-time"><span class="notification-time">4 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="${pageContext.request.contextPath}/view/assets/img/profiles/avatar-03.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Tarah Shropshire</span> changed the task name <span class="noti-title">Appointment booking with payment gateway</span></p>
                                                    <p class="noti-time"><span class="notification-time">6 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="${pageContext.request.contextPath}/view/assets/img/profiles/avatar-06.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Misty Tison</span> added <span class="noti-title">Domenic Houston</span> and <span class="noti-title">Claire Mapes</span> to project <span class="noti-title">Doctor available module</span></p>
                                                    <p class="noti-time"><span class="notification-time">8 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="${pageContext.request.contextPath}/view/assets/img/profiles/avatar-17.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Rolland Webber</span> completed task <span class="noti-title">Patient and Doctor video conferencing</span></p>
                                                    <p class="noti-time"><span class="notification-time">12 mins ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="notification-message">
                                        <a href="activities.html">
                                            <div class="media d-flex">
                                                <span class="avatar flex-shrink-0">
                                                    <img alt="" src="${pageContext.request.contextPath}/view/assets/img/profiles/avatar-13.jpg">
                                                </span>
                                                <div class="media-body flex-grow-1">
                                                    <p class="noti-details"><span class="noti-title">Bernardo Galaviz</span> added new task <span class="noti-title">Private chat module</span></p>
                                                    <p class="noti-time"><span class="notification-time">2 days ago</span></p>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="topnav-dropdown-footer">
                                <a href="activities.html">View all Notifications</a>
                            </div>
                        </div>
                    </li>

                    <li class="nav-item dropdown has-arrow main-drop">
                        <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                            <span class="user-img"><img src="${pageContext.request.contextPath}/view/assets/img/profiles/avator1.jpg" alt="">
                                <span class="status online"></span></span>
                        </a>
                        <div class="dropdown-menu menu-drop-user">
                            <div class="profilename">
                                <div class="profileset">
                                    <span class="user-img"><img src="${pageContext.request.contextPath}/view/assets/img/profiles/avator1.jpg" alt="">
                                        <span class="status online"></span></span>
                                    <div class="profilesets">
                                        <h6>John Doe</h6>
                                        <h5>Admin</h5>
                                    </div>
                                </div>
                                <hr class="m-0">
                                <a class="dropdown-item" href="profile.html"> <i class="me-2" data-feather="user"></i> My Profile</a>
                                <a class="dropdown-item" href="generalsettings.html"><i class="me-2" data-feather="settings"></i>Settings</a>
                                <hr class="m-0">
                                <a class="dropdown-item logout pb-0" href="signin.html"><img src="${pageContext.request.contextPath}/view/assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>


                <div class="dropdown mobile-user-menu">
                    <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="profile.html">My Profile</a>
                        <a class="dropdown-item" href="generalsettings.html">Settings</a>
                        <a class="dropdown-item" href="signin.html">Logout</a>
                    </div>
                </div>

            </div>


            <div class="sidebar" id="sidebar">
                <div class="sidebar-inner slimscroll">
                    <div id="sidebar-menu" class="sidebar-menu">
                        <ul>
                            <li>
                                <a href="index.html"><img src="${pageContext.request.contextPath}/view/assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span> </a>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li>Product</li>
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=1">Product List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=2">Add Product</a></li>
                                    <li>Warehouse</li>
                                    <li><a href="#">Checking Inventory</a></li>
                                    <li><a href="#">Create Inventory Form</a></li>
                                    <li>Attributes</li>
                                    <li><a href="#">Attributes List</a></li>
                                    <li><a href="#">Add Attributes</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/view/assets/img/icons/store.svg" alt="img"><span> Logistics</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="#">Store List</a></li>
                                    <li><a href="#">Warehouse List</a></li>
                                    <li><a href="#">Inventory List</a></li>
                                    <li><a href="#">Purchase List</a></li>
                                </ul>
                            </li>                    
                        </ul>
                    </div>
                </div>
            </div>

            <div class="page-wrapper">
                <div class="content">
                    <div class="report-container" style="display: flex; gap: 30px; padding: 24px; max-width: 100%;">
                        <div class="report-sidebar" style="width: 280px; flex-shrink: 0;">
                            <label>Kiểu hiển thị</label>
                            <div class="report-btn-group">
                                <form method="post" action="productreport">
                                    <button type="submit" name="view" value="chart" class="report-btn">Biểu đồ</button>
                                </form>
                                <form method="post" action="productreport">
                                    <button type="submit" name="view" value="table" class="report-btn">Báo cáo</button>
                                </form>
                            </div>

                            <label>Phím tắt thời gian</label>

                            <small><strong>Theo ngày</strong></small>
                            <div class="quick-date-range">
                                <form action="productreport" method="post"><button type="submit" name="today">Hôm nay</button></form>
                                <form action="productreport" method="post"><button type="submit" name="yesterday">Hôm qua</button></form>
                            </div>

                            <small><strong>Theo tuần</strong></small>
                            <div class="quick-date-range">
                                <form action="productreport" method="post"><button type="submit" name="thisWeek">Tuần này</button></form>
                                <form action="productreport" method="post"><button type="submit" name="lastWeek">Tuần trước</button></form>
                                <form action="productreport" method="post"><button type="submit" name="last7Days">7 ngày qua</button></form>
                            </div>

                            <small><strong>Theo tháng</strong></small>
                            <div class="quick-date-range">
                                <form action="productreport" method="post"><button type="submit" name="thisMonth">Tháng này</button></form>
                                <form action="productreport" method="post"><button type="submit" name="lastMonth">Tháng trước</button></form>
                                <form action="productreport" method="post"><button type="submit" name="last30Days">30 ngày qua</button></form>
                            </div>

                            <small><strong>Theo quý</strong></small>
                            <div class="quick-date-range">
                                <form action="productreport" method="post"><button type="submit" name="thisQuarter">Quý này</button></form>
                                <form action="productreport" method="post"><button type="submit" name="lastQuarter">Quý trước</button></form>
                            </div>

                            <small><strong>Theo năm</strong></small>
                            <div class="quick-date-range">
                                <form action="productreport" method="post"><button type="submit" name="thisYear">Năm nay</button></form>
                                <form action="productreport" method="post"><button type="submit" name="lastYear">Năm trước</button></form>
                            </div>


                            <form action="productreport" method="post" style="margin-top: 24px; display: flex; flex-direction: column; gap: 10px;">
                                <label>Ngày bắt đầu</label>
                                <input type="date" name="startDate">

                                <label>Ngày kết thúc</label>
                                <input type="date" name="endDate">

                                <label>% dưới mức tối thiểu &gt;=</label>
                                <input type="text" name="minPercentLow" value="0">

                                <label>Giá trị tồn &gt;=</label>
                                <input type="text" name="minStockValue" value="0">

                                <button type="submit" name="filter" class="report-btn">Tìm kiếm</button>
                            </form>
                            <label>Xuất file</label>
                            <div class="report-btn-group">
                                <form method="post" action="excelcontroller">
                                    <button class="report-btn" type="submit" name="type" value="product">Excel</button>
                                </form>
                            </div>
                        </div>

                        <div class="report-main" style="flex: 1;">
                            <h5 style="margin-bottom: 16px;">Biểu đồ tồn kho theo danh mục</h5>
                            <%-- Lấy chế độ xem từ session --%>
                            <c:set var="productViewMode" value="${sessionScope.productViewMode}" />
                            <c:if test="${productViewMode eq 'table'}">
                                <table style="width: 100%; border-collapse: collapse; margin-bottom: 24px;">
                                    <thead>
                                        <tr style="background-color: #f1f1f1;">
                                            <th>Danh mục</th>
                                            <th>Số lượng</th>
                                            <th>Giá trị tồn</th>
                                            <th>Tỷ lệ dưới min (%)</th>
                                            <th>SL dưới min</th>
                                            <th>SL trên min</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${data}" var="d">
                                            <tr>
                                                <td>${d.getName()}</td>
                                                <td>${d.getQuantity()}</td>
                                                <td><fmt:formatNumber value="${d.getTotalmoney()}" pattern="#,###"/></td>
                                                <td><fmt:formatNumber value="${d.getPercentbelowmin()}" pattern="0.##"/>%</td>
                                                <td>${d.getNumberbelowmin()}</td>
                                                <td>${d.getNumbergreatermin()}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${productViewMode eq 'chart'}">
                                <div class="report-chart-area" style="background: #fff; padding: 16px; border-radius: 8px;">
                                    <canvas id="productChart"></canvas>
                                </div>
                                <%
                                    ArrayList<ProductReportModel> data = (ArrayList<ProductReportModel>) request.getAttribute("data");
                                    if (data == null) data = new ArrayList<>();

                                    StringBuilder names = new StringBuilder();
                                    StringBuilder quantity = new StringBuilder();
                                    StringBuilder totalmoney = new StringBuilder();
                                    StringBuilder percent = new StringBuilder();

                                    for (int i = 0; i < data.size(); i++) {
                                        ProductReportModel p = data.get(i);
                                        names.append("'").append(p.getName().replace("'", "\\'")).append("'");
                                        quantity.append(p.getQuantity());
                                        totalmoney.append(p.getTotalmoney());
                                        percent.append(p.getPercentbelowmin());

                                        if (i < data.size() - 1) {
                                            names.append(", ");
                                            quantity.append(", ");
                                            totalmoney.append(", ");
                                            percent.append(", ");
                                        }
                                    }
                                %>


                                <script>
                                    const ctx = document.getElementById('productChart').getContext('2d');
                                    const chart = new Chart(ctx, {
                                        data: {
                                            labels: [<%= names.toString() %>],
                                            datasets: [
                                                {
                                                    type: 'bar',
                                                    label: 'Số lượng',
                                                    data: [<%= quantity.toString() %>],
                                                    backgroundColor: '#0d6efd',
                                                    yAxisID: 'y',
                                                    order: 2
                                                },
                                                {
                                                    type: 'bar',
                                                    label: 'Tổng tiền nhập (VND)',
                                                    data: [<%= totalmoney.toString() %>],
                                                    backgroundColor: '#198754',
                                                    yAxisID: 'y1',
                                                    order: 2
                                                },
                                                {
                                                    type: 'line',
                                                    label: '% dưới mức min',
                                                    data: [<%= percent.toString() %>],
                                                    borderColor: '#dc3545',
                                                    backgroundColor: '#dc354555',
                                                    yAxisID: 'y2',
                                                    order: 1,
                                                    tension: 0.3,
                                                    fill: false,
                                                    pointRadius: 4
                                                }
                                            ]
                                        },
                                        options: {
                                            responsive: true,
                                            interaction: {
                                                mode: 'index',
                                                intersect: false
                                            },
                                            stacked: false,
                                            scales: {
                                                y: {
                                                    type: 'linear',
                                                    position: 'left',
                                                    title: {display: true, text: 'Số lượng'},
                                                    beginAtZero: true
                                                },
                                                y1: {
                                                    type: 'linear',
                                                    position: 'right',
                                                    title: {display: true, text: 'Tổng tiền (VND)'},
                                                    beginAtZero: true,
                                                    grid: {drawOnChartArea: false}
                                                },
                                                y2: {
                                                    type: 'linear',
                                                    display: false,
                                                    beginAtZero: true
                                                }
                                            },
                                            plugins: {
                                                tooltip: {
                                                    callbacks: {
                                                        label: function (context) {
                                                            if (context.dataset.label.includes('VND')) {
                                                                return context.dataset.label + ': ' + context.parsed.y.toLocaleString('vi-VN') + ' VND';
                                                            } else if (context.dataset.label.includes('%')) {
                                                                return context.dataset.label + ': ' + context.parsed.y + '%';
                                                            }
                                                            return context.dataset.label + ': ' + context.parsed.y;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    });
                                </script>
                            </c:if>

                        </div>
                    </div>


                </div>
            </div>
        </div>




        <script src="${pageContext.request.contextPath}/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/feather.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>
    </body>
</html>