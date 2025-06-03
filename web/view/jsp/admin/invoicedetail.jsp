<!DOCTYPE html>
<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Dreams Pos admin template</title>

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">

            <div class="header">

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
                                        <span><img src="<%=path%>/view/assets/img/icons/closes.svg" alt="img"></span>
                                    </div>
                                </div>
                                <a class="btn" id="searchdiv"><img src="<%=path%>/view/assets/img/icons/search.svg" alt="img"></a>
                            </form>
                        </div>
                    </li>


                    <li class="nav-item dropdown has-arrow flag-nav">
                        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="javascript:void(0);" role="button">
                            <img src="<%=path%>/view/assets/img/flags/us1.png" alt="" height="20">
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="<%=path%>/view/assets/img/flags/us.png" alt="" height="16"> English
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="<%=path%>/view/assets/img/flags/fr.png" alt="" height="16"> French
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="<%=path%>/view/assets/img/flags/es.png" alt="" height="16"> Spanish
                            </a>
                            <a href="javascript:void(0);" class="dropdown-item">
                                <img src="<%=path%>/view/assets/img/flags/de.png" alt="" height="16"> German
                            </a>
                        </div>
                    </li>


                    <li class="nav-item dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                            <img src="<%=path%>/view/assets/img/icons/notification-bing.svg" alt="img"> <span class="badge rounded-pill">4</span>
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
                                                    <img alt="" src="<%=path%>/view/assets/img/profiles/avatar-02.jpg">
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
                                                    <img alt="" src="<%=path%>/view/assets/img/profiles/avatar-03.jpg">
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
                                                    <img alt="" src="<%=path%>/view/assets/img/profiles/avatar-06.jpg">
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
                                                    <img alt="" src="<%=path%>/view/assets/img/profiles/avatar-17.jpg">
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
                                                    <img alt="" src="<%=path%>/view/assets/img/profiles/avatar-13.jpg">
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
                            <span class="user-img"><img src="<%=path%>/view/assets/img/profiles/avator1.jpg" alt="">
                                <span class="status online"></span></span>
                        </a>
                        <div class="dropdown-menu menu-drop-user">
                            <div class="profilename">
                                <div class="profileset">
                                    <span class="user-img"><img src="<%=path%>/view/assets/img/profiles/avator1.jpg" alt="">
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
                                <a class="dropdown-item logout pb-0" href="signin.html"><img src="<%=path%>/view/assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
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
                                <a href="index.html"><img src="<%=path%>/view/assets/img/icons/dashboard.svg" alt="img"><span> Dashboard</span> </a>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="productlist.html">Product List</a></li>
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
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/sales1.svg" alt="img"><span> Sales</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="saleslist.html" class="active">Sales List</a></li>
                                    <li><a href="pos.html">POS</a></li>
                                    <li><a href="pos.html">New Sales</a></li>
                                    <li><a href="salesreturnlist.html">Sales Return List</a></li>
                                    <li><a href="createsalesreturns.html">New Sales Return</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/purchase1.svg" alt="img"><span> Purchase</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="purchaselist.html">Purchase List</a></li>
                                    <li><a href="addpurchase.html">Add Purchase</a></li>
                                    <li><a href="importpurchase.html">Import Purchase</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/expense1.svg" alt="img"><span> Expense</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="expenselist.html">Expense List</a></li>
                                    <li><a href="createexpense.html">Add Expense</a></li>
                                    <li><a href="expensecategory.html">Expense Category</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/quotation1.svg" alt="img"><span> Quotation</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="quotationList.html">Quotation List</a></li>
                                    <li><a href="addquotation.html">Add Quotation</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/transfer1.svg" alt="img"><span> Transfer</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="transferlist.html">Transfer List</a></li>
                                    <li><a href="addtransfer.html">Add Transfer </a></li>
                                    <li><a href="importtransfer.html">Import Transfer </a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/return1.svg" alt="img"><span> Return</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="salesreturnlist.html">Sales Return List</a></li>
                                    <li><a href="createsalesreturn.html">Add Sales Return </a></li>
                                    <li><a href="purchasereturnlist.html">Purchase Return List</a></li>
                                    <li><a href="createpurchasereturn.html">Add Purchase Return </a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span> People</span> <span class="menu-arrow"></span></a>
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
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/places.svg" alt="img"><span> Places</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="newcountry.html">New Country</a></li>
                                    <li><a href="countrieslist.html">Countries list</a></li>
                                    <li><a href="newstate.html">New State </a></li>
                                    <li><a href="statelist.html">State list</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/time.svg" alt="img"><span> Report</span> <span class="menu-arrow"></span></a>
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
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span> Users</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="newuser.html">New User </a></li>
                                    <li><a href="userlist.html">Users List</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/settings.svg" alt="img"><span> Settings</span> <span class="menu-arrow"></span></a>
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

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Invoice Details</h4>
                            <h6>View invoice details</h6>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <div class="card-sales-split">
                                <h2>Invoice Detail : #${invoiceID}</h2>   
                            </div>
                            <div class="invoice-box table-height" style="max-width: 1600px;width:100%;overflow: auto;margin:15px auto;padding: 0;font-size: 14px;line-height: 24px;color: #555;">
                                <table cellpadding="0" cellspacing="0" style="width: 100%;line-height: inherit;text-align: left;">
                                    <tbody><tr class="top">
                                            <td colspan="6" style="padding: 5px;vertical-align: top;">
                                                <table style="width: 100%;line-height: inherit;text-align: left;">
                                                    <tbody><tr>
                                                            <td style="padding:5px;vertical-align:top;text-align:left;padding-bottom:20px">
                                                                <font style="vertical-align: inherit;margin-bottom:25px;"><font style="vertical-align: inherit;font-size:14px;color:#7367F0;font-weight:600;line-height: 35px; ">#${customer.getRank()} Customer Info </font></font><br>
                                                                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;font-size: 14px;color:#000;font-weight: 400;">${customer.getFullName()}</font></font><br>
                                                                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;font-size: 14px;color:#000;font-weight: 400;">${customer.getPhone()}</font></font><br>
                                                                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;font-size: 14px;color:#000;font-weight: 400;">${customer.getEmail()}</font></font><br>
                                                                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;font-size: 14px;color:#000;font-weight: 400;">${customer.getGender()}</font></font><br>
                                                                <font style="vertical-align: inherit;"><font style="vertical-align: inherit;font-size: 14px;color:#000;font-weight: 400;">${customer.getAddress()}</font></font><br>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                            </td>
                                        </tr>
                                        <tr class="heading " style="background: #F3F2F7;">
                                            <td style="padding: 5px;vertical-align: middle;font-weight: 600;color: #5E5873;font-size: 14px;padding: 10px; ">
                                                Product Name
                                            </td>
                                            <td style="padding: 5px;vertical-align: middle;font-weight: 600;color: #5E5873;font-size: 14px;padding: 10px; ">
                                                QTY
                                            </td>
                                            <td style="padding: 5px;vertical-align: middle;font-weight: 600;color: #5E5873;font-size: 14px;padding: 10px; ">
                                                Price
                                            </td>
                                            <td style="padding: 5px;vertical-align: middle;font-weight: 600;color: #5E5873;font-size: 14px;padding: 10px; ">
                                                Discount
                                            </td>                                           
                                            <td style="padding: 5px;vertical-align: middle;font-weight: 600;color: #5E5873;font-size: 14px;padding: 10px; ">
                                                Subtotal
                                            </td>
                                        </tr>
                                        <c:forEach items="${data}" var="a" >
                                            <tr class="details" style="border-bottom:1px solid #E9ECEF ;">
                                                <td style="padding: 10px;vertical-align: top; display: flex;align-items: center;">
                                                    <img src="<%=path%>/view/assets/img/product/product1.jpg" alt="img" class="me-2" style="width:40px;height:40px;">
                                                    ${a.getNameProductNameByID()}
                                                </td>
                                                <td style="padding: 10px;vertical-align: top; ">
                                                    ${a.getQuantity()}
                                                </td>
                                                <td style="padding: 10px;vertical-align: top; ">
                                                    ${a.getUnitPrice()}
                                                </td>
                                                <td style="padding: 10px;vertical-align: top; ">
                                                    ${a.getDiscountPercent()}
                                                </td>                                              
                                                <td style="padding: 10px;vertical-align: top; ">
                                                    ${a.getUnitPrice()}
                                                </td
                                            </tr>                         
                                        </c:forEach>
                                    </tbody></table>
                            </div>
                            <!--                            <div class="row">
                                                            <div class="col-lg-3 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>Order Tax</label>
                                                                    <input type="text">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>Discount</label>
                                                                    <input type="text">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>Shipping</label>
                                                                    <input type="text">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-3 col-sm-6 col-12">
                                                                <div class="form-group">
                                                                    <label>Status</label>
                                                                    <select class="select">
                                                                        <option>Choose Status</option>
                                                                        <option>Completed</option>
                                                                        <option>Inprogress</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-lg-6 ">
                                                                    <div class="total-order w-100 max-widthauto m-auto mb-4">
                                                                        <ul>
                                                                            <li>
                                                                                <h4>Order Tax</h4>
                                                                                <h5>$ 0.00 (0.00%)</h5>
                                                                            </li>
                                                                            <li>
                                                                                <h4>Discount	</h4>
                                                                                <h5>$ 0.00</h5>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <div class="col-lg-6 ">
                                                                    <div class="total-order w-100 max-widthauto m-auto mb-4">
                                                                        <ul>
                                                                            <li>
                                                                                <h4>Shipping</h4>
                                                                                <h5>$ 0.00</h5>
                                                                            </li>
                                                                            <li class="total">
                                                                                <h4>Grand Total</h4>
                                                                                <h5>$ 0.00</h5>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <a href="javascript:void(0);" class="btn btn-submit me-2">Update</a>
                                                                <a href="javascript:void(0);" class="btn btn-cancel">Cancel</a>
                                                            </div>
                                                        </div>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script data-cfasync="false" src="../../cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script><script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="<%=path%>/view/assets/js/feather.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
        <script>
            function deleteProduct(id) {
                if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?')) {
                    // AJAX hoặc chuyển trang để xóa - code thực tế tuỳ backend
                    window.location.href = 'deleteproduct?id=' + id;
                }
            }
        </script>
        <script src="<%=path%>/view/assets/js/script.js"></script>
    </body>
</html>
