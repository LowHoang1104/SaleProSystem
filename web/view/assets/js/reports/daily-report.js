/**
 * Daily Report JavaScript - SalePro System
 * Handles charts initialization and user interactions
 */

$(document).ready(function() {
    initializeCharts();
    initializeEventHandlers();
    feather.replace();
});

/**
 * Initialize all charts
 */
function initializeCharts() {
    initHourlyRevenueChart();
    initPaymentMethodChart();
}

/**
 * Initialize hourly revenue chart
 */
function initHourlyRevenueChart() {
    const data = window.hourlyRevenueData || {};
    
    // Check if we have real data
    const hasData = Object.keys(data).length > 0 && Object.values(data).some(val => val > 0);
    
    let labels, values;
    if (hasData) {
        labels = Object.keys(data);
        values = Object.values(data);
    } else {
        // Show empty chart with hours but no data
        labels = ['8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00'];
        values = new Array(labels.length).fill(0); // All zeros
    }

    const options = {
        series: [{
            name: 'Doanh thu',
            data: values
        }],
        chart: {
            type: 'area',
            height: 350,
            toolbar: { show: false },
            fontFamily: 'inherit'
        },
        colors: ['#ff9f43'],
        stroke: {
            curve: 'smooth',
            width: 3
        },
        fill: {
            type: 'gradient',
            gradient: {
                shadeIntensity: 1,
                type: "vertical",
                colorStops: [
                    { offset: 0, color: "#ff9f43", opacity: hasData ? 0.8 : 0.2 },
                    { offset: 100, color: "#ff9f43", opacity: 0.1 }
                ]
            }
        },
        dataLabels: { enabled: false },
        xaxis: {
            categories: labels,
            title: { 
                text: 'Giờ trong ngày',
                style: { 
                    fontSize: '12px',
                    fontWeight: 600,
                    color: '#6c757d'
                }
            },
            labels: {
                style: {
                    fontSize: '11px',
                    colors: '#6c757d'
                }
            }
        },
        yaxis: {
            title: { 
                text: 'Doanh thu (VNĐ)',
                style: { 
                    fontSize: '12px',
                    fontWeight: 600,
                    color: '#6c757d'
                }
            },
            labels: {
                style: {
                    fontSize: '11px',
                    colors: '#6c757d'
                },
                formatter: function(value) {
                    return new Intl.NumberFormat('vi-VN').format(value);
                }
            }
        },
        grid: {
            borderColor: '#f1f1f1',
            strokeDashArray: 3
        },
        tooltip: {
            y: {
                formatter: function(value) {
                    return new Intl.NumberFormat('vi-VN').format(value) + ' VNĐ';
                }
            },
            theme: 'light'
        },
        // Add "No data" annotation if empty
        annotations: !hasData ? {
            position: 'front',
            texts: [{
                x: '50%',
                y: '50%',
                text: 'Không có dữ liệu cho ngày này',
                textAnchor: 'middle',
                style: {
                    fontSize: '14px',
                    fontWeight: 600,
                    color: '#999'
                }
            }]
        } : {}
    };

    if (document.querySelector("#hourlyRevenueChart")) {
        const chart = new ApexCharts(document.querySelector("#hourlyRevenueChart"), options);
        chart.render();
    }
}

/**
 * Initialize payment method chart
 */
function initPaymentMethodChart() {
    const data = window.paymentMethodData || {};
    
    // Check if we have real data
    const hasData = Object.keys(data).length > 0 && Object.values(data).some(val => val > 0);
    
    let labels, values;
    if (hasData) {
        labels = Object.keys(data);
        values = Object.values(data);
    } else {
        // Show "No data" state
        labels = ['Không có dữ liệu'];
        values = [1]; // Small value to show the chart
    }

    const options = {
        series: values,
        chart: {
            type: 'donut',
            height: 300,
            fontFamily: 'inherit'
        },
        labels: labels,
        colors: hasData ? ['#28c76f', '#ff9f43', '#7367f0'] : ['#e9ecef'],
        plotOptions: {
            pie: {
                donut: {
                    size: '70%',
                    labels: {
                        show: true,
                        name: { 
                            show: true, 
                            fontSize: '14px', 
                            fontWeight: 600,
                            color: '#2c2c2c'
                        },
                        value: {
                            show: hasData,
                            fontSize: '12px',
                            color: '#6c757d',
                            formatter: function(val) {
                                return hasData ? new Intl.NumberFormat('vi-VN').format(val) + ' VNĐ' : '';
                            }
                        },
                        total: {
                            show: true,
                            label: hasData ? 'Tổng' : 'Không có dữ liệu',
                            fontSize: '14px',
                            fontWeight: 600,
                            color: hasData ? '#2c2c2c' : '#999',
                            formatter: function (w) {
                                if (!hasData) return 'Chọn ngày khác';
                                const total = w.globals.seriesTotals.reduce((a, b) => a + b, 0);
                                return new Intl.NumberFormat('vi-VN').format(total) + ' VNĐ';
                            }
                        }
                    }
                }
            }
        },
        dataLabels: {
            enabled: hasData,
            style: {
                fontSize: '11px',
                fontWeight: 600,
                colors: ['#fff']
            },
            formatter: function(val, opts) {
                return hasData ? Math.round(val) + '%' : '';
            },
            dropShadow: {
                enabled: false
            }
        },
        legend: { 
            show: hasData,
            position: 'bottom',
            fontSize: '12px',
            fontWeight: 500,
            labels: {
                colors: '#6c757d'
            },
            markers: {
                width: 8,
                height: 8
            }
        },
        tooltip: {
            enabled: hasData,
            y: {
                formatter: function(val) {
                    return hasData ? new Intl.NumberFormat('vi-VN').format(val) + ' VNĐ' : '';
                }
            },
            theme: 'light'
        }
    };

    if (document.querySelector("#paymentMethodChart")) {
        const chart = new ApexCharts(document.querySelector("#paymentMethodChart"), options);
        chart.render();
    }
}

