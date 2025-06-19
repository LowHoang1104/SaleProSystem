

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

// ================================Customer

document.addEventListener('DOMContentLoaded', function () {
    const customerInput = document.getElementById('customerInput');
    const clearBtn = document.getElementById('clearBtn');
    const resultDiv = document.getElementById('customerResult');

    function getContextPath() {
        const path = window.location.pathname;
        const contextPath = path.substring(0, path.indexOf('/', 1));
        console.log('Context path:', contextPath);
        return contextPath;
    }

    customerInput.addEventListener('change', function () {
        const phone = this.value.trim();
        console.log('Phone input changed:', phone);

        if (!phone) {
            resultDiv.style.display = 'none';
            resultDiv.innerHTML = '';
            toggleClearBtn();
            return;
        }

        clearTimeout(customerInput.searchTimeout);
        customerInput.searchTimeout = setTimeout(() => {
            searchCustomer(phone);
        }, 300);
    });


    function searchCustomer(phone) {

        const contextPath = getContextPath();
        const servletUrl = `${contextPath}/CustomerSearchServlet?phone=${encodeURIComponent(phone)}`;

        $.ajax({
            url: servletUrl,
            type: 'GET',
            success: function (customer) {

                resultDiv.innerHTML = `<div class="customer-item" style="padding:8px; cursor:pointer;" data-id="${customer.customerId}" data-name="${customer.fullName}" data-phone="${customer.phone || ''}">
                    ${customer.fullName} - ${customer.phone || ''}
                </div>`;
                resultDiv.style.display = 'block';

                // Bắt sự kiện click chọn khách
                resultDiv.querySelector('.customer-item').addEventListener('click', function () {
                    const selectedCustomer = {
                        customerId: this.dataset.id,
                        fullName: this.dataset.name,
                        phone: this.dataset.phone
                    };

                    customerInput.value = selectedCustomer.fullName;
                    customerInput.disabled = true;
                    customerInput.style.color = 'blue';
                    resultDiv.style.display = 'none';
                    saveCustomerToSession(selectedCustomer.customerId);
                    toggleClearBtn();
                });
            },

            error: function (err) {
                console.error('Search error:', err);

                let errorMessage = 'Lỗi kết nối: Không xác định';

                if (err.responseJSON && err.responseJSON.error) {
                    errorMessage = err.responseJSON.error;
                }

                resultDiv.innerHTML = `<div style="padding:8px; color:red;">${errorMessage}</div>`;
                resultDiv.style.display = 'block';
            }
        });
    }

// Xử lý sự kiện nút Clear
    clearBtn.addEventListener('click', function () {
        console.log('Clear button clicked'); // Debug log

        customerInput.value = '';  // Xóa nội dung ô tìm kiếm
        customerInput.disabled = false;  // Bật lại ô tìm kiếm
        customerInput.style.color = '';  // Đặt lại màu chữ
        clearBtn.style.display = 'none';  // Ẩn nút Clear
        resultDiv.style.display = 'none';  // Ẩn kết quả tìm kiếm
        resultDiv.innerHTML = '';  // Xóa nội dung kết quả
        customerInput.focus();  // Đặt lại focus vào ô tìm kiếm

        // Gửi yêu cầu xóa khách đã chọn - SỬA LỖI TẠI ĐÂY
        const contextPath = getContextPath();
        const clearUrl = `${contextPath}/CustomerSearchServlet`;


        $.ajax({
            url: clearUrl,
            type: 'POST',
            data: {
                action: 'clearSelectedCustomer'
            },
            success: function (data) {
                if (data.success) {
                    console.log('Đã xóa khách đã chọn trên server thành công');
                } else {
                    console.error('Lỗi khi xóa khách:', data.message);
                }
            },
            error: function (err) {
                console.error('Lỗi mạng khi xóa khách:', err);
            }
        });
    });


    // Lưu khách hàng vào session
    function saveCustomerToSession(customerId) {
        const contextPath = getContextPath();
        const saveUrl = `${contextPath}/CustomerSearchServlet`;
        $.ajax({
            url: saveUrl,
            type: 'POST',
            data: {
                action: 'saveSelectedCustomer',
                customerId: customerId
            },
            success: function (data) {
                console.log('Save response data:', data); // Debug log
                if (data.success) {
                    console.log("Khách hàng đã được lưu vào session!");
                } else {
                    console.error("Lỗi khi lưu khách hàng vào session:", data.message);
                }
            },
            error: function (err) {
                alert('Không thể lưu thông tin khách hàng. Vui lòng thử lại!');
            }
        });
    }

    function toggleClearBtn() {
        if (customerInput.value.trim() !== "") {
            clearBtn.style.display = 'block';
        } else {
            clearBtn.style.display = 'none';
        }
    }

});

function goToPage(page) {
    const url = new URL(window.location);
    url.searchParams.set('page', page);
    window.location.href = url.toString();
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
