<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

        <link rel="shortcut icon" type="image/x-icon" href="<%=path%>/view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/animate.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="<%=path%>/view/assets/css/bootstrap-datetimepicker.min.css">

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
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Sales List</h4>
                            <h6>Manage your sales</h6>
                        </div>
                        <div class="page-btn">
                            <a href="add-sales.html" class="btn btn-added"><img src="<%=path%>/view/assets/img/icons/plus.svg" alt="img" class="me-1">Add Sales</a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-path">
                                        <a class="btn btn-filter" id="filter_search">
                                            <img src="<%=path%>/view/assets/img/icons/filter.svg" alt="img">
                                            <span><img src="<%=path%>/view/assets/img/icons/closes.svg" alt="img"></span>
                                        </a>
                                    </div>
                                    <div class="search-input">
                                        <a class="btn btn-searchset"><img src="<%=path%>/view/assets/img/icons/search-white.svg" alt="img"></a>
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="pdf"><img src="<%=path%>/view/assets/img/icons/pdf.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="excel"><img src="<%=path%>/view/assets/img/icons/excel.svg" alt="img"></a>
                                        </li>
                                        <li>
                                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="print"><img src="<%=path%>/view/assets/img/icons/printer.svg" alt="img"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>

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
                                                <select name="stores" class="select">
                                                    <c:forEach items="stores" var="index">
                                                        <option>Completed</option>                                             
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <select name="paymethod" class="select">
                                                    <c:forEach items="paymethods" var="index">
                                                        <option>Completed</option>                                             
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <a class="btn btn-filters ms-auto" onchange=""><img src="<%=path%>/view/assets/img/icons/search-whites.svg" alt="img"></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>
                                                <label class="checkboxs">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </th>
                                    <form id="sort" method="post" action="Invoice">
                                        <input type="hidden" name="sort" id="sortValue">
                                    </form>

                                    <th style="cursor: pointer;" onclick="submitSort(1)">Date</th>
                                    <th style="cursor: pointer;" onclick="submitSort(2)">Customer Name</th>
                                    <th style="cursor: pointer;" onclick="submitSort(3)">Payment</th>
                                    <th style="cursor: pointer;" onclick="submitSort(4)">Store</th>
                                    <th style="cursor: pointer;" onclick="submitSort(5)">Total</th>
                                    <th style="cursor: pointer;" onclick="submitSort(6)">Biller</th>
                                    <th class="text-center">Action</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${data}" var="a" varStatus="b">
                                            <tr>
                                                <td>
                                                    <label class="checkboxs">
                                                        ${b.index + 1}
                                                        <span class="checkmarks"></span>
                                                    </label>
                                                </td>
                                                <td>${a.getInvoiceDate()}</td>
                                                <td>${a.getCustomerNameByID()}</td>
                                                <td>${a.getPaymentMethodNameByID()}</td>                                          
                                                <td>${a.getStoreNameByID()}</td>
                                                <td><fmt:formatNumber value="${a.getTotalAmount()}" type="number" pattern="#,###"/></td>
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
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <div class="modal fade" id="showpayment" tabindex="-1" aria-labelledby="showpayment" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Show Payments</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Reference</th>
                                        <th>Amount</th>
                                        <th>Paid By</th>
                                        <th>Paid By</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="bor-b1">
                                        <td>2022-03-07	</td>
                                        <td>INV/SL0101</td>
                                        <td>$ 0.00	</td>
                                        <td>Cash</td>
                                        <td>
                                            <a class="me-2" href="javascript:void(0);">
                                                <img src="<%=path%>/view/assets/img/icons/printer.svg" alt="img">
                                            </a>
                                            <a class="me-2" href="javascript:void(0);" data-bs-target="#editpayment" data-bs-toggle="modal" data-bs-dismiss="modal">
                                                <img src="<%=path%>/view/assets/img/icons/edit.svg" alt="img">
                                            </a>
                                            <a class="me-2 confirm-text" href="javascript:void(0);">
                                                <img src="<%=path%>/view/assets/img/icons/delete.svg" alt="img">
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="createpayment" tabindex="-1" aria-labelledby="createpayment" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create Payment</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Customer</label>
                                    <div class="input-groupicon">
                                        <input type="text" value="2022-03-07" class="datetimepicker">
                                        <div class="addonset">
                                            <img src="<%=path%>/view/assets/img/icons/calendars.svg" alt="img">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Reference</label>
                                    <input type="text" value="INV/SL0101">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Received Amount</label>
                                    <input type="text" value="0.00">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Paying Amount</label>
                                    <input type="text" value="0.00">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Payment type</label>
                                    <select class="select">
                                        <option>Cash</option>
                                        <option>Online</option>
                                        <option>Inprogress</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group mb-0">
                                    <label>Note</label>
                                    <textarea class="form-control"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-submit">Submit</button>
                        <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="editpayment" tabindex="-1" aria-labelledby="editpayment" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Payment</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Customer</label>
                                    <div class="input-groupicon">
                                        <input type="text" value="2022-03-07" class="datetimepicker">
                                        <div class="addonset">
                                            <img src="<%=path%>/view/assets/img/icons/datepicker.svg" alt="img">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Reference</label>
                                    <input type="text" value="INV/SL0101">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Received Amount</label>
                                    <input type="text" value="0.00">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Paying Amount</label>
                                    <input type="text" value="0.00">
                                </div>
                            </div>
                            <div class="col-lg-6 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Payment type</label>
                                    <select class="select">
                                        <option>Cash</option>
                                        <option>Online</option>
                                        <option>Inprogress</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="form-group mb-0">
                                    <label>Note</label>
                                    <textarea class="form-control"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-submit">Submit</button>
                        <button type="button" class="btn btn-cancel" data-bs-dismiss="modal">Close</button>
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

        <script src="<%=path%>/view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="<%=path%>/view/assets/js/moment.min.js"></script>
        <script src="<%=path%>/view/assets/js/bootstrap-datetimepicker.min.js"></script>

        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="<%=path%>/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="<%=path%>/view/assets/js/script.js"></script>
        <script>
                                        function submitSort(value) {
                                            document.getElementById('sortValue').value = value;
                                            document.getElementById('sort').submit();
                                        }
                                        function submitSearch(value) {

                                        }
        </script>
    </body>
</html>