<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- /.aside -->

<script  src="<%=request.getContextPath()%>/assets/project/customer/jquery.autocomplete.min.js"></script> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css"> 

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
</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul
                class="breadcrumb no-border no-radius b-b b-light pull-in bg-primary">
                <li><a href="customer/msisdn"><i class="fa fa-book"></i> Quản lý đăng số</a></li>
                <li><a href=""><i class="fa fa-book"></i> Chuyển sim cho người bán khác</a></li>
            </ul>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel panel-default">
                        <div class="panel-body">
                            <%@include file="../../views/notify.jsp"%>
                            <form class="bs-example form-horizontal" data-validate="parsley" id="frmAdd"
                                  action="forwardMsisdn" method="post">
                                <input type="hidden" name="id" value="${msi.id}"/>
                                <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                    <i class="fa fa-foursquare"></i> Thông tin số đăng bán
                                </h5>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Số thuê bao:</span>
                                    <div class="col-lg-4">
                                        ${msi.msisdn}
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Giá bán (VNĐ) <font
                                            color="red">*</font>: </span>
                                    <div class="col-lg-4">
                                        <input name="price" class="form-control input-sm currency" value="${msi.price}"
                                               data-required="true"
                                               data-trigger="change"
                                               data-required-message="<font color=green>Giá bán</font> không được bỏ trống"
                                               data-maxlength="13"
                                               data-maxlength-message="Độ dài không được vượt quá 10 ký tự"
                                               />
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Mô tả:
                                    </span>
                                    <div class="col-lg-4">
                                        <textarea name="description" class="form-control input-sm" 
                                                  data-trigger="change"
                                                  data-maxlength="500"
                                                  data-maxlength-message="<font color=green>Thông tin mô tả</font> quá lớn!" >${msi.description}</textarea>
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Username người nhận số:
                                    </span>
                                    <div class="col-lg-4">
                                        <input id ="userName" name="userName" class="form-control input-sm" 
                                               data-required="true"
                                               data-trigger="change"
                                               data-maxlength="20"
                                               data-maxlength-message="<font color=green>Thông tin username</font> quá lớn!" />
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
                                                class="fa fa-forward"></i> Chuyển số</a> 
                                        <a href="javascript:void(0);" data-toggle="modal" data-target="#confirm-cancel-forward"
                                           class="btn btn-sm btn-danger" style="width: 90px;">Hủy bỏ</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <div class="modal fade" id="confirm-cancel-forward" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="alert-warning modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><i class="fa fa-warning"></i> Cảnh báo</h4>
                    </div>
                    <div class="modal-body">Bạn có chắc chắn muốn hủy bỏ?</div>
                    <div class="modal-footer">
                        <a class="btn btn-primary btn-ok" href="msisdn"><i class="fa fa-check"></i> Đồng ý</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Không</button>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../../views/layoutCus/footer.jsp"%>
    </section>
</section>
<script type="text/javascript">
    function addAuction() {
        if ($("#frmAdd").parsley().isValid())
            $("#linkSave").removeAttr("onclick");
        return true;
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
