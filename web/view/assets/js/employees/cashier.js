//================= Detail Product
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

function goToPage(page) {
    const url = new URL(window.location);
    url.searchParams.set('page', page);
    window.location.href = url.toString();
}

// ================================ Search Customer - IMPROVED VERSION
document.addEventListener('DOMContentLoaded', function () {
    const customerInput = document.getElementById('customerInput');
    const clearBtn = document.getElementById('clearBtn');
    const addCustomerBtn = document.getElementById('addCustomerBtn');
    const resultDiv = document.getElementById('customerResult');

    // ✅ KHÔNG CẦN loadCustomerInfoOnPageLoad() NỮA - ĐÃ CÓ TRONG JSP!

    // Search functionality (unchanged)
    customerInput.addEventListener('input', function () {
        const phone = this.value.trim();

        if (!phone || this.disabled) {
            hideSearchResults();
            return;
        }

        if (!isValidPhoneFormat(phone)) {
            showErrorMessage('Số điện thoại không đúng định dạng');
            return;
        }

        clearTimeout(customerInput.searchTimeout);
        customerInput.searchTimeout = setTimeout(() => {
            searchCustomer(phone);
        }, 500);
    });

    // Clear button
    clearBtn.addEventListener('click', function () {
        // Reset UI
        customerInput.value = '';
        customerInput.disabled = false;
        customerInput.style.color = '';
        customerInput.style.fontWeight = '';
        hideSearchResults();

        // Toggle buttons
        clearBtn.style.display = 'none';
        addCustomerBtn.style.display = 'block';

        customerInput.focus();

        // Clear from server
        clearCustomerFromServer();
    });

    // Add customer button
    addCustomerBtn.addEventListener('click', function () {
        showAddCustomerPanel();
    });

    // ===== HELPER FUNCTIONS =====
    function isValidPhoneFormat(phone) {
        const phonePattern = /^(03|05|07|08|09|01[2|6|8|9])[0-9]{8}$/;
        return phonePattern.test(phone);
    }

    function hideSearchResults() {
        resultDiv.style.display = 'none';
        resultDiv.innerHTML = '';
    }

    function showErrorMessage(message) {
        resultDiv.innerHTML = `<div style="padding:8px; color:red; font-size:12px;">${message}</div>`;
        resultDiv.style.display = 'block';
        setTimeout(() => hideSearchResults(), 3000);
    }

    function searchCustomer(phone) {
        const contextPath = getContextPath();
        const servletUrl = `${contextPath}/CustomerSearchServlet?phone=${encodeURIComponent(phone)}`;

        resultDiv.innerHTML = `<div style="padding:8px; color:#666;">
            <i class="fas fa-spinner fa-spin"></i> Đang tìm kiếm...
        </div>`;
        resultDiv.style.display = 'block';

        $.ajax({
            url: servletUrl,
            type: 'GET',
            timeout: 10000,
            success: function (customer) {
                displaySearchResult(customer);
            },
            error: function (xhr, status, error) {
                let errorMessage = 'Lỗi kết nối';
                if (xhr.status === 404) {
                    errorMessage = 'Không tìm thấy khách hàng';
                } else if (xhr.status === 400) {
                    errorMessage = 'Số điện thoại không hợp lệ';
                }
                showErrorMessage(errorMessage);
            }
        });
    }

    function displaySearchResult(customer) {
        resultDiv.innerHTML = `
            <div class="customer-item" 
                 style="padding:12px; cursor:pointer; border-radius:4px; transition: background-color 0.2s;" 
                 data-id="${customer.customerId}" 
                 data-name="${customer.fullName}" 
                 data-phone="${customer.phone || ''}">
                <div style="font-weight:500; color:#333;">${customer.fullName}</div>
                <div style="font-size:12px; color:#666;">${customer.phone || ''}</div>
            </div>`;
        resultDiv.style.display = 'block';

        resultDiv.querySelector('.customer-item').addEventListener('click', function () {
            selectCustomer(this);
        });
    }

    function selectCustomer(element) {
        const selectedCustomer = {
            customerId: element.dataset.id,
            fullName: element.dataset.name,
            phone: element.dataset.phone
        };

        // Update UI
        customerInput.value = selectedCustomer.fullName;
        customerInput.disabled = true;
        customerInput.style.color = '#2563eb';
        customerInput.style.fontWeight = '500';
        hideSearchResults();

        // Toggle buttons
        clearBtn.style.display = 'block';
        addCustomerBtn.style.display = 'none';

        // Save to session
        saveCustomerToSession(selectedCustomer.customerId);
    }

    function clearCustomerFromServer() {
        $.ajax({
            url: `${getContextPath()}/CustomerSearchServlet`,
            type: 'POST',
            data: {action: 'clearSelectedCustomer'},
            success: function (data) {
                console.log('Customer cleared from server');
            },
            error: function (err) {
                console.error('Error clearing customer:', err);
            }
        });
    }

    function saveCustomerToSession(customerId) {
        $.ajax({
            url: `${getContextPath()}/CustomerSearchServlet`,
            type: 'POST',
            data: {
                action: 'saveSelectedCustomer',
                customerId: customerId
            },
            success: function (data) {
                if (data.success) {
                    console.log("Customer saved to session!");
                }
            },
            error: function (err) {
                console.error('Error saving customer:', err);
            }
        });
    }

    function getContextPath() {
        const path = window.location.pathname;
        return path.substring(0, path.indexOf('/', 1));
    }

    // Click outside to hide results
    document.addEventListener('click', function (event) {
        if (!customerInput.contains(event.target) && !resultDiv.contains(event.target)) {
            hideSearchResults();
        }
    });
});

