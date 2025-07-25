function showPaymentPanel() {
    document.getElementById('paymentOverlay').style.display = 'block';
    document.getElementById('paymentPanel').style.display = 'flex';
    $.ajax({
        url: 'PaymentServlet',
        type: 'GET',
        data: {action: 'getPaymentInfo'},
        success: function (paymentHtml) {
            console.log("Update");
            $('#paymentPanel').html(paymentHtml);
        },
        error: function () {
            console.error('Lỗi khi tải thông tin thanh toán');
        }
    });
}

function hidePaymentPanel() {
    document.getElementById('paymentOverlay').style.display = 'none';
    document.getElementById('paymentPanel').style.display = 'none';
}

$(document).ready(function () {
    console.log('Script ready');

    $(document).on('change', '#staffDropdown', function () {
        console.log('Cập nhật id nhân viên');
        var staffId = $(this).val();
        console.log(staffId);

        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                staffId: staffId,
                action: 'updateInvoiceSaleId'
            },
            success: function (response) {
                console.log('Cập nhật nhân viên thành công');
            },
            error: function () {

            }
        });
    });

    $(document).on('change', '#discountInput', function () {
        console.log('Cập nhật discount');
        var discount = $(this).val();
        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                discount: discount,
                action: 'updateDiscount'
            },
            success: function (html) {
                $('#paymentPanel').html(html);
                console.log('Cập nhật mã giảm giá thành công' + discount);
            },
            error: function () {
                alert('Lỗi khi cập nhật mã giảm giá');
            }
        });
    });

    $(document).on('change', '#paidAmount', function () {
        var paidAmount = $(this).val().replace(/[^\d]/g, '');

        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                paidAmount: paidAmount,
                action: 'updatePaidAmount'
            },
            success: function (html) {
                $('#paymentPanel').html(html);
            }
        });
    });
});

$(document).on('click', '#checkout', function () {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'PaymentServlet';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'checkout';
    form.appendChild(actionInput);

    //staffId
    const staffSelect = document.getElementById('staffDropdown');
    const staffId = document.createElement('input');
    staffId.type = 'hidden';
    staffId.name = 'staffId';
    staffId.value = staffSelect.value;
    form.appendChild(staffId);

    // Payment Method
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
    if (!paymentMethod) {
        alert('Vui lòng chọn phương thức thanh toán');
        return;
    }

    const paymentMethodInput = document.createElement('input');
    paymentMethodInput.type = 'hidden';
    paymentMethodInput.name = 'paymentMethod';
    paymentMethodInput.value = paymentMethod.value;
    form.appendChild(paymentMethodInput);

    // Get fundId based on payment method
    let fundId = null;
    if (paymentMethod.value === '1') { // Tiền mặt
        const cashSelect = document.getElementById('cashFundSelect');
        if (cashSelect) {
            fundId = cashSelect.value;
        }
    } else if (paymentMethod.value === '2') { // Chuyển khoản
        const bankSelect = document.getElementById('bankAccountSelect');
        if (bankSelect) {
            fundId = bankSelect.value;
        }
    }

    if (!fundId) {
        alert('Vui lòng chọn quỹ thanh toán');
        return;
    }

    const fundIdInput = document.createElement('input');
    fundIdInput.type = 'hidden';
    fundIdInput.name = 'fundId';
    fundIdInput.value = fundId;
    form.appendChild(fundIdInput);
    
    
    document.body.appendChild(form);
    form.submit();
});

