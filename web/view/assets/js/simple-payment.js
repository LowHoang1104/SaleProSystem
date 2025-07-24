/**
 * JavaScript đơn giản để test thanh toán VNPay
 */

document.addEventListener('DOMContentLoaded', function() {
    
    // Xử lý submit form thanh toán
    const paymentForms = document.querySelectorAll('form[action*="/payment/create"]');
    
    paymentForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const button = form.querySelector('button[type="submit"]');
            
            // Disable button và show loading
            if (button) {
                button.disabled = true;
                button.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
            }
            
            // Show loading message
            showMessage('Đang tạo giao dịch thanh toán...', 'info');
            
            console.log('Creating VNPay payment...');
        });
    });
    
    // Kiểm tra URL params để hiển thị kết quả
    checkPaymentResult();
});

/**
 * Kiểm tra kết quả thanh toán từ URL
 */
function checkPaymentResult() {
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('status');
    const message = urlParams.get('message');
    
    if (status) {
        handlePaymentResult(status, message);
        
        // Clean URL
        const url = new URL(window.location);
        url.searchParams.delete('status');
        url.searchParams.delete('message');
        window.history.replaceState({}, document.title, url);
    }
}

/**
 * Xử lý hiển thị kết quả thanh toán
 */
function handlePaymentResult(status, message) {
    switch (status) {
        case 'success':
            showMessage(message || 'Thanh toán thành công!', 'success');
            // Auto reload after 3 seconds
            setTimeout(() => {
                window.location.reload();
            }, 3000);
            break;
            
        case 'failed':
            showMessage(message || 'Thanh toán thất bại!', 'error');
            enablePaymentButtons();
            break;
            
        case 'cancelled':
            showMessage(message || 'Đã hủy thanh toán!', 'warning');
            enablePaymentButtons();
            break;
    }
}

/**
 * Hiển thị thông báo
 */
function showMessage(message, type) {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.payment-message');
    existingMessages.forEach(msg => msg.remove());
    
    const alertClass = {
        'success': 'alert-success',
        'error': 'alert-danger',
        'warning': 'alert-warning',
        'info': 'alert-info'
    };
    
    const iconClass = {
        'success': 'fa-check-circle',
        'error': 'fa-exclamation-circle',
        'warning': 'fa-exclamation-triangle',
        'info': 'fa-info-circle'
    };
    
    const messageHtml = `
        <div class="alert ${alertClass[type]} payment-message" style="margin: 20px 0;">
            <i class="fas ${iconClass[type]}"></i>
            <span>${message}</span>
            <button type="button" class="close" onclick="this.parentElement.remove()">
                <span>&times;</span>
            </button>
        </div>
    `;
    
    // Insert at top of content
    const contentWrapper = document.querySelector('.content-wrapper') || document.body;
    contentWrapper.insertAdjacentHTML('afterbegin', messageHtml);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        const msg = document.querySelector('.payment-message');
        if (msg) msg.remove();
    }, 5000);
}

/**
 * Enable lại các nút thanh toán
 */
function enablePaymentButtons() {
    const buttons = document.querySelectorAll('button[type="submit"]');
    buttons.forEach(button => {
        button.disabled = false;
        button.innerHTML = 'Mua ngay';
    });
}

/**
 * Test payment với thông tin mẫu
 */
function testPayment() {
    const testForm = document.createElement('form');
    testForm.method = 'POST';
    testForm.action = '/your-project/payment/create';
    
    const packageInput = document.createElement('input');
    packageInput.type = 'hidden';
    packageInput.name = 'packageId';
    packageInput.value = '1';
    
    const shopInput = document.createElement('input');
    shopInput.type = 'hidden';
    shopInput.name = 'shopId';
    shopInput.value = '123';
    
    testForm.appendChild(packageInput);
    testForm.appendChild(shopInput);
    document.body.appendChild(testForm);
    
    testForm.submit();
}

// CSS styles for messages
const style = document.createElement('style');
style.textContent = `
    .payment-message {
        position: relative;
        padding: 12px 16px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        display: flex;
        align-items: center;
        gap: 10px;
    }
    
    .alert-success {
        color: #155724;
        background-color: #d4edda;
        border-color: #c3e6cb;
    }
    
    .alert-danger {
        color: #721c24;
        background-color: #f8d7da;
        border-color: #f5c6cb;
    }
    
    .alert-warning {
        color: #856404;
        background-color: #fff3cd;
        border-color: #ffeaa7;
    }
    
    .alert-info {
        color: #0c5460;
        background-color: #d1ecf1;
        border-color: #bee5eb;
    }
    
    .payment-message .close {
        position: absolute;
        top: 0;
        right: 0;
        padding: 12px 16px;
        color: inherit;
        background: none;
        border: none;
        font-size: 18px;
        cursor: pointer;
    }
    
    .payment-message i {
        flex-shrink: 0;
    }
    
    .fa-spinner {
        animation: spin 1s linear infinite;
    }
    
    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }
`;
document.head.appendChild(style);