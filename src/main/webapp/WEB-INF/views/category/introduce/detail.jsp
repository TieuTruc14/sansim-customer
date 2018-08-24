<%@page import="com.osp.model.Customer"%>
<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    Customer manager = (Customer) request.getSession().getAttribute("userShop");
%>

<div class="container-sim">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Giới thiệu</li>
        </ol>
    </nav>
    <h1 class="page-title">GIỚI THIỆU</h1>
    <div class="page-content">
        <c:if test="<%= manager != null%>">
            <%= manager.getIntroduce()%>
        </c:if>
        <c:if test="<%= manager == null%>">


            ${item.content}
        </c:if>
    </div>
</div>