// =================== TABLE SCROLL SYNC MODULE ===================
let TableScrollSync = {
    isHeaderScrolling: false,
    isBodyScrolling: false,
    headerElement: null,
    bodyElement: null,

    init: function () {
        this.headerElement = document.querySelector('.table-header-scroll');
        this.bodyElement = document.querySelector('.table-body-container');

        if (!this.headerElement || !this.bodyElement) {
            console.warn('Table scroll sync elements not found');
            return;
        }

        this.bindEvents();
        this.initScrollPosition();
        console.log('Table scroll synchronization initialized');
    },

    bindEvents: function () {
        this.bodyElement.addEventListener('scroll', () => this.syncHeaderScroll());
        this.headerElement.addEventListener('scroll', () => this.syncBodyScroll());
        window.addEventListener('resize', () => {
            setTimeout(() => this.initScrollPosition(), 100);
        });
    },

    syncHeaderScroll: function () {
        if (this.isHeaderScrolling)
            return;
        this.isBodyScrolling = true;
        this.headerElement.scrollLeft = this.bodyElement.scrollLeft;
        setTimeout(() => {
            this.isBodyScrolling = false;
        }, 10);
    },

    syncBodyScroll: function () {
        if (this.isBodyScrolling)
            return;
        this.isHeaderScrolling = true;
        this.bodyElement.scrollLeft = this.headerElement.scrollLeft;
        setTimeout(() => {
            this.isHeaderScrolling = false;
        }, 10);
    },

    initScrollPosition: function () {
        this.headerElement.scrollLeft = 0;
        this.bodyElement.scrollLeft = 0;
    }
};

