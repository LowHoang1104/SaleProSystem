<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="16kb" autoFlush="true" %>
<%@ page errorPage="" %>
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
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=1">Product List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=2">Add Product</a></li>
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=3">Attributes List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=4">Checking Inventory</a></li>
                                </ul>
                            </li>
                            <li class="submenu">
                                <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/view/assets/img/icons/store.svg" alt="img"><span> Logistics</span> <span class="menu-arrow"></span></a>
                                <ul>
                                    <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=1">Store List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=2">Warehouse List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=4">Purchase List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=5">Supplier List</a></li>
                                    <li><a href="${pageContext.request.contextPath}/supplierreport">Report Supplier</a></li>
                                    <li><a href="${pageContext.request.contextPath}/productreport">Report Product</a></li>
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
                            <h4>Detail List</h4>
                            <h6>Manage Stock Take Detail</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addVariant" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                Add Stock Take Detail
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">                
                                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; gap: 20px;">
                                        <!-- Form tìm kiếm -->
                                        <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex; gap: 8px; align-items: center;">
                                            <input type="text" name="kw" placeholder="Search..." 
                                                   style="padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
                                            <input type="submit" name="search" value="Search" 
                                                   style="padding: 6px 12px; background-color: green; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                        </form>

                                        <!-- Chú thích icon có thể click -->
                                        <div style="display: flex; align-items: center; gap: 16px;">
                                            <a href="stocktakecontroller?id=${stkid}" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #6c757d; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/gray.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Tất cả</span>
                                            </a>
                                            <a href="stocktakecontroller?id=${stkid}&mode=searchEqual" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #28a745; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/green.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Hợp lệ</span>
                                            </a>
                                            <a href="stocktakecontroller?id=${stkid}&mode=searchNotEqual" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #dc3545; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/red.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Có sự chênh lệch</span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="wordset">
                                <ul>
                                    <li>
                                        <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img src="${pageContext.request.contextPath}/view/assets/img/icons/pdf.svg" alt="img"></a>
                                    </li>
                                    <li>
                                        <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img src="${pageContext.request.contextPath}/view/assets/img/icons/excel.svg" alt="img"></a>
                                    </li>
                                    <li>
                                        <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img src="${pageContext.request.contextPath}/view/assets/img/icons/printer.svg" alt="img"></a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table  datanew">
                                <thead>
                                    <tr>
                                        <th>
                                            <label class="checkboxs">
                                                <input type="checkbox" id="select-all">
                                                <span class="checkmarks"></span>
                                            </label>
                                        </th>
                                        <th>Stock Take Detail ID</th>
                                        <th>Product Variant</th>
                                        <th>Recorded Quantity</th>
                                        <th>Actual Quantity</th>                                           
                                        <th>Different</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sddata}" var="sd">
                                        <c:set var="actual" value="${sd.getActualQuantity()}" />
                                        <c:set var="recorded" value="${sd.recordedQuantity()}" />
                                        <c:set var="diff" value="${actual - recorded}" />
                                        <tr style="background-color: ${actual == recorded ? '#a3cfbb' : '#f5b5b0'};">
                                            <td>
                                                <label class="checkboxs">
                                                    <input type="checkbox">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </td>
                                            <td style="color: black">${sd.getStockTakeDetailID()}</td>
                                            <td style="color: black">${sd.productVarianttoString()}</td>
                                            <td style="color: black">${sd.recordedQuantity()}</td>        
                                            <td style="color: black">${sd.getActualQuantity()}</td>
                                            <td style="color: black">${diff > 0 ? '+' : ''}${diff}</td>
                                            <td style="color: black">
                                                <c:choose>
                                                    <c:when test="${diff > 0}">
                                                        Thừa hàng
                                                    </c:when>
                                                    <c:when test="${diff < 0}">
                                                        Thiếu hàng
                                                    </c:when>
                                                    <c:otherwise>
                                                        Đủ hàng
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a class="me-3" href="stocktakecontroller?id=${stkid}&mode=2">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                </a>
                                                <a class="me-3" href="stocktakecontroller?id=${stkid}&mode=3">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- Modal -->
                    <div id="variantInputModal" class="overlay" style="display: none; position: fixed; top: 0; left: 0;
                         width: 100%; height: 100%; background: rgba(0,0,0,0.4); justify-content: center; align-items: center; z-index: 9999;">

                        <div class="modal-content" style="background: white; padding: 20px; border-radius: 10px; width: 400px; position: relative;">
                            <h4 id="variantModalTitle">Select Product Variants</h4>
                            <form action="${pageContext.request.contextPath}/stocktakecontroller" method="post">
                                <div style="max-height: 300px; overflow-y: auto; margin-bottom: 16px; border: 1px solid #ccc; border-radius: 6px;">
                                    <table style="width: 100%; border-collapse: collapse;">
                                        <thead>
                                            <tr style="background-color: #f0f0f0;">
                                                <th style="padding: 8px; border: 1px solid #ccc;">Select</th>
                                                <th style="padding: 8px; border: 1px solid #ccc;">Variant</th>
                                                <th style="padding: 8px; border: 1px solid #ccc;">Actual Quantity</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pvdata}" var="pv">
                                                <tr>
                                                    <td style="text-align: center; padding: 8px; border: 1px solid #ccc;">
                                                        <input type="checkbox" name="variantIds" value="${pv.getId()}">
                                                    </td>
                                                    <td style="padding: 8px; border: 1px solid #ccc;">${pv.productVarianttoString()}</td>
                                                    <td style="padding: 8px; border: 1px solid #ccc;">
                                                        <input type="number" name="actualQuantity_${pv.getId()}" min="0" value="0"
                                                               style="width: 80px; padding: 4px;">
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <input type="hidden" name="id" value="${stkid}">

                                <div style="display: flex; justify-content: center; gap: 10px; margin-top: 16px;">
                                    <button type="submit" name="add"
                                            style="padding: 8px 16px; background-color: #28a745; color: white;
                                            border: none; border-radius: 4px; cursor: pointer;">
                                        Save Selected
                                    </button>
                                    <button type="button" onclick="closeVariantModal()"
                                            style="padding: 8px 16px; background-color: #6c757d; color: white;
                                            border: none; border-radius: 4px; cursor: pointer;">
                                        Cancel
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        document.getElementById("addVariant").addEventListener("click", function (event) {
            event.preventDefault();
            document.getElementById("variantInputModal").style.display = "flex";
        });

        function closeVariantModal() {
            document.getElementById("variantInputModal").style.display = "none";
        }

        // Đóng nếu click bên ngoài
        window.addEventListener("click", function (e) {
            const modal = document.getElementById("variantInputModal");
            if (e.target === modal) {
                closeVariantModal();
            }
        });
    </script>
    <c:if test="${not empty err}">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const modal = document.getElementById("variantInputModal");
                if (modal) {
                    modal.style.display = "flex";
                    document.body.classList.add("modal-open");

                    // Tạo phần tử hiển thị lỗi
                    const errorMsg = document.createElement("div");
                    errorMsg.textContent = "${err}";
                    errorMsg.style.color = "red";
                    errorMsg.style.marginTop = "8px";

                    // Thêm lỗi vào ngay sau thẻ h4
                    const title = modal.querySelector("#variantModalTitle");
                    if (title) {
                        title.insertAdjacentElement("afterend", errorMsg);
                    }
                }
            });
        </script>
    </c:if>
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