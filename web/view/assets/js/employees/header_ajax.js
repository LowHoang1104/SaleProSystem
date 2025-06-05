$(document).on('click', '#addInvoiceBtn', function () {
    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: {action: 'addInvoice'},
        success: function (html) {
            $('#headerSection').html(html);
            $.ajax({
                url: 'CartServlet',
                type: 'POST',
                data: {action: 'loadCart'},
                success: function (cartHtml) {
                    $('#cartSection').html(cartHtml);
                },
                error: function () {
                    alert('Lỗi khi tải giỏ hàng');
                }
            });
        },
        
        error: function (xhr, status, error) {
            console.error('Lỗi khi thêm hóa đơn:', error);
            alert('Thêm hóa đơn thất bại, vui lòng thử lại.');
        }
    });
});

$(document).on('click', '.removeInvoiceBtn', function (e) {
    e.stopPropagation();  // Ngừng sự kiện click tiếp tục lan ra ngoài
    const invoiceId = $(this).data('id');
    console.error('xoa hóa đơn:', invoiceId);

    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: { action: 'removeInvoice', invoiceId: invoiceId },
        success: function (html) {
            $('#headerSection').html(html);
            $.ajax({
                url: 'CartServlet',
                type: 'POST',
                data: { action: 'loadCart' },
                success: function (cartHtml) {
                    $('#cartSection').html(cartHtml);
                },
                error: function () {
                    alert('Lỗi khi tải giỏ hàng');
                }
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi xóa hóa đơn:', error);
            alert('Xóa hóa đơn thất bại, vui lòng thử lại.');
        }
    });
});

$(document).on('click', '.invoice-tab', function () {
    const invoiceId = $(this).data('id');
    console.error('chon hóa đơn:', invoiceId);

    $.ajax({
        url: 'HeaderServlet',
        type: 'POST',
        data: { action: 'selectInvoice', invoiceId: invoiceId },
        success: function (html) {
            $('#headerSection').html(html);

            $.ajax({
                url: 'CartServlet',
                type: 'POST',
                data: { action: 'loadCart' },
                success: function (cartHtml) {
                    $('#cartSection').html(cartHtml);
                },
                error: function () {
                    alert('Lỗi khi tải giỏ hàng');
                }
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi truy cập đến hóa đơn:', error);
        }
    });
});