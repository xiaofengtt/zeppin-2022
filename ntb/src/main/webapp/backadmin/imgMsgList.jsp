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
<link rel="stylesheet" href="./css/rechargeDrawStatus.css">
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
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a class="current">图文内容管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="screening">
                    <div class="button_search">
                        <div class="search_div" style="width:200px;">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="搜索..." id="search">
                                <label class="input-group-addon" id="searchButton"></label>
                            </div>
                        </div>
                    </div>
                    <a href="./imgMsgAdd.jsp" class="add_account">添加图文信息</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="30%">图文内容标题</th>
                        <th>封面图片</th>
                        <th>发布状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt">
                        <tr>
                            <td><a href="#">牛头理财品牌介绍H5页面（APP版）</a></td>
                            <td>有</td>
                            <td>已发布</td>
                            <td>用户1</td>
                            <td>2012.12.12 11:11:11</td>
                            <td>
                                <a href="./imgMsgEdit.jsp">编辑</a>
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
<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/imgMsgList.js"></script>
