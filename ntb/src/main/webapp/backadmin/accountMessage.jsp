<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/accountMessage.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<%--企业账户信息模板--%>
<script type="text/template" id="account_mssage_tpl">
    <div class="account_message text_box">
        {{if type != "third"}}
            <div class="account_logo">
                <img src="..{{:bankIconUrl}}">
            </div>
        {{/if}}
        <div class="message_item">
            <span><i>*</i> 账户类型：</span>
            <p>{{:typeCN}}</p>
        </div>

        {{if type != "third"}}
            <div class="message_item">
                <span><i>*</i> 开户行：</span>
                <p>{{:bankName}}</p>
            </div>
            <div class="message_item">
                <span><i>*</i> 支行：</span>
                <p>{{:branchBankName}}</p>
            </div>
        {{/if}}

        <div class="message_item">
            <span><i>*</i> 开户名：</span>
            <p>{{:companyName}}</p>
        </div>

        <div class="message_item">
            <span><i>*</i> 开户别名：</span>
            <p>{{:accountName}}</p>
        </div>

        <div class="message_item">
            <span><i>*</i> 账号：</span>
            <p>{{:accountNum}}</p>
        </div>



            <!-- <div class="message_item">
                <span><i>*</i> 绑定账户：</span>
                <p>{{:bindAccountName}}</p>
            </div> -->

        <div class="message_item">
            <span><i>*</i> 状态：</span>
            <p>{{:statusCN}}</p>
        </div>
        <a href="./editAccount.jsp?uuid={{:uuid}}" id="edit" style="position:absolute;bottom:10%;right:8%;">修改</a>
    </div>

    <div class="account_balance text_box">
        <p>账户余额</p>
        <span>{{:accountBalanceCN}}</span>
    </div>
</script>

<%--资金总额模板--%>
<script type="text/template" id="invest_all">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td>{{:bankFinancialProductName}}</td>
        <td>{{:returnRateCN}}%</td>
        <td>{{:maturityDateCN}}</td>
        <td>{{:totalAmountCN}}</td>
    </tr>
</script>

<%--资金流水模板--%>
<script type="text/template" id="balance_all">
<tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
    {{if type == "expend" || type == "invest" || type == "takeout"}}
        {{if totalAmount == 0}}
            <td class="color_green">{{:totalAmountCN}}</td>
        {{else}}
            <td class="color_green">-{{:totalAmountCN}}</td>
        {{/if}}
    {{else typeCN == "转入"}}
        <td class="color_orange">{{:totalAmountCN}}</td>
    {{else typeCN == "转出"}}
        <td class="color_green">-{{:totalAmountCN}}</td>
    {{else type == "qcb_takeout" || type == "qcb_payroll" || type == "emp_takeout"}}
        <td class="color_green">-{{:totalAmountCN}}</td>
    {{else type == "qcb_recharge" || type == "emp_fillin"}}
        <td class="color_orange">{{:totalAmountCN}}</td>
    {{else}}
        <td class="color_orange">{{:totalAmountCN}}</td>
    {{/if}}
    <td>{{:typeCN}}</td>
    <td>{{:abountName}}</td>
    <td>{{:createtimeCN}}</td>
    {{if creatorName != null}}
        <td>{{:creatorName}}</td>
    {{else}}
        <td> - </td>
    {{/if}}
    <td>
		{{if typeCN == "转入"}}
			{{:accountBalanceInCN}}
		{{else}}
        	{{:accountBalanceCN}}
		{{/if}}
    </td>
    <td><a href="./historyMessage.jsp?uuid={{:uuid}}" target="_blank">查看</a></td>
</tr>
</script>

<style>
    .condition{
        margin-top:16px;
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a class="current">账户信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="account_message_Cnt">

                </div>

                <div class="all_price text_box">
                    <div class="all_price_title">
                        <p>投资总额</p>
                        <span id="totalAmount"></span>
                    </div>
                    <table cellpadding="0" cellspacing="0" class="msg_table">
                        <tr class="first_tr">
                            <th width="40%">投资品名称</th>
                            <th width="20%">产品利率</th>
                            <th width="20%">到期日</th>
                            <th width="20%">投资金额</th>
                        </tr>

                        <tbody id="all_price_Cnt"></tbody>
                    </table>
                    <div id="pageTool_1" style="margin-top:10px;">

                    </div>
                </div>

                <div class="price_journal text_box">
                    <p class="price_journal_title">资金流水</p>
                    <div class="condition">
                        <div class="statusDiv shortStatusDiv filter">
                            <div>
                                <a class="statusLight" id="all">全部<span id="allCount">(0)</span></a>
                                <a id="transfer">转账<span id="transferCount">(0)</span></a>
                                <a id="recharge">企业充值<span id="rechargeCount">(0)</span></a>
                                <a id="expend">支出<span id="expendCount">(0)</span></a>
                                <a id="invest">投资<span id="investCount">(0)</span></a>
                                <a id="redeem">赎回<span id="redeemCount">(0)</span></a>
                                <a id="return">收益<span id="returnCount">(0)</span></a>
                                <a id="takeout">用户提现<span id="takeoutCount">(0)</span></a>
                                <a id="fillin">用户充值<span id="fillinCount">(0)</span></a>
                                <a id="qcb_takeout">企财宝企业提现<span id="qcb_takeoutCount">(0)</span></a>
                                <a id="qcb_recharge">企财宝企业充值<span id="qcb_rechargeCount">(0)</span></a>
                                <a id="emp_takeout">企财宝员工提现<span id="emp_takeoutCount">(0)</span></a>
                                <a id="emp_fillin">企财宝员工充值<span id="emp_fillinCount">(0)</span></a>
                            </div>
                        </div>
                    </div>
                    <table cellpadding="0" cellspacing="0" class="msg_table">
                        <tr class="first_tr">
                            <th>金额</th>
                            <th>类型</th>
                            <th>相关账户</th>
                            <th>时间</th>
                            <th>操作人</th>
                            <th>本次余额</th>
                            <th>操作</th>
                        </tr>

                        <tbody id="price_journal_Cnt">

                        </tbody>
                    </table>
                    <div id="pageTool_2" style="margin-top:10px;">

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/accountMessage.js"></script>
</body>
</html>
