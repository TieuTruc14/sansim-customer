
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<style>--%>
<%--table {font-size:13px;}--%>
<%--</style>--%>
<script src="<%=request.getContextPath()%>/assets/js/CommonFunction.js"></script>
<!--<script src="<%=request.getContextPath()%>/assets/project/customer/detail.sim.js"></script>-->
<style>
    a:hover {
        text-decoration: underline;
    }
</style>
<section ng-app="sansimso">
    <div class="container-sim">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Chi tiết sim</li>
            </ol>
        </nav>
        <div class="page-content">
            <div class="sim-content clearfix">
                <div class="page-center up">
                    <div class="content p-2" id ="list-sim" name ="list-sim">
                        <div class="row">
                           
                            <div class="col-md-8 col-sm-6">
                                <p style="font-size: 20px"> Số thuê bao:  <strong style="color: #337cbc">${item[1]}</strong></p>
                                <hr  width="100%" align="left" /> 
                                <p style="font-size: 16px"><i class="fa fa-dollar"></i> Giá bán (VNĐ):  <strong style="color: #337cbc"><lable class=" currencyHtml">${item[2]}</lable></strong> vnđ</p>
                            </div>

                            <!--<div class="col-sm-2"></div>-->
                            <div class="col-md-8 col-sm-6" style="font-size: 16px;">
                                <input type="hidden" name ="cusId" id ="cusId" value="${cusId}"/>
                                <p><i class="fa fa-phone"></i> Nhà mạng: 
                                    <c:choose>
                                        <c:when test="${item[10]==1}">
                                            <img style="margin-bottom:5px; height:10px; width: 80px" src="<%=request.getContextPath()%>/assets/images//cus/viettel.png" alt="viettel">
                                        </c:when>
                                        <c:when test="${item[10]==2}">
                                            <img style="margin-bottom:5px; height:25px; width: 80px"  src="<%=request.getContextPath()%>/assets/images//cus/vinaphone.png" alt="vinaphone">
                                        </c:when>
                                        <c:when test="${item[10]==3}">
                                            <img style="margin-bottom:5px; height:18px; width: 80px"  src="<%=request.getContextPath()%>/assets/images//cus/mobifone.png" alt="mobifone">
                                        </c:when>
                                        <c:when test="${item[10]==4}">
                                            <img style="margin-bottom:5px; height:20px; width: 80px"  src="<%=request.getContextPath()%>/assets/images//cus/gmobile.png" alt="gmobile">
                                        </c:when>
                                        <c:when test="${item[10]==5}">
                                            <img style="margin-bottom:5px; height:25px; width: 80px"  src="<%=request.getContextPath()%>/assets/images//cus/vietnamobile.png" alt="vietnamobile">
                                        </c:when>
                                        <c:otherwise>
                                            Nhà mạng khác
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p><i class="fa fa-check-circle"></i> Trạng thái xác thực: <strong>
                                        <c:choose>
                                            <c:when test="${item[3]==1}">
                                                Đã xác thực
                                            </c:when>
                                            <c:otherwise>
                                                Chưa xác thực
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </p>
                                <c:if test="${item[9].length()>0}">
                                    <p><i class="fa fa-info-circle"></i> Mô tả: <strong> ${item[9]}</strong></p>
                                </c:if>

                            </div>

                            <div class="col-md-8 col-sm-6" style="font-size: 16px;">
                                <p><i class="fa fa-user"></i>&nbsp;Tên người bán: <a href="http://${item[11]}" style="color: blue; " ><strong>${item[5]} </strong> </a></p>
                                <p><i class="fa fa-phone-square"></i> Điện thoại liên hệ: <strong>  ${item[6]}</strong></p>
                                <p><i class="fa fa-map-marker"></i> Địa chỉ: <strong>${item[7].length()>0?item[7]:'Chưa cập nhập'}</strong></p>
                            </div>
                            <div class="col-md-8 col-sm-6" style="font-size: 16px;">
                                <p><i class="fa fa-search"></i> Xem thêm các số khác từ người bán tại ( <a href="http://${item[11]}" style="color: blue; " ><strong>đây </strong>) </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<div class="fb-comments" data-href="<spring:eval expression="@commonProperties.getProperty('domain')" />/sim/${id}" data-width="100%" data-numposts="10"></div>
