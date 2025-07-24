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
