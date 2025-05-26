<%-- 
    Document   : Product_chart_rp
    Created on : May 24, 2025, 11:25:01 PM
    Author     : admin
--%>


<!DOCTYPE html>
<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
    </head>
    <!--    <body>
            <h1>Hello World!</h1>
        </body>-->
    <body>
        <!-- HEADER: Bê NGUYÊN từ index.html -->
        <div class="header">
            <!-- Thay phần này bằng code header thực tế của bạn trong index.html
                 Ví dụ: logo, menu user, thông báo, ... -->
            <!--            <div class="header-left">
                            <a href="index.html" class="logo">
                                <img src="<%=path%>/view/assets/img/logo.png" width="35" height="35" alt=""> 
                            </a>
                        </div>-->
            <div class="header-left active">
                <a href="index.html" class="logo">
                    <img src="<%=path%>/view/assets/img/logo.png" alt="">
                </a>
                <a href="index.html" class="logo-small">
                    <img src="<%=path%>/view/assets/img/logo-small.png" alt="">
                </a>
                <a id="toggle_btn" href="javascript:void(0);">
                </a>
            </div>
            <!--            <a id="toggle_btn" href="javascript:void(0);"><i class="fas fa-bars"></i></a>-->
            <!-- ... Phần menu thông tin user, bell, v.v... -->
        </div>

        

        <div class="sidebar" id="sidebar">
            <div class="sidebar-inner slimscroll">
                <div id="sidebar-menu" class="sidebar-menu">
                    <ul>
                        <li>
                            <a href="index.html"><img src="<%=path%>/view/assets/img/icons/dashboard.svg" alt="img"><span>
                                    Dashboard</span> </a>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/product.svg" alt="img"><span>
                                    Product</span> 
                                <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="productlist.html" class="active">Product List</a></li>
                                <li><a href="addproduct.html">Add Product</a></li>
                                <li><a href="categorylist.html">Category List</a></li>
                                <li><a href="addcategory.html">Add Category</a></li>
                                <li><a href="subcategorylist.html">Sub Category List</a></li>
                                <li><a href="subaddcategory.html">Add Sub Category</a></li>
                                <li><a href="brandlist.html">Brand List</a></li>
                                <li><a href="addbrand.html">Add Brand</a></li>
                                <li><a href="importproduct.html">Import Products</a></li>
                                <li><a href="barcode.html">Print Barcode</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/sales1.svg" alt="img"><span>
                                    Sales</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="saleslist.html">Sales List</a></li>
                                <li><a href="pos.html">POS</a></li>
                                <li><a href="pos.html">New Sales</a></li>
                                <li><a href="salesreturnlists.html">Sales Return List</a></li>
                                <li><a href="createsalesreturns.html">New Sales Return</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/purchase1.svg" alt="img"><span>
                                    Purchase</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="purchaselist.html">Purchase List</a></li>
                                <li><a href="addpurchase.html">Add Purchase</a></li>
                                <li><a href="importpurchase.html">Import Purchase</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/expense1.svg" alt="img"><span>
                                    Expense</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="expenselist.html">Expense List</a></li>
                                <li><a href="createexpense.html">Add Expense</a></li>
                                <li><a href="expensecategory.html">Expense Category</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/quotation1.svg" alt="img"><span>
                                    Quotation</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="quotationList.html">Quotation List</a></li>
                                <li><a href="addquotation.html">Add Quotation</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/transfer1.svg" alt="img"><span>
                                    Transfer</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="transferlist.html">Transfer List</a></li>
                                <li><a href="addtransfer.html">Add Transfer </a></li>
                                <li><a href="importtransfer.html">Import Transfer </a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/return1.svg" alt="img"><span>
                                    Return</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="salesreturnlist.html">Sales Return List</a></li>
                                <li><a href="createsalesreturn.html">Add Sales Return </a></li>
                                <li><a href="purchasereturnlist.html">Purchase Return List</a></li>
                                <li><a href="createpurchasereturn.html">Add Purchase Return </a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="components.html"><i data-feather="layers"></i><span> Components</span> </a>
                        </li>
                        <li>
                            <a href="blankpage.html"><i data-feather="file"></i><span> Blank Page</span> </a>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="alert-octagon"></i> <span> Error Pages
                                </span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="error-404.html">404 Error </a></li>
                                <li><a href="error-500.html">500 Error </a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="box"></i> <span>Elements </span> <span
                                    class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="sweetalerts.html">Sweet Alerts</a></li>
                                <li><a href="tooltip.html">Tooltip</a></li>
                                <li><a href="popover.html">Popover</a></li>
                                <li><a href="ribbon.html">Ribbon</a></li>
                                <li><a href="clipboard.html">Clipboard</a></li>
                                <li><a href="drag-drop.html">Drag & Drop</a></li>
                                <li><a href="rangeslider.html">Range Slider</a></li>
                                <li><a href="rating.html">Rating</a></li>
                                <li><a href="toastr.html">Toastr</a></li>
                                <li><a href="text-editor.html">Text Editor</a></li>
                                <li><a href="counter.html">Counter</a></li>
                                <li><a href="scrollbar.html">Scrollbar</a></li>
                                <li><a href="spinner.html">Spinner</a></li>
                                <li><a href="notification.html">Notification</a></li>
                                <li><a href="lightbox.html">Lightbox</a></li>
                                <li><a href="stickynote.html">Sticky Note</a></li>
                                <li><a href="timeline.html">Timeline</a></li>
                                <li><a href="form-wizard.html">Form Wizard</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="bar-chart-2"></i> <span> Charts </span> <span
                                    class="menu-arrow"></span></a>
                            <ul>
