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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/attribute/attribute.css">
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
                            <h4>Purchase List</h4>
                            <h6>Manage your Purchase</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addVariant" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                Add Purchase
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div>
                                        <form action="${pageContext.request.contextPath}/purchasecontroller" method="post" style="display: flex">
                                            <select name="warehouseID">
                                                <option value="0">Choose Warehouse</option>
                                                <c:forEach items="${wdata}" var="w">
                                                    <option value="${w.getWarehouseID()}"><c:out value="${w.getWarehouseName() != null ? w.getWarehouseName() : ''}" /></option>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        <li>
                                            <form method="post" action="excelcontroller">
                                                <button data-bs-toggle="tooltip" data-bs-placement="top" title="excel" type="submit" name="type" value="purchase"><img src="${pageContext.request.contextPath}/view/assets/img/icons/excel.svg" alt="img"></button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table class="table  datanew">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Purchase ID</th>
                                            <th>Purchase Date</th>
                                            <th>Supplier</th>
                                            <th>Warehouse</th>                                           
                                            <th>Total Amount</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="counter" value="1" />
                                        <c:forEach items="${pcdata}" var="pc" varStatus="stt">
                                            <tr>
                                                <td>${counter}</td>
                                                <c:set var="counter" value="${counter + 1}" />
                                                <td>${pc.getPurchaseID()}</td>
                                                <td>${pc.getPurchaseDate()}</td>
                                                <td>${pc.getSupplierNameById()}</td>        
                                                <td>${pc.getWarehouseNameById()}</td>
                                                <td><fmt:formatNumber value="${pc.getTotalAmount()}" pattern="#,###" /></td>
                                                <c:if test="${pc.isConfirm() == false}">
                                                    <td>
                                                        <a class="me-3" href="purchasecontroller?id=${pc.getPurchaseID()}&mode=1">
                                                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/eye.svg" alt="img">
                                                        </a>
                                                        <!--<a class="me-3" href="#">
                                                                                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                                                                            </a>-->
                                                        <form action="purchasecontroller" method="post" style="display: inline;">
                                                            <input type="hidden" name="id" value="${pc.getPurchaseID()}" />
                                                            <button type="submit"
                                                                    name="deletePurchase"
                                                                    class="me-3"
                                                                    style="border: none; background: none; padding: 0;">
                                                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="delete">
                                                            </button>
                                                        </form>
                                                        <!-- Button open modal -->
                                                        <button type="button"
                                                                class="me-3"
                                                                style="border: none; background: none; padding: 0;"
                                                                onclick="openConfirmModal(${pc.getPurchaseID()})">
                                                            <img style="width: 27px; height: 27px; display: block;"
                                                                 src="${pageContext.request.contextPath}/view/assets/img/icons/confirm.svg"
                                                                 alt="confirm">
                                                        </button>
                                                    </td>
                                                </c:if>
                                                <c:if test="${pc.isConfirm() == true}">
                                                    <td>Confirmed</td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Modal ch?a form add-->
                    <div id="variantInputModal" style="display: none;" class="overlay">
                        <div class="modal-content">
                            <form id="colorForm" action="${pageContext.request.contextPath}/purchasecontroller" method="post">
                                <!-- Thêm select n?m phía trên input -->
                                <label for="colorGroup">Add Purchase:</label><br>
                                <select name="warehouseID" id="storeSelect" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 6px; border: 1px solid #ccc;">
                                    <c:forEach items="${wdata}" var="w">
                                        <option value="${w.getWarehouseID()}"><c:out value="${w.getWarehouseName() != null ? w.getWarehouseName() : ''}" /></option>
                                    </c:forEach>
                                </select>
                                <select name="supplierID" id="storeSelect" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 6px; border: 1px solid #ccc;">
                                    <c:forEach items="${spdata}" var="sp">
                                        <option value="${sp.getSupplierID()}"><c:out value="${sp.getSupplierName() != null ? sp.getSupplierName() : ''}" /></option>
                                    </c:forEach>
                                </select>

                                <!-- Buttons -->
                                <div class="modal-buttons">
                                    <button type="submit" name="addPurchase" class="btn btn-primary">Xác nh?n</button>
                                    <button type="button" onclick="closeVariantModal()" class="btn btn-secondary">H?y</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- Modal choose and confirm Store Fund -->
                    <div id="confirmModal" class="overlay" style="display: none;">
                        <div class="modal-content">
                            <h4>Confirm Purchase</h4>
                            <form id="confirmForm" action="purchasecontroller" method="post">
                                <input type="hidden" name="id" id="modalPurchaseID" />

                                <label for="storeFund">Choose Store Fund:</label>
                                <select name="storeFund" id="storeFund" required>
                                    <c:forEach items="${sfdata}" var="sf">
                                        <option value="${sf.getFundID()}">${sf.getFundName()}</option>
                                    </c:forEach>
                                </select>

                                <div class="modal-buttons" style="margin-top: 15px;">
                                    <button type="submit" name="confirmPurchase" class="btn btn-primary">Confirm</button>
                                    <button type="button" class="btn btn-secondary" onclick="closeConfirmModal()">Cancel</button>
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

            // ?óng n?u click bên ngoài
            window.addEventListener("click", function (e) {
                const modal = document.getElementById("variantInputModal");
                if (e.target === modal) {
                    closeVariantModal();
                }
            });
        </script>
        <c:if test="${not empty sessionScope.errDelete}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Delete failed',
                        html: '${sessionScope.errDelete}',
                        confirmButtonColor: '#d33'
                    });
                });
            </script>
            <c:remove var="errDelete" scope="session"/>
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
        <script>
                function openConfirmModal(purchaseId) {
                    document.getElementById('modalPurchaseID').value = purchaseId;
                    document.getElementById('confirmModal').style.display = 'flex';
                }

                function closeConfirmModal() {
                    document.getElementById('confirmModal').style.display = 'none';
                }
        </script>

    </body>
</html>