/**
 * Initialize event handlers
 */
function initializeEventHandlers() {
    // Generate Report Button
    $('#generateReportBtn').click(function() {
        const $btn = $(this);
        const originalText = $btn.html();
        const reportDate = $('#reportDate').val();
        const storeId = $('#storeId').val();
        
        if (!reportDate) {
            showAlert('warning', 'Vui lòng chọn ngày báo cáo');
            return;
        }

        // Show loading
        $btn.html('<span class="loading-spinner me-2"></span>Đang tạo báo cáo...').prop('disabled', true);

        setTimeout(() => {
            window.location.href = window.contextPath + '/DailyReportServlet?reportDate=' + reportDate + '&storeId=' + storeId;
        }, 500);
    });

    // Export buttons
    $('#exportExcelBtn').click(function() {
        exportReport('excel');
    });

    $('#exportPDFBtn').click(function() {
        exportReport('pdf');
    });

    // Print button
    $('#printBtn').click(function() {
        window.print();
    });

    // Quick date buttons
    $('.quick-date-btn').click(function() {
        const days = $(this).data('days');
        const date = new Date();
        date.setDate(date.getDate() - days);
        const formattedDate = date.toISOString().split('T')[0];
        $('#reportDate').val(formattedDate);
    });

    // Auto-submit form on store change
    $('#storeId').change(function() {
        const reportDate = $('#reportDate').val();
        const storeId = $(this).val();
        
        if (reportDate) {
            window.location.href = window.contextPath + '/DailyReportServlet?reportDate=' + reportDate + '&storeId=' + storeId;
        }
    });
}

/**
 * Export report function
 */
function exportReport(type) {
    const reportDate = $('#reportDate').val() || new Date().toISOString().split('T')[0];
    const storeId = $('#storeId').val() || '1';
    
    const url = window.contextPath + '/DailyReportServlet?action=export' + 
        (type === 'excel' ? 'Excel' : 'PDF') + 
        '&reportDate=' + reportDate + '&storeId=' + storeId;
    
    window.open(url, '_blank');
}

/**
 * Show alert message
 */
function showAlert(type, message) {
    const alertClass = type === 'success' ? 'alert-success' : 
                      type === 'warning' ? 'alert-warning' : 'alert-danger';
    
    const alert = `
        <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'warning' ? 'exclamation-triangle' : 'times-circle'} me-2"></i>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
    
    $('.content').prepend(alert);
    setTimeout(() => $('.alert').fadeOut(), 3000);
}

/**
 * Refresh chart data (optional function for future use)
 */
function refreshChartData() {
    const reportDate = $('#reportDate').val();
    const storeId = $('#storeId').val();
    
    if (reportDate && storeId) {
        $.ajax({
            url: window.contextPath + '/DailyReportServlet',
            method: 'GET',
            data: {
                action: 'generateReport',
                reportDate: reportDate,
                storeId: storeId
            },
            success: function(response) {
                if (response.success) {
                    // Update chart data and reinitialize
                    window.hourlyRevenueData = response.data.hourlyRevenue || {};
                    window.paymentMethodData = response.data.paymentMethodBreakdown || {};
                    initializeCharts();
                    showAlert('success', 'Dữ liệu đã được cập nhật');
                } else {
                    showAlert('danger', response.message || 'Có lỗi xảy ra khi cập nhật dữ liệu');
                }
            },
            error: function() {
                showAlert('danger', 'Không thể kết nối đến server');
            }
        });
    }
}

/**
 * Auto-refresh every hour during business hours
 */
setInterval(function() {
    const now = new Date();
    const hour = now.getHours();
    if (hour >= 8 && hour <= 22 && document.visibilityState === 'visible') {
        refreshChartData();
    }
}, 3600000); // 1 hour