// =================== INVOICE DETAIL MODAL MODULE ===================
let InvoiceDetailModal = {
    isInitialized: false,
    currentInvoiceId: null, // ADD THIS
    hasChanges: false, // ADD THIS

    init: function () {
        if (this.isInitialized)
            return;
        this.bindEvents();
        this.isInitialized = true;
    },

    bindEvents: function () {
        $(document).on('click', '.btn-action[title="Xem chi tiết"]', (e) => {
            e.preventDefault();
            const invoiceId = $(e.target).closest('.btn-action').data('invoice-id');
            if (invoiceId) {
                this.loadInvoiceDetail(invoiceId, $(e.target).closest('.btn-action'));
            }
        });

        // Event for sold-by dropdown change
        $(document).on('change', '.sold-by-select', (e) => {
            this.hasChanges = true;
            this.updateSaveButtonState();
        });

        // Event for save button
        $(document).on('click', '#invoiceModal .btn-save', (e) => {
            e.preventDefault();
            this.saveInvoiceChanges();
        });

        // Reset changes when modal is closed
        $(document).on('hidden.bs.modal', '#invoiceModal', () => {
            this.hasChanges = false;
            this.currentInvoiceId = null;
            this.updateSaveButtonState();
        });

        $(document).on('click', '#invoiceModal .btn-export', (e) => {
            e.preventDefault();
            this.handleExportSingleInvoice($(e.target));
        });

    },

    loadInvoiceDetail: function (invoiceId, $button) {
        this.currentInvoiceId = invoiceId;  // ADD THIS LINE
        const originalHtml = $button.html();
        $button.html('<i class="fas fa-spinner fa-spin"></i>').prop('disabled', true);

        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'GET',
            data: {
                invoiceId: invoiceId,
                action: 'getDetail'
            },
            success: (response) => {
                $button.html(originalHtml).prop('disabled', false);
                this.updateModal(response);
                this.hasChanges = false;        // ADD THIS LINE
                this.updateSaveButtonState();   // ADD THIS LINE
                $('#invoiceModal').modal('show');
            },
            error: (xhr, status, error) => {
                $button.html(originalHtml).prop('disabled', false);
                console.error('Error loading invoice details:', error);
                alert('Có lỗi xảy ra khi tải chi tiết hóa đơn. Vui lòng thử lại.');
            }
        });
    },

    saveInvoiceChanges: function () {
        if (!this.hasChanges || !this.currentInvoiceId) {
            console.log('No changes or no invoice ID'); // Debug log
            return;
        }

        const newSoldById = $('.sold-by-select').val();
        console.log('Saving changes for invoice:', this.currentInvoiceId, 'New sold by:', newSoldById); // Debug log

        const $saveButton = $('#invoiceModal .btn-save');
        const originalHtml = $saveButton.html();

        // Show loading state
        $saveButton.html('<i class="fas fa-spinner fa-spin"></i> Đang lưu...').prop('disabled', true);

        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'POST',
            data: {
                invoiceId: this.currentInvoiceId,
                action: 'updateSoldBy',
                soldById: newSoldById
            },
            success: (response) => {
                console.log('Update response:', response); // Debug log
                $saveButton.html(originalHtml).prop('disabled', false);

                if (response.success) {
                    this.hasChanges = false;
                    this.updateSaveButtonState();

                    // Show success message
                    this.showNotification('Cập nhật người bán thành công!', 'success');

                    // Optionally refresh the main table if needed
                    if (typeof window.refreshInvoiceTable === 'function') {
                        window.refreshInvoiceTable();
                    }
                } else {
                    this.showNotification(response.message || 'Có lỗi xảy ra khi cập nhật!', 'error');
                }
            },
            error: (xhr, status, error) => {
                console.error('Error updating invoice:', xhr, status, error); // Debug log
                $saveButton.html(originalHtml).prop('disabled', false);

                let errorMessage = 'Có lỗi xảy ra khi cập nhật người bán. Vui lòng thử lại.';
                if (xhr.responseText) {
                    try {
                        const errorResponse = JSON.parse(xhr.responseText);
                        errorMessage = errorResponse.message || errorMessage;
                    } catch (e) {
                        // Use default error message
                    }
                }

                this.showNotification(errorMessage, 'error');
            }
        });
    },

    updateSaveButtonState: function () {
        const $saveButton = $('#invoiceModal .btn-save');
        if (this.hasChanges) {
            $saveButton.removeClass('btn-outline-secondary').addClass('btn-warning');
            $saveButton.find('i').removeClass('fa-save').addClass('fa-exclamation-triangle');
        } else {
            $saveButton.removeClass('btn-warning').addClass('btn-outline-secondary');
            $saveButton.find('i').removeClass('fa-exclamation-triangle').addClass('fa-save');
        }
    },

    showNotification: function (message, type = 'info') {
        // Create notification element
        const notificationClass = type === 'success' ? 'alert-success' :
                type === 'error' ? 'alert-danger' : 'alert-info';

        const notification = $(`
            <div class="alert ${notificationClass} alert-dismissible fade show notification-alert" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);

        // Add to body
        $('body').append(notification);

        // Auto remove after 5 seconds
        setTimeout(() => {
            notification.alert('close');
        }, 5000);
    },

    updateModal: function (data) {
        console.log('Received data:', data); // Debug log
        this.updateHeader(data);
        this.updateInfo(data);
        this.updateProducts(data);
        this.updateSummary(data);
        this.updatePaymentHistory(data);
    },

    updateHeader: function (invoice) {
        // Update customer name - handle null case
        $('.customer-name .name-text').text(invoice.customerName);

        // Update invoice code
        $('.invoice-code').text(invoice.invoiceCode);

        // Update status badge
        $('.invoice-status')
                .removeClass('badge-success badge-warning badge-danger badge-info badge-secondary')
                .addClass(this.getStatusBadgeClass(invoice.status))
                .text(this.getStatusText(invoice.status));

        $('.branch-info').text(invoice.branch);

    },

    updateInfo: function (invoice) {
        // Update created by
        $('.created-by').text(invoice.createdBy || 'N/A');

        // Update invoice date
        $('.invoice-date').text(this.formatDate(invoice.invoiceDate));

        // Update update date
        $('.update-date').text(this.formatDate(invoice.updateDate));

        // Update sold by select dropdown
        this.updateSoldBySelect(invoice, invoice.listUser);
    },

    updateSoldBySelect: function (invoice, userList) {
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
    },

    updateProducts: function (invoice) {
        const $tbody = $('.invoice-products-table tbody');
        $tbody.empty();

        if (invoice.invoiceDetails && invoice.invoiceDetails.length > 0) {
            invoice.invoiceDetails.forEach(product => {
                // Handle potential null/undefined values
                const productCode = product.productCode;
                const productName = product.productName;
                const quantity = product.quantity || 0;
                const unitPrice = product.unitPrice || 0;
                const totalPrice = unitPrice * quantity;

                const row = `
                    <tr>
                        <td><span class="text-primary fw-bold">${productCode}</span></td>
                        <td>${productName}</td>
                        <td class="text-center fw-bold">${quantity}</td>
                        <td class="text-end">${this.formatNumber(unitPrice)}</td>
                        <td class="text-end">0</td>
                        <td class="text-end">${this.formatNumber(unitPrice)}</td>
                        <td class="text-end fw-bold text-primary">${this.formatNumber(totalPrice)}</td>
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
    },

    updateSummary: function (invoice) {
        // Update total items count
        $('.total-items').text(invoice.totalItems || 0);

        // Update amounts - handle both possible field names from servlet
        $('.total-amount').text(this.formatNumber(invoice.subTotal));
        $('.discount-amount').text(this.formatNumber(invoice.discountAmount));
        $('.VAT-amount').text(this.formatNumber(invoice.VATAmount));
        $('.need-to-pay').text(this.formatNumber(invoice.paidAmount));
        $('.paid-amount').text(this.formatNumber(invoice.paidAmount));
    },

    updatePaymentHistory: function (invoice) {
        const $tbody = $('.payment-history-table tbody');
        $tbody.empty();

        // Create payment history entry
        const paymentCode = 'TTHD' + String(invoice.invoiceId).padStart(6, '0');
        const amount = invoice.subTotal || invoice.totalAmount || 0;
        const paymentMethod = invoice.paymentMethod;
        const createdBy = invoice.createdBy
        const invoiceDate = this.formatDate(invoice.invoiceDate);

        const row = `
            <tr>
                <td class="fw-bold text-primary">${paymentCode}</td>
                <td>${invoiceDate}</td>
                <td>${createdBy}</td>
                <td class="text-end fw-bold">${this.formatNumber(amount)}</td>
                <td>${paymentMethod}</td>
                <td><span class="badge bg-success">Đã thanh toán</span></td>
                <td class="text-end fw-bold text-success">${this.formatNumber(amount)}</td>
            </tr>
        `;
        $tbody.append(row);
    },

    getStatusBadgeClass: function (status) {
        if (!status)
            return 'badge-info';

        switch (status.toLowerCase()) {
            case 'completed':
                return 'badge-success';
            case 'processing':
                return 'badge-warning';
            case 'cancelled':
                return 'badge-danger';
            case 'refunded':
                return 'badge-secondary';
            default:
                return 'badge-info';
        }
    },

    getStatusText: function (status) {
        if (!status)
            return 'N/A';

        switch (status.toLowerCase()) {
            case 'completed':
                return 'Hoàn thành';
            case 'processing':
                return 'Đang xử lý';
            case 'cancelled':
                return 'Không giao được';
            case 'refunded':
                return 'Đã hủy';
            default:
                return status;
        }
    },

    formatDate: function (dateString) {
        if (!dateString)
            return 'N/A';

        // If already formatted as dd/MM/yyyy HH:mm, return as is
        if (typeof dateString === 'string' && dateString.match(/\d{2}\/\d{2}\/\d{4}/)) {
            return dateString;
        }

        // Try to parse the date
        let date;
        if (typeof dateString === 'string') {
            // Handle different date formats from server
            date = new Date(dateString);
        } else if (dateString instanceof Date) {
            date = dateString;
        } else {
            return 'N/A';
        }

        if (isNaN(date.getTime()))
            return 'N/A';

        // Format to Vietnamese locale
        return date.toLocaleDateString('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },

    formatNumber: function (number) {
        if (!number || isNaN(number))
            return '0';
        return new Intl.NumberFormat('vi-VN').format(number);
    },
    // Function để xuất file hóa đơn riêng lẻ
    handleExportSingleInvoice: function ($button) {
        if (!this.currentInvoiceId) {
            this.showNotification('Không tìm thấy ID hóa đơn để xuất file!', 'error');
            return;
        }

        const originalHtml = $button.html();
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang xuất...');

        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'GET',
            data: {
                action: 'exportSingle',
                invoiceId: this.currentInvoiceId
            },
            xhrFields: {
                responseType: 'blob'
            },
            success: (data) => {
                $button.prop('disabled', false).html(originalHtml);
                this.downloadSingleInvoiceFile(data);
                this.showNotification('Xuất file hóa đơn thành công!', 'success');
            },
            error: (xhr, status, error) => {
                $button.prop('disabled', false).html(originalHtml);
                console.error('Export error:', xhr, status, error);

                let errorMessage = 'Lỗi khi xuất file hóa đơn';
                if (xhr.responseText && xhr.responseText.includes('application/json')) {
                    try {
                        const reader = new FileReader();
                        reader.onload = () => {
                            try {
                                const errorResponse = JSON.parse(reader.result);
                                this.showNotification(errorResponse.message || errorMessage, 'error');
                            } catch (e) {
                                this.showNotification(errorMessage, 'error');
                            }
                        };
                        reader.readAsText(xhr.responseText);
                    } catch (e) {
                        this.showNotification(errorMessage, 'error');
                    }
                } else {
                    this.showNotification(errorMessage + ': ' + (error || 'Unknown error'), 'error');
                }
            }
        });
    },

// Function để download file hóa đơn
    downloadSingleInvoiceFile: function (data) {
        const url = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = url;

        // Tạo tên file với invoice code nếu có
        const invoiceCode = $('.invoice-code').text() || 'Invoice';
        const fileName = `${invoiceCode}_${new Date().toISOString().split('T')[0]}.xlsx`;

        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }


};


