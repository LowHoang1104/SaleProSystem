<%-- 
    Document   : edit_invoice
    Created on : May 25, 2025, 4:11:00 PM
    Author     : ADMIN
--%>

<%String path = request.getContextPath();%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap-datetimepicker.min.css">

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
            <%@include file="../HeadSideBar/header.jsp"%>
            <%@include file="../HeadSideBar/sidebar.jsp"%>
            <form action="<%=path%>/Invoice" method="post">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Edit Sale</h4>
                                <h6>Edit your sale details</h6>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Customer</label>
                                            <div class="row">
                                                <div class="col-lg-10 col-sm-10 col-10">
                                                    <select class="select2 form-control" id="customerSelect" name="customerId" style="width:100%" >
                                                        <option value="">Nhập tên/SĐT để tìm kiếm khách hàng...</option>
                                                        <c:forEach items="${customers}" var="a">
                                                            <option <c:if test="${invoice.getCustomerId() eq a.getCustomerID()}">selected</c:if> value="${a.getCustomerID()}">
                                                                ${a.getFullName()} - ${a.getPhone()}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Date</label>
                                            <div class="input-groupicon">
                                                <input name="date" type="date"  value=${invoice.getInvoiceDate()}>
                                                <div class="addonset">
                                                    <img src="<%=path%>/view/assets/img/icons/calendars.svg" alt="img">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Supplier</label>
                                            <select name="storeId" class="select">
                                                <c:forEach items="${stores}" var="a">
                                                    <option value="${a.getStoreID()}" <c:if test="${invoice.getStoreId() eq a.getStoreID()}">selected</c:if>>${a.getStoreName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Product Name</label>
                                            <div class="input-groupicon">
                                                <input type="text" placeholder="Please type product code and select...">
                                                <div class="addonset">
                                                    <img src="<%=path%>/view/assets/img/icons/scanner.svg" alt="img">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="table-responsive mb-3">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Product Name</th>
                                                    <th>QTY</th>
                                                    <th>Price</th>
                                                    <th>Discount</th>
                                                    <th>Subtotal</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${data}" var="a" >
                                                    <tr>                                 
                                                        <td></td>
                                                        <td><img src="<%=path%>/view/assets/img/product/product1.jpg" alt="img" class="me-2" style="width:40px;height:40px;">${a.getNameProductNameByID()}</td>
                                                        <td>${a.getQuantity()}</td>
                                                        <td>${a.getUnitPrice()}</td>
                                                        <td>${a.getDiscountPercent()}</td>
                                                        <td>${a.getUnitPrice()}</td>
                                                        <td>
                                                            <a href="javascript:void(0);" class="delete-set"><img src="<%=path%>/view/assets/img/icons/delete.svg" alt="svg"></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">

                                    <div class="col-lg-12">
                                        <input type="submit" name="update" value="Submit" href="javascript:void(0);" class="btn btn-submit me-2">
                                        <input type="submit" value="Cancel" href="javascript:void(0);" class="btn btn-cancel">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>


        <script src="<%=path%>/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="<%=path%>/view/assets/js/feather.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="<%=path%>/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="<%=path%>/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="<%=path%>/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="<%=path%>/view/assets/js/moment.min.js"></script> 
        <script src="<%=path%>/view/assets/js/bootstrap-datetimepicker.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#customerSelect').select2({
                    placeholder: 'Nhập tên, SĐT để tìm kiếm khách hàng...',
                    allowClear: true,
                    width: '100%'
                });
            });
        </script>

        <script src="<%=path%>/view/assets/js/script.js"></script>
    </body>
</html>