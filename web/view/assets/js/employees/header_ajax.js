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
//                    $('body').removeClass('loading');
                });
            });
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi truy cập đến hóa đơn:', error);
            isProcessing = false;
//            $('body').removeClass('loading');
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
