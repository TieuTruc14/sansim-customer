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
    .warning{
        color: red;
    }
</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul
                class="breadcrumb no-border no-radius b-b b-light pull-in bg-primary">
                <li><a href=""><i class="fa fa-book"></i> Quản lý người
                        dùng</a></li>
                <li><a href=""><i class="fa fa-book"></i> Cập nhật thông tin tài khoản</a></li>
            </ul>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel panel-default">
                        <div class="panel-body">
                            <form class="bs-example form-horizontal" id="frmChangePassword" data-validate="parsley" >
                                <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                    <i class="fa fa-foursquare"></i> Đổi mật khẩu
                                </h5>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Mật khẩu cũ <span class="warning" >(*)</span>: </span>
                                    <div class="col-lg-4">
                                        <input type="password" name="curPass" id="curPass"
                                               class="form-control input-sm"
                                               placeholder="Mật khẩu cũ" 
                                               data-trigger="change"
                                               data-validation-minlength="1"
                                               data-required="true"
                                               data-required-message="<font color=green>Mật khẩu cũ</font> không được bỏ trống"
                                               data-minlength="8"
                                               data-minlength-message="Độ dài ít nhất 8 ký tự"
                                               maxlength="100"
                                               data-regexp="^[A-Za-z0-9_-]+$"
                                               data-regexp-message="Mật khẩu không hợp lệ (Mật khẩu chỉ được gồm các kí tự A - z, số, dấu gạch dưới và dấu gạch ngang.)">
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Mật khẩu <span class="warning" >(*)</span>: </span>
                                    <div class="col-lg-4">
                                        <input type="password" name="password" id="password"
                                               class="form-control input-sm"
                                               data-trigger="change"
                                               data-validation-minlength="1"
                                               placeholder="Mật khẩu" data-required="true"
                                               data-required-message="<font color=green>Mật khẩu</font> không được bỏ trống"
                                               data-minlength="8"
                                               data-minlength-message="Độ dài ít nhất 8 ký tự"
                                               maxlength="100"
                                               data-regexp="^[A-Za-z0-9_-]+$"
                                               data-regexp-message="Mật khẩu không hợp lệ (Mật khẩu chỉ được gồm các kí tự A - z, số, dấu gạch dưới và dấu gạch ngang.)">
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Nhập lại mật khẩu <span class="warning" >(*)</span>: </span>
                                    <div class="col-lg-4">
                                        <input type="password" name="confirmpassword"
                                               class="form-control input-sm"
                                               placeholder="Nhập lại mật khẩu" data-required="true"
                                               data-trigger="change"
                                               data-validation-minlength="1"
                                               data-required-message="<font color=green>Xác nhận mật khẩu</font> không được bỏ trống"
                                               data-equalto="#password"
                                               data-equalto-message="Xác nhận mật khẩu không chính xác">
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l"></span>
                                    <div class="col-lg-6">
                                        <input type="button" class="btn btn-sm btn-primary"
                                               value="Đổi mật khẩu" onclick="changePassword();" />
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel panel-default">
                        <div class="panel-body">
                            <form class="bs-example form-horizontal" data-validate="parsley"
                                  action="sua-thong-tin-nguoi-dung.html" method="post" id="frmInfo" >
                                <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                    <i class="fa fa-foursquare"></i> Thông tin tài khoản
                                </h5>
                                <input type="hidden" name="id" value="${user.id }" />
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Tài khoản đăng
                                        nhập: </span> <span class="col-lg-2 control-span m-l">${user.username }</span>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Số điện thoại: </span> <span class="col-lg-2 control-span m-l">${user.msisdn }</span>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Họ tên: </span>
                                    <div class="col-lg-3">
                                        <input type="text" name="fullname"
                                               class="form-control input-sm" value="${user.full_name }"
                                               placeholder="Họ tên" data-required="true"
                                               data-trigger="change"
                                               data-required-message="<font color=green>Họ tên</font> không được bỏ trống"
                                               data-regexp="^[a-zA-Z0-9_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ_\s]+$"
                                               data-maxlength="50"
                                               data-maxlength-message="Độ dài không được vượt quá 50 ký tự" />
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Địa chỉ: </span>
                                    <div class="col-lg-4">
                                        <input type="text" name="address"
                                               class="form-control input-sm" value="${user.address }"
                                               placeholder="Địa chỉ" 
                                               data-maxlength="100"
                                               data-maxlength-message="Độ dài không được vượt quá 200 ký tự" />
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Thông tin chi
                                        tiết: </span>
                                    <div class="col-lg-4">
                                        <textarea id="editor" class="form-control"
                                                  style="overflow: scroll; height: 100px; max-height: 100px"
                                                  contenteditable="true" placeholder="Thông tin chi tiết"
                                                  name="description" 
                                                  data-maxlength="200" data-maxlength-message="Độ dài không được vượt quá 200 ký tự">${user.description }</textarea>
                                    </div>
                                </div>    
                                <div class="line line-dashed line-lg pull-in"></div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l"></span>
                                    <div class="col-lg-6">
                                        <input type="button" class="btn btn-sm btn-primary"
                                               value="Lưu" style="width: 90px;" onclick="changeInfo();" /> 
                                        <a href="javascript:void(0);" data-toggle="modal" data-target="#confirm-cancel-edit"
                                           class="btn btn-sm btn-danger" style="width: 90px;">Hủy bỏ</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <div class="modal fade" id="confirm-cancel-edit" tabindex="-1" role="dialog"
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
                        <a class="btn btn-primary btn-ok" href="index.html"><i class="fa fa-check"></i> Đồng ý</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Không</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<script type="text/javascript">
    function back() {
        if (confirm("Bạn có chắc chắn muốn hủy bỏ?")) {
            window.location = "index.html";
        }
    }
    function changePassword() {
        if ($("#frmChangePassword").parsley().validate()) {
            var formdata = $("#frmChangePassword").serialize();
            $.ajax({
                url: "<%= request.getContextPath() %>/customer/changePass",
                type: "POST",
                data: formdata,
                success: function (data) {
                    if(data =='newPassEmpty'){
                        alert('Mật khẩu mới không được để trống. Vui lòng kiểm tra lại!');
                    }else if(data =='confirmPassEmpty'){
                        alert('Nhập lại mật khẩu không được để trống. Vui lòng kiểm tra lại!');
                    }else if(data =='notMatch'){
                        alert('Nhập lại mật khẩu không khớp. Vui lòng kiểm tra lại!');
                    }else if(data =='curPassWrong'){
                        alert('Mật khẩu cũ không đúng. Vui lòng kiểm tra lại!');
                    }else if(data =='success'){   
                        alert('Cập nhật thông tin thành công!');
                    }else{
                        alert('Cập nhật thông tin thất bại. Vui lòng thử lại sau!');
                    }
                },
                error: function (data) {
                    alert("Cập nhật thông tin thất bại. Vui lòng thử lại sau!");
                }
            });
        }
        return false;
    }
    function changeInfo() {
        if ($("#frmInfo").parsley().validate()) {
            var formdata = $("#frmInfo").serialize();
            $.ajax({
                url: "<%= request.getContextPath() %>/customer/editProfile",
                type: "POST",
                data: formdata,
                success: function (data) {
                    if(data =='error'){
                        alert('Cập nhật thông tin thất bại. Vui lòng thử lại sau!');
                    }else if(data =='success'){   
                        alert('Cập nhật thông tin thành công!');
                    }else{
                        alert('Cập nhật thông tin thất bại. Vui lòng thử lại sau!');
                    }
                },
                error: function (data) {
                    alert("Cập nhật thông tin thất bại. Vui lòng thử lại sau!");
                }
            });
        }
        return false;
    }
    $("#curPass").focus();
</script>