<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/rechargeDrawStatus.css">
<style>
    .select_page{
        position: relative;
    }
    .search_div {
        width: 200px;
        height: 40px;
        display: inline-block;
        vertical-align: top;
        position: absolute;
        left:0px;
        top:0px;
    }

    .search_div label {
        cursor: pointer;
        background: url(./img/search.png) center no-repeat;
        width: 40px;
    }
</style>
<script type="text/template" id="queBoxTpl">
        <tr>
            {{if priceflag == true}}
                <td class="color_orange">{{:price}}</td>
            {{else priceflag == false}}
                <td class="color_green">-{{:price}}</td>
            {{/if}}
            <td>{{:typeCN}}</td>
            <td>{{:orderTypeCN}}</td>
            <td>{{:application}}</td>
            <td>{{:statusCN}}</td>
            <td>{{:createtimeCN}}</td>
            <td><a href="javascript:void(0);">{{:userName}}</a></td>
            <td class="color_green"><a href="./investorDetailMsg.jsp?uuid={{:user}}&billuuid={{:uuid}}&userType={{:userType}}" class="bill_message">查看</a></td>
        </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600065" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">用户充值提现查询</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="button_search">
                    <a href="">+&ensp;添加筛选条件</a>

                </div>

                <div class="nav_balance">


                    <div class="">
                        充值金额：
                        <span id="recharge" class="color_orange"></span>
                    </div>
                    <!-- <div class="">
                        提现金额：
                        <span id="withDraw"></span>
                    </div> -->
                    <div class="">
                        当前余额：
                        <span id="balance" class="color_orange"></span>
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
                    <span></span>
                </div>
                <div id="container"></div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th>交易金额</th>
                        <th>类型</th>
                        <th>第三方通道</th>
                        <th>应用</th>
                        <th>状态</th>
                        <th>交易时间</th>
                        <th>用户</th>
                        <th>操作</th>
                    </tr>

                    <tbody id="queBoxCnt">


                    </tbody>
                </table>
                <div class="select_page">
                    <div class="search_div">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="用户名称..." id="search">
                            <label class="input-group-addon" id="searchButton"></label>
                        </div>
                    </div>
                    <div id="pageTool">

                    </div>
                </div>

            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/highcharts.js" ></script>
    <%-- <script src="./js/exporting.js"></script> --%>
    <script src="./js/highcharts-zh_CN.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/rechargeDrawStatus.js" ></script>
</body>
</html>
