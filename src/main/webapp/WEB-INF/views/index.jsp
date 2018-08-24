<%@page import="com.osp.model.Customer"%>
<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/mark.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/jquery.mark.min.js" charset="UTF-8"></script>

<script src="<%=request.getContextPath()%>/assets/project/customer/index.js"></script>

<%
    Customer manager = (Customer) request.getSession().getAttribute("userShop");
%>

<style>
.hiden1{display: none;}
.mark, mark {
    padding: 0px;
    background-color: white;
    color:#daa503;
}
.mark:hover{
    color:#17a2b8 !important;
}
mark:hover{
    color:#17a2b8 !important;
}
.msisdn-text:hover mark{
    color:#17a2b8 !important;
}

</style>

<section ng-app="sansimso"  ng-controller="sansimCtrl">
    <div id ="formSearchSanSim" class="container-sim">
        <div class="topSearch clearfix">
            <div class="simSearch clearfix">
                <h3 class="h3Title">Tìm sim số đẹp</h3>
                <div class="simSearchGroup clearfix">
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 col-md-12">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-3 sim-text-right">
                                    <div class="form-group hide">
                                        <label class="control-label pull-right">Tìm số</label>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 responsive-app">
                                    <div class="form-group">
                                        <select class="form-control simple-select2-sm input-sm" ng-model="search.dauso" style="width:100%">
                                            <option value=""></option>
                                            <option value="10">Đầu 10 số</option>
                                            <option value="11">Đầu 11 số</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6">
                                    <div class="form-group">
                                        <input name="browser" class="form-control enter form-control-browser" placeholder="Số cần tìm" maxlength="7"  ng-model="search.so" my-enter="find()" onkeypress="return restrictCharacters(this, event, digitsAndAsterisk);" data-toggle="tooltip" data-html="true" data-original-title="<div class='bg-intruction-search' style='width: 300px;' >
                                               1. Nhập chính xác số cần tìm. Ví dụ: 4896999. <br>
                                               2. Hoặc sử dụng dấu * đại diện cho một chuỗi số bất kỳ:<br>
                                               <ul style='padding-left: 10px;' >
                                               <li>a. Để tìm số bắt đầu bằng 88, nhập vào 88*</li>
                                               <li>b. Để  tìm số kết thúc bằng 88, nhập vào *88 hoặc 88</li>
                                               <li>c. Để tìm số bắt đầu bằng 88 và kết thúc bằng 99, nhập vào 88*99</li>
                                               <li>d. Để tìm số chứa  88, nhập vào *88*</li>
                                               <li>e. Để tìm số chứa  88 và  99, nhập vào *88*99*</li>
                                               </ul>
                                               </div>">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 sim-text-right">
                                    <div class="form-group hide">
                                        <label class="control-label pull-right">Nhà mạng</label>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-9 responsive-app">
                                    <div class="form-group">
                                        <select class="form-control simple-select2-nhamang input-sm" ng-model="search.telco" style="width:100%">
                                            <option value=""></option>
                                            <option value="1">Viettel</option>
                                            <option value="2">Vinaphone</option>
                                            <option value="3">Mobifone</option>
                                            <option value="4">Gmobile</option>
                                            <option value="5">VietNamMobile</option>
                                            <option value="0">Khác</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 sim-text-right">
                                    <div class="form-group hide">
                                        <label class="control-label pull-right">Giá</label>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 responsive-app">
                                    <div class="form-group">
                                        <select class="form-control select2-from input-sm" ng-model="search.from" style="width:100%">
                                            <option value=""></option>
                                            <option value = "0">0 đồng</option>
                                            <option value = "200000">200 nghìn</option>
                                            <option value = "500000">500 nghìn</option>
                                            <option value = "1000000">1 triệu</option>
                                            <option value = "5000000">5 triệu</option>
                                            <option value = "10000000">10 triệu</option>
                                            <option value = "20000000">20 triệu</option>
                                            <option value = "50000000">50 triệu</option>
                                            <option value = "100000000">100 triệu</option>
                                            <option value = "200000000">200 triệu</option>
                                            <option value = "500000000">500 triệu</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 responsive-app">
                                    <div class="form-group">
                                        <select class="form-control select2-to input-sm" ng-model="search.to" style="width:100%">
                                            <option value=""></option>
                                            <option value = "0">0 đồng</option>
                                            <option value = "200000">200 nghìn</option>
                                            <option value = "500000">500 nghìn</option>
                                            <option value = "1000000">1 triệu</option>
                                            <option value = "5000000">5 triệu</option>
                                            <option value = "10000000">10 triệu</option>
                                            <option value = "20000000">20 triệu</option>
                                            <option value = "50000000">50 triệu</option>
                                            <option value = "100000000">100 triệu</option>
                                            <option value = "200000000">200 triệu</option>
                                            <option value = "500000000">500 triệu</option>
                                            <option value = "1000000000">1 tỉ</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3"></div>
                                <div class="col-xs-12 col-sm-12 col-md-3 sim-text-right">
                                    <div class="form-group hide">
                                        <label class="control-label pull-right"> Tìm theo ngày sinh </label>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 hide">
                                    <div class="form-group">
                                        <%--<input type="text" id="birth-date" class="form-control"/>--%>
                                        <input id= "birthday" name="birthday" placeholder="Ngày/Tháng/Năm" maxlength="10" ng-model="search.birthday" my-enter="find()"   onkeypress="return restrictCharacters(this, event, digitsAndSlash);" class="input-sm form-control datepicker-input" data-date-format="dd/mm/yyyy" />
                                        <a style="color:#d80000" class="hiden1">{{errorDate}}</a>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 hiden1" ng-show="count > 50">
                                    <div class="form-group">
                                        <input type="text" class="form-control enter input-sm"  size="3" my-enter="find()" placeholder="Mã xác nhận" name="captcha" ng-model="captcha" />
                                        <label class="control-label hiden1" ng-show="errorCaptcha" style="color:#d80000">{{errorCaptchaMessage}}</label>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-3 hiden1" ng-show="count > 50">
                                    <div class="form-group">
                                        <img ng-src="{{imageUrl}}" id="captcha" style="height: 30px">
                                        <a href="javascript:void(0)" ng-click="changeCaptcha();"><span class="fa fa-refresh" style="color:white;"></span></a>
                                    </div>
                                </div>    
                                <div class="col-xs-12 col-sm-12 col-md-12 text-center responsive-app">
                                    <div class="form-group">
                                        <input type="checkbox" aria-label="Checkbox for following text input" class="sim-check" ng-model="search.confirmStatus">
                                        <label>Chỉ tìm số xác thực (*)</label><br/>
                                        <span style="color:white;font-size:12px;" class="hide">(Số xác thực là số đã được người bán sử dụng nhắn tin xác nhận)</span>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-12 text-center findAdvance">           
                                    <a href="javascript:void(0)" onclick="findAdvance()" class=""> <span class="link-display-advance-search" style="color: #ffbd05;">Thêm tiêu chí tìm kiếm <i class="fa fa-caret-down"></i> </span>   </a>      
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-12">
                        <div class="form-group text-center p-2"> <a href="javascript:void(0)" class="btn btn-search" ng-click="find()"> Tìm kiếm </a> </div>
                    </div>
                                    
                </div>
            </div>
        </div>
    </div>
    <div class="container-sim">
        <div class="page-content">
            <div class="sim-content clearfix">
                <div class="page-left hide">
                    <div class="title border-top-title">SIM THEO GIÁ</div>
                    <form>
                        <ul class="list-ul-sim hiden1">
                            <li ng-repeat="item in listGroupPrice track by $index">
                                <input type="checkbox" ng-click="searchGroupPrice(item.id)" id="price_{{$index + 1}}" name=""/>
                                <span>{{item.name}}</span>
                                <label for="price_{{$index + 1}}" ><span >{{item.name}}</span></label>
                            </li>
                        </ul>
                    </form>
                    <form>
                        <div class="title border-top-title">SIM THEO LOẠI</div>
                        <ul class="list-ul-sim hiden1">
                            <li ng-repeat="item in listGroupMsisdn track by $index">
                                <input type="checkbox" ng-click="searchGroupMsisdn(item.id)" id="type_{{$index + 1}}" name=""/>
                                <span>{{item.groupName}}</span>
                                <label for="type_{{$index + 1}}"><span>{{item.groupName}}</span></label>
                            </li>
                        </ul>
                    </form>
                    <div class="mb-2"> <img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/anh1.jpg" alt="ads"> </div>
                        <%--<div class="mb-2"> <img class="img-responsive" src="<%=request.getContextPath()%>/assets/img/ad2.jpg" alt="ads"> </div>--%>
                </div>
                <!--                <div ng-model="thinghiem">
                                    
                                </div>-->
                <div class="page-center up">
                    <div class="content p-2" id ="list-sim" name ="list-sim">
                        <c:if test="<%= manager != null%>">
                            <h1 class="title_table">SIM SỐ ĐẸP - <%= manager.getFull_name().toUpperCase()%></h1>
                        </c:if>
                        <c:if test="<%= manager == null%>">
                            <h1 class="title_table">SIM SỐ ĐẸP</h1>
                        </c:if>
                        <div class="table-responsive hiden1">
                            <div class="form-group text-center" style="margin-top:5px;">
                                <a href="javascript:void(0)" class="bold" style="margin-right:4px;padding:2px;background-color:#5a8eef; color: #ffffff; !important;border:1px solid transparent;border-radius:4px;" ng-click="unLabelPrice()" ng-show="groupPriceId>0">{{label.price}} <span style="color:#f13131!important">x</span></a>
                                <a href="javascript:void(0)" class="bold" style="margin-right:4px;padding:2px;background-color:#5a8eef; color: #ffffff; !important;border:1px solid transparent;border-radius:4px;" ng-click="unLabelMsisdn()" ng-show="groupMsisdnId>0">{{label.msisdn}}  <span style="color:#f13131!important">x</span></a>
                                <a href="javascript:void(0)" class="bold" style="margin-right:4px;padding:2px;background-color:#5a8eef; color: #ffffff; !important;border:1px solid transparent;border-radius:4px;" ng-click="unLabelYear()" ng-show="groupYearId>0">{{label.year}}  <span style="color:#f13131!important">x</span></a>
                                <a href="javascript:void(0)" class="bold" style="margin-right:4px;padding:2px;background-color:#5a8eef; color: #ffffff; !important;border:1px solid transparent;border-radius:4px;" ng-click="unLabelTelco()" ng-show="groupTelcoId!=9">{{label.telco}}  <span style="color:#f13131!important">x</span></a>
                            </div>
                            <table class="list-sim " >
                                <thead>
                                    <tr>
                                        <th class="hide text-center">STT</th>
                                        <th>Số thuê bao</th>
                                        <th>Giá bán<span class="hide">(VND)</span>
                                            <span ng-switch on="search.sortPrice">
                                                <a ng-switch-when="1" href="javascript:void(0);" ng-click="sortPrice()" title="Sắp xếp tăng"><i class="fa fa-sort-asc"></i></a>
                                                <a ng-switch-when="2" href="javascript:void(0);" ng-click="sortPrice()" title="Sắp xếp giảm"><i class="fa fa-sort-desc"></i></a>
                                                <a ng-switch-default href="javascript:void(0);" ng-click="sortPrice()" title="Sắp xếp tăng/giảm"><i class="fa fa-sort"></i></a>
                                            </span>
                                        </th>
                                        <th>Nhà mạng</th>
                                        <c:if test="<%= manager == null%>">
                                        <th class="hide">Người bán</th>
                                        </c:if>
                                        <th style="text-align: center;" class="hide">Đã xác thực</th>
                                        <th>Đặt mua</th>
                                    </tr>
                                </thead>
                                <tbody >
                                    <tr ng-repeat="item in page.items track by $index">
                                        <td class="hide text-center ">{{(page.pageNumber - 1) * page.numberPerPage + $index + 1}}</td>
                                        <td class="highlight-content">
                                            <a  ng-bind-html="item[8]" class="btnPopup bold msisdn-text highlight-content"  ng-click="searchDetailMsisdn(item[0])"  href="javascript:void(0)"></a>
                                            <%--<a ng-bind-html="item[8]" ng-bind-html="highlight(item[8], searchText)" class="btnPopup bold msisdn-text"  ng-click="searchDetailMsisdn(item[0])"  href="javascript:void(0)"></a>--%>
                                            <%--<a ng-bind-html="item[8]" class="btnLink bold msisdn-text" href="<%=request.getContextPath()%>/sim/sim-so-dep-{{item[1]}}-{{item[0]}}"></a>--%>
                                        </td>
                                        <td >
                                            <span class="text-td">{{item[2]|currency:"":0}}</span>
                                        </td>
                                        <td ng-switch on="item[6]" >
                                            <img ng-switch-when="1" class="img-viettel"  src="<%=request.getContextPath()%>/assets/images/cus/viettel.png" alt="viettel">
                                            <img ng-switch-when="2" class="img-nhamang"  src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.png" alt="vinaphone">
                                            <img ng-switch-when="3" class="img-nhamang" src="<%=request.getContextPath()%>/assets/images/cus/mobifone.png" alt="mobifone">
                                            <img ng-switch-when="4" class="img-nhamang" src="<%=request.getContextPath()%>/assets/images/cus/gmobile.png" alt="gmobile">
                                            <img ng-switch-when="5" class="img-nhamang" src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.png" alt="vietnamobile">
                                            <span ng-switch-default>Mạng khác</span>
                                        </td>
                                        <c:if test="<%= manager == null%>">
                                            <td class="hide"><a href="http://{{item[7]}}" class=" name-customer font-500 text-td">{{item[4]}}</a></td>
                                        </c:if>
                                        <td class="hide" style="text-align: center ">
                                            <span ng-if="item[5]==1"><i class="fa fa-check fa-2x text-success btnPopup"></i></span>
                                        </td>
                                        <td>
                                            <!--<a class="btn btn-info btn-sm text-white btnPopup  text-td" ng-click="searchDetailMsisdn(item[0])"  href="javascript:void(0)" style="background-color: #337cbc;">Chi tiết</a>-->
                                            <!--btnLink-->
                                            <a class="btn btn-info btn-sm text-white text-td" href="<%=request.getContextPath()%>/sim/sim-so-dep-{{item[1]}}-{{item[0]}}" style="background-color: #337cbc;">Chi tiết</a>
                                        </td>
                                    </tr>
                                    <tr ng-if="loadding">
                                        <td class="hide" class="align-center "></td>
                                        <td colspan="4" class="align-center "><i class="fa fa-2x fa-spinner fa-spin"></i></td>
                                        <td class="hide" class="align-center " ></td>
                                    </tr>
                                    <tr ng-if="page.items.length == 0 && !loadding">
                                        <td class="hide" class="align-center " ></td>
                                        <td colspan="4" class="align-center " ><h4 style="font-size:16px; padding-top: 15px;">{{noData}}</h4></td>
                                        <td class="hide" class="align-center " ></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="text-center hiden1">
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
                        <div class="title border-top-title">SIM THEO NĂM SINH</div>
                        <form> <ul class="list-ul-sim hiden1">
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
                    <div class="hide">
                        <div class="title border-top-title">NHÀ MẠNG</div>
                        <form> <ul class="list-ul-sim hiden1">
                            <li ng-repeat="item in listGroupTelco track by $index">
                                <input type="checkbox" ng-click="searchGroupTelco(item.id)" id="telco_{{$index + 1}}" name=""/>
                                <span>{{item.name}}</span>
                                <label for="telco_{{$index + 1}}">
                                    <span>{{item.name}}</span>
                                </label>
                            </li>
                        </ul>
                        </form>
                    </div>
