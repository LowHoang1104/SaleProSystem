const productInput = document.getElementById('productInput');
const productSearchBtn = document.getElementById('productSearchBtn');

function filterProducts() {
  const searchTerm = productInput.value.trim().toLowerCase();
  const productCards = document.querySelectorAll('.product-card');

  productCards.forEach(card => {
    const nameElem = card.querySelector('.product-name');
    if (!nameElem) {
      card.style.display = 'none'; // ẩn nếu không có tên
      return;
    }
    const productName = nameElem.textContent.toLowerCase();
    if (productName.includes(searchTerm)) {
      card.style.display = ''; // hiện
    } else {
      card.style.display = 'none'; // ẩn
    }
  });
}

// Tìm khi nhấn nút
productSearchBtn.addEventListener('click', filterProducts);

// Tìm realtime khi gõ (nếu muốn)
productInput.addEventListener('input', filterProducts);

/////////
function toggleCartMenu(button) {
    const menu = button.nextElementSibling;
    const isShown = menu.style.display === 'block';

    // Ẩn tất cả menu khác
    document.querySelectorAll('.cart-menu').forEach(m => m.style.display = 'none');

    // Toggle menu hiện tại
    menu.style.display = isShown ? 'none' : 'block';
}


// Hiển thị panel chi tiết sản phẩm
function showDetailPanel(id) {
    document.getElementById('detailOverlay').style.display = 'block';
    document.getElementById('detailPanel').style.display = 'flex';

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'CashierServlet';

    const action = document.createElement('input');
    action.type = 'hidden';
    action.name = 'action';
    action.value = 'detailItem';

    const detailProductId = document.createElement('input');
    action.type = 'hidden';
    action.name = 'detailProductId';
    action.value = id;

    form.appendChild(action);
    form.appendChild(productId);

    document.body.appendChild(form);
    form.submit();
}

function hideDetailPanel() {
    document.getElementById('detailOverlay').style.display = 'none';
    document.getElementById('detailPanel').style.display = 'none';
}


function showPaymentPanel() {
    
    document.getElementById('paymentOverlay').style.display = 'block';
    document.getElementById('paymentPanel').style.display = 'flex';
  
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

// Hàm xử lý nút Xong (ví dụ: thêm sản phẩm vào giỏ theo số lượng đã chọn)
function confirmDetail() {
    const quantity = parseInt(document.getElementById('detailQuantity').value) || 1;
    alert(`Bạn đã chọn ${quantity} sản phẩm.`);
    hideDetailPanel();
}


// Add to cart function
function addToCart(code, productName, price) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'CashierServlet';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'addToCart';

    const codeInput = document.createElement('input');
    codeInput.type = 'hidden';
    codeInput.name = 'productCode';
    codeInput.value = code;

    const nameInput = document.createElement('input');
    nameInput.type = 'hidden';
    nameInput.name = 'productName';
    nameInput.value = productName;

    const priceInput = document.createElement('input');
    priceInput.type = 'hidden';
    priceInput.name = 'price';
    priceInput.value = price;

    form.appendChild(actionInput);
    form.appendChild(codeInput);
    form.appendChild(nameInput);
    form.appendChild(priceInput);

    document.body.appendChild(form);
    form.submit();
}

// Remove from cart function
function removeFromCart(code) {

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'CashierServlet';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'removeFromCart';

    const codeInput = document.createElement('input');
    codeInput.type = 'hidden';
    codeInput.name = 'productCode';
    codeInput.value = code;

    form.appendChild(actionInput);
    form.appendChild(codeInput);

    document.body.appendChild(form);
    form.submit();

}

function changeQuantity(inputElem, productId) {
    let newQuantity = parseInt(inputElem.value);

    if (isNaN(newQuantity) || newQuantity < 1) {
        newQuantity = 1;
        inputElem.value = newQuantity;
    }
    updateQuantity(productId, newQuantity);
}

// Update quantity function
function updateQuantity(code, newQuantity) {
    if (newQuantity < 1)
        return;

    const form = document.createElement('form');
    form.method = 'POST';
    form.action = 'CashierServlet';

    const actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'updateQuantity';

    const codeInput = document.createElement('input');
    codeInput.type = 'hidden';
    codeInput.name = 'productCode';
    codeInput.value = code;

    const quantityInput = document.createElement('input');
    quantityInput.type = 'hidden';
    quantityInput.name = 'quantity';
    quantityInput.value = newQuantity;

    form.appendChild(actionInput);
    form.appendChild(codeInput);
    form.appendChild(quantityInput);

    document.body.appendChild(form);
    form.submit();
}

// Go to page function
function goToPage(page) {
    window.location.href = 'CashierServlet?page=' + page;
}

