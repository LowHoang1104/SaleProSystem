// Sales Report JavaScript - Updated Version

$(document).ready(function() {
    initializeCharts();
    feather.replace();
    console.log('Sales Report initialized for store:', window.selectedStoreId);
});

function initializeCharts() {
    initDailyRevenueChart();
    initPaymentMethodChart();
    initHourlyChart();
}

// Biểu đồ doanh thu 7 ngày gần nhất
function initDailyRevenueChart() {
    const data = window.dailyRevenueData || {};
    const labels = data.labels || ['07/17', '07/18', '07/19', '07/20', '07/21', '07/22', '07/23'];
    const values = data.values || [1500000, 2300000, 1800000, 2800000, 2100000, 3200000, 2900000];

    const options = {
        series: [{
            name: 'Doanh thu',
            data: values
        }],
        chart: {
            type: 'area',
            height: 350,
            toolbar: { show: false },
            animations: { enabled: true, easing: 'easeinout', speed: 800 }
        },
        colors: ['#007bff'],
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
                    {
                        offset: 0,
                        color: "#007bff",
                        opacity: 0.8
                    },
                    {
                        offset: 100,
                        color: "#007bff",
                        opacity: 0.1
                    }
                ]
            }
        },
        dataLabels: {
            enabled: false
        },
        markers: {
            size: 4,
            strokeWidth: 2,
            strokeColors: '#fff',
            hover: {
                size: 6
            }
        },
        xaxis: {
            categories: labels,
            title: {
                text: 'Ngày'
            },
            labels: {
                style: {
                    colors: '#6c757d',
                    fontSize: '12px'
                }
            }
        },
        yaxis: {
            title: {
                text: 'Doanh thu (VNĐ)'
            },
            labels: {
                style: {
                    colors: '#6c757d',
                    fontSize: '12px'
                },
                formatter: function(value) {
                    return formatCurrency(value, true);
                }
            }
        },
        tooltip: {
            theme: 'dark',
            y: {
                formatter: function(value) {
                    return formatCurrency(value);
                }
            }
        },
        grid: {
            borderColor: '#e7e7e7',
            strokeDashArray: 3
        }
    };

    const chart = new ApexCharts(document.querySelector("#daily-revenue-chart"), options);
    chart.render();
}

// Biểu đồ phương thức thanh toán - Tổng từ tất cả hóa đơn
function initPaymentMethodChart() {
    const data = window.paymentMethodData || {};
    const labels = data.labels || ['Tiền mặt', 'Chuyển khoản', 'Thẻ tín dụng'];
    const values = data.values || [5200000, 3800000, 1500000];

    const options = {
        series: values,
        chart: {
            type: 'donut',
            height: 300,
            animations: { enabled: true, easing: 'easeinout', speed: 800 }
        },
        labels: labels,
        colors: ['#28a745', '#007bff', '#ffc107'],
        stroke: {
            width: 0
        },
        plotOptions: {
            pie: {
                donut: {
                    size: '70%',
                    labels: {
                        show: true,
                        name: {
                            show: true,
                            fontSize: '16px',
                            fontWeight: 600,
                            color: '#373d3f'
                        },
                        value: {
                            show: true,
                            fontSize: '14px',
                            fontWeight: 400,
                            color: '#666',
                            formatter: function(val) {
                                return formatCurrency(val, true);
                            }
                        },
                        total: {
                            show: true,
                            showAlways: true,
                            label: 'Tổng',
                            fontSize: '16px',
                            fontWeight: 600,
                            color: '#373d3f',
                            formatter: function (w) {
                                const total = w.globals.seriesTotals.reduce((a, b) => a + b, 0);
                                return formatCurrency(total, true);
                            }
                        }
                    }
                }
            }
        },
        dataLabels: {
            enabled: true,
            formatter: function(val, opts) {
                const value = opts.w.config.series[opts.seriesIndex];
                return formatCurrency(value, true);
            },
            style: {
                fontSize: '12px',
                fontWeight: 'bold',
                colors: ['#fff']
            }
        },
        tooltip: {
            theme: 'dark',
            y: {
                formatter: function(value) {
                    return formatCurrency(value);
                }
            }
        },
        legend: {
            position: 'bottom',
            fontSize: '12px',
            fontWeight: 500,
            markers: {
                width: 12,
                height: 12
            }
        }
    };

    const chart = new ApexCharts(document.querySelector("#payment-method-chart"), options);
    chart.render();
}

