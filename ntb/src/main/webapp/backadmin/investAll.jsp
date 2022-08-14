<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/investBuyList.css">
<link rel="stylesheet" href="./css/bootstrap-switch.min.css">

<title>牛投帮-后台管理系统</title>
<%--投资中--%>
<script type="text/template" id="collectTpl">
    <tr>
        <td class="invest_date">

        </td>
        <td class="product_name">
            <p><a href="./productMessage.jsp?uuid={{:uuid}}" target="_blank">【{{:custodianName}}】{{:name}}</a></p>
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
                    {{:minInvestAmountCN}}元起购
                </li>

                <li>
                    计息日：{{:valueDate}} 至 {{:maturityDate}}
                </li>
            </ul>
        </td>
        <td>{{:investmentCN}}</td>
        <td>
            <a href="./investAllMsg.jsp?uuid={{:uuid}}" class="investAllMsg">查看</a>
            {{if stage != checked}}
            <a href="./investProductMessage.jsp?uuid={{:uuid}}">投资</a>
            {{/if}}
        </td>
    </tr>
</script>

<%--收益中--%>
<script type="text/template" id="incomeTpl">
    <tr>
        <td class="target_date">

        </td>
        <td class="product_name">
            <p><a href="./productMessage.jsp?uuid={{:uuid}}" target="_blank">【{{:custodianName}}】{{:name}}</a></p>
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
                    {{:minInvestAmountCN}}元起购
                </li>

                <li>
                    计息日：{{:valueDate}} 至 {{:maturityDate}}
                </li>
            </ul>
        </td>
        <td>{{:investmentCN}}</td>
        <td class="totalReturn color_orange"></td>
        <td>
            {{if isRedeem == true}}
                <span class="color_green">已赎回</span>
            {{else}}
                {{if totalRedeem == 0}}
                    <span class="color_orange">未赎回</span>
                {{else}}
                    <span class="color_red">部分赎回</span>
                {{/if}}
            {{/if}}
        </td>
        <td>
            <a href="./investAllMsg.jsp?uuid={{:uuid}}" class="investAllMsg">查看</a>
            {{if isRedeem == false}}
                <a href="./redemption.jsp?uuid={{:uuid}}">赎回</a>
            {{/if}}

        </td>
    </tr>
</script>


<%--已结束--%>
<script type="text/template" id="finishedTpl">
    <tr>
        <td>

            {{if isRedeem == true}}
                <span class="color_green">已赎回</span>
            {{else}}
                {{if totalRedeem == 0}}
                    <span class="color_orange">未赎回</span>
                {{else}}
                    <span class="color_red">部分赎回</span>
                {{/if}}
            {{/if}}

        </td>
        <td class="product_name">
            <p><a href="./productMessage.jsp?uuid={{:uuid}}" target="_blank">【{{:custodianName}}】{{:name}}</a></p>
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
                    {{:minInvestAmountCN}}元起购
                </li>

                <li>
                    计息日：{{:valueDate}} 至 {{:maturityDate}}
                </li>
            </ul>
        </td>
        <td>{{:investmentCN}}</td>
        <td>
            <span class="color_orange">{{:totalReturnCN}}</span>
        </td>
        <td>
            <span {{if totalReturn >= 0 }}class="color_orange"{{else}}class="color_orange"{{/if}}>{{:totalReturnCN}}</span>
        </td>

        <td>
            <a href="./investAllMsg.jsp?uuid={{:uuid}}" class="investAllMsg">查看</a>
            {{if isRedeem == false}}
                <a href="./redemption.jsp?uuid={{:uuid}}">赎回</a>
            {{else}}
                <a href="#" target="_blank">分析</a>
            {{/if}}
        </td>
    </tr>
</script>

<%-- 活期模板 --%>

<script type="text/template" id="freeTpl">
    <tr>
        <td>{{:gpName}}({{:name}} {{:scode}})</td>
        <td>{{:planingScale}}元</td>
        <td>{{:creditLevelCN}}</td>
        <td>{{:typeCN}}</td>
        <td>{{:flagStructuredCN}}</td>
        <td>
            <a href="./fundPublishRedem.jsp?uuid={{:uuid}}">赎回</a>
        </td>
    </tr>
</script>

