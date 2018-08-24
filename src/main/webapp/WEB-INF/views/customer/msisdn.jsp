<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:useBean id="today" class="java.util.Date" />
<!-- /.aside -->
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Quản lý đăng số</a></li>
            </ul>
            <section class="panel panel-default">
                <div class="wrapper bg-light lt">
                    <%@include file="../../views/notify.jsp"%>
                    <form method="post" class="bs-example form-horizontal" data-validate="parsley"
                          action="msisdn" id="frmSearch">
                        <input type="hidden" id="page" name="p"
                               value="${param.p != null ? param.p : 1}" /> 
                        <span id="jsonParam" class="hide">${param.filter}</span> 
                        <input type="hidden" id="filter" name="filter" />
                        <input type="hidden" id="typeAction" name="typeAction"/>
                        <script type="text/javascript">
                            $("#filter").val($("#jsonParam").text());
                        </script>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Đầu số:
                                </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <select name="firstNumber" class="form-control"
                                            id="firstNumber">
                                        <option value="0"
                                                ${ param.firstNumber == null || param.firstNumber == -1 ? "selected" : ""}>--
                                            Tất cả --</option>
                                        <option value="10"
                                                ${ param.firstNumber != null && param.firstNumber == 10 ? "selected" : ""}>10 số</option>
                                        <option value="11"
                                                ${ param.firstNumber != null && param.firstNumber == 11 ? "selected" : ""}>11 số</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Trang thái xác thực: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <select name="confstatus" class="form-control"
                                            id="confstatus">
                                        <option value="-1"
                                                ${ param.confstatus == null || param.confstatus == -1 ? "selected" : ""}>--
                                            Tất cả --</option>
                                        <option value="0"
                                                ${ param.confstatus != null && param.confstatus == 0 ? "selected" : ""}>Chưa xác thực</option>
                                        <option value="1"
                                                ${ param.confstatus != null && param.confstatus == 1 ? "selected" : ""}>Đã xác thực
                                        </option>
                                    </select>
                                </div>
                            </div>   
                        </div>

                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Số thuê bao: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="msisdn" type="text" autofocus="autofocus" data-trigger="change"
                                           class="form-control input-sm" name="msisdn"
                                           value="${param.msisdn}">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Số sắp hết hạn xác thực: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <select name="confirmExpired" class="form-control"
                                            id="confirmExpired">
                                        <option value="-1"
                                                ${ param.confirmExpired == null || param.confirmExpired == -1 ? "selected" : ""}>Không chọn</option>
                                        <option value="1"
                                                ${ param.confirmExpired != null && param.confirmExpired == 1 ? "selected" : ""}>Trong 1 ngày tới</option>
                                        <option value="3"
                                                ${ param.confirmExpired != null && param.confirmExpired == 3 ? "selected" : ""}>Trong 3 ngày tới</option>
                                        <option value="5"
                                                ${ param.confirmExpired != null && param.confirmExpired == 5 ? "selected" : ""}>Trong 5 ngày tới
                                        </option>
                                        <option value="7"
                                                ${ param.confirmExpired != null && param.confirmExpired == 7 ? "selected" : ""}>Trong 7 ngày tới
                                        </option>
                                        <option value="10"
                                                ${ param.confirmExpired != null && param.confirmExpired == 10 ? "selected" : ""}>Trong 10 ngày tới
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-md-3 control-label"
                                       style="padding-right: 0px; text-align: left;">Thời gian đăng từ ngày: </label>
                                <div class="col-md-9" style="padding-right: 0px;">
                                    <input id="fromDate" type="text"
                                           class="form-control input-sm datepicker-input" name="fromDate" data-date-format="dd/mm/yyyy"
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
                                            onclick="searchAuction();"
                                            class="btn btn-sm btn-s-sm btn-primary">
                                        <i class="fa fa-search"></i> Tìm kiếm
                                    </button>
                                    <button type="button" class="btn btn-sm btn-danger"
                                            onclick="resetForm();">
                                        <i class="fa fa-trash-o"></i> Xóa điều kiện
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
            <!--</div>-->
            <sec:authorize access="hasAnyRole('ROLE_USER')">
                <div class="row m-b-sm">
                    <div class="col-xs-12">
                        <!--<div class="col-xs-1">-->    
                        <div class="pull-left">
                            <sec:authorize access="hasAnyRole('ROLE_USER')">
                                <a href="javascript:void(0)" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-cancel"><i
                                        class="fa fa-trash-o"></i> Xóa số đã chọn</a>
                                </sec:authorize>
                        <!--</div>-->
                        
                        </div>
                        <div class="col-xs-7">
                            <sec:authorize access="hasAnyRole('ROLE_USER')">
                                <div class="pull-left">
                                    <input type="checkbox" id="isViettel" name="isViettel" onchange="searchByTelco();"  ${ telco != null && fn:contains(telco, '1')  ? "checked" : ""} style="width: 14px !important;"> <img style="height:10px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/viettel.png" alt="viettel">
                                </div>
                                <div class="pull-left">
                                    &nbsp;&nbsp;
                                </div>
                                <div class="pull-left">
                                    <input type="checkbox" id="isVinaphone" name="isVinaphone" onchange="searchByTelco();" ${ telco != null && fn:contains(telco, '2') ? "checked" : ""} style="width: 14px !important;"> <img style="height:25px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.png" alt="vinaphone" style="width: 70px; height:  20px; ">
                                </div>
                                <div class="pull-left">
                                    &nbsp;&nbsp;
                                </div>
                                <div class="pull-left">
                                    <input type="checkbox" id="isMobifone" name="isMobifone" onchange="searchByTelco();" ${ telco != null && fn:contains(telco, '3') ? "checked" : ""} style="width: 14px !important;"> <img style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/mobifone.png" alt="mobifone" style="width: 70px; height:  10px;">
                                </div>
                                <div class="pull-left">
                                    &nbsp;&nbsp;
                                </div>
                                <div class="pull-left">
                                    <input type="checkbox" id="isGmobile"  name="isGmobile" onchange="searchByTelco();" ${ telco != null && fn:contains(telco, '4') ? "checked" : ""} style="width: 14px !important;"> <img style="height:20px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/gmobile.png" alt="gmobile" >
                                </div>
                                <div class="pull-left">
                                    &nbsp;&nbsp;
                                </div>
                                <div class="pull-left">
                                    <input type="checkbox" id="isVietnamobile"  name="isVietnamobile" onchange="searchByTelco();" ${ telco != null && fn:contains(telco, '5') ? "checked" : ""} style="width: 14px !important;"> <img  style="height:25px; width: 80px"   src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.png" alt="vietnamobile" >
                                </div>
                                <div class="pull-left">
                                    &nbsp;&nbsp;
                                </div>
                                <div class="pull-left">
                                    <input type="checkbox" id="isOrtherTelco" name="isOrtherTelco" onchange="searchByTelco();" ${ telco != null && fn:contains(telco, '0') ? "checked" : ""} style="width: 14px !important;"> Nhà mạng khác
                                </div>
                            </sec:authorize>
                        </div>
                        <c:if test="${!empty listresult}">

                            <!--                            <div class="pull-right">
                            <%--<sec:authorize access="hasAnyRole('ROLE_USER')">--%>
                                <a href="javascript:void(0)" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-cancel"><i
                                        class="fa fa-trash-o"></i> Hủy đăng số</a>
                                 <button type="submit" id="btn_export"
                                                    onclick="exportAuction();"
                                                    class="btn btn-sm btn-s-sm btn-primary">
                                                    <i class="fa fa-download"></i> Export dữ liệu
                                                </button>       
                                        
                            <%--</sec:authorize>--%>
                    </div>
                    <div class="pull-right">
                        &nbsp;
                    </div>-->
                        </c:if>
                        <!--<div class="col-lg-2">-->
                        <div class="pull-right">
                            <a href="uploadMsisdn?type=single" class="btn btn-sm btn-primary"><i class="fa fa-plus"></i>
                                Đăng số mới</a>
                        </div>
                        <div class="pull-right">
                            &nbsp;
                        </div>
                        <div class="pull-right">
                            <a id="btnDwn"  href="javascript:void(0)"  onclick="exportAuction();" class="btn btn-sm btn-info" target="_blank"><i class="fa fa-download"></i>
                                Tải danh sách số</a>
                        </div>
                        <!--</div>-->
                    </div>
                </div>
            </sec:authorize>
            <section class="panel panel-default">
                <div class="table-responsive">
                    <form action="removeMsisdn" method="post" id="frmRemoveMsisdn" >
                        <input type="hidden" value=";" name="ids" id="hdfIds" />
                        <input type="hidden" value="" name="pageRemoveMsisdn" id="pageRemoveMsisdn" >
                        <input type="hidden" id="filterRemove" name="filterRemove" />
                        <table class="table table-striped table-bordered m-b-none">
                            <thead>
                                <tr>
                                    <sec:authorize access="hasAnyRole('ROLE_USER')">
                                        <th width="1%" class="box-shadow-inner small_col text-center" style=" padding: 6px 0px !important" ><input type="checkbox" id="sltAll"
                                                                                                             value="0" onchange="chkAll();"
                                                                                                             style="width: 14px !important;">
                                        </th>
                                    </sec:authorize>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="1%">STT</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="8%">Số thuê bao</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="8%">Nhà mạng</th>

                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="8%">Giá bán (VNĐ)</th>
                                    <!--<th style="text-align:center;" >Phí đăng (VNĐ)</th>-->
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="11%">Thời gian đăng</th>
                                    <!--<th style="text-align:center;">Mã thanh toán</th>-->
                                    <!--<th style="text-align:center;">T/G hiệu lực thanh toán</th>-->
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="9%">Trạng thái</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="11%">T/G hiệu lực đăng</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="12%">Trạng thái xác thực</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="15%">T/G hết hiệu lực xác thực</th>
                                    <!--<th class="box-shadow-inner small_col" style="text-align:left;" width="21%">Mô tả</th>-->
                                    <th class="box-shadow-inner small_col" style="text-align:center; padding: 6px 0px !important" width="19%">Thao tác</th>
                                </tr>
                            </thead>
                            <c:set var="k" value="${start}"></c:set>

                                <tbody class="list">
                                <c:if test="${!empty listresult}">
                                    <c:forEach var="items" items="${listresult}">
                                        <tr>
                                            <sec:authorize access="hasAnyRole('ROLE_USER')">
                                                <td class="text-center" style="vertical-align: middle;">
                                                    <input type="checkbox"
                                                           onchange="CommonFunction.selectedItems(this);"
                                                           style="width: 14px !important;" value="${items.id}">
                                                </td>
                                            </sec:authorize>
                                            <td style="text-align: center;">${k=k+1}</td>       
                                            <td style="text-align: left;font-weight: 550"><a href="<%=request.getContextPath()%>/customer/editMsisdn?id=${items.id}">${items.msisdnAlias}</a></td>
                                            <td style="text-align: center;"><c:choose>
                                                    <c:when test="${items.telco==1}">
                                                        <img style="margin-bottom:5px; height:10px; width: 70px" src="<%=request.getContextPath()%>/assets/images/cus/viettel.png" alt="viettel">
                                                    </c:when>
                                                    <c:when test="${items.telco==2}">
                                                        <img style="margin-bottom:5px; height:20px; width: 70px"  src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.png" alt="vinaphone">
                                                    </c:when>
                                                    <c:when test="${items.telco==3}">
                                                        <img style="margin-bottom:5px; height:15px; width: 70px"  src="<%=request.getContextPath()%>/assets/images/cus/mobifone.png" alt="mobifone">
                                                    </c:when>
                                                    <c:when test="${items.telco==4}">
                                                        <img style="margin-bottom:5px; height:18px; width: 70px"  src="<%=request.getContextPath()%>/assets/images/cus/gmobile.png" alt="gmobile">
                                                    </c:when>
                                                    <c:when test="${items.telco==5}">
                                                        <img style="margin-bottom:5px;height:20px; width: 70x "  src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.png" alt="vietnamobile">
                                                    </c:when>
                                                    <c:otherwise>
                                                        Nhà mạng khác
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td style="text-align: left;" >
                                                <c:choose>
                                                    <c:when test="${items.price == 0}">
                                                        <a href="/customer/editStatus" data-toggle="modal" data-target="#myModal"  onclick="onChangeFee('${items.id}', '${items.price}', '${items.msisdn }');"> Thương lượng </a> 
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#/customer/editStatus" 
                                                           data-toggle="modal" 
                                                           data-target="#myModal" 
                                                           >
                                                            <span onclick="onChangeFee('${items.id}', '${items.price}', '${items.msisdn }');" class="currencyHtml">${items.price}</span></a>
                                                        </c:otherwise>
                                                    </c:choose>
                                            </td>
                                            <td style="text-align: center;"><fmt:formatDate value="${items.genDate}" pattern="HH:mm:ss dd/MM/yyyy"/></td>
                                            <td style="text-align: center;" >
                                                <!--<input type="text" value="${items.status}"/>-->
                                                <c:choose >
                                                    <c:when test="${items.status == 1}" >
                                                        <a  href="#" onclick="editSTT('1', '${items.id}');"  > <span id="${items.id}" style="color: blue">Hiển thị</span>  </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#" onclick="editSTT('0', '${items.id}');" > <span id="${items.id}"> Không hiển thị </span></a>
                                                    </c:otherwise>
                                                </c:choose></td>
                                            <td style="text-align: center;">
                                                <c:if test="${items.status == 1}">
                                                    <fmt:formatDate value="${custService.expiredDate}" pattern="HH:mm:ss dd/MM/yyyy"/>
                                                </c:if>
                                            </td>
                                            <td style="text-align: left;"><c:choose>
                                                    <c:when test="${items.confirmStatus == 1}">
                                                        <span style="color: blue">Đã xác thực</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        Chưa xác thực
                                                    </c:otherwise>
                                                </c:choose></td>
                                            <td style="text-align: center;"><fmt:formatDate value="${items.confirmExpired}" pattern="HH:mm:ss dd/MM/yyyy"/> </td>
                                            <!--<td style="text-align: left;">${items.description}</td>-->    
                                            <td>

                                                <sec:authorize access="hasAnyRole('ROLE_USER')">
                                                    <div style="text-align: center;">
                                                        <a href="<%=request.getContextPath()%>/customer/forwardMsisdn?id=${items.id}"><img style="width: 16px; height: 16px; margin-left: 5px" src="<%=request.getContextPath()%>/assets/images/chuyenso.png"  title="Chuyển số"></a>
                                                        <a href="<%=request.getContextPath()%>/customer/detailMsisdn?id=${items.id}"><img style="width: 16px; height: 16px; margin-left: 5px" src="<%=request.getContextPath()%>/assets/images/detail.png"  title="Xem số"></a>
                                                        <a href="javascript:void(0)" data-toggle="modal" data-target="#confirm-cancel" onclick="$('#hdfIds').val('${items.id}');"><img style="width: 16px; height: 16px; margin-left: 5px" src="<%=request.getContextPath()%>/assets/images/delete.png"  title="Xóa số"></a>            
                                                        <a href="<%=request.getContextPath()%>/customer/editMsisdn?id=${items.id}"><img style="width: 16px; height: 16px; margin-left: 5px" src="<%=request.getContextPath()%>/assets/images/edit.png"  title="Sửa số"></a>
                                                        

                                                    </div>
                                                </sec:authorize>
                                            </td> 
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>

                    </form>
                    <!-- Modal edit price -->

                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-sm" role="document">
                            <div class="modal-content">
                                <div class="modal-header" style="padding: 7px !important;background: initial" >
                                    <button type="button" class="close" data-dismiss="modal" style=".modal-sim .close{position: absolute; right: 15px; top: 10px; z-index: 999;font-size: 24px;color: #fff;opacity: 1;font-weight: normal; padding-right: 9px}">&times;</button>
                                    <h5 class="modal-title font-bold text-left" id="exampleModalLabel" style="padding-left: 2px;">Cập nhật giá bán</h5>
                                    
                                </div>

                                <form class="bs-example form-horizontal" data-validate="parsley" id="frmAdd"
                                      action="editFee" method="post" >
                                    <div class="modal-body" style="padding: 7px !important;">
                                        <div class="form-group" style="padding-top: 5px;">
                                            <div class="col-sm-5">
                                                <label for="recipient-name" class="col-form-label font-bold">Số thuê bao: </label>
                                            </div>
                                            <div class="col-sm-7">
                                                <input name="modalMsisdn" class="form-control input-sm" id="modalMsisdn"  disabled="true"
       />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-5">
                                                <label for="message-text" style="margin-top: 10px;" class="col-form-label font-bold">Giá bán:</label>
                                            </div>
                                            <div class="col-sm-7">
                                                <input name="price" class="form-control input-sm currency currencyHtml" id="price"  
                                                       data-trigger="change" maxlength="13" 
                                                       data-maxlength="13"
                                                       data-maxlength-message="<font color=green>Giá bán</font> quá lớn!" />
                                            </div>
                                            <input type="hidden" name="idModal" id="idModal" />
                                            <input type="hidden" id="pageModal" name="pModal"
                                                   value="${param.pageModal != null ? param.pageModal : 1}" /> 
                                            <span id="jsonParamModal" class="hide">${param.filterModal}</span> 
                                            <input type="hidden" id="filterModal" name="filterModal" />
                                            <script type="text/javascript">
                                                $("#filterModal").val($("#jsonParamModal").text());
                                            </script>
                                        </div>
                                    </div>
                                    <div class="row" style="text-align: center; padding-bottom:6px; ">
                                        <button type="submit"  onclick="setFilterValueModal();" class="btn btn-primary ">Cập nhật</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>

                <%@include file="../../views/paging.jsp"%>
            </section>
            <!-- kết thúc thanh phải -->
        </section>       
        <%--<%@include file="../../views/layoutCus/footer.jsp"%>--%>
    </section>
    <a href="#" class="hide nav-off-screen-block"
       data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
