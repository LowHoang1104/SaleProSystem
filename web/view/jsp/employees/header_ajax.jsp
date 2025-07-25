<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
/* Simple search dropdown styles */
.search-section {
    position: relative;
}

.search-dropdown {
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    background: white;
    border: 1px solid #ddd;
    border-top: none;
    max-height: 250px;
    overflow-y: auto;
    display: none;
    z-index: 1000;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.search-dropdown.show {
    display: block;
}

.dropdown-item {
    padding: 10px 12px;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
}

.dropdown-item:hover {
    background-color: #f5f5f5;
}

.dropdown-item:last-child {
    border-bottom: none;
}

.product-code {
    font-weight: bold;
    color: #666;
    font-size: 12px;
}

.product-name {
    margin: 3px 0;
    font-size: 14px;
}

.product-price {
    color: #2196F3;
    font-weight: bold;
    font-size: 13px;
}

.no-results {
    padding: 15px;
    text-align: center;
    color: #999;
    font-style: italic;
}

/* Store selector styles */
.store-selector {
    margin-right: 15px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.store-selector label {
    font-size: 13px;
    color: #666;
    white-space: nowrap;
}

.store-selector select {
    padding: 6px 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: white;
    font-size: 13px;
    color: #333;
    min-width: 120px;
    cursor: pointer;
}

.store-selector select:focus {
    outline: none;
    border-color: #2196F3;
    box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.2);
}

.header-actions {
    display: flex;
    align-items: center;
}
</style>

<div id="headerSection">   
    <div class="header">
        <div class="search-section">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" placeholder="Tìm hàng hóa (F3)" id="productSearch">
            </div>
            <div class="search-dropdown" id="searchDropdown"></div>
        </div>
        
        <div class="tab-section">
            <c:forEach var="invoice" items="${sessionScope.invoices}">
                <div class="invoice-tab ${invoice.id == sessionScope.currentInvoiceId ? 'active' : ''}" data-id="${invoice.id}">
                    <i class="fas fa-file-invoice"></i>
                    <span>${invoice.name}</span>
                    <button class="removeInvoiceBtn" data-id="${invoice.id}" title="Xóa hóa đơn">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            </c:forEach>
        </div>
        
        <div class="header-actions">
            <i class="fas fa-plus" id="addInvoiceBtn" title="Thêm hóa đơn"></i>
            <i class="fas fa-money-bill-wave" id="cashManagementBtn" title="Quản lý tiền mặt" onclick="showCashPanel()" style="color: #ff5722; cursor: pointer;"></i>
            
            <!-- Store Selector - Only for Admin (userId == 1) -->
            <c:if test="${sessionScope.user.userId == 1}">
                <div class="store-selector">
                    <label for="storeSelect">
                        <i class="fas fa-store"></i> Cửa hàng:
                    </label>
                    
                    <select id="storeSelect" name="storeId" onchange="changeStore(this.value)">
                        <c:forEach var="store" items="${sessionScope.storecurrent}">
                            <option value="${store.storeID}" 
                                    ${(store.storeID == sessionScope.currentStoreId) || (empty sessionScope.currentStoreId && store.storeID == 1) ? 'selected' : ''}>
                                ${store.storeName} (${store.storeCode})
                            </option>
                        </c:forEach>
                    </select>
                    
                </div>
            </c:if>
            
            <span class="phone-number">${phoneNumber}</span>
            <i class="fas fa-bars" id="menuBtn" title="Menu"></i>
        </div>
    </div>
</div>

<!-- Side Panel -->
<div class="side-panel-overlay" id="sidePanelOverlay">
    <div class="side-panel" id="sidePanel">
        <div class="panel-header">
            <h3>Menu</h3>
            <button class="close-panel" id="closePanelBtn">
                <i class="fas fa-times"></i>
            </button>
        </div>
        <div class="panel-content">
            <div class="menu-section">
                <div class="menu-section-title">Báo cáo</div>
                <div class="menu-item">
                    <i class="fas fa-chart-line"></i>
                    <span>Xem báo cáo cuối ngày</span>
                </div>
            </div>
            <div class="menu-section"> 
                <a href="${pageContext.request.contextPath}/InvoiceManagementServlet">
                    <div class="menu-item">
                        <i class="fas fa-cog"></i>
                        <span>Quản lý</span>
                    </div>
                </a>
            </div>
            <div class="menu-section">
                <div class="menu-item danger">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Đăng xuất</span>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
$(document).ready(function() {
    console.log('=== PRODUCT SEARCH INITIALIZED ===');
    
    // Auto-select store 1 if no store is currently selected
    const storeSelect = $('#storeSelect');
    if (storeSelect.length && !storeSelect.val()) {
        console.log('Auto-selecting store 1');
        storeSelect.val('1');
        
        // Optionally update session automatically
        if (storeSelect.val() === '1') {
            updateCurrentStore(1);
        }
    }
    
    let searchTimeout;

    // Product search
    $('#productSearch').on('input', function() {
        const keyword = $(this).val().trim();
        console.log('Search input:', keyword);
        
        if (keyword.length >= 2) {
            clearTimeout(searchTimeout);
            searchTimeout = setTimeout(function() {
                searchProducts(keyword);
            }, 300);
        } else {
            hideDropdown();
        }
    });

    // Hide dropdown when clicking outside
    $(document).click(function(e) {
        if (!$(e.target).closest('.search-section').length) {
            hideDropdown();
        }
    });

    function searchProducts(keyword) {
        console.log('Searching for:', keyword);
        
        $.ajax({
            url: '${pageContext.request.contextPath}/ProductSearchServlet',
            type: 'GET',
            data: { keyword: keyword },
            success: function(products) {
                console.log('Search results:', products);
                displayResults(products);
            },
            error: function(xhr, status, error) {
                console.error('Search error:', status, error);
                showError();
            }
        });
    }

    function displayResults(products) {
        console.log('Displaying results for', products.length, 'products');
        const dropdown = $('#searchDropdown');
        dropdown.empty();

        if (!products || products.length === 0) {
            dropdown.html('<div class="no-results">Không tìm thấy sản phẩm</div>');
        } else {
            // Use traditional string concatenation instead of template literals
            products.forEach(function(product) {
                var itemHtml = '<div class="dropdown-item">' +
                    '<div class="product-code">' + product.code + '</div>' +
                    '<div class="product-name">' + product.name + '</div>' +
                    '<div class="product-price">' + formatPrice(product.price) + '</div>' +
                    '</div>';
                
                var item = $(itemHtml);
                
                item.click(function() {
                    selectProduct(product);
                });
                
                dropdown.append(item);
            });
        }
        
        showDropdown();
    }

    function selectProduct(product) {
        console.log('Selected product:', product);
        $('#productSearch').val('');
        hideDropdown();
        
        // Call existing addToCart function
        if (typeof addToCart === 'function') {
            addToCart(product.code, product.name, product.price);
        } else {
            console.error('addToCart function not found');
            alert('Lỗi: Không thể thêm sản phẩm vào giỏ hàng');
        }
    }

    function showDropdown() {
        $('#searchDropdown').addClass('show');
    }

    function hideDropdown() {
        $('#searchDropdown').removeClass('show');
    }

    function showError() {
        $('#searchDropdown').html('<div class="no-results">Lỗi tìm kiếm</div>').addClass('show');
    }

    function formatPrice(price) {
        if (typeof price === 'number') {
            return price.toLocaleString('vi-VN') + ' đ';
        }
        return price + ' đ';
    }
    
    // Test function - call this in console: testProductSearch()
    window.testProductSearch = function() {
        console.log('Testing product search...');
        searchProducts('ao');
    };
});

// Store selection function
function changeStore(storeId) {
    if (!storeId) {
        alert('Vui lòng chọn cửa hàng');
        return;
    }
    
    console.log('Changing to store:', storeId);
    
    // Show loading indicator
    const select = document.getElementById('storeSelect');
    const originalText = select.options[select.selectedIndex].text;
    select.options[select.selectedIndex].text = 'Đang chuyển...';
    select.disabled = true;
    
    // Send AJAX request to change store
    $.ajax({
        url: '${pageContext.request.contextPath}/HeaderServlet',
        type: 'POST',
        data: { 
            storeId: storeId,
            action: 'changeStore'
        },
        success: function(response) {
            if (response.success) {
                // Reload page to refresh data for new store
                window.location.reload();
            } else {
                alert('Lỗi: ' + (response.message || 'Không thể chuyển cửa hàng'));
                // Restore original state
                select.options[select.selectedIndex].text = originalText;
                select.disabled = false;
            }
        },
        error: function(xhr, status, error) {
            console.error('Store change error:', error);
            alert('Lỗi kết nối: Không thể chuyển cửa hàng');
            // Restore original state
            select.options[select.selectedIndex].text = originalText;
            select.disabled = false;
        }
    });
}
</script>