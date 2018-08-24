<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${total == 0}">
    <footer class="footer bg-white">
        <div style="text-align: center">Không có dữ liệu</div>
    </footer>
</c:if>
<c:if test="${total > 0}">
    <footer class="footer bg-white b-t">
        <form id="frmDelete"
              action="${ deleteURL != null ? deleteURL : 'remove' }"
              method="post">
            <input type="hidden" value=";" name="ids" id="hdfIds" />
        </form>
        <c:set var="j"
        value='<%=request.getParameter("p") == null ? 1 : request
						.getParameter("p")%>' />

        <div class="row text-center-xs">
            <div class="col-md-4 hidden-sm">
                <p class="m-t" style="color: #2e3e4e;">
                    Tổng số <strong>${total}</strong> dữ liệu
                </p>
            </div>
            <c:if test="${total > 1}">
                <div class="col-md-8 col-sm-8 text-right">
                    <ul class="pagination pagination-sm m-t-sm m-b-none m-r-xs">
                        <c:choose>
                            <c:when test="${j == 1}">
                                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                                <li class="disabled"><a href="#">&lt;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="javascript:void(0)"
                                       onclick="CommonFunction.goToPageNum(1);">&lt;&lt;</a></li>
                                <li><a href="javascript:void(0)"
                                       onclick="CommonFunction.goToPageNum(${j-1});">&lt;</a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                                <c:choose>
                                    <c:when test="${i == j}">
                                    <li class="active"><a><c:out value="${i}" /></a></li>
                                        </c:when>
                                        <c:otherwise>
                                    <li><a href="javascript:void(0)"
                                           onclick="CommonFunction.goToPageNum(${i});"><c:out
                                                value="${i}" /></a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${j == totalPage}">
                                <li class="disabled"><a href="#">&gt;</a></li>
                                <li class="disabled"><a href="#">&gt;&gt;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="javascript:void(0)"
                                       onclick="CommonFunction.goToPageNum(${j+1});">&gt;</a></li>
                                <li><a href="javascript:void(0)"
                                       onclick="CommonFunction.goToPageNum(${totalPage});">&gt;&gt;</a></li>
                                </c:otherwise>
                            </c:choose>
                    </ul>
                </div>
            </c:if>
        </div>
    </footer>
</c:if>

