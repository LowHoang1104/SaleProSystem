<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();%>
<div class="sidebar">
    <div class="sidebar-header">
        <h2><i class="fas fa-crown"></i> Super Admin</h2>
        <p>SalePro</p>
    </div>
        <li class="nav-item">
            <a href="<%=path%>/ShopOwnerController" class="nav-link active">
                <i class="fas fa-store"></i>
                Management ShopOwners
            </a>
        </li>
        <li class="nav-item">
            <a href="<%=path%>/TransactionController" class="nav-link">
                <i class="fas fa-chart-bar"></i>
                Transaction
            </a>
        </li>
    </ul>
</div>