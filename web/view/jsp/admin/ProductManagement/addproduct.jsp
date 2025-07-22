<%-- 
    Document   : addproduct.jsp
    Created on : May 22, 2025, 12:56:15 AM
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
                            <h4>Product Add</h4>
                            <h6>Create new product</h6>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <form id="uploadForm" action="productcontroller" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                                <div class="row">
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Product Code</label>
                                            <input type="text" name="id" required>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Product Name</label>
                                            <input type="text" name="name">
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Category</label>
                                            <select class="select" name="category">
                                                <c:forEach items="${cdata}" var="c">
                                                    <c:if test="${not empty c.categoryID}">
                                                        <option value="${c.categoryID}">
                                                            <c:out value="${c.categoryName != null ? c.categoryName : ''}" />
                                                        </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Types</label>
                                            <select class="select" name="type">
                                                <c:forEach items="${tdata}" var="t">
                                                    <option value="${t.typeID}"><c:out value="${t.typeName != null ? t.typeName : ''}" /></option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" name="des"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Price</label>
                                            <div class="input-group">
                                                <button type="button" class="btn btn-secondary" onclick="decrease('price')">-</button>
                                                <input type="text" id="price" name="price" value="0" class="form-control">                                                <button type="button" class="btn btn-secondary" onclick="increase('price')">+</button>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <label>Cost Price</label>
                                            <div class="input-group">
                                                <button type="button" class="btn btn-secondary" onclick="decrease('cost')">-</button>
                                                <input type="text" id="cost" name="cost" value="0" class="form-control">
                                                <button type="button" class="btn btn-secondary" onclick="increase('cost')">+</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                            <label> Product Image</label>
                                            <div class="image-upload">
                                                <input id="imageInput" type="file" name="image">
                                                <div class="image-uploads">
                                                    <img  src="${pageContext.request.contextPath}/view/assets/img/icons/upload.svg" alt="img">
                                                    <h4>Drag and drop a file to upload</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-sm-6 col-12">
                                        <div class="form-group">
                                            <img id="preview" src="#" alt="Ảnh sẽ hiển thị ở đây" style="max-width: 300px; display: none;" />
                                        </div>
                                    </div>
                                    <div class="col-lg-12">
                                        <p id="errorMsg" style="color: red;">${err}</p>
                                        <button type="submit" name="add" class="btn btn-submit me-2">Submit</button>
                                        <button type="button" name="cancel" class="btn btn-cancel" onclick="window.location.href = 'productlist.html'">Cancel</button>                 
                                    </div>                                              
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script>
            function formatNumber(num) {
                return num.toLocaleString('vi-VN');
            }

            // Bỏ dấu . (ngăn cách hàng nghìn) và đổi , (thập phân) về . để parseFloat hiểu
            function unformatNumber(str) {
                if (!str)
                    return 0;
                return parseFloat(str.replace(/\./g, '').replace(',', '.')) || 0;
            }

            // Cho phép chỉ nhập số
            function setupNumericInput(id) {
                const input = document.getElementById(id);
                input.addEventListener("keypress", function (e) {
                    if (e.ctrlKey || e.metaKey || e.altKey)
                        return;
                    let char = String.fromCharCode(e.which);
                    if (!/[\d]/.test(char)) {
                        e.preventDefault();
                    }
                });

                input.addEventListener("input", function () {
                    let raw = unformatNumber(this.value);
                    this.value = formatNumber(raw);
                });
            }

            setupNumericInput("price");
            setupNumericInput("cost");

            // Tăng giảm số:
            function increase(id) {
                let input = document.getElementById(id);
                let value = unformatNumber(input.value);
                value += 1000;
                input.value = formatNumber(value);
            }

            function decrease(id) {
                let input = document.getElementById(id);
                let value = unformatNumber(input.value);
                if (value > 0)
                    value -= 1000;
                if (value < 0)
                    value = 0;
                input.value = formatNumber(value);
            }

            // Trước khi submit: bỏ dấu phẩy
            document.querySelector("form").addEventListener("submit", function () {
                document.getElementById("price").value = unformatNumber(document.getElementById("price").value);
                document.getElementById("cost").value = unformatNumber(document.getElementById("cost").value);
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const fileInput = document.getElementById("imageInput");
                const errorMsg = document.getElementById("errorMsg");
                const previewImg = document.getElementById("preview");

                fileInput.addEventListener("change", function (event) {
                    const file = fileInput.files[0];

                    if (!file)
                        return;

                    // 1. Kiểm tra định dạng ảnh
                    if (!file.type.startsWith("image/")) {
                        errorMsg.textContent = "Vui lòng chọn một file ảnh hợp lệ (jpg hoặc png).";
                        previewImg.style.display = "none";
                        fileInput.value = "";
                        return;
                    }

                    // 2. Kiểm tra dung lượng ảnh
                    const maxSizeInBytes = 1 * 1024 * 1024;
                    if (file.size > maxSizeInBytes) {
                        errorMsg.textContent = "Ảnh phải nhỏ hơn hoặc bằng 1MB.";
                        previewImg.style.display = "none";
                        fileInput.value = "";
                        return;
                    }

                    // Nếu hợp lệ: hiển thị preview và xóa lỗi
                    errorMsg.textContent = "";

                    const reader = new FileReader();
                    reader.onload = function (e) {
                        previewImg.src = e.target.result;
                        previewImg.style.display = "block";
                    };
                    reader.readAsDataURL(file);
                });
            });
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const inputs = document.querySelectorAll('input[name="price"], input[name="cost"]');

                inputs.forEach(input => {
                    input.addEventListener('input', function () {
                        if (this.value < 0) {
                            this.value = 0;
                        }
                    });

                    input.addEventListener('paste', function (e) {
                        const paste = (e.clipboardData || window.clipboardData).getData('text');
                        if (parseFloat(paste) < 0) {
                            e.preventDefault();
                        }
                    });
                });
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
