<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/bootstrap.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/QCBcompanyConfirm.css">
<%-- <link rel="stylesheet" href="./css/uploadfile.css"> --%>
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="queboxTpl">
    <h2 id="title">企业资料</h2>
    <p>营业执照</p>
    <img src="..{{:businessLicenceUrl}}" alt="">
    <a href="..{{:businessLicenceUrl}}" target="_blank">查看大图</a>

    <p>企业授权材料</p>
    <img src="..{{:evidenceUrl}}" alt="">
    <a href="..{{:evidenceUrl}}" target="_blank">查看大图</a>

    <p>授权人身份证</p>
    <img id="id-card-face" src="..{{:idcardFaceUrl}}" alt="">
     <img id="id-card-back" src="..{{:idcardBackUrl}}" alt="">
    <p>历史审核记录</p>

    

</script>
</head>
<body>
    <input id="scode" type="hidden" value="00800081" />
    <div class="contain">
        <div class="contain-right">

            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="text_box" style="margin:10px auto;width:95%;">
                    <%-- <div id="queboxCnt">

                    </div> --%>
                    <h2 id="title">企业资料</h2>
                    <p>营业执照</p>
                    <img src="" alt="" id="businessLicenceIMG">
                    <a href="" id="businessLicenceHREF" target="_blank">查看大图</a>

                    <p>企业授权材料</p>
                    <img src="" alt="" id="evidenceIMG">
                    <a href="" target="_blank" id="evidenceHREF">查看大图</a>

                    <p>授权人身份证</p>
                    <img id="id-card-face" src="" id="idcardFaceIMG">
                    <img id="id-card-back" src="" id="idcardBackIMG">
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="js/uploadFile.js"></script>
    <script type="text/javascript" src="js/changePrice.js"></script>
    <script type="text/javascript" src="./js/QCBCompanyConfirm.js"></script>


</body>
</html>
