<%@ page pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>
<!-- /.aside -->
<sec:authorize access="hasRole('ROLE_USER')">   

    <section id="content" style="width: 100%;">
        <section class="vbox">
            <section class="scrollable padder">
                <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                    <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>

                </ul>
                    
                <section id="select_sim">
                    <div class="container" style="width:100%;">
                        <div class="text-center col-md-12">
                            GIÚP CÁC SỐ MUỐN BÁN CỦA BẠN ĐƯỢC NHIỀU NGƯỜI BIẾT ĐẾN NHẤT
                        </div>
                        <br /><br />
                        <div class="text-center col-md-6 col-sm-12 col-xs-12">
                            <div class="panel-group" id="accordionmsisdn">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a class="accordion-toggle" data-toggle="collapse"
                                               data-parent="#accordionmsisdn" href="#collapsemsisdn">
                                                Đăng số</a>
                                        </h4>
                                    </div>
                                    <div id="collapsemsisdn" class="panel-collapse collapse in">
                                        <div class="panel-body ">
                                            <div class="info-offset">
                                                <div class="row">
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/huong-dan-sgd/huong-dan-dang-so-14051.html">Hướng dẫn</a></div>
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/customer/uploadMsisdn">Đăng số</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-center col-md-6 col-sm-12 col-xs-12">
                            <div class="panel-group" id="accordionuser">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a class="accordion-toggle" data-toggle="collapse"
                                               data-parent="#accordionuser" href="#collapseuser">
                                                Quản lý đăng số</a>
                                        </h4>
                                    </div>
                                    <div id="collapseuser" class="panel-collapse collapse in">
                                        <div class="panel-body ">
                                            <div class="info-offset">
                                                <div class="row">
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/customer/huong-dan-quan-ly-dang-so-14052.html">Hướng dẫn</a></div>
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/customer/msisdn">Quản lý danh sách số</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

            </section>
                <%@include file="../../views/layoutCus/footer.jsp"%>
        </section>
        <a href="#" class="hide nav-off-screen-block"
           data-toggle="class:nav-off-screen" data-target="#nav"></a>
    </section>
</sec:authorize>
<sec:authorize access="isAnonymous()"> 
    <section id="content" style="width: 100%;">
        <section class="vbox">
            <section class="scrollable padder">
                <ul
                    class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                    <li><a href="index"><i class="fa fa-home"></i> Trang
                            chủ</a></li>
                    <!-- <li class="active">Workset</li> -->
                </ul>



                <section id="select_sim">
                    <div class="container" style="width:100%;">
                        <div class="text-center col-md-12">
                            GIÚP CÁC SỐ MUỐN BÁN CỦA BẠN ĐƯỢC NHIỀU NGƯỜI BIẾT ĐẾN NHẤT
                        </div>
                        <br /><br />
                        <div class="text-center col-md-6 col-sm-12 col-xs-12">
                            <div class="panel-group" id="accordionmsisdn">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a class="accordion-toggle" data-toggle="collapse"
                                               data-parent="#accordionmsisdn" href="#collapsemsisdn">
                                                Đăng ký tài khoản</a>
                                        </h4>
                                    </div>
                                    <div id="collapsemsisdn" class="panel-collapse collapse in">
                                        <div class="panel-body ">
                                            <div class="info-offset">
                                                <div class="row">
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/register">Đăng ký ngay</a></div>
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%=request.getContextPath()%>/login">Đăng nhập</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-center col-md-6 col-sm-12 col-xs-12">
                            <div class="panel-group" id="accordionuser">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a class="accordion-toggle" data-toggle="collapse"
                                               data-parent="#accordionuser" href="#collapseuser">
                                                Đăng số</a>
                                        </h4>
                                    </div>
                                    <div id="collapseuser" class="panel-collapse collapse in">
                                        <div class="panel-body ">
                                            <div class="info-offset">
                                                <div class="row">
                                                    <div class="col-sm-6 col-xs-6"><a class="btn btn-default" href="<%= request.getContextPath()%>/customer/huong-dan-quan-ly-dang-so-14052.html">Hướng dẫn</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </section>
            <%@include file="../../views/layoutCus/footer.jsp"%>
        </section>
        <a href="#" class="hide nav-off-screen-block"
           data-toggle="class:nav-off-screen" data-target="#nav"></a>
    </section>
</sec:authorize>
<!-- App -->
<script src="<%=request.getContextPath()%>/resources/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.3.custom.min.js"></script>
</body>
</html>
