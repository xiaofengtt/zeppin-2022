<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品认购情况</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/rechargeDrawStatus.css">
<link rel="stylesheet" href="./css/msg_table.css">
<style>
    .select_page{
        position: relative;
    }
    .search_div1 {
        width: 200px;
        height: 40px;
        display: inline-block;
        vertical-align: top;
        position: absolute;
        left:0px;
        top:0px;
    }

    .search_div1 label {
        cursor: pointer;
        background: url(./img/search.png) center no-repeat;
        width: 40px;
    }
</style>
<script type="text/template" id="queBoxTpl">
    <tr>
        <td>
            <span class="day color_orange">{{:price}}</span>
        </td>
        <td class="product_name">
            <p><a href="./productMessagePublish.jsp?uuid={{:productInfo.uuid}}">&ensp;{{:productInfo.name}}</a></p>
            <ul>
                <li>
                    <span class="big">{{:productInfo.targetAnnualizedReturnRate}}</span><span>%</span>
                </li>

                <li>
                    {{:productInfo.term}}天
                </li>

                <li>
                    {{:productInfo.scode}}
                </li>

                <li>
                    {{:productInfo.typeCN}}
                </li>

                <li>
                    {{:productInfo.minInvestAmount}}元起购
                </li>

                <li>
                    计息日：{{:productInfo.valueDate}} 至 {{:productInfo.maturityDate}}
                </li>
            </ul>
        </td>
        <td>{{:createtimeCN}}</td>
        <td>{{:userName}}</td>

        <td><a href="./investorDetailMsg.jsp?uuid={{:user}}&billuuid={{:uuid}}&userType={{:userType}}" class="bill_message">查看</a></td>
    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600064" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">产品认购情况</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="button_search">
                    <a href="">+&ensp;添加筛选条件</a>
                    <div class="search_div">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="理财产品名称..." id="search">
                            <label class="input-group-addon" id="searchButton"></label>
                        </div>
                    </div>
                </div>

                <div class="nav_balance">

                    <div class="">
                        <span class="color_orange" id="totalAmount"></span>
                    </div>

                    <a class="filter">
                        <span>选择筛选日期...</span>
                        <ul>
                            <li><span data-value="1">昨天</span></li>
                            <li><span data-value="2">过去7天</span></li>
                            <li><span data-value="3">过去30天</span></li>
                            <li><span data-value="4">过去60天</span></li>
                            <li><span data-value="5">过去365天</span></li>
                            <li>自定义范围</li>
                            <li>
                                <input type="text" name="" value="" readonly id="start" style="font-size:12px;">
                                <input type="text" name="" value="" readonly id="end" style="font-size:12px;">
                            </li>
                        </ul>
                    </a>
                </div>
                <div class="highcharts_title">
                    <span>每日产品销量</span>
                </div>
                <div id="container"></div>

                <table class="msg_table" cellspacing="3" cellpadding="0">
                    <tr class="first_tr">
                        <th width="10%">认购金额</th>
                        <th width="40%">银行理财产品信息</th>
                        <th width="10%">认购时间</th>
                        <th width="10%">用户</th>
                        <th width="12%">操作</th>
                    </tr>

                    <tbody id="queBoxCnt"></tbody>
                </table>
                <div class="select_page">
                    <div class="search_div1">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="用户名称..." id="search1">
                            <label class="input-group-addon" id="searchButton1"></label>
                        </div>
                    </div>
                    <div id="pageTool">

                    </div>
                </div>
            </div>
        </div>
    </div>

	<script type="text/javascript" src="js/highcharts.js" ></script>
	<script src="./js/highcharts-zh_CN.js"></script>
	<script type="text/javascript" src="js/laydate/laydate.js"></script>
	<script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
	<script type="text/javascript" src="js/jsrender.min.js"></script>
	<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
	<script type="text/javascript" src="./js/productBuyStatus.js" ></script>
</body>
</html>
