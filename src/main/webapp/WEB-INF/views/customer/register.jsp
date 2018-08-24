<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <title>Đăng ký tài khoản</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->

        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>-->
        <!--<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />-->
        <!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
        <script src="<%=request.getContextPath()%>/assets/js/parsley/parsley.min.js" ></script>
        <script src="<%=request.getContextPath()%>/assets/js/parsley/parsley.extend.js" ></script>
        <script src="<%=request.getContextPath()%>/assets/js/CommonFunction.js" ></script>
        <script src="<%=request.getContextPath()%>/assets/js/bootstrap3-dialog/bootstrap-dialog.min.js" ></script>
        <!--<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap.css" type="text/css" />-->




        <style>
            .form-register {
                max-width: 650px;
            }
            .warning{
                color:red;
            }

            @media ( min-width : 768px) {
                .form-horizontal .control-label {
                    padding-left: 0;
                    padding-right: 0;
                }
                .nopadding{
                    padding:0;
                }
            }

            .form-control1 {
                display: block;
                width: 100%;
                height: 34px;
                padding: 6px 12px;
                font-size: 14px;
                line-height: 1.42857143;
                color: #555;
                background-color: #fff;
                background-image: none;
                border: 1px solid #ccc;
                border-radius: 4px;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
                -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
                -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
                transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
            }
            .form-control1:focus {
              border-color: #66afe9;
              outline: 0;
              -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
                      box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
            }
            .form-control1::-moz-placeholder {
              color: #999;
              opacity: 1;
            }
            .form-control1:-ms-input-placeholder {
              color: #999;
            }
            .form-control1::-webkit-input-placeholder {
              color: #999;
            }
           .form-control1[disabled],
            .form-control1[readonly],
            fieldset[disabled] .form-control1 {
              background-color: #eee;
              opacity: 1;
            }
            .form-control1[disabled],
            fieldset[disabled] .form-control1 {
              cursor: not-allowed;
            }
            textarea.form-control1 {
                height: auto;
            }
            .bootstrap-dialog-header{
                width: 100%;
            }



        </style>
        <script type="text/javascript">
            function changeCaptcha() {
                var _uri = $("body").attr("uri");
                $("#imgCaptcha").attr("src", "<%=request.getContextPath()%>/captcha?u=" + new Date());
            }
            function register1() {
                if ($("#frmRegister").parsley().validate()) {
                    $("#btnRegister").attr("disabled", "disabled");
                    var $form = $("#frmRegister");
                    var formdata = $("#frmRegister").serialize();
                    var _uri = $("body").attr("uri");
                    $.ajax({
                        url: "register",
                        type: "POST",
                        data: formdata,
                        success: function (data) {
                            if (data == 'captcha') {
                                show_dialog("Thông báo", "Mã xác nhận không chính xác", "#captcha");
                            } else if (data == 'ok') {
                                BootstrapDialog.show({
                                    title: "Thông báo",
                                    message: "Đăng ký thành công !\n Vui lòng dùng số điện thoại bạn đã đăng ký soạn <span style=\"font-weight: 600; color:blue;\">AT</span> gửi tới đầu số <span style=\"font-weight: 600; color:blue;\">8055</span>(1.000 Vnđ/SMS) để kích hoạt tài khoản. \nLink gian hàng của bạn là <a href =\"http://"+ $("#username").val() +".sansim.vn\" style =\"text-decoration: underline; color:blue;font-weight: 600; \"> http://"+ $("#username").val() +".sansim.vn</a>.\nXin cảm ơn !",
                                    buttons: [
                                        {
                                            label: 'Đóng',
                                            action: function (dialogItself) {
                                                dialogItself.close();
                                                if (_focus != undefined)
                                                {
                                                    $(_focus).focus();
                                                    if ($(_focus).is("select")) {
                                                        $(_focus).select2("open");
                                                    }
                                                }
                                            }
                                        }],
                                    onhidden: function (dialogRef) {
                                        window.location.href = "<%=request.getContextPath()%>/login";
                                    }
                                });
                            } else if (data == 'alreadyLogin') {
                                show_dialog("Thông báo", "Bạn đã đăng nhập rồi, vui lòng thoát trước khi tiếp tục");
                            } else if (data == 'requiredFail') {
                                show_dialog("Thông báo", "Bạn cần nhập đầy đủ các thông tin bắt buộc (các thông tin có dấu \"*\")");
                            } else if (data == 'msisdnSystax') {
                                show_dialog("Thông báo", "Số điện thoại không hợp lệ");
                            } else if (data == 'userNameSystax') {
                                show_dialog("Thông báo", "Tài khoản không hợp lệ. Tài khoản chỉ bao gồm chữ và số");
                            } else if (data == 'emailSystax') {
                                show_dialog("Thông báo", "Email không hợp lệ");
                            } else if (data == 'birthDaySystax') {
                                show_dialog("Thông báo", "Ngày sinh không hợp lệ");
                            } else if (data == 'confirmPassSystax') {
                                show_dialog("Thông báo", "Xác nhận mật khẩu không chính xác");
                            } else if (data == 'msisdnAlready') {
                                show_dialog("Thông báo", "Số điện thoại đã tồn tại trên hệ thống");
                            } else if (data == 'userNameAlready') {
                                show_dialog("Thông báo", "Tài khoản đã tồn tại trên hệ thống");
                            } else if (data == 'registerFail') {
                                show_dialog("Thông báo", "Đăng ký thất bại. Vui lòng thử lại sau");
                            } else if (data == 'isBlackList') {
                                show_dialog("Thông báo", "Tài khoản không hợp lệ. Tên tài khoản trên đã được sử dụng bởi hệ thống. Vui lòng sử dụng tên khác!");
                            } else {
                                show_dialog("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại");
                            }
                            $("#btnRegister").removeAttr("disabled");
                        },
                        error: function (data) {
                            show_dialog("Thông báo", "Có lỗi trong quá trình xử lý, vui lòng thử lại");
                            $("#btnRegister").removeAttr("disabled");
                        }
                    });
                }
                return false;
            }

            function show_dialog(_title, _message, _focus)
            {
                BootstrapDialog.show({
                    title: _title,
                    message: _message,
                    buttons: [
                        {
                            label: 'Đóng',
                            action: function (dialogItself) {
                                dialogItself.close();
                                if (_focus != undefined)
                                {
                                    $(_focus).focus();
                                    if ($(_focus).is("select")) {
                                        $(_focus).select2("open");
                                    }
                                }
                            }
                        }]
                });
            }

        </script>
    </head>
    <body>
        <div class="p-login">
            <div class="text-center">
                <h1 class="page-title-center mb-2 mt-2">Đăng Ký</h1>
            </div>
            <form:form action="register" modelAttribute="userRegister" id="frmRegister" data-validate="parsley"
                       method="post" class="form-horizontal panel-body wrapper-lg">
                <div class="form-group">
                    <div class="form-group">
                        <label>Tài khoản <span>(*)</span></label> 
                        <form:input path="username" cssClass="form-control enter" id ="username" placeholder="Tài khoản"
                                    data-trigger="change"
                                    data-required="true"
                                    data-required-message="<font color='red' >Tài khoản không được phép bỏ trống</font>"
                                    data-maxlength="20" data-minlength="5"
                                    data-maxlength-message="<font color='red' >Độ dài không được vượt quá 15 ký tự</font>"
                                    data-minlength-message="<font color='red' >Độ dài tối thiểu là 5 ký tự</font>"
                                    />
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại <span>(*)</span></label>   
                        <form:input path="msisdn" cssClass="form-control enter" placeholder="Số điện thoại"
                                    data-trigger="change"
                                    data-required="true"
                                    data-required-message="<font color='red' >Số điện thoại không được phép bỏ trống</font>"
                                    data-maxlength="15" data-minlength="10"
                                    data-maxlength-message="<font color='red' >Độ dài không được vượt quá 15 ký tự</font>"
                                    data-minlength-message="<font color='red' >Độ dài tối thiểu là 10 ký tự</font>"
                                    data-type="number"
                                    data-type-number-message="<font color='red' >Số điện thoại chỉ được phép nhập kiểu số</font>" />
                        <label><span>Vui lòng nhập chính xác số điện thoại. Hệ thống sẽ gửi những tin nhắn thông báo đến số điện thoại này!</span></label>   
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu <span>(*)</span></label>   
                        <form:password path="password" data-trigger="change"
                                       cssClass="form-control enter" data-required="true"
                                       data-required-message="<font color='red' >Mật khẩu không được bỏ trống</font>"
                                       data-maxlength="50"
                                       data-maxlength-message="<font color='red' >Độ dài mật khẩu không được vượt quá 50 ký tự</font>"
                                       data-minlength="8"
                                       data-minlength-message="<font color='red' >Độ dài không được dưới 8 ký tự</font>"
                                       maxlength="50" />
                    </div> 
                    <div class="form-group">
                        <label>Xác nhận mật khẩu <span>(*)</span></label>   
                        <input id="confirmPassword" type="password" name="confirmPassword" class="form-control enter"
                               maxlength="50" 
                               data-trigger="change"
                               data-required="true"
                               data-required-message="<font color='red' >Xác nhận mật khẩu không được bỏ trống</font>"
                               data-maxlength="50"
                               data-maxlength-message="<font color='red' >Độ dài không được vượt quá 50 ký tự</font>"
                               data-minlength="8"
                               data-minlength-message="<font color='red' >Độ dài không được dưới 8 ký tự</font>"
                               data-equalto="#password"
                               data-equalto-message="<font color='red' >Xác nhận mật khẩu không chính xác</font>"
                               />
                    </div>
                    <div class="form-group">
                        <label>Tên hiển thị của đại lý<span>(*)</span></label> 
                        <form:input path="full_name" cssClass="form-control enter"  placeholder=""
                                    data-trigger="change"
                                    maxlength="50" data-required="true"
                                    data-required-message="<font color='red'>Họ và tên không được bỏ trống</font>"
                                    data-maxlength="50"
                                    data-maxlength-message="<font color='red'>Độ dài không được vượt quá 50 ký tự</font>"/>
                    </div>
                    <div class="form-group">
                        <label>Mô tả</label>
                        <form:textarea path="description" class="form-control1 enter" rows="5"
                                       placeholder=""></form:textarea>

                        </div>
                        <div class="form-group">
                            <label>Địa chỉ</label>
                        <form:textarea path="address" class="form-control1 enter" rows="5" 
                                       placeholder=""></form:textarea>
                        </div>

                        <div class="form-group">
                            <label>Mã xác nhận <span>(*)</span></label>   
                            <div class="clearfix"> 
                                <div class="confirm-left">
                                    <input type="text" class="form-control enter" id="captcha"
                                           placeholder="Mã xác nhận" name="captcha" data-required="true"
                                           data-required-message="<font color='red'>Mã xác nhận không được bỏ trống</font>">
                                </div>
                                <div class="confirm-right">
                                    <img src="<%=request.getContextPath()%>/captcha"
                                     style="height: 34px" id="imgCaptcha"> <a
                                     href="javascript:void(0)" onclick="changeCaptcha();"><span
                                        class="fa fa-refresh"></span></a>
                            </div>

                        </div>
                        <label><span>Sau khi đăng ký tài khoản thành
                                công. Vui lòng dùng số điện thoại bạn đã đăng ký soạn AT gửi tới
                                đầu số 8055(1.000 Vnđ/SMS) để kích hoạt tài khoản. Xin cảm ơn!</span></label>   
                    </div>
                    <div class="form-group">
                        <a class="btn btn-primary" href="javascript:void(0)"
                           onclick="register1();" id="btnRegister" ><span style="color: white;"> Đăng ký tài khoản</span> </a> 
                        <input type="reset" class="btn btn-secondary" value="Làm lại" onclick="$('#frmRegister').parsley().reset();
                                $('#username').focus();" >
                    </div>
                </div>
            </form:form>			
        </div>
        <%--<c:import url="popup.jsp"></c:import>--%>

    </body>
</html>