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
    </div>

    <!-- Navigation Menu -->
    <ul class="nav-menu">
        <li class="nav-item">
            <a href="#" class="nav-link">
                <i class="fas fa-info-circle"></i>
                <span>Thông tin gian hàng</span>
            </a>
        </li>
        
        <div class="nav-category">Gói dịch vụ</div>
        
        <li class="nav-item">
            <a href="#" class="nav-link active">
                <i class="fas fa-box"></i>
                <span>Gói dịch vụ</span>
            </a>
        </li>
        
        <li class="nav-item">
            <a href="#" class="nav-link">
                <i class="fas fa-history"></i>
                <span>Lịch sử mua hàng</span>
            </a>
        </li>
        
        <li class="nav-item">
            <a href="#" class="nav-link">
                <i class="fas fa-certificate"></i>
                <span>Chứng nhận dịch vụ</span>
            </a>
        </li>

        <div class="nav-category">Khác</div>
        
        <li class="nav-item">
            <a href="#" class="nav-link">
                <i class="fas fa-headset"></i>
                <span>Hỗ trợ khách hàng</span>
            </a>
        </li>
        
        <li class="nav-item">
            <a href="#" class="nav-link">
                <i class="fas fa-credit-card"></i>
                <span>Thanh toán</span>
            </a>
        </li>
    </ul>
</div>