<%-- 
    Document   : product_detail.jsp
    Created on : May 24, 2025, 9:20:08 PM
    Author     : tungd
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/plugins/owlcarousel/owl.carousel.min.css">

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
                            <h4>Product Details</h4>
                            <h6>Full details of a product</h6>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="bar-code-view">
                                        <img src="${pageContext.request.contextPath}/view/assets/img/barcode1.png" alt="barcode">
                                        <a class="printimg">
                                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/printer.svg" alt="print">
                                        </a>
                                    </div>
                                    <div class="productdetails">
                                        <ul class="product-bar">
                                            <li>
                                                <h4>Product ID</h4>
                                                <h6>${p.code}</h6>
                                            </li>
                                            <li>
                                                <h4>Product Name</h4>
                                                <h6>${p.name}</h6>
                                            </li>
                                            <li>
                                                <h4>Category</h4>
                                                <h6>${p.getCategoryNameById()}</h6>
                                            </li>
                                            <li>
                                                <h4>Type</h4>
                                                <h6>${p.getTypeNameById()}</h6>
                                            </li>
                                            <li>
                                                <h4>Price</h4>
                                                <h6>${p.price}</h6>
                                            </li>
                                            <li>
                                                <h4>Cost Price</h4>
                                                <h6>${p.costPrice}</h6>
                                            </li>
                                            <li>
                                                <h4>Status</h4>
                                                <h6>${p.status}</h6>
                                            </li>
                                            <li>
                                                <h4>Release Date</h4>
                                                <h6>${p.releaseDate}</h6>
                                            </li>
                                            <li>
                                                <h4>Description</h4>
                                                <h6>${p.description}</h6>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="slider-product-details">
                                        <div class="owl-carousel owl-theme product-slide">
                                            <div class="slider-product">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/product/product69.jpg" alt="img">
                                                <h4>Product Image</h4>
                                            </div>
                                            <div class="slider-product">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/product/product69.jpg" alt="img">
                                                <h4>Product Image</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Product Variants List</h4>
                                <h6>Manage your products</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#" id="addVariant" class="btn btn-added">
                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                    Add Product Variant
                                </a>
                            </div>
                        </div>
                        <table class="table datanew" id="productVariantTable">
                            <thead>
                                <tr>
                                    <th>
                                        <label class="checkboxs">
                                            <input type="checkbox" id="select-all">
                                            <span class="checkmarks"></span>
                                        </label>
                                    </th>
                                    <th>ProductVariant ID</th>
                                    <th>Size</th>
                                    <th>Color</th>
                                    <th>SKU</th>
                                    <th>Unit</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <c:forEach items="${pvdata}" var="pv">
                                <tbody>
                                    <tr>
                                        <td>
                                            <label class="checkboxs">
                                                <input type="checkbox">
                                                <span class="checkmarks"></span>
                                            </label>
                                        </td>
                                        <td>${pv.getId()}</td>
                                        <td>${pv.getSizenameByID()}</td>
                                        <td>${pv.getColornameByID()}</td>
                                        <td>${pv.getSku()}</td>
                                        <td>${pv.getUnit()}</td>
                                        <td>
                                            <a class="me-3" href="productvariantcontroller">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                            </a>
                                            <a class="me-3 confirm-text" href="javascript:void(0);">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                        <!-- Modal -->
                        <div id="variantInputModal" class="overlay" style="display: none; position: fixed; top: 0; left: 0;
                             width: 100%; height: 100%; background: rgba(0,0,0,0.4); justify-content: center; align-items: center; z-index: 9999;">

                            <div class="modal-content" style="background: white; padding: 20px; border-radius: 10px; width: 500px; position: relative;">
                                <h4 id="variantModalTitle">Add Product Variant</h4>
                                <form action="${pageContext.request.contextPath}/productvariantcontroller" method="post">                                
                                    <div class="form-group">
                                        <label>Size</label>
                                        <select name="size" class="form-control" required>
                                            <c:forEach items="${sdata}" var="s">
                                                <option value="${s.getSizeID()}">${s.getSizeName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Color</label>
                                        <select name="color" class="form-control" required>
                                            <c:forEach items="${cldata}" var="cl">
                                                <option value="${cl.getColorID()}">${cl.getColorName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Unit</label>
                                        <input type="text" name="unit" class="form-control" >
                                    </div>
                                    <div class="form-group">
                                        <label>Average Quantity</label>
                                        <input type="number" name="averageQuantity" class="form-control" value="50" min="0" required="">
                                    </div>
                                    <input type="hidden" name="productCode" value="${p.code}">
                                    <div class="text-end mt-3">
                                        <button type="submit" name="add" class="btn btn-primary">Save</button>
                                        <button type="button" class="btn btn-secondary" onclick="closeVariantModal()">Cancel</button>
                                    </div>
                                </form>
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

            <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/plugins/owlcarousel/owl.carousel.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
            <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>


            <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
            <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
    </body>
</html>
