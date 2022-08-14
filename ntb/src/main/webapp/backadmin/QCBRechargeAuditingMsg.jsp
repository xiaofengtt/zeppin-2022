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
<link rel="stylesheet" href="./css/QCBRecharge.css">
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="queboxTpl">


</script>
<script type="text/template" id="msgTpl">

        <div class="confirm_item">
            <span class="confirm_item_title">企业名称：</span>
            <span class="company-name">{{:qcbVirtualAccountInfo.qcbCompanyName}}</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">充值金额：</span>
            <span class="confirm_item_price" id="rechargeConfirm">{{:newData.price}}</span>
            <span class="confirm_item_cn" id="rechargeBigConfirm">人民币：</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">充值时间：</span>
            <span id="rechargeTimeConfirm">{{:newData.createtimeCN}}</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">备注说明：</span>
            <span id="psConfirm">{{:newData.remark}}</span>
        </div>

        <div class="confirm_item">
            <span class="confirm_item_title">专属充值账号：</span>
            <div class="recharge-account">
                <div class="recharge-account-item">
                    <span class="key">账户名称</span>
                    <span class="value">{{:qcbVirtualAccountInfo.companyAccountName}}</span>
                </div>
                <div class="recharge-account-item">
                    <span class="key">银行账号</span>
                    <span class="value">{{:qcbVirtualAccountInfo.companyAccountNum}} <em style="color:orange;font-size:14px;font-style:normal">{{:qcbVirtualAccountInfo.accountNum}}</em></span>
                </div>
                <div class="recharge-account-item">
                    <span class="key">开户银行</span>
                    <span class="value">
                        {{if qcbVirtualAccountInfo.branchBankName != null}}
                            {{:qcbVirtualAccountInfo.branchBankName}}
                        {{else}}
                            无
                        {{/if}}
                    </span>
                </div>
                <div class="recharge-account-item">
                    <span class="key">开户行地址</span>
                    <span class="value">
                        {{if qcbVirtualAccountInfo.branchBankAddress != null}}
                            {{:qcbVirtualAccountInfo.branchBankAddress}}
                        {{else}}
                            无
                        {{/if}}
                    </span>
                </div>
            </div>
        </div>
        <div class="fileBox" style="margin-top:30px;margin-left:50px;">
            {{for listReceipt}}
                <a class='uploadSrc' href="../{{:url}}" target='_blank'><img src="../{{:url}}" class='uploadImg'></a>
            {{/for}}
            <div class="clear"></div>
        <div>

</script>

<script type="text/template" id="valueTpl">
    <input type="hidden" name="receipt" value="">
    <input type="hidden" name="uuid" value="{{:uuid}}" id="companyAccountIn">
    <div class="form_item">
        <span><i>* </i>企业名称</span>
        <span class="company-name">{{:qcbVirtualAccountInfo.qcbCompanyName}}</span>
    </div>
    <div class="form_item">
        <span><i>* </i>充值金额</span>
        <input type="number" name="totalAmount" value="{{:newData.price}}" step="0.01" id="rechargeValue">
        <small>元</small>
        <span class="cn_price" id="rechargeBigValue">人民币：</span>
    </div>

    <div class="form_item">
        <span><i>* </i>充值时间</span>
        <input type="text" name="paytime" value="{{:newData.createtimeCNStr}}" id="rechargeTimeValue" readonly>

    </div>

    <div class="form_item">
        <span><i>* </i>备注说明</span>
        <textarea name="remark" id="psValue" value="{{:newData.remark}}">{{:newData.remark}}</textarea>
    </div>
    <div class="form_item">
        <span><i>* </i>专属充值账号</span>
        <div class="recharge-account">
            <div class="recharge-account-item">
                <span class="key">账户名称</span>
                <span class="value">{{:qcbVirtualAccountInfo.companyAccountName}}</span>
            </div>
            <div class="recharge-account-item">
                <span class="key">银行账号</span>
                <span class="value">{{:qcbVirtualAccountInfo.companyAccountNum}} <em style="color:orange;font-size:14px;font-style:normal">{{:qcbVirtualAccountInfo.accountNum}}</em></span>
            </div>
            <div class="recharge-account-item">
                <span class="key">开户银行</span>
                <span class="value">{{:qcbVirtualAccountInfo.branchBankName}}</span>
            </div>
            <div class="recharge-account-item">
                <span class="key">开户行地址</span>
                <span class="value">{{:qcbVirtualAccountInfo.branchBankAddress}}</span>
            </div>
        </div>
    </div>

    <div id="upload">
    </div>
    <a href="javascript:void(0)" id="delete" {{if listReceipt.length == 0}}style="display:none"{{/if}}>删除</a>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00800084" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
            <div class="locationLeft"><a href="">企财宝网站管理</a><span>></span><a href="./QCBRechargeAuditing.jsp">申请事项</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <%-- <div id="queboxCnt">

                </div> --%>


                <div class="recharge_msg text_box" style="display:none;">
                    <p class="text_title">充值信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/qcbCompanyTransfer/operateEdit" method="post" id="rechargeSubmit">
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
                        <p class="text_title">充值明细</p>
                        <div id="msgCnt">

                        </div>

                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="history.go(-1);">
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

    <script type="text/javascript" src="./js/url.min.js"></script>
    <script src="./js/laydate-v1.1/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="./js/QCBRechargeAuditingMsg.js"></script>


</body>
</html>