// =================== INVOICE CREATION MODULE ===================
let InvoiceCreator = {
    init: function () {
        this.bindEvents();
    },

    bindEvents: function () {
        $(document).on('click', '#btn-added', (e) => {
            e.preventDefault();
            this.createNewInvoice();
        });
    },

    createNewInvoice: function () {
        window.location.href = 'CashierServlet';
        $.ajax({
            url: 'HeaderServlet',
            type: 'POST',
            data: {action: 'addInvoice'},
            success: function (html) {
                $('#headerSection').html(html);
                if (typeof loadCart === 'function') {
                    loadCart(function () {
                        if (typeof loadCustomerInfo === 'function') {
                            loadCustomerInfo(function () {});
                        }
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi thêm hóa đơn:', error);
            }
        });
    }
};
// =================== COLUMN VISIBILITY MODULE ===================
let ColumnVisibility = {
    state: {
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
    },

    allColumns: [
        'create-time', 'update-time', 'customer', 'email', 'phone',
        'birthday', 'branch', 'total', 'discount', 'vat', 'need-pay', 'paid', 'status'
    ],

    defaultVisible: [
        'create-time', 'customer', 'total', 'discount', 'paid', 'status'
    ],

    init: function () {
        this.initializeDisplay();
        this.setupPopup();
        this.bindToggleEvents();
    },

    initializeDisplay: function () {
        let style = document.getElementById('column-visibility-style');
        if (!style) {
            style = document.createElement('style');
            style.id = 'column-visibility-style';
            document.head.appendChild(style);
        }

        let cssRules = '';

        // Hide all columns first
        this.allColumns.forEach(columnName => {
            cssRules += `
                th[data-column="${columnName}"], 
                td[data-column="${columnName}"], 
                col[data-column="${columnName}"] { 
                    display: none !important; 
                }
            `;
        });

        // Show default visible columns
        this.defaultVisible.forEach(columnName => {
            cssRules += `
                th[data-column="${columnName}"] { display: table-cell !important; }
                td[data-column="${columnName}"] { display: table-cell !important; }
                col[data-column="${columnName}"] { display: table-column !important; }
            `;
        });

        style.textContent = cssRules;
    },

    setupPopup: function () {
        const columnSettingsBtn = document.getElementById('columnSettingsBtn');
        const columnSettingsPopup = document.getElementById('columnSettingsPopup');

        if (!columnSettingsBtn || !columnSettingsPopup)
            return;

        columnSettingsBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            columnSettingsPopup.classList.toggle('show');
            columnSettingsBtn.classList.toggle('active');
            this.updateCheckboxStates();
        });

        document.addEventListener('click', (e) => {
            if (!e.target.closest('.column-settings-container')) {
                columnSettingsPopup.classList.remove('show');
                columnSettingsBtn.classList.remove('active');
            }
        });
    },

    updateCheckboxStates: function () {
        this.allColumns.forEach(columnName => {
            const checkbox = document.querySelector(`input[data-column="${columnName}"]`);
            if (checkbox) {
                checkbox.checked = this.state[columnName] || false;
            }
        });
    },

    bindToggleEvents: function () {
        document.addEventListener('change', (e) => {
            if (e.target.classList.contains('column-toggle')) {
                const columnName = e.target.getAttribute('data-column');
                const isChecked = e.target.checked;
                this.toggleColumn(columnName, isChecked);
            }
        });
    },

    toggleColumn: function (columnName, isVisible) {
        this.state[columnName] = isVisible;

        let style = document.getElementById('column-visibility-style');
        if (!style) {
            style = document.createElement('style');
            style.id = 'column-visibility-style';
            document.head.appendChild(style);
        }

        let currentCSS = style.textContent;

        const hideRule = `
            th[data-column="${columnName}"], 
            td[data-column="${columnName}"], 
            col[data-column="${columnName}"] { 
                display: none !important; 
            }
        `;

        const showRule = `
            th[data-column="${columnName}"] { display: table-cell !important; }
            td[data-column="${columnName}"] { display: table-cell !important; }
            col[data-column="${columnName}"] { display: table-column !important; }
        `;

        // Remove old rules for this column
        const oldHideRegex = new RegExp(
                `\\s*th\\[data-column="${columnName}"\\],\\s*td\\[data-column="${columnName}"\\],\\s*col\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*none[^}]*\\}`,
                'g'
                );
        const oldShowRegex = new RegExp(
                `\\s*th\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-cell[^}]*\\}\\s*td\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-cell[^}]*\\}\\s*col\\[data-column="${columnName}"\\]\\s*\\{[^}]*display:\\s*table-column[^}]*\\}`,
                'g'
                );

        currentCSS = currentCSS.replace(oldHideRegex, '').replace(oldShowRegex, '');

        // Add new rule
        if (isVisible) {
            currentCSS += showRule;
        } else {
            currentCSS += hideRule;
        }

        style.textContent = currentCSS;
    }
};
// =================== IMPORT/EXPORT MODULE ===================
let ImportExport = {
    isInitialized: false,

    init: function () {
        if (this.isInitialized)
            return;
        this.bindEvents();
        this.isInitialized = true;
    },

    bindEvents: function () {
        // Import functionality
        $('#importForm').on('submit', (e) => {
            e.preventDefault();
            this.handleImport(e.target);
        });

        // Export functionality
        $('#exportFileTrigger').on('click', (e) => {
            e.preventDefault();
            this.handleExport($(e.target));
        });
    },

    handleImport: function (form) {
        const formData = new FormData(form);
        const $submitButton = $('#importSubmit');

        $submitButton.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang import...');

        $.ajax({
            url: $(form).attr('action'),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: (response) => {
                $submitButton.prop('disabled', false).html('Import');
                alert(response);
                $('#importModal').modal('hide');
                location.reload();
            },
            error: (xhr, status, error) => {
                $submitButton.prop('disabled', false).html('Import');
                alert('Lỗi khi import: ' + (xhr.responseText || error));
            }
        });
    },

    handleExport: function ($button) {
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang xuất...');

        $.ajax({
            url: 'ImportExportServlet',
            type: 'GET',
            data: {action: 'export'},
            xhrFields: {responseType: 'blob'},
            success: (data) => {
                $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');
                this.downloadFile(data);
                alert('Xuất file thành công!');
            },
            error: (xhr, status, error) => {
                $button.prop('disabled', false).html('<i class="fas fa-file-export me-1"></i>Xuất file');
                alert('Lỗi khi xuất file: ' + (xhr.responseText || error));
            }
        });
    },

    downloadFile: function (data) {
        const url = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = url;
        link.download = `Invoices_list_export_${new Date().toISOString().split('T')[0]}.xlsx`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }
};


$(document).ready(function () {
    // Initialize all modules
    TableScrollSync.init();
    InvoiceDetailModal.init();
    InvoiceCreator.init();
    ColumnVisibility.init();
    ImportExport.init();
    console.log('📄 Invoice management features ready!');
});

