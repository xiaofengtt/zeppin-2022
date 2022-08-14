<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/revenueCount.css">
<link rel="stylesheet" href="./css/msg_table.css">
<title>盈收统计</title>
<script type="text/template" id="queBoxTpl">
    <div class="product_item">

        <p class="product_title">【中国银行】中银智富理财计划2017年第319期(AMZYZF2017319-SH)</p>
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
                <td><a href="#">详细</a></td>
            </tr>
        </table>
    </div>

    <table class="msg_table">
        <tr class="first_tr">
            <th>项目</th>
            <th>值</th>
            <th>说明</th>
        </tr>

        <tbody>
            <tr>
                <td>产品总募集</td>
                <td>{{:realCollectCN}}</td>
                <td></td>
            </tr>
            <tr>
                <td>理财期限</td>
                <td>{{:term}}天</td>
                <td></td>
            </tr>
            <tr>
                <td>项目总投资</td>
                <td>{{:investmentCN}}</td>
                <td></td>
            </tr>
            <tr>
                <td>投资收益</td>
                <td>{{:totalReturnCN}}</td>
                <td></td>
            </tr>
            <tr>
                <td>投资实际收益率</td>
                <td class="realReturnRate">{{:(totalReturn / realCollect / term * 365)*100}}%</td>
                <td></td>
                <!--投资收益除以总募集/期限 -->
            </tr>
            <!-- <tr>
                <td>投资年化收益率</td>
                <td></td>
                <td></td>
            </tr> -->
            <tr>
                <td>预期年化收益率</td>
                <td>{{:targetAnnualizedReturnRate}}%</td>
                <td></td>
            </tr>
            <tr>
                <td>是否完成预期</td>
                <td>
                    {{if (totalReturn / realCollect / term * 365) * 100 >= targetAnnualizedReturnRate}}
                        是
                    {{else}}
                        否
                    {{/if}}

                </td>
                <td></td>
                <!-- 投资实际收益率 预期年化收益率比大小 -->
            </tr>
            <tr>
                <td>用户认购费用收入</td>
                <td>!!</td>
                <td></td>
            </tr>
            <tr>
                <td>用户认购费率</td>
                <td>!!</td>
                <td></td>
            </tr>
            <tr>
                <td>支付用户本金</td>
                <td>
                    {{if stage == "finished"}}
                        {{:realCollectCN}}
                    {{else}}
                        0.00
                    {{/if}}
                    </td>
                <td></td>
            </tr>
            <tr>
                <td>支付用户利息</td>
                <td>{{:realReturnCN}}</td>
                <td></td>
                <!-- 总募集*实际收益率*term -->
            </tr>
            <tr>
                <td>用户实际收益率(年化后)</td>
                <td>{{:realReturnRate}}%</td>
                <td></td>
            </tr>
            <tr>
                <td>银行认购手续费</td>
                <td>!!</td>
                <td></td>
            </tr>
            <tr>
                <td>项目毛利润</td>
                <td class="color_green">!!</td>
                <td></td>
            </tr>
            <tr>
                <td>产品状态</td>
                <td>{{:stageCN}}</td>
                <td></td>
            </tr>

        </tbody>
    </table>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./unbalanceProduct.jsp">产品结算还款</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="queBoxCnt">

                </div>

                <div class="btn_group">
                    <input type="button" name="" value="关闭" onclick="window.location.href = document.referrer;">
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="js/revenueCount.js"></script>
</body>
</html>
