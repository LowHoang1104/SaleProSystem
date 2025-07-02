document.addEventListener('DOMContentLoaded', function() {
    
    // Get DOM elements
    const tableHeaderScroll = document.querySelector('.table-header-scroll');
    const tableBodyContainer = document.querySelector('.table-body-container');
    
    if (!tableHeaderScroll || !tableBodyContainer) {
        console.warn('Table scroll sync elements not found');
        return;
    }
    
    let isHeaderScrolling = false;
    let isBodyScrolling = false;
    
    /**
     * Sync header scroll with body scroll
     */
    function syncHeaderScroll() {
        if (isHeaderScrolling) return;
        
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
        if (isBodyScrolling) return;
        
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
    window.addEventListener('resize', function() {
        setTimeout(initScrollSync, 100);
    });
    
    console.log('Invoice table scroll synchronization initialized');
});

$(document).ready(function() {
    setupPagination();
    console.log('📄 AJAX pagination ready!');
});

function setupPagination() {
    // Page size dropdown change
    $(document).on('change', '.pagination-select', function() {
        const newPageSize = parseInt($(this).val());
        loadPage(1, newPageSize); // Reset to page 1 when changing size
    });

    // Page navigation clicks
    $(document).on('click', '.page-link', function(e) {
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
 * Load page via AJAX
 */
function loadPage(page, pageSize) {
    console.log(`Loading page ${page} with size ${pageSize}`);
    
    // Show loading
    showLoading();
    
    // AJAX request
    $.ajax({
        url: 'InvoiceManagementServlet',
        type: 'GET',
        data: {
            page: page,
            pageSize: pageSize,
            ajax: 'true' // Flag để servlet biết đây là AJAX
        },
        success: function(data) {
            hideLoading();
            
            // Update toàn bộ table content
            updateContent(data);
            
            console.log('Page loaded successfully');
        },
        error: function(xhr, status, error) {
            hideLoading();
            console.error('Error loading page:', error);
            alert('Có lỗi xảy ra khi tải trang. Vui lòng thử lại.');
        }
    });
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
    
    $('.page-numbers .page-link[data-page]').each(function() {
        const page = parseInt($(this).data('page'));
        if (!isNaN(page) && page > maxPage) {
            maxPage = page;
        }
    });
    
    return maxPage;
}

/*===============================*/
// Invoice Detail View Handler
$(document).ready(function() {
    // Handle view detail button click
    $(document).on('click', '.btn-action[title="Xem chi tiết"]', function(e) {
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
            success: function(response) {
                // Restore button
                $button.html(originalHtml).prop('disabled', false);
                
                // Update modal content and show
                updateInvoiceModal(response);
                $('#invoiceModal').modal('show');
            },
            error: function(xhr, status, error) {
                // Restore button
                $button.html(originalHtml).prop('disabled', false);
                
                console.error('Error loading invoice details:', error);
                alert('Có lỗi xảy ra khi tải chi tiết hóa đơn. Vui lòng thử lại.');
            }
        });
    });
});

/**
 * Update invoice modal with data
 */
function updateInvoiceModal(data) {
    const $modal = $('#invoiceModal');
    
    // Parse response if it's JSON
    let invoiceData;
    if (typeof data === 'string') {
        try {
            invoiceData = JSON.parse(data);
        } catch (e) {
            // If not JSON, assume it's HTML content
            $modal.find('.modal-body').html(data);
            return;
        }
    } else {
        invoiceData = data;
    }
    
    // Update modal content with invoice data
    if (invoiceData) {
        updateModalHeader(invoiceData);
        updateModalInfo(invoiceData);
        updateModalProducts(invoiceData);
        updateModalSummary(invoiceData);
        updateModalPaymentHistory(invoiceData);
    }
}

/**
 * Update modal header
 */
function updateModalHeader(invoice) {
    const $header = $('.invoice-detail-header');
    
    // Update customer name
    const customerName = invoice.customerName || 'Khách lẻ';
    $header.find('.customer-name').text(customerName);
    
    // Update badges
    const $badges = $header.find('.invoice-badges');
    $badges.empty();
    
    // Invoice ID badge
    $badges.append(`<span class="badge bg-primary">${invoice.invoiceId}</span>`);
    
    // Status badge
    const statusClass = getStatusBadgeClass(invoice.status);
    const statusText = getStatusText(invoice.status);
    $badges.append(`<span class="badge ${statusClass}">${statusText}</span>`);
}

/**
 * Update modal info section
 */
function updateModalInfo(invoice) {
    const $infoGrid = $('.invoice-info-grid');
    
    // Update info items
    $infoGrid.find('.info-item').each(function() {
        const $item = $(this);
        const label = $item.find('label').text().toLowerCase();
        
        if (label.includes('người tạo')) {
            $item.find('span').text(invoice.createdBy || '');
        } else if (label.includes('ngày bán')) {
            $item.find('span').text(formatDate(invoice.invoiceDate));
        } else if (label.includes('bảng giá')) {
            $item.find('span').text(invoice.priceList || 'Bảng giá chung');
        }
    });
}

/**
 * Update modal products table
 */
function updateModalProducts(invoice) {
    const $tbody = $('.products-section table tbody');
    $tbody.empty();
    
    if (invoice.products && invoice.products.length > 0) {
        invoice.products.forEach(product => {
            const row = `
                <tr>
                    <td>${product.productCode || ''}</td>
                    <td>${product.productName || ''}</td>
                    <td class="text-center">${product.quantity || 0}</td>
                    <td class="text-end">${formatNumber(product.unitPrice)}</td>
                    <td class="text-end">${formatNumber(product.discount)}</td>
                    <td class="text-end">${formatNumber(product.salePrice)}</td>
                    <td class="text-end">${formatNumber(product.totalAmount)}</td>
                </tr>
            `;
            $tbody.append(row);
        });
    } else {
        $tbody.append(`
            <tr>
                <td colspan="7" class="text-center text-muted">Không có sản phẩm</td>
            </tr>
        `);
    }
}

/**
 * Update modal summary
 */
function updateModalSummary(invoice) {
    const $summary = $('.invoice-summary .summary-lines');
    
    $summary.find('.summary-line').each(function() {
        const $line = $(this);
        const text = $line.find('span:first').text().toLowerCase();
        
        if (text.includes('tổng tiền hàng')) {
            $line.find('span:last').text(formatNumber(invoice.totalAmount));
        } else if (text.includes('giảm giá')) {
            $line.find('span:last').text(formatNumber(invoice.discount));
        } else if (text.includes('khách cần trả')) {
            $line.find('span:last').text(formatNumber(invoice.totalAmount - invoice.discount));
        } else if (text.includes('khách đã trả')) {
            $line.find('span:last').text(formatNumber(invoice.paidAmount));
        }
    });
}

/**
 * Update payment history tab
 */
function updateModalPaymentHistory(invoice) {
    const $tbody = $('#payment-content table tbody');
    $tbody.empty();
    
    if (invoice.paymentHistory && invoice.paymentHistory.length > 0) {
        invoice.paymentHistory.forEach(payment => {
            const row = `
                <tr>
                    <td>${payment.paymentCode || ''}</td>
                    <td>${formatDate(payment.paymentDate)}</td>
                    <td>${payment.createdBy || ''}</td>
                    <td class="text-end">${formatNumber(payment.amount)}</td>
                    <td>${payment.paymentMethod || ''}</td>
                    <td><span class="badge bg-success">Đã thanh toán</span></td>
                    <td class="text-end">${formatNumber(payment.amount)}</td>
                </tr>
            `;
            $tbody.append(row);
        });
    } else {
        $tbody.append(`
            <tr>
                <td colspan="7" class="text-center text-muted">Chưa có lịch sử thanh toán</td>
            </tr>
        `);
    }
}

// Helper functions
function getStatusBadgeClass(status) {
    switch(status) {
        case 'Completed': return 'bg-success';
        case 'PROCESSING': return 'bg-warning';
        case 'CANCELLED': return 'bg-danger';
        case 'REFUNDED': return 'bg-secondary';
        default: return 'bg-info';
    }
}

function getStatusText(status) {
    switch(status) {
        case 'Completed': return 'Hoàn thành';
        case 'PROCESSING': return 'Đang xử lý';
        case 'CANCELLED': return 'Không giao được';
        case 'REFUNDED': return 'Đã hủy';
        default: return status;
    }
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN') + ' ' + date.toLocaleTimeString('vi-VN', { 
        hour: '2-digit', 
        minute: '2-digit' 
    });
}

function formatNumber(number) {
    if (!number || isNaN(number)) return '0';
    return new Intl.NumberFormat('vi-VN').format(number);
}