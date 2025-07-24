<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Simple Packages Grid -->
<div class="packages-grid">
    <c:forEach var="pkg" items="${packages}">
        <div class="package-card ${pkg.recommended ? 'recommended' : ''} ${pkg.popular ? 'popular' : ''}">

            <!-- Package Badge -->
            <c:if test="${pkg.recommended}">
                <div class="package-badge recommended">Đề xuất</div>
            </c:if>
            <c:if test="${pkg.popular}">
                <div class="package-badge popular">Phổ biến</div>
            </c:if>

            <!-- Package Info -->
            <h3 class="package-name">
                <c:out value="${pkg.packageName}" />
            </h3>

            <div class="package-price">
                <fmt:formatNumber value="${pkg.price}" pattern="#,###" />
                <span class="currency">đ</span>
            </div>

            <div class="package-duration">
                <c:choose>
                    <c:when test="${pkg.durationMonths == 1}">1 tháng</c:when>
                    <c:when test="${pkg.durationMonths == 3}">3 tháng</c:when>
                    <c:when test="${pkg.durationMonths == 6}">6 tháng</c:when>
                    <c:when test="${pkg.durationMonths == 12}">1 năm</c:when>
                    <c:when test="${pkg.durationMonths == 24}">2 năm</c:when>
                    <c:otherwise>${pkg.durationMonths} tháng</c:otherwise>
                </c:choose>
            </div>

            <!-- Package Description -->
            <p class="package-description">
                <c:out value="${pkg.description}" />
            </p>

            <!-- Package Features -->
            <ul class="package-features">
                <li><i class="fas fa-check check-icon"></i> Không giới hạn sản phẩm</li>
                <li><i class="fas fa-check check-icon"></i> Không giới hạn chi nhánh</li>
                <li><i class="fas fa-check check-icon"></i> Báo cáo chuyên sâu</li>
                <li><i class="fas fa-check check-icon"></i> Hỗ trợ 24/7</li>
                <li><i class="fas fa-check check-icon"></i> API tùy chỉnh</li>
                <li><i class="fas fa-check check-icon"></i> Quản lý nhân sự</li>
            </ul>

            <!-- Package Button -->
            <form action="${pageContext.request.contextPath}/payment/create" method="POST">
                <input type="hidden" name="packageId" value="${pkg.packageId}">
                <input type="hidden" name="shopId" value="${shopId}">

                <button type="submit" class="package-button ${pkg.recommended ? 'secondary' : (pkg.popular ? 'premium' : 'primary')}">
                    Mua ngay
                </button>
            </form>
        </div>
    </c:forEach>
</div>