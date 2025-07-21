// Enhanced ready function
$(document).ready(function () {
    setupPagination();
    initializePagination();
    console.log('📄 AJAX pagination ready!');
});

document.addEventListener('DOMContentLoaded', function () {

    // Get DOM elements
    const tableHeaderScroll = document.querySelector('.table-header-scroll');
    const tableBodyContainer = document.querySelector('.table-body-container');

    if (!tableHeaderScroll || !tableBodyContainer) {
        console.warn('Table scroll sync elements not found');
        return;
    }

    let isHeaderScrolling = false;
    let isBodyScrolling = false;

    function syncHeaderScroll() {
        if (isHeaderScrolling)
            return;

        isBodyScrolling = true;
        tableHeaderScroll.scrollLeft = tableBodyContainer.scrollLeft;

        setTimeout(() => {
            isBodyScrolling = false;
        }, 10);
    }

    /**
     * Sync body scroll with header scroll
     */
    function syncBodyScroll() {
        if (isBodyScrolling)
            return;

        isHeaderScrolling = true;
        tableBodyContainer.scrollLeft = tableHeaderScroll.scrollLeft;

        setTimeout(() => {
            isHeaderScrolling = false;
        }, 10);
    }

    // Add scroll event listeners
    tableBodyContainer.addEventListener('scroll', syncHeaderScroll);
    tableHeaderScroll.addEventListener('scroll', syncBodyScroll);

    /**
     * Initialize scroll sync
     */
    function initScrollSync() {
        // Ensure both elements start at the same scroll position
        tableHeaderScroll.scrollLeft = 0;
        tableBodyContainer.scrollLeft = 0;
    }

    // Initialize on load
    initScrollSync();

    /**
     * Handle window resize to maintain sync
     */
    window.addEventListener('resize', function () {
        setTimeout(initScrollSync, 100);
    });

    console.log('Invoice table scroll synchronization initialized');
});

$(document).ready(function () {
    setupPagination();
    console.log('📄 AJAX pagination ready!');
});

function setupPagination() {
    // Page size dropdown change
    $(document).on('change', '.pagination-select', function () {
        const newPageSize = parseInt($(this).val());
        loadPage(1, newPageSize); // Reset to page 1 when changing size
    });

    // Page navigation clicks
    $(document).on('click', '.page-link', function (e) {
        e.preventDefault();

        const $this = $(this);
        const $parent = $this.parent();

        // Skip if disabled
        if ($parent.hasClass('disabled')) {
            return false;
        }

        const action = $this.data('action');
        const page = parseInt($this.data('page'));
        const currentPageSize = getCurrentPageSize();

        // Handle different actions
        if (action === 'first') {
            loadPage(1, currentPageSize);
        } else if (action === 'prev') {
            const currentPage = getCurrentPage();
            loadPage(currentPage - 1, currentPageSize);
        } else if (action === 'next') {
            const currentPage = getCurrentPage();
            loadPage(currentPage + 1, currentPageSize);
        } else if (action === 'last') {
            const totalPages = getTotalPages();
            loadPage(totalPages, currentPageSize);
        } else if (!isNaN(page)) {
            loadPage(page, currentPageSize);
        }

        return false;
    });
}

/**
 * UNIFIED Load page via AJAX - Sử dụng InvoiceTimeFilter nếu có
 */
function loadPage(page, pageSize) {
    console.log(`Loading page ${page} with size ${pageSize}`);

    // Check if InvoiceTimeFilter exists and use it
    if (typeof window.InvoiceTimeFilter !== 'undefined') {
        console.log('Using InvoiceTimeFilter system');
        window.InvoiceTimeFilter.loadFilteredInvoices(page);
        return;
    }

    // Fallback to basic pagination if InvoiceTimeFilter not available
    console.log('Using basic pagination fallback');

    // Show loading
    showLoading();

    // Collect any available filter data
    const filterData = {};

    // Try to get time filter from active button
    const activeTimeBtn = document.querySelector('.time-btn.active');
    if (activeTimeBtn) {
        filterData.timeFilter = activeTimeBtn.getAttribute('data-value') || 'today';
        filterData.action = 'filter';
    }

    // Try to get other filters if selects exist
    const paymentMethodSelect = document.querySelector('select[data-filter="paymentMethod"]');
    if (paymentMethodSelect && paymentMethodSelect.value) {
        filterData.paymentMethod = paymentMethodSelect.value;
        filterData.action = 'filter';
    }

    const createdBySelect = document.querySelector('select[data-filter="createdBy"]');
    if (createdBySelect && createdBySelect.value) {
        filterData.createdBy = createdBySelect.value;
        filterData.action = 'filter';
    }

    const soldBySelect = document.querySelector('select[data-filter="soldBy"]');
    if (soldBySelect && soldBySelect.value) {
        filterData.soldBy = soldBySelect.value;
        filterData.action = 'filter';
    }

    // Prepare AJAX data
    const ajaxData = {
        page: page,
        pageSize: pageSize,
        ajax: 'true',
        ...filterData // Include any filter data found
    };

    console.log('AJAX data being sent:', ajaxData);

    // AJAX request
    $.ajax({
        url: 'InvoiceManagementServlet',
        type: 'GET',
        data: ajaxData,
        success: function (data) {
            hideLoading();
            updateContent(data);
            console.log('Page loaded successfully');
        },
        error: function (xhr, status, error) {
            hideLoading();
            console.error('Error loading page:', error);
            alert('Có lỗi xảy ra khi tải trang. Vui lòng thử lại.');
        }
    });
}

