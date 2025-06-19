//====================================Filter Panel Functions

function showFilterPanel() {
    $('#filterOverlay').fadeIn(300);
    $('#filterPanel').fadeIn(300);

    // Load filter content qua AJAX
    $.ajax({
        url: 'CashierServlet',
        type: 'POST',
        data: {action: 'filter'},
        success: function (filterHtml) {
            console.log("Filter loaded successfully");
            $('#filterPanel').html(filterHtml);
            
            setTimeout(() => {
                initializeFilterPanel();
            }, 100);
        },
        error: function () {
            console.error('Lỗi khi tải thông tin filter');
        }
    });
}

// Ẩn filter panel
function hideFilterPanel() {
    $('#filterOverlay').fadeOut(300);
    $('#filterPanel').fadeOut(300);
}

// Khởi tạo filter panel sau khi load AJAX
function initializeFilterPanel() {
    setupFilterSearch();
    setupCheckboxEvents();
    updateSelectionCount();
    updateApplyButton();
    
    // Ẩn tất cả sub-categories ban đầu
    document.querySelectorAll('.sub-categories').forEach(subCat => {
        subCat.style.display = 'none';
    });
    
    console.log('Filter panel initialized');
}

// ================== CHECKBOX EVENT SETUP ==================

// Thiết lập events cho tất cả checkboxes
function setupCheckboxEvents() {
    console.log('Setting up checkbox events...');
    
    // Event cho "Tất cả nhóm hàng" checkbox
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        selectAllCheckbox.removeEventListener('change', handleSelectAllChange);
        selectAllCheckbox.addEventListener('change', handleSelectAllChange);
        console.log('Select All checkbox event attached');
    }
    
    // Events cho ProductType checkboxes
    const productTypeCheckboxes = document.querySelectorAll('input[id^="type_"]');
    productTypeCheckboxes.forEach(checkbox => {
        const typeID = checkbox.id.replace('type_', '');
        
        checkbox.removeEventListener('change', checkbox.productTypeHandler);
        checkbox.productTypeHandler = (event) => handleProductTypeChange(typeID, event.target);
        checkbox.addEventListener('change', checkbox.productTypeHandler);
        
        console.log(`ProductType checkbox ${typeID} event attached`);
    });
    
    // Events cho Category checkboxes
    const categoryCheckboxes = document.querySelectorAll('input[id^="cat_"]');
    categoryCheckboxes.forEach(checkbox => {
        const categoryID = checkbox.id.replace('cat_', '');
        // Tìm typeID từ parent element
        const productTypeParent = checkbox.closest('.product-type');
        const typeID = productTypeParent ? productTypeParent.dataset.typeId : null;
        
        if (typeID) {
            checkbox.removeEventListener('change', checkbox.categoryHandler);
            checkbox.categoryHandler = (event) => handleCategoryChange(typeID, categoryID, event.target);
            checkbox.addEventListener('change', checkbox.categoryHandler);
            
            console.log(`Category checkbox ${categoryID} event attached`);
        }
    });
    
    // Events cho ProductType click areas (để expand/collapse)
    const productTypeRows = document.querySelectorAll('.product-type-row');
    productTypeRows.forEach(row => {
        const typeID = row.closest('.product-type').dataset.typeId;
        
        row.removeEventListener('click', row.clickHandler);
        row.clickHandler = (event) => handleProductTypeRowClick(event, typeID);
        row.addEventListener('click', row.clickHandler);
        
        console.log(`ProductType row ${typeID} click event attached`);
    });
    
    // Events cho Category click areas
    const categoryRows = document.querySelectorAll('.category-item');
    categoryRows.forEach(row => {
        const categoryID = row.dataset.categoryId;
        
        row.removeEventListener('click', row.clickHandler);
        row.clickHandler = (event) => handleCategoryRowClick(event, categoryID);
        row.addEventListener('click', row.clickHandler);
        
        console.log(`Category row ${categoryID} click event attached`);
    });
    
    console.log('All checkbox events setup completed');
}

// ================== EVENT HANDLERS ==================

