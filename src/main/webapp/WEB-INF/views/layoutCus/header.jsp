<%@ page isELIgnored="true" pageEncoding="UTF-8" import="java.util.*"
         contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>

<header class="bg-dark dk header navbar navbar-fixed-top-xs">
      <div class="navbar-header aside-md">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
          <i class="fa fa-bars"></i>
        </a>
        <a href="<%=request.getContextPath()%>/customer/index" class="navbar-brand" ><img src="<%=request.getContextPath()%>/assets/images/osp.ico" class="m-r-sm">Sàn sim</a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target="#navLogin">
          <i class="fa fa-cog"></i>
        </a>
      </div>
      
      <ul class="nav navbar-nav navbar-right m-n hidden-xs nav-customer" id="navLogin">
        <li class="dropdown hidden-xs">
            <a href="<%=request.getContextPath()%>/" class="dropdown-toggle dker" >Quay trở lại <span style="font-weight: 550">sansim.vn</span></a>
        </li> 
        <sec:authorize access="hasRole('ROLE_USER')">   
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <span class="thumb-sm avatar pull-left">
              <img src="<%=request.getContextPath()%>/assets/images/user.png">
            </span>
            <%=request.getRemoteUser()%>  <b class="caret"></b>
          </a>
          <ul class="dropdown-menu animated fadeInRight">
            <!--<span class="arrow top"></span>-->
            <li>
              <a href="<%=request.getContextPath()%>/customer/profile">Cập nhật thông tin cá nhân</a>
            </li>
            <li>
              <a href="docs.html">Trợ giúp</a>
            </li>
            <li class="divider"></li>
            <li>
                <a href="<c:url value="/j_spring_security_logout" />" >Đăng xuất</a>
            </li>
          </ul>
        </li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">    
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            Tài khoản  <b class="caret"></b>
          </a>
          <ul class="dropdown-menu animated fadeInRight">
            <!--<span class="arrow top"></span>-->
            <li>
              <a href="<%=request.getContextPath()%>/login">Đăng nhập</a>
            </li>
            <li>
              <a href="<%=request.getContextPath()%>/customer/register">Đăng ký tài khoản</a>
            </li>
            <li>
              <a href="docs.html">Trợ giúp</a>
            </li>
          </ul>
        </li>
        </sec:authorize>
        
      </ul>      
    </header>
