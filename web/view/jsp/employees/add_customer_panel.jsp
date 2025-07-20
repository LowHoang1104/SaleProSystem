<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-customer-header">
    <h3>Thêm khách hàng</h3>
    <button type="button" class="close-btn">
        <i class="fas fa-times"></i>
    </button>
</div>

<div class="add-customer-content">
    <form id="addCustomerForm">
        <div class="form-sections">
            <!-- Left Section -->
            <div class="form-section left">
                <div class="form-group">
                    <label for="customerFullName">Họ và tên <span class="required">*</span></label>
                    <input type="text" id="customerFullName" name="fullName" 
                           placeholder="Nhập họ và tên khách hàng" maxlength="100" required>
                </div>
                
                <div class="form-group">
                    <label for="customerPhone">Số điện thoại <span class="required">*</span></label>
                    <input type="tel" id="customerPhone" name="phone" 
                           placeholder="Nhập số điện thoại" maxlength="15" required>
                </div>
                
                <div class="form-group">
                    <label for="customerEmail">Email <span class="required">*</span></label>
                    <input type="email" id="customerEmail" name="email" 
                           placeholder="Nhập địa chỉ email" maxlength="100" required>
                </div>
                
                <div class="form-group">
                    <label for="customerAddress">Địa chỉ</label>
                    <input type="text" id="customerAddress" name="address" 
                           placeholder="Nhập địa chỉ khách hàng" maxlength="255">
                </div>
            </div>
            
            <!-- Right Section -->
            <div class="form-section right">
                <div class="form-group">
                    <label for="customerBirthDate">Ngày sinh <span class="required">*</span></label>
                    <input type="date" id="customerBirthDate" name="birthDate" required>
                </div>
                
                <div class="form-group">
                    <label>Giới tính <span class="required">*</span></label>
                    <div class="gender-options">
                        <label class="gender-option">
                            <input type="radio" name="gender" value="Male" checked required>
                            <span>Nam</span>
                        </label>
                        <label class="gender-option">
                            <input type="radio" name="gender" value="Female" required>
                            <span>Nữ</span>
                        </label>
                        <label class="gender-option">
                            <input type="radio" name="gender" value="Other" required>
                            <span>Khác</span>
                        </label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="customerDescription">Ghi chú</label>
                    <textarea id="customerDescription" name="description" rows="4" 
                              placeholder="Nhập ghi chú về khách hàng" maxlength="500"></textarea>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="add-customer-footer">
    <button type="button" class="btn btn-cancel">
        Bỏ qua
    </button>
    <button type="button" class="btn btn-save">
        Lưu
    </button>
</div>

<style>
/* Basic form styling */
.add-customer-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    border-bottom: 1px solid #e5e7eb;
    background-color: #f9fafb;
}

.add-customer-header h3 {
    margin: 0;
    color: #1f2937;
    font-size: 18px;
    font-weight: 600;
}

.branch-select {
    display: flex;
    align-items: center;
    gap: 8px;
}

.branch-select label {
    font-size: 14px;
    color: #6b7280;
    white-space: nowrap;
}

.branch-select select {
    padding: 6px 12px;
    border: 1px solid #d1d5db;
    border-radius: 4px;
    font-size: 14px;
}

.close-btn {
    background: none;
    border: none;
    font-size: 18px;
    color: #6b7280;
    cursor: pointer;
    padding: 4px;
    border-radius: 4px;
}

.close-btn:hover {
    background-color: #f3f4f6;
    color: #374151;
}

.add-customer-content {
    padding: 20px;
}

.form-sections {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 30px;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 6px;
    font-weight: 500;
    color: #374151;
    font-size: 14px;
}

.required {
    color: #ef4444;
    font-weight: bold;
}

.form-group input,
.form-group textarea,
.form-group select {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    transition: border-color 0.2s, box-shadow 0.2s;
}

.form-group input:focus,
.form-group textarea:focus,
.form-group select:focus {
    outline: none;
    border-color: #3b82f6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.gender-options {
    display: flex;
    gap: 15px;
    padding: 8px;
}

.gender-option {
    display: flex;
    align-items: center;
    gap: 6px;
    cursor: pointer;
    font-size: 14px;
}

.gender-option input[type="radio"] {
    width: auto;
    margin: 0;
}

.add-customer-footer {
    padding: 20px;
    border-top: 1px solid #e5e7eb;
    background-color: #f9fafb;
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;
}

.btn-cancel {
    background-color: #f3f4f6;
    color: #6b7280;
}

.btn-cancel:hover {
    background-color: #e5e7eb;
}

.btn-save {
    background-color: #3b82f6;
    color: white;
}

.btn-save:hover {
    background-color: #2563eb;
}

.btn-save:disabled {
    background-color: #9ca3af;
    cursor: not-allowed;
}

/* Field validation states */
.error {
    border-color: #ef4444 !important;
    box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1) !important;
}

.success {
    border-color: #10b981 !important;
    box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1) !important;
}

.error-message {
    color: #ef4444;
    font-size: 12px;
    margin-top: 4px;
    display: block;
    font-weight: 500;
}

.gender-options.error {
    border: 1px solid #ef4444;
    border-radius: 4px;
    background-color: rgba(239, 68, 68, 0.05);
}

.gender-options.success {
    border: 1px solid #10b981;
    border-radius: 4px;
    background-color: rgba(16, 185, 129, 0.05);
}

/* Responsive design */
@media (max-width: 768px) {
    .form-sections {
        grid-template-columns: 1fr;
        gap: 0;
    }
    
    .add-customer-header {
        flex-direction: column;
        gap: 15px;
        align-items: flex-start;
    }
    
    .branch-select {
        width: 100%;
        justify-content: space-between;
    }
}
</style>