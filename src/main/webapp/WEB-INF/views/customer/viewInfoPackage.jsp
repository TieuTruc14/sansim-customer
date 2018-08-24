<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<jsp:useBean id="today" class="java.util.Date" />
<!-- /.aside -->
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="bg-primary breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/customer/msisdn"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Hiển thị thông tin gói cước</a></li>
            </ul>

            <!--</div>-->

            <section class="panel panel-default">
                <div class="table-responsive">
                    <form action="removeMsisdn" method="post" id="frmRemoveMsisdn" >
                        <input type="hidden" value=";" name="ids" id="hdfIds" />
                        <input type="hidden" value="" name="pageRemoveMsisdn" id="pageRemoveMsisdn" >
                        <input type="hidden" id="filterRemove" name="filterRemove" />
                        <table class="table table-striped table-bordered m-b-none">
                            <thead>
                                <tr>

                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="2%">STT</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="3%">Mã gói</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="6%">Số lượng tối đa có thể đăng (số)</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="5%">Chu kỳ(ngày)</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="3%">Phí(VNĐ)</th>
                                    <th class="box-shadow-inner small_col" style="text-align:center;" width="10%">Cách thức đăng ký</th>

                                </tr>
                            </thead>
                            <c:set var="k" value="${start}"></c:set>

                                <tbody class="list">

                                <c:forEach var="item" items="${list}" varStatus="myIndex">
                                    <tr>
                                        <td style="text-align: center;">${myIndex.index + 1}</td>
                                        <td style="text-align: center;">${item.packageCode }</td>
                                        <td style="text-align: center;">${item.maxQuantity }</td>
                                        <td style="text-align: center;">${item.period }</td>
                                        <c:if test="${item.fee ==0 }">
                                            
                                        <td style="text-align: center;">Khuyến mại: 0đ</td>
                                            
                                        </c:if>
                                        <c:if test="${item.fee !=0 }">
                                            
                                        <td style="text-align: center;">${item.fee }</td>
                                            
                                        </c:if>
                                        
                                        <c:if test="${item.fee ==0 }">
                                            
                                        <td style="text-align: left;"> Trong giai đoạn triển khai. Gói G1 được miễn phí ngay sau khi đăng ký tài khoản </td>
                                            
                                        </c:if>
                                        <c:if test="${item.fee !=0 }">
                                            
                                        <td style="text-align: left;"> Soạn tin HT ${item.packageCode} <sec:authentication property="principal.username" /> gửi 8655 </td>
                                            
                                        </c:if>
                                        
                                        
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </form>
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

</script>
