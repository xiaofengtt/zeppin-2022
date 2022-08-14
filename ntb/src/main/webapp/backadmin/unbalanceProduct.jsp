<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/unbalanceProduct.css">
<script type="text/template" id="unbalanceTpl">
    <div class="product_item">
        <table class="product_head" cellspacing="0" cellpadding="0">
            <tr class="tr_bg_blue">
                <td width="5%">
                    {{if stage == "balance"}}
                        <span class="product_tip bg_black">{{:stageCN}}</span>
                    {{else stage == "profit"}}
                        <span class="product_tip bg_blue">{{:stageCN}}</span>
                    {{else stage == "finished"}}
                        <span class="product_tip bg_green">{{:stageCN}}</span>
                    {{/if}}
                </td>
                <td width="80%" class="product_name color_blue"><a href="./productMessage.jsp?uuid={{:bankFinancialProduct}}">【{{:custodianName}}】{{:name}}</a></td>
                <td width="20%">
                    <a href="#" class="color_green margin_r">盈收预测</a>
                    {{if stage == "balance"}}
                        <a href="./repayment.jsp?uuid={{:uuid}}" class="color_green">结算还款</a>
                    {{/if}}
                </td>
            </tr>
        </table>

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
                <td>{{:maturityDate}}</td>
                <td>{{:term}}<samll>天</small></td>
                <td class="color_red">{{:targetAnnualizedReturnRate}}<small>%</small></td>
                <td>{{:currencyTypeCN}}</td>
                <td>{{:guaranteeStatusCN}}</td>
                <td>{{:minInvestAmountNum}}{{:minInvestAmountCN}}</td>
                <td class="color_orange">{{:realCollectCN}}</td>
                <td><a href="./collectMessage.jsp?uuid={{:uuid}}" class="collectMessage">详细</a></td>
            </tr>
        </table>
    </div>
</script>


<script type="text/template" id="balanceTpl">
    <div class="product_item bg_gary">
        <table class="product_head bg_gray" cellspacing="0" cellpadding="0">
            <tr>
                <td width="5%">
                    <span class="product_tip tip_bg_gray">{{:stageCN}}</span>
                </td>
                <td width="80%" class="product_name name_color_gray"><a href="./productMessage.jsp?uuid={{:bankFinancialProduct}}">【{{:custodianName}}】{{:name}}</a></td>
                <td width="20%">
                    <a href="./revenueCount.jsp?uuid={{:uuid}}" class="margin_r">盈收统计</a>
                    <a href="./productMessage.jsp?uuid={{:bankFinancialProduct}}">查看</a>
                </td>
            </tr>
        </table>

        <table class="product_content bg_gray">
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
                <td>{{:maturityDate}}</td>
                <td>{{:term}}<samll>天</small></td>
                <td>{{:targetAnnualizedReturnRate}}<small>%</small></td>
                <td>{{:currencyTypeCN}}</td>
                <td>{{:guaranteeStatusCN}}</td>
                <td>{{:minInvestAmountNum}}{{:minInvestAmountCN}}</td>
                <td>{{:realCollectCN}}</td>
                <td><a href="./collectMessage.jsp?uuid={{:uuid}}" class="collectMessage">详细</a></td>
            </tr>
        </table>
    </div>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600063" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">产品结算还款</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <ul class="select_page">
                    <li class="item">
                        <a id="balance" class="highLight">
                            未结算
                            <span id="balanceCount">(0)</span>
                        </a>
                    </li>
                    <li class="item">
                        <a id="finished">
                            已结算
                            <span id="finishedCount">(0)</span>
                        </a>
                    </li>
                </ul>

                <div class="searchDiv">
                    <div class="input-group">
                        <input id="search" class="form-control" type="text" value="" placeholder="搜索">
                        <label class="input-group-addon" onclick="searchBtn()"></label>
                    </div>
                </div>
                <div class="condition">
                    <div class="statusDiv filter filter-income">
                        <label>预期收益：</label>
                        <div>
                            <a class="statusLight" id="all">全部</a>
                            <a id="1">3%以下</a>
                            <a id="2">3%-4%</a>
                            <a id="3">4%-5%</a>
                            <a id="4">5%-6%</a>
                            <a id="5">6%以上</a>
                        </div>
                    </div>
                    <div class="hide" id="moreFilter">

                        <div class="statusDiv filter filter-term">
                            <label>产品期限：</label>
                            <div>
                                <a class="statusLight" id="all">全部</a>
                                <a id="1">60天以下</a>
                                <a id="2">61-120天</a>
                                <a id="3">121-180天</a>
                                <a id="4">181-365天</a>
                                <a id="5">365天以上</a>
                            </div>
                        </div>

                        <div class="statusDiv filter filter-custodian">
                            <label style="margin-top:10px">管理银行：</label>
                            <div id="custodians" style="width:80%;vertical-align:top;">
                                <a class="statusLight" id="all">全部</a>

                            </div>
                        </div>

                    </div>
                    <div class="text-center">
                        <a id="filterController">展开</a>
                    </div>
                </div>

                <div class="sort">
                    <div class="order-option">排序:</div>
                    <ul class="sorting-btns">
                        <li class="light desc" id="sorting-default"><a data-name="createtime"><span>默认</span></a></li>
                        <li id="sorting-collectStarttime"><a data-name="term"><span>产品期限</span></a></li>
                        <li id="sorting-targetAnnualizedReturnRate" class=""><a data-name="target_annualized_return_rate"><span>收益率</span></a></li>
                        <li id="sorting-maturityDate" class=""><a data-name="maturity_date"><span>产品到期日</span></a></li>
                        <li id="sorting-term" class=""><a data-name="min_invest_amount"><span>最小认购</span></a></li>
                        <li id="sorting-term" class=""><a data-name="status"><span>状态</span></a></li>
                    </ul>
                </div>

                <div class="unbalance" id="unbanlanceCnt">

                </div>

                <div class="balance" id="balanceCnt">

                </div>


                <div id="pageTool">

                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/unbalance.js"></script>
</body>
</html>