// Xử lý khi checkbox "Tất cả nhóm hàng" thay đổi
function handleSelectAllChange(event) {
    const isChecked = event.target.checked;
    console.log('Select All changed to:', isChecked);
    
    // Tìm tất cả checkbox (trừ selectAll)
    const allCheckboxes = document.querySelectorAll('.category-tree input[type="checkbox"]:not(#selectAll)');
    console.log('Found checkboxes to update:', allCheckboxes.length);
    
    // Tạm thời tắt events để tránh infinite loop
    allCheckboxes.forEach(cb => {
        cb.removeEventListener('change', cb.productTypeHandler);
        cb.removeEventListener('change', cb.categoryHandler);
    });
    
    // Set tất cả checkboxes
    allCheckboxes.forEach((cb, index) => {
        cb.checked = isChecked;
        cb.indeterminate = false;
        console.log(`Set checkbox ${index} (${cb.id}) to:`, cb.checked);
    });
    
    // Bật lại events sau một chút
    setTimeout(() => {
        setupCheckboxEvents();
        updateSelectionCount();
        updateApplyButton();
    }, 50);
}

// Xử lý click vào row của ProductType (không phải checkbox)
function handleProductTypeRowClick(event, typeID) {
    // Chỉ toggle nếu không click vào checkbox
    if (event.target.type !== 'checkbox' && !event.target.closest('input[type="checkbox"]')) {
        event.preventDefault();
        toggleProductType(typeID);
    }
}

// Xử lý click vào row của Category (không phải checkbox)
function handleCategoryRowClick(event, categoryID) {
    // Toggle checkbox nếu click vào row (không phải checkbox)
    if (event.target.type !== 'checkbox' && !event.target.closest('input[type="checkbox"]')) {
        event.preventDefault();
        const checkbox = document.getElementById(`cat_${categoryID}`);
        if (checkbox) {
            checkbox.checked = !checkbox.checked;
            
            // Trigger change event
            const changeEvent = new Event('change', { bubbles: true });
            checkbox.dispatchEvent(changeEvent);
        }
    }
}

// ================== MANUAL SELECTION FUNCTIONS ==================

// Function để manually select tất cả (backup method)
function forceSelectAll(shouldCheck) {
    console.log('Force selecting all checkboxes to:', shouldCheck);
    
    // Tìm tất cả checkboxes
    const productTypeCheckboxes = document.querySelectorAll('input[id^="type_"]');
    const categoryCheckboxes = document.querySelectorAll('input[id^="cat_"]');
    
    console.log('ProductType checkboxes found:', productTypeCheckboxes.length);
    console.log('Category checkboxes found:', categoryCheckboxes.length);
    
    // Set ProductType checkboxes
    productTypeCheckboxes.forEach(cb => {
        cb.checked = shouldCheck;
        cb.indeterminate = false;
    });
    
    // Set Category checkboxes  
    categoryCheckboxes.forEach(cb => {
        cb.checked = shouldCheck;
        cb.indeterminate = false;
    });
    
    // Update select all checkbox
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        selectAllCheckbox.checked = shouldCheck;
        selectAllCheckbox.indeterminate = false;
    }
    
    updateSelectionCount();
    updateApplyButton();
}

// Function để select specific ProductType
function selectProductType(typeID, shouldCheck) {
    console.log(`Selecting ProductType ${typeID}:`, shouldCheck);
    
    const typeCheckbox = document.getElementById(`type_${typeID}`);
    if (typeCheckbox) {
        typeCheckbox.checked = shouldCheck;
        handleProductTypeChange(typeID, typeCheckbox);
    }
}

// Function để select specific Category
function selectCategory(categoryID, shouldCheck) {
    console.log(`Selecting Category ${categoryID}:`, shouldCheck);
    
    const categoryCheckbox = document.getElementById(`cat_${categoryID}`);
    if (categoryCheckbox) {
        categoryCheckbox.checked = shouldCheck;
        
        // Tìm typeID
        const productTypeParent = categoryCheckbox.closest('.product-type');
        const typeID = productTypeParent ? productTypeParent.dataset.typeId : null;
        
        if (typeID) {
            handleCategoryChange(typeID, categoryID, categoryCheckbox);
        }
    }
}