// Biểu đồ doanh thu theo giờ hôm nay
function initHourlyChart() {
    const data = window.hourlyData || {};
    const labels = data.labels || ['8h', '9h', '10h', '11h', '12h', '13h', '14h', '15h', '16h', '17h', '18h', '19h', '20h', '21h', '22h'];
    const values = data.values || [120000, 340000, 450000, 680000, 890000, 1200000, 980000, 760000, 890000, 1100000, 950000, 640000, 380000, 220000, 150000];

    const options = {
        series: [{
            name: 'Doanh thu hôm nay',
            data: values
        }],
        chart: {
            type: 'bar',
            height: 300,
            toolbar: { show: false },
            animations: { enabled: true, easing: 'easeinout', speed: 800 }
        },
        colors: ['#28a745'],
        plotOptions: {
            bar: {
                borderRadius: 4,
                horizontal: false,
                columnWidth: '70%',
                dataLabels: {
                    position: 'top'
                }
            }
        },
        dataLabels: {
            enabled: true,
            formatter: function(val) {
                return val > 0 ? formatCurrency(val, true) : '';
            },
            offsetY: -20,
            style: {
                fontSize: '10px',
                colors: ["#304758"]
            }
        },
        xaxis: {
            categories: labels,
            title: {
                text: 'Giờ trong ngày'
            },
            labels: {
                style: {
                    colors: '#6c757d',
                    fontSize: '12px'
                }
            }
        },
        yaxis: {
            title: {
                text: 'Doanh thu (VNĐ)'
            },
            labels: {
                style: {
                    colors: '#6c757d',
                    fontSize: '12px'
                },
                formatter: function(value) {
                    return formatCurrency(value, true);
                }
            }
        },
        tooltip: {
            theme: 'dark',
            y: {
                formatter: function(value) {
                    return value > 0 ? formatCurrency(value) : 'Chưa có doanh thu';
                }
            }
        },
        grid: {
            borderColor: '#e7e7e7',
            strokeDashArray: 3
        }
    };

    const chart = new ApexCharts(document.querySelector("#hourly-chart"), options);
    chart.render();
}

// Utility Functions
function formatCurrency(amount, short = false) {
    if (!amount || amount === 0) return '0 VNĐ';
    
    if (short && amount >= 1000000) {
        return (amount / 1000000).toFixed(1) + 'M VNĐ';
    } else if (short && amount >= 1000) {
        return (amount / 1000).toFixed(1) + 'K VNĐ';
    }
    
    return new Intl.NumberFormat('vi-VN').format(amount) + ' VNĐ';
}

// Export Functions
function exportExcel() {
    const selectedStoreId = window.selectedStoreId || '1';
    const url = window.contextPath + '/SalesReportServlet?action=exportExcel&storeId=' + selectedStoreId;
    
    showToast('Đang xuất báo cáo chi nhánh ' + selectedStoreId + '...', 'info');
    window.open(url, '_blank');
}

function showToast(message, type = 'info') {
    const alertClass = {
        success: 'alert-success',
        error: 'alert-danger', 
        info: 'alert-info',
        warning: 'alert-warning'
    }[type] || 'alert-info';
    
    const icon = {
        success: '<i class="fas fa-check-circle me-2"></i>',
        error: '<i class="fas fa-exclamation-triangle me-2"></i>',
        info: '<i class="fas fa-info-circle me-2"></i>',
        warning: '<i class="fas fa-exclamation-triangle me-2"></i>'
    }[type] || '<i class="fas fa-info-circle me-2"></i>';
    
    const toastId = 'toast-' + Date.now();
    const toast = $(`
        <div id="${toastId}" class="alert ${alertClass} alert-dismissible fade show position-fixed" 
             style="top: 20px; right: 20px; z-index: 9999; min-width: 300px; box-shadow: 0 4px 12px rgba(0,0,0,0.15);">
            ${icon}${message}
            <button type="button" class="close" data-dismiss="alert">
                <span>&times;</span>
            </button>
        </div>
    `);
    
    $('body').append(toast);
    setTimeout(() => $(`#${toastId}`).fadeOut(400, () => $(`#${toastId}`).remove()), 3000);
}

// Store change handler
$(document).ready(function() {
    $('#storeId').on('change', function() {
        showToast('Đang tải dữ liệu chi nhánh...', 'info');
    });
});

// Auto refresh every 10 minutes for current day data
setInterval(function() {
    if (document.visibilityState === 'visible') {
        const currentHour = new Date().getHours();
        // Only refresh during business hours (8-22h)
        if (currentHour >= 8 && currentHour <= 22) {
            location.reload();
        }
    }
}, 600000); // 10 minutes

// Error handling
window.addEventListener('error', function(e) {
    console.error('JavaScript Error:', e.error);
    showToast('Đã xảy ra lỗi. Vui lòng làm mới trang.', 'error');
});