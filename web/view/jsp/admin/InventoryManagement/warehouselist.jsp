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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/assets/css/logistics/purchase.css">

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
                            <h4>Warehouse List</h4>
                            <h6>Manage your Warehouse</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#"  id="addAtb" class="btn btn-added"><img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">Add Warehouse</a>
                        </div>
                    </div>
                    <!-- Modal chứa form -->
                    <div id="atbInputModal" style="display: none;" class="overlay">
                        <div class="modal-content">
                            <form id="colorForm" action="${pageContext.request.contextPath}/warehousecontroller" method="post">

                                <!-- Select Store -->
                                <label for="storeSelect">Store:</label><br>
                                <select name="storeID" id="storeSelect" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 6px; border: 1px solid #ccc;">
                                    <c:forEach items="${stdata}" var="s">
                                        <option value="${s.getStoreID()}">${s.getStoreName()}</option>
                                    </c:forEach>
                                </select>

                                <!-- Input 1: Warehouse Name -->
                                <label for="colorName">Warehouse Name:</label><br>
                                <input type="text" name="warehouseName" id="colorName" placeholder="Ví dụ: Kho tổng, Kho chi nhánh 1,..." /><br>

                                <!-- Input 2: Address -->
                                <label for="colorCode">Address:</label><br>
                                <input type="text" name="warehouseAddress" id="colorCode" placeholder="Ví dụ: 456 Lý Thường Kiệt, TP.HCM" /><br>

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
                                        <form action="${pageContext.request.contextPath}/warehousecontroller" method="post" style="display: flex">
                                            <select name="storeID" id="storeSelect">
                                                <c:forEach items="${stdata}" var="s">
                                                    <option value="${s.getStoreID()}">${s.getStoreName()}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" name="search" value="Search">
                                        </form>                                   
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
                                            <th>
                                                <label class="checkboxs">
                                                    <input type="checkbox" id="select-all">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </th>
                                            <th>WareHouse ID</th>
                                            <th>WareHouse Name</th>
                                            <th>Address</th>
                                            <th>Store</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${wdata}" var="w">
                                            <tr>
                                                <td>
                                                    <label class="checkboxs">
                                                        <input type="checkbox">
                                                        <span class="checkmarks"></span>
                                                    </label>
                                                </td>
                                                <td>${w.getWarehouseID()}</td>
                                                <td>${w.getWarehouseName()}</td>
                                                <td>${w.getAddress()}</td>
                                                <td>${w.getStoreNameById()}</td>
                                                <td>
                                                    <button type="button" class="btn-edit-warehouse"
                                                            data-wid="${w.getWarehouseID()}"
                                                            data-wname="${w.getWarehouseName()}"
                                                            data-addr="${w.getAddress()}"
                                                            data-sid="${w.getStoreID()}"
                                                            style="border: none; background: none; padding: 0; margin-right: 15px">
                                                        <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="Edit">
                                                    </button>

                                                    <form action="warehousecontroller" method="post" style="display: inline;">
                                                        <input type="hidden" name="id" value="${w.getWarehouseID()}" />
                                                        <button type="submit"
                                                                name="deleteWarehouse"
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
                    <!-- Modal Edit Warehouse -->
                    <div id="editWarehouseModal" class="overlay">
                        <div class="modal-content">
                            <form method="post" action="warehousecontroller">
                                <!-- WarehouseID (ẩn, không cho sửa) -->
                                <input type="hidden" name="warehouseID" id="modalWarehouseID">

                                <!-- Warehouse Name -->
                                <div class="form-group">
                                    <label for="modalWarehouseName">Warehouse Name</label>
                                    <input type="text" name="warehouseName" id="modalWarehouseName" class="form-control">
                                </div>

                                <!-- Address -->
                                <div class="form-group">
                                    <label for="modalAddress">Address</label>
                                    <input type="text" name="warehouseAddress" id="modalAddress" class="form-control">
                                </div>

                                <!-- Store Select -->
                                <div class="form-group">
                                    <label for="storeSelect">Store</label>
                                    <select id="storeSelectEdit" name="storeID" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 6px; border: 1px solid #ccc;">
                                        <c:forEach items="${stdata}" var="s">
                                            <option value="${s.getStoreID()}">${s.getStoreName()}</option>
                                        </c:forEach>
                                    </select>

                                </div>

                                <!-- Buttons -->
                                <div class="button-group">
                                    <button type="submit" name="editWarehouse" class="btn-primary">Save</button>
                                    <button type="button" class="btn-cancel" onclick="closeWarehouseModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>


                </div>
            </div>
        </div>

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

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Gán sự kiện click cho các nút sửa Warehouse
                document.querySelectorAll(".btn-edit-warehouse").forEach(function (btn) {
                    btn.addEventListener("click", function () {
                        // Gán dữ liệu vào các input
                        document.getElementById("modalWarehouseID").value = btn.dataset.wid || "";
                        document.getElementById("modalWarehouseName").value = btn.dataset.wname || "";
                        document.getElementById("modalAddress").value = btn.dataset.addr || "";

                        // Gán giá trị cho dropdown store (dùng id riêng: storeSelectEdit)
                        const storeSelect = document.getElementById("storeSelectEdit");
                        const targetStoreID = String(btn.dataset.sid || "");

                        if (storeSelect) {
                            let matched = false;

                            Array.from(storeSelect.options).forEach(function (opt) {
                                if (opt.value === targetStoreID) {
                                    opt.selected = true;
                                    matched = true;
                                } else {
                                    opt.selected = false;
                                }
                            });

                            // Nếu không khớp giá trị nào, chọn option đầu tiên
                            if (!matched && storeSelect.options.length > 0) {
                                storeSelect.selectedIndex = 0;
                            }
                        } else {
                            console.error("⛔ Không tìm thấy #storeSelectEdit trong DOM.");
                        }

                        // Hiện modal
                        document.getElementById("editWarehouseModal").style.display = "flex";
                    });
                });

                // Đóng modal khi click ra ngoài nội dung
                const modal = document.getElementById("editWarehouseModal");
                const modalContent = modal.querySelector(".modal-content");

                modal.addEventListener("click", function (e) {
                    if (!modalContent.contains(e.target)) {
                        closeWarehouseModal();
                    }
                });
            });

            function closeModal() {
                document.getElementById("atbInputModal").style.display = "none";
                document.body.classList.remove("modal-open"); // BỎ KHÓA CUỘN
            }
        </script>

        <script>
            function closeWarehouseModal() {
                document.getElementById("editWarehouseModal").style.display = "none";
            }
            console.log("storeSelect value sau gán cứng:", storeSelect.value);

            Array.from(storeSelect.options).forEach(opt => {
                console.log("Option:", opt.value, opt.text);
            });
        </script>


        <c:if test="${not empty sessionScope.errAdd or not empty sessionScope.errEdit}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const errAdd = `<c:out value="${sessionScope.errAdd}" default="" />`;
                    const errEdit = `<c:out value="${sessionScope.errEdit}" default="" />`;

                    if (errAdd && errAdd.trim() !== "") {
                        const modal = document.getElementById("atbInputModal");
                        if (modal) {
                            modal.style.display = "flex";
                            document.body.classList.add("modal-open");

                            // Hiển thị lỗi dưới form nếu muốn (append vào modal)
                            const alert = document.createElement("div");
                            alert.className = "alert alert-danger";
                            alert.innerHTML = errAdd;
                            modal.querySelector(".modal-content").prepend(alert);
                        }
                    }

                    if (errEdit && errEdit.trim() !== "") {
                        const modal = document.getElementById("editWarehouseModal");
                        if (modal) {
                            modal.style.display = "flex";
                            document.body.classList.add("modal-open");

                            const alert = document.createElement("div");
                            alert.className = "alert alert-danger";
                            alert.innerHTML = errEdit;
                            modal.querySelector(".modal-content").prepend(alert);
                        }
                    }
                });
            </script>
        </c:if>
        <% session.removeAttribute("errAdd"); %>
        <% session.removeAttribute("errEdit"); %>

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