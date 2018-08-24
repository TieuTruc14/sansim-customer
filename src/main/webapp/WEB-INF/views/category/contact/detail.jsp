<%@page import="java.util.Objects"%>
<%@page import="com.osp.model.Customer"%>
<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
    
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
</style>

<div class="container-sim">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Liên hệ</li>
        </ol>
    </nav>
    <%
        Customer manager = (Customer) request.getSession().getAttribute("userShop");
        
    %> 
    <c:if test="<%= manager != null && manager.getLinkMap() != null %>">
        <div class="row contact">
            <!--<div class="col-xs-12 col-sm-6 col-md-6 p-3" style="max-width: 100% !important; max-width: 500px !important;">-->
               
               <%= manager.getLinkMap() %>
                
                <!--<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d931.0057749715002!2d105.78207592925358!3d21.031761699124818!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab4b50e26a67%3A0x6c815bc9efd9bd9e!2zMiBOZ8O1IDgyIER1eSBUw6JuLCBE4buLY2ggVuG7jW5nIEjhuq11LCBD4bqndSBHaeG6pXksIEjDoCBO4buZaSwgVmlldG5hbQ!5e0!3m2!1sen!2s!4v1521250727758" width="100%" height="500" frameborder="0" style="border:0" allowfullscreen></iframe>-->
            <!--</div>-->
            <div class="col-xs-12 col-sm-6 col-md-6 p-3">
                <div class="contact_page">
                    <p class="contact"> <i class="fa fa-home"></i> Địa chỉ: <%= manager.getAddress() %></p>
                    <p class="contact"> <i class="fa fa-envelope"></i> Email: <%= Objects.equals(manager.getEmail(), null)? "":  manager.getEmail() %> </p>
                    <p class="contact"> <i class="fa fa-phone"></i> Hotline: <%= manager.getMsisdn() %> </p>
                    <form>
                        <div class="form-group">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Tên">
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Email">
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Tiêu đề">
                            </div>
                            <div class="form-group">
                                <label >Nội dung</label>
                                <textarea  class="form-control1" rows="5"></textarea>
                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary">GỬI</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>              


    </c:if>
    <c:if test="<%= manager == null%>">
        <div class="row contact">
            <div class="col-xs-12 col-sm-6 col-md-6 p-3">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d931.0057749715002!2d105.78207592925358!3d21.031761699124818!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab4b50e26a67%3A0x6c815bc9efd9bd9e!2zMiBOZ8O1IDgyIER1eSBUw6JuLCBE4buLY2ggVuG7jW5nIEjhuq11LCBD4bqndSBHaeG6pXksIEjDoCBO4buZaSwgVmlldG5hbQ!5e0!3m2!1sen!2s!4v1521250727758" width="100%" height="500" frameborder="0" style="border:0" allowfullscreen></iframe>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-6 p-3">
                <div class="contact_page">
                    <p class="contact"> <i class="fa fa-home"></i> Địa chỉ: Số 2, Ngõ 82 Đường Duy Tân, Phường Dịch Vọng Hậu, Quận Cầu Giấy, TP.Hà Nội </p>
                    <p class="contact"> <i class="fa fa-envelope"></i> Email: sansim.cskh@gmail.com </p>
                    <p class="contact"> <i class="fa fa-phone"></i> Hotline: 0983.992.892 (Hỗ trợ trong giờ hành chính) </p>
                    <form>
                        <div class="form-group">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Tên">
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Email">
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Tiêu đề">
                            </div>
                            <div class="form-group">
                                <label >Nội dung</label>
                                <textarea  class="form-control1" rows="5"></textarea>
                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary">GỬI</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

</div>
