<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <li><a href=""><i class="fa fa-book"></i> Quản lý gian hàng</a></li>
            </ul>
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel panel-default">
                        <div class="panel-body">
                            <form class="bs-example form-horizontal" id="frmChangeShopInfo" data-validate="parsley" >
                                <h5 class="page-header m-t-xs" style="font-weight: 600;">
                                    <i class="fa fa-foursquare"></i> Thay đổi thông tin gian hàng
                                </h5>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Tài khoản đăngnhập: </span> 
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control input-sm" name="username"  value="${user.username }" 
                                               disabled="true"/>
                                    </div>
                                </div>
                                <br/>
                                <div class="form-group m-b-xs">
                                    <label class="col-lg-2 control-span m-l">Link website gian hàng(<font color="red">*</font>)</label>
                                    <div class="col-sm-4">
                                        <input TYPE="radio" NAME="typedomain1" id ='typedomain1' VALUE="${user.username}.sansim.vn"  ${ user.domain != null && user.domain == user.username.toString().concat(".sansim.vn".toString()) ? "checked" : ""}    > <span style='color: #00F;  text-decoration: underline;'>http://${user.username }.sansim.vn </span>
                                        <br/>
                                        <input TYPE="radio" NAME="typedomain2" id ='typedomain2' VALUE="sansim.vn/${user.username }"  ${ user.domain != null && user.domain == ("sansim.vn/".toString().concat(user.username).toString()) ? "checked" : ""}    > <span  style='color: #00F;  text-decoration: underline;' >http://sansim.vn/${user.username} </span>
                                        <br/>
                                        <input TYPE="radio" NAME="typedomain3" id ='typedomain3' VALUE="ortherDomain"  ${ user.domain != null && user.domain != user.username.toString().concat(".sansim.vn".toString()) && user.domain != ("sansim.vn/".toString().concat(user.username).toString())  ? "checked" : ""}   > Link website khác (Khách hàng cấu hình website của mình vào ip server của hệ thống 127.0.0.1)
                                        <br/>
                                        <input type="text" class="form-control input-sm" value="${user.domain}" name ="textdomain3"  id ='textdomain3' ${ user.domain != null && user.domain != user.username.toString().concat(".sansim.vn".toString()) && user.domain != ("sansim.vn/".toString().concat(user.username).toString())  ? "" : "disabled"}
                                               data-maxlength="100"
                                               data-maxlength-message="Độ dài không được vượt quá 100 ký tự" />
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l">Bài viết giới thiệu:
                                    </span>
                                    <div class="col-lg-4">
                                        <textarea name="introduce" class="form-control input-sm" style="height: 295px;" 
                                                  data-trigger="change"
                                                  data-maxlength="10000"
                                                  data-maxlength-message="<font color=green>Bài viết giới thiệu</font> quá lớn!" >${user.introduce}</textarea>
                                    </div>
                                </div> 


                                <div class="form-group m-b-xs">
                                    <label class="col-lg-2 control-span m-l">Link bản đồ(<font color="red">*</font>)</label>
                                    <div class="col-sm-4">
                                        <input type="text" class="form-control input-sm" name="linkMap"  value="${fn:escapeXml(user.linkMap) }" 
                                               data-maxlength="1000"
                                               data-maxlength-message="Độ dài không được vượt quá 1000 ký tự" 
                                               data-required="true"
                                               data-required-message="<font color=green>Link bản đồ</font> không được bỏ trống"/>
                                    </div>
                                </div>
                                <div class="form-group m-b-xs">
                                    <span class="col-lg-2 control-span m-l"></span>
                                    <div class="col-lg-6">
                                        <input type="button" class="btn btn-sm btn-primary"
                                               value="Đổi thông tin gian hàng" onclick="changeInfo();" />
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
            window.location = "index";
        }
    }
    function changeInfo() {
        if ($("#frmChangeShopInfo").parsley().validate()) {
            var formdata = $("#frmChangeShopInfo").serialize();
            $.ajax({
                url: "<%= request.getContextPath()%>/customer/changeShopInfo",
                type: "POST",
                data: formdata,
                success: function (data) {
                    if (data == 'typedomain3Systax') {
                        alert('Link gian hàng khách hàng cung cấp không hợp lệ. Vui lòng kiểm tra lại!');
                    } else if (data == 'success') {
                        alert('Cập nhật thông tin thành công!');
                    } else if (data == 'domainIsExits') {
                        alert('Link gian hàng khách hàng cung cấp đã tồn tại. Vui lòng kiểm tra lại!');
                    }else {
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

    $(document).ready(function () {
        $("#typedomain1").change(function () {
            if ($("#typedomain1").is(":checked")) {
                $("#typedomain2").removeAttr("checked");
                $("#typedomain3").removeAttr("checked");
                $("#textdomain3").prop("disabled", true);
            }

        });

        $("#typedomain2").change(function () {
            if ($("#typedomain2").is(":checked")) {
                $("#typedomain1").removeAttr("checked");
                $("#typedomain3").removeAttr("checked");
                $("#textdomain3").prop("disabled", true);
            }

        });

        $("#typedomain3").change(function () {
            if ($("#typedomain3").is(":checked")) {
                $("#typedomain1").removeAttr("checked");
                $("#typedomain2").removeAttr("checked");
                $("#textdomain3").prop("disabled", false);
            }
        });
    });


</script>