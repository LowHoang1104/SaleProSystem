/**
 * Invoice Filter System - Production Version
 * File: invoice_filter.js
 * Version: 4.0 - Clean & Optimized
 */

let InvoiceTimeFilter = {
    // Current filters
    currentFilters: {
        timeFilter: 'today',
        paymentMethod: '',
        createdBy: '',
        soldBy: ''
    },
    
    // Configuration
    config: {
        servletUrl: 'InvoiceManagementServlet',
        pageSize: 10,
        debounceDelay: 300
    },

    // Debounce timer
    debounceTimer: null,

    /**
     * Initialize the filter system
     */
    init: function() {
        if (typeof $ === 'undefined') {
            console.error('jQuery is required');
            return;
        }
        
        this.bindFilterEvents();
        this.setDefaultFilters();
    },

    /**
     * Bind all filter events
     */
    bindFilterEvents: function() {
        const self = this;
        
        // Time filter buttons - Apply immediately
        $(document).on('click', '.time-btn', function(e) {
            e.preventDefault();
            const $this = $(this);
            const value = $this.data('value');
            
            if (value === 'custom') {
                alert('Tính năng tùy chỉnh sẽ được phát triển sau');
                return;
            }
            
            self.selectTimeFilter($this, value);
        });
        
        // Filter selects - Auto apply with debounce
        $(document).on('change', 'select[data-filter="paymentMethod"]', function() {
            self.currentFilters.paymentMethod = $(this).val();
            self.debouncedApplyFilters();
        });
        
        $(document).on('change', 'select[data-filter="createdBy"]', function() {
            self.currentFilters.createdBy = $(this).val();
            self.debouncedApplyFilters();
        });
        
        $(document).on('change', 'select[data-filter="soldBy"]', function() {
            self.currentFilters.soldBy = $(this).val();
            self.debouncedApplyFilters();
        });
        
        // Status checkboxes
        $(document).on('change', '.filter-checkbox-group input[type="checkbox"]', function() {
            self.debouncedApplyFilters();
        });
        
        // Reset filter button
        $(document).on('click', '#resetFilter', function(e) {
            e.preventDefault();
            self.resetAllFilters();
        });
        
        // Pagination integration
        this.setupPaginationIntegration();
    },

    /**
     * Debounced filter application
     */
    debouncedApplyFilters: function() {
        const self = this;
        
        if (this.debounceTimer) {
            clearTimeout(this.debounceTimer);
        }
        
        this.debounceTimer = setTimeout(function() {
            self.applyAllFilters();
        }, this.config.debounceDelay);
    },

    /**
     * Setup pagination integration
     */
    setupPaginationIntegration: function() {
        const self = this;
        
        // Page size change
        $(document).on('change', '.pagination-select', function() {
            const newPageSize = parseInt($(this).val());
            self.config.pageSize = newPageSize;
            self.loadFilteredInvoices(1);
        });

        // Page navigation
        $(document).on('click', '.page-link', function(e) {
            e.preventDefault();
            
            const $this = $(this);
            const $parent = $this.parent();
            
            if ($parent.hasClass('disabled')) {
                return false;
            }
            
            const action = $this.data('action');
            const page = parseInt($this.data('page'));
            
            if (action === 'first') {
                self.loadFilteredInvoices(1);
            } else if (action === 'prev') {
                const currentPage = self.getCurrentPage();
                self.loadFilteredInvoices(currentPage - 1);
            } else if (action === 'next') {
                const currentPage = self.getCurrentPage();
                self.loadFilteredInvoices(currentPage + 1);
            } else if (action === 'last') {
                const totalPages = self.getTotalPages();
                self.loadFilteredInvoices(totalPages);
            } else if (!isNaN(page)) {
                self.loadFilteredInvoices(page);
            }
            
            return false;
        });
    },

    /**
     * Select time filter and apply immediately
     */
    selectTimeFilter: function(button, value) {
        $('.time-btn').removeClass('active');
        button.addClass('active');
        
        this.currentFilters.timeFilter = value;
        this.applyAllFilters();
    },

    /**
     * Apply all current filters
     */
    applyAllFilters: function() {
        this.loadFilteredInvoices(1);
    },

    /**
     * Reset all filters to default
     */
    resetAllFilters: function() {
        // Clear pending debounce
        if (this.debounceTimer) {
            clearTimeout(this.debounceTimer);
            this.debounceTimer = null;
        }
        
        // Reset UI
        $('.time-btn').removeClass('active');
        $('[data-value="today"]').addClass('active');
        
        $('select[data-filter="paymentMethod"]').val('');
        $('select[data-filter="createdBy"]').val('');
        $('select[data-filter="soldBy"]').val('');
        
        $('.filter-checkbox-group input[type="checkbox"]').each(function() {
            const id = $(this).attr('id');
            if (id === 'processing' || id === 'completed' || id === 'noDelivery' || id === 'delivery') {
                $(this).prop('checked', true);
            } else {
                $(this).prop('checked', false);
            }
        });
        
        // Reset filters
        this.currentFilters = {
            timeFilter: 'today',
            paymentMethod: '',
            createdBy: '',
            soldBy: ''
        };
        
        this.applyAllFilters();
    },

    /**
     * Load filtered invoices via AJAX
     */
    loadFilteredInvoices: function(page) {
        this.showLoading();
        
        const requestData = {
            action: 'filter',
            timeFilter: this.currentFilters.timeFilter,
            paymentMethod: this.currentFilters.paymentMethod,
            createdBy: this.currentFilters.createdBy,
            soldBy: this.currentFilters.soldBy,
            page: page,
            pageSize: this.getCurrentPageSize(),
            ajax: 'true'
        };
        
        $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            success: (data) => {
                this.hideLoading();
                this.updateContent(data);
                this.updateFilterResults();
            },
            error: (xhr, status, error) => {
                this.hideLoading();
                this.showErrorMessage('Có lỗi xảy ra khi tải dữ liệu: ' + error);
            }
        });
    },

    /**
     * Update page content
     */
    updateContent: function(data) {
        const $newContent = $(data);
        
        // Update table body
        const newTableBody = $newContent.find('.invoice-table tbody').html();
        if (newTableBody) {
            $('.invoice-table tbody').html(newTableBody);
        }
        
        // Update pagination
        const newPagination = $newContent.find('.fixed-pagination').html();
        if (newPagination) {
            $('.fixed-pagination').html(newPagination);
        }
    },

    /**
     * Update filter results display
     */
    updateFilterResults: function() {
        const $results = $('#filterResults');
        const $count = $('#resultCount');
        const $range = $('#dateRange');
        
        const totalText = $('.pagination-info strong').text();
        const totalCount = parseInt(totalText) || 0;
        
        if ($count.length) {
            $count.text(totalCount);
        }
        
        const rangeText = this.getFilterDisplayText();
        if ($range.length) {
            $range.text(rangeText);
        }
        
        if ($results.length) {
            $results.show();
        }
    },

    /**
     * Show error message
     */
    showErrorMessage: function(message) {
        $('.filter-error-message').remove();
        
        const errorHtml = `
            <div class="alert alert-danger alert-dismissible fade show filter-error-message" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        `;
        
        $('.invoice-content').prepend(errorHtml);
        
        setTimeout(() => {
            $('.filter-error-message').fadeOut();
        }, 5000);
    },

    /**
     * Get display text for current filter
     */
    getFilterDisplayText: function() {
        const timeMap = {
            'today': 'hôm nay',
            'yesterday': 'hôm qua',
            'this_week': 'tuần này',
            'last_week': 'tuần trước',
            '7_days': '7 ngày qua',
            'this_month': 'tháng này',
            'last_month': 'tháng trước',
            '30_days': '30 ngày qua',
            'this_quarter': 'quý này',
            'last_quarter': 'quý trước',
            'this_year': 'năm nay',
            'last_year': 'năm trước'
        };
        return timeMap[this.currentFilters.timeFilter] || this.currentFilters.timeFilter;
    },

    /**
     * Loading states
     */
    showLoading: function() {
        if ($('.table-loading-overlay').length === 0) {
            const overlay = $(`
                <div class="table-loading-overlay" style="
                    position: absolute; top: 0; left: 0; right: 0; bottom: 0;
                    background: rgba(255, 255, 255, 0.8);
                    display: flex; align-items: center; justify-content: center; z-index: 999;">
                    <div style="text-align: center;">
                        <div class="spinner-border text-primary" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                        <div class="mt-2">Đang tải...</div>
                    </div>
                </div>
            `);
            $('.table-body-container').css('position', 'relative').append(overlay);
        }
    },

    hideLoading: function() {
        $('.table-loading-overlay').remove();
    },

    /**
     * Helper functions
     */
    getCurrentPage: function() {
        const activePage = $('.page-numbers .active .page-link');
        if (activePage.length) {
            const page = parseInt(activePage.data('page'));
            if (!isNaN(page)) return page;
        }
        return 1;
    },

    getCurrentPageSize: function() {
        const pageSize = parseInt($('.pagination-select').val());
        return !isNaN(pageSize) ? pageSize : this.config.pageSize;
    },

    getTotalPages: function() {
        let maxPage = 1;
        $('.page-numbers .page-link[data-page]').each(function() {
            const page = parseInt($(this).data('page'));
            if (!isNaN(page) && page > maxPage) {
                maxPage = page;
            }
        });
        return maxPage;
    },

    /**
     * Set default filters
     */
    setDefaultFilters: function() {
        $('[data-value="today"]').addClass('active');
        this.currentFilters.timeFilter = 'today';
        this.setupSelectFilters();
    },

    /**
     * Setup select filter attributes
     */
    setupSelectFilters: function() {
        $('.filter-section:has(h6:contains("Phương thức thanh toán")) select').attr('data-filter', 'paymentMethod');
        $('.filter-section:has(h6:contains("Người tạo")) select').attr('data-filter', 'createdBy');
        $('.filter-section:has(h6:contains("Người bán")) select').attr('data-filter', 'soldBy');
    },

    /**
     * Public API
     */
    getCurrentFilters: function() {
        return { ...this.currentFilters };
    },

    setFilters: function(filters) {
        this.currentFilters = { ...this.currentFilters, ...filters };
        this.updateUI();
        this.applyAllFilters();
    },

    updateUI: function() {
        $('.time-btn').removeClass('active');
        $(`[data-value="${this.currentFilters.timeFilter}"]`).addClass('active');
        
        $('select[data-filter="paymentMethod"]').val(this.currentFilters.paymentMethod);
        $('select[data-filter="createdBy"]').val(this.currentFilters.createdBy);
        $('select[data-filter="soldBy"]').val(this.currentFilters.soldBy);
    },

    refresh: function() {
        this.loadFilteredInvoices(this.getCurrentPage());
    }
};

// Auto-initialize
$(document).ready(function() {
    setTimeout(function() {
        InvoiceTimeFilter.init();
    }, 100);
});

// Global access
window.InvoiceTimeFilter = InvoiceTimeFilter;