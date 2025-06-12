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


function showDetailPanel(productVariantId) {

    $.ajax({
        url: 'DetailServlet',
        type: 'GET',
        data: {
            action: 'showDetail',
            productVariantId: productVariantId
        },
        success: function (html) {
            $('#detailSection').html(html);
        }
    });
    document.getElementById('detailOverlay').style.display = 'block';
    document.getElementById('detailPanel').style.display = 'flex';
}

function hideDetailPanel() {
    document.getElementById('detailOverlay').style.display = 'none';
    document.getElementById('detailPanel').style.display = 'none';
}
