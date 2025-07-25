function showPaymentPanel() {
    setTimeout(function () {
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
    }, 200);
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

    function formatAndValidate(input) {
        // Remove non-numeric characters
        let value = input.value.replace(/[^\d]/g, '');

        // Validate
        if (value.length > 12) {
            showError('Số tiền quá lớn');
            value = value.substring(0, 12);
        } else {
            hideError();
        }

        // Format with commas
        if (value) {
            input.value = parseInt(value).toLocaleString('vi-VN');
        } else {
            input.value = '';
        }
    }

    function showError(msg) {
        const errorDiv = document.getElementById('amountError');
        const input = document.getElementById('paidAmount');

        errorDiv.textContent = msg;
        errorDiv.style.display = 'block';
        input.style.borderColor = 'red';
    }

    function hideError() {
        const errorDiv = document.getElementById('amountError');
        const input = document.getElementById('paidAmount');

        errorDiv.style.display = 'none';
        input.style.borderColor = '';
    }

// ===== SỬA AJAX CALL ===== 
    $(document).on('change', '#paidAmount', function () {
        var paidAmount = $(this).val().replace(/[^\d]/g, '');

        if (!paidAmount) {
            showError('Vui lòng nhập số tiền');
            return;
        }

        hideError();

        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                paidAmount: paidAmount,
                action: 'updatePaidAmount'
            },
            success: function (html) {
                $('#paymentPanel').html(html);
            },
            error: function () {
                showError('Lỗi khi cập nhật');
            }
        });
    });



    $(document).on('click', '#checkout', function () {
        const paidAmount = $('#paidAmount').val().replace(/[^\d]/g, '');

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

    // FIX: Sửa lỗi 'clickk' thành 'click'
    $(document).on('click', '#use-points', function () {
        console.log('Sử dụng điểm');
        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                action: 'usePoints'
            },
            success: function (html) {
                $('#paymentPanel').html(html);
                console.log('Sử dụng điểm thành công');
            },
            error: function () {
                alert('Lỗi khi sử dụng điểm');
            }
        });
    });

    // FIX: Sửa lỗi 'clickk' thành 'click'
    $(document).on('click', '#add-points', function () {
        console.log('Tích điểm từ tiền thừa');
        $.ajax({
            url: 'PaymentServlet',
            method: 'POST',
            data: {
                action: 'addPoints'
            },
            success: function (html) {
                $('#paymentPanel').html(html);
                console.log('Tích điểm thành công');
            },
            error: function () {
                alert('Lỗi khi tích điểm');
            }
        });
    });

});



