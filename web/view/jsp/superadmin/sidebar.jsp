<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<div class="sidebar">
    <div class="sidebar-header">
        <h2><i class="fas fa-crown"></i> Super Admin</h2>
        <p>SalePro</p>
    </div>
    <ul class="nav-menu">
        <li class="nav-item">
            <a href="<%=path%>/view/jsp/superadmin/shop-owners.jsp" class="nav-link active">
                <i class="fas fa-store"></i>
                Management ShopOwners
            </a>
        </li>

    </ul>
</div>