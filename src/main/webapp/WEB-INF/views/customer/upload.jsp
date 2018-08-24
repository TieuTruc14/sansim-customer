<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!-- /.aside -->
<style>
    .input-file-row-1:after {
        content: ".";
        display: block;
        clear: both;
        visibility: hidden;
        line-height: 0;
        height: 0;
    }

    .input-file-row-1 {
        display: inline-block;
        margin-top: 25px;
        position: relative;
    }

    #preview_image {
        display: none;
        width: 90px;
        height: 90px;
        margin: 2px 0px 0px 5px;
        border-radius: 10px;
    }

    .upload-file-container {
        position: relative;
        width: 100px;
        height: 137px;
        overflow: hidden;
        background: url(http://i.imgur.com/AeUEdJb.png) top center no-repeat;
        float: left;
        margin-left: 23px;
    }

    .upload-file-container-text {
        font-family: Arial, sans-serif;
        font-size: 12px;
        color: #719d2b;
        line-height: 17px;
        text-align: center;
        display: block;
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100px;
        height: 35px;
    }

    .upload-file-container-text>span {
        border-bottom: 1px solid #719d2b;
        cursor: pointer;
    }

    .one_opacity_0 {
        opacity: 0;
        height: 0;
        width: 1px;
        float: left;
    }
    .btn-file {
        position: relative;
        overflow: hidden;
    }
    .btn-file input[type=file] {
        position: absolute;
        top: 0;
        right: 0;
        min-width: 100%;
        min-height: 100%;
        font-size: 100px;
        text-align: right;
        filter: alpha(opacity=0);
        opacity: 0;
        outline: none;
        background: white;
        cursor: inherit;
        display: block;
    }
</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Đăng số mới</a></li>
            </ul>
                <section class="panel panel-default">
                    <%@include file="../../views/notify.jsp"%>
                    <header class="panel-heading text-right bg-light">
                        <ul class="nav nav-tabs pull-left">
                            <li class="${ uploadtype != 'multi' ? 'active' : '' }">
                                <h4 style="font-weight: 600; margin-top:  25px; margin-left: 5px" class="${ uploadtype != 'multi' ? 'btn btn-primary' : 'btn btn-default' }" name="btnsingle" id="btnsingle" ><a href="#tabsingle"    data-toggle="tab" style="font-size: 14px" onclick="changeMethod(1)"><i class="fa fa-user text-default " > </i>Đăng số</a></h4>
                            </li>
                            <li class="${ uploadtype == 'multi' ? 'active' : '' }">
                                <h4 style="font-weight: 600; margin-top:  25px; margin-left: 5px" class="${ uploadtype == 'multi' ? 'btn btn-primary' : 'btn btn-default' }" name="btnmulti" id="btnmulti"  ><a href="#tabmulti"  data-toggle="tab" style="font-size: 14px" onclick="changeMethod(2)">
                                    <i class="fa fa-users text-default"> </i> 
                                        Đăng số qua file
                                </a>
                                </h4>
                            </li>
                        </ul>
                        <div class="clear"></div>
                    </header>
                                <div class="panel-body" style="margin-top: 35px">
                        <div class="tab-content">
                            <div class="tab-pane fade ${ uploadtype == 'multi' ? '' : 'active in' }" id="tabsingle">
                                <form class="bs-example form-horizontal" data-validate="parsley" id="frmAdd" action="uploadMsisdn" method="post" >
                                    <input type="hidden" name="uploadtype" value="single"/>
                                    <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                        <i class="fa fa-foursquare"></i> Thông tin đăng số
                                    </h5>
<!--                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l">Số thuê bao <font
                                                color="red">*</font>:
                                        </span>
                                        <div class="col-lg-4">
                                            <input type="text" name="msisdn"
                                                   class="form-control input-sm"
                                                   data-required="true" data-trigger="change"
                                                   autofocus="autofocus"
                                                   data-required-message="<font color=green>Số thuê bao</font> không được bỏ trống"
                                                   data-maxlength="22"
                                                   data-maxlength-message="Độ dài không được vượt quá 22 ký tự"
                                                   onkeypress="return restrictCharacters(this, event, digitsAndAlias);" />
                                        </div>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l">Giá bán <font
                                                color="red">*</font>:
                                        </span>
                                        <div class="col-lg-4">
                                            <input name="price" class="form-control input-sm currency"
                                                   data-trigger="change"
                                                   data-maxlength="14"
                                                   data-maxlength-message="<font color=green>Giá bán</font> quá lớn!" />
                                        </div>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l">Mô tả:
                                        </span>
                                        <div class="col-lg-4">
                                            <textarea name="description" class="form-control input-sm"
                                                      data-trigger="change"
                                                      data-maxlength="500"
                                                      data-maxlength-message="<font color=green>Thông tin mô tả</font> quá lớn!" ></textarea>
                                        </div>
                                    </div>-->
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l hide">Danh sách số đăng:
                                        </span>
                                        <div class="col-lg-4">
                                            <span style="color: orangered">Copy 2 cột số thuê bao và giá bán từ file excel.</span>
                                            <textarea id ="msisdnUpload" name="msisdnUpload" class="form-control input-sm" rows="20" cols="40" 
                                                      data-trigger="change"
                                                      data-maxlength="2500000"
                                                      data-maxlength-message="<font color=green>Danh sách số đăng</font> quá lớn!" ></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l hide">Hiển thị giá tiền
                                        </span>
                                        <div class="col-lg-8">
                                            <span style='color: #00F;'><input TYPE="radio" NAME="typeShow1" class="form-check-input" id ='typeShow1' VALUE="typeShow1" checked="true" > - Giữ nguyên định dạng ( 300 -> 300 )  </span>
                                            <span style='color: #00F; padding-left: 5px'><input TYPE="radio" class="form-check-input" NAME="typeShow2" id ='typeShow2' VALUE="typeShow2" > - Thêm hàng nghìn ( 300 -> 300,000 )  </span>
                                            <span style='color: #00F; padding-left: 5px'><input TYPE="radio" class="form-check-input" NAME="typeShow3" id ='typeShow3' VALUE="typeShow3" > - Thêm hàng triệu ( 300 -> 300,000,000 )</span>
                                        </div>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l"></span>
                                        <div class="col-lg-6">
                                            <!-- <input type="submit" class="btn btn-sm btn-primary"
                                    value="Lưu" style="width: 90px;" />  -->
                                            <input type="submit" id="btnSave" style="display: none;"
                                                   onclick="return addAuction();" /> <a
                                                   class="btn btn-sm btn-primary" id="linkSave"
                                                   onclick="$('#btnSave').click();" style="width: 90px;"><i
                                                    class="fa fa-save"></i> Đăng số</a> 
                                            <a href="javascript: back();" class="btn btn-sm btn-danger" style="width: 90px;">Hủy bỏ</a>
                                        </div>
                                    </div>
                                    <c:if test="${countSuccess == null && error == null}">                
                                    <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                        <i class="fa fa-foursquare"></i> Hướng dẫn đăng số
                                    </h5>
                                    <div class="form-group m-b-xs" style="padding: 3px; color: black">
                                        <div class="col-lg-8">
                                            <span class="control-span m-l">Copy 2 trường số thuê bao và giá bán từ file excel hoặc điền theo mẫu <span style="color: blue; font-weight: 550">0931346121 60000</span>.</span>
                                            <ul>
                                                <li>Thông tin số thuê bao là số điện thoại bắt đầu có thể bắt đầu  bằng 0, 84 , +84.</li>
                                                <li>Thông tin số thuê bao có thể bao gồm ký tự "." để phân tách số ví dụ: 0931.888.888</li>
                                                <li>Giá bán nằm trong khoảng giá từ 0 -> 99,000,000,000 đồng.</li>
                                                <li>Mặc định giá tiền sẽ giữ nguyên như thông tin bạn đẩy. Ngoài ra có thể chọn thêm hàng nghìn để nhân giá tiền thêm 1000 hoặc thêm hàng triệu để nhân lên 1,000,000.</li>
                                            </ul>
                                        <span class="control-span m-l">Nhấn vào <strong>Lưu</strong> để đẩy danh sách số lên kho cá nhân.</span>  
                                        
                                        </div>
                                    </div> 
                                    </c:if>
                                    <c:if test="${countSuccess != null}">
                                        <div class="form-group m-b-xs">
                                            <span class="col-lg-2 control-span m-l"></span>
                                            <div class="col-lg-6 control-span">
                                                <c:if test="${error == 'errorFormat'}">
                                                    <div><span style="color:red;">File không đúng format!</span></div>
                                                </c:if>
                                                <c:if test="${error == 'errorFile'}">
                                                    <div><span style="color:red;">Bạn chưa chọn file danh sách !</span></div>
                                                </c:if> 
                                                 
                                                <c:if test="${error == 'systemBusy'}">
                                                    <div><span style="color:red;">${msgErrorStr}</span></div>
                                                </c:if>   
                                                <c:if test="${error != 'errorFormat' && error != 'errorFile' && error != 'maxQual' && error != 'systemBusy'}">
                                                    <div class="text-primary">Đăng số thành công</div>
                                                    <div>Số lượng thuê bao: <span style="color:red;">${total}</span></div>
                                                    <div>Số lượng thuê bao đăng thành công: <span style="color:red;">${countSuccess}</span></div>
                                                    <div>Số lượng thuê bao đăng thất bại: <span style="color:red;">${countError}</span>
                                                        <c:if test="${countError > 0}">
                                                            <c:set var="data" value=""></c:set>
                                                            <c:forEach var="error" items="${listError}">
                                                                <c:set var="data" value="${data.concat(error).concat(';')}" />
                                                            </c:forEach>
                                                            <a href="javascript:void(0);" class="text-blue" style="margin-left:100px;" onclick="downloadErrorList('${data}');">Download danh sách thuê bao đăng thất bại</a>
                                                        </c:if>
                                                    </div>
                                                    <c:if test="${custService != null}">
                                                        <div>Gói đang sử dụng: <span style="color:blue;">${custService.confPackage.packageName}</span></div>
                                                        <div>Thời hạn sử dụng: <span style="color:blue;"><fmt:formatDate value="${custService.expiredDate}" pattern="HH:mm:ss dd/MM/yyyy"/></span></div>
                                                        <div>Bạn còn được đăng: <span style="color:blue;">${totalUpload}</span> số.</div>
                                                    </c:if>

                                                </c:if>    

                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${countSuccess != null}">   
                                                   
                                     <c:if test="${error == 'maxQual'}">
                                                    <div><span style="color:red;">${msgErrorStr}</span></div>
                                                    <%--<c:if test="${msgError1 != null}">--%>
                                                    <div class="form-group m-b-xs">
                                                        <div class="col-lg-12">
                                                            <c:if test="${!empty listPackage}">
                                                                <div>Lựa chọn các gói đăng số dưới đây để đăng được nhiều số hơn.</div>
                                                                <div class="table-responsive">
                                                                    <table class="table table-striped b-t b-light text-sm list-group">
                                                                        <thead>
                                                                            <tr>
                                                                                <th style="text-align:center;" width="2%">STT</th>
                                                                                <th style="text-align:left;" width="12%">Tên gói</th>
                                                                                <th style="text-align:center;" width="8%">Mã gói</th>
                                                                                <th style="text-align:left;" width="15%">Số lượng tối đa có thể đăng</th>
                                                                                <th style="text-align:left;" width="7%">Chu kỳ(ngày)</th>
                                                                                <th style="text-align:left;" width="7%">Phí(Vnđ)</th>
                                                                                <th style="text-align:left;" width="45%">Cách thức đăng ký</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <c:set var="k" value="${start}"></c:set>
                                                                            <tbody class="list">
                                                                            <c:forEach var="items" items="${listPackage}">
                                                                                <tr>
                                                                                    <td style="text-align: center;">${k=k+1}</td>
                                                                                    <td style="text-align: left;">${items.packageName}</td>
                                                                                    <td style="text-align: center;">${items.packageCode}</td>
                                                                                    <td style="text-align: left;">${items.maxQuantity}</td>
                                                                                    <td style="text-align: left;">${items.period}</td>
                                                                                    <c:if test="${items.fee ==0 }">
                                                                                        <td style="text-align: left;">Khuyến mại: 0đ</td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee !=0 }">
                                                                                        <td style="text-align: left;"><span class="currencyHtml">${items.fee}</span></td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee ==0 }">
                                                                                        <td style="text-align: left;"> Trong giai đoạn triển khai. Gói G1 được miễn phí ngay sau khi đăng ký tài khoản </td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee !=0 }">
                                                                                        <td style="text-align: left;"> Soạn tin HT ${items.packageCode} <%=request.getRemoteUser()%> gửi 8655 </td>
                                                                                    </c:if>
                                                                                    <!--<td style="text-align: left;"><span class="currencyHtml">${items.fee}</span></td>-->
                                                                                    <!--<td style="text-align: left;">Soạn tin HT ${items.packageCode} <%=request.getRemoteUser()%> gửi đến 8655(10,000 Vnđ/SMS)</td>-->
        
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                <%--</c:if>--%>
                                                </c:if>                
                                                   
                                     </c:if>  

                                </form>

                            </div>
                            <div class="tab-pane fade ${ uploadtype == 'multi' ? 'active in' : '' }" id="tabmulti">
                                <form class="bs-example form-horizontal" data-validate="parsley" id="frmAddMulti" enctype="multipart/form-data" action="uploadMsisdn" method="post">
                                    <input type="hidden" name="uploadtype" value="multi"/>
                                    <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                        <i class="fa fa-foursquare"></i> Thông tin đăng số
                                    </h5>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l">Loại file:
                                        </span>
                                        <div class="col-lg-4">
                                            <select name="filetype" class="form-control input-sm" id="sltFileType">
                                                <option value="0" ${param.filetype == null || param.filetype == 0 ? 'selected' : ''}>xls</option>
                                                <!--<option value="1" ${param.filetype != null && param.filetype == 1 ? 'selected' : ''}>txt</option>-->
                                            </select>
                                        </div>
                                            <a id="btnDwn" class="col-lg-4 text-blue" style="padding-top: 7px; font-weight: 400" href="downloadtemplate?filetype=xls" target="_blank">Tải xuống file mẫu</a>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l">Danh sách:
                                        </span>
                                        <div class="col-lg-6">
