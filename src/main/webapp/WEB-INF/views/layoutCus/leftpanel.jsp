<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@ page pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>
<!-- .aside -->
<style>
    @media (min-width: 768px){
        .aside-md {
            width: 265px;
        }
        .nav-xs {
            width: 55px;
        }
    }
</style>
<aside class="bg-dark b-r aside-md hidden-print" id="nav">
    <section class="vbox">
        <section class="w-f scrollable">
            <div class="slim-scroll" data-height="auto"
                 data-disable-fade-out="true" data-distance="0" data-size="5px"
                 data-color="#333333">
                <!-- nav -->
                <nav class="nav-primary hidden-xs">
                    <ul class="nav">
                        <%
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            if (auth != null) {
                        %>
                        <li class="link-home"><a
                                href="<%=request.getContextPath()%>/customer/index"> <i
                                    class="fa fa-home icon"> <b class="bg-primary"></b>
                                </i> <span>Trang chủ</span>
                            </a></li>
                            <%} else {%>
                        <li class="link-home"><a
                                href="<%=request.getContextPath()%>/customer/index"> <i
                                    class="fa fa-home icon"> <b class="bg-primary"></b>
                                </i> <span>Trang chủ</span>
                            </a></li>
                            <%}%>
                            <sec:authorize access="hasAnyRole('ROLE_USER')">
                               
                            <li><a href="<%=request.getContextPath()%>/customer/msisdn">
                                    <i class="fa fa-angle-right"></i> <span>Quản lý danh sách số</span>
                                </a>
                            </li>
                            <li> <a href="<%=request.getContextPath()%>/customer/uploadMsisdn">
                                    <i class="fa fa-angle-right"></i> <span>Đăng số</span>
                                </a>
                            </li>
                            <li> <a href="<%=request.getContextPath()%>/customer/shopManager">
                                    <i class="fa fa-angle-right"></i> <span>Thay đổi thông tin gian hàng</span>
                                </a>
                            </li> 
                             <li>
                                <a href="<%=request.getContextPath()%>/customer/viewInfoPackage">
                                    <i class="fa fa-angle-right"></i> <span>Danh sách gói đăng số</span>
                                </a>
                            </li>
                            <li><a href="#uikit"> <i class="fa fa-list-alt icon">
                                        <b class="bg-primary"></b>
                                    </i> <span class="pull-right"> <i
                                            class="fa fa-angle-down text"></i> <i
                                            class="fa fa-angle-up text-active"></i>
                                    </span>
                                    <span>Lịch sử chuyển - nhận số</span>
                                </a>
                                <ul class="nav lt">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/customer/forwardLog">
                                            <i class="fa fa-angle-right"></i> <span>Danh sách số chuyển cho người bán khác</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="<%=request.getContextPath()%>/customer/receiverLog">
                                            <i class="fa fa-angle-right"></i> <span>Danh sách số nhận từ người bán khác</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li><a href="<%=request.getContextPath()%>/customer/transpay">
                                    <i class="fa fa-angle-right"></i> <span>Lịch sử đăng ký gói cước</span>
                                </a>
                            </li>
                            <li><a href="<%=request.getContextPath()%>/customer/smslog">
                                    <i class="fa fa-angle-right"></i> <span>Lịch sử tin nhắn</span>
                                </a>
                            </li>
                            <li><a href="<%=request.getContextPath()%>/customer/upMsisdnGuide">
                                    <i class="fa fa-angle-right"></i> <span>Hướng dẫn đăng số</span>
                                </a>
                            </li>
                            <li><a href="http://sansim.vn/">
                                    <i class="fa fa-angle-right"></i> <span>Quay trở lại SANSIM.VN</span>
                                </a>
                            </li>
                            
                        </sec:authorize>
                    </ul>
                </nav>
                <!-- / nav -->
            </div>
        </section>
        <footer class="footer lt hidden-xs b-t b-light">
            <div id="chat" class="dropup">
                <section class="dropdown-menu on aside-md m-l-n">
                    <section class="panel bg-white">
                        <header class="panel-heading b-b b-light">Active chats</header>
                        <div class="panel-body animated fadeInRight">
                            <p class="text-sm">No active chats.</p>

                            <p>
                                <a href="#" class="btn btn-sm btn-default">Start a chat</a>
                            </p>
                        </div>
                    </section>
                </section>
            </div>
            <div id="invite" class="dropup">
                <section class="dropdown-menu on aside-md m-l-n">
                    <section class="panel bg-white">
                        <header class="panel-heading b-b b-light">
                            John <i class="fa fa-circle text-success"></i>
                        </header>
                        <div class="panel-body animated fadeInRight">
                            <p class="text-sm">No contacts in your lists.</p>

                            <p>
                                <a href="#" class="btn btn-sm btn-facebook"><i
                                        class="fa fa-fw fa-facebook"></i> Invite from Facebook</a>
                            </p>
                        </div>
                    </section>
                </section>
            </div>
            <a href="#nav" data-toggle="class:nav-xs" 
               class="pull-right btn btn-sm btn-default btn-icon"> <i
                    class="fa fa-angle-left text"></i> <i
                    class="fa fa-angle-right text-active"></i>
            </a>
        </footer>
    </section>
</aside>
<!-- /.aside -->
<script type="text/javascript">
    $(document).ready(
            function () {
                if (window.location.href.lastIndexOf("/") == window.location.href.length - 1) {
                    $(".link-home").addClass("active");
                } else {
                    $(".nav-primary .nav li a").each(
                            function () {
                                if (!$(this).attr("href").match("/$")
                                        && window.location.href
                                        .indexOf($(this).attr(
                                                "href")) >= 0) {
                                    if ($(this).parent().parent()
                                            .parent().is("li")) {
                                        $(this).parent().parent()
                                                .parent().addClass(
                                                "active");
                                        //$(this).parent().css("background-color","#428bca");
                                    } else
                                        $(this).parent().addClass(
                                                "active");

                                    $(this).css("color", "white");
                                }
                            });
                }
            });
</script>
