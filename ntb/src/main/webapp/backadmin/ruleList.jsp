<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛头帮－后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<script type="text/template" id="queboxTpl">
    <tr>
        <td>{{:strategyIdentification}}</td>
        <td>{{:couponMap.name}}</td>

        {{if status == "open"}}
            <td class="color_green">开启</td>
        {{else}}
            <td class="color_red">关闭</td>
        {{/if}}

        <td>{{:creatorCN}}</td>
        <td>{{:createtimeCN}}</td>
        <td>
            {{if status == "open"}}
                <a href="javascript:void(0);" class="change" data-uuid="{{:uuid}}" data-status="false">关闭</a>
            {{else}}
                <a href="javascript:void(0);" class="change" data-uuid="{{:uuid}}" data-status="true">开启</a>
            {{/if}}
            <a href="./ruleListEdit.jsp?uuid={{:uuid}}" class="edit">编辑</a>
            <a href="javascript:void(0);" class="delete" data-uuid="{{:uuid}}">删除</a>
        </td>
    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00400045" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <form:form class="" method="post"></form:form>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a>优惠券管理</a><span>></span><a class="current">投放策略</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="screening">
                    <div class="item filter">

                    </div>
                    <a href="./ruleListAdd.jsp" id="add" class="add_account">添加投放策略</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th>投放ID</th>
                        <th>投放内容</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt">

                    </tbody>
                </table>

                <div id="pageTool"></div>
                <%--//--%>
            </div>
        </div>
    </div>


</body>
</html>
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/ruleList.js"></script>