// Function để toggle tất cả (có thể gọi từ ngoài)
function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        const newState = !selectAllCheckbox.checked;
        selectAllCheckbox.checked = newState;
        
        // Trigger change event
        const changeEvent = new Event('change', { bubbles: true });
        selectAllCheckbox.dispatchEvent(changeEvent);
        
        // Fallback nếu event không hoạt động
        setTimeout(() => {
            const checkedCount = document.querySelectorAll('input[id^="cat_"]:checked').length;
            const expectedCount = document.querySelectorAll('input[id^="cat_"]').length;
            
            if (newState && checkedCount !== expectedCount) {
                console.log('Event failed, using force method');
                forceSelectAll(newState);
            } else if (!newState && checkedCount > 0) {
                console.log('Event failed, using force method');
                forceSelectAll(newState);
            }
        }, 100);
    }
}

// ================== PRODUCT TYPE FUNCTIONS ==================

// Toggle ProductType (mở/đóng categories con)
function toggleProductType(typeID) {
    const categoriesDiv = document.getElementById(`categories_${typeID}`);
    const expandIcon = document.querySelector(`[data-type-id="${typeID}"] .fa-chevron-right, [data-type-id="${typeID}"] .fa-chevron-down`);

    if (categoriesDiv && !expandIcon.classList.contains('disabled')) {
        // Toggle visibility
        if (categoriesDiv.style.display === 'none' || categoriesDiv.style.display === '') {
            categoriesDiv.style.display = 'block';
            expandIcon.classList.remove('fa-chevron-right');
            expandIcon.classList.add('fa-chevron-down');
        } else {
            categoriesDiv.style.display = 'none';
            expandIcon.classList.remove('fa-chevron-down');
            expandIcon.classList.add('fa-chevron-right');
        }
    }
}

// Xử lý khi checkbox ProductType thay đổi
function handleProductTypeChange(typeID, checkbox) {
    const categoriesDiv = document.getElementById(`categories_${typeID}`);
    
    if (categoriesDiv) {
        const categoryCheckboxes = categoriesDiv.querySelectorAll('input[type="checkbox"]');
        
        // Check/uncheck tất cả categories con
        categoryCheckboxes.forEach(cb => {
            cb.checked = checkbox.checked;
        });
    }

    updateSelectionCount();
    updateApplyButton();
}

// ================== CATEGORY FUNCTIONS ==================

// Xử lý khi checkbox Category thay đổi
function handleCategoryChange(typeID, categoryID, checkbox) {
    const typeCheckbox = document.getElementById(`type_${typeID}`);
    const categoriesDiv = document.getElementById(`categories_${typeID}`);
    
    if (categoriesDiv && typeCheckbox) {
        const categoryCheckboxes = categoriesDiv.querySelectorAll('input[type="checkbox"]');

        // Kiểm tra xem tất cả categories có được check không
        const allChecked = Array.from(categoryCheckboxes).every(cb => cb.checked);
        const anyChecked = Array.from(categoryCheckboxes).some(cb => cb.checked);

        // Cập nhật trạng thái ProductType checkbox
        if (allChecked) {
            typeCheckbox.checked = true;
            typeCheckbox.indeterminate = false;
        } else if (anyChecked) {
            typeCheckbox.checked = false;
            typeCheckbox.indeterminate = true; // Trạng thái "một phần"
        } else {
            typeCheckbox.checked = false;
            typeCheckbox.indeterminate = false;
        }
    }

    updateSelectionCount();
    updateApplyButton();
}

// Click vào category (toggle checkbox)
function handleCategoryClick(categoryID) {
    const checkbox = document.getElementById(`cat_${categoryID}`);
    if (checkbox) {
        checkbox.checked = !checkbox.checked;
        
        // Trigger change event manually
        const changeEvent = new Event('change', { bubbles: true });
        checkbox.dispatchEvent(changeEvent);
    }
}

// ================== SELECT ALL FUNCTIONS ==================

// Xử lý "Tất cả nhóm hàng"
function handleSelectAll(checkbox) {
    console.log('Select All clicked:', checkbox.checked);
    
    // Tick/untick TẤT CẢ checkbox (trừ selectAll)
    const allCheckboxes = document.querySelectorAll('.category-tree input[type="checkbox"]');
    console.log('Found total checkboxes:', allCheckboxes.length);
    
    allCheckboxes.forEach(cb => {
        if (cb.id !== 'selectAll') {
            cb.checked = checkbox.checked;
            cb.indeterminate = false;
        }
    });
    
    updateSelectionCount();
    updateApplyButton();
}

// Toggle select all checkbox
function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        selectAllCheckbox.checked = !selectAllCheckbox.checked;
        handleSelectAll(selectAllCheckbox);
    }
}

