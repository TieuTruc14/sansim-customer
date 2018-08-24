<%@ page import="com.osp.model.Customer" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    Customer manager = (Customer) request.getSession().getAttribute("userShop");
%>
<footer style="background: none repeat scroll 0 0 #EDEDED;width: 100%;overflow: hidden ;padding-left:  0px;max-width:  2000px; min-height: 228px" class="footerSS">
    <div class="container py-5" style="width: 100% ; overflow: hidden">
        <div class="row" style="width: 100% ; overflow: hidden">
        <div class="col-md-3 fb-page" data-href="https://www.facebook.com/sansim.vn" data-tabs="timeline" data-height="130" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true"><blockquote cite="https://www.facebook.com/sansim.vn" class="fb-xfbml-parse-ignore"><a href="https://www.facebook.com/sansim.vn">Sàn Sim</a></blockquote></div>
        <div class="col-md-2">
          <h5>VỀ SANSIM.VN</h5>  
          <ul class="list-unstyled text-small footer-link">
            <li><a class="text-muted" href="http://sansim.vn/gioi-thieu"> <i class="fa fa-angle-right fa-size"></i>Giới thiệu</a></li>
            <li><a class="text-muted" href="http://sansim.vn/huong-dan"> <i class="fa fa-angle-right fa-size"></i>Hướng dẫn</a></li>
            <li><a class="text-muted" href="http://sansim.vn/tin-tuc"> <i class="fa fa-angle-right fa-size"></i>Tin tức</a></li>
            <li><a class="text-muted" href="http://sansim.vn/lien-he"> <i class="fa fa-angle-right fa-size"></i>Liên hệ</a></li>
          </ul>
        </div>
        <div class="col-md-6">
          <h5>CÔNG TY CỔ PHẦN CÔNG NGHỆ PHẦN MỀM VÀ NỘI DUNG SỐ OSP</h5>
          <ul class="list-unstyled text-small footer-link">
            <!--<li><a class="text-muted" href="#"> <i class="fa fa-copyright"></i> Copyright © 2015 CÔNG TY CỔ PHẦN CÔNG NGHỆ PHẦN MỀM VÀ NỘI DUNG SỐ OSP</a></li>-->
            <li><a class="text-muted" href="#"> <i class="fa fa-globe fa-size"></i> Website: www.osp.com.vn</a></li>
            <li><a class="text-muted" href="#"> <i class="fa fa-envelope fa-size"></i> E-mail: info@osp.com.vn</a></li>
            <li><a class="text-muted" href="#"> <i class="fa fa-home fa-size"></i> Địa chỉ: Tầng 7, Toà Nhà Đại Phát, Số 82, Phố Duy Tân, Cầu Giấy, Hà Nội.</a></li>
            <li><a class="text-muted" href="#"> <i class="fa fa-phone fa-size"></i> Hotline: 0983.992.892 (Hỗ trợ trong giờ hành chính)</a></li>
          </ul>
        </div>
      </div>
    </div>
  </footer>