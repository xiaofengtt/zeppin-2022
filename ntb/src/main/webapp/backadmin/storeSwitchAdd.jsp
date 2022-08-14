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

</head>

<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00200029" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a href="./storeSwitch.jsp">应用商店管理</a><span>></span><a class="current">添加</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="text_box add_account_form">
                    <p class="text_title" style="margin-bottom:20px;">添加应用商店信息</p>
                    <form:form class="" action="../rest/backadmin/market/add" method="post" id="sub">
                        <div class="form_item">
                            <span><i>*</i> 应用市场标识码：</span>
                            <input type="text" name="webmarket" id="webmarket" value="" autocomplete="off" placeholder="请输入应用市场标识码...">
                        </div>
                        <div class="form_item">
                            <span><i>*</i> 应用市场名称：</span>
                            <input type="text" name="webmarketName" id="webmarketName" value="" autocomplete="off" placeholder="请输入应用市场名称...">
                        </div>
                        <div class="form_item">
                            <span><i>*</i> 版本名：</span>
                            <select id="version" name="version">
                                <option value="0">请选择版本名...</option>
                            </select>
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
    <script src="./js/storeSwitchAdd.js"></script>
</body>
</html>
