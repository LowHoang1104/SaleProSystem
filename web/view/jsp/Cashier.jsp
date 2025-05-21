<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kiot Vi?t - H? th?ng bán hàng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: #f5f5f5;
            font-size: 14px;
        }
        
        .header {
            background-color: #27ae60;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 15px;
            height: 50px;
        }

        .search-box {
            background-color: white;
            border-radius: 4px;
            display: flex;
            align-items: center;
            padding: 5px 10px;
            width: 330px;
        }

        .search-box input {
            border: none;
            outline: none;
            width: 100%;
            padding: 5px;
        }

        .header-btn {
            background-color: #27ae60;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
            display: flex;
            align-items: center;
            margin-left: 10px;
        }

        .main-container {
            display: flex;
            height: calc(100vh - 100px);
        }

        .left-panel {
            width: 60%;
            background-color: white;
            border-right: 1px solid #ddd;
            display: flex;
            flex-direction: column;
        }

        .right-panel {
            width: 40%;
            background-color: white;
            overflow-y: auto;
        }

        .item-list {
            flex: 1;
            overflow-y: auto;
        }

        .item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #eee;
        }

        .item:hover {
            background-color: #f9f9f9;
        }

        .item-delete {
            color: #e74c3c;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 18px;
        }

        .item-add {
            color: #2ecc71;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 18px;
        }

        .item-actions {
            color: #7f8c8d;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 18px;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            border: 1px solid #eee;
            border-radius: 4px;
            width: 100px;
            text-align: center;
        }

        .quantity-btn {
            width: 30px;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 16px;
            color: #7f8c8d;
        }

        .quantity-input {
            width: 40px;
            border: none;
            text-align: center;
            font-weight: bold;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
            padding: 15px;
        }

        .product-card {
            border: 1px solid #eee;
            border-radius: 8px;
            padding: 10px;
            cursor: pointer;
            text-align: center;
            transition: all 0.2s ease;
        }

        .product-card:hover {
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .product-name {
            font-weight: 500;
            margin-bottom: 5px;
            color: #333;
        }

        .product-price {
            color: #27ae60;
            font-weight: bold;
        }

        .total-section {
            display: flex;
            justify-content: space-between;
            padding: 15px;
            font-weight: bold;
            font-size: 16px;
            border-bottom: 1px solid #eee;
        }

        .total-number {
            color: #27ae60;
        }

        .search-customer {
            padding: 15px;
            display: flex;
            align-items: center;
            border-bottom: 1px solid #eee;
        }

        .search-customer input {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 8px;
            width: 100%;
            outline: none;
        }

        .search-customer button {
            background: none;
            border: none;
            cursor: pointer;
            margin-left: 10px;
            color: #7f8c8d;
        }

        .pagination {
            display: flex;
            justify-content: center;
            padding: 15px;
            border-top: 1px solid #eee;
        }

        .page-btn {
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            padding: 5px 10px;
            margin: 0 5px;
            cursor: pointer;
        }

        .page-btn.active {
            background-color: #27ae60;
            color: white;
            border-color: #27ae60;
        }

        .filters {
            display: flex;
            padding: 15px;
            align-items: center;
            border-bottom: 1px solid #eee;
        }

        .filter-btn {
            background: none;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 8px 15px;
            margin-right: 10px;
            cursor: pointer;
            display: flex;
            align-items: center;
        }

        .payment-button {
            background-color: #27ae60;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 12px;
            width: 100%;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 15px;
        }

        .payment-button:hover {
            background-color: #219653;
        }

        .footer {
            background-color: #f9f9f9;
            border-top: 1px solid #eee;
            height: 50px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 15px;
        }

        .sale-type {
            display: flex;
            border-top: 1px solid #eee;
        }

        .sale-type-btn {
            flex: 1;
            padding: 12px;
            text-align: center;
            cursor: pointer;
            border: none;
            background: none;
        }

        .sale-type-btn.active {
            background-color: #e6f7ed;
            color: #27ae60;
            border-bottom: 2px solid #27ae60;
            font-weight: bold;
        }

        .header-buttons {
            display: flex;
            align-items: center;
        }

        .note-input {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }

        .note-input textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: none;
            outline: none;
        }

        .icon-margin {
          margin-right: 5px;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="search-box">
            <i class="fas fa-search"></i>
            <input type="text" placeholder="Tìm hàng hóa (F3)">
        </div>
        <div class="header-buttons">
            <button class="header-btn">
                <i class="fas fa-hashtag icon-margin"></i> S? l??ng
            </button>
            <div style="position: relative;">
                <button class="header-btn">
                    <i class="fas fa-file-invoice icon-margin"></i> Hóa ??n 1 <i class="fas fa-times"></i>
                </button>
            </div>
            <button class="header-btn">
                <i class="fas fa-plus icon-margin"></i>
            </button>
            <button class="header-btn">
                <i class="fas fa-shopping-bag"></i>
            </button>
            <button class="header-btn">
                <i class="fas fa-arrow-left"></i>
            </button>
            <button class="header-btn">
                <i class="fas fa-arrow-right"></i>
            </button>
            <button class="header-btn">
                <i class="fas fa-print"></i>
            </button>
            <div style="display: flex; align-items: center; margin-left: 10px;">
                <span style="color: white; font-weight: bold;">0335678512</span>
                <button class="header-btn">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-container">
        <!-- Left Panel -->
        <div class="left-panel">
            <div class="item-list">
                <!-- Item 1 -->
                <div class="item">
                    <div style="display: flex; align-items: center; width: 40px; text-align: center;">
                        <span>3</span>
                        <button class="item-delete">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    <div style="width: 100px; font-weight: 500;">NAM010</div>
                    <div style="flex: 1; font-weight: 500;">Cà v?t nam Hàn Qu?c</div>
                    <div class="quantity-control">
                        <button class="quantity-btn">-</button>
                        <input type="text" class="quantity-input" value="1">
                        <button class="quantity-btn">+</button>
                    </div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">200,000</div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">200,000</div>
                    <button class="item-actions">
                        <i class="fas fa-ellipsis-v"></i>
                    </button>
                </div>

                <!-- Item 2 -->
                <div class="item">
                    <div style="display: flex; align-items: center; width: 40px; text-align: center;">
                        <span>2</span>
                        <button class="item-delete">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    <div style="width: 100px; font-weight: 500;">NAM007</div>
                    <div style="flex: 1; font-weight: 500;">Áo s? mi nam s?c tr?ng</div>
                    <div class="quantity-control">
                        <button class="quantity-btn">-</button>
                        <input type="text" class="quantity-input" value="1">
                        <button class="quantity-btn">+</button>
                    </div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">719,200</div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">719,200</div>
                    <button class="item-actions">
                        <i class="fas fa-ellipsis-v"></i>
                    </button>
                </div>

                <!-- Item 3 -->
                <div class="item">
                    <div style="display: flex; align-items: center; width: 40px; text-align: center;">
                        <span>1</span>
                        <button class="item-delete">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                    <div style="width: 100px; font-weight: 500;">NAM004</div>
                    <div style="flex: 1; font-weight: 500;">Áo vest nam màu xanh tr?ng</div>
                    <div class="quantity-control">
                        <button class="quantity-btn">-</button>
                        <input type="text" class="quantity-input" value="1">
                        <button class="quantity-btn">+</button>
                    </div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">1,299,000</div>
                    <div style="width: 100px; text-align: right; font-weight: 500;">1,299,000</div>
                    <button class="item-actions">
                        <i class="fas fa-ellipsis-v"></i>
                    </button>
                </div>
            </div>

            <div class="note-input">
                <textarea placeholder="Ghi chú ??n hàng"></textarea>
            </div>

            <div class="total-section">
                <div>T?ng ti?n hàng <span style="font-weight: normal;">(3)</span></div>
                <div class="total-number">2,218,200</div>
            </div>
        </div>

        <!-- Right Panel -->
        <div class="right-panel">
            <div class="search-customer">
                <input type="text" placeholder="Tìm khách hàng (F4)">
                <button><i class="fas fa-plus"></i></button>
                <button><i class="fas fa-list"></i></button>
                <button><i class="fas fa-filter"></i></button>
                <button><i class="fas fa-image"></i></button>
            </div>

            <div class="product-grid">
                <!-- Row 1 -->
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Giày da nam" class="product-image">
                    <div class="product-name">Giày da nam màu nâu</div>
                    <div class="product-price">629,100?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Giày nam bu?c dây" class="product-image">
                    <div class="product-name">Giày nam bu?c dây màu nâu</div>
                    <div class="product-price">629,100?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Giày tây l??i nam" class="product-image">
                    <div class="product-name">Giày tây l??i nam màu ?en</div>
                    <div class="product-price">629,150?</div>
                </div>

                <!-- Row 2 -->
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Áo vest n? màu h?ng" class="product-image">
                    <div class="product-name">Áo vest n? màu h?ng chim</div>
                    <div class="product-price">2,499,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Áo vest n? màu xám" class="product-image">
                    <div class="product-name">Áo vest n? màu xám</div>
                    <div class="product-price">1,599,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Áo vest n? màu h?ng" class="product-image">
                    <div class="product-name">Áo vest n? màu h?ng</div>
                    <div class="product-price">1,249,000?</div>
                </div>

                <!-- Row 3 -->
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Áo vest n? xanh d??ng" class="product-image">
                    <div class="product-name">Áo vest n? màu xanh d??ng</div>
                    <div class="product-price">2,169,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Ví VIENNE màu Tím" class="product-image">
                    <div class="product-name">Ví VIENNE màu Tím</div>
                    <div class="product-price">190,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Ví màu xanh nh?t" class="product-image">
                    <div class="product-name">Ví màu xanh nh?t</div>
                    <div class="product-price">1,899,000?</div>
                </div>

                <!-- Row 4 -->
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Ví màu xanh lá cây" class="product-image">
                    <div class="product-name">Ví màu xanh lá cây</div>
                    <div class="product-price">1,849,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Túi xách n? màu xanh d??ng" class="product-image">
                    <div class="product-name">Túi xách n? màu xanh d??ng</div>
                    <div class="product-price">1,899,000?</div>
                </div>
                
                <div class="product-card">
                    <img src="/api/placeholder/80/80" alt="Giày n? màu ?en h? m?i" class="product-image">
                    <div class="product-name">Giày n? màu ?en h? m?i</div>
                    <div class="product-price">1,980,000?</div>
                </div>
            </div>

            <div class="pagination">
                <button class="page-btn">&lt;</button>
                <button class="page-btn active">2/2</button>
                <button class="page-btn">&gt;</button>
            </div>

            <div style="padding: 15px;">
                <button class="payment-button">THANH TOÁN</button>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <div style="display: flex;">
            <button style="border: none; background: none; margin-right: 20px; cursor: pointer; display: flex; align-items: center;">
                <i class="fas fa-bolt" style="margin-right: 5px; color: #f39c12;"></i> Bán nhanh
            </button>
            <button style="border: none; background: none; margin-right: 20px; cursor: pointer; display: flex; align-items: center; color: #27ae60; font-weight: bold;">
                <i class="fas fa-circle" style="margin-right: 5px; color: #27ae60;"></i> Bán th??ng
            </button>
            <button style="border: none; background: none; cursor: pointer; display: flex; align-items: center;">
                <i class="fas fa-truck" style="margin-right: 5px; color: #e67e22;"></i> Bán giao hàng
            </button>
        </div>
        <div>
            <button style="border: none; background: none; cursor: pointer; display: flex; align-items: center;">
                <i class="fas fa-phone" style="margin-right: 5px; color: #e74c3c;"></i> 1900 6522
            </button>
            <button style="border: none; background: none; margin-left: 20px; cursor: pointer;">
                <i class="fas fa-cog" style="color: #7f8c8d;"></i>
            </button>
            <button style="border: none; background: none; margin-left: 20px; cursor: pointer;">
                <i class="fas fa-file-alt" style="color: #7f8c8d;"></i>
            </button>
        </div>
    </div>

    <script>
        // Add interactive functionality
        document.addEventListener('DOMContentLoaded', function() {
            // Quantity buttons
            const minusBtns = document.querySelectorAll('.quantity-btn:first-child');
            const plusBtns = document.querySelectorAll('.quantity-btn:last-child');
            const quantityInputs = document.querySelectorAll('.quantity-input');
            
            minusBtns.forEach((btn, index) => {
                btn.addEventListener('click', function() {
                    if (parseInt(quantityInputs[index].value) > 1) {
                        quantityInputs[index].value = parseInt(quantityInputs[index].value) - 1;
                    }
                });
            });
            
            plusBtns.forEach((btn, index) => {
                btn.addEventListener('click', function() {
                    quantityInputs[index].value = parseInt(quantityInputs[index].value) + 1;
                });
            });
            
            // Product cards
            const productCards = document.querySelectorAll('.product-card');
            productCards.forEach(card => {
                card.addEventListener('click', function() {
                    // Flash effect
                    this.style.backgroundColor = '#e6f0fa';
                    setTimeout(() => {
                        this.style.backgroundColor = '';
                    }, 200);
                });
            });
            
            // Delete buttons
            const deleteButtons = document.querySelectorAll('.item-delete');
            deleteButtons.forEach(btn => {
                btn.addEventListener('click', function() {
                    const item = this.closest('.item');
                    item.style.opacity = '0.5';
                    setTimeout(() => {
                        item.style.display = 'none';
                    }, 300);
                });
            });
        });
    </script>
</body>
</html>