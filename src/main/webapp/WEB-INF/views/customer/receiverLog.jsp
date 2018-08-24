<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<script  src="<%=request.getContextPath()%>/assets/project/customer/jquery.autocomplete.min.js"></script> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css"> 
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Lịch sử nhận số từ người bán khác</a></li>
            </ul>
            <section class="panel panel-default">
                <div class="wrapper bg-light lt">
                    <%@include file="../../views/notify.jsp"%>
                    <form method="post" class="bs-example form-horizontal" data-validate="parsley"
                          action="receiverLog" id="frmSearch">
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
                                       style="padding-right: 0px; text-align: left;">Số điện thoại: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="msisdn" type="text" autofocus="autofocus" data-trigger="change"
                                           class="form-control input-sm" name="msisdn"
                                           value="${param.msisdn}">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Người chuyển: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="userName" type="text" autofocus="autofocus" data-trigger="change"
                                           class="form-control input-sm" name="userName"
                                           value="${param.userName}">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Thời gian nhận từ ngày: </label>
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
                                <th class="box-shadow-inner small_col text-left" width="15%">Số thuê bao</th>
                                <th class="box-shadow-inner small_col text-left" width="8%" >Người chuyển số</th>
                            </tr>
                        </thead>
                        <c:set var="k" value="${start}"></c:set>
                            <tbody class="list">
                            <c:if test="${!empty listresult}">
                                <c:forEach var="items" items="${listresult}">
                                    <tr>
                                        <td style="text-align: center;">${k=k+1}</td>
                                        <td style="text-align: center;"><fmt:formatDate value="${items.genDate}" pattern="HH:mm:ss dd/MM/yyyy"/></td>
                                        <td style="text-align: left;">${items.msisdn}</td>
                                        <td style="text-align: left;">${items.customerSender.username}</td>
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
</section>
        
<script type="text/javascript">
    function setFilterValue() {
        $("#filter").val( "{\"msisdn\":\"" + $("#msisdn").val() 
                + "\"," + "\"userName\":\"" + $("#userName").val()
                + "\"," + "\"fromDate\":\""+ $("#fromDate").val()
                + "\"," + "\"toDate\":\""+ $("#toDate").val()
                + "\"}");
    }
    function searchTranspay() {
        $("#page").val("1");
        setFilterValue();
        $("#frmSearch").submit();
    }
    
    $(document).ready(function() {

		$('#userName').autocomplete({
			serviceUrl: 'getUserNames',
			paramName: "userName",
			delimiter: ",",
		    transformResult: function(response) {
		        return {
		            suggestions: $.map($.parseJSON(response), function(item) {
		                return { value: item.username, data: item.id };
		            })
		        };
		    }
		    
		});
		
		
	});
    
</script>        