// ================== UPDATE FUNCTIONS ==================

// Cập nhật số lượng được chọn
function updateSelectionCount() {
    const checkedCategories = document.querySelectorAll('.category input[type="checkbox"]:checked');
    const count = checkedCategories.length;

    // Cập nhật hiển thị tổng
    const selectionCountEl = document.getElementById('selectionCount');
    const selectedCountEl = document.getElementById('selectedCount');
    
    if (selectionCountEl) {
        selectionCountEl.textContent = `(${count} được chọn)`;
    }
    if (selectedCountEl) {
        selectedCountEl.textContent = count;
    }

    // Cập nhật count cho từng ProductType
    document.querySelectorAll('.product-type').forEach(productTypeDiv => {
        const typeID = productTypeDiv.dataset.typeId;
        const categoriesDiv = document.getElementById(`categories_${typeID}`);
        const countEl = document.getElementById(`count_${typeID}`);
        
        if (categoriesDiv && countEl) {
            const checkedInType = categoriesDiv.querySelectorAll('input[type="checkbox"]:checked').length;
            const totalInType = categoriesDiv.querySelectorAll('input[type="checkbox"]').length;
            countEl.textContent = `(${checkedInType}/${totalInType} danh mục)`;
        }
    });

    // Cập nhật trạng thái "Tất cả" checkbox
    updateSelectAllState();
}

// Cập nhật trạng thái checkbox "Tất cả"
function updateSelectAllState() {
    const selectAllCheckbox = document.getElementById('selectAll');
    if (!selectAllCheckbox) return;

    const allCategoryCheckboxes = document.querySelectorAll('.category input[type="checkbox"]');
    const checkedCategories = document.querySelectorAll('.category input[type="checkbox"]:checked');

    if (checkedCategories.length === 0) {
        selectAllCheckbox.checked = false;
        selectAllCheckbox.indeterminate = false;
    } else if (checkedCategories.length === allCategoryCheckboxes.length) {
        selectAllCheckbox.checked = true;
        selectAllCheckbox.indeterminate = false;
    } else {
        selectAllCheckbox.checked = false;
        selectAllCheckbox.indeterminate = true;
    }
}

// Cập nhật button "Áp dụng"
function updateApplyButton() {
    const checkedCategories = document.querySelectorAll('.category input[type="checkbox"]:checked');
    const applyBtn = document.getElementById('applyBtn');

    if (applyBtn) {
        if (checkedCategories.length > 0) {
            applyBtn.disabled = false;
            applyBtn.classList.remove('disabled');
        } else {
            applyBtn.disabled = true;
            applyBtn.classList.add('disabled');
        }
    }
}

// ================== SEARCH FUNCTIONS ==================

// Khởi tạo tìm kiếm
function setupFilterSearch() {
    const searchInput = document.getElementById('filterSearch');
    const clearBtn = document.querySelector('.clear-search');

    if (searchInput) {
        // Xóa previous event listeners
        searchInput.removeEventListener('input', handleSearchInput);
        searchInput.addEventListener('input', handleSearchInput);
    }

    if (clearBtn) {
        clearBtn.removeEventListener('click', clearSearch);
        clearBtn.addEventListener('click', clearSearch);
    }
}

// Xử lý input search
function handleSearchInput(event) {
    const searchTerm = event.target.value.toLowerCase();
    filterProductTypes(searchTerm);
    
    // Show/hide clear button
    const clearBtn = document.querySelector('.clear-search');
    if (clearBtn) {
        clearBtn.style.display = searchTerm ? 'block' : 'none';
    }
}

