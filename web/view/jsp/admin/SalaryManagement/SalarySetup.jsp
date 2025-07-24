<%-- 
    Document   : List_work_schedule
    Created on : Jun 8, 2025, 10:49:26 PM
    Author     : Thinhnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, user-scalable=0"
            />
        <meta name="description" content="POS - Bootstrap Admin Template" />
        <meta
            name="keywords"
            content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects"
            />
        <meta name="author" content="Dreamguys - Bootstrap Admin Template" />
        <meta name="robots" content="noindex, nofollow" />
        <title>Thiết lập lương</title>

        <!-- Favicon -->
        <link
            rel="shortcut icon"
            type="image/x-icon"
            href="view/assets/img/favicon.jpg"
            />

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="view/assets/css/bootstrap.min.css" />

        <!-- animation CSS -->
        <link rel="stylesheet" href="view/assets/css/animate.css" />

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="view/assets/plugins/select2/css/select2.min.css" />

        <!-- Datepicker CSS -->
        <link rel="stylesheet" href="view/assets/css/bootstrap-datetimepicker.min.css" />

        <!-- Fontawesome CSS -->
        <link
            rel="stylesheet"
            href="view/assets/plugins/fontawesome/css/fontawesome.min.css"
            />
        <link rel="stylesheet" href="view/assets/plugins/fontawesome/css/all.min.css" />

        <!-- Main CSS -->
        <link rel="stylesheet" href="view/assets/css/style.css" />

        <style>
            /* Cập nhật CSS cho page-header */
            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                padding: 0;
                gap: 20px; /* Thêm khoảng cách giữa title và filter */
            }

            .page-title h4 {
                margin: 0;
                font-size: 24px;
                font-weight: 600;
                color: #333;
            }

            .header-filter {
                display: flex;
                align-items: center;
                gap: 15px;
                flex: 1; /* Cho phép filter chiếm không gian còn lại */
                max-width: 400px; /* Giới hạn chiều rộng tối đa */
            }

            .filter-group {
                display: flex;
                align-items: center;
                gap: 10px;
                width: 100%; /* Chiếm toàn bộ không gian của header-filter */
            }

            .filter-label {
                font-weight: 600;
                color: #495057;
                font-size: 14px;
                white-space: nowrap;
            }

            .filter-select {
                padding: 8px 12px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                font-size: 14px;
                background: white;
                cursor: pointer;
                transition: border-color 0.3s ease;
                min-width: 250px; /* Tăng chiều rộng tối thiểu */
                width: 100%; /* Chiếm toàn bộ không gian còn lại */
            }

            .filter-select:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
            }

            /* Responsive cho mobile */
            @media (max-width: 768px) {
                .page-header {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 15px;
                }

                .header-filter {
                    width: 100%;
                    max-width: none;
                }

                .filter-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 5px;
                }

                .filter-select {
                    width: 100%;
                    min-width: auto;
                }
            }

            .results-info {
                background: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-bottom: 20px;
                font-size: 14px;
                color: #6c757d;
            }

            .employee-table {
                background: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                overflow-x: auto;
                overflow-y: hidden;
            }

            .table-header {
                background: #f8f9fa;
                padding: 12px 16px;
                border-bottom: 1px solid #e9ecef;
                display: grid;
                grid-template-columns: 200px 150px 150px 170px 150px 150px;
                gap: 16px;
                font-weight: 600;
                color: #495057;
            }

            .table-row {
                padding: 16px;
                border-bottom: 1px solid #e9ecef;
                display: grid;
                grid-template-columns: 200px 150px 150px 170px 150px 150px;
                gap: 16px;
                align-items: center;
            }

            .table-row:hover {
                background: #f8f9fa;
            }

            .employee-info {
                display: flex;
                flex-direction: column;
            }

            .employee-name {
                font-weight: 600;
                color: #333;
                margin-bottom: 4px;
            }

            .employee-id {
                font-size: 12px;
                color: #6c757d;
            }

            .branch-info {
                display: flex;
                flex-direction: column;
            }

            .branch-name {
                font-weight: 500;
                color: #333;
                margin-bottom: 2px;
            }

            .branch-code {
                font-size: 12px;
                color: #6c757d;
            }

            /* CSS để hiển thị biểu tượng edit khi hover vào salary-info */
            .salary-info {
                position: relative;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .salary-info:hover {
                background: #f8f9fa;
                border-radius: 4px;
                padding: 8px;
                margin: -8px;
            }

            .salary-info .edit-btn {
                position: absolute;
                top: 50%;
                right: 8px;
                transform: translateY(-50%);
                opacity: 0;
                transition: opacity 0.3s ease;
                background: rgba(0, 123, 255, 0.1);
                color: #007bff;
                width: 24px;
                height: 24px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 12px;
            }

            .salary-info:hover .edit-btn {
                opacity: 1;
            }

            .salary-amount {
                font-weight: 600;
                color: #333;
                margin-bottom: 2px;
            }

            .salary-type {
                font-size: 12px;
                color: #6c757d;
            }

            .bonus-info {
                color: #28a745;
                font-weight: 500;
            }

            .add-btn {
                background: #f8f9fa;
                border: 2px dashed #dee2e6;
                color: #6c757d;
                cursor: pointer;
                padding: 8px;
                border-radius: 4px;
                font-size: 20px;
                text-align: center;
                transition: all 0.3s ease;
            }

            .add-btn:hover {
                background: #e9ecef;
                border-color: #adb5bd;
            }

            .modal {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                display: none;
                justify-content: center;
                align-items: center;
                z-index: 9999;
            }

            .modal-content {
                background: white;
                margin: 5% auto;
                padding: 0;
                border-radius: 8px;
                width: 90%;
                max-width: 800px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            }

            .modal-header {
                padding: 20px;
                border-bottom: 1px solid #e9ecef;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .modal-title {
                font-size: 20px;
                font-weight: 600;
                color: #333;
            }

            .close {
                background: none;
                border: none;
                font-size: 24px;
                cursor: pointer;
                color: #6c757d;
            }

            .modal-body {
                padding: 20px;
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-label {
                display: block;
                margin-bottom: 8px;
                font-weight: 600;
                color: #333;
            }

            .form-control {
                width: 100%;
                padding: 10px 12px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                font-size: 14px;
                transition: border-color 0.3s ease;
            }

            .form-control:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
            }

            .form-row {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr;
                gap: 16px;
            }

            .bonus-table {
                margin-top: 20px;
            }

            .bonus-header {
                background: #f8f9fa;
                padding: 12px;
                border-radius: 4px;
                display: grid;
                grid-template-columns: 150px 200px 1fr 60px;
                gap: 12px;
                font-weight: 600;
                color: #495057;
            }

            .bonus-row {
                padding: 12px;
                border-bottom: 1px solid #e9ecef;
                display: grid;
                grid-template-columns: 150px 200px 1fr 60px;
                gap: 12px;
                align-items: center;
            }

            .deduction-table {
                margin-top: 20px;
            }

            .deduction-header {
                background: #f8f9fa;
                padding: 12px;
                border-radius: 4px;
                display: grid;
                grid-template-columns: 150px 150px 150px 1fr 60px;
                gap: 12px;
                font-weight: 600;
                color: #495057;
            }

            .deduction-row {
                padding: 12px;
                border-bottom: 1px solid #e9ecef;
                display: grid;
                grid-template-columns: 150px 150px 150px 1fr 60px;
                gap: 12px;
                align-items: center;
            }

            .modal-footer {
                padding: 20px;
                border-top: 1px solid #e9ecef;
                display: flex;
                justify-content: flex-end;
                gap: 12px;
            }

            .btn {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 500;
                transition: all 0.3s ease;
            }

            .btn-primary {
                background: #28a745;
                color: white;
            }

            .btn-primary:hover {
                background: #218838;
            }

            .btn-secondary {
                background: #6c757d;
                color: white;
            }

            .btn-secondary:hover {
                background: #5a6268;
            }

            .btn-outline {
                background: white;
                color: #6c757d;
                border: 1px solid #ced4da;
            }

            .btn-outline:hover {
                background: #f8f9fa;
            }

            .add-link {
                color: #007bff;
                text-decoration: none;
                font-weight: 500;
                cursor: pointer;
            }

            .add-link:hover {
                text-decoration: underline;
            }

            .delete-btn {
                background: none;
                border: none;
                color: #dc3545;
                cursor: pointer;
                font-size: 18px;
            }

            .delete-btn:hover {
                color: #c82333;
            }

            .employee-id-display {
                font-size: 14px;
                color: #6c757d;
                margin-bottom: 10px;
            }

            @media (max-width: 768px) {
                .page-header {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 15px;
                }

                .header-filter {
                    width: 100%;
                }

                .filter-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 5px;
                }

                .filter-select {
                    width: 100%;
                    min-width: auto;
                }

                .table-header,
                .table-row {
                    grid-template-columns: 1fr;
                    gap: 8px;
                }

                .table-header > div,
                .table-row > div {
                    padding: 8px;
                    border-bottom: 1px solid #e9ecef;
                }
            }

            /* CSS chung cho cả overtimeModal và deductionModal */
            #overtimeModal .modal-content,
            #deductionModal .modal-content {
                width: 60%;
                max-width: 600px;
                margin: 10% auto;
            }

            #overtimeModal .overtime-table,
            #deductionModal .overtime-table {
                margin-top: 15px;
            }

            #overtimeModal .table-header,
            #deductionModal .table-header,
            #overtimeModal .table-row,
            #deductionModal .table-row {
                display: grid;
                grid-template-columns: 1fr 140px;
                gap: 15px;
                padding: 10px;
                align-items: center;
            }

            #overtimeModal .table-header,
            #deductionModal .table-header {
                background: #f8f9fa;
                border-radius: 4px;
                font-weight: 600;
                color: #495057;
            }

            #overtimeModal .table-row,
            #deductionModal .table-row {
                border-bottom: 1px solid #e9ecef;
            }

            #overtimeModal .day-label,
            #deductionModal .day-label {
                font-weight: 500;
                color: #333;
            }

            #overtimeModal .overtime-input,
            #deductionModal .overtime-input {
                position: relative;
                display: flex;
                align-items: center;
            }

            #overtimeModal .overtime-input input,
            #deductionModal .overtime-input input {
                width: 80px;
                padding: 6px 8px;
                font-size: 14px;
            }

            #overtimeModal .overtime-input .percent-symbol,
            #deductionModal .overtime-input .percent-symbol {
                margin-left: 8px;
                font-weight: 500;
                color: #6c757d;
            }

            /* Responsive */
            @media (max-width: 768px) {
                #overtimeModal .modal-content,
                #deductionModal .modal-content {
                    width: 90%;
                    max-width: none;
                    margin: 5% auto;
                }

                #overtimeModal .table-header,
                #deductionModal .table-header,
                #overtimeModal .table-row,
                #deductionModal .table-row {
                    grid-template-columns: 1fr 120px;
                    gap: 10px;
                }

                #overtimeModal .overtime-input input,
                #deductionModal .overtime-input input {
                    width: 70px;
                }
            }

            @media (max-width: 480px) {
                #overtimeModal .modal-content,
                #deductionModal .modal-content {
                    width: 95%;
                    margin: 2% auto;
                }

                #overtimeModal .table-header,
                #deductionModal .table-header,
                #overtimeModal .table-row,
                #deductionModal .table-row {
                    grid-template-columns: 1fr;
                    gap: 8px;
                    text-align: left;
                }

                #overtimeModal .overtime-input,
                #deductionModal .overtime-input {
                    justify-content: flex-start;
                }

                #overtimeModal .overtime-input input,
                #deductionModal .overtime-input input {
                    width: 100px;
                }
            }
        </style>

    </head>
    <body>
        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <%@include file="../HeadSideBar/header.jsp" %>
            <%@include file="../HeadSideBar/sidebar.jsp" %>

            <!-- Page Wrapper -->
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="page-title">
                            <h4>Thiết lập lương nhân viên</h4>
                        </div>
                        <!-- Lọc theo chi nhánh-->
                        <div class="header-filter">
                            <div class="filter-group">
                                <select class="filter-select" onchange="window.location.href = 'ListSalaryServlet?storeId=' + this.value">
                                    <option value="">Tất cả chi nhánh</option>
                                    <c:forEach var="store" items="${stores}">
                                        <option ${storeId==store.getStoreID()?'selected':''} value="${store.getStoreID()}">${store.getStoreName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="employee-table">
                        <div class="table-header">
                            <div>Nhân viên</div>
                            <div>Lương chính</div>
                            <div>Làm thêm</div>
                            <!--                                <div>Thưởng</div>-->
                            <div>Hoa hồng</div>
                            <div>Phụ cấp</div>
                            <div>Giảm trừ</div>
                        </div>
                        <c:forEach var="employee" items="${employees}">
                            <div class="table-row">
                                <div class="employee-info">
                                    <div class="employee-name">${employee.getFullName()}</div>
                                    <div class="employee-id">${employee.getCode()}</div>
                                </div>

                                <c:set var="salary" value="${salaryMap[employee.employeeID]}" />
                                <fmt:setLocale value="en_US" />
                                <!-- Lương chính -->
                                <c:choose>
                                    <c:when test="${salary!=null && salary.getSalaryType() != null}">
                                        <div class="salary-info" onclick="openSalaryModal('${employee.fullName}', '${employee.employeeID}', '${salary.getSalaryType()}', '${salary.salaryRate}')">
                                            <div class="salary-amount">
                                                <fmt:formatNumber value="${salary.salaryRate}" type="number" groupingUsed="true" /> /
                                                <c:choose>
                                                    <c:when test="${salary.salaryType == 'Hourly'}">giờ</c:when>
                                                    <c:when test="${salary.salaryType == 'Daily'}">ngày</c:when>
                                                    <c:when test="${salary.salaryType == 'Shiftly'}">ca</c:when>
                                                    <c:when test="${salary.salaryType == 'Fixed'}">tháng</c:when>
                                                    <c:otherwise>--</c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="salary-type">${salary.getSalaryTypeVietsub()}</div>
                                            <div class="edit-btn">
                                                <i class="fas fa-edit"></i>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="add-btn" onclick="openSalaryModal('${employee.fullName}', '${employee.employeeID}')">+</div>
                                    </c:otherwise>
                                </c:choose>
                                <!-- Làm thêm -->
                                <c:choose>
                                    <c:when test="${salary!=null && salary.getOvertimeRate() != 0}">
                                        <div class="salary-info" onclick="openOvertimeModal('${employee.fullName}', '${employee.employeeID}', '${salary.overtimeRate}', '${salary.overtimeSaturdayRate}', '${salary.overtimeSundayRate}', '${salary.overtimeHolidayRate}')">
                                            <div class="salary-amount">
                                                <fmt:formatNumber value="${salary.getOvertimeRate()}" type="number" groupingUsed="true" /> % giờ thường
                                            </div>
                                            <div class="edit-btn" >
                                                <i class="fas fa-edit"></i>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="add-btn" onclick="openOvertimeModal('${employee.fullName}', '${employee.employeeID}')">+</div>
                                    </c:otherwise>
                                </c:choose>
                                <!--                                    <div class="bonus-info">2 mức thưởng</div>-->
                                <!--Hoa hồng-->
                                <c:choose>
                                    <c:when test="${salary!=null && salary.comissionRate != 0}">
                                        <div class="salary-info" onclick="openComissionModal('${employee.fullName}', '${employee.employeeID}', '${salary.comissionRate}')">
                                            <div class="salary-amount">
                                                <fmt:formatNumber value="${salary.comissionRate}" type="number" groupingUsed="true" /> % doanh thu
                                            </div>
                                            <div class="edit-btn" >
                                                <i class="fas fa-edit"></i>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="add-btn" onclick="openComissionModal('${employee.fullName}', '${employee.employeeID}')">+</div>
                                    </c:otherwise>
                                </c:choose>
                                <!-- Phụ cấp -->
                                <c:choose>
                                    <c:when test="${salary!=null && salary.allowanceRate != 0}">
                                        <div class="salary-info"   onclick="openAllowanceModal('${employee.fullName}', '${employee.employeeID}', '${salary.allowanceRate}')">
                                            <div class="salary-amount">
                                                <fmt:formatNumber value="${salary.allowanceRate}" type="number" groupingUsed="true" /> /tháng
                                            </div>
                                            <div class="edit-btn" >
                                                <i class="fas fa-edit"></i>
                                            </div>

                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="add-btn" onclick="openAllowanceModal('${employee.fullName}', '${employee.employeeID}')">+</div>
                                    </c:otherwise>
                                </c:choose>
                                <!-- Giảm trừ -->
                                <c:choose>
                                    <c:when test="${salary!=null && salary.penaltyLateArrival != 0}">
                                        <div class="salary-info" onclick="openDeductionModal('${employee.fullName}', '${employee.employeeID}', '${salary.penaltyLateArrival}', '${salary.penaltyEarlyLeave}', '${salary.penaltyOthers}')">
                                            <div class="salary-amount">
                                                <fmt:formatNumber value="${salary.penaltyLateArrival}" type="number" groupingUsed="true" /> Đi muộn
                                            </div>
                                            <div class="edit-btn">
                                                <i class="fas fa-edit"></i>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="add-btn" onclick="openDeductionModal('${employee.fullName}', '${employee.employeeID}')">+</div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </c:forEach>

                    </div>

                </div>
            </div>
            <!-- /Page Wrapper -->
        </div>

        <!-- Modal Lương chính -->
        <div id="salaryModal" class="modal" data-employee-id="">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Lương chính</h3>
                    <button class="close" onclick="closeModal('salaryModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="employee-id-display">Nhân viên: <span id="salaryEmployeeName"></span></div>
                    <div class="form-group">
                        <label class="form-label">Loại lương</label>
                        <select class="form-control" id="salaryType">
                            <option value="">Chọn loại lương</option>
                            <option  value="Hourly">Theo giờ làm việc</option>
                            <option value="Shiftly">Theo ca làm việc</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Mức lương</label>
                        <input type="text" class="form-control" id="salaryAmount" placeholder="Nhập mức lương" 
                               oninput="formatSalary(this)" onkeypress="return isNumberKey(event)">

                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal('salaryModal')">Bỏ qua</button>
                    <button class="btn btn-outline">Lưu và áp dụng cho toàn bộ nhân viên</button>
                    <button class="btn btn-primary" onclick="saveSalary()">Lưu</button>
                </div>
            </div>
        </div>
        <!-- Modal Lương làm thêm giờ -->
        <div id="overtimeModal" class="modal" data-employee-id="">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Lương làm thêm giờ</h3>
                    <button class="close" onclick="closeModal('overtimeModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="employee-id-display">Nhân viên: <span id="overtimeEmployeeName"></span></div>
                    <div class="overtime-table">
                        <div class="table-header">
                            <div>Ngày làm việc</div>
                            <div>Hệ số lương trên giờ</div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Ngày thường</div>
                            <div class="overtime-input">
                                <input id="overtimeRate" type="text" class="form-control"
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">%</span>
                            </div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Thứ 7</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control"
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">%</span>
                            </div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Chủ nhật</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control" 
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">%</span>
                            </div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Ngày lễ tết</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control" 
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">%</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    
                    <button class="btn btn-outline" onclick="closeModal('overtimeModal')">Bỏ qua</button>
                    <button class="btn btn-primary" onclick="saveOvertime()">Lưu</button>
                </div>
            </div>
        </div>
        <!-- Modal Phụ cấp -->
        <div id="allowanceModal" class="modal" style="display: none;" data-employee-id="">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Phụ cấp</h3>
                    <button class="close" onclick="closeModal('allowanceModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="employee-id-display">Nhân viên: <span id="allowanceEmployeeName"></span></div>
                    <div class="form-group">
                        <label class="form-label">Phụ cấp hàng tháng cố định</label>
                        <input type="text" class="form-control" id="allowanceAmount" placeholder="Nhập số tiền phụ cấp" 
                               oninput="formatSalary(this)" onkeypress="return isNumberKey(event)">

                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal('allowanceModal')">Bỏ qua</button>
                    <button class="btn btn-secondary" onclick="applyAllowanceToAll()">Lưu và áp dụng cho toàn bộ nhân viên</button>
                    <button class="btn btn-primary" onclick="saveAllowance()">Lưu</button>
                </div>
            </div>
        </div>
        <!-- Modal hoa hồng -->
        <div id="comissionModal" class="modal" style="display: none;" data-employee-id="">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Hoa hồng</h3>
                    <button class="close" onclick="closeModal('comissionModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="employee-id-display">Nhân viên: <span id="comissionEmployeeName"></span></div>
                    <div class="form-group">
                        <label class="form-label">Hưởng hoa hồng theo phần trăm doanh thu</label>
                        <input type="text" class="form-control" id="comissionAmount" placeholder="Nhập phần trăm " 
                               onkeypress="return isNumberKey(event)">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal('commissionModal')">Bỏ qua</button>
                    <button class="btn btn-secondary" onclick="applyAllowanceToAll()">Lưu và áp dụng cho toàn bộ nhân viên</button>
                    <button class="btn btn-primary" onclick="saveCommissionRate()">Lưu</button>
                </div>
            </div>
        </div>
        <!-- Modal Giảm trừ -->
        <div id="deductionModal" class="modal" data-employee-id="">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Giảm trừ</h3>
                    <button class="close" onclick="closeModal('deductionModal')">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="employee-id-display">Nhân viên: <span id="deductionEmployeeName"></span></div>
                    <div class="overtime-table">
                        <div class="table-header">
                            <div>Loại phạt</div>
                            <div>Số tiền \ lần</div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Đi muộn</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control"
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">VND</span>
                            </div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Về sớm</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control"
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">VND</span>
                            </div>
                        </div>
                        <div class="table-row">
                            <div class="day-label">Các loại khác</div>
                            <div class="overtime-input">
                                <input type="text" class="form-control" 
                                       onkeypress="return isNumberKey(event)">
                                <span class="percent-symbol">VND</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline" onclick="closeModal('deductionModal')">Bỏ qua</button>
                    <button class="btn btn-outline">Lưu và áp dụng cho toàn bộ nhân viên</button>
                    <button class="btn btn-primary" onclick="saveDeduction()">Lưu</button>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- jQuery -->
        <script src="view/assets/js/jquery-3.6.0.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="view/assets/js/feather.min.js"></script>

        <!-- Slimscroll JS -->
        <script src="view/assets/js/jquery.slimscroll.min.js"></script>

        <!-- Select2 JS -->
        <script src="view/assets/plugins/select2/js/select2.min.js"></script>

        <!-- Datepicker Core JS -->
        <script src="view/assets/js/moment.min.js"></script>
        <script src="view/assets/js/bootstrap-datetimepicker.min.js"></script>

        <!-- Bootstrap Core JS -->
        <script src="view/assets/js/bootstrap.bundle.min.js"></script>

        <!-- Custom JS -->
        <script src="view/assets/js/script.js"></script>

        <!-- jQuery -->
        <script src="assets/js/jquery-3.6.0.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="assets/js/feather.min.js"></script>

        <!-- Slimscroll JS -->
        <script src="assets/js/jquery.slimscroll.min.js"></script>

        <!-- Datatable JS -->
        <script src="assets/js/jquery.dataTables.min.js"></script>
        <script src="assets/js/dataTables.bootstrap4.min.js"></script>

        <!-- Bootstrap Core JS -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>

        <!-- Select2 JS -->
        <script src="assets/plugins/select2/js/select2.min.js"></script>

        <!-- Datetimepicker JS -->
        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>

        <!-- Custom JS -->
        <script src="assets/js/script.js"></script>
        <script>
                        // Chỉ cho phép phím số
                        function isNumberKey(evt) {
                            const charCode = evt.which ? evt.which : evt.keyCode;
                            return charCode >= 48 && charCode <= 57; // chỉ cho nhập 0-9
                        }
                        // Format số với dấu phẩy ngăn cách hàng nghìn
                        function formatSalary(input) {
                            let value = input.value.replace(/,/g, ''); // Bỏ dấu phẩy cũ
                            if (!/^\d+$/.test(value)) {
                                input.value = ''; // Không hợp lệ -> xóa
                                return;
                            }

                            // Format lại với dấu phẩy
                            input.value = Number(value).toLocaleString('en-US');
                        }


                        // Mở modal lương chính
                        function openSalaryModal(name, id, salaryType, salaryRate) {
                            const modal = document.getElementById('salaryModal');
                            document.getElementById('salaryEmployeeName').textContent = name;
                            modal.dataset.employeeId = id;
                            document.getElementById('salaryModal').style.display = 'block';
                            // Load dữ liệu hiện tại nếu có
                            if (salaryType && salaryRate) {
                                document.getElementById('salaryType').value = salaryType;
                                document.getElementById('salaryAmount').value = parseFloat(salaryRate).toLocaleString('en-US');
                            }
                        }
                        // Lưu lương chính
                        function saveSalary() {
                            const modal = document.getElementById('salaryModal');
                            const type = document.getElementById('salaryType').value;
                            const formattedSalary = document.getElementById('salaryAmount').value;
                            const amount = formattedSalary.replace(/,/g, ''); // bỏ dấu phẩy
                            const empId = modal.dataset.employeeId;
                            if (!type || !amount) {
                                alert('Vui lòng nhập đầy đủ thông tin');
                                return;
                            }
                            const value = parseFloat(amount);
                            if (isNaN(value) || value <= 0) {
                                alert('Mức luơng phải lớn hơn 0');
                                return;
                            }

                            fetch('SaveSalaryServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    action: 'saveSalary',
                                    employeeId: empId,
                                    salaryType: type,
                                    salaryAmount: amount
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã lưu lương chính thành công!');
                                            closeModal('salaryModal');
                                            location.reload();
                                        } else {
                                            alert('Thất bại: ' + result);
                                        }
                                    })
                                    .catch(error => {
                                        alert('Lỗi khi lưu dữ liệu: ' + error.message);
                                        console.error('Error:', error);
                                    });
                        }
                        function openOvertimeModal(name, id, overtimeRate, overtimeSaturday, overtimeSunday, overtimeHoliday) {
                            const modal = document.getElementById('overtimeModal');
                            document.getElementById('overtimeEmployeeName').textContent = name;
                            modal.dataset.employeeId = id;

                            // Format và gán lại dữ liệu
                            const overtime = document.querySelectorAll('#overtimeModal .form-control');
                            overtime[0].value = overtimeRate && !isNaN(overtimeRate) ? overtimeRate : '';
                            overtime[1].value = overtimeSaturday && !isNaN(overtimeSaturday) ? overtimeSaturday : '';
                            overtime[2].value = overtimeSunday && !isNaN(overtimeSunday) ? overtimeSunday : '';
                            overtime[3].value = overtimeHoliday && !isNaN(overtimeHoliday) ? overtimeHoliday : '';

                            // Hiển thị modal
                            modal.style.display = 'flex';
                        }
                        function saveOvertime() {
                            const modal = document.getElementById('overtimeModal');
                            const overtime = document.querySelectorAll('#overtimeModal .form-control');
                            const empId = modal.dataset.employeeId;
                            const overtimeRates = {
                                normal: overtime[0].value,
                                saturday: overtime[1].value,
                                sunday: overtime[2].value,
                                festival: overtime[3].value
                            };
                            // Validate dữ liệu
                            for (let key in overtimeRates) {
                                const value = parseFloat(overtimeRates[key]);
                                if (isNaN(value) || value < 100 || value > 500) {
                                    alert('Hệ số lương phải từ 100% đến 500%');
                                    return;
                                }
                            }

                            fetch('SaveSalaryServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    employeeId: empId,
                                    action: 'saveOvertime',
                                    normalRate: overtimeRates.normal,
                                    saturdayRate: overtimeRates.saturday,
                                    sundayRate: overtimeRates.sunday,
                                    holidayRate: overtimeRates.festival
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã lưu cấu hình lương làm thêm giờ thành công!');
                                            closeModal('overtimeModal');
                                            location.reload();
                                        } else {
                                            alert('Thất bại: ' + result);
                                        }
                                    })
                                    .catch(error => {
                                        alert('Lỗi khi lưu dữ liệu: ' + error.message);
                                        console.error('Error:', error);
                                    });
                        }
                        // Đóng modal khi click bên ngoài
                        window.onclick = function (event) {
                            const modal = document.getElementById('overtimeModal');
                            if (event.target === modal) {
                                closeModal('overtimeModal');
                            }
                        };
                        // Mở modal phụ cấp
                        function openAllowanceModal(name, id, allowanceAmount) {
                            const modal = document.getElementById('allowanceModal');
                            modal.dataset.employeeId = id;
                            document.getElementById('allowanceModal').style.display = 'flex';
                            document.getElementById('allowanceEmployeeName').textContent = name;
                            // Load dữ liệu hiện tại nếu có
                            document.getElementById('allowanceAmount').value = allowanceAmount && !isNaN(allowanceAmount) ? parseFloat(allowanceAmount).toLocaleString('en-US') : '';
//                                loadAllowanceData(id);
                        }
                        // Lưu phụ cấp
                        function saveAllowance() {
                            const modal = document.getElementById('allowanceModal');
                            const formattedAllowance = document.getElementById('allowanceAmount').value;
                            const amount = formattedAllowance.replace(/,/g, ''); // bỏ dấu phẩy
                            const empId = modal.dataset.employeeId;
                            if (!amount) {
                                alert('Vui lòng nhập đầy đủ thông tin');
                                return;
                            }


                            fetch('SaveSalaryServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    action: 'saveAllowance',
                                    employeeId: empId,
                                    allowanceAmount: amount
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã lưu thông tin phụ cấp thành công!');
                                            closeModal('allowanceModal');
                                            location.reload();
                                        } else {
                                            alert('Thất bại: ' + result);
                                        }
                                    })
                                    .catch(error => {
                                        alert('Lỗi khi lưu dữ liệu: ' + error.message);
                                        console.error('Error:', error);
                                    });
                        }
                        function openComissionModal(name, id, comissionRate) {
                            const modal = document.getElementById('comissionModal');
                            modal.dataset.employeeId = id;

                            document.getElementById('comissionModal').style.display = 'flex';
                            document.getElementById('comissionEmployeeName').textContent = name;
                            // Load dữ liệu hiện tại nếu có
                            document.getElementById('comissionAmount').value = comissionRate && !isNaN(comissionRate) ? parseFloat(comissionRate).toLocaleString('en-US') : '';
//                                loadAllowanceData(id);
                        }
                        function saveCommissionRate() {
                            const modal = document.getElementById('comissionModal');
                            const amount = document.getElementById('comissionAmount').value;
                            const empId = modal.dataset.employeeId;
                            if (!amount) {
                                alert('Vui lòng nhập đầy đủ thông tin');
                                return;
                            }

                            const value = parseFloat(amount);
                            if (isNaN(value) || value > 100) {
                                alert('Tỷ lệ hoa hồng phải nhỏ 100%');
                                return;
                            }
                            fetch('SaveSalaryServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    action: 'saveCommission',
                                    employeeId: empId,
                                    commissionRate: amount
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã lưu thông tin hoa hồng thành công!');
                                            closeModal('comissionModal');
                                            location.reload();
                                        } else {
                                            alert('Thất bại: ' + result);
                                        }
                                    })
                                    .catch(error => {
                                        alert('Lỗi khi lưu dữ liệu: ' + error.message);
                                        console.error('Error:', error);
                                    });
                        }

                        // Mở modal giảm trừ
                        function openDeductionModal(name, id, penaltyLateArrival, penaltyEarlyLeave, penaltyOthers) {
                            const modal = document.getElementById('deductionModal');
                            document.getElementById('deductionEmployeeName').textContent = name;
                            modal.dataset.employeeId = id;

                            // Format và gán lại dữ liệu
                            const deduction = document.querySelectorAll('#deductionModal .form-control');
                            deduction[0].value = penaltyLateArrival && !isNaN(penaltyLateArrival) ? parseFloat(penaltyLateArrival).toLocaleString('en-US') : '';
                            deduction[1].value = penaltyEarlyLeave && !isNaN(penaltyEarlyLeave) ? parseFloat(penaltyEarlyLeave).toLocaleString('en-US') : '';
                            deduction[2].value = penaltyOthers && !isNaN(penaltyOthers) ? parseFloat(penaltyOthers).toLocaleString('en-US') : '';
                            document.getElementById('deductionModal').style.display = 'block';
                        }
                        // Lưu giảm trừ
                        function saveDeduction() {
                            const modal = document.getElementById('deductionModal');
                            const deduction = document.querySelectorAll('#deductionModal .form-control');
                            const empId = modal.dataset.employeeId;
                            const deductionAmounts = {
                                penaltyLateArrival: deduction[0].value,
                                penaltyEarlyLeave: deduction[1].value,
                                penaltyOthers: deduction[2].value
                            };
                            // Validate dữ liệu
//                            for (let key in deductionAmounts) {
//                                const value = parseFloat(deductionAmounts[key]);
//                                if (isNaN(value)) {
//                                    alert('Tiền phạt phải là số');
//                                    return;
//                                }
//                            }

                            fetch('SaveSalaryServlet', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: new URLSearchParams({
                                    employeeId: empId,
                                    action: 'saveDeductionAmount',
                                    penaltyLateArrival: deductionAmounts.penaltyLateArrival,
                                    penaltyEarlyLeave: deductionAmounts.penaltyEarlyLeave,
                                    penaltyOthers: deductionAmounts.penaltyOthers
                                })
                            })
                                    .then(response => response.text())
                                    .then(result => {
                                        if (result.trim() === 'success') {
                                            alert('Đã lưu cấu hình tiền phạt thành công!');
                                            closeModal('deductionModal');
                                            location.reload();
                                        } else {
                                            alert('Thất bại: ' + result);
                                        }
                                    })
                                    .catch(error => {
                                        alert('Lỗi khi lưu dữ liệu: ' + error.message);
                                        console.error('Error:', error);
                                    });
                        }

                        function closeModal(modalId) {
                            document.getElementById(modalId).style.display = 'none';
                        }

                        // Load dữ liệu làm thêm giờ hiện tại
