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
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/imgMsg.css">
<title>牛头帮－后台管理系统</title>
<style>
    .add_account_form .form_item .bar {
        width:100px;
        text-align: left;
    }
    .add_account_form .form_item .bar-inner{
        background:#0088cc;
        padding:0;
        margin:0;
        width:0%;
        height:18px;
        transition: all 0.2s linear;
        text-align: left;
    }
    .add_account_form .form_item #rate{
        text-align: left;
    }
</style>

</head>

<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00200025" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a href="./versionControl.jsp">版本控制</a><span>></span><a class="current">添加</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="text_box add_account_form">
                    <p class="text_title" style="margin-bottom:20px;">添加版本信息</p>
                    <div class="form_item">
                        <span><i>*</i> 文件名：</span>
                        <span style="text-align:left;width:auto;"><a href="javascript:void(0);" id="file_html_name">请上传文件...</a></span>
                        <span class="bar"><span class="bar-inner"></span></span>
                        <span id="rate"></span>
                    </div>
                    <div class="upload_box">
                        <form class="" action="../rest/backadmin/resource/add" method="post" id="upload_html">
                            <input type="file" name="file" value="" id="file_html">
                            <a href="javascript:void(0);">添加文件</a>
                        </form>
                    </div>
                    <form:form class="" action="../rest/backadmin/version/add" method="post" id="sub">
                        <input type="hidden" name="resource" value="" id="pkg_id">
                        <div class="form_item">
                            <span><i>*</i> 版本号：</span>
                            <input type="number" name="version" id="version" value="" autocomplete="off" placeholder="请输入版本号...">
                        </div>
                        <div class="form_item">
                            <span><i>*</i> 版本名称：</span>
                            <input type="text" name="versionName" id="versionName" value="" autocomplete="off" placeholder="请输入版本名称...">
                        </div>
                        <div class="form_item">
                            <span><i>*</i> 设备平台：</span>
                            <input type="text" name="" value="" id="device" placeholder="请上传文件自动识别设备平台..." readonly>
                            <input type="hidden" name="device" value="" id="deviceValue">
                        </div>
                        <div class="form_item">
                            <span><i>*</i> 强制更新：</span>
                            <select class="" name="flagCompel" id="flagCompel">
                                <option value=false>否</option>
                                <option value=true>是</option>
                            </select>
                        </div>


                        <div class="form_item">
                            <span><i></i> 版本描述：</span>
                            <textarea name="versionDes" rows="" cols="" id="versionDes"></textarea>
                        </div>

                        <div class="btn_group">
                            <input type="button" name="" value="取消" onclick="window.location.href = document.referrer;">
                            <input type="button" name="" value="保存" id="add_submit">
                        </div>
                    </form:form>
                </div>




            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/versionControlAdd.js"></script>
</body>
</html>
