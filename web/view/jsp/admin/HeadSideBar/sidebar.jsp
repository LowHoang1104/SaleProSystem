<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="active">
                    <a href="<%=path%>/HomepageController"><img src="<%=path%>/view/assets/img/icons/dashboard.svg" alt="img"><span> Tổng quan</span> </a>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/view/assets/img/icons/product.svg" alt="img"><span> Product</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=1">Product List</a></li>
                                <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=2">Add Product</a></li>
                                <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=3">Attributes List</a></li>
                                <li><a href="${pageContext.request.contextPath}/productsidebarcontroller?mode=4">Checking Inventory</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/view/assets/img/icons/store.svg" alt="img"><span> Logistics</span> <span class="menu-arrow"></span></a>
                            <ul>
                                <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=1">Store List</a></li>
                                <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=2">Warehouse List</a></li>
                                <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=4">Purchase List</a></li>
                                <li><a href="${pageContext.request.contextPath}/logisticscontroller?mode=5">Supplier List</a></li>
                                <li><a href="${pageContext.request.contextPath}/supplierreport">Report Supplier</a></li>
                                <li><a href="${pageContext.request.contextPath}/productreport">Report Product</a></li>
                            </ul>
                        </li>
                    </ul>

                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/sales1.svg" alt="img"><span> Đơn hàng</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/InvoiceManagementServlet?action=invoices" title="Xem danh sách hóa đơn">Hóa đơn</a></li>
                        <li><a href="${pageContext.request.contextPath}/InvoiceManagementServlet?action=orders" title="Xem danh đặt hàng">Hóa đơn</a></li>
                        <li><a href="pos.html">Trả hàng</a></li>
                        <li><a href="salesreturnlists.html">Đối tác giao hàng</a></li>
                        <li><a href="createsalesreturns.html">Vận đơn</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/purchase1.svg" alt="img"><span> Khách hàng</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/ListCustomerServlet">Khách hàng</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span> Nhân viên</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/ListUserServlet">Danh sách nhân viên</a></li>
                        <li><a href="${pageContext.request.contextPath}/ListUserPermissionServlet">Quản lí quyền của nhân viên</a></li>
                        <li><a href="${pageContext.request.contextPath}/ListShiftServlet">Danh sách ca làm việc</a></li>
                        <li><a href="${pageContext.request.contextPath}/ListWorkScheduleServlet">Lịch làm việc</a></li>
                        <li><a href="${pageContext.request.contextPath}/ListAttendanceServlet">Bảng chấm công</a></li>
                        <li><a href="${pageContext.request.contextPath}/ListSalaryServlet">Thiết lập lương</a></li>
                        <li><a href="${pageContext.request.contextPath}/PayrollServlet">Bảng lương</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/transfer1.svg" alt="img"><span> Sổ quỹ</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="<%=path%>/cashbookController">Sổ quỹ</a></li>
                    </ul>
                </li>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/transfer1.svg" alt="img"><span> Báo cáo</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/DailyReportServlet">Cuối ngày</a></li>
                        <li><a href="<%=path%>/view/jsp/admin/Reports/SupplierReport.jsp">Nhà cung cấp</a></li>
                        <li><a href="${pageContext.request.contextPath}/SalesReportServlet">Bán hàng</a></li>
                        <li><a href="<%=path%>/EmployeeReport">Nhân viên</a></li>
                        <li><a href="<%=path%>/view/jsp/admin/Reports/OrderReport.jsp">Đặt hàng</a></li>
                        <li><a href="<%=path%>/view/jsp/admin/Reports/ChannelReport.jsp">Kênh bán hàng</a></li>
                        <li><a href="<%=path%>/view/jsp/admin/Reports/ProductReport.jsp">Hàng hóa</a></li>
                        <li><a href="<%=path%>/FinancialReport">Tài chính</a></li>
                        <li><a href="${pageContext.request.contextPath}/CustomerReportServlet">Khách hàng</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>