//============================================
// Updated Add Customer Panel JavaScript - Fixed UI Reset Issues

function showAddCustomerPanel() {
    $('#addCustomerOverlay').fadeIn(300);
    $('#addCustomerPanel').fadeIn(300, function () {
        // Load initial form
        $.ajax({
            url: 'AddCustomerServlet',
            type: 'POST',
            data: {action: 'loadForm'},
            success: function (addCustomerHtml) {
                $('#addCustomerPanel').html(addCustomerHtml);
                bindAddCustomerEvents();
            },
            error: function () {
                console.error('Error loading add customer form');
                alert('Lỗi khi tải form. Vui lòng thử lại!');
            }
        });
    });
}

function hideAddCustomerPanel() {
    // Clear all validation states before hiding
    clearAllValidationStates();

    // Reset form if it exists
    if ($('#addCustomerForm').length) {
        $('#addCustomerForm')[0].reset();
    }

    // Hide panels with animation
    $('#addCustomerOverlay').fadeOut(300);
    $('#addCustomerPanel').fadeOut(300);
}

function bindAddCustomerEvents() {
    console.log('Binding individual field events...');

    // Unbind các events cũ
    $('#addCustomerForm input, #addCustomerForm select, #addCustomerForm textarea').off();

    // Individual field validation events
    $('#customerFullName').on('blur', function () {
        validateSingleField('fullName', $(this).val());
    });

    $('#customerPhone').on('blur', function () {
        validateSingleField('phone', $(this).val());
    });

    $('#customerEmail').on('blur', function () {
        validateSingleField('email', $(this).val());
    });

    $('#customerAddress').on('blur', function () {
        validateSingleField('address', $(this).val());
    });

    $('#customerBirthDate').on('change', function () {
        validateSingleField('birthDate', $(this).val());
    });

    $('input[name="gender"]').on('change', function () {
        validateSingleField('gender', $(this).val());
    });

    $('#customerDescription').on('blur', function () {
        validateSingleField('description', $(this).val());
    });

    // Button events
    $('.btn-cancel, .close-btn').off('click').on('click', function () {
        hideAddCustomerPanel();
    });

    $('.btn-save').off('click').on('click', function () {
        submitAddCustomer();
    });
}

