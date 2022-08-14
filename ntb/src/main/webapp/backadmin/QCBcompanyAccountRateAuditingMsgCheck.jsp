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
<link rel="stylesheet" href="./css/QCBRecharge.css">
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="queboxTpl">


</script>
<script type="text/template" id="msgTpl">
        <div class="confirm_item">
            <span class="confirm_item_title">企业名称：</span>
            <span class="company-name">{{:newData.name}}</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">平台手续费率：</span>
            <span class="confirm_item_price" id="rechargeConfirm">{{:newData.feeTicketCN}}%</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">提交人：</span>
            <span>{{:creatorName}}</span>
        </div>
        <div class="confirm_item">
            <span class="confirm_item_title">提交时间：</span>
            <span>{{:submittimeCN}}</span>
        </div>

        {{if status=='unchecked'}}
            <div class="confirm_item">
                <span class="confirm_item_title">审核信息：</span>
                <span><input type="text" name="" value="" style="margin:0;" id="reason" autocomplete="off"></span>
            </div>
        {{else status=='checked'}}
            <div class="confirm_item">
                <span class="confirm_item_title">审核信息：</span>
                <span class="color_green">{{:reason}}</span>
            </div>
        {{else status == "unpassed"}}
            <div class="confirm_item">
                <span class="confirm_item_title">审核信息：</span>
                <span class="color_red">{{:reason}}</span>
            </div>
        {{/if}}
        <div class="btn_group">
            {{if status == "unchecked"}}
                <a href="javascript:;" class="auditing_check" data-uuid="{{:uuid}}" data-status="checked">通过</a>
                <a href="javascript:;" class="auditing_check" data-uuid="{{:uuid}}" data-status="unpassed">不通过</a>
            {{/if}}
            <a href="javascript:;" id="edit" onclick="history.go(-1);">取消</a>
        </div>


</script>

<script type="text/template" id="valueTpl">
    <div class="form_item">
        <span><i>* </i>充值金额</span>
        <input type="text" name="totalAmount" value="{{:newData.totalAmount}}" id="rechargeValue">
        <small>元</small>
        <span class="cn_price" id="rechargeBigValue">人民币：壹拾万元</span>
    </div>

    <div class="form_item">
        <span><i>* </i>资金用途</span>
        <input type="number" name="purpose" value="{{:newData.purpose}}" id="useValue">

    </div>

    <div class="form_item">
        <span><i>* </i>备注说明</span>
        <textarea name="remark" id="psValue" value="{{:newData.remark}}"></textarea>
    </div>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00800087" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
            <div class="locationLeft"><a href="">企财宝网站管理</a><span>></span><a href="./QCBcompanyAccountRateAuditingCheck.jsp">待审批事项</a><span>></span><a class="current">信息</a></div>
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
                        <p class="text_title">提现明细</p>
                        <div id="msgCnt">

                        </div>

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

    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/QCBcompanyAccountRateAuditingMsgCheck.js"></script>

</body>
</html>
