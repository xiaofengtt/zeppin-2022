<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/card.css">
<link rel="stylesheet" href="./css/bank_form.css">
</head>
<script type="text/template" id="queBoxTpl">
    {{if type == "expend"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="blank_card text_box">
            <p class="text_title">费用支出</p>
        </div>

    {{else type == "transfer"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>

    {{else type == "recharge"}}

        <div class="blank_card text_box">
            <p class="text_title">企业充值</p>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>

    {{else type == "invest"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:bankFinancialProductInfo.name}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">

            </span>
        </div>

    {{else type == "redeem"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
        <div class="msg_card text_box">
            <p class="text_title">{{:bankFinancialProductInfo.name}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">

            </span>
        </div>

    {{else type == "return"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
        <div class="msg_card text_box">
            <p class="text_title">{{:bankFinancialProductInfo.name}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">

            </span>
        </div>

    {{else type == "takeout"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:investorInfo.realname}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">
                余额：
                <i>{{:investorInfo.accountBalance}}</i>
            </span>
        </div>

    {{else type == "fillin"}}

        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
        <div class="msg_card text_box">
            <p class="text_title">{{:investorInfo.realname}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">
                余额：
                <i>{{:investorInfo.accountBalance}}</i>
            </span>
        </div>
    {{else type == "emp_fillin"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbEmployeeInfo.realname}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">
                余额：
                <i>{{:qcbEmployeeInfo.accountBalance}}</i>
            </span>
        </div>
    {{else type == "emp_takeout"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbEmployeeInfo.realname}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">
                余额：
                <i>{{:qcbEmployeeInfo.accountBalance}}</i>
            </span>
        </div>

    {{else type == "qcb_takeout"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbCompanyAccountInfo.name}}</p>
            <span class="card_balance">
                余额：
                <i>{{:qcbCompanyAccountInfo.accountBalance}}</i>
            </span>
        </div>
    {{else type == "qcb_recharge"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbCompanyAccountInfo.name}}</p>
            <span class="card_balance">
                余额：
                <i>{{:qcbCompanyAccountInfo.accountBalance}}</i>
            </span>
        </div>
    {{else type == "qcb_payroll"}}
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbCompanyAccountInfo.name}}</p>
            <span class="card_balance">
                余额：
                <i>{{:qcbCompanyAccountInfo.accountBalance}}</i>
            </span>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
        <div class="msg_card text_box">
            <p class="text_title">{{:qcbEmployeeInfo.realname}}</p>
            <span class="company_name"></span>
            <span class="card_number"></span>
            <span class="card_balance">
                余额：
                <i>{{:qcbEmployeeInfo.accountBalance}}</i>
            </span>
        </div>
    {{/if}}

    <div class="recharge_confirm text-box">
        <div class="confirm text_box">
            <p class="text_title">
                {{:typeCN}}明细
            </p>
            <div class="confirm_item">
                <span class="confirm_item_title">金额：</span>
                <input type="hidden" value="{{:totalAmount}}" id="totalAmount">
                <span class="confirm_item_price" id="rechargeConfirm">{{:totalAmountCN}}</span>
                <span class="confirm_item_cn" id="rechargeBigConfirm">人民币：</span>
            </div>

            <div class="confirm_item">
                <span class="confirm_item_title">资金用途：</span>
                <span id="useConfirm">
                    {{if purpose != null}}
                        {{:purpose}}
                    {{else}}
                        无
                    {{/if}}
                </span>
            </div>

            <div class="confirm_item">
                <span class="confirm_item_title">手续费：</span>
                <span id="useConfirm">
                    {{if poundage != null}}
                        {{:poundageCN}}
                    {{else}}
                        无
                    {{/if}}
                </span>
            </div>

            <div class="confirm_item">
                <span class="confirm_item_title">备注说明：</span>
                <span id="psConfirm">
                    {{if remark != null}}
                        {{:remark}}
                    {{else}}
                        无
                    {{/if}}
                </span>
            </div>

        </div>
        <div class="btn_group">
            <input type="button" name="" value="返回" id="back" onclick="window.close()">
            <%-- <input type="button" name="" value="确认" id="allRightConfirm"> --%>
        </div>

        <%-- <div class="auditing text_box">
            <span class="confirm_item_title">填报信息：</span>
            <div class="auditing_msg">
                <p>2017-9-12 15:00:00 秦龙填报</p>
                <p>2017-9-12 15:00:00 荣景峰审批</p>
            </div>
        </div>
        <div class="buy_password text_box">
            <span class="confirm_item_title"><i>* </i>交易密码：</span>
            <input type="password" name="" value="">
        </div> --%>


    </div>
</script>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a onclick="history.go(-1)">账户信息</a><span>></span><a class="current">资金流水明细</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="queBoxCnt">

                </div>
            </div>
        </div>
    </div>


    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/historyMessage.js"></script>
</body>
</html>
