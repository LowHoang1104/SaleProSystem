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
        <li class="nav-item dropdown has-arrow main-drop">
            <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                <span class="user-img"><img src="src="<%= path %>/${sessionScope.user.getAvatar()}?rand=<%= Math.random()%>""
                                                    alt="Ảnh đại diện"
                                                    onerror="this.onerror=null; this.src='<%=path%>/view/assets/img/profiles/avator1.jpg';">
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
            <a class="dropdown-item" href="signin.html">Logout</a>
        </div>
    </div>

</div>