//                            function loadOvertimeData(employeeId) {
//                                // Giả lập dữ liệu - trong thực tế sẽ load từ server
//                                const defaultRates = {
//                                    normal: 150,
//                                    saturday: 200,
//                                    sunday: 200,
//                                    holiday: 200,
//                                    festival: 300
//                                };
//
//                                const inputs = document.querySelectorAll('#overtimeModal .form-control');
//                                inputs[0].value = defaultRates.normal;
//                                inputs[1].value = defaultRates.saturday;
//                                inputs[2].value = defaultRates.sunday;
//                                inputs[3].value = defaultRates.holiday;
//                                inputs[4].value = defaultRates.festival;
//                            }

                        // Lưu thông tin làm thêm giờ



                        // Áp dụng cho tất cả nhân viên
//                            function applyToAllEmployees() {
//                                const inputs = document.querySelectorAll('#overtimeModal .form-control');
//                                const overtimeRates = {
//                                    normal: inputs[0].value,
//                                    saturday: inputs[1].value,
//                                    sunday: inputs[2].value,
//                                    holiday: inputs[3].value,
//                                    festival: inputs[4].value
//                                };
//
//                                // Validate dữ liệu
//                                for (let key in overtimeRates) {
//                                    if (!overtimeRates[key] || overtimeRates[key] < 100 || overtimeRates[key] > 500) {
//                                        alert('Hệ số lương phải từ 100% đến 500%');
//                                        return;
//                                    }
//                                }
//
//                                if (confirm('Bạn có chắc muốn áp dụng cấu hình này cho tất cả nhân viên?')) {
//                                    // Gửi dữ liệu lên server
//                                    console.log('Applying overtime rates to all employees:', overtimeRates);
//
//                                    // Giả lập thành công
//                                    alert('Đã áp dụng cấu hình lương làm thêm giờ cho tất cả nhân viên!');
//                                    closeModal('overtimeModal');
//                                }
//                            }



                        // Chỉ cho phép nhập số và giới hạn giá trị