<!--                    <div>
                        <div class="fb-page" data-href="https://www.facebook.com/sansim.vn/" data-tabs="timeline" data-height="300" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true"><blockquote cite="https://www.facebook.com/facebook" class="fb-xfbml-parse-ignore"><a href="https://www.facebook.com/facebook"></a></blockquote></div>
                    </div>-->
                </div>
            </div>
        </div>
    </div>
    <div class="modal modal-sim fade" id="exampleModal" tabindex="-1"  role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" >
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3 class="text-center title-modal">Thông tin sim <font  color="#FFF">{{itemDetail[1]}}</font></h3>
                <div class="content-md" >
                    <div class="content" >
                        <div class="info-sim">
                                <h4 class="title1">Chi tiết thuê bao</h4>
                                <ul class="list">
                                    <li>
                                        <span class="name-label">Số thuê bao</span>
                                        <span class="txt"><font id ="msisdnSelected" color="#0A629E">{{itemDetail[1]}}</font></span>
                                    </li>
                                    <li>
                                        <span class="name-label">Giá bán</span>
                                        <span class="txt"><lable class="currencyHtml ">{{itemDetail[2]|currency:"":0}}</lable> VNĐ</span>
                                    </li>
                                    <li>
                                        <span class="name-label">Nhà mạng</span>
                                        <span class="txt" ng-switch on="itemDetail[10]">
                                            <img ng-switch-when="1" style="height:10px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/viettel.png" alt="viettel">
                                            <img ng-switch-when="2" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.png" alt="vinaphone">
                                            <img ng-switch-when="3" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/mobifone.png" alt="mobifone">
                                            <img ng-switch-when="4" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/gmobile.png" alt="gmobile">
                                            <img ng-switch-when="5" style="height:18px; width: 80px" src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.png" alt="vietnamobile">
                                            <span ng-switch-default>Mạng khác</span>
                                    
                                        </span>
                                    </li>
                                    <li>
                                        <span class="name-label">Trạng thái xác thực</span>
                                        <span class="txt" ng-switch on="itemDetail[3]" >
                                            <span ng-switch-when="1"> Đã xác thực</span>
                                            <span ng-switch-default> Chưa xác thực</span>
                                        </span>
                                    </li>
                                    <li>
                                        <span class="name-label">Mô tả</span>
                                        <div class="col-md-6">
                                            {{itemDetail[9]}}
                                        </div>
                                    </li>
                                </ul>
                        </div>
                        <div class="info-user">
                            <div class="item">
                            <h4 class="title1">Người bán</h4>
                                <ul class="list">
                                    <li>
                                        <span class="name-label">Tên người bán</span>
                                        <span class="txt"><a href="http://{{itemDetail[11]}}" style="color: blue; " >{{itemDetail[5]}}</a></span>
                                    </li>
                                    <li>
                                        <span class="name-label">Liên hệ để mua</span>
                                        <span class="txt">{{itemDetail[6]}}</span>
                                    </li>
                                    <li>
                                        <span class="name-label">Địa chỉ</span>
                                        <div class="col-md-4">
                                        <span class="txt">{{itemDetail[7]}}</span>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                        </div>
                        
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</div>                                    