<!--                                            <input type="file" class="filestyle" data-icon="false" accept="${param.filetype == null || param.filetype == 0 ? '.xls' : '.txt'}" data-classButton="btn btn-default"
                                                   id="uploadfile" name="uploadfile" data-classInput="form-control inline input-s" />-->
                                            <label class="btn btn-default btn-file">
                                                <i class="fa fa-cloud-upload text">&nbsp;</i>Chọn tập tin<input type="file" style="display: none;" data-icon="false" accept=".xls" data-classButton="btn btn-default btn-file"
                                                                   id="uploadfile" name="uploadfile" data-classInput="form-control inline input-s" />
                                            </label>
                                            <label id ="fileName"> </label>
                                        </div>
                                    </div>
                                    <div class="form-group m-b-xs">
                                        <span class="col-lg-2 control-span m-l"></span>
                                        <div class="col-lg-6">
                                            <!-- <input type="submit" class="btn btn-sm btn-primary"
                                    value="Lưu" style="width: 90px;" />  -->
                                            <input type="submit" id="btnSaveMulti" style="display: none;"
                                                   onclick="return addAuctionMulti();" /> <a
                                                   class="btn btn-sm btn-primary" id="linkSaveMulti"
                                                   onclick="$('#btnSaveMulti').click();" style="width: 90px;"><i
                                                    class="fa fa-save"></i> Đăng số</a> <a href="javascript: back();"
                                                                               class="btn btn-sm btn-danger" style="width: 90px;">Hủy bỏ</a>
                                        </div>
                                    </div>
                                    <c:if test="${countSuccess == null && error == null}">                
                                    <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                        <i class="fa fa-foursquare"></i> Hướng dẫn đăng số
                                    </h5>
                                    <div class="form-group m-b-xs" style="padding: 3px;">
                                        <div class="col-lg-8">
                                            <span class="control-span m-l">Tải file excel mẫu từ link <strong>Tải xuống file mẫu</strong> (nếu như chưa có mẫu), sau đó nhập thông tin các thuê bao như file mẫu.</span>
                                                
                                            <img src="/assets/filedemo/demo.JPG"  style="padding: 15px 15px 15px 26px ">
                                            <ul>
                                                <li>Thông tin số thuê bao là số điện thoại bắt đầu có thể bắt đầu  bằng 0, 84 , +84.</li>
                                                <li>Thông tin số thuê bao có thể bao gồm ký tự "." để phân tách số ví dụ: 0931.888.888</li>
                                                <li>Giá bán nằm trong khoảng giá từ 0 -> 99.000.000.000 đồng.</li>
                                                <li>Thông tin mô tả về thuê bao có thể để trống.</li>
                                            </ul>
                                        <span class="control-span m-l">Nhấn vào <strong>Chọn tập tin</strong> để mở file danh sách số và nhấn vào <strong>Lưu</strong> để đẩy danh sách số lên kho cá nhân.</span>  
                                        
                                        </div>
                                    </div> 
                                    </c:if>
                                    <c:if test="${countSuccess != null}">
                                        <div class="form-group m-b-xs">
                                            <span class="col-lg-2 control-span m-l"></span>
                                            <div class="col-lg-6 control-span">
                                                <c:if test="${error == 'errorFormat'}">
                                                    <div><span style="color:red;">File không đúng format!</span></div>
                                                </c:if>
                                                <c:if test="${error == 'errorFile'}">
                                                    <div><span style="color:red;">Bạn chưa chọn file danh sách !</span></div>
                                                </c:if> 
                                                 
                                                <c:if test="${error == 'systemBusy'}">
                                                    <div><span style="color:red;">${msgErrorStr}</span></div>
                                                </c:if>   
                                                <c:if test="${error != 'errorFormat' && error != 'errorFile' && error != 'maxQual' && error != 'systemBusy'}">
                                                    <div class="text-primary">Đăng số thành công</div>
                                                    <div>Số lượng thuê bao: <span style="color:red;">${total}</span></div>
                                                    <div>Số lượng thuê bao đăng thành công: <span style="color:red;">${countSuccess}</span></div>
                                                    <div>Số lượng thuê bao đăng thất bại: <span style="color:red;">${countError}</span>
                                                        <c:if test="${countError > 0}">
                                                            <c:set var="data" value=""></c:set>
                                                            <c:forEach var="error" items="${listError}">
                                                                <c:set var="data" value="${data.concat(error).concat(';')}" />
                                                            </c:forEach>
                                                            <a href="javascript:void(0);" class="text-blue" style="margin-left:100px;" onclick="downloadErrorList('${data}');">Download danh sách thuê bao đăng thất bại</a>
                                                        </c:if>
                                                    </div>
                                                    <c:if test="${custService != null}">
                                                        <div>Gói đang sử dụng: <span style="color:blue;">${custService.confPackage.packageName}</span></div>
                                                        <div>Thời hạn sử dụng: <span style="color:blue;"><fmt:formatDate value="${custService.expiredDate}" pattern="HH:mm:ss dd/MM/yyyy"/></span></div>
                                                        <div>Bạn còn được đăng: <span style="color:blue;">${totalUpload}</span> số.</div>
                                                    </c:if>

                                                </c:if>    

                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${countSuccess != null}">   
                                                   
                                     <c:if test="${error == 'maxQual'}">
                                                    <div><span style="color:red;">${msgErrorStr}</span></div>
                                                    <%--<c:if test="${msgError1 != null}">--%>
                                                    <div class="form-group m-b-xs">
                                                        <div class="col-lg-12">
                                                            <c:if test="${!empty listPackage}">
                                                                <div>Lựa chọn các gói đăng số dưới đây để đăng được nhiều số hơn.</div>
                                                                <div class="table-responsive">
                                                                    <table class="table table-striped b-t b-light text-sm list-group">
                                                                        <thead>
                                                                            <tr>
                                                                                <th style="text-align:center;" width="2%">STT</th>
                                                                                <th style="text-align:left;" width="12%">Tên gói</th>
                                                                                <th style="text-align:center;" width="8%">Mã gói</th>
                                                                                <th style="text-align:left;" width="15%">Số lượng tối đa có thể đăng</th>
                                                                                <th style="text-align:left;" width="7%">Chu kỳ(ngày)</th>
                                                                                <th style="text-align:left;" width="7%">Phí(Vnđ)</th>
                                                                                <th style="text-align:left;" width="45%">Cách thức đăng ký</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <c:set var="k" value="${start}"></c:set>
                                                                            <tbody class="list">
                                                                            <c:forEach var="items" items="${listPackage}">
                                                                                <tr>
                                                                                    <td style="text-align: center;">${k=k+1}</td>
                                                                                    <td style="text-align: left;">${items.packageName}</td>
                                                                                    <td style="text-align: center;">${items.packageCode}</td>
                                                                                    <td style="text-align: left;">${items.maxQuantity}</td>
                                                                                    <td style="text-align: left;">${items.period}</td>
                                                                                    <c:if test="${items.fee ==0 }">
                                                                                        <td style="text-align: left;">Khuyến mại: 0đ</td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee !=0 }">
                                                                                        <td style="text-align: left;"><span class="currencyHtml">${items.fee}</span></td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee ==0 }">
                                                                                        <td style="text-align: left;"> Trong giai đoạn triển khai. Gói G1 được miễn phí ngay sau khi đăng ký tài khoản </td>
                                                                                    </c:if>
                                                                                    <c:if test="${items.fee !=0 }">
                                                                                        <td style="text-align: left;"> Soạn tin HT ${items.packageCode} <%=request.getRemoteUser()%> gửi 8655 </td>
                                                                                    </c:if>
                                                                                    <!--<td style="text-align: left;"><span class="currencyHtml">${items.fee}</span></td>-->
                                                                                    <!--<td style="text-align: left;">Soạn tin HT ${items.packageCode} <%=request.getRemoteUser()%> gửi đến 8655(10,000 Vnđ/SMS)</td>-->
        
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                <%--</c:if>--%>
                                                </c:if>                
                                                   
                                     </c:if>               
                                                   
                                    <c:if test="${countSuccess == null && param.file != null}">
                                        <div class="form-group m-b-xs">
                                            <span class="col-lg-2 control-span m-l"></span>
                                            <div class="col-lg-6 control-span">
                                                <div style="color:red;">Đã xảy ra lỗi trong quá trình đăng số. Kiểm tra lại danh sách số hoặc liên hệ quản trị để được hỗ trợ !</div>
                                            </div>
                                        </div>
                                    </c:if>

                                </form>
                            </div>
                        </div>
                    </div>
                </section>
        </section>
        <%--<%@include file="../../views/layoutCus/footer.jsp"%>--%>
    </section>
