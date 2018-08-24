<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>
<style>
    #accordionuser{
        max-width:700px;
        margin:0 auto;
    }
    .row{
        margin-bottom:10px;
    }
</style>
<!-- /.aside -->
<jsp:useBean id="today" class="java.util.Date" />
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul
                class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a style="cursor: default;"><i
                            class="fa fa-list-alt icon"></i> Quản lý đăng số</a></li>
                <li><a style="cursor: default;"><i
                            class="fa fa-list-alt icon"></i> Chi tiết số đăng</a></li>
            </ul>
            <section class="panel panel-default">
                <div class="panel-body">
                    <div class="panel-heading">
                        <h4 class="panel-title text-left">Thông tin đăng số</h4>
                    </div>
                    <div id="collapseuser" class="panel-collapse collapse in">
                        <div class="panel-body ">
                            <div class="info-offset">
                                <form id="frmDelete" action="${ deleteURL != null ? deleteURL : 'remove' }" method="post">
                                    <input type="hidden" value=";" name="ids" id="hdfIds" />
                                </form>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Số thuê bao:</div>
                                    <div class="col-sm-8 col-xs-8">${msi.msisdn}</div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Giá bán:</div>
                                    <div class="col-sm-8 col-xs-8">
                                        <c:choose>
                                            <c:when test="${msi.price == null || msi.price == 0}">
                                                Thương lượng
                                            </c:when>
                                            <c:otherwise>
                                                <span class="currencyHtml">${msi.price}</span> VNĐ
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Mô tả:</div>
                                    <div class="col-sm-8 col-xs-8">${msi.description}</div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Thời gian đăng:</div>
                                    <div class="col-sm-8 col-xs-8">
                                        <fmt:formatDate pattern="HH:mm:ss dd-MM-yyyy"
                                                        value="${msi.genDate}"></fmt:formatDate>
                                        </div>
                                    </div>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Trạng thái hiển thị:</div>
                                    <div class="col-sm-8 col-xs-8">
                                        <c:choose>
                                            <c:when test="${msi.status == 1}"><font color=green>Hiển thị</font></c:when>
                                            <c:otherwise><font color=red>Không hiển thị</font></c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-xs-4">Trạng thái xác thực:</div>
                                    <div class="col-sm-8 col-xs-8">
                                        <c:choose>
                                            <c:when test="${msi.confirmStatus ==1}">
                                                <font color=green>Đã xác thực</font>
                                            </c:when>
                                            <c:otherwise>
                                                <font color=red>Chưa xác thực</font> ( Để xác thực dùng số <font color=blue>${msi.msisdn}</font> soạn <font color=blue>XTH <sec:authentication property="principal.username" /></font> lên đầu số <font color=blue>8055</font>(1000 vnđ). Thời hạn xác thực 30 ngày! )
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <c:if test="${msi.confirmStatus == 1 }">
                                    <div class="row">
                                        <div class="col-sm-4 col-xs-4">Thời gian hiệu lực xác thực:</div>
                                        <div class="col-sm-8 col-xs-8" >
                                            <fmt:formatDate value="${msi.confirmExpired}"
                                                            pattern="HH:mm:ss dd/MM/yyyy" />
                                            <c:choose>
                                                <c:when test="${msi.confirmExpired <= today}"><font color=red>Hết hiệu lực</font></c:when>
                                                <c:otherwise><font color=green>Còn hiệu lực</font></c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="row text-center">
                                    <sec:authorize access="hasAnyRole('ROLE_USER')">
                                        <a class="btn btn-sm btn-danger" href="msisdn">Trở lại</a>
                                    </sec:authorize>
                                </div>
                            </div>
                        </div>     
                    </div>
                </div>
                <div class="panel-heading">
                    <h4 class="panel-title text-left">Lịch sử xác thực số</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped b-t b-light text-sm list-group">
                        <thead>
                            <tr>
                                <th style="text-align:center;" width="4%">STT</th>
                                <th style="text-align:center;" width="15%">Thời gian gửi</th>
                                <th style="text-align:left;" width="10%">MO</th>
                                <th style="text-align:left;" width="60%">MT</th>
                            </tr>
                        </thead>
                        <c:set var="k" value="${start}"></c:set>

                            <tbody class="list">
                            <c:if test="${!empty listConfirm}">
                                <c:forEach var="items" items="${listConfirm}">
                                    <tr>
                                        <td style="text-align: center;">${k=k+1}</td>
                                        <td style="text-align: center;"><fmt:formatDate value="${items.genDate}" pattern="HH:mm:ss dd/MM/yyyy"/></td>
                                        <td style="text-align: left;">${items.info}</td>    
                                        <td style="text-align: left;">${items.mt}</td>    
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </section>




            <!-- kết thúc thanh phải -->
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block"
       data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

