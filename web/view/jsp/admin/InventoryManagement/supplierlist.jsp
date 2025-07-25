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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/logistics/supplier.css">
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
                            <h4>Supplier List</h4>
                            <h6>Manage your Supplier</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Supplier</a>
                        </div>
                    </div>
                    <!-- Supplier Modal -->
                    <div id="atbInputModal" class="overlay" style="display: none;">
                        <div class="modal-content styled-modal">
                            <form id="colorForm" action="${pageContext.request.contextPath}/suppliercontroller" method="post">
                                <h2 class="modal-title">Add Supplier</h2>

                                <div class="form-group">
                                    <label for="colorName">Supplier Name:</label>
                                    <input type="text" name="supplierName" id="colorName" placeholder="Ví dụ: Công ty ABC" required />
                                </div>

                                <div class="form-group">
                                    <label for="contactPerson">Contact Person:</label>
                                    <input type="text" name="contactPerson" id="contactPerson" placeholder="Ví dụ: Nguyễn Văn A" />
                                </div>

                                <div class="form-group">
                                    <label for="phone">Phone:</label>
                                    <input type="text" name="phone" id="phone" placeholder="09********" />
                                </div>

                                <div class="form-group">
                                    <label for="email">Email:</label>
                                    <input type="email" name="email" id="email" placeholder="abc@example.com" />
                                </div>

                                <div class="form-group">
                                    <label for="address">Address:</label>
                                    <input type="text" name="address" id="address" placeholder="123 Nguyễn Trãi, Hà Nội" />
                                </div>

                                <div class="form-group">
                                    <label for="description">Description:</label>
                                    <input type="text" name="description" id="description" placeholder="Ghi chú nếu có..." />
                                </div>

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
                                        <form id="frm" action="${pageContext.request.contextPath}/suppliercontroller" method="post" style="display: flex">
                                            <input  type="text" name="kw" placeholder="Search...">
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   
                                    </div>
                                </div>
                                <div class="wordset">
                                    <ul>
                                        
                                    </ul>
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
                                            <th>Supplier ID</th>
                                            <th>Supplier Name</th>
                                            <th>Contact Person</th>
                                            <th>Phone</th>
                                            <th>Email</th>
                                            <th>Address</th>
                                            <th>Description</th>
                                            <th>Create At</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="counter" value="1" />
                                        <c:forEach items="${spdata}" var="sp" varStatus="stt">
                                            <tr>
                                                <td>${counter}</td>
                                                <c:set var="counter" value="${counter + 1}" />
                                                <td>${sp.getSupplierID()}</td>
                                                <td>${sp.getSupplierName()}</td>
                                                <td>${sp.getContactPerson()}</td>
                                                <td>${sp.getPhone()}</td>
                                                <td>${sp.getEmail()}</td>
                                                <td>${sp.getAddress()}</td>
                                                <td>${sp.getDescription()}</td>
                                                <td>${sp.getCreatedAt()}</td>
                                                <td>
                                                    <!-- Button Edit Supplier -->
                                                    <button type="button" class="btn-edit-supplier"
                                                            data-sid="${sp.getSupplierID()}"
                                                            data-sname="${sp.getSupplierName()}"
                                                            data-contact="${sp.getContactPerson()}"
                                                            data-phone="${sp.getPhone()}"
                                                            data-email="${sp.getEmail()}"
                                                            data-address="${sp.getAddress()}"
                                                            data-desc="${sp.getDescription()}"
                                                            style="border: none; background: none; padding: 0; margin-right: 15px">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="Edit">
                                                    </button>

                                                    <!-- Form Delete Supplier -->
                                                    <form action="suppliercontroller" method="post" style="display: inline;">
                                                        <input type="hidden" name="id" value="${sp.getSupplierID()}" />
                                                        <button type="submit"
                                                                name="deleteSupplier"
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
                    <!-- Modal Update Supplier -->
                    <div id="editSupplierModal" class="overlay" style="display: none;">
                        <div class="modal-content styled-modal">
                            <form action="${pageContext.request.contextPath}/suppliercontroller" method="post">
                                <h2 class="modal-title">Edit Supplier</h2>

                                <input type="hidden" name="supplierID" id="editSupplierID" />

                                <div class="form-group">
                                    <label for="editSupplierName">Supplier Name:</label>
                                    <input type="text" name="supplierName" id="editSupplierName" required />
                                </div>

                                <div class="form-group">
                                    <label for="editContactPerson">Contact Person:</label>
                                    <input type="text" name="contactPerson" id="editContactPerson" />
                                </div>

                                <div class="form-group">
                                    <label for="editPhone">Phone:</label>
                                    <input type="text" name="phone" id="editPhone" />
                                </div>

                                <div class="form-group">
                                    <label for="editEmail">Email:</label>
                                    <input type="email" name="email" id="editEmail" />
                                </div>

                                <div class="form-group">
                                    <label for="editAddress">Address:</label>
                                    <input type="text" name="address" id="editAddress" />
                                </div>

                                <div class="form-group">
                                    <label for="editDescription">Description:</label>
                                    <input type="text" name="description" id="editDescription" />
                                </div>

                                <div class="modal-buttons">
                                    <button type="submit" name="editSupplier" class="btn btn-primary">Cập nhật</button>
                                    <button type="button" onclick="closeEditModal()" class="btn btn-secondary">Hủy</button>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const editButtons = document.querySelectorAll(".btn-edit-supplier");
                const modal = document.getElementById("editSupplierModal");

                editButtons.forEach(btn => {
                    btn.addEventListener("click", function () {
                        document.getElementById("editSupplierID").value = btn.dataset.sid;
                        document.getElementById("editSupplierName").value = btn.dataset.sname;
                        document.getElementById("editContactPerson").value = btn.dataset.contact;
                        document.getElementById("editPhone").value = btn.dataset.phone;
                        document.getElementById("editEmail").value = btn.dataset.email;
                        document.getElementById("editAddress").value = btn.dataset.address;
                        document.getElementById("editDescription").value = btn.dataset.desc;

                        modal.style.display = "flex";
                        document.body.classList.add("modal-open");
                    });
                });
            });

            function closeEditModal() {
                document.getElementById("editSupplierModal").style.display = "none";
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
        <c:if test="${not empty sessionScope.errAdd}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("atbInputModal").style.display = "flex";
                    document.body.classList.add("modal-open");

                    const errorMsg = document.createElement("div");
                    errorMsg.innerHTML = `${sessionScope.errAdd}`;
                    errorMsg.style.color = "red";
                    errorMsg.style.marginTop = "10px";

                    const modal = document.querySelector("#atbInputModal .modal-content");
                    if (modal)
                        modal.prepend(errorMsg);
                });
            </script>
            <c:remove var="errAdd" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.errEdit}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const modal = document.getElementById("editStoreModal");
                    if (modal) {
                        modal.style.display = "flex";
                        document.body.classList.add("modal-open");

                        // Gán lại giá trị nếu có
                        document.getElementById("editStoreID").value = "${sessionScope.oldEditID}";
                        document.getElementById("editStoreName").value = "${fn:escapeXml(sessionScope.oldEditName)}";
                        document.getElementById("editStoreAddress").value = "${fn:escapeXml(sessionScope.oldEditAddress)}";
                        document.getElementById("editStorePhone").value = "${fn:escapeXml(sessionScope.oldEditPhone)}";

                        // Hiện thông báo lỗi
                        const errorMsg = document.createElement("div");
                        errorMsg.innerHTML = "${sessionScope.errEdit}";
                        errorMsg.style.color = "red";
                        errorMsg.style.marginTop = "10px";
                        document.querySelector("#editStoreModal .modal-content").prepend(errorMsg);
                    }
                });
            </script>

            <!-- Xóa session sau khi dùng -->
            <c:remove var="errEdit" scope="session"/>
            <c:remove var="oldEditID" scope="session"/>
            <c:remove var="oldEditName" scope="session"/>
            <c:remove var="oldEditAddress" scope="session"/>
            <c:remove var="oldEditPhone" scope="session"/>
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