<!--                                <li><a href="chart-apex.html">Apex Charts</a></li>-->
                                <li><a href="Product_chart_rp.jsp">Chart Js</a></li>
                                <!--<li><a href="chart-morris.html">Morris Charts</a></li>-->
                                <!--<li><a href="chart-flot.html">Flot Charts</a></li>-->
                                <!--<li><a href="chart-peity.html">Peity Charts</a></li>-->
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="award"></i><span> Icons </span> <span
                                    class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="icon-fontawesome.html">Fontawesome Icons</a></li>
                                <li><a href="icon-feather.html">Feather Icons</a></li>
                                <li><a href="icon-ionic.html">Ionic Icons</a></li>
                                <li><a href="icon-material.html">Material Icons</a></li>
                                <li><a href="icon-pe7.html">Pe7 Icons</a></li>
                                <li><a href="icon-simpleline.html">Simpleline Icons</a></li>
                                <li><a href="icon-themify.html">Themify Icons</a></li>
                                <li><a href="icon-weather.html">Weather Icons</a></li>
                                <li><a href="icon-typicon.html">Typicon Icons</a></li>
                                <li><a href="icon-flag.html">Flag Icons</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="columns"></i> <span> Forms </span> <span
                                    class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="form-basic-inputs.html">Basic Inputs </a></li>
                                <li><a href="form-input-groups.html">Input Groups </a></li>
                                <li><a href="form-horizontal.html">Horizontal Form </a></li>
                                <li><a href="form-vertical.html"> Vertical Form </a></li>
                                <li><a href="form-mask.html">Form Mask </a></li>
                                <li><a href="form-validation.html">Form Validation </a></li>
                                <li><a href="form-select2.html">Form Select2 </a></li>
                                <li><a href="form-fileupload.html">File Upload </a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><i data-feather="layout"></i> <span> Table </span> <span
                                    class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="tables-basic.html">Basic Tables </a></li>
                                <li><a href="data-tables.html">Data Table </a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/product.svg" alt="img"><span>
                                    Application</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="chat.html">Chat</a></li>
                                <li><a href="calendar.html">Calendar</a></li>
                                <li><a href="email.html">Email</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span>
                                    People</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="customerlist.html">Customer List</a></li>
                                <li><a href="addcustomer.html">Add Customer </a></li>
                                <li><a href="supplierlist.html">Supplier List</a></li>
                                <li><a href="addsupplier.html">Add Supplier </a></li>
                                <li><a href="userlist.html">User List</a></li>
                                <li><a href="adduser.html">Add User</a></li>
                                <li><a href="storelist.html">Store List</a></li>
                                <li><a href="addstore.html">Add Store</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/places.svg" alt="img"><span>
                                    Places</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="newcountry.html">New Country</a></li>
                                <li><a href="countrieslist.html">Countries list</a></li>
                                <li><a href="newstate.html">New State </a></li>
                                <li><a href="statelist.html">State list</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/time.svg" alt="img"><span>
                                    Report</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="purchaseorderreport.html">Purchase order report</a></li>
                                <li><a href="inventoryreport.html">Inventory Report</a></li>
                                <li><a href="salesreport.html">Sales Report</a></li>
                                <li><a href="invoicereport.html">Invoice Report</a></li>
                                <li><a href="purchasereport.html">Purchase Report</a></li>
                                <li><a href="supplierreport.html">Supplier Report</a></li>
                                <li><a href="customerreport.html">Customer Report</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span>
                                    Users</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="newuser.html">New User </a></li>
                                <li><a href="userlists.html">Users List</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/settings.svg" alt="img"><span>
                                    Settings</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="generalsettings.html">General Settings</a></li>
                                <li><a href="emailsettings.html">Email Settings</a></li>
                                <li><a href="paymentsettings.html">Payment Settings</a></li>
                                <li><a href="currencysettings.html">Currency Settings</a></li>
                                <li><a href="grouppermissions.html">Group Permissions</a></li>
                                <li><a href="taxrates.html">Tax Rates</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- MAIN WRAPPER, Giữ như index.html -->
        <div class="main-wrapper">
            <div class="page-wrapper">
                <!-- === ONLY THAY ĐOẠN DƯỚI === -->
                <div class="content container-fluid">

                    <!-- Filter -->
                    <div class="row filter-row">
                        <div class="col-md-3">
                            <label for="monthFilter">Xem theo:</label>
                            <select class="form-control" id="monthFilter" style="width: 100%;">
                                <option value="all">12 tháng gần nhất</option>
                            </select>
                        </div>
                    </div>

                    <!-- Top 10 sản phẩm bán chạy -->
                    <div class="row chart-card">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5>Top 10 sản phẩm bán chạy nhất</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="top10ProductChart" height="120"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Top 10 doanh thu cao nhất -->
                    <div class="row chart-card">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header">
                                    <h5>Top 10 sản phẩm doanh thu cao nhất</h5>
                                </div>
                                <div class="card-body">
                                    <canvas id="top10RevenueChart" height="120"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- === END CONTENT === -->
            </div>
        </div>

        <!-- Script JS & Plugins như index.html -->
        <script src="<%=path%>/view/assets/plugins/chartjs/chart.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/moment/moment.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>
        <script src="<%=path%>/view/assets/js/feather.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>
        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
        <script src="<%=path%>/view/assets/js/script.js"></script>

        <script>
            // ========== Mock data đầu vào ==========
            const mockMonthlyData = (() => {
                const products = [
                    'Áo Thun', 'Quần Jean', 'Váy Maxi', 'Giày Sneaker', 'Áo Sơ Mi',
                    'Balo', 'Túi Xách', 'Mắt Kính', 'Đồng Hồ', 'Nón Lưỡi Trai',
                    'Dép Sandal', 'Áo Khoác', 'Giày Tây', 'Khăn Choàng', 'Vớ Cotton'
                ];
                const months = [];
                for (let i = 0; i < 12; i++) {
                    months.push(moment().subtract(i, 'months').format('MM/YYYY'));
                }
                return months.reverse().map(month => {
                    return {
                        month,
                        products: products.map(name => ({
                                name,
                                sold: Math.floor(Math.random() * 150 + 10),
                                revenue: Math.floor(Math.random() * 5000000 + 300000)
                            }))
                    };
                });
            })();

            $(document).ready(function () {
                mockMonthlyData.forEach(({ month }) => {
                    $('#monthFilter').append(`<option value="${month}">${month}</option>`);
                });
                $('#monthFilter').select2({minimumResultsForSearch: 7});
            });

            let top10ProductChartInstance = null;
            let top10RevenueChartInstance = null;

            function getTop10ByField(products, field) {
                const sorted = [...products].sort((a, b) => b[field] - a[field]);
                return sorted.slice(0, 10);
            }

            function drawCharts(filterMonth = 'all') {
                let filteredProducts = [];
                if (filterMonth === 'all') {
                    const agg = {};
                    mockMonthlyData.forEach(monthData => {
                        monthData.products.forEach(prod => {
                            if (!agg[prod.name]) {
                                agg[prod.name] = {name: prod.name, sold: 0, revenue: 0};
                            }
                            agg[prod.name].sold += prod.sold;
                            agg[prod.name].revenue += prod.revenue;
                        });
                    });
                    filteredProducts = Object.values(agg);
                } else {
                    const monthData = mockMonthlyData.find(m => m.month === filterMonth);
                    filteredProducts = monthData ? monthData.products : [];
                }
                const top10Sold = getTop10ByField(filteredProducts, 'sold');
                const top10Revenue = getTop10ByField(filteredProducts, 'revenue');

                if (top10ProductChartInstance)
                    top10ProductChartInstance.destroy();
                if (top10RevenueChartInstance)
                    top10RevenueChartInstance.destroy();

                const ctx1 = document.getElementById('top10ProductChart').getContext('2d');
                top10ProductChartInstance = new Chart(ctx1, {
                    type: 'bar',
                    data: {
                        labels: top10Sold.map(p => p.name),
                        datasets: [{
                                label: 'Số lượng bán',
                                data: top10Sold.map(p => p.sold),
                                backgroundColor: 'rgba(54, 162, 235, 0.6)'
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {display: false},
                            title: {display: false}
                        },
                        scales: {
                            x: {beginAtZero: true},
                            y: {beginAtZero: true}
                        }
                    }
                });

                const ctx2 = document.getElementById('top10RevenueChart').getContext('2d');
                top10RevenueChartInstance = new Chart(ctx2, {
                    type: 'bar',
                    data: {
                        labels: top10Revenue.map(p => p.name),
                        datasets: [{
                                label: 'Doanh thu (vnđ)',
                                data: top10Revenue.map(p => p.revenue),
                                backgroundColor: 'rgba(255, 99, 132, 0.6)'
                            }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {display: false},
                            title: {display: false}
                        },
                        scales: {
                            x: {beginAtZero: true},
                            y: {beginAtZero: true}
                        }
                    }
                });
            }

            $(document).ready(function () {
                drawCharts('all');
                $('#monthFilter').on('change', function () {
                    const val = $(this).val();
                    drawCharts(val);
                })
            });
        </script>
    </body>
</html>
