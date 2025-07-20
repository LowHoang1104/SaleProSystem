<%-- 
    Document   : Update_customer
    Created on : May 25, 2025, 11:29:16 AM
    Author     : Thinhnt
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <link rel="shortcut icon" type="image/x-icon" href="view/assets/img/favicon.jpg">

        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css">

        <link rel="stylesheet" href="view/assets/css/animate.css">

        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css">

        <link rel="stylesheet" href="view/assets/css/dataTables.bootstrap4.min.css">

        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css">

        <link rel="stylesheet" href="view/assets/css/style.css">
    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>

        <div class="main-wrapper">

            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Customer Management</h4>
                            <h6>Add/Update Customer</h6>
                        </div>
                    </div>
                    <%-- Hiển thị thông báo lỗi nếu có --%>
                    <% String message = (String) request.getAttribute("error"); %>
                    <% if (message != null) { %>
                    <div class="alert <%= message.contains("Vui")? "alert-danger" : "alert-success" %>">
                        <%= message %>
                    </div>
                    <% } %>
                    <div class="card">
                        <div class="card-body">
                            <form action="SaveCustomerServlet" method="POST">
                                <div class="row">
                                    <input type="hidden" name="customerId" value="${cusId}">
                                    <!-- Họ tên khách hàng -->
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Họ và tên</label>
                                            <input type="text" name="fullName" value="${cusFullName}" class="form-control">
                                        </div>
                                    </div>

                                    <!-- Giới tính -->
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Giới tính</label>
                                            <select class="select" name="gender">
                                                <option value="" ${gender == '' ? 'selected' : ''}>Chọn giới tính</option>
                                                <option value="Male" ${cusGender == 'Male' ? 'selected' : ''}>Nam</option>
                                                <option value="Female" ${cusGender == 'Female' ? 'selected' : ''}>Nữ</option>
                                            </select>
                                        </div>
                                    </div>

                                    <!-- Số điện thoại -->
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Số điện thoại</label>
                                            <input type="text" name="phone" value="${cusPhone}" class="form-control">
                                        </div>
                                    </div>

                                    <!-- Email -->
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="text" name="email" value="${cusEmail}" class="form-control">
                                        </div>
                                    </div>

                                    <!-- Ngày sinh -->
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Ngày sinh</label>
                                            <input type="date" name="birthDate" class="form-control" value="${cusBirthDate}">
                                        </div>
                                    </div>

                                    <!-- Địa chỉ -->
                                    <div class="col-lg-9 col-12">
                                        <div class="form-group">
                                            <label>Địa chỉ</label>
                                            <input type="text" name="address" value="${cusAddress}" class="form-control">
                                        </div>
                                    </div>

                                    <!-- Mô tả -->
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Ghi chú</label>
                                            <textarea class="form-control" name="description">${cusDescription}</textarea>
                                        </div>
                                    </div>

                                    <!-- Nút Submit + Hủy -->
                                    <div class="col-lg-12">
                                        <button type="submit" name="action" value="submit" class="btn btn-submit me-2">Cập nhật</button>
                                        <a href="ListCustomerServlet" class="btn btn-cancel">Hủy</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="view/assets/js/feather.min.js"></script>

        <script src="view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="view/assets/js/jquery.dataTables.min.js"></script>
        <script src="view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="view/assets/plugins/select2/js/select2.min.js"></script>

        <script src="view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

        <script src="view/assets/js/script.js"></script>
    </body>
</html>

