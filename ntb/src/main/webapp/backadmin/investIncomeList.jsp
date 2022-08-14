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


<title>牛投帮-后台管理系统</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00100015" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">投资一览</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <ul class="select_invest_list">
                    <li>
                        <a href="./investBuyList.jsp">
                            认购中
                            <span>(0)<span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" style="color:#E0615F">
                            收益中
                            <span>(0)</span>
                        </a>
                    </li>
                    <li>
                        <a href="./investFinishList.jsp">
                            已结束
                            <span>(0)</span>
                        </a>
                    </li>
                </ul>

                <%--筛选--%>
                <div class="searchDiv">
                    <div class="input-group">
                        <input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
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
                        <a onclick="filterControl(this)" id="filterController">展开</a>
                    </div>
                </div>
                <%--筛选结束--%>

                <table class="msg_table" cellspacing="3" cellpadding="0">
                    <tr class="first_tr">
                        <th width="10%">到期日</th>
                        <th width="40%">银行理财产品信息</th>
                        <th width="10%">投资金额</th>
                        <th width="12%">预期收益</th>
                        <th width="12%">操作</th>
                    </tr>

                    <tr>
                        <td>
                            300天
                        </td>
                        <td class="product_name">
                            <p>【中国农业银行】“金钥匙安心得利”2017年第5412期人民币理财产品（高净值用户）</p>
                            <ul>
                                <li>
                                    <span class="big">5.52</span><span>%</span>
                                </li>

                                <li>
                                    365天
                                </li>

                                <li>
                                    N123123
                                </li>

                                <li>
                                    非保本浮动收益
                                </li>

                                <li>
                                    5万元起购
                                </li>

                                <li>
                                    计息日：2017年9月20日 至 2017年9月28日
                                </li>
                            </ul>
                        </td>
                        <td>300000.00</td>
                        <td class="color_orange">15000.00</td>
                        <td><a href="#">查看</a></td>
                    </tr>

                    <tr>
                        <td>
                            灵活期限
                        </td>
                        <td class="product_name">
                            <p>【中国农业银行】“金钥匙安心得利”2017年第5412期人民币理财产品（高净值用户）</p>
                            <ul>
                                <li>
                                    <span class="big">5.52</span><span>%</span>
                                </li>

                                <li>
                                    365天
                                </li>

                                <li>
                                    N123123
                                </li>

                                <li>
                                    非保本浮动收益
                                </li>

                                <li>
                                    5万元起购
                                </li>

                                <li>
                                    计息日：2017年9月20日 至 2017年9月28日
                                </li>
                            </ul>
                        </td>
                        <td>300000.00</td>
                        <td class="color_orange">15000.00</td>
                        <td>
                            <a href="#">查看</a>
                            <a href="#">投资</a>
                            <a href="#">赎回</a>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span class="color_orange">今日到期</span>
                        </td>
                        <td class="product_name">
                            <p>【中国农业银行】“金钥匙安心得利”2017年第5412期人民币理财产品（高净值用户）</p>
                            <ul>
                                <li>
                                    <span class="big">5.52</span><span>%</span>
                                </li>

                                <li>
                                    365天
                                </li>

                                <li>
                                    N123123
                                </li>

                                <li>
                                    非保本浮动收益
                                </li>

                                <li>
                                    5万元起购
                                </li>

                                <li>
                                    计息日：2017年9月20日 至 2017年9月28日
                                </li>
                            </ul>
                        </td>
                        <td>300000.00</td>
                        <td class="color_orange">15000.00</td>
                        <td>
                            <a href="#">查看</a>
                            <a href="#">投资</a>
                            <a href="#">赎回</a>
                        </td>
                    </tr>


                </table>

            </div>
        </div>
    </div>
</body>
</html></html>
