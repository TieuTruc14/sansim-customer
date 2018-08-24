<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <title><tiles:insertAttribute name="title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/osp.ico" />
    <meta name="description" content="san sim"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <meta property="og:title" content="${PAGE_META_TITLE != null ? PAGE_META_TITLE : 'Sàn Sim Online' }" />
    <meta property="og:site_name" content="San sim so dep"/>
    <meta property="og:description" content="${PAGE_META_DESCRIPTION != null ? PAGE_META_DESCRIPTION : 'Sàn giao dịch sim số đẹp uy tín' }" />
    <meta property="article:author" content="https://www.facebook.com/sansim.vn/" />
    <meta property="article:publisher" content="https://www.facebook.com/sansim.vn/" />
    <meta property="og:image" content="${PAGE_META_IMAGE != null ? '/upload/'.concat(PAGE_META_IMAGE) : 'http://sansim.vn/assets/static/images/logo-san-sim.jpg' }" />
    <meta property="og:type" content="article" />    
        
        
    <!--<meta property="og:title" content="Sàn Sim Online" />-->
    <!--<meta property="og:image" content="" />-->
    <meta property="og:image:type" content="image/png">
    <meta property="og:image:width" content="1920">
    <meta property="og:image:height" content="1200">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/bootstrap/dist/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/fontawesome/css/fontawesome-all.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font.css" type="text/css" cache="false"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/css/plus.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/css/additional.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/select2-bootstrap4/select2.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/static/select2-bootstrap4/select2-bootstrap4.css" type="text/css"/>
    <script src="<%=request.getContextPath()%>/assets/static/js/jquery-3.3.1.min.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/datepicker/datepicker.css" type="text/css"/>

    <script src="<%=request.getContextPath()%>/assets/note/js/datepicker/bootstrap-datepicker.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
    <script src="<%=request.getContextPath()%>/assets/static/select2-bootstrap4/select2.full.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/libs/moment.min.js"></script><!--ho tro xu ly trong js ve time convert-->
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular-sanitize.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/customer/common.js"></script>

    <%--for share--%>
    <script src="<%=request.getContextPath()%>/assets/html2canvas/canvas2image.js"></script>
    <script src="<%=request.getContextPath()%>/assets/html2canvas/html2canvas.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/html2canvas/base64.js"></script>
    <script src="<%=request.getContextPath()%>/assets/html2canvas/jquery.plugin.html2canvas.js"></script>

    <script>
        var preUrl='<%=request.getContextPath()%>';
    </script>
</head>
<body>

<div id="fb-root"></div>
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = 'https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v3.0&appId=940975612746291&autoLogAppEvents=1';
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<div class="container" style="width: 100% ; overflow: hidden">
    <div class="container-modify">
    <section >
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="page" />
    </section>
    <script src="<%=request.getContextPath()%>/assets/static/bootstrap/assets/js/vendor/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/static/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/static/js/input-mask.js"></script>
    <script src="<%=request.getContextPath()%>/assets/static/js/plus.js"></script>
    </div>
</div>
<tiles:insertAttribute name="footer" />

</body>
</html>
