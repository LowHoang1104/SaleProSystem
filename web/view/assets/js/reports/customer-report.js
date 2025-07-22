// Customer Analytics Dashboard JavaScript - Optimized

$(document).ready(function() {
    initializeDashboard();
});

function initializeDashboard() {
    initializeCharts();
    initializeEventHandlers();
    feather.replace();
    console.log('Customer Analytics Dashboard initialized');
}

// ==================== CHART INITIALIZATION ====================

function initializeCharts() {
    const chartData = getChartData();
    initMonthlyTrendChart(chartData.monthly);
    initRankDistributionChart(chartData.rank);
    initGenderDistributionChart(chartData.gender);
    initAgeDistributionChart(chartData.age);
}

function getChartData() {
    return {
        monthly: window.monthlyDataJson ? JSON.parse(window.monthlyDataJson) : 
                [12, 19, 15, 25, 22, 30, 28, 35, 32, 40, 38, 45],
        rank: window.rankDataJson ? JSON.parse(window.rankDataJson) : [2, 5, 3, 9], // 4 ranks: Bronze, Silver, Gold, VIP
        gender: window.genderDataJson ? JSON.parse(window.genderDataJson) : [65, 35],
        age: window.ageDataJson ? JSON.parse(window.ageDataJson) : [245, 456, 389, 155]
    };
}

function initMonthlyTrendChart(data) {
    new ApexCharts(document.querySelector("#monthly-registration-chart"), {
        series: [{ name: 'Khách hàng mới', data: data }],
        chart: { type: 'area', height: 350, toolbar: { show: false }, animations: { enabled: true, easing: 'easeinout', speed: 800 } },
        dataLabels: { enabled: false },
        stroke: { curve: 'smooth', width: 3 },
        fill: { 
            type: 'gradient', 
            gradient: { shadeIntensity: 1, opacityFrom: 0.4, opacityTo: 0.1, stops: [0, 90, 100] } 
        },
        colors: ['#7367F0'],
        xaxis: { 
            categories: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
            labels: { style: { colors: '#6c757d', fontSize: '12px' } }
        },
        yaxis: { labels: { style: { colors: '#6c757d', fontSize: '12px' }, formatter: val => Math.round(val) } },
        grid: { borderColor: '#f1f1f1', strokeDashArray: 5 },
        tooltip: { theme: 'dark', y: { formatter: val => `${val} khách hàng` } }
    }).render();
}

function initRankDistributionChart(data) {
    // Ensure we always have 4 elements for 4 ranks
    const chartData = data.length === 4 ? data : [2, 5, 3, 9];
    
    new ApexCharts(document.querySelector("#customer-rank-chart"), {
        series: chartData,
        chart: { type: 'donut', height: 300, animations: { enabled: true, easing: 'easeinout', speed: 800 } },
        labels: ['Bronze', 'Silver', 'Gold', 'VIP'],
        colors: ['#FF9F43', '#C0C0C0', '#FFD700', '#FF4560'],
        plotOptions: {
            pie: {
                donut: {
                    size: '70%',
                    labels: {
                        show: true,
                        name: { show: true, fontSize: '16px', fontWeight: 600, color: '#373d3f' },
                        value: { 
                            show: true, 
                            fontSize: '20px', 
                            fontWeight: 700, 
                            color: '#373d3f', 
                            formatter: val => val 
                        },
                        total: { 
                            show: true, 
                            showAlways: true, 
                            label: 'Tổng cộng', 
                            fontSize: '14px', 
                            fontWeight: 600, 
                            color: '#373d3f', 
                            formatter: () => chartData.reduce((a, b) => a + b, 0) 
                        }
                    }
                }
            }
        },
        dataLabels: {
            enabled: true,
            formatter: function (val, opts) {
                const value = opts.w.config.series[opts.seriesIndex];
                const total = opts.w.config.series.reduce((a, b) => a + b, 0);
                const percentage = total > 0 ? ((value / total) * 100).toFixed(1) : 0;
                return `${value} (${percentage}%)`;
            },
            style: { fontSize: '12px', fontWeight: 'bold', colors: ['#fff'] }
        },
        legend: { 
            position: 'bottom', 
            fontSize: '14px', 
            fontWeight: 500, 
            markers: { width: 12, height: 12 } 
        },
        tooltip: { 
            theme: 'dark', 
            y: { 
                formatter: (val) => { 
                    const total = chartData.reduce((a, b) => a + b, 0);
                    const percentage = total > 0 ? ((val / total) * 100).toFixed(1) : 0;
                    return `${val} khách hàng (${percentage}%)`;
                }
            }
        }
    }).render();
}

