<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a class="current">栏目管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="screening">
                    <div class="item filter">

                    </div>
                    <a href="./addColumn.jsp" class="add_account">添加栏目</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th>栏目地址</th>
                        <th>栏目名称</th>
                        <th>仅新用户可见</th>
                        <th>顺序</th>
                        <th>栏目类型</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt">
                        <tr>
                            <td>appindex</td>
                            <td><a href="#">APP首页</a></td>
                            <td>否</td>
                            <td>1</td>
                            <td>理财产品</td>
                            <td>
                                <a href="./nextColumnList.jsp?uuid={{:uuid}}">管理下级栏目</a>
                                <a href="./editColumn.jsp?uuid={{:uuid}}">修改</a>
                                <a href="javascript:void(0);" class="delete">删除</a>
                                <input type="hidden" name="" value="{{:uuid}}">
                            </td>
                        </tr>
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
<script type="text/javascript" src="./js/url.min.js"></script>
<script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/columnList.js"></script>
