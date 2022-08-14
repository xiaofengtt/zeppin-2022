<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/investProductMessage.css">
<link rel="stylesheet" href="./css/uploadfile.css">

<title>牛投帮-后台管理系统</title>
<style>
    .redemption_msg{
        width:100%;
        padding:0 10px 15px 10px;
    }
    .redemption_confirm{
        padding:100%;
        padding:0 10px;
    }
    .text_title{
        margin-bottom:20px;
    }
</style>

<script type="text/template" id="productNameTpl">
    <p><a href="./productMessage.jsp?uuid={{:uuid}}">【{{:custodianCN}}】{{:name}}</a></p>
    <ul>
        <li>
            <span class="big">{{:targetAnnualizedReturnRate}}</span><span>%</span>
        </li>

        <li>
            {{:term}}天
        </li>

        <li>
            {{:scode}}
        </li>

        <li>
            {{:typeCN}}
        </li>

        <li>
            {{:minInvestAmount}}元起购
        </li>

        <li>
            计息日：{{:valueDateCN}} 至 {{:maturityDateCN}}
        </li>
    </ul>
    <input type="hidden" value="{{:uuid}}" id="bankFinancialProduct">
    <span class="end_time">{{:collectEndtimeCN}} 截止认购</span>
</script>

<script type="text/template" id="tableTpl">
    <!-- <tr style="height:45px">
        <td><input type="checkbox" name="" value="" class="check"></td>
        <td></td>
        <td></td>
        <td></td>
        <td><input type="number" name="" value="" style="margin:0;width:150px;" step="0.01" class="balance_num" disabled></td>
        <td>{{:accountBalanceCN}}</td>
        <input type="hidden" value="{{:uuid}}" class="uuid">
        <input type="hidden" value="【{{:custodianName}}】{{:name}}({{:scode}})" class="name">
        <input type="hidden" value="{{:targetAnnualizedReturnRate}}%" class="returnRate">
        <input type="hidden" value="{{:valueDate}}" class="valueDate">
        <input type="hidden" value="{{:accountBalance}}" class="banlanceCN">
    </tr> -->
    <tr style="height:45px">
        <td>{{:productPublishName}}</td>
        <td>
            {{if productPublishTargetReturn == "-"}}
                -
            {{else}}
                {{:productPublishTargetReturn}}%
            {{/if}}
        </td>
        <td>{{:productPublishMaturityDate}}</td>
        <td class="accountBalance">{{:accountBalanceCN}}</td>
        <td class="totalReturn">

        </td>
        <td><input type="number" name="" value="{{:totalRedeem}}" style="margin:0;width:150px;" step="0.01" class="rede_num" disabled></td>
        <input type="hidden" value="{{:accountBalance}}" class="investment">
        <input type="hidden" value="{{:productPublish}}" class="productuuid">
    </tr>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./auditingItem.jsp">待审批事项</a><span>></span><a href="./investAndRedemptionAuditing.jsp">企业投资、赎回申请</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="product_name">
                    <div id="productNameCnt"></div>
                </div>

                <div class="select_account">
                    <select class="" name="" id="accountList">

                    </select>
                    <span id="balance" class="color_red" style="font-size:15px;">余额：</span>
                </div>

                <div class="product_msg">

                    <div class="invest_msg text_box">
                        <p class="text_title">资金来源</p>

                        <table cellpadding="0" cellspacing="0" class="msg_table">
                            <tr class="first_tr">
                                <th width="30%">产品名称</th>
                                <th width="8%">产品利率</th>
                                <th width="10%">到期日</th>
                                <th width="15%">可赎回金额</th>
                                <th width="10%">预计收益</th>
                                <th width="15%">赎回金额</th>
                            </tr>

                            <tbody id="tableCnt">




                            </tbody>
                        </table>

                        <div class="price_msg">
                            <ul>
                                <%-- <li>手续费：<input type="number" style="margin:0;" value="0.00" step="0.01"></li> --%>
                                <li id="investAll">总投资：</li>
                                <li>预计收益：<span id="returnAll"></span></li>
                            </ul>
                        </div>

                    </div>

                    <%-- <div class="invest_confirm_ps text_box">
                        <span>备注：</span>
                        <i>无</i>
                    </div> --%>
                </div>


                <div class="redemption_msg">
                    <div class="add_account_form text_box">
                        <input type="hidden" name="receipt" value="">
                        <p class="text_title">赎回信息</p>
                        <div class="form_item">
                            <span><i>* </i>赎回时间：</span>
                            <input type="text" name="" value="" id="dateValue" readonly style="margin:0;">
                        </div>

                        <div class="form_item">
                            <span><i>* </i>赎回金额：</span>
                            <span type="text" id="redeValue" style="text-align:left"></span>
                        </div>

                        <div class="form_item">
                            <span><i>* </i>实际收益：</span>
                            <input type="number" name="" value="" id="realReturn" step="0.01" style="margin:0;">
                        </div>

                        <div class="form_item">
                            <span><i>* </i>收益利率(年化)：</span>
                            <span id="targetReturn" style="text-align:left"></span>
                        </div>

                        <div class="form_item">
                            <span>备注：</span>
                            <input type="text" name="" value="" id="psValue" style="margin:0;">
                        </div>
                        <div id="upload">
                            <div class="clear">

                            </div>
                        </div>
                          <a href="javascript:void(0)" id="delete">删除</a>
                    </div>

                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.reload();">
                        <input type="button" name="" value="确认" id="allRight">
                    </div>
                </div>


                <%--  --%>

                <div class="redemption_confirm">
                    <div class="text_box" style="width:100%">
                        <p class="text_title">赎回明细</p>
                        <div class="confirm_item">
                            <span class="confirm_item_title">赎回时间：</span>
                            <span id="dateConfirm"></span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">赎回金额：</span>
                            <span id="redeConfirm"></span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">实际收益：</span>
                            <span id="realReturnConfirm"></span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">收益利率(年化)：</span>
                            <span id="targetReturnConfirm"></span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">备注：</span>
                            <span id="psConfirm"></span>
                        </div>
                        <div class="fileBox" style="margin-top:30px;margin-left:50px">
                            <div class="clear">

                            </div>
                        </div>
                    </div>

                    <%-- <div class="buy_password text_box">
                        <span class="confirm_item_title"><i>* </i>交易密码：</span>
                        <input type="password" name="" value="">
                    </div> --%>

                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="history.go(-1)">
                        <input type="button" name="" value="修改" id="allRightConfirm">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form:form class="" method="post"></form:form>
    <script type="text/javascript" src="./js/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="./js/redeemptionAuditingMsg.js"></script>
</body>
</html>
