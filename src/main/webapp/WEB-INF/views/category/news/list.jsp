<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container-sim">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Tin tức</li>
        </ol>
    </nav>

    <div class="page-content">
        <div class="row mb-2">
            <c:forEach items="${page.items}" var="item" varStatus="stat">
                <div class="col-xs-12 col-sm-6 col-md-6">
                    <article class="clearfix">
                        <figure class="figure_img"> <a href="<%=request.getContextPath()%>/tin-tuc/${item[5]}.html"><img src="${urlImage}${item[4]}" alt=""></a> </figure>
                        <bgroup> <a href="<%=request.getContextPath()%>/tin-tuc/${item[5]}.html">
                            <h2 class="h-title-blog">${item[1]}</h2>
                        </a>
                            <p class="p_blog">
                                <c:if test="${(item[2].length()) > 150}">
                                    ${item[2].concat("").substring(0,150)}...
                                </c:if>
                                <c:if test="${(item[2].length()) <= 150}">
                                    ${item[2]}
                                </c:if>
                            </p>
                            <div class="line-dashed"></div>
                            <p class="blog-date"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${item[3]}" /></p>
                            <a href="<%=request.getContextPath()%>/tin-tuc/${item[5]}.html">
                                <button type="button" class="btn btn-info btn-sm"> Chi tiết </button>
                            </a> </bgroup>
                    </article>
                </div>
            </c:forEach>
        </div>
        <div class="row mb-2" >
            <c:if test="${page.pageCount>1}">
                    <ul class="pagination" style="margin:0px auto;">
                        <c:if test="${page.pageNumber>1}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/tin-tuc/${id}?p=${page.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span> <span class="sr-only">Trước</span> </a> </a>
                            </li>
                        </c:if>
                        <c:forEach items="${page.pageList}" var="item">
                            <c:choose>
                                <c:when test="${page.pageNumber==item}">
                                    <li class="page-item active"><a class="page-link" href="javascript:void(0)">${item}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/tin-tuc/${id}?p=${item}">${item}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${page.pageNumber<page.pageCount}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/tin-tuc/${id}?p=${page.pageNumber+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Tiếp</span></a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>

