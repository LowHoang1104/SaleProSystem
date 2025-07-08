// Global request manager to prevent duplicate requests
window.GlobalRequestManager = window.GlobalRequestManager || {
    activeRequests: new Map(),
    
    canMakeRequest: function(fingerprint) {
        if (this.activeRequests.has(fingerprint)) {
            return false;
        }
        return true;
    },
    
    startRequest: function(fingerprint, xhr) {
        this.activeRequests.set(fingerprint, xhr);
    },
    
    finishRequest: function(fingerprint) {
        this.activeRequests.delete(fingerprint);
    }
};

// Destroy existing instance completely
if (window.InvoiceTimeFilter) {
    if (window.InvoiceTimeFilter.currentRequest) {
        window.InvoiceTimeFilter.currentRequest.abort();
    }
    $(document).off('.invoiceFilter .invoicePagination');
    $('.page-link').off('click');
    $('.pagination-select').off('change');
}

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
    isInitialized: false,
    initialLoadComplete: false,

    init: function () {
        if (typeof $ === 'undefined') {
            console.error('jQuery is required');
            return;
        }

        if (window.InvoiceTimeFilter && window.InvoiceTimeFilter.isInitialized) {
            return;
        }
        
        this.bindFilterEvents();
        this.setDefaultFilters();
        this.isInitialized = true;
    },

    createRequestFingerprint: function(action, page, searchTerm) {
        return `${action}_${page}_${searchTerm || 'null'}_${this.currentFilters.timeFilter}_${this.currentFilters.paymentMethod}_${this.currentFilters.createdBy}_${this.currentFilters.soldBy}`;
    },

    bindFilterEvents: function () {
        const self = this;

        // Remove ALL possible event handlers
        $(document).off('.invoiceFilter');
        $(document).off('.invoicePagination'); 
        $('.page-link').off();
        $('.pagination-select').off();
        $('.time-btn').off();
        $('select[data-filter]').off();
        $('#resetFilter').off();

        // Wait a bit to ensure cleanup
        setTimeout(() => {
            const namespace = '.invoiceFilter' + Date.now();
            
            $(document).on('click' + namespace, '.time-btn', function (e) {
                e.preventDefault();
                e.stopImmediatePropagation();
                
                const $this = $(this);
                const value = $this.data('value');
                
                if ($this.hasClass('processing')) return false;
                
                self.selectTimeFilter($this, value);
            });

            $(document).on('change' + namespace, 'select[data-filter="paymentMethod"]', function (e) {
                e.stopImmediatePropagation();
                self.currentFilters.paymentMethod = $(this).val();
                self.debouncedApplyFilters();
            });

            $(document).on('change' + namespace, 'select[data-filter="createdBy"]', function (e) {
                e.stopImmediatePropagation();
                self.currentFilters.createdBy = $(this).val();
                self.debouncedApplyFilters();
            });

            $(document).on('change' + namespace, 'select[data-filter="soldBy"]', function (e) {
                e.stopImmediatePropagation();
                self.currentFilters.soldBy = $(this).val();
                self.debouncedApplyFilters();
            });

            $(document).on('click' + namespace, '#resetFilter', function (e) {
                e.preventDefault();
                e.stopImmediatePropagation();
                
                if ($(this).hasClass('processing')) return false;
                self.resetAllFilters();
            });

            self.setupPaginationIntegration();
        }, 100);
    },

    debouncedApplyFilters: function () {
        if (this.debounceTimer) clearTimeout(this.debounceTimer);
        this.debounceTimer = setTimeout(() => this.applyAllFilters(), this.config.debounceDelay);
    },

    setupPaginationIntegration: function () {
        const self = this;
        const namespace = '.invoicePagination' + Date.now();

        $(document).on('change' + namespace, '.pagination-select', function (e) {
            e.preventDefault();
            e.stopImmediatePropagation();
            
            const $this = $(this);
            const newPageSize = parseInt($this.val());
            
            if ($this.data('processing') === 'true') return false;
            if (self.config.pageSize === newPageSize) return false;
            
            $this.data('processing', 'true');
            self.config.pageSize = newPageSize;
            
            self.loadPaginationOnly(1).always(() => {
                $this.data('processing', 'false');
            });
        });

        $(document).on('click' + namespace, '.page-link', function (e) {
            e.preventDefault();
            e.stopImmediatePropagation();
            e.stopPropagation();

            const $this = $(this);
            const $parent = $this.parent();

            if ($parent.hasClass('disabled')) return false;
            if ($this.hasClass('processing')) return false;
            if ($this.data('processing') === 'true') return false;

            const action = $this.data('action');
            const page = parseInt($this.data('page'));
            let targetPage = 1;

            if (action === 'first') {
                targetPage = 1;
            } else if (action === 'prev') {
                targetPage = Math.max(1, self.getCurrentPage() - 1);
            } else if (action === 'next') {
                targetPage = self.getCurrentPage() + 1;
            } else if (action === 'last') {
                targetPage = self.getTotalPages();
            } else if (!isNaN(page)) {
                targetPage = page;
            }

            const isSearchActive = window.InvoiceSearch && InvoiceSearch.isSearchActive();
            const currentSearchTerm = isSearchActive ? $('#mainSearchInput').val().trim() : '';
            const requestAction = isSearchActive && currentSearchTerm ? 'filterWithSearch' : 'filter';
            const fingerprint = self.createRequestFingerprint(requestAction, targetPage, currentSearchTerm);

            if (!window.GlobalRequestManager.canMakeRequest(fingerprint)) {
                return false;
            }

            $this.data('processing', 'true').addClass('processing');
            $('.page-link').prop('disabled', true);

            const requestPromise = self.loadPaginationOnly(targetPage);
            window.GlobalRequestManager.startRequest(fingerprint, requestPromise);

            requestPromise.always(() => {
                $this.data('processing', 'false').removeClass('processing');
                $('.page-link').prop('disabled', false);
                window.GlobalRequestManager.finishRequest(fingerprint);
            });

            return false;
        });
    },

    selectTimeFilter: function (button, value) {
        $('.time-btn').removeClass('active processing');
        button.addClass('active processing');
        this.currentFilters.timeFilter = value;
        this.applyAllFilters().always(() => button.removeClass('processing'));
    },

    applyAllFilters: function () {
        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
        }

        const isSearchActive = window.InvoiceSearch && InvoiceSearch.isSearchActive();
        const currentSearchTerm = isSearchActive ? $('#mainSearchInput').val().trim() : '';

        return isSearchActive && currentSearchTerm 
            ? this.loadFilterWithSearch(1, currentSearchTerm)
            : this.loadFilteredInvoices(1);
    },

    loadFilterWithSearch: function (page, searchTerm) {
        const fingerprint = this.createRequestFingerprint('filterWithSearch', page, searchTerm);
        
        if (!window.GlobalRequestManager.canMakeRequest(fingerprint)) {
            return $.Deferred().reject('duplicate').promise();
        }

        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
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
            ajax: 'true',
            _timestamp: Date.now()
        };

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            timeout: 30000,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
                $('#mainSearchInput').val(searchTerm);
                $('.search-container').addClass('search-active');
                $('#searchBtn').addClass('search-active');
                window.GlobalRequestManager.finishRequest(fingerprint);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort' && status !== 'duplicate') {
                    console.error('filterWithSearch error:', status, error);
                    this.hideLoading();
                }
                window.GlobalRequestManager.finishRequest(fingerprint);
            }
        });

        window.GlobalRequestManager.startRequest(fingerprint, this.currentRequest);
        return this.currentRequest;
    },

    loadPaginationOnly: function (page) {
        const isSearchActive = window.InvoiceSearch && InvoiceSearch.isSearchActive();
        const currentSearchTerm = isSearchActive ? $('#mainSearchInput').val().trim() : '';
        const action = isSearchActive && currentSearchTerm ? 'filterWithSearch' : 'filter';
        const fingerprint = this.createRequestFingerprint(action, page, currentSearchTerm);
        
        if (!window.GlobalRequestManager.canMakeRequest(fingerprint)) {
            return $.Deferred().reject('duplicate').promise();
        }

        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
        }
        
        this.showLoading();

        const requestData = {
            action: action,
            timeFilter: this.currentFilters.timeFilter,
            paymentMethod: this.currentFilters.paymentMethod,
            createdBy: this.currentFilters.createdBy,
            soldBy: this.currentFilters.soldBy,
            page: page,
            pageSize: this.getCurrentPageSize(),
            ajax: 'true',
            _timestamp: Date.now()
        };

        if (isSearchActive && currentSearchTerm) {
            requestData.quickSearch = currentSearchTerm;
        }

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            timeout: 30000,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
                window.GlobalRequestManager.finishRequest(fingerprint);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort' && status !== 'duplicate') {
                    console.error('Pagination error:', status, error);
                    this.hideLoading();
                }
                window.GlobalRequestManager.finishRequest(fingerprint);
            }
        });

        window.GlobalRequestManager.startRequest(fingerprint, this.currentRequest);
        return this.currentRequest;
    },

    resetAllFilters: function () {
        $('#resetFilter').addClass('processing');
        
        if (this.debounceTimer) {
            clearTimeout(this.debounceTimer);
            this.debounceTimer = null;
        }

        $('.time-btn').removeClass('active');
        $('[data-value="today"]').addClass('active');
        $('select[data-filter]').val('');

        this.currentFilters = {
            timeFilter: 'today',
            paymentMethod: '',
            createdBy: '',
            soldBy: ''
        };

        if (window.InvoiceSearch) InvoiceSearch.clearSearch();

        this.applyAllFilters().always(() => $('#resetFilter').removeClass('processing'));
    },

    loadFilteredInvoices: function (page) {
        const fingerprint = this.createRequestFingerprint('filter', page, null);
        
        if (!window.GlobalRequestManager.canMakeRequest(fingerprint)) {
            return $.Deferred().reject('duplicate').promise();
        }

        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
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
            ajax: 'true',
            _timestamp: Date.now()
        };

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            timeout: 30000,
            success: (data) => {
                this.currentRequest = null;
                this.hideLoading();
                this.updateContent(data);
                this.initialLoadComplete = true;
                window.GlobalRequestManager.finishRequest(fingerprint);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort' && status !== 'duplicate') {
                    console.error('Filter error:', status, error);
                    this.hideLoading();
                }
                window.GlobalRequestManager.finishRequest(fingerprint);
            }
        });

        window.GlobalRequestManager.startRequest(fingerprint, this.currentRequest);
        return this.currentRequest;
    },

    updateContent: function (data) {
        const $newContent = $(data);
        const newTableBody = $newContent.find('.invoice-table tbody').html();
        const newPagination = $newContent.find('.fixed-pagination').html();
        
        if (newTableBody) $('.invoice-table tbody').html(newTableBody);
        if (newPagination) $('.fixed-pagination').html(newPagination);
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
        const paginationText = $('.pagination-info').text();
        const match = paginationText.match(/trên tổng\s+(\d+)\s+kết quả/i);
        
        if (match) {
            const totalItems = parseInt(match[1]);
            const pageSize = this.getCurrentPageSize();
            return Math.ceil(totalItems / pageSize);
        }
        
        return 1;
    },

    setDefaultFilters: function () {
        $('[data-value="today"]').addClass('active');
        this.currentFilters.timeFilter = 'today';
        this.setupSelectFilters();
        
        const hasTableData = $('.invoice-table tbody tr').length > 0;
        const currentAction = new URLSearchParams(window.location.search).get('action');
        
        if (hasTableData && (currentAction === 'invoices' || !currentAction)) {
            this.initialLoadComplete = true;
        } else {
            this.loadFilteredInvoices(1);
        }
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
        debounceDelay: 300,
        pageSize: 10
    },

    searchState: {
        isSearchActive: false,
        currentSearchData: null
    },

    searchTimer: null,
    currentRequest: null,
    isInitialized: false,

    init: function () {
        if (typeof $ === 'undefined') {
            console.error('jQuery is required for InvoiceSearch');
            return;
        }
        
        if (this.isInitialized) return;
        
        this.bindSearchEvents();
        this.isInitialized = true;
    },

    bindSearchEvents: function () {
        const self = this;

        $(document).off('.invoiceSearch');

        $(document).on('input.invoiceSearch', '#mainSearchInput', function (e) {
            e.stopImmediatePropagation();
            
            const value = $(this).val().trim();
            
            if (self.searchTimer) {
                clearTimeout(self.searchTimer);
                self.searchTimer = null;
            }
            
            if (value.length === 0) {
                self.clearSearch();
            } else if (value.length >= 1) {
                self.debouncedQuickSearch(value);
            }
        });

        $(document).on('keyup.invoiceSearch', '#mainSearchInput', function (e) {
            e.stopImmediatePropagation();
            
            const value = $(this).val().trim();
            
            if (value.length === 0 && self.searchState.isSearchActive) {
                if (self.searchTimer) {
                    clearTimeout(self.searchTimer);
                    self.searchTimer = null;
                }
                self.clearSearch();
            }
        });

        $(document).on('click.invoiceSearch', '#searchBtn', function (e) {
            e.preventDefault();
            e.stopImmediatePropagation();
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
            setTimeout(() => $('#searchInvoiceCode').focus(), 250);
        }
    },

    debouncedQuickSearch: function (query) {
        if (this.searchTimer) clearTimeout(this.searchTimer);
        this.searchTimer = setTimeout(() => this.quickSearch(query), this.config.debounceDelay);
    },

    quickSearch: function (query) {
        this.performQuickSearch({ quickSearch: query });
    },

    performQuickSearch: function (searchData) {
        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
        }

        this.showSearchLoading();
        this.searchState.isSearchActive = true;
        this.searchState.currentSearchData = searchData;

        const requestData = {
            action: 'filterWithSearch',
            timeFilter: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.timeFilter : 'today',
            paymentMethod: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.paymentMethod : '',
            createdBy: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.createdBy : '',
            soldBy: window.InvoiceTimeFilter ? InvoiceTimeFilter.currentFilters.soldBy : '',
            quickSearch: searchData.quickSearch,
            page: 1,
            pageSize: this.config.pageSize,
            ajax: 'true',
            _timestamp: Date.now()
        };

        this.currentRequest = $.ajax({
            url: this.config.servletUrl,
            type: 'GET',
            data: requestData,
            timeout: 30000,
            success: (response) => {
                this.currentRequest = null;
                this.hideSearchLoading();
                this.handleSearchSuccess(response, searchData);
            },
            error: (xhr, status, error) => {
                this.currentRequest = null;
                if (xhr.statusText !== 'abort') {
                    console.error('Search error:', status, error);
                    this.hideSearchLoading();
                }
            }
        });
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
        if (this.searchTimer) {
            clearTimeout(this.searchTimer);
            this.searchTimer = null;
        }
        
        if (this.currentRequest) {
            this.currentRequest.abort();
            this.currentRequest = null;
        }

        this.searchState.isSearchActive = false;
        this.searchState.currentSearchData = null;

        $('#mainSearchInput').val('');
        $('.search-container').removeClass('search-active');
        $('#searchBtn').removeClass('search-active');

        if (window.InvoiceTimeFilter && typeof InvoiceTimeFilter.loadFilteredInvoices === 'function') {
            InvoiceTimeFilter.loadFilteredInvoices(1);
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

let isGloballyInitialized = false;

$(document).ready(function () {
    if (isGloballyInitialized) return;
    
    setTimeout(function () {
        try {
            InvoiceTimeFilter.init();
            InvoiceSearch.init();
            isGloballyInitialized = true;
        } catch (error) {
            console.error('Error initializing invoice modules:', error);
        }
    }, 100);
});

$(window).on('beforeunload', function() {
    if (window.InvoiceTimeFilter && InvoiceTimeFilter.currentRequest) {
        InvoiceTimeFilter.currentRequest.abort();
    }
    if (window.InvoiceSearch && InvoiceSearch.currentRequest) {
        InvoiceSearch.currentRequest.abort();
    }
});

window.InvoiceTimeFilter = InvoiceTimeFilter;
window.InvoiceSearch = InvoiceSearch;