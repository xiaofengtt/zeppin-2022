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
    <div class="msg_card text_box">
        <p class="text_title">{{:newData.companyAccountOutInfo.accountName}}</p>
        <span class="company_name">{{:newData.companyAccountOutInfo.companyName}}</span>
        <span class="card_number">{{:newData.companyAccountOutInfo.accountNum}}</span>
        <span class="card_balance">
            余额：
            <i id="aBalance">{{:newData.companyAccountOutInfo.accountBalanceCN}}</i>
        </span>
    </div>

    <span style="margin:0 10px;">>>>>>></span>

    <div class="msg_card text_box">
        <p class="text_title">{{:newData.companyAccountInInfo.accountName}}</p>
        <span class="company_name">{{:newData.companyAccountInInfo.companyName}}</span>
        <span class="card_number">{{:newData.companyAccountInInfo.accountNum}}</span>
        {{if type == "withdraw"}}
            <span class="card_bind">绑定</span>
        {{/if}}
        <span class="card_balance">
            余额：
            <i>{{:newData.companyAccountInInfo.accountBalanceCN}}</i>
        </span>
    </div>
</script>



<script type="text/template" id="msgTpl">
    <input type="hidden" name="companyAccountOut" id="companyAccountOut" value="{{:newData.companyAccountOutInfo.uuid}}">
    <input type="hidden" name="companyAccountIn" id="companyAccountIn" value="{{:newData.companyAccountInInfo.uuid}}">
    <input type="hidden" name="uuid" id="companyAccountIn" value="{{:uuid}}">
    <input type="hidden" name="receipt" value="">
    <p class="cn_price" id="transferBigValue">人民币：</p>
    <div class="form_item">
        <span><i>* </i>转账金额</span>
        <input type="number" name="totalAmount" value="{{:newData.totalAmount}}" id="transferValue">
        <small>元</small>
    </div>

    <div class="form_item">
        <span><i>* </i>手续费</span>
        <input type="number" name="poundage" value="{{:newData.poundageCN}}" id="poundage">
        <small>元</small>
        <span class="form_balance"></span>
    </div>

    <div class="form_item">
        <span><i>* </i>资金用途</span>
        <input type="text" name="purpose" value="{{:newData.purpose}}" id="useValue">

    </div>

    <div class="form_item">
        <span><i>* </i>备注说明</span>
        <textarea name="remark" id="psValue">{{:newData.remark}}</textarea>
    </div>

      <div id="upload">
      </div>
      <a href="javascript:void(0)" id="delete" {{if listReceipt.length == 0}}style="display:none"{{/if}}>删除</a>
</script>

<script type="text/template" id="valueTpl">
    <div class="confirm_item">
        <span class="confirm_item_title">转账金额：</span>
        <span class="confirm_item_price" id="transferConfirm">{{:newData.totalAmountCN}}</span>
        <span class="confirm_item_cn" id="transferBigConfirm">人民币：</span>
    </div>


    <div class="confirm_item">
        <span class="confirm_item_title">手续费：</span>
        <span id="poundageConfirm">{{:newData.poundageCN}}</span>
    </div>

    <div class="confirm_item">
        <span class="confirm_item_title">资金用途：</span>
        <span  id="useConfirm">{{:newData.purpose}}</span>
    </div>

    <div class="confirm_item">
        <span class="confirm_item_title">备注说明：</span>
        <span id="psConfirm">{{:newData.remark}}</span>
        <%-- <span>123123123</span>
        <span>&ensp;&ensp;交易时间：</span>
        <span>2017.12.12</span> --%>
    </div>
    <div class="fileBox" style="margin-top:30px;margin-left:50px;">
        {{for listReceipt}}
            <a class='uploadSrc' href="../{{:url}}" target='_blank'><img src="../{{:url}}" class='uploadImg'></a>
        {{/for}}
        <div class="clear"></div>
    <div>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a href="./transferAndDrawCashAuditing.jsp">企业账户间转账申请</a><span>></span><a class="current">转账变动审核信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div id="queboxCnt"></div>


                <div class="recharge_msg text_box">
                    <p class="text_title">转账信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/companyAccountTransfer/operateEdit" method="post" id="submit">
                        <div id="msgCnt"></div>
                        <div class="btn_group">
                            <input type="button" name="" value="取消" id="back">
                            <input type="button" name="" value="保存" id="allRight">
                        </div>
                    </form:form>

                </div>

                <div class="recharge_confirm text-box">
                    <div class="confirm text_box">
                        <p class="text_title">转账明细</p>
                        <div id="valueCnt"></div>

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

                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer">
                        <input type="button" name="" value="修改" id="allRightConfirm">
                    </div>
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
    <script type="text/javascript" src="js/transferAndDrawCashAuditingMsg.js"></script>
</body>
</html>