<!-- App -->
<script type="text/javascript">
    function setFilterValue() {
//        alert(getTelco());
        $("#filter").val(
                "{\"msisdn\":\"" + $("#msisdn").val() + "\","
                + "\"confirmExpired\":\"" + $("#confirmExpired").val()
                + "\"," + "\"fromDate\":\""
                + $("#fromDate").val()
                + "\"," + "\"toDate\":\""
                + $("#toDate").val()
                + "\"," + "\"firstNumber\":\"" + $("#firstNumber").val()
                + "\"," + "\"confstatus\":\"" + $("#confstatus").val()
                + "\"," + "\"telco\":\"" + getTelco()
                + "\"}");
    }

    function searchAuction() {
        $("#page").val("1");
        setFilterValue();
        $("#typeAction").val("1");
        $("#frmSearch").submit();
    }
    function exportAuction() {
        $("#page").val("1");
        setFilterValue();
//        $.get("<%= request.getContextPath()%>/customer/downloadExcel", {filter: $("#filter").val()});
//        $.ajax({
//            url: "<%= request.getContextPath()%>/customer/downloadExcel",
//            type: "GET",
//            dataType: "text",
//            data: {filter: $("#filter").val()}
//        });

        $("#typeAction").val("2");
        
        $("#frmSearch").submit();
    }

    function searchByTelco() {
        $("#filter").val(
                "{\"msisdn\":\"" + $("#msisdn").val() + "\","
                + "\"confirmExpired\":\"" + $("#confirmExpired").val()
                + "\"," + "\"fromDate\":\""
                + $("#fromDate").val()
                + "\"," + "\"toDate\":\""
                + $("#toDate").val()
                + "\"," + "\"firstNumber\":\"" + $("#firstNumber").val()
                + "\"," + "\"confstatus\":\"" + $("#confstatus").val()
                + "\"," + "\"telco\":\"" + getTelco()
                + "\"}");
        $("#typeAction").val("1");
        $("#frmSearch").submit();
    }


    function resetForm() {
        $("#msisdn").val("");
        $("#confirmExpired").val("-1");
        $("#confstatus").val("-1");
        $("#fromDate").val("");
        $("#toDate").val("");
        $("#firstNumber").val("0");
    }



    function chkAll() {
        var selected_val = "all";
        $("#hdfIds").val(selected_val);
        var removeMsisdn = ${param.p != null ? param.p : 1};
        $("#pageRemoveMsisdn").val(removeMsisdn);
        $("#pageRemoveMsisdn").val($("#page").val());
        $("#filterRemove").val(
                "{\"msisdn\":\"" + $("#msisdn").val() + "\","
                + "\"confirmExpired\":\"" + $("#confirmExpired").val()
                + "\"," + "\"fromDate\":\""
                + $("#fromDate").val()
                + "\"," + "\"toDate\":\""
                + $("#toDate").val()
                + "\"," + "\"firstNumber\":\"" + $("#firstNumber").val()
                + "\"," + "\"confstatus\":\"" + $("#confstatus").val()
                + "\"," + "\"telco\":\"" + getTelco()
                + "\"}");
    }

    function choose() {
        var selected_val = "";
        $('.selected_item').each(function () {
            if (this.checked) {
                selected_val = selected_val + "," + $(this).val();
            }
        });
        $("#ids").val(selected_val);
    }


    $(document).ready(function () {
        $("#confstatus").change(function () {
            if (this.value == 1) {
                $("#confirmExpired").val("-1");
                var status = document.getElementById("confirmExpired");
                status.removeAttribute("disabled");

            } else {
                $("#confirmExpired").val("-1");
                $("#confirmExpired").attr("disabled", "true");
            }
        });
        if ($("#confstatus").val() == 1) {
            $("#confirmExpired").val("-1");
            var status = document.getElementById("confirmExpired");
            status.removeAttribute("disabled");

        } else {
            $("#confirmExpired").val("-1");
            $("#confirmExpired").attr("disabled", "true");
        }

    })
    function onChangeFee(id, price, msisdn) {
        $('#modalMsisdn').val(msisdn);
        $('#price').val(price);
        $('#idModal').val(id);

//        setFilterValue();
    }

    function setFilterValueModal() {
        $("#filterModal").val(
                "{\"msisdn\":\"" + $("#msisdn").val() + "\","
                + "\"confirmExpired\":\"" + $("#confirmExpired").val()
                + "\"," + "\"fromDate\":\""
                + $("#fromDate").val()
                + "\"," + "\"toDate\":\""
                + $("#toDate").val()
                + "\"," + "\"firstNumber\":\"" + $("#firstNumber").val()
                + "\"," + "\"confstatus\":\"" + $("#confstatus").val()
                + "\"," + "\"telco\":\"" + getTelco()
                + "\"}");
    }
    function keepValFilterSTT() {
        $("#filterSTT").val(
                "{\"msisdn\":\"" + $("#msisdn").val() + "\","
                + "\"confirmExpired\":\"" + $("#confirmExpired").val()
                + "\"," + "\"fromDate\":\""
                + $("#fromDate").val()
                + "\"," + "\"toDate\":\""
                + $("#toDate").val()
                + "\"," + "\"firstNumber\":\"" + $("#firstNumber").val()
                + "\"," + "\"confstatus\":\"" + $("#confstatus").val()
                + "\"," + "\"telco\":\"" + getTelco()
                + "\"}");
    }
    function editSTT(valStt, idSTT) {
        $.ajax({
            url: "<%= request.getContextPath()%>/customer/editStatus",
            type: "POST",
            dataType: "text",
            data: {id: idSTT, valSTT: valStt},
            success: function (result) {
                if (result === 'success') {
                    alert('Cập nhật trạng thái thành công!');
                    if ($('#' + idSTT).text().trim() === 'Hiển thị') {

                        $('#' + idSTT).text('Không hiển thị');
                    } else {

                        $('#' + idSTT).text('Hiển thị');
                    }
                } else {
                    alert('Cập nhật trạng thái thất bại. Vui lòng thử lại sau!');
                }
            },
            error: function (result) {
                alert("Cập nhật trạng thái thất bại. Vui lòng thử lại sau!");
            }
        });
    }

    $("#btnDwn").change(function () {
        $("#btnDwn").attr("href", "downloadExcel");
    });


    function getTelco() {
        var typeTelco = "";
        if ($("#isViettel").is(":checked")) {
            typeTelco = typeTelco + ';1';
        }
        if ($("#isVinaphone").is(":checked")) {
            typeTelco = typeTelco + ';2';
        }
        if ($("#isMobifone").is(":checked")) {
            typeTelco = typeTelco + ';3';
        }
        if ($("#isGmobile").is(":checked")) {
            typeTelco = typeTelco + ';4';
        }
        if ($("#isVietnamobile").is(":checked")) {
            typeTelco = typeTelco + ';5';
        }
        if ($("#isOrtherTelco").is(":checked")) {
            typeTelco = typeTelco + ';0';
        }
        return typeTelco;
    }

</script>