//                            document.addEventListener('DOMContentLoaded', function () {
//                                const inputs = document.querySelectorAll('#overtimeModal .form-control');
//                                inputs.forEach(input => {
//                                    input.addEventListener('input', function () {
//                                        let value = parseInt(this.value);
//                                        if (isNaN(value) || value < 100) {
//                                            this.value = 100;
//                                        } else if (value > 500) {
//                                            this.value = 500;
//                                        }
//                                    });
//
//                                    input.addEventListener('blur', function () {
//                                        if (!this.value || this.value < 100) {
//                                            this.value = 100;
//                                        }
//                                    });
//                                });
//                            });

                        function openCommissionModal(name, id) {
                            alert('Chức năng hoa hồng đang được phát triển');
                        }



                        // Đóng modal
                        function closeModal(modalId) {
                            document.getElementById(modalId).style.display = 'none';
                        }

                        // Load dữ liệu phụ cấp hiện tại
                        function loadAllowanceData(employeeId) {
                            // Giả lập dữ liệu - trong thực tế sẽ load từ server
                            // Có thể để trống hoặc load dữ liệu mặc định
                        }

                        // Thêm hàng phụ cấp mới
                        function addAllowanceRow() {
                            const allowanceTable = document.querySelector('.allowance-table');
                            const newRow = document.createElement('div');
                            newRow.className = 'allowance-row';
                            newRow.innerHTML = `
            <div>
                <select class="form-control">
                    <option value="">Chọn loại phụ cấp</option>
                    <option value="transport">Phụ cấp đi lại</option>
                    <option value="meal">Phụ cấp ăn trưa</option>
                    <option value="phone">Phụ cấp điện thoại</option>
                    <option value="housing">Phụ cấp nhà ở</option>
                    <option value="responsibility">Phụ cấp trách nhiệm</option>
                    <option value="hazard">Phụ cấp độc hại</option>
                    <option value="other">Khác</option>
                </select>
            </div>
            <div>
                <select class="form-control">
                    <option value="monthly">Phụ cấp hàng tháng cố định</option>
                    <option value="daily">Phụ cấp hàng ngày</option>
                    <option value="hourly">Phụ cấp theo giờ</option>
                    <option value="percentage">Theo phần trăm lương</option>
                </select>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="Nhập số tiền">
            </div>
            <div>
                <button class="delete-btn" onclick="deleteAllowanceRow(this)">🗑</button>
            </div>
        `;
                            allowanceTable.appendChild(newRow);
                        }

                        // Xóa hàng phụ cấp
                        function deleteAllowanceRow(button) {
                            const row = button.closest('.allowance-row');
                            if (document.querySelectorAll('.allowance-row').length > 1) {
                                row.remove();
                            } else {
                                alert('Phải có ít nhất một phụ cấp');
                            }
                        }



                        // Áp dụng cho tất cả nhân viên
                        function applyAllowanceToAll() {
                            const rows = document.querySelectorAll('.allowance-row');
                            const allowances = [];
                            rows.forEach(row => {
                                const name = row.querySelector('select').value;
                                const type = row.querySelectorAll('select')[1].value;
                                const amount = row.querySelector('input').value;
                                if (name && type && amount) {
                                    allowances.push({
                                        name: name,
                                        type: type,
                                        amount: amount
                                    });
                                }
                            });
                            if (allowances.length === 0) {
                                alert('Vui lòng nhập đầy đủ thông tin phụ cấp');
                                return;
                            }

                            if (confirm('Bạn có chắc muốn áp dụng cấu hình phụ cấp này cho tất cả nhân viên?')) {
                                console.log('Applying allowances to all employees:', allowances);
                                alert('Đã áp dụng cấu hình phụ cấp cho tất cả nhân viên!');
                                closeModal('allowanceModal');
                            }
                        }

                        // Đóng modal khi click bên ngoài
                        window.onclick = function (event) {
                            const modal = document.getElementById('allowanceModal');
                            if (event.target === modal) {
                                closeModal('allowanceModal');
                            }
                        }

                        // Format số tiền
