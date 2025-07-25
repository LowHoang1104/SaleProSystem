$(document).on('click', '#addInvoiceBtn', function () {
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

$(document).on('click', '.removeInvoiceBtn', function (e) {
    e.stopPropagation();
    const invoiceId = $(this).data('id');
    console.error('xoa hóa đơn:', invoiceId);

    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: {action: 'removeInvoice', invoiceId: invoiceId},
        success: function (html) {
            $('#headerSection').html(html);
            loadCart(function () {
                loadCustomerInfo(function () {
                });
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi xóa hóa đơn:', error);
        }
    });
});

$(document).on('click', '.invoice-tab', function () {
    const invoiceId = $(this).data('id');
    console.log('Chọn hóa đơn:', invoiceId);

    isProcessing = true;
    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: {action: 'selectInvoice', invoiceId: invoiceId},
        success: function (html) {
            $('#headerSection').html(html);
            loadCart(function () {
                loadCustomerInfo(function () {
                });
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi truy cập đến hóa đơn:', error);
            isProcessing = false;
        }
    });
});

// Cập nhật giỏ hàng
function loadCart(callback) {
    $.ajax({
        url: 'CartServlet',
        type: 'POST',
        data: {action: 'loadCart'},
        success: function (cartHtml) {
            $('#cartSection').html(cartHtml);
            callback();
        },
        error: function () {
            alert('Lỗi khi tải giỏ hàng');
        }
    });
}

function loadCustomerInfo(callback) {
    console.log('Chọn customer1:');
    $.ajax({
        url: 'CustomerSearchServlet',
        type: 'POST',
        data: {action: 'displayCustomer'},
        success: function (data) {
            if (data != null && data.fullName) {
                $('#customerInput').val(data.fullName).prop('disabled', true);
                $('#clearBtn').show();
                $('#customerInput').css('color', 'blue');
            } else {
                $('#customerInput').val("").prop('disabled', false);
                $('#clearBtn').hide();
                $('#customerInput').css('color', '');
            }
            callback();
        },
        error: function () {
            alert('Lỗi khi tải thông tin khách hàng');
        }
    });
}

// =============SIDE PANEL FUNCTIONS============
function showSidePanel() {
    const overlay = document.getElementById('sidePanelOverlay');
    const panel = document.getElementById('sidePanel');
    if (overlay && panel) {
        overlay.classList.add('active');
        panel.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
}

function hideSidePanel() {
    const overlay = document.getElementById('sidePanelOverlay');
    const panel = document.getElementById('sidePanel');
    if (overlay && panel) {
        overlay.classList.remove('active');
        panel.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

// =============REPORT FUNCTIONS WITH PAGING============
function showEndOfDayReport() {
    console.log('Showing end of day report...');

    const overlay = document.getElementById('reportOverlay');
    const panel = document.getElementById('reportPanel');

    console.log('Report overlay found:', overlay);
    console.log('Report panel found:', panel);

    if (overlay && panel) {
        // Set ngày hiện tại cho date input
        $('.date-input').val(getCurrentDate());
        $('#employeeSelect').val('');
        $('#creatorSelect').val('');
        $('#warehouseSelect').val('A4');

        overlay.classList.add('active');
        panel.classList.add('active');
        document.body.style.overflow = 'hidden';

        // Gọi servlet để lấy listInvoice trang đầu tiên
        loadReportData(1);

        console.log('Report overlay and panel activated');
    } else {
        console.error('Report overlay or panel not found!');
    }
}

function hideEndOfDayReport() {
    console.log('Hiding end of day report...');

    const overlay = document.getElementById('reportOverlay');
    const panel = document.getElementById('reportPanel');

    if (overlay && panel) {
        overlay.classList.remove('active');
        panel.classList.remove('active');
        document.body.style.overflow = 'auto';
        console.log('Report overlay and panel deactivated');
    }
}

function loadReportData(page = 1) {
    const selectedDate = $('.date-input').val();
    const selectedEmployee = $('#employeeSelect').val();
    const selectedCreator = $('#creatorSelect').val();
    const selectedWarehouse = $('#warehouseSelect').val();
    
    console.log('Loading report data with params:' +selectedDate)
    console.log('Loading report data with params:', {
        date: selectedDate,
        page: page,
        employee: selectedEmployee,
        creator: selectedCreator,
        warehouse: selectedWarehouse
    });
    
    $.ajax({
        url: 'ReportEndDayServlet',
        method: 'POST',
        data: {
            action: 'getReportData',
            date: selectedDate,
            page: page,
            employeeId: selectedEmployee,
            creatorId: selectedCreator,
            warehouseSelect: selectedWarehouse
        },
        success: function (response) {
            $('#reportPanel').html(response);
            
            // Sau khi load xong, áp dụng logic hiển thị dựa trên warehouse
            applyWarehouseDisplay(selectedWarehouse);
        },
        error: function (xhr, status, error) {
            console.error('Error loading data:', error);
        }
    });
}

// Hàm riêng để xử lý hiển thị dựa trên warehouse
function applyWarehouseDisplay(warehouse) {
    console.log('Applying warehouse display for:', warehouse);
    
    const reportTitle = $('#reportTitle');
    const summaryView = $('#summaryView');
    const detailView = $('#detailView');

    if (warehouse === 'K80') {
        // Switch to summary view for K80
        reportTitle.text('Báo cáo cuối ngày tổng hợp');
        summaryView.show();
        detailView.hide();
        console.log('Switched to K80 summary view');
    } else {
        // Switch to detail view for other warehouses
        reportTitle.text('Báo cáo cuối ngày về bán hàng');
        summaryView.hide();
        detailView.show();
        console.log('Switched to A4 detail view');
    }  
}

// =============PAGINATION FUNCTIONS============
function goToFirstPage() {
    loadReportData(1);
}

function goToPrevPage() {
    const currentPage = parseInt($('.page-input').val()) || 1;
    if (currentPage > 1) {
        loadReportData(currentPage - 1);
    }
}

function goToNextPage() {
    const currentPage = parseInt($('.page-input').val()) || 1;
    const totalPages = parseInt($('.page-text').text().split('/')[1]) || 1;
    if (currentPage < totalPages) {
        loadReportData(currentPage + 1);
    }
}

function goToLastPage() {
    const totalPages = parseInt($('.page-text').text().split('/')[1]) || 1;
    loadReportData(totalPages);
}

function goToPage(page) {
    const pageNum = parseInt(page);
    const totalPages = parseInt($('.page-text').text().split('/')[1]) || 1;

    if (pageNum >= 1 && pageNum <= totalPages) {
        loadReportData(pageNum);
    } else {
        // Reset về trang hiện tại nếu số trang không hợp lệ
        const currentPage = parseInt($('.page-input').val()) || 1;
        $('.page-input').val(currentPage);
    }
}

function refreshReport() {
    const currentPage = parseInt($('.page-input').val()) || 1;
    loadReportData(currentPage);
}

function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// =============EVENT LISTENERS - GỘP TẤT CẢ VÀO 1 DOCUMENT.READY============
$(document).ready(function () {
    console.log('Document ready - initializing event listeners');

    // =============SIDE PANEL EVENTS============
    // Menu button click
    $(document).on('click', '#menuBtn', function (e) {
        e.stopPropagation();
        showSidePanel();
    });

    // Close button click
    $(document).on('click', '#closePanelBtn', function () {
        hideSidePanel();
    });

    // Click outside to close side panel
    $(document).on('click', '#sidePanelOverlay', function (e) {
        if (e.target === this) {
            hideSidePanel();
        }
    });

    // Click outside to close report
    $(document).on('click', '#reportOverlay', function (e) {
        if (e.target === this) {
            hideEndOfDayReport();
        }
    });

    // Prevent panels from closing when clicking inside
    $(document).on('click', '#sidePanel, #reportPanel', function (e) {
        e.stopPropagation();
    });

    // Close with Escape key
    $(document).keydown(function (e) {
        if (e.key === 'Escape') {
            const sidePanelOverlay = document.getElementById('sidePanelOverlay');
            const reportOverlay = document.getElementById('reportOverlay');

            if (reportOverlay && reportOverlay.classList.contains('active')) {
                hideEndOfDayReport();
            } else if (sidePanelOverlay && sidePanelOverlay.classList.contains('active')) {
                hideSidePanel();
            }
        }
    });

    // Menu item clicks
    $(document).on('click', '.menu-item', function () {
        const text = $(this).find('span').text();
        console.log('Menu item clicked:', text);

        if (text === 'Đăng xuất') {
            if (confirm('Bạn có chắc muốn đăng xuất?')) {
                window.location.href = '/logout';
            }
        } else if (text === 'Xem báo cáo cuối ngày') {
            console.log('Report menu clicked!');
            hideSidePanel(); // Đóng side panel trước

            // Đợi side panel đóng xong rồi mở báo cáo
            setTimeout(() => {
                showEndOfDayReport();
            }, 300); // Thời gian chờ để animation hoàn thành
            return;
        } else {
            console.log('Other menu clicked:', text);
        }

        // Close panel after action (không chạy cho báo cáo)
        hideSidePanel();
    });

    // Close report button
    $(document).on('click', '.close-report', function () {
        hideEndOfDayReport();
    });

    // =============REPORT FILTER EVENTS============
    // Khi thay đổi date, kiểm tra ngày tương lai và reload trang đầu tiên
    $(document).on('change', '.date-input', function () {
        if ($('#reportOverlay').hasClass('active')) {
            const selectedDate = new Date($(this).val());
            const today = new Date();
            today.setHours(23, 59, 59, 999);
            
            // Nếu chọn ngày tương lai, tự động reset về hôm nay
            if (selectedDate > today) {
                const currentDate = getCurrentDate();
                $(this).val(currentDate);
                console.log('Future date selected, reset to today:', currentDate);
                
                // Hiển thị thông báo
                showDateWarning('Không thể chọn ngày trong tương lai! Đã chuyển về ngày hôm nay.');
            }
            
            console.log('Date changed to:', $(this).val());
            loadReportData(1);
        }
    });

    // Khi thay đổi employee hoặc creator filter
    $(document).on('change', '#employeeSelect, #creatorSelect', function () {
        if ($('#reportOverlay').hasClass('active')) {
            console.log('Filter changed - Employee:', $('#employeeSelect').val(), 'Creator:', $('#creatorSelect').val());
            loadReportData(1);
        }
    });

    // SỬA LOGIC CHO WAREHOUSE SELECT - QUAN TRỌNG!
    $(document).on('change', '#warehouseSelect', function () {
        const warehouse = $(this).val();
        console.log('Warehouse changed to:', warehouse);

        if ($('#reportOverlay').hasClass('active')) {
            // Gửi request đến servlet để cập nhật warehouse selection
            loadReportData(1);
        }
    });

    // Page input validation
    $(document).on('input', '.page-input', function () {
        const value = parseInt($(this).val());
        const totalPages = parseInt($('.page-text').text().split('/')[1]) || 1;

        if (isNaN(value) || value < 1) {
            $(this).val('1');
        } else if (value > totalPages) {
            $(this).val(totalPages);
        }
    });

    // =============OTHER EVENTS============
    // Close export menu when clicking outside
    $(document).click(function (e) {
        const exportDropdown = $('.export-dropdown');
        const exportMenu = $('#exportMenu');
        if (!exportDropdown.is(e.target) && exportDropdown.has(e.target).length === 0) {
            exportMenu.removeClass('active');
        }
    });

    // Expand/Collapse row
    $(document).on('click', '.expand-btn', function () {
        const icon = $(this).find('i');
        if (icon.hasClass('fa-plus-square')) {
            icon.removeClass('fa-plus-square').addClass('fa-minus-square');
            console.log('Mở rộng chi tiết hóa đơn');
        } else {
            icon.removeClass('fa-minus-square').addClass('fa-plus-square');
            console.log('Thu gọn chi tiết hóa đơn');
        }
    });
});

// Function hiển thị thông báo cảnh báo
function showDateWarning(message) {
    // Xóa thông báo cũ nếu có
    $('.date-warning').remove();
    
    // Tạo thông báo mới
    const warning = $(`
        <div class="date-warning" style="
            position: fixed;
            top: 20px;
            right: 20px;
            background: #ff6b6b;
            color: white;
            padding: 12px 20px;
            border-radius: 6px;
            box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
            z-index: 10000;
            font-size: 14px;
            font-weight: 500;
            display: flex;
            align-items: center;
            animation: slideInRight 0.3s ease;
        ">
            <i class="fas fa-exclamation-triangle" style="margin-right: 8px;"></i>
            ${message}
        </div>
    `);
    
    // Thêm CSS animation nếu chưa có
    if (!$('#dateWarningCSS').length) {
        $('<style id="dateWarningCSS">').prop('type', 'text/css').html(`
            @keyframes slideInRight {
                from {
                    transform: translateX(100%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }
        `).appendTo('head');
    }
    
    $('body').append(warning);
    
    // Tự động xóa sau 3 giây
    setTimeout(() => {
        warning.fadeOut(300, function() {
            $(this).remove();
        });
    }, 3000);
}

let ReportExport = {
    isInitialized: false,
    
    init: function() {
        if (this.isInitialized) return;
        this.bindEvents();
        this.isInitialized = true;
        console.log('ReportExport module initialized');
    },
    
    bindEvents: function() {
        // Export Excel functionality
        $(document).on('click', '.export-btn', (e) => {
            e.preventDefault();
            this.handleExport($(e.target));
        });
        
        // Print functionality
        $(document).on('click', '[title="In báo cáo"]', (e) => {
            e.preventDefault();
            this.handlePrint();
        });
        
        // Zoom functions
        $(document).on('click', '[title="Phóng to"]', () => this.zoomIn());
        $(document).on('click', '[title="Thu nhỏ"]', () => this.zoomOut());
        
        // Keyboard shortcuts
        $(document).keydown((e) => {
            if ($('#reportOverlay').hasClass('active')) {
                if (e.ctrlKey && e.key === 'p') {
                    e.preventDefault();
                    this.handlePrint();
                } else if (e.ctrlKey && e.key === 's') {
                    e.preventDefault();
                    this.handleExport($('.export-btn'));
                }
            }
        });
    },
    
    handleExport: function($button) {
        const originalHtml = $button.html();
        
        // Get current filters
        const params = this.getCurrentFilters();
        
        // Validate
        if (!params.date) {
            alert('Vui lòng chọn ngày báo cáo!');
            return;
        }
        
        // Show loading
        $button.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i> Đang xuất...');
        
        // Export with AJAX blob
        $.ajax({
            url: 'ReportEndDayServlet',
            type: 'GET',
            data: {
                action: 'exportExcel',
                date: params.date,
                employeeId: params.employeeId,
                creatorId: params.creatorId,
                warehouseSelect: params.warehouseSelect
            },
            xhrFields: { responseType: 'blob' },
            success: (data) => {
                $button.prop('disabled', false).html(originalHtml);
                this.downloadFile(data, params.date);
                alert('Xuất báo cáo Excel thành công!');
            },
            error: (xhr, status, error) => {
                $button.prop('disabled', false).html(originalHtml);
                alert('Lỗi khi xuất báo cáo: ' + (xhr.responseText || error));
            }
        });
    },
    
    handlePrint: function() {
        const warehouse = $('#warehouseSelect').val() || 'A4';
        const params = this.getCurrentFilters();
        
        // Validate
        if (!params.date) {
            alert('Vui lòng chọn ngày báo cáo!');
            return;
        }
        
        // Show loading
        const $printBtn = $('[title="In báo cáo"]');
        const originalHtml = $printBtn.html();
        $printBtn.prop('disabled', true).html('<i class="fas fa-spinner fa-spin"></i>');
        
        // Load data first, then print
        $.ajax({
            url: 'ReportEndDayServlet',
            type: 'POST',
            data: {
                action: 'getReportData',
                date: params.date,
                employeeId: params.employeeId,
                creatorId: params.creatorId,
                warehouseSelect: params.warehouseSelect
            },
            success: (response) => {
                // Reset button
                $printBtn.prop('disabled', false).html(originalHtml);
                
                // Wait for DOM update then print
                setTimeout(() => {
                    const contentId = warehouse === 'K80' ? 'summaryView' : 'detailView';
                    const content = document.getElementById(contentId);
                    
                    if (!content || !content.innerHTML.trim()) {
                        alert('Không có dữ liệu để in!');
                        return;
                    }
                    
                    this.printDirectly(warehouse, content);
                }, 500);
            },
            error: (xhr, status, error) => {
                $printBtn.prop('disabled', false).html(originalHtml);
                alert('Lỗi khi tải dữ liệu: ' + (xhr.responseText || error));
            }
        });
    },
    
    printDirectly: function(warehouse, content) {
        const title = warehouse === 'K80' ? 'Báo cáo cuối ngày tổng hợp' : 'Báo cáo cuối ngày về bán hàng';
        const date = $('.date-input').val() || new Date().toISOString().split('T')[0];
        
        // Get actual content from the page
        let printContent = content.innerHTML;
        
        // Create clean print styles without noise
        const printStyles = `
            @media print {
                html, body {
                    margin: 0 !important;
                    padding: 0 !important;
                    background: white !important;
                    overflow: hidden !important;
                }
                * {
                    visibility: hidden !important;
                    box-shadow: none !important;
                    background: white !important;
                    color: black !important;
                }
                .print-section, .print-section * {
                    visibility: visible !important;
                    background: white !important;
                    color: black !important;
                }
                .print-section {
                    position: fixed !important;
                    top: 0 !important;
                    left: 0 !important;
                    width: 100vw !important;
                    height: 100vh !important;
                    margin: 0 !important;
                    padding: 15mm !important;
                    background: white !important;
                    z-index: 9999 !important;
                    box-sizing: border-box !important;
                }
                @page {
                    margin: 0 !important;
                    padding: 0 !important;
                    size: A4 !important;
                    background: white !important;
                }
                /* Hide everything else completely */
                body > *:not(.print-section) {
                    display: none !important;
                }
                /* Remove any overlays or backgrounds */
                body::before,
                body::after,
                html::before,
                html::after {
                    display: none !important;
                }
            }
            .print-section {
                display: none;
            }
            .print-section.printing {
                display: block !important;
                font-family: Arial, sans-serif !important;
                background: white !important;
                color: black !important;
                width: 100% !important;
                max-width: none !important;
            }
            .print-section h1 {
                text-align: center !important;
                color: black !important;
                margin: 0 0 8px 0 !important;
                font-size: 16px !important;
                font-weight: bold !important;
                background: white !important;
            }
            .print-section .print-date {
                text-align: center !important;
                margin: 0 0 15px 0 !important;
                font-weight: bold !important;
                color: black !important;
                background: white !important;
            }
            .print-section .print-info {
                margin: 0 0 12px 0 !important;
                font-size: 11px !important;
                color: black !important;
                text-align: center !important;
                background: white !important;
            }
            .print-section table {
                width: 100% !important;
                border-collapse: collapse !important;
                margin: 10px 0 !important;
                background: white !important;
            }
            .print-section table th,
            .print-section table td {
                border: 1px solid black !important;
                padding: 4px !important;
                text-align: left !important;
                color: black !important;
                background: white !important;
                font-size: 10px !important;
            }
            .print-section table th {
                background: #f5f5f5 !important;
                font-weight: bold !important;
                text-align: center !important;
                color: black !important;
            }
            .print-section div,
            .print-section span,
            .print-section p {
                background: white !important;
                color: black !important;
            }
        `;
        
        // Add or update print styles
        let styleElement = document.getElementById('printStyles');
        if (styleElement) {
            styleElement.remove();
        }
        
        styleElement = document.createElement('style');
        styleElement.id = 'printStyles';
        styleElement.innerHTML = printStyles;
        document.head.appendChild(styleElement);
        
        // Remove existing print section
        let printSection = document.getElementById('printSection');
        if (printSection) {
            printSection.remove();
        }
        
        // Create new print section
        printSection = document.createElement('div');
        printSection.id = 'printSection';
        printSection.className = 'print-section';
        
        // Get actual info from the page - only if selected
        const selectedEmployee = $('#employeeSelect').val() && $('#employeeSelect').val() !== '' 
            ? $('#employeeSelect option:selected').text() : '';
        const selectedCreator = $('#creatorSelect').val() && $('#creatorSelect').val() !== '' 
            ? $('#creatorSelect option:selected').text() : '';
        const selectedWarehouse = $('#warehouseSelect option:selected').text() || '';
        
        // Build print info only for selected fields
        let printInfoParts = [`<strong>Ngày bán:</strong> ${this.formatDate(date)}`];
        printInfoParts.push(`<strong>Ngày thanh toán:</strong> ${this.formatDate(date)}`);
        
        if (selectedWarehouse && selectedWarehouse !== 'Chọn kho') {
            printInfoParts.push(`<strong>Chi nhánh: Trung tâm</strong>`);
        }
        
        if (selectedEmployee) {
            printInfoParts.push(`<strong>Nhân viên:</strong> ${selectedEmployee}`);
        }
        
        if (selectedCreator) {
            printInfoParts.push(`<strong>Người tạo:</strong> ${selectedCreator}`);
        }
        
        // Generate complete print content with actual data
        printSection.innerHTML = `
            <h1>${title}</h1>
            <div class="print-date">Ngày: ${this.formatDate(date)}</div>
            <div class="print-info">
                ${printInfoParts.join(' | ')}
            </div>
            <div>${printContent}</div>
        `;
        
        document.body.appendChild(printSection);
        
        // Add printing class and trigger print
        printSection.classList.add('printing');
        
        // Hide page scrollbars and other elements
        document.body.style.overflow = 'hidden';
        
        // Trigger print dialog
        window.print();
        
        // Clean up after print
        setTimeout(() => {
            if (printSection) {
                printSection.remove();
            }
            document.body.style.overflow = '';
        }, 1000);
    },
    
    getCurrentFilters: function() {
        return {
            date: $('.date-input').val() || new Date().toISOString().split('T')[0],
            employeeId: $('#employeeSelect').val() || '',
            creatorId: $('#creatorSelect').val() || '',
            warehouseSelect: $('#warehouseSelect').val() || 'A4'
        };
    },
    
    downloadFile: function(data, date) {
        const url = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = url;
        link.download = `BaoCaoCuoiNgay_${date.replace(/-/g, '')}.xlsx`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    },
    
    formatDate: function(dateString) {
        try {
            return new Date(dateString).toLocaleDateString('vi-VN');
        } catch (e) {
            return dateString;
        }
    },
    
    zoomIn: function() {
        const content = document.querySelector('.report-content');
        if (content) {
            const currentZoom = parseFloat(content.style.zoom || 1);
            const newZoom = Math.min(currentZoom + 0.1, 2.0);
            content.style.zoom = newZoom;
        }
    },
    
    zoomOut: function() {
        const content = document.querySelector('.report-content');
        if (content) {
            const currentZoom = parseFloat(content.style.zoom || 1);
            const newZoom = Math.max(currentZoom - 0.1, 0.5);
            content.style.zoom = newZoom;
        }
    }
};

// Auto-initialize
$(document).ready(() => {
    ReportExport.init();
});