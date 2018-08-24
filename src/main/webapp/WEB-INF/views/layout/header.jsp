<%@ page import="com.osp.model.Customer" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    Customer manager = (Customer) request.getSession().getAttribute("userShop");
%>
<div class="header">
    <div class="container-sim">
        <div class="row" style="width: 100% ; overflow: hidden">
            <div class="col-xs-12 col-sm-6 col-md-6 text-left"  > <a href="http://sansim.vn"><img src="<%=request.getContextPath()%>/assets/static/images/logo-san-sim.jpg" alt=""></a> </div>
            <sec:authorize access="hasRole('ROLE_USER')">
                 <div class="col-xs-12 col-sm-6 col-md-6 text-right">
                    <div class="login"> <a href="http://sansim.vn/customer/msisdn"><i class="fa fa-desktop"></i>&nbsp; Quay lại trang người bán</a> </div>
                </div>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <div class="col-xs-12 col-sm-6 col-md-6 text-right">
                    <div class="login"> <a href="http://sansim.vn/login"><i class="fa fa-lock"></i>&nbsp; Đăng nhập</a> <a href="http://sansim.vn/register"><i class="fa fa-user-md"></i>&nbsp; Đăng ký</a> </div>
                </div>
            </sec:authorize>
            
        </div>
    </div>
</div>
<div class="container-sim">
    <div class="topnav" id="myTopnav">
        <c:if test="<%= manager != null%>">
            <a href="http://<%=manager.getDomain()%>"><i class="fa fa-home" id ="home-logo" style="font-size:17px;padding:4px;"></i></a>
        </c:if>
        <c:if test="<%= manager == null%>">
            <a href="<%=request.getContextPath()%>/"><i class="fa fa-home" id ="home-logo" style="font-size:17px;padding:4px;"></i></a>
        </c:if>
        <a href="<%=request.getContextPath()%>/gioi-thieu">Giới thiệu</a>
        <a href="<%=request.getContextPath()%>/huong-dan">Hướng dẫn</a>
        <a href="<%=request.getContextPath()%>/tin-tuc">Tin tức</a>
        <a href="<%=request.getContextPath()%>/lien-he">Liên hệ</a>
        <a href="javascript:void(0);" style="font-size:15px;" class="icon" onclick="myTopnav()">&#9776;</a>
    </div>
</div>