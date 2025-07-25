<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header">
    <div class="header-left active">
        <a href="index.html" class="logo">
            <img src="<%=path%>/view/assets/img/logo.png" alt="">
        </a>
        <a href="index.html" class="logo-small">
            <img src="<%=path%>/view/assets/img/logo-small.png" alt="">
        </a>
        <a id="toggle_btn" href="javascript:void(0);">
        </a>
    </div>
    <a id="mobile_btn" class="mobile_btn" href="#sidebar">
        <span class="bar-icon">
            <span></span>
            <span></span>
            <span></span>
        </span>
    </a>

    <ul class="nav user-menu">
        <!-- Thêm nút Bán hàng -->
        <ul class="nav user-menu">
            <li class="nav-item dropdown has-arrow main-drop">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                    <span class="user-img"><img src="src="<%= path %>/${sessionScope.user.getAvatar()}?rand=<%= Math.random()%>""
                                                alt="Ảnh đại diện"
                                                onerror="this.onerror=null; this.src='<%=path%>/view/assets/img/profiles/avator1.jpg';"></span>
                    <span>Bán hàng</span>
                </a>
            </li>
            <!-- Thêm nút gói dịch vụ ngay cạnh avatar -->
            <li class="nav-item">
                <a href="<%=request.getContextPath()%>/subscription/" class="subscription-btn">
                    <i class="fas fa-box"></i>
                    <span>Gói dịch vụ</span>
                </a>
            </li>
            <li class="nav-item dropdown has-arrow main-drop">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                    <span class="user-img"><img src="<%=path%>/view/assets/img/profiles/avator1.jpg" alt="">
                        <span class="status online"></span></span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
                            <span class="user-img"><img src="src="<%= path %>/${sessionScope.user.getAvatar()}?rand=<%= Math.random() %>""
                                                        alt="Ảnh đại diện"
                                                        onerror="this.onerror=null; this.src='<%=path%>/view/assets/img/profiles/avator1.jpg';">
                                <span class="status online"></span></span>
                            <div class="profilesets">
                                <h6><c:if test="${sessionScope.user.getRoleId() eq '1'}">Chào mừng, Admin</c:if></h6>
                                <h6><c:if test="${sessionScope.user.getRoleId() eq '2'}">Chào mừng, ${sessionScope.user.getFullName()}</c:if></h6>
                                </div>
                            </div>
                            <hr class="m-0">
                            <a class="dropdown-item" href="<%=path%>/ProfileController?mode=profile"> <i class="me-2" data-feather="user"></i>Tài khoản của tôi</a>
                        <hr class="m-0">
                        <a class="dropdown-item" href="<%=path%>/ProfileController?mode=profile"> <i class="me-2" data-feather="user"></i>Tài khoản của tôi</a>
                        <a class="dropdown-item" href="generalsettings.html"><i class="me-2" data-feather="settings"></i>Hồ sơ gian hàng</a>
                        <!-- Thêm link bán hàng vào dropdown menu -->
                        <a class="dropdown-item" href="<%=request.getContextPath()%>/CashierServlet"><i class="me-2 fas fa-cash-register"></i>Bán hàng</a>
                        <!-- Thêm link gói dịch vụ vào dropdown menu -->
                        <a class="dropdown-item" href="<%=request.getContextPath()%>/subscription/"><i class="me-2 fas fa-box"></i>Gói dịch vụ</a>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href="<%=path%>/ProfileController?mode=logout"><img src="<%=path%>/view/assets/img/icons/log-out.svg" class="me-2" alt="img">Đăng xuất</a>
                    </div>
                </div>
            </li>
        </ul>

        <div class="dropdown mobile-user-menu">
            <a href="javascript:void(0);" class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="profile.html">My Profile</a>
                <a class="dropdown-item" href="generalsettings.html">Settings</a>
                <!-- Thêm link bán hàng vào mobile menu -->
                <a class="dropdown-item" href="<%=request.getContextPath()%>/CashierServlet">
                    <i class="fas fa-cash-register me-2"></i>Bán hàng
                </a>
                <!-- Thêm link gói dịch vụ vào mobile menu -->
                <a class="dropdown-item" href="<%=request.getContextPath()%>/subscription/">
                    <i class="fas fa-box me-2"></i>Gói dịch vụ
                </a>
                <a class="dropdown-item" href="signin.html">Logout</a>
            </div>
        </div>
</div>

<style>
    /* CSS cho nút bán hàng */
    .nav.user-menu .cashier-btn {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 8px 16px;
        background: linear-gradient(135deg, #ff6b35, #f7931e);
        color: white;
        text-decoration: none;
        border-radius: 20px;
        font-size: 13px;
        font-weight: 500;
        transition: all 0.3s ease;
        box-shadow: 0 2px 6px rgba(255, 107, 53, 0.2);
        margin-right: 10px;
    }

    .nav.user-menu .cashier-btn:hover {
        background: linear-gradient(135deg, #e55a2b, #d63384);
        transform: translateY(-1px);
        box-shadow: 0 3px 10px rgba(255, 107, 53, 0.3);
        color: white;
        text-decoration: none;
    }

    .nav.user-menu .cashier-btn i {
        font-size: 14px;
    }

    .nav.user-menu .cashier-btn span {
        font-weight: 500;
    }

    /* CSS cho nút gói dịch vụ ngay cạnh avatar */
    .nav.user-menu .subscription-btn {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 8px 16px;
        background: linear-gradient(135deg, #28a745, #20c997);
        color: white;
        text-decoration: none;
        border-radius: 20px;
        font-size: 13px;
        font-weight: 500;
        transition: all 0.3s ease;
        box-shadow: 0 2px 6px rgba(40, 167, 69, 0.2);
        margin-right: 10px;
    }

    .nav.user-menu .subscription-btn:hover {
        background: linear-gradient(135deg, #218838, #1e7e34);
        transform: translateY(-1px);
        box-shadow: 0 3px 10px rgba(40, 167, 69, 0.3);
        color: white;
        text-decoration: none;
    }

    .nav.user-menu .subscription-btn i {
        font-size: 14px;
    }

    .nav.user-menu .subscription-btn span {
        font-weight: 500;
    }

    /* Responsive cho mobile */
    @media (max-width: 768px) {
        .nav.user-menu .subscription-btn span,
        .nav.user-menu .cashier-btn span {
            display: none; /* Chỉ hiển thị icon trên mobile */
        }

        .nav.user-menu .subscription-btn,
        .nav.user-menu .cashier-btn {
            padding: 8px;
            min-width: 36px;
            justify-content: center;
        }
    }

    /* Đảm bảo alignment với user menu */
    .nav.user-menu {
        display: flex;
        align-items: center;
    }

    .nav.user-menu .nav-item {
        display: flex;
        align-items: center;
    }

    /* Style cho dropdown items */
    .dropdown-item i.fas.fa-cash-register {
        color: #ff6b35;
    }

    .dropdown-item i.fas.fa-box {
        color: #28a745;
    }

    .dropdown-item:hover i.fas.fa-cash-register,
    .dropdown-item:hover i.fas.fa-box {
        color: white;
    }
</style>

