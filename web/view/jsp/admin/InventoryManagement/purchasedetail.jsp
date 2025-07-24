<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/logistics/purchase.css">
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
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Purchase Detail List</h4>
                            <h6>Manage your products</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addVariant" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                Add Purchase Detail
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <!--                            <div class="table-top">
                                                            <div class="search-set">                
                                                                <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex; gap: 8px; align-items: center;">
                                                                    <input type="text" name="kw" placeholder="Search..." 
                                                                           style="padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
                                                                    <input type="submit" name="search" value="Search" 
                                                                           style="padding: 6px 12px; background-color: green; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                                                </form>
                                                            </div>
                                                        </div>-->
                            <div class="wordset">
                                <ul>
                                    <li>             
                                        <form method="post" action="excelcontroller">
                                            <input type="hidden" name="id" value="${pcid}">
                                            <button data-bs-toggle="tooltip" data-bs-placement="top" title="excel" type="submit" name="type" value="purchasedetail"><img src="${pageContext.request.contextPath}/view/assets/img/icons/excel.svg" alt="img"></button>
                                        </form>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table  datanew">
                                <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>Product Variant</th>
                                        <th>Quantity</th>
                                        <th>Cost Price</th>                                           
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="counter" value="1" />
                                    <c:forEach items="${pddata}" var="pd" varStatus="stt">
                                        <tr>
                                            <td>${counter}</td>
                                            <c:set var="counter" value="${counter + 1}" />
                                            <td>${pd.productVarianttoString()}</td>
                                            <td>${pd.getQuantity()}</td>
                                            <td><fmt:formatNumber value="${pd.getCostPrice()}" pattern="#,###" /></td>
                                            <td>
                                                <button style="border: none; background: none; padding: 0; margin-right: 15px" type="button" class="btn-edit" 
                                                        data-pcid="${pcid}"
                                                        data-pvid="${pd.getProductID()}"
                                                        data-qty="${pd.getQuantity()}"
                                                        data-price="${pd.getCostPrice()}">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                </button>
                                                <form action="purchasecontroller" method="post" style="display: inline;">
                                                    <input type="hidden" name="id" value="${pcid}" />
                                                    <input type="hidden" name="productVariantID" value="${pd.getProductID()}" />

                                                    <button type="submit"
                                                            name="deleteDetail"
                                                            class="me-3"
                                                            style="border: none; background: none; padding: 0;">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="delete">
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--Modal Input-->
                    <div id="variantInputModal" class="overlay">
                        <div class="modal-content">
                            <h4 id="variantModalTitle">Select Product Variants</h4>
                            <form id="variantForm" action="${pageContext.request.contextPath}/purchasecontroller" method="post">
                                <input type="hidden" name="id" value="${pcid}">
                                <input type="hidden" name="code" value="${productcode}">
                                Input<input type="text" name="productcode" /> 
                                <input type="submit" name="searchcode" value="search">
                                
                                Size
                                <select name="size" class="form-control" >
                                    <c:forEach items="${sdata}" var="s">
                                        <option value="${s.getSizeID()}">${s.getSizeName()}</option>
                                    </c:forEach>
                                </select>
                                Color
                                <select name="color" class="form-control" >
                                    <c:forEach items="${cldata}" var="cl">
                                        <option value="${cl.getColorID()}">${cl.getColorName()}</option>
                                    </c:forEach>
                                </select>
