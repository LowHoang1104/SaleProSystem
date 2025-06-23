function showFilterPanel() {
    $('#filterOverlay').fadeIn(300);
    $('#filterPanel').fadeIn(300);

    // Đọc Url
    const urlParams = new URLSearchParams(window.location.search);
    const currentCategoryIds = urlParams.getAll('categoryIds');
    const currentTypeIds = urlParams.getAll('typeIds');

    console.log('Current filters:', {currentCategoryIds, currentTypeIds});
    const postData = {
        action: 'filter',
        currentCategoryIds: currentCategoryIds,
        currentTypeIds: currentTypeIds
    };

    $.ajax({
        url: 'CashierServlet',
        type: 'POST',
        data: postData,
        traditional: true,
        success: function (filterHtml) {
            console.log("Filter loaded successfully");
            $('#filterPanel').html(filterHtml);

            setTimeout(() => {
                initializeFilterPanel();
            }, 100);
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi tải thông tin filter:', error);
        }
    });
}

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

    // Tự động expand categories có checkbox checked
    document.querySelectorAll('.sub-categories').forEach(subCat => {
        const hasCheckedCategories = subCat.querySelectorAll('input[type="checkbox"]:checked').length > 0;
        if (hasCheckedCategories) {
            subCat.style.display = 'block';
            // Update icon
            const typeId = subCat.id.replace('categories_', '');
            const expandIcon = document.querySelector(`[data-type-id="${typeId}"] .fa-chevron-right`);
            if (expandIcon) {
                expandIcon.classList.remove('fa-chevron-right');
                expandIcon.classList.add('fa-chevron-down');
            }
        } else {
            subCat.style.display = 'none';
        }
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
    const categoryRows = document.querySelectorAll('.category-item.category');
    categoryRows.forEach(row => {
        const categoryID = row.dataset.categoryId;
        if (categoryID) {
            row.removeEventListener('click', row.clickHandler);
            row.clickHandler = (event) => handleCategoryRowClick(event, categoryID);
            row.addEventListener('click', row.clickHandler);

            console.log(`Category row ${categoryID} click event attached`);
        }
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
            const changeEvent = new Event('change', {bubbles: true});
            checkbox.dispatchEvent(changeEvent);
        }
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
    if (!selectAllCheckbox)
        return;

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
    const checkedTypes = document.querySelectorAll('.product-type input[type="checkbox"]:checked');
    const totalSelected = checkedCategories.length + checkedTypes.length;

    const applyBtn = document.getElementById('applyBtn');

    if (applyBtn) {
        if (totalSelected > 0) {
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

        if (typeName.includes(searchTerm) || hasMatchingCategory || searchTerm === '') {
            productType.style.display = 'block';

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

    // Reset về trạng thái ban đầu (categories có checked vẫn mở)
    document.querySelectorAll('.sub-categories').forEach(subCat => {
        const hasCheckedCategories = subCat.querySelectorAll('input[type="checkbox"]:checked').length > 0;
        if (!hasCheckedCategories) {
            subCat.style.display = 'none';
            const typeId = subCat.id.replace('categories_', '');
            const expandIcon = document.querySelector(`[data-type-id="${typeId}"] .fa-chevron-down`);
            if (expandIcon) {
                expandIcon.classList.remove('fa-chevron-down');
                expandIcon.classList.add('fa-chevron-right');
            }
        }
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
    const selectedTypes = [];

    // Collect selected categories
    const checkedCategoryCheckboxes = document.querySelectorAll('input[id^="cat_"]:checked');
    checkedCategoryCheckboxes.forEach(checkbox => {
        const categoryId = checkbox.id.replace('cat_', '');
        if (categoryId) {
            selectedCategories.push(categoryId);
        }
    });

    const allTypeCheckboxes = document.querySelectorAll('input[id^="type_"]');

    const checkedTypeCheckboxes = document.querySelectorAll('input[id^="type_"]:checked');

    checkedTypeCheckboxes.forEach(checkbox => {
        const typeId = checkbox.id.replace('type_', '');
        if (typeId) {
            // Kiểm tra xem có category nào của type này được chọn không
            const categoriesDiv = document.getElementById(`categories_${typeId}`);
            const checkedCategoriesInType = categoriesDiv ?
                    categoriesDiv.querySelectorAll('input[type="checkbox"]:checked').length : 0;

            // Nếu type được check nhưng KHÔNG có category nào được chọn
            // OR type được check và indeterminate = false (toàn bộ type)
            if (!checkbox.indeterminate || checkedCategoriesInType === 0) {
                selectedTypes.push(typeId);
            }
        }
    });


    if (selectedCategories.length > 0 || selectedTypes.length > 0) {
        applyFilterToServer(selectedCategories, selectedTypes);
    } else {
        window.location.href = 'CashierServlet';
    }

    hideFilterPanel();
}

// Send filter data to server
function applyFilterToServer(selectedCategories, selectedTypes) {
    const params = new URLSearchParams();

    selectedCategories.forEach(categoryId => {
        params.append('categoryIds', categoryId);
    });

    selectedTypes.forEach(typeId => {
        params.append('typeIds', typeId);
    });

    const redirectUrl = 'CashierServlet' + (params.toString() ? '?' + params.toString() : '');
    window.location.href = redirectUrl;
}

function clearFilters() {
    window.location.href = 'CashierServlet';
}

// ================== EVENT LISTENERS ==================

$(document).ready(function () {
    $(document).on('click', '#applyBtn', function () {
        applyFilters();
    });

    $(document).on('click', '#clearFiltersBtn', function () {
        clearAllFilters();
    });

    $(document).on('change', '.category-tree input[type="checkbox"]', function () {
        setTimeout(() => {
            updateSelectionCount();
            updateApplyButton();
        }, 10);
    });
});

function goToPage(pageNumber) {
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('page', pageNumber);
    window.location.href = currentUrl.toString();
}