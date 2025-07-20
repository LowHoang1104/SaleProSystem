<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<div class="sidebar">
    <div class="sidebar-header">
        <h2><i class="fas fa-crown"></i> Super Admin</h2>
        <p>SalePro</p>
    </div>
    <ul class="nav-menu">
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/dashboard.jsp" class="nav-link">
                <i class="fas fa-tachometer-alt"></i>
                Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/shop-owners.jsp" class="nav-link active">
                <i class="fas fa-store"></i>
                Management ShopOwners
            </a>
        </li>
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/reports.jsp" class="nav-link">
                <i class="fas fa-chart-bar"></i>
                Report
            </a>
        </li>
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/settings.jsp" class="nav-link">
                <i class="fas fa-cog"></i>
                Setting
            </a>
        </li>
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/admin_portal.jsp" class="nav-link">
                <i class="fas fa-sign-out-alt"></i>
                Log-Out
            </a>
        </li>
    </ul>
</div>