</section>
<script src="<%=request.getContextPath()%>/resources/js/bootstrap-filestyle.min.js"></script>
<script type="text/javascript">
    function addAuction() {
        if ($("#frmAdd").parsley().isValid())
            $("#linkSave").removeAttr("onclick");
        return true;
    }
    function addAuctionMulti() {
        if ($("#frmAddMulti").parsley().isValid())
            $("#linkSaveMulti").removeAttr("onclick");
        return true;
    }
    function back() {
        if (confirm("Bạn có chắc chắn muốn hủy bỏ?")) {
            window.location = "index";
        }
    }
    $("#sltFileType").change(function () {
        if (this.value == 1) {
            $("#uploadfile").attr("accept", ".txt");
            $("#btnDwn").attr("href", "downloadtemplate?filetype=txt");
        } else {
            $("#uploadfile").attr("accept", ".xls");
            $("#btnDwn").attr("href", "downloadtemplate?filetype=xls");
        }
    });
    
    $("#uploadfile").change(function () {
        if (this.value != null) {
            $("#fileName").html(this.value.split(/(\\|\/)/g).pop());
        } else {
            $("#fileName").html("");
        }
    });
    $("#fileName").html("");
    function downloadErrorList(text) {
        var pom = document.createElement('a');
        var arr = text.substring(0, text.length - 1).split(';');
        text = "";
        for (var i = 0; i < arr.length; i++)
        {
            text += arr[i] + "\r\n";
        }
        pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
        pom.setAttribute('download', "Danh sach so thue bao loi.txt");

        if (document.createEvent) {
            var event = document.createEvent('MouseEvents');
            event.initEvent('click', true, true);
            pom.dispatchEvent(event);
        } else {
            pom.click();
        }
    }
    
    
    
     function changeMethod(type) {
        
        if(type =="1"){
            $("#btnsingle").removeClass("btn-default");
            $("#btnsingle").addClass("btn-primary");
            $("#btnmulti").removeClass("btn-primary");
            $("#btnmulti").addClass("btn-default");
            
        }else{
            $("#btnmulti").removeClass("btn-default");
            $("#btnmulti").addClass("btn-primary");
            $("#btnsingle").removeClass("btn-primary");
            $("#btnsingle").addClass("btn-default");
            
        }
        
        
    }
    
    
    
    $(document).ready(function () {
        
        $("#msisdnUpload").focus();
        $("#typeShow1").change(function () {
            if ($("#typeShow1").is(":checked")) {
                $("#typeShow2").removeAttr("checked");
                $("#typeShow3").removeAttr("checked");
            }

        });

        $("#typeShow2").change(function () {
            if ($("#typeShow2").is(":checked")) {
                $("#typeShow1").removeAttr("checked");
                $("#typeShow3").removeAttr("checked");
            }

        });

        $("#typeShow3").change(function () {
            if ($("#typeShow3").is(":checked")) {
                $("#typeShow1").removeAttr("checked");
                $("#typeShow2").removeAttr("checked");
            }

        });

    });

</script>
