<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .li-active{color:red;font-weight:700;}
    .select2-choice{height:30px!important;}
    .popover-content {
        padding: 9px 14px;
        color: white;
        /*background-color: black;*/
        background-color:rgba(0, 0, 0, 0.5);
        font-size: 12px;
        border: none;
        border-radius: 3px;
        z-index: 1000;
    }
    table {font-size:13px;}
</style>


<script src="<%=request.getContextPath()%>/assets/project/customer/index.js"></script>
<section ng-app="sansimso"  ng-controller="sansimCtrl">
    <div class="text-sm wrapper bg-light lt container_form" >
        <form class="padder" id="form_group" action="list" role="form" theme="simple">
            <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
            <div class="form-group text-center">
                <h3 id="h3_form">Tìm sim số đẹp</h3>
            </div>
            <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
            <div class="col-md-9">


                <div class="form-group ">
                    <div class="col-md-2">
                        <label class="control-label pull-right">Tìm số</label>
                    </div>
                    <div class="col-md-3">
                        <select data-placeholder="Đầu số" name="dauso" class="select2-option" style="width:100%;height:30px;" ng-model="search.dauso">
                            <option value="">Đầu số</option>
                            <option value="10">Đầu 10 số</option>
                            <option value="11">Đầu 11 số</option>
                            <%--<option value="090">090</option>--%>
                        </select>
                    </div>
                    <div class="col-md-7">
                        <input name="so"  data-toggle="tooltip" data-placement="bottom" data-html="true" title="<div class='bg-intruction-search' style='width: 300px;' >
                                                1. Nhập chính xác số cần tìm. Ví dụ: 4896999. <br>
                                                2. Hoặc sử dụng dấu * đại diện cho một chuỗi số bất kỳ:<br>
                                                <ul style='padding-left: 10px;' >
                                                    <li>a. Để tìm số bắt đầu bằng 88, nhập vào 88*</li>
                                                    <li>b. Để  tìm số kết thúc bằng 88, nhập vào *88 hoặc 88</li>
                                                    <li>c. Để tìm số bắt đầu bằng 88 và kết thúc bằng 99, nhập vào 88*99</li>
                                                    <li>d. Để tìm số chứa  88, nhập vào *88*</li>
                                                    <li>e. Để tìm số chứa  88 và  99, nhập vào *88*99*</li>
                                                </ul>
                                                </div>"
                               data-original-title="Tooltip on bottom" placeholder="Số cần tìm" maxlength="7" ng-model="search.so" my-enter="search()" class="input-sm form-control " />
                    </div>
                </div>
                <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                <div class="form-group">
                    <div class="col-md-2">
                        <label class="control-label pull-right">Nhà mạng</label>
                    </div>
                    <div class="col-md-10">
                        <select data-placeholder="Nhà mạng" name="telco" class="select2-option" style="width:100%;height:30px;" ng-model="search.telco">
                            <option value="">Tất cả</option>
                            <option value="1">Viettel</option>
                            <option value="2">Vinaphone</option>
                            <option value="3">Mobifone</option>
                            <option value="4">Gmobile</option>
                            <option value="5">VietNameMobile</option>
                            <option value="0">Khác</option>
                        </select>
                    </div>
                </div>
                <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                <div class="form-group">
                    <div class="col-sm-2"><label class="control-label pull-right">Từ giá</label></div>
                    <div class="col-sm-4">
                        <select data-placeholder="Từ giá" name="from" ng-model="search.from"  class="select2-option" style="width:100%;" >
                            <option value = "">Tất cả</option>
                            <option value = "0">0 nghìn</option>
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
                    <div class="col-sm-2"><label class="control-label pull-right">Đến giá</label></div>
                    <div class="col-sm-4">
                        <select data-placeholder="Đến giá" name="to" ng-model="search.to"  class="select2-option" style="width:100%" >
                            <option value = "">Tất cả</option>
                            <option value = "0">0 nghìn</option>
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
                <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                <div class="form-group">
                    <div class="col-md-2">
                        <label class="control-label pull-right">Tìm theo ngày sinh</label>
                    </div>
                    <div class="col-md-5">
                        <input name="birthday" placeholder="Ngày/Tháng/Năm" maxlength="20" id="birthday" ng-model="search.birthday" my-enter="search()"   onkeypress="return restrictCharacters(this, event, digitsAndSlash);" class="input-sm form-control" />
                        <a style="color:red">{{errorDate}}</a>
                    </div>
                    <div ng-show="count>50">
                        <div class="col-sm-3 " >
                            <input type="text" class="form-control enter input-sm"  size="3" placeholder="Mã xác nhận" name="captcha" ng-model="captcha" />
                            <label class="control-label" ng-show="errorCaptcha" style="color:red">{{errorCaptchaMessage}}</label>
                        </div>
                        <div class="col-sm-2 " style="padding: 0px 0px;margin:0px 0px;">
                            <img ng-src="{{imageUrl}}" id="captcha" style="height: 30px">
                            <a href="javascript:void(0)" ng-click="changeCaptcha();"><span class="fa fa-refresh"></span></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="checkbox">
                    <label>
                        <input type="checkbox"  ng-model="search.confirmStatus"
                               style="width:25px;height:30px;margin-top:-6px; margin-right:4px; margin-left: -27px;"> Chỉ tìm số xác thực (*)
                        <h6 id="note_form">(Số xác thực là số đã được người bán sử dụng nhắn tin xác nhận)</h6>
                    </label>
                </div>
            </div>

            <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
            <div class="form-group text-center">
                <a ng-click="find()" class="btn btn-search">Tìm kiếm</a>
            </div>
            <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
        </form>
    </div>


    <!-- end form -->
    <!--==========================
    Content
    ============================-->
    <section>
        <div id="content">
            <div class="row">
                <div class="col-md-2 padding0">
                    <p class="title">Sim theo giá</p>
                    <ul id="left_table_1">
                        <li ng-repeat="item in listGroupPrice" ng-click="searchGroupPrice(item.id)" ng-class="{'li-active': item.id == groupPriceSelected}"><a href="javascript:void(0)">{{item.name}}</a></li>
                    </ul>
                    <p class="title">Sim theo loại</p>
                    <ul id="left_table_2">
                        <li ng-repeat="item in listGroupMsisdn" ng-click="searchGroupMsisdn(item.id)" ng-class="{'li-active': item.id == groupMsisdnSelected}"><a href="javascript:void(0)">{{item.groupName}}</a></li>
                    </ul>
                    <a href="#"><img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/anh1.jpg" alt="Anh1"></a>
                    <%--<br/>--%>
                    <%--<a href="#"><img class="img-responsive" src="<%=request.getContextPath()%>/assets/images/cus/anh2.jpg" alt="Anh2"></a>--%>
                </div>
                <div class="col-md-8">
                    <h1 class="title_table_2">KẾT QUẢ TÌM KIẾM</h1>

                    <table  class="table table-striped m-b-none">
                        <thead>
                        <tr id="heading_table">
                            <th>STT</th>
                            <th>Số thuê bao</th>
                            <th>Nhà mạng</th>
                            <th>Giá bán</th>
                            <th>Người bán</th>
                            <th>Trạng thái xác thực</th>
                            <th>Đặt mua</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr  ng-repeat="item in page.items track by $index">
                            <td>{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td>{{item[1]}}</td>
                            <td ng-switch on="item[6]">
                                <span ng-switch-when="0">Mạng khác</span>
                                <span ng-switch-when="1"><img src="<%=request.getContextPath()%>/assets/images/cus/viettel.jpg" alt="viettel"></span>
                                <span ng-switch-when="2"><img src="<%=request.getContextPath()%>/assets/images/cus/vinaphone.jpg" alt="mobifone"></span>
                                <span ng-switch-when="3"><img src="<%=request.getContextPath()%>/assets/images/cus/mobifone.jpg" alt="vinaphone"></span>
                                <span ng-switch-when="4"><img src="<%=request.getContextPath()%>/assets/images/cus/gmobile.jpg" alt="gmobile"></span>
                                <span ng-switch-when="5"><img src="<%=request.getContextPath()%>/assets/images/cus/vietnamobile.jpg" alt="vietnamobile"></span>
                                <span ng-switch-default></span>
                            </td>
                            <td>{{item[2]|currency:"":0}}</td>
                            <td  >{{item[4]}}</td>
                            <td ng-switch on="item[5]">
                                <span ng-switch-when="1">Đã xác thực</span>
                                <span ng-switch-default>Chưa xác thực</span>
                            </td>
                            <td>
                                <a class="btn btn-color" href="<%=request.getContextPath()%>/sim/{{item[0]}}">Chi tiết</a>
                            </td>
                        </tr>
                        <tr ng-if="loadding">
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"><h3></h3></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"><i class="fa fa-2x fa-spinner fa-spin"></i></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                        </tr>
                        <tr ng-if="page.items.length==0 && !loadding">
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"><h3>{{noData}}</h3></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                        </tr>
                        </tbody>
                    </table>
                    <nav id="pagination_page"  ng-if="page.pageCount>1">
                        <ul class="pagination">
                            <li class="page-item"  ng-if="page.pageNumber>1" >
                                <a ng-click="beforeLoadPage(page.pageNumber-1)" href="javascript:void(0)" class="page-link" tabindex="-1">Trước</a>
                            </li>
                            <li class="page-item"  ng-show="item<page.pageNumber" ng-repeat="item in page.pageList">
                                <a ng-click="beforeLoadPage(item)" href="javascript:void(0)"> {{item}}</a>
                            </li>
                            <li class="page-item active"  ng-show="item==page.pageNumber" ng-repeat="item in page.pageList">
                                <a class="page-link" href="javascript:void(0)"> [{{item}}]</a>
                            </li>
                            <li class="page-item"  ng-show="item>page.pageNumber" ng-repeat="item in page.pageList">
                                <a ng-click="beforeLoadPage(item)" href="javascript:void(0)"> {{item}}</a>
                            </li>
                            <li class="page-item" ng-if="page.pageNumber<page.pageCount">
                                <a class="page-link" ng-click="beforeLoadPage(page.pageNumber+1)" href="javascript:void(0)">Tiếp</a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <div class="col-md-2 padding0">
                    <p class="title">Sim theo năm sinh</p>
                    <ul id="right_table_1">
                        <li ng-repeat="item in listGroupYear" ng-click="searchGroupYear(item.id)" ng-class="{'li-active': item.id == groupYearSelected}"><a href="javascript:void(0)">{{item.name}}</a></li>
                    </ul>
                    <%--<p class="title">Thông tin</p>--%>
                    <%--<figure id="figure_index">--%>
                    <%--<div class="" ng-repeat="item in news.items" style="margin-bottom:10px;">--%>
                    <%--<a href="<%=request.getContextPath()%>/tin-tuc/{{item[0]}}"><img class="img-responsive" src="${urlImage}{{item[4]}}" alt="tin tuc"></a>--%>
                    <%--<figcaption><a href="<%=request.getContextPath()%>/tin-tuc/{{item[0]}}">{{item[1]}}</a></figcaption>--%>
                    <%--</div>--%>
                    <%--<br/>--%>
                    <%--</figure>--%>
                    <div>
                        <div class="fb-page" data-href="https://www.facebook.com/sansim.vn/" data-tabs="timeline" data-height="300" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true"><blockquote cite="https://www.facebook.com/facebook" class="fb-xfbml-parse-ignore"><a href="https://www.facebook.com/facebook">Facebook</a></blockquote></div>
                    </div>
                </div>
            </div>

        </div>
    </section>
</section>









