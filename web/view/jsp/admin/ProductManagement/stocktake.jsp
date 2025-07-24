<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="16kb" autoFlush="true" %>
<%@ page errorPage="" %>
<%
    String path = request.getContextPath();
%>
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
            <%@include file="../HeadSideBar/header.jsp"%>
            <%@include file="../HeadSideBar/sidebar.jsp"%>          
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Stock Take List</h4>
                            <h6>Manage Your Stock Take</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addStockTakeBtn" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" alt="img" class="me-1">
                                Add New Stock Take
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-path">
                                        <!--                                        <a class="btn btn-filter" id="filter_search">
                                                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/filter.svg" alt="img">
                                                                                    <span><img src="${pageContext.request.contextPath}/view/assets/img/icons/closes.svg" alt="img"></span>
                                                                                </a>-->
                                    </div>
                                    <div>
<!--                                        <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex">
                                            <input  type="text" name="kw" placeholder="Search...">
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   -->
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        <li>
                                            <form method="post" action="excelcontroller">
                                                <button style="border: none; background: none" data-bs-toggle="tooltip" data-bs-placement="top" title="excel" type="submit" name="type" value="stocktake"><img src="${pageContext.request.contextPath}/view/assets/img/icons/excel.svg" alt="img"></button>
                                            </form>
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
                                            <th>Stock Take ID</th>
                                            <th>Warehouse</th>
                                            <th>Check Date</th>
                                            <th>Check By</th>                                           
                                            <th>Note</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${stkdata}" var="stk">
                                            <tr>
                                                <td>
                                                    <label class="checkboxs">
                                                        <input type="checkbox">
                                                        <span class="checkmarks"></span>
                                                    </label>
                                                </td>
                                                <td>${stk.getStockTakeID()}</td>
                                                <td>${stk.getWarehouseName()}</td>
                                                <td>${stk.getCheckDate()}</td>        
                                                <td>${stk.getUserName()}</td>
                                                <td>${stk.getNote()}</td>
                                                <td>
                                                    <a class="me-3" href="stocktakecontroller?id=${stk.getStockTakeID()}&mode=1">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/eye.svg" alt="img">
                                                    </a>
                                                <!--<a class="me-3" href="stocktakecontroller?id=${stk.getStockTakeID()}&mode=1">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                    </a>-->
                                                    <a class="me-3" href="stocktakecontroller?id=${stk.getStockTakeID()}&mode=3">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- Add Stock Take Modal -->
                                <div class="modal fade" id="addStockTakeModal" tabindex="-1" role="dialog" aria-labelledby="addStockTakeModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <form action="stocktakecontroller" method="post">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="addStockTakeModalLabel">Add New Stock Take</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span>&times;</span>
                                                    </button>
                                                </div>

                                                <div class="modal-body">
                                                    <!-- Warehouse dropdown -->
                                                    <div class="form-group">
                                                        <label for="warehouseID">Warehouse</label>
                                                        <select class="form-control" id="warehouseID" name="warehouseID" required>
                                                            <c:forEach var="w" items="${wdata}">
                                                                <option value="${w.getWarehouseID()}">${w.getWarehouseName()}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <input type="hidden" name="id" value="${stkid}">
                                                    <!-- Note -->
                                                    <div class="form-group">
                                                        <label for="note">Note</label>
                                                        <textarea class="form-control" id="note" name="note" rows="3" placeholder="Enter note (optional)"></textarea>
                                                    </div>
                                                </div>

                                                <div class="modal-footer">
                                                    <button type="submit" name="addStockTake" class="btn btn-primary">Save</button>
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script>
            document.getElementById("addStockTakeBtn").addEventListener("click", function (e) {
                e.preventDefault(); // Ngăn chuyển trang
                $('#addStockTakeModal').modal('show'); // Dùng Bootstrap 4
            });
        </script>


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