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
    // Khi thay đổi date, reload trang đầu tiên
    $(document).on('change', '.date-input', function () {
        if ($('#reportOverlay').hasClass('active')) {
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