// Update payment details dynamically
function updatePayment() {
    const totalAmount = window.appData.totalAmount || 0;

    // Lấy giá trị giảm giá (%) từ input
    let discountPercent = parseFloat(document.getElementById('discountInput').value);
    if (isNaN(discountPercent) || discountPercent < 0)
        discountPercent = 0;
    if (discountPercent > 100)
        discountPercent = 100; // không quá 100%

    // Tính số tiền được giảm
    const discountAmount = totalAmount * discountPercent / 100;

    // Tính số tiền khách cần trả
    let payable = totalAmount - discountAmount;
    if (payable < 0)
        payable = 0;

    // Cập nhật hiển thị "Khách cần trả"
    const payableAmountSpan = document.getElementById('payableAmount');
    payableAmountSpan.textContent = new Intl.NumberFormat('vi-VN').format(payable) + ' đ';

    // Cập nhật input số tiền khách thanh toán (nếu thấp hơn số phải trả thì set lại)
    const paidAmountInput = document.getElementById('paidAmount');
    if (parseFloat(paidAmountInput.value) < payable) {
        paidAmountInput.value = payable;
    }
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

// Add bank account function (placeholder)
function addBankAccount() {
    alert('Chức năng thêm tài khoản ngân hàng chưa được cài đặt.');
}

// Checkout function
function checkout() {
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

    //discount
    const discountInput = document.getElementById('discountInput');
    const discount = document.createElement('input');
    discount.type = 'hidden';
    discount.name = 'staffId';
    discount.value = discountInput.value;
    form.appendChild(discount);

// paidAmount
    const paidAmountInput = document.getElementById('paidAmount');
    const paidAmount = document.createElement('input');
    paidAmount.type = 'hidden';
    paidAmount.name = 'paidAmount';
    paidAmount.value = paidAmountInput.value;
    form.appendChild(paidAmount);

    // paymentMethod 
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
    if (paymentMethod) {
        const paymentMethodInput = document.createElement('input');
        paymentMethodInput.type = 'hidden';
        paymentMethodInput.name = 'paymentMethod';
        paymentMethodInput.value = paymentMethod.value;
        form.appendChild(paymentMethodInput);
    }

    // totalAmount 
    const totalAmountValue = window.appData.totalAmount || 0;
    const totalAmountInput = document.createElement('input');
    totalAmountInput.type = 'hidden';
    totalAmountInput.name = 'totalAmount';
    totalAmountInput.value = totalAmountValue;
    form.appendChild(totalAmountInput);

    document.body.appendChild(form);
    form.submit();
}

// Search products
document.getElementById('productSearch').addEventListener('input', function (e) {
    const searchTerm = e.target.value.toLowerCase();
    const productCards = document.querySelectorAll('.product-card');

    productCards.forEach(card => {
        const productName = card.querySelector('.product-name').textContent.toLowerCase();
        if (productName.includes(searchTerm)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    }
    );
});

// Keyboard shortcuts
document.addEventListener('keydown', function (e) {
    if (e.key === 'F3') {
        e.preventDefault();
        document.getElementById('productSearch').focus();
    }
    if (e.key === 'F4') {
        e.preventDefault();
        document.querySelector('.customer-search input').focus();
    }
    if (e.key === 'Enter' && e.ctrlKey) {
        e.preventDefault();
        showPaymentPanel();
    }
});

// Auto-refresh every 30 seconds
setInterval(function () {
    console.log('Auto-sync check...');
}, 30000);



document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('productSearch').focus();
    console.log('Trang bán hàng đã sẵn sàng');
});


function updatePayment() {
    const totalAmount = window.appData.totalAmount || 0;

    // Lấy giá trị giảm giá (%) từ input
    let discountPercent = parseFloat(document.getElementById('discountInput').value);
    if (isNaN(discountPercent) || discountPercent < 0)
        discountPercent = 0;
    if (discountPercent > 100)
        discountPercent = 100; // không quá 100%

    // Tính số tiền được giảm
    const discountAmount = totalAmount * discountPercent / 100;

    // Tính số tiền khách cần trả
    let payable = totalAmount - discountAmount;
    if (payable < 0)
        payable = 0;

    // Cập nhật hiển thị "Khách cần trả"
    const payableAmountSpan = document.getElementById('payableAmount');
    payableAmountSpan.textContent = new Intl.NumberFormat('vi-VN').format(payable) + ' đ';

    // Cập nhật input số tiền khách thanh toán (nếu thấp hơn số phải trả thì set lại)
    const paidAmountInput = document.getElementById('paidAmount');
    if (parseFloat(paidAmountInput.value) < payable) {
        paidAmountInput.value = payable;
    }
}



