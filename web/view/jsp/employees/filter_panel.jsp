<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="filter-header">
    <h3>
        <i class="fas fa-filter"></i>
        Lọc theo nhóm hàng
    </h3>
    <button class="filter-close" onclick="hideFilterPanel()" title="Đóng">
        <i class="fas fa-times"></i>
    </button>
</div>

<div class="filter-content">
    <!-- Search box -->
    <div class="filter-search-section">
        <div class="filter-search-box">
            <i class="fas fa-search"></i>
            <input type="text" id="filterSearch" placeholder="Tìm nhóm hàng" autocomplete="off">
            <button type="button" class="clear-search" title="Xóa tìm kiếm">
                <i class="fas fa-times"></i>
            </button>
        </div>
    </div>

    <!-- Category tree container -->
    <div class="category-tree" id="categoryTree">
        <!-- Tất cả -->
        <div class="category-item main-category">
            <div class="category-header select-all-row">
                <i class="fas fa-list expand-icon"></i>
                <input type="checkbox" id="selectAll">
                <label for="selectAll">Tất cả nhóm hàng</label>
                <span class="selection-count" id="selectionCount">(0 được chọn)</span>
            </div>
        </div>

        <!-- Dynamic content từ database -->
        <div id="productTypesContainer">
            <c:forEach var="productType" items="${listType}">
                <!-- ProductType Item -->
                <div class="category-item product-type" data-type-id="${productType.typeID}">
                    <div class="category-header product-type-row" data-type-id="${productType.typeID}">
                        <!-- Check xem có categories con không -->
                        <c:set var="hasCategories" value="false" />
                        <c:set var="categoryCount" value="0" />
                        <c:set var="selectedInType" value="0" />
                        <c:forEach var="category" items="${listCategory}">
                            <c:if test="${category.typeID == productType.typeID}">
                                <c:set var="hasCategories" value="true" />
                                <c:set var="categoryCount" value="${categoryCount + 1}" />
                                <!-- Đếm categories được chọn -->
                                <c:if test="${not empty currentCategoryIds and currentCategoryIds.contains(category.categoryID.toString())}">
                                    <c:set var="selectedInType" value="${selectedInType + 1}" />
                                </c:if>
                            </c:if>
                        </c:forEach>

                        <!-- Expand icon -->
                        <i class="fas fa-chevron-right expand-icon ${hasCategories ? '' : 'disabled'}" 
                           data-type-id="${productType.typeID}"></i>

                        <!-- Type checkbox với preserved state -->
                        <input type="checkbox" 
                               id="type_${productType.typeID}" 
                               data-type-id="${productType.typeID}"
                               <c:if test="${not empty currentTypeIds and currentTypeIds.contains(productType.typeID.toString())}">checked</c:if>>

                        <!-- Type label -->
                        <label for="type_${productType.typeID}">${productType.typeName}</label>

                        <!-- Selection count -->
                        <span class="selection-count" id="count_${productType.typeID}">
                            (${selectedInType}/${categoryCount} danh mục)
                        </span>
                    </div>

                    <!-- Sub-categories -->
                    <c:if test="${hasCategories}">
                        <div class="sub-categories" id="categories_${productType.typeID}">
                            <c:forEach var="category" items="${listCategory}">
                                <c:if test="${category.typeID == productType.typeID}">
                                    <div class="category-item category" 
                                         data-category-id="${category.categoryID}" 
                                         data-type-id="${productType.typeID}">
                                        <div class="category-header category-row" 
                                             data-category-id="${category.categoryID}">
                                            <span class="expand-icon"></span>
                                            <!-- Category checkbox với preserved state -->
                                            <input type="checkbox" 
                                                   id="cat_${category.categoryID}" 
                                                   data-category-id="${category.categoryID}"
                                                   data-type-id="${productType.typeID}"
                                                   <c:if test="${not empty currentCategoryIds and currentCategoryIds.contains(category.categoryID.toString())}">checked</c:if>>
                                            <label for="cat_${category.categoryID}">${category.categoryName}</label>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
                
            <c:if test="${empty listType}">
                <div class="empty-state">
                    <i class="fas fa-inbox"></i>
                    <h4>Không có dữ liệu</h4>
                    <p>Chưa có nhóm hàng nào trong hệ thống</p>
                </div>
            </c:if>
        </div>
    </div>
</div>

<div class="filter-actions">
    <button class="filter-btn secondary" id="clearFiltersBtn">
        <i class="fas fa-eraser"></i>
        Xóa bộ lọc
    </button>
    <div class="primary-actions">
        <button class="filter-btn secondary" onclick="hideFilterPanel()">
            Hủy
        </button>
        <button class="filter-btn primary" id="applyBtn" disabled>
            <i class="fas fa-check"></i>
            Áp dụng (<span id="selectedCount">0</span>)
        </button>
    </div>
</div>