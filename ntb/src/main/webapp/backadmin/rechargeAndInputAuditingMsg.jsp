<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/card.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/uploadfile.css">
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="queboxTpl">
    {{if newData.type == "expend"}}
        <div class="blank_card text_box">
            <p class="text_title">费用支出</p>
        </div>
        <span style="margin:0 10px;"><<<<<<</span>
    {{else newData.type == "recharge"}}
        <div class="blank_card text_box">
            <p class="text_title">企业充值</p>
        </div>
        <span style="margin:0 10px;">>>>>>></span>
    {{/if}}

    <div class="msg_card text_box">
        {{if newData.type == "expend"}}
            <p class="text_title">{{:newData.companyAccountOutInfo.accountName}}</p>
            <span class="company_name">{{:newData.companyAccountOutInfo.companyName}}</span>
            <span class="card_number">{{:newData.companyAccountOutInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i id="aBalance">{{:newData.companyAccountOutInfo.accountBalanceCN}}</i>
            </span>
        {{else newData.type == "recharge"}}
            <p class="text_title">{{:newData.companyAccountInInfo.accountName}}</p>
            <span class="company_name">{{:newData.companyAccountInInfo.companyName}}</span>
            <span class="card_number">{{:newData.companyAccountInInfo.accountNumStar}}</span>
            <span class="card_balance">
                余额：
                <i>{{:newData.companyAccountInInfo.accountBalanceCN}}</i>
            </span>
        {{/if}}
    </div>

</script>
<script type="text/template" id="msgTpl">

        <div class="confirm_item">
            {{if newData.type == "expend"}}
                <span class="confirm_item_title">支出金额：</span>
            {{else newData.type == "recharge"}}
                <span class="confirm_item_title">充值金额：</span>
            {{/if}}
            <span class="confirm_item_price" id="rechargeConfirm">{{:newData.totalAmountCN}}</span>
            <span class="confirm_item_cn" id="rechargeBigConfirm">人民币：</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">资金用途：</span>
            <span id="useConfirm">{{:newData.purpose}}</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">备注说明：</span>
            <span id="psConfirm">{{:newData.remark}}</span>
        </div>

        <div class="fileBox" style="margin-top:30px;margin-left:50px;">
            {{for listReceipt}}
                <a class='uploadSrc' href="../{{:url}}" target='_blank'><img src="../{{:url}}" class='uploadImg'></a>
            {{/for}}
            <div class="clear"></div>
        <div>

</script>

<script type="text/template" id="valueTpl">
    <p class="cn_price" id="rechargeBigValue">人民币：</p>
    <input type="hidden" name="receipt" value="">
    <div class="form_item">
        <span><i>* </i>充值金额</span>
        <input type="number" name="totalAmount" value="{{:newData.totalAmount}}" id="rechargeValue">
        <small>元</small>
    </div>

    <div class="form_item">
        <span><i>* </i>资金用途</span>
        <input type="text" name="purpose" value="{{:newData.purpose}}" id="useValue">

    </div>

    <div class="form_item">
        <span><i>* </i>备注说明</span>
        <textarea name="remark" id="psValue" value="{{:newData.remark}}">{{:newData.remark}}</textarea>
    </div>

    <div id="upload">
    </div>
    <a href="javascript:void(0)" id="delete" {{if listReceipt.length == 0}}style="display:none"{{/if}}>删除</a>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600066" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
            <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./auditingItem.jsp">待审批事项</a><span>></span><a href="./rechargeAndInputAuditing.jsp">企业账户充值、资金支出申请</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="queboxCnt">

                </div>


                <div class="recharge_msg text_box" style="display:none;">
                    <p class="text_title">信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/companyAccountTransfer/operateEdit" method="post" id="rechargeSubmit">
                        <input type="hidden" name="uuid" value="" id="companyAccountIn">
                        <input type="hidden" name="poundage" value="0.00">
                        <div id="valueCnt">

                        </div>

                        <div class="btn_group">
                            <input type="button" name="" value="取消" id="back">
                            <input type="button" name="" value="保存" id="allRight">
                        </div>
                    </form:form>

                </div>

                <div class="recharge_confirm text-box" style="display:block;">
                    <div class="confirm text_box">
                        <p class="text_title">明细</p>
                        <div id="msgCnt">

                        </div>

                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer">
                        <input type="button" name="" value="修改" id="allRightConfirm">
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

            </div>
        </div>
    </div>

    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="js/rechargeAndInputAuditingMsg.js"></script>


</body>
</html>
