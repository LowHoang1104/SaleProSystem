function addToCart(productCode, productName, productPrice) {
    console.log("addToCart được gọi với:", productCode, productName, productPrice);
    $.ajax({
        url: 'CartServlet',
        type: 'POST',
        data: {
            action: 'addToCart',
            productCode: productCode,
            productName: productName,
            price: productPrice
        },

        success: function (html) {
            $('#cartSection').html(html);
        },
        error: function () {
            alert('Lỗi khi thêm sản phẩm');
        }
    });
}

function removeFromCart(productCode) {
    $.ajax({
        url: 'CartServlet',
        type: 'POST',
        data: {
            action: 'removeFromCart',
            productCode: productCode

        },
        success: function (html) {
            $('#cartSection').html(html);
        }
    });
}

function updateQuantity(productCode, quantity) {
    if (quantity < 1)
        return;
    $.ajax({
        url: 'CartServlet',
        type: 'POST',
        data: {
            action: 'updateQuantity',
            productCode: productCode,
            quantity: quantity
        },
        success: function (html) {
            $('#cartSection').html(html);
        }
    });
}

function changeQuantity(inputElem, productCode) {
    let newQuantity = parseInt(inputElem.value);

    if (isNaN(newQuantity) || newQuantity < 1) {
        newQuantity = 1;
        inputElem.value = 1;
    }
    updateQuantity(productCode, newQuantity);
}

function updateVariant(productCode, variantType, selectValue) {
    console.log(productCode, variantType, selectValue)
    $.ajax({
        url: 'CartServlet',
        type: 'Post',
        data: {
            action: 'updateVariant',
            productCode: productCode,
            variantType: variantType,
            selectValue: selectValue
        },
        success: function (html) {
            $('#cartSection').html(html);
        }
    });

}


//==============================Detail Product
function showDetail(variantId, code) {
    console.log(variantId, code)

    if (variantId == 0) {
        $.ajax({
            url: 'CartServlet',
            type: 'POST',
            data: {action: 'updateStatus',
                productCode: code,
                status: 'Need_Size_And_Color'},
            success: function (cartHtml) {
                $('#cartSection').html(cartHtml);
            },
            error: function () {
                alert('Lỗi khi tải giỏ hàng');
            }
        });
        return;
    }
    $.ajax({
        url: 'DetailServlet',
        type: 'GET',
        data: {
            action: 'showDetail',
            productVariantId: variantId,
        },
        success: function (html) {
            $('#detailSection').html(html);
        }
    });
    document.getElementById('detailOverlay').style.display = 'block';
    document.getElementById('detailPanel').style.display = 'flex';

}

function hideDetailPanel() {
    $('#detailPanel').fadeOut(300);
    $('#detailOverlay').fadeOut(300);
}

function switchTab(tabName) {
    const tabButtons = document.querySelectorAll('#detailSection .tab-btn');
    tabButtons.forEach(button => {
        button.classList.remove('active');
    });
    
    const activeButton = document.querySelector(`#detailSection .tab-btn[onclick*="${tabName}"]`);
    if (activeButton) {
        activeButton.classList.add('active');
    }
    
    const tabContents = document.querySelectorAll('#detailSection .tab-content');
    tabContents.forEach(content => {
        content.classList.remove('active');
        content.style.display = 'none';
    });
    
    let targetTab;
    if (tabName === 'general') {
        targetTab = document.querySelector('#detailSection #generalTab');
    } else if (tabName === 'specs') {
        targetTab = document.querySelector('#detailSection #specsTab');
    }
    
    if (targetTab) {
        targetTab.classList.add('active');
        targetTab.style.display = 'block';
    }
}
