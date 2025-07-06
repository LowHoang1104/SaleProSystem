let InvoiceTimeFilter = {
    currentFilters: {
        timeFilter: 'today',
        paymentMethod: '',
        createdBy: '',
        soldBy: ''
    },

    config: {
        servletUrl: 'InvoiceManagementServlet',
        pageSize: 10,
        debounceDelay: 300
    },

    debounceTimer: null,
    currentRequest: null,

    init: function () {
        if (typeof $ === 'undefined') {
            console.error('jQuery is required');
            return;
        }
        this.bindFilterEvents();
        this.setDefaultFilters();
    },

    bindFilterEvents: function () {
        const self = this;

        // Time filter buttons
        $(document).on('click', '.time-btn', function (e) {
            e.preventDefault();
            const $this = $(this);
            const value = $this.data('value');
            self.selectTimeFilter($this, value);
        });

        // Filter selects
        $(document).on('change', 'select[data-filter="paymentMethod"]', function () {
            self.currentFilters.paymentMethod = $(this).val();
            self.debouncedApplyFilters();
        });

        $(document).on('change', 'select[data-filter="createdBy"]', function () {
            self.currentFilters.createdBy = $(this).val();
            self.debouncedApplyFilters();
        });

        $(document).on('change', 'select[data-filter="soldBy"]', function () {
            self.currentFilters.soldBy = $(this).val();
            self.debouncedApplyFilters();
        });

        // Reset filter button
        $(document).on('click', '#resetFilter', function (e) {
            e.preventDefault();
            self.resetAllFilters();
        });

        this.setupPaginationIntegration();
    },

    debouncedApplyFilters: function () {
        const self = this;

        if (this.debounceTimer) {
            clearTimeout(this.debounceTimer);
        }

        this.debounceTimer = setTimeout(function () {
            self.applyAllFilters();
        }, this.config.debounceDelay);
    },

    setupPaginationIntegration: function () {
        const self = this;

        $(document).on('change', '.pagination-select', function () {
            const newPageSize = parseInt($(this).val());
            self.config.pageSize = newPageSize;
            self.loadPaginationOnly(1);
        });

        $(document).on('click', '.page-link', function (e) {
            e.preventDefault();

            const $this = $(this);
            const $parent = $this.parent();

            if ($parent.hasClass('disabled')) {
                return false;
            }

            const action = $this.data('action');
            const page = parseInt($this.data('page'));

            let targetPage = 1;

            if (action === 'first') {
                targetPage = 1;
            } else if (action === 'prev') {
                const currentPage = self.getCurrentPage();
                targetPage = Math.max(1, currentPage - 1);
            } else if (action === 'next') {
                const currentPage = self.getCurrentPage();
                targetPage = currentPage + 1;
            } else if (action === 'last') {
                const totalPages = self.getTotalPages();
                targetPage = totalPages;
            } else if (!isNaN(page)) {
                targetPage = page;
            }

            self.loadPaginationOnly(targetPage);
            return false;
        });
    },

    selectTimeFilter: function (button, value) {
        $('.time-btn').removeClass('active');
        button.addClass('active');

        this.currentFilters.timeFilter = value;
        this.applyAllFilters();
    },

    applyAllFilters: function () {
        // Kiểm tra xem có đang search không
        const isSearchActive = window.InvoiceSearch && InvoiceSearch.isSearchActive();
        const currentSearchTerm = isSearchActive ? $('#mainSearchInput').val().trim() : '';

        if (isSearchActive && currentSearchTerm) {
            // Nếu đang search, gọi filterWithSearch để apply filter + search cùng lúc
            this.loadFilterWithSearch(1, currentSearchTerm);
        } else {
            // Không search, chỉ filter bình thường
            this.loadFilteredInvoices(1);
        }
    },

    loadFilterWithSearch: function (page, searchTerm) {
        if (this.currentRequest) {
            this.currentRequest.abort();
        }

        this.showLoading();

        const requestData = {
            action: 'filterWithSearch',
            timeFilter: this.currentFilters.timeFilter,
            paymentMethod: this.currentFilters.paymentMethod,
            createdBy: this.currentFilters.createdBy,
            soldBy: this.currentFilters.soldBy,
            quickSearch: searchTerm,
            page: page,
            pageSize: this.getCurrentPageSize(),
            ajax: 'true'
        };

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
                
                // Đảm bảo search input vẫn có giá trị
                $('#mainSearchInput').val(searchTerm);
                $('.search-container').addClass('search-active');
                $('#searchBtn').addClass('search-active');
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort') {
                    this.hideLoading();
                }
            }
        });
    },

    loadPaginationOnly: function (page) {
        if (this.currentRequest) {
            this.currentRequest.abort();
        }

        this.showLoading();

        // Kiểm tra xem có đang search không
        const isSearchActive = window.InvoiceSearch && InvoiceSearch.isSearchActive();
        const currentSearchTerm = isSearchActive ? $('#mainSearchInput').val().trim() : '';

        let requestData;
        
        if (isSearchActive && currentSearchTerm) {
            // Đang search -> pagination với search
            requestData = {
                action: 'filterWithSearch',
                timeFilter: this.currentFilters.timeFilter,
                paymentMethod: this.currentFilters.paymentMethod,
                createdBy: this.currentFilters.createdBy,
                soldBy: this.currentFilters.soldBy,
                quickSearch: currentSearchTerm,
                page: page,
                pageSize: this.getCurrentPageSize(),
                ajax: 'true'
            };
        } else {
            // Không search -> pagination thông thường
            requestData = {
                action: 'filter',
                timeFilter: this.currentFilters.timeFilter,
                paymentMethod: this.currentFilters.paymentMethod,
                createdBy: this.currentFilters.createdBy,
                soldBy: this.currentFilters.soldBy,
                page: page,
                pageSize: this.getCurrentPageSize(),
                ajax: 'true'
            };
        }

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort') {
                    this.hideLoading();
                }
            }
        });
    },

    resetAllFilters: function () {
        if (this.debounceTimer) {
            clearTimeout(this.debounceTimer);
            this.debounceTimer = null;
        }

        $('.time-btn').removeClass('active');
        $('[data-value="today"]').addClass('active');

        $('select[data-filter="paymentMethod"]').val('');
        $('select[data-filter="createdBy"]').val('');
        $('select[data-filter="soldBy"]').val('');

        this.currentFilters = {
            timeFilter: 'today',
            paymentMethod: '',
            createdBy: '',
            soldBy: ''
        };

        this.applyAllFilters();
    },

    loadFilteredInvoices: function (page) {
        if (this.currentRequest) {
            this.currentRequest.abort();
        }

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

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort') {
                    this.hideLoading();
                }
            }
        });
    },

    updateContent: function (data) {
        const $newContent = $(data);

        const newTableBody = $newContent.find('.invoice-table tbody').html();
        if (newTableBody) {
            $('.invoice-table tbody').html(newTableBody);
        }

        const newPagination = $newContent.find('.fixed-pagination').html();
        if (newPagination) {
            $('.fixed-pagination').html(newPagination);
        }
    },

    showLoading: function () {
        if ($('.table-loading-overlay').length === 0) {
            const overlay = $(`
                <div class="table-loading-overlay" style="
                    position: absolute; top: 0; left: 0; right: 0; bottom: 0;
                    background: rgba(255, 255, 255, 0.8);
                    display: flex; align-items: center; justify-content: center; z-index: 999;">
                    <div class="spinner-border text-primary"></div>
                </div>
            `);
            $('.table-body-container').css('position', 'relative').append(overlay);
        }
    },

    hideLoading: function () {
        $('.table-loading-overlay').remove();
    },

    getCurrentPage: function () {
        const activePage = $('.page-numbers .active .page-link');
        if (activePage.length) {
            const page = parseInt(activePage.data('page'));
            if (!isNaN(page)) return page;
        }
        return 1;
    },

    getCurrentPageSize: function () {
        const pageSize = parseInt($('.pagination-select').val());
        return !isNaN(pageSize) ? pageSize : this.config.pageSize;
    },

    getTotalPages: function () {
        let maxPage = 1;
        $('.page-numbers .page-link[data-page]').each(function () {
            const page = parseInt($(this).data('page'));
            if (!isNaN(page) && page > maxPage) {
                maxPage = page;
            }
        });
        return maxPage;
    },

    setDefaultFilters: function () {
        $('[data-value="today"]').addClass('active');
        this.currentFilters.timeFilter = 'today';
        this.setupSelectFilters();
        this.loadFilteredInvoices(1);
    },

    setupSelectFilters: function () {
        $('.filter-section:has(h6:contains("Phương thức thanh toán")) select').attr('data-filter', 'paymentMethod');
        $('.filter-section:has(h6:contains("Người tạo")) select').attr('data-filter', 'createdBy');
        $('.filter-section:has(h6:contains("Người bán")) select').attr('data-filter', 'soldBy');
    }
};

