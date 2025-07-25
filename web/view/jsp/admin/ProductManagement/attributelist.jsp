<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <%@include file="../HeadSideBar/header.jsp"%>
            <%@include file="../HeadSideBar/sidebar.jsp"%>
            <c:if test="${sessionScope.atb == 1}">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Category List</h4>
                                <h6>Manage your Category</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Category</a>
                            </div>
                        </div>
                        <!-- Modal chứa form -->
                        <div id="atbInputModal" style="display: none;" class="overlay">
                            <div class="modal-content">
                                <form id="colorForm" action="${pageContext.request.contextPath}/attributecontroller" method="post">
                                    <!-- Thêm select nằm phía trên input -->
                                    <label for="colorGroup">Nhóm màu:</label><br>
                                    <select name="typeID" id="colorGroup" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 6px; border: 1px solid #ccc;">
                                        <c:forEach items="${tdata}" var="t">
                                            <option value="${t.getTypeID()}">${t.getTypeName()}</option>
                                        </c:forEach>
                                    </select>

                                    <!-- Label + Input -->
                                    <label for="colorName">Category Name:</label><br>
                                    <input type="text" name="categoryName" id="colorName" placeholder="Ví dụ: Sơ mi, Áo Phông,..." />

                                    <!-- Buttons -->
                                    <div class="modal-buttons">
                                        <button type="submit" name="add" class="btn btn-primary">Xác nhận</button>
                                        <button type="button" onclick="closeModal()" class="btn btn-secondary">Hủy</button>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <div class="card">
                            <div class="card-body">
                                <div class="table-top">
                                    <div class="search-set">
                                        <div class="search-path">                                           
                                        </div>
                                        <div>
                                            <form id="frm" action="${pageContext.request.contextPath}/attributecontroller" method="post" style="display: flex">
                                                <input  type="text" name="kw" placeholder="Search...">
                                                <input type="submit" name="search" value="Search">
                                                <select name="atb" onchange="document.getElementById('frm').submit()" >
                                                    <option value="1" <c:if test="${sessionScope.atb == 1}">selected</c:if>>Category</option>
                                                    <option value="2" <c:if test="${sessionScope.atb == 2}">selected</c:if>>Product Types</option>
                                                    <option value="3" <c:if test="${sessionScope.atb == 3}">selected</c:if>>Size</option>
                                                    <option value="4" <c:if test="${sessionScope.atb == 4}">selected</c:if>>Color</option>
                                                    </select>
                                                </form>                                   
                                            </div>
                                        </div>
                                        <div class="wordset">
                                            <ul>
                                            </ul>
                                        </div>
                                    </div>


                                    <div class="table-responsive">
                                        <table class="table datanew">
                                            <thead>
                                                <tr>
                                                    <th>No</th>
                                                    <th>Category ID</th>
                                                    <th>Category Name</th>
                                                    <th>Product Type</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="counter" value="1" />
                                            <c:forEach items="${cdata}" var="c" varStatus="stt">
                                                <tr>
                                                    <td>${counter}</td>
                                                    <c:set var="counter" value="${counter + 1}" />
                                                    <td>${c.getCategoryID()}</td>
                                                    <td>${c.getCategoryName()}</td>
                                                    <td>${c.getTypeNameById()}</td>
                                                    <td>
                                                        
                                                        <a class="me-3 confirm-text" href="javascript:void(0);">
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
            </c:if>
            <c:if test="${sessionScope.atb == 2}">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Product Types List</h4>
                                <h6>Manage your Product Types</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Type</a>
                            </div>
                        </div>
                        <!-- Modal chứa form -->
                        <div id="atbInputModal" style="display: none;" class="overlay">
                            <div class="modal-content">
                                <form id="colorForm" action="${pageContext.request.contextPath}/attributecontroller" method="post">
                                    <label for="colorName">Type Name:</label><br>
                                    <input type="text" name="typeName" id="colorName" placeholder="Ví dụ: Áo, Quần, Phụ Kiện,..." />
                                    <div class="modal-buttons">
                                        <button type="submit" name="add" class="btn btn-primary">Xác nhận</button>
                                        <button type="button" onclick="closeModal()" class="btn btn-secondary">Hủy</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <div class="table-top">
                                    <div class="search-set">
                                        <div class="search-path">
                                            
                                        </div>
                                        <div>
                                            <form id="frm" action="${pageContext.request.contextPath}/attributecontroller" method="post" style="display: flex">
                                                <input  type="text" name="kw" placeholder="Search...">
                                                <input type="submit" name="search" value="Search">
                                                <select name="atb" onchange="document.getElementById('frm').submit()" >
                                                    <option value="1" <c:if test="${sessionScope.atb == 1}">selected</c:if>>Category</option>
                                                    <option value="2" <c:if test="${sessionScope.atb == 2}">selected</c:if>>Product Types</option>
                                                    <option value="3" <c:if test="${sessionScope.atb == 3}">selected</c:if>>Size</option>
                                                    <option value="4" <c:if test="${sessionScope.atb == 4}">selected</c:if>>Color</option>
                                                    </select>
                                                </form>                                   
                                            </div>
                                        </div>
                                        <div class="wordset">
                                            <ul>

                                            </ul>
                                        </div>
                                    </div>

                                <div class="table-responsive">
                                    <table class="table datanew">
                                        <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>Type ID</th>
                                                <th>Type Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="counter" value="1" />
                                            <c:forEach items="${tdata}" var="t" varStatus="stt">
                                                <tr>
                                                    <td>${counter}</td>
                                                    <c:set var="counter" value="${counter + 1}" />
                                                    <td>${t.getTypeID()}</td>
                                                    <td>${t.getTypeName()}</td>
                                                    <td>
                                                       
                                                        <a class="me-3 confirm-text" href="javascript:void(0);">
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
            </c:if>
            <c:if test="${sessionScope.atb == 3}">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Size List</h4>
                                <h6>Manage your Size</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Size</a>
                            </div>
                        </div>
                        <!-- Modal chứa form -->
                        <div id="atbInputModal" style="display: none;" class="overlay">
                            <div class="modal-content">
                                <form id="colorForm" action="${pageContext.request.contextPath}/attributecontroller" method="post">
                                    <label for="colorName">Size Name:</label><br>
                                    <input type="text" name="sizeName" id="colorName" placeholder="Ví dụ: M, L, XL,..." />
                                    <div class="modal-buttons">
                                        <button type="submit" name="add" class="btn btn-primary">Xác nhận</button>
                                        <button type="button" onclick="closeModal()" class="btn btn-secondary">Hủy</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-body">
                                <div class="table-top">
                                    <div class="search-set">
                                        <div class="search-path">
                                            
                                        </div>
                                        <div>
                                            <form id="frm" action="${pageContext.request.contextPath}/attributecontroller" method="post" style="display: flex">
                                                <input  type="text" name="kw" placeholder="Search...">
                                                <input type="submit" name="search" value="Search">
                                                <select name="atb" onchange="document.getElementById('frm').submit()" >
                                                    <option value="1" <c:if test="${sessionScope.atb == 1}">selected</c:if>>Category</option>
                                                    <option value="2" <c:if test="${sessionScope.atb == 2}">selected</c:if>>Product Types</option>
                                                    <option value="3" <c:if test="${sessionScope.atb == 3}">selected</c:if>>Size</option>
                                                    <option value="4" <c:if test="${sessionScope.atb == 4}">selected</c:if>>Color</option>
                                                    </select>
                                                </form>                                   
                                            </div>
                                        </div>
                                        <div class="wordset">
                                            <ul>

                                            </ul>
                                        </div>
                                    </div>

                                <div class="table-responsive">
                                    <table class="table datanew">
                                        <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>Size ID</th>
                                                <th>Size Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="counter" value="1" />
                                            <c:forEach items="${sdata}" var="s" varStatus="stt">
                                                <tr>
                                                    <td>${counter}</td>
                                                    <c:set var="counter" value="${counter + 1}" />
                                                    <td>${s.getSizeID()}</td>
                                                    <td>${s.getSizeName()}</td>
                                                    <td>
                                                        
                                                        <a class="me-3 confirm-text" href="javascript:void(0);">
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
            </c:if>
            <c:if test="${sessionScope.atb == 4}">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="page-title">
                                <h4>Color List</h4>
                                <h6>Manage your Color</h6>
                            </div>
                            <div class="page-btn">
                                <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Color</a>
                            </div>
                        </div>
                        <!-- Modal chứa form -->
                        <div id="atbInputModal" style="display: none;" class="overlay">
                            <div class="modal-content">
                                <form id="colorForm" action="${pageContext.request.contextPath}/attributecontroller" method="post">
                                    <label for="colorName">Nhập tên màu:</label><br>
                                    <input type="text" name="colorName" id="colorName" placeholder="Ví dụ: Đỏ, Xanh, Vàng" />
                                    <div class="modal-buttons">
                                        <button type="submit" name="add" class="btn btn-primary">Xác nhận</button>
                                        <button type="button" onclick="closeModal()" class="btn btn-secondary">Hủy</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="table-top">
                                    <div class="search-set">
                                        <div class="search-path">
                                            
                                        </div>
                                        <div>
                                            <form id="frm" action="${pageContext.request.contextPath}/attributecontroller" method="post" style="display: flex">
                                                <input  type="text" name="kw" placeholder="Search...">
                                                <input type="submit" name="search" value="Search">
                                                <select name="atb" onchange="document.getElementById('frm').submit()" >
                                                    <option value="1" <c:if test="${sessionScope.atb == 1}">selected</c:if>>Category</option>
                                                    <option value="2" <c:if test="${sessionScope.atb == 2}">selected</c:if>>Product Types</option>
                                                    <option value="3" <c:if test="${sessionScope.atb == 3}">selected</c:if>>Size</option>
                                                    <option value="4" <c:if test="${sessionScope.atb == 4}">selected</c:if>>Color</option>
                                                    </select>
                                                </form>                                   
                                            </div>
                                        </div>
                                        <div class="wordset">
                                            <ul>

                                            </ul>
                                        </div>
                                    </div>

                                <div class="table-responsive">
                                    <table class="table datanew">
                                        <thead>
                                            <tr>
                                                <th>No</th>
                                                <th>Color ID</th>
                                                <th>Color Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="counter" value="1" />
                                            <c:forEach items="${cldata}" var="cl" varStatus="stt">
                                                <tr>
                                                    <td>${counter}</td>
                                                    <c:set var="counter" value="${counter + 1}" />
                                                    <td>${cl.getColorID()}</td>
                                                    <td>${cl.getColorName()}</td>
                                                    <td>
                                                        <a class="me-3" href="#" onclick="confirmDelete('${pageContext.request.contextPath}/attributecontroller?id=${cl.getColorID()}&mode=2')">
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
            </c:if>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
            <c:if test="${not empty errMessage}">
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi xoá!',
                    text: "${fn:escapeXml(errMessage)}",
                    confirmButtonText: 'OK'
                });
            </c:if>

            <c:if test="${not empty successMessage}">
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công!',
                    text: "${fn:escapeXml(successMessage)}",
                    confirmButtonText: 'OK'
                });
            </c:if>
            });
        </script>


        <script>
            function confirmDelete(url) {
                Swal.fire({
                    title: "Are you sure you want to delete this attribute?",
                    text: "This action can only be performed if there are no products with this attribute!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Xóa',
                    cancelButtonText: 'Hủy'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = url;
                    }
                });
            }
        </script>

        <script>
            document.getElementById("addAtb").addEventListener("click", function (event) {
                event.preventDefault(); // Ngăn reload
                document.getElementById("atbInputModal").style.display = "flex";
            });

            function closeModal() {
                document.getElementById("atbInputModal").style.display = "none";
            }
        </script>

        <c:if test="${not empty err}">
            <script>
                // Mở modal khi có lỗi được truyền từ server
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("atbInputModal").style.display = "flex";
                    document.body.classList.add("modal-open");

                    // Optionally: hiện thông báo lỗi trong modal
                    const errorMsg = document.createElement("div");
                    errorMsg.textContent = "${err}";
                    errorMsg.style.color = "red";
                    errorMsg.style.marginTop = "10px";
                    document.querySelector(".modal-content").prepend(errorMsg);
                });
            </script>
        </c:if>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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