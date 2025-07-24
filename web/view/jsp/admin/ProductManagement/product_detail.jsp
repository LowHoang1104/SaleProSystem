<%-- 
    Document   : product_detail.jsp
    Created on : May 24, 2025, 9:20:08 PM
    Author     : tungd
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/plugins/owlcarousel/owl.carousel.min.css">

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
                            <h4>Product Details</h4>
                            <h6>Full details of a product</h6>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-8 col-sm-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="bar-code-view">
                                        <img src="${pageContext.request.contextPath}/view/assets/img/barcode1.png" alt="barcode">
                                        <a class="printimg">
                                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/printer.svg" alt="print">
                                        </a>
                                    </div>
                                    <div class="productdetails">
                                        <ul class="product-bar">
                                            <li>
                                                <h4>Product ID</h4>
                                                <h6>${p.code}</h6>
                                            </li>
                                            <li>
                                                <h4>Product Name</h4>
                                                <h6>${p.name}</h6>
                                            </li>
                                            <li>
                                                <h4>Category</h4>
                                                <h6>${p.getCategoryNameById()}</h6>
                                            </li>
                                            <li>
                                                <h4>Type</h4>
                                                <h6>${p.getTypeNameById()}</h6>
                                            </li>
                                            <li>
                                                <h4>Price</h4>
                                                <h6>${p.price}</h6>
                                            </li>
                                            <li>
                                                <h4>Cost Price</h4>
                                                <h6>${p.costPrice}</h6>
                                            </li>
                                            <li>
                                                <h4>Status</h4>
                                                <h6>${p.status}</h6>
                                            </li>
                                            <li>
                                                <h4>Release Date</h4>
                                                <h6>${p.releaseDate}</h6>
                                            </li>
                                            <li>
                                                <h4>Description</h4>
                                                <h6>${p.description}</h6>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-sm-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="slider-product-details">
                                        <div class="owl-carousel owl-theme product-slide">
                                            <div class="slider-product">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/product/product69.jpg" alt="img">
                                                <h4>Product Image</h4>
                                            </div>
                                            <div class="slider-product">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/product/product69.jpg" alt="img">
                                                <h4>Product Image</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Product Variants List</h4>
                                <h6>Manage your products</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#" id="addVariant" class="btn btn-added">
                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                    Add Product Variant
                                </a>
                            </div>
                        </div>
                        <table class="table datanew" id="productVariantTable">
                            <thead>
                                <tr>
                                    <th>
                                        <label class="checkboxs">
                                            <input type="checkbox" id="select-all">
                                            <span class="checkmarks"></span>
                                        </label>
                                    </th>
                                    <th>ProductVariant ID</th>
                                    <th>Size</th>
                                    <th>Color</th>
                                    <th>SKU</th>
                                    <th>Unit</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <c:forEach items="${pvdata}" var="pv">
                                <tbody>
                                    <tr>
                                        <td>
                                            <label class="checkboxs">
                                                <input type="checkbox">
                                                <span class="checkmarks"></span>
                                            </label>
                                        </td>
                                        <td>${pv.getId()}</td>
                                        <td>${pv.getSizenameByID()}</td>
                                        <td>${pv.getColornameByID()}</td>
                                        <td>${pv.getSku()}</td>
                                        <td>${pv.getUnit()}</td>
                                        <td>
                                            <a class="me-3" href="productvariantcontroller">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                            </a>
                                            <a class="me-3 confirm-text" href="javascript:void(0);">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                        <!-- Modal -->
                        <div id="variantInputModal" class="overlay" style="display: none; position: fixed; top: 0; left: 0;
                             width: 100%; height: 100%; background: rgba(0,0,0,0.4); justify-content: center; align-items: center; z-index: 9999;">

                            <div class="modal-content" style="background: white; padding: 20px; border-radius: 10px; width: 500px; position: relative;">
                                <h4 id="variantModalTitle">Add Product Variant</h4>
                                <form action="${pageContext.request.contextPath}/productvariantcontroller" method="post">                                
                                    <div class="form-group">
                                        <label>Size</label>
                                        <select name="size" class="form-control" required>
                                            <c:forEach items="${sdata}" var="s">
                                                <option value="${s.getSizeID()}">${s.getSizeName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Color</label>
                                        <select name="color" class="form-control" required>
                                            <c:forEach items="${cldata}" var="cl">
                                                <option value="${cl.getColorID()}">${cl.getColorName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Unit</label>
                                        <input type="text" name="unit" class="form-control" >
                                    </div>
                                    <div class="form-group">
                                        <label>Average Quantity</label>
                                        <input type="number" name="averageQuantity" class="form-control" value="50" min="0" required="">
                                    </div>
                                    <input type="hidden" name="productCode" value="${p.code}">
                                    <div class="text-end mt-3">
                                        <button type="submit" name="add" class="btn btn-primary">Save</button>
                                        <button type="button" class="btn btn-secondary" onclick="closeVariantModal()">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <script>
                document.getElementById("addVariant").addEventListener("click", function (event) {
                    event.preventDefault();
                    document.getElementById("variantInputModal").style.display = "flex";
                });

                function closeVariantModal() {
                    document.getElementById("variantInputModal").style.display = "none";
                }

                // Đóng nếu click bên ngoài
                window.addEventListener("click", function (e) {
                    const modal = document.getElementById("variantInputModal");
                    if (e.target === modal) {
                        closeVariantModal();
                    }
                });
            </script>
            <c:if test="${not empty err}">
                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const modal = document.getElementById("variantInputModal");
                        if (modal) {
                            modal.style.display = "flex";
                            document.body.classList.add("modal-open");

                            // Tạo phần tử hiển thị lỗi
                            const errorMsg = document.createElement("div");
                            errorMsg.textContent = "${err}";
                            errorMsg.style.color = "red";
                            errorMsg.style.marginTop = "8px";

                            // Thêm lỗi vào ngay sau thẻ h4
                            const title = modal.querySelector("#variantModalTitle");
                            if (title) {
                                title.insertAdjacentElement("afterend", errorMsg);
                            }
                        }
                    });
                </script>
            </c:if>

            <script src="${pageContext.request.contextPath}/view/assets/js/jquery-3.6.0.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/feather.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/jquery.slimscroll.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/plugins/owlcarousel/owl.carousel.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>

            <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
            <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>


            <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
            <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>
    </body>
</html>
