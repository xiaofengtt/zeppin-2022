<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/bootstrap-switch.min.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/repayment.css">
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="productTpl">
    <p class="product_title"><a href="./productMessagePublish.jsp?uuid={{:uuid}}">【{{:custodianCN}}】{{:name}}</a></p>
    <table class="product_content">
        <tr>
            <td class="color_gray">到期日</td>
            <td class="color_gray">期限</td>
            <td class="color_gray">目标年化利率</td>
            <td class="color_gray">币种</td>
            <td class="color_gray">资金保障</td>
            <td class="color_gray">最小认购</td>
            <td class="color_gray">实际募集</td>
            <td class="color_gray">操作</td>
        </tr>
        <tr>
            <td>{{:maturityDateCN}}</td>
            <td>{{:term}}<samll>天</small></td>
            <td class="color_red">{{:targetAnnualizedReturnRate}}<small>%</small></td>
            <td>{{:currencyTypeCN}}</td>
            <td>{{:guaranteeStatusCN}}</td>
            <td>{{:minInvestAmount}}</td>
            <td class="color_orange">{{:realCollectCN}}</td>
            <td><a href="./collectMessage.jsp?uuid={{:uuid}}" class="collectMessage">详细</a></td>
        </tr>
    </table>
</script>

<script type="text/template" id="investTpl">
    <tr style="height:45px">
        <td>{{:productName}}</td>
        <td>{{:productTargetReturn}}%</td>
        <td>{{:productMaturityDate}}</td>
        <td class="invest">{{:totalAmountCN}}</td>
        <td class="totalReturn">
            {{if totalReturn != 0}}
                {{:totalReturnCN}}
            {{else}}
                未赎回
            {{/if}}
        </td>
        <input type="hidden" value="{{:totalAmount}}" class="investment">
    </tr>
</script>

<script type="text/template" id="repayTpl">
    <tr>
        <td>{{:userName}}</td>
        <td>{{:totalAmountCN}}</td>
        <td>{{:totalAmountCN}}</td>
        <td class="return"></td>
        <td class="all_price"></td>
        <input type="hidden" value="{{:totalAmount}}" class="totalAmount">
        <!-- <td><a href="#">查看</a></td> -->
    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600067" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./repaymentAuditingCheck.jsp">产品结算还款待审核事项</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="product_item" id="productCnt">

                </div>

                <div class="product_msg">

                    <div class="invest_msg text_box">
                        <p class="text_title">还款信息</p>

                        <table cellpadding="0" cellspacing="0" class="msg_table">
                            <tr class="first_tr">
                                <th width="30%">产品名称</th>
                                <th width="8%">产品利率</th>
                                <th width="10%">到期日</th>
                                <th width="15%">投资金额</th>
                                <th width="10%">实际收益</th>
                            </tr>

                            <tbody id="investCnt"></tbody>
                        </table>

                        <div class="price_msg">
                            <ul>
                                <%-- <li>手续费：0.00</li> --%>
                                <li id="investAll">总投资：</li>
                                <li>实际收益：<span id="totalReturnAll"></span></li>
                                <!-- <li>收益率(年化)：<span class="color_orange" id="returnRateAll"></span></li> -->
                            </ul>
                        </div>

                    </div>

                    <%-- <div class="invest_confirm_ps text_box">
                        <span>备注：</span>
                        <i>无</i>
                    </div> --%>
                </div>

                <div class="repayment_set text_box">
                    <p class="text_title">结算还款设置</p>
                    <div class="confirm_item">
                        <span class="confirm_item_title">是否返回预期收益：</span>
                        <input type="checkbox" name="my-checkbox" value="" data-on-text="是" data-off-text="否" data-size="small">
                        <span id="back_boolean" class="color_green"></span>
                        <small class="color_red" id="targetReturn">预期收益率：%</small>
                    </div>
                    <div class="confirm_item">
                        <span class="confirm_item_title">用户实际收益率：</span>
                        <input type="number" name="" value="" step="0.01" style="margin-bottom:3px;" id="useValue">
                        <span class="color_orange" id="useConfirm"></span>
                    </div>
                    <div class="confirm_item">
                        <span class="confirm_item_title">返本付息总额：</span>
                        <span class="color_orange" id="allPrice">0</span>
                        <small>其中：募集额：<small id="collectValue"></small>&ensp;&ensp;利息额：<small id="returnValue"></small></small>
                    </div>

                    <div class="confirm_item">
                        <span class="confirm_item_title">提交人：</span>
                        <span id="createName"></span>
                    </div>
                    <div class="confirm_item">
                        <span class="confirm_item_title">提交时间：</span>
                        <span id="createTime"></span>
                    </div>
                    <div class="confirm_item" id="reasonValue">
                        <span class="confirm_item_title">审核信息：</span>
                        <span><input type="text" name="" value="" style="margin:0;" id="reason" autocomplete="off"></span>
                    </div>
                    <div class="confirm_item" id="reasonConfirm">
                        <span class="confirm_item_title">审核信息：</span>
                        <span class="color_green" id="reasonMsg"></span>
                    </div>
                </div>

                <div class="btn_group">
                    <input type="button" name="" value="取消" onclick="window.location.reload()">
                    <input type="button" name="" value="结算还款" id="allRight">
                </div>



                <%--确认--%>
                <div class="repayment_confirm">
                    <div class="repayment_msg text_box">
                        <p class="text_title">还款明细</p>
                        <table class="msg_table">
                            <tr class="first_tr">
                                <th>用户</th>
                                <th>认购总额</th>
                                <th>返还本金</th>
                                <th>返还盈利</th>
                                <th>返还总计</th>
                                <%-- <th>操作</th> --%>
                            </tr>

                            <tbody id="repayCnt">


                            </tbody>
                        </table>
                    </div>
                    <div class="btn_group">
                        <a href="javascript:;" class="auditing_check" data-uuid="" data-status="">通过</a>
                        <a href="javascript:;" class="auditing_check" data-uuid="" data-status="">不通过</a>
                        <a href="javascript:;" id="edit" onclick="history.go(-1)">取消</a>
                        <%-- <input type="button" name="" value="修改" id="allRightConfirm"> --%>
                    </div>



                </div>
            </div>
        </div>
    </div>


    <script src="./js/bootstrap-switch.min.js"></script>
    <script src="./js/changePrice.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/repaymentAuditingMsgCheck.js"></script>


</body>
</html>