/**
 * Initialize time filter button as active on page load
 */
function initializePagination() {
    // Ensure "today" button is active if no button is active
    const activeTimeBtn = document.querySelector('.time-btn.active');
    if (!activeTimeBtn) {
        const todayBtn = document.querySelector('.time-btn[data-value="today"]');
        if (todayBtn) {
            todayBtn.classList.add('active');
            console.log('Initialized "today" as default time filter');
        }
    }

    console.log('Pagination initialized');
}



/**
 * Update content with new data
 */
function updateContent(data) {
    // Parse HTML response
    const $newContent = $(data);

    // Update table body
    const newTableBody = $newContent.find('.invoice-table tbody').html();
    $('.invoice-table tbody').html(newTableBody);

    // Update pagination
    const newPagination = $newContent.find('.fixed-pagination').html();
    $('.fixed-pagination').html(newPagination);

    console.log('Content updated');
}

function showLoading() {
    // Add loading overlay to table
    if ($('.table-loading-overlay').length === 0) {
        const overlay = $(`
                <div class="table-loading-overlay" style="
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    bottom: 0;
                    background: rgba(255, 255, 255, 0.8);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    z-index: 999;
                ">
                    <div style="text-align: center;">
                        <div class="spinner-border text-primary" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                        <div class="mt-2">Đang tải...</div>
                    </div>
                </div>
            `);
        $('.table-body-container').css('position', 'relative').append(overlay);
    }

    // Disable pagination controls
    $('.pagination-select, .page-link').prop('disabled', true).addClass('disabled');
}

function hideLoading() {
    // Remove loading overlay
    $('.table-loading-overlay').remove();

    // Enable pagination controls
    $('.pagination-select, .page-link').prop('disabled', false).removeClass('disabled');
}

/**
 * Get current page from active pagination button
 */
function getCurrentPage() {
    const activePage = $('.page-numbers .active .page-link');
    if (activePage.length) {
        const page = parseInt(activePage.data('page'));
        if (!isNaN(page)) {
            return page;
        }
    }
    return 1;
}

/**
 * Get current page size from dropdown
 */
function getCurrentPageSize() {
    const pageSize = parseInt($('.pagination-select').val());
    return !isNaN(pageSize) ? pageSize : 5;
}

/**
 * Get total pages from pagination UI
 */
function getTotalPages() {
    let maxPage = 1;

    $('.page-numbers .page-link[data-page]').each(function () {
        const page = parseInt($(this).data('page'));
        if (!isNaN(page) && page > maxPage) {
            maxPage = page;
        }
    });

    return maxPage;
}

/*==========================Detail================ */
$(document).ready(function () {
    // Handle view detail button click
    $(document).on('click', '.btn-action[title="Xem chi tiết"]', function (e) {
        e.preventDefault();

        const invoiceId = $(this).data('invoice-id');

        if (!invoiceId) {
            console.error('Invoice ID not found');
            return;
        }

        // Show loading state
        const $button = $(this);
        const originalHtml = $button.html();
        $button.html('<i class="fas fa-spinner fa-spin"></i>').prop('disabled', true);

        // AJAX request to get invoice details
        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'GET',
            data: {
                invoiceId: invoiceId,
                action: 'getDetail'
            },
            success: function (response) {
                // Restore button
                $button.html(originalHtml).prop('disabled', false);

                // Update modal content and show
                updateInvoiceModal(response);
                $('#invoiceModal').modal('show');
            },
            error: function (xhr, status, error) {
                // Restore button
                $button.html(originalHtml).prop('disabled', false);

                console.error('Error loading invoice details:', error);
                alert('Có lỗi xảy ra khi tải chi tiết hóa đơn. Vui lòng thử lại.');
            }
        });
    });


});

