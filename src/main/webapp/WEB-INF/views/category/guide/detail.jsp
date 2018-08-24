<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container-sim">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Hướng dẫn</li>
        </ol>
    </nav>
    <h1 class="page-title">${item.title}</h1>
    <%--<img class="img_huong_dan img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/hinhvehuongdan.jpg" alt="hinhve">--%>
    <br/>
    <div class="page-content">
        ${item.content}
    </div>
</div>