let InvoiceSearch = {
    config: {
        servletUrl: 'InvoiceManagementServlet',
        debounceDelay: 500,
        pageSize: 10
    },

    searchState: {
        isSearchActive: false,
        currentSearchData: null
    },

    searchTimer: null,
    currentRequest: null,

    init: function () {
        if (typeof $ === 'undefined') {
            console.error('jQuery is required for InvoiceSearch');
            return;
        }
        this.bindSearchEvents();
    },

    bindSearchEvents: function () {
        const self = this;

        $(document).on('input', '#mainSearchInput', function () {
            const value = $(this).val().trim();
            if (value.length >= 2) {
                self.debouncedQuickSearch(value);
            } else if (value.length === 0 && self.searchState.isSearchActive) {
                self.clearSearch();
            }
        });

        $(document).on('click', '#searchBtn', function (e) {
            e.preventDefault();
            self.toggleSearchPopup();
        });
    },

    toggleSearchPopup: function () {
        const $popup = $('#searchPopup');
        const $searchBtn = $('#searchBtn');

        if ($popup.is(':visible')) {
            $popup.slideUp(200);
            $searchBtn.removeClass('active');
        } else {
            $popup.slideDown(200);
            $searchBtn.addClass('active');
            setTimeout(() => {
                $('#searchInvoiceCode').focus();
            }, 250);
        }
    },

    quickSearch: function (query) {
        const searchData = {
            quickSearch: query
        };
        this.performQuickSearch(searchData);
    },

    performQuickSearch: function (searchData) {
        if (this.currentRequest) {
            this.currentRequest.abort();
        }

        this.showSearchLoading();

        this.searchState.isSearchActive = true;
        this.searchState.currentSearchData = searchData;

        // Sử dụng filterWithSearch để apply filter hiện tại + search cùng lúc
        const requestData = {
            action: 'filterWithSearch',
            timeFilter: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.timeFilter : 'today',
            paymentMethod: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.paymentMethod : '',
            createdBy: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.createdBy : '',
            soldBy: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.soldBy : '',
            quickSearch: searchData.quickSearch,
            page: 1,
            pageSize: this.config.pageSize,
            ajax: 'true'
        };

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            success: (response) => {
                this.currentRequest = null;
                this.hideSearchLoading();
                this.handleSearchSuccess(response, searchData);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort') {
                    this.hideSearchLoading();
                }
            }
        });
    },

    debouncedQuickSearch: function (query) {
        const self = this;

        if (this.searchTimer) {
            clearTimeout(this.searchTimer);
        }

        this.searchTimer = setTimeout(() => {
            self.quickSearch(query);
        }, this.config.debounceDelay);
    },

    handleSearchSuccess: function (response, searchData) {
        if (window.InvoiceTimeFilter && typeof InvoiceTimeFilter.updateContent === 'function') {
            InvoiceTimeFilter.updateContent(response);
        }

        $('#mainSearchInput').val(searchData.quickSearch);
        $('.search-container').addClass('search-active');
        $('#searchBtn').addClass('search-active');
    },

    clearSearch: function () {
        this.searchState.isSearchActive = false;
        this.searchState.currentSearchData = null;

        $('#mainSearchInput').val('');
        $('.search-container').removeClass('search-active');
        $('#searchBtn').removeClass('search-active');

        if (window.InvoiceTimeFilter && typeof InvoiceTimeFilter.applyAllFilters === 'function') {
            InvoiceTimeFilter.applyAllFilters();
        }
    },

    showSearchLoading: function () {
        const $searchBtn = $('#searchBtn');
        $searchBtn.html('<i class="fas fa-spinner fa-spin"></i>').prop('disabled', true);

        if (window.InvoiceTimeFilter && typeof InvoiceTimeFilter.showLoading === 'function') {
            InvoiceTimeFilter.showLoading();
        }
    },

    hideSearchLoading: function () {
        const $searchBtn = $('#searchBtn');
        $searchBtn.html('<i class="fas fa-bars"></i>').prop('disabled', false);

        if (window.InvoiceTimeFilter && typeof InvoiceTimeFilter.hideLoading === 'function') {
            InvoiceTimeFilter.hideLoading();
        }
    },

    isSearchActive: function () {
        return this.searchState.isSearchActive;
    }
};

// Initialize
$(document).ready(function () {
    setTimeout(function () {
        InvoiceTimeFilter.init();
        InvoiceSearch.init();
    }, 100);
});

// Global access
window.InvoiceTimeFilter = InvoiceTimeFilter;
window.InvoiceSearch = InvoiceSearch;