/**
 * Update invoice modal with data from servlet response
 */
function updateInvoiceModal(data) {
    console.log('Invoice data received:', data);

    // Update modal header
    updateModalHeader(data);

    // Update invoice info
    updateModalInfo(data);

    // Update products table
    updateModalProducts(data);

    // Update summary
    updateModalSummary(data);

    // Update payment history
    updateModalPaymentHistory(data);
}

/**
 * Update modal header section
 */
function updateModalHeader(invoice) {
    // Update customer name
    $('.customer-name .name-text').text(invoice.customerName);

    // Update invoice code and status
    $('.invoice-code').text('HD' + String(invoice.invoiceId).padStart(6, '0'));
    $('.invoice-status')
            .removeClass('badge-success badge-warning badge-danger badge-info badge-secondary')
            .addClass(getStatusBadgeClass(invoice.status))
            .text(getStatusText(invoice.status));
}

/**
 * Update modal info section
 */
function updateModalInfo(invoice) {
    // Update basic info fields
    $('.created-by').text(invoice.createdBy);
    $('.invoice-date').text(formatDate(invoice.invoiceDate));

    // Update sold by select with user list
    updateSoldBySelect(invoice, invoice.listUser);
}

/**
 * Update sold by select dropdown
 */
function updateSoldBySelect(invoice, userList) {
    const $select = $('.sold-by-select');
    $select.empty();

    if (userList && userList.length > 0) {
        userList.forEach(user => {
            const selected = user.userId === invoice.soldById ? 'selected' : '';
            $select.append(`<option value="${user.userId}" ${selected}>${user.fullName}</option>`);
        });
    } else {
        $select.append(`<option selected>N/A</option>`);
    }
}

/**
 * Update products table
 */
function updateModalProducts(invoice) {
    const $tbody = $('.invoice-products-table tbody');
    $tbody.empty();

    if (invoice.invoiceDetails && invoice.invoiceDetails.length > 0) {
        invoice.invoiceDetails.forEach(product => {
            const row = `
                    <tr>
                        <td>
                            <span class="text-primary fw-bold">${product.productCode || 'N/A'}</span>
                        </td>
                        <td>${product.productName || 'Sản phẩm không xác định'}</td>
                        <td class="text-center fw-bold">${product.quantity || 0}</td>
                        <td class="text-end">${formatNumber(product.unitPrice)}</td>
                        <td class="text-end">0</td>
                        <td class="text-end">${formatNumber(product.unitPrice)}</td>
                        <td class="text-end fw-bold text-primary">${formatNumber(product.unitPrice * product.quantity)}</td>
                    </tr>
                `;
            $tbody.append(row);
        });
    } else {
        $tbody.append(`
                <tr>
                    <td colspan="7" class="text-center text-muted py-4">Không có sản phẩm</td>
                </tr>
            `);
    }
}

/**
 * Update invoice summary
 */
function updateModalSummary(invoice) {
    $('.total-items').text(invoice.totalItems || 0);
    $('.total-amount').text(formatNumber(invoice.totalAmount));
    $('.discount-amount').text(formatNumber(invoice.discount || 0));
    $('.need-to-pay').text(formatNumber(invoice.subTotal));
    $('.paid-amount').text(formatNumber(invoice.subTotal));
}

/**
 * Update payment history tab
 */
function updateModalPaymentHistory(invoice) {
    const $tbody = $('.payment-history-table tbody');
    $tbody.empty();

    // Simple payment entry based on invoice data

    const row = `
            <tr>
                <td class="fw-bold text-primary">TTHD${String(invoice.invoiceId).padStart(6, '0')}</td>
                <td>${formatDate(invoice.invoiceDate)}</td>
                <td>${invoice.createdBy}</td>
                <td class="text-end fw-bold">${formatNumber(invoice.subTotal)}</td>
                <td>${invoice.paymentMethod || 'Tiền mặt'}</td>
                <td><span class="badge bg-success">Đã thanh toán</span></td>
                <td class="text-end fw-bold text-success">${formatNumber(invoice.subTotal)}</td>
            </tr>
        `;
    $tbody.append(row);
}

// Helper functions
function getStatusBadgeClass(status) {
    switch (status) {
        case 'Completed':
            return 'badge-success';
        case 'PROCESSING':
            return 'badge-warning';
        case 'CANCELLED':
            return 'badge-danger';
        case 'REFUNDED':
            return 'badge-secondary';
        default:
            return 'badge-info';
    }
}

