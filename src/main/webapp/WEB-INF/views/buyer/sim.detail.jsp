
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<style>--%>
<%--table {font-size:13px;}--%>
<%--</style>--%>
<script src="<%=request.getContextPath()%>/assets/js/CommonFunction.js"></script>
<script src="<%=request.getContextPath()%>/assets/project/customer/detail.sim.js"></script>
<style>
    .hiden1{
        display: none;
    }
</style>
<section ng-app="sansimso"  ng-controller="detailsimCtrl" class="hiden1">
    <div class="container-sim">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Chi tiết sim</li>
            </ol>
        </nav>
        <title>Sim số đẹp - ${item[1]}</title>        
        <div class="page-content">
            <div class="sim-content clearfix">
                <div class="page-left hide">
                    <div class="title">SIM THEO GIÁ</div>
                    <form>
                        <ul class="list-ul-sim">
                            <li ng-repeat="item in listGroupPrice track by $index">
                                <input type="checkbox" ng-click="searchGroupPrice(item.id)" id="price_{{$index + 1}}" name=""/>
                                <span>{{item.name}}</span>
                                <label for="price_{{$index + 1}}" ><span >{{item.name}}</span></label>
                            </li>
                        </ul>
                    </form>
                    <form>
                        <div class="title">SIM THEO LOẠI</div>
                        <ul class="list-ul-sim">
                            <li ng-repeat="item in listGroupMsisdn track by $index">
                                <input type="checkbox" ng-click="searchGroupMsisdn(item.id)" id="type_{{$index + 1}}" name=""/>
                                <span>{{item.groupName}}</span>
                                <label for="type_{{$index + 1}}"><span>{{item.groupName}}</span></label>
                            </li>
                        </ul>
                    </form>
                    <!--<div class="mb-2"> <img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/anh1.jpg" alt="ads"> </div>-->
                        <%--<div class="mb-2"> <img class="img-responsive" src="<%=request.getContextPath()%>/assets/img/ad2.jpg" alt="ads"> </div>--%>
                </div>
                <div class="page-center up">
                    <div class="content p-2" id ="list-sim" name ="list-sim">
                        <div class="row">
                            <div class="col-sm-12">
                                <p style="font-size: 24px" class="text-center">SIM SỐ ĐẸP:  <strong style="color: #337cbc">${item[1]}</strong></p>
                                <hr  width="100%" align="left" /> 
                                
                            </div>
                            <!--<div class="col-sm-2"></div>-->
                            <div class="col-sm-5" style="font-size: 16px;">
                                <input type="hidden" name ="cusId" id ="cusId" value="${cusId}"/>
                                <p style="font-size: 16px"><i class="fa fa-dollar  fa-size"></i> Giá bán (VNĐ):  <strong style="color: #337cbc"><lable class=" currencyHtml">${item[2]}</lable></strong> vnđ</p>
                                <p><i class="fa fa-phone  fa-size"></i> Nhà mạng: 
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
                                <p><i class="fa fa-check-circle  fa-size"></i> Trạng thái xác thực: <strong>
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
                            </div>
                                  

                            <div class="col-sm-7" style="font-size: 16px;">
                                <p><i class="fa fa-user  fa-size"></i> Tên người bán: <a href="http://${item[11]}" style="color: #333333; " ><strong>${item[5]} </strong> </a></p>
                                <p><i class="fa fa-phone-square  fa-size"></i> Điện thoại liên hệ: <strong>  ${item[6]}</strong></p>
                                <p><i class="fa fa-map-marker  fa-size"></i> Địa chỉ: 
                                    <strong>${item[7].length()>0?item[7]:'Chưa cập nhập'}</strong>
                                </p>
                            </div>
                            
                            <div class="col-sm-12" style="font-size: 16px;">
                                <c:if test="${item[9].length()>0}">
                                    <p><i class="fa fa-info-circle  fa-size"></i> Mô tả: <strong> ${item[9]}</strong></p>
                                </c:if>
                                <p><i class="fa fa-search"></i> Xem thêm các số khác từ người bán tại ( <a href="http://${item[11]}" style="color: blue; " ><strong>đây </strong>)</a> </p>
                            </div>     

                        </div>
                        <div class="" style="text-align: right;margin-bottom:10px;" >
                            <span>
                                Hãy chia sẻ lên facebook để mọi người cùng biết >
                            </span>
                            <a  class="fl chiasefb"  style="margin-right: 10px;color:white;" onclick="shareImage('Bán sim số đẹp ${item[12]}',${item[9]});" href="javascript:void(0);" rel="nofollow">
                                Chia sẻ <i></i>
                            </a>
                        </div>
                        <h3 class="title_table" style="font-size: 22px;">SIM SỐ ĐẸP CÙNG NGƯỜI BÁN</h3>
                        <div class="table-responsive">
                            <table class="list-sim">
                                <thead>
                                    <tr>
                                        <th class="hide text-center">STT</th>
                                        <th>Số thuê bao</th>
                                        <th>Giá bán <span class="hide">(VND)</span><a href="javascript:void(0);" ng-click="changeSortPrice()" title="Sắp xếp tăng/giảm"><i class="fa fa-sort"></i></a></th>
                                        <th>Nhà mạng</th>
                                        <th class="hide" style="text-align: center;">Đã xác thực</th>
                                        <th class="hide">Đặt mua</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="item in page.items track by $index">
                                        <td class="hide text-center">{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                                         <td >
                                            <a class="bold msisdn-text" href="<%=request.getContextPath()%>/sim/{{item[0]}}">{{item[1]}}</a>
                                        </td>
                                        <td>
                                            <span class="text-td ">{{item[2]|currency:"":0}}</span>
                                        </td>
                                        <td ng-switch on="item[6]" >
                                            <img ng-switch-when="1" style="height:10px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/viettel.png" alt="viettel">
                                            <img ng-switch-when="2" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.png" alt="vinaphone">
                                            <img ng-switch-when="3" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/mobifone.png" alt="mobifone">
                                            <img ng-switch-when="4" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/gmobile.png" alt="gmobile">
                                            <img ng-switch-when="5" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.png" alt="vietnamobile">
                                            <span ng-switch-default>Mạng khác</span>
                                        </td>
                                        <td class="hide" style="text-align: center ">
                                            <span ng-if="item[5]==1"><i class="fa fa-check fa-2x text-success btnPopup"></i></span>
                                        </td>
                                        <td class="hide">
                                            <!--<a class="btn btn-info btn-sm text-white btnPopup text-left"   data-toggle="modal" data-target="#exampleModal"  href="javascript:void(0)">Chi tiết</a>-->
                                            <a class="btn btn-info btn-sm text-white text-left" href="<%=request.getContextPath()%>/sim/{{item[0]}}">Chi tiết</a>
                                        </td>
                                    </tr>
                                     <tr ng-if="loadding">
                                        <td class="hide" class="align-center "></td>
                                        <td colspan="4" class="align-center "><i class="fa fa-2x fa-spinner fa-spin"></i></td>
                                        <td class="hide" class="align-center" ></td>
                                    </tr>
                                    <tr ng-if="page.items.length == 0 && !loadding">
                                        <td class="hide" class="align-center " ></td>
                                        <td colspan="4" class="align-center " ><h4 style="font-size:16px; padding-top: 15px;">{{noData}}</h4></td>
                                        <td class="hide" class="align-center " ></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="text-center">
                            <nav aria-label="Page navigation example" class="page-list"  ng-if="page.pageCount > 1">
                                <ul class="pagination">
                                    <li class="page-item" ng-if="page.pageNumber > 1" > <a  ng-click="beforeLoadPage(page.pageNumber - 1)" class="page-link" href="javascript:void(0)" aria-label="Trước"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">Trước</span> </a> </li>
                                    <li class="page-item {{item==page.pageNumber?'active':''}}"  ng-repeat="item in page.pageList"><a class="page-link" ng-click="beforeLoadPage(item)" href="javascript:void(0)">{{item}}</a></li>
                                    <li class="page-item" ng-if="page.pageNumber < page.pageCount"> <a class="page-link" href="javascript:void(0)" ng-click="beforeLoadPage(page.pageNumber + 1)" aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Tiếp</span> </a> </li>
                                </ul>
                            </nav>

                        </div>
                    </div>
                </div>
                <div class="page-right">
                    <div class="hide">
                        <div class="title">SIM THEO NĂM SINH</div>                  
                        <form> <ul class="list-ul-sim">
                                <li ng-repeat="item in listGroupYear track by $index">
                                    <input type="checkbox" ng-click="searchGroupYear(item.id)" id="birth_{{$index + 1}}" name=""/>
                                    <span>{{item.name}}</span>
                                    <label for="birth_{{$index + 1}}">
                                        <span>{{item.name}}</span>
                                    </label>
                                </li>
                            </ul>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>
</section>
<div class="fb-comments" data-href="<spring:eval expression="@commonProperties.getProperty('domain')" />/sim/${id}" data-width="100%" data-numposts="10"></div>
<div id="target-share" style="margin:0 auto;width:560px;background-image: url('<%=request.getContextPath()%>/assets/images/cus/content.png');">
    <p class="text-center"><img src="<%=request.getContextPath()%>/assets/static/images/logo-san-sim.jpg" alt=""></p>
    <p style="font-size: 20px" class="text-center">SIM SỐ ĐẸP:  <strong style="color: #337cbc">${item[1]}</strong></p>
    <p style="font-size: 16px" class="text-center"><i class="fa fa-dollar  fa-size"></i> Giá bán (VNĐ):  <strong style="color: #337cbc"><lable class=" currencyHtml">${item[2]}</lable></strong> vnđ</p>
    <p class="text-center"><i class="fa fa-phone  fa-size"></i> Nhà mạng:
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
    <p class="text-center"><i class="fa fa-phone-square  fa-size"></i> Liên hệ: <strong>  ${item[6]}</strong></p>
</div>

<style>
    #target-share{display:none;}
    .chiasefb {background: #4167b2;height: 25px;line-height: 20px;display: inline;border-radius: 3px;color: #fff;padding: 5px 10px;}
    .chiasefb i {font-style: normal;}
</style>
<script type="text/javascript">
    var imageGen;
    $(function() {
        var elem = $('#target-share');
        if(!elem.is(":visible")){
            elem.show();elem.fadeIn(100);elem.fadeOut(100);
        }
        html2canvas(document.querySelector("#target-share")).then(function(canvas) {
            var giatri=canvas.toDataURL("image/png");
            var value = giatri.replace(/^data:image\/(png|jpg);base64,/, "");
            $.ajax({
                type: "POST",
                url: '<%=request.getContextPath()%>/image-save',
                dataType: 'text',
                data:{ image: value},
                success: function(result){
                    if(result!=null && result!='error'){
                        imageGen="http://sansim.vn"+<%=request.getContextPath()%>result;
                    }
                }
            });
        });

    });
    function shareImage(title,descript) {
        if(descript==null || descript=='undefined' || descript.length ==0) descript='';
        setTimeout(function(){
            FB.ui({
                method: 'share_open_graph',
                action_type: 'og.shares',
                display: 'popup',
                action_properties: JSON.stringify({
                    object: {
                        'og:url': document.URL,
                        'og:title': title,
                        'og:description': descript,
                        'og:image': imageGen,
                        'og:image:width': '1920',
                        'og:image:height': '1200',
                    }
                })
            }, function(response){});
        }, 500);

    }


</script>