//                            function formatCurrency(input) {
//                                let value = input.value.replace(/,/g, '');
//                                if (!/^\d+$/.test(value)) {
//                                    input.value = input.value.slice(0, -1);
//                                    return;
//                                }
//                                input.value = Number(value).toLocaleString('vi-VN');
//                            }

                        // Thêm event listener cho input số tiền
//                            document.addEventListener('DOMContentLoaded', function () {
//                                const amountInputs = document.querySelectorAll('#allowanceAmount');
//                                amountInputs.forEach(input => {
//                                    input.addEventListener('input', function () {
//                                        formatCurrency(this);
//                                    });
//                                });
//                            });
                        // Đóng modal
                        function closeModal(modalId) {
                            document.getElementById(modalId).style.display = 'none';
                        }



                        // Lưu thưởng
                        function saveBonus() {
                            alert('Đã lưu thông tin thưởng thành công!');
                            closeModal('bonusModal');
                        }



                        // Thêm hàng thưởng mới
                        function addBonusRow() {
                            const bonusTable = document.querySelector('.bonus-table');
                            const newRow = document.createElement('div');
                            newRow.className = 'bonus-row';
                            newRow.innerHTML = `
                    <div>
                        <input type="text" class="form-control" placeholder="Loại hình">
                    </div>
                    <div>
                        <input type="text" class="form-control" placeholder="Từ" style="width: 60px; display: inline-block;">
                        <span style="margin: 0 8px;">-</span>
                        <input type="text" class="form-control" value="0" style="width: 60px; display: inline-block;">
                    </div>
                    <div>
                        <input type="text" class="form-control" placeholder="0% Doanh thu" style="width: 120px;">
                    </div>
                    <div>
                        <button class="delete-btn" onclick="deleteRow(this)">🗑</button>
                    </div>
                `;
                            bonusTable.appendChild(newRow);
                        }

                        // Thêm hàng giảm trừ mới
                        function addDeductionRow() {
                            const deductionTable = document.querySelector('.deduction-table');
                            const newRow = document.createElement('div');
                            newRow.className = 'deduction-row';
                            newRow.innerHTML = `
                    <div>
                        <select class="form-control">
                            <option value="">Chọn loại giảm trừ</option>
                            <option value="insurance">Bảo hiểm</option>
                            <option value="tax">Thuế</option>
                            <option value="advance">Tạm ứng</option>
                            <option value="other">Khác</option>
                        </select>
                    </div>
                    <div>
                        <select class="form-control">
                            <option value="">Cố định</option>
                            <option value="percentage">Theo phần trăm</option>
                        </select>
                    </div>
                    <div>
                        <select class="form-control">
                            <option value="">Chọn loại</option>
                            <option value="late">Theo số lần</option>
                        </select>
                    </div>
                    <div>
                        <input type="text" class="form-control" placeholder="Theo số lần" style="width: 100px;">
                    </div>
                    <div>
                        <input type="number" class="form-control" value="0" style="width: 80px;">
                    </div>
                    <div>
                        <button class="delete-btn" onclick="deleteRow(this)">🗑</button>
                    </div>
                `;
                            deductionTable.appendChild(newRow);
                        }

                        // Xóa hàng
                        function deleteRow(button) {
                            const row = button.closest('.bonus-row, .deduction-row');
                            row.remove();
                        }

                        // Đóng modal khi click bên ngoài
                        window.onclick = function (event) {
                            const modals = document.querySelectorAll('.modal');
                            modals.forEach(modal => {
                                if (event.target === modal) {
                                    modal.style.display = 'none';
                                }
                            });
                        };
        </script>

    </body>
</html>