function validateSingleField(fieldName, fieldValue) {
    console.log('Validating field:', fieldName, 'with value:', fieldValue);

    $.ajax({
        url: 'AddCustomerServlet',
        type: 'POST',
        data: {
            action: 'validateField',
            fieldName: fieldName,
            fieldValue: fieldValue || ''
        },
        dataType: 'json',
        success: function (response) {
            console.log('Validation response for', fieldName, ':', response);

            removeFieldError(fieldName);

            if (!response.valid) {
                showFieldError(fieldName, response.message);
            } else {
                showFieldSuccess(fieldName);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error validating field', fieldName, ':', error);
        }
    });
}

// Show error cho một field cụ thể
function showFieldError(fieldName, errorMessage) {
    var fieldElement = getFieldElement(fieldName);
    if (fieldElement) {
        // Add error class
        fieldElement.addClass('error').removeClass('success');

        // Remove existing error message
        fieldElement.next('.error-message').remove();

        // Add new error message
        fieldElement.after('<span class="error-message">' + errorMessage + '</span>');
    }
}

// Show success cho một field cụ thể
function showFieldSuccess(fieldName) {
    var fieldElement = getFieldElement(fieldName);
    if (fieldElement) {
        // Add success class
        fieldElement.addClass('success').removeClass('error');

        // Remove error message
        fieldElement.next('.error-message').remove();
    }
}

// Remove error cho một field cụ thể
function removeFieldError(fieldName) {
    var fieldElement = getFieldElement(fieldName);
    if (fieldElement) {
        fieldElement.removeClass('error success');
        fieldElement.next('.error-message').remove();
    }
}

// NEW: Clear all validation states from form
function clearAllValidationStates() {
    console.log('Clearing all validation states...');

    // Remove all error and success classes from form elements
    $('#addCustomerForm input, #addCustomerForm select, #addCustomerForm textarea, .gender-options')
            .removeClass('error success');

    // Remove all error messages
    $('#addCustomerForm .error-message').remove();

    // Special handling for gender options
    $('.gender-options').removeClass('error success');
}

// Get jQuery element cho field
function getFieldElement(fieldName) {
    switch (fieldName) {
        case 'fullName':
            return $('#customerFullName');
        case 'phone':
            return $('#customerPhone');
        case 'email':
            return $('#customerEmail');
        case 'address':
            return $('#customerAddress');
        case 'birthDate':
            return $('#customerBirthDate');
        case 'gender':
            return $('.gender-options'); // Special case for radio group
        case 'description':
            return $('#customerDescription');
        default:
            return null;
    }
}

// Submit add customer function
function submitAddCustomer() {
    console.log('Submitting customer...');

    // Disable save button to prevent multiple submissions
    $('.btn-save').prop('disabled', true).text('Đang lưu...');

    // Get form data
    var formData = {
        action: 'saveCustomer',
        fullName: $('#customerFullName').val() || '',
        phone: $('#customerPhone').val() || '',
        email: $('#customerEmail').val() || '',
        address: $('#customerAddress').val() || '',
        birthDate: $('#customerBirthDate').val() || '',
        gender: $('input[name="gender"]:checked').val() || '',
        description: $('#customerDescription').val() || ''
    };

    console.log('Form data:', formData);

    // Submit to server
    $.ajax({
        url: 'AddCustomerServlet',
        type: 'POST',
        data: formData,
        dataType: 'json',
        success: function (response) {
            console.log('Save response:', response);

            if (response.success) {
                showNotification('success', response.message);

                // Close panel after short delay
                setTimeout(function () {
                    hideAddCustomerPanel();

                    // Optionally refresh customer list or trigger callback
                    if (typeof onCustomerAdded === 'function') {
                        onCustomerAdded(response.customer);
                    }
                }, 1000);

            } else {
                // Handle validation errors
                if (response.errors) {
                    showValidationErrors(response.errors);
                }
                showNotification('error', response.message);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error saving customer:', error);
            showNotification('error', 'Lỗi kết nối. Vui lòng thử lại!');
        },
        complete: function () {
            // Re-enable save button
            $('.btn-save').prop('disabled', false).text('Lưu');
        }
    });
}

// Show validation errors on specific fields
function showValidationErrors(errors) {
    console.log('Showing validation errors:', errors);

    // Clear previous errors
    clearAllValidationStates();

    // Show errors for each field
    for (var fieldName in errors) {
        if (errors.hasOwnProperty(fieldName)) {
            showFieldError(fieldName, errors[fieldName]);
        }
    }
}

// Show notification message
function showNotification(type, message) {
    // Remove existing notifications
    $('.notification').remove();

    var notificationClass = type === 'success' ? 'notification-success' : 'notification-error';
    var icon = type === 'success' ? 'fas fa-check-circle' : 'fas fa-exclamation-circle';

    var notification = $(`
        <div class="notification ${notificationClass}">
            <i class="${icon}"></i>
            <span>${message}</span>
        </div>
    `);

    // Add to form
    $('.add-customer-content').prepend(notification);

    // Auto remove after 1s for cuccess, 3 for err
    var delay = type === 'success' ? 1000 : 3000;
    setTimeout(function () {
        notification.fadeOut(300, function () {
            $(this).remove();
        });
    }, delay);
}

// Document ready events
$(document).ready(function () {
    // Close overlay when click outside
    $(document).on('click', '#addCustomerOverlay', function (e) {
        if (e.target === this) {
            hideAddCustomerPanel();
        }
    });

    // ESC key to close panel
    $(document).on('keydown', function (e) {
        if (e.key === 'Escape' && $('#addCustomerPanel').is(':visible')) {
            hideAddCustomerPanel();
        }
    });

    console.log('Simple add customer events initialized');
});

// CSS for field validation states
var validationStyles = `
<style>
/* Field validation states */
.error {
    border-color: #ef4444 !important;
    box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1) !important;
}

.success {
    border-color: #10b981 !important;
    box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1) !important;
}

.error-message {
    color: #ef4444;
    font-size: 12px;
    margin-top: 4px;
    display: block;
    font-weight: 500;
}

.required {
    color: #ef4444;
    font-weight: bold;
}

.gender-options.error {
    border: 1px solid #ef4444;
    border-radius: 4px;
    padding: 8px;
    background-color: rgba(239, 68, 68, 0.05);
}

.gender-options.success {
    border: 1px solid #10b981;
    border-radius: 4px;
    padding: 8px;
    background-color: rgba(16, 185, 129, 0.05);
}

/* Smooth transitions for validation states */
#addCustomerForm input,
#addCustomerForm textarea,
#addCustomerForm select,
.gender-options {
    transition: border-color 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
}

.error-message {
    animation: fadeInError 0.3s ease-in-out;
}

@keyframes fadeInError {
    from {
        opacity: 0;
        transform: translateY(-5px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Notification styles */
.notification {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 12px 16px;
    border-radius: 6px;
    margin-bottom: 20px;
    font-size: 14px;
    font-weight: 500;
    animation: slideInDown 0.3s ease-out;
}

.notification-success {
    background-color: #dcfce7;
    color: #166534;
    border: 1px solid #bbf7d0;
}

.notification-error {
    background-color: #fef2f2;
    color: #dc2626;
    border: 1px solid #fecaca;
}

.notification i {
    font-size: 16px;
}

@keyframes slideInDown {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Button disabled state */
.btn-save:disabled {
    background-color: #9ca3af !important;
    cursor: not-allowed !important;
    opacity: 0.7;
}
</style>
`;

// Inject CSS if not already present
if (!$('#validation-styles').length) {
    $('head').append('<div id="validation-styles">' + validationStyles + '</div>');
}