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
    currentInvoiceId: null,
    hasChanges: false,

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

        // Event for export button
        $(document).on('click', '#invoiceModal .btn-export', (e) => {
            e.preventDefault();
            this.handleExportSingleInvoice($(e.target));
        });

        // Event for print button
        $(document).on('click', '#invoiceModal .btn-print', (e) => {
            e.preventDefault();
            this.handlePrintInvoice($(e.target));
        });
    },

    loadInvoiceDetail: function (invoiceId, $button) {
        this.currentInvoiceId = invoiceId;
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
                this.hasChanges = false;
                this.updateSaveButtonState();
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
            console.log('No changes or no invoice ID');
            return;
        }

        const newSoldById = $('.sold-by-select').val();
        console.log('Saving changes for invoice:', this.currentInvoiceId, 'New sold by:', newSoldById);

        const $saveButton = $('#invoiceModal .btn-save');
        const originalHtml = $saveButton.html();

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
                console.log('Update response:', response);
                $saveButton.html(originalHtml).prop('disabled', false);

                if (response.success) {
                    this.hasChanges = false;
                    this.updateSaveButtonState();
                    this.showNotification('Cập nhật người bán thành công!', 'success');

                    if (typeof window.refreshInvoiceTable === 'function') {
                        window.refreshInvoiceTable();
                    }
                } else {
                    this.showNotification(response.message || 'Có lỗi xảy ra khi cập nhật!', 'error');
                }
            },
            error: (xhr, status, error) => {
                console.error('Error updating invoice:', xhr, status, error);
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

    downloadSingleInvoiceFile: function (data) {
        const url = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = url;

        const invoiceCode = $('.invoice-code').text() || 'Invoice';
        const fileName = `${invoiceCode}_${new Date().toISOString().split('T')[0]}.xlsx`;

        link.download = fileName;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    },

    handlePrintInvoice: function ($button) {
        if (!this.currentInvoiceId) {
            this.showNotification('Không tìm thấy ID hóa đơn để in!', 'error');
            return;
        }

        const originalHtml = $button.html();
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang tải...');

        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'GET',
            data: {
                action: 'getPrintData',
                invoiceId: this.currentInvoiceId
            },
            success: (response) => {
                $button.prop('disabled', false).html(originalHtml);
                this.openPrintWindow(response);
            },
            error: (xhr, status, error) => {
                $button.prop('disabled', false).html(originalHtml);
                console.error('Print error:', xhr, status, error);
                this.showNotification('Lỗi khi tải dữ liệu in: ' + (error || 'Unknown error'), 'error');
            }
        });
    },

    openPrintWindow: function (invoiceData) {
        const printContent = this.generatePrintHTML(invoiceData);
        
        const printWindow = window.open('', '_blank', 'width=800,height=600');
        printWindow.document.write(printContent);
        printWindow.document.close();
        
        printWindow.onload = function() {
            printWindow.focus();
            printWindow.print();
            
            printWindow.onafterprint = function() {
                printWindow.close();
            };
        };
    },

    generatePrintHTML: function (invoice) {
        const currentDate = new Date().toLocaleDateString('vi-VN');
        
        return `
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa đơn ${invoice.invoiceCode}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Times New Roman', serif; font-size: 14px; line-height: 1.4; color: #000; background: white; }
        .invoice-container { width: 100%; max-width: 800px; margin: 0 auto; padding: 20px; }
        .header { text-align: center; margin-bottom: 30px; border-bottom: 2px solid #000; padding-bottom: 20px; }
        .company-name { font-size: 24px; font-weight: bold; color: #2c5aa0; margin-bottom: 5px; }
        .company-info { font-size: 12px; color: #666; margin-bottom: 20px; }
        .invoice-title { font-size: 20px; font-weight: bold; text-transform: uppercase; margin-bottom: 10px; }
        .invoice-code { font-size: 16px; color: #2c5aa0; font-weight: bold; }
        .invoice-info { display: flex; justify-content: space-between; margin-bottom: 30px; padding: 15px; background-color: #f8f9fa; border-radius: 5px; }
        .info-section { flex: 1; }
        .info-section h4 { font-size: 14px; margin-bottom: 10px; color: #2c5aa0; border-bottom: 1px solid #ddd; padding-bottom: 5px; }
        .info-row { display: flex; margin-bottom: 5px; }
        .info-label { width: 120px; font-weight: bold; }
        .info-value { flex: 1; }
        .products-table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
        .products-table th, .products-table td { border: 1px solid #000; padding: 8px; text-align: left; }
        .products-table th { background-color: #2c5aa0; color: white; font-weight: bold; text-align: center; }
        .products-table .text-center { text-align: center; }
        .products-table .text-right { text-align: right; }
        .products-table .product-code { color: #2c5aa0; font-weight: bold; }
        .summary-section { display: flex; justify-content: flex-end; margin-bottom: 30px; }
        .summary-table { width: 300px; border-collapse: collapse; }
        .summary-table td { border: 1px solid #ddd; padding: 8px; }
        .summary-table .summary-label { background-color: #f8f9fa; font-weight: bold; width: 60%; }
        .summary-table .summary-value { text-align: right; width: 40%; }
        .summary-table .total-row { background-color: #2c5aa0; color: white; font-weight: bold; }
        .signature-section { display: flex; justify-content: space-between; margin-top: 50px; padding-top: 20px; }
        .signature-box { text-align: center; width: 200px; }
        .signature-title { font-weight: bold; margin-bottom: 50px; }
        .signature-line { border-top: 1px solid #000; margin-top: 50px; padding-top: 5px; font-size: 12px; }
        .footer { text-align: center; margin-top: 30px; font-size: 12px; color: #666; border-top: 1px solid #ddd; padding-top: 15px; }
        @media print {
            body { margin: 0; padding: 0; }
            .invoice-container { padding: 0; margin: 0; max-width: none; width: 100%; }
            .no-print { display: none !important; }
            @page { margin: 1cm; size: A4; }
        }
    </style>
</head>
<body>
    <div class="invoice-container">
        <div class="header">
            <div class="company-name">CÔNG TY CỔ PHẦN SALEPRO</div>
            <div class="company-info">
                Địa chỉ: 123 Đường ABC, Quận XYZ, Thành phố Hà Nội<br>
                Điện thoại: (024) 1234 5678 | Email: info@salepro.com<br>
                Mã số thuế: 0123456789
            </div>
            <div class="invoice-title">Hóa đơn bán hàng</div>
            <div class="invoice-code">${invoice.invoiceCode}</div>
        </div>
        
        <div class="invoice-info">
            <div class="info-section">
                <h4>Thông tin khách hàng</h4>
                <div class="info-row">
                    <span class="info-label">Tên khách hàng:</span>
                    <span class="info-value">${invoice.customerName || 'Khách lẻ'}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Địa chỉ:</span>
                    <span class="info-value">-</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Điện thoại:</span>
                    <span class="info-value">-</span>
                </div>
            </div>
            
            <div class="info-section">
                <h4>Thông tin hóa đơn</h4>
                <div class="info-row">
                    <span class="info-label">Ngày bán:</span>
                    <span class="info-value">${this.formatDate(invoice.invoiceDate)}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Người bán:</span>
                    <span class="info-value">${invoice.soldBy || 'N/A'}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Chi nhánh:</span>
                    <span class="info-value">${invoice.branch || 'N/A'}</span>
                </div>
            </div>
        </div>
        
        <table class="products-table">
            <thead>
                <tr>
                    <th width="50">STT</th>
                    <th width="120">Mã hàng</th>
                    <th>Tên hàng</th>
                    <th width="80">Số lượng</th>
                    <th width="100">Đơn giá</th>
                    <th width="100">Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                ${this.generateProductRows(invoice.invoiceDetails)}
            </tbody>
        </table>
        
        <div class="summary-section">
            <table class="summary-table">
                <tr>
                    <td class="summary-label">Tổng tiền hàng (${invoice.totalItems || 0}):</td>
                    <td class="summary-value">${this.formatNumber(invoice.subTotal)}</td>
                </tr>
                <tr>
                    <td class="summary-label">Giảm giá:</td>
                    <td class="summary-value">${this.formatNumber(invoice.discountAmount)}</td>
                </tr>
                <tr>
                    <td class="summary-label">Thuế VAT:</td>
                    <td class="summary-value">${this.formatNumber(invoice.VATAmount)}</td>
                </tr>
                <tr class="total-row">
                    <td class="summary-label">Tổng thanh toán:</td>
                    <td class="summary-value">${this.formatNumber(invoice.totalAmount)}</td>
                </tr>
            </table>
        </div>
        
        <div class="signature-section">
            <div class="signature-box">
                <div class="signature-title">Người mua hàng</div>
                <div class="signature-line">(Ký và ghi rõ họ tên)</div>
            </div>
            
            <div class="signature-box">
                <div class="signature-title">Người bán hàng</div>
                <div class="signature-line">${invoice.soldBy || 'N/A'}</div>
            </div>
        </div>
        
        <div class="footer">
            Hóa đơn được in vào ngày ${currentDate}<br>
            Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!
        </div>
    </div>
</body>
</html>`;
    },

    generateProductRows: function (products) {
        if (!products || products.length === 0) {
            return '<tr><td colspan="6" class="text-center">Không có sản phẩm</td></tr>';
        }
        
        let rows = '';
        products.forEach((product, index) => {
            const quantity = product.quantity || 0;
            const unitPrice = product.unitPrice || 0;
            const totalPrice = quantity * unitPrice;
            
            rows += `
                <tr>
                    <td class="text-center">${index + 1}</td>
                    <td class="product-code">${product.productCode || ''}</td>
                    <td>${product.productName || ''}</td>
                    <td class="text-center">${quantity}</td>
                    <td class="text-right">${this.formatNumber(unitPrice)}</td>
                    <td class="text-right">${this.formatNumber(totalPrice)}</td>
                </tr>
            `;
        });
        
        return rows;
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
        const notificationClass = type === 'success' ? 'alert-success' :
                type === 'error' ? 'alert-danger' : 'alert-info';

        const notification = $(`
            <div class="alert ${notificationClass} alert-dismissible fade show notification-alert" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999; min-width: 300px;">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `);

        $('body').append(notification);

        setTimeout(() => {
            notification.alert('close');
        }, 5000);
    },
    
    updateModal: function (data) {
        console.log('Received data:', data);
        this.updateHeader(data);
        this.updateInfo(data);
        this.updateProducts(data);
        this.updateSummary(data);
        this.updatePaymentHistory(data);
    },

    updateHeader: function (invoice) {
        $('.customer-name .name-text').text(invoice.customerName);
        $('.invoice-code').text(invoice.invoiceCode);
        $('.invoice-status')
                .removeClass('badge-success badge-warning badge-danger badge-info badge-secondary')
                .addClass(this.getStatusBadgeClass(invoice.status))
                .text(this.getStatusText(invoice.status));
        $('.branch-info').text(invoice.branch);
    },

    updateInfo: function (invoice) {
        $('.created-by').text(invoice.createdBy || 'N/A');
        $('.invoice-date').text(this.formatDate(invoice.invoiceDate));
        $('.update-date').text(this.formatDate(invoice.updateDate));
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
        $('.total-items').text(invoice.totalItems || 0);
        $('.total-amount').text(this.formatNumber(invoice.subTotal));
        $('.discount-amount').text(this.formatNumber(invoice.discountAmount));
        $('.VAT-amount').text(this.formatNumber(invoice.VATAmount));
        $('.need-to-pay').text(this.formatNumber(invoice.paidAmount));
        $('.paid-amount').text(this.formatNumber(invoice.paidAmount));
    },

    updatePaymentHistory: function (invoice) {
        const $tbody = $('.payment-history-table tbody');
        $tbody.empty();

        const paymentCode = 'TTHD' + String(invoice.invoiceId).padStart(6, '0');
        const amount = invoice.subTotal || invoice.totalAmount || 0;
        const paymentMethod = invoice.paymentMethod;
        const createdBy = invoice.createdBy;
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
        if (!status) return 'badge-info';
        switch (status.toLowerCase()) {
            case 'completed': return 'badge-success';
            case 'processing': return 'badge-warning';
            case 'cancelled': return 'badge-danger';
            case 'refunded': return 'badge-secondary';
            default: return 'badge-info';
        }
    },

    getStatusText: function (status) {
        if (!status) return 'N/A';
        switch (status.toLowerCase()) {
            case 'completed': return 'Hoàn thành';
            case 'processing': return 'Đang xử lý';
            case 'cancelled': return 'Không giao được';
            case 'refunded': return 'Đã hủy';
            default: return status;
        }
    },

    formatDate: function (dateString) {
        if (!dateString) return 'N/A';
        if (typeof dateString === 'string' && dateString.match(/\d{2}\/\d{2}\/\d{4}/)) {
            return dateString;
        }

        let date;
        if (typeof dateString === 'string') {
            date = new Date(dateString);
        } else if (dateString instanceof Date) {
            date = dateString;
        } else {
            return 'N/A';
        }

        if (isNaN(date.getTime())) return 'N/A';

        return date.toLocaleDateString('vi-VN', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },

    formatNumber: function (number) {
        if (!number || isNaN(number)) return '0';
        return new Intl.NumberFormat('vi-VN').format(number);
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
    },

    // Function để in hóa đơn
    handlePrintInvoice: function ($button) {
        if (!this.currentInvoiceId) {
            this.showNotification('Không tìm thấy ID hóa đơn để in!', 'error');
            return;
        }

        const originalHtml = $button.html();
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang tải...');

        $.ajax({
            url: 'InvoiceDetailServlet',
            type: 'GET',
            data: {
                action: 'getPrintData',
                invoiceId: this.currentInvoiceId
            },
            success: (response) => {
                $button.prop('disabled', false).html(originalHtml);
                this.openPrintWindow(response);
            },
            error: (xhr, status, error) => {
                $button.prop('disabled', false).html(originalHtml);
                console.error('Print error:', xhr, status, error);
                this.showNotification('Lỗi khi tải dữ liệu in: ' + (error || 'Unknown error'), 'error');
            }
        });
    },

// Function tạo cửa sổ in
    openPrintWindow: function (invoiceData) {
        const printContent = this.generatePrintHTML(invoiceData);

        // Mở cửa sổ mới để in
        const printWindow = window.open('', '_blank', 'width=800,height=600');
        printWindow.document.write(printContent);
        printWindow.document.close();

        // Đợi load xong rồi in
        printWindow.onload = function () {
            printWindow.focus();
            printWindow.print();

            // Đóng cửa sổ sau khi in (optional)
            printWindow.onafterprint = function () {
                printWindow.close();
            };
        };
    },

// Function tạo HTML để in
    generatePrintHTML: function (invoice) {
        const currentDate = new Date().toLocaleDateString('vi-VN');

        return `
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa đơn ${invoice.invoiceCode}</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Times New Roman', serif;
            font-size: 14px;
            line-height: 1.4;
            color: #000;
            background: white;
        }
        
        .invoice-container {
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .header {
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 2px solid #000;
            padding-bottom: 20px;
        }
        
        .company-name {
            font-size: 24px;
            font-weight: bold;
            color: #2c5aa0;
            margin-bottom: 5px;
        }
        
        .company-info {
            font-size: 12px;
            color: #666;
            margin-bottom: 20px;
        }
        
        .invoice-title {
            font-size: 20px;
            font-weight: bold;
            text-transform: uppercase;
            margin-bottom: 10px;
        }
        
        .invoice-code {
            font-size: 16px;
            color: #2c5aa0;
            font-weight: bold;
        }
        
        .invoice-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        
        .info-section {
            flex: 1;
        }
        
        .info-section h4 {
            font-size: 14px;
            margin-bottom: 10px;
            color: #2c5aa0;
            border-bottom: 1px solid #ddd;
            padding-bottom: 5px;
        }
        
        .info-row {
            display: flex;
            margin-bottom: 5px;
        }
        
        .info-label {
            width: 120px;
            font-weight: bold;
        }
        
        .info-value {
            flex: 1;
        }
        
        .products-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        
        .products-table th,
        .products-table td {
            border: 1px solid #000;
            padding: 8px;
            text-align: left;
        }
        
        .products-table th {
            background-color: #2c5aa0;
            color: white;
            font-weight: bold;
            text-align: center;
        }
        
        .products-table .text-center {
            text-align: center;
        }
        
        .products-table .text-right {
            text-align: right;
        }
        
        .products-table .product-code {
            color: #2c5aa0;
            font-weight: bold;
        }
        
        .summary-section {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 30px;
        }
        
        .summary-table {
            width: 300px;
            border-collapse: collapse;
        }
        
        .summary-table td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        
        .summary-table .summary-label {
            background-color: #f8f9fa;
            font-weight: bold;
            width: 60%;
        }
        
        .summary-table .summary-value {
            text-align: right;
            width: 40%;
        }
        
        .summary-table .total-row {
            background-color: #2c5aa0;
            color: white;
            font-weight: bold;
        }
        
        .signature-section {
            display: flex;
            justify-content: space-between;
            margin-top: 50px;
            padding-top: 20px;
        }
        
        .signature-box {
            text-align: center;
            width: 200px;
        }
        
        .signature-title {
            font-weight: bold;
            margin-bottom: 50px;
        }
        
        .signature-line {
            border-top: 1px solid #000;
            margin-top: 50px;
            padding-top: 5px;
            font-size: 12px;
        }
        
        .footer {
            text-align: center;
            margin-top: 30px;
            font-size: 12px;
            color: #666;
            border-top: 1px solid #ddd;
            padding-top: 15px;
        }
        
        @media print {
            body {
                margin: 0;
                padding: 0;
            }
            
            .invoice-container {
                padding: 0;
                margin: 0;
                max-width: none;
                width: 100%;
            }
            
            .no-print {
                display: none !important;
            }
            
            @page {
                margin: 1cm;
                size: A4;
            }
        }
    </style>
</head>
<body>
    <div class="invoice-container">
        <!-- Header -->
        <div class="header">
            <div class="company-name">CÔNG TY CỔ PHẦN SALEPRO</div>
            <div class="company-info">
                Địa chỉ: 123 Đường ABC, Quận XYZ, Thành phố Hà Nội<br>
                Điện thoại: (024) 1234 5678 | Email: info@salepro.com<br>
                Mã số thuế: 0123456789
            </div>
            <div class="invoice-title">Hóa đơn bán hàng</div>
            <div class="invoice-code">${invoice.invoiceCode}</div>
        </div>
        
        <!-- Invoice Info -->
        <div class="invoice-info">
            <div class="info-section">
                <h4>Thông tin khách hàng</h4>
                <div class="info-row">
                    <span class="info-label">Tên khách hàng:</span>
                    <span class="info-value">${invoice.customerName || 'Khách lẻ'}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Địa chỉ:</span>
                    <span class="info-value">-</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Điện thoại:</span>
                    <span class="info-value">-</span>
                </div>
            </div>
            
            <div class="info-section">
                <h4>Thông tin hóa đơn</h4>
                <div class="info-row">
                    <span class="info-label">Ngày bán:</span>
                    <span class="info-value">${this.formatDate(invoice.invoiceDate)}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Người bán:</span>
                    <span class="info-value">${invoice.soldBy || 'N/A'}</span>
                </div>
                <div class="info-row">
                    <span class="info-label">Chi nhánh:</span>
                    <span class="info-value">${invoice.branch || 'N/A'}</span>
                </div>
            </div>
        </div>
        
        <!-- Products Table -->
        <table class="products-table">
            <thead>
                <tr>
                    <th width="50">STT</th>
                    <th width="120">Mã hàng</th>
                    <th>Tên hàng</th>
                    <th width="80">Số lượng</th>
                    <th width="100">Đơn giá</th>
                    <th width="100">Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                ${this.generateProductRows(invoice.invoiceDetails)}
            </tbody>
        </table>
        
        <!-- Summary -->
        <div class="summary-section">
            <table class="summary-table">
                <tr>
                    <td class="summary-label">Tổng tiền hàng (${invoice.totalItems || 0}):</td>
                    <td class="summary-value">${this.formatNumber(invoice.subTotal)}</td>
                </tr>
                <tr>
                    <td class="summary-label">Giảm giá:</td>
                    <td class="summary-value">${this.formatNumber(invoice.discountAmount)}</td>
                </tr>
                <tr>
                    <td class="summary-label">Thuế VAT:</td>
                    <td class="summary-value">${this.formatNumber(invoice.VATAmount)}</td>
                </tr>
                <tr class="total-row">
                    <td class="summary-label">Tổng thanh toán:</td>
                    <td class="summary-value">${this.formatNumber(invoice.totalAmount)}</td>
                </tr>
            </tbody>
        </table>
        
        <!-- Signatures -->
        <div class="signature-section">
            <div class="signature-box">
                <div class="signature-title">Người mua hàng</div>
                <div class="signature-line">(Ký và ghi rõ họ tên)</div>
            </div>
            
            <div class="signature-box">
                <div class="signature-title">Người bán hàng</div>
                <div class="signature-line">${invoice.soldBy || 'N/A'}</div>
            </div>
        </div>
        
        <!-- Footer -->
        <div class="footer">
            Hóa đơn được in vào ngày ${currentDate}<br>
            Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!
        </div>
    </div>
</body>
</html>`;
    },

// Function tạo các dòng sản phẩm
    generateProductRows: function (products) {
        if (!products || products.length === 0) {
            return '<tr><td colspan="6" class="text-center">Không có sản phẩm</td></tr>';
        }

        let rows = '';
        products.forEach((product, index) => {
            const quantity = product.quantity || 0;
            const unitPrice = product.unitPrice || 0;
            const totalPrice = quantity * unitPrice;

            rows += `
            <tr>
                <td class="text-center">${index + 1}</td>
                <td class="product-code">${product.productCode || ''}</td>
                <td>${product.productName || ''}</td>
                <td class="text-center">${quantity}</td>
                <td class="text-right">${this.formatNumber(unitPrice)}</td>
                <td class="text-right">${this.formatNumber(totalPrice)}</td>
            </tr>
        `;
        });

        return rows;
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

