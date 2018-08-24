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

    <div class="p-content">
        <div class="text-center">
            <h1 class="page-title-center mb-2 mt-2">HƯỚNG DẪN</h1>
            <img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/hinhvehuongdan.jpg" alt="hr">
        </div>
        <div class="page-content">
            <c:forEach items="${guides}" var="item" varStatus="stat">
                <div class="list-blog">
                    <h2 class="blog-h-title"> <a href="<%=request.getContextPath()%>/huong-dan/${item.formaturl}.html">${item.title}</a> </h2>
                    <div class="blog-list-content">
                        <div class="blog-list-left">
                                ${item.shortContent}
                        </div>
                        <div class="blog-list-right"> <a class="blog-view-link" href="<%=request.getContextPath()%>/huong-dan/${item.formaturl}.html">Xem tiếp</a> </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!--<div class="photoad mt-2 mb-2"> <img src="<%=request.getContextPath()%>/assets/static/images/gioithieu.jpg" alt="gioi thieu" style="width: 100%"> </div>-->
        <div class="text-center">
            <h1 class="page-title-center mb-2 mt-2">CÂU HỎI THƯỜNG GẶP</h1>
            <img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/hinhvehuongdan.jpg" alt="hr">
        </div>

        <div id="accordion">
            <c:forEach items="${questions}" var="item" varStatus="stat">
                <div class="card">
                    <div class="card-header" id="heading${stat.count}">
                        <h2 class="guide-title collapsed" data-toggle="collapse" data-target="#collapse${stat.count}" aria-expanded="true" aria-controls="collapse${stat.count}" style="padding:5px 10px;">
                                ${item.title}
                        </h2>
                    </div>
                    <div id="collapse${stat.count}" class="collapse" aria-labelledby="heading${stat.count}" data-parent="#accordion">
                        <div class="card-body">
                            <p>${item.content}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</div>