function initGenderDistributionChart(data) {
    new ApexCharts(document.querySelector("#gender-chart"), {
        series: data,
        chart: { type: 'pie', height: 280, animations: { enabled: true, easing: 'easeinout', speed: 800 } },
        labels: ['Nam', 'Nữ'],
        colors: ['#7367F0', '#EA5455'],
        plotOptions: { pie: { dataLabels: { offset: -5 } } },
        dataLabels: {
            enabled: true,
            formatter: (val, opts) => {
                const value = opts.w.config.series[opts.seriesIndex];
                return `${value}\n(${val.toFixed(1)}%)`;
            },
            style: { fontSize: '14px', fontWeight: 'bold', colors: ['#fff'] }
        },
        legend: { position: 'bottom', fontSize: '14px', fontWeight: 500 },
        tooltip: { theme: 'dark', y: { formatter: val => `${val} khách hàng` } }
    }).render();
}

function initAgeDistributionChart(data) {
    new ApexCharts(document.querySelector("#age-chart"), {
        series: [{ name: 'Số lượng khách hàng', data: data }],
        chart: { type: 'bar', height: 280, toolbar: { show: false }, animations: { enabled: true, easing: 'easeinout', speed: 800 } },
        plotOptions: { bar: { horizontal: true, borderRadius: 8, barHeight: '60%', distributed: true } },
        dataLabels: {
            enabled: true,
            formatter: function (val, opts) {
                const total = opts.w.config.series[0].data.reduce((a, b) => a + b, 0);
                const percentage = total > 0 ? ((val / total) * 100).toFixed(1) : 0;
                return `${val} (${percentage}%)`;
            },
            style: { colors: ['#fff'], fontSize: '12px', fontWeight: 'bold' }
        },
        colors: ['#28C76F', '#FF9F43', '#EA5455', '#7367F0'],
        xaxis: { 
            categories: ['Dưới 25 tuổi', '25-35 tuổi', '36-45 tuổi', '46+ tuổi'],
            labels: { style: { colors: '#6c757d', fontSize: '12px' } }
        },
        yaxis: { labels: { style: { colors: '#6c757d', fontSize: '12px' } } },
        grid: { borderColor: '#f1f1f1', xaxis: { lines: { show: true } }, yaxis: { lines: { show: false } } },
        legend: { show: false },
        tooltip: { 
            theme: 'dark', 
            y: { 
                formatter: val => `${val} khách hàng` 
            } 
        }
    }).render();
}

// ==================== EVENT HANDLERS ====================

function initializeEventHandlers() {
    $('#printReport').click(handlePrintReport);
    
    $(document).on('click', '[onclick="refreshChartData()"]', refreshChartData);
    $(document).on('click', '[onclick="exportTopCustomers()"]', exportTopCustomers);
    
    $('.dash-count').hover(
        function() { $(this).addClass('shadow-lg'); },
        function() { $(this).removeClass('shadow-lg'); }
    );
}

// ==================== EXPORT FUNCTIONS ====================

function handlePrintReport() {
    showToast('Đang chuẩn bị in báo cáo...', 'info');
    setTimeout(() => window.print(), 500);
}

function exportTopCustomers() {
    showToast('Đang xuất danh sách Top 10 khách hàng...', 'info');
    
    // Create a hidden form to submit the request
    const form = document.createElement('form');
    form.method = 'GET';
    form.action = getExportUrl('exportTopCustomers');
    form.style.display = 'none';
    document.body.appendChild(form);
    
    setTimeout(() => {
        form.submit();
        document.body.removeChild(form);
        showToast('Đang tải file Excel...', 'success');
    }, 500);
}

// ==================== UTILITY FUNCTIONS ====================

function getExportUrl(action) {
    return `${window.location.pathname}?action=${action}`;
}

function refreshChartData() {
    showToast('Đang làm mới dữ liệu...', 'info');
    setTimeout(() => location.reload(), 1000);
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

// ==================== ERROR HANDLING ====================

window.addEventListener('error', function(e) {
    console.error('JavaScript Error:', e.error);
    showToast('Đã xảy ra lỗi. Vui lòng làm mới trang.', 'error');
});

$(window).on('load', function() {
    console.log('Dashboard loaded in', performance.now().toFixed(2), 'ms');
});