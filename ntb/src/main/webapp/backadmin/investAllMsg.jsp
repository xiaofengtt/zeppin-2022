<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" type="text/css" href="css/paging.css">
<link rel="stylesheet" href="./css/investProductMessage.css">
<title>牛投帮-后台管理系统</title>

<style>
    .main{
        width:97%;
        margin:0 auto;
    }
    ul{
        padding:0;
    }
</style>
<script type="text/template" id="nameTpl">
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
</script>

<script type="text/template" id="listTpl">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        {{if type == "invest"}}
            <td class="totalAmount_msg color_green">{{:totalAmount}}</td>
        {{else type == "redeem"}}
            <td class="totalAmount_msg color_orange">{{:totalAmount + totalReturn}}</td>
        {{/if}}
        <td>{{:typeCN}}</td>
        <td>{{:reason}}</td>
        <td>{{:creatorName}}</td>
        <td>{{:createtimeCN}}</td>
        <td>{{:checkerName}}</td>
        <td>{{:checktimeCN}}</td>
        <td>
            {{if type == "invest"}}
                <a href="./investAuditingMsgCheck.jsp?uuid={{:uuid}}" target="_blank">详细</a>
            {{else type == "redeem"}}
                <a href="./redeemptionAuditingMsgCheck.jsp?uuid={{:uuid}}" target="_blank">详细</a>
            {{/if}}

        </td>

    </tr>
</script>
</head>
<body>
    <div class="main">
        <div class="product_name">
            <div id="nameCnt">

            </div>
        </div>
        <hr style="color:#c1c1c1">
        <table class="msg_table" cellpadding="0" cellspacing="0">
            <tr class="first_tr">
                <th>金额</th>
                <th>类型</th>
                <th>备注信息</th>
                <th>经办人</th>
                <th>提交时间</th>
                <th>审核人</th>
                <th>审核时间</th>
                <th>操作</th>
            </tr>
            <tbody id="listCnt">

            </tbody>
        </table>
        <div id="pageTool">

        </div>
    </div>





    <script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <script type="text/javascript" src="js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
    <script type="text/javascript" src="js/changePrice.js" ></script>
    <script type="text/javascript" src="js/investAllMsg.js" ></script>
</body>
</html>