<!--                                unit<input type="text" name="unit" />
                                averageQuantity<input type="text" name="averageQuantity" /> -->
                                <input type="submit" name="addVariant" value="add">
                                <div class="scrollable">
                                    <table>
                                        <thead>
                                            <tr style="background-color: #f0f0f0;">
                                                <th>Select</th>
                                                <th>Variant</th>
                                                <th>Quantity</th>
                                                <th>Cost Price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pvdata}" var="pv">
                                                <tr>
                                                    <td><input type="checkbox" name="variantIds" value="${pv.getId()}"></td>
                                                    <td>${pv.productVarianttoString()}</td>
                                                    <td>
                                                        <input type="number" name="quantity_${pv.getId()}" min="0" value="0" style="width: 80px;">
                                                    </td>
                                                    <td>
                                                        <div class="cost-cell">
                                                            <button type="button" class="btn btn-secondary" onclick="decrease('cost_${pv.getId()}')">-</button>
                                                            <input type="text" id="cost_${pv.getId()}" name="costPrice_${pv.getId()}" value="0" class="form-control" style="width: 100px; text-align: right;">
                                                            <button type="button" class="btn btn-secondary" onclick="increase('cost_${pv.getId()}')">+</button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <input type="hidden" name="id" value="${pcid}">
                                <div class="button-group">
                                    <button type="submit" name="addDetail" class="btn-primary">Save Selected</button>
                                    <button type="button" class="btn-cancel" onclick="closeVariantModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!--Modal Edit-->
                    <div id="editModal" class="overlay">
                        <div class="modal-content">
                            <form method="post" action="purchasecontroller">
                                <input type="hidden" name="purchaseID" id="modalPurchaseID">
                                <input type="hidden" name="productVariantID" id="modalProductVariantID">
                                <input type="hidden" name="id" value="${pcid}">

                                <div class="form-row">
                                    <div class="form-quantity">
                                        <label for="modalQuantity">Quantity</label>
                                        <input type="number" name="quantity" id="modalQuantity" min="0">
                                    </div>

                                    <div class="form-cost">
                                        <label for="modalCostPrice">Cost Price (₫)</label>
                                        <div class="cost-cell">
                                            <button type="button" class="btn btn-secondary" onclick="decrease('modalCostPrice')">-</button>
                                            <input type="text" id="modalCostPrice" name="costPrice" oninput="formatCurrencyVN(this)" class="form-control" style="flex: 1;">
                                            <button type="button" class="btn btn-secondary" onclick="increase('modalCostPrice')">+</button>
                                        </div>
                                    </div>
                                </div>

                                <div class="button-group">
                                    <button type="submit" name="editDetail" class="btn-primary">Save</button>
                                    <button type="button" class="btn-cancel" onclick="closeModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Bắt sự kiện click vào nút sửa
                document.querySelectorAll(".btn-edit").forEach(function (btn) {
                    btn.addEventListener("click", function () {
                        const rawPrice = parseFloat(btn.dataset.price || 0);
                        document.getElementById("modalPurchaseID").value = btn.dataset.pcid;
                        document.getElementById("modalProductVariantID").value = btn.dataset.pvid;
                        document.getElementById("modalQuantity").value = btn.dataset.qty;

                        document.getElementById("modalCostPrice").value = rawPrice.toLocaleString("vi-VN");
                        document.getElementById("editModal").style.display = "flex";
                    });
                });

                // Đóng modal khi click ra ngoài
                const modal = document.getElementById("editModal");
                const modalContent = modal.querySelector(".modal-content");
                modal.addEventListener("click", function (e) {
                    if (!modalContent.contains(e.target)) {
                        closeModal();
                    }
                });
            });

            function closeModal() {
                document.getElementById("editModal").style.display = "none";
            }

            // Format tiền Việt khi người dùng gõ
            function formatCurrencyVN(input) {
                let raw = input.value.replace(/[^\d]/g, ""); // Loại bỏ mọi ký tự không phải số
                if (raw === "") {
                    input.value = "";
                    return;
                }
                let number = parseInt(raw);
                input.value = number.toLocaleString("vi-VN");
            }
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("variantForm");
                if (!form) {
                    console.warn("Form not found!");
                    return;
                }

                form.addEventListener("submit", function (e) {
                    const formData = new FormData(form);
                    console.log("---- Form Data ----");
                    for (let [key, value] of formData.entries()) {
                        console.log(`${key}: ${value}`);
                                    }

                                    const variants = formData.getAll("variantIds");
                                    console.log("Selected variantIds:", variants);
                                });
                            });
        </script>

        <script>
            // --- Định dạng và bỏ định dạng số ---
            function formatNumber(num) {
                return num.toLocaleString("en-US");
            }

            function unformatNumber(str) {
                if (!str)
                    return 0;
                return parseFloat(str.replace(/,/g, '')) || 0;
            }

            // --- Setup khi người dùng nhập ---
            function setupNumericInput(id) {
                const input = document.getElementById(id);
                if (!input)
                    return;

                input.addEventListener("input", function () {
                    const value = unformatNumber(this.value);
                    this.value = formatNumber(value);
                });
            }
        </script>

        <%-- Sinh JavaScript gọi setupNumericInput() cho từng input cost_... --%>
        <% 
            List<salepro.models.ProductVariants> pvdata = (List<salepro.models.ProductVariants>) request.getAttribute("pvdata");
            for (salepro.models.ProductVariants pv : pvdata) {
        %>
        <script>
            setupNumericInput("cost_<%= pv.getId() %>");
        </script>
        <% } %>

        <script>
            // --- Format lại dữ liệu costPrice trước khi submit ---
            document.getElementById("variantForm").addEventListener("submit", function () {
                document.querySelectorAll("input[name^='costPrice_']").forEach(input => {
                    input.value = unformatNumber(input.value);
                });
                console.log("Submit form with costPrice cleaned.");
            });

            // --- Mở modal ---
            document.getElementById("addVariant").addEventListener("click", function (event) {
                event.preventDefault();
                document.getElementById("variantInputModal").style.display = "flex";
            });

            function closeVariantModal() {
                document.getElementById("variantInputModal").style.display = "none";
            }

            // --- Đóng modal khi click ra ngoài ---
            window.addEventListener("click", function (e) {
                const modal = document.getElementById("variantInputModal");
                if (e.target === modal) {
                    closeVariantModal();
                }
            });
        </script>

        <c:if test="${not empty sessionScope.err}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const modal = document.getElementById("variantInputModal");
                    if (modal) {
                        modal.style.display = "flex";
                        document.body.classList.add("modal-open");

                        const errorMsg = document.createElement("div");
                        errorMsg.innerHTML = '<%= session.getAttribute("err").toString().replaceAll("'", "\\\\'").replaceAll("\n", "<br>") %>';
                        errorMsg.style.color = "red";
                        errorMsg.style.marginTop = "8px";

                        const title = modal.querySelector("#variantModalTitle");
                        if (title) {
                            title.insertAdjacentElement("afterend", errorMsg);
                        }
                    }

                    // Xóa err khỏi session để không lặp lại
                <% session.removeAttribute("err"); %>
                });
            </script>
        </c:if>


        <c:if test="${not empty sessionScope.errEdit}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("editModal").style.display = "flex";

                    document.getElementById("modalPurchaseID").value = "${pcid}";
                    document.getElementById("modalProductVariantID").value = "${pvid}";
                    document.getElementById("modalQuantity").value = "${qty}";
                    document.getElementById("modalCostPrice").value = (parseFloat("${price}") || 0).toLocaleString("vi-VN");

                    const errMsg = document.createElement("div");
                    errMsg.innerHTML = "${sessionScope.errEdit}";
                    errMsg.style.color = "red";
                    errMsg.style.marginBottom = "10px";
                    document.querySelector("#editModal form").prepend(errMsg);
                });
            </script>
            <% session.removeAttribute("errEdit"); %>
        </c:if>
        <!-- Script dùng chung cho cả 2 modal -->
        <script>
            function formatNumber(num) {
                return num.toLocaleString("vi-VN");
            }

            function unformatNumber(str) {
                if (!str)
                    return 0;
                let cleaned = str.replace(/\./g, '').replace(/[^\d]/g, '');
                let num = parseInt(cleaned);
                return isNaN(num) ? 0 : num;
            }

            function increase(id) {
                let input = document.getElementById(id);
                if (!input)
                    return;

                let value = unformatNumber(input.value);
                value += 1000;
                input.value = formatNumber(value);
            }

            function decrease(id) {
                let input = document.getElementById(id);
                if (!input)
                    return;

                let value = unformatNumber(input.value);
                value = Math.max(0, value - 1000);
                input.value = formatNumber(value);
            }

            function formatCurrencyVN(input) {
                let value = unformatNumber(input.value);
                input.value = formatNumber(value);
            }
        </script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/feather.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

<!--        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>-->

        <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>
    </body>
</html>