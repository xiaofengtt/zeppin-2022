<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/investProductMessage.css">
<link rel="stylesheet" href="./css/uploadfile.css">
<script type="text/template" id="productNameTpl">
    <p>【{{:custodianCN}}】{{:name}}</p>
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
    <tr style="height:45px">
        <td><input type="checkbox" name="" value="" class="check"></td>
        <td>【{{:custodianName}}】{{:name}}({{:scode}})</td>
        <td>{{:targetAnnualizedReturnRate}}%</td>
        <td>{{:valueDate}}</td>
        <td><input type="number" name="" value="" style="margin:0;width:150px;" step="0.01" class="balance_num" disabled></td>
        <td>{{:accountBalanceCN}}</td>
        <input type="hidden" value="{{:uuid}}" class="uuid">
        <input type="hidden" value="【{{:custodianName}}】{{:name}}({{:scode}})" class="name">
        <input type="hidden" value="{{:targetAnnualizedReturnRate}}%" class="returnRate">
        <input type="hidden" value="{{:valueDate}}" class="valueDate">
        <input type="hidden" value="{{:accountBalance}}" class="banlanceCN">
    </tr>
</script>
<script type="text/template" id="balanceTpl">
    <tr style="height:45px">
        <td><input type="checkbox" name="" value="" class="check"></td>
        <td>平台可用余额</td>
        <td>-</td>
        <td>-</td>
        <td><input type="number" name="" value="" style="margin:0;width:150px;" step="0.01" class="balance_num" disabled></td>
        <td>{{:totalAmountCN}}</td>
        <input type="hidden" value="{{:uuid}}" class="uuid">
        <input type="hidden" value="平台可用余额" class="name">
        <input type="hidden" value="{{:totalAmount}}" class="banlanceCN">
    </tr>
</script>

<script type="text/template" id="tableConfirmTpl">
    <tr>
        <td>{{:name}}</td>
        <td>{{:returnRate}}</td>
        <td>{{:valueDate}}</td>
        <td>{{:value}}</td>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="">投资</a><span>></span><a class="current"></a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="product_name">

                    <div id="productNameCnt"></div>

                </div>

                <div class="select_account">
                    <select class="" name="" id="accountList">

                    </select>
                    <span style="font-size:14px;font-style:normal;">余额：<i id="balance" class="color_red" style="font-style:normal;"></i></span>
                </div>

                <div class="product_msg">

                    <div class="invest_msg text_box">
                        <input type="hidden" name="receipt" value="">
                        <p class="text_title">投资信息</p>

                        <table cellpadding="0" cellspacing="0" class="msg_table">
                            <tr class="first_tr">
                                <th width="5%">操作</th>
                                <th width="30%">募集产品</th>
                                <th width="8%">产品利率</th>
                                <th width="10%">起息日</th>
                                <th width="15%">投资金额</th>
                                <th width="10%">余额</th>
                            </tr>

                            <tbody id="tableCnt">

                            </tbody>
                        </table>

                        <div class="all_price_box">
                            <span>手续费</span>
                            <input type="number" step="0.01" name="" value="0.00" id="poundageValue">
                            <p class="all_price">总计：<i class="all_num" style="font-style:normal"></i></p>
                        </div>

                    </div>

                    <div class="invest_msg_ps">
                        <span>备注</span>
                        <input type="text" name="" value="" id="psValue">
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
                </div>


                <%--确认--%>
                <div class="invest_confirm_box">


                    <div class="invest_confirm text_box">
                        <p class="text_title">投资明细</p>
                        <table cellpadding="0" cellspacing="0" class="msg_table">
                            <tr class="first_tr">
                                <th width="30%">产品名称</th>
                                <th width="8%">产品利率</th>
                                <th width="10%">起息日</th>
                                <th width="10%">投资金额</th>
                            </tr>

                            <tbody id="tableConfirmCnt">

                            </tbody>
                        </table>

                        <div class="all_price_confirm">
                            <span>手续费：</span>
                            <i id="poundageConfirm">0.00</i>
                            <p class="all_price">总计：<i class="all_num" style="font-style:normal;font-size:16px;color:green"></i></p>
                        </div>
                    </div>
                    <div class="invest_confirm_ps text_box">
                        <span>备注：</span>
                        <i id="psConfirm"></i>
                    </div>

                    <%-- <div class="buy_password text_box">
                        <span class="confirm_item_title"><i>* </i>交易密码：</span>
                        <input type="password" name="" value="">
                    </div> --%>
                    <div class="fileBox" style="margin-top:30px;margin-left:10px">
                        <div class="clear">

                        </div>
                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" id="back">
                        <input type="button" name="" value="投资" id="invest">
                    </div>
                </div>
            </div>
        </div>
    </div>
	<form:form method="post"></form:form>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="js/uploadFile.js"></script>
    <script type="text/javascript" src="js/investProductMessage.js"></script>

</body>
</html>