function getStatusText(status) {
    switch (status) {
        case 'Completed':
            return 'Hoàn thành';
        case 'PROCESSING':
            return 'Đang xử lý';
        case 'CANCELLED':
            return 'Không giao được';
        case 'REFUNDED':
            return 'Đã hủy';
        default:
            return status || 'N/A';
    }
}

function formatDate(dateString) {
    if (!dateString)
        return 'N/A';

    // If it's already formatted as dd/MM/yyyy HH:mm, return as is
    if (typeof dateString === 'string' && dateString.match(/\d{2}\/\d{2}\/\d{4}/)) {
        return dateString;
    }

    // Otherwise format the date
    const date = new Date(dateString);
    if (isNaN(date.getTime()))
        return 'N/A';

    return date.toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function formatNumber(number) {
    if (!number || isNaN(number))
        return '0';
    return new Intl.NumberFormat('vi-VN').format(number);
}


$(document).on('click', '#btn-added', function (e) {
    e.preventDefault();

    window.location.href = 'CashierServlet';
    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: {action: 'addInvoice'},
        success: function (html) {
            $('#headerSection').html(html);
            loadCart(function () {
                loadCustomerInfo(function () {
                });
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi thêm hóa đơn:', error);
        }
    });
});

// Hàm khởi tạo đơn giản - chỉ hiển thị các cột được checked
function initializeColumnDisplay() {
    console.log('🔄 Initializing column display...');
    
    // Danh sách các cột được hiển thị mặc định (theo checkbox checked)
    const defaultVisibleColumns = [
        'create-time',
        'customer', 
        'total',
        'discount',
        'paid',
        'status'
    ];
    
    // Ẩn tất cả các cột data-column trước
    const allDataColumns = [
        'create-time',
        'update-time', 
        'customer',
        'email',
        'phone',
        'birthday',
        'branch',
        'total',
        'discount',
        'vat',
        'need-pay',
        'paid',
        'status'
    ];
    
    console.log('Hiding all columns first...');
    
    // THÊM CSS RULE ĐỘNG để override CSS hiện tại
    let style = document.getElementById('column-visibility-style');
    if (!style) {
        style = document.createElement('style');
        style.id = 'column-visibility-style';
        document.head.appendChild(style);
    }
    
    // Tạo CSS rules để ẩn tất cả cột trước
    let cssRules = '';
    allDataColumns.forEach(columnName => {
        cssRules += `
            th[data-column="${columnName}"], 
            td[data-column="${columnName}"], 
            col[data-column="${columnName}"] { 
                display: none !important; 
            }
        `;
    });
    
    // Tạo CSS rules để hiển thị các cột mặc định
    defaultVisibleColumns.forEach(columnName => {
        cssRules += `
            th[data-column="${columnName}"] { 
                display: table-cell !important; 
            }
            td[data-column="${columnName}"] { 
                display: table-cell !important; 
            }
            col[data-column="${columnName}"] { 
                display: table-column !important; 
            }
        `;
    });
    
    // Áp dụng CSS rules
    style.textContent = cssRules;
    
    console.log('✅ Column display initialization completed with CSS override');
}

// Sự kiện click vào columnSettingsBtn
function setupColumnSettingsPopup() {
    const columnSettingsBtn = document.getElementById('columnSettingsBtn');
    const columnSettingsPopup = document.getElementById('columnSettingsPopup');
    
    // Click vào button để show/hide popup
    columnSettingsBtn.addEventListener('click', function(e) {
        e.stopPropagation();
        
        // Toggle popup
        columnSettingsPopup.classList.toggle('show');
        columnSettingsBtn.classList.toggle('active');
        
        // Cập nhật trạng thái checkbox theo cột hiện tại
        updateCheckboxStates();
    });
    
    // Click bên ngoài để đóng popup
    document.addEventListener('click', function(e) {
        if (!e.target.closest('.column-settings-container')) {
            columnSettingsPopup.classList.remove('show');
            columnSettingsBtn.classList.remove('active');
        }
    });
}

// Object theo dõi trạng thái các cột
let columnVisibilityState = {
    'create-time': true,
    'update-time': false,
    'customer': true,
    'email': false,
    'phone': false,
    'birthday': false,
    'branch': false,
    'total': true,
    'discount': true,
    'vat': false,
    'need-pay': false,
    'paid': true,
    'status': true
};

// Cập nhật trạng thái checkbox theo object hiện tại (không reset về default)
function updateCheckboxStates() {
    const allDataColumns = [
        'create-time',
        'update-time', 
        'customer',
        'email',
        'phone',
        'birthday',
        'branch',
        'total',
        'discount',
        'vat',
        'need-pay',
        'paid',
        'status'
    ];
    
    allDataColumns.forEach(columnName => {
        const checkbox = document.querySelector(`input[data-column="${columnName}"]`);
        if (checkbox) {
            // Cập nhật checkbox theo trạng thái hiện tại trong object (không phải default)
            checkbox.checked = columnVisibilityState[columnName] || false;
            console.log(`📋 Popup checkbox ${columnName}: ${checkbox.checked}`);
        }
    });
}

// Sự kiện thay đổi checkbox
function setupColumnToggleEvents() {
    // Lắng nghe sự kiện change cho tất cả checkbox toggle
    document.addEventListener('change', function(e) {
        if (e.target.classList.contains('column-toggle')) {
            const columnName = e.target.getAttribute('data-column');
            const isChecked = e.target.checked;
            
            console.log(`🔄 Column toggle: ${columnName} = ${isChecked}`);
            
            // Toggle hiển thị cột
            toggleColumnVisibility(columnName, isChecked);
        }
    });
}

// Hàm toggle hiển thị cột
function toggleColumnVisibility(columnName, isVisible) {
    // Cập nhật trạng thái vào object
    columnVisibilityState[columnName] = isVisible;
    
    // Lấy style element hiện tại
    let style = document.getElementById('column-visibility-style');
    if (!style) {
        style = document.createElement('style');
        style.id = 'column-visibility-style';
        document.head.appendChild(style);
    }
    
    // Lấy CSS hiện tại
    let currentCSS = style.textContent;
    
    // Tạo CSS rule cho cột này
    const hideRule = `
        th[data-column="${columnName}"], 
        td[data-column="${columnName}"], 
        col[data-column="${columnName}"] { 
            display: none !important; 
        }
    `;
    
    const showRule = `
        th[data-column="${columnName}"] { 
            display: table-cell !important; 
        }
        td[data-column="${columnName}"] { 
            display: table-cell !important; 
        }
        col[data-column="${columnName}"] { 
            display: table-column !important; 
        }
    `;
    
    // Xóa CSS cũ của cột này
    const oldHideRegex = new RegExp(
        `\\s*th\\[data-column="${columnName}"\\],\\s*td\\[data-column="${columnName}"\\],\\s*col\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*none[^}]*\\}`, 
        'g'
    );
    const oldShowRegex = new RegExp(
        `\\s*th\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-cell[^}]*\\}\\s*td\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-cell[^}]*\\}\\s*col\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-column[^}]*\\}`, 
        'g'
    );
    
    currentCSS = currentCSS.replace(oldHideRegex, '').replace(oldShowRegex, '');
    
    // Thêm CSS mới
    if (isVisible) {
        currentCSS += showRule;
        console.log(`✅ Showing column: ${columnName}`);
    } else {
        currentCSS += hideRule;
        console.log(`❌ Hiding column: ${columnName}`);
    }
    
    // Áp dụng CSS mới
    style.textContent = currentCSS;
}

// Gọi khi document ready
$(document).ready(function() {
    initializeColumnDisplay();
    setupColumnSettingsPopup();
    setupColumnToggleEvents();
});


$(document).ready(function () {
    // Handle import form submission with AJAX
    $('#importForm').on('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);
        const $submitButton = $('#importSubmit');

        $submitButton.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang import...');

        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                $submitButton.prop('disabled', false).html('Import');
                alert(response); // Hiển thị thông báo từ Servlet
                $('#importModal').modal('hide'); // Đóng modal sau khi thành công
                location.reload(); // Tải lại trang để cập nhật dữ liệu
            },
            error: function (xhr, status, error) {
                $submitButton.prop('disabled', false).html('Import');
                alert('Lỗi khi import: ' + (xhr.responseText || error));
            }
        });
    });

    // Existing code (if any) can stay below
});


$(document).ready(function () {
    // Handle export button click
    $('#exportFileTrigger').on('click', function (e) {
        e.preventDefault();
        const $button = $(this);
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang xuất...');
        
        $.ajax({
            url: 'ImportExportServlet',
            type: 'GET',
            data: { action: 'export' },
            xhrFields: { responseType: 'blob' },
            success: function (data) {
                $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');
                
                // Tạo URL từ blob data
                const url = window.URL.createObjectURL(data);
                
                // Tạo link download
                const link = document.createElement('a');
                link.href = url;
                link.download = `Invoices_export_${new Date().toISOString().split('T')[0]}.xlsx`;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
                
                // Dọn dẹp memory
                window.URL.revokeObjectURL(url);
                
                alert('Xuất file thành công!');
            },
            error: function (xhr, status, error) {
                $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');
                alert('Lỗi khi xuất file: ' + (xhr.responseText || error));
            }
        });
    });
});