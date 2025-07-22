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
 <%@include file="../HeadSideBar/header.jsp"%>
            <%@include file="../HeadSideBar/sidebar.jsp"%>
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
<<<<<<<< HEAD:web/view/jsp/admin/InvoiceManager/invoicelist.jsp
                            <h4>Sales List</h4>
                            <h6>Manage your sales</h6>
========
                            <h4>Product List</h4>
                            <h6>Manage your products</h6>
>>>>>>>> Dat:web/view/jsp/admin/productlist.jsp
                        </div>
                        <div class="page-btn">
                            <a href="${pageContext.request.contextPath}/sidebarcontroller?mode=2" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" alt="img" class="me-1">Add New Product</a>
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
                                        <form action="productcontroller" method="post" style="display: flex">
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

<<<<<<<< HEAD:web/view/jsp/admin/InvoiceManager/invoicelist.jsp
                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <input type="text" placeholder="Enter Name">
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <input type="text" placeholder="Enter Reference No">
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <select class="select">
                                                    <option>Completed</option>
                                                    <option>Paid</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <a class="btn btn-filters ms-auto"><img src="<%=path%>/view/assets/img/icons/search-whites.svg" alt="img"></a>
========
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
                                                            <button type="submit" class="btn btn-filters ms-auto" name="filter">
                                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/search-whites.svg" alt="img">
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
>>>>>>>> Dat:web/view/jsp/admin/productlist.jsp
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
<<<<<<<< HEAD:web/view/jsp/admin/InvoiceManager/invoicelist.jsp
                                            <th>Date</th>
                                            <th>Customer Name</th>
                                            <th>Payment</th>                                          
                                            <th>Store</th>
                                            <th>Total</th>
                                            <th>Biller</th>
                                            <th class="text-center">Action</th>
========
                                            <th>Product ID</th>
                                            <th>Product name</th>
                                            <th>Category</th>
                                            <th>Type</th>                                           
                                            <th>Price</th>
                                            <th>Cost Price</th>
                                            <th>Action</th>
>>>>>>>> Dat:web/view/jsp/admin/productlist.jsp
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
<<<<<<< HEAD:web/view/jsp/admin/productlist.jsp
<<<<<<<< HEAD:web/view/jsp/admin/InvoiceManager/invoicelist.jsp
                                                <td>${a.getInvoiceDate()}</td>
                                                <td>${a.getCustomerNameByID()}</td>
                                                <td>${a.getPaymentMethodNameByID()}</td>                                          
                                                <td>${a.getStoreNameByID()}</td>
                                                <td>${a.getTotalAmount()}</td>
                                                <td>${a.getEmployeeNameByID()}</td>
                                                <td class="text-center">
                                                    <a class="action-set" href="javascript:void(0);" data-bs-toggle="dropdown" aria-expanded="true">
                                                        <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                                                    </a>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a href="<%=path%>/InvoiceDetail?invoiceID=${a.getId()}&mode=1" class="dropdown-item"><img src="<%=path%>/view/assets/img/icons/eye1.svg" class="me-2" alt="img">Sale Detail</a>
                                                        </li>
                                                        <li>
                                                            <a href="<%=path%>/InvoiceDetail?invoiceID=${a.getId()}&mode=2" class="dropdown-item"><img src="<%=path%>/view/assets/img/icons/edit.svg" class="me-2" alt="img">Edit Sale</a>
                                                        </li>
                                                        <li>
                                                            <a href="" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#showpayment"><img src="<%=path%>/view/assets/img/icons/dollar-square.svg" class="me-2" alt="img">Show Payments</a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:void(0);" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#createpayment"><img src="<%=path%>/view/assets/img/icons/plus-circle.svg" class="me-2" alt="img">Create Payment</a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:void(0);" class="dropdown-item"><img src="<%=path%>/view/assets/img/icons/download.svg" class="me-2" alt="img">Download pdf</a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:void(0);" class="dropdown-item confirm-text"><img src="<%=path%>/view/assets/img/icons/delete1.svg" class="me-2" alt="img">Delete Sale</a>
                                                        </li>
                                                    </ul>
                                                </td>
                                            </tr>
========
                                                <td>${i.getProductCode()}</td>
=======
                                                <td>${i.getCode()}</td>
>>>>>>> Tung1:web/view/jsp/admin/ProductManagement/productlist.jsp
                                                <td class="productimgname">
                                                    <a href="javascript:void(0);" class="product-img">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/product/product1.jpg" alt="product">
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

>>>>>>>> Dat:web/view/jsp/admin/productlist.jsp
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