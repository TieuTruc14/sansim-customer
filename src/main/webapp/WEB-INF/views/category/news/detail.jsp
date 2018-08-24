<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container-sim">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Tin tức</li>
        </ol>
    </nav>
    <h1 class="page-title">${item.title}</h1>
    <ul style="float:left;margin-left:-30px;">
        <li>
            <div class="fb-like" data-href="<spring:eval expression="@commonProperties.getProperty('domain')" />/tin-tuc/${item.formaturl}.html" data-layout="button" data-action="like" data-size="small" data-show-faces="false" data-share="false"></div>
            <div class="fb-share-button" data-href="<spring:eval expression="@commonProperties.getProperty('domain')" />/tin-tuc/${item.formaturl}.html" data-layout="button" data-size="small" data-mobile-iframe="true"><a target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=<spring:eval expression="@commonProperties.getProperty('domain')" />/tin-tuc/${item.formaturl}.html&amp;src=sdkpreparse" class="fb-xfbml-parse-ignore">Chia sẻ</a></div>
        </li>
        <li><span ><i class="fa fa-calendar"></i><fmt:formatDate pattern = "HH:mm dd/MM/yyyy" value = "${item.dateCreated}" /></span></li>
    </ul>
    <br/> <br/>
    <div class="page-content">
        ${item.content}
    </div>


    <div class="title_list"> <img src="<%=request.getContextPath()%>/assets/static/images/blog_list.jpg" alt="TIN TỨC KHÁC"> TIN TỨC KHÁC </div>
    <div class="row list-blog">
        <c:forEach items="${page.items}" var="item" varStatus="stat">
        <div class="col-md-4">
            <div class="card mb-4 box-shadow">
                <div class="thumb-wrapper">
                    <img class="card-img-top" src="${urlImage}${item[4]}" alt="Card image cap">
                </div>
                <div class="card-body" style="min-height: 169px;">
                    <h2 class="title_r"><a href="<%=request.getContextPath()%>/tin-tuc/${item[5]}.html">${item[1]}</a></h2>
                    <p>
                    
                    <c:if test="${(item[2].length()) > 150}">
                                    ${item[2].concat("").substring(0,150)}...
                                </c:if>
                                <c:if test="${(item[2].length()) <= 150}">
                                    ${item[2]}
                                </c:if>
                    </p>
                </div>
            </div>
        </div>
        </c:forEach>

    </div>

</div>
<div class="fb-comments" data-href="<spring:eval expression="@commonProperties.getProperty('domain')" />/tin-tuc/${item.formaturl}.html" data-width="100%" data-numposts="10"></div>
