<%-- 
    Document   : Add_EmployeeType
    Created on : May 26, 2025, 4:17:29 PM
    Author     : Thinhnt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="Inventory Management Admin Dashboard">
    <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="PureCode AI">
    <meta name="robots" content="noindex, nofollow">
    <title>Phân Quyền Nhóm - Admin</title>

    <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.png">

    <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="view/assets/css/animate.css">
    <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="view/assets/css/style.css">
</head>
<body>
    <div id="global-loader">
        <div class="whirly-loader"> </div>
    </div>

    <div class="main-wrapper">

        <div class="header">
            <!-- Header Content (Copied from a typical page like productlist.html) -->
            <div class="header-left active">
                <a href="index.html" class="logo">
                    <img src="view/assets/img/logo.png" alt="">
                </a>
                <a href="index.html" class="logo-small">
                    <img src="view/assets/img/logo-small.png" alt="">
                </a>
                <a id="toggle_btn" href="javascript:void(0);"></a>
            </div>
            <a id="mobile_btn" class="mobile_btn" href="#sidebar">
                <span class="bar-icon">
                    <span></span>
                    <span></span>
                    <span></span>
                </span>
            </a>
            <ul class="nav user-menu">
                <li class="nav-item dropdown has-arrow main-drop">
                    <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                        <span class="user-img"><img src="view/assets/img/profiles/avator1.jpg" alt="">
                            <span class="status online"></span></span>
                    </a>
                    <div class="dropdown-menu menu-drop-user">
                        <div class="profilename">
                            <div class="profileset">
                                <span class="user-img"><img src="view/assets/img/profiles/avator1.jpg" alt="">
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
                            <a class="dropdown-item logout pb-0" href="signin.html"><img src="view/assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
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
            <!-- Sidebar Content (Copied from a typical page) -->
            <div class="sidebar-inner slimscroll">
                <div id="sidebar-menu" class="sidebar-menu">
                    <ul>
                        <li><a href="index.html"><img src="view/assets/img/icons/dashboard.svg" alt="img"><span>Dashboard</span> </a></li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="productlist.html">Product List</a></li>
                                <li><a href="addproduct.html">Add Product</a></li>
                                <li><a href="categorylist.html">Category List</a></li>
                                <!-- ... other product links -->
                            </ul>
                        </li>
                         <!-- ... other main menu items ... -->
                        <li class="submenu">
                            <a href="javascript:void(0);" class="active subdrop"><img src="view/assets/img/icons/users1.svg" alt="img"><span> Users</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="newuser.html">New User </a></li>
                                <li><a href="userlists.html">Users List</a></li>
                                <li><a href="grouppermissions.html" class="active">Group Permissions</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="view/assets/img/icons/settings.svg" alt="img"><span> Settings</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="generalsettings.html">General Settings</a></li>
                                <!-- ... other settings links -->
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="page-wrapper">
            <div class="content">
                <div class="page-header">
                    <div class="page-title">
                        <h4>Phân Quyền Nhóm</h4>
                        <h6>Quản lý quyền hạn cho các nhóm người dùng</h6>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Chọn Vai Trò</label>
                                    <select class="select">
                                        <option>Nhân viên bán hàng</option>
                                        <option>Nhân viên kho</option>
                                        <option>Nhân viên thu ngân</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <form id="permissionForm">
                    <div class="row">
                        <!-- Hệ thống -->
                        <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Hệ Thống</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="system_settings" id="permSystemSettings">
                                                <label class="form-check-label" for="permSystemSettings">Thiết lập chung</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="system_print_templates" id="permPrintTemplates">
                                                <label class="form-check-label" for="permPrintTemplates">Mẫu in</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="system_users" id="permSystemUsers">
                                                <label class="form-check-label" for="permSystemUsers">Quản lý Người dùng</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="system_branches" id="permSystemBranches">
                                                <label class="form-check-label" for="permSystemBranches">Quản lý Chi nhánh</label>
                                            </div>
                                        </li>
                                         <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="system_action_history" id="permActionHistory">
                                                <label class="form-check-label" for="permActionHistory">Lịch sử thao tác</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Hàng hóa -->
                        <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Hàng Hóa</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="goods_catalog" id="permGoodsCatalog">
                                                <label class="form-check-label" for="permGoodsCatalog">Danh mục sản phẩm</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="goods_price_settings" id="permPriceSettings">
                                                <label class="form-check-label" for="permPriceSettings">Thiết lập giá</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="goods_inventory_check" id="permInventoryCheck">
                                                <label class="form-check-label" for="permInventoryCheck">Kiểm kho</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="goods_production" id="permProduction">
                                                <label class="form-check-label" for="permProduction">Sản xuất</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Giao dịch -->
                        <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Giao Dịch</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="transaction_place_order" id="permPlaceOrder">
                                                <label class="form-check-label" for="permPlaceOrder">Đặt hàng</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="transaction_invoice" id="permInvoice">
                                                <label class="form-check-label" for="permInvoice">Hóa Đơn</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="transaction_returns" id="permReturns">
                                                <label class="form-check-label" for="permReturns">Trả hàng</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="transaction_purchase_order" id="permPurchaseOrder">
                                                <label class="form-check-label" for="permPurchaseOrder">Đặt hàng nhập</label>
                                            </div>
                                        </li>
                                         <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="transaction_receive_goods" id="permReceiveGoods">
                                                <label class="form-check-label" for="permReceiveGoods">Nhập hàng</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Đối tác -->
                         <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Đối Tác</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="partner_customers" id="permCustomers">
                                                <label class="form-check-label" for="permCustomers">Khách hàng</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="partner_suppliers" id="permSuppliers">
                                                <label class="form-check-label" for="permSuppliers">Nhà cung cấp</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="partner_delivery" id="permDeliveryPartners">
                                                <label class="form-check-label" for="permDeliveryPartners">Đối tác giao hàng</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Báo cáo -->
                        <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Báo Cáo</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="report_sales" id="permReportSales">
                                                <label class="form-check-label" for="permReportSales">Báo cáo Bán hàng</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="report_inventory" id="permReportInventory">
                                                <label class="form-check-label" for="permReportInventory">Báo cáo Hàng hóa</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="report_financial" id="permReportFinancial">
                                                <label class="form-check-label" for="permReportFinancial">Báo cáo Tài chính</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Sổ quỹ -->
                        <div class="col-lg-4 col-md-6">
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title">Sổ Quỹ</h5>
                                </div>
                                <div class="card-body">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="cashbook_main" id="permCashbookMain">
                                                <label class="form-check-label" for="permCashbookMain">Quản lý Sổ quỹ</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" value="cashbook_bank_accounts" id="permBankAccounts">
                                                <label class="form-check-label" for="permBankAccounts">Tài khoản ngân hàng</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        
                    </div>

                    <div class="row mt-3">
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-submit me-2">Lưu Thay Đổi</button>
                            <button type="button" class="btn btn-cancel">Hủy Bỏ</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="view/assets/js/jquery-3.6.0.min.js"></script>
    <script src="view/assets/js/feather.min.js"></script>
    <script src="view/assets/js/jquery.slimscroll.min.js"></script>
    <script src="view/assets/js/jquery.dataTables.min.js"></script>
    <script src="view/assets/js/dataTables.bootstrap4.min.js"></script>
    <script src="view/assets/js/bootstrap.bundle.min.js"></script>
    <script src="view/assets/plugins/select2/js/select2.min.js"></script>
    <script src="view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
    <script src="view/assets/js/script.js"></script>
</body>
</html>