</section>


<script>
    $(document).ready(function () {
        $('.simple-select2-sm').select2({
            theme: 'bootstrap4',
            containerCssClass: ':all:',
            placeholder: "Chọn đầu số",
            allowClear: true
        });
        $('.simple-select2-nhamang').select2({
            theme: 'bootstrap4',
            containerCssClass: ':all:',
            placeholder: "Chọn nhà mạng",
            allowClear: true
        });

        $('.select2-from').select2({
            theme: 'bootstrap4',
            containerCssClass: ':all:',
            placeholder: "Từ giá",
            allowClear: true
        });
        $('.select2-to').select2({
            theme: 'bootstrap4',
            containerCssClass: ':all:',
            placeholder: "Đến giá",
            allowClear: true
        });
        if($(window).width() <500){
            
        $('.form-control-browser').attr("placeholder", "Nhập số cần tìm");
        }

    });
    
    function findAdvance(){
//        alert($('.responsive-app').css("display"));
        if($('.responsive-app').css("display")=='none'){
            $('.responsive-app').css("cssText", "display: block !important;");
            $('.link-display-advance-search').html("Bớt tiêu chí tìm kiếm <i id=\"findAdvance\" class=\"fa fa-caret-up\"></i>");
            
        }else{
            
            $('.responsive-app').css("cssText", "display: none !important;");
            $('.link-display-advance-search').html("Thêm tiêu chí tìm kiếm <i id=\"findAdvance\" class=\"fa fa-caret-down\"></i>");
        }
        
    }
    
</script>
