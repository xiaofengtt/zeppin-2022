<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛头帮－后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/coupon.css">
<script type="text/template" id="queboxTpl">
    <tr>
        <td class="card_td">
            <div class="card">

                {{if couponType == "cash"}}
                    <span class="card_title">{{:couponPrice}}</span><small class="card_title_small">元</small>
                    <span class="card_title_right color_orange">现金券</span>
                {{else}}
                    <span class="card_title">+{{:couponPrice}}</span><small class="card_title_small">%</small>
                    <span class="card_title_right color_orange">加息券</span>
                {{/if}}

                <!-- {{if couponType == "cash"}}
                    <span class="card_title_right color_orange">现金券</span>
                {{else}}

                    <span class="card_title_right color_orange">加息券</span>
                {{/if}} -->
                <p class="card_remark_top">可用于{{:minInvestAccountCN}}元以上的投资</p>
                {{if deadline != null}}
                    <p class="card_remark_bottom">发放起{{:deadline}}日内有效</p>
                {{/if}}
            </div>
        </td>
        <td>{{:couponName}}</td>
        <td>{{:creatorCN}}</td>
        <td>{{:createtimeCN}}</td>
        <td>
            <a href="./cardListEdit.jsp?uuid={{:uuid}}" class="edit_card">编辑</a>
            <a href="javascript:void(0);" class="delete" data-uuid="{{:uuid}}">删除</a>
        </td>
    </tr>
</script>
<style>
    .card_td{
        padding:10px;
        height:140px;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00400046" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a class="current">优惠券管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="screening">
                    <div class="item filter">

                    </div>
                    <a href="./cardListAdd.jsp" class="add_account" style="right:120px" id="addCard">添加优惠券</a>
                    <a href="./ruleList.jsp" class="add_account">投放策略管理</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>
                <table class="msg_table" cellspacing="0" cellpadding="0">
                    <tr class="first_tr">
                        <th width="30%">优惠券</th>
                        <th>名称</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>

                    <tbody id="queboxCnt">

                    </tbody>

                </table>
                <div id="pageTool">

                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/cardList.js"></script>
</body>
</html>
