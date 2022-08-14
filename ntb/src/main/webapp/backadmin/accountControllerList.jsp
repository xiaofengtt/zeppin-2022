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
        <td title=""><a href="./accountMessage.jsp?uuid={{:uuid}}">{{:accountName}}</a></td>
        <td title="">{{:accountNum}}</td>
        <td title="">{{:companyName}}</td>
        <td title="">
            {{if type == "third"}}第三方{{/if}}
            {{if type == "invest"}}投资户{{/if}}
            {{if type == "collect"}}募集户{{/if}}
            {{if type == "redeem"}}结算户{{/if}}
        </td>
        <td title="">{{:bankName}}</td>
        <td class="investment" title="">{{:investment - totalRedeem}}</td>
        <td title="">{{:accountBalanceCN}}</td>
        <td>
            <a href="./recharge.jsp?uuid={{:uuid}}">充值</a>
            <a href="./transferSelect.jsp?uuid={{:uuid}}">转账</a>
            <!-- <a href="./drawCash.jsp?uuid={{:uuid}}">提现</a> -->
            {{if type != "third"}}
                <a class="invest" href="./selectProductList.jsp?uuid={{:uuid}}&bank={{:bank}}">投资</a>
            {{/if}}
            <a href="./fundInput.jsp?uuid={{:uuid}}">支出</a>
        </td>
    </tr>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">账户管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="account_price">
                    <div class="account_price_header">
                        <p class="account_price_title">账户一览</p>
                        <div class="account_price_header_btn">
                            <a href="#">经营分析</a>
                            <a href="#">资金流水</a>
                        </div>
                    </div>
                    <div class="account_price_content">
                        <div class="account_price_content_key">
                            <span>平台可用余额</span>
                            <span>企业专属资金</span>
                            <span>用户总投资额</span>
                            <span>企业投资总额</span>
                            <span>用户累计收益</span>
                            <span>

                                <p>企财宝员工</p>
                                <p>可提现总金额</p>
                            </span>
                        </div>
                        <div class="account_price_content_value">
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                            <span><i class="balance_value"></i><i class="balance_none">******</i></span>
                        </div>
                    </div>
                </div>

                <div class="screening">
                    <div class="item filter">
                        <label>账户类型：</label>
                        <div>
                            <a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
                            <a id="collect">募集户<span id="collectCount">(0)</span></a>
                            <a id="invest">投资户<span id="investCount">(0)</span></a>
                            <a id="unchecked">结算户<span id="redeemCount">(0)</span></a>
                            <a id="third">第三方<span id="thirdCount">(0)</span></a>
                        </div>
                    </div>
                    <a href="./addAccount.jsp" class="add_account">+&ensp;添加账户</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="5%">#</th>
                        <th>别名</th>
                        <th>账号</th>
                        <th>开户名</th>
                        <th width="8%">类型</th>
                        <th width="8%">开户行</th>
                        <th width="8%">投资金额</th>
                        <th width="8%">账户余额</th>
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
    <script src="js/changePrice.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/accountControllerList.js"></script>
</body>
</html>
