<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span> People</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="<%=path%>/ListCustomerServlet">Customer List</a></li>
                        <li><a href="<%=path%>/SaveCustomerServlet">Add Customer </a></li>
                        <li><a href="<%=path%>/ListUserServlet">User List</a></li>
                        <li><a href="<%=path%>/SaveUserServlet">Add User</a></li>
                        <li><a href="<%=path%>/ListUserPermissionServlet">Manage Permissions</a></li>
                    </ul>
                </li> 
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="<%=path%>/view/assets/img/icons/users1.svg" alt="img"><span>Nhân viên</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="<%=path%>/ListShiftServlet">Ca làm việc</a></li>
                        <li><a href="<%=path%>/ListWorkScheduleServlet">Lịch làm việc</a></li>
                        <li><a href="<%=path%>/ListAttendanceServlet">Bảng chấm công</a></li>
                    </ul>
                </li> 
            </ul>
        </div>
    </div>
</div>