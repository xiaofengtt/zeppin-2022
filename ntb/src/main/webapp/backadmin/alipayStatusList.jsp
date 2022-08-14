<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<script type="text/template" id="queboxTpl">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td>{{:#index + 1}}</td>
        <td>
            <a href="./alipayStatusListEdit.jsp?uuid={{:uuid}}">{{:payment}}</a>
        </td>
        <td>{{:paymentDes}}</td>
        <td>
            {{if flagSwitch == true}}
                <span class="color_green">开启</span>
            {{else}}
                <span class="color_red">关闭</span>
            {{/if}}
        </td>

        <td>
            {{if flagSwitch == true}}
                <a class="change" href="javascript:void(0);" data-uuid="{{:uuid}}" data-status=false>关闭</a>
            {{else flagSwitch == false}}
                <a class="change" href="javascript:void(0);" data-uuid="{{:uuid}}" data-status=true>开启</a>
            {{/if}}
            <a href="./alipayStatusListEdit.jsp?uuid={{:uuid}}">编辑</a>
            <a class="delete" href="javascript:void(0);" data-uuid="{{:uuid}}">删除</a>
        </td>
    </tr>
</script>
<style>
    .screening{
        margin-top:0;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00200026" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a class="current">支付方式</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="screening">
                    <div class="item filter">
                        <%-- <label>账户类型：</label>
                        <div>
                            <a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
                            <a id="collect">募集户<span id="collectCount">(0)</span></a>
                            <a id="invest">投资户<span id="investCount">(0)</span></a>
                            <a id="unchecked">结算户<span id="redeemCount">(0)</span></a>
                            <a id="third">第三方<span id="thirdCount">(0)</span></a>
                        </div> --%>
                        <p class="text_title">支付方式列表</p>
                    </div>
                    <a href="./alipayStatusListAdd.jsp" class="add_account">＋添加</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="5%">#</th>
                        <th>名称</th>
                        <th>描述</th>
                        <th>开关</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt"></tbody>
                </table>

                <div id="pageTool"></div>
                <%--//--%>
            </div>
        </div>
    </div>
<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script>
<script type="text/javascript" src="./js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/alipayStatusList.js"></script>
</body>
</html>
