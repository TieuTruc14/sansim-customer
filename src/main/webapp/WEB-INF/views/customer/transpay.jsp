<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%--<script  type="text/javascript">--%>
<%--$("#sansim-status").show();--%>
<%--setTimeout(function() { $("#sansim-status").hide(); }, 3000);--%>
<%--</script>--%>

<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Lịch sử gói cước</a></li>
            </ul>
            <section class="panel panel-default">
                <div class="wrapper bg-light lt">
                    <%@include file="../../views/notify.jsp"%>
                    <form method="post" class="bs-example form-horizontal" data-validate="parsley"
                          action="transpay" id="frmSearch">
                        <input type="hidden" id="page" name="p"
                               value="${param.p != null ? param.p : 1}" /> <span
                               id="jsonParam" class="hide">${param.filter}</span> <input
                               type="hidden" id="filter" name="filter" />
                        <script type="text/javascript">
                            $("#filter").val($("#jsonParam").text());
                        </script>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Mã gói cước: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="packageName" type="text" autofocus="autofocus" data-trigger="change"
                                           class="form-control input-sm" name="packageName"
                                           value="${param.packageName}">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Loại tác động: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <select name="type" class="form-control"
                                            id="type">
                                        <option value="-1"
                                                ${ param.type == null || param.type == -1 ? "selected" : ""}>-- Tất cả --</option>
                                        <option value="1"
                                                ${ param.type != null && param.type == 1 ? "selected" : ""}>Đăng ký</option>
                                        <option value="2"
                                                ${ param.type != null && param.type == 2 ? "selected" : ""}>Gia hạn
                                        </option>
                                        <option value="5"
                                                ${ param.type != null && param.type == 5 ? "selected" : ""}>Hủy do hệ thống
                                        </option>
                                        <option value="6"
                                                ${ param.type != null && param.type == 6 ? "selected" : ""}>Hủy do người dùng
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Thời gian tác động từ ngày: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="fromDate" type="text"
                                           class="form-control input-sm datepicker-input" name="fromDate"  data-date-format="dd/mm/yyyy"
                                           value="${param.fromDate}">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Đến ngày: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="toDate" type="text" class="form-control input-sm datepicker-input" data-date-format="dd/mm/yyyy"
                                           name="toDate" value="${param.toDate}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-12">
                            <div class="col-md-12 text-center">
                                    <button type="submit" id="btn_search"
                                            onclick="searchTranspay();"
                                            class="btn btn-sm btn-s-sm btn-primary">
                                        <i class="fa fa-search"></i> Tìm kiếm
                                    </button>
                            </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="table-responsive ">
                    <table id="tbl" class="table table-striped table-bordered m-b-none">
                        <thead>
                            <tr>
                                <th class="box-shadow-inner small_col text-center" width="2%" >STT</th>
                                <th class="box-shadow-inner small_col text-center" width="10%">Ngày</th>
                                <th class="box-shadow-inner small_col text-left" width="15%">Số thuê bao thanh toán</th>
                                <th class="box-shadow-inner small_col text-left" width="8%" >Gói cước</th>
                                <th class="box-shadow-inner small_col text-left" width="8%">Số tiền</th>
                                <th class="box-shadow-inner small_col text-left" width="8%">Tác động</th>
                                <th class="box-shadow-inner small_col text-left" width="8%" >Kết quả</th>
                            </tr>
                        </thead>
                        <c:set var="k" value="${start}"></c:set>

                            <tbody class="list">
                            <c:if test="${!empty listresult}">
                                <c:forEach var="items" items="${listresult}">
                                    <tr>
                                        <td style="text-align: center;">${k=k+1}</td>
                                        <td style="text-align: center;"><fmt:formatDate value="${items.genDate}" pattern="HH:mm:ss dd/MM/yyyy"/></td>
                                        <td style="text-align: left;">${items.msisdnPay}</td>
                                        <td style="text-align: left;">${items.confPackage.packageName}</td>
                                        <td style="text-align: left;"> <span class="currencyHtml">${items.price}</span></td>
                                        <td style="text-align: left;">
                                            <c:choose>
                                                <c:when test="${items.type == 1}">
                                                    Đăng ký
                                                </c:when>
                                                <c:when test="${items.type == 2}">
                                                    Gia hạn
                                                </c:when>
                                                <c:when test="${items.type == 5}">
                                                    Hủy do hệ thống
                                                </c:when>
                                                    <c:when test="${items.type == 6}">
                                                    Hủy do người dùng
                                                </c:when>
                                                <c:otherwise>
                                                    Chưa xác định
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align: left;"><c:choose>
                                                <c:when test="${items.payResult == 1}">
                                                    Thành công
                                                </c:when>
                                                <c:otherwise>
                                                    Không thành công
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
                

            <%@include file="../../views/paging.jsp"%>
            </section>
        </section>
        <%--<%@include file="../../views/layoutCus/footer.jsp"%>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>

    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content" style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Quản lý gói cước</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">{{messageStatus}}</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
                </div>

            </div>
        </div>
    </div>
</section>
        
<script type="text/javascript">
    function setFilterValue() {
        $("#filter").val( "{\"packageName\":\"" + $("#packageName").val() 
                + "\"," + "\"type\":\"" + $("#type").val()
                + "\"," + "\"fromDate\":\""+ $("#fromDate").val()
                + "\"," + "\"toDate\":\""+ $("#toDate").val()
                + "\"}");
    }
    function searchTranspay() {
        $("#page").val("1");
        setFilterValue();
        $("#frmSearch").submit();
    }
    
</script>        

