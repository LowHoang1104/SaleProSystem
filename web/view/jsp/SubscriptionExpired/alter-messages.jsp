<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Error/Success Messages -->
<c:if test="${not empty errorMessage}">
    <div class="alert error" role="alert">
        <i class="fas fa-exclamation-circle"></i>
        <span><c:out value="${errorMessage}" /></span>
    </div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="alert success" role="alert">
        <i class="fas fa-check-circle"></i>
        <span><c:out value="${successMessage}" /></span>
    </div>
</c:if>

<c:if test="${not empty warningMessage}">
    <div class="alert warning" role="alert">
        <i class="fas fa-exclamation-triangle"></i>
        <span><c:out value="${warningMessage}" /></span>
    </div>
</c:if>

<c:if test="${not empty infoMessage}">
    <div class="alert info" role="alert">
        <i class="fas fa-info-circle"></i>
        <span><c:out value="${infoMessage}" /></span>
    </div>
</c:if>

<style>
    /* Additional alert styles */
    .alert.warning {
        background: linear-gradient(135deg, #fef3c7, #fde68a);
        border: 1px solid #fde68a;
        color: #92400e;
    }
    
    .alert.info {
        background: linear-gradient(135deg, #dbeafe, #bfdbfe);
        border: 1px solid #bfdbfe;
        color: #1e40af;
    }
    
    .alert {
        animation: slideIn 0.3s ease-out;
    }
    
    @keyframes slideIn {
        from {
            transform: translateX(-100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
</style>