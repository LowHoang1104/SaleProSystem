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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/logistics/logistics.css">
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
                            <h4>Store List</h4>
                            <h6>Manage your Store</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Store</a>
                        </div>
                    </div>
                    <!-- Modal chứa form -->
                    <div id="atbInputModal" style="display: none;" class="overlay">
                        <div class="modal-content">
                            <form id="colorForm" action="${pageContext.request.contextPath}/storecontroller" method="post">

                                <!-- Input 1: Store Name -->
                                <label for="colorName">Store Name:</label><br>
                                <input type="text" name="storeName" id="colorName" placeholder="Ví dụ: Cửa hàng Nguyễn Trãi,..." /><br>

                                <!-- Input 2: Store Address -->
                                <label for="colorCode">Store Address:</label><br>
                                <input type="text" name="storeAddress" id="colorCode" placeholder="Ví dụ: 123 Nguyễn Trãi, Hà Nội" /><br>

                                <!-- Input 3: Store Phone -->
                                <label for="description">Description:</label><br>
                                <input type="text" name="description" id="description" placeholder="Ví dụ:09********" /><br>

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
                                    <div>
                                        <form id="frm" action="${pageContext.request.contextPath}/storecontroller" method="post" style="display: flex">
                                            <input  type="text" name="kw" placeholder="Search...">
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   
                                    </div>
                                </div>
                            </div>

                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <input type="text" placeholder="Enter Brand Name">
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="form-group">
                                                <input type="text" placeholder="Enter Brand Description">
                                            </div>
                                        </div>
                                        <div class="col-lg-1 col-sm-6 col-12 ms-auto">
                                            <div class="form-group">
                                                <a class="btn btn-filters ms-auto"><img src="${pageContext.request.contextPath}/view/assets/img/icons/search-whites.svg" alt="img"></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <table class="table datanew">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Store ID</th>
                                            <th>Store Name</th>
                                            <th>Address</th>
                                            <th>Phone</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="counter" value="1" />
                                        <c:forEach items="${stdata}" var="st" varStatus="stt">
                                            <tr>
                                                <td>${counter}</td>
                                                <c:set var="counter" value="${counter + 1}" />
                                                <td>${st.getStoreID()}</td>
                                                <td>${st.getStoreName()}</td>
                                                <td>${st.getAddress()}</td>
                                                <td>${st.getPhone()}</td>
                                                <td>
                                                    <button type="button" class="btn-edit-store"
                                                            data-id="${st.getStoreID()}"
                                                            data-name="${st.getStoreName()}"
                                                            data-addr="${st.getAddress()}"
                                                            data-phone="${st.getPhone()}"
                                                            style="border: none; background: none; padding: 0; margin-right: 15px">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="Edit">
                                                    </button>

                                                    <form action="storecontroller" method="post" style="display: inline;">
                                                        <input type="hidden" name="id" value="${st.getStoreID()}" />
                                                        <button type="submit"
                                                                name="deleteStore"
                                                                class="me-3"
                                                                style="border: none; background: none; padding: 0;">
                                                            <img src="${pageContext.request.contextPath}/view/assets/img/icons/delete.svg" alt="delete">
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- Modal cập nhật Store -->
                    <div id="editStoreModal" class="overlay" style="display: none;">
                        <div class="modal-content">
                            <form action="storecontroller" method="post">
                                <input type="hidden" name="id" id="editStoreID">

                                <label for="editStoreName">Store Name:</label><br>
                                <input type="text" name="storeName" id="editStoreName" placeholder="e.g. Store Nguyễn Trãi"><br>

                                <label for="editStoreAddress">Address:</label><br>
                                <input type="text" name="storeAddress" id="editStoreAddress" placeholder="e.g. 123 ABC Street"><br>

                                <label for="editStorePhone">Phone:</label><br>
                                <input type="text" name="description" id="editStorePhone" placeholder="e.g. 09********"><br>

                                <div class="modal-buttons">
                                    <button type="submit" name="editStore" class="btn btn-primary">Update</button>
                                    <button type="button" class="btn btn-secondary" onclick="closeEditStoreModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <c:if test="${not empty sessionScope.errEdit}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const modal = document.getElementById("editStoreModal");
                    if (modal) {
                        modal.style.display = "flex";
                        document.body.classList.add("modal-open");

                        const err = document.createElement("div");
                        err.innerHTML = "${sessionScope.errEdit}";
                        err.style.color = "red";
                        err.style.marginTop = "10px";
                        modal.querySelector("form").prepend(err);
                    }

                    // Gán lại giá trị cũ nếu cần
                    document.getElementById("editStoreName").value = "${sessionScope.oldStoreName}";
                    document.getElementById("editStoreAddress").value = "${sessionScope.oldStoreAddress}";
                    document.getElementById("editStorePhone").value = "${sessionScope.oldStorePhone}";
                    document.getElementById("editStoreID").value = "${sessionScope.editStoreID}";
                });
            </script>
            <c:remove var="errEdit" scope="session" />
            <c:remove var="oldStoreName" scope="session" />
            <c:remove var="oldStoreAddress" scope="session" />
            <c:remove var="oldStorePhone" scope="session" />
            <c:remove var="editStoreID" scope="session" />
        </c:if>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".btn-edit-store").forEach(function (btn) {
                    btn.addEventListener("click", function () {
                        document.getElementById("editStoreID").value = btn.dataset.id || "";
                        document.getElementById("editStoreName").value = btn.dataset.name || "";
                        document.getElementById("editStoreAddress").value = btn.dataset.addr || "";
                        document.getElementById("editStorePhone").value = btn.dataset.phone || "";

                        document.getElementById("editStoreModal").style.display = "flex";
                        document.body.classList.add("modal-open");
                    });
                });

                const modal = document.getElementById("editStoreModal");
                const modalContent = modal.querySelector(".modal-content");

                modal.addEventListener("click", function (e) {
                    if (!modalContent.contains(e.target)) {
                        closeEditStoreModal();
                    }
                });
            });

            function closeEditStoreModal() {
                document.getElementById("editStoreModal").style.display = "none";
                document.body.classList.remove("modal-open");
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

        <c:if test="${not empty sessionScope.errAdd}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("atbInputModal").style.display = "flex";
                    document.body.classList.add("modal-open");

                    const errorMsg = document.createElement("div");
                    errorMsg.innerHTML = "${sessionScope.errAdd}";
                    errorMsg.style.color = "red";
                    errorMsg.style.marginTop = "10px";

                    document.querySelector("#atbInputModal .modal-content").prepend(errorMsg);
                });
            </script>
            <c:remove var="errAdd" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.errDelete}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    Swal.fire({
                        icon: 'error',
                        title: 'Cannot delete store',
                        html: '${sessionScope.errDelete}',
                        confirmButtonColor: '#d33'
                    });
                });
            </script>
            <c:remove var="errDelete" scope="session"/>
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