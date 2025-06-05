function toggleCartMenu(button) {
    const menu = button.nextElementSibling;
    const isShown = menu.style.display === 'block';

    // Ẩn tất cả menu khác
    document.querySelectorAll('.cart-menu').forEach(m => m.style.display = 'none');

    // Toggle menu hiện tại
    menu.style.display = isShown ? 'none' : 'block';
}



function hideDetailPanel() {
    document.getElementById('detailOverlay').style.display = 'none';
    document.getElementById('detailPanel').style.display = 'none';
}


function showPaymentPanel() {

    document.getElementById('paymentOverlay').style.display = 'block';
    document.getElementById('paymentPanel').style.display = 'flex';

    $.ajax({
        url: 'PaymentServlet',
        type: 'GET',
        data: {action: 'getPaymentInfo'},
        success: function (paymentHtml) {
            console.log("Update");
            $('#paymentSection').html(paymentHtml);
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

// Chuyển tab trong panel
function showTab(tabName) {
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.toggle('active', btn.textContent.toLowerCase().includes(tabName));
    });
    document.getElementById('generalTab').classList.toggle('hidden', tabName !== 'general');
    document.getElementById('descriptionTab').classList.toggle('hidden', tabName !== 'description');
}

// Thay đổi số lượng trong panel chi tiết
function changeDetailQuantity(delta) {
    const quantityInput = document.getElementById('detailQuantity');
    let current = parseInt(quantityInput.value) || 1;
    current += delta;
    if (current < 1)
        current = 1;
    quantityInput.value = current;
}

// Định dạng tiền (có thể tái sử dụng)
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN').format(amount) + ' đ';
}


const customerInput = document.getElementById('customerInput');
const clearBtn = document.getElementById('clearBtn');
const resultDiv = document.getElementById('customerResult');

function toggleClearBtn() {
    if (customerInput.disabled && customerInput.value.length > 0) {
        clearBtn.style.display = 'block';
    } else {
        clearBtn.style.display = 'none';
    }
}


customerInput.addEventListener('input', function () {
    if (!customerInput.disabled) {
        clearBtn.style.display = 'none';
    }
});

// remove 
clearBtn.addEventListener('click', function () {
    customerInput.value = '';
    customerInput.disabled = false;
    customerInput.style.color = 'initial';
    clearBtn.style.display = 'none';
    resultDiv.style.display = 'none';
    customerInput.focus();

    fetch('CustomerSearchServlet?action=clearSelectedCustomer', {
        method: 'POST'
    })
            .then(res => {
                if (!res.ok) {
                    console.error('Lỗi khi xóa khách đã chọn trên server');
                } else {
                    console.log('Đã xóa khách đã chọn trên server thành công');
                }
            })
            .catch(err => {
                console.error('Lỗi mạng khi xóa khách:', err);
            });
});

// change phone
customerInput.addEventListener('change', function () {
    const phone = this.value.trim();
    if (!phone) {
        resultDiv.style.display = 'none';
        resultDiv.innerHTML = '';
        toggleClearBtn();
        return;
    }
    fetch(`CustomerSearchServlet?phone=${encodeURIComponent(phone)}`)
            .then(res => {
                if (!res.ok) {
                    if (res.status === 404)
                        return null;
                    throw new Error('Lỗi khi tìm kiếm khách hàng');
                }
                return res.json();
            })
            .then(customer => {
                if (!customer) {
                    resultDiv.innerHTML = '<div style="padding:8px;">Không tìm thấy khách hàng</div>';
                    resultDiv.style.display = 'block';
                    return;
                }

                resultDiv.innerHTML = `<div class="customer-item" style="padding:8px; cursor:pointer;" data-id="${customer.customerId}" data-name="${customer.fullName}">
          ${customer.fullName}
      </div>`;
                resultDiv.style.display = 'block';

                // Bắt sự kiện click chọn khách
                resultDiv.querySelector('.customer-item').addEventListener('click', function () {
                    customerInput.value = this.dataset.name;
                    customerInput.disabled = true;
                    customerInput.style.color = 'blue';
                    toggleClearBtn();
                    resultDiv.style.display = 'none';
                });
                toggleClearBtn();
            })
            .catch(err => {
                resultDiv.innerHTML = `<div style="padding:8px; color:red;">${err.message}</div>`;
                resultDiv.style.display = 'block';
            });
});




// Go to page function
function goToPage(page) {
    window.location.href = 'CashierServlet?page=' + page;
}



// Handle bank account visibility
document.querySelectorAll('input[name="paymentMethod"]').forEach(radio => {
    radio.addEventListener('change', function () {
        if (this.value === 'transfer') {
            const bankAccounts = document.getElementById('bankAccounts');
            const noBankMsg = document.getElementById('noBankMsg');
            if (bankAccounts.children.length > 1) {
                bankAccounts.style.display = 'block';
                noBankMsg.style.display = 'none';
            } else {
                bankAccounts.style.display = 'none';
                noBankMsg.style.display = 'block';
            }
        } else {
            document.getElementById('bankAccounts').style.display = 'none';
            document.getElementById('noBankMsg').style.display = 'none';
        }
    });
});

// Auto-refresh every 30 seconds
setInterval(function () {
    console.log('Auto-sync check...');
}, 30000);




