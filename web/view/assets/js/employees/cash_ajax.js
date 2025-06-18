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
        }
    });
}

function hideCashPanel() {
    console.log("CashServlet response received");

    document.getElementById('cashOverlay').style.display = 'none';
    document.getElementById('cashPanel').style.display = 'none';
}


function onFundSelectionChange(fundId) {
    const sessionType = document.querySelector('input[name="sessionType"]:checked').value;
    $.ajax({
        url: 'CashServlet',
        type: 'Post',
        data: {
            action: 'changeFund',
            sessionType: sessionType,
            fundId: fundId
        },
        success: function (html) {
            console.log("CashServlet response received    " + fundId);
            $('#cashSection').html(html);
        }
    });

}

function clearTempData(fundId) {
    $.ajax({
        url: 'CashServlet',
        type: 'Post',
        data: {
            action: 'clearTempData',
        },
        success: function (html) {
            console.log("CashServlet response received    " + fundId);
            $('#cashSection').html(html);
        }
    });
}


function calculateAmount(inputElement) {
    const quantity = parseInt(inputElement.value) || 0;
    const denominationId = parseInt(inputElement.getAttribute('data-denomination-id'));
    const denominationValue = parseInt(inputElement.getAttribute('data-value'));
    const fundId = document.getElementById('fundSelect').value;
    const sessionType = document.querySelector('input[name="sessionType"]:checked').value;

    $.ajax({
        url: 'CashServlet',
        type: 'POST',
        data: {
            action: 'calculateAmount',
            fundId: fundId,
            quantity: quantity,
            sessionType: sessionType,
            denominationId: denominationId,
            denominationValue: denominationValue
        },
        success: function (html) {
            console.log("CashServlet response received for fundId: " + fundId);
            $('#cashSection').html(html);
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi tính toán:', error);
        }
    });
}

function saveCashCount() {
    console.log("Starting saveCashCount...");

    const fundId = $('#fundSelect').val();
    const sessionType = $('input[name="sessionType"]:checked').val();
    const notes = $('#sessionNotes').val();

    console.log("Data to send:", {fundId, sessionType, notes});

    if (!fundId) {
        console.error("No fundId selected!");
        return;
    }

    if (!sessionType) {
        console.error("No sessionType selected!");
        return;
    }

    $.ajax({
        url: 'CashServlet',
        type: 'POST',
        data: {
            action: 'saveCashCount',
            sessionType: sessionType,
            fundId: fundId,
            notes: notes || ''
        },
        success: function (html) {
            console.log("Save successful - reloading panel");
            $('#cashSection').html(html);
        },
        error: function (xhr, status, error) {
            console.error('Ajax error:', error);
        }
    });
}

