<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar -->
<div class="sidebar">
    <div class="shop-header">
        <div class="shop-name">
            <i class="fas fa-store"></i>
            <c:out value="${shopName}" default="Cửa hàng" />
        </div>
        
        <div class="trial-status ${remainingDays < 0 ? 'expired' : 'warning'}">
            <i class="fas fa-${remainingDays < 0 ? 'times-circle' : 'exclamation-triangle'}"></i>
            <span>
                <c:choose>
                    <c:when test="${remainingDays < 0}">Đã hết hạn</c:when>
                    <c:when test="${remainingDays == 0}">Hết hạn hôm nay</c:when>
                    <c:otherwise>Còn ${remainingDays} ngày</c:otherwise>
                </c:choose>
            </span>
        </div>
        
        <!-- Nút đăng nhập/chuyển trang - chỉ hiển thị khi không hết hạn -->
        <c:if test="${remainingDays >= 0}">
            <div class="login-section">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <!-- Đã đăng nhập - chuyển về trang chủ cửa hàng -->
                        <a href="${pageContext.request.contextPath}/HomepageController" class="login-btn">
                            <i class="fas fa-home"></i>
                            <span>Về trang chủ</span>
                        </a>
                    </c:when>
                        
                    <c:otherwise>
                        <!-- Chưa đăng nhập - hiển thị nút đăng nhập -->
                        <a href="${pageContext.request.contextPath}/LoginOnwerShop" class="login-btn">
                            <i class="fas fa-sign-in-alt"></i>
                            <span>Đăng nhập cửa hàng</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>
    
    <!-- Navigation Menu -->
    <ul class="nav-menu">
        
        <div class="nav-category">Gói dịch vụ</div>
        
        <li class="nav-item">
            <a href="#" class="nav-link active">
                <i class="fas fa-box"></i>
                <span>Gói dịch vụ</span>
            </a>
        </li>
    </ul>
</div>

<style>
/* CSS cho nút đăng nhập */
.login-section {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.login-btn {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 100%;
    padding: 12px 16px;
    background: linear-gradient(135deg, #4CAF50, #45a049);
    color: white;
    text-decoration: none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    border: none;
    cursor: pointer;
}

.login-btn:hover {
    background: linear-gradient(135deg, #45a049, #3d8b40);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
    color: white;
    text-decoration: none;
}

.login-btn i {
    font-size: 16px;
}

.login-btn span {
    font-weight: 500;
}

/* Responsive cho mobile */
@media (max-width: 768px) {
    .login-btn {
        font-size: 13px;
        padding: 10px 14px;
    }
    
    .login-btn i {
        font-size: 14px;
    }
}

/* Animation khi load */
.login-section {
    animation: fadeInUp 0.5s ease-out;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>