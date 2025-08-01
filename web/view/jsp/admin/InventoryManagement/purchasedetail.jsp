<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
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
                            <h4>Purchase Detail List</h4>
                            <h6>Manage your products</h6>
                        </div>
                        <div class="page-btn">
                            <a href="#" id="addVariant" class="btn btn-added">
                                <img src="${pageContext.request.contextPath}/view/assets/img/icons/plus.svg" class="me-2" alt="img">
                                Add Purchase Detail
                            </a>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <!--                            <div class="table-top">
                                                            <div class="search-set">                
                                                                <form action="${pageContext.request.contextPath}/productcontroller" method="post" style="display: flex; gap: 8px; align-items: center;">
                                                                    <input type="text" name="kw" placeholder="Search..." 
                                                                           style="padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
                                                                    <input type="submit" name="search" value="Search" 
                                                                           style="padding: 6px 12px; background-color: green; color: white; border: none; border-radius: 4px; cursor: pointer;">
                                                                </form>
                                                            </div>
                                                        </div>-->
                            <div class="wordset">
                                <ul>
                                    <li>             
                                        <form method="post" action="excelcontroller">
                                            <input type="hidden" name="id" value="${pcid}">
                                            <button data-bs-toggle="tooltip" data-bs-placement="top" title="excel" type="submit" name="type" value="purchasedetail"><img src="${pageContext.request.contextPath}/view/assets/img/icons/excel.svg" alt="img"></button>
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
                                        <th>Product Variant</th>
                                        <th>Quantity</th>
                                        <th>Cost Price</th>                                           
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="counter" value="1" />
                                    <c:forEach items="${pddata}" var="pd" varStatus="stt">
                                        <tr>
                                            <td>${counter}</td>
                                            <c:set var="counter" value="${counter + 1}" />
                                            <td>${pd.productVarianttoString()}</td>
                                            <td>${pd.getQuantity()}</td>
                                            <td><fmt:formatNumber value="${pd.getCostPrice()}" pattern="#,###" /></td>
                                            <td>
                                                <button style="border: none; background: none; padding: 0; margin-right: 15px" type="button" class="btn-edit" 
                                                        data-pcid="${pcid}"
                                                        data-pvid="${pd.getProductID()}"
                                                        data-qty="${pd.getQuantity()}"
                                                        data-price="${pd.getCostPrice()}">
                                                    <img src="${pageContext.request.contextPath}/view/assets/img/icons/edit.svg" alt="img">
                                                </button>
                                                <form action="purchasecontroller" method="post" style="display: inline;">
                                                    <input type="hidden" name="id" value="${pcid}" />
                                                    <input type="hidden" name="productVariantID" value="${pd.getProductID()}" />

                                                    <button type="submit"
                                                            name="deleteDetail"
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
                    <!--Modal Input-->
                    <div id="variantInputModal" class="overlay">
                        <div class="modal-content">
                            <h4 id="variantModalTitle">Select Product Variants</h4>
                            <form id="variantForm" action="${pageContext.request.contextPath}/purchasecontroller" method="post">
                                <input type="hidden" name="id" value="${pcid}">
                                <input type="hidden" name="code" value="${productcode}">

                                <!-- Input + Search -->
                                <div class="form-group-row">
                                    <div class="form-group" style="flex: 2;">
                                        <label>Input</label>
                                        <input type="text" name="productcode" />
                                    </div>
                                    <div class="form-group" style="flex: 1; align-self: flex-end;">
                                        <button type="submit" name="searchcode" class="btn btn-search">Search</button>
                                    </div>
                                </div>

                                <!-- Size + Color -->
                                <div class="form-group-row">
                                    <div class="form-group">
                                        <label>Size</label>
                                        <select name="size">
                                            <c:forEach items="${sdata}" var="s">
                                                <option value="${s.getSizeID()}">${s.getSizeName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>Color</label>
                                        <select name="color">
                                            <c:forEach items="${cldata}" var="cl">
                                                <option value="${cl.getColorID()}">${cl.getColorName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <!-- Unit + Avg Quantity -->
                                <div class="form-group-row">
                                    <div class="form-group">
                                        <label>Unit</label>
                                        <input type="text" name="unit" />
                                    </div>
                                    <div class="form-group">
                                        <label>Average Quantity</label>
                                        <input type="text" name="averageQuantity" value="50" />
                                    </div>
                                </div>
                                <div class="form-group-row">
                                    <div class="form-group" style="flex: 0 0 33%;">
                                        <button type="submit" name="addVariant" class="btn btn-add">Add</button>
                                    </div>
                                </div>
                                <!-- Variant table -->
                                <div class="scrollable">
                                    <table>
                                        <thead>
                                            <tr style="background-color: #f0f0f0;">
                                                <th>Select</th>
                                                <th>Variant</th>
                                                <th>Quantity</th>
                                                <th>Cost Price</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pvdata}" var="pv">
                                                <tr>
                                                    <td><input type="checkbox" name="variantIds" value="${pv.getId()}"></td>
                                                    <td>${pv.productVarianttoString()}</td>
                                                    <td>
                                                        <input type="number" name="quantity_${pv.getId()}" min="0" value="0" style="width: 80px;">
                                                    </td>
                                                    <td>
                                                        <div class="cost-cell">
                                                            <button type="button" class="btn btn-secondary" onclick="decrease('cost_${pv.getId()}')">-</button>
                                                            <input type="text" id="cost_${pv.getId()}" name="costPrice_${pv.getId()}" value="0" class="form-control" style="width: 100px; text-align: right;">
                                                            <button type="button" class="btn btn-secondary" onclick="increase('cost_${pv.getId()}')">+</button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="button-group">
                                    <button type="submit" name="addDetail" class="btn-primary">Save Selected</button>
                                    <button type="button" class="btn-cancel" onclick="closeVariantModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>


                    <!--Modal Edit-->
                    <div id="editModal" class="overlay">
                        <div class="modal-content">
                            <form method="post" action="purchasecontroller">
                                <input type="hidden" name="purchaseID" id="modalPurchaseID">
                                <input type="hidden" name="productVariantID" id="modalProductVariantID">
                                <input type="hidden" name="id" value="${pcid}">

                                <div class="form-row">
                                    <div class="form-quantity">
                                        <label for="modalQuantity">Quantity</label>
                                        <input type="number" name="quantity" id="modalQuantity" min="0">
                                    </div>

                                    <div class="form-cost">
                                        <label for="modalCostPrice">Cost Price (?)</label>
                                        <div class="cost-cell">
                                            <button type="button" class="btn btn-secondary" onclick="decrease('modalCostPrice')">-</button>
                                            <input type="text" id="modalCostPrice" name="costPrice" oninput="formatCurrencyVN(this)" class="form-control" style="flex: 1;">
                                            <button type="button" class="btn btn-secondary" onclick="increase('modalCostPrice')">+</button>
                                        </div>
                                    </div>
                                </div>

                                <div class="button-group">
                                    <button type="submit" name="editDetail" class="btn-primary">Save</button>
                                    <button type="button" class="btn-cancel" onclick="closeModal()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>

            </div>
        </div>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery-3.6.0.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/feather.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.slimscroll.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/js/dataTables.bootstrap4.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/js/bootstrap.bundle.min.js"></script>

        <script src="${pageContext.request.contextPath}/view/assets/plugins/select2/js/select2.min.js"></script>

<!--        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/assets/plugins/sweetalert/sweetalerts.min.js"></script>-->

        <script src="${pageContext.request.contextPath}/view/assets/js/script.js"></script>
        <script>
                                        document.addEventListener("DOMContentLoaded", function () {
                                            // ===== Mở modal Add =====
                                            document.getElementById("addVariant")?.addEventListener("click", function (e) {
                                                e.preventDefault();
                                                openVariantModal();
                                            });

                                            // ===== Mở modal Edit =====
                                            document.querySelectorAll(".btn-edit").forEach(function (btn) {
                                                btn.addEventListener("click", function () {
                                                    const rawPrice = parseFloat(btn.dataset.price || 0);
                                                    document.getElementById("modalPurchaseID").value = btn.dataset.pcid;
                                                    document.getElementById("modalProductVariantID").value = btn.dataset.pvid;
                                                    document.getElementById("modalQuantity").value = btn.dataset.qty;
                                                    document.getElementById("modalCostPrice").value = rawPrice.toLocaleString("vi-VN");
                                                    document.getElementById("editModal").style.display = "flex";
                                                });
                                            });

                                            // ===== Đóng modal khi click ngoài modal-content =====
                                            document.querySelectorAll(".overlay").forEach(function (modal) {
                                                modal.addEventListener("click", function (e) {
                                                    if (!e.target.closest(".modal-content")) {
                                                        modal.style.display = "none";
                                                        document.body.classList.remove("modal-open");
                                                    }
                                                });
                                            });

                                            // ===== Cancel button =====
                                            document.querySelectorAll(".btn-cancel").forEach(btn => {
                                                btn.addEventListener("click", function () {
                                                    closeAllModals();
                                                });
                                            });

                                            // ===== Submit form - làm sạch tiền VN =====
                                            const variantForm = document.getElementById("variantForm");
                                            if (variantForm) {
                                                variantForm.addEventListener("submit", function () {
                                                    document.querySelectorAll("input[name^='costPrice_']").forEach(input => {
                                                        input.value = unformatNumber(input.value);
                                                    });
                                                });
                                            }

                                            // ===== Tự mở modal nếu đang search =====
                                            const isSearch = ${isSearch == true ? "true" : "false"};
                                            if (isSearch === "true") {
                                                openVariantModal();
                                            }

                                            // ===== Mở modal nếu có lỗi Add =====
            <% if (session.getAttribute("err") != null) { %>
                                            openVariantModal();
                                            const errorMsg = document.createElement("div");
                                            errorMsg.innerHTML = '<%= session.getAttribute("err").toString().replaceAll("'", "\\\\'").replaceAll("\n", "<br>") %>';
                                            errorMsg.style.color = "red";
                                            errorMsg.style.marginTop = "8px";
                                            document.querySelector("#variantModalTitle")?.insertAdjacentElement("afterend", errorMsg);
            <% session.removeAttribute("err"); %>
            <% } %>

                                            // ===== Mở modal nếu có lỗi Edit =====
            <% if (session.getAttribute("errEdit") != null) { %>
                                            document.getElementById("editModal").style.display = "flex";
                                            document.getElementById("modalPurchaseID").value = "${pcid}";
                                            document.getElementById("modalProductVariantID").value = "${pvid}";
                                            document.getElementById("modalQuantity").value = "${qty}";
                                            document.getElementById("modalCostPrice").value = (parseFloat("${price}") || 0).toLocaleString("vi-VN");
                                            const errMsg = document.createElement("div");
                                            errMsg.innerHTML = "${sessionScope.errEdit}";
                                            errMsg.style.color = "red";
                                            errMsg.style.marginBottom = "10px";
                                            document.querySelector("#editModal form").prepend(errMsg);
            <% session.removeAttribute("errEdit"); %>
            <% } %>

                                            // ===== Format tự động input tiền VN cho từng variant =====
            <% 
            List<salepro.models.ProductVariants> pvdata = (List<salepro.models.ProductVariants>) request.getAttribute("pvdata");
            for (salepro.models.ProductVariants pv : pvdata) {
            %>
                                            setupNumericInput("cost_<%= pv.getId() %>");
            <% } %>
                                        });

                                        // ===== Functions =====
                                        function openVariantModal() {
                                            const modal = document.getElementById("variantInputModal");
                                            if (modal) {
                                                modal.style.display = "flex";
                                                document.body.classList.add("modal-open");
                                            }
                                        }

                                        function closeAllModals() {
                                            document.querySelectorAll(".overlay").forEach(modal => {
                                                modal.style.display = "none";
                                            });
                                            document.body.classList.remove("modal-open");
                                        }

                                        function formatNumber(num) {
                                            return num.toLocaleString("vi-VN");
                                        }

                                        function unformatNumber(str) {
                                            if (!str)
                                                return 0;
                                            return parseInt(str.replace(/\./g, '').replace(/[^\d]/g, '')) || 0;
                                        }

                                        function setupNumericInput(id) {
                                            const input = document.getElementById(id);
                                            if (input) {
                                                input.addEventListener("input", function () {
                                                    const value = unformatNumber(this.value);
                                                    this.value = formatNumber(value);
                                                });
                                            }
                                        }

                                        function increase(id) {
                                            const input = document.getElementById(id);
                                            if (input) {
                                                let value = unformatNumber(input.value);
                                                value += 1000;
                                                input.value = formatNumber(value);
                                            }
                                        }

                                        function decrease(id) {
                                            const input = document.getElementById(id);
                                            if (input) {
                                                let value = unformatNumber(input.value);
                                                value = Math.max(0, value - 1000);
                                                input.value = formatNumber(value);
                                            }
                                        }
        </script>
        <c:if test="${isSearch == true}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    openVariantModal();
                });
            </script>
        </c:if>

    </body>
</html>