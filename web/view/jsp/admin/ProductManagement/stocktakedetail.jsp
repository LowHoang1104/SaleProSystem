<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                            <h4>Detail List</h4>
                            <h6>Manage Stock Take Detail</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addVariant" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                Add Stock Take Detail
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">                
                                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; gap: 20px;">
                                        <!-- Form tìm kiếm -->
                                        <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex; gap: 8px; align-items: center;">
                                            <input type="text" name="kw" placeholder="Search..." 
                                                   style="padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
                                            <input type="submit" name="search" value="Search" 
                                                   style="padding: 6px 12px; background-color: green; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                        </form>

                                        <!-- Chú thích icon có thể click -->
                                        <div style="display: flex; align-items: center; gap: 16px;">
                                            <a href="stocktakecontroller?id=${stkid}" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #6c757d; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/gray.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Tất cả</span>
                                            </a>
                                            <a href="stocktakecontroller?id=${stkid}&mode=searchEqual" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #28a745; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/green.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Hợp lệ</span>
                                            </a>
                                            <a href="stocktakecontroller?id=${stkid}&mode=searchNotEqual" style="display: inline-flex; align-items: center; gap: 6px; font-size: 14px; color: #dc3545; text-decoration: none;">
                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/red.svg"
                                                     alt="icon"
                                                     style="width: 16px; height: 16px; display: block;">
                                                <span>Có sự chênh lệch</span>
                                            </a>
                                        </div>
                                    </div>
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
                                        <th>Stock Take Detail ID</th>
                                        <th>Product Variant</th>
                                        <th>Recorded Quantity</th>
                                        <th>Actual Quantity</th>                                           
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sddata}" var="sd">
                                        <c:set var="actual" value="${sd.getActualQuantity()}" />
                                        <c:set var="recorded" value="${sd.recordedQuantity()}" />
                                        <tr style="background-color: ${actual == recorded ? '#a3cfbb' : '#f5b5b0'};">
                                            <td>
                                                <label class="checkboxs">
                                                    <input type="checkbox">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </td>
                                            <td>${sd.getStockTakeDetailID()}</td>
                                            <td>${sd.productVarianttoString()}</td>
                                            <td>${sd.recordedQuantity()}</td>        
                                            <td>${sd.getActualQuantity()}</td>
                                            <td>
                                                <a class="me-3" href="stocktakecontroller?id=${stkid}&mode=2">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                </a>
                                                <a class="me-3" href="stocktakecontroller?id=${stkid}&mode=3">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="img">
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- Modal -->
                    <div id="variantInputModal" class="overlay" style="display: none; position: fixed; top: 0; left: 0;
                         width: 100%; height: 100%; background: rgba(0,0,0,0.4); justify-content: center; align-items: center; z-index: 9999;">

                        <div class="modal-content" style="background: white; padding: 20px; border-radius: 10px; width: 400px; position: relative;">
                            <h4 id="variantModalTitle">Select Product Variants</h4>
                            <form action="${pageContext.request.contextPath}/stocktakecontroller" method="post">
                                <div style="max-height: 300px; overflow-y: auto; margin-bottom: 16px; border: 1px solid #ccc; border-radius: 6px;">
                                    <table style="width: 100%; border-collapse: collapse;">
                                        <thead>
                                            <tr style="background-color: #f0f0f0;">
                                                <th style="padding: 8px; border: 1px solid #ccc;">Select</th>
                                                <th style="padding: 8px; border: 1px solid #ccc;">Variant</th>
                                                <th style="padding: 8px; border: 1px solid #ccc;">Actual Quantity</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pvdata}" var="pv">
                                                <tr>
                                                    <td style="text-align: center; padding: 8px; border: 1px solid #ccc;">
                                                        <input type="checkbox" name="variantIds" value="${pv.getId()}">
                                                    </td>
                                                    <td style="padding: 8px; border: 1px solid #ccc;">${pv.productVarianttoString()}</td>
                                                    <td style="padding: 8px; border: 1px solid #ccc;">
                                                        <input type="number" name="actualQuantity_${pv.getId()}" min="0" value="0"
                                                               style="width: 80px; padding: 4px;">
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <input type="hidden" name="id" value="${stkid}">

                                <div style="display: flex; justify-content: center; gap: 10px; margin-top: 16px;">
                                    <button type="submit" name="add"
                                            style="padding: 8px 16px; background-color: #28a745; color: white;
                                            border: none; border-radius: 4px; cursor: pointer;">
                                        Save Selected
                                    </button>
                                    <button type="button" onclick="closeVariantModal()"
                                            style="padding: 8px 16px; background-color: #6c757d; color: white;
                                            border: none; border-radius: 4px; cursor: pointer;">
                                        Cancel
                                    </button>
                                </div>
                            </form>
                        </div>
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

    <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>

    <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

    <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

    <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>

    <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>
</body>
</html>