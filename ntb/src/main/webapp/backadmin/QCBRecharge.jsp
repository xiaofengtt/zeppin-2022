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
    <div class="recharge-account-item">
        <span class="key">账户名称</span>
        <span class="value">{{:companyAccountName}}</span>
    </div>
    <div class="recharge-account-item">
        <span class="key">银行账号</span>
        <span class="value">{{:companyAccountNum}} <em style="color:orange;font-size:14px;font-style:normal">{{:accountNum}}</em></span>
    </div>
    <div class="recharge-account-item">
        <span class="key">开户银行</span>
        <span class="value">
            {{if branchBankName != null}}
                {{:branchBankName}}
            {{else}}
                无
            {{/if}}
        </span>
    </div>
    <div class="recharge-account-item">
        <span class="key">开户行地址</span>
        <span class="value">
            {{if branchBankAddress != null}}
                {{:branchBankAddress}}
            {{else}}
                无
            {{/if}}
        </span>
    </div>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00800081" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">企业用户管理</a><span>></span><a href="javascript:void(0);" click="window.locatoin.href=document.referrer">企业用户详情</a><span>></span><a class="current">企业充值</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="recharge_msg text_box">
                    <p class="text_title">充值信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/qcbCompanyTransfer/recharge" method="post" id="rechargeSubmit">
                        <input type="hidden" name="qcbCompany" value="" id="qcbCompany">
                        <input type="hidden" name="receipt" value="">
                        <div class="form_item">
                            <span><i>* </i>企业名称</span>
                            <span class="company-name"></span>
                        </div>
                        <div class="form_item">
                            <span><i>* </i>充值金额</span>
                            <input type="number" name="totalAmount" value="" step="0.01" id="rechargeValue">
                            <small>元</small>
                            <span class="cn_price" id="rechargeBigValue">人民币：</span>
                        </div>

                        <div class="form_item">
                            <span><i>* </i>充值时间</span>
                            <input type="text" name="paytime" value="" id="rechargeTimeValue" readonly>
                        </div>

                        <div class="form_item">
                            <span><i>* </i>备注说明</span>
                            <textarea name="remark" id="psValue"></textarea>
                        </div>
                        <div class="form_item">
                            <span><i>* </i>专属充值账号</span>
                            <div class="recharge-account">
                                <div id="queboxCnt">

                                </div>
                            </div>
                        </div>

                        <div id="upload">
                            <div class="clear">

                            </div>
                        </div>
                        <a href="javascript:void(0)" style="display:none" id="delete">删除</a>
                        <div class="btn_group">
                            <input type="button" name="" value="取消" onclick="history.go(-1)">
                            <input type="button" name="" value="确认" id="allRight">
                        </div>
                    </form:form>

                </div>

                <div class="recharge_confirm text-box">
                    <div class="confirm text_box">
                        <p class="text_title">充值明细</p>
                        <div class="confirm_item">
                            <span class="confirm_item_title">企业名称：</span>
                            <span class="company-name">北京天窗科技有限公司</span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">充值金额：</span>
                            <span class="confirm_item_price" id="rechargeConfirm"></span>
                            <span class="confirm_item_cn" id="rechargeBigConfirm">人民币：</span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">充值时间：</span>
                            <span id="rechargeTimeConfirm"></span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">备注说明：</span>
                            <span id="psConfirm"></span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">专属充值账号：</span>
                            <div class="recharge-account">
                                <div id="queboxCnt_">

                                </div>
                            </div>
                        </div>

                        <div class="fileBox" style="margin-top:30px;margin-left:50px">
                            <div class="clear">

                            </div>
                        </div>
                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" id="back">
                        <input type="button" name="" value="确认" id="allRightConfirm">
                    </div>
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
    <script type="text/javascript" src="./js/uploadFile.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/QCBRecharge.js"></script>


</body>
</html>