</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600062" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location" style="position:relative;">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">投资一览</a></div>
                <div id="typeSelect">
                    <input type="checkbox" name="my-checkbox" value="" data-on-text="定期" data-on-color='primary' data-off-color='info' data-off-text="活期" checked>
                </div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <%-- 定期 --%>
                <div id="fix">
                    <ul class="select_invest_list">
                        <li class="item">
                            <a href="javascript:;" class="highLight" id="collect">
                                投资中
                                <span id="collectCount">(0)</span>
                            </a>
                        </li>
                        <li class="item">
                            <a href="javascript:;" id="income">
                                收益中
                                <span id="incomeCount">(0)</span>
                            </a>
                        </li>
                        <li class="item">
                            <a href="javascript:;" id="finished">
                                已结束
                                <span id="finishedCount">(0)</span>
                            </a>
                        </li>
                    </ul>

                    <%--筛选--%>
                    <div class="searchDiv">
                        <div class="input-group">
                            <input id="search" class="form-control" type="text" value="" placeholder="搜索">
                            <label class="input-group-addon" id="searchButton"><label>
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
                                <label style="margin-top:10px">发行银行：</label>
                                <div id="custodians" style="width:80%;vertical-align:top;">
                                    <a class="statusLight" id="all">全部</a>

                                </div>
                            </div>

                            <div class="statusDiv filter filter-isRedeem">
                                <label>产品状态：</label>
                                <div>
                                    <a class="statusLight" id="all">全部</a>
                                    <a href="javascript:;" id="0">未赎回</a>
                                    <a href="javascript:;" id="1">已赎回</a>
                                </div>
                            </div>


                        </div>
                        <div class="text-center">
                            <a id="filterController">展开</a>
                        </div>
                    </div>
                    <%--筛选结束--%>

                    <%-- 投资中 --%>
                    <table class="msg_table" cellspacing="3" cellpadding="0">
                        <tr class="first_tr">
                            <th width="10%">认购截止日</th>
                            <th width="40%">银行理财产品信息</th>
                            <th width="10%">已投资</th>
                            <th width="12%">操作</th>
                        </tr>
                        <tbody id="collectCnt">

                        </tbody>



                    </table>

                    <%--  -------------------------------%>
                    <%--  -------------------------------%>
                    <%--  -------------------------------%>


                    <%--  ---------收益中--------------------%>
                    <table class="msg_table" cellspacing="3" cellpadding="0">
                        <tr class="first_tr">
                            <th width="7%">到期日</th>
                            <th width="50%">银行理财产品信息</th>
                            <th width="10%">投资金额</th>
                            <th width="7%">预期收益</th>
                            <th width="7%">赎回状态</th>
                            <th width="7%">操作</th>
                        </tr>
                        <tbody id="incomeCnt">

                        </tbody>
                    </table>

                    <%--  -----------------------------%>
                    <%--  -----------------------------%>
                    <%--  -----------------------------%>
                    <%--  ---------已结束--------------------%>

                    <table class="msg_table" cellspacing="3" cellpadding="0">
                        <tr class="first_tr">
                            <th width="7%">状态</th>
                            <th width="45%">银行理财产品信息</th>
                            <th width="10%">投资金额</th>
                            <th width="8%">实际收益</th>
                            <th width="8%">利润</th>
                            <th width="6%">操作</th>
                        </tr>
                        <tbody id="finishedCnt">

                        </tbody>
                    </table>
                    <div id="pageTool">

                    </div>
                </div>

                <%--  --%>
                <%--  --%>
                <%--  --%>
                <%-- 活期 --%>
                <div id="free">
                    <table class="msg_table" cellspacing="0" cellpadding="0">
                        <tr class="first_tr">
                            <th width="30%">名称</th>
                            <th width="8%">募集规模</th>
                            <th width="10%">信用等级</th>
                            <th width="14%">活期理财类型</th>
                            <th width="15%">分级状态</th>
                            <th width="20%">操作</th>
                        </tr>
                        <tbody id="freeCnt">

                        </tbody>



                    </table>
                    <div id="pageTools" style="margin-top:10px;">

                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/bootstrap-switch.min.js"></script>
    <script type="text/javascript" src="./js/investAll.js"></script>
</body>
</html>
