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
<style>
    #sub{
        margin-bottom:0
    }
    .add_account_form .form_item{
        margin-bottom:0;
    }
</style>
</head>

<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="./advertList.jsp">广告内容管理</a><span>></span><a class="current">添加</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="text_box add_account_form">
                    <p class="text_title" style="margin-bottom:20px;">添加广告内容</p>
                    <form:form class="" action="../rest/backadmin/advert/add" method="post" id="sub">
                        <input type="hidden" name="picture" value="" id="img_id">
                        <div class="form_item">
                            <span><i>*</i> 标题：</span>
                            <input type="text" name="title" id="title" value="" autocomplete="off" placeholder="请输入标题...">
                        </div>
                    </form:form>
                    <div class="upload_box">
                        <form class="" action="../rest/backadmin/resource/add" method="post" id="upload_img">
                            <input type="file" name="file" value="" id="file_img">
                            <a href="javascript:void(0);">上传图片</a>
                        </form>
                    </div>
                    <div class="form_item">
                        <span><i>*</i> 图片：</span>
                        <span style="text-align:left;width:auto;max-width:50%;">
                            <a href="" target="_blank"><img src="" alt="" style="height:300px;" id="file_img_name"></a>
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
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/advertAdd.js"></script>
</body>
</html>
