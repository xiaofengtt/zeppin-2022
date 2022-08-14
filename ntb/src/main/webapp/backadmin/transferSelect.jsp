<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/card.css">
<link rel="stylesheet" href="./css/transferSelect.css">

<title>牛投帮-后台管理系统</title>
<script type="text/template" id="queboxTpl">

    <div class="transfer_card text_box">
        <input type="hidden" value="{{:uuid}}" id="uuid">
        <p class="transfer_card_title">{{:bankName}}</p>
        <span class="transfer_card_num">{{:accountNum}}</span>
        <span class="transfer_card_type">
            {{if type == "third"}}第三方{{/if}}
            {{if type == "invest"}}投资户{{/if}}
            {{if type == "collect"}}募集户{{/if}}
            {{if type == "redeem"}}结算户{{/if}}
        </span>
        <img src="./img/li1_4.png" alt="">
    </div>

</script>
<script type="text/template" id="msgTpl">
    <div class="msg_card text_box">
        <p class="text_title">{{:accountName}}</p>
        <span class="company_name">{{:companyName}}</span>
        <span class="card_number">{{:accountNumStar}}</span>
        <span class="card_balance">
            余额：
            <i>{{:accountBalanceCN}}</i>
        </span>
    </div>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a class="current">转账</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                    <div class="card_border">
                        <div id="msgCnt"></div>
                    </div>

                    <hr style="margin:20px 0;color:#c1c1c1">

                    <p class="text_title transfer_box_title">转账到</p>
                    <div class="transfer_card_box">

                        <%--模板--%>
                        <div id="queboxCnt"></div>
                        <div id="pageTool"></div>
                    </div>

                    <div class="btn_group">
                        <a href="javascript:;" onclick="history.go(-1)">取消</a>
                        <a href="javascript:;" id="allRight">确认</a>
                    </div>
            <div>
        </div>
    </div>

    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="js/transferSelect.js"></script>
</body>
</html>
