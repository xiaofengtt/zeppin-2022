<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/auditingItem.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00600067" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a class="current">待审批事项</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="auditing_item">
                    <a href="./accountMessageAuditingCheck.jsp">1、企业账户信息变动待审核事项</a>
                    <span><i id="info"></i>项</span>
                </div>

                <div class="auditing_item">
                    <a href="./rechargeAndInputAuditingCheck.jsp">2、企业账户充值、资金支出待审核事项</a>
                    <span><i id="recharge"></i>项</span>
                </div>

                <div class="auditing_item">
                    <a href="./transferAndDrawCashAuditingCheck.jsp">3、企业账户间转账待审核事项</a>
                    <span><i id="transfer"></i>项</span>
                </div>
                <div class="auditing_item">
                    <a href="./investAndRedemptionAuditingCheck.jsp">4、企业投资、赎回待审核事项</a>
                    <span><i id="invest"></i>项</span>
                </div>
                <div class="auditing_item">
                    <a href="./repaymentAuditingCheck.jsp">5、产品结算还款待审核事项</a>
                    <span><i id="balance"></i>项</span>
                </div>
                <div class="auditing_item">
                    <a href="./fundPublishInvestAuditingCheck.jsp">6、活期产品投资、赎回待审核事项</a>
                    <span><i id="current"></i>项</span>
                </div>
            </div>
        </div>
    </div>
        <script src="js/auditingItemCheck.js"></script>
</body>
</html>