// Filter ProductTypes theo search term
function filterProductTypes(searchTerm) {
    const productTypes = document.querySelectorAll('.product-type');
    
    productTypes.forEach(productType => {
        const typeLabel = productType.querySelector('label');
        const typeName = typeLabel ? typeLabel.textContent.toLowerCase() : '';
        const categories = productType.querySelectorAll('.category');
        let hasMatchingCategory = false;

        // Kiểm tra categories con
        categories.forEach(category => {
            const categoryLabel = category.querySelector('label');
            const categoryName = categoryLabel ? categoryLabel.textContent.toLowerCase() : '';
            
            if (categoryName.includes(searchTerm)) {
                category.style.display = 'block';
                hasMatchingCategory = true;
            } else {
                category.style.display = searchTerm === '' ? 'block' : 'none';
            }
        });

        // Hiện ProductType nếu tên match hoặc có category con match
        if (typeName.includes(searchTerm) || hasMatchingCategory || searchTerm === '') {
            productType.style.display = 'block';
            
            // Tự động mở categories khi search (nếu có kết quả)
            if (searchTerm !== '' && hasMatchingCategory) {
                const categoriesDiv = productType.querySelector('.sub-categories');
                if (categoriesDiv) {
                    categoriesDiv.style.display = 'block';
                    const expandIcon = productType.querySelector('.fa-chevron-right, .fa-chevron-down');
                    if (expandIcon) {
                        expandIcon.classList.remove('fa-chevron-right');
                        expandIcon.classList.add('fa-chevron-down');
                    }
                }
            }
        } else {
            productType.style.display = 'none';
        }
    });
}

// Xóa tìm kiếm
function clearSearch() {
    const searchInput = document.getElementById('filterSearch');
    const clearBtn = document.querySelector('.clear-search');
    
    if (searchInput) {
        searchInput.value = '';
        filterProductTypes('');
    }
    
    if (clearBtn) {
        clearBtn.style.display = 'none';
    }
    
    // Đóng tất cả categories
    document.querySelectorAll('.sub-categories').forEach(subCat => {
        subCat.style.display = 'none';
    });
    
    document.querySelectorAll('.fa-chevron-down').forEach(icon => {
        icon.classList.remove('fa-chevron-down');
        icon.classList.add('fa-chevron-right');
    });
}

// ================== ACTION FUNCTIONS ==================

// Xóa tất cả filter
function clearAllFilters() {
    const allCheckboxes = document.querySelectorAll('.category-tree input[type="checkbox"]');
    
    allCheckboxes.forEach(cb => {
        cb.checked = false;
        cb.indeterminate = false;
    });
    
    updateSelectionCount();
    updateApplyButton();
}

// Áp dụng filter
function applyFilters() {
    const selectedCategories = [];
    const checkedCategories = document.querySelectorAll('.category input[type="checkbox"]:checked');
    
    checkedCategories.forEach(checkbox => {
        const categoryItem = checkbox.closest('.category-item');
        if (categoryItem) {
            selectedCategories.push({
                categoryID: categoryItem.dataset.categoryId,
                typeID: categoryItem.dataset.typeId,
                categoryName: checkbox.nextElementSibling.textContent
            });
        }
    });
    
    console.log('Selected categories:', selectedCategories);
    
    if (selectedCategories.length > 0) {
        // TODO: Gửi filter request về server
        filterProductsByCategories(selectedCategories);
    }
    
    // Đóng panel
    hideFilterPanel();
}

// Filter products theo categories đã chọn
function filterProductsByCategories(selectedCategories) {
    // TODO: Implement logic filter products
    console.log('Filtering products by categories:', selectedCategories);
    
    // Có thể gửi AJAX request để filter products
    /*
    $.ajax({
        url: 'CashierServlet',
        type: 'POST',
        data: {
            action: 'filterProducts',
            categories: JSON.stringify(selectedCategories)
        },
        success: function(response) {
            // Update product list
            $('#productList').html(response);
        }
    });
    */
}

// ================== EVENT LISTENERS ==================

// Khởi tạo khi document ready (backup)
$(document).ready(function() {
    // Event delegation cho các elements được load qua AJAX
    $(document).on('change', '.category-tree input[type="checkbox"]', function() {
        // Auto update khi có thay đổi checkbox
        setTimeout(() => {
            updateSelectionCount();
            updateApplyButton();
        }, 10);
    });
});

// Debug function
function debugFilterPanel() {
    console.log('=== FILTER PANEL DEBUG ===');
    console.log('All checkboxes:', document.querySelectorAll('.category-tree input[type="checkbox"]').length);
    console.log('Product type checkboxes:', document.querySelectorAll('.product-type input[type="checkbox"]').length);
    console.log('Category checkboxes:', document.querySelectorAll('.category input[type="checkbox"]').length);
    console.log('Checked categories:', document.querySelectorAll('.category input[type="checkbox"]:checked').length);
    console.log('Select all checkbox:', document.getElementById('selectAll'));
    console.log('Apply button:', document.getElementById('applyBtn'));
}