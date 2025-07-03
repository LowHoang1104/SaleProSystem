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

    /**
     * Sync header scroll with body scroll
     */
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
        success: function (data) {
            hideLoading();

            // Update toàn bộ table content
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

// Invoice Detail View Handler
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