<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛头帮－后台管理系统</title>

<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/imgMsg.css">
</head>

<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a>图文内容管理</a><span>></span><a class="current">修改图文信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="text_box add_account_form">
                    <p class="text_title" style="margin-bottom:20px;">添加图文内容</p>
                    <input type="hidden" name="" value="" id="html_id">
                    <input type="hidden" name="" value="" id="img_id">
                    <div class="form_item">
                        <span><i>*</i> 标题：</span>
                        <input type="text" name="companyName" id="bt" value="" autocomplete="off" placeholder="请输入标题...">
                    </div>
                    <div class="upload_box">
                        <form class="" action="index.html" method="post">
                            <input type="file" name="" value="">
                            <a href="javascript:void(0);">添加文件</a>
                        </form>
                        &ensp;
                        <form class="" action="index.html" method="post">
                            <input type="file" name="" value="">
                            <a href="javascript:void(0);">添加封面</a>
                        </form>
                    </div>
                    <div class="form_item">
                        <span><i>*</i> 文件链接：</span>
                        <span style="text-align:left;width:auto;"><a href="javascript:void(0);" id=""></a></span>
                    </div>
                    <div class="form_item">
                        <span><i>*</i> 文件名：</span>
                        <input type="text" name="companyName" id="wjm" value="" autocomplete="off" placeholder="请输入标题..."><small class="color_red">文件名称不唯一</small>
                    </div>
                    <div class="form_item">
                        <span><i>*</i> 封面：</span>
                        <span style="text-align:left;width:auto;max-width:50%;">
                            <img src="" alt="" style="width:100%;">
                        </span>
                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer;">
                        <input type="button" name="" value="保存" id="add_submit">
                    </div>
                </div>




            </div>
        <div>
    </div>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/imgMsgEdit.js"></script>
</body>
</html>