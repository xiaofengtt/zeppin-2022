<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/base.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00200026" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a href="./alipayStatusList.jsp">支付方式</a><span>></span><a class="current">修改</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <form:form class="add_account_form" action="../rest/backadmin/bkpayment/edit" method="post" id="add">

                    <p class="text_title" style="margin:0 0 20px 95px">支付方式</p>
                    <input type="hidden" name="uuid" value="" id="uuid">
                    <div class="form_item">
                        <span><i>*</i> 名称</span>
                        <input type="text" name="paymentName" id="paymentName" value="" autocomplete="off" placeholder="请输入名称...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 描述</span>
                        <input type="text" name="paymentDes" id="paymentDes" value="" autocomplete="off" placeholder="请输入描述...">
                    </div>


                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer;">
                        <input type="button" name="" value="保存" id="add_submit">
                    </div>
                </form:form>


            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/alipayStatusListEdit.js"></script>
</body>
</html>
