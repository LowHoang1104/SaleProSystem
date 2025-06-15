function showCashPanel() {
    console.log("CashServlet response received");
    document.getElementById('cashOverlay').style.display = 'block';
    document.getElementById('cashPanel').style.display = 'flex';
    $.ajax({
        url: 'CashServlet',
        type: 'GET',
        success: function (cashHtml) {
            $('#cashSection').html(cashHtml);

        },
        error: function () {
            console.error('Lỗi khi tải thông tin thanh toán');
            console.error('Status:', status);
            console.error('Error:', error);
            console.error('Response:', xhr.responseText);

        }
    });
}

function hideCashPanel() {
    console.log("CashServlet response received");

    document.getElementById('cashOverlay').style.display = 'none';
    document.getElementById('cashPanel').style.display = 'none';
}


function onFundSelectionChange(fundId) {
    $.ajax({
        url: 'CashServlet',
        type: 'Post',
        data: {
            action: 'changeFund',
            fundId: fundId
        },
        success: function (html) {
            $('#cashSection').html(html);
        }
    });

}

