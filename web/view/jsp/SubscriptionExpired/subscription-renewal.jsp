<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gia hạn dịch vụ - ${shopName}</title>
        <!-- Font Awesome Icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="${pageContext.request.contextPath}/view/assets/css/subscription-renewal.css" rel="stylesheet">
        <!-- Meta tags for SEO -->
        <meta name="description" content="Gia hạn dịch vụ quản lý bán hàng cho ${shopName}">
        <meta name="robots" content="noindex, nofollow">
    </head>
    <body>
        <!-- Include Sidebar -->
        <jsp:include page="sidebar.jsp" />

        <!-- Main Content -->
        <div class="main-content">
            <div class="content-wrapper">
                <!-- Include Alert Messages -->
                <jsp:include page="alter-messages.jsp" />

                <!-- Packages Section -->
                <div class="packages-section">
                    <h1 class="section-title">Chọn gói dịch vụ phù hợp</h1>

                    <!-- Include Packages Grid -->
                    <jsp:include page="packages-grid.jsp" />

                </div>
            </div>
        </div>

        <!-- Trước thẻ đóng </body> -->
        <script src="${pageContext.request.contextPath}/view/assets/js/simple-payment.js"></script>
        <!-- JavaScript Variables -->
        <script>
            window.contextPath = '${pageContext.request.contextPath}';
            window.remainingDays = ${remainingDays};
            window.shopId = '${shopId}';
            window.currentStatus = '${currentStatus}';
        </script>
    </body>
</html>