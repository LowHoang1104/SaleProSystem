<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
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
            <%@include file="../HeadSideBar/header.jsp"%>
            <%@include file="../HeadSideBar/sidebar.jsp"%>
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Product List</h4>
                            <h6>Manage your products</h6>
                        </div>
                        <div class="page-btn">
                            <a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=2" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" alt="img" class="me-1">Add New Product</a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-path">
                                        <a class="btn btn-filter" id="filter_search">
                                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/filter.svg" alt="img">
                                            <span><img src="${pageContext.request.contextPath}/view/assets/img/icons/closes.svg" alt="img"></span>
                                        </a>
                                    </div>
                                    <div>
                                        <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex">
                                            <input  type="text" name="kw" placeholder="Search...">
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   
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
                            <div class="card mb-0" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-12 col-sm-12">
                                            <div class="row">
                                                <form action="productcontroller" method="post" style="display: flex">
                                                    <div class="col-lg col-sm-6 col-12">
                                                        <div class="form-group">
                                                            <select class="select" name="category">
                                                                <option value="0">Choose Category</option>
                                                                <c:forEach items="${cdata}" var="c">
                                                                    <option value="${c.categoryID}"><c:out value="${c.categoryName != null ? c.categoryName : ''}" /></option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg col-sm-6 col-12">
                                                        <div class="form-group">
                                                            <select class="select" name="type">
                                                                <option value="0">Choose Type</option>
                                                                <c:forEach items="${tdata}" var="t">
                                                                    <option value="${t.typeID}"><c:out value="${t.typeName != null ? t.typeName : ''}" /></option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg col-sm-6 col-12">
                                                        <div class="form-group">
                                                            <select class="select" name="store">
                                                                <option value="0">Choose Store</option>
                                                                <c:forEach items="${stdata}" var="st">
                                                                    <option value="${st.storeID}"><c:out value="${st.storeName != null ? st.storeName : ''}" /></option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-1 col-sm-6 col-12">
                                                        <div class="form-group">
                                                            <button type="submit" class="btn btn-filters ms-auto" name="filter" value="true">
                                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/search-whites.svg" alt="img">
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
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
                                            <th>Product ID</th>
                                            <th>Product name</th>
                                            <th>Category</th>
                                            <th>Type</th>                                           
                                            <th>Price</th>
                                            <th>Cost Price</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pdata}" var="i">
                                            <tr>
                                                <td>
                                                    <label class="checkboxs">
                                                        <input type="checkbox">
                                                        <span class="checkmarks"></span>
                                                    </label>
                                                </td>
                                                <td>${i.getCode()}</td>
                                                <td class="productimgname">
                                                    <a href="javascript:void(0);" class="product-img">
                                                        <img src="${i.getImage()}">
                                                    </a>
                                                    <a href="javascript:void(0);">${i.getName()}</a>
                                                </td>
                                                <td>${i.getCategoryNameById()}</td>        
                                                <td>${i.getTypeNameById()}</td>
                                                <td><fmt:formatNumber value="${i.price}" pattern="#,###"/></td>
                                                <td><fmt:formatNumber value="${i.costPrice}" pattern="#,###"/></td>
                                                <td>
                                                    <a class="me-3" href="productcontroller?id=${i.getCode()}&mode=1">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/eye.svg" alt="img">
                                                    </a>
                                                    <a class="me-3" href="productcontroller?id=${i.getCode()}&mode=2">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                    </a>
                                                    <a class="me-3" href="productcontroller?id=${i.getCode()}&mode=3">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                                    </a>
                                                </td>
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