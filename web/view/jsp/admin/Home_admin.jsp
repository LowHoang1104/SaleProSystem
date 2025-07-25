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
        <title>Dreams Pos admin template</title>

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/fontawesome/css/all.min.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="<%=path%>/view/assets/css/style.css" type="text/css"/>
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>
        <div class="main-wrapper">
            <%@include file="HeadSideBar/header.jsp" %>
            <%@include file="HeadSideBar/sidebar.jsp" %> 
            <div class="page-wrapper">
                <div class="content">
                    <div class="row">

                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count">
                                <div class="dash-counts">
                                    <h4>${customerNum}</h4>
                                    <h5>Customers</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das1">
                                <div class="dash-counts">
                                    <h4>${supplierNum}</h4>
                                    <h5>Suppliers</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="user-check"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das2">
                                <div class="dash-counts">
                                    <h4>${purchaseNum}</h4>
                                    <h5>Purchase Invoice</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="file-text"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 col-sm-6 col-12 d-flex">
                            <div class="dash-count das3">
                                <div class="dash-counts">
                                    <h4>${invoiceNum}</h4>
                                    <h5>Invoices Invoice</h5>
                                </div>
                                <div class="dash-imgs">
                                    <i data-feather="file"></i>
                                </div>
                            </div>
                        </div>
                    </div>

                  
                    <div class="card mb-0">
                        <div class="card-body">
                            <h4 class="card-title">Expired Products</h4>
                            <div class="table-responsive dataview">
                                <form id="frm" action="HomepageController" method="get">
                                    <select name="op" onchange="document.getElementById('frm').submit()">                                  
                                        <option <c:if test="${op eq 0}">selected</c:if> value="0">All time</option>
                                        <option <c:if test="${op eq 1}">selected</c:if> value="1">7 ngày qua</option>
                                        <option <c:if test="${op eq 2}">selected</c:if> value="2">1 tháng qua</option>
                                        <option <c:if test="${op eq 3}">selected</c:if> value="3">3 tháng qua</option>
                                        <option <c:if test="${op eq 4}">selected</c:if> value="4">6 tháng qua</option>
                                        <option <c:if test="${op eq 5}">selected</c:if> value="5">1 năm qua</option>
                                    </select>
                                </form>
                                <table class="table datatable ">
                                    <thead>
                                        <tr>
                                            <th>SNo</th>
                                            <th>Product Code</th>
                                            <th>Product Name</th>
                                            <th>Brand Name</th>
                                            <th>Category Name</th>
                                            <th style="text-align: center">Number Of Sale</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${products}" var="a" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td> <!-- Tự động tăng SNo -->
                                                <td><a href="javascript:void(0);">${a.getCode()}</a></td>
                                                <td class="productimgname">
                                                    <a class="product-img" href="productlist.html">
                                                        <img src="${a.getImage()}" alt="alt"/> 
                                                    </a>
                                                    <a href="productlist.html">${a.getProductName()}</a>
                                                </td>
                                                <td>${a.getSupplierNumber()}</td>
                                                <td>${a.getTypeName()}</td>
                                                <td style="text-align: center">${a.getNumSale()}</td>
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
        <script src="<%=path%>/view/assets/plugins/apexchart/chart-data.js"></script>

        <script src="<%=path%>/view/assets/js/script.js"></script>
